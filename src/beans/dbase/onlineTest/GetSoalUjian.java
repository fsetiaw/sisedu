package beans.dbase.onlineTest;

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

import beans.dbase.SearchDb;

/**
 * Session Bean implementation class GetSoalUjian
 */
@Stateless
@LocalBean
public class GetSoalUjian extends SearchDb {


    /**
     * Default constructor. 
     */

	String url;     
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	DataSource ds;   
    /**
     * @see SearchDb#SearchDb()
     */
    public GetSoalUjian() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(Connection)
     */
    public GetSoalUjian(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public String getSoal(int idsoal) {
    	//Vector v = new Vector();
    	//ListIterator li = v.listIterator();
    	System.out.println("getSoal");
    	String tknSoal="";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbSchemaBankSoal());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from SOAL where ID=?");
    		stmt.setInt(1,Integer.valueOf(idsoal).intValue());
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			System.out.println("getSoalii");
    			String soal = rs.getString("SOAL");
    			String tkn_choice = rs.getString("TKN_MULTIPLE_CHOICE");
    			tkn_choice=tkn_choice.replace("?", "##");
    			String pictFile = ""+rs.getString("PICT_FILE");
    			String audioFile = ""+rs.getString("AUDIO_FILE");
    			String videoFile = ""+rs.getString("VIDEO_FILE");
    			tknSoal = soal+"$$"+tkn_choice+"$$"+pictFile+"$$"+audioFile+"$$"+videoFile;
    			//li.add(soal);
    			//li.add(tkn_choice);
    		}
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
    	return tknSoal;
    }	
}
