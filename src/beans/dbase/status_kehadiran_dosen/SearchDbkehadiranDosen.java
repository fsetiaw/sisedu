package beans.dbase.status_kehadiran_dosen;

import beans.dbase.SearchDb;
import beans.sistem.AskSystem;
import beans.tools.Checker;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class SearchDbkehadiranDosen
 */
@Stateless
@LocalBean
public class SearchDbkehadiranDosen extends SearchDb {
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
     * @see SearchDb#SearchDb()
     */
    public SearchDbkehadiranDosen() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbkehadiranDosen(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(Connection)
     */
    public SearchDbkehadiranDosen(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public Vector getStatusKehadiranDosenHariIniDanKedepan() {
    	String thsms_now = Checker.getThsmsNow(); 
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//stmt = con.prepareStatement("select * from STATUS_KEHADIRAN_DOSEN where THSMS="+thsms_now+" and TANGGAL<=?");
			stmt = con.prepareStatement("select * from STATUS_KEHADIRAN_DOSEN where THSMS=? and TANGGAL>=?");
			System.out.println(""+java.sql.Date.valueOf(AskSystem.getCurrentDate()));
			//stmt = con.prepareStatement("select * from STATUS_KEHADIRAN_DOSEN where THSMS=?");
			stmt.setString(1,  thsms_now );
			stmt.setDate(2,  java.sql.Date.valueOf(AskSystem.getCurrentDate()) );
			rs = stmt.executeQuery();
			while(rs.next()) {
				String cuid = ""+rs.getString("CLASS_UNIQUE_ID");
				String tgl = ""+rs.getDate("TANGGAL");
				String batal = ""+rs.getBoolean("BATAL");
				String delay_time = ""+rs.getString("PERKIRAAN_LAMA_KETERLAMBATAN");
				String real_start = ""+rs.getString("WAKTU_PERKULIAHAN_DIMULAI");
				String replace_date = ""+rs.getString("PERGANTIAN_TANGGAL_PEMBATALAN");
				String usrNpm = ""+rs.getString("NPM_OPERATOR");
				String superNpm = ""+rs.getString("NPM_SUPERVISOR");
				String thsms = ""+rs.getString("THSMS");
				
				li.add(cuid+"`"+tgl+"`"+batal+"`"+delay_time+"`"+real_start+"`"+replace_date+"`"+usrNpm+"`"+superNpm+"`"+thsms);
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
    
    public Vector mergeClassPollDenganKehadiranDosen(Vector vSdClassPoolGetListKelasYgBisaDiUpdateStatusKehadiranAtauNilainya) {
    	//format vKlsAjar lis.add(kdpst+"`"+nakmk+"`"+idkur+"`"+idkmk+"`"+shift+"`"+thsms+"`"+nopll+"`"+initNpmInp+"`"+lasNpmUpd+"`"+lasStatInf+"`"+curAvailStat+"`"+locked+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+batal+"`"+kodeKls+"`"+kodeRuang+"`"+kodeGdg+"`"+kodeKmp+"`"+tknDayTime+"`"+nmmDos+"`"+nmmAsdos+"`"+totEnrol+"`"+maxEnrol+"`"+minEnrol+"`"+subKeterKdkmk+"`"+initReqTime+"`"+tknNpmApr+"`"+tknAprTime+"`"+targetTtmhs+"`"+passed+"`"+rejected+"`"+kodeGabung+"`"+kodeGabungUniv+"`"+cuid);
    	Vector vf = null;
    	if(vSdClassPoolGetListKelasYgBisaDiUpdateStatusKehadiranAtauNilainya!=null && vSdClassPoolGetListKelasYgBisaDiUpdateStatusKehadiranAtauNilainya.size()>0) {
    		vf = new Vector();
    		//String thsms_now = Checker.getThsmsNow();
    		try {
    			ListIterator li = vSdClassPoolGetListKelasYgBisaDiUpdateStatusKehadiranAtauNilainya.listIterator();
    			ListIterator lif = vf.listIterator();
    			Vector vAbs = getStatusKehadiranDosenHariIniDanKedepan();
    			//li.add(cuid+"`"+tgl+"`"+batal+"`"+delay_time+"`"+real_start+"`"+replace_date+"`"+usrNpm+"`"+superNpm+"`"+thsms);
    			
    	    	if(vAbs==null || vAbs.size()<1) {
    	    		while(li.hasNext()) {
    	    			String brs = (String)li.next();
    	    			lif.add(brs);
    	    			lif.add("null");
    	    		}
    	    	}
    	    	else {
    	    		while(li.hasNext()) {
    	    			String brs = (String)li.next();
    	    			lif.add(brs);
    	    			StringTokenizer st = new StringTokenizer(brs,"`");
    	    			String kdpst = st.nextToken();
    	    			String nakmk = st.nextToken();
    	    			String idkur = st.nextToken();
    	    			String idkmk = st.nextToken();
    	    			String shift = st.nextToken();
    	    			String thsms = st.nextToken();
    	    			String nopll = st.nextToken();
    	    			String initNpmInp = st.nextToken();
    	    			String lasNpmUpd = st.nextToken();
    	    			String lasStatInf = st.nextToken();
    	    			String curAvailStat = st.nextToken();
    	    			String locked = st.nextToken();
    	    			String npmdos = st.nextToken();
    	    			String nodos = st.nextToken();
    	    			String npmasdos = st.nextToken();
    	    			String noasdos = st.nextToken();
    	    			String batal = st.nextToken();
    	    			String kodeKls = st.nextToken();
    	    			String kodeRuang = st.nextToken();
    	    			String kodeGdg = st.nextToken();
    	    			String kodeKmp = st.nextToken();
    	    			String tknDayTime = st.nextToken();
    	    			String nmmDos = st.nextToken();
    	    			String nmmAsdos = st.nextToken();
    	    			String totEnrol = st.nextToken();
    	    			String maxEnrol = st.nextToken();
    	    			String minEnrol = st.nextToken();
    	    			String subKeterKdkmk = st.nextToken();
    	    			String initReqTime = st.nextToken();
    	    			String tknNpmApr = st.nextToken();
    	    			String tknAprTime = st.nextToken();
    	    			String targetTtmhs = st.nextToken();
    	    			String passed = st.nextToken();
    	    			String rejected = st.nextToken();
    	    			String kodeGabung = st.nextToken();
    	    			String kodeGabungUniv = st.nextToken();
    	    			String cuid = st.nextToken();
    	    			boolean match = false;
    	    			
    	    			ListIterator liv = vAbs.listIterator();
    	    			while(liv.hasNext() && !match) {
    	    				String brs1 = (String)liv.next();
    	    				st = new StringTokenizer(brs1,"`");
    	    				String cuid1 = st.nextToken();
    	    				String tgl1 = st.nextToken();
    	    				String batal1 = st.nextToken();
    	    				String delay_time = st.nextToken();
    	    				String real_start = st.nextToken();
    	    				String replace_date = st.nextToken();
    	    				String usrNpm = st.nextToken();
    	    				String superNpm = st.nextToken();
    	    				String thsms1 = st.nextToken();
    	    				if(cuid.equalsIgnoreCase(cuid1)) {
    	    					match = true;
    	    					lif.add(brs1);
    	    				}
    	    			}
    	    			if(!match) {
    	    				lif.add("null");
    	    			}
    	    		}
    	    	}
    		}
    		catch(ConcurrentModificationException e) {
        		e.printStackTrace();
        	}
    		/*
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
    		*/
        	catch(Exception e) {
        		e.printStackTrace();
        	}
    		finally {
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
    		}
    	}
    	return vf;
    }
    
    public String getNotificationStatusKehadiranDosenMhsBanBerjalan() {
    	String thsms_now = Checker.getThsmsNow(); 
    	String list_cuid = "";
    	String info_kls = "";
    	java.sql.Date dt_now = AskSystem.getTodayDate();
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			
			//1. cek krs
			//stmt = con.prepareStatement("select * from STATUS_KEHADIRAN_DOSEN where THSMS="+thsms_now+" and TANGGAL<=?");
			//stmt = con.prepareStatement("select KDPSTTRNLM,KDKMKTRNLM,CLASS_POOL_UNIQUE_ID from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=?");
			stmt = con.prepareStatement("select KDPSTTRNLM,KDKMKTRNLM,CLASS_POOL_UNIQUE_ID from TRNLM inner join CLASS_POOL on CLASS_POOL_UNIQUE_ID=UNIQUE_ID where THSMS=? and NPMHSTRNLM=?");
			stmt.setString(1, thsms_now);
			stmt.setString(2, this.operatorNpm);
			rs = stmt.executeQuery();
			while(rs.next()) {
				String kdpst = ""+rs.getString(1);
				String kdkmk = ""+rs.getString(2);
				String cuid = ""+rs.getLong(3);
				
				list_cuid = list_cuid+cuid+"`"+kdkmk+"`"+kdpst+"`";
				System.out.println("list_cuid="+list_cuid);
			}
			// 2. add nama mk
			if(!Checker.isStringNullOrEmpty(list_cuid)) {
				stmt = con.prepareStatement("select * from MAKUL  where KDPSTMAKUL=? and KDKMKMAKUL=?");
				StringTokenizer st = new StringTokenizer(list_cuid,"`");
				list_cuid = new String("");
				while(st.hasMoreTokens()) {
					String cuid = st.nextToken();
					String kdkmk = st.nextToken();
					String kdpst = st.nextToken();
					stmt.setString(1, kdpst);
					stmt.setString(2, kdkmk);
					rs = stmt.executeQuery();
					rs.next();
					String nakmk = rs.getString("NAKMKMAKUL");
					list_cuid = list_cuid+cuid+"`"+kdkmk+"`"+kdpst+"`"+nakmk+"`";
				}
			}
			//3. add nama dosen
			if(!Checker.isStringNullOrEmpty(list_cuid)) {
				stmt = con.prepareStatement("select NMMDOS,NMMASDOS,NPMDOS,NPMASDOS from CLASS_POOL  where UNIQUE_ID=?");
				StringTokenizer st = new StringTokenizer(list_cuid,"`");
				list_cuid = new String("");
				while(st.hasMoreTokens()) {
					String cuid = st.nextToken();
					String kdkmk = st.nextToken();
					String kdpst = st.nextToken();
					String nakmk = st.nextToken();
					if(cuid==null || Checker.isStringNullOrEmpty(cuid)) {
						cuid = "-1";
					}
					stmt.setLong(1, Long.parseLong(cuid));
					rs = stmt.executeQuery();
					String nmmdos="null";
					String nmmasdos="null";
					String npmdos="null";
					String npmasdos="null";
					
					if(rs.next()) {
						nmmdos = ""+rs.getString("NMMDOS");
						nmmasdos = ""+rs.getString("NMMASDOS");
						npmdos = ""+rs.getString("NPMDOS");
						npmasdos = ""+rs.getString("NPMASDOS");
					}
					
					list_cuid = list_cuid+cuid+"`"+kdkmk+"`"+kdpst+"`"+nakmk+"`"+nmmdos+"`"+npmdos+"`"+nmmasdos+"`"+npmasdos+"`";
				}
			}
			//4. cek info di tabel kehadiran dosen
			info_kls = "";
			if(list_cuid!=null && !Checker.isStringNullOrEmpty(list_cuid)) {
				stmt = con.prepareStatement("select * from STATUS_KEHADIRAN_DOSEN where CLASS_UNIQUE_ID=? and THSMS=? and TANGGAL>=? order by TANGGAL");
				StringTokenizer st = new StringTokenizer(list_cuid,"`");
				while(st.hasMoreTokens()) {
					String cuid = st.nextToken();
					String kdkmk = st.nextToken();
					String kdpst = st.nextToken();
					String nakmk = st.nextToken();
					String nmmdos = st.nextToken();
					String npmdos = st.nextToken();
					String nmmasdos = st.nextToken();
					String npmasdos = st.nextToken();
					stmt.setLong(1,Long.parseLong(cuid));
					stmt.setString(2, thsms_now);
					stmt.setDate(3, dt_now);
					rs = stmt.executeQuery();
					if(rs.next()) {
						boolean batal = rs.getBoolean("BATAL");
						int delay_time = rs.getInt("PERKIRAAN_LAMA_KETERLAMBATAN");
						String tgl_tm = ""+rs.getDate("TANGGAL");
						
						info_kls = info_kls+cuid+"`"+kdkmk+"`"+kdpst+"`"+nakmk+"`"+batal+"`"+nmmdos+"`"+npmdos+"`"+nmmasdos+"`"+npmasdos+"`"+delay_time+"`"+tgl_tm+"`";
					}
					else {
						info_kls = info_kls+cuid+"`"+kdkmk+"`"+kdpst+"`"+nakmk+"`null`";
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
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	
    	return info_kls;
    }

    public String getNotificationStatusKehadiranDosen() {
    	String thsms_now = Checker.getThsmsNow(); 
    	String list_cuid = "";
    	String info_kls = "";
    	java.sql.Date dt_now = AskSystem.getTodayDate();
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			
			//1. cek krs
			//stmt = con.prepareStatement("select * from STATUS_KEHADIRAN_DOSEN where THSMS="+thsms_now+" and TANGGAL<=?");
			stmt = con.prepareStatement("select KDPSTTRNLM,KDKMKTRNLM,CLASS_POOL_UNIQUE_ID from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=?");
			stmt.setString(1, thsms_now);
			stmt.setString(2, this.operatorNpm);
			rs = stmt.executeQuery();
			while(rs.next()) {
				String kdpst = ""+rs.getString(1);
				String kdkmk = ""+rs.getString(2);
				String cuid = ""+rs.getLong(3);
				
				list_cuid = list_cuid+cuid+"`"+kdkmk+"`"+kdpst+"`";
				System.out.println("list_cuid="+list_cuid);
			}
			// 2. add nama mk
			if(!Checker.isStringNullOrEmpty(list_cuid)) {
				stmt = con.prepareStatement("select * from MAKUL  where KDPSTMAKUL=? and KDKMKMAKUL=?");
				StringTokenizer st = new StringTokenizer(list_cuid,"`");
				list_cuid = new String("");
				while(st.hasMoreTokens()) {
					String cuid = st.nextToken();
					String kdkmk = st.nextToken();
					String kdpst = st.nextToken();
					stmt.setString(1, kdpst);
					stmt.setString(2, kdkmk);
					rs = stmt.executeQuery();
					rs.next();
					String nakmk = rs.getString("NAKMKMAKUL");
					list_cuid = list_cuid+cuid+"`"+kdkmk+"`"+kdpst+"`"+nakmk+"`";
				}
			}
			//3. add nama dosen
			if(!Checker.isStringNullOrEmpty(list_cuid)) {
				stmt = con.prepareStatement("select NMMDOS,NMMASDOS,NPMDOS,NPMASDOS from CLASS_POOL  where UNIQUE_ID=?");
				StringTokenizer st = new StringTokenizer(list_cuid,"`");
				list_cuid = new String("");
				while(st.hasMoreTokens()) {
					String cuid = st.nextToken();
					String kdkmk = st.nextToken();
					String kdpst = st.nextToken();
					String nakmk = st.nextToken();
					if(cuid==null || Checker.isStringNullOrEmpty(cuid)) {
						cuid = "-1";
					}
					stmt.setLong(1, Long.parseLong(cuid));
					rs = stmt.executeQuery();
					String nmmdos="null";
					String nmmasdos="null";
					String npmdos="null";
					String npmasdos="null";
					
					if(rs.next()) {
						nmmdos = ""+rs.getString("NMMDOS");
						nmmasdos = ""+rs.getString("NMMASDOS");
						npmdos = ""+rs.getString("NPMDOS");
						npmasdos = ""+rs.getString("NPMASDOS");
					}
					
					list_cuid = list_cuid+cuid+"`"+kdkmk+"`"+kdpst+"`"+nakmk+"`"+nmmdos+"`"+npmdos+"`"+nmmasdos+"`"+npmasdos+"`";
				}
			}
			//4. cek info di tabel kehadiran dosen
			info_kls = "";
			if(list_cuid!=null && !Checker.isStringNullOrEmpty(list_cuid)) {
				stmt = con.prepareStatement("select * from STATUS_KEHADIRAN_DOSEN where CLASS_UNIQUE_ID=? and THSMS=? and TANGGAL>=? order by TANGGAL");
				StringTokenizer st = new StringTokenizer(list_cuid,"`");
				while(st.hasMoreTokens()) {
					String cuid = st.nextToken();
					String kdkmk = st.nextToken();
					String kdpst = st.nextToken();
					String nakmk = st.nextToken();
					String nmmdos = st.nextToken();
					String npmdos = st.nextToken();
					String nmmasdos = st.nextToken();
					String npmasdos = st.nextToken();
					stmt.setLong(1,Long.parseLong(cuid));
					stmt.setString(2, thsms_now);
					stmt.setDate(3, dt_now);
					rs = stmt.executeQuery();
					if(rs.next()) {
						boolean batal = rs.getBoolean("BATAL");
						int delay_time = rs.getInt("PERKIRAAN_LAMA_KETERLAMBATAN");
						String tgl_tm = ""+rs.getDate("TANGGAL");
						
						info_kls = info_kls+cuid+"`"+kdkmk+"`"+kdpst+"`"+nakmk+"`"+batal+"`"+nmmdos+"`"+npmdos+"`"+nmmasdos+"`"+npmasdos+"`"+delay_time+"`"+tgl_tm+"`";
					}
					else {
						info_kls = info_kls+cuid+"`"+kdkmk+"`"+kdpst+"`"+nakmk+"`null`";
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
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	
    	return info_kls;
    }

}
