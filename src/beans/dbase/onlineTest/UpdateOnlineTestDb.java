package beans.dbase.onlineTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.StringTokenizer;
import beans.dbase.UpdateDb;
import beans.sistem.AskSystem;
import beans.tools.Checker;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class UpdateOnlineTestDb
 */
@Stateless
@LocalBean
public class UpdateOnlineTestDb extends UpdateDb {
	String url;     
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	DataSource ds;
    /**
     * @see UpdateDb#UpdateDb()
     */
    public UpdateOnlineTestDb() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String validasiUjianBlmEnd(String idJadwal) {
    	boolean pass = true;
    	String stat = "";
    	int id = 0;
    	try {
    		id = Integer.valueOf(idJadwal).intValue();
    	}
    	catch(NumberFormatException nfe) {
    		pass = false;
    	}
    	if(pass) {
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from JADWAL_TEST  where ID=?");
    			stmt.setInt(1, id);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				if(rs.getBoolean("DONE")==true && rs.getBoolean("REUSABLE")==false) {
    					stat = "UJIAN SELESAI";
    				}
    				else {
    					stat = "UJIAN REUSABLE";
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
    			if (con!=null) {
    				try {
    					con.close();
    				}
    				catch (Exception ignore) {
    					System.out.println(ignore);
    				}
    			}
    		}	
    	}
    	return stat;	
    }
    
    public String stopUjian(String idJadwal,String npmopr,String nmmopr) {
    	boolean pass = true;
    	String stat = "";
    	int id = 0;
    	try {
    		id = Integer.valueOf(idJadwal).intValue();
    	}
    	catch(NumberFormatException nfe) {
    		pass = false;
    	}
    	if(pass) {
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			
    			/*
    			 * update civitas_jadwal_bridge set taken bagi mhs yg mengikuti;
    			 * cek table TEST_RECORD_MHS untuk ngecek siapa yg mengikuti lalu update table civitas_jadwal_bride
    			 * set taken=true bila yg mengikuti;
    			 */
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbSchemaBankSoal());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select distinct ID_CIVITAS_JADWAL_BRIDGE from RECORD_TEST_MHS");
    			rs = stmt.executeQuery();
    			String tkn_id = "";
    			while(rs.next()) {
    				tkn_id = tkn_id+rs.getInt("ID_CIVITAS_JADWAL_BRIDGE")+",";
    			}
    			
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			/*
    			 * update CIVITAS_JADWAL_BRIDGE set taken = true;
    			 */
    			StringTokenizer st = new StringTokenizer(tkn_id,",");
    			if(st.countTokens()>0) {
    				stmt = con.prepareStatement("update CIVITAS_JADWAL_BRIDGE set TAKEN=? where ID=?");
    				while(st.hasMoreTokens()) {
    					long id1 = Long.valueOf(st.nextToken()).longValue();
    					stmt.setBoolean(1,true);
    					stmt.setLong(2, id1);
    					stmt.executeUpdate();
    				}
    			}	
    			stmt = con.prepareStatement("update JADWAL_TEST set INPROGRESS=?,PAUSE=?,DONE=?,CANCELED=?,REAL_TEST_TIME_END=?,NPMOPR=?,NMMOPR=? where ID=?");
    			stmt.setBoolean(1,false);
    			stmt.setBoolean(2,false);
    			stmt.setBoolean(3,true);
    			stmt.setBoolean(4,false);
    			stmt.setTimestamp(5, AskSystem.getCurrentTimestamp());
    			stmt.setString(6, npmopr);
    			stmt.setString(7, nmmopr);
    			stmt.setInt(8, id);
    			int i = stmt.executeUpdate();
    			if(i>0) {
    				stat="UJIAN SELESAI";//sync dgn dashListTest.jsp
    			}
    			else {
    				stat="ERROR PADA STOP UJIAN";
    			}
    		}
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
    		finally {
    			if (con!=null) {
    				try {
    					con.close();
    				}
    				catch (Exception ignore) {
    					System.out.println(ignore);
    				}
    			}
    		}	
    	}
    	return stat;
    }
    
    
    public String startUjian(String idJadwal,String npmopr,String nmmopr) {
    	boolean pass = true;
    	String stat = "";
    	int id = 0;
    	try {
    		id = Integer.valueOf(idJadwal).intValue();
    	}
    	catch(NumberFormatException nfe) {
    		pass = false;
    	}
    	stat = validasiUjianBlmEnd(idJadwal);
    	if(stat!=null && stat.equalsIgnoreCase("UJIAN SELESAI")) {
    		pass = false;
    	}
    	if(pass) {
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("update JADWAL_TEST set INPROGRESS=?,PAUSE=?,DONE=?,CANCELED=?,REAL_TEST_TIME_START=?,NPMOPR=?,NMMOPR=? where ID=?");
    			stmt.setBoolean(1, true);
    			stmt.setBoolean(2,false);
    			stmt.setBoolean(3,false);
    			stmt.setBoolean(4,false);
    			stmt.setTimestamp(5, AskSystem.getCurrentTimestamp());
    			stmt.setString(6, npmopr);
    			stmt.setString(7, nmmopr);
    			stmt.setInt(8, id);
    			int i = stmt.executeUpdate();
    			if(i>0) {
    				stat="SEDANG BERJALAN";//sync dgn dashListTest.jsp
    			}
    			else {
    				stat="ERROR PADA START UJIAN";
    			}
    		}
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
    		finally {
    			if (con!=null) {
    				try {
    					con.close();
    				}
    				catch (Exception ignore) {
    					System.out.println(ignore);
    				}
    			}
    		}	
    	}
    	return stat;
    }
    

    public String pauseUjian(String idJadwal,String npmopr,String nmmopr) {
    	boolean pass = true;
    	String stat = "";
    	int id = 0;
    	try {
    		id = Integer.valueOf(idJadwal).intValue();
    	}
    	catch(NumberFormatException nfe) {
    		pass = false;
    	}
    	stat = validasiUjianBlmEnd(idJadwal);
    	if(stat!=null && stat.equalsIgnoreCase("UJIAN SELESAI")) {
    		pass = false;
    	}
    	if(pass) {
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("update JADWAL_TEST set PAUSE=? where ID=?");
    			stmt.setBoolean(1, true);
    			stmt.setInt(2, id);
    			int i = stmt.executeUpdate();
    			if(i>0) {
    				stat="SEDANG REHAT";//sync dgn dashListTest.jsp
    			}
    			else {
    				stat="ERROR PADA REHAT UJIAN";
    			}
    		}
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
    		finally {
    			if (con!=null) {
    				try {
    					con.close();
    				}
    				catch (Exception ignore) {
    					System.out.println(ignore);
    				}
    			}
    		}	
    	}
    	return stat;
    }

    public String replayUjian(String idJadwal,String npmopr,String nmmopr) {
    	boolean pass = true;
    	String stat = "";
    	int id = 0;
    	try {
    		id = Integer.valueOf(idJadwal).intValue();
    	}
    	catch(NumberFormatException nfe) {
    		pass = false;
    	}
    	stat = validasiUjianBlmEnd(idJadwal);
    	if(stat!=null && stat.equalsIgnoreCase("UJIAN SELESAI")) {
    		pass = false;
    	}
    	if(pass) {
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("update JADWAL_TEST set PAUSE=? where ID=?");
    			stmt.setBoolean(1, false);
    			stmt.setInt(2, id);
    			int i = stmt.executeUpdate();
    			if(i>0) {
    				stat="SEDANG BERJALAN";//sync dgn dashListTest.jsp
    			}
    			else {
    				stat="ERROR PADA REPLAY UJIAN";
    			}
    		}
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
    		finally {
    			if (con!=null) {
    				try {
    					con.close();
    				}
    				catch (Exception ignore) {
    					System.out.println(ignore);
    				}
    			}
    		}	
    	}
    	return stat;
    }
    
    
    
    public String updateNoteUjian(String idJadwal,String comment) {
    	//return comment if pass kalo ngga return prev value
    	boolean setNull = false;
    	if(Checker.isStringNullOrEmpty(comment)) {
    		setNull = true;
    	}
    	String tmp = null;
    	boolean pass = true;
    	int id = 0;
    	try {
    		id = Integer.valueOf(idJadwal).intValue();
    	}
    	catch(NumberFormatException nfe) {
    		pass = false;
    	}
    	if(pass) {
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("update JADWAL_TEST set NOTE_PENGAWAS=? where ID=?");
    			if(setNull) {
    				stmt.setNull(1, java.sql.Types.VARCHAR);
    			}
    			else {
    				stmt.setString(1, comment);
    			}	
    			stmt.setInt(2, id);
    			int i = stmt.executeUpdate();
    			if(i>0) {
    				tmp = ""+comment;
    			}
    			else {
    				stmt = con.prepareStatement("select NOTE_PENGAWAS from JADWAL_TEST where ID=?");
        			stmt.setInt(1, id);
        			rs = stmt.executeQuery();
        			tmp = ""+rs.getString("NOTE_PENGAWAS");
    			}
    		}
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
    		finally {
    			if (con!=null) {
    				try {
    					con.close();
    				}
    				catch (Exception ignore) {
    					System.out.println(ignore);
    				}
    			}
    		}	
    	}
    	return tmp;
    }
    
    public int insJawabanMhs(String jawaban,String idCivitasJadwalBridge,String idOnlineTest, String idSoal, String RealDateTimeStart) {
    	int i=0;
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbSchemaBankSoal());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("update RECORD_TEST_MHS set JAWABAN=? where ID_CIVITAS_JADWAL_BRIDGE=? AND ID_ONLINE_TEST=? AND ID_SOAL_TEST=? AND REAL_TEST_TIME_START=?");
    		stmt.setString(1, jawaban);
    		stmt.setInt(2, Integer.valueOf(idCivitasJadwalBridge).intValue());
    		stmt.setInt(3, Integer.valueOf(idOnlineTest).intValue());
    		stmt.setInt(4, Integer.valueOf(idSoal).intValue());
    		stmt.setTimestamp(5, Timestamp.valueOf(RealDateTimeStart));
    		i = stmt.executeUpdate();
    		if(i<1) {
    			stmt=con.prepareStatement("INSERT INTO RECORD_TEST_MHS (ID_CIVITAS_JADWAL_BRIDGE,ID_ONLINE_TEST,ID_SOAL_TEST,JAWABAN,REAL_TEST_TIME_START)VALUES(?,?,?,?,?)");
        		stmt.setInt(1, Integer.valueOf(idCivitasJadwalBridge).intValue());
        		stmt.setInt(2, Integer.valueOf(idOnlineTest).intValue());
        		stmt.setInt(3, Integer.valueOf(idSoal).intValue());
        		stmt.setString(4, jawaban);
        		stmt.setTimestamp(5, Timestamp.valueOf(RealDateTimeStart));
        		i = stmt.executeUpdate();
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
    	return i;
    }

}
