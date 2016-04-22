package beans.dbase.onlineTest;

import beans.dbase.SearchDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class SearchOnlineTestDb
 */
@Stateless
@LocalBean
public class SearchOnlineTestDb extends SearchDb {
	String url;     
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	DataSource ds;
    /**
     * @see SearchDb#SearchDb()
     */
    public SearchOnlineTestDb() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(Connection)
     */
    public SearchOnlineTestDb(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }

    public String getJawabanMhs(String idCivJadwalBridge,String idOnlineTest,String idSoalTest, String realTestTimeStart) {
    	String jawaban="null";
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbSchemaBankSoal());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from RECORD_TEST_MHS where ID_CIVITAS_JADWAL_BRIDGE=? and ID_ONLINE_TEST=? and ID_SOAL_TEST=? AND REAL_TEST_TIME_START=?");
    		stmt.setInt(1, Integer.valueOf(idCivJadwalBridge).intValue());
    		stmt.setInt(2, Integer.valueOf(idOnlineTest).intValue());
    		stmt.setInt(3, Integer.valueOf(idSoalTest).intValue());
    		stmt.setTimestamp(4, Timestamp.valueOf(realTestTimeStart));
    		rs = stmt.executeQuery();
    		if(rs.next()){
    			jawaban = rs.getString("JAWABAN");
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
    	return jawaban;
    }

    public String getStatusUjian(String idJadwalTest) {
    	String status_cancel_done_inprogress_pause="null";
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from JADWAL_TEST where ID=?");
    		if(idJadwalTest!=null) {
    			System.out.println("masup");
    			stmt.setInt(1,Integer.valueOf(idJadwalTest).intValue());
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				String cancel = ""+rs.getBoolean("CANCELED");
    				String done = ""+rs.getBoolean("DONE");
    				String inprogress = ""+rs.getBoolean("INPROGRESS");
    				String pause = ""+rs.getBoolean("PAUSE");
    				status_cancel_done_inprogress_pause = cancel+","+done+","+inprogress+","+pause;
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
    	return status_cancel_done_inprogress_pause;
    }

    public String getListUjianThatReusableAndScheduledAfterToday() {
    	String tknUjian=null;
    	boolean first = true;
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from JADWAL_TEST inner join ONLINE_TEST on JADWAL_TEST.ID_ONLINE_TEST=ONLINE_TEST.ID where JADWAL_TEST >= NOW() OR REUSABLE=true");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String idOnlineTest = ""+rs.getLong("ONLINE_TEST.ID");
    			String kodeNamaTest = rs.getString("KODE_NAMA_TEST");
    			String keterTest = rs.getString("KETERANGAN_TEST");
    			String totSoal = ""+rs.getInt("TOTAL_SOAL");
    			String totTime = ""+rs.getInt("TOTAL_WAKTU");
    			String passGrade = ""+rs.getFloat("PASSING_GRADE");
    			String idJadwalTest = ""+rs.getLong("JADWAL_TEST.ID");
    			String jadwalTest = ""+rs.getTimestamp("JADWAL_TEST");
    			String rilTestTimeStart = ""+rs.getTimestamp("REAL_TEST_TIME_START");
    			String rilTestTimeEnd = ""+rs.getTimestamp("REAL_TEST_TIME_END");
    			String canceled = ""+rs.getBoolean("CANCELED");
    			String done = ""+rs.getBoolean("DONE");
    			String inprogress = ""+rs.getBoolean("INPROGRESS");
    			String pause = ""+rs.getBoolean("PAUSE");
    			String npmOper = ""+rs.getString("NPMOPR");
    			String nmmOper = ""+rs.getString("NMMOPR");
    			String totMhs = ""+rs.getInt("TOTAL_MHS");
    			String room = ""+rs.getInt("ROOM");
    			//String ipAllow = ""+rs.getInt("IP_ALLOW");
    			String ipAllow = ""+rs.getString("IP_ALLOW");
    			String npmOprAllow = ""+rs.getString("NPMOPR_ALLOW");
    			String note = ""+rs.getString("NOTE_PENGAWAS");
    			String reusable = ""+rs.getBoolean("REUSABLE");
    			if(first) {
    				first = false;
    				tknUjian = idOnlineTest+"$$"+kodeNamaTest+"$$"+keterTest+"$$"+totSoal+"$$"+totTime+"$$"+passGrade+"$$"+idJadwalTest+"$$"+jadwalTest+"$$"+rilTestTimeStart+"$$"+rilTestTimeEnd+"$$"+canceled+"$$"+done+"$$"+inprogress+"$$"+pause+"$$"+npmOper+"$$"+nmmOper+"$$"+totMhs+"$$"+room+"$$"+ipAllow+"$$"+npmOprAllow+"$$"+note+"$$"+reusable+"||";
    			}
    			else {
    				tknUjian = tknUjian+idOnlineTest+"$$"+kodeNamaTest+"$$"+keterTest+"$$"+totSoal+"$$"+totTime+"$$"+passGrade+"$$"+idJadwalTest+"$$"+jadwalTest+"$$"+rilTestTimeStart+"$$"+rilTestTimeEnd+"$$"+canceled+"$$"+done+"$$"+inprogress+"$$"+pause+"$$"+npmOper+"$$"+nmmOper+"$$"+totMhs+"$$"+room+"$$"+ipAllow+"$$"+npmOprAllow+"$$"+note+"$$"+reusable+"||";
    			}	
    			//System.out.println("1tknUjian="+tknUjian);
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
    	return tknUjian;
    }
    
    
    
}
