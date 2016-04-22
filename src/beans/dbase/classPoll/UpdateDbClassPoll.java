package beans.dbase.classPoll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.Vector;
import java.util.ListIterator;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Collections;
import org.apache.tomcat.jdbc.pool.DataSource;

import beans.dbase.UpdateDb;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.sistem.AskSystem;
import beans.tools.Checker;
import beans.tools.Tool;

/**
 * Session Bean implementation class UpdateDb
 */
@Stateless
@LocalBean
public class UpdateDbClassPoll extends UpdateDb{
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
     * Default constructor. 
     */
    public UpdateDbClassPoll() {
        // TODO Auto-generated constructor stub
    }

    public UpdateDbClassPoll(String operatorNpm) {
        // TODO Auto-generated constructor stub
    	super(operatorNpm);
    	this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
    }

    public void updateClassPollRules(String thsms, String kode_kampus, String[]kdpst, String[]alur, String[]kdpst_urut) {
    	int i=0;
    	if(kdpst!=null && kdpst.length>0) {
    		System.out.println("sampe sini juga");
    		try {
    			Vector v = new Vector();
    			ListIterator li = v.listIterator();
        		//String ipAddr =  request.getRemoteAddr();
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//update kalo blum ada recordnya lalu diinsert
        		stmt = con.prepareStatement("update CLASS_POOL_RULES set TKN_VERIFICATOR=? where THSMS=? and KDPST=? and KODE_KAMPUS=?");
        		for(int j=0;j<kdpst.length;j++) {
        			if(alur[j]==null || Checker.isStringNullOrEmpty(alur[j])) {
        				stmt.setNull(1, java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(1, alur[j]);
        			}
        			stmt.setString(2, thsms);
        			stmt.setString(3, kdpst[j]);
        			stmt.setString(4, kode_kampus);
        			i=0;
        			System.out.println(j+". "+alur[j]+" , "+thsms+" , "+kdpst[j]+" , "+kode_kampus);
        			i = stmt.executeUpdate();
        			System.out.println("i="+i);
        			if(i<1) {
        				li.add(alur[j]+"`"+kdpst[j]);
        			}
        		}
        		//bila ada yg butuh diinsert
        		if(v!=null && v.size()>0) {
        			stmt = con.prepareStatement("insert into CLASS_POOL_RULES(THSMS,KDPST,TKN_VERIFICATOR,URUTAN,KODE_KAMPUS)VALUES(?,?,?,?,?)");
        			li = v.listIterator();
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				StringTokenizer st = new StringTokenizer(brs,"`");
        				String alur_ = st.nextToken();
        				String kdpst_ = st.nextToken();
        				stmt.setString(1, thsms);
        				stmt.setString(2, kdpst_);
        				if(alur_==null || Checker.isStringNullOrEmpty(alur_)) {
        					stmt.setNull(3,java.sql.Types.VARCHAR);
        				}
        				else {
        					stmt.setString(3, alur_);
        				}
        				stmt.setBoolean(4, false);
        				stmt.setString(5, kode_kampus);
        				stmt.executeUpdate();
        			}
        		}
        		//reset uutan dulu
        		stmt = con.prepareStatement("update CLASS_POOL_RULES set URUTAN=? where THSMS=? and KODE_KAMPUS=?");
        		stmt.setBoolean(1, false);
        		stmt.setString(2, thsms);
        		stmt.setString(3, kode_kampus);
        		stmt.executeUpdate();
        		//update urutan
        		if(kdpst_urut!=null && kdpst_urut.length>0) {
        			stmt = con.prepareStatement("update CLASS_POOL_RULES set URUTAN=? where THSMS=? and KDPST=? and KODE_KAMPUS=?");
        			for(int j=0;j<kdpst_urut.length;j++) {
        				stmt.setBoolean(1, true);
        				stmt.setString(2, thsms);
        				stmt.setString(3, kdpst_urut[j]);
        				stmt.setString(4, kode_kampus);
        				stmt.executeUpdate();
        			}
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
    	}
    		
    	//return i;
    }	    
 }
