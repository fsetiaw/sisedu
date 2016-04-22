package beans.dbase.moodle;

import beans.dbase.UpdateDb;
import beans.tools.Checker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ListIterator;
import java.util.Vector;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class UpdateDbMoodle
 */
@Stateless
@LocalBean
public class UpdateDbMoodle extends UpdateDb {
	String operatorNpm;
	String operatorNmm;
	String tknOperatorNickname;
	boolean petugas;
	String url;     
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	DataSource ds;  
              
    /**
     * @see UpdateDb#UpdateDb()
     */
    public UpdateDbMoodle() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbMoodle(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
    
    public int updateUsrTable(String npm) {
    	//kalo dah ada record berati cuma reset pwd
    	//System.out.println("mpm="+npm);
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	int i = 0;
    	try {
    		//System.out.println("01");
			Context initContext  = new InitialContext();
			//System.out.println("02");
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			//System.out.println("03");
			ds = (DataSource)envContext.lookup("jdbc/MyMoodle");
			//System.out.println("04");
			con = ds.getConnection();
			//System.out.println("1");
			stmt = con.prepareStatement("update mdl_user set password=MD5(?) where username=?");
			//System.out.println("2");
			stmt.setString(1, npm+"_Usr_pwd");
			stmt.setString(2, npm+"_Usr");
			
			i = stmt.executeUpdate();
			//System.out.println("i="+i);
		}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return i;
    }
    
    public int insertUsrTable(String npm, String nmm, String obj_nick) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	int i = 0;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/MyMoodle");
			con = ds.getConnection();
			stmt = con.prepareStatement("insert into mdl_user(confirmed,mnethostid,username,password,firstname,lastname,email)values(?,?,?,MD5(?),?,?,?)");
			stmt.setInt(1, 1);
			stmt.setInt(2, 1);
			stmt.setString(3, npm+"_Usr");
			stmt.setString(4, npm+"_Usr_pwd");
			stmt.setString(5, nmm);
			stmt.setString(6, nmm);
			stmt.setString(7, "default@default.com");
			i = stmt.executeUpdate();		
			stmt = con.prepareStatement("select id from mdl_user where username=?");
			stmt.setString(1, npm+"_Usr");
			rs = stmt.executeQuery();
			rs.next();
			long id  = rs.getLong(1);
			stmt = con.prepareStatement("insert into mdl_role_assignments(roleid,contextid,userid)values(?,?,?)");
			if(obj_nick.contains("MHS")) {
				//mhs
				stmt.setLong(1, 5);
				stmt.setLong(2, 2);
			}
			else {
				//dosen
				stmt.setLong(1, 3);
				stmt.setLong(2, 2);
			}
			stmt.setLong(3,id);
			stmt.executeUpdate();
			
			
		}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return i;
    }

}
