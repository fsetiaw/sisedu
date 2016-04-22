package beans.dbase.onlineTest;

import beans.dbase.SearchDb;
import beans.tools.Checker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.util.Collections;
import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;
import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class OnlineTest
 */
@Stateless
@LocalBean
public class OnlineTest extends SearchDb {
	String url;     
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	DataSource ds;   
    /**
     * @see SearchDb#SearchDb()
     */
    public OnlineTest() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(Connection)
     */
    public OnlineTest(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    
    
    public Vector getOnlineTestTerdaftarUntuk(String npmhs) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	Vector v1 = new Vector();
    	ListIterator li1 = v1.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		/*
    		 * get total login
    		 */
    		stmt = con.prepareStatement("select * from CIVITAS_JADWAL_BRIDGE where NPMHS=?");
    		stmt.setString(1,npmhs);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String idCivJdwlBridge = ""+rs.getInt("ID");
    			String idJdwlTest = ""+rs.getInt("ID_JADWAL_TEST");
    			String kdpst = ""+rs.getString("KDPST");
    			String wajib = ""+rs.getBoolean("WAJIB");
    			String rightAnswer = ""+rs.getInt("BENAR");
    			String wrongAnswer = ""+rs.getInt("SALAH");
    			String nilai = ""+rs.getFloat("NILAI");
    			String taken = ""+rs.getBoolean("TAKEN");
    			String notePengawas = ""+rs.getString("NOTE_PENGAWAS");
    			String sisaWaktu = ""+rs.getInt("SISA_WAKTU");
    			String lulus = ""+rs.getBoolean("LULUS");
    			li.add(idCivJdwlBridge+"#&"+idJdwlTest+"#&"+kdpst+"#&"+npmhs+"#&"+wajib+"#&"+rightAnswer+"#&"+wrongAnswer+"#&"+nilai+"#&"+taken+"#&"+notePengawas+"#&"+sisaWaktu+"#&"+lulus);
    		}
    		
    		stmt = con.prepareStatement("select * from JADWAL_TEST where ID=?");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"#&");
    			String idCivJdwlBridge = st.nextToken();
    			String idJdwlTest = st.nextToken();
    			String kdpst = st.nextToken();
    			st.nextToken(); //npmhs
    			String wajib = st.nextToken();
    			String rightAnswer = st.nextToken();
    			String wrongAnswer = st.nextToken();
    			String nilai = st.nextToken();
    			String taken = st.nextToken();
    			String notePengawas = st.nextToken();
    			String sisaWaktu = st.nextToken();
    			String lulus = st.nextToken();
    			stmt.setInt(1, Integer.valueOf(idJdwlTest).intValue());
    			rs = stmt.executeQuery();
    			String brs1 = "null";
    			while(rs.next()) {
    				String idOnlineTest = ""+rs.getInt("ID_ONLINE_TEST");
    				String schedDateTime = ""+rs.getTimestamp("JADWAL_TEST");
    				String RealDateTimeStart = ""+rs.getTimestamp("REAL_TEST_TIME_START");
    				String RealDateTimeEnd = ""+rs.getTimestamp("REAL_TEST_TIME_END");
    				String canceled = ""+rs.getBoolean("CANCELED");
    				String done = ""+rs.getBoolean("DONE");
    				String inprogress = ""+rs.getBoolean("INPROGRESS");
    				String pause = ""+rs.getBoolean("PAUSE");
    				String npmopr = ""+rs.getString("NPMOPR");
    				String nmmopr = ""+rs.getString("NMMOPR");
    				String mhstt = ""+rs.getInt("TOTAL_MHS");
    				String room = ""+rs.getString("ROOM");
    				String ipAllow = ""+rs.getString("IP_ALLOW");
    				String reusable = ""+rs.getBoolean("REUSABLE");
    				brs1 = idOnlineTest+"#&"+schedDateTime+"#&"+RealDateTimeStart+"#&"+RealDateTimeEnd+"#&"+canceled+"#&"+done+"#&"+inprogress+"#&"+pause+"#&"+npmopr+"#&"+nmmopr+"#&"+mhstt+"#&"+room+"#&"+ipAllow+"#&"+reusable;
    			}
    			li1.add(brs);
    			li1.add(brs1);
    		}
    		
    		
    		stmt = con.prepareStatement("select * from ONLINE_TEST where ID=?");
    		v = new Vector();
    		li = v.listIterator();
    		li1 = v1.listIterator();
    		while(li1.hasNext()) {
    			String brs1 = (String)li1.next();
    			String brs2 = (String)li1.next();
    			StringTokenizer st = new StringTokenizer(brs2,"#&");
    			String idOnlineTest = st.nextToken();
        		String schedDateTime = st.nextToken();
        		String RealDateTimeStart = st.nextToken();
        		String RealDateTimeEnd = st.nextToken();
        		String canceled = st.nextToken();
        		String done = st.nextToken();
        		String inprogress = st.nextToken();
        		String pause = st.nextToken();
        		String npmopr = st.nextToken();
        		String nmmopr = st.nextToken();
        		String mhstt = st.nextToken();
        		String room = st.nextToken();
        		String ipAllow = st.nextToken();
        		String reusable = st.nextToken();
        		stmt.setInt(1,Integer.valueOf(idOnlineTest).intValue());
        		rs = stmt.executeQuery();
        		String brs3 = "null";
        		while(rs.next()) {
        			String nmmTest = ""+rs.getString("KODE_NAMA_TEST");
        			String keterTest = ""+rs.getString("KETERANGAN_TEST");
        			String totalSoal = ""+rs.getInt("TOTAL_SOAL");
        			String totalWaktu = ""+rs.getInt("TOTAL_WAKTU");
        			String passingGrade = ""+rs.getFloat("PASSING_GRADE");
        			brs3=nmmTest+"#&"+keterTest+"#&"+totalSoal+"#&"+totalWaktu+"#&"+passingGrade;
        		}
        		li.add(brs1);
        		li.add(brs2);
        		li.add(brs3);
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
    	return v;
    }

    public Vector getOnlineTestTerdaftarUntukOperator(String oprnpm) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	Vector v1 = new Vector();
    	ListIterator li1 = v1.listIterator();
    	String brs1 = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from JADWAL_TEST order by JADWAL_TEST");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String tknNpmOprAllow = ""+rs.getString("NPMOPR_ALLOW");
    			if(tknNpmOprAllow.contains(oprnpm)) {
    				String idJadwalTest = ""+rs.getInt("ID");
    				String idOnlineTest = ""+rs.getInt("ID_ONLINE_TEST");
    				String schedDateTime = ""+rs.getTimestamp("JADWAL_TEST");
    				String RealDateTimeStart = ""+rs.getTimestamp("REAL_TEST_TIME_START");
    				String RealDateTimeEnd = ""+rs.getTimestamp("REAL_TEST_TIME_END");
    				String canceled = ""+rs.getBoolean("CANCELED");
    				String done = ""+rs.getBoolean("DONE");
    				String inprogress = ""+rs.getBoolean("INPROGRESS");
    				String pause = ""+rs.getBoolean("PAUSE");
    				String npmopr = ""+rs.getString("NPMOPR");
    				String nmmopr = ""+rs.getString("NMMOPR");
    				String mhstt = ""+rs.getInt("TOTAL_MHS");
    				String room = ""+rs.getString("ROOM");
    				String ipAllow = ""+rs.getString("IP_ALLOW");
    				String npmopr_allow = ""+rs.getString("NPMOPR_ALLOW");
    				String reusable = ""+rs.getBoolean("REUSABLE");
    				brs1 = idOnlineTest+"#&"+schedDateTime+"#&"+RealDateTimeStart+"#&"+RealDateTimeEnd+"#&"+canceled+"#&"+done+"#&"+inprogress+"#&"+pause+"#&"+npmopr+"#&"+nmmopr+"#&"+mhstt+"#&"+room+"#&"+ipAllow+"#&"+npmopr_allow+"#&"+idJadwalTest+"#&"+reusable;
    				li.add(brs1);
    			}
    		}
    		
    		stmt = con.prepareStatement("select * from CIVITAS_JADWAL_BRIDGE where ID_JADWAL_TEST=?");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			brs1 = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs1,"#&");
    			String idOnlineTest=st.nextToken();
    			String schedDateTime=st.nextToken();
    			String RealDateTimeStart=st.nextToken();
    			String RealDateTimeEnd=st.nextToken();
    			String canceled=st.nextToken();
    			String done=st.nextToken();
    			String inprogress=st.nextToken();
    			String pause=st.nextToken();
    			String npmopr=st.nextToken();
    			String nmmopr=st.nextToken();
    			String mhstt=st.nextToken();
    			String room=st.nextToken();
    			String ipAllow=st.nextToken();
    			String npmopr_allow=st.nextToken();
    			String idJadwalTest=st.nextToken();
    			String reusable=st.nextToken();
    			stmt.setInt(1, Integer.valueOf(idJadwalTest).intValue());
    			rs = stmt.executeQuery();
    			String tknNpmPeserta = null;
    			while(rs.next()) {
    				String npmhs = rs.getString("NPMHS");
    				tknNpmPeserta = tknNpmPeserta+npmhs+",";
    			}
    			li1.add(brs1);
    			li1.add(tknNpmPeserta);
    		}
    		v = new Vector();
    		li = v.listIterator();
    		stmt = con.prepareStatement("select * from ONLINE_TEST where ID=?");
    		li1=v1.listIterator();
    		while(li1.hasNext()) {
    			brs1 = (String)li1.next();
    			String brs2 = (String)li1.next();
    			StringTokenizer st = new StringTokenizer(brs1,"#&");
    			String idOnlineTest=st.nextToken();
    			String schedDateTime=st.nextToken();
    			String RealDateTimeStart=st.nextToken();
    			String RealDateTimeEnd=st.nextToken();
    			String canceled=st.nextToken();
    			String done=st.nextToken();
    			String inprogress=st.nextToken();
    			String pause=st.nextToken();
    			String npmopr=st.nextToken();
    			String nmmopr=st.nextToken();
    			String mhstt=st.nextToken();
    			String room=st.nextToken();
    			String ipAllow=st.nextToken();
    			String npmopr_allow=st.nextToken();
    			String idJadwalTest=st.nextToken();
    			String reusable=st.nextToken();
    			stmt.setInt(1,Integer.valueOf(idOnlineTest).intValue());
        		rs = stmt.executeQuery();
        		String brs3 = "null";
        		while(rs.next()) {
        			String nmmTest = ""+rs.getString("KODE_NAMA_TEST");
        			String keterTest = ""+rs.getString("KETERANGAN_TEST");
        			String totalSoal = ""+rs.getInt("TOTAL_SOAL");
        			String totalWaktu = ""+rs.getInt("TOTAL_WAKTU");
        			String passingGrade = ""+rs.getFloat("PASSING_GRADE");
        			brs3=nmmTest+"#&"+keterTest+"#&"+totalSoal+"#&"+totalWaktu+"#&"+passingGrade;
        		}
        		li.add(brs1);
        		li.add(brs2);
        		li.add(brs3);
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
    	return v;
    }
    
    
    
    public String prepSoalUjian(String idOnlineTest) {
    	/*
    	 * di sort berdasar kode group (harus diisi)
    	 * bila norut group tidak ada norut maka bisa diacak
    	 * kemudian sort berdasar norut soal, kalo tidak ada no urut maka bisa diacak
    	 */
    	String tokenKodeGroupAndListSoal = "";
    	Vector v1 = new Vector();
		ListIterator li1 = v1.listIterator();
    	if(!Checker.isStringNullOrEmpty(idOnlineTest)) {
    		try {
    			Vector v = new Vector();
    			ListIterator li = v.listIterator();
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			/*
    			 * get distinct kode_group
    			 */
    			stmt = con.prepareStatement("select distinct KODE_GROUP from TEST_SOAL_BRIDGE where ID_ONLINE_TEST=? order by NORUT_GROUP");
    			stmt.setInt(1, Integer.valueOf(idOnlineTest).intValue());
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String kodeGroup = rs.getString("KODE_GROUP");
    				li.add(kodeGroup);
    			}
    			
    			/*
    			 * cek apa ada norut group, cukup gunakan 1 rec untuk pengecekan.
    			 * bila tida ada norut - maka random collection
    			 */
    			stmt = con.prepareStatement("select * from TEST_SOAL_BRIDGE where ID_ONLINE_TEST=? and KODE_GROUP=?");
    			li = v.listIterator();
    			boolean adaNorutGroup = true;
    			if(li.hasNext()) {
    				String kodeGroup = (String)li.next();
    				stmt.setInt(1, Integer.valueOf(idOnlineTest).intValue());
    				stmt.setString(2, kodeGroup);
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					String apaAdaNoRut = ""+rs.getInt("NORUT_GROUP");
    					System.out.println("1.apaAdaNoRut="+apaAdaNoRut);
    					if(apaAdaNoRut.equalsIgnoreCase("null")||apaAdaNoRut.equalsIgnoreCase("0")) {
    						adaNorutGroup = false;
    					}
    				}
    			}
    			System.out.println("adaNorutGroup="+adaNorutGroup);
    			if(!adaNorutGroup) {
    				Collections.shuffle(v);
    			}
    			
    			/*
    			 * get soal per kode group
    			 */
    			
    			stmt = con.prepareStatement("select * from TEST_SOAL_BRIDGE where ID_ONLINE_TEST=? and KODE_GROUP=? order by NORUT_SOAL_DI_GROUP");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String kodeGroup = (String)li.next();
    				stmt.setInt(1, Integer.valueOf(idOnlineTest).intValue());
    				stmt.setString(2, kodeGroup);
    				rs = stmt.executeQuery();
    				Vector vTmp = new Vector();
    				ListIterator liTmp = vTmp.listIterator();
    				boolean adaNorutSoal = true;
    				while(rs.next()) {
    					String idSoal = ""+rs.getInt("ID_SOAL_TEST");
    					String norutSoal = ""+rs.getInt("NORUT_SOAL_DI_GROUP");
    					if(norutSoal.equalsIgnoreCase("null")||norutSoal.equalsIgnoreCase("0")) {
    						adaNorutSoal = false;
    					}
    					liTmp.add(idSoal+","+norutSoal);
    				}
    				if(!adaNorutSoal) {
    					Collections.shuffle(vTmp);
    				}
    				li1.add(kodeGroup);
    				li1.add(vTmp);
    				//System.out.println(kodeGroup);
    			}
    			
    			li1 = v1.listIterator();
    			while(li1.hasNext()) {
    				String kodeGroup = (String)li1.next();
    				tokenKodeGroupAndListSoal = tokenKodeGroupAndListSoal+kodeGroup;
    				//System.out.println("kode group = "+kodeGroup);
    				if(li1.hasNext()) {
    					tokenKodeGroupAndListSoal=tokenKodeGroupAndListSoal+",";
    				}
    				Vector vSoal = (Vector)li1.next();
    				ListIterator liSoal = vSoal.listIterator();
    				int i = 0;
    				while(liSoal.hasNext()) {
    					i++;
    					String brs = (String)liSoal.next();
    					tokenKodeGroupAndListSoal=tokenKodeGroupAndListSoal+brs;
    					if(liSoal.hasNext()) {
    						tokenKodeGroupAndListSoal=tokenKodeGroupAndListSoal+",";
    					}
    					//System.out.println(i+".soal = "+brs);
    				}
    				tokenKodeGroupAndListSoal=tokenKodeGroupAndListSoal+"#";
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
    	return tokenKodeGroupAndListSoal;
    }
    
}
