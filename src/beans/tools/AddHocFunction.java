package beans.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 * Session Bean implementation class AddHocFunction
 */
@Stateless
@LocalBean
public class AddHocFunction {

    /**
     * Default constructor. 
     */
    public AddHocFunction() {
        // TODO Auto-generated constructor stub
    }
    
    public static Vector addInfoKdkmk(Vector v) {
    	
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Long uid = null;
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	if(v!=null && v.size()>0) {
    		ListIterator li = v.listIterator();
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			
    			stmt = con.prepareStatement("select KDKMKMAKUL from MAKUL where IDKMKMAKUL=?");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"||");
    				st.nextToken();//nakmk1
    				st.nextToken();//smsmk1
    				st.nextToken();//cmd1
    				st.nextToken();//idkur1
    				String idkmk = st.nextToken();//idkmk1
    				stmt.setLong(1,Long.parseLong(idkmk));
    				rs = stmt.executeQuery();
    				rs.next();
    				String kdkmk = rs.getString("KDKMKMAKUL");
    				li.set(brs+"||"+kdkmk);
    			}
    			
    			
        	}
        	catch(ConcurrentModificationException e) {
        		e.printStackTrace();
        	}
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
        	catch(Exception e) {
        		e.printStackTrace();
        	}
    		finally {
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
    		}
    	}
    	
    	return v;
    }
    
   public static void updateTabelDaftarUlang() {
    	
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Long uid = null;
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			
			stmt = con.prepareStatement("select * from DAFTAR_ULANG inner join CIVITAS on NPMHS=NPMHSMSMHS");
			rs = stmt.executeQuery();
			Vector v = new Vector();
			ListIterator li = v.listIterator();
			while(rs.next()) {
				String npmhs = rs.getString("NPMHS");
				String kdpst = rs.getString("KDPST");
				int idobj = rs.getInt("CIVITAS.ID_OBJ");
				li.add(kdpst+"`"+npmhs+"`"+idobj);
			}
			v = Tool.removeDuplicateFromVector(v);
			li = v.listIterator();
			String list_info="";
			while(li.hasNext()) {
				list_info = list_info+"`"+(String)li.next();
			}
			StringTokenizer st = new StringTokenizer(list_info,"`");
			v = new Vector();
			li = v.listIterator();
			while(st.hasMoreTokens()) {
				String kdpst = st.nextToken();
				String npmhs = st.nextToken();
				String idobj = st.nextToken();
				String pesan = Checker.sudahDaftarUlang(kdpst, npmhs);
				if(pesan==null) {
					li.add(kdpst+"`"+npmhs+"`"+idobj+"`true");
				}
				else {
					li.add(kdpst+"`"+npmhs+"`"+idobj+"`false");
				}
			}
			li = v.listIterator();
			stmt = con.prepareStatement("update DAFTAR_ULANG set ID_OBJ=?,ALL_APPROVED=? where NPMHS=?");
			while(li.hasNext()) {
				String brs = (String)li.next();
				st = new StringTokenizer(brs,"`");
				String kdpst = st.nextToken();
				String npmhs = st.nextToken();
				String idobj = st.nextToken();
				String approved = st.nextToken();
				stmt.setInt(1, Integer.parseInt(idobj));
				stmt.setBoolean(2, Boolean.parseBoolean(approved));
				stmt.setString(3, npmhs);
				stmt.executeUpdate();
			}
    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	
    	//return v;
    }
    
    
    public static Vector addInfoKelasEmpty(Vector vGab) {
    	
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Long uid = null;
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	if(vGab!=null && vGab.size()>0) {
    		ListIterator li = vGab.listIterator();
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			
    			stmt = con.prepareStatement("select NPMHSTRNLM from TRNLM where CLASS_POOL_UNIQUE_ID=? limit 1");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"$");
    				
    				String nakmk1 = st.nextToken();
    				String idkmk1 = st.nextToken();
    				String idkur1 = st.nextToken();
    				String kdkmk1 = st.nextToken();
    				String thsms1 = st.nextToken();
    				String kdpst1 = st.nextToken();
    				String shift1 = st.nextToken();
    				String norutKlsPll1 = st.nextToken();
    				String initNpmInput1 = st.nextToken();
    				String latestNpmUpdate1 = st.nextToken();
    				String latesStatusInfo1 = st.nextToken();
    				String currAvailStatus1 = st.nextToken();
    				String locked1 = st.nextToken();
    				String npmdos1 = st.nextToken();
    				String nodos1 = st.nextToken();
    				String npmasdos1 = st.nextToken();
    				String noasdos1 = st.nextToken();
    				String canceled1 = st.nextToken();
    				String kodeKelas1 = st.nextToken();
    				String kodeRuang1 = st.nextToken();
    				String kodeGedung1 = st.nextToken();
    				String kodeKampus1 = st.nextToken();
    				String tknHrTime1 = st.nextToken();
    				String nmdos1 = st.nextToken();
    				String nmasdos1 = st.nextToken();
    				String enrolled1 = st.nextToken();
    				String maxEnrolled1 = st.nextToken();
    				String minEnrolled1 = st.nextToken();
    				String subKeterKdkmk1 = st.nextToken();
    				String initReqTime1 = st.nextToken();
    				String tknNpmApr1 = st.nextToken();
    				String tknAprTime1 = st.nextToken();
    				String targetTtmhs1 = st.nextToken();
    				String passed1 = st.nextToken();
    				String rejected1 = st.nextToken();
    				String konsen1 = st.nextToken();
    				String nmpst1 = st.nextToken();
    				String kodeGabungan = st.nextToken();
    				String cuid1 = st.nextToken();
    				stmt.setLong(1, Long.parseLong(cuid1));
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					li.set(brs+"$false");
    				}
    				else {
    					li.set(brs+"$true");
    				}
    				
    			}
    			
    			
        	}
        	catch(ConcurrentModificationException e) {
        		e.printStackTrace();
        	}
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
        	catch(Exception e) {
        		e.printStackTrace();
        	}
    		finally {
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
    		}
    	}
    	
    	return vGab;
    }

    
    public static Vector modifHistKrsForMenuEdit(Vector vHistKrsKhs, String tkn_allow_shift_kelas) {
    	
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Long uid = null;
    	String thsms_now = Checker.getThsmsKrs();
    	//liTmp.set(nakmk1+"||"+smsmk1+"||"+cmd1+"||"+idkur1+"||"+idkmk1+"||"+thsms1+"||"+kdpst1+"||"+shift1+"||"+noKlsPll1+"||"+initNpmInput1+"||"+latestNpmUpdate1+"||"+latestStatusInfo1+"||"+locked1+"||"+npmdos1+"||"+nodos1+"||"+npmasdos1+"||"+noasdos1+"||"+cancel+"||"+kodeKelas1+"||"+kodeRuang1+"||"+kodeGedung1+"||"+kodeKampus1+"||"+tknHrTime1+"||"+nmmdos1+"||"+nmmasdos1+"||"+enrolled1+"||"+maxEnrol1+"||"+minEnrol1+"||"+subKeterMk1+"||"+initReqTime1+"||"+tknNpmApproval1+"||"+tknApprovalTime1+"||"+targetTotMhs1+"||"+passed1+"||"+rejected1+"||"+konsen1);
    	if(vHistKrsKhs!=null && vHistKrsKhs.size()>0) {
    		ListIterator li = vHistKrsKhs.listIterator();
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			
    			stmt = con.prepareStatement("select * from CLASS_POOL where THSMS=? and IDKMK=? and CANCELED=?");
    			while(li.hasNext()) {
    				//String brs = (String) li.next();
    				String thsms = (String) li.next();
					String kdkmk = (String) li.next();
					String nakmk = (String) li.next();
					String nlakh = (String) li.next();
					String bobot = (String) li.next();
					String sksmk = (String) li.next();
					String kelas = (String) li.next();
					String sksem = (String) li.next();
					String nlips = (String) li.next();
					String skstt = (String) li.next();
					String nlipk = (String) li.next();	
					String shift = (String) li.next();	
					String krsdown = (String) li.next();//tambahan baru
					String khsdown = (String) li.next();//tambahan baru
					String bakprove = (String) li.next();//tambahan baru
					String paprove = (String) li.next();//tambahan baru
					String note = (String) li.next();//tambahan baru
					String lock = (String) li.next();//tambahan baru
					String baukprove = (String) li.next();//tambahan baru
					//tambahan
					String idkmk = (String) li.next();
					String addReq = (String) li.next();
					String drpReq = (String) li.next();
					String npmPa = (String) li.next();
					String npmBak = (String) li.next();
					String npmBaa = (String) li.next();
					String npmBauk = (String) li.next();
					String baaProve = (String) li.next();
					String ktuProve = (String) li.next();
					String dknProve = (String) li.next();
					String npmKtu = (String) li.next();
					String npmDekan = (String) li.next();
					String lockMhs = (String) li.next();
					String kodeKampus = (String) li.next();
					String cuid = (String) li.next();
					
					stmt.setString(1, thsms_now);
					stmt.setLong(2, Long.parseLong(idkmk));
					stmt.setBoolean(3, false);
					rs = stmt.executeQuery();
					boolean alternative_cuid_avail = false;
					while(rs.next()) {
						String kdpst_ = ""+rs.getString("KDPST");
						String shift_ = ""+rs.getString("SHIFT");
						String nopll_ = ""+rs.getString("NORUT_KELAS_PARALEL");
						String npmdos_ = ""+rs.getString("NPMDOS");
						String nodos_ = ""+rs.getString("NODOS");
						String npmasdos_ = ""+rs.getString("NPMASDOS");
						String noasdos_ = ""+rs.getString("NOASDOS");
						String kmp_ = ""+rs.getString("KODE_KAMPUS");
						String nmmdos_ = ""+rs.getString("NMMDOS");
						String nmmasdos_ = ""+rs.getString("NMMASDOS");
						String sub_keter_kdkmk_ = ""+rs.getString("SUB_KETER_KDKMK");
						String cuid_ = ""+rs.getLong("UNIQUE_ID");
						if(tkn_allow_shift_kelas.contains(shift_)) {
							cuid = cuid+"`"+kdpst_+"`"+shift_+"`"+nopll_+"`"+npmdos_+"`"+nodos_+"`"+npmasdos_+"`"+noasdos_+"`"+kmp_+"`"+nmmdos_+"`"+nmmasdos_+"`"+sub_keter_kdkmk_+"`"+cuid_;	
							alternative_cuid_avail = true;
						}
						
					}
					if(alternative_cuid_avail) {
						li.set(cuid);	
					}
					
					String npmdos = (String) li.next();
					String nodos = (String) li.next();
					String npmasdos = (String) li.next();
					String noasdos = (String) li.next();
					String nmmdos = (String) li.next();
					String nmmasdos = (String) li.next(); 
					    				//System.out.println("-- "+cuid);
    			}
        	}
        	catch(ConcurrentModificationException e) {
        		e.printStackTrace();
        	}
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
        	catch(Exception e) {
        		e.printStackTrace();
        	}
    		finally {
    			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    			if (con!=null) try { con.close();} catch (Exception ignore){}
    		}
    	}
    	
    	return vHistKrsKhs;
    }
    
    public static void getListObjIdYgBlumAdaDiPilihanCakupanKelas(String target_thsms) {
    	String []tipe=null;
    	Vector v = new Vector();
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//get list obj mhs
    		stmt = con.prepareStatement("SELECT * from OBJECT where OBJ_NAME=?");
    		stmt.setString(1,"MHS");
    		rs = stmt.executeQuery();
    		ListIterator li = v.listIterator();
    		while(rs.next()) {
    			String id_obj = ""+rs.getInt("ID_OBJ");
    			String kdpst = ""+rs.getString("KDPST");
    			String kd_kmp = ""+rs.getString("KODE_KAMPUS_DOMISILI");
    			li.add(id_obj+"`"+kdpst+"`"+kd_kmp);
    		}
    		
    		stmt = con.prepareStatement("select * from PILIH_KELAS_RULES where THSMS=? and ID_OBJ_MHS=?");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs  = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String id_obj = st.nextToken();
    			String kdpst = st.nextToken();
    			String kd_kmp = st.nextToken();
    			stmt.setString(1, target_thsms);
    			stmt.setLong(2, Long.parseLong(id_obj));
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				li.remove();
    			}
    		}
    		
    		stmt = con.prepareStatement("insert into PILIH_KELAS_RULES (THSMS,KDPST,ID_OBJ_MHS,KODE_KAMPUS)values(?,?,?,?)");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			while(li.hasNext()) {
        			String brs  = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String id_obj = st.nextToken();
        			String kdpst = st.nextToken();
        			String kd_kmp = st.nextToken();
        			stmt.setString(1, target_thsms);
        			stmt.setString(2, kdpst);
        			stmt.setLong(3, Long.parseLong(id_obj));
        			stmt.setString(4, kd_kmp);
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
    	//return tipe;
    }
    
    
    public static String getAndSyncStmhsBetweenTrlsmAndMsmhs(String kdpst,String npmhs) {
    	/*
    	 * STATUS STMHSMSMHS ADALAH KUNCI UNTUK MENENTUKAN SCKOPE AKSES, SEPERTI INS KRS,DSB
    	 */
    	String status_akhir = null;
    	String thsms_now = Checker.getThsmsNow();
    	//String thsms_krs = Checker.getThsmsKrs();
    	String psn = Checker.sudahDaftarUlang(kdpst, npmhs);//menggunakan thsms_heregistrasi
    	boolean sdu = false;
    	if(psn==null) {
    		sdu = true;
    	}
    	String thsms_her = Checker.getThsmsHeregistrasi();
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//get curr stmhsmsmhs from civitas table
    		stmt = con.prepareStatement("SELECT STMHSMSMHS from CIVITAS where NPMHSMSMHS=?");
    		stmt.setString(1,npmhs);
    		rs = stmt.executeQuery();
    		rs.next();
    		String stmhsmsmhs = ""+rs.getString("STMHSMSMHS");
    		
    		
    		String latest_thsmstrlsm = null;
    		String latest_stmhstrlsm = null;
    		
    		
    		//cek status terakhir di trlsm
    		stmt = con.prepareStatement("select * from TRLSM where NPMHS=? order by THSMS desc limit 1");
    		stmt.setString(1, npmhs);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			latest_thsmstrlsm = new String(""+rs.getString("THSMS"));
    			latest_stmhstrlsm = new String(""+rs.getString("STMHS"));
    		}
    		//}
    		//cek status terakhir aktif dari trnlm tidak dari heregistrasi
    		String latest_thsmstrnlm = null;
    		stmt = con.prepareStatement("select THSMSTRNLM from TRNLM where NPMHSTRNLM=? order by THSMSTRNLM desc limit 1");
    		stmt.setString(1, npmhs);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			latest_thsmstrnlm = new String(""+rs.getString("THSMSTRNLM"));
    		}
    	
    		
    		//if(stmhsmsmhs.equalsIgnoreCase("null")) {
    	
    		if(true) {
    			//System.out.println("1");
    			//belum ada status di CIVITAS
    			//1. cek latest trlsm apa pernah keluar
    			if(latest_stmhstrlsm==null) {
    				//System.out.println("2");
    				//belum ada record di trlsm
    				if(latest_thsmstrnlm==null) {
    					//System.out.println("3");
    					//juga tidak ada record krs
    					if(sdu) {
    						//System.out.println("4");
    						//tapi sudah daftar ulang - jadi set aktif
    						//set status A
        					stmt = con.prepareStatement("update CIVITAS set STMHSMSMHS=? where NPMHSMSMHS=?");
        					stmt.setString(1, "A");
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        					status_akhir = new String("A");
    					}
    					else {
    						//set status N
    						//System.out.println("5");
        					stmt = con.prepareStatement("update CIVITAS set STMHSMSMHS=? where NPMHSMSMHS=?");
        					stmt.setString(1, "N");
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        					status_akhir = new String("N");
    					}
    					
    				}
    				else {
    					//ada record krs
    					//System.out.println("6");
    					if(latest_thsmstrnlm.compareToIgnoreCase(thsms_now)>=1 || sdu) {
    						//System.out.println("7");
    						stmt = con.prepareStatement("update CIVITAS set STMHSMSMHS=? where NPMHSMSMHS=?");
        					stmt.setString(1, "A");
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        					status_akhir = new String("A");
    					}
    					else {
    						//System.out.println("8");
    						//krs terakhir < thsms now
    						//set status N
        					stmt = con.prepareStatement("update CIVITAS set STMHSMSMHS=? where NPMHSMSMHS=?");
        					stmt.setString(1, "N");
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        					status_akhir = new String("N");
    					}
    				}
    			}
    			else {
    				//ada reccord di trlsm
    				//System.out.println("9");
    				if(latest_stmhstrlsm.equalsIgnoreCase("K")||latest_stmhstrlsm.equalsIgnoreCase("D")) {
    					//System.out.println("10");
    					//status terakhir keluar
    					//update status CIVITAS
    					stmt = con.prepareStatement("update CIVITAS set STMHSMSMHS=? where NPMHSMSMHS=?");
    					stmt.setString(1, latest_stmhstrlsm);
    					stmt.setString(2, npmhs);
    					stmt.executeUpdate();
    					//tinggal mikir return valuenya - looking fo errrors
    					if(latest_thsmstrnlm==null) {
    						//System.out.println("11");
    						//ngga ada record krs -,no error
    						status_akhir = new String(latest_stmhstrlsm);
    					}
    					else {
    						//System.out.println("12");
    						//ada krs
    						if(latest_thsmstrlsm.equalsIgnoreCase("0")) {
    							//System.out.println("13");
    							//ngga usah di compare dengan thsmstrnlm krn ini hasil migrasi epsebd
    							status_akhir = new String(latest_stmhstrlsm);
    						}
    						else {
    							//System.out.println("14");
    							//cek thsms krs terakhir >= trslm keluar = kalo keluar mgga boleh ada krs pada sms tersebut 
        						if(latest_thsmstrnlm.compareToIgnoreCase(latest_thsmstrlsm)>=0) {
        							//System.out.println("15");
        							status_akhir = new String("E`"+latest_stmhstrlsm+"`Ada krs at THSMS > THSMS keluar");
        						}
        						else if(sdu) {
        							//System.out.println("16");
        							status_akhir = new String("E`"+latest_stmhstrlsm+"`Status keluar tapi sudah daftar ulang");
        						}
        						else if(latest_thsmstrnlm.compareToIgnoreCase(thsms_now)>=0) {
        							//System.out.println("17");
        							status_akhir = new String("E`"+latest_stmhstrlsm+"`Status keluar tapi ada krs at THSMS NOW");
        						}
        						else {
        							//no error
        							//System.out.println("18");
        							status_akhir = new String(latest_stmhstrlsm);
        						}
    						}
    					}
    				}
    				else if(latest_stmhstrlsm.equalsIgnoreCase("L")) {
    					//System.out.println("19");
    					stmt = con.prepareStatement("update CIVITAS set STMHSMSMHS=? where NPMHSMSMHS=?");
    					stmt.setString(1, latest_stmhstrlsm);
    					stmt.setString(2, npmhs);
    					stmt.executeUpdate();
    					
    					//tinggal mikir return valuenya - looking fo errrors
    					if(latest_thsmstrnlm==null) {
    						//System.out.println("20");
    						//ngga ada record krs -,no error
    						status_akhir = new String(latest_stmhstrlsm);
    					}
    					else {
    						//ada krs
    						//System.out.println("21");
    						if(latest_thsmstrlsm.equalsIgnoreCase("0")) {
    							//System.out.println("22");
    							//ngga usah di compare dengan thsmstrnlm krn ini hasil migrasi epsebd
    							status_akhir = new String(latest_stmhstrlsm);
    						}
    						else {
    							//System.out.println("23");
    							//cek thsms krs terakhir >= trslm keluar = kalo keluar mgga boleh ada krs pada sms tersebut 
        						if(latest_thsmstrnlm.compareToIgnoreCase(latest_thsmstrlsm)>0) {
        							//System.out.println("24");
        							status_akhir = new String("E`"+latest_stmhstrlsm+"`Ada krs at THSMS > THSMS lulus");
        						}
        						else if(latest_thsmstrnlm.compareToIgnoreCase(thsms_now)>0) {
        							//System.out.println("25");
        							status_akhir = new String("E`"+latest_stmhstrlsm+"`Status lulus tapi ada krs at THSMS LULUS");
        						}
        						else if(sdu && thsms_her.compareToIgnoreCase(latest_thsmstrlsm)>0) {
        							//System.out.println("26");
        							status_akhir = new String("E`"+latest_stmhstrlsm+"`Status lulus tapi sudah daftar ulang untuk sms depan");
        						}
        						else {
        							//System.out.println("27");
        							//no error
        							status_akhir = new String(latest_stmhstrlsm);
        						}
    						}
    						
    					}
    				}
    				else {
    					//non aktif / CUTI
    					//System.out.println("28");
    					if(sdu || latest_thsmstrnlm.compareToIgnoreCase(thsms_now)>=0) {
    						//System.out.println("29");
    						stmt = con.prepareStatement("update CIVITAS set STMHSMSMHS=? where NPMHSMSMHS=?");
        					stmt.setString(1, "A");
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        					status_akhir = new String("A");
    					}
    					else if(latest_thsmstrlsm.compareToIgnoreCase(thsms_now)>=0) {
    						//System.out.println("30");
    						stmt = con.prepareStatement("update CIVITAS set STMHSMSMHS=? where NPMHSMSMHS=?");
        					stmt.setString(1, latest_stmhstrlsm);
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        					status_akhir = new String(latest_stmhstrlsm);
    					}
    					else {
    						//System.out.println("31");
    						//bukan thsms saat ini jadi non aktif
							stmt = con.prepareStatement("update CIVITAS set STMHSMSMHS=? where NPMHSMSMHS=?");
        					stmt.setString(1, "N");
        					stmt.setString(2, npmhs);
        					stmt.executeUpdate();
        					status_akhir = new String("N");
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
        finally {
        	if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
        }	
    	return status_akhir;
    }
    
    public static boolean isAllApproved(long topik_id, String tipe_pengajuan) {
    	boolean all_approved = false;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where ID=?");
    		stmt.setLong(1, topik_id);
    		rs = stmt.executeQuery();
    		rs.next();
    		String tkn_target_approvee_id = ""+rs.getString("TOKEN_TARGET_OBJID");
    		String tkn_approvee_id_yg_sdh_approved = ""+rs.getString("APPROVED");
    		all_approved= isAllApproved( tkn_target_approvee_id, tkn_approvee_id_yg_sdh_approved);
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
    	return all_approved;
    }
    
    public static boolean isAllApproved(String tkn_requird_approvee_id, String tkn_approvee_id_at_topik_pengajuan) {
    	//String tkn_requird_approvee_id = new String(tkn_target_objid_topik);
    	//boolean all_approved = true;
		tkn_requird_approvee_id = tkn_requird_approvee_id.replace("]", "`");
		tkn_requird_approvee_id = tkn_requird_approvee_id.replace("[", "");
		//System.out.println("tkn_target_objid_topik="+tkn_requird_approvee_id);
		StringTokenizer st = new StringTokenizer(tkn_requird_approvee_id,"`");
		boolean all_approved = true;
		//System.out.println("approved_topik="+approved_topik);
		if(st.hasMoreTokens()) {
			while(st.hasMoreTokens() && all_approved) {
				String required_id_approvee = st.nextToken();
				//System.out.println("required_id_approvee="+required_id_approvee);
				if(!tkn_approvee_id_at_topik_pengajuan.contains("["+required_id_approvee+"]")) {
					all_approved = false;
				}
			}
		}
		else {
			all_approved = false;
		}
		return all_approved;
    }
    
    public static String siapaIni(String tkn_target_objnickname, String tkn_target_objid, String target_objid, String remove_word) {
    	tkn_target_objnickname = tkn_target_objnickname.replace("[", "");
    	tkn_target_objnickname = tkn_target_objnickname.replace("]", "`");
    	
    	tkn_target_objid = tkn_target_objid.replace("[", "");
    	tkn_target_objid = tkn_target_objid.replace("]", "`");
    	//System.out.println("adhoc tkn_target_objid="+tkn_target_objid);
    	//System.out.println("adhoc tkn_target_objnickname="+tkn_target_objnickname);
    	//System.out.println("adhoc target_objid="+target_objid);
    	StringTokenizer st1 = new StringTokenizer(tkn_target_objid,"`");
    	StringTokenizer st2 = new StringTokenizer(tkn_target_objnickname,"`");
    	String tkn_nick = null;
    	boolean match = false;
    	while(st1.hasMoreTokens() && !match) {
    		String tkn_id = st1.nextToken();
    		tkn_nick = new String(st2.nextToken());
    		if(remove_word!=null) {
    			tkn_nick = tkn_nick.replace(remove_word, "");	
    		}
    		
    		if(tkn_id.equalsIgnoreCase(target_objid)) {
    			match = true;
    		}
    	}
    	
    	return tkn_nick;
    }
}
