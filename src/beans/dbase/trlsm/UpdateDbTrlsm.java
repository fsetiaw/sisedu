package beans.dbase.trlsm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import beans.dbase.UpdateDb;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Tool;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class UpdateDbTrlsm
 */
@Stateless
@LocalBean
public class UpdateDbTrlsm extends UpdateDb {
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
    public UpdateDbTrlsm() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbTrlsm(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }

    /*
     * DEPRECATED pake yg 4 input var
     */
    public void updStmhs(String kdpst, String npmhs, String[]thsms_stmhs) {
    	int upd = 0;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	Vector v = null;
    	try {
    		if(thsms_stmhs!=null && thsms_stmhs.length>0) {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		v = new Vector();
            	li = v.listIterator();
        		stmt = con.prepareStatement("update TRLSM set STMHS=? where THSMS=? and NPMHS=?");
        		for(int i=0;i<thsms_stmhs.length;i++) {
        			if(thsms_stmhs[i]!=null && !Checker.isStringNullOrEmpty(thsms_stmhs[i])) {
        				st = new StringTokenizer(thsms_stmhs[i],"`");
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				//System.out.println("thsms_stmhs["+i+"]="+thsms_stmhs[i]);
        				if(stmhs==null || Checker.isStringNullOrEmpty(stmhs)) {
        					stmt.setNull(1, java.sql.Types.VARCHAR);
        				}
        				else {
        					stmt.setString(1, stmhs);
        				}
        				stmt.setString(2,thsms);
        				stmt.setString(3,npmhs);
        				upd = stmt.executeUpdate();
        				//System.out.println(upd);
        				if(upd<1) {
        					//no prev rec - harus diinsert
        					li.add(thsms_stmhs[i]);
        				}
        			}
        		}
        		//System.out.println("vsizer="+v.size());
        		li = v.listIterator();
        		if(li.hasNext()) {
        			stmt = con.prepareStatement("insert into TRLSM(THSMS,KDPST,NPMHS,STMHS)values(?,?,?,?)");
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				st = new StringTokenizer(brs,"`");
        				//System.out.println("bariss="+brs);
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				stmt.setString(1, thsms);
        				stmt.setString(2, kdpst);
        				stmt.setString(3, npmhs);
        				if(stmhs==null || Checker.isStringNullOrEmpty(stmhs)) {
        					stmt.setNull(4, java.sql.Types.VARCHAR);
        				}
        				else {
        					stmt.setString(4, stmhs);
        				}
        				stmt.executeUpdate();
        			}
        		}
    		}
    	} 
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		} 
		finally {
			if (con!=null) {
				try {
					con.close();
				}
				catch (Exception ignore) {
					//System.out.println(ignore);
				}
			}
		}
    	//return upd;
    }
    
    public void updStmhs(String kdpst, String npmhs, String[]thsms_stmhs, String[] alasan) {
    	int upd = 0;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	Vector v = null;
    	try {
    		if(thsms_stmhs!=null && thsms_stmhs.length>0) {
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		v = new Vector();
            	li = v.listIterator();
        		stmt = con.prepareStatement("update TRLSM set STMHS=?,NOTE=? where THSMS=? and NPMHS=?");
        		for(int i=0;i<thsms_stmhs.length;i++) {
        			if(thsms_stmhs[i]!=null && !Checker.isStringNullOrEmpty(thsms_stmhs[i])) {
        				st = new StringTokenizer(thsms_stmhs[i],"`");
        				//System.out.println("thsms_stmhs[i]="+thsms_stmhs[i]);
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				//System.out.println("thsms_stmhs["+i+"]="+thsms_stmhs[i]);
        				if(stmhs==null || Checker.isStringNullOrEmpty(stmhs)) {
        					stmt.setNull(1, java.sql.Types.VARCHAR);
        				}
        				else {
        					stmt.setString(1, stmhs);
        				}
        				
        				if(alasan==null || alasan[i]==null || Checker.isStringNullOrEmpty(alasan[i])) {
        					stmt.setNull(2, java.sql.Types.VARCHAR);
        				}
        				else {
        					stmt.setString(2, alasan[i]);
        				}
        				
        				stmt.setString(3,thsms);
        				stmt.setString(4,npmhs);
        				upd = stmt.executeUpdate();
        				//System.out.println(upd);
        				if(upd<1) {
        					//no prev rec - harus diinsert
        					li.add(thsms_stmhs[i]+"`"+alasan[i]);
        				}
        			}
        		}
        		//System.out.println("vsizer="+v.size());
        		li = v.listIterator();
        		if(li.hasNext()) {
        			stmt = con.prepareStatement("insert into TRLSM(THSMS,KDPST,NPMHS,STMHS,NOTE)values(?,?,?,?,?)");
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				st = new StringTokenizer(brs,"`");
        				//System.out.println("bariss="+brs);
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				String note = st.nextToken();
        				stmt.setString(1, thsms);
        				stmt.setString(2, kdpst);
        				stmt.setString(3, npmhs);
        				if(stmhs==null || Checker.isStringNullOrEmpty(stmhs)) {
        					stmt.setNull(4, java.sql.Types.VARCHAR);
        				}
        				else {
        					stmt.setString(4, stmhs);
        				}
        				if(note==null || Checker.isStringNullOrEmpty(note)) {
        					stmt.setNull(5, java.sql.Types.VARCHAR);
        				}
        				else {
        					stmt.setString(5, note);
        				}
        				stmt.executeUpdate();
        			}
        		}
        		
        		//updaate trnlm 
        		stmt = con.prepareStatement("delete from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=?");
        		for(int i=0;i<thsms_stmhs.length;i++) {
        			if(thsms_stmhs[i]!=null && !Checker.isStringNullOrEmpty(thsms_stmhs[i])) {
        				st = new StringTokenizer(thsms_stmhs[i],"`");
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				if(stmhs!=null && (stmhs.equalsIgnoreCase("C")||stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("DO"))) {
        					stmt.setString(1, thsms);
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        				}
        			}
        		}	
        		//updaate trakm 
        		stmt = con.prepareStatement("delete from TRAKM where THSMSTRAKM=? and NPMHSTRAKM=?");
        		for(int i=0;i<thsms_stmhs.length;i++) {
        			if(thsms_stmhs[i]!=null && !Checker.isStringNullOrEmpty(thsms_stmhs[i])) {
        				st = new StringTokenizer(thsms_stmhs[i],"`");
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				if(stmhs!=null && (stmhs.equalsIgnoreCase("C")||stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("DO"))) {
        					stmt.setString(1, thsms);
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        				}
        			}
        		}
        		//update krs notification
        		stmt = con.prepareStatement("delete from KRS_NOTIFICATION where THSMS_TARGET=? and NPM_SENDER=?");
        		for(int i=0;i<thsms_stmhs.length;i++) {
        			if(thsms_stmhs[i]!=null && !Checker.isStringNullOrEmpty(thsms_stmhs[i])) {
        				st = new StringTokenizer(thsms_stmhs[i],"`");
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				if(stmhs!=null && (stmhs.equalsIgnoreCase("C")||stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("DO"))) {
        					stmt.setString(1, thsms);
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        				}
        			}
        		}
        		
        		//update daftar ulang
        		stmt = con.prepareStatement("delete from DAFTAR_ULANG where THSMS=? and NPMHS=?");
        		for(int i=0;i<thsms_stmhs.length;i++) {
        			if(thsms_stmhs[i]!=null && !Checker.isStringNullOrEmpty(thsms_stmhs[i])) {
        				st = new StringTokenizer(thsms_stmhs[i],"`");
        				String thsms = st.nextToken();
        				String stmhs = st.nextToken();
        				if(stmhs!=null && (stmhs.equalsIgnoreCase("C")||stmhs.equalsIgnoreCase("K")||stmhs.equalsIgnoreCase("DO"))) {
        					stmt.setString(1, thsms);
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        				}
        			}
        		}
        		
        		
    		}
    	} 
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		} 
		finally {
			if (con!=null) {
				try {
					con.close();
				}
				catch (Exception ignore) {
					//System.out.println(ignore);
				}
			}
		}
    	//return upd;
    }
    
 }
