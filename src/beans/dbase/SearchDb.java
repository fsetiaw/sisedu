package beans.dbase;

import java.util.Comparator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.util.Collections;

import org.apache.tomcat.jdbc.pool.*;

import beans.login.InitSessionUsr;
import beans.sistem.AskSystem;
import beans.setting.*;
import beans.tools.*;

import java.util.LinkedHashSet;
import java.util.Collections;
/**
 * Session Bean implementation class SearchDb
 */
@Stateless
@LocalBean
public class SearchDb {
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
    public SearchDb() {
        // TODO Auto-generated constructor stub
    }

    public SearchDb(String operatorNpm) {
        // TODO Auto-generated constructor stub
    	this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
    }
    
    public SearchDb(Connection con) {
    	this.con = con;
    }
    
    public Connection getCon() {
    	return con;
    }
    
    public String getInfoLoginHistory(String kdpst, String npmhs) {
    	int ttlog=0,tmlog=0;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		/*
    		 * get total login
    		 */
    		stmt = con.prepareStatement("select * from EXT_CIVITAS where KDPSTMSMHS=? and NPMHSMSMHS=?");
    		stmt.setString(1,kdpst);
    		stmt.setString(2, npmhs);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			ttlog = rs.getInt("TTLOGMSMHS");
    			tmlog = rs.getInt("TMLOGMSMHS");
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
    	return ttlog+","+tmlog;
    }
    
    public String getListTipeUjian(String thsms_aktif, Vector vScopeHasKartuUjianMenu) {
    	ListIterator li = null;
    	String listUjian = "";
    	if(vScopeHasKartuUjianMenu!=null && vScopeHasKartuUjianMenu.size()>0) {
    		try {
    			li = vScopeHasKartuUjianMenu.listIterator();
    			String sql = "select distinct TIPE_UJIAN from KARTU_UJIAN_RULES where THSMS='"+thsms_aktif+"' and (";
    			while(li.hasNext()) {
    				String prodi = (String)li.next();
    				sql = sql+" KDPST='"+prodi+"'";
    				if(li.hasNext()) {
    					sql = sql+" or";
    				}
       			}
    			sql = sql+")";
    			//System.out.println("sql=>"+sql);
    			
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		/*
        		 * get total login
        		 */
        		stmt = con.prepareStatement(sql);
        		rs = stmt.executeQuery();
        		
        		while(rs.next()) {
        			String tipeUjian = ""+rs.getString("TIPE_UJIAN");
        			listUjian=listUjian+tipeUjian+"#";
        		}
        		listUjian = listUjian.replace("null", "");
        		//System.out.println("listUjian=>"+listUjian);
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
    	return listUjian;	
    	//return ttlog+","+tmlog;
    }
    
   
    /*
     * ada versi prev aktif dimana sms antara diperhitungkan
     * getNpmhsMhsAktifPrevSms(String kdpst, String thsms)
     */
    public Vector getNpmhsMhsAktifGiven(String kdpst, String thsms) {
    	//return npmhs,smawl
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		/*
    		 * get total login
    		 */
    		stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM where THSMSTRNLM=? and KDPSTTRNLM=?");
    		stmt.setString(1, thsms);
    		stmt.setString(2, kdpst);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String npmhs = rs.getString("NPMHSTRNLM");
    			li.add(npmhs);
    		}
    		//get smawl
    		stmt = con.prepareStatement("SELECT SMAWLMSMHS FROM CIVITAS WHERE NPMHSMSMHS=?");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String npmhs = (String)li.next();
    			stmt.setString(1, npmhs);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				String smawl = ""+rs.getString("SMAWLMSMHS");
    				if(Checker.isStringNullOrEmpty(smawl)) {
    					smawl = "null";
    				}
    				String shift = ""+rs.getString("SHIFTMSMHS");
    				if(Checker.isStringNullOrEmpty(shift)) {
    					smawl = "null";
    				}
    				li.set(npmhs+","+smawl);
    			}
    			else {
    				li.set(npmhs+",null");
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
    	return v;
    }
    
    public boolean cekApaUsrPetugas() {
    	/*
    	 * if usrId < 0; berarti unverified usr jharusnya cuma pada saat login
    	 */
    	boolean petugas = false;
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT PETUGAS from EXT_CIVITAS where NPMHSMSMHS=?");
    		stmt.setString(1, this.operatorNpm);
    		//System.out.println("this.operatorNpm="+this.operatorNpm);
    		rs = stmt.executeQuery();
    		rs.next();
    		petugas = rs.getBoolean("PETUGAS");
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
    	return petugas;
    }	    
    
    public String getTknOprNickname() {
    	/*
    	 * if usrId < 0; berarti unverified usr jharusnya cuma pada saat login
    	 */
    	String nickname = null;
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT * from OBJECT inner join CIVITAS on OBJECT.ID_OBJ=CIVITAS.ID_OBJ where NPMHSMSMHS=?");
    		stmt.setString(1, this.operatorNpm);
    		//System.out.println("thiss.operatorNpm="+this.operatorNpm);
    		rs = stmt.executeQuery();
    		rs.next();
    		nickname = rs.getString("OBJ_NICKNAME");
    		//System.out.println("thiss.nickname="+nickname);
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
    	return nickname;
    }    
    
    public String getNmmOperator() {
    	/*
    	 * if usrId < 0; berarti unverified usr jharusnya cuma pada saat login
    	 */
    	String nmmhs = null;
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT NMMHSMSMHS from CIVITAS where NPMHSMSMHS=?");
    		stmt.setString(1, this.operatorNpm);
    		//System.out.println("this.operatorNpm="+this.operatorNpm);
    		rs = stmt.executeQuery();
    		rs.next();
    		nmmhs = rs.getString("NMMHSMSMHS");
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
    	return nmmhs;
    }	

    public Vector getInfoNpmhsMhsAktifPrevSms(String kdpst, String thisIsPrevThsms) {
    	//return npmhs,smawl
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		/*
    		 * get total login
    		 */
    		if(thisIsPrevThsms.length()==5) {
    			stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM where THSMSTRNLM=? and KDPSTTRNLM=?");
        		stmt.setString(1, thisIsPrevThsms);
        		stmt.setString(2, kdpst);
    		}
    		else {
        		if(thisIsPrevThsms.length()==6) {//posisi di smsm semnetara
        			String thsmsReg = thisIsPrevThsms.substring(0,5);
        			stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM where (THSMSTRNLM=? or THSMSTRNLM=?) and KDPSTTRNLM=?");
            		stmt.setString(1, thisIsPrevThsms);
            		stmt.setString(2, thsmsReg);
            		stmt.setString(3, kdpst);
        		}
    		}
    		
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String npmhs = rs.getString("NPMHSTRNLM");
    			li.add(npmhs);
    		}
    		//get smawl
    		stmt = con.prepareStatement("SELECT SMAWLMSMHS,SHIFTMSMHS,KRKLMMSMHS FROM CIVITAS inner join EXT_CIVITAS on CIVITAS.NPMHSMSMHS=EXT_CIVITAS.NPMHSMSMHS WHERE CIVITAS.NPMHSMSMHS=?");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String npmhs = (String)li.next();
    			stmt.setString(1, npmhs);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				String smawl = rs.getString("SMAWLMSMHS");
    				if(Checker.isStringNullOrEmpty(smawl)) {
    					smawl = "null";
    				}
    				String shift = rs.getString("SHIFTMSMHS");
    				if(Checker.isStringNullOrEmpty(shift)) {
    					shift = "null";
    				}
    				String krklm = ""+rs.getString("KRKLMMSMHS");
    				if(Checker.isStringNullOrEmpty(krklm)) {
    					krklm = "null";
    				}
    				li.set(npmhs+","+smawl+","+shift+","+krklm);
    			}
    			else {
    				li.set(npmhs+",null,null,null");
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
    	return v;
    }

    
    public Vector getListKdpst() {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		/*
    		 * get total login
    		 */
    		stmt = con.prepareStatement("select * from MSPST where KDJENMSPST=? or KDJENMSPST=? or KDJENMSPST=? or KDJENMSPST=? or KDJENMSPST=?");
    		stmt.setString(1, "A");
    		stmt.setString(2, "B");
    		stmt.setString(3, "C");
    		stmt.setString(4, "D");
    		stmt.setString(5, "E");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String kdpst = rs.getString("KDPSTMSPST");
    			li.add(kdpst);
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
    
    public Vector getPerkiraanJumlahMhsPerMakul(String kdpst, String thsms,Vector vNimhs) {
    	/*
    	 * fungsi ini mengembalikan makul beserta jumlah mhs yg belum ambil makulnya berdasarkan list mhs
    	 * yg ada di given vector
    	 * output struvtur vector
    	 * baris pertama 9 tkn
    	 * baris kedua = vector mhs //v sisze = 1 = tkn npmhs
    	 * baris ketiga = 	Vector kur StringTokenizer # betwenn kur, "," clom within kur //v sisze = 1 = tkn kur
    	 * NAKMK ngga boleh pake koma !!!!!!
    	 */
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	Vector v1 = new Vector();
    	ListIterator li1 = v1.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		//get kelas aktif untuk given kdpst
    		stmt = con.prepareStatement("select * from MAKUL where KDPSTMAKUL=? and STKMKMAKUL=?");
    		stmt.setString(1,kdpst);
    		stmt.setString(2,"A");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			int idkmk = rs.getInt("IDKMKMAKUL");
    			String kdkmk = rs.getString("KDKMKMAKUL");
    			String nakmk = rs.getString("NAKMKMAKUL");
    			nakmk = nakmk.replace(",", "-");
    			int skstm = rs.getInt("SKSTMMAKUL");
    			int skspr = rs.getInt("SKSPRMAKUL");
    			int skslp = rs.getInt("SKSLPMAKUL");
    			String kdwpl = rs.getString("KDWPLMAKUL");
    			String jenis = rs.getString("JENISMAKUL");
    			li.add(idkmk+","+kdkmk+","+nakmk+","+skstm+","+skspr+","+skslp+","+kdwpl+","+jenis);
    		}
    		
    		
    		/*
    		 * get info kurikulum
    		 * filter hanya matakuliah yg terdaftar di kurikulum aktif maupun non aktif
    		 */
    		stmt = con.prepareStatement("SELECT * FROM MAKUR INNER JOIN KRKLM on IDKURMAKUR=IDKURKRKLM where IDKMKMAKUR=?");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String baris = (String)li.next();
    			StringTokenizer st = new StringTokenizer(baris,",");
    			String idkmk = st.nextToken();
    			String kdkmk = st.nextToken();
    			String nakmk = st.nextToken();
    			String skstm = st.nextToken();
    			String skspr = st.nextToken();
    			String skslp = st.nextToken();
    			String kdwpl = st.nextToken();
    			String jenis = st.nextToken();
    			stmt.setInt(1, Integer.valueOf(idkmk).intValue());
    			rs = stmt.executeQuery();
    			Vector vTmp = new Vector();
    			ListIterator liTmp = vTmp.listIterator();
    			String tknKur = "";
    			boolean adaKrklmnya = false;
    			if(rs.next()) {
    				adaKrklmnya = true;
    				String idkur = ""+rs.getInt("IDKURMAKUR");
    				String semes = rs.getString("SEMESMAKUR");
    				String nmkur = ""+rs.getString("NMKURKRKLM");
    				String targetAng = ""+rs.getString("TARGTKRKLM");
    				tknKur = tknKur + idkur+"%"+nmkur+"%"+semes+"%"+targetAng+"#";
    				while(rs.next()) {
    					idkur = ""+rs.getInt("IDKURMAKUR");
    					semes = rs.getString("SEMESMAKUR");
        				nmkur = ""+rs.getString("NMKURKRKLM");
        				targetAng = ""+rs.getString("TARGTKRKLM");
        				tknKur = tknKur + idkur+"%"+nmkur+"%"+semes+"%"+targetAng+"#";
    				}
    			}
    			else {
    				/*
    				 * ignore empty tknKur
    				 */
    				//li1.add("kosong");
    			}
    			//kalo makul tdk ada dikurukiulum mana2 tidak diinput
    			if(adaKrklmnya) {
    				li1.add(baris);
    				liTmp.add(tknKur);
    				li1.add(vTmp);
    			}	
    		}
    		
    		
    		
    		
    		//get mhs per makul
    		String idkmk = "", idkur="",kdwpl = "";
			String kdkmk = "", nmkur="",jenis = "";
			String nakmk = "", semes="";
			String skstp = "",skspr = "",skslp = "";
			
			
			/*
			 * jangan lupa !!!
    		 * filter mhs yg aktif dan belum ngambil makul ini = memang sesuai dgn kurikulum yg harus diambil
    		 */
			stmt = con.prepareStatement("select * from TRNLM where KDPSTTRNLM=? and NPMHSTRNLM=? and KDKMKTRNLM=? and BOBOTTRNLM > 0");
    		v = new Vector();
    		li = v.listIterator();
    		li1 = v1.listIterator();
    		while(li1.hasNext()) {
    			String baris9token = (String)li1.next();
    			//System.out.println("9 token = "+baris9token);
    			Vector vTknKur = (Vector)li1.next();
    			
      			//get targetAngkatan dari vTknKur
    			Vector vSmawl = new Vector();
    			ListIterator liSml = vSmawl.listIterator();
    			if(vTknKur!=null && vTknKur.size()>0)  {
    				ListIterator liTkn = vTknKur.listIterator();
    				String baris = (String)liTkn.next();
    				StringTokenizer stt = new StringTokenizer(baris,"#");
    				while(stt.hasMoreTokens()) {
    					String tknInfoKur = stt.nextToken();
    					StringTokenizer st1 = new StringTokenizer(tknInfoKur,"%");
    					String tkn_idkur = st1.nextToken();
    					String tkn_semes = st1.nextToken();
        				String tkn_nmkur = st1.nextToken();
        				String tkn_targetAng = st1.nextToken();
        				StringTokenizer st3 = new StringTokenizer(tkn_targetAng,",");
        				while(st3.hasMoreTokens()) {
        					String smawlkur = st3.nextToken();
        					liSml.add(smawlkur);
        					//System.out.println(smawlkur);
        				}			
    				}
    			}
    			
    			StringTokenizer st = new StringTokenizer(baris9token,",");
   				idkmk = st.nextToken();
   				kdkmk = st.nextToken();
   				nakmk = st.nextToken();
   				skstp = st.nextToken();
   				skspr = st.nextToken();
    			skslp = st.nextToken();
    			kdwpl = st.nextToken();
    			jenis = st.nextToken();
    			ListIterator liMhs = vNimhs.listIterator();
    			Vector vNpm = new Vector();
    			ListIterator liNpm = vNpm.listIterator();
    			//String tknNpm = "";
    			int totMhs=0;
    			while(liMhs.hasNext()) {
    				String infoMhs = (String)liMhs.next();
    				StringTokenizer stt = new StringTokenizer(infoMhs,",");
    				String npmhs = stt.nextToken();
    				String smawl = stt.nextToken();
    				boolean match = false;
    				liSml = vSmawl.listIterator();
    				while(liSml.hasNext() && !match) {
    					String smawlKur = (String)liSml.next();
    					if(smawl.equalsIgnoreCase(smawlKur)) {
    						match = true;
    						stmt.setString(1,kdpst);
    	    				stmt.setString(2, npmhs);
    	    				stmt.setString(3, kdkmk);
    	    				rs = stmt.executeQuery();
    	    				if(!rs.next()) {
    	    					liNpm.add(npmhs);
    	    					//tknNpm = tknNpm+","+npmhs;
    	    					totMhs++;
    	    				}
    					}
    					else {
    						/*
    						 * ignore bkn kurikulumnya
    						 */
    					}
    				}
    			}
    			li.add(baris9token+","+totMhs+","+kdpst);
    			li.add(vNpm);
    			li.add(vTknKur);
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
    
    public Vector getListTargetKurikulum(String idkur) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	String target_ang = "",target_mhs="",target = "";
    	try {
    		if(con==null || con.isClosed()) {
    		
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    		/*
    		 * get list krkklm aktif
    		 */
    			stmt = con.prepareStatement("select TARGTKRKLM from KRKLM where IDKURKRKLM=?");
    			stmt.setInt(1,Integer.valueOf(idkur).intValue());
    			rs = stmt.executeQuery();
    			String tmp = null;
    			if(rs.next()) {
    				tmp = rs.getString("TARGTKRKLM");
    			}
    			if(tmp!=null && !tmp.equalsIgnoreCase("null")) {
    				StringTokenizer st = new StringTokenizer(tmp,",");
    				while(st.hasMoreTokens()) {
    					String temp = st.nextToken();
    					if(temp.length()==5) { // 5 = thsms length
    						if(target_ang.length()>0) {
    							target_ang=target_ang+",";
    						}
    						target_ang = target_ang+temp;
    					}
    					else {
    						if(target_mhs.length()>0) {
    							target_mhs=target_mhs+",";
    						}
    						target_mhs = target_mhs+temp;
    					}	
    				}
    			}
    		}
    		else {
    		/*
    		 * get list krkklm aktif
    		 */
    			stmt = con.prepareStatement("select TARGTKRKLM from KRKLM where IDKURKRKLM=?");
    			stmt.setInt(1,Integer.valueOf(idkur).intValue());
    			rs = stmt.executeQuery();
    			String tmp = null;
    			if(rs.next()) {
    				tmp = rs.getString("TARGTKRKLM");
    			}
    			if(tmp!=null && !tmp.equalsIgnoreCase("null")) {
    				StringTokenizer st = new StringTokenizer(tmp,",");
    				while(st.hasMoreTokens()) {
    					String temp = st.nextToken();
    					if(temp.length()==5) { // 5 = thsms length
    						if(target_ang.length()>0) {
    							target_ang=target_ang+",";
    						}
    						target_ang = target_ang+temp;
    					}
    					else {
    						if(target_mhs.length()>0) {
    							target_mhs=target_mhs+",";
    						}
    						target_mhs = target_mhs+temp;
    					}	
    				}
    			}
    		
    		}
    		li.add(""+target_ang);
    		li.add(""+target_mhs);
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
    
    
    public String getDistinctAngkatan(String kdpst) {
    	String list_angkatan ="";
    	try {
    		if(con==null || con.isClosed()) {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    		/*
    		 * get list krkklm aktif
    		 */
    			stmt = con.prepareStatement("select distinct SMAWLMSMHS from CIVITAS where KDPSTMSMHS=? order by SMAWLMSMHS desc");
    			stmt.setString(1,kdpst);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				list_angkatan = list_angkatan+","+rs.getString("SMAWLMSMHS");
    			}	
    		}
    		else {
    		/*
    		 * get list krkklm aktif
    		 */
    			stmt = con.prepareStatement("select distinct SMAWLMSMHS from CIVITAS where KDPSTMSMHS=? order by SMAWLMSMHS desc");
    			stmt.setString(1,kdpst);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				list_angkatan = list_angkatan+","+rs.getString("SMAWLMSMHS");
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
    				//System.out.println(ignore);
    			}
    		}
    	}
    	return list_angkatan;		
    }
    
    public Vector getListMatakuliahAktif(String kdpst) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		if(con==null || con.isClosed()) {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    		/*
    		 * get list krkklm aktif
    		 */
    			stmt = con.prepareStatement("select * from MAKUL where KDPSTMAKUL=? and STKMKMAKUL=? order by NAKMKMAKUL");
    			stmt.setString(1,kdpst);
    			stmt.setString(2,"A");
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String idkmk = ""+rs.getInt("IDKMKMAKUL");
    				String kdkmk = rs.getString("KDKMKMAKUL");
    				String nakmk = rs.getString("NAKMKMAKUL");
    				String skstm = ""+rs.getInt("SKSTMMAKUL");
    				String skspr = ""+rs.getInt("SKSPRMAKUL");
    				String skslp = ""+rs.getInt("SKSLPMAKUL");
    				String kdwpl = rs.getString("KDWPLMAKUL");
    				String jenis = rs.getString("JENISMAKUL");
    				String nodos = rs.getString("NODOSMAKUL");
    				li.add(idkmk);
    				li.add(kdkmk);
    				li.add(nakmk);
    				li.add(skstm);
    				li.add(skspr);
    				li.add(skslp);
    				li.add(kdwpl);
    				li.add(jenis);
    				li.add(nodos);
    			}
    		}
    		else {
    		/*
    		 * get list krkklm aktif
    		 */
    			stmt = con.prepareStatement("select * from MAKUL where KDPSTMAKUL=? and STKMKMAKUL=? order by NAKMKMAKUL");
    			stmt.setString(1,kdpst);
    			stmt.setString(2,"A");
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String idkmk = ""+rs.getInt("IDKMKMAKUL");
    				String kdkmk = rs.getString("KDKMKMAKUL");
    				String nakmk = rs.getString("NAKMKMAKUL");
    				String skstm = ""+rs.getInt("SKSTMMAKUL");
    				String skspr = ""+rs.getInt("SKSPRMAKUL");
    				String skslp = ""+rs.getInt("SKSLPMAKUL");
    				String kdwpl = rs.getString("KDWPLMAKUL");
    				String jenis = rs.getString("JENISMAKUL");
    				String nodos = rs.getString("NODOSMAKUL");
    				li.add(idkmk);
    				li.add(kdkmk);
    				li.add(nakmk);
    				li.add(skstm);
    				li.add(skspr);
    				li.add(skslp);
    				li.add(kdwpl);
    				li.add(jenis);
    				li.add(nodos);
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
    				//System.out.println(ignore);
    			}
    		}
    	}
    	return v;
    }
    
    public Vector getListInfotMakur(String kdpst) {
    	Vector v = new Vector();
    	Vector v1 = null;
    	ListIterator li = v.listIterator();
    	try {
    		if(con==null || con.isClosed()) {
    		
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    		/*
    		 * get list krkklm aktif
    		 */
    			stmt = con.prepareStatement("select * from KRKLM where KDPSTKRKLM=? and STKURKRKLM=?");
    			stmt.setString(1,kdpst);
    			stmt.setString(2,"A");
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				int idkur = rs.getInt("IDKURKRKLM");
    				String nmkur = rs.getString("NMKURKRKLM");
    				String start = rs.getString("STARTTHSMS");
    				String ended = rs.getString("ENDEDTHSMS");
    				li.add(idkur+" "+start+" "+ended);
    				li.add(nmkur);
    			}
    		/*
    		 * get info dari bridge table untuk given idkur
    		 */
    			v1 = new Vector();
    			ListIterator li1 = v1.listIterator();
    			stmt = con.prepareStatement("select * from MAKUR inner join MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURMAKUR=?");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String baris1 = (String)li.next();
    				String nmkur = (String)li.next();
    				StringTokenizer st = new StringTokenizer(baris1);
    				String idkur = st.nextToken();
    				String start = st.nextToken();
    				String ended = st.nextToken();
    				stmt.setInt(1, Integer.valueOf(idkur).intValue());
    				rs = stmt.executeQuery();
    				Vector vTmp = new Vector();
    				ListIterator liTmp = vTmp.listIterator();
    				int skstt = 0;
    				int ttkls =0;
    			
    				while(rs.next()) {
    					String idkmk = ""+rs.getInt("IDKMKMAKUL");
    					String kdkmk = rs.getString("KDKMKMAKUL");
    					String nakmk = rs.getString("NAKMKMAKUL");
    					int skstm = rs.getInt("SKSTMMAKUL");
    					int skspr = rs.getInt("SKSPRMAKUL");
    					int skslp = rs.getInt("SKSLPMAKUL");
    					int sksmk = skstm+skspr+skslp;
    					skstt = skstt+sksmk;
    					ttkls = ttkls+1;
    					String semes = ""+rs.getString("SEMESMAKUR");
    					String mk_akhir = ""+rs.getBoolean("FINAL_MK");
    					liTmp.add(idkmk+","+sksmk+","+semes+","+nakmk+","+mk_akhir);
    					//liTmp.add(idkmk+","+sksmk+","+semes+","+nakmk);
    				}
    				li1.add(baris1);
    				li1.add(nmkur);
    				li1.add(vTmp);
    				li1.add(""+ttkls);
    				//System.out.println("add ttkls="+ttkls);
    				li1.add(""+skstt);
    				//System.out.println("add skstt="+skstt);
    			}
    		}
    		else {
    		/*
    		 * get list krkklm aktif
    		 */
    			stmt = con.prepareStatement("select * from KRKLM where KDPSTKRKLM=? and STKURKRKLM=?");
    			stmt.setString(1,kdpst);
    			stmt.setString(2,"A");
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				int idkur = rs.getInt("IDKURKRKLM");
    				String nmkur = rs.getString("NMKURKRKLM");
    				String start = rs.getString("STARTTHSMS");
    				String ended = rs.getString("ENDEDTHSMS");
    				li.add(idkur+" "+start+" "+ended);
    				li.add(nmkur);
    			}
    		/*
    		 * get info dari bridge table untuk given idkur
    		 */
    			v1 = new Vector();
    			ListIterator li1 = v1.listIterator();
    			stmt = con.prepareStatement("select * from MAKUR inner join MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURMAKUR=?");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String baris1 = (String)li.next();
    				String nmkur = (String)li.next();
    				StringTokenizer st = new StringTokenizer(baris1);
    				String idkur = st.nextToken();
    				String start = st.nextToken();
    				String ended = st.nextToken();
    				stmt.setInt(1, Integer.valueOf(idkur).intValue());
    				rs = stmt.executeQuery();
    				Vector vTmp = new Vector();
    				ListIterator liTmp = vTmp.listIterator();
    				int skstt = 0;
    				int ttkls =0;
    			
    				while(rs.next()) {
    					String idkmk = ""+rs.getInt("IDKMKMAKUL");
    					String kdkmk = rs.getString("KDKMKMAKUL");
    					String nakmk = rs.getString("NAKMKMAKUL");
    					int skstm = rs.getInt("SKSTMMAKUL");
    					int skspr = rs.getInt("SKSPRMAKUL");
    					int skslp = rs.getInt("SKSLPMAKUL");
    					int sksmk = skstm+skspr+skslp;
    					skstt = skstt+sksmk;
    					ttkls = ttkls+1;
    					String semes = ""+rs.getString("SEMESMAKUR");
    					String mk_akhir = ""+rs.getBoolean("FINAL_MK");
    					liTmp.add(idkmk+","+sksmk+","+semes+","+nakmk+","+mk_akhir);
    					//liTmp.add(idkmk+","+sksmk+","+semes+","+nakmk);
    				}
    				li1.add(baris1);
    				li1.add(nmkur);
    				li1.add(vTmp);
    				li1.add(""+ttkls);
    				//System.out.println("add ttkls="+ttkls);
    				li1.add(""+skstt);
    				//System.out.println("add skstt="+skstt);
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
    	return v1;
    }

    public Vector getListInfotMakur(String kdpst, String idkur) {
    	Vector v = new Vector();
    	Vector v1 = null;
    	ListIterator li = v.listIterator();
    	try {
    		if(con==null || con.isClosed()) {
    		
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			/*
    		 * 	get list krkklm aktif
    		 	*/
    			stmt = con.prepareStatement("select * from KRKLM where KDPSTKRKLM=? and IDKURKRKLM=?");
    			stmt.setString(1,kdpst);
    			stmt.setInt(2,Integer.valueOf(idkur).intValue());
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    			//int idkur = rs.getInt("IDKURKRKLM");
    				String nmkur = rs.getString("NMKURKRKLM");
    				String start = rs.getString("STARTTHSMS");
    				String ended = rs.getString("ENDEDTHSMS");
    				li.add(idkur+" "+start+" "+ended);
    				li.add(nmkur);
    			}
    			/*
    			 * get info dari bridge table untuk given idkur
    			 */
    			v1 = new Vector();
    			ListIterator li1 = v1.listIterator();
    			stmt = con.prepareStatement("select * from MAKUR inner join MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURMAKUR=?");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String baris1 = (String)li.next();
    				String nmkur = (String)li.next();
    				StringTokenizer st = new StringTokenizer(baris1);
    				idkur = st.nextToken();
    				String start = st.nextToken();
    				String ended = st.nextToken();
    				stmt.setInt(1, Integer.valueOf(idkur).intValue());
    				rs = stmt.executeQuery();
    				Vector vTmp = new Vector();
    				ListIterator liTmp = vTmp.listIterator();
    				int skstt = 0;
    				int ttkls =0;
    			
    				while(rs.next()) {
    					String idkmk = ""+rs.getInt("IDKMKMAKUL");
    					String kdkmk = rs.getString("KDKMKMAKUL");
    					String nakmk = rs.getString("NAKMKMAKUL");
    					int skstm = rs.getInt("SKSTMMAKUL");
    					int skspr = rs.getInt("SKSPRMAKUL");
    					int skslp = rs.getInt("SKSLPMAKUL");
    					int sksmk = skstm+skspr+skslp;
    					skstt = skstt+sksmk;
    					ttkls = ttkls+1;
    					String semes = ""+rs.getString("SEMESMAKUR");
    					String mk_akhir = ""+rs.getBoolean("FINAL_MK");
    					liTmp.add(idkmk+","+sksmk+","+semes+","+nakmk+","+mk_akhir);
    				//	liTmp.add(idkmk+","+sksmk+","+nakmk);
    				}
    				li1.add(baris1);
    				li1.add(nmkur);
    				li1.add(vTmp);
    				li1.add(""+ttkls);
    				//System.out.println("add ttkls="+ttkls);
    				li1.add(""+skstt);
    				//System.out.println("add skstt="+skstt);
    			}
    		}
    		else {
    			/*
    			 * 	get list krkklm aktif
    		 	*/
    			stmt = con.prepareStatement("select * from KRKLM where KDPSTKRKLM=? and IDKURKRKLM=?");
    			stmt.setString(1,kdpst);
    			stmt.setInt(2,Integer.valueOf(idkur).intValue());
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    			//int idkur = rs.getInt("IDKURKRKLM");
    				String nmkur = rs.getString("NMKURKRKLM");
    				String start = rs.getString("STARTTHSMS");
    				String ended = rs.getString("ENDEDTHSMS");
    				li.add(idkur+" "+start+" "+ended);
    				li.add(nmkur);
    			}
    			/*
    			 * get info dari bridge table untuk given idkur
    			 */
    			v1 = new Vector();
    			ListIterator li1 = v1.listIterator();
    			stmt = con.prepareStatement("select * from MAKUR inner join MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURMAKUR=?");
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String baris1 = (String)li.next();
    				String nmkur = (String)li.next();
    				StringTokenizer st = new StringTokenizer(baris1);
    				idkur = st.nextToken();
    				String start = st.nextToken();
    				String ended = st.nextToken();
    				stmt.setInt(1, Integer.valueOf(idkur).intValue());
    				rs = stmt.executeQuery();
    				Vector vTmp = new Vector();
    				ListIterator liTmp = vTmp.listIterator();
    				int skstt = 0;
    				int ttkls =0;
    			
    				while(rs.next()) {
    					String idkmk = ""+rs.getInt("IDKMKMAKUL");
    					String kdkmk = rs.getString("KDKMKMAKUL");
    					String nakmk = rs.getString("NAKMKMAKUL");
    					int skstm = rs.getInt("SKSTMMAKUL");
    					int skspr = rs.getInt("SKSPRMAKUL");
    					int skslp = rs.getInt("SKSLPMAKUL");
    					int sksmk = skstm+skspr+skslp;
    					skstt = skstt+sksmk;
    					ttkls = ttkls+1;
    					String semes = ""+rs.getString("SEMESMAKUR");
    					String mk_akhir = ""+rs.getBoolean("FINAL_MK");
    					liTmp.add(idkmk+","+sksmk+","+semes+","+nakmk+","+mk_akhir);
    					//liTmp.add(idkmk+","+sksmk+","+semes+","+nakmk);
    				//	liTmp.add(idkmk+","+sksmk+","+nakmk);
    				}
    				li1.add(baris1);
    				li1.add(nmkur);
    				li1.add(vTmp);
    				li1.add(""+ttkls);
    				//System.out.println("add ttkls="+ttkls);
    				li1.add(""+skstt);
    				//System.out.println("add skstt="+skstt);
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
    	return v1;
    }

    
    public Vector searchKui(Vector vScope,String kword) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	
    	int keyword = Integer.valueOf(kword).intValue();
    	//System.out.println(keyword);
    	ListIterator liScope = vScope.listIterator();
    	String sql = "(";
		while(liScope.hasNext()) {
			String baris = (String)liScope.next();
			StringTokenizer st = new StringTokenizer(baris);
			String idObj = st.nextToken();
			String kdpst = st.nextToken();
			String keter = st.nextToken();
			String objLv = st.nextToken();
			sql = sql+"KDPSTPYMNT='"+kdpst+"'";
			if(liScope.hasNext()) {
				sql = sql +" OR ";
			}
		}
		sql = sql+")";
		try {
			if(con==null || con.isClosed()) {
				Context initContext  = new InitialContext();
				Context envContext  = (Context)initContext.lookup("java:/comp/env");
				ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
				con = ds.getConnection();
				stmt = con.prepareStatement("select * from PYMNT inner join CIVITAS on NPMHSPYMNT=NPMHSMSMHS where NORUTPYMNT=? and "+sql);
				stmt.setInt(1,keyword);
				rs = stmt.executeQuery();
				while(rs.next()) {
					String kuiid = ""+rs.getString("KUIIDPYMNT");
					String kdpst = ""+rs.getString("KDPSTPYMNT");
					String npmhs = ""+rs.getString("NPMHSPYMNT");
					String norut = ""+rs.getString("NORUTPYMNT");
					String tgkui = ""+rs.getDate("TGKUIPYMNT");
					String tgtrs = ""+rs.getDate("TGTRSPYMNT");
					String keter = ""+rs.getString("KETERPYMNT");
					keter = keter.replaceAll(",", "-");
					String amont = ""+rs.getDouble("AMONTPYMNT");
					String opnmm = ""+rs.getString("OPNMMPYMNT");
					String voidd = ""+rs.getBoolean("VOIDDPYMNT");
					String nmmhs = ""+rs.getString("NMMHSMSMHS");
					li.add(kuiid+","+norut+","+kdpst+","+npmhs+","+nmmhs+","+tgkui+","+tgtrs+","+keter+","+amont+","+opnmm+","+voidd);
				}	
			}
			else {
				stmt = con.prepareStatement("select * from PYMNT inner join CIVITAS on NPMHSPYMNT=NPMHSMSMHS where NORUTPYMNT=? and "+sql);
				stmt.setInt(1,keyword);
				rs = stmt.executeQuery();
				while(rs.next()) {
					String kuiid = ""+rs.getString("KUIIDPYMNT");
					String kdpst = ""+rs.getString("KDPSTPYMNT");
					String npmhs = ""+rs.getString("NPMHSPYMNT");
					String norut = ""+rs.getString("NORUTPYMNT");
					String tgkui = ""+rs.getDate("TGKUIPYMNT");
					String tgtrs = ""+rs.getDate("TGTRSPYMNT");
					String keter = ""+rs.getString("KETERPYMNT");
					keter = keter.replaceAll(",", "-");
					String amont = ""+rs.getDouble("AMONTPYMNT");
					String opnmm = ""+rs.getString("OPNMMPYMNT");
					String voidd = ""+rs.getBoolean("VOIDDPYMNT");
					String nmmhs = ""+rs.getString("NMMHSMSMHS");
					li.add(kuiid+","+norut+","+kdpst+","+npmhs+","+nmmhs+","+tgkui+","+tgtrs+","+keter+","+amont+","+opnmm+","+voidd);
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
		return v;
    }
    
    public String getThsmsAktif() {
    	String thsms = null;
    	try {
    		if(con==null || con.isClosed()) {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select THSMS from CALENDAR where AKTIF=?");
    			stmt.setBoolean(1, true);
    			rs = stmt.executeQuery();
    		
    			if(rs.next()) {
    				thsms = rs.getString("THSMS");
    			}
    		}
    		else {
    			stmt = con.prepareStatement("select THSMS from CALENDAR where AKTIF=?");
    			stmt.setBoolean(1, true);
    			rs = stmt.executeQuery();
    		
    			if(rs.next()) {
    				thsms = rs.getString("THSMS");
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
    	return thsms;
    }

    public Vector getListKuitansi(Vector vScope,String yr,String mm,int startAt,int range,String filterTgl) {
    	if(mm.length()==1) {
    		mm="0"+mm;
    	}
    	if(Checker.isStringNullOrEmpty(filterTgl)) {
    		filterTgl = "tglkui";
    	}
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	ListIterator liScp = vScope.listIterator();
    	String sql  = "select * from PYMNT inner join CIVITAS on PYMNT.NPMHSPYMNT=CIVITAS.NPMHSMSMHS where (";
    	String tkn_kdpst = "";
		while(liScp.hasNext()){
			String baris = (String)liScp.next();
			StringTokenizer st = new StringTokenizer(baris);
			String idObj = st.nextToken();
			String kdpst = st.nextToken();
			String keter = st.nextToken();
			String objLv = st.nextToken();
			tkn_kdpst = tkn_kdpst+kdpst;
			sql=sql+"KDPSTPYMNT='"+kdpst+"'";
			if(liScp.hasNext()) {
				tkn_kdpst=tkn_kdpst+" ";
				sql = sql+" || ";
			}
		}
		sql = sql+")";
		if(filterTgl.equalsIgnoreCase("tglkui")) {
			sql = sql+" and ((TGKUIPYMNT between '"+yr+"-"+mm+"-01' and '"+yr+"-"+mm+"-31'))";	
		}
		else {
			sql = sql+" and ((TGTRSPYMNT between '"+yr+"-"+mm+"-01' and '"+yr+"-"+mm+"-31'))";
		}
		
    	//sql = sql+" and ((TGTRSPYMNT between '"+yr+"-"+mm+"-01' and '"+yr+"-"+mm+"-31') or (TGKUIPYMNT between '"+yr+"-"+mm+"-01' and '"+yr+"-"+mm+"-31'))";
    	//System.out.println(periode);
    	try {
    		if(con==null || con.isClosed()) {
    		
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			//sql = sql+" order by NORUTPYMNT desc";
    			sql = sql+" order by NORUTPYMNT desc limit "+startAt+","+range;
    		//	//System.out.println(sql);
    			stmt = con.prepareStatement(sql);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String kuiid = ""+rs.getInt("KUIIDPYMNT");
    				String kdpst = rs.getString("KDPSTPYMNT");
    				String nmphs = rs.getString("NPMHSPYMNT");
    				String norut = ""+rs.getInt("NORUTPYMNT");
    				String tgkui = ""+rs.getDate("TGKUIPYMNT");
    				String tgtrs = ""+rs.getDate("TGTRSPYMNT");
    				String keter = rs.getString("KETERPYMNT");
    				keter = keter.replaceAll(",", "-");
    				String amont = ""+rs.getDouble("AMONTPYMNT");
    				String noacc = ""+rs.getString("NOACCPYMNT");
    				String voidd = ""+rs.getBoolean("VOIDDPYMNT");
    				String nmmhs = rs.getString("NMMHSMSMHS");
    				li.add(kuiid+","+kdpst+","+nmphs+","+nmmhs+","+norut+","+tgkui+","+tgtrs+","+keter+","+amont+","+voidd+","+noacc);
    			}	
    		}
    		else {
    			sql = sql+" order by NORUTPYMNT desc limit "+startAt+","+range;
    			//sql = sql+" order by NORUTPYMNT desc";
    		//	//System.out.println(sql);
    			stmt = con.prepareStatement(sql);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String kuiid = ""+rs.getInt("KUIIDPYMNT");
    				String kdpst = rs.getString("KDPSTPYMNT");
    				String nmphs = rs.getString("NPMHSPYMNT");
    				String norut = ""+rs.getInt("NORUTPYMNT");
    				String tgkui = ""+rs.getDate("TGKUIPYMNT");
    				String tgtrs = ""+rs.getDate("TGTRSPYMNT");
    				String keter = rs.getString("KETERPYMNT");
    				keter = keter.replaceAll(",", "-");
    				String amont = ""+rs.getDouble("AMONTPYMNT");
    				String noacc = ""+rs.getString("NOACCPYMNT");
    				String voidd = ""+rs.getBoolean("VOIDDPYMNT");
    				String nmmhs = rs.getString("NMMHSMSMHS");
    				li.add(kuiid+","+kdpst+","+nmphs+","+nmmhs+","+norut+","+tgkui+","+tgtrs+","+keter+","+amont+","+voidd+","+noacc);
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
    	return v;
    }

    public Vector getListKuitansiBasedOnIdObj(Vector vScopeIdObj,String yr,String mm,int startAt,int range,String filterTgl) {
    	if(mm.length()==1) {
    		mm="0"+mm;
    	}
    	if(Checker.isStringNullOrEmpty(filterTgl)) {
    		filterTgl = "tglkui";
    	}
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	ListIterator liScp = vScopeIdObj.listIterator();
    	String sql  = "select * from PYMNT inner join CIVITAS on PYMNT.NPMHSPYMNT=CIVITAS.NPMHSMSMHS where (";
    	String tkn_idObj = "";
		while(liScp.hasNext()){
			String idObj = (String)liScp.next();
			//StringTokenizer st = new StringTokenizer(baris);
			//String idObj = st.nextToken();
			//String kdpst = st.nextToken();
			//String keter = st.nextToken();
			//String objLv = st.nextToken();
			tkn_idObj = tkn_idObj+idObj;
			sql=sql+"PYMNT.ID_OBJ="+idObj+"";
			if(liScp.hasNext()) {
				tkn_idObj=tkn_idObj+" ";
				sql = sql+" || ";
			}
		}
		sql = sql+")";
		if(filterTgl.equalsIgnoreCase("tglkui")) {
			sql = sql+" and ((TGKUIPYMNT between '"+yr+"-"+mm+"-01' and '"+yr+"-"+mm+"-31'))";	
		}
		else {
			sql = sql+" and ((TGTRSPYMNT between '"+yr+"-"+mm+"-01' and '"+yr+"-"+mm+"-31'))";
		}
		
    	//sql = sql+" and ((TGTRSPYMNT between '"+yr+"-"+mm+"-01' and '"+yr+"-"+mm+"-31') or (TGKUIPYMNT between '"+yr+"-"+mm+"-01' and '"+yr+"-"+mm+"-31'))";
    	//System.out.println(periode);
    	try {
    		if(con==null || con.isClosed()) {
    		
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			//sql = sql+" order by NORUTPYMNT desc";
    			sql = sql+" order by NORUTPYMNT desc limit "+startAt+","+range;
    		//	//System.out.println(sql);
    			stmt = con.prepareStatement(sql);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String kuiid = ""+rs.getInt("KUIIDPYMNT");
    				String kdpst = rs.getString("KDPSTPYMNT");
    				String nmphs = rs.getString("NPMHSPYMNT");
    				String norut = ""+rs.getInt("NORUTPYMNT");
    				String tgkui = ""+rs.getDate("TGKUIPYMNT");
    				String tgtrs = ""+rs.getDate("TGTRSPYMNT");
    				String keter = rs.getString("KETERPYMNT");
    				keter = keter.replaceAll(",", "-");
    				String amont = ""+rs.getDouble("AMONTPYMNT");
    				String noacc = ""+rs.getString("NOACCPYMNT");
    				String voidd = ""+rs.getBoolean("VOIDDPYMNT");
    				String nmmhs = rs.getString("NMMHSMSMHS");
    				li.add(kuiid+","+kdpst+","+nmphs+","+nmmhs+","+norut+","+tgkui+","+tgtrs+","+keter+","+amont+","+voidd+","+noacc);
    			}	
    		}
    		else {
    			sql = sql+" order by NORUTPYMNT desc limit "+startAt+","+range;
    			//sql = sql+" order by NORUTPYMNT desc";
    		//	//System.out.println(sql);
    			stmt = con.prepareStatement(sql);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String kuiid = ""+rs.getInt("KUIIDPYMNT");
    				String kdpst = rs.getString("KDPSTPYMNT");
    				String nmphs = rs.getString("NPMHSPYMNT");
    				String norut = ""+rs.getInt("NORUTPYMNT");
    				String tgkui = ""+rs.getDate("TGKUIPYMNT");
    				String tgtrs = ""+rs.getDate("TGTRSPYMNT");
    				String keter = rs.getString("KETERPYMNT");
    				keter = keter.replaceAll(",", "-");
    				String amont = ""+rs.getDouble("AMONTPYMNT");
    				String noacc = ""+rs.getString("NOACCPYMNT");
    				String voidd = ""+rs.getBoolean("VOIDDPYMNT");
    				String nmmhs = rs.getString("NMMHSMSMHS");
    				li.add(kuiid+","+kdpst+","+nmphs+","+nmmhs+","+norut+","+tgkui+","+tgtrs+","+keter+","+amont+","+voidd+","+noacc);
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
    	return v;
    }

    
    
    public Vector getListKuitansi(Vector vScope,String periode,int startAt,int range, String filterTgl) {
    	Vector v = new Vector();
    	if(Checker.isStringNullOrEmpty(filterTgl)) {
    		filterTgl = "tglkui";
    	}
    	ListIterator li = v.listIterator();
    	ListIterator liScp = vScope.listIterator();
    	String sql  = "select * from PYMNT inner join CIVITAS on PYMNT.NPMHSPYMNT=CIVITAS.NPMHSMSMHS where (";
    	String tkn_kdpst = "";
		while(liScp.hasNext()){
			String baris = (String)liScp.next();
			StringTokenizer st = new StringTokenizer(baris);
			String idObj = st.nextToken();
			String kdpst = st.nextToken();
			String keter = st.nextToken();
			String objLv = st.nextToken();
			tkn_kdpst = tkn_kdpst+kdpst;
			sql=sql+"KDPSTPYMNT='"+kdpst+"'";
			if(liScp.hasNext()) {
				tkn_kdpst=tkn_kdpst+" ";
				sql = sql+" || ";
			}
		}
		sql = sql+")";
		
    	if(periode.equals("currentYear")) {
    		String yr = AskSystem.getCurrentYear();
    		String mm = AskSystem.getCurrentMonth();
    		//mm = Integer.valueOf(mm).intValue()-1+"";
    		mm = Integer.valueOf(mm).intValue()+"";
    		if(mm.length()==1) {
    			mm = "0"+mm;
    		}
    		//sql = sql+" and ((TGTRSPYMNT between '"+yr+"-"+mm+"-01' and '"+yr+"-"+mm+"-31') or (TGKUIPYMNT between '"+yr+"-"+mm+"-01' and '"+yr+"-"+mm+"-31'))";
    		if(filterTgl.equalsIgnoreCase("tglkui")) {
    			sql = sql+" and ((TGKUIPYMNT between '"+yr+"-"+mm+"-01' and '"+yr+"-"+mm+"-31'))";	
    		}
    		else {
    			sql = sql+" and ((TGTRSPYMNT between '"+yr+"-"+mm+"-01' and '"+yr+"-"+mm+"-31'))";
    		}
    		//sql = sql+" and ((TGTRSPYMNT between '"+yr+"-"+mm+"-01' and '"+yr+"-"+mm+"-31') or (TGKUIPYMNT between '"+yr+"-"+mm+"-01' and '"+yr+"-"+mm+"-31'))";
    		//System.out.println("-"+yr+"-"+mm);
    		//System.out.println(sql);
    	}
    	else {
    		/*
    		 * other peride type
    		 */
    	}
    	try {
    		//boolean connected = false;
    		if(con==null || con.isClosed()) {
    		
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			sql = sql+" order by NORUTPYMNT desc limit "+startAt+","+range;
    		//	//System.out.println(sql);
    			stmt = con.prepareStatement(sql);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String kuiid = ""+rs.getInt("KUIIDPYMNT");
    				String kdpst = rs.getString("KDPSTPYMNT");
    				String nmphs = rs.getString("NPMHSPYMNT");
    				String norut = ""+rs.getInt("NORUTPYMNT");
    				String tgkui = ""+rs.getDate("TGKUIPYMNT");
    				String tgtrs = ""+rs.getDate("TGTRSPYMNT");
    				String keter = rs.getString("KETERPYMNT");
    				String amont = ""+rs.getDouble("AMONTPYMNT");
    				String noacc = ""+rs.getString("NOACCPYMNT");
    				String voidd = ""+rs.getBoolean("VOIDDPYMNT");
    				String nmmhs = rs.getString("NMMHSMSMHS");
    				li.add(kuiid+","+kdpst+","+nmphs+","+nmmhs+","+norut+","+tgkui+","+tgtrs+","+keter+","+amont+","+voidd+","+noacc);
    			}
    		}
    		else {
    			sql = sql+" order by NORUTPYMNT desc limit "+startAt+","+range;
    			//sql = sql+" order by NORUTPYMNT desc";
    		//	//System.out.println(sql);
    			stmt = con.prepareStatement(sql);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String kuiid = ""+rs.getInt("KUIIDPYMNT");
    				String kdpst = rs.getString("KDPSTPYMNT");
    				String nmphs = rs.getString("NPMHSPYMNT");
    				String norut = ""+rs.getInt("NORUTPYMNT");
    				String tgkui = ""+rs.getDate("TGKUIPYMNT");
    				String tgtrs = ""+rs.getDate("TGTRSPYMNT");
    				String keter = rs.getString("KETERPYMNT");
    				String amont = ""+rs.getDouble("AMONTPYMNT");
    				String noacc = ""+rs.getString("NOACCPYMNT");
    				String voidd = ""+rs.getBoolean("VOIDDPYMNT");
    				String nmmhs = rs.getString("NMMHSMSMHS");
    				li.add(kuiid+","+kdpst+","+nmphs+","+nmmhs+","+norut+","+tgkui+","+tgtrs+","+keter+","+amont+","+voidd+","+noacc);
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
    	return v;
    }
    
    public Vector getListKuitansiBasedOnIdObj(Vector vScopeIdObj,String periode,int startAt,int range, String filterTgl) {
    	//System.out.println("inin");
    	Vector v = new Vector();
    	if(Checker.isStringNullOrEmpty(filterTgl)) {
    		filterTgl = "tglkui";
    	}
    	ListIterator li = v.listIterator();
    	ListIterator liScp = vScopeIdObj.listIterator();
    	String sql  = "select * from PYMNT inner join CIVITAS on PYMNT.NPMHSPYMNT=CIVITAS.NPMHSMSMHS where (";
    	String tkn_idObj = "";
		while(liScp.hasNext()){
			String idObj = (String)liScp.next();
			//StringTokenizer st = new StringTokenizer(baris);
			//String idObj = st.nextToken();
			//String kdpst = st.nextToken();
			//String keter = st.nextToken();
			//String objLv = st.nextToken();
			tkn_idObj = tkn_idObj+idObj;
			sql=sql+"PYMNT.ID_OBJ="+idObj+"";
			if(liScp.hasNext()) {
				tkn_idObj=tkn_idObj+" ";
				sql = sql+" || ";
			}
		}
		sql = sql+")";
		
    	if(periode.equals("currentYear")) {
    		//System.out.println("inin2");
    		String yr = AskSystem.getCurrentYear();
    		String mm = AskSystem.getCurrentMonth();
    		//System.out.println("yr+mm="+yr+"-"+mm);
    		//mm = Integer.valueOf(mm).intValue()-1+"";
    		mm = Integer.valueOf(mm).intValue()+"";
    		if(mm.length()==1) {
    			mm = "0"+mm;
    		}
    		//sql = sql+" and ((TGTRSPYMNT between '"+yr+"-"+mm+"-01' and '"+yr+"-"+mm+"-31') or (TGKUIPYMNT between '"+yr+"-"+mm+"-01' and '"+yr+"-"+mm+"-31'))";
    		if(filterTgl.equalsIgnoreCase("tglkui")) {
    			sql = sql+" and ((TGKUIPYMNT between '"+yr+"-"+mm+"-01' and '"+yr+"-"+mm+"-31'))";	
    		}
    		else {
    			sql = sql+" and ((TGTRSPYMNT between '"+yr+"-"+mm+"-01' and '"+yr+"-"+mm+"-31'))";
    		}
    		//sql = sql+" and ((TGTRSPYMNT between '"+yr+"-"+mm+"-01' and '"+yr+"-"+mm+"-31') or (TGKUIPYMNT between '"+yr+"-"+mm+"-01' and '"+yr+"-"+mm+"-31'))";
    		//System.out.println("-"+yr+"-"+mm);
    		//System.out.println(sql);
    	}
    	else {
    		/*
    		 * other peride type
    		 */
    	}
    	try {
    		//boolean connected = false;
    		if(con==null || con.isClosed()) {
    		
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			sql = sql+" order by NORUTPYMNT desc limit "+startAt+","+range;
    			//System.out.println("sql0011="+sql);
    			stmt = con.prepareStatement(sql);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String kuiid = ""+rs.getInt("KUIIDPYMNT");
    				String kdpst = rs.getString("KDPSTPYMNT");
    				String nmphs = rs.getString("NPMHSPYMNT");
    				String norut = ""+rs.getInt("NORUTPYMNT");
    				String tgkui = ""+rs.getDate("TGKUIPYMNT");
    				String tgtrs = ""+rs.getDate("TGTRSPYMNT");
    				String keter = rs.getString("KETERPYMNT");
    				String amont = ""+rs.getDouble("AMONTPYMNT");
    				String noacc = ""+rs.getString("NOACCPYMNT");
    				String voidd = ""+rs.getBoolean("VOIDDPYMNT");
    				String nmmhs = rs.getString("NMMHSMSMHS");
    				li.add(kuiid+","+kdpst+","+nmphs+","+nmmhs+","+norut+","+tgkui+","+tgtrs+","+keter+","+amont+","+voidd+","+noacc);
    			}
    		}
    		else {
    			sql = sql+" order by NORUTPYMNT desc limit "+startAt+","+range;
    			//sql = sql+" order by NORUTPYMNT desc";
    		//	//System.out.println(sql);
    			stmt = con.prepareStatement(sql);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String kuiid = ""+rs.getInt("KUIIDPYMNT");
    				String kdpst = rs.getString("KDPSTPYMNT");
    				String nmphs = rs.getString("NPMHSPYMNT");
    				String norut = ""+rs.getInt("NORUTPYMNT");
    				String tgkui = ""+rs.getDate("TGKUIPYMNT");
    				String tgtrs = ""+rs.getDate("TGTRSPYMNT");
    				String keter = rs.getString("KETERPYMNT");
    				String amont = ""+rs.getDouble("AMONTPYMNT");
    				String noacc = ""+rs.getString("NOACCPYMNT");
    				String voidd = ""+rs.getBoolean("VOIDDPYMNT");
    				String nmmhs = rs.getString("NMMHSMSMHS");
    				li.add(kuiid+","+kdpst+","+nmphs+","+nmmhs+","+norut+","+tgkui+","+tgtrs+","+keter+","+amont+","+voidd+","+noacc);
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
    	return v;
    }
    
    
    
    
    public String getNmdosFromMsdos(String nodos) {
    	String nmdos = null;
    	try {
    		if(con==null || con.isClosed()) {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from MSDOS where NODOSMSDOS=?");
    			stmt.setString(1, nodos);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				nmdos = rs.getString("NMDOSMSDOS");
    			}
    		}
    		else {
    			stmt = con.prepareStatement("select * from MSDOS where NODOSMSDOS=?");
    			stmt.setString(1, nodos);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				nmdos = rs.getString("NMDOSMSDOS");
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
    	return nmdos;
    }
    
    public Vector getListMakulProdi(String kdpst) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		if(con==null || con.isClosed()) {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from MAKUL where KDPSTMAKUL=? order by STKMKMAKUL,KDKMKMAKUL,IDKMKMAKUL");
    			stmt.setString(1, kdpst);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				int idkmk = rs.getInt("IDKMKMAKUL");
    				String kdkmk = rs.getString("KDKMKMAKUL");
    				String nakmk = rs.getString("NAKMKMAKUL");
    				//System.out.println("nakmk="+nakmk);
    				nakmk = nakmk.replace(",", "$");
    				//System.out.println("nakmk2="+nakmk);
    				int skstm = rs.getInt("SKSTMMAKUL");
    				int skspr = rs.getInt("SKSPRMAKUL");
    				int skslp = rs.getInt("SKSLPMAKUL");
    				int skstt = skstm+skspr+skslp;
    				//System.out.println("skstt = "+skstt);
    				String kdwpl = rs.getString("KDWPLMAKUL");
    				String jenis = rs.getString("JENISMAKUL");
    				String nodos = rs.getString("NODOSMAKUL");
    				if(nodos!=null) {
    					StringTokenizer st = new StringTokenizer(nodos);
    					if(st.countTokens()<1) {
    						nodos="null";
    					}
    				}
    				String stkmk = rs.getString("STKMKMAKUL");
    				li.add(idkmk+","+kdpst+","+kdkmk+","+nakmk+","+skstt+","+skstm+","+skspr+","+skslp+","+kdwpl+","+jenis+","+nodos+","+stkmk);
    			}	
    		}
    		else {
    			stmt = con.prepareStatement("select * from MAKUL where KDPSTMAKUL=? order by STKMKMAKUL,KDKMKMAKUL,IDKMKMAKUL");
    			stmt.setString(1, kdpst);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				int idkmk = rs.getInt("IDKMKMAKUL");
    				String kdkmk = rs.getString("KDKMKMAKUL");
    				String nakmk = rs.getString("NAKMKMAKUL");
    				//System.out.println("nakmk3="+nakmk);
    				nakmk = nakmk.replace(",", "$");
    				//System.out.println("nakmk4="+nakmk);
    				int skstm = rs.getInt("SKSTMMAKUL");
    				int skspr = rs.getInt("SKSPRMAKUL");
    				int skslp = rs.getInt("SKSLPMAKUL");
    				int skstt = skstm+skspr+skslp;
    				//System.out.println("skstt = "+skstt);
    				String kdwpl = rs.getString("KDWPLMAKUL");
    				String jenis = rs.getString("JENISMAKUL");
    				String nodos = rs.getString("NODOSMAKUL");
    				if(nodos!=null) {
    					StringTokenizer st = new StringTokenizer(nodos);
    					if(st.countTokens()<1) {
    						nodos="null";
    					}
    				}
    				String stkmk = rs.getString("STKMKMAKUL");
    				li.add(idkmk+","+kdpst+","+kdkmk+","+nakmk+","+skstt+","+skstm+","+skspr+","+skslp+","+kdwpl+","+jenis+","+nodos+","+stkmk);
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
    	return v;
    }

    public Vector getListKurikulumProdi(String kdpst) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		if(con==null || con.isClosed()) {
    		
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from KRKLM where KDPSTKRKLM=? order by IDKURKRKLM");
    			stmt.setString(1, kdpst);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				int idkur = rs.getInt("IDKURKRKLM");
    				String kdkur = rs.getString("NMKURKRKLM");
    				String stkur = rs.getString("STKURKRKLM");
    				String thsms1 = rs.getString("STARTTHSMS");
    				String thsms2 = rs.getString("ENDEDTHSMS");
    				String konsen = rs.getString("KONSENTRASI");
    				li.add(idkur+","+kdkur+","+stkur+","+thsms1+","+thsms2+","+konsen);
    			}
    		}
    		else {
    			stmt = con.prepareStatement("select * from KRKLM where KDPSTKRKLM=? order by IDKURKRKLM");
    			stmt.setString(1, kdpst);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				int idkur = rs.getInt("IDKURKRKLM");
    				String kdkur = rs.getString("NMKURKRKLM");
    				String stkur = rs.getString("STKURKRKLM");
    				String thsms1 = rs.getString("STARTTHSMS");
    				String thsms2 = rs.getString("ENDEDTHSMS");
    				li.add(idkur+","+kdkur+","+stkur+","+thsms1+","+thsms2);
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
    	return v;
    }
    
    
    public Vector getVectorPaymentMhs(String kdpst, String twoTokenYear) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		if(con==null || con.isClosed()) {
    		//System.out.println("gateC");
    		
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from PYMNT where KDPSTPYMNT=? and TGTRSPYMNT between ? and ? and VOIDDPYMNT=?");
    			StringTokenizer st = new StringTokenizer(twoTokenYear);
    			String yr1 = st.nextToken();	
    			String yr2 = st.nextToken();
    			stmt.setString(1,kdpst);
    			stmt.setDate(2,java.sql.Date.valueOf(yr1+"-01-01"));
    			stmt.setDate(3,java.sql.Date.valueOf(yr2+"-12-31"));
    			stmt.setBoolean(4,false);
    			rs = stmt.executeQuery();
    		//li.add(kdpst);
    			int i = 0;
    			while(rs.next()) {
    				i++;
    				String dt = ""+rs.getDate("TGTRSPYMNT");
    				String amnt = ""+rs.getDouble("AMONTPYMNT");
    			//	//System.out.println("dt/amnt="+dt+" "+amnt);
    				li.add(dt+" "+amnt);
    			}
    		//System.out.println(i);
    		}
    		else {
    			stmt = con.prepareStatement("select * from PYMNT where KDPSTPYMNT=? and TGTRSPYMNT between ? and ? and VOIDDPYMNT=?");
    			StringTokenizer st = new StringTokenizer(twoTokenYear);
    			String yr1 = st.nextToken();	
    			String yr2 = st.nextToken();
    			stmt.setString(1,kdpst);
    			stmt.setDate(2,java.sql.Date.valueOf(yr1+"-01-01"));
    			stmt.setDate(3,java.sql.Date.valueOf(yr2+"-12-31"));
    			stmt.setBoolean(4,false);
    			rs = stmt.executeQuery();
    		//li.add(kdpst);
    			int i = 0;
    			while(rs.next()) {
    				i++;
    				String dt = ""+rs.getDate("TGTRSPYMNT");
    				String amnt = ""+rs.getDouble("AMONTPYMNT");
    			//	//System.out.println("dt/amnt="+dt+" "+amnt);
    				li.add(dt+" "+amnt);
    			}
    		//System.out.println(i);
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
    
    public int getTotMhs(String id_obj) {
    	int tt=0;
    	try {
    		if(con==null || con.isClosed()) {
    		
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from CIVITAS where ID_OBJ=?");
    			stmt.setLong(1, Long.valueOf(id_obj).longValue());
    			rs = stmt.executeQuery();
    			
    			while(rs.next()) {
    				tt++;
    			}	
    		}
    		else {
    			stmt = con.prepareStatement("select * from CIVITAS where ID_OBJ=?");
    			stmt.setLong(1, Long.valueOf(id_obj).longValue());
    			rs = stmt.executeQuery();
    			
    			while(rs.next()) {
    				tt++;
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
    	return tt;
    }

    public double getTotPymntMhs(String npm) {
    	double tt_pymnt=0;
    	try {
    		if(con==null || con.isClosed()) {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from PYMNT where NPMHSPYMNT=? order by TGTRSPYMNT");
    			stmt.setString(1,npm );
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				tt_pymnt = tt_pymnt+rs.getDouble("AMONTPYMNT");
    			}
    			//System.out.println("ttpymnt="+tt_pymnt);
    		
    		}
    		else {
    			stmt = con.prepareStatement("select * from PYMNT where NPMHSPYMNT=? order by TGTRSPYMNT");
    			stmt.setString(1,npm );
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				tt_pymnt = tt_pymnt+rs.getDouble("AMONTPYMNT");
    			}	
    			//System.out.println("ttpymnt="+tt_pymnt);
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
    	return tt_pymnt;
    }

    public Vector getUserKurikulum(Vector vScope, String npm) {
    	/*
    	 * value scope, bisa level atau own = ditentukan di tabel civitas;
    	 */
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	ListIterator lis = vScope.listIterator();
    	try {
    		if(con==null || con.isClosed()) {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    		//get aktif thsms
    			stmt = con.prepareStatement("select * from CALENDAR where AKTIF=true");
    			rs = stmt.executeQuery();
    			rs.next();
    			String thsms = rs.getString("THSMS");
    			boolean stop = false;
    			while(lis.hasNext() && !stop) {
    				String scope = (String)lis.next();
    				if(scope.equalsIgnoreCase("own")) {
    	    		//kode kurikulum berdasarkan EXT_CIVITAS, bila belum ada show blm update page
    	    			stmt = con.prepareStatement("select * from CIVITAS where NPMHSMSMHS=?");
    	    			stmt.setString(1, npm);
    	    			rs = stmt.executeQuery();
    	    			String kdpst = null;
    	    			if(rs.next()) {
    	    				kdpst = rs.getString("KDPSTMSMHS");
    	    			}	
    				}
    				else {
    				}	
    			}
    		}
    		else {
    		//get aktif thsms
    			stmt = con.prepareStatement("select * from CALENDAR where AKTIF=true");
    			rs = stmt.executeQuery();
    			rs.next();
    			String thsms = rs.getString("THSMS");
    			boolean stop = false;
    			while(lis.hasNext() && !stop) {
    				String scope = (String)lis.next();
    				if(scope.equalsIgnoreCase("own")) {
    	    		//kode kurikulum berdasarkan EXT_CIVITAS, bila belum ada show blm update page
    	    			stmt = con.prepareStatement("select * from CIVITAS where NPMHSMSMHS=?");
    	    			stmt.setString(1, npm);
    	    			rs = stmt.executeQuery();
    	    			String kdpst = null;
    	    			if(rs.next()) {
    	    				kdpst = rs.getString("KDPSTMSMHS");
    	    			}	
    				}
    				else {
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
    	return v;
    }
    
    
    public double getTotPymntMhsPerAngkatan(String kdpst, String smawl) {
    	double tt_pymnt=0;
    	try {
    		if(con==null || con.isClosed()) {
    		//System.out.println("gate1");
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from CIVITAS inner join PYMNT on NPMHSMSMHS=NPMHSPYMNT where KDPSTMSMHS=? and SMAWLMSMHS=?");
    			stmt.setString(1,kdpst);
    			stmt.setString(2,smawl);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				tt_pymnt = tt_pymnt+rs.getDouble("AMONTPYMNT");
    				//System.out.println("tt pymnt ="+tt_pymnt);
    			}	
    		}
    		else {
    			stmt = con.prepareStatement("select * from CIVITAS inner join PYMNT on NPMHSMSMHS=NPMHSPYMNT where KDPSTMSMHS=? and SMAWLMSMHS=?");
    			stmt.setString(1,kdpst);
    			stmt.setString(2,smawl);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				tt_pymnt = tt_pymnt+rs.getDouble("AMONTPYMNT");
    			}	
    		}
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (PoolExhaustedException pee) {
			pee.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}  	
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return tt_pymnt;
    }
    
    
    public Vector getTotMhsPerSmawl(String id_obj) {
    	int tt=0;
    	Vector vf = null;
    	try {
    		if(con==null || con.isClosed()) {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    		//get distinct smawl;
    			stmt = con.prepareStatement("select distinct SMAWLMSMHS from CIVITAS where ID_OBJ=?");
    			stmt.setLong(1, Long.valueOf(id_obj).longValue());
    			rs = stmt.executeQuery();
    			Vector v_smawl = new Vector();
    			ListIterator li_sm = v_smawl.listIterator();
    			while(rs.next()) {
    				li_sm.add(rs.getString("SMAWLMSMHS"));
    			}
    		
    			vf = new Vector();
    			ListIterator lif = vf.listIterator();
    			stmt = con.prepareStatement("select * from CIVITAS where ID_OBJ=? and SMAWLMSMHS=?");
    			li_sm = v_smawl.listIterator();
    			
    			while(li_sm.hasNext()) {
    				String smawl = (String)li_sm.next();
    				stmt.setLong(1, Long.valueOf(id_obj).longValue());
    				stmt.setString(2, smawl);
    				rs = stmt.executeQuery();
    				tt = 0;
    				while(rs.next()) {
    					tt++;
    				}	
    				lif.add(smawl+" "+tt);
    			}
    		}
    		else {
    		//get distinct smawl;
    			stmt = con.prepareStatement("select distinct SMAWLMSMHS from CIVITAS where ID_OBJ=?");
    			stmt.setLong(1, Long.valueOf(id_obj).longValue());
    			rs = stmt.executeQuery();
    			Vector v_smawl = new Vector();
    			ListIterator li_sm = v_smawl.listIterator();
    			while(rs.next()) {
    				li_sm.add(rs.getString("SMAWLMSMHS"));
    			}
    		
    			vf = new Vector();
    			ListIterator lif = vf.listIterator();
    			stmt = con.prepareStatement("select * from CIVITAS where ID_OBJ=? and SMAWLMSMHS=?");
    			li_sm = v_smawl.listIterator();
    			
    			while(li_sm.hasNext()) {
    				String smawl = (String)li_sm.next();
    				stmt.setLong(1, Long.valueOf(id_obj).longValue());
    				stmt.setString(2, smawl);
    				rs = stmt.executeQuery();
    				tt = 0;
    				while(rs.next()) {
    					tt++;
    				}	
    				lif.add(smawl+" "+tt);
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
    	return vf;
    }
    
    
    public Vector getListCivitas(String id_obj) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		if(con==null || con.isClosed()) {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from CIVITAS where ID_OBJ=? order by NMMHSMSMHS,SMAWLMSMHS");
    			stmt.setLong(1, Long.valueOf(id_obj).longValue());
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String npmhs = rs.getString("NPMHSMSMHS");
    				String nmmhs = rs.getString("NMMHSMSMHS");
    				String nimhs = rs.getString("NIMHSMSMHS");
    				String smawl = rs.getString("SMAWLMSMHS");
    				li.add(npmhs+" "+nimhs+" "+smawl+" "+nmmhs);
    			}	
    		}
    		else {
    			stmt = con.prepareStatement("select * from CIVITAS where ID_OBJ=? order by NMMHSMSMHS,SMAWLMSMHS");
    			stmt.setLong(1, Long.valueOf(id_obj).longValue());
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String npmhs = rs.getString("NPMHSMSMHS");
    				String nmmhs = rs.getString("NMMHSMSMHS");
    				String nimhs = rs.getString("NIMHSMSMHS");
    				String smawl = rs.getString("SMAWLMSMHS");
    				li.add(npmhs+" "+nimhs+" "+smawl+" "+nmmhs);
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
    	return v;
    }

    public Vector getListCivitas(String id_obj, String smawl) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		if(con==null || con.isClosed()) {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from CIVITAS where ID_OBJ=? and SMAWLMSMHS=? order by NMMHSMSMHS");
    			stmt.setLong(1, Long.valueOf(id_obj).longValue());
    			stmt.setString(2,smawl);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String npmhs = rs.getString("NPMHSMSMHS");
    				String nmmhs = rs.getString("NMMHSMSMHS");
    				String nimhs = rs.getString("NIMHSMSMHS");
    				li.add(npmhs+" "+nimhs+" "+smawl+" "+nmmhs);
    			}	
    		}
    		else {
    			stmt = con.prepareStatement("select * from CIVITAS where ID_OBJ=? and SMAWLMSMHS=? order by NMMHSMSMHS");
    			stmt.setLong(1, Long.valueOf(id_obj).longValue());
    			stmt.setString(2,smawl);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String npmhs = rs.getString("NPMHSMSMHS");
    				String nmmhs = rs.getString("NMMHSMSMHS");
    				String nimhs = rs.getString("NIMHSMSMHS");
    				li.add(npmhs+" "+nimhs+" "+smawl+" "+nmmhs);
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
    	return v;
    }
    
    public String getNmpst(String kdpst) {
    	String nmpst = null;
    	try {
    		if(con==null || con.isClosed()) {
    		
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select NMPSTMSPST from MSPST where KDPSTMSPST=?");
    			stmt.setString(1, kdpst);
    			rs = stmt.executeQuery();
    		//rs.next();
    			if(rs.next()) {
    				nmpst = rs.getString("NMPSTMSPST");
    			}
    			else {
    				nmpst = ""+kdpst;
    			}
    		
    		}
    		else {
    			stmt = con.prepareStatement("select NMPSTMSPST from MSPST where KDPSTMSPST=?");
    			stmt.setString(1, kdpst);
    			rs = stmt.executeQuery();
    		//rs.next();
    			if(rs.next()) {
    				nmpst = rs.getString("NMPSTMSPST");
    			}
    			else {
    				nmpst = ""+kdpst;
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
    	return nmpst;
    }
    
    public String getIndividualKurikulum(String kdpst, String npmhs) {
    	/*
    	 * fungsi ini menghasilkan idkur yang dipakai oleh npm terkait
    	 * return null kalo belum ditentukan idkur
    	 */
    	String idkurAng = null;
    	String idkurNpm = null;
    	String smawl = null;
    	String idkur = null;
    	String krklm = null;
    	try {
    		//get angkatan
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//stmt = con.prepareStatement("select * from CIVITAS where KDPSTMSMHS=? and NPMHSMSMHS=?");
			stmt = con.prepareStatement("select SMAWLMSMHS,KRKLMMSMHS from CIVITAS inner join EXT_CIVITAS on CIVITAS.NPMHSMSMHS=EXT_CIVITAS.NPMHSMSMHS where CIVITAS.KDPSTMSMHS=? and CIVITAS.NPMHSMSMHS=?");
			stmt.setString(1, kdpst);
			stmt.setString(2, npmhs);
			rs = stmt.executeQuery();
			if(rs.next()) {
				smawl = rs.getString("SMAWLMSMHS");
				krklm = rs.getString("KRKLMMSMHS");
			}
			
			/*
			 * cek bila di ext_civitas sudah ada tidak perlu cek di krklm
			 */
			if(!Checker.isStringNullOrEmpty(krklm)) {
				idkur = krklm;
			}
			else {
				//cek kurikulum
				stmt = con.prepareStatement("select * from KRKLM where KDPSTKRKLM=?");
				stmt.setString(1, kdpst);
				rs = stmt.executeQuery();
				boolean match = false;
			
				while(rs.next() && !match) {
					String tmpIdKur = rs.getString("IDKURKRKLM");
					String tokenTarget = rs.getString("TARGTKRKLM");
					if(tokenTarget!=null) {
						StringTokenizer st = new StringTokenizer(tokenTarget,",");
						boolean match2 = false;
						while(st.hasMoreTokens() && !match2) {
							String tmp = st.nextToken();
							if(tmp.equalsIgnoreCase(npmhs)) {
								idkurNpm = ""+tmpIdKur;
								match = true;
								match2= true;
							}
							else {
								if(tmp.equalsIgnoreCase(smawl)) {
									idkurAng = ""+tmpIdKur;
								}
							}
						}
						if(idkurNpm==null) {
							if(idkurAng!=null) {
								idkur = ""+idkurAng;
							}
						}
						else {
							idkur = ""+idkurNpm;
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
    	return idkur;
    }
    /*
     * depricated : versi baru ada tambahan mata kuliah kelulusan
     */
    public Vector getListMatakuliahDalamKurikulum(String kdpst,String idkur) {
    	/*
    	 * fungsi ini menghasilkan List seluruh matakuliah untuk kurikulum dengan idkur terkait
    	 * return empty vector bila kosong v.size()==0
    	 */
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	boolean idkurIsAnumber=true;
    	try {
    		Integer.valueOf(idkur).intValue();
    	}
    	catch(Exception e) {
    		idkurIsAnumber = false;
    	}
    	try {
    		if(idkurIsAnumber) {
    		//get angkatan
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from MAKUR inner join MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURMAKUR=? and KDPSTMAKUL=? order by SEMESMAKUR,KDKMKMAKUL");
    			stmt.setInt(1,Integer.valueOf(idkur).intValue());
    			stmt.setString(2,kdpst);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				int idkmk = rs.getInt("IDKMKMAKUL");
    				li.add(""+idkmk);
    				//System.out.println("idkmk kurikulum="+idkmk);
    				String kdkmk = rs.getString("KDKMKMAKUL");
    				li.add(kdkmk);
    				String nakmk = rs.getString("NAKMKMAKUL");
    				
    				li.add(nakmk);
    				//System.out.println("nakmk="+nakmk);
    				int skstm = rs.getInt("SKSTMMAKUL");
    				li.add(""+skstm);
    				int skspr = rs.getInt("SKSPRMAKUL");
    				li.add(""+skspr);
    				int skslp = rs.getInt("SKSLPMAKUL");
    				li.add(""+skslp);
    				String kdwpl = rs.getString("KDWPLMAKUL");
    				li.add(kdwpl);
    				String jenis = rs.getString("JENISMAKUL");
    				li.add(jenis);
    				String stkmk = rs.getString("STKMKMAKUL");
    				li.add(stkmk);
    				String nodos = rs.getString("NODOSMAKUL");
    				li.add(nodos);
    				String semes = rs.getString("SEMESMAKUR");
    				li.add(semes);
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
    	//Vector newVect = new Vector(new LinkedHashSet(v));
    	return v;
    }
    
    
    public Vector getListMatakuliahDalamKurikulum_v1(String kdpst,String idkur) {
    	/*
    	 * fungsi ini menghasilkan List seluruh matakuliah untuk kurikulum dengan idkur terkait
    	 * return empty vector bila kosong v.size()==0
    	 */
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	boolean idkurIsAnumber=true;
    	try {
    		Integer.valueOf(idkur).intValue();
    	}
    	catch(Exception e) {
    		idkurIsAnumber = false;
    	}
    	try {
    		if(idkurIsAnumber) {
    		//get angkatan
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from MAKUR inner join MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURMAKUR=? and KDPSTMAKUL=? order by SEMESMAKUR,KDKMKMAKUL");
    			stmt.setInt(1,Integer.valueOf(idkur).intValue());
    			stmt.setString(2,kdpst);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				int idkmk = rs.getInt("IDKMKMAKUL");
    				li.add(""+idkmk);
    				//System.out.println("idkmk kurikulum="+idkmk);
    				String kdkmk = rs.getString("KDKMKMAKUL");
    				li.add(kdkmk);
    				String nakmk = rs.getString("NAKMKMAKUL");
    				
    				li.add(nakmk);
    				//System.out.println("nakmk="+nakmk);
    				int skstm = rs.getInt("SKSTMMAKUL");
    				li.add(""+skstm);
    				int skspr = rs.getInt("SKSPRMAKUL");
    				li.add(""+skspr);
    				int skslp = rs.getInt("SKSLPMAKUL");
    				li.add(""+skslp);
    				String kdwpl = ""+rs.getString("KDWPLMAKUL");
    				li.add(kdwpl);
    				String jenis = ""+rs.getString("JENISMAKUL");
    				li.add(jenis);
    				String stkmk = ""+rs.getString("STKMKMAKUL");
    				li.add(stkmk);
    				String nodos = ""+rs.getString("NODOSMAKUL");
    				li.add(nodos);
    				String semes = ""+rs.getString("SEMESMAKUR");
    				li.add(semes);
    				String final_mk = ""+rs.getBoolean("FINAL_MK");
    				li.add(final_mk);
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
    	//Vector newVect = new Vector(new LinkedHashSet(v));
    	return v;
    }
    public Vector getListMatakuliahDalamKurikulum(String kdpst,String idkur,String mkSmsKe) {
    	/*
    	 * fungsi ini menghasilkan List seluruh matakuliah untuk kurikulum dengan idkur terkait
    	 * return empty vector bila kosong v.size()==0
    	 */
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	boolean idkurIsAnumber=true;
    	try {
    		Integer.valueOf(idkur).intValue();
    	}
    	catch(Exception e) {
    		idkurIsAnumber = false;
    	}
    	try {
    		if(idkurIsAnumber) {
    		//get angkatan
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from MAKUR inner join MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURMAKUR=? and KDPSTMAKUL=? and SEMESMAKUR like ? order by SEMESMAKUR,KDKMKMAKUL");
    			stmt.setInt(1,Integer.valueOf(idkur).intValue());
    			stmt.setString(2,kdpst);
    			stmt.setString(3,"%"+mkSmsKe+"%");
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				int idkmk = rs.getInt("IDKMKMAKUL");
    				li.add(""+idkmk);
    				//System.out.println("idkmk kurikulum="+idkmk);
    				String kdkmk = rs.getString("KDKMKMAKUL");
    				li.add(kdkmk);
    				String nakmk = rs.getString("NAKMKMAKUL");
    				li.add(nakmk);
    				//System.out.println("nakmk="+nakmk);
    				int skstm = rs.getInt("SKSTMMAKUL");
    				li.add(""+skstm);
    				int skspr = rs.getInt("SKSPRMAKUL");
    				li.add(""+skspr);
    				int skslp = rs.getInt("SKSLPMAKUL");
    				li.add(""+skslp);
    				String kdwpl = rs.getString("KDWPLMAKUL");
    				li.add(kdwpl);
    				String jenis = rs.getString("JENISMAKUL");
    				li.add(jenis);
    				String stkmk = rs.getString("STKMKMAKUL");
    				li.add(stkmk);
    				String nodos = rs.getString("NODOSMAKUL");
    				li.add(nodos);
    				String semes = rs.getString("SEMESMAKUR");
    				li.add(semes);
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
    	//Vector newVect = new Vector(new LinkedHashSet(v));
    	return v;
    }

    /*
     * depricated
     */
    public Vector getListMakulDalamClassPool(String thsms,String kdpst) {
    	/*
    	 * fungsi ini menghasilkan List seluruh matakuliah untuk kurikulum dengan idkur terkait
    	 * return empty vector bila kosong v.size()==0
    	 */
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		//get angkatan
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and KDPST=? and CANCELED=? order by IDKMKMAKUL,SHIFT,NORUT_KELAS_PARALEL");
    		stmt.setString(1,thsms);
    		stmt.setString(2,kdpst);
    		stmt.setBoolean(3, false);
    		rs = stmt.executeQuery();
    			while(rs.next()) {
    				int idkmk = rs.getInt("IDKMK");
    				//li.add(""+idkmk);
    				String shift = ""+rs.getString("SHIFT");
    				//li.add(shift);
    				//==================================================================
    				String norutKelasParalel = ""+rs.getString("NORUT_KELAS_PARALEL");
    				//li.add(norutKelasParalel);
    				String currStatus = ""+rs.getString("CURR_AVAIL_STATUS");
    				//li.add(currStatus);
    				String npmdos = ""+rs.getString("NPMDOS");
    				//li.add(npmdos);
    				String npmasdos = ""+rs.getString("NPMASDOS");
    				//li.add(npmasdos);
    				String canceled = ""+rs.getBoolean("CANCELED");
    				//li.add(canceled);
    				String kodeKelas = ""+rs.getString("KODE_KELAS");
    				//li.add(kodeKelas);
    				String kodeRuang = ""+rs.getString("KODE_RUANG");
    				//li.add(kodeRuang);
    				String kodeGedung = ""+rs.getString("KODE_GEDUNG");
    				//li.add(kodeGedung);
    				String kodeKampus = ""+rs.getString("KODE_KAMPUS");
    				//li.add(kodeKampus);
    				String tknDayTime = ""+rs.getString("TKN_HARI_TIME");
    				//li.add(tknDayTime);
    				String nmmdos = ""+rs.getString("NMMDOS");
    				//li.add(nmmdos);
    				String nmmasdos = ""+rs.getString("NMMASDOS");
    				//li.add(nmmasdos);
    				String enrolled = ""+rs.getString("ENROLLED");
    				//li.add(enrolled);
    				String maxEnrolled = ""+rs.getString("MAX_ENROLLED");
    				//li.add(maxEnrolled);
    				String minEnrolled = ""+rs.getString("MIN_ENROLLED");
    				//li.add(minEnrolled);
    				//====================================================================
    				String kdkmk = ""+rs.getString("KDKMKMAKUL");
    				//li.add(kdkmk);
    				String nakmk = ""+rs.getString("NAKMKMAKUL");
    				//li.add(nakmk);
    				int skstm = rs.getInt("SKSTMMAKUL");
    				//li.add(""+skstm);
    				int skspr = rs.getInt("SKSPRMAKUL");
    				//li.add(""+skspr);
    				int skslp = rs.getInt("SKSLPMAKUL");
    				//li.add(""+skslp);
    				String kdwpl = rs.getString("KDWPLMAKUL");
    				//li.add(kdwpl);
    				String jenis = rs.getString("JENISMAKUL");
    				//li.add(jenis);
    				String stkmk = rs.getString("STKMKMAKUL");
    				//li.add(stkmk);
    				String nodos = rs.getString("NODOSMAKUL");
    				//li.add(nodos);
    				//String semes = rs.getString("SEMESMAKUR");
    				//li.add(semes);
    				/*
    				 * rule2 tambahan
    				 */
    				if(!maxEnrolled.equalsIgnoreCase("null")) {
    					//ada batasan max jum mhs
    				}
    				//
    				li.add(""+idkmk);
    				li.add(shift);
    				li.add(norutKelasParalel);
    				li.add(currStatus);
    				li.add(npmdos);
    				li.add(npmasdos);
    				li.add(canceled);
    				li.add(kodeKelas);
    				li.add(kodeRuang);
    				li.add(kodeGedung);
    				li.add(kodeKampus);
    				li.add(tknDayTime);
    				li.add(nmmdos);
    				li.add(nmmasdos);
    				li.add(enrolled);
    				li.add(maxEnrolled);
    				li.add(minEnrolled);
    				//====================================================================
    				li.add(kdkmk);
    				li.add(nakmk);
    				li.add(""+skstm);
    				li.add(""+skspr);
    				li.add(""+skslp);
    				li.add(kdwpl);
    				li.add(jenis);
    				li.add(stkmk);
    				li.add(nodos);
    				
			}
    			
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			//System.out.println("cp.  "+brs);
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
    	//Vector newVect = new Vector(new LinkedHashSet(v));
    	return v;
    }

    public Vector getListMakulDalamClassPoolVer2(String thsms,String kdpst) {
    	/*
    	 * fungsi ini menghasilkan List seluruh matakuliah untuk kurikulum dengan idkur terkait
    	 * return empty vector bila kosong v.size()==0
    	 */
    	
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//get kelas gabungan
    		//1 get kode gabungan
    		Vector v0 = new Vector();
    		ListIterator li0 = v0.listIterator();
    		stmt = con.prepareStatement("select distinct KODE_PENGGABUNGAN from CLASS_POOL where THSMS=? and KDPST=? and KODE_PENGGABUNGAN IS NOT NULL");
    		stmt.setString(1,thsms);
    		stmt.setString(2,kdpst);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String kode = ""+rs.getString("KODE_PENGGABUNGAN");
    			li0.add(kode);
    		}
    		//2. get lidt kelas yg digabungkan
    		Vector vGab = new Vector();
    		ListIterator ligab = vGab.listIterator();
    		if(v0!=null && v0.size()>0) {
    			li0 = v0.listIterator();
    			stmt = con.prepareStatement("select * from CLASS_POOL where THSMS=? and KODE_PENGGABUNGAN=?");
    			while(li0.hasNext()) {
    				String kode = (String)li0.next();
    				stmt.setString(1,thsms);
    				stmt.setString(2,kode);
    	    		rs = stmt.executeQuery();
    	    		//isi masing variable selectif
    	    		String idkmk2=null,kdpst2=null,shift2=null,norutKelasParalel2=null,currStatus2=null,npmdos2=null,npmasdos2=null,canceled2=null,kodeKelas2=null;
    	    		String kodeRuang2=null,kodeGedung2=null,kodeKampus2=null,tknDayTime2=null,nmmdos2=null,nmmasdos2=null,enrolled2=null,maxEnrolled2=null,minEnrolled2=null;
    	    		boolean match1=false,match2=false;
    	    		while(rs.next()) {
    	    			String idkmktmp = ""+rs.getLong("IDKMK");
    	    			String kdpsttmp = ""+rs.getString("KDPST");
    	    			String shift = ""+rs.getString("SHIFT");
            			String norutKelasParalel = ""+rs.getString("NORUT_KELAS_PARALEL");
        				String currStatus = ""+rs.getString("CURR_AVAIL_STATUS");
            			
        				String npmdos = ""+rs.getString("NPMDOS");
        				String npmasdos = ""+rs.getString("NPMASDOS");
        				String canceled = ""+rs.getBoolean("CANCELED");
        				String kodeKelas = ""+rs.getString("KODE_KELAS");
        				String kodeRuang = ""+rs.getString("KODE_RUANG");
        				String kodeGedung = ""+rs.getString("KODE_GEDUNG");
        				String kodeKampus = ""+rs.getString("KODE_KAMPUS");
        				String tknDayTime = ""+rs.getString("TKN_HARI_TIME");
        				String nmmdos = ""+rs.getString("NMMDOS");
        				nmmdos = nmmdos.replace(",", "tandaKoma");
        				String nmmasdos = ""+rs.getString("NMMASDOS");
        				nmmasdos = nmmasdos.replace(",", "tandaKoma");
        				String enrolled = ""+rs.getString("ENROLLED");
        				String maxEnrolled = ""+rs.getString("MAX_ENROLLED");
        				String minEnrolled = ""+rs.getString("MIN_ENROLLED");
        				if(kdpsttmp.equalsIgnoreCase(kdpst)) {
        					match1=true;
        					idkmk2=""+idkmktmp;
        					kdpst2 = ""+kdpsttmp;
        				}
        				if(canceled.equalsIgnoreCase("false")) {
        					match2=true;
        					shift2 = ""+shift;
        					norutKelasParalel2 = ""+norutKelasParalel;
        					currStatus2 = ""+currStatus;
        					npmdos2=""+npmdos;
        					npmasdos2=""+npmasdos;
        					canceled2="false";
        					kodeKelas2=""+kodeKelas;
        					kodeRuang2 = ""+kodeRuang;
        					kodeGedung2 = ""+kodeGedung;
        					kodeKampus2 = ""+kodeKampus;
        					tknDayTime2 = ""+tknDayTime;
        					nmmdos2 = ""+nmmdos;
        					nmmasdos2 = ""+nmmasdos;
        					enrolled2 = ""+enrolled;
        					maxEnrolled2 = ""+maxEnrolled;
        					minEnrolled2 = ""+minEnrolled;
        				}
    	    		}
    	    		if(match1&&match2) {
    	    			ligab.add(idkmk2+","+kdpst2+","+shift2+","+norutKelasParalel2+","+currStatus2+","+npmdos2+","+npmasdos2+","+canceled2+","+kodeKelas2+","+kodeRuang2+","+kodeGedung2+","+kodeKampus2+","+tknDayTime2+","+nmmdos2+","+nmmasdos2+","+enrolled2+","+maxEnrolled2+","+minEnrolled2);
    	    			//System.out.println("nu="+idkmk2+","+kdpst2+","+shift2+","+norutKelasParalel2+","+currStatus2+","+npmdos2+","+npmasdos2+","+canceled2+","+kodeKelas2+","+kodeRuang2+","+kodeGedung2+","+kodeKampus2+","+tknDayTime2+","+nmmdos2+","+nmmasdos2+","+enrolled2+","+maxEnrolled2+","+minEnrolled2);
    	    		}	//System.out.println("adding"+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled);
            		//}
    			}
    		}
    		
    		
    		
    		//get kelas normal lainnya
    		stmt = con.prepareStatement("select distinct IDKMK from CLASS_POOL where THSMS=? and KDPST=? and CANCELED=?");
    		stmt.setString(1,thsms);
    		stmt.setString(2,kdpst);
    		stmt.setBoolean(3, false);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
				int idkmk = rs.getInt("IDKMK");
				li.add(""+idkmk);
    		}	
    		Vector v1 = new Vector();
    		ListIterator li1 = v1.listIterator();
    		stmt = con.prepareStatement("select distinct SHIFT from CLASS_POOL where IDKMK=? and THSMS=? and KDPST=? and CANCELED=?");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String idkmk = (String)li.next();
    			li1.add(idkmk);
    			Vector vtmp = new Vector();
    			ListIterator liTmp= vtmp.listIterator();
    			stmt.setLong(1,Long.valueOf(idkmk).longValue());
        		stmt.setString(2,thsms);
        		stmt.setString(3,kdpst);
        		stmt.setBoolean(4, false);
        		rs=stmt.executeQuery();
        		while(rs.next()) {
        			String shift = ""+rs.getString("SHIFT");
        			liTmp.add(shift);
        		}
        		li1.add(vtmp);
    		}
    		
    		    		
    		v = new Vector();
    		li = v.listIterator();
    		stmt = con.prepareStatement("select * from CLASS_POOL where IDKMK=? and THSMS=? and KDPST=? and CANCELED=? and SHIFT=?");
    		li1 = v1.listIterator();
    		while(li1.hasNext()) {
    			String idkmk = (String)li1.next();//kdkmk
    			//System.out.println("idkmk="+idkmk);
    			li.add("idkmk,"+idkmk);
    			Vector vShift = (Vector)li1.next();//vector list shift yg ada
    			ListIterator liShift = vShift.listIterator();
    			while(liShift.hasNext()) {
    				String shift = (String)liShift.next();
    				li.add("shift,"+shift);
    				//System.out.println("shift="+shift);
    				stmt.setLong(1,Long.valueOf(idkmk).longValue());
            		stmt.setString(2,thsms);
            		stmt.setString(3,kdpst);
            		stmt.setBoolean(4, false);
            		stmt.setString(5, shift);
            		rs = stmt.executeQuery();
            		Vector vtmp = new Vector();
        			ListIterator liTmp= vtmp.listIterator();
            		while(rs.next()) {
            			//yg match idkmk dan shift yg diinput ke vTmp
            			String norutKelasParalel = ""+rs.getString("NORUT_KELAS_PARALEL");
        				String currStatus = ""+rs.getString("CURR_AVAIL_STATUS");
            			//String currStatus = ""+rs.getString("LATEST_STATUS_INFO");
        				String npmdos = ""+rs.getString("NPMDOS");
        				String npmasdos = ""+rs.getString("NPMASDOS");
        				String canceled = ""+rs.getBoolean("CANCELED");
        				String kodeKelas = ""+rs.getString("KODE_KELAS");
        				String kodeRuang = ""+rs.getString("KODE_RUANG");
        				String kodeGedung = ""+rs.getString("KODE_GEDUNG");
        				String kodeKampus = ""+rs.getString("KODE_KAMPUS");
        				String tknDayTime = ""+rs.getString("TKN_HARI_TIME");
        				String nmmdos = ""+rs.getString("NMMDOS");
        				nmmdos = nmmdos.replace(",", "tandaKoma");
        				String nmmasdos = ""+rs.getString("NMMASDOS");
        				nmmasdos = nmmasdos.replace(",", "tandaKoma");
        				String enrolled = ""+rs.getString("ENROLLED");
        				String maxEnrolled = ""+rs.getString("MAX_ENROLLED");
        				String minEnrolled = ""+rs.getString("MIN_ENROLLED");
        				liTmp.add(shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled);
        				//gabung dengan vgab yg match
        				if(vGab!=null && vGab.size()>0) {
        					ligab = vGab.listIterator();
        					while(ligab.hasNext()) {
        						String brs = (String)ligab.next();
        						//ligab.add(idkmk2+","+kdpst2+","+shift2+","+norutKelasParalel2+","+currStatus2+","+npmdos2+","+npmasdos2+","+canceled2+","+kodeKelas2+","+kodeRuang2+","+kodeGedung2+","+kodeKampus2+","+tknDayTime2+","+nmmdos2+","+nmmasdos2+","+enrolled2+","+maxEnrolled2+","+minEnrolled2);
        						StringTokenizer st = new StringTokenizer(brs,",");
        						String idkmk2 = st.nextToken();
        						String kdpst2 = st.nextToken();
        						String shift2 = st.nextToken();
        						String norutKelasParalel2 = st.nextToken();
        						String currStatus2 = st.nextToken();
        						String npmdos2 = st.nextToken();
        						String npmasdos2 = st.nextToken();
        						String canceled2 = st.nextToken();
        						String kodeKelas2 = st.nextToken();
        						String kodeRuang2 = st.nextToken();
        						String kodeGedung2 = st.nextToken();
        						String kodeKampus2 = st.nextToken();
        						String tknDayTime2 = st.nextToken();
        						String nmmdos2 = st.nextToken();
        						String nmmasdos2 = st.nextToken();
        						String enrolled2 = st.nextToken();
        						String maxEnrolled2 = st.nextToken();
        						String minEnrolled2 = st.nextToken();
        						if(idkmk2.equalsIgnoreCase(idkmk) && shift2.equalsIgnoreCase(shift)) {
        							//System.out.println("matching");
        							liTmp.add(shift2+","+norutKelasParalel2+","+currStatus2+","+npmdos2+","+npmasdos2+","+canceled2+","+kodeKelas2+","+kodeRuang2+","+kodeGedung2+","+kodeKampus2+","+tknDayTime2+","+nmmdos2+","+nmmasdos2+","+enrolled2+","+maxEnrolled2+","+minEnrolled2);
        							ligab.remove(); // sisanya berarti ngga aada yg match, yg dibuka fakultas laen
        						}
        					}
        				}
            		}
            		//gabung dgn vgab
            		vtmp = Tool.removeDuplicateFromVector(vtmp);
            		//====end gabung dengan vgab yg match
            		li.add(vtmp);
            		//System.out.println("# "+idkmk+" "+shift+" "+vtmp.size());
    			}
    		}	
    		//System.out.println("gabungan");
    			//====add gabung dengan vgab yg match tambahan=======
    			
    		if(vGab!=null && vGab.size()>0) {
    			//System.out.println("vGab Size2 = "+vGab.size());
    			//Vector vtmp = new Vector();
        		//ListIterator liTmp= vtmp.listIterator();
    			ligab = vGab.listIterator();
				while(ligab.hasNext()) {
					String brs = (String)ligab.next();
					Vector vtmp = new Vector();
	        		ListIterator liTmp= vtmp.listIterator();
					//System.out.println("gab="+brs);
						//ligab.add(idkmk2+","+kdpst2+","+shift2+","+norutKelasParalel2+","+currStatus2+","+npmdos2+","+npmasdos2+","+canceled2+","+kodeKelas2+","+kodeRuang2+","+kodeGedung2+","+kodeKampus2+","+tknDayTime2+","+nmmdos2+","+nmmasdos2+","+enrolled2+","+maxEnrolled2+","+minEnrolled2);
					StringTokenizer st = new StringTokenizer(brs,",");
					String idkmk2 = st.nextToken();
					String kdpst2 = st.nextToken();
					String shift2 = st.nextToken();
					li.add("idkmk,"+idkmk2);
					li.add("shift,"+shift2);
					String norutKelasParalel2 = st.nextToken();
					String currStatus2 = st.nextToken();
					String npmdos2 = st.nextToken();
					String npmasdos2 = st.nextToken();
					String canceled2 = st.nextToken();
					String kodeKelas2 = st.nextToken();
					String kodeRuang2 = st.nextToken();
					String kodeGedung2 = st.nextToken();
					String kodeKampus2 = st.nextToken();
					String tknDayTime2 = st.nextToken();
					String nmmdos2 = st.nextToken();
					String nmmasdos2 = st.nextToken();
					String enrolled2 = st.nextToken();
					String maxEnrolled2 = st.nextToken();
					String minEnrolled2 = st.nextToken();
					liTmp.add(shift2+","+norutKelasParalel2+","+currStatus2+","+npmdos2+","+npmasdos2+","+canceled2+","+kodeKelas2+","+kodeRuang2+","+kodeGedung2+","+kodeKampus2+","+tknDayTime2+","+nmmdos2+","+nmmasdos2+","+enrolled2+","+maxEnrolled2+","+minEnrolled2);
					li.add(vtmp);
				}
				//li.add(vtmp);
    		}
    			//====add gabung dengan vgab yg match tambahan======= 			
       	}
    	catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch (Exception e) {
    		//System.out.println("error removeDuplicateFromVector");
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	//Vector newVect = new Vector(new LinkedHashSet(v));
    	return v;
    }
    
    
    
    
    public Vector getListMakulDalamClassPoolForRequestBukaKelas(String thsmsPmb,String kdpst) {
    	/*
    	 * 
    	 */
    	
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		//get angkatan
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		stmt = con.prepareStatement("select distinct IDKMK from CLASS_POOL where THSMS=? and KDPST=? and CANCELED=?");
    		stmt.setString(1,thsmsPmb);
    		stmt.setString(2,kdpst);
    		stmt.setBoolean(3, false);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
				int idkmk = rs.getInt("IDKMK");
				li.add(""+idkmk);
    		}	
    		Vector v1 = new Vector();
    		ListIterator li1 = v1.listIterator();
    		stmt = con.prepareStatement("select distinct SHIFT from CLASS_POOL where IDKMK=? and THSMS=? and KDPST=? and CANCELED=?");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String idkmk = (String)li.next();
    			li1.add(idkmk);
    			Vector vtmp = new Vector();
    			ListIterator liTmp= vtmp.listIterator();
    			stmt.setLong(1,Long.valueOf(idkmk).longValue());
        		stmt.setString(2,thsmsPmb);
        		stmt.setString(3,kdpst);
        		stmt.setBoolean(4, false);
        		rs=stmt.executeQuery();
        		while(rs.next()) {
        			String shift = ""+rs.getString("SHIFT");
        			liTmp.add(shift);
        		}
        		li1.add(vtmp);
    		}
    		
    		    		
    		v = new Vector();
    		li = v.listIterator();
    		stmt = con.prepareStatement("select * from CLASS_POOL where IDKMK=? and THSMS=? and KDPST=? and CANCELED=? and SHIFT=?");
    		li1 = v1.listIterator();
    		while(li1.hasNext()) {
    			String idkmk = (String)li1.next();
    			//System.out.println("idkmk="+idkmk);
    			li.add("idkmk,"+idkmk);
    			Vector vShift = (Vector)li1.next();
    			ListIterator liShift = vShift.listIterator();
    			while(liShift.hasNext()) {
    				String shift = (String)liShift.next();
    				li.add("shift,"+shift);
    				//System.out.println("shift="+shift);
    				stmt.setLong(1,Long.valueOf(idkmk).longValue());
            		stmt.setString(2,thsmsPmb);
            		stmt.setString(3,kdpst);
            		stmt.setBoolean(4, false);
            		stmt.setString(5, shift);
            		rs = stmt.executeQuery();
            		Vector vtmp = new Vector();
        			ListIterator liTmp= vtmp.listIterator();
            		while(rs.next()) {
            			String norutKelasParalel = ""+rs.getString("NORUT_KELAS_PARALEL");
        				String currStatus = ""+rs.getString("CURR_AVAIL_STATUS");
        				String npmdos = ""+rs.getString("NPMDOS");
        				String npmasdos = ""+rs.getString("NPMASDOS");
        				String canceled = ""+rs.getBoolean("CANCELED");
        				String kodeKelas = ""+rs.getString("KODE_KELAS");
        				String kodeRuang = ""+rs.getString("KODE_RUANG");
        				String kodeGedung = ""+rs.getString("KODE_GEDUNG");
        				String kodeKampus = ""+rs.getString("KODE_KAMPUS");
        				String tknDayTime = ""+rs.getString("TKN_HARI_TIME");
        				String nmmdos = ""+rs.getString("NMMDOS");
        				String nmmasdos = ""+rs.getString("NMMASDOS");
        				String enrolled = ""+rs.getString("ENROLLED");
        				String maxEnrolled = ""+rs.getString("MAX_ENROLLED");
        				String minEnrolled = ""+rs.getString("MIN_ENROLLED");
        				liTmp.add(shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled);
        				//System.out.println(norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled);
            		}
            		li.add(vtmp);
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
    	//Vector newVect = new Vector(new LinkedHashSet(v));
    	return v;
    }    
    
    
    public Vector getListMatakuliahTrnlp(String kdpst,String npmhs) {
    	/*
    	 * fungsi ini menghasilkan list matakuliah transfered (trnlp) untuk npmhs terkait
    	 */
    	//cek
    	SearchDb sdb = new SearchDb();
    	String idkur = sdb.getIndividualKurikulum(kdpst, npmhs);
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	Vector v1 = new Vector();
    	ListIterator li1 = v1.listIterator();
    	int totSksTransfer = 0;
    	try {
    		//get angkatan
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from TRNLP where KDPSTTRNLP=? and NPMHSTRNLP=? and TRANSFERRED=?");
			stmt.setString(1,kdpst);
			stmt.setString(2,npmhs);
			stmt.setBoolean(3, true);
			rs = stmt.executeQuery();
			while(rs.next()) {
				String thsms = rs.getString("THSMSTRNLP");
				li.add(thsms);
				String kdkmk = rs.getString("KDKMKTRNLP");
				li.add(kdkmk);
				//String nakmk = rs.getString("NAKMKMAKUL");
				//li.add(nakmk);
				String nlakh = rs.getString("NLAKHTRNLP");
				li.add(nlakh);
				String bobot = ""+rs.getDouble("BOBOTTRNLP");
				li.add(bobot);
				String kdasl = rs.getString("KDKMKASALP");
				li.add(kdasl);
				String nmasl = rs.getString("NAKMKASALP");
				li.add(nmasl);
				String keter = rs.getString("KETERTRNLP");
				li.add(keter);
				int sksmk = rs.getInt("SKSMKTRNLP");
				li.add(""+sksmk);
				totSksTransfer = totSksTransfer+sksmk;
				li.add(""+totSksTransfer);
				int sksas = rs.getInt("SKSMKASAL");
				li.add(""+sksas);
				String transferred = ""+rs.getBoolean("TRANSFERRED");
				li.add(""+transferred);


			}
			
			stmt = con.prepareStatement("select * from MAKUL inner join MAKUR on IDKMKMAKUL=IDKMKMAKUR where IDKURMAKUR=? and KDPSTMAKUL=? and KDKMKMAKUL=?");
			li = v.listIterator();
			while(li.hasNext()) {
				String thsms = (String)li.next();
				String kdkmk = (String)li.next();
				String nlakh = (String)li.next();
				String bobot = (String)li.next();
				String kdasl = (String)li.next();
				String nmasl = (String)li.next();
				String keter = (String)li.next();
				String sksmk = (String)li.next();
				String totSksTransfered = (String)li.next();
				String sksas = (String)li.next();
				String transferred = (String)li.next();
				
				stmt.setInt(1,Integer.valueOf(idkur).intValue());
				stmt.setString(2, kdpst);
				stmt.setString(3, kdkmk);
				rs = stmt.executeQuery();
				String nakmk = "KOKE MATAKULIAH TIDAK ADA DIKURIKULUM,<BR/> HARAP DEPERIKSA KEMBALI";
				if(rs.next()) {
					nakmk = rs.getString("NAKMKMAKUL");
					//skstm = rs.getInt("SKSTMMAKUL");
					//li.add(""+skstm);
					//skspr = rs.getInt("SKSPRMAKUL");
					//li.add(""+skspr);
					//skslp = rs.getInt("SKSLPMAKUL");
					//li.add(""+skslp);
					//li.add(""+(skstm+skspr+skslp));
					//totSksTransfer = totSksTransfer +(skstm+skspr+skslp);
				}
				li1.add(thsms);
				li1.add(kdkmk);
				li1.add(nakmk);
				li1.add(nlakh);
				li1.add(bobot);
				li1.add(kdasl);
				li1.add(nmasl);
				li1.add(""+(sksmk));
				li1.add(totSksTransfered+"");
				li1.add(sksas);
				li1.add(transferred);
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
    	return v1;
    }

    
    public Vector getListMatakuliahPtAsal(String kdpst,String npmhs) {
    	/*
    	 * fungsi ini menghasilkan list matakuliah transfered (trnlp) untuk npmhs terkait
    	 */
    	//cek
    	SearchDb sdb = new SearchDb();
    	String idkur = sdb.getIndividualKurikulum(kdpst, npmhs);
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	Vector v1 = new Vector();
    	ListIterator li1 = v1.listIterator();
    	int totSksTransfer = 0;
    	try {
    		//get angkatan
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from TRNLP where KDPSTTRNLP=? and NPMHSTRNLP=? order by NAKMKASALP");
			stmt.setString(1,kdpst);
			stmt.setString(2,npmhs);
			rs = stmt.executeQuery();
			while(rs.next()) {
				String thsms = rs.getString("THSMSTRNLP");
				li.add(thsms);
				String kdkmk = rs.getString("KDKMKTRNLP");
				li.add(kdkmk);
				//String nakmk = rs.getString("NAKMKMAKUL");
				//li.add(nakmk);
				String nlakh = rs.getString("NLAKHTRNLP");
				li.add(nlakh);
				String bobot = ""+rs.getDouble("BOBOTTRNLP");
				li.add(bobot);
				String kdasl = rs.getString("KDKMKASALP");
				li.add(kdasl);
				String nmasl = rs.getString("NAKMKASALP");
				li.add(nmasl);
				String keter = rs.getString("KETERTRNLP");
				li.add(keter);
				int sksmk = rs.getInt("SKSMKTRNLP");
				li.add(""+sksmk);
				totSksTransfer = totSksTransfer+sksmk;
				li.add(""+totSksTransfer);
				int sksas = rs.getInt("SKSMKASAL");
				li.add(""+sksas);
				String transferred = ""+rs.getBoolean("TRANSFERRED");
				li.add(""+transferred);


			}
			
			stmt = con.prepareStatement("select * from MAKUL inner join MAKUR on IDKMKMAKUL=IDKMKMAKUR where IDKURMAKUR=? and KDPSTMAKUL=? and KDKMKMAKUL=?");
			li = v.listIterator();
			while(li.hasNext()) {
				String thsms = (String)li.next();
				String kdkmk = (String)li.next();
				String nlakh = (String)li.next();
				String bobot = (String)li.next();
				String kdasl = (String)li.next();
				String nmasl = (String)li.next();
				String keter = (String)li.next();
				String sksmk = (String)li.next();
				String totSksTransfered = (String)li.next();
				String sksas = (String)li.next();
				String transferred = (String)li.next();
				
				stmt.setInt(1,Integer.valueOf(idkur).intValue());
				stmt.setString(2, kdpst);
				stmt.setString(3, kdkmk);
				rs = stmt.executeQuery();
				String nakmk = "KOKE MATAKULIAH TIDAK ADA DIKURIKULUM,<BR/> HARAP DEPERIKSA KEMBALI";
				if(rs.next()) {
					nakmk = rs.getString("NAKMKMAKUL");
					//skstm = rs.getInt("SKSTMMAKUL");
					//li.add(""+skstm);
					//skspr = rs.getInt("SKSPRMAKUL");
					//li.add(""+skspr);
					//skslp = rs.getInt("SKSLPMAKUL");
					//li.add(""+skslp);
					//li.add(""+(skstm+skspr+skslp));
					//totSksTransfer = totSksTransfer +(skstm+skspr+skslp);
				}
				li1.add(thsms);
				li1.add(kdkmk);
				li1.add(nakmk);
				li1.add(nlakh);
				li1.add(bobot);
				li1.add(kdasl);
				li1.add(nmasl);
				li1.add(""+(sksmk));
				li1.add(totSksTransfered+"");
				li1.add(sksas);
				li1.add(transferred);
				//System.out.println("sdb = "+kdkmk+","+nakmk+","+kdasl+","+nmasl);
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
    	return v1;
    }
    
    /*
     * depricated
    public Vector getListMatakuliahTrnlm(String kdpst,String npmhs) {
    	 * fungsi ini menghasilkan list krs khs
    	 */
        /*
    	SearchDb sdb = new SearchDb();
    	String idkur = sdb.getIndividualKurikulum(kdpst, npmhs);
    	//System.out.println("start get krs");
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		//get angkatan
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from TRNLM where KDPSTTRNLM=? and NPMHSTRNLM=? order by THSMSTRNLM");
			
			//stmt = con.prepareStatement("select * from TRNLM inner join MAKUL on KDKMKTRNLM=KDKMKMAKUL where KDPSTTRNLM=? and NPMHSTRNLM=? order by THSMSTRNLM");
			stmt.setString(1,kdpst);
			stmt.setString(2,npmhs);
			rs = stmt.executeQuery();
			while(rs.next()) {
				//System.out.println("yes, rs.next()");
				String thsms = rs.getString("THSMSTRNLM");
				//li.add(thsms);
				String kdkmk = rs.getString("KDKMKTRNLM");
				//li.add(kdkmk);
				//String nakmk = rs.getString("NAKMKMAKUL");
				//li.add(nakmk);
				String nlakh = rs.getString("NLAKHTRNLM");
				//li.add(nlakh);
				String bobot = ""+rs.getDouble("BOBOTTRNLM");
				String sksmk = ""+rs.getInt("SKSMKTRNLM");
				//li.add(bobot);
				String kelas = rs.getString("KELASTRNLM");
				//li.add(kelas);
				li.add(thsms+","+kdkmk+","+nlakh+","+bobot+","+sksmk+","+kelas);
			}
			Collections.sort(v);
		}
    	catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (st!=null) try  { st.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	//System.out.println("cSize="+v.size());
    	return v;
    }
*/
    public String getCurrrentPa(String kdpst, String npmhs) {
    	String infoPa = null;
    	try {
    		//get angkatan
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt=con.prepareStatement("select * from EXT_CIVITAS where KDPSTMSMHS=? and NPMHSMSMHS=?");
			stmt.setString(1, kdpst);
			stmt.setString(2, npmhs);
			rs = stmt.executeQuery();
			if(rs.next()) {
				String npmPa = rs.getString("NPM_PA");
				String nmmPa = rs.getString("NMM_PA");
				infoPa = npmPa+","+nmmPa;
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
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return infoPa;
    }
    
    
    public String getHistoryNpmPa(String kdpst, String npmhs) {
    	//System.out.println("getHistoryKrsKhs start");
    	
    	Vector v = new Vector();
    	Vector vf = new Vector();
    	String tkn_npm_pa = "";
    	ListIterator li = v.listIterator();
    	try {
    		//get angkatan
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt=con.prepareStatement("select distinct THSMSTRNLM from TRNLM where KDPSTTRNLM=? and NPMHSTRNLM=?");
			stmt.setString(1, kdpst);
			stmt.setString(2, npmhs);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				String thsms = rs.getString("THSMSTRNLM");
				li.add(thsms);
			}
			//get info pa
			stmt = con.prepareStatement("select * from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=?");
			String npmPa="null";
			String nmmPa="null";
			
			li = v.listIterator();
			while(li.hasNext()) {
				String thsms = (String)li.next();
				stmt.setString(1,thsms);
				stmt.setString(2, npmhs);
				rs = stmt.executeQuery();
				if(rs.next()) {
					npmPa=""+rs.getString("NPM_PA");
				}
				tkn_npm_pa = tkn_npm_pa+thsms+","+npmPa;
				if(li.hasNext()) {
					tkn_npm_pa = tkn_npm_pa+"#";
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
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
		}	
    	return tkn_npm_pa;
    }
    	
    /*
     * depricated _ gunakan getHistoryKrsKhs_v1 (ada tambahan cuid)
     * SAAT INI DIPAKAI UNTUK VIEW KRS & CETAK KRS 
     * YG V1 DIPAKAI UNTUK INSERT KRS
     */
    public Vector getHistoryKrsKhs(String kdpst, String npmhs, String idkur) {
    	//System.out.println("getHistoryKrsKhs start");
    	/*
    	 * calc nilai trakm dulu !!!
    	 */
    	updateIndividualTrakm(kdpst, npmhs);
    	Vector v = new Vector();
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	ListIterator li = v.listIterator();
    	try {
    		//get angkatan
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt=con.prepareStatement("select * from TRNLM inner join TRAKM on (THSMSTRNLM=THSMSTRAKM AND NPMHSTRNLM=NPMHSTRAKM) where KDPSTTRNLM=? and NPMHSTRNLM=? order by THSMSTRNLM");
			stmt.setString(1, kdpst);
			stmt.setString(2, npmhs);
			rs = stmt.executeQuery();
			while(rs.next()) {
				String thsms = rs.getString("THSMSTRNLM");
				String kdkmk = rs.getString("KDKMKTRNLM");
				String nlakh = rs.getString("NLAKHTRNLM");
				String bobot =""+rs.getDouble("BOBOTTRNLM");
				String sksmk =""+rs.getInt("SKSMKTRNLM");
				String kelas = rs.getString("KELASTRNLM");
				String shift = ""+rs.getString("SHIFTTRNLM");
				String sksem = ""+rs.getInt("SKSEMTRAKM");
				String nlips = ""+rs.getDouble("NLIPSTRAKM");
				String skstt = ""+rs.getInt("SKSTTTRAKM");
				String nlipk = ""+rs.getDouble("NLIPKTRAKM");
				String krsdown = ""+rs.getBoolean("KRSDONWLOADED");
				String khsdown = ""+rs.getBoolean("KHSDONWLOADED");
				String bakprove = ""+rs.getBoolean("BAK_APPROVAL");
				String paprove = ""+rs.getBoolean("PA_APPROVAL");
				String note = ""+rs.getString("NOTE");
				String lock = ""+rs.getBoolean("LOCKTRNLM");
				String baukprove = ""+rs.getBoolean("BAUK_APPROVAL");
				//tambahan
				//Krs & KhsDownload.jaba butuh diupdate
				String idkmk = ""+rs.getLong("IDKMKTRNLM");
				String addReq = ""+rs.getBoolean("ADD_REQ");
				String drpReq  = ""+rs.getBoolean("DRP_REQ");
				String npmPa = ""+rs.getString("NPM_PA");
				String npmBak = ""+rs.getString("NPM_BAK");
				String npmBaa = ""+rs.getString("NPM_BAA");
				String npmBauk = ""+rs.getString("NPM_BAUK");
				String baaProve = ""+rs.getBoolean("BAA_APPROVAL");
				String ktuProve = ""+rs.getBoolean("KTU_APPROVAL");
				String dknProve = ""+rs.getBoolean("DEKAN_APPROVAL");
				String npmKtu = ""+rs.getString("NPM_KTU");
				String npmDekan = ""+rs.getString("NPM_DEKAN");
				String lockMhs = ""+rs.getBoolean("LOCKMHS");
				String kodeKampus = ""+rs.getString("KODE_KAMPUS");
				//System.out.println(thsms+","+kdkmk);
				//lif.add(thsms+","+kdkmk+","+nlakh+","+bobot+","+sksmk+","+kelas+","+sksem+","+nlips+","+skstt+","+nlipk);
				li.add(thsms);
				li.add(kdkmk);
				li.add(nlakh);
				li.add(bobot);
				li.add(sksmk);
				li.add(kelas);
				li.add(sksem);
				li.add(nlips);
				li.add(skstt);
				li.add(nlipk);
				li.add(shift);//tambahan baru
				li.add(krsdown);//tambahan baru
				li.add(khsdown);//tambahan baru
				li.add(bakprove);//tambahan baru
				li.add(paprove);//tambahan baru
				li.add(note);//tambahan baru
				li.add(lock);//tambahan baru
				li.add(baukprove);//tambahan baru
				//tambahan
				li.add(idkmk);
				li.add(addReq);
				li.add(drpReq);
				li.add(npmPa);
				li.add(npmBak);
				li.add(npmBaa);
				li.add(npmBauk);
				li.add(baaProve);
				li.add(ktuProve);
				li.add(dknProve);
				li.add(npmKtu);
				li.add(npmDekan);
				li.add(lockMhs);
				li.add(kodeKampus);
			}
			/*
			//System.out.println("vf");
			if(vf!=null && vf.size()>0) {
				//System.out.println("vf size = "+vf.size());
				vf = Tool.removeDuplicateFromVector(vf);
				//System.out.println("vf1 size = "+vf.size());
				lif = vf.listIterator();
				while(lif.hasNext()) {
					String baris = (String)lif.next();
					StringTokenizer st = new StringTokenizer(baris,",");
					String thsms = st.nextToken();
					String kdkmk = st.nextToken();
					String nlakh = st.nextToken();
					String bobot = st.nextToken();
					String sksmk = st.nextToken();
					String kelas = st.nextToken();
					String sksem = st.nextToken();
					String nlips = st.nextToken();
					String skstt = st.nextToken();
					String nlipk = st.nextToken();
					li.add(thsms);
					li.add(kdkmk);
					li.add(nlakh);
					li.add(bobot);
					li.add(sksmk);
					li.add(kelas);
					li.add(sksem);
					li.add(nlips);
					li.add(skstt);
					li.add(nlipk);
				}	
			}
			*/
			
			//System.out.println("v- size = "+v.size());
			/*
			 * deprecated cari nakmk tidak dari kurikulum, pastikan kdkmk utk 1 kdpst tidak boleh sama
			 */
			//stmt = con.prepareStatement("select * from MAKUL inner join MAKUR on (IDKMKMAKUL=IDKMKMAKUR) where KDKMKMAKUL=? and IDKURMAKUR=?");
			/*
			 * cara baru nakmk cukup berdasarkan kdpst dan kdkmk (jadi sepertti primary key)
			 */
			/*
			 * update lagi: utk nama mk agar akurat
			 */
			stmt = con.prepareStatement("select KRKLMMSMHS from EXT_CIVITAS WHERE KDPSTMSMHS=? and NPMHSMSMHS=?");
			stmt.setString(1, kdpst);
			stmt.setString(2, npmhs);
			rs = stmt.executeQuery();
			if(rs.next()) {
				String idKurMhs = ""+rs.getString("KRKLMMSMHS");
				if(Checker.isStringNullOrEmpty(idKurMhs)||idKurMhs.equalsIgnoreCase("0")) {
					//kalo null berarti sama kaaya dibawah
					stmt = con.prepareStatement("select * from MAKUL WHERE KDPSTMAKUL=? and  KDKMKMAKUL=?");
					li = v.listIterator();
					while(li.hasNext()) {
						String thsms=(String)li.next();
						String kdkmk=(String)li.next();
						String nlakh=(String)li.next();
						String bobot=(String)li.next();
						String sksmk=(String)li.next();
						String kelas=(String)li.next();
						String sksem=(String)li.next();
						String nlips=(String)li.next();
						String skstt=(String)li.next();
						String nlipk=(String)li.next();
						String shift=(String)li.next();
						String krsdown=(String)li.next();;//tambahan baru
						String khsdown=(String)li.next();;//tambahan baru
						String bakprove=(String)li.next();;//tambahan baru
						String paprove=(String)li.next();;//tambahan baru
						String note=(String)li.next();;//tambahan baru
						String lock=(String)li.next();;//tambahan baru
						String baukprove=(String)li.next();;//tambahan baru
						//tambahan
						String idkmk =(String)li.next();;//tambahan baru
						String addReq =(String)li.next();;//tambahan baru
						String drpReq  =(String)li.next();;//tambahan baru
						String npmPa =(String)li.next();;//tambahan baru
						String npmBak =(String)li.next();;//tambahan baru
						String npmBaa =(String)li.next();;//tambahan baru
						String npmBauk =(String)li.next();;//tambahan baru
						String baaProve =(String)li.next();;//tambahan baru
						String ktuProve =(String)li.next();;//tambahan baru
						String dknProve =(String)li.next();;//tambahan baru
						String npmKtu =(String)li.next();;//tambahan baru
						String npmDekan =(String)li.next();;//tambahan baru
						String lockMhs =(String)li.next();;//tambahan baru
						String kodeKampus =(String)li.next();;//tambahan baru
						//stmt.setString(1,kdkmk);
						//stmt.setInt(2,Integer.valueOf(idkur).intValue());
						stmt.setString(1, kdpst);
						stmt.setString(2, kdkmk);
						rs = stmt.executeQuery();
						String nakmk = "KODE MATAKULIAH TIDAK ADA DI KURIKULUM,<br/> HARAP DICEK KEMBALI";
						if(rs.next()) {
							nakmk = rs.getString("NAKMKMAKUL");
						}
						lif.add(thsms);
						lif.add(kdkmk);
						lif.add(nakmk);
						lif.add(nlakh);
						lif.add(bobot);
						lif.add(sksmk);
						lif.add(kelas);
						lif.add(sksem);
						lif.add(nlips);
						lif.add(skstt);
						lif.add(nlipk);	
						lif.add(shift);	
						lif.add(krsdown);//tambahan baru
						lif.add(khsdown);//tambahan baru
						lif.add(bakprove);//tambahan baru
						lif.add(paprove);//tambahan baru
						lif.add(note);//tambahan baru
						lif.add(lock);//tambahan baru
						lif.add(baukprove);//tambahan baru
						//tambahan
						lif.add(idkmk);
						lif.add(addReq);
						lif.add(drpReq);
						lif.add(npmPa);
						lif.add(npmBak);
						lif.add(npmBaa);
						lif.add(npmBauk);
						lif.add(baaProve);
						lif.add(ktuProve);
						lif.add(dknProve);
						lif.add(npmKtu);
						lif.add(npmDekan);
						lif.add(lockMhs);
						lif.add(kodeKampus);
					}
				}
				else {
					//get
					//System.out.println("yang ini");
					stmt = con.prepareStatement("select * from MAKUL inner join MAKUR on (IDKMKMAKUL=IDKMKMAKUR) where KDKMKMAKUL=? and IDKURMAKUR=?");

					li = v.listIterator();
					while(li.hasNext()) {
						String thsms=(String)li.next();
						String kdkmk=(String)li.next();
						String nlakh=(String)li.next();
						String bobot=(String)li.next();
						String sksmk=(String)li.next();
						String kelas=(String)li.next();
						String sksem=(String)li.next();
						String nlips=(String)li.next();
						String skstt=(String)li.next();
						String nlipk=(String)li.next();
						String shift=(String)li.next();
						String krsdown=(String)li.next();;//tambahan baru
						String khsdown=(String)li.next();;//tambahan baru
						String bakprove=(String)li.next();;//tambahan baru
						String paprove=(String)li.next();;//tambahan baru
						String note=(String)li.next();;//tambahan baru
						String lock=(String)li.next();;//tambahan baru
						String baukprove=(String)li.next();;//tambahan baru
						//tambahan
						String idkmk =(String)li.next();;//tambahan baru
						String addReq =(String)li.next();;//tambahan baru
						String drpReq  =(String)li.next();;//tambahan baru
						String npmPa =(String)li.next();;//tambahan baru
						String npmBak =(String)li.next();;//tambahan baru
						String npmBaa =(String)li.next();;//tambahan baru
						String npmBauk =(String)li.next();;//tambahan baru
						String baaProve =(String)li.next();;//tambahan baru
						String ktuProve =(String)li.next();;//tambahan baru
						String dknProve =(String)li.next();;//tambahan baru
						String npmKtu =(String)li.next();;//tambahan baru
						String npmDekan =(String)li.next();;//tambahan baru
						String lockMhs =(String)li.next();;//tambahan baru
						String kodeKampus =(String)li.next();;//tambahan baru
						
						stmt.setString(1, kdkmk);
						stmt.setInt(2, Integer.parseInt(idKurMhs));
						rs = stmt.executeQuery();
						String nakmk = "KODE MATAKULIAH TIDAK ADA DI KURIKULUM,<br/> HARAP DICEK KEMBALI";
						if(rs.next()) {
							nakmk = rs.getString("NAKMKMAKUL");
						}
						lif.add(thsms);
						lif.add(kdkmk);
						lif.add(nakmk);
						lif.add(nlakh);
						lif.add(bobot);
						lif.add(sksmk);
						lif.add(kelas);
						lif.add(sksem);
						lif.add(nlips);
						lif.add(skstt);
						lif.add(nlipk);	
						lif.add(shift);	
						lif.add(krsdown);//tambahan baru
						lif.add(khsdown);//tambahan baru
						lif.add(bakprove);//tambahan baru
						lif.add(paprove);//tambahan baru
						lif.add(note);//tambahan baru
						lif.add(lock);//tambahan baru
						lif.add(baukprove);//tambahan baru
						//tambahan
						lif.add(idkmk);
						lif.add(addReq);
						lif.add(drpReq);
						lif.add(npmPa);
						lif.add(npmBak);
						lif.add(npmBaa);
						lif.add(npmBauk);
						lif.add(baaProve);
						lif.add(ktuProve);
						lif.add(dknProve);
						lif.add(npmKtu);
						lif.add(npmDekan);
						lif.add(lockMhs);
						lif.add(kodeKampus);
					}	
				}
			}
			else {
				stmt = con.prepareStatement("select * from MAKUL WHERE KDPSTMAKUL=? and  KDKMKMAKUL=?");
				li = v.listIterator();
				while(li.hasNext()) {
					String thsms=(String)li.next();
					String kdkmk=(String)li.next();
					String nlakh=(String)li.next();
					String bobot=(String)li.next();
					String sksmk=(String)li.next();
					String kelas=(String)li.next();
					String sksem=(String)li.next();
					String nlips=(String)li.next();
					String skstt=(String)li.next();
					String nlipk=(String)li.next();
					String shift=(String)li.next();
					String krsdown=(String)li.next();;//tambahan baru
					String khsdown=(String)li.next();;//tambahan baru
					String bakprove=(String)li.next();;//tambahan baru
					String paprove=(String)li.next();;//tambahan baru
					String note=(String)li.next();;//tambahan baru
					String lock=(String)li.next();;//tambahan baru
					String baukprove=(String)li.next();;//tambahan baru
					//tambahan
					String idkmk =(String)li.next();;//tambahan baru
					String addReq =(String)li.next();;//tambahan baru
					String drpReq  =(String)li.next();;//tambahan baru
					String npmPa =(String)li.next();;//tambahan baru
					String npmBak =(String)li.next();;//tambahan baru
					String npmBaa =(String)li.next();;//tambahan baru
					String npmBauk =(String)li.next();;//tambahan baru
					String baaProve =(String)li.next();;//tambahan baru
					String ktuProve =(String)li.next();;//tambahan baru
					String dknProve =(String)li.next();;//tambahan baru
					String npmKtu =(String)li.next();;//tambahan baru
					String npmDekan =(String)li.next();;//tambahan baru
					String lockMhs =(String)li.next();;//tambahan baru
					String kodeKampus =(String)li.next();;//tambahan baru
					//stmt.setString(1,kdkmk);
					//stmt.setInt(2,Integer.valueOf(idkur).intValue());
					stmt.setString(1, kdpst);
					stmt.setString(2, kdkmk);
					rs = stmt.executeQuery();
					String nakmk = "KODE MATAKULIAH TIDAK ADA DI KURIKULUM,<br/> HARAP DICEK KEMBALI";
					if(rs.next()) {
						nakmk = rs.getString("NAKMKMAKUL");
					}
					lif.add(thsms);
					lif.add(kdkmk);
					lif.add(nakmk);
					lif.add(nlakh);
					lif.add(bobot);
					lif.add(sksmk);
					lif.add(kelas);
					lif.add(sksem);
					lif.add(nlips);
					lif.add(skstt);
					lif.add(nlipk);	
					lif.add(shift);	
					lif.add(krsdown);//tambahan baru
					lif.add(khsdown);//tambahan baru
					lif.add(bakprove);//tambahan baru
					lif.add(paprove);//tambahan baru
					lif.add(note);//tambahan baru
					lif.add(lock);//tambahan baru
					lif.add(baukprove);//tambahan baru
					//tambahan
					lif.add(idkmk);
					lif.add(addReq);
					lif.add(drpReq);
					lif.add(npmPa);
					lif.add(npmBak);
					lif.add(npmBaa);
					lif.add(npmBauk);
					lif.add(baaProve);
					lif.add(ktuProve);
					lif.add(dknProve);
					lif.add(npmKtu);
					lif.add(npmDekan);
					lif.add(lockMhs);
					lif.add(kodeKampus);
				}
			}
			
			//System.out.println("vf size = "+vf.size());
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
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
		}	
    	return vf;
    }
    
    /*
     * DUPLICAT ada kembarannya di SearchDb jadi kalo diupdate hrs dua2nya
     */
    public void updateIndividualTrakm(String kdpst, String npmhs) {
    	
    	try {
    		//getListMatakuliahTrnlp dan hitung sks dan ipk awal
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//stmt = con.prepareStatement("select * from TRNLP inner join MAKUL on KDKMKTRNLP=KDKMKMAKUL where KDPSTTRNLP=? and NPMHSTRNLP=? and TRANSFERRED=?");
			//stmt.setString(1,kdpst);
			//stmt.setString(2,npmhs);
			//stmt.setBoolean(3, true);
			stmt =  con.prepareStatement("select * from TRNLP where KDPSTTRNLP=? and NPMHSTRNLP=? and TRANSFERRED=?");
			stmt.setString(1,kdpst);
			stmt.setString(2,npmhs);
			stmt.setBoolean(3, true);
			rs = stmt.executeQuery();
			//variable initial
			Vector vTrnlp = new Vector();
			ListIterator liTrnlp = vTrnlp.listIterator();
			int sksta = 0;
			double ipsta=0,bbtXnlakhSta=0;;
			//untuk trnlp nlakh dan bobot hrs terisi dan tidak ada nilai tunda
			while(rs.next()) {
				String thsms = "00000"; //thsms=00000 utk menandakan krs trnlp
				String kdkmk = rs.getString("KDKMKTRNLP");
				//String nakmk = rs.getString("NAKMKMAKUL");
				String nlakh = rs.getString("NLAKHTRNLP");
				String bobot = ""+rs.getDouble("BOBOTTRNLP");
				String sksmk = ""+rs.getInt("SKSMKTRNLP");
				String kelas = "00";//trnlp ngga ada kodekelas;
				//liTrnlp.add(thsms+","+kdkmk+","+nakmk+","+nlakh+","+bobot+","+sksmk+","+kelas);
				liTrnlp.add(thsms+"#&"+kdkmk+"#&"+nlakh+"#&"+bobot+"#&"+sksmk+"#&"+kelas);
				sksta = sksta+Integer.valueOf(sksmk).intValue();
				ipsta = ipsta + (Double.valueOf(bobot).doubleValue()*Integer.valueOf(sksmk).intValue());
			}
			if(sksta>0) {
				bbtXnlakhSta = ipsta; //ipsta adalah komulatif bobot * sks
				ipsta = ipsta/sksta;
			}
			//System.out.println("ipsta = "+ipsta+"-"+sksta);
			stmt = con.prepareStatement("select * from MAKUL where KDPSTMAKUL=? and KDKMKMAKUL=?");
			liTrnlp = vTrnlp.listIterator();
			while(liTrnlp.hasNext()) {
				String brs =(String)liTrnlp.next();
				StringTokenizer st = new StringTokenizer(brs,"#&");
				String thsms = st.nextToken();
				//System.out.println("thsms="+thsms);
				String kdkmk = st.nextToken();
				//System.out.println("kdkmk="+kdkmk);
				String nlakh = st.nextToken();
				//System.out.println("nlakh="+nlakh);
				String bobot = st.nextToken();
				//System.out.println("bobot="+bobot);
				String sksmk = st.nextToken();
				//System.out.println("sksmk="+sksmk);
				String kelas = st.nextToken();
				//System.out.println("kelas="+kelas);
				stmt.setString(1,"20201");
				stmt.setString(2,kdkmk);
				//System.out.println("kdkmk="+kdkmk);
				rs = stmt.executeQuery();
				String nakmk = "TIDAK TERDAFTAR";
				if(rs.next()) {
					nakmk = rs.getString("NAKMKMAKUL");
				}
				liTrnlp.set(thsms+"#&"+kdkmk+"#&"+nakmk+"#&"+nlakh+"#&"+bobot+"#&"+sksmk+"#&"+kelas);
				//System.out.println(thsms+","+kdkmk+","+nakmk+","+nlakh+","+bobot+","+sksmk+","+kelas);

			}
			
			//get distinct thsms from trnlm
			stmt = con.prepareStatement("select distinct THSMSTRNLM from TRNLM where KDPSTTRNLM=? and NPMHSTRNLM=? order by THSMSTRNLM");
			stmt.setString(1,kdpst);
			stmt.setString(2, npmhs);
			rs = stmt.executeQuery();
			Vector vThsms = new Vector();
			ListIterator liThsms = vThsms.listIterator();
			while(rs.next()) {
				liThsms.add(rs.getString("THSMSTRNLM"));
			}
			
			if(vThsms.size()>0) {
				/*
				 * delete prev trakm record
				 */
				stmt = con.prepareStatement("delete from TRAKM where KDPSTTRAKM=? and NPMHSTRAKM=?");
				stmt.setString(1, kdpst);
				stmt.setString(2, npmhs);
				stmt.executeUpdate();
				
				/*
				 * calculate ipsem & sksem per thsms
				 */
				Vector vSms = new Vector();
				ListIterator liSms = vSms.listIterator();
				stmt = con.prepareStatement("select * from TRNLM where THSMSTRNLM=? and KDPSTTRNLM=? and NPMHSTRNLM=?");
				liThsms = vThsms.listIterator();
				
				while(liThsms.hasNext()) {
					int sksem=0;
					double sksXbbt=0, sksDevider=0, ipsms=0;
					String thsms = (String)liThsms.next();
					stmt.setString(1,thsms);
					stmt.setString(2,kdpst);
					stmt.setString(3,npmhs);
					rs = stmt.executeQuery();
					while(rs.next()) {
						int sksmk = rs.getInt("SKSMKTRNLM");
						float bobot = rs.getFloat("BOBOTTRNLM");
						String nlakh = rs.getString("NLAKHTRNLM");
						sksem = sksem + sksmk;
						if(nlakh.equalsIgnoreCase("T")) {
							if(Constants.getTipePerhitunganNlakhTunda().equalsIgnoreCase("excluded")) {
								//pembagi tidak ditambah
							}
							else {
							//perhitungan nilai Tunda cara lainnya bila ada
							}
						}
						else {
							sksXbbt = sksXbbt + (sksmk*bobot);
							sksDevider = sksDevider + sksmk;
						}
						//if(nlakh.equalsIgnoreCase("exclude")) {
							//sks tidah dihitung  skstotal pembagi
						//}
						//else {
							//sksXbbt = sksXbbt + (sksmk*bobot);
							//sksDevider = sksDevider + sksmk;
						//}
					}
					if(sksDevider>0) {
						ipsms = sksXbbt/sksDevider;
						liSms.add(thsms+","+sksem+","+ipsms);
					}
					else {
						liSms.add(thsms+","+sksem+",0");
					}	
				}
				
				/*
				 * insert new record sksem and ipsem trakm 
				 */
				stmt = con.prepareStatement("INSERT INTO TRAKM (THSMSTRAKM,KDPTITRAKM,KDJENTRAKM,KDPSTTRAKM,NPMHSTRAKM,SKSEMTRAKM,NLIPSTRAKM,SKSTTTRAKM,NLIPKTRAKM) VALUES (?,?,?,?,?,?,?,?,?)");
				liSms = vSms.listIterator();
				while(liSms.hasNext()) {
					String baris = (String)liSms.next();
					//System.out.println("semesteran = "+baris);
					StringTokenizer st = new StringTokenizer(baris,",");
					String thsms = st.nextToken();
					String sksem = st.nextToken();
					String ipsem = st.nextToken();
					stmt.setString(1,thsms);
					stmt.setString(2,Constants.getKdpti());
					stmt.setNull(3,java.sql.Types.VARCHAR);
					stmt.setString(4,kdpst);
					stmt.setString(5,npmhs);
					stmt.setInt(6,Integer.valueOf(sksem).intValue());
					stmt.setFloat(7, Float.valueOf(ipsem).floatValue());
					stmt.setInt(8,0);
					stmt.setFloat(9,0);
					stmt.executeUpdate();
					
				}
				//======end calkulasi sks & ip sms==================
				

				
				
				/*
				 * ======start calculasi ipk=========================
				 */
				Vector vThsmsSksttIpk = new Vector();
				ListIterator lit = vThsmsSksttIpk.listIterator();
				String tknThsms = "";
				String tknSqlThsms = "";
				if(vThsms.size()>0) {
					liThsms = vThsms.listIterator();
					while(liThsms.hasNext()) {
						tknThsms = tknThsms+(String)liThsms.next();
						if(liThsms.hasNext()) {
							tknThsms = tknThsms+",";
						}
					}
					//System.out.println("tknThsms ="+tknThsms);
					//update command
					
					int sksInit = sksta;
					double nilai = bbtXnlakhSta;
					boolean first = true;
					liThsms = vThsms.listIterator();
					int i=1;
					while(liThsms.hasNext()) {
						String thsms = (String)liThsms.next();
						if(first) { //first thsms
							first = false;
							//System.out.println("first ="+thsms);		
							stmt = con.prepareStatement("select * from TRNLM where THSMSTRNLM=? and KDPSTTRNLM=? and NPMHSTRNLM=?");
							stmt.setString(1,thsms);
							stmt.setString(2,kdpst);
							stmt.setString(3,npmhs);
							rs = stmt.executeQuery();
							//System.out.println("bbtXnlakhSta awal= "+bbtXnlakhSta+"-"+sksta);
							while(rs.next()) {
								String thsms0 = rs.getString("THSMSTRNLM");
								String kdkmk0 = rs.getString("KDKMKTRNLM");
								String bobot0 = ""+rs.getDouble("BOBOTTRNLM");
								int sksmk0 = rs.getInt("SKSMKTRNLM");
								nilai=nilai+(Double.valueOf(bobot0).doubleValue()*sksmk0);
								sksta=sksta+sksmk0;
							}
							//stmt.setInt(1, sksta);
							//stmt.setDouble(2,nilai/sksta);
							//stmt.setString(3,thsms);
							//stmt.setString(4,kdpst);
							//stmt.setString(5,npmhs);
							//stmt.executeUpdate();
							//System.out.println("bbtXnlakhSta first = "+bbtXnlakhSta+"-"+sksta);
							//System.out.println("nilai ipk = "+thsms+" "+sksInit+" "+(sksta)+" "+(nilai/sksta));
							//System.out.println("1. thsms="+thsms);
							//System.out.println("2. sksta="+sksta);
							//System.out.println("3. nilai="+nilai);
							//System.out.println("4. sksta="+sksta);
							if(nilai==0 && sksta==0) {
								lit.add(thsms+" "+sksta+" 0.0");
							}
							else {
								lit.add(thsms+" "+sksta+" "+(nilai/sksta));
							}	
						}
						else {
							tknSqlThsms = "";
							//System.out.println("i ="+i);							
							StringTokenizer st = new StringTokenizer(tknThsms,",");
							for(int j=0;j<i;j++) {
								tknSqlThsms = tknSqlThsms+st.nextToken()+" ";
							}
							st = new StringTokenizer(tknSqlThsms);
							//int counter = st.countTokens();
							String sqlCmd = "select * from TRNLM where (THSMSTRNLM='";
							while(st.hasMoreTokens()) {
								sqlCmd=sqlCmd+st.nextToken();
								if(st.hasMoreTokens()) {
									sqlCmd=sqlCmd+"' or THSMSTRNLM='";
								}
							}
							
							/*
							 * sort berdasarkan kdkmk dan bobot
							 * jadi bila prev kdkmk equals current kdkmk maka hapus current kdkmk karena bobot lebih kecil dari 
							 * bobot pada prev kdkmk
							 */
							sqlCmd=sqlCmd+"') and KDPSTTRNLM=? and NPMHSTRNLM=? order by KDKMKTRNLM,BOBOTTRNLM desc";
							//System.out.println("liThsms ="+thsms);
							stmt = con.prepareStatement(sqlCmd);
							stmt.setString(1,kdpst);
							stmt.setString(2,npmhs);
							rs = stmt.executeQuery();
							//System.out.println("bbtXnlakhSta awal= "+bbtXnlakhSta+"-"+sksta);
							Vector vTmp = new Vector();
							ListIterator liTmp = vTmp.listIterator();
							while(rs.next()) {
								String thsms0 = rs.getString("THSMSTRNLM");
								String kdkmk0 = rs.getString("KDKMKTRNLM");
								String nlakh0 = rs.getString("NLAKHTRNLM");
								String bobot0 = ""+rs.getDouble("BOBOTTRNLM");
								int sksmk0 = rs.getInt("SKSMKTRNLM");
								liTmp.add(thsms0+","+kdkmk0+","+nlakh0+","+bobot0+","+sksmk0);
							}
							
							//System.out.println("sampe1");
							//boolean barisPertama = true;
							/*
							 * filtering matakuliah yg mengulang, utk bestBobot diambil nilai terbaik
							 */
							String prevKdkmk="";
							if(Constants.getTipePerhitunganTranscript().equalsIgnoreCase("bestBobot")) {
								liTmp = vTmp.listIterator();
								if(liTmp.hasNext()) {
									//process baris pertama
									//System.out.println("sampe2");
									String baris = (String)liTmp.next();
									st = new StringTokenizer(baris,",");
									String thsms_ = st.nextToken();
									String kdkmk_ = st.nextToken();
									String nlakh_ = st.nextToken();
									String bobot_ = st.nextToken();
									String sksmk_ = st.nextToken();
									prevKdkmk = ""+kdkmk_;
									while(liTmp.hasNext()) {
										baris = (String)liTmp.next();
										st = new StringTokenizer(baris,",");
										//System.out.println("sampe3");
										String thsms_1 = st.nextToken();
										String kdkmk_1 = st.nextToken();
										String nlakh_1 = st.nextToken();
										String bobot_1 = st.nextToken();
										String sksmk_1 = st.nextToken();
										if(prevKdkmk.equalsIgnoreCase(kdkmk_1)) {
											liTmp.remove(); // hapus record kdkmk dengan bobot yg lebih rendah
										}
										else {
											prevKdkmk = ""+kdkmk_1;
										}
									}
								}
							}
							else {
								//bila ada perhitungan lainya
							}
							//=====end filtering==================
							
							
							/*
							 * ========hitung ipk dan skstt and update new trakm record=====================
							 */
							
						
							int skstt = sksInit, pembagi=sksInit;
							double nilaiTt = bbtXnlakhSta;
							//System.out.println("nilaiTT awal ="+ nilaiTt);
							liTmp = vTmp.listIterator();
							while(liTmp.hasNext()) {
								String baris = (String)liTmp.next();
								//System.out.println(baris);
								st = new StringTokenizer(baris,",");
								String thsms_ = st.nextToken();
								String kdkmk_ = st.nextToken();
								String nlakh = st.nextToken();
								String bobot_ = st.nextToken();
								String sksmk_ = st.nextToken();
								skstt = skstt + Integer.valueOf(sksmk_).intValue();
								nilaiTt=nilaiTt+(Double.valueOf(bobot_).doubleValue()*Integer.valueOf(sksmk_).intValue());
								if(nlakh.equalsIgnoreCase("T")) {
									if(Constants.getTipePerhitunganNlakhTunda().equalsIgnoreCase("excluded")) {
										//pembagi tidak ditambah
									}
									else {
									//perhitungan nilai Tunda cara lainnya bila ada
									}
								}
								else {
									pembagi = pembagi + Integer.valueOf(sksmk_).intValue();
								}
							}
					
							if(nilaiTt==0 && pembagi==0) {
								lit.add(thsms+" "+skstt+" 0.0");
							}
							else {
								lit.add(thsms+" "+skstt+" "+(nilaiTt/pembagi));
							}
							//System.out.println("nilai ipk = "+thsms+" "+sksInit+" "+(skstt)+" "+(pembagi)+" "+(nilaiTt)+" "+(nilaiTt/pembagi));
							
						}
						i = i + 1;
					}
					
					stmt=con.prepareStatement("update TRAKM set SKSTTTRAKM=?,NLIPKTRAKM=? where THSMSTRAKM=? and KDPSTTRAKM=? and NPMHSTRAKM=?");
					lit = vThsmsSksttIpk.listIterator();
					while(lit.hasNext()) {
						String baris = (String)lit.next();
						//System.out.println("ini-"+baris);
						StringTokenizer stt = new StringTokenizer(baris);
						String thsms = stt.nextToken();
						String skstt = stt.nextToken();
						String ipk = stt.nextToken();
						//System.out.println("skstt="+skstt);
						stmt.setInt(1,Integer.valueOf(skstt).intValue());
						stmt.setDouble(2,Double.valueOf(ipk).doubleValue());
						stmt.setString(3,thsms);
						stmt.setString(4,kdpst);
						stmt.setString(5,npmhs);
						stmt.executeUpdate();
						
						//System.out.println("IPK="+baris);
					}
				}
				
				//======end calculasi ipk=========================
			}
			else {
				// 0 record di trnlm
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
					//System.out.println(ignore);
				}
			}
		}
    }
    
    public Vector getHistoryKrsKhs_v1(String kdpst, String npmhs, String idkur) {
    	//System.out.println("getHistoryKrsKhs start");
    	/*
    	 * calc nilai trakm dulu !!!
    	 */
    	updateIndividualTrakm(kdpst, npmhs);
    	
    	Vector v = new Vector();
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	ListIterator li = v.listIterator();
    	try {
    		//get angkatan
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt=con.prepareStatement("select * from TRNLM inner join TRAKM on (THSMSTRNLM=THSMSTRAKM AND NPMHSTRNLM=NPMHSTRAKM) where KDPSTTRNLM=? and NPMHSTRNLM=? order by THSMSTRNLM");
			stmt.setString(1, kdpst);
			stmt.setString(2, npmhs);
			rs = stmt.executeQuery();
			while(rs.next()) {
				String thsms = rs.getString("THSMSTRNLM");
				String kdkmk = rs.getString("KDKMKTRNLM");
				String nlakh = rs.getString("NLAKHTRNLM");
				String bobot =""+rs.getDouble("BOBOTTRNLM");
				String sksmk =""+rs.getInt("SKSMKTRNLM");
				String kelas = rs.getString("KELASTRNLM");
				String shift = ""+rs.getString("SHIFTTRNLM");
				String sksem = ""+rs.getInt("SKSEMTRAKM");
				String nlips = ""+rs.getDouble("NLIPSTRAKM");
				String skstt = ""+rs.getInt("SKSTTTRAKM");
				String nlipk = ""+rs.getDouble("NLIPKTRAKM");
				String krsdown = ""+rs.getBoolean("KRSDONWLOADED");
				String khsdown = ""+rs.getBoolean("KHSDONWLOADED");
				String bakprove = ""+rs.getBoolean("BAK_APPROVAL");
				String paprove = ""+rs.getBoolean("PA_APPROVAL");
				String note = ""+rs.getString("NOTE");
				String lock = ""+rs.getBoolean("LOCKTRNLM");
				String baukprove = ""+rs.getBoolean("BAUK_APPROVAL");
				//tambahan
				//Krs & KhsDownload.jaba butuh diupdate
				String idkmk = ""+rs.getLong("IDKMKTRNLM");
				String addReq = ""+rs.getBoolean("ADD_REQ");
				String drpReq  = ""+rs.getBoolean("DRP_REQ");
				String npmPa = ""+rs.getString("NPM_PA");
				String npmBak = ""+rs.getString("NPM_BAK");
				String npmBaa = ""+rs.getString("NPM_BAA");
				String npmBauk = ""+rs.getString("NPM_BAUK");
				String baaProve = ""+rs.getBoolean("BAA_APPROVAL");
				String ktuProve = ""+rs.getBoolean("KTU_APPROVAL");
				String dknProve = ""+rs.getBoolean("DEKAN_APPROVAL");
				String npmKtu = ""+rs.getString("NPM_KTU");
				String npmDekan = ""+rs.getString("NPM_DEKAN");
				String lockMhs = ""+rs.getBoolean("LOCKMHS");
				String kodeKampus = ""+rs.getString("KODE_KAMPUS");
				String cuid = ""+rs.getInt("CLASS_POOL_UNIQUE_ID");
				String cuid_init = ""+rs.getInt("CUID_INIT");
				//System.out.println("add idkmk="+idkmk);
				
				li.add(thsms);
				li.add(kdkmk);
				li.add(nlakh);
				li.add(bobot);
				li.add(sksmk);
				li.add(kelas);
				li.add(sksem);
				li.add(nlips);
				li.add(skstt);
				li.add(nlipk);
				li.add(shift);//tambahan baru
				li.add(krsdown);//tambahan baru
				li.add(khsdown);//tambahan baru
				li.add(bakprove);//tambahan baru
				li.add(paprove);//tambahan baru
				li.add(note);//tambahan baru
				li.add(lock);//tambahan baru
				li.add(baukprove);//tambahan baru
				
				//tambahan
				li.add(idkmk);
				li.add(addReq);
				li.add(drpReq);
				li.add(npmPa);
				li.add(npmBak);
				li.add(npmBaa);
				li.add(npmBauk);
				li.add(baaProve);
				li.add(ktuProve);
				li.add(dknProve);
				li.add(npmKtu);
				li.add(npmDekan);
				li.add(lockMhs);
				li.add(kodeKampus);
				li.add(cuid);
				li.add(cuid_init);
				//System.out.println(thsms+","+kdkmk+","+nlakh+","+bobot+","+sksmk+","+kelas+","+sksem+","+nlips+","+skstt+","+nlipk+","+krsdown+","+khsdown+","+bakprove+","+paprove+","+note+","+lock+","+baukprove+","+idkmk+","+cuid_init);
			}
			
			Vector vtmp = new Vector();
			ListIterator litmp = vtmp.listIterator();
			stmt = con.prepareStatement("select NPMDOS,NODOS,NPMASDOS,NOASDOS,NMMDOS,NMMASDOS from CLASS_POOL where UNIQUE_ID=?");
			li = v.listIterator();
			while(li.hasNext()) {
				String thsms = (String)li.next();
				String kdkmk = (String)li.next();
				String nlakh = (String)li.next();
				String bobot = (String)li.next();
				String sksmk = (String)li.next();
				String kelas = (String)li.next();
				String sksem = (String)li.next();
				String nlips = (String)li.next();
				String skstt = (String)li.next();
				String nlipk = (String)li.next();
				String shift = (String)li.next();
				String krsdown = (String)li.next();
				String khsdown = (String)li.next();
				String bakprove = (String)li.next();
				String paprove = (String)li.next();
				String note = (String)li.next();
				String lock = (String)li.next();
				String baukprove = (String)li.next();
				String idkmk = (String)li.next();
				String addReq = (String)li.next();
				String drpReq = (String)li.next();
				String npmPa = (String)li.next();
				String npmBak = (String)li.next();
				String npmBaa = (String)li.next();
				String npmBauk = (String)li.next();
				String baaProve = (String)li.next();
				String ktuProve = (String)li.next();
				String dknProve = (String)li.next();
				String npmKtu = (String)li.next();
				String npmDekan = (String)li.next();
				String lockMhs = (String)li.next();
				String kodeKampus = (String)li.next();
				String cuid = (String)li.next();
				String cuid_init = (String)li.next();
				//System.out.println("cuid="+cuid);
				stmt.setInt(1, Integer.parseInt(cuid));
				rs = stmt.executeQuery();
				String npmdos = "null";
				String nodos = "null";
				String npmasdos = "null";
				String noasdos = "null";
				String nmmdos = "null";
				String nmmasdos = "null";
				if(rs.next()) {
					npmdos = ""+rs.getString("NPMDOS");
					nodos = ""+rs.getString("NODOS");
					npmasdos = ""+rs.getString("NPMASDOS");
					noasdos = ""+rs.getString("NOASDOS");
					nmmdos = ""+rs.getString("NMMDOS");
					nmmasdos = ""+rs.getString("NMMASDOS");
				}
				
				
				litmp.add(thsms);
				litmp.add(kdkmk);
				litmp.add(nlakh);
				litmp.add(bobot);
				litmp.add(sksmk);
				litmp.add(kelas);
				litmp.add(sksem);
				litmp.add(nlips);
				litmp.add(skstt);
				litmp.add(nlipk);	
				litmp.add(shift);	
				litmp.add(krsdown);//tambahan baru
				litmp.add(khsdown);//tambahan baru
				litmp.add(bakprove);//tambahan baru
				litmp.add(paprove);//tambahan baru
				litmp.add(note);//tambahan baru
				litmp.add(lock);//tambahan baru
				litmp.add(baukprove);//tambahan baru
				//tambahan
				litmp.add(idkmk);
				litmp.add(addReq);
				litmp.add(drpReq);
				litmp.add(npmPa);
				litmp.add(npmBak);
				litmp.add(npmBaa);
				litmp.add(npmBauk);
				litmp.add(baaProve);
				litmp.add(ktuProve);
				litmp.add(dknProve);
				litmp.add(npmKtu);
				litmp.add(npmDekan);
				litmp.add(lockMhs);
				litmp.add(kodeKampus);
				litmp.add(cuid);
				litmp.add(cuid_init);
				litmp.add(npmdos);
				litmp.add(nodos);
				litmp.add(npmasdos);
				litmp.add(noasdos);
				litmp.add(nmmdos);
				litmp.add(nmmasdos); 
			}
			

			//System.out.println("v- size = "+v.size());
			/*
			 * deprecated cari nakmk tidak dari kurikulum, pastikan kdkmk utk 1 kdpst tidak boleh sama
			 */
			//stmt = con.prepareStatement("select * from MAKUL inner join MAKUR on (IDKMKMAKUL=IDKMKMAKUR) where KDKMKMAKUL=? and IDKURMAKUR=?");
			/*
			 * cara baru nakmk cukup berdasarkan kdpst dan kdkmk (jadi sepertti primary key)
			 */
			/*
			 * update lagi: utk nama mk agar akurat
			 */
			stmt = con.prepareStatement("select KRKLMMSMHS from EXT_CIVITAS WHERE KDPSTMSMHS=? and NPMHSMSMHS=?");
			stmt.setString(1, kdpst);
			stmt.setString(2, npmhs);
			rs = stmt.executeQuery();
			if(rs.next()) {
				String idKurMhs = ""+rs.getString("KRKLMMSMHS");
				if(Checker.isStringNullOrEmpty(idKurMhs)||idKurMhs.equalsIgnoreCase("0")) {
					//kalo null berarti sama kaaya dibawah
					stmt = con.prepareStatement("select * from MAKUL WHERE KDPSTMAKUL=? and  KDKMKMAKUL=?");
					litmp = vtmp.listIterator();
					while(litmp.hasNext()) {
						String thsms=(String)litmp.next();
						String kdkmk=(String)litmp.next();
						String nlakh=(String)litmp.next();
						String bobot=(String)litmp.next();
						String sksmk=(String)litmp.next();
						String kelas=(String)litmp.next();
						String sksem=(String)litmp.next();
						String nlips=(String)litmp.next();
						String skstt=(String)litmp.next();
						String nlipk=(String)litmp.next();
						String shift=(String)litmp.next();
						String krsdown=(String)litmp.next();;//tambahan baru
						String khsdown=(String)litmp.next();;//tambahan baru
						String bakprove=(String)litmp.next();;//tambahan baru
						String paprove=(String)litmp.next();;//tambahan baru
						String note=(String)litmp.next();;//tambahan baru
						String lock=(String)litmp.next();;//tambahan baru
						String baukprove=(String)litmp.next();;//tambahan baru
						//tambahan
						String idkmk =(String)litmp.next();;//tambahan baru
						String addReq =(String)litmp.next();;//tambahan baru
						String drpReq  =(String)litmp.next();;//tambahan baru
						String npmPa =(String)litmp.next();;//tambahan baru
						String npmBak =(String)litmp.next();;//tambahan baru
						String npmBaa =(String)litmp.next();;//tambahan baru
						String npmBauk =(String)litmp.next();;//tambahan baru
						String baaProve =(String)litmp.next();;//tambahan baru
						String ktuProve =(String)litmp.next();;//tambahan baru
						String dknProve =(String)litmp.next();;//tambahan baru
						String npmKtu =(String)litmp.next();;//tambahan baru
						String npmDekan =(String)litmp.next();;//tambahan baru
						String lockMhs =(String)litmp.next();;//tambahan baru
						String kodeKampus =(String)litmp.next();;//tambahan baru
						String cuid =(String)litmp.next();;//tambahan baru
						String cuid_init =(String)litmp.next();;//tambahan baru
						String npmdos =(String)litmp.next();;//tambahan baru
						String nodos =(String)litmp.next();;//tambahan baru
						String npmasdos =(String)litmp.next();;//tambahan baru
						String noasdos =(String)litmp.next();;//tambahan baru
						String nmmdos =(String)litmp.next();;//tambahan baru
						String nmmasdos =(String)litmp.next();;//tambahan baru 
						//stmt.setString(1,kdkmk);
						//stmt.setInt(2,Integer.valueOf(idkur).intValue());
						stmt.setString(1, kdpst);
						stmt.setString(2, kdkmk);
						rs = stmt.executeQuery();
						String nakmk = "KODE MATAKULIAH TIDAK ADA DI KURIKULUM,<br/> HARAP DICEK KEMBALI";
						if(rs.next()) {
							nakmk = rs.getString("NAKMKMAKUL");
						}
						lif.add(thsms);
						lif.add(kdkmk);
						lif.add(nakmk);
						lif.add(nlakh);
						lif.add(bobot);
						lif.add(sksmk);
						lif.add(kelas);
						lif.add(sksem);
						lif.add(nlips);
						lif.add(skstt);
						lif.add(nlipk);	
						lif.add(shift);	
						lif.add(krsdown);//tambahan baru
						lif.add(khsdown);//tambahan baru
						lif.add(bakprove);//tambahan baru
						lif.add(paprove);//tambahan baru
						lif.add(note);//tambahan baru
						lif.add(lock);//tambahan baru
						lif.add(baukprove);//tambahan baru
						//tambahan
						lif.add(idkmk);
						lif.add(addReq);
						lif.add(drpReq);
						lif.add(npmPa);
						lif.add(npmBak);
						lif.add(npmBaa);
						lif.add(npmBauk);
						lif.add(baaProve);
						lif.add(ktuProve);
						lif.add(dknProve);
						lif.add(npmKtu);
						lif.add(npmDekan);
						lif.add(lockMhs);
						lif.add(kodeKampus);
						lif.add(cuid);
						lif.add(cuid_init);
						lif.add(npmdos);
						lif.add(nodos);
						lif.add(npmasdos);
						lif.add(noasdos);
						lif.add(nmmdos);
						lif.add(nmmasdos); 
					}
				}
				else {
					//get
					stmt = con.prepareStatement("select * from MAKUL inner join MAKUR on (IDKMKMAKUL=IDKMKMAKUR) where KDKMKMAKUL=? and IDKURMAKUR=?");

					litmp = vtmp.listIterator();
					while(litmp.hasNext()) {
						String thsms=(String)litmp.next();
						String kdkmk=(String)litmp.next();
						String nlakh=(String)litmp.next();
						String bobot=(String)litmp.next();
						String sksmk=(String)litmp.next();
						String kelas=(String)litmp.next();
						String sksem=(String)litmp.next();
						String nlips=(String)litmp.next();
						String skstt=(String)litmp.next();
						String nlipk=(String)litmp.next();
						String shift=(String)litmp.next();
						String krsdown=(String)litmp.next();;//tambahan baru
						String khsdown=(String)litmp.next();;//tambahan baru
						String bakprove=(String)litmp.next();;//tambahan baru
						String paprove=(String)litmp.next();;//tambahan baru
						String note=(String)litmp.next();;//tambahan baru
						String lock=(String)litmp.next();;//tambahan baru
						String baukprove=(String)litmp.next();;//tambahan baru
						//tambahan
						String idkmk =(String)litmp.next();;//tambahan baru
						String addReq =(String)litmp.next();;//tambahan baru
						String drpReq  =(String)litmp.next();;//tambahan baru
						String npmPa =(String)litmp.next();;//tambahan baru
						String npmBak =(String)litmp.next();;//tambahan baru
						String npmBaa =(String)litmp.next();;//tambahan baru
						String npmBauk =(String)litmp.next();;//tambahan baru
						String baaProve =(String)litmp.next();;//tambahan baru
						String ktuProve =(String)litmp.next();;//tambahan baru
						String dknProve =(String)litmp.next();;//tambahan baru
						String npmKtu =(String)litmp.next();;//tambahan baru
						String npmDekan =(String)litmp.next();;//tambahan baru
						String lockMhs =(String)litmp.next();;//tambahan baru
						String kodeKampus =(String)litmp.next();;//tambahan baru
						String cuid =(String)litmp.next();;//tambahan baru
						String cuid_init =(String)litmp.next();;//tambahan baru
						String npmdos =(String)litmp.next();;//tambahan baru
						String nodos =(String)litmp.next();;//tambahan baru
						String npmasdos =(String)litmp.next();;//tambahan baru
						String noasdos =(String)litmp.next();;//tambahan baru
						String nmmdos =(String)litmp.next();;//tambahan baru
						String nmmasdos =(String)litmp.next();;//tambahan baru
						
						stmt.setString(1, kdkmk);
						stmt.setInt(2, Integer.parseInt(idKurMhs));
						rs = stmt.executeQuery();
						String nakmk = "KODE MATAKULIAH TIDAK ADA DI KURIKULUM,<br/> HARAP DICEK KEMBALI";
						if(rs.next()) {
							nakmk = rs.getString("NAKMKMAKUL");
						}
						lif.add(thsms);
						lif.add(kdkmk);
						lif.add(nakmk);
						lif.add(nlakh);
						lif.add(bobot);
						lif.add(sksmk);
						lif.add(kelas);
						lif.add(sksem);
						lif.add(nlips);
						lif.add(skstt);
						lif.add(nlipk);	
						lif.add(shift);	
						lif.add(krsdown);//tambahan baru
						lif.add(khsdown);//tambahan baru
						lif.add(bakprove);//tambahan baru
						lif.add(paprove);//tambahan baru
						lif.add(note);//tambahan baru
						lif.add(lock);//tambahan baru
						lif.add(baukprove);//tambahan baru
						//tambahan
						lif.add(idkmk);
						lif.add(addReq);
						lif.add(drpReq);
						lif.add(npmPa);
						lif.add(npmBak);
						lif.add(npmBaa);
						lif.add(npmBauk);
						lif.add(baaProve);
						lif.add(ktuProve);
						lif.add(dknProve);
						lif.add(npmKtu);
						lif.add(npmDekan);
						lif.add(lockMhs);
						lif.add(kodeKampus);
						lif.add(cuid);
						lif.add(cuid_init);
						lif.add(npmdos);
						lif.add(nodos);
						lif.add(npmasdos);
						lif.add(noasdos);
						lif.add(nmmdos);
						lif.add(nmmasdos); 
					}	
				}
			}
			else {
				stmt = con.prepareStatement("select * from MAKUL WHERE KDPSTMAKUL=? and  KDKMKMAKUL=?");
				litmp = vtmp.listIterator();
				while(litmp.hasNext()) {
					String thsms=(String)litmp.next();
					String kdkmk=(String)litmp.next();
					String nlakh=(String)litmp.next();
					String bobot=(String)litmp.next();
					String sksmk=(String)litmp.next();
					String kelas=(String)litmp.next();
					String sksem=(String)litmp.next();
					String nlips=(String)litmp.next();
					String skstt=(String)litmp.next();
					String nlipk=(String)litmp.next();
					String shift=(String)litmp.next();
					String krsdown=(String)litmp.next();;//tambahan baru
					String khsdown=(String)litmp.next();;//tambahan baru
					String bakprove=(String)litmp.next();;//tambahan baru
					String paprove=(String)litmp.next();;//tambahan baru
					String note=(String)litmp.next();;//tambahan baru
					String lock=(String)litmp.next();;//tambahan baru
					String baukprove=(String)litmp.next();;//tambahan baru
					//tambahan
					String idkmk =(String)litmp.next();;//tambahan baru
					String addReq =(String)litmp.next();;//tambahan baru
					String drpReq  =(String)litmp.next();;//tambahan baru
					String npmPa =(String)litmp.next();;//tambahan baru
					String npmBak =(String)litmp.next();;//tambahan baru
					String npmBaa =(String)litmp.next();;//tambahan baru
					String npmBauk =(String)litmp.next();;//tambahan baru
					String baaProve =(String)litmp.next();;//tambahan baru
					String ktuProve =(String)litmp.next();;//tambahan baru
					String dknProve =(String)litmp.next();;//tambahan baru
					String npmKtu =(String)litmp.next();;//tambahan baru
					String npmDekan =(String)litmp.next();;//tambahan baru
					String lockMhs =(String)litmp.next();;//tambahan baru
					String kodeKampus =(String)litmp.next();;//tambahan baru
					String cuid =(String)litmp.next();;//tambahan baru
					String cuid_init =(String)litmp.next();;//tambahan baru
					String npmdos =(String)litmp.next();;//tambahan baru
					String nodos =(String)litmp.next();;//tambahan baru
					String npmasdos =(String)litmp.next();;//tambahan baru
					String noasdos =(String)litmp.next();;//tambahan baru
					String nmmdos =(String)litmp.next();;//tambahan baru
					String nmmasdos =(String)litmp.next();;//tambahan baru
					//stmt.setString(1,kdkmk);
					//stmt.setInt(2,Integer.valueOf(idkur).intValue());
					stmt.setString(1, kdpst);
					stmt.setString(2, kdkmk);
					rs = stmt.executeQuery();
					String nakmk = "KODE MATAKULIAH TIDAK ADA DI KURIKULUM,<br/> HARAP DICEK KEMBALI";
					if(rs.next()) {
						nakmk = rs.getString("NAKMKMAKUL");
					}
					lif.add(thsms);
					lif.add(kdkmk);
					lif.add(nakmk);
					lif.add(nlakh);
					lif.add(bobot);
					lif.add(sksmk);
					lif.add(kelas);
					lif.add(sksem);
					lif.add(nlips);
					lif.add(skstt);
					lif.add(nlipk);	
					lif.add(shift);	
					lif.add(krsdown);//tambahan baru
					lif.add(khsdown);//tambahan baru
					lif.add(bakprove);//tambahan baru
					lif.add(paprove);//tambahan baru
					lif.add(note);//tambahan baru
					lif.add(lock);//tambahan baru
					lif.add(baukprove);//tambahan baru
					//tambahan
					lif.add(idkmk);
					lif.add(addReq);
					lif.add(drpReq);
					lif.add(npmPa);
					lif.add(npmBak);
					lif.add(npmBaa);
					lif.add(npmBauk);
					lif.add(baaProve);
					lif.add(ktuProve);
					lif.add(dknProve);
					lif.add(npmKtu);
					lif.add(npmDekan);
					lif.add(lockMhs);
					lif.add(kodeKampus);
					lif.add(cuid);
					lif.add(cuid_init);
					lif.add(npmdos);
					lif.add(nodos);
					lif.add(npmasdos);
					lif.add(noasdos);
					lif.add(nmmdos);
					lif.add(nmmasdos); 
				}
			}
		
			//System.out.println("vf size = "+vf.size());
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
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
		}	
    	return vf;
    }
    
    
    
    
    
    public Vector getHistoryKrsKhs(String kdpst, String asnim) {
    	//get krskhs nimhs = asnim
    	/*
    	 * calc nilai trakm dulu !!!
    	 */
    	updateIndividualTrakm(kdpst, asnim);
    	
    	Vector v = new Vector();
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	ListIterator li = v.listIterator();
    	try {
    		
    		//get angkatan
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt=con.prepareStatement("select * from CIVITAS where KDPSTMSMHS=? and NIMHSMSMHS=?");
			stmt.setString(1, kdpst);
			stmt.setString(2,asnim);
			rs = stmt.executeQuery();
			String npmhs = "";
			if(rs.next()) {
				npmhs = rs.getString("NPMHSMSMHS");
			}
			stmt=con.prepareStatement("select * from TRNLM inner join TRAKM on (THSMSTRNLM=THSMSTRAKM AND NPMHSTRNLM=NPMHSTRAKM) where KDPSTTRNLM=? and NPMHSTRNLM=? order by THSMSTRNLM");
			stmt.setString(1, kdpst);
			stmt.setString(2, npmhs);
			rs = stmt.executeQuery();
			while(rs.next()) {
				String thsms = rs.getString("THSMSTRNLM");
				String kdkmk = rs.getString("KDKMKTRNLM");
				String nlakh = rs.getString("NLAKHTRNLM");
				String bobot =""+rs.getDouble("BOBOTTRNLM");
				String sksmk =""+rs.getInt("SKSMKTRNLM");
				String kelas = rs.getString("KELASTRNLM");
				String sksem = ""+rs.getInt("SKSEMTRAKM");
				String nlips = ""+rs.getDouble("NLIPSTRAKM");
				String skstt = ""+rs.getInt("SKSTTTRAKM");
				String nlipk = ""+rs.getDouble("NLIPKTRAKM");
				li.add(thsms);
				li.add(kdkmk);
				li.add(nlakh);
				li.add(bobot);
				li.add(sksmk);
				li.add(kelas);
				li.add(sksem);
				li.add(nlips);
				li.add(skstt);
				li.add(nlipk);
			}
			/*
			 * deprecated cari nakmk tidak dari kurikulum, pastikan kdkmk utk 1 kdpst tidak boleh sama
			 */
			//stmt = con.prepareStatement("select * from MAKUL inner join MAKUR on (IDKMKMAKUL=IDKMKMAKUR) where KDKMKMAKUL=? and IDKURMAKUR=?");
			/*
			 * cara baru nakmk cukup berdasarkan kdpst dan kdkmk (jadi sepertti primary key)
			 */
			stmt = con.prepareStatement("select * from MAKUL WHERE KDPSTMAKUL=? and  KDKMKMAKUL=?");
			li = v.listIterator();
			while(li.hasNext()) {
				String thsms=(String)li.next();
				String kdkmk=(String)li.next();
				String nlakh=(String)li.next();
				String bobot=(String)li.next();
				String sksmk=(String)li.next();
				String kelas=(String)li.next();
				String sksem=(String)li.next();
				String nlips=(String)li.next();
				String skstt=(String)li.next();
				String nlipk=(String)li.next();
				//stmt.setString(1,kdkmk);
				//stmt.setInt(2,Integer.valueOf(idkur).intValue());
				stmt.setString(1, kdpst);
				stmt.setString(2, kdkmk);
				rs = stmt.executeQuery();
				String nakmk = "KODE MATAKULIAH TIDAK ADA DI KURIKULUM,<br/> HARAP DICEK KEMBALI";
				if(rs.next()) {
					nakmk = rs.getString("NAKMKMAKUL");
				}
				lif.add(thsms);
				lif.add(kdkmk);
				lif.add(nakmk);
				lif.add(nlakh);
				lif.add(bobot);
				lif.add(sksmk);
				lif.add(kelas);
				lif.add(sksem);
				lif.add(nlips);
				lif.add(skstt);
				lif.add(nlipk);	
				
			}
			//System.out.println("vf size = "+vf.size());
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
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
		}	
    	return vf;
    }
    
    public Vector continuSistemAdjustment(String npmhs, Vector vTrlsm, Vector  vTrnlp, Vector vTrnlm, Vector vMakulKurikulum, String smawlMhs, String currentPa, String kode_kmp) {
    	//get krskhs nimhs = asnim
    	/*
    	 * KRS DIINPUT SESUAI DENGAN KO DARI SMAWL MPE AKHIR , KALO LEWAT DARI TOT THSMS KO MAKA BERIKUTNYA ADALAH MK FINAL 
    	 */
    			
    	String thsms_krs = Checker.getThsmsKrs(); // tidak thsms now krn thsms krs minimal == thsmow atao thsmsnow + 1
    	/*
    	 * STRUKTUR vTrnlm
    	 * 				litmp.add(thsms);
				litmp.add(kdkmk);
				litmp.add(nlakh);
				litmp.add(bobot);
				litmp.add(sksmk);
				litmp.add(kelas);
				litmp.add(sksem);
				litmp.add(nlips);
				litmp.add(skstt);
				litmp.add(nlipk);	
				litmp.add(shift);	
				litmp.add(krsdown);//tambahan baru
				litmp.add(khsdown);//tambahan baru
				litmp.add(bakprove);//tambahan baru
				litmp.add(paprove);//tambahan baru
				litmp.add(note);//tambahan baru
				litmp.add(lock);//tambahan baru
				litmp.add(baukprove);//tambahan baru
				//tambahan
				litmp.add(idkmk);
				litmp.add(addReq);
				litmp.add(drpReq);
				litmp.add(npmPa);
				litmp.add(npmBak);
				litmp.add(npmBaa);
				litmp.add(npmBauk);
				litmp.add(baaProve);
				litmp.add(ktuProve);
				litmp.add(dknProve);
				litmp.add(npmKtu);
				litmp.add(npmDekan);
				litmp.add(lockMhs);
				litmp.add(kodeKampus);
				litmp.add(cuid);
				litmp.add(npmdos);
				litmp.add(nodos);
				litmp.add(npmasdos);
				litmp.add(noasdos);
				litmp.add(nmmdos);
				litmp.add(nmmasdos); 
    	 */
    	
    	//struktur vTrnlp
		/*
		 * 
		li1.add(thsms);
		li1.add(kdkmk);
		li1.add(nakmk);
		li1.add(nlakh);
		li1.add(bobot);
		li1.add(kdasl);
		li1.add(nmasl);
		li1.add(""+(sksmk));
		li1.add(totSksTransfered+"");
		li1.add(sksas);
		li1.add(transferred);
		 */
    	
    	/*
    	 * struktur vMakulKur
    	 * 			li.add(""+idkmk);
    				li.add(kdkmk);
    				li.add(nakmk);
    				li.add(""+skstm);
    				li.add(""+skspr);
    				li.add(""+skslp);
    				li.add(kdwpl);
    				li.add(jenis);
    				li.add(stkmk);
    				li.add(nodos);
    				li.add(semes);
    	 */
    	
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	
    	try {
    		

    		String tkn_final_mk = null;
    		ListIterator likur = vMakulKurikulum.listIterator();
			/*
			 * section 1 : get MAKUL kelulusan (tesis, skripsi,dll)
			 */
			while(likur.hasNext()) {
				String idkmk = (String)likur.next();
				String kdkmk = (String)likur.next();
				String nakmk = (String)likur.next();
				String skstm = (String)likur.next();
				String skspr = (String)likur.next();
				String skslp = (String)likur.next();
				String kdwpl = (String)likur.next();
				String jenis = (String)likur.next();
				String stkmk = (String)likur.next();
				String nodos = (String)likur.next();
				//if(Checker.isStringNullOrEmpty(nodos)) {
				//	nodos = new String("null");
				//}
				String semes = (String)likur.next();
				String final_mk = (String)likur.next();
				if(final_mk.equalsIgnoreCase("true")) {
					String tmp = "`"+idkmk+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp+"`"+kdwpl+"`"+jenis+"`"+stkmk+"`"+nodos+"`"+semes;
					//tkn_final_mk=new String("`"+idkmk+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp+"`"+kdwpl+"`"+jenis+"`"+stkmk+"`"+nodos+"`"+semes);
					tkn_final_mk=new String(tmp.replace("``", "`null`"));//mata kuliah kelulusan
				}
				
			}	
			//System.out.println("tkn_final_mk="+tkn_final_mk);	
				
			likur = vMakulKurikulum.listIterator();
			String tkn_makul_kur = "";
			String smawl = new String(smawlMhs);
			if(likur.hasNext()) {
				//semester 1 = tidak mungkin ada isi di trlsm krn pasti aktif
				String idkmk = (String)likur.next();
				String kdkmk = (String)likur.next();
				String nakmk = (String)likur.next();
				String skstm = (String)likur.next();
				String skspr = (String)likur.next();
				String skslp = (String)likur.next();
				String kdwpl = (String)likur.next();
				String jenis = (String)likur.next();
				String stkmk = (String)likur.next();
				String nodos = (String)likur.next();
				//if(Checker.isStringNullOrEmpty(nodos)) {
				//	nodos = new String("null");
				//}
				String semes = (String)likur.next();
				String final_mk = (String)likur.next();
				
				tkn_makul_kur=tkn_makul_kur+"`"+smawl+"`"+idkmk+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp+"`"+kdwpl+"`"+jenis+"`"+stkmk+"`"+nodos+"`"+semes;
				do {
					String nu_idkmk = (String)likur.next();
					String nu_kdkmk = (String)likur.next();
					String nu_nakmk = (String)likur.next();
					String nu_skstm = (String)likur.next();
					String nu_skspr = (String)likur.next();
					String nu_skslp = (String)likur.next();
					String nu_kdwpl = (String)likur.next();
					String nu_jenis = (String)likur.next();
					String nu_stkmk = (String)likur.next();
					String nu_nodos = (String)likur.next();
					//if(Checker.isStringNullOrEmpty(nu_nodos)) {
					//	nu_nodos = new String("null");
					//}
					String nu_semes = (String)likur.next();
					String nu_final_mk = (String)likur.next();
					//System.out.println("nfk="+nu_final_mk);
					//System.out.println("semes="+semes);
					//System.out.println("nu_semes="+nu_semes);
					if(!semes.equalsIgnoreCase(nu_semes)) {
						//System.out.println("semes vs nu_semes = "+semes+" vs "+nu_semes);
						//harusnya semes sudah berurutan jadi bila berubah berarti naik 1
						semes = new String(nu_semes);
						//System.out.println("smawl="+smawl);
						smawl = Tool.returnNextThsmsGivenTpAntara(smawl);
						//cek TRLSM pada smawl yg baru
						if(vTrlsm!=null && vTrlsm.size()>0) {
							ListIterator litmp = vTrlsm.listIterator();
							//boolean match = false;
							while(litmp.hasNext()) {
								String brs = (String)litmp.next();
								StringTokenizer st = new StringTokenizer(brs,"`");
								String thsmsTrlms = st.nextToken();
								String stmhsTrlms = st.nextToken();
								//add smawl bila mhs c, n,d, k
								//keluar dan do juga ditambah karena tidak ada krs pada smawl dan kemudian krs > smawl dihapus
								if(thsmsTrlms.equalsIgnoreCase(smawl) && (stmhsTrlms.equalsIgnoreCase("C")||stmhsTrlms.equalsIgnoreCase("N")||stmhsTrlms.equalsIgnoreCase("K")||stmhsTrlms.equalsIgnoreCase("D"))) {
									smawl = Tool.returnNextThsmsGivenTpAntara(smawl);
								}
							}
						}
						
					}
					tkn_makul_kur=tkn_makul_kur+"`"+smawl+"`"+nu_idkmk+"`"+nu_kdkmk+"`"+nu_nakmk+"`"+nu_skstm+"`"+nu_skspr+"`"+nu_skslp+"`"+nu_kdwpl+"`"+nu_jenis+"`"+nu_stkmk+"`"+nu_nodos+"`"+nu_semes;
				}while(likur.hasNext());
				
			}
			//proses diatas adalah pengisian krs berdasarkan lama studi kurikulum
			//
			//proses dibawah melanjutkan bila setelah lewat lama studi kurikulum mhs masih belum lulus
			smawl = Tool.returnNextThsmsGivenTpAntara(smawl);
			//System.out.println("tkn_makul_kur1="+tkn_makul_kur);
			
			/*
			 * bila smawl < thsms krs berarti lewat dari masa waktu paket, maka yg tersisa hanya MK final
			 */
			if((smawl.compareToIgnoreCase(thsms_krs)<=0)) {
				do {
					
					if(vTrlsm!=null && vTrlsm.size()>0) {
						ListIterator litmp = vTrlsm.listIterator();
						//boolean match = false;
						while(litmp.hasNext()) {
							String brs = (String)litmp.next();
							//System.out.println("baris vTrlsm="+brs);
							StringTokenizer st = new StringTokenizer(brs,"`");
							String thsmsTrlms = st.nextToken();
							String stmhsTrlms = st.nextToken();
							//add smawl bila mhs c, n,d, k
							//keluar dan do juga ditambah karena tidak ada krs pada smawl dan kemudian krs > smawl dihapus
							if(thsmsTrlms.equalsIgnoreCase(smawl) && (stmhsTrlms.equalsIgnoreCase("C")||stmhsTrlms.equalsIgnoreCase("N")||stmhsTrlms.equalsIgnoreCase("K")||stmhsTrlms.equalsIgnoreCase("D"))) {
								smawl = Tool.returnNextThsmsGivenTpAntara(smawl);
							}
						}
						//tkn_makul_kur=tkn_makul_kur+"`"+smawl+tkn_final_mk;
						//isi krs final
					}
					
					//else {
						//isi krs final
					tkn_makul_kur=tkn_makul_kur+"`"+smawl+tkn_final_mk;
					smawl = Tool.returnNextThsmsGivenTpAntara(smawl);
						
					//}
					//System.out.println("smawl == "+smawl);
					
				}while((smawl.compareToIgnoreCase(thsms_krs)<=0));
				
			}
			//System.out.println("tkn_makul_kur2="+tkn_makul_kur);
			// remove makul pindahan
			tkn_makul_kur=tkn_makul_kur.replace("``", "`null`");
			if(vTrnlp!=null && vTrnlp.size()>0) {
				StringTokenizer st = new StringTokenizer(tkn_makul_kur,"`");
				//System.out.println("tkn_makul_kur tkn_makul_kur 11 = "+tkn_makul_kur);
				tkn_makul_kur = new String();
				while(st.hasMoreTokens()) {
					String nu_smawl = st.nextToken();
					String nu_idkmk = st.nextToken();
					String nu_kdkmk = st.nextToken();
					String nu_nakmk = st.nextToken();
					String nu_skstm = st.nextToken();
					String nu_skspr = st.nextToken();
					String nu_skslp = st.nextToken();
					String nu_kdwpl = st.nextToken();
					String nu_jenis = st.nextToken();
					String nu_stkmk = st.nextToken();
					String nu_nodos = st.nextToken();
					//if(Checker.isStringNullOrEmpty(nu_nodos)) {
					//	nu_nodos = new String("null");
					//}
					String nu_semes = st.nextToken();
					//System.out.println(nu_smawl+"`"+nu_idkmk+"`"+nu_kdkmk+"`"+nu_nakmk+"`"+nu_skstm+"`"+nu_skspr+"`"+nu_skslp+"`"+nu_kdwpl+"`"+nu_jenis+"`"+nu_stkmk+"`"+nu_nodos+"`"+nu_semes);
					
		
					
					ListIterator litmp = vTrnlp.listIterator();
					boolean match = false;
					while(litmp.hasNext() && !match) {
						String thsmsTrnlp = (String)litmp.next();
						String kdkmkTrnlp = (String)litmp.next();
						String nakmkTrnlp = (String)litmp.next();
						String nlakhTrnlp = (String)litmp.next();
						String bobotTrnlp = (String)litmp.next();
						String kdaslTrnlp = (String)litmp.next();
						String nmaslTrnlp = (String)litmp.next();
						String sksmkTrnlp = (String)litmp.next();
						String totSksTransferedTrnlp = (String)litmp.next();
						String sksasTrnlp = (String)litmp.next();
						String transferredTrnlp = (String)litmp.next();
						if(nu_kdkmk.equalsIgnoreCase(kdkmkTrnlp)) {
							match = true;
							//System.out.println("match="+match);
						}
					}
					if(!match) {
						tkn_makul_kur = tkn_makul_kur+nu_smawl+"`"+nu_idkmk+"`"+nu_kdkmk+"`"+nu_nakmk+"`"+nu_skstm+"`"+nu_skspr+"`"+nu_skslp+"`"+nu_kdwpl+"`"+nu_jenis+"`"+nu_stkmk+"`"+nu_nodos+"`"+nu_semes+"`";
					}
				}
				
			}
			/*
			 * kalo sudah ada di trnlm ignore, kalo blum ada insert ke trnlm
			 */
			//System.out.println("tkn_makul_kur3="+tkn_makul_kur);
			tkn_makul_kur = tkn_makul_kur.replace("``", "`null`");
			
			if(vTrnlm!=null && vTrnlm.size()>0) {
				StringTokenizer st = new StringTokenizer(tkn_makul_kur,"`");
				//System.out.println("tkn_makul_kur token 2 = "+tkn_makul_kur);
				 
				tkn_makul_kur = new String("`");
				while(st.hasMoreTokens()) {
					String nu_thsms = st.nextToken();
					String nu_idkmk = st.nextToken();
					String nu_kdkmk = st.nextToken();
					String nu_nakmk = st.nextToken();
					String nu_skstm = st.nextToken();
					String nu_skspr = st.nextToken();
					String nu_skslp = st.nextToken();
					String nu_kdwpl = st.nextToken();
					String nu_jenis = st.nextToken();
					String nu_stkmk = st.nextToken();
					String nu_nodos = st.nextToken();
					//if(Checker.isStringNullOrEmpty(nu_nodos)) {
					//	nu_nodos = new String("null");
					//}
					String nu_semes = st.nextToken();
					//System.out.println(nu_thsms+"`"+nu_idkmk+"`"+nu_kdkmk+"`"+nu_nakmk+"`"+nu_skstm+"`"+nu_skspr+"`"+nu_skslp+"`"+nu_kdwpl+"`"+nu_jenis+"`"+nu_stkmk+"`"+nu_nodos+"`"+nu_semes);
					//					20102`			1022`		MU1`		TEORI PEMBANGUNAN DALAM PEMERINTAHAN`3`			0`			0`				A`			0`			A`9903015147`120102
					
					
					String thsms_trnlm = "";
					String kdkmk_trnlm = "";
					String nakmk_trnlm = "";
					String nlakh_trnlm = "";
					String bobot_trnlm = "";
					String sksmk_trnlm = "";
					String kelas_trnlm = "";
					String sksem_trnlm = "";
					String nlips_trnlm = "";
					String skstt_trnlm = "";
					String nlipk_trnlm = "";	
					String shift_trnlm = "";	
					String krsdown_trnlm = "";//tambahan baru
					String khsdown_trnlm = "";//tambahan baru
					String bakprove_trnlm = "";//tambahan baru
					String paprove_trnlm = "";//tambahan baru
					String note_trnlm = "";//tambahan baru
					String lock_trnlm = "";//tambahan baru
					String baukprove_trnlm = "";//tambahan baru
					//tambahan
					String idkmk_trnlm = "";
					String addReq_trnlm = "";
					String drpReq_trnlm = "";
					String npmPa_trnlm = "";
					String npmBak_trnlm = "";
					String npmBaa_trnlm = "";
					String npmBauk_trnlm = "";
					String baaProve_trnlm = "";
					String ktuProve_trnlm = "";
					String dknProve_trnlm = "";
					String npmKtu_trnlm = "";
					String npmDekan_trnlm = "";
					String lockMhs_trnlm = "";
					String kodeKampus_trnlm = "";
					String cuid_trnlm = "";
					String cuid_init_trnlm = "";
					String npmdos_trnlm = "";
					String nodos_trnlm = "";
					String npmasdos_trnlm = "";
					String noasdos_trnlm = "";
					String nmmdos_trnlm = "";
					String nmmasdos_trnlm = ""; 
					

					//System.out.println("vTrnlm  "+vTrnlm);
					//System.out.println("vTrnlm ize "+vTrnlm.size());
					ListIterator litmp = vTrnlm.listIterator();
					boolean match = false;
					//System.out.println("nu_idkmk="+nu_idkmk);
					while(litmp.hasNext() && !match) {
						
						thsms_trnlm = (String)litmp.next();//1
						//System.out.println("thsms_trnlm ize "+thsms_trnlm);
						kdkmk_trnlm = (String)litmp.next();//2
						nakmk_trnlm = (String)litmp.next();//3
						//System.out.println("kdkmk_trnlm ize "+kdkmk_trnlm);
						nlakh_trnlm = (String)litmp.next();//4
						bobot_trnlm = (String)litmp.next();//5
						sksmk_trnlm = (String)litmp.next();//6
						kelas_trnlm = (String)litmp.next();//7
						sksem_trnlm = (String)litmp.next();//8
						nlips_trnlm = (String)litmp.next();//9
						skstt_trnlm = (String)litmp.next();//10
						nlipk_trnlm = (String)litmp.next();//11	
						shift_trnlm = (String)litmp.next();//12	
						krsdown_trnlm = (String)litmp.next();//13
						khsdown_trnlm = (String)litmp.next();//14
						bakprove_trnlm = (String)litmp.next();//15
						paprove_trnlm = (String)litmp.next();//16
						note_trnlm = (String)litmp.next();//17
						lock_trnlm = (String)litmp.next();//18
						baukprove_trnlm = (String)litmp.next();//19
						//tambahan
						idkmk_trnlm = (String)litmp.next();//20
						addReq_trnlm = (String)litmp.next();//21
						drpReq_trnlm = (String)litmp.next();//22
						npmPa_trnlm = (String)litmp.next();//23
						npmBak_trnlm = (String)litmp.next();//24
						npmBaa_trnlm = (String)litmp.next();//25
						npmBauk_trnlm = (String)litmp.next();//26
						baaProve_trnlm = (String)litmp.next();//27
						ktuProve_trnlm = (String)litmp.next();//28
						dknProve_trnlm = (String)litmp.next();//29
						npmKtu_trnlm = (String)litmp.next();//30
						npmDekan_trnlm = (String)litmp.next();//31
						lockMhs_trnlm = (String)litmp.next();//32
						kodeKampus_trnlm = (String)litmp.next();//33
						cuid_trnlm = (String)litmp.next();//34
						cuid_init_trnlm = (String)litmp.next();//35
						npmdos_trnlm = (String)litmp.next();//36
						nodos_trnlm = (String)litmp.next();//37
						npmasdos_trnlm = (String)litmp.next();//38
						noasdos_trnlm = (String)litmp.next();//39
						nmmdos_trnlm = (String)litmp.next();//40
						nmmasdos_trnlm = (String)litmp.next();//41
						//cuidKampusTrnlm = (String)litmp.next();
						//System.out.println("nu_idkmk="+nu_idkmk+" vs "+idkmk_trnlm );
						//System.out.println("nu_thsms="+nu_thsms+" vs "+thsms_trnlm );
						if(nu_idkmk.equalsIgnoreCase(idkmk_trnlm) && nu_thsms.equalsIgnoreCase(thsms_trnlm)) {
							match = true;
							//System.out.println("match="+match);
						}
					}
					if(!match) {
						//System.out.println("no_match="+match);
						lif.add(nu_thsms);//1
						lif.add(nu_kdkmk);//2
						lif.add(nu_nakmk);//3
						lif.add("T");//4
						lif.add("0");//5
						if(Checker.isStringNullOrEmpty(nu_skstm)) {
							nu_skstm = "0";
						}
						if(Checker.isStringNullOrEmpty(nu_skspr)) {
							nu_skspr = "0";
						}
						if(Checker.isStringNullOrEmpty(nu_skslp)) {
							nu_skslp = "0";
						}
						lif.add(""+(Integer.parseInt(nu_skstm)+Integer.parseInt(nu_skspr)+Integer.parseInt(nu_skslp)));//6
						lif.add("null");//7
						lif.add("0");//8
						lif.add("0");//9
						lif.add("0");//10
						lif.add("0");//11	
						lif.add("N/A");//12	
						lif.add("false");//13
						lif.add("false");//14
						lif.add("false");//15
						lif.add("false");//16
						lif.add("null");//17
						lif.add("false");//18
						lif.add("false");//19
						//tambahan
						if(Checker.isStringNullOrEmpty(nu_idkmk)) {
							nu_idkmk = "null";
						}
						lif.add(nu_idkmk);//20
						lif.add("false");//21
						lif.add("false");//22
						//String currentPa, String tknPaInfo
						lif.add(""+currentPa);//23
						lif.add("null");//24
						lif.add("null");//25
						lif.add("null");//26
						lif.add("false");//27
						lif.add("false");//28
						lif.add("false");//29
						lif.add("null");//30
						lif.add("null");//31
						lif.add("true");//32
						lif.add(kode_kmp);//33
						lif.add("null");//34
						lif.add("null");//35
						lif.add("null");//36
						lif.add("null");//37
						lif.add("null");//38
						lif.add("null");//39
						lif.add("null");//40
						lif.add("null");//41
						
					}
				}
				
			}
			else {
				StringTokenizer st = new StringTokenizer(tkn_makul_kur,"`");
				//System.out.println("tkn_makul_kur token 3 = "+st.countTokens());
				 
				tkn_makul_kur = new String("`");
				while(st.hasMoreTokens()) {
					String nu_thsms = st.nextToken();
					String nu_idkmk = st.nextToken();
					String nu_kdkmk = st.nextToken();
					String nu_nakmk = st.nextToken();
					String nu_skstm = st.nextToken();
					String nu_skspr = st.nextToken();
					String nu_skslp = st.nextToken();
					String nu_kdwpl = st.nextToken();
					String nu_jenis = st.nextToken();
					String nu_stkmk = st.nextToken();
					String nu_nodos = st.nextToken();
					String nu_semes = st.nextToken();
					//System.out.println(nu_thsms+"`"+nu_idkmk+"`"+nu_kdkmk+"`"+nu_nakmk+"`"+nu_skstm+"`"+nu_skspr+"`"+nu_skslp+"`"+nu_kdwpl+"`"+nu_jenis+"`"+nu_stkmk+"`"+nu_nodos+"`"+nu_semes);
					lif.add(nu_thsms);//1
					if(Checker.isStringNullOrEmpty(nu_thsms)) {
						System.out.println("empty mbo");
					}
					lif.add(nu_kdkmk);//2
					lif.add(nu_nakmk);//3
					lif.add("T");//4
					lif.add("0");//5
					lif.add(""+(Integer.parseInt(nu_skstm)+Integer.parseInt(nu_skspr)+Integer.parseInt(nu_skslp)));//6
					lif.add("null");//7
					lif.add("0");//8
					lif.add("0");//9
					lif.add("0");//10
					lif.add("0");//11	
					lif.add("N/A");//12	
					lif.add("false");//13
					lif.add("false");//14
					lif.add("false");//15
					lif.add("false");//16
					lif.add("null");//17
					lif.add("false");//18
					lif.add("false");//19
					//tambahan
					lif.add(nu_idkmk);//20
					lif.add("false");//21
					lif.add("false");//22
					//String currentPa, String tknPaInfo
					lif.add(""+currentPa);//23
					lif.add("null");//24
					lif.add("null");//25
					lif.add("null");//26
					lif.add("null");//27
					lif.add("null");//28
					lif.add("null");//29
					lif.add("null");//30
					lif.add("null");//31
					lif.add("true");//32
					lif.add(kode_kmp);//33
					lif.add("null");//34
					lif.add("null");//35
					lif.add("null");//36
					lif.add("null");//37
					lif.add("null");//38
					lif.add("null");//39
					lif.add("null");//40
					lif.add("null");//41
				}	
			}
			
			//
			//System.out.println("vf size = "+vf.size());
    	}
    	catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
		}	
    	return vf;
    }
    
    public Vector continuSistemAdjustment_ori(String npmhs, Vector vTrlsm, Vector  vTrnlp, Vector vTrnlm, Vector vMakulKurikulum, String smawlMhs, String currentPa, String kode_kmp) {
    	//get krskhs nimhs = asnim
    	String thsms_krs = Checker.getThsmsKrs(); // tidak thsms now krn thsms krs minimal == thsmow atao thsmsnow + 1
    	/*
    	 * STRUKTUR vTrnlm
    	 * 				lif.add(thsms);
						lif.add(kdkmk);
						lif.add(nakmk);
						lif.add(nlakh);
						lif.add(bobot);
						lif.add(sksmk);
						lif.add(kelas);
						lif.add(sksem);
						lif.add(nlips);
						lif.add(skstt);
						lif.add(nlipk);	
						lif.add(shift);	
						lif.add(krsdown);//tambahan baru
						lif.add(khsdown);//tambahan baru
						lif.add(bakprove);//tambahan baru
						lif.add(paprove);//tambahan baru
						lif.add(note);//tambahan baru
						lif.add(lock);//tambahan baru
						lif.add(baukprove);//tambahan baru
						//tambahan
						lif.add(idkmk);
						lif.add(addReq);
						lif.add(drpReq);
						lif.add(npmPa);
						lif.add(npmBak);
						lif.add(npmBaa);
						lif.add(npmBauk);
						lif.add(baaProve);
						lif.add(ktuProve);
						lif.add(dknProve);
						lif.add(npmKtu);
						lif.add(npmDekan);
						lif.add(lockMhs);
						lif.add(kodeKampus);
    	 */
    	
    	//struktur vTrnlp
		/*
		 * 
		li1.add(thsms);
		li1.add(kdkmk);
		li1.add(nakmk);
		li1.add(nlakh);
		li1.add(bobot);
		li1.add(kdasl);
		li1.add(nmasl);
		li1.add(""+(sksmk));
		li1.add(totSksTransfered+"");
		li1.add(sksas);
		li1.add(transferred);
		 */
    	
    	/*
    	 * struktur vMakulKur
    	 * 			li.add(""+idkmk);
    				li.add(kdkmk);
    				li.add(nakmk);
    				li.add(""+skstm);
    				li.add(""+skspr);
    				li.add(""+skslp);
    				li.add(kdwpl);
    				li.add(jenis);
    				li.add(stkmk);
    				li.add(nodos);
    				li.add(semes);
    	 */
    	
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	
    	try {
    		
    		/*
    		//System.out.println("smawlmhs="+smawlMhs);
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt=con.prepareStatement("select * from TRLSM where NPMHS=? order by THSMS");
			stmt.setString(1, npmhs);
			rs = stmt.executeQuery();
			Vector vTrlsm = null;
			if(rs.next()) {
				vTrlsm = new Vector();
				ListIterator litmp = vTrlsm.listIterator();
				do {
					String thsmsTrlsm = ""+rs.getString("THSMS");
					String stmhsTrlsm = ""+rs.getString("STMHS");
					litmp.add(thsmsTrlsm+"`"+stmhsTrlsm);
				}while(rs.next());
			}
			*/
    		String tkn_final_mk = null;
    		ListIterator likur = vMakulKurikulum.listIterator();
			/*
			 * section 1 : get MAKUL kelulusan (tesis, skripsi,dll)
			 */
			while(likur.hasNext()) {
				String idkmk = (String)likur.next();
				String kdkmk = (String)likur.next();
				String nakmk = (String)likur.next();
				String skstm = (String)likur.next();
				String skspr = (String)likur.next();
				String skslp = (String)likur.next();
				String kdwpl = (String)likur.next();
				String jenis = (String)likur.next();
				String stkmk = (String)likur.next();
				String nodos = (String)likur.next();
				//if(Checker.isStringNullOrEmpty(nodos)) {
				//	nodos = new String("null");
				//}
				String semes = (String)likur.next();
				String final_mk = (String)likur.next();
				if(final_mk.equalsIgnoreCase("true")) {
					String tmp = "`"+idkmk+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp+"`"+kdwpl+"`"+jenis+"`"+stkmk+"`"+nodos+"`"+semes;
					//tkn_final_mk=new String("`"+idkmk+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp+"`"+kdwpl+"`"+jenis+"`"+stkmk+"`"+nodos+"`"+semes);
					tkn_final_mk=new String(tmp.replace("``", "`null`"));//mata kuliah kelulusan
				}
				
			}	
			//System.out.println("tkn_final_mk="+tkn_final_mk);	
				
			likur = vMakulKurikulum.listIterator();
			String tkn_makul_kur = "";
			String smawl = new String(smawlMhs);
			
			if(likur.hasNext()) {
				//semester 1 = tidak mungkin ada isi di trlsm krn pasti aktif
				String idkmk = (String)likur.next();
				String kdkmk = (String)likur.next();
				String nakmk = (String)likur.next();
				String skstm = (String)likur.next();
				String skspr = (String)likur.next();
				String skslp = (String)likur.next();
				String kdwpl = (String)likur.next();
				String jenis = (String)likur.next();
				String stkmk = (String)likur.next();
				String nodos = (String)likur.next();
				//if(Checker.isStringNullOrEmpty(nodos)) {
				//	nodos = new String("null");
				//}
				String semes = (String)likur.next();
				String final_mk = (String)likur.next();
				
				tkn_makul_kur=tkn_makul_kur+"`"+smawl+"`"+idkmk+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp+"`"+kdwpl+"`"+jenis+"`"+stkmk+"`"+nodos+"`"+semes;
				do {
					String nu_idkmk = (String)likur.next();
					String nu_kdkmk = (String)likur.next();
					String nu_nakmk = (String)likur.next();
					String nu_skstm = (String)likur.next();
					String nu_skspr = (String)likur.next();
					String nu_skslp = (String)likur.next();
					String nu_kdwpl = (String)likur.next();
					String nu_jenis = (String)likur.next();
					String nu_stkmk = (String)likur.next();
					String nu_nodos = (String)likur.next();
					//if(Checker.isStringNullOrEmpty(nu_nodos)) {
					//	nu_nodos = new String("null");
					//}
					String nu_semes = (String)likur.next();
					String nu_final_mk = (String)likur.next();
					//System.out.println("nfk="+nu_final_mk);
					//System.out.println("semes="+semes);
					//System.out.println("nu_semes="+nu_semes);
					if(!semes.equalsIgnoreCase(nu_semes)) {
						//System.out.println("semes vs nu_semes = "+semes+" vs "+nu_semes);
						//harusnya semes sudah berurutan jadi bila berubah berarti naik 1
						semes = new String(nu_semes);
						//System.out.println("smawl="+smawl);
						smawl = Tool.returnNextThsmsGivenTpAntara(smawl);
						//cek TRLSM pada smawl yg baru
						if(vTrlsm!=null && vTrlsm.size()>0) {
							ListIterator litmp = vTrlsm.listIterator();
							//boolean match = false;
							while(litmp.hasNext()) {
								String brs = (String)litmp.next();
								StringTokenizer st = new StringTokenizer(brs,"`");
								String thsmsTrlms = st.nextToken();
								String stmhsTrlms = st.nextToken();
								//add smawl bila mhs c, n,d, k
								//keluar dan do juga ditambah karena tidak ada krs pada smawl dan kemudian krs > smawl dihapus
								if(thsmsTrlms.equalsIgnoreCase(smawl) && (stmhsTrlms.equalsIgnoreCase("C")||stmhsTrlms.equalsIgnoreCase("N")||stmhsTrlms.equalsIgnoreCase("K")||stmhsTrlms.equalsIgnoreCase("D"))) {
									smawl = Tool.returnNextThsmsGivenTpAntara(smawl);
								}
							}
						}
						
					}
					tkn_makul_kur=tkn_makul_kur+"`"+smawl+"`"+nu_idkmk+"`"+nu_kdkmk+"`"+nu_nakmk+"`"+nu_skstm+"`"+nu_skspr+"`"+nu_skslp+"`"+nu_kdwpl+"`"+nu_jenis+"`"+nu_stkmk+"`"+nu_nodos+"`"+nu_semes;
				}while(likur.hasNext());
				
			}
			//proses diatas adalah pengisian krs berdasarkan lama studi kurikulum
			//
			//proses dibawah melanjutkan bila setelah lewat lama studi kurikulum mhs masih belum lulus
			smawl = Tool.returnNextThsmsGivenTpAntara(smawl);
			//System.out.println("tkn_makul_kur1="+tkn_makul_kur);
			
			/*
			 * bila smawl < thsms krs berarti lewat dari masa waktu paket, maka yg tersisa hanya MK final
			 */
			if((smawl.compareToIgnoreCase(thsms_krs)<=0)) {
				do {
					
					if(vTrlsm!=null && vTrlsm.size()>0) {
						ListIterator litmp = vTrlsm.listIterator();
						//boolean match = false;
						while(litmp.hasNext()) {
							String brs = (String)litmp.next();
							//System.out.println("baris vTrlsm="+brs);
							StringTokenizer st = new StringTokenizer(brs,"`");
							String thsmsTrlms = st.nextToken();
							String stmhsTrlms = st.nextToken();
							//add smawl bila mhs c, n,d, k
							//keluar dan do juga ditambah karena tidak ada krs pada smawl dan kemudian krs > smawl dihapus
							if(thsmsTrlms.equalsIgnoreCase(smawl) && (stmhsTrlms.equalsIgnoreCase("C")||stmhsTrlms.equalsIgnoreCase("N")||stmhsTrlms.equalsIgnoreCase("K")||stmhsTrlms.equalsIgnoreCase("D"))) {
								smawl = Tool.returnNextThsmsGivenTpAntara(smawl);
							}
						}
						//tkn_makul_kur=tkn_makul_kur+"`"+smawl+tkn_final_mk;
						//isi krs final
					}
					
					//else {
						//isi krs final
					tkn_makul_kur=tkn_makul_kur+"`"+smawl+tkn_final_mk;
					smawl = Tool.returnNextThsmsGivenTpAntara(smawl);
						
					//}
					//System.out.println("smawl == "+smawl);
					
				}while((smawl.compareToIgnoreCase(thsms_krs)<=0));
				
			}
			//System.out.println("tkn_makul_kur2="+tkn_makul_kur);
			// remove makul pindahan
			tkn_makul_kur=tkn_makul_kur.replace("``", "`null`");
			if(vTrnlp!=null && vTrnlp.size()>0) {
				StringTokenizer st = new StringTokenizer(tkn_makul_kur,"`");
				//System.out.println("tkn_makul_kur tkn_makul_kur 11 = "+tkn_makul_kur);
				tkn_makul_kur = new String();
				while(st.hasMoreTokens()) {
					String nu_smawl = st.nextToken();
					String nu_idkmk = st.nextToken();
					String nu_kdkmk = st.nextToken();
					String nu_nakmk = st.nextToken();
					String nu_skstm = st.nextToken();
					String nu_skspr = st.nextToken();
					String nu_skslp = st.nextToken();
					String nu_kdwpl = st.nextToken();
					String nu_jenis = st.nextToken();
					String nu_stkmk = st.nextToken();
					String nu_nodos = st.nextToken();
					//if(Checker.isStringNullOrEmpty(nu_nodos)) {
					//	nu_nodos = new String("null");
					//}
					String nu_semes = st.nextToken();
					//System.out.println(nu_smawl+"`"+nu_idkmk+"`"+nu_kdkmk+"`"+nu_nakmk+"`"+nu_skstm+"`"+nu_skspr+"`"+nu_skslp+"`"+nu_kdwpl+"`"+nu_jenis+"`"+nu_stkmk+"`"+nu_nodos+"`"+nu_semes);
					
		
					
					ListIterator litmp = vTrnlp.listIterator();
					boolean match = false;
					while(litmp.hasNext() && !match) {
						String thsmsTrnlp = (String)litmp.next();
						String kdkmkTrnlp = (String)litmp.next();
						String nakmkTrnlp = (String)litmp.next();
						String nlakhTrnlp = (String)litmp.next();
						String bobotTrnlp = (String)litmp.next();
						String kdaslTrnlp = (String)litmp.next();
						String nmaslTrnlp = (String)litmp.next();
						String sksmkTrnlp = (String)litmp.next();
						String totSksTransferedTrnlp = (String)litmp.next();
						String sksasTrnlp = (String)litmp.next();
						String transferredTrnlp = (String)litmp.next();
						if(nu_kdkmk.equalsIgnoreCase(kdkmkTrnlp)) {
							match = true;
							//System.out.println("match="+match);
						}
					}
					if(!match) {
						tkn_makul_kur = tkn_makul_kur+nu_smawl+"`"+nu_idkmk+"`"+nu_kdkmk+"`"+nu_nakmk+"`"+nu_skstm+"`"+nu_skspr+"`"+nu_skslp+"`"+nu_kdwpl+"`"+nu_jenis+"`"+nu_stkmk+"`"+nu_nodos+"`"+nu_semes+"`";
					}
				}
				
			}
			/*
			 * kalo sudah ada di trnlm ignore, kalo blum ada insert ke trnlm
			 */
			//System.out.println("tkn_makul_kur3="+tkn_makul_kur);
			tkn_makul_kur = tkn_makul_kur.replace("``", "`null`");
			
			if(vTrnlm!=null && vTrnlm.size()>0) {
				StringTokenizer st = new StringTokenizer(tkn_makul_kur,"`");
				//System.out.println("tkn_makul_kur token 2 = "+tkn_makul_kur);
				 
				tkn_makul_kur = new String("`");
				while(st.hasMoreTokens()) {
					String nu_thsms = st.nextToken();
					String nu_idkmk = st.nextToken();
					String nu_kdkmk = st.nextToken();
					String nu_nakmk = st.nextToken();
					String nu_skstm = st.nextToken();
					String nu_skspr = st.nextToken();
					String nu_skslp = st.nextToken();
					String nu_kdwpl = st.nextToken();
					String nu_jenis = st.nextToken();
					String nu_stkmk = st.nextToken();
					String nu_nodos = st.nextToken();
					//if(Checker.isStringNullOrEmpty(nu_nodos)) {
					//	nu_nodos = new String("null");
					//}
					String nu_semes = st.nextToken();
					//System.out.println(nu_thsms+"`"+nu_idkmk+"`"+nu_kdkmk+"`"+nu_nakmk+"`"+nu_skstm+"`"+nu_skspr+"`"+nu_skslp+"`"+nu_kdwpl+"`"+nu_jenis+"`"+nu_stkmk+"`"+nu_nodos+"`"+nu_semes);
					//					20102`			1022`		MU1`		TEORI PEMBANGUNAN DALAM PEMERINTAHAN`3`			0`			0`				A`			0`			A`9903015147`120102
					String thsmsTrnlm = "";
					String kdkmkTrnlm ="";
					String nakmkTrnlm ="";
					String nlakhTrnlm ="";
					String bobotTrnlm ="";
					String sksmkTrnlm ="";
					String kelasTrnlm ="";
					String sksemTrnlm ="";
					String nlipsTrnlm ="";
					String sksttTrnlm ="";
					String nlipkTrnlm ="";	
					String shiftTrnlm ="";	
					String krsdownTrnlm ="";//tambahan baru
					String khsdownTrnlm ="";//tambahan baru
					String bakproveTrnlm ="";//tambahan baru
					String paproveTrnlm ="";//tambahan baru
					String noteTrnlm ="";//tambahan baru
					String lockTrnlm ="";//tambahan baru
					String baukproveTrnlm ="";//tambahan baru
					//tambahan
					String idkmkTrnlm ="";
					String addReqTrnlm ="";
					String drpReqTrnlm ="";
					String npmPaTrnlm ="";
					String npmBakTrnlm ="";
					String npmBaaTrnlm ="";
					String npmBaukTrnlm ="";
					String baaProveTrnlm ="";
					String ktuProveTrnlm ="";
					String dknProveTrnlm ="";
					String npmKtuTrnlm ="";
					String npmDekanTrnlm ="";
					String lockMhsTrnlm ="";
					String kodeKampusTrnlm ="";
					//String cuidKampusTrnlm ="";
					
					ListIterator litmp = vTrnlm.listIterator();
					boolean match = false;
					while(litmp.hasNext() && !match) {
						thsmsTrnlm = (String)litmp.next();
						kdkmkTrnlm = (String)litmp.next();
						nakmkTrnlm = (String)litmp.next();
						nlakhTrnlm = (String)litmp.next();
						bobotTrnlm = (String)litmp.next();
						sksmkTrnlm = (String)litmp.next();
						kelasTrnlm = (String)litmp.next();
						sksemTrnlm = (String)litmp.next();
						nlipsTrnlm = (String)litmp.next();
						sksttTrnlm = (String)litmp.next();
						nlipkTrnlm = (String)litmp.next();	
						shiftTrnlm = (String)litmp.next();	
						krsdownTrnlm = (String)litmp.next();//tambahan baru
						khsdownTrnlm = (String)litmp.next();//tambahan baru
						bakproveTrnlm = (String)litmp.next();//tambahan baru
						paproveTrnlm = (String)litmp.next();//tambahan baru
						noteTrnlm = (String)litmp.next();//tambahan baru
						lockTrnlm = (String)litmp.next();//tambahan baru
						baukproveTrnlm = (String)litmp.next();//tambahan baru
						//tambahan
						idkmkTrnlm = (String)litmp.next();
						addReqTrnlm = (String)litmp.next();
						drpReqTrnlm = (String)litmp.next();
						npmPaTrnlm = (String)litmp.next();
						npmBakTrnlm = (String)litmp.next();
						npmBaaTrnlm = (String)litmp.next();
						npmBaukTrnlm = (String)litmp.next();
						baaProveTrnlm = (String)litmp.next();
						ktuProveTrnlm = (String)litmp.next();
						dknProveTrnlm = (String)litmp.next();
						npmKtuTrnlm = (String)litmp.next();
						npmDekanTrnlm = (String)litmp.next();
						lockMhsTrnlm = (String)litmp.next();
						kodeKampusTrnlm = (String)litmp.next();
						//cuidKampusTrnlm = (String)litmp.next();
						//System.out.println("nu_idkmk="+nu_idkmk+" vs "+idkmkTrnlm );
						//System.out.println("nu_thsms="+nu_thsms+" vs "+thsmsTrnlm );
						if(nu_idkmk.equalsIgnoreCase(idkmkTrnlm) && nu_thsms.equalsIgnoreCase(thsmsTrnlm)) {
							match = true;
							//System.out.println("match="+match);
						}
					}
					if(!match) {
						//System.out.println("no_match="+match);
						lif.add(nu_thsms);
						lif.add(nu_kdkmk);
						lif.add(nu_nakmk);
						lif.add("T");
						lif.add("0");
						//System.out.println("nu_thsms="+nu_thsms);
						//System.out.println("nu_kdkmk="+nu_kdkmk);
						//System.out.println("nu_nakmk="+nu_nakmk);
						
						//System.out.println("nu_skstm="+nu_skstm);
						//System.out.println("nu_skspr="+nu_skspr);
						//System.out.println("nu_skslp="+nu_skslp);
						lif.add(""+(Integer.parseInt(nu_skstm)+Integer.parseInt(nu_skspr)+Integer.parseInt(nu_skslp)));
						lif.add("null");
						lif.add("0");
						lif.add("0");
						lif.add("0");
						lif.add("0");	
						lif.add("N/A");	
						lif.add("false");//tambahan baru
						lif.add("false");//tambahan baru
						lif.add("false");//tambahan baru
						lif.add("false");//tambahan baru
						lif.add("null");//tambahan baru
						lif.add("false");//tambahan baru
						lif.add("false");//tambahan baru
						//tambahan
						lif.add(nu_idkmk);
						lif.add("false");
						lif.add("false");
						//String currentPa, String tknPaInfo
						lif.add(""+currentPa);
						lif.add("null");
						lif.add("null");
						lif.add("null");
						lif.add("null");
						lif.add("null");
						lif.add("null");
						lif.add("null");
						lif.add("null");
						lif.add("true");
						lif.add(kode_kmp);
					}
				}
				
			}
			else {
				StringTokenizer st = new StringTokenizer(tkn_makul_kur,"`");
				//System.out.println("tkn_makul_kur token 3 = "+st.countTokens());
				 
				tkn_makul_kur = new String("`");
				while(st.hasMoreTokens()) {
					String nu_thsms = st.nextToken();
					String nu_idkmk = st.nextToken();
					String nu_kdkmk = st.nextToken();
					String nu_nakmk = st.nextToken();
					String nu_skstm = st.nextToken();
					String nu_skspr = st.nextToken();
					String nu_skslp = st.nextToken();
					String nu_kdwpl = st.nextToken();
					String nu_jenis = st.nextToken();
					String nu_stkmk = st.nextToken();
					String nu_nodos = st.nextToken();
					String nu_semes = st.nextToken();
					//System.out.println(nu_thsms+"`"+nu_idkmk+"`"+nu_kdkmk+"`"+nu_nakmk+"`"+nu_skstm+"`"+nu_skspr+"`"+nu_skslp+"`"+nu_kdwpl+"`"+nu_jenis+"`"+nu_stkmk+"`"+nu_nodos+"`"+nu_semes);
					lif.add(nu_thsms);
					lif.add(nu_kdkmk);
					lif.add(nu_nakmk);
					lif.add("T");
					lif.add("0");
					lif.add(""+(Integer.parseInt(nu_skstm)+Integer.parseInt(nu_skspr)+Integer.parseInt(nu_skslp)));
					lif.add("null");
					lif.add("0");
					lif.add("0");
					lif.add("0");
					lif.add("0");	
					lif.add("N/A");	
					lif.add("false");//tambahan baru
					lif.add("false");//tambahan baru
					lif.add("false");//tambahan baru
					lif.add("false");//tambahan baru
					lif.add("null");//tambahan baru
					lif.add("false");//tambahan baru
					lif.add("false");//tambahan baru
					//tambahan
					lif.add(nu_idkmk);
					lif.add("false");
					lif.add("false");
					//String currentPa, String tknPaInfo
					lif.add(""+currentPa);
					lif.add("null");
					lif.add("null");
					lif.add("null");
					lif.add("null");
					lif.add("null");
					lif.add("null");
					lif.add("null");
					lif.add("null");
					lif.add("true");
					lif.add(kode_kmp);
				}	
			}
			
			//
			//System.out.println("vf size = "+vf.size());
    	}
    	catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
		}	
    	return vf;
    }
    
    
    
    public Vector getRiwayatTrlsm(String npmhs) {
    	Vector vTrlsm = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt=con.prepareStatement("select * from TRLSM where NPMHS=? and THSMS<>'0' order by THSMS");
			stmt.setString(1, npmhs);
			rs = stmt.executeQuery();
			vTrlsm = null;
			if(rs.next()) {
				vTrlsm = new Vector();
				ListIterator litmp = vTrlsm.listIterator();
				do {
					String thsmsTrlsm = ""+rs.getString("THSMS");
					String stmhsTrlsm = ""+rs.getString("STMHS");
					litmp.add(thsmsTrlsm+"`"+stmhsTrlsm);
				}while(rs.next());
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
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
		}	
    	return vTrlsm;
    }
    
    public Vector getMatakuliahYgBisaDiSetarakan(String kdpst, String npmhs) {
    	/*
    	 * fungsi ini mengembalikan list Matakuliah yg sudah diambil namun tidak ada di KURIKULUM yg sedang npmhs tempuh
    	 * step:
    	 * 1.combine list makul dari trnlp dan trnlm
    	 */
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	Vector v2 = new Vector();
    	try {
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//step 1 === get all makul dari trnlp dan trnlm
    		stmt = con.prepareStatement("select * from TRNLP inner join MAKUL on KDKMKTRNLP=KDKMKMAKUL where KDPSTTRNLP=? and NPMHSTRNLP=? order by THSMSTRNLP");
    		stmt.setString(1, kdpst);
    		stmt.setString(2, npmhs);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String thsms = rs.getString("THSMSTRNLP");
    			String kdkmk = rs.getString("KDKMKTRNLP");
    			String nakmk = rs.getString("NAKMKMAKUL");
    			int sksmk = rs.getInt("SKSMKTRNLP");
    			li.add("trnlp,"+thsms+","+kdkmk+","+nakmk+","+sksmk);
    		}
    		stmt = con.prepareStatement("select * from TRNLM inner join MAKUL on KDKMKTRNLM=KDKMKMAKUL where KDPSTTRNLM=? and NPMHSTRNLM=? order by THSMSTRNLM");
    		stmt.setString(1, kdpst);
    		stmt.setString(2, npmhs);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String thsms = rs.getString("THSMSTRNLM");
    			String kdkmk = rs.getString("KDKMKTRNLM");
    			String nakmk = rs.getString("NAKMKMAKUL");
    			int sksmk = rs.getInt("SKSMKTRNLM");
    			li.add("trnlm,"+thsms+","+kdkmk+","+nakmk+","+sksmk);
    		}
    		//================end step1=========================================
    		String idkur = getIndividualKurikulum(kdpst, npmhs);
    		Vector v1 = getListMatakuliahDalamKurikulum(kdpst,idkur);
    		//===end step2=================================================
    		
    		//====step 3 =================================
    		/*
    		 * filter makul yg sudah diambil tapi tidak ada di KOnya sekarang
    		 */
    		
    		ListIterator li2 = v2.listIterator();
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String baris = (String)li.next();
    			StringTokenizer st = new StringTokenizer(baris,",");
    			String tabel = st.nextToken();
    			String thsms = st.nextToken();
    			String kdkmk = st.nextToken();
    			String nakmk = st.nextToken();
    			String sksmk = st.nextToken();
    			boolean match = false;
    			ListIterator li1 = v1.listIterator();
        		while(li1.hasNext() && !match) {
        			String idkmk_ = (String)li1.next();
    				String kdkmk_ = (String)li1.next();
    				String nakmk_ = (String)li1.next();
    				String skstm_ = (String)li1.next();
    				String skspr_ = (String)li1.next();
    				String skslp_ = (String)li1.next();
    				String kdwpl_ = (String)li1.next();
    				String jenis_ = (String)li1.next();
    				String stkmk_ = (String)li1.next();
    				String nodos_ = (String)li1.next();
    				String semes_ = (String)li1.next();
    				if(kdkmk.equalsIgnoreCase(kdkmk_)) {
    					match = true;
    				}
        		}    		
    			
        		if(!match) {
        			li2.add(tabel+","+thsms+","+kdkmk+","+nakmk+","+sksmk);
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
    	return v2;
    }
    
    public Vector getInfoFromForecast_1(String kdpst) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	String thsmsForcast = Tool.returnNextThsmsGiven(getThsmsAktif());
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//get
    		stmt = con.prepareStatement("select * from FORECAST_1 where THSMSFCAST=? and KDPSTFCAST=? and TTMHSFCAST > 0 order by NAKMKFCAST");
    		stmt.setString(1, thsmsForcast);
    		stmt.setString(2, kdpst);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			int idkmk = rs.getInt("IDKMKFCAST");
    			String kdkmk = rs.getString("KDKMKFCAST");
    			String nakmk = rs.getString("NAKMKFCAST");
    			int sksmk = rs.getInt("SKSMKFCAST");
    			String npmhs = rs.getString("NPMHSFCAST");
    			li.add(thsmsForcast+"#"+kdpst+"#"+idkmk+"#"+kdkmk+"#"+nakmk+"#"+sksmk+"#"+npmhs);
    			//if(kdpst.equalsIgnoreCase("74201")) {
				//	//System.out.println("sdb "+kdpst+"#"+idkmk+"#"+kdkmk+"#"+nakmk+"#"+sksmk+"#"+npmhs);
				//}
    		}
    		//li = v.listIterator();
    		//while(li.hasNext()) {
    		//	String baris = (String)li.next();
    		//	//System.out.println(baris);
    		//}
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
    	//System.out.println(v.size());
    	return v;
    }
    
    public String getInfoSmawlKdpstShiftNmmMhsGiven(String npmhs) {
    	String tkn = "";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//get
    		stmt = con.prepareStatement("select * from CIVITAS where NPMHSMSMHS=?");
    		stmt.setString(1, npmhs);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String kdpst = rs.getString("KDPSTMSMHS");
    			String nmmhs = rs.getString("NMMHSMSMHS");
    			String shift = rs.getString("SHIFTMSMHS");
    			String smawl = rs.getString("SMAWLMSMHS");
    			tkn = smawl+","+kdpst+","+shift+","+nmmhs;
    		}
    		else {
    			//empty token
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
    	return tkn;
    }
    
    public Vector buatRangkumanJumlahMhsPerAngkatanGiven(Vector vGetInfoSmawlKdpstShiftNmmMhsGiven) {
    	Vector v1 = new Vector();
    	ListIterator li1 = v1.listIterator();
    	if(vGetInfoSmawlKdpstShiftNmmMhsGiven!=null && vGetInfoSmawlKdpstShiftNmmMhsGiven.size()>0) {
    		int counter = 0;
    		ListIterator li = vGetInfoSmawlKdpstShiftNmmMhsGiven.listIterator();
    		if(li.hasNext()) {
    			//first
    			String baris = (String)li.next();
    			StringTokenizer st = new StringTokenizer(baris,",");
    			
    			String prev_smawl = st.nextToken();
    			String prev_kdpst = st.nextToken();
    			String prev_shift = st.nextToken();
    			String prev_Nmmhs = st.nextToken();
    			counter = counter+1;
    			if(li.hasNext()) {
    				do {
    					baris = (String)li.next();
    	    			st = new StringTokenizer(baris,",");
    	    			
    	    			String smawl = st.nextToken();
    	    			String kdpst = st.nextToken();
    	    			String shift = st.nextToken();
    	    			String Nmmhs = st.nextToken();
    					if(prev_smawl.equalsIgnoreCase(smawl)) {
    						counter = counter+1;
    						prev_smawl = ""+smawl;
    						//process ang yg sama
    					}
    					else {
    						if(!prev_smawl.equalsIgnoreCase(smawl)) {
    							li1.add(prev_smawl+" "+counter);
    							counter = 0;
    							//process ang prev-smawl
    							prev_smawl = ""+smawl;
    							counter = counter+1;
    						}
    					}
    				}
    				while(li.hasNext());
    				if(!li.hasNext()) {
						//process akhiw
						li1.add(prev_smawl+" "+counter);
					}
    			}
    			else {
    				//cuma 1 record
    				li1.add(prev_smawl+" "+counter);
    				counter = 0;
    			}
    		}
    	}
    	return v1;
    }
    
/*
    public Vector getInfoIjazah(String noija) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try{
    		
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
*/
    
    public Vector getInfoIjazah(String kdpst,String npmhs) {
    	Vector v=new Vector();
    	try{
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//get
    		stmt = con.prepareStatement("select * from IJAZAH where NPMHS=?");
    		stmt.setString(1, npmhs);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    	    	ListIterator li = v.listIterator();
    			String id = ""+rs.getInt("ID");
    			li.add(id);
    			String nonirl=""+rs.getString("NONIRL");
    			li.add(nonirl);
    			String noija =""+rs.getString("NOIJA");
    			li.add(noija);
    			String noskr =""+rs.getString("NOSKR");
    			li.add(noskr);
    			String tglre =""+rs.getString("TGLRE");
    			li.add(tglre);
    			String nmmija=""+rs.getString("NAMADIIJAZAH");
    			li.add(nmmija);
    			String nimija=""+rs.getString("NIMHSDIIJAZAH");
    			li.add(nimija);
    			String tptglhr=""+rs.getString("TPTGLHRDIIJAZAH");
    			li.add(tptglhr);
    			String tgctk =""+rs.getDate("TGLCETAK");
    			li.add(tgctk);
    			String tgctkstr=""+rs.getString("TGLCETAKSTR");
    			li.add(tgctkstr);
    			String status=""+rs.getString("STATUS");
    			li.add(status);
    			String note =""+rs.getString("NOTE");
    			li.add(note);
    			String pemeriksa=""+rs.getString("DIPERIKSA_OLEH");
    			li.add(pemeriksa);
    			String pencetak=""+rs.getString("DICETAK_OLEH");
    			li.add(pencetak);
    			String diserahkan=""+rs.getString("DISERAHKAN_OLEH");
    			li.add(diserahkan);
    			String penerima=""+rs.getString("DITERIMA_OLEH");
    			li.add(penerima);
    			String tgterima =""+rs.getDate("TGL_SERAH_TERIMA");
    			li.add(tgterima);
    			String editable = ""+rs.getBoolean("EDITABLE");
    			li.add(editable);
    			String cetakable = ""+rs.getBoolean("CETAKABLE");
    			li.add(cetakable);
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
    
    public String prepNoIja(String kdpst) {
    	String noija = null;
    	Vector v = new Vector();
    	
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//get
    		stmt = con.prepareStatement("select * from IJAZAH where KDPST=?");
    		stmt.setString(1,kdpst);
    		rs = stmt.executeQuery();
    		ListIterator li = v.listIterator();
    		while(rs.next()) {
    			String norut = ""+rs.getString("NOIJA");
    			StringTokenizer st = new StringTokenizer(norut,"/");
    			if(st.hasMoreTokens()) {
    				norut = st.nextToken();
    				if(!Checker.isStringNullOrEmpty(norut)) {
    					li.add(norut);
    				}
    			}
    		}
    		Collections.sort(v);
    		li = v.listIterator();
    		while(li.hasNext()) {
    			noija = (String)li.next();
    		}
    		//prep emebel2 ijazah
    		String kodeIja = getStdKodeIja(kdpst);
    		String tahun = AskSystem.getCurrentYear();
    		noija = ""+(Integer.valueOf(noija).intValue()+1);
    		noija = noija+kodeIja+tahun;
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
    	return noija;
    }

    public String getStdKodeIja(String kdpst) {
    	String kodeIja=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//get
    		stmt = con.prepareStatement("select * from EXT_MSPST where KDPST=? and AKTIF=?");
    		stmt.setString(1, kdpst);
    		stmt.setBoolean(2, true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			kodeIja=rs.getString("KODEIJA");
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
    	return kodeIja;
    }
    
    public String getStdKodeNirl(String kdpst) {
    	String kodeNirl=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//get
    		stmt = con.prepareStatement("select * from EXT_MSPST where KDPST=? and AKTIF=?");
    		stmt.setString(1, kdpst);
    		stmt.setBoolean(2, true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			kodeNirl=rs.getString("KODENIRL");
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
    	return kodeNirl;
    }
    
    public String prepNoNirl(String kdpst) {
    	String nonirl = null;
    	Vector v = new Vector();
    	
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//get
    		stmt = con.prepareStatement("select * from IJAZAH where KDPST=?");
    		stmt.setString(1,kdpst);
    		rs = stmt.executeQuery();
    		ListIterator li = v.listIterator();
    		while(rs.next()) {
    			String norut = ""+rs.getString("NONIRL");
    			if(norut.length()>5) {
    				norut = norut.substring(norut.length()-4,norut.length());
    				li.add(norut);
    			}	
    		}
    		Collections.sort(v);
    		li = v.listIterator();
    		while(li.hasNext()) {
    			nonirl = (String)li.next();
    		}
    		nonirl = ""+(Integer.valueOf(nonirl).intValue()+1);
    		nonirl = AskSystem.getCurrentYear()+getStdKodeNirl(kdpst)+nonirl;
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
    	return nonirl;
    }
    
    public String getNamaJenjang(String kdpst) {
    	String nmjen = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//get
    		stmt = con.prepareStatement("select * from EXT_MSPST where KDPST=?");
    		stmt.setString(1, kdpst);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			nmjen = rs.getString("NAMAJENJANG");
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
    	return nmjen;
    }

    public Vector getDataUntukDicetakDiIjazah(String npmhs) {
    	Vector v = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//get
    		stmt = con.prepareStatement("select * from IJAZAH where NPMHS=?");
    		stmt.setString(1, npmhs);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			v = new Vector();
    			ListIterator li = v.listIterator();
    			String nomoija = rs.getString("NOIJA");
    			li.add(nomoija);
    			String namaija = rs.getString("NAMADIIJAZAH");
    			li.add(namaija);
    			String tptglhr = rs.getString("TPTGLHRDIIJAZAH");
    			li.add(tptglhr);
    			String nonirl = rs.getString("NONIRL");
    			li.add(nonirl);
    			String nimija = rs.getString("NIMHSDIIJAZAH");
    			li.add(nimija);
    			String gelar = rs.getString("GELARIJA");
    			li.add(gelar);
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

    public Vector getDataUntukDicetakDiKrs(Vector v_npmhs, String tkn_info_krs) {
    	Vector v = null;
    	StringTokenizer st = new StringTokenizer(tkn_info_krs,"#");
    	String thsms = null;
    	String objId = null;
    	String nmm = null;
    	String npm = null;
    	String kdpst = null;
    	String obj_lvl = null;
    	String kdkmk = null;
    	String nakmk = null;
    	String nlakh = null;
    	String bobot = null;
    	String sksmk = null;
    	if(st.hasMoreTokens()) {
    		
    	}
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//get
    		stmt = con.prepareStatement("select * from IJAZAH where NPMHS=?");
    		
    		//StringTokenizer st = new 
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
    
    
    public String getBobotGivenNlakh(String thsms,String kdpst,String nlakh) {
    	//fungsi nini ada di searchDb & UpdateDb
    	String bobot = "0";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//get
    		stmt = con.prepareStatement("select * from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? and NLAKHTBBNL=?");
    		stmt.setString(1, thsms);
    		stmt.setString(2, kdpst);
    		stmt.setString(3, nlakh);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			bobot = ""+rs.getDouble("BOBOTTRNLP");
    		}
    		//StringTokenizer st = new 
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
    	return bobot;
    }    

    /*
     * upd1 @ feb 2015
     * problem:
     * Karena ada penambahan ttg kode kampus sehingga menimbulkan penampakan dobel terhadap summari 
     * penerimaan mahasiswa baru, sesuai dengan jumlah kampus yang membuka kd prodi yg sama. 
     */
    public Vector getSummaryPmb(Vector VgetScopeUpd7des2012) {
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	try {
    		if(VgetScopeUpd7des2012!=null && VgetScopeUpd7des2012.size()>0){
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//getThsmsPmb
        		stmt = con.prepareStatement("select * from CALENDAR where AKTIF=?");
        		stmt.setBoolean(1, true);
        		rs = stmt.executeQuery();
        		rs.next();//harus ada yg aktif
        		String thsmsPmb  = rs.getString("THSMS_PMB");
    			ListIterator li = VgetScopeUpd7des2012.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs);
    				String id_obj = st.nextToken();
    				String kdpst = st.nextToken();
    				String obj_dsc = st.nextToken();
    				String obj_level = st.nextToken();
    				st = new StringTokenizer(obj_dsc,"_");
    				String nmpst = "";
    				while(st.hasMoreTokens()) {
    					String tmp = st.nextToken();
    					if(!tmp.equalsIgnoreCase("MHS")) {
    						nmpst = nmpst+tmp;
    					}
    					if(st.hasMoreTokens()) {
    						nmpst=nmpst+" ";	
    					}
    				}
    				// upd1- get kode kampus
    				stmt=con.prepareStatement("select KODE_KAMPUS_DOMISILI from OBJECT where ID_OBJ=?");
    				stmt.setLong(1, Long.parseLong(id_obj));
        			rs = stmt.executeQuery();
        			rs.next();
        			String kmp = ""+rs.getString(1);
        			//end upd1
        		
    				
    				
        			//upd1
    				//stmt = con.prepareStatement("select * from CIVITAS where KDPSTMSMHS=? and SMAWLMSMHS=?");
    				stmt = con.prepareStatement("select * from CIVITAS where ID_OBJ=? and SMAWLMSMHS=?");
    				//stmt.setString(1,kdpst);
    				stmt.setLong(1,Long.parseLong(id_obj));
    				stmt.setString(2,thsmsPmb);
    				//end upd1
    				rs = stmt.executeQuery();
    				Vector vTmp = new Vector();
    				ListIterator liTmp = vTmp.listIterator();
    				while(rs.next()) {
    					String npmhs = rs.getString("NPMHSMSMHS");
    					String nmmhs = rs.getString("NMMHSMSMHS");
    					String stpid = rs.getString("STPIDMSMHS");
    					String kdjek = rs.getString("KDJEKMSMHS");
    					String kdjen = rs.getString("KDJENMSMHS"); 
    					liTmp.add(kdjen+"#&"+npmhs+"#&"+nmmhs+"#&"+stpid+"#&"+kdjek+"#&"+kmp);
    				
    				}
    				//System.out.println("brsan="+brs);
    				//System.out.println("vTmpvTmp="+vTmp.size());
    				
    				
    				
    				lif.add(brs);
    				lif.add(vTmp);
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
    	return vf;
    }  
    
    /*
     * upd1 @ feb 2015
     * problem:
     * Karena ada penambahan ttg kode kampus sehingga menimbulkan penampakan dobel terhadap summari 
     * penerimaan mahasiswa baru, sesuai dengan jumlah kampus yang membuka kd prodi yg sama. 
     */
    public Vector getSummaryPmb(Vector VgetScopeUpd7des2012,String target_thsms) {
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	try {
    		if(VgetScopeUpd7des2012!=null && VgetScopeUpd7des2012.size()>0){
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//getThsmsPmb
        		//stmt = con.prepareStatement("select * from CALENDAR where AKTIF=?");
        		//stmt.setBoolean(1, true);
        		//rs = stmt.executeQuery();
        		//rs.next();//harus ada yg aktif
        		String thsmsPmb  = ""+target_thsms;
    			ListIterator li = VgetScopeUpd7des2012.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs);
    				String id_obj = st.nextToken();
    				String kdpst = st.nextToken();
    				String obj_dsc = st.nextToken();
    				String obj_level = st.nextToken();
    				st = new StringTokenizer(obj_dsc,"_");
    				String nmpst = "";
    				while(st.hasMoreTokens()) {
    					String tmp = st.nextToken();
    					if(!tmp.equalsIgnoreCase("MHS")) {
    						nmpst = nmpst+tmp;
    					}
    					if(st.hasMoreTokens()) {
    						nmpst=nmpst+" ";	
    					}
    				}
    				//System.out.println(brs);
    				//get total pendaftar
    				// upd1- get kode kampus
    				stmt=con.prepareStatement("select KODE_KAMPUS_DOMISILI from OBJECT where ID_OBJ=?");
    				stmt.setLong(1, Long.parseLong(id_obj));
        			rs = stmt.executeQuery();
        			rs.next();
        			String kmp = ""+rs.getString(1);
        			//end upd1
    				//
        			
        			//upd1
    				//stmt = con.prepareStatement("select * from CIVITAS where KDPSTMSMHS=? and SMAWLMSMHS=?");
    				stmt = con.prepareStatement("select * from CIVITAS where ID_OBJ=? and SMAWLMSMHS=?");
    				//stmt.setString(1,kdpst);
    				stmt.setLong(1,Long.parseLong(id_obj));
    				stmt.setString(2,thsmsPmb);
    				//end upd1
    				
    				rs = stmt.executeQuery();
    				Vector vTmp = new Vector();
    				ListIterator liTmp = vTmp.listIterator();
    				while(rs.next()) {
    					
    					String npmhs = rs.getString("NPMHSMSMHS");
    					String nmmhs = rs.getString("NMMHSMSMHS");
    					String stpid = rs.getString("STPIDMSMHS");
    					String kdjek = rs.getString("KDJEKMSMHS");
    					String kdjen = rs.getString("KDJENMSMHS"); 
    					//upd1
    					//liTmp.add(kdjen+"#&"+npmhs+"#&"+nmmhs+"#&"+stpid+"#&"+kdjek);
    					liTmp.add(kdjen+"#&"+npmhs+"#&"+nmmhs+"#&"+stpid+"#&"+kdjek+"#&"+kmp);
    					//end upd1
    				}
    				
    				
    				lif.add(brs);
    				lif.add(vTmp);
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
    	return vf;
    }  

    public Vector getListKurikulumInDetail(String kdpst) {
    	Vector vf = new Vector();
    	//Vector v1 = null;
    	ListIterator lif = vf.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
       		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
       		con = ds.getConnection();
       		stmt = con.prepareStatement("select * from KRKLM where KDPSTKRKLM=? order by STARTTHSMS desc");
    		stmt.setString(1,kdpst);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String idkur = ""+rs.getInt("IDKURKRKLM");
    			if(Checker.isStringNullOrEmpty(idkur)) {
    				idkur="null";
    			}
    			String nmkur = rs.getString("NMKURKRKLM");
    			if(Checker.isStringNullOrEmpty(nmkur)) {
    				nmkur="null";
    			}
    			String stkur = rs.getString("STKURKRKLM");
    			if(Checker.isStringNullOrEmpty(stkur)) {
    				stkur="null";
    			}
    			String start = rs.getString("STARTTHSMS");
    			if(Checker.isStringNullOrEmpty(start)) {
    				start="null";
    			}
    			String ended = rs.getString("ENDEDTHSMS");
    			if(Checker.isStringNullOrEmpty(ended)) {
    				ended="null";
    			}
    			String targt = rs.getString("TARGTKRKLM");
    			if(Checker.isStringNullOrEmpty(targt)) {
    				targt="null";
    			}
    			String skstt = ""+rs.getInt("SKSTTKRKLM");
    			if(Checker.isStringNullOrEmpty(skstt)) {
    				skstt="null";
    			}
    			String smstt = ""+rs.getInt("SMSTTKRKLM");
    			if(Checker.isStringNullOrEmpty(smstt)) {
    				smstt="null";
    			}
    			lif.add(idkur+"#&"+nmkur+"#&"+stkur+"#&"+start+"#&"+ended+"#&"+targt+"#&"+skstt+"#&"+smstt);
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
    	return vf;
    }
    
    public Vector getListKurikulumInDetailAktifOnly(String kdpst, String thsms_now) {
    	Vector vf = new Vector();
    	//String thsms_now = Checker.getThsmsNow();
    	
    	//Vector v1 = null;
    	ListIterator lif = vf.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
       		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
       		con = ds.getConnection();
       		stmt = con.prepareStatement("select * from KRKLM where KDPSTKRKLM=? and STARTTHSMS<=? and (ENDEDTHSMS IS NULL or ENDEDTHSMS>=?)order by STARTTHSMS desc");
    		stmt.setString(1,kdpst);
    		stmt.setString(2, thsms_now);
    		stmt.setString(3, thsms_now);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String idkur = ""+rs.getInt("IDKURKRKLM");
    			if(Checker.isStringNullOrEmpty(idkur)) {
    				idkur="null";
    			}
    			String nmkur = rs.getString("NMKURKRKLM");
    			if(Checker.isStringNullOrEmpty(nmkur)) {
    				nmkur="null";
    			}
    			String stkur = rs.getString("STKURKRKLM");
    			if(Checker.isStringNullOrEmpty(stkur)) {
    				stkur="null";
    			}
    			String start = rs.getString("STARTTHSMS");
    			if(Checker.isStringNullOrEmpty(start)) {
    				start="null";
    			}
    			String ended = rs.getString("ENDEDTHSMS");
    			if(Checker.isStringNullOrEmpty(ended)) {
    				ended="null";
    			}
    			String targt = rs.getString("TARGTKRKLM");
    			if(Checker.isStringNullOrEmpty(targt)) {
    				targt="null";
    			}
    			String skstt = ""+rs.getInt("SKSTTKRKLM");
    			if(Checker.isStringNullOrEmpty(skstt)) {
    				skstt="null";
    			}
    			String smstt = ""+rs.getInt("SMSTTKRKLM");
    			if(Checker.isStringNullOrEmpty(smstt)) {
    				smstt="null";
    			}
    			lif.add(idkur+"#&"+nmkur+"#&"+stkur+"#&"+start+"#&"+ended+"#&"+targt+"#&"+skstt+"#&"+smstt);
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
    	return vf;
    }

    
    /*
     * deprecated
     
    public Vector getTotMhsPerMakul(String thsms,String kdpst) {
    	Vector vf = new Vector();
    	//Vector v1 = null;
    	ListIterator lif = vf.listIterator();
    	try {
    		if(!Checker.isStringNullOrEmpty(thsms) && !Checker.isStringNullOrEmpty(kdpst)) {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from TRNLM where THSMSTRNLM=? and KDPSTTRNLM=?");
    			stmt.setString(1,thsms);
    			stmt.setString(2,kdpst);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String kdkmk = rs.getString("KDKMKTRNLM");
    				int sksmk = rs.getInt("SKSMKTRNLM");
    				String shift = rs.getString("SHIFTTRNLM");
    				lif.add(kdkmk+","+sksmk+","+shift);
    			}
    			if(vf!=null) {
    				vf = Tool.removeDuplicateFromVector(vf);
    			}	
    			
    			stmt = con.prepareStatement("select * from MAKUL where KDPSTMAKUL=? and KDKMKMAKUL=?");
    			lif = vf.listIterator();
    			while(lif.hasNext()) {
    				String brs = (String)lif.next();
    				StringTokenizer st = new StringTokenizer(brs,",");
    				String kdkmk = st.nextToken();
    				String sksmk = st.nextToken();
    				String shift = st.nextToken();
    				stmt.setString(1,kdpst);
    				stmt.setString(2, kdkmk);
    				rs = stmt.executeQuery();
    				while(rs.next()) {
    					String nakmk = rs.getString("NAKMKMAKUL");
    					int skstm = rs.getInt("SKSTMMAKUL");
    					int skspr = rs.getInt("SKSPRMAKUL");
    					int skslp = rs.getInt("SKSLPMAKUL");
    					int skstt = (skstm+skspr+skslp);
    					if(skstt==Integer.valueOf(sksmk).intValue()) {
    						lif.set(kdkmk+","+sksmk+","+nakmk+","+shift);
    					}
    				}
    			}
    			stmt = con.prepareStatement("select * from TRNLM where THSMSTRNLM=? and KDPSTTRNLM=? and KDKMKTRNLM=?");
    			lif = vf.listIterator();
    			while(lif.hasNext()) {
    				String brs = (String)lif.next();
    				StringTokenizer st = new StringTokenizer(brs,",");
    				String kdkmk = st.nextToken();
    				String sksmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String shift = st.nextToken();
    				stmt.setString(1, thsms);
    				stmt.setString(2, kdpst);
    				stmt.setString(3, kdkmk);
    				rs = stmt.executeQuery();
    				String tkn_npm = "";
    				while(rs.next()) {
    					String npmhs = rs.getString("NPMHSTRNLM");
    					tkn_npm = tkn_npm + npmhs +"#";
    				}
    				tkn_npm = tkn_npm.substring(0,tkn_npm.length()-1);
    				lif.set(shift+","+kdkmk+","+sksmk+","+nakmk+","+tkn_npm);
    				//System.out.println(shift+","+kdkmk+","+sksmk+","+nakmk+","+tkn_npm);
    			}
    			Collections.sort(vf);
    		}//end if	
    	}
    	catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		//System.out.println(e);
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return vf;
    }  
	*/
    
    public Vector getInfoCivitasForTampleteListMhs(String tknNpm) {
    	String tknCivitasInfo = null;
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		if(tknNpm!=null) {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from CIVITAS inner join EXT_CIVITAS on CIVITAS.NPMHSMSMHS=EXT_CIVITAS.NPMHSMSMHS where CIVITAS.NPMHSMSMHS=?");
    			StringTokenizer st = new StringTokenizer(tknNpm,",");
    			while(st.hasMoreTokens()) {
    				String npmhs = st.nextToken();
    				stmt.setString(1,npmhs);
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					String kdpst = rs.getString("KDPSTMSMHS");
    					String nimhs = rs.getString("NIMHSMSMHS");
    					String nmmhs = rs.getString("NMMHSMSMHS");
    					String shift = rs.getString("SHIFTMSMHS");
    					String kdjek = rs.getString("KDJEKMSMHS");
    					String smawl = rs.getString("SMAWLMSMHS");
    					tknCivitasInfo = kdpst+"#&"+npmhs+"#&"+nimhs+"#&"+nmmhs+"#&"+shift+"#&"+kdjek+"#&"+smawl;
    					li.add(tknCivitasInfo);
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
    	return v;
    }   
    
    
    public Vector getInfoCivitasForTampleteListMhs(Vector vTokenPertamaNpmhs) {
    	/*
    	 * input vector = token pertama harus npmhs, pemisah "`"
    	 */
    	Vector v = new Vector();
    	String tknCivitasInfo = "";
    	if(vTokenPertamaNpmhs!=null && vTokenPertamaNpmhs.size()>0) {
    		ListIterator lin = vTokenPertamaNpmhs.listIterator();
        	ListIterator li = v.listIterator();
        	try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from CIVITAS inner join EXT_CIVITAS on CIVITAS.NPMHSMSMHS=EXT_CIVITAS.NPMHSMSMHS where CIVITAS.NPMHSMSMHS=?");
        		while(lin.hasNext()) {
        			String brs = (String) lin.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String npmhs = st.nextToken();
        			stmt.setString(1,npmhs);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				String kdpst = ""+rs.getString("KDPSTMSMHS");
        				String nimhs = ""+rs.getString("NIMHSMSMHS");
        				String nmmhs = ""+rs.getString("NMMHSMSMHS");
        				String shift = ""+rs.getString("SHIFTMSMHS");
        				String kdjek = ""+rs.getString("KDJEKMSMHS");
        				String smawl = ""+rs.getString("SMAWLMSMHS");
        				//tknCivitasInfo = kdpst+"#&"+npmhs+"#&"+nimhs+"#&"+nmmhs+"#&"+shift+"#&"+kdjek+"#&"+smawl+"#&"+brs;
        				tknCivitasInfo = kdpst+"`"+npmhs+"`"+nimhs+"`"+nmmhs+"`"+shift+"`"+kdjek+"`"+smawl+"`"+brs;
        				li.add(tknCivitasInfo);
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
    	return v;
    }   

    public boolean cekStatusLockUnlockTrnlm(String thsms,String npmhs ) {
    	boolean locked = false;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select LOCKTRNLM from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=?");
    		stmt.setString(1,thsms);
    		stmt.setString(2, npmhs);
    		rs = stmt.executeQuery();
    		while(rs.next() && !locked) {
    			locked = rs.getBoolean("LOCKTRNLM");
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
    	return locked;
    }  

    public boolean cekStatusLockMhsTrnlm(String thsms,String npmhs ) {
    	boolean locked = false;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select LOCKMHS from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=?");
    		stmt.setString(1,thsms);
    		stmt.setString(2, npmhs);
    		rs = stmt.executeQuery();
    		if(rs.next() && !locked) {
    			locked = rs.getBoolean("LOCKMHS");
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
    	return locked;
    }  
    
    
    
   // public boolean kelasKrsSesuaiShiftnya(String kdpst) {
    //public boolean pilihKelasHrsSesuaiShiftnya(String kdpst,String thsms_krs,String targetNpmhs) {
    public boolean bolehPilihKelasAllShift(String kdpst,String thsms_krs,String targetNpmhs) {
    	boolean value = true;
    	long npm_id_obj = Checker.getObjectId(targetNpmhs);
    	//System.out.println("kok ngga kesisini");
    	/*
		 * proses filter untuk kelas yang setara yang di offer oleh prodi lainnya
		 * bussiness prosessnya :
		 * if all shift = boleh seluruh shift - default
		 * kalo !aalll_kampus:
		 * 1. cek status white listnya
		 * jika whitelist contain all atao tkn npm maka masuk kedalam whitelist
			untuk shift pilihannya all atao sesuai profile shift kalo yang lennya cek tkn pada colum masg2
		 */
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from PILIH_KELAS_RULES where THSMS=? and ID_OBJ_MHS=?");
    		stmt.setString(1,thsms_krs);
    		stmt.setLong(2,npm_id_obj);
    		rs=stmt.executeQuery();
    		rs.next();
    		//column shift_only deprecated
    			//boolean shiftOnly = rs.getBoolean("SHIFT_ONLY");
    		/*
    		 * column ALL_ berisikan default value jadi kalo npm yg aada di whitelist nilainya kebalikannya
    		 */
    		//System.out.println("kok ngga kesisini");
    		boolean allShift = rs.getBoolean("ALL_SHIFT");
    		if(allShift==false) {
    			value = false;
    		}
    		else {
    			value = true;
    		}
    		//System.out.println("value0="+value);
    		String whiteListShift = ""+rs.getString("NPMHS_WHITELIST_SHIFT");
    		//System.out.println("whiteListShift="+whiteListShift);
    		if(whiteListShift.contains("ALL")||whiteListShift.contains("all")||whiteListShift.contains(targetNpmhs)) {
    			value = !value;
    		}
    		else {
    			//tidak berubah
    		}
    		//System.out.println("value01="+value);
    		
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
    	//System.out.println("value=)"+value);
    	return value;
    } 
    
    public String  bolehPilihKelasAllProdi(String kdpst,String thsms_krs,String targetNpmhs) {
    	boolean value = true;
    	long npm_id_obj = Checker.getObjectId(targetNpmhs);
    	String tkn_value_prodi = "";
    	String tkn_allowd_prodi = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from PILIH_KELAS_RULES where THSMS=? and ID_OBJ_MHS=?");
    		stmt.setString(1,thsms_krs);
    		stmt.setLong(2,npm_id_obj);
    		rs=stmt.executeQuery();
    		rs.next();
    		/*
    		 * all prodi adalah default value utk seluruh mhs, nilainya terbalik bila npm ada di white list
    		 */
    		boolean allProdi = rs.getBoolean("ALL_PRODI");
    		tkn_allowd_prodi = ""+rs.getString("TKN_PRODI");
    		if(allProdi==false) {
    			value = false;
    		}
    		else {
    			value = true;
    		}
    		String whiteListProdi = ""+rs.getString("NPMHS_WHITELIST_PRODI");
    		/*
    		 * jika npm termasuk dalam whitelist maka value berubah ajdi kebalikannya
    		 * all: berarti prodi dalam 1 fakultas, kalo all fak baru diluar fakultas
    		 */
    		if(whiteListProdi.contains("ALL")||whiteListProdi.contains("all")||whiteListProdi.contains(targetNpmhs)) {
    			value = !value;
    		}
    		else {
    			//tidak berubah
    		}
    		/*
    		 * if value = true, cek prodi mana aja yg boleh
    		 */
    		if(value && Checker.isStringNullOrEmpty(tkn_allowd_prodi)) {
    			//isi kurang komplit karena kalo boleh dari prodi lain tapi tkn nya null
    			//jadi valuenya nilainya dibalik lagi
    			value = !value;
    		}
    		else if(value && (tkn_allowd_prodi.contains("ALL")||tkn_allowd_prodi.contains("all"))){
    			tkn_allowd_prodi = Getter.getListProdiDalam1Fakultas(kdpst);
    		}
    		
    		if(value) {
    			tkn_value_prodi = value+" "+tkn_allowd_prodi;
    		}
    		else {
    			tkn_value_prodi = ""+value;
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
    	//System.out.println("value=)"+value);
    	return tkn_value_prodi;
    } 
    
       
    public String bolehPilihKelasAllFakultas(String kdpst,String thsms_krs,String targetNpmhs) {
    	boolean value = true;
    	long npm_id_obj = Checker.getObjectId(targetNpmhs);
    	String tkn_value_fak = "";
    	String tkn_allowd_fak = null;
    	//String thsms_krs = Checker.getThsmsKrs();
    	//System.out.println("thsms_krs=)"+thsms_krs);
    	//System.out.println("kdpst=)"+kdpst);
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from PILIH_KELAS_RULES where THSMS=? and ID_OBJ_MHS=?");
    		stmt.setString(1,thsms_krs);
    		stmt.setLong(2,npm_id_obj);
    		rs=stmt.executeQuery();
    		rs.next();
    		boolean allFak = rs.getBoolean("ALL_FAKULTAS");
    		tkn_allowd_fak = ""+rs.getString("TKN_FAKULTAS");
    		if(allFak==false) {
    			value = false;
    		}
    		else {
    			value = true;
    		}
    		String whiteListFak = ""+rs.getString("NPMHS_WHITELIST_FAK");
    		if(whiteListFak.contains("ALL")||whiteListFak.contains("all")||whiteListFak.contains(targetNpmhs)) {
    			value = !value;
    		}
    		else {
    			//tidak berubah
    		}
    		
    		/*
    		 * if value = true, cek prodi dr fak mana aja yg boleh
    		 */
    		if(value && Checker.isStringNullOrEmpty(tkn_allowd_fak)) {
    			//isi kurang komplit karena kalo boleh dari prodi lain tapi tkn nya null
    			//jadi valuenya nilainya dibalik lagi
    			value = !value;
    		}
    		else if(value && (tkn_allowd_fak.contains("ALL")||tkn_allowd_fak.contains("all"))){
    			tkn_allowd_fak = Getter.getListProdiDariLainFakultas(kdpst);
    		}
    		if(value) {
    			tkn_value_fak = value+" "+tkn_allowd_fak;
    		}
    		else {
    			tkn_value_fak = ""+value;
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
    	//System.out.println("value=)"+value);
    	return tkn_value_fak;
    } 
    
    public String bolehPilihKelasAllKampus(String kdpst,String thsms_krs,String targetNpmhs) {
    	boolean value = true;
    	long npm_id_obj = Checker.getObjectId(targetNpmhs);
    	String tkn_value_kmp = "";
    	String tkn_allowd_kmp = null;
    	//String thsms_krs = Checker.getThsmsKrs();
    	//System.out.println("thsms_krs=)"+thsms_krs);
    	//System.out.println("kdpst=)"+kdpst);
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from PILIH_KELAS_RULES where THSMS=? and ID_OBJ_MHS=?");
    		stmt.setString(1,thsms_krs);
    		stmt.setLong(2,npm_id_obj);
    		rs=stmt.executeQuery();
    		rs.next();
    		boolean allKmp = rs.getBoolean("ALL_KAMPUS");
    		tkn_allowd_kmp = ""+rs.getString("TKN_KAMPUS");
    		if(allKmp==false) {
    			value = false;
    		}
    		else {
    			value = true;
    		}
    		String whiteListKmp = ""+rs.getString("NPMHS_WHITELIST_FAK");
    		if(whiteListKmp.contains(targetNpmhs)) {
    			value = !value;
    		}
    		else {
    			//tidak berubah
    		}
    		/*
    		 * tidak ada value ALL untuk tkn kampus
    		 */
    		if(value) {
    			tkn_value_kmp = value+" "+tkn_allowd_kmp;
    		}
    		else {
    			tkn_value_kmp = ""+value;
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
    	//System.out.println("value=)"+value);
    	return tkn_value_kmp;
    } 
    
    public boolean pilihKelasSemuaShift(String kdpst,String thsms_krs) {
    	boolean value = true;
    	//String thsms_krs = Checker.getThsmsKrs();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from PILIH_KELAS_RULES where THSMS=? and KDPST=?");
    		stmt.setString(1,thsms_krs);
    		stmt.setString(2,kdpst);
    		rs = stmt.executeQuery();
    		rs.next();
    		value = rs.getBoolean("ALL_SHIFT");
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
    	return value;
    } 
   /* 
    public boolean pilihKelasSemuaShiftJikaTidakAdaDiShiftnya(String kdpst) {
    	boolean value = true;
    	String thsms_krs = Checker.getThsmsKrs();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select ALL_SHIFT_IF_NON_AT_SHIFT from PILIH_KELAS_RULES where THSMS=? and KDPST=?");
    		stmt.setString(1,thsms_krs);
    		stmt.setString(1,kdpst);
    		rs = stmt.executeQuery();
    		value = rs.getBoolean("ALL_SHIFT_IF_NON_AT_SHIFT");
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
    	return value;
    } 
    */
    public Vector filterRequestKrsAddOrDrop(String[]kelasInfo,String thsms,String npmhs) {
    	//<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,null,<%=currStatus%>,<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,<%=maxEnrolled%>,<%=minEnrolled%>"
    	//remove null value
    	Vector v1 = new Vector();
    	ListIterator li1 = v1.listIterator();
    	for(int i=0;i<kelasInfo.length;i++) {
    		if(!kelasInfo[i].startsWith("null")) {
    			li1.add(kelasInfo[i]);
    			//System.out.println("kelasInfo "+i+"="+kelasInfo[i]);
    		}
    	}
    			
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=?");
    		stmt.setString(1,thsms);
    		stmt.setString(2,npmhs);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String kdkmk = ""+rs.getString("KDKMKTRNLM");
    			String nilai = ""+rs.getInt("NILAITRNLM");
    			String nlakh = ""+rs.getString("NLAKHTRNLM");
    			String bobot = ""+rs.getFloat("BOBOTTRNLM");
    			String sksmk = ""+rs.getInt("SKSMKTRNLM");
    			String kelas = ""+rs.getString("KELASTRNLM");
    			String krsdn = ""+rs.getInt("KRSDONWLOADED");
    			String khsdn = ""+rs.getInt("KHSDONWLOADED");
    			String bakApr = ""+rs.getBoolean("BAK_APPROVAL");
    			String shiftKls = ""+rs.getString("SHIFTTRNLM");
    			String paApr = ""+rs.getBoolean("PA_APPROVAL");
    			String lock = ""+rs.getBoolean("LOCKTRNLM");
    			String baukApr = ""+rs.getBoolean("BAUK_APPROVAL");
    			String idkmk = ""+rs.getLong("IDKMKTRNLM");
    			String npmPa = ""+rs.getString("NPM_PA");
    			String npmBak = ""+rs.getString("NPM_BAK");
    			String npmBaa = ""+rs.getString("NPM_BAA");
    			String npmBauk= ""+rs.getString("NPM_BAUK");
    			String baaApr = ""+rs.getBoolean("BAA_APPROVAL");
    			String ktuApr = ""+rs.getBoolean("KTU_APPROVAL");
    			String dknApr = ""+rs.getString("DEKAN_APPROVAL");
    			String npmKtu = ""+rs.getString("NPM_KTU");
    			String npmDkn = ""+rs.getString("NPM_DEKAN");
    			String lockMhs = ""+rs.getBoolean("LOCKMHS");
    			String kodeKampus = ""+rs.getString("KODE_KAMPUS");
    			li.add(idkmk+"#"+kdkmk+"#"+shiftKls+"#"+kelas+"#"+lock+"#"+baaApr+"#"+ktuApr+"#"+dknApr+"#"+bakApr+"#"+paApr+"#"+baukApr+"#"+nilai+"#"+nlakh+"#"+bobot+"#"+sksmk+"#"+krsdn+"#"+khsdn+"#"+npmPa+"#"+npmBak+"#"+npmBaa+"#"+npmBauk+"#"+npmKtu+"#"+npmDkn+"#"+lockMhs+"#"+kodeKampus);
    		}
    		if(v.size()<1) {
    			//blm ada record
    			li1 = v1.listIterator();
    			while(li1.hasNext()) {
    				String brs = (String)li1.next();
    				li1.set("add_req,"+brs);
    			}
    		}
    		else {
    			//cek for add
    			li1 = v1.listIterator();
    			while(li1.hasNext()) {
    			//for(int i=0;i<kelasInfo.length;i++) {
    				String brs = (String)li1.next();
    				StringTokenizer st = new StringTokenizer(brs,",");
    				//value="<%=idkmk%>,<%=shift%>,<%=norutKelasParalel%>,<%=kdkmk%>,null,<%=currStatus%>,
    				//<%=npmdos%>,<%=npmasdos%>,<%=canceled%>,<%=kodeKelas%>,<%=kodeRuang%>,<%=kodeGedung%>,
    				//<%=kodeKampus%>,<%=tknDayTime%>,<%=nmmdos%>,<%=nmmasdos%>,<%=enrolled%>,
    				//<%=maxEnrolled%>,<%=minEnrolled%>">
    				String idkmk_ = st.nextToken();
    				String shift_ = st.nextToken();
    				String kelas_ = st.nextToken();
    				String kdkmk_ = st.nextToken(); 
    				String sksmk_ = st.nextToken();
    				String currStatus_ = st.nextToken();
    				String npmdos_ = st.nextToken();
    				String npmasdos_ = st.nextToken();
    				String canceled_ = st.nextToken();
    				String kodeKelas_ = st.nextToken();
    				String kodeRuang_ = st.nextToken();
    				String kodeGedung_ = st.nextToken();
    				String kodeKampus_ = st.nextToken();
    				String tknDayTime_ = st.nextToken();
    				String nmmdos_ = st.nextToken();
    				String nmmasdos_ = st.nextToken();
    				String enrolled_ = st.nextToken();
    				String maxEnrolled_ = st.nextToken();
    				String minEnrolled_ = st.nextToken();
    				//tambahan uniqueIdClassPool
    				boolean match = false;
    				li = v.listIterator();
    				while(li.hasNext()&&!match) {
    					//li.add(idkmk+"#"+kdkmk+"#"+shiftKls+"#"+kelas+"#"+lock+"#"+baaApr+"#"+ktuApr+"#"+dknApr+"#"+bakApr+"#"+paApr+"#"+baukApr+"#"+nilai+"#"+nlakh+"#"+bobot+"#"+sksmk+"#"+krsdn+"#"+khsdn+"#"+npmPa+"#"+npmBak+"#"+npmBaa+"#"+npmBauk+"#"+npmKtu+"#"+npmDkn);
    					String brs1 = (String)li.next();
    					st = new StringTokenizer(brs1,"#");
    					String idkmk = st.nextToken();
    					String kdkmk = st.nextToken();
    					String shift = st.nextToken();
    					String kelas = st.nextToken();
    					String lock = st.nextToken();
    					st.nextToken();//baaApr+"#"+
    					st.nextToken();//ktuApr+"#"+
    					st.nextToken();//dknApr+"#"+
    					st.nextToken();//bakApr+"#"+
    					st.nextToken();//paApr+"#"+
    					st.nextToken();//baukApr+"#"+
    					st.nextToken();//nilai+"#"+
    					st.nextToken();//nlakh+"#"+
    					st.nextToken();//bobot+"#"+
    					st.nextToken();//sksmk+"#"+
    					st.nextToken();//krsdn+"#"+
    					st.nextToken();//khsdn+"#"+
    					st.nextToken();//npmPa+"#"+
    					st.nextToken();//npmBak+"#"+
    					st.nextToken();//npmBaa+"#"+
    					st.nextToken();//npmBauk+"#"+
    					st.nextToken();//npmKtu+"#"+
    					st.nextToken();//npmDkn);
    					st.nextToken();//lockMhs
    					//tambahan uniqueIdClassPooll
    					String kodeKampus = st.nextToken();//
    					//if(idkmk.equalsIgnoreCase(idkmk_)&&shift.equalsIgnoreCase(shift_)&&kodeKampus.equalsIgnoreCase(kodeKampus_)) {
    					if(idkmk.equalsIgnoreCase(idkmk_)) {
    						match = true;
    					}
    				}
    				if(match) {
    					li1.set("ignore_req,"+brs);
    				}
    				else {
    					li1.set("add_req,"+brs);
    					//System.out.println("add_req,"+brs);
    				}
    			}
    			//cek for drop
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"#");
					String idkmk = st.nextToken();
					String kdkmk = st.nextToken();
					String shift = st.nextToken();
					String kelas = st.nextToken();
					String lock = st.nextToken();
					st.nextToken();//baaApr+"#"+
					st.nextToken();//ktuApr+"#"+
					st.nextToken();//dknApr+"#"+
					st.nextToken();//bakApr+"#"+
					st.nextToken();//paApr+"#"+
					st.nextToken();//baukApr+"#"+
					st.nextToken();//nilai+"#"+
					st.nextToken();//nlakh+"#"+
					st.nextToken();//bobot+"#"+
					st.nextToken();//sksmk+"#"+
					st.nextToken();//krsdn+"#"+
					st.nextToken();//khsdn+"#"+
					st.nextToken();//npmPa+"#"+
					st.nextToken();//npmBak+"#"+
					st.nextToken();//npmBaa+"#"+
					st.nextToken();//npmBauk+"#"+
					st.nextToken();//npmKtu+"#"+
					st.nextToken();//npmDkn);
					st.nextToken();//lockMhs
					//uniqueidClassPool
					String kodeKampus = st.nextToken();//
					li1 = v1.listIterator();
					boolean match = false;
					while(li1.hasNext() && !match) {
						String brs1 = (String)li1.next();
						st = new StringTokenizer(brs1,",");
						//233,REGULER PAGI,2
						String req_ = "null";
						if(st.hasMoreTokens()) {
							req_ = st.nextToken();
						}
						String idkmk_ = "null";
						if(st.hasMoreTokens()) {
							idkmk_ = st.nextToken();
						}
						String shift_ = "null";
						if(st.hasMoreTokens()) {
							shift_ = st.nextToken();
						}
						String kelas_ = "null";
						if(st.hasMoreTokens()) {
							kelas_ = st.nextToken();
						}
						String kdkmk_ = "null"; 
						if(st.hasMoreTokens()) {
							kdkmk_ = st.nextToken(); 
						}
	    				String sksmk_ = "null";
						if(st.hasMoreTokens()) {
							sksmk_ = st.nextToken();
						}
	    				String currStatus_ = "null";
						if(st.hasMoreTokens()) {
							currStatus_ = st.nextToken();
						}
	    				String npmdos_ = "null";
						if(st.hasMoreTokens()) {
							npmdos_ = st.nextToken();
						}
	    				String npmasdos_ = "null";
						if(st.hasMoreTokens()) {
							npmasdos_ = st.nextToken();
						}
	    				String canceled_ = "null";
						if(st.hasMoreTokens()) {
							canceled_ = st.nextToken();
						}
	    				String kodeKelas_ = "null";
						if(st.hasMoreTokens()) {
							kodeKelas_ = st.nextToken();
						}
	    				String kodeRuang_ = "null";
						if(st.hasMoreTokens()) {
							kodeRuang_ = st.nextToken();
						}
	    				String kodeGedung_ = "null";
						if(st.hasMoreTokens()) {
							kodeGedung_ = st.nextToken();
						}
	    				
	    				String kodeKampus_ = "null";
						if(st.hasMoreTokens()) {
							kodeKampus_ = st.nextToken();
						}
	    				String tknDayTime_ = "null";
						if(st.hasMoreTokens()) {
							tknDayTime_ = st.nextToken();
						}
	    				String nmmdos_ = "null";
						if(st.hasMoreTokens()) {
							nmmdos_ = st.nextToken();
						}
	    				String nmmasdos_ = "null";
						if(st.hasMoreTokens()) {
							nmmasdos_ = st.nextToken();
						}
	    				String enrolled_ = "null";
						if(st.hasMoreTokens()) {
							enrolled_ = st.nextToken();
						}
	    				String maxEnrolled_ = "null";
						if(st.hasMoreTokens()) {
							maxEnrolled_ = st.nextToken();
						}
	    				String minEnrolled_ = "null";
						if(st.hasMoreTokens()) {
							minEnrolled_ = st.nextToken();
						}
	    				
	    				
	    				
						//if(idkmk_.equalsIgnoreCase(idkmk)&&shift_.equalsIgnoreCase(shift)&&kodeKampus_.equalsIgnoreCase(kodeKampus)) {
						if(idkmk_.equalsIgnoreCase(idkmk)) {
							match = true;
						}
					}
					if(!match) {
						li1.add("drop_req,"+idkmk+","+shift+","+kelas+","+kdkmk);
						//li1.add("drop_req,"+idkmk+","+shift+","+kelas);
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
    	//if(v1!=null) {
    		//harus di sort terbalik dulu agar drop duluan yg dikerjain
    	//	Collections.sort(v1);;
    	//	Collections.reverse(v1);
    	//}
    	return v1;
    }      

    /*
     * KRS NOTIFICATION SECTION
     * DEPRRECATED : USE getReqKrsApprovalNotification(tkn_npm_pa)
     * DI V1 ADA MODIFIKASI YANG RECEIVE BISA DIWAKILKAN OLEH YG LAEN TIDAK HANYA PA MEREKA
     */
    
    public String getReqKrsApprovalNotification() {
    	/*
    	 * fungsi ini ditujukan untuk para PA
    	 */
    	//Vector v = new Vector();
    	//ListIterator li = v.listIterator();
    	String tkn = "";
    	//String thsmsPmb = Checker.getThsmsPmb(); -> diganti thsmsKrs
    	
    	String tkn_npmhs = "";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//target adalah perorangan / namun harus masuk scope si target
    		
    		stmt = con.prepareStatement("select * FROM KRS_NOTIFICATION where KATEGORI=? and NPM_RECEIVER=? and HIDDEN_AT_RECEIVER=? and DELETE_BY_RECEIVER=? and DELETE_BY_SENDER=? and THSMS_TARGET=? ORDER BY THSMS_TARGET,UPDTM");
    		stmt.setString(1,"KRS APPROVAL");
    		stmt.setString(2,this.operatorNpm);
    		stmt.setBoolean(3, false);
    		stmt.setBoolean(4, false);
    		stmt.setBoolean(5, false);
    		stmt.setString(6, Checker.getThsmsKrs());
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String id = ""+rs.getLong("ID");
    			String kstegori = ""+rs.getString("KATEGORI");
    			String thsms = ""+rs.getString("THSMS_TARGET");
    			String berita = ""+rs.getString("BERITA");
    			String npmSender = ""+rs.getString("NPM_SENDER");
    			String nmmSender = ""+rs.getString("NMM_SENDER");
    			String npmReceiver = ""+rs.getString("NPM_RECEIVER");
    			String nmmReceiver = ""+rs.getString("NMM_RECEIVER");
    			String kdpstReceiver = ""+rs.getString("KDPST_RECEIVER");
    			String smawlReceiver = ""+rs.getString("SMAWL_RECEIVER");
    			String hiddenAtSender = ""+rs.getBoolean("HIDDEN_AT_SENDER");
    			String hiddenAtReceiver = ""+rs.getBoolean("HIDDEN_AT_RECEIVER");
    			String delBySender = ""+rs.getBoolean("DELETE_BY_SENDER");
    			String delByReceiver = ""+rs.getBoolean("DELETE_BY_RECEIVER");
    			String approved = ""+rs.getBoolean("APPROVED");
    			String declined = ""+rs.getBoolean("DECLINED");
    			String unlockApproved = ""+rs.getBoolean("UNLOCK_APPROVED");
    			String unlockDeclined = ""+rs.getBoolean("UNLOCK_DECLINED");
    			String note = ""+rs.getString("NOTE");
    			String updtm = ""+rs.getTimestamp("UPDTM");
    			tkn = tkn+id+","+kstegori+","+thsms+","+berita+","+npmSender+","+nmmSender+","+npmReceiver+","+nmmReceiver+","+kdpstReceiver+","+smawlReceiver+","+hiddenAtSender+","+hiddenAtReceiver+","+delBySender+","+delByReceiver+","+approved+","+declined+","+unlockApproved+","+unlockDeclined+","+note+","+updtm+"||";
    			//li.add(id+"#"+kstegori+"#"+thsms+"#"+berita+"#"+npmSender+"#"+nmmSender+"#"+npmReceiver+"#"+nmmReceiver+"#"+kdpstReceiver+"#"+smawlReceiver+"#"+hiddenAtSender+"#"+hiddenAtReceiver+"#"+delBySender+"#"+delByReceiver+"#"+approved+"#"+declined+"#"+note+"#"+updtm);

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
    	return tkn;
    }

    /*
     * KRS NOTIFICATION SECTION UPDATED
     *
     */
    public String getReqKrsApprovalNotification(String tkn_npm_pa, String tkn_scope_kdpst, String tkn_scope_kampus) {
    	/*
    	 * fungsi ini ditujukan untuk para PA
    	 * UPDATED BAGI YANG DIWAKILKAN OLEH YG LAEN
    	 */
    	//STEP 1:
    	//  CEK SCOPE : krsPaApproval
    	//Vector v = new Vector();
    	//ListIterator li = v.listIterator();
    
    	String tkn = "";
    	if(tkn_npm_pa!=null && !Checker.isStringNullOrEmpty(tkn_npm_pa)) {
    		StringTokenizer st = new StringTokenizer(tkn_npm_pa,"`");
    		if(st.countTokens()>0) {
    			//String thsmsPmb = Checker.getThsmsPmb(); -> diganti thsmsKrs
            	String sql_cmd = "NPM_RECEIVER='";
            	while(st.hasMoreTokens()) {
            		String npm_pa = st.nextToken();
            		sql_cmd = sql_cmd + npm_pa;
            		if(st.hasMoreTokens()) {
            			sql_cmd = sql_cmd+"' OR NPM_RECEIVER='"; 
            		}
            	}
            	sql_cmd = sql_cmd+"'";
            	
            	String tkn_npmhs = "";
            	try {
            		Context initContext  = new InitialContext();
            		Context envContext  = (Context)initContext.lookup("java:/comp/env");
            		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            		con = ds.getConnection();
            		//target adalah perorangan / namun harus masuk scope si target
            		
            		//stmt = con.prepareStatement("select * FROM KRS_NOTIFICATION where KATEGORI=? and NPM_RECEIVER=? and HIDDEN_AT_RECEIVER=? and DELETE_BY_RECEIVER=? and DELETE_BY_SENDER=? and THSMS_TARGET=? ORDER BY THSMS_TARGET,UPDTM");
            		stmt = con.prepareStatement("select * FROM KRS_NOTIFICATION where KATEGORI=? and ("+sql_cmd+") and HIDDEN_AT_RECEIVER=? and DELETE_BY_RECEIVER=? and DELETE_BY_SENDER=? and THSMS_TARGET=? ORDER BY THSMS_TARGET,UPDTM");
            		stmt.setString(1,"KRS APPROVAL");
            		//stmt.setString(2,this.operatorNpm);
            		stmt.setBoolean(2, false);
            		stmt.setBoolean(3, false);
            		stmt.setBoolean(4, false);
            		String target_thsms = Checker.getThsmsKrs();
            		stmt.setString(5, target_thsms);
            		rs = stmt.executeQuery();
            		while(rs.next()) {
            			String id = ""+rs.getLong("ID");
            			String kstegori = ""+rs.getString("KATEGORI");
            			String thsms = ""+rs.getString("THSMS_TARGET");
            			String berita = ""+rs.getString("BERITA");
            			String npmSender = ""+rs.getString("NPM_SENDER");
            			String nmmSender = ""+rs.getString("NMM_SENDER");
            			String npmReceiver = ""+rs.getString("NPM_RECEIVER");
            			String nmmReceiver = ""+rs.getString("NMM_RECEIVER");
            			String kdpstReceiver = ""+rs.getString("KDPST_RECEIVER");
            			String smawlReceiver = ""+rs.getString("SMAWL_RECEIVER");
            			String hiddenAtSender = ""+rs.getBoolean("HIDDEN_AT_SENDER");
            			String hiddenAtReceiver = ""+rs.getBoolean("HIDDEN_AT_RECEIVER");
            			String delBySender = ""+rs.getBoolean("DELETE_BY_SENDER");
            			String delByReceiver = ""+rs.getBoolean("DELETE_BY_RECEIVER");
            			String approved = ""+rs.getBoolean("APPROVED");
            			String declined = ""+rs.getBoolean("DECLINED");
            			String unlockApproved = ""+rs.getBoolean("UNLOCK_APPROVED");
            			String unlockDeclined = ""+rs.getBoolean("UNLOCK_DECLINED");
            			String note = ""+rs.getString("NOTE");
            			String updtm = ""+rs.getTimestamp("UPDTM");
            			tkn = tkn+id+","+kstegori+","+thsms+","+berita+","+npmSender+","+nmmSender+","+npmReceiver+","+nmmReceiver+","+kdpstReceiver+","+smawlReceiver+","+hiddenAtSender+","+hiddenAtReceiver+","+delBySender+","+delByReceiver+","+approved+","+declined+","+unlockApproved+","+unlockDeclined+","+note+","+updtm+"||";
            			//li.add(id+"#"+kstegori+"#"+thsms+"#"+berita+"#"+npmSender+"#"+nmmSender+"#"+npmReceiver+"#"+nmmReceiver+"#"+kdpstReceiver+"#"+smawlReceiver+"#"+hiddenAtSender+"#"+hiddenAtReceiver+"#"+delBySender+"#"+delByReceiver+"#"+approved+"#"+declined+"#"+note+"#"+updtm);

            		}
            		/*
            		 * HARUS DI FILTER JUGA BERDASAKAN SCOPE KDPST DAN KAMPUS, KARENA KASUS PUTRI BISA NYEBAR KELUAR SCOPE KDPSTNYA
            		 */
            		//1. add info kdpst & kampus sender
            		StringTokenizer st2 = null;
            		stmt = con.prepareStatement("select KDPSTMSMHS,KODE_KAMPUS_DOMISILI from CIVITAS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where NPMHSMSMHS=?");
            		if(tkn!=null && !Checker.isStringNullOrEmpty(tkn)) {
            			st = new StringTokenizer(tkn,"||");
            			//tkn = tkn+id+","+kstegori+","+thsms+","+berita+","+npmSender+","+nmmSender+","+npmReceiver+","+nmmReceiver+","+kdpstReceiver+","+smawlReceiver+","+hiddenAtSender+","+hiddenAtReceiver+","+delBySender+","+delByReceiver+","+approved+","+declined+","+unlockApproved+","+unlockDeclined+","+note+","+updtm+"||";
            			tkn = "";
            			while(st.hasMoreTokens()) {
            				String one_tkn = st.nextToken();
            				st2 = new StringTokenizer(one_tkn,",");
            				String id = st2.nextToken();
            				String kstegori = st2.nextToken();
            				String thsms = st2.nextToken();
            				String berita = st2.nextToken();
            				String npmSender = st2.nextToken();
            				String nmmSender = st2.nextToken();
            				String npmReceiver = st2.nextToken();
            				String nmmReceiver = st2.nextToken();
            				String kdpstReceiver = st2.nextToken();
            				String smawlReceiver = st2.nextToken();
            				String hiddenAtSender = st2.nextToken();
            				String hiddenAtReceiver = st2.nextToken();
            				String delBySender = st2.nextToken();
            				String delByReceiver = st2.nextToken();
            				String approved = st2.nextToken();
            				String declined = st2.nextToken();
            				String unlockApproved = st2.nextToken();
            				String unlockDeclined = st2.nextToken();
            				String note = st2.nextToken();
            				String updtm = st2.nextToken();
            				stmt.setString(1, npmSender);
            				rs = stmt.executeQuery();
            				rs.next();
            				String kdpst_sender = ""+rs.getString("KDPSTMSMHS");
            				String dom_kmp_sender = ""+rs.getString("KODE_KAMPUS_DOMISILI");
            				tkn = tkn+id+","+kstegori+","+thsms+","+berita+","+npmSender+","+nmmSender+","+npmReceiver+","+nmmReceiver+","+kdpstReceiver+","+smawlReceiver+","+hiddenAtSender+","+hiddenAtReceiver+","+delBySender+","+delByReceiver+","+approved+","+declined+","+unlockApproved+","+unlockDeclined+","+note+","+updtm+","+kdpst_sender+","+dom_kmp_sender+"||";
            			}
            			
            		}
            		//2. filter berdasarkan kdpst & kampus keculai kalo npmPa = npm user
            		if(tkn!=null && !Checker.isStringNullOrEmpty(tkn)) {
            			st = new StringTokenizer(tkn,"||");
            			//tkn = tkn+id+","+kstegori+","+thsms+","+berita+","+npmSender+","+nmmSender+","+npmReceiver+","+nmmReceiver+","+kdpstReceiver+","+smawlReceiver+","+hiddenAtSender+","+hiddenAtReceiver+","+delBySender+","+delByReceiver+","+approved+","+declined+","+unlockApproved+","+unlockDeclined+","+note+","+updtm+"||";
            			tkn = "";
            			while(st.hasMoreTokens()) {
            				String one_tkn = st.nextToken();
            				st2 = new StringTokenizer(one_tkn,",");
            				String id = st2.nextToken();
            				String kstegori = st2.nextToken();
            				String thsms = st2.nextToken();
            				String berita = st2.nextToken();
            				String npmSender = st2.nextToken();
            				String nmmSender = st2.nextToken();
            				String npmReceiver = st2.nextToken();
            				String nmmReceiver = st2.nextToken();
            				String kdpstReceiver = st2.nextToken();
            				String smawlReceiver = st2.nextToken();
            				String hiddenAtSender = st2.nextToken();
            				String hiddenAtReceiver = st2.nextToken();
            				String delBySender = st2.nextToken();
            				String delByReceiver = st2.nextToken();
            				String approved = st2.nextToken();
            				String declined = st2.nextToken();
            				String unlockApproved = st2.nextToken();
            				String unlockDeclined = st2.nextToken();
            				String note = st2.nextToken();
            				String updtm = st2.nextToken();
            				String kdpst_sender = st2.nextToken();
            				String dom_kmp_sender = st2.nextToken();
            				if(!nmmReceiver.equalsIgnoreCase(this.operatorNpm)) {
            					//cek scope kdpst & kmp
            					if(tkn_scope_kampus==null || Checker.isStringNullOrEmpty(tkn_scope_kampus)) {
            						//cek kdpst
            						if(tkn_scope_kdpst.contains(kdpst_sender)) {
            							tkn = tkn+id+","+kstegori+","+thsms+","+berita+","+npmSender+","+nmmSender+","+npmReceiver+","+nmmReceiver+","+kdpstReceiver+","+smawlReceiver+","+hiddenAtSender+","+hiddenAtReceiver+","+delBySender+","+delByReceiver+","+approved+","+declined+","+unlockApproved+","+unlockDeclined+","+note+","+updtm+","+kdpst_sender+","+dom_kmp_sender+"||";
            						}
            					}
            					else {
            						if(tkn_scope_kampus.contains(dom_kmp_sender)) {
            							if(tkn_scope_kdpst.contains(kdpst_sender)) {
                							tkn = tkn+id+","+kstegori+","+thsms+","+berita+","+npmSender+","+nmmSender+","+npmReceiver+","+nmmReceiver+","+kdpstReceiver+","+smawlReceiver+","+hiddenAtSender+","+hiddenAtReceiver+","+delBySender+","+delByReceiver+","+approved+","+declined+","+unlockApproved+","+unlockDeclined+","+note+","+updtm+","+kdpst_sender+","+dom_kmp_sender+"||";
                						}	
            						}
            					}
            				}
            				else {
            					//ignore karena memang PAnya
            				}
            			}
            		}	
            		
            		
            		//filter hanya yg sudah heregistrasi
            		if(tkn!=null && !Checker.isStringNullOrEmpty(tkn)) {
            			st = new StringTokenizer(tkn,"||");
            			//tkn = tkn+id+","+kstegori+","+thsms+","+berita+","+npmSender+","+nmmSender+","+npmReceiver+","+nmmReceiver+","+kdpstReceiver+","+smawlReceiver+","+hiddenAtSender+","+hiddenAtReceiver+","+delBySender+","+delByReceiver+","+approved+","+declined+","+unlockApproved+","+unlockDeclined+","+note+","+updtm+"||";
            			tkn = "";
            			while(st.hasMoreTokens()) {
            				String one_tkn = st.nextToken();
            				st2 = new StringTokenizer(one_tkn,",");
            				String id = st2.nextToken();
            				String kstegori = st2.nextToken();
            				String thsms = st2.nextToken();
            				String berita = st2.nextToken();
            				String npmSender = st2.nextToken();
            				String nmmSender = st2.nextToken();
            				String npmReceiver = st2.nextToken();
            				String nmmReceiver = st2.nextToken();
            				String kdpstReceiver = st2.nextToken();
            				String smawlReceiver = st2.nextToken();
            				String hiddenAtSender = st2.nextToken();
            				String hiddenAtReceiver = st2.nextToken();
            				String delBySender = st2.nextToken();
            				String delByReceiver = st2.nextToken();
            				String approved = st2.nextToken();
            				String declined = st2.nextToken();
            				String unlockApproved = st2.nextToken();
            				String unlockDeclined = st2.nextToken();
            				String note = st2.nextToken();
            				String updtm = st2.nextToken();
            				String kdpst_sender = st2.nextToken();
            				String dom_kmp_sender = st2.nextToken();
            				String pesan = Checker.sudahDaftarUlang(kdpst_sender, npmSender, target_thsms);
            				if(pesan==null) {
            					tkn = tkn+id+","+kstegori+","+thsms+","+berita+","+npmSender+","+nmmSender+","+npmReceiver+","+nmmReceiver+","+kdpstReceiver+","+smawlReceiver+","+hiddenAtSender+","+hiddenAtReceiver+","+delBySender+","+delByReceiver+","+approved+","+declined+","+unlockApproved+","+unlockDeclined+","+note+","+updtm+","+kdpst_sender+","+dom_kmp_sender+"||";
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
    		}
        	
    	}
    	
    	
    	return tkn;
    }

    public String getNonHiddenReqKrsApprovalNotificationForSender() {
    	/*
    	 * fungsi ini ditujukan untuk para pengirim request hanya 
    	 */
    	//Vector v = new Vector();
    	//ListIterator li = v.listIterator();
    	String tkn = "";
    	//String thsmsPmb = Checker.getThsmsPmb(); --> diganti thsmsKrs
    	
    	String tkn_npmhs = "";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * FROM KRS_NOTIFICATION where KATEGORI=? and NPM_SENDER=? and DELETE_BY_RECEIVER=? and DELETE_BY_SENDER=? and THSMS_TARGET=? ORDER BY THSMS_TARGET,UPDTM");
    		stmt.setString(1,"KRS APPROVAL");
    		stmt.setString(2,this.operatorNpm);
    		//stmt.setBoolean(3, false);
    		stmt.setBoolean(3, false);
    		stmt.setBoolean(4, false);
    		String target_thsms = Checker.getThsmsKrs();
    		stmt.setString(5, target_thsms);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String id = ""+rs.getLong("ID");
    			String kstegori = ""+rs.getString("KATEGORI");
    			String thsms = ""+rs.getString("THSMS_TARGET");
    			String berita = ""+rs.getString("BERITA");
    			String npmSender = ""+rs.getString("NPM_SENDER");
    			String nmmSender = ""+rs.getString("NMM_SENDER");
    			String npmReceiver = ""+rs.getString("NPM_RECEIVER");
    			String nmmReceiver = ""+rs.getString("NMM_RECEIVER");
    			String kdpstReceiver = ""+rs.getString("KDPST_RECEIVER");
    			String smawlReceiver = ""+rs.getString("SMAWL_RECEIVER");
    			String hiddenAtSender = ""+rs.getBoolean("HIDDEN_AT_SENDER");
    			String hiddenAtReceiver = ""+rs.getBoolean("HIDDEN_AT_RECEIVER");
    			String delBySender = ""+rs.getBoolean("DELETE_BY_SENDER");
    			String delByReceiver = ""+rs.getBoolean("DELETE_BY_RECEIVER");
    			String approved = ""+rs.getBoolean("APPROVED");
    			String declined = ""+rs.getBoolean("DECLINED");
    			String unlockApproved = ""+rs.getBoolean("UNLOCK_APPROVED");
    			String unlockDeclined = ""+rs.getBoolean("UNLOCK_DECLINED");
    			String note = ""+rs.getString("NOTE");
    			String updtm = ""+rs.getTimestamp("UPDTM");
    			
    			if(hiddenAtSender.equalsIgnoreCase("false")) {
    				tkn = tkn+id+","+kstegori+","+thsms+","+berita+","+npmSender+","+nmmSender+","+npmReceiver+","+nmmReceiver+","+kdpstReceiver+","+smawlReceiver+","+hiddenAtSender+","+hiddenAtReceiver+","+delBySender+","+delByReceiver+","+approved+","+declined+","+unlockApproved+","+unlockDeclined+","+note+","+updtm+"||";
    			}
    				//li.add(id+"#"+kstegori+"#"+thsms+"#"+berita+"#"+npmSender+"#"+nmmSender+"#"+npmReceiver+"#"+nmmReceiver+"#"+kdpstReceiver+"#"+smawlReceiver+"#"+hiddenAtSender+"#"+hiddenAtReceiver+"#"+delBySender+"#"+delByReceiver+"#"+approved+"#"+declined+"#"+note+"#"+updtm);

    		}
    		
    		/*
    		 * !! updated 2 maret 2016 agar ada filter yg sudah heregistrasi saja
    		*/
    		StringTokenizer st2 = null;
    		stmt = con.prepareStatement("select KDPSTMSMHS,KODE_KAMPUS_DOMISILI from CIVITAS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where NPMHSMSMHS=?");
    		if(tkn!=null && !Checker.isStringNullOrEmpty(tkn)) {
    			StringTokenizer st = new StringTokenizer(tkn,"||");
    			//tkn = tkn+id+","+kstegori+","+thsms+","+berita+","+npmSender+","+nmmSender+","+npmReceiver+","+nmmReceiver+","+kdpstReceiver+","+smawlReceiver+","+hiddenAtSender+","+hiddenAtReceiver+","+delBySender+","+delByReceiver+","+approved+","+declined+","+unlockApproved+","+unlockDeclined+","+note+","+updtm+"||";
    			tkn = "";
    			while(st.hasMoreTokens()) {
    				String one_tkn = st.nextToken();
    				st2 = new StringTokenizer(one_tkn,",");
    				String id = st2.nextToken();
    				String kstegori = st2.nextToken();
    				String thsms = st2.nextToken();
    				String berita = st2.nextToken();
    				String npmSender = st2.nextToken();
    				String nmmSender = st2.nextToken();
    				String npmReceiver = st2.nextToken();
    				String nmmReceiver = st2.nextToken();
    				String kdpstReceiver = st2.nextToken();
    				String smawlReceiver = st2.nextToken();
    				String hiddenAtSender = st2.nextToken();
    				String hiddenAtReceiver = st2.nextToken();
    				String delBySender = st2.nextToken();
    				String delByReceiver = st2.nextToken();
    				String approved = st2.nextToken();
    				String declined = st2.nextToken();
    				String unlockApproved = st2.nextToken();
    				String unlockDeclined = st2.nextToken();
    				String note = st2.nextToken();
    				String updtm = st2.nextToken();
    				stmt.setString(1, npmSender);
    				rs = stmt.executeQuery();
    				rs.next();
    				String kdpst_sender = ""+rs.getString("KDPSTMSMHS");
    				String dom_kmp_sender = ""+rs.getString("KODE_KAMPUS_DOMISILI");
    				tkn = tkn+id+","+kstegori+","+thsms+","+berita+","+npmSender+","+nmmSender+","+npmReceiver+","+nmmReceiver+","+kdpstReceiver+","+smawlReceiver+","+hiddenAtSender+","+hiddenAtReceiver+","+delBySender+","+delByReceiver+","+approved+","+declined+","+unlockApproved+","+unlockDeclined+","+note+","+updtm+","+kdpst_sender+","+dom_kmp_sender+"||";
    			}
    			
    		}
    		//filter hanya yg sudah heregistrasi
    		if(tkn!=null && !Checker.isStringNullOrEmpty(tkn)) {
    			StringTokenizer st = new StringTokenizer(tkn,"||");
    			//tkn = tkn+id+","+kstegori+","+thsms+","+berita+","+npmSender+","+nmmSender+","+npmReceiver+","+nmmReceiver+","+kdpstReceiver+","+smawlReceiver+","+hiddenAtSender+","+hiddenAtReceiver+","+delBySender+","+delByReceiver+","+approved+","+declined+","+unlockApproved+","+unlockDeclined+","+note+","+updtm+"||";
    			tkn = "";
    			while(st.hasMoreTokens()) {
    				String one_tkn = st.nextToken();
    				st2 = new StringTokenizer(one_tkn,",");
    				String id = st2.nextToken();
    				String kstegori = st2.nextToken();
    				String thsms = st2.nextToken();
    				String berita = st2.nextToken();
    				String npmSender = st2.nextToken();
    				String nmmSender = st2.nextToken();
    				String npmReceiver = st2.nextToken();
    				String nmmReceiver = st2.nextToken();
    				String kdpstReceiver = st2.nextToken();
    				String smawlReceiver = st2.nextToken();
    				String hiddenAtSender = st2.nextToken();
    				String hiddenAtReceiver = st2.nextToken();
    				String delBySender = st2.nextToken();
    				String delByReceiver = st2.nextToken();
    				String approved = st2.nextToken();
    				String declined = st2.nextToken();
    				String unlockApproved = st2.nextToken();
    				String unlockDeclined = st2.nextToken();
    				String note = st2.nextToken();
    				String updtm = st2.nextToken();
    				String kdpst_sender = st2.nextToken();
    				String dom_kmp_sender = st2.nextToken();
    				String pesan = Checker.sudahDaftarUlang(kdpst_sender, npmSender, target_thsms);
    				if(pesan==null) {
    					tkn = tkn+id+","+kstegori+","+thsms+","+berita+","+npmSender+","+nmmSender+","+npmReceiver+","+nmmReceiver+","+kdpstReceiver+","+smawlReceiver+","+hiddenAtSender+","+hiddenAtReceiver+","+delBySender+","+delByReceiver+","+approved+","+declined+","+unlockApproved+","+unlockDeclined+","+note+","+updtm+","+kdpst_sender+","+dom_kmp_sender+"||";
    				}
    			}
    		}
    		
    		/*
    		 * !! ende updated 2 maret 2016 agar ada filter yg sudah heregistrasi saja
    		 */
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
    	return tkn;
    }

    public String[] getObjectAccessLevel() {
    	String []tmp = new String[2];
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select ID_OBJ FROM CIVITAS where NPMHSMSMHS=?");
    		stmt.setString(1, this.operatorNpm);
    		rs = stmt.executeQuery();
    		rs.next();
    		long objId = rs.getLong("ID_OBJ");
    		stmt = con.prepareStatement("select * FROM OBJECT where ID_OBJ=?");
    		stmt.setLong(1,objId);
    		rs = stmt.executeQuery();
    		rs.next();
    		tmp[0] = ""+rs.getString("ACCESS_LEVEL_CONDITIONAL");
    		tmp[1] = ""+rs.getString("ACCESS_LEVEL");
    		
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
    	return tmp;
    }
    
    
    public String getProdiYgBlmMengajukanBukaKelas() {
    	String thsmsPmb = Checker.getThsmsBukaKelas();
    	String tmp = "";
    	String tmp1 = "";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from MSPST where KDJENMSPST='A' OR KDJENMSPST='B' OR KDJENMSPST='C' OR KDJENMSPST='D' OR KDJENMSPST='E' OR KDJENMSPST='F' OR KDJENMSPST='G' OR KDJENMSPST='H' OR KDJENMSPST='I' OR KDJENMSPST='J' OR KDJENMSPST='X' order BY KDJENMSPST,KDPSTMSPST");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String kdjen = rs.getString("KDJENMSPST");
    			String kdpst = rs.getString("KDPSTMSPST");
    			String nmpst = rs.getString("NMPSTMSPST");
    			tmp = tmp+kdjen+"||"+kdpst+"||"+nmpst+"||";
    		}
    		StringTokenizer st = new StringTokenizer(tmp,"||");
    		stmt = con.prepareStatement("select * from CLASS_POOL where THSMS=? and KDPST=?");
    		while(st.hasMoreTokens()) {
    			String kdjen = st.nextToken();
    			String kdpst = st.nextToken();
    			String nmpst = st.nextToken();
    			stmt.setString(1, thsmsPmb);
    			stmt.setString(2, kdpst);
    			rs = stmt.executeQuery();
    			if(!rs.next()) {
    				tmp1 = tmp1+kdjen+"||"+kdpst+"||"+nmpst+"||";
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
    	return tmp1;
    }    
    
    public Vector getRelatedInfoRequestBukaKelas(InitSessionUsr isu) {
    	/*
    	 * cek apa ada request buka kelas within scope user dgn cara
    	 * apa user punya hak untuk request buka kelas ato approv kelas
    	 */
    	String thsmsPmb = Checker.getThsmsBukaKelas();
    	Vector vfinal = new Vector();
    	ListIterator lifinal = vfinal.listIterator();
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	String[]aksesInfo = getObjectAccessLevel();
    	//System.out.println("getStatusRequestBukaKelas");
    	//System.out.println(aksesInfo[0]);//System.out.println(aksesInfo[1]);
    	if(aksesInfo[1].contains("BukaKelas")) {
    		
    		Vector vCp = new Vector();
    		ListIterator li = vCp.listIterator();
    		Vector vRul = new Vector();
    		ListIterator liRul = vRul.listIterator();
    		//Vector v = isu.getScopeUpd7des2012("reqBukaKelas");
    		//ListIterator li = v.listIterator();
    		//while(li.hasNext()) {
    		//	String brs = (String)li.next();
    		//	//System.out.println(brs);
    		//}
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from CLASS_POOL inner join KRKLM on IDKUR=IDKURKRKLM where THSMS=? and CANCELED=? order by KDPST");
    	    	//stmt = con.prepareStatement("select * from CLASS_POOL where THSMS=? and CANCELED=? order by KDPST");
    	    	//stmt = con.prepareStatement("select * from CLASS_POOL where THSMS=? and CANCELED=? and LOCKED=? order by KDPST");
    	    	stmt.setString(1, thsmsPmb);
    	    	stmt.setBoolean(2, false);
    	    	//stmt.setBoolean(3, false);
    	    	rs = stmt.executeQuery();
    	    	while(rs.next()) {
    	    		String idkur = ""+rs.getLong("IDKUR");
    	    		String idkmk = ""+rs.getLong("IDKMK");
    	    		String thsms = ""+rs.getString("THSMS");
    	    		String kdpst = ""+rs.getString("KDPST");
    	    		String shift = ""+rs.getString("SHIFT");
    	    		String noKlsPll = ""+rs.getInt("NORUT_KELAS_PARALEL");
    	    		String initNpmInput = ""+rs.getString("INIT_NPM_INPUT");
    	    		String latestNpmUpdate = ""+rs.getString("LATEST_NPM_UPDATE");
    	    		String latestStatusInfo = ""+rs.getString("LATEST_STATUS_INFO");
    	    		String locked = ""+rs.getBoolean("LOCKED");
    	    		String npmdos = ""+rs.getString("NPMDOS");
    	    		String nodos = ""+rs.getString("NODOS");
    	    		String npmasdos = ""+rs.getString("NPMASDOS");
    	    		String noasdos = ""+rs.getString("NOASDOS");
    	    		String cancel = ""+rs.getBoolean("CANCELED");
    	    		String kodeKelas = ""+rs.getString("KODE_KELAS");
    	    		String kodeRuang = ""+rs.getString("KODE_RUANG");
    	    		String kodeGedung = ""+rs.getString("KODE_GEDUNG");
    	    		String kodeKampus = ""+rs.getString("KODE_KAMPUS");
    	    		String tknHrTime = ""+rs.getString("TKN_HARI_TIME");
    	    		String nmmdos = ""+rs.getString("NMMDOS");
    	    		String nmmasdos = ""+rs.getString("NMMASDOS");
    	    		String enrolled = ""+rs.getInt("ENROLLED");
    	    		String maxEnrol = ""+rs.getInt("MAX_ENROLLED");
    	    		String minEnrol = ""+rs.getInt("MIN_ENROLLED");
    	    		String subKeterMk = ""+rs.getString("SUB_KETER_KDKMK");
    	    		String initReqTime=""+rs.getTimestamp("INIT_REQ_TIME");
    	    		String tknNpmApproval = ""+rs.getString("TKN_NPM_APPROVAL");
    	    		String tknApprovalTime = ""+rs.getString("TKN_APPROVAL_TIME");
    	    		String targetTotMhs = ""+rs.getInt("TARGET_TTMHS");
    	    		String passed = ""+rs.getBoolean("PASSED");
    	    		String rejected = ""+rs.getBoolean("REJECTED");
    	    		String konsen = ""+rs.getString("KONSENTRASI");
    	    		//String tmp = idkur+"||"+idkmk+"||"+thsms+"||"+kdpst+"||"+shift+"||"+noKlsPll+"||"+initNpmInput+"||"+latestNpmUpdate+"||"+npmdos+"||"+nodos+"||"+npmasdos+"||"+noasdos+"||"+kodeKelas+"||"+kodeRuang+"||"+kodeGedung+"||"+kodeKampus+"||"+tknHrTime+"||"+nmmdos+"||"+nmmasdos+"||"+enrolled+"||"+maxEnrol+"||"+minEnrol+"||"+subKeterMk+"||"+initReqTime+"||"+tknNpmApproval+"||"+norutApproval+"||"+targetTotMhs;
    	    		
    	    		String tmp = idkur+"||"+idkmk+"||"+thsms+"||"+kdpst+"||"+shift+"||"+noKlsPll+"||"+initNpmInput+"||"+latestNpmUpdate+"||"+latestStatusInfo+"||"+locked+"||"+npmdos+"||"+nodos+"||"+npmasdos+"||"+noasdos+"||"+cancel+"||"+kodeKelas+"||"+kodeRuang+"||"+kodeGedung+"||"+kodeKampus+"||"+tknHrTime+"||"+nmmdos+"||"+nmmasdos+"||"+enrolled+"||"+maxEnrol+"||"+minEnrol+"||"+subKeterMk+"||"+initReqTime+"||"+tknNpmApproval+"||"+tknApprovalTime+"||"+targetTotMhs+"||"+passed+"||"+rejected+"||"+konsen; 
    	    		li.add(tmp);
    	    		
    	    	}
    	    	//rules
    	    	/*
    	    	stmt = con.prepareStatement("select * from CLASS_POOL_RULES where THSMS=?");
    	    	stmt.setString(1, thsmsPmb);
    	    	rs = stmt.executeQuery();
    	    	while(rs.next()) {
    	    		//String thsms = ""+rs.getString("THSMS");
    	    		String kdpst = ""+rs.getString("KDPST");
    	    		String tknVerificator = ""+rs.getString("TKN_VERIFICATOR");
    	    		StringTokenizer st = new StringTokenizer(tknVerificator,",");
    	    		liRul.add(thsmsPmb+"||"+kdpst+"||"+tknVerificator+"||"+st.countTokens());
    	    	}
    	    	*/
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
    					//System.out.println(ignore);
    				}
    			}
    		}
    		
    		if(vCp!=null && vCp.size()>0) {
    			Vector v = isu.getScopeUpd7des2012("reqBukaKelas");
    			//Vector v = isu.getScopeCommandLike("reqBukaKelas");
    			if(v==null) {
    				v = new Vector();
    			}
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs);
    				String idObj = st.nextToken();
    				String kdpst = st.nextToken();
    				String objDesc = st.nextToken();
    				String objLvl = st.nextToken();
    				String aksesLvl = st.nextToken();
    				
    				ListIterator liCp = vCp.listIterator();
    				while(liCp.hasNext()) {
    					String brs1 = (String)liCp.next();
    					
    					brs1 = brs1.replace("||||", "||null||");
    					
    					st = new StringTokenizer(brs1,"||");
    					
    					String idkur1 = st.nextToken();
    					String idkmk1 = st.nextToken();
        	    		String thsms1 = st.nextToken();
        	    		String kdpst1 = st.nextToken();
        	    		String shift1 = st.nextToken();
        	    		String noKlsPll1 = st.nextToken();
        	    		String initNpmInput1 = st.nextToken();
        	    		String latestNpmUpdate1 = st.nextToken();
        	    		String latestStatusInfo1 = st.nextToken();
        	    		String locked1 = st.nextToken();
        	    		String npmdos1 = st.nextToken();
        	    		String nodos1 = st.nextToken();
        	    		String npmasdos1 = st.nextToken();
        	    		String noasdos1 = st.nextToken();
        	    		String cancel = st.nextToken();
        	    		String kodeKelas1 = st.nextToken();
        	    		String kodeRuang1 = st.nextToken();
        	    		String kodeGedung1 = st.nextToken();
        	    		String kodeKampus1 = st.nextToken();
        	    		String tknHrTime1 = st.nextToken();
        	    		String nmmdos1 = st.nextToken();
        	    		String nmmasdos1 = st.nextToken();
        	    		String enrolled1 = st.nextToken();
        	    		String maxEnrol1 = st.nextToken();
        	    		String minEnrol1 = st.nextToken();
        	    		String subKeterMk1 = st.nextToken();
        	    		String initReqTime1 = st.nextToken();
        	    		String tknNpmApproval1 = st.nextToken();
        	    		String tknApprovalTime1 = st.nextToken();
        	    		String targetTotMhs1 = st.nextToken();
        	    		String passed1 = st.nextToken();
        	    		String rejected1 = st.nextToken();
        	    		String konsen1 = st.nextToken();
        	    		if(kdpst.equalsIgnoreCase(kdpst1)) {
        	    			lif.add("reqBukaKelas||"+brs1);
        	    		}
    				}
    			}	
    			//lifinal.add(vf);
    			//System.out.println("vf Size="+vf.size());
    			/*
    			 * approval right section
    			 */
    			Vector vAprLvl = new Vector();
    			ListIterator liTmp = vAprLvl.listIterator();
    			StringTokenizer st = new StringTokenizer (aksesInfo[1],"#");
    			while(st.hasMoreTokens()) {
    				String rights = (String)st.nextToken();
    				if(rights.contains("BukaKelas") && !rights.equalsIgnoreCase("reqBukaKelas")) {
    					liTmp.add(rights);
    					//System.out.println("rights added="+rights);
    				}
    			}
    			//System.out.println("vAprLvl="+vAprLvl.size());
    			if(vAprLvl.size()>0) {
    				ListIterator liAprLvl = vAprLvl.listIterator();
    				while(liAprLvl.hasNext()) {
	    				Vector vTmp = null;
    					String cmd = (String) liAprLvl.next();
    					//System.out.println("cmd i ="+cmd);
    					vTmp = isu.getScopeUpd7des2012(cmd);
    	    			liTmp = vTmp.listIterator();
    	    			while(liTmp.hasNext()) {
    	    				String brs = (String)liTmp.next();
    	    				st = new StringTokenizer(brs);
    	    				String idObj = st.nextToken();
    	    				String kdpst = st.nextToken();
    	    				String objDesc = st.nextToken();
    	    				String objLvl = st.nextToken();
    	    				String aksesLvl = st.nextToken();
    	    				ListIterator liCp = vCp.listIterator();
    	    				while(liCp.hasNext()) {
    	    					String brs1 = (String)liCp.next();
    	    					brs1 = brs1.replace("||||", "||null||");
    	    					//System.out.println("xx="+brs1);
    	    					st = new StringTokenizer(brs1,"||");
    	    					String idkur1 = st.nextToken();
    	    					String idkmk1 = st.nextToken();
    	        	    		String thsms1 = st.nextToken();
    	        	    		String kdpst1 = st.nextToken();
    	        	    		String shift1 = st.nextToken();
    	        	    		String noKlsPll1 = st.nextToken();
    	        	    		String initNpmInput1 = st.nextToken();
    	        	    		String latestNpmUpdate1 = st.nextToken();
    	        	    		String latestStatusInfo1 = st.nextToken();
    	        	    		String locked1 = st.nextToken();
    	        	    		String npmdos1 = st.nextToken();
    	        	    		String nodos1 = st.nextToken();
    	        	    		String npmasdos1 = st.nextToken();
    	        	    		String noasdos1 = st.nextToken();
    	        	    		String cancel = st.nextToken();
    	        	    		String kodeKelas1 = st.nextToken();
    	        	    		String kodeRuang1 = st.nextToken();
    	        	    		String kodeGedung1 = st.nextToken();
    	        	    		String kodeKampus1 = st.nextToken();
    	        	    		String tknHrTime1 = st.nextToken();
    	        	    		String nmmdos1 = st.nextToken();
    	        	    		String nmmasdos1 = st.nextToken();
    	        	    		String enrolled1 = st.nextToken();
    	        	    		String maxEnrol1 = st.nextToken();
    	        	    		String minEnrol1 = st.nextToken();
    	        	    		String subKeterMk1 = st.nextToken();
    	        	    		String initReqTime1 = st.nextToken();
    	        	    		String tknNpmApproval1 = st.nextToken();
    	        	    		String tknApprovalTime1 = st.nextToken();
    	        	    		String targetTotMhs1 = st.nextToken();
    	        	    		String passed1 = st.nextToken();
    	        	    		String rejected1 = st.nextToken();
    	        	    		String konsen1 = st.nextToken();
    	        	    		if(kdpst.equalsIgnoreCase(kdpst1)) {
    	        	    			lif.add(cmd+"||"+brs1);
    	        	    		}
    	    				}
    	    			}	
    				}
    			}//end if cp	

    		}
    		
    	}	
    	return vf;
    }
    
    
    public String getInfoNotificationAtPmb(String npmhs) {
    	/*
    	 * fungsi ini ditujukan untuk para pengirim request hanya 
    	 */
    	//Vector v = new Vector();
    	//ListIterator li = v.listIterator();
    	String tkn = "";
    	String thsmsPmb = Checker.getThsmsPmb();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * FROM KRS_NOTIFICATION where KATEGORI=? and NPM_SENDER=? and THSMS_TARGET=?");
    		stmt.setString(1,"KRS APPROVAL");
    		if(npmhs==null || Checker.isStringNullOrEmpty(npmhs)) {
    			stmt.setString(2,this.operatorNpm);
    		}
    		else {
    			stmt.setString(2,npmhs);
    		}
    		stmt.setString(3, thsmsPmb);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			//System.out.println("rs.next()@getInfoNotificationAtPmb");
    			String id = ""+rs.getLong("ID");
    			String kstegori = ""+rs.getString("KATEGORI");
    			String thsms = ""+rs.getString("THSMS_TARGET");
    			String berita = ""+rs.getString("BERITA");
    			String npmSender = ""+rs.getString("NPM_SENDER");
    			String nmmSender = ""+rs.getString("NMM_SENDER");
    			String npmReceiver = ""+rs.getString("NPM_RECEIVER");
    			String nmmReceiver = ""+rs.getString("NMM_RECEIVER");
    			String kdpstReceiver = ""+rs.getString("KDPST_RECEIVER");
    			String smawlReceiver = ""+rs.getString("SMAWL_RECEIVER");
    			String hiddenAtSender = ""+rs.getBoolean("HIDDEN_AT_SENDER");
    			String hiddenAtReceiver = ""+rs.getBoolean("HIDDEN_AT_RECEIVER");
    			String delBySender = ""+rs.getBoolean("DELETE_BY_SENDER");
    			String delByReceiver = ""+rs.getBoolean("DELETE_BY_RECEIVER");
    			String approved = ""+rs.getBoolean("APPROVED");
    			String declined = ""+rs.getBoolean("DECLINED");
    			String unlockApproved = ""+rs.getBoolean("UNLOCK_APPROVED");
    			String unlockDeclined = ""+rs.getBoolean("UNLOCK_DECLINED");
    			String note = ""+rs.getString("NOTE");
    			String updtm = ""+rs.getTimestamp("UPDTM");
    			tkn = id+"#"+kstegori+"#"+thsms+"#"+berita+"#"+npmSender+"#"+nmmSender+"#"+npmReceiver+"#"+nmmReceiver+"#"+kdpstReceiver+"#"+smawlReceiver+"#"+hiddenAtSender+"#"+hiddenAtReceiver+"#"+delBySender+"#"+delByReceiver+"#"+approved+"#"+declined+"#"+unlockApproved+"#"+unlockDeclined+"#"+note+"#"+updtm;

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
    	return tkn;
    }

    /*
     * END KRS NOTIFICATION SECTION
     */
   
    /*
     * depricated
     * guanaan yg ada variable kodeKampus
     * 
     */
    public boolean cekApaRequestKelasTelahDiLock(String kdpst) {
    	/*
    	 * fungsi ini ditujukan untuk ngecek apa kdpst ini sudah mengajukan buka kelas
    	 * dan telah disetujui oleh approval tertinggi 
    	 */
    	boolean locked  = false;
    	String thsmsPmb = Checker.getThsmsPmb();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * FROM CLASS_POOL where THSMS=? and KDPST=? and LOCKED=?");
    		stmt.setString(1,thsmsPmb);
    		stmt.setString(2,kdpst);
    		stmt.setBoolean(3,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			locked = true;
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
    	return locked;
    }	
    
    public boolean cekApaRequestKelasTelahDiLock(String kdpst, String kodeKampus) {
    	/*
    	 * fungsi ini ditujukan untuk ngecek apa kdpst ini sudah mengajukan buka kelas
    	 * dan telah disetujui oleh approval tertinggi 
    	 */
    	boolean locked  = false;
    	String thsmsPmb = Checker.getThsmsPmb();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * FROM CLASS_POOL where THSMS=? and KDPST=? and kodeKampus=? and LOCKED=?");
    		stmt.setString(1,thsmsPmb);
    		stmt.setString(2,kdpst);
    		stmt.setString(3,kodeKampus);
    		stmt.setBoolean(4,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			locked = true;
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
    	return locked;
    }	
    
    
    /*
     * deprecated
     */
    public Vector getNonHiddenNonDeletedTopikForOrFromBaaBasedOnObjNickName(String TargetObjNickname, String startAt, String quantity) {
    	/*
    	 * fungsi ini ditujukan untuk get topik yang ditujukan ke baa atau dari baa ke kita
    	 */
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	boolean locked  = false;
    	String thsmsPmb = Checker.getThsmsPmb();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//get topic yg kita buat untuk
    		//stmt = con.prepareStatement("select * from TOPIK where ((NPMHSCREATOR=? and TARGETOBJECTNICKNAME=?) or (CREATOROBJNICKNAME=? and TARGETNPMHS=?)) and DELETEDBYCREATOR=? order by UPDTMTOPIK desc limit ?,?");
    		//stmt = con.prepareStatement("select * from TOPIK where ((NPMHSCREATOR=? and TARGETOBJECTNICKNAME like ?) or (CREATOROBJNICKNAME=? and TARGETNPMHS LIKE ?)) and DELETEDBYCREATOR=? order by UPDTMTOPIK desc limit ?,?");
    		stmt = con.prepareStatement("select * from TOPIK where (TARGETOBJECTNICKNAME like ? or CREATOROBJNICKNAME LIKE ?) and DELETEDBYCREATOR=? order by UPDTMTOPIK desc limit ?,?");
    		//stmt.setString(1, this.operatorNpm);
    		stmt.setString(1,TargetObjNickname);
    		stmt.setString(2,TargetObjNickname);
    		stmt.setBoolean(3,false);
    		//stmt.setString(3,TargetObjNickname);
    		//stmt.setString(4,"%"+this.operatorNpm+"%");
    		//stmt.setBoolean(5,false);
    		stmt.setInt(4, Integer.valueOf(startAt).intValue());
    		stmt.setInt(5, Integer.valueOf(quantity).intValue());
    		
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			long idTopik = rs.getLong("IDTOPIK");
    			String content = rs.getString("TOPIKCONTENT");
    			String npmhsCreator = ""+rs.getString("NPMHSCREATOR");
    			String nmmhsCreator = ""+rs.getString("NMMHSCREATOR");
    			String creatorObjId = ""+rs.getString("CRETOROBJECTID");
    			String creatorObjNickName = ""+rs.getString("CREATOROBJNICKNAME");
    			String targetKdpst = ""+rs.getString("TARGETKDPST");
    			String targetNpmhs = ""+rs.getString("TARGETNPMHS");
    			String targetSmawl = ""+rs.getString("TARGETSMAWL");
    			String targetObjId = ""+rs.getString("TARGETOBJEKID");
    			String targetObjNickName = ""+rs.getString("TARGETOBJECTNICKNAME");
    			String targetGroupId = ""+rs.getString("TARGETGROUPID");
    			String groupPwd = ""+rs.getString("GROUPPWD");
    			String showOnlyToGroup = ""+rs.getString("SHOWNTOGROUPONLY");
    			boolean deletedByCreator = rs.getBoolean("DELETEDBYCREATOR");
    			boolean hiddenAtCreator = rs.getBoolean("HIDENATCREATOR");
    			boolean pinnedAtCreator = rs.getBoolean("PINEDATCREATOR");
    			boolean markedAsReadAtCreator = rs.getBoolean("MARKEDASREADATCREATOR");
    			String deletedAtTarget = rs.getString("DELETEDATTARGET");
    			String hiddenAtTarget = rs.getString("HIDENATTARGET");
    			String pinnedAtTartget = rs.getString("PINEDATTARGET");
    			String markedAsReadAtTarget = rs.getString("MARKEDASREADATTARGET");
    			boolean creatorAsAnonymous = rs.getBoolean("CREATORASANONYMOUS");
    			boolean cretorIsPetugas = rs.getBoolean("CRETORISPETUGAS");
    			String updtm = ""+rs.getTimestamp("UPDTMTOPIK");	
    			//boolean included = true;
    			//if(creatorObjNickName.equalsIgnoreCase(TargetObjNickname) && !targetNpmhs.contains(this.operatorNpm)) {
    			//	included = false;
    			//}
    			//}
    			//else {
    			//	if(!deletedByCreator && hiddenAtTarget!=null && !Checker.isStringNullOrEmpty(hiddenAtTarget) && hiddenAtTarget.contains(this.operatorNpm)) {
    			//		deletedOrHidden = true;
    			//	}
    			//}
    			//System.out.println("idtopik & hidden = "+idTopik+" & "+deletedOrHidden);
    			//if(included) {
    				String tmp =idTopik+"||"+content+"||"+npmhsCreator+"||"+nmmhsCreator+"||"+creatorObjId+"||"+creatorObjNickName+"||"+targetKdpst+"||"+targetNpmhs+"||"+targetSmawl+"||"+targetObjId+"||"+targetObjNickName+"||"+targetGroupId+"||"+groupPwd+"||"+showOnlyToGroup+"||"+deletedByCreator+"||"+hiddenAtCreator+"||"+pinnedAtCreator+"||"+markedAsReadAtCreator+"||"+deletedAtTarget+"||"+hiddenAtTarget+"||"+pinnedAtTartget+"||"+markedAsReadAtTarget+"||"+creatorAsAnonymous+"||"+cretorIsPetugas+"||"+updtm;
    				li.add(tmp);
    			//}	
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
  
    public Vector getMostRecentMsgWithLimit(String objNickNameUsr,Vector vScopeObjNicknameOwnInbox, String startAt, String quantity) {
    	/*
    	 * recent msg dari inbox jadi bisa ada beberapa tipe objek
    	 */
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	boolean locked  = false;
    	int counter = 0;
    	try {
    		if(vScopeObjNicknameOwnInbox!=null || vScopeObjNicknameOwnInbox.size()>0) {
    			counter = vScopeObjNicknameOwnInbox.size();
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			counter = vScopeObjNicknameOwnInbox.size();
    			//sqlStmt = "select * from SUBTOPIK inner join TOPIK on SUBTOPIK.IDTOPIK=TOPIK.IDTOPIK where (SUBTOPIK.NPMHSRECEIVER like ? or ";
				/*
				 * 1. cek  msg at topik table only - blm ada stu komen pun
				 */
    			String sqlStmt = "";
    			boolean first = true;
				int j = 1;
				ListIterator li1 = vScopeObjNicknameOwnInbox.listIterator();    			
				/*
				 * topik table
				 * jika targetNpmhs=null & targetObjectNickname!= null maka tar
				 */
				sqlStmt = "select * from TOPIK left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where (TOPIK.TARGETNPMHS like ? or SUBTOPIK.NPMHSRECEIVER like ?) ";
    			first = true;
    			String tmp1 = "";
    			String tmp2 = "";
    			for(int i=0;i<counter;i++) {
    				
    				if(first) {
    					first = false;
    					tmp1 =  "TOPIK.TARGETOBJECTNICKNAME like ? ";
    					tmp2 =  "SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    					//sqlStmt = sqlStmt+"SUBTOPIK.OBJNICKNAMERECEIVER like ? or TOPIK.TARGETOBJECTNICKNAME like ? ";
    				}
    				else {
    					tmp1 =  tmp1+"or TOPIK.TARGETOBJECTNICKNAME like ? ";
    					tmp2 =  tmp2+"or SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    					//sqlStmt = sqlStmt+"or SUBTOPIK.OBJNICKNAMERECEIVER like ? or TOPIK.TARGETOBJECTNICKNAME like ? ";
    				}
			
    			}
    			tmp1 = "(("+tmp1+") and TOPIK.TARGETNPMHS is null) ";
    			tmp2 = "(("+tmp2+") and SUBTOPIK.NPMHSRECEIVER is null) ";
    			sqlStmt=sqlStmt+" or "+tmp1+" or "+tmp2+" or TOPIK.NPMHSCREATOR like ? or NPMHSSENDER like ? order by SUBTOPIK.UPDTMTOPIK desc,TOPIK.UPDTMTOPIK desc limit ?,?";
    			stmt = con.prepareStatement(sqlStmt);
    			//System.out.println("sqlStmt2="+sqlStmt);
    			j = 1;
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			stmt.setString(j++, this.operatorNpm);
    			stmt.setString(j++, this.operatorNpm);
    			while(li1.hasNext()) {
    				String nicname = (String)li1.next();
    				stmt.setString(j++, nicname);
    			}
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			while(li1.hasNext()) {
    				String nicname = (String)li1.next();
    				stmt.setString(j++, "%"+nicname+"%");
    			}
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
//    			//System.out.println(j+"%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			stmt.setInt(j++, Integer.valueOf(startAt).intValue());
        		stmt.setInt(j++, Integer.valueOf(quantity).intValue());
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String topik_idTopik = ""+rs.getLong("TOPIK.IDTOPIK");
    				String topik_conten = ""+rs.getString("TOPIK.TOPIKCONTENT");
    				String topik_npmhsCreator = ""+rs.getString("TOPIK.NPMHSCREATOR");
    				String topik_nmmhsCreator = ""+rs.getString("TOPIK.NMMHSCREATOR");
    				String topik_creatorObjId = ""+rs.getString("TOPIK.CRETOROBJECTID");
    				String topik_creatorObjNickname = ""+rs.getString("TOPIK.CREATOROBJNICKNAME");
    				String topik_targetKdpst = ""+rs.getString("TOPIK.TARGETKDPST");
    				String topik_targetNpmhs = ""+rs.getString("TOPIK.TARGETNPMHS");
    				String topik_targetSmawl = ""+rs.getString("TOPIK.TARGETSMAWL");
    				String topik_targetObjId = ""+rs.getString("TOPIK.TARGETOBJEKID");
    				String topik_targetObjNickname = ""+rs.getString("TOPIK.TARGETOBJECTNICKNAME");
    				String topik_targetGroupId = ""+rs.getLong("TOPIK.TARGETGROUPID");
    				String topik_groupPwd = ""+rs.getString("TOPIK.GROUPPWD");
    				String topik_shownToGroupOnly = ""+rs.getBoolean("TOPIK.SHOWNTOGROUPONLY");
    				String topik_deletedByCreator = ""+rs.getBoolean("TOPIK.DELETEDBYCREATOR");
    				String topik_hidenAtCreator = ""+rs.getBoolean("TOPIK.HIDENATCREATOR");
    				String topik_pinedAtCreator = ""+rs.getBoolean("TOPIK.PINEDATCREATOR");
    				String topik_markedAsReadAtCreator = ""+rs.getBoolean("TOPIK.MARKEDASREADATCREATOR");
    				String topik_deletedAtTarget = ""+rs.getString("TOPIK.DELETEDATTARGET");
    				String topik_hidenAtTarget = ""+rs.getString("TOPIK.HIDENATTARGET");
    				String topik_pinedAtTarget = ""+rs.getString("TOPIK.PINEDATTARGET");
    				String topik_markedAsReasAsTarget = ""+rs.getString("TOPIK.MARKEDASREADATTARGET");
    				String topik_creatorAsAnonymous = ""+rs.getBoolean("TOPIK.CREATORASANONYMOUS");
    				String topik_creatorIsPetugas = ""+rs.getBoolean("TOPIK.CRETORISPETUGAS");
    				String topik_updtm = ""+rs.getTimestamp("TOPIK.UPDTMTOPIK");
				
				
    				String sub_id = ""+rs.getLong("SUBTOPIK.IDSUBTOPIK");
    				String sub_idTopik = ""+rs.getLong("SUBTOPIK.IDTOPIK");
    				String sub_comment = ""+rs.getString("SUBTOPIK.COMMENT");
    				String sub_npmhsSender = ""+rs.getString("SUBTOPIK.NPMHSSENDER");
    				String sub_nmmhsSender = ""+rs.getString("SUBTOPIK.NMMHSSENDER");
    				String sub_anonymousReply = ""+rs.getBoolean("SUBTOPIK.ANONYNOUSREPLY");
    				String sub_shownToCreatorObly = ""+rs.getBoolean("SUBTOPIK.SHOWNTOCREATERONLY");
    				String sub_commenterIsPetugas = ""+rs.getBoolean("SUBTOPIK.COMMENTERISPETUGAS");
    				String sub_markedAsReadAtCreator = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATRECEIVER");
    				String sub_markedAsReadAtSender = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATSENDER");
    				String sub_objNicknameSender = ""+rs.getString("SUBTOPIK.OBJNICKNAMESENDER");
    				String sub_npmhsReceiver =  ""+rs.getString("SUBTOPIK.NPMHSRECEIVER");
    				String sub_nmmhsReceiver = ""+rs.getString("SUBTOPIK.NMMHSRECEIVER");
    				String sub_objNicknameReceiver = ""+rs.getString("SUBTOPIK.OBJNICKNAMERECEIVER");
    				String sub_updtm = ""+rs.getTimestamp("SUBTOPIK.UPDTMTOPIK");
				 
				/*
				 * note: semua topik id yang udag ada komennya maka TOPIK.MARKEDASREADATTARGET contains objnameUsr
				 */
    				String tmp = topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+sub_id+"||"+sub_idTopik+"||"+sub_comment+"||"+sub_npmhsSender+"||"+sub_nmmhsSender+"||"+sub_anonymousReply+"||"+sub_shownToCreatorObly+"||"+sub_commenterIsPetugas+"||"+sub_markedAsReadAtCreator+"||"+sub_markedAsReadAtSender+"||"+sub_objNicknameSender+"||"+sub_npmhsReceiver+"||"+sub_nmmhsReceiver+"||"+sub_objNicknameReceiver+"||"+sub_updtm;
    				tmp = tmp.replace("||||", "||null||");
					li.add(tmp);
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
    	return v;
    }	              
    
    public Vector getSubtopik(long topikId) {
    	/*
    	 * fungsi ini ditujukan untuk get topik yang ditujukan ke baa atau dari baa ke kita
    	 */
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	boolean locked  = false;
    	String thsmsPmb = Checker.getThsmsPmb();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		

    		stmt = con.prepareStatement("select * from SUBTOPIK where IDTOPIK=? order by UPDTMTOPIK");
    		stmt.setLong(1, topikId);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			long idSubTopik = rs.getLong("IDSUBTOPIK");
    			String content = rs.getString("COMMENT");
    			String npmhsSender = ""+rs.getString("NPMHSSENDER");
    			String nmmhsSender = ""+rs.getString("NMMHSSENDER");
    			boolean anonymous = rs.getBoolean("ANONYNOUSREPLY");
    			boolean shownToCreatorOnly = rs.getBoolean("SHOWNTOCREATERONLY");
    			boolean commentorIsPetugas = rs.getBoolean("COMMENTERISPETUGAS");
    			String updtm = ""+rs.getTimestamp("UPDTMTOPIK");	
    			String tmp =topikId+"||"+idSubTopik+"||"+content+"||"+npmhsSender+"||"+nmmhsSender+"||"+anonymous+"||"+shownToCreatorOnly+"||"+commentorIsPetugas+"||"+updtm;
    			li.add(tmp);
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
    //ngga dipake lagi
    public Vector getUnreadMsgInboxDepricated(String objNickNameUsr,Vector vScopeObjNicknameOwnInbox) {
    	/*
    	 * 
    	 */
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	boolean locked  = false;
    	String sqlStmt=null;
    	int counter = 0;
    	//String thsmsPmb = Checker.getThsmsPmb();
    	//boolean ada = false;
    	try {
    		if(vScopeObjNicknameOwnInbox!=null || vScopeObjNicknameOwnInbox.size()>0) {
    			counter = vScopeObjNicknameOwnInbox.size();
    			//sqlStmt = "select * from SUBTOPIK inner join TOPIK on SUBTOPIK.IDTOPIK=TOPIK.IDTOPIK where (SUBTOPIK.NPMHSRECEIVER like ? or ";
				/*
				 * 1. cek  msg at topik table only - blm ada stu komen pun
				 */
    			sqlStmt = "select * from TOPIK where (TARGETNPMHS like ? or ";
    			boolean first = true;
				for(int i=0;i<counter;i++) {
					if(first) {
						first = false;
						sqlStmt = sqlStmt+"TARGETOBJECTNICKNAME like ? ";
					}
					else {
						sqlStmt = sqlStmt+"or TARGETOBJECTNICKNAME like ? ";
					}
				
				}
				sqlStmt=sqlStmt+") and (MARKEDASREADATTARGET is NULL or MARKEDASREADATTARGET not like ?) order by UPDTMTOPIK";
				Context initContext  = new InitialContext();
				Context envContext  = (Context)initContext.lookup("java:/comp/env");
				ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
				con = ds.getConnection();
    		//	stmt = con.prepareStatement("select * from SUBTOPIK inner join TOPIK on SUBTOPIK.IDTOPIK=TOPIK.IDTOPIK where (SUBTOPIK.NPMHSRECEIVER like ? or SUBTOPIK.OBJNICKNAMERECEIVER like ?) and SUBTOPIK.MARKEDASREADATRECEIVER=? order by SUBTOPIK.UPDTMTOPIK");
				stmt = con.prepareStatement(sqlStmt);
				int j = 1;
				//System.out.println("sqlStmt1="+sqlStmt);
				ListIterator li1 = vScopeObjNicknameOwnInbox.listIterator();
				stmt.setString(j++, "%"+this.operatorNpm+"%");
				while(li1.hasNext()) {
					String nicname = (String)li1.next();
				//System.out.println("nicname="+nicname);
					stmt.setString(j++, nicname);
				}
				stmt.setString(j++, objNickNameUsr);
				rs = stmt.executeQuery();
				while(rs.next()) {
					String topik_idTopik = ""+rs.getLong("TOPIK.IDTOPIK");
					String topik_conten = ""+rs.getString("TOPIK.TOPIKCONTENT");
					String topik_npmhsCreator = ""+rs.getString("TOPIK.NPMHSCREATOR");
					String topik_nmmhsCreator = ""+rs.getString("TOPIK.NMMHSCREATOR");
					String topik_creatorObjId = ""+rs.getString("TOPIK.CRETOROBJECTID");
					String topik_creatorObjNickname = ""+rs.getString("TOPIK.CREATOROBJNICKNAME");
					String topik_targetKdpst = ""+rs.getString("TOPIK.TARGETKDPST");
					String topik_targetNpmhs = ""+rs.getString("TOPIK.TARGETNPMHS");
					String topik_targetSmawl = ""+rs.getString("TOPIK.TARGETSMAWL");
					String topik_targetObjId = ""+rs.getString("TOPIK.TARGETOBJEKID");
					String topik_targetObjNickname = ""+rs.getString("TOPIK.TARGETOBJECTNICKNAME");
					String topik_targetGroupId = ""+rs.getLong("TOPIK.TARGETGROUPID");
					String topik_groupPwd = ""+rs.getString("TOPIK.GROUPPWD");
					String topik_shownToGroupOnly = ""+rs.getBoolean("TOPIK.SHOWNTOGROUPONLY");
					String topik_deletedByCreator = ""+rs.getBoolean("TOPIK.DELETEDBYCREATOR");
					String topik_hidenAtCreator = ""+rs.getBoolean("TOPIK.HIDENATCREATOR");
					String topik_pinedAtCreator = ""+rs.getBoolean("TOPIK.PINEDATCREATOR");
					String topik_markedAsReadAtCreator = ""+rs.getBoolean("TOPIK.MARKEDASREADATCREATOR");
    				String topik_deletedAtTarget = ""+rs.getString("TOPIK.DELETEDATTARGET");
    				String topik_hidenAtTarget = ""+rs.getString("TOPIK.HIDENATTARGET");
    				String topik_pinedAtTarget = ""+rs.getString("TOPIK.PINEDATTARGET");
    				String topik_markedAsReasAsTarget = ""+rs.getString("TOPIK.MARKEDASREADATTARGET");
    				String topik_creatorAsAnonymous = ""+rs.getBoolean("TOPIK.CREATORASANONYMOUS");
    				String topik_creatorIsPetugas = ""+rs.getBoolean("TOPIK.CRETORISPETUGAS");
    				String topik_updtm = ""+rs.getTimestamp("TOPIK.UPDTMTOPIK");
    				
    				/*
    				String sub_id = ""+rs.getLong("SUBTOPIK.IDSUBTOPIK");
    				String sub_idTopik = ""+rs.getLong("SUBTOPIK.IDTOPIK");
    				String sub_comment = ""+rs.getString("SUBTOPIK.COMMENT");
    				String sub_npmhsSender = ""+rs.getString("SUBTOPIK.NPMHSSENDER");
    				String sub_nmmhsSender = ""+rs.getString("SUBTOPIK.NMMHSSENDER");
    				String sub_anonymousReply = ""+rs.getBoolean("SUBTOPIK.ANONYNOUSREPLY");
    				String sub_shownToCreatorObly = ""+rs.getBoolean("SUBTOPIK.SHOWNTOCREATERONLY");
    				String sub_commenterIsPetugas = ""+rs.getBoolean("SUBTOPIK.COMMENTERISPETUGAS");
    				String sub_markedAsReadAtCreator = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATRECEIVER");
    				String sub_markedAsReadAtSender = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATSENDER");
    				String sub_objNicknameSender = ""+rs.getString("SUBTOPIK.OBJNICKNAMESENDER");
    				String sub_npmhsReceiver =  ""+rs.getString("SUBTOPIK.NPMHSRECEIVER");
    				String sub_nmmhsReceiver = ""+rs.getString("SUBTOPIK.NMMHSRECEIVER");
    				String sub_objNicknameReceiver = ""+rs.getString("SUBTOPIK.OBJNICKNAMERECEIVER");
    				String sub_updtm = ""+rs.getTimestamp("SUBTOPIK.UPDTMTOPIK");
    				 */
    				
    				//yang include hanya blm topik_markedAsReasAsTarget artinya baru ada topik doang blm ada komen satupun
    				//if(topik_markedAsReasAsTarget.equalsIgnoreCase("null") || !topik_markedAsReasAsTarget.contains(objNickNameUsr)) {
    				String tmp = topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+null+"||"+null+"||"+null+"||"+null+"||"+null+"||"+null+"||"+null+"||"+null+"||"+null+"||"+null+"||"+null+"||"+null+"||"+null+"||"+null+"||"+null;
    				tmp = tmp.replace("||||","||null||");
    				li.add(tmp);
    				//}
    					//li.add(topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+sub_id+"||"+sub_idTopik+"||"+sub_comment+"||"+sub_npmhsSender+"||"+sub_nmmhsSender+"||"+sub_anonymousReply+"||"+sub_shownToCreatorObly+"||"+sub_commenterIsPetugas+"||"+sub_markedAsReadAtCreator+"||"+sub_markedAsReadAtSender+"||"+sub_objNicknameSender+"||"+sub_npmhsReceiver+"||"+sub_nmmhsReceiver+"||"+sub_objNicknameReceiver+"||"+sub_updtm);
    			
    				//ada = true;
				}
				try {
					v = Tool.removeDuplicateFromVector(v);
				}
				catch(Exception e) {
					//System.out.println(e);
				}
				li = v.listIterator();
				while(li.hasNext()) {
					li.next();
				}
				/*
				 * 2. cek  msg at subtopik table
				 */
				
				sqlStmt = "select * from SUBTOPIK inner join TOPIK on SUBTOPIK.IDTOPIK=TOPIK.IDTOPIK where (SUBTOPIK.NPMHSRECEIVER like ? or ";
    			first = true;
				for(int i=0;i<counter;i++) {
					if(first) {
						first = false;
						sqlStmt = sqlStmt+"SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
					}
					else {
						sqlStmt = sqlStmt+"or SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
					}
				
				}
				sqlStmt=sqlStmt+") and SUBTOPIK.MARKEDASREADATRECEIVER not like ? order by SUBTOPIK.UPDTMTOPIK";
				stmt = con.prepareStatement(sqlStmt);
				//System.out.println("sqlStmt2="+sqlStmt);
				j = 1;
				li1 = vScopeObjNicknameOwnInbox.listIterator();
				stmt.setString(j++, "%"+this.operatorNpm+"%");
				while(li1.hasNext()) {
					String nicname = (String)li1.next();
				//System.out.println("nicname="+nicname);
					//System.out.println(j+"."+nicname);
					stmt.setString(j++, "%"+nicname+"%");
					
				}
				//System.out.println(j+"."+objNickNameUsr);
				stmt.setString(j++, objNickNameUsr);
				rs = stmt.executeQuery();
				//System.out.println("a2. cek  msg at subtopik table");
				while(rs.next()) {
					//System.out.println("2. cek  msg at subtopik table");
					String topik_idTopik = ""+rs.getLong("TOPIK.IDTOPIK");
					String topik_conten = ""+rs.getString("TOPIK.TOPIKCONTENT");
					String topik_npmhsCreator = ""+rs.getString("TOPIK.NPMHSCREATOR");
					String topik_nmmhsCreator = ""+rs.getString("TOPIK.NMMHSCREATOR");
					String topik_creatorObjId = ""+rs.getString("TOPIK.CRETOROBJECTID");
					String topik_creatorObjNickname = ""+rs.getString("TOPIK.CREATOROBJNICKNAME");
					String topik_targetKdpst = ""+rs.getString("TOPIK.TARGETKDPST");
					String topik_targetNpmhs = ""+rs.getString("TOPIK.TARGETNPMHS");
					String topik_targetSmawl = ""+rs.getString("TOPIK.TARGETSMAWL");
					String topik_targetObjId = ""+rs.getString("TOPIK.TARGETOBJEKID");
					String topik_targetObjNickname = ""+rs.getString("TOPIK.TARGETOBJECTNICKNAME");
					String topik_targetGroupId = ""+rs.getLong("TOPIK.TARGETGROUPID");
					String topik_groupPwd = ""+rs.getString("TOPIK.GROUPPWD");
					String topik_shownToGroupOnly = ""+rs.getBoolean("TOPIK.SHOWNTOGROUPONLY");
					String topik_deletedByCreator = ""+rs.getBoolean("TOPIK.DELETEDBYCREATOR");
					String topik_hidenAtCreator = ""+rs.getBoolean("TOPIK.HIDENATCREATOR");
					String topik_pinedAtCreator = ""+rs.getBoolean("TOPIK.PINEDATCREATOR");
					String topik_markedAsReadAtCreator = ""+rs.getBoolean("TOPIK.MARKEDASREADATCREATOR");
    				String topik_deletedAtTarget = ""+rs.getString("TOPIK.DELETEDATTARGET");
    				String topik_hidenAtTarget = ""+rs.getString("TOPIK.HIDENATTARGET");
    				String topik_pinedAtTarget = ""+rs.getString("TOPIK.PINEDATTARGET");
    				String topik_markedAsReasAsTarget = ""+rs.getString("TOPIK.MARKEDASREADATTARGET");
    				String topik_creatorAsAnonymous = ""+rs.getBoolean("TOPIK.CREATORASANONYMOUS");
    				String topik_creatorIsPetugas = ""+rs.getBoolean("TOPIK.CRETORISPETUGAS");
    				String topik_updtm = ""+rs.getTimestamp("TOPIK.UPDTMTOPIK");
    				
    				
    				String sub_id = ""+rs.getLong("SUBTOPIK.IDSUBTOPIK");
    				String sub_idTopik = ""+rs.getLong("SUBTOPIK.IDTOPIK");
    				String sub_comment = ""+rs.getString("SUBTOPIK.COMMENT");
    				String sub_npmhsSender = ""+rs.getString("SUBTOPIK.NPMHSSENDER");
    				String sub_nmmhsSender = ""+rs.getString("SUBTOPIK.NMMHSSENDER");
    				String sub_anonymousReply = ""+rs.getBoolean("SUBTOPIK.ANONYNOUSREPLY");
    				String sub_shownToCreatorObly = ""+rs.getBoolean("SUBTOPIK.SHOWNTOCREATERONLY");
    				String sub_commenterIsPetugas = ""+rs.getBoolean("SUBTOPIK.COMMENTERISPETUGAS");
    				String sub_markedAsReadAtCreator = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATRECEIVER");
    				String sub_markedAsReadAtSender = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATSENDER");
    				String sub_objNicknameSender = ""+rs.getString("SUBTOPIK.OBJNICKNAMESENDER");
    				String sub_npmhsReceiver =  ""+rs.getString("SUBTOPIK.NPMHSRECEIVER");
    				String sub_nmmhsReceiver = ""+rs.getString("SUBTOPIK.NMMHSRECEIVER");
    				String sub_objNicknameReceiver = ""+rs.getString("SUBTOPIK.OBJNICKNAMERECEIVER");
    				String sub_updtm = ""+rs.getTimestamp("SUBTOPIK.UPDTMTOPIK");
    				 
    				/*
    				 * note: semua topik id yang udag ada komennya maka TOPIK.MARKEDASREADATTARGET contains objnameUsr
    				 */
    	
    				//if(!topik_markedAsReasAsTarget.equalsIgnoreCase("null") && topik_markedAsReasAsTarget.contains(objNickNameUsr)) {
    					String tmp = topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+sub_id+"||"+sub_idTopik+"||"+sub_comment+"||"+sub_npmhsSender+"||"+sub_nmmhsSender+"||"+sub_anonymousReply+"||"+sub_shownToCreatorObly+"||"+sub_commenterIsPetugas+"||"+sub_markedAsReadAtCreator+"||"+sub_markedAsReadAtSender+"||"+sub_objNicknameSender+"||"+sub_npmhsReceiver+"||"+sub_nmmhsReceiver+"||"+sub_objNicknameReceiver+"||"+sub_updtm;
    					tmp = tmp.replace("||||", "||null||");
    					li.add(tmp);
    					//System.out.println(topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+sub_id+"||"+sub_idTopik+"||"+sub_comment+"||"+sub_npmhsSender+"||"+sub_nmmhsSender+"||"+sub_anonymousReply+"||"+sub_shownToCreatorObly+"||"+sub_commenterIsPetugas+"||"+sub_markedAsReadAtCreator+"||"+sub_markedAsReadAtSender+"||"+sub_objNicknameSender+"||"+sub_npmhsReceiver+"||"+sub_nmmhsReceiver+"||"+sub_objNicknameReceiver+"||"+sub_updtm);
    				//}
				}	
				try {
					v = Tool.removeDuplicateFromVector(v);
				}
				catch(Exception e) {
					//System.out.println(e);
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
    	return v;
    }	
    
    
    //depricated diganti dgn yg pake index
    public Vector getUnreadMsgInbox(String objNickNameUsr,Vector vScopeObjNicknameOwnInbox) {
    	/*
    	 * Unread means = only search msg sbg receiver only!!
    	 */
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	boolean locked  = false;
    	String sqlStmt=null;
    	int counter = 0;
    	//String thsmsPmb = Checker.getThsmsPmb();
    	//boolean ada = false;
    	try {
    		if(vScopeObjNicknameOwnInbox!=null || vScopeObjNicknameOwnInbox.size()>0) {
    			counter = vScopeObjNicknameOwnInbox.size();
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			
    			counter = vScopeObjNicknameOwnInbox.size();
    			sqlStmt = "";
    			boolean first = true;
				int j = 1;
				ListIterator li1 = vScopeObjNicknameOwnInbox.listIterator();    
				sqlStmt = "select * from TOPIK left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where (TOPIK.TARGETNPMHS like ? and (TOPIK.MARKEDASREADATTARGET not like ? or TOPIK.MARKEDASREADATTARGET is null)) or (SUBTOPIK.NPMHSRECEIVER like ? and SUBTOPIK.MARKEDASREADATRECEIVER=false) ";//NPM ONLY PART
    			first = true;
    			String tmp1 = "",tmp3="";
    			String tmp2 = "";
    			String tmp1a = "";
    			for(int i=0;i<counter;i++) {		
    				if(first) {
    					first = false;
    					tmp1 =  "TOPIK.TARGETOBJECTNICKNAME like ? ";
    					//tmp1 =  "(TOPIK.TARGETOBJECTNICKNAME like ? and TOPIK.MARKEDASREADATTARGET not like ?) ";
    					tmp1a=  "TOPIK.MARKEDASREADATTARGET not like ? ";
    					tmp2 =  "SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    					tmp3 =  "TOPIK.CREATOROBJNICKNAME not like ? and SUBTOPIK.OBJNICKNAMESENDER not like ? ";
    				}
    				else {
    					tmp1 =  tmp1+"or TOPIK.TARGETOBJECTNICKNAME like ? ";
    					tmp1a=  tmp1a+" and TOPIK.MARKEDASREADATTARGET not like ? ";
    					tmp2 =  tmp2+"or SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    					tmp3 =  tmp3+" and TOPIK.CREATOROBJNICKNAME not like ? and SUBTOPIK.OBJNICKNAMESENDER not like ? ";
    				}
    			}
    			//tmp1&1a = msg berdasarkan obj nicknam dan blum dibaca @ topik
    			//tmp2&3 = msg berdasarkan obj nicknam dan blum dibaca @ subtopik 
    			tmp1 = "(("+tmp1+") and TOPIK.TARGETNPMHS is null  and (("+tmp1a+" and TOPIK.MARKEDASREADATTARGET not like ?) or TOPIK.MARKEDASREADATTARGET is null)) ";
    			//tmp1 = "(("+tmp1+") and ("+tmp1a+") and TOPIK.TARGETNPMHS is null and  TOPIK.MARKEDASREADATTARGET not like ?) ";
    			tmp2 = "(("+tmp2+") and SUBTOPIK.NPMHSRECEIVER is null and SUBTOPIK.MARKEDASREADATRECEIVER=false) ";
    			sqlStmt=sqlStmt+" or "+tmp1+" or "+tmp2+" and TOPIK.NPMHSCREATOR not like ? and SUBTOPIK.NPMHSSENDER NOT like ? and "+tmp3+" order by SUBTOPIK.UPDTMTOPIK desc,TOPIK.UPDTMTOPIK desc;";
    			stmt = con.prepareStatement(sqlStmt);
    			//System.out.println("sqlStmt getUnreadMsgInboox="+sqlStmt);
    			j = 1;
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			String nicname="EmptyNickNmaeSbgDefaultValue";
    			while(li1.hasNext()) { // isi tmp1
    				nicname = (String)li1.next();
    				stmt.setString(j++, nicname);
    				//System.out.println(j+"%"+nicname+"%");
    			}
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			while(li1.hasNext()) { //isi tmp1a
    				nicname = (String)li1.next();
    				stmt.setString(j++, nicname);
    			}
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			nicname = null;
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			while(li1.hasNext()) {//isi tmp2 
    				nicname = (String)li1.next();
    				stmt.setString(j++, nicname);
    			}
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			while(li1.hasNext()) {//isi tmp3 
    				nicname = (String)li1.next();
    				stmt.setString(j++, nicname);
    				stmt.setString(j++, nicname);
    			}
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String topik_idTopik = ""+rs.getLong("TOPIK.IDTOPIK");
    				String topik_conten = ""+rs.getString("TOPIK.TOPIKCONTENT");
    				String topik_npmhsCreator = ""+rs.getString("TOPIK.NPMHSCREATOR");
    				String topik_nmmhsCreator = ""+rs.getString("TOPIK.NMMHSCREATOR");
    				String topik_creatorObjId = ""+rs.getString("TOPIK.CRETOROBJECTID");
    				String topik_creatorObjNickname = ""+rs.getString("TOPIK.CREATOROBJNICKNAME");
    				String topik_targetKdpst = ""+rs.getString("TOPIK.TARGETKDPST");
    				String topik_targetNpmhs = ""+rs.getString("TOPIK.TARGETNPMHS");
    				String topik_targetSmawl = ""+rs.getString("TOPIK.TARGETSMAWL");
    				String topik_targetObjId = ""+rs.getString("TOPIK.TARGETOBJEKID");
    				String topik_targetObjNickname = ""+rs.getString("TOPIK.TARGETOBJECTNICKNAME");
    				String topik_targetGroupId = ""+rs.getLong("TOPIK.TARGETGROUPID");
    				String topik_groupPwd = ""+rs.getString("TOPIK.GROUPPWD");
    				String topik_shownToGroupOnly = ""+rs.getBoolean("TOPIK.SHOWNTOGROUPONLY");
    				String topik_deletedByCreator = ""+rs.getBoolean("TOPIK.DELETEDBYCREATOR");
    				String topik_hidenAtCreator = ""+rs.getBoolean("TOPIK.HIDENATCREATOR");
    				String topik_pinedAtCreator = ""+rs.getBoolean("TOPIK.PINEDATCREATOR");
    				String topik_markedAsReadAtCreator = ""+rs.getBoolean("TOPIK.MARKEDASREADATCREATOR");
    				String topik_deletedAtTarget = ""+rs.getString("TOPIK.DELETEDATTARGET");
    				String topik_hidenAtTarget = ""+rs.getString("TOPIK.HIDENATTARGET");
    				String topik_pinedAtTarget = ""+rs.getString("TOPIK.PINEDATTARGET");
    				String topik_markedAsReasAsTarget = ""+rs.getString("TOPIK.MARKEDASREADATTARGET");
    				String topik_creatorAsAnonymous = ""+rs.getBoolean("TOPIK.CREATORASANONYMOUS");
    				String topik_creatorIsPetugas = ""+rs.getBoolean("TOPIK.CRETORISPETUGAS");
    				String topik_updtm = ""+rs.getTimestamp("TOPIK.UPDTMTOPIK");
				
				
    				String sub_id = ""+rs.getLong("SUBTOPIK.IDSUBTOPIK");
    				String sub_idTopik = ""+rs.getLong("SUBTOPIK.IDTOPIK");
    				String sub_comment = ""+rs.getString("SUBTOPIK.COMMENT");
    				String sub_npmhsSender = ""+rs.getString("SUBTOPIK.NPMHSSENDER");
    				String sub_nmmhsSender = ""+rs.getString("SUBTOPIK.NMMHSSENDER");
    				String sub_anonymousReply = ""+rs.getBoolean("SUBTOPIK.ANONYNOUSREPLY");
    				String sub_shownToCreatorObly = ""+rs.getBoolean("SUBTOPIK.SHOWNTOCREATERONLY");
    				String sub_commenterIsPetugas = ""+rs.getBoolean("SUBTOPIK.COMMENTERISPETUGAS");
    				String sub_markedAsReadAtCreator = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATRECEIVER");
    				String sub_markedAsReadAtSender = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATSENDER");
    				String sub_objNicknameSender = ""+rs.getString("SUBTOPIK.OBJNICKNAMESENDER");
    				String sub_npmhsReceiver =  ""+rs.getString("SUBTOPIK.NPMHSRECEIVER");
    				String sub_nmmhsReceiver = ""+rs.getString("SUBTOPIK.NMMHSRECEIVER");
    				String sub_objNicknameReceiver = ""+rs.getString("SUBTOPIK.OBJNICKNAMERECEIVER");
    				String sub_updtm = ""+rs.getTimestamp("SUBTOPIK.UPDTMTOPIK");
				 
				/*
				 * note: semua topik id yang udag ada komennya maka TOPIK.MARKEDASREADATTARGET contains objnameUsr
				 */
    				String tmp = topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+sub_id+"||"+sub_idTopik+"||"+sub_comment+"||"+sub_npmhsSender+"||"+sub_nmmhsSender+"||"+sub_anonymousReply+"||"+sub_shownToCreatorObly+"||"+sub_commenterIsPetugas+"||"+sub_markedAsReadAtCreator+"||"+sub_markedAsReadAtSender+"||"+sub_objNicknameSender+"||"+sub_npmhsReceiver+"||"+sub_nmmhsReceiver+"||"+sub_objNicknameReceiver+"||"+sub_updtm;
    				tmp = tmp.replace("||||", "||null||");
					li.add(tmp);
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
    	return v;
    }	          

    /*
     * depricated yg dipake v2
     */
    public Vector getUnreadMsgInboxWithinRange(String objNickNameUsr,Vector vScopeObjNicknameOwnInbox,int start,int range_qtt) {
    	/*
    	 * Unread means = only search msg sbg receiver only!!
    	 */
    	
    	range_qtt++;//ditambahin krn untuk ngecek apa masih ada msg di atar range_qtt
    	//System.out.println("limit "+start+","+range_qtt);
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	boolean locked  = false;
    	String sqlStmt=null;
    	int counter = 0;
    	//String thsmsPmb = Checker.getThsmsPmb();
    	//boolean ada = false;
    	try {
    		if(vScopeObjNicknameOwnInbox!=null || vScopeObjNicknameOwnInbox.size()>0) {
    			counter = vScopeObjNicknameOwnInbox.size();
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			
    			counter = vScopeObjNicknameOwnInbox.size();
    			sqlStmt = "";
    			boolean first = true;
				int j = 1;
				ListIterator li1 = vScopeObjNicknameOwnInbox.listIterator();    
				sqlStmt = "select * from TOPIK left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where (TOPIK.TARGETNPMHS like ? and (TOPIK.MARKEDASREADATTARGET not like ? or TOPIK.MARKEDASREADATTARGET is null)) or (SUBTOPIK.NPMHSRECEIVER like ? and SUBTOPIK.MARKEDASREADATRECEIVER=false) ";//NPM ONLY PART
    			first = true;
    			String tmp1 = "",tmp3="";
    			String tmp2 = "";
    			String tmp1a = "";
    			for(int i=0;i<counter;i++) {		
    				if(first) {
    					first = false;
    					tmp1 =  "TOPIK.TARGETOBJECTNICKNAME like ? ";
    					//tmp1 =  "(TOPIK.TARGETOBJECTNICKNAME like ? and TOPIK.MARKEDASREADATTARGET not like ?) ";
    					tmp1a=  "TOPIK.MARKEDASREADATTARGET not like ? ";
    					tmp2 =  "SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    					tmp3 =  "TOPIK.CREATOROBJNICKNAME not like ? and SUBTOPIK.OBJNICKNAMESENDER not like ? ";
    				}
    				else {
    					tmp1 =  tmp1+"or TOPIK.TARGETOBJECTNICKNAME like ? ";
    					tmp1a=  tmp1a+" and TOPIK.MARKEDASREADATTARGET not like ? ";
    					tmp2 =  tmp2+"or SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    					tmp3 =  tmp3+" and TOPIK.CREATOROBJNICKNAME not like ? and SUBTOPIK.OBJNICKNAMESENDER not like ? ";
    				}
    			}
    			//tmp1&1a = msg berdasarkan obj nicknam dan blum dibaca @ topik
    			//tmp2&3 = msg berdasarkan obj nicknam dan blum dibaca @ subtopik
    			/*
    			 * tmp1 updated 14juni2014 (1) tmp1 nya doang 1 brs dibawah ini yg diupdate
    			 */
    			//tmp1 = "(("+tmp1+") and TOPIK.TARGETNPMHS is null  and (("+tmp1a+" and TOPIK.MARKEDASREADATTARGET not like ?) or TOPIK.MARKEDASREADATTARGET is null)) ";
    			tmp1 = "(("+tmp1+") and TOPIK.TARGETNPMHS is null  and (("+tmp1a+" and TOPIK.MARKEDASREADATTARGET not like ?) or TOPIK.MARKEDASREADATTARGET is null  and TOPIK.NPMHSCREATOR not like ? )) ";
    			//tmp1 = "(("+tmp1+") and ("+tmp1a+") and TOPIK.TARGETNPMHS is null and  TOPIK.MARKEDASREADATTARGET not like ?) ";
    			tmp2 = "(("+tmp2+") and SUBTOPIK.NPMHSRECEIVER is null and SUBTOPIK.MARKEDASREADATRECEIVER=false) ";
    			sqlStmt=sqlStmt+" or "+tmp1+" or "+tmp2+" and TOPIK.NPMHSCREATOR not like ? and SUBTOPIK.NPMHSSENDER NOT like ? and "+tmp3+" order by SUBTOPIK.UPDTMTOPIK desc,TOPIK.UPDTMTOPIK desc limit ?,?;";
    			stmt = con.prepareStatement(sqlStmt);
    			//System.out.println("sqlStmtgetUnreadMsgInbox="+sqlStmt);
    			j = 1;
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			String nicname="EmptyNickNmaeSbgDefaultValue";
    			while(li1.hasNext()) { // isi tmp1
    				nicname = (String)li1.next();
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, nicname);
    				//System.out.println(j+"%"+nicname+"%");
    			}
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			while(li1.hasNext()) { //isi tmp1a
    				nicname = (String)li1.next();
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, nicname);
    			}
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			/*
    			 * tmp1 updated 14juni2014 (2)
    			 */
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			/*
    			 * end tmp1 updated 14juni2014 (2)
    			 */
    			nicname = null;
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			while(li1.hasNext()) {//isi tmp2 
    				nicname = (String)li1.next();
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, "%"+nicname+"%");
    			}
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			while(li1.hasNext()) {//isi tmp3 
    				nicname = (String)li1.next();
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, nicname);
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, nicname);
    			}
    			stmt.setInt(j++, start);
    			stmt.setInt(j++, range_qtt);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String topik_idTopik = ""+rs.getLong("TOPIK.IDTOPIK");
    				String topik_conten = ""+rs.getString("TOPIK.TOPIKCONTENT");
    				String topik_npmhsCreator = ""+rs.getString("TOPIK.NPMHSCREATOR");
    				String topik_nmmhsCreator = ""+rs.getString("TOPIK.NMMHSCREATOR");
    				String topik_creatorObjId = ""+rs.getString("TOPIK.CRETOROBJECTID");
    				String topik_creatorObjNickname = ""+rs.getString("TOPIK.CREATOROBJNICKNAME");
    				String topik_targetKdpst = ""+rs.getString("TOPIK.TARGETKDPST");
    				String topik_targetNpmhs = ""+rs.getString("TOPIK.TARGETNPMHS");
    				String topik_targetSmawl = ""+rs.getString("TOPIK.TARGETSMAWL");
    				String topik_targetObjId = ""+rs.getString("TOPIK.TARGETOBJEKID");
    				String topik_targetObjNickname = ""+rs.getString("TOPIK.TARGETOBJECTNICKNAME");
    				String topik_targetGroupId = ""+rs.getLong("TOPIK.TARGETGROUPID");
    				String topik_groupPwd = ""+rs.getString("TOPIK.GROUPPWD");
    				String topik_shownToGroupOnly = ""+rs.getBoolean("TOPIK.SHOWNTOGROUPONLY");
    				String topik_deletedByCreator = ""+rs.getBoolean("TOPIK.DELETEDBYCREATOR");
    				String topik_hidenAtCreator = ""+rs.getBoolean("TOPIK.HIDENATCREATOR");
    				String topik_pinedAtCreator = ""+rs.getBoolean("TOPIK.PINEDATCREATOR");
    				String topik_markedAsReadAtCreator = ""+rs.getBoolean("TOPIK.MARKEDASREADATCREATOR");
    				String topik_deletedAtTarget = ""+rs.getString("TOPIK.DELETEDATTARGET");
    				String topik_hidenAtTarget = ""+rs.getString("TOPIK.HIDENATTARGET");
    				String topik_pinedAtTarget = ""+rs.getString("TOPIK.PINEDATTARGET");
    				String topik_markedAsReasAsTarget = ""+rs.getString("TOPIK.MARKEDASREADATTARGET");
    				String topik_creatorAsAnonymous = ""+rs.getBoolean("TOPIK.CREATORASANONYMOUS");
    				String topik_creatorIsPetugas = ""+rs.getBoolean("TOPIK.CRETORISPETUGAS");
    				String topik_updtm = ""+rs.getTimestamp("TOPIK.UPDTMTOPIK");
				
				
    				String sub_id = ""+rs.getLong("SUBTOPIK.IDSUBTOPIK");
    				String sub_idTopik = ""+rs.getLong("SUBTOPIK.IDTOPIK");
    				String sub_comment = ""+rs.getString("SUBTOPIK.COMMENT");
    				String sub_npmhsSender = ""+rs.getString("SUBTOPIK.NPMHSSENDER");
    				String sub_nmmhsSender = ""+rs.getString("SUBTOPIK.NMMHSSENDER");
    				String sub_anonymousReply = ""+rs.getBoolean("SUBTOPIK.ANONYNOUSREPLY");
    				String sub_shownToCreatorObly = ""+rs.getBoolean("SUBTOPIK.SHOWNTOCREATERONLY");
    				String sub_commenterIsPetugas = ""+rs.getBoolean("SUBTOPIK.COMMENTERISPETUGAS");
    				String sub_markedAsReadAtCreator = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATRECEIVER");
    				String sub_markedAsReadAtSender = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATSENDER");
    				String sub_objNicknameSender = ""+rs.getString("SUBTOPIK.OBJNICKNAMESENDER");
    				String sub_npmhsReceiver =  ""+rs.getString("SUBTOPIK.NPMHSRECEIVER");
    				String sub_nmmhsReceiver = ""+rs.getString("SUBTOPIK.NMMHSRECEIVER");
    				String sub_objNicknameReceiver = ""+rs.getString("SUBTOPIK.OBJNICKNAMERECEIVER");
    				String sub_updtm = ""+rs.getTimestamp("SUBTOPIK.UPDTMTOPIK");
				 
				/*
				 * note: semua topik id yang udag ada komennya maka TOPIK.MARKEDASREADATTARGET contains objnameUsr
				 */
    				String tmp = topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+sub_id+"||"+sub_idTopik+"||"+sub_comment+"||"+sub_npmhsSender+"||"+sub_nmmhsSender+"||"+sub_anonymousReply+"||"+sub_shownToCreatorObly+"||"+sub_commenterIsPetugas+"||"+sub_markedAsReadAtCreator+"||"+sub_markedAsReadAtSender+"||"+sub_objNicknameSender+"||"+sub_npmhsReceiver+"||"+sub_nmmhsReceiver+"||"+sub_objNicknameReceiver+"||"+sub_updtm;
    				tmp = tmp.replace("||||", "||null||");
					li.add(tmp);
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
    	return v;
    }
    
    
    public Vector getUnreadMsgInboxWithinRange_v2(String objNickNameUsr,Vector vScopeObjNicknameOwnInbox,int start,int range_qtt) {
    	/*
    	 * Unread means = only search msg sbg receiver only!!
    	 */
    	
    	range_qtt++;//ditambahin krn untuk ngecek apa masih ada msg di atar range_qtt
    	//System.out.println("limit "+start+","+range_qtt);
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	boolean locked  = false;
    	String sqlStmt=null;
    	int counter = 0;
    	//String thsmsPmb = Checker.getThsmsPmb();
    	//boolean ada = false;
    	try {
    		if(vScopeObjNicknameOwnInbox!=null || vScopeObjNicknameOwnInbox.size()>0) {
    			counter = vScopeObjNicknameOwnInbox.size();
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			
    			counter = vScopeObjNicknameOwnInbox.size();
    			sqlStmt = "";
    			boolean first = true;
				int j = 1;
				ListIterator li1 = vScopeObjNicknameOwnInbox.listIterator();    
				sqlStmt = "select * from TOPIK left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where (TOPIK.TARGETNPMHS like ? and (TOPIK.MARKEDASREADATTARGET not like ? or TOPIK.MARKEDASREADATTARGET is null)) or (SUBTOPIK.NPMHSRECEIVER like ? and SUBTOPIK.MARKEDASREADATRECEIVER=false) ";//NPM ONLY PART
    			first = true;
    			String tmp1 = "",tmp3="";
    			String tmp2 = "";
    			String tmp1a = "";
    			for(int i=0;i<counter;i++) {		
    				if(first) {
    					first = false;
    					tmp1 =  "TOPIK.TARGETOBJECTNICKNAME like ? ";
    					//tmp1 =  "(TOPIK.TARGETOBJECTNICKNAME like ? and TOPIK.MARKEDASREADATTARGET not like ?) ";
    					tmp1a=  "TOPIK.MARKEDASREADATTARGET not like ? ";
    					tmp2 =  "SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    					tmp3 =  "TOPIK.CREATOROBJNICKNAME not like ? and SUBTOPIK.OBJNICKNAMESENDER not like ? ";
    				}
    				else {
    					tmp1 =  tmp1+"or TOPIK.TARGETOBJECTNICKNAME like ? ";
    					tmp1a=  tmp1a+" and TOPIK.MARKEDASREADATTARGET not like ? ";
    					tmp2 =  tmp2+"or SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    					tmp3 =  tmp3+" and TOPIK.CREATOROBJNICKNAME not like ? and SUBTOPIK.OBJNICKNAMESENDER not like ? ";
    				}
    			}
    			//tmp1&1a = msg berdasarkan obj nicknam dan blum dibaca @ topik
    			//tmp2&3 = msg berdasarkan obj nicknam dan blum dibaca @ subtopik
    			/*
    			 * tmp1 updated 14juni2014 (1) tmp1 nya doang 1 brs dibawah ini yg diupdate
    			 */
    			//tmp1 = "(("+tmp1+") and TOPIK.TARGETNPMHS is null  and (("+tmp1a+" and TOPIK.MARKEDASREADATTARGET not like ?) or TOPIK.MARKEDASREADATTARGET is null)) ";
    			tmp1 = "(("+tmp1+") and TOPIK.TARGETNPMHS is null  and (("+tmp1a+" and TOPIK.MARKEDASREADATTARGET not like ?) or TOPIK.MARKEDASREADATTARGET is null)  and (TOPIK.NPMHSCREATOR is null or TOPIK.NPMHSCREATOR not like ? )) ";
    			//tmp1 = "(("+tmp1+") and ("+tmp1a+") and TOPIK.TARGETNPMHS is null and  TOPIK.MARKEDASREADATTARGET not like ?) ";
    			tmp2 = "(("+tmp2+") and SUBTOPIK.NPMHSRECEIVER is null and SUBTOPIK.MARKEDASREADATRECEIVER=false) ";
    			sqlStmt=sqlStmt+" or "+tmp1+" or "+tmp2+" and TOPIK.NPMHSCREATOR not like ? and SUBTOPIK.NPMHSSENDER NOT like ? and "+tmp3+" order by SUBTOPIK.UPDTMTOPIK desc,TOPIK.UPDTMTOPIK desc limit ?,?;";
    			stmt = con.prepareStatement(sqlStmt);
    			//System.out.println("sqlStmtgetUnreadMsgInbox="+sqlStmt);
    			j = 1;
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			String nicname="EmptyNickNmaeSbgDefaultValue";
    			while(li1.hasNext()) { // isi tmp1
    				nicname = (String)li1.next();
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, nicname);
    				//System.out.println(j+"%"+nicname+"%");
    			}
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			while(li1.hasNext()) { //isi tmp1a
    				nicname = (String)li1.next();
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, nicname);
    			}
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			/*
    			 * tmp1 updated 14juni2014 (2)
    			 */
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			/*
    			 * end tmp1 updated 14juni2014 (2)
    			 */
    			nicname = null;
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			while(li1.hasNext()) {//isi tmp2 
    				nicname = (String)li1.next();
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, "%"+nicname+"%");
    			}
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			while(li1.hasNext()) {//isi tmp3 
    				nicname = (String)li1.next();
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, nicname);
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, nicname);
    			}
    			stmt.setInt(j++, start);
    			stmt.setInt(j++, range_qtt);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String topik_idTopik = ""+rs.getLong("TOPIK.IDTOPIK");
    				String topik_conten = ""+rs.getString("TOPIK.TOPIKCONTENT");
    				String topik_npmhsCreator = ""+rs.getString("TOPIK.NPMHSCREATOR");
    				String topik_nmmhsCreator = ""+rs.getString("TOPIK.NMMHSCREATOR");
    				String topik_creatorObjId = ""+rs.getString("TOPIK.CRETOROBJECTID");
    				String topik_creatorObjNickname = ""+rs.getString("TOPIK.CREATOROBJNICKNAME");
    				String topik_targetKdpst = ""+rs.getString("TOPIK.TARGETKDPST");
    				String topik_targetNpmhs = ""+rs.getString("TOPIK.TARGETNPMHS");
    				String topik_targetSmawl = ""+rs.getString("TOPIK.TARGETSMAWL");
    				String topik_targetObjId = ""+rs.getString("TOPIK.TARGETOBJEKID");
    				String topik_targetObjNickname = ""+rs.getString("TOPIK.TARGETOBJECTNICKNAME");
    				String topik_targetGroupId = ""+rs.getLong("TOPIK.TARGETGROUPID");
    				String topik_groupPwd = ""+rs.getString("TOPIK.GROUPPWD");
    				String topik_shownToGroupOnly = ""+rs.getBoolean("TOPIK.SHOWNTOGROUPONLY");
    				String topik_deletedByCreator = ""+rs.getBoolean("TOPIK.DELETEDBYCREATOR");
    				String topik_hidenAtCreator = ""+rs.getBoolean("TOPIK.HIDENATCREATOR");
    				String topik_pinedAtCreator = ""+rs.getBoolean("TOPIK.PINEDATCREATOR");
    				String topik_markedAsReadAtCreator = ""+rs.getBoolean("TOPIK.MARKEDASREADATCREATOR");
    				String topik_deletedAtTarget = ""+rs.getString("TOPIK.DELETEDATTARGET");
    				String topik_hidenAtTarget = ""+rs.getString("TOPIK.HIDENATTARGET");
    				String topik_pinedAtTarget = ""+rs.getString("TOPIK.PINEDATTARGET");
    				String topik_markedAsReasAsTarget = ""+rs.getString("TOPIK.MARKEDASREADATTARGET");
    				String topik_creatorAsAnonymous = ""+rs.getBoolean("TOPIK.CREATORASANONYMOUS");
    				String topik_creatorIsPetugas = ""+rs.getBoolean("TOPIK.CRETORISPETUGAS");
    				String topik_updtm = ""+rs.getTimestamp("TOPIK.UPDTMTOPIK");
				
				
    				String sub_id = ""+rs.getLong("SUBTOPIK.IDSUBTOPIK");
    				String sub_idTopik = ""+rs.getLong("SUBTOPIK.IDTOPIK");
    				String sub_comment = ""+rs.getString("SUBTOPIK.COMMENT");
    				String sub_npmhsSender = ""+rs.getString("SUBTOPIK.NPMHSSENDER");
    				String sub_nmmhsSender = ""+rs.getString("SUBTOPIK.NMMHSSENDER");
    				String sub_anonymousReply = ""+rs.getBoolean("SUBTOPIK.ANONYNOUSREPLY");
    				String sub_shownToCreatorObly = ""+rs.getBoolean("SUBTOPIK.SHOWNTOCREATERONLY");
    				String sub_commenterIsPetugas = ""+rs.getBoolean("SUBTOPIK.COMMENTERISPETUGAS");
    				String sub_markedAsReadAtCreator = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATRECEIVER");
    				String sub_markedAsReadAtSender = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATSENDER");
    				String sub_objNicknameSender = ""+rs.getString("SUBTOPIK.OBJNICKNAMESENDER");
    				String sub_npmhsReceiver =  ""+rs.getString("SUBTOPIK.NPMHSRECEIVER");
    				String sub_nmmhsReceiver = ""+rs.getString("SUBTOPIK.NMMHSRECEIVER");
    				String sub_objNicknameReceiver = ""+rs.getString("SUBTOPIK.OBJNICKNAMERECEIVER");
    				String sub_updtm = ""+rs.getTimestamp("SUBTOPIK.UPDTMTOPIK");
				 
				/*
				 * note: semua topik id yang udag ada komennya maka TOPIK.MARKEDASREADATTARGET contains objnameUsr
				 */
    				String tmp = topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+sub_id+"||"+sub_idTopik+"||"+sub_comment+"||"+sub_npmhsSender+"||"+sub_nmmhsSender+"||"+sub_anonymousReply+"||"+sub_shownToCreatorObly+"||"+sub_commenterIsPetugas+"||"+sub_markedAsReadAtCreator+"||"+sub_markedAsReadAtSender+"||"+sub_objNicknameSender+"||"+sub_npmhsReceiver+"||"+sub_nmmhsReceiver+"||"+sub_objNicknameReceiver+"||"+sub_updtm;
    				tmp = tmp.replace("||||", "||null||");
					li.add(tmp);
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
    	return v;
    }
    
    public Vector getUnreadMsgInboxMonitor(String objNickNameUsr,Vector vScopeObjNicknameMonitorInbox) {
    	/*
    	 * Unread means = only search msg sbg receiver only!!
    	 */
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	boolean locked  = false;
    	String sqlStmt=null;
    	int counter = 0;
    	//String thsmsPmb = Checker.getThsmsPmb();
    	//boolean ada = false;
    	try {
    		if(vScopeObjNicknameMonitorInbox!=null || vScopeObjNicknameMonitorInbox.size()>0) {
    			counter = vScopeObjNicknameMonitorInbox.size();
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			
    			counter = vScopeObjNicknameMonitorInbox.size();
    			sqlStmt = "";
    			boolean first = true;
				int j = 1;
				ListIterator li1 = vScopeObjNicknameMonitorInbox.listIterator();    
				sqlStmt = "select * from TOPIK left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where (TOPIK.TARGETNPMHS like ? and (TOPIK.MARKEDASREADATTARGET not like ? or TOPIK.MARKEDASREADATTARGET is null)) or (SUBTOPIK.NPMHSRECEIVER like ? and SUBTOPIK.MARKEDASREADATRECEIVER=false) ";//NPM ONLY PART
    			first = true;
    			String tmp1 = "",tmp3="";
    			String tmp2 = "";
    			String tmp1a = "";
    			for(int i=0;i<counter;i++) {		
    				if(first) {
    					first = false;
    					tmp1 =  "TOPIK.TARGETOBJECTNICKNAME like ? ";
    					//tmp1 =  "(TOPIK.TARGETOBJECTNICKNAME like ? and TOPIK.MARKEDASREADATTARGET not like ?) ";
    					tmp1a=  "TOPIK.MARKEDASREADATTARGET not like ? ";
    					tmp2 =  "SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    					tmp3 =  "TOPIK.CREATOROBJNICKNAME not like ? and SUBTOPIK.OBJNICKNAMESENDER not like ? ";
    				}
    				else {
    					tmp1 =  tmp1+"or TOPIK.TARGETOBJECTNICKNAME like ? ";
    					tmp1a=  tmp1a+" and TOPIK.MARKEDASREADATTARGET not like ? ";
    					tmp2 =  tmp2+"or SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    					tmp3 =  tmp3+" and TOPIK.CREATOROBJNICKNAME not like ? and SUBTOPIK.OBJNICKNAMESENDER not like ? ";
    				}
    			}
    			tmp1 = "(("+tmp1+") and TOPIK.TARGETNPMHS is null  and (("+tmp1a+" and TOPIK.MARKEDASREADATTARGET not like ?) or TOPIK.MARKEDASREADATTARGET is null)) ";
    			//tmp1 = "(("+tmp1+") and ("+tmp1a+") and TOPIK.TARGETNPMHS is null and  TOPIK.MARKEDASREADATTARGET not like ?) ";
    			tmp2 = "(("+tmp2+") and SUBTOPIK.NPMHSRECEIVER is null and SUBTOPIK.MARKEDASREADATRECEIVER=false) ";
    			sqlStmt=sqlStmt+" or "+tmp1+" or "+tmp2+" and TOPIK.NPMHSCREATOR not like ? and SUBTOPIK.NPMHSSENDER NOT like ? and "+tmp3+" order by SUBTOPIK.UPDTMTOPIK desc,TOPIK.UPDTMTOPIK desc;";
    			stmt = con.prepareStatement(sqlStmt);
    			//System.out.println("sqlStmt getUnreadMsgInbox="+sqlStmt);
    			j = 1;
    			li1 = vScopeObjNicknameMonitorInbox.listIterator();
    			stmt.setString(j++, this.operatorNpm);
    			stmt.setString(j++, this.operatorNpm);
    			stmt.setString(j++, this.operatorNpm);
    			String nicname="EmptyNickNmaeSbgDefaultValue";
    			while(li1.hasNext()) { // isi tmp1
    				nicname = (String)li1.next();
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, nicname);
    			}
    			li1 = vScopeObjNicknameMonitorInbox.listIterator();
    			while(li1.hasNext()) { //isi tmp1a
    				nicname = (String)li1.next();
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, nicname);
    			}
    			stmt.setString(j++, this.operatorNpm);
    			nicname = null;
    			li1 = vScopeObjNicknameMonitorInbox.listIterator();
    			while(li1.hasNext()) {//isi tmp2 
    				nicname = (String)li1.next();
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, nicname);
    			}
    			//System.out.println(j+"."+this.operatorNpm);
    			stmt.setString(j++, this.operatorNpm);
    			//System.out.println(j+"."+this.operatorNpm);
    			stmt.setString(j++, this.operatorNpm);
    			li1 = vScopeObjNicknameMonitorInbox.listIterator();
    			while(li1.hasNext()) {//isi tmp3 
    				nicname = (String)li1.next();
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, nicname);
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, nicname);
    			}
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String topik_idTopik = ""+rs.getLong("TOPIK.IDTOPIK");
    				String topik_conten = ""+rs.getString("TOPIK.TOPIKCONTENT");
    				String topik_npmhsCreator = ""+rs.getString("TOPIK.NPMHSCREATOR");
    				String topik_nmmhsCreator = ""+rs.getString("TOPIK.NMMHSCREATOR");
    				String topik_creatorObjId = ""+rs.getString("TOPIK.CRETOROBJECTID");
    				String topik_creatorObjNickname = ""+rs.getString("TOPIK.CREATOROBJNICKNAME");
    				String topik_targetKdpst = ""+rs.getString("TOPIK.TARGETKDPST");
    				String topik_targetNpmhs = ""+rs.getString("TOPIK.TARGETNPMHS");
    				String topik_targetSmawl = ""+rs.getString("TOPIK.TARGETSMAWL");
    				String topik_targetObjId = ""+rs.getString("TOPIK.TARGETOBJEKID");
    				String topik_targetObjNickname = ""+rs.getString("TOPIK.TARGETOBJECTNICKNAME");
    				String topik_targetGroupId = ""+rs.getLong("TOPIK.TARGETGROUPID");
    				String topik_groupPwd = ""+rs.getString("TOPIK.GROUPPWD");
    				String topik_shownToGroupOnly = ""+rs.getBoolean("TOPIK.SHOWNTOGROUPONLY");
    				String topik_deletedByCreator = ""+rs.getBoolean("TOPIK.DELETEDBYCREATOR");
    				String topik_hidenAtCreator = ""+rs.getBoolean("TOPIK.HIDENATCREATOR");
    				String topik_pinedAtCreator = ""+rs.getBoolean("TOPIK.PINEDATCREATOR");
    				String topik_markedAsReadAtCreator = ""+rs.getBoolean("TOPIK.MARKEDASREADATCREATOR");
    				String topik_deletedAtTarget = ""+rs.getString("TOPIK.DELETEDATTARGET");
    				String topik_hidenAtTarget = ""+rs.getString("TOPIK.HIDENATTARGET");
    				String topik_pinedAtTarget = ""+rs.getString("TOPIK.PINEDATTARGET");
    				String topik_markedAsReasAsTarget = ""+rs.getString("TOPIK.MARKEDASREADATTARGET");
    				String topik_creatorAsAnonymous = ""+rs.getBoolean("TOPIK.CREATORASANONYMOUS");
    				String topik_creatorIsPetugas = ""+rs.getBoolean("TOPIK.CRETORISPETUGAS");
    				String topik_updtm = ""+rs.getTimestamp("TOPIK.UPDTMTOPIK");
				
				
    				String sub_id = ""+rs.getLong("SUBTOPIK.IDSUBTOPIK");
    				String sub_idTopik = ""+rs.getLong("SUBTOPIK.IDTOPIK");
    				String sub_comment = ""+rs.getString("SUBTOPIK.COMMENT");
    				String sub_npmhsSender = ""+rs.getString("SUBTOPIK.NPMHSSENDER");
    				String sub_nmmhsSender = ""+rs.getString("SUBTOPIK.NMMHSSENDER");
    				String sub_anonymousReply = ""+rs.getBoolean("SUBTOPIK.ANONYNOUSREPLY");
    				String sub_shownToCreatorObly = ""+rs.getBoolean("SUBTOPIK.SHOWNTOCREATERONLY");
    				String sub_commenterIsPetugas = ""+rs.getBoolean("SUBTOPIK.COMMENTERISPETUGAS");
    				String sub_markedAsReadAtCreator = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATRECEIVER");
    				String sub_markedAsReadAtSender = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATSENDER");
    				String sub_objNicknameSender = ""+rs.getString("SUBTOPIK.OBJNICKNAMESENDER");
    				String sub_npmhsReceiver =  ""+rs.getString("SUBTOPIK.NPMHSRECEIVER");
    				String sub_nmmhsReceiver = ""+rs.getString("SUBTOPIK.NMMHSRECEIVER");
    				String sub_objNicknameReceiver = ""+rs.getString("SUBTOPIK.OBJNICKNAMERECEIVER");
    				String sub_updtm = ""+rs.getTimestamp("SUBTOPIK.UPDTMTOPIK");
				 
				/*
				 * note: semua topik id yang udag ada komennya maka TOPIK.MARKEDASREADATTARGET contains objnameUsr
				 */
    				String tmp = topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+sub_id+"||"+sub_idTopik+"||"+sub_comment+"||"+sub_npmhsSender+"||"+sub_nmmhsSender+"||"+sub_anonymousReply+"||"+sub_shownToCreatorObly+"||"+sub_commenterIsPetugas+"||"+sub_markedAsReadAtCreator+"||"+sub_markedAsReadAtSender+"||"+sub_objNicknameSender+"||"+sub_npmhsReceiver+"||"+sub_nmmhsReceiver+"||"+sub_objNicknameReceiver+"||"+sub_updtm;
    				tmp = tmp.replace("||||", "||null||");
					li.add(tmp);
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
    	return v;
    }	          

    
    
    public Vector getMostRecentMsgDistinctTopikId(int limitMsg, String objNickNameUsr,Vector vScopeObjNicknameOwnInbox) {
    	/*
    	 * recent msg dari inbox jadi bisa ada beberapa tipe objek
    	 */
    	Vector vtmp = new Vector();
    	ListIterator litmp = vtmp.listIterator();
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	boolean locked  = false;
    	int counter = 0;
    	try {
    		if(vScopeObjNicknameOwnInbox!=null || vScopeObjNicknameOwnInbox.size()>0) {
    			counter = vScopeObjNicknameOwnInbox.size();
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			counter = vScopeObjNicknameOwnInbox.size();
    			//sqlStmt = "select * from SUBTOPIK inner join TOPIK on SUBTOPIK.IDTOPIK=TOPIK.IDTOPIK where (SUBTOPIK.NPMHSRECEIVER like ? or ";
				/*
				 * 1. cek  msg at topik table only - blm ada stu komen pun
				 */
    			String sqlStmt = "";
    			boolean first = true;
				int j = 1;
				ListIterator li1 = vScopeObjNicknameOwnInbox.listIterator();    			
				/*
				 * topik table
				 * jika targetNpmhs=null & targetObjectNickname!= null maka tar
				 */
				sqlStmt = "select DISTINCT TOPIK.IDTOPIK from TOPIK left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where (TOPIK.TARGETNPMHS like ? or SUBTOPIK.NPMHSRECEIVER like ?) ";
    			first = true;
    			String tmp1 = "";
    			String tmp2 = "";
    			for(int i=0;i<counter;i++) {
    				
    				if(first) {
    					first = false;
    					tmp1 =  "TOPIK.TARGETOBJECTNICKNAME like ? ";
    					tmp2 =  "SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    					//sqlStmt = sqlStmt+"SUBTOPIK.OBJNICKNAMERECEIVER like ? or TOPIK.TARGETOBJECTNICKNAME like ? ";
    				}
    				else {
    					tmp1 =  tmp1+"or TOPIK.TARGETOBJECTNICKNAME like ? ";
    					tmp2 =  tmp2+"or SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    					//sqlStmt = sqlStmt+"or SUBTOPIK.OBJNICKNAMERECEIVER like ? or TOPIK.TARGETOBJECTNICKNAME like ? ";
    				}
			
    			}
    			tmp1 = "(("+tmp1+") and TOPIK.TARGETNPMHS is null) ";
    			tmp2 = "(("+tmp2+") and SUBTOPIK.NPMHSRECEIVER is null) ";
    			//sqlStmt=sqlStmt+") and SUBTOPIK.MARKEDASREADATRECEIVER not like ? order by SUBTOPIK.UPDTMTOPIK";
    			//sqlStmt=sqlStmt+") order by SUBTOPIK.UPDTMTOPIK";
    			sqlStmt=sqlStmt+" or "+tmp1+" or "+tmp2+" or TOPIK.NPMHSCREATOR like ? or NPMHSSENDER like ? order by SUBTOPIK.UPDTMTOPIK desc,TOPIK.UPDTMTOPIK desc limit ?";
    			stmt = con.prepareStatement(sqlStmt);
    			//System.out.println("sqlStmt2="+sqlStmt);
    			j = 1;
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			stmt.setString(j++, this.operatorNpm);
    			stmt.setString(j++, this.operatorNpm);
    			while(li1.hasNext()) {
    				String nicname = (String)li1.next();
    				stmt.setString(j++, nicname);
    			}
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			while(li1.hasNext()) {
    				String nicname = (String)li1.next();
    				stmt.setString(j++, nicname);
    			}
    			stmt.setString(j++, this.operatorNpm);
    			//System.out.println(j+"%"+this.operatorNpm+"%");
    			stmt.setString(j++, this.operatorNpm);
    			stmt.setInt(j++, limitMsg);
    			//System.out.println(j+"%"+this.operatorNpm+"%");
    			//stmt.setString(j++, "%"+objNickNameUsr+"%");
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String topik_idTopik = ""+rs.getLong("TOPIK.IDTOPIK");
    				/*
    				String topik_conten = ""+rs.getString("TOPIK.TOPIKCONTENT");
    				String topik_npmhsCreator = ""+rs.getString("TOPIK.NPMHSCREATOR");
    				String topik_nmmhsCreator = ""+rs.getString("TOPIK.NMMHSCREATOR");
    				String topik_creatorObjId = ""+rs.getString("TOPIK.CRETOROBJECTID");
    				String topik_creatorObjNickname = ""+rs.getString("TOPIK.CREATOROBJNICKNAME");
    				String topik_targetKdpst = ""+rs.getString("TOPIK.TARGETKDPST");
    				String topik_targetNpmhs = ""+rs.getString("TOPIK.TARGETNPMHS");
    				String topik_targetSmawl = ""+rs.getString("TOPIK.TARGETSMAWL");
    				String topik_targetObjId = ""+rs.getString("TOPIK.TARGETOBJEKID");
    				String topik_targetObjNickname = ""+rs.getString("TOPIK.TARGETOBJECTNICKNAME");
    				String topik_targetGroupId = ""+rs.getLong("TOPIK.TARGETGROUPID");
    				String topik_groupPwd = ""+rs.getString("TOPIK.GROUPPWD");
    				String topik_shownToGroupOnly = ""+rs.getBoolean("TOPIK.SHOWNTOGROUPONLY");
    				String topik_deletedByCreator = ""+rs.getBoolean("TOPIK.DELETEDBYCREATOR");
    				String topik_hidenAtCreator = ""+rs.getBoolean("TOPIK.HIDENATCREATOR");
    				String topik_pinedAtCreator = ""+rs.getBoolean("TOPIK.PINEDATCREATOR");
    				String topik_markedAsReadAtCreator = ""+rs.getBoolean("TOPIK.MARKEDASREADATCREATOR");
    				String topik_deletedAtTarget = ""+rs.getString("TOPIK.DELETEDATTARGET");
    				String topik_hidenAtTarget = ""+rs.getString("TOPIK.HIDENATTARGET");
    				String topik_pinedAtTarget = ""+rs.getString("TOPIK.PINEDATTARGET");
    				String topik_markedAsReasAsTarget = ""+rs.getString("TOPIK.MARKEDASREADATTARGET");
    				String topik_creatorAsAnonymous = ""+rs.getBoolean("TOPIK.CREATORASANONYMOUS");
    				String topik_creatorIsPetugas = ""+rs.getBoolean("TOPIK.CRETORISPETUGAS");
    				String topik_updtm = ""+rs.getTimestamp("TOPIK.UPDTMTOPIK");
				
				
    				String sub_id = ""+rs.getLong("SUBTOPIK.IDSUBTOPIK");
    				String sub_idTopik = ""+rs.getLong("SUBTOPIK.IDTOPIK");
    				String sub_comment = ""+rs.getString("SUBTOPIK.COMMENT");
    				String sub_npmhsSender = ""+rs.getString("SUBTOPIK.NPMHSSENDER");
    				String sub_nmmhsSender = ""+rs.getString("SUBTOPIK.NMMHSSENDER");
    				String sub_anonymousReply = ""+rs.getBoolean("SUBTOPIK.ANONYNOUSREPLY");
    				String sub_shownToCreatorObly = ""+rs.getBoolean("SUBTOPIK.SHOWNTOCREATERONLY");
    				String sub_commenterIsPetugas = ""+rs.getBoolean("SUBTOPIK.COMMENTERISPETUGAS");
    				String sub_markedAsReadAtCreator = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATRECEIVER");
    				String sub_markedAsReadAtSender = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATSENDER");
    				String sub_objNicknameSender = ""+rs.getString("SUBTOPIK.OBJNICKNAMESENDER");
    				String sub_npmhsReceiver =  ""+rs.getString("SUBTOPIK.NPMHSRECEIVER");
    				String sub_nmmhsReceiver = ""+rs.getString("SUBTOPIK.NMMHSRECEIVER");
    				String sub_objNicknameReceiver = ""+rs.getString("SUBTOPIK.OBJNICKNAMERECEIVER");
    				String sub_updtm = ""+rs.getTimestamp("SUBTOPIK.UPDTMTOPIK");
				 */
				/*
				 * note: semua topik id yang udag ada komennya maka TOPIK.MARKEDASREADATTARGET contains objnameUsr
				 */
	
    				//String tmp = topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+sub_id+"||"+sub_idTopik+"||"+sub_comment+"||"+sub_npmhsSender+"||"+sub_nmmhsSender+"||"+sub_anonymousReply+"||"+sub_shownToCreatorObly+"||"+sub_commenterIsPetugas+"||"+sub_markedAsReadAtCreator+"||"+sub_markedAsReadAtSender+"||"+sub_objNicknameSender+"||"+sub_npmhsReceiver+"||"+sub_nmmhsReceiver+"||"+sub_objNicknameReceiver+"||"+sub_updtm;
    				//tmp = tmp.replace("||||", "||null||");
					litmp.add(""+topik_idTopik);
    			}
    			
    			sqlStmt="select * from TOPIK left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where TOPIK.IDTOPIK=? limit 1";
    			stmt = con.prepareStatement(sqlStmt);
    			litmp = vtmp.listIterator();
    			while(litmp.hasNext()) {
    				String topId = (String)litmp.next();
    				stmt.setLong(1,Long.valueOf(topId).longValue());
    				rs = stmt.executeQuery();
    				rs.next();
    				String topik_idTopik = ""+rs.getLong("TOPIK.IDTOPIK");
    				String topik_conten = ""+rs.getString("TOPIK.TOPIKCONTENT");
    				String topik_npmhsCreator = ""+rs.getString("TOPIK.NPMHSCREATOR");
    				String topik_nmmhsCreator = ""+rs.getString("TOPIK.NMMHSCREATOR");
    				String topik_creatorObjId = ""+rs.getString("TOPIK.CRETOROBJECTID");
    				String topik_creatorObjNickname = ""+rs.getString("TOPIK.CREATOROBJNICKNAME");
    				String topik_targetKdpst = ""+rs.getString("TOPIK.TARGETKDPST");
    				String topik_targetNpmhs = ""+rs.getString("TOPIK.TARGETNPMHS");
    				String topik_targetSmawl = ""+rs.getString("TOPIK.TARGETSMAWL");
    				String topik_targetObjId = ""+rs.getString("TOPIK.TARGETOBJEKID");
    				String topik_targetObjNickname = ""+rs.getString("TOPIK.TARGETOBJECTNICKNAME");
    				String topik_targetGroupId = ""+rs.getLong("TOPIK.TARGETGROUPID");
    				String topik_groupPwd = ""+rs.getString("TOPIK.GROUPPWD");
    				String topik_shownToGroupOnly = ""+rs.getBoolean("TOPIK.SHOWNTOGROUPONLY");
    				String topik_deletedByCreator = ""+rs.getBoolean("TOPIK.DELETEDBYCREATOR");
    				String topik_hidenAtCreator = ""+rs.getBoolean("TOPIK.HIDENATCREATOR");
    				String topik_pinedAtCreator = ""+rs.getBoolean("TOPIK.PINEDATCREATOR");
    				String topik_markedAsReadAtCreator = ""+rs.getBoolean("TOPIK.MARKEDASREADATCREATOR");
    				String topik_deletedAtTarget = ""+rs.getString("TOPIK.DELETEDATTARGET");
    				String topik_hidenAtTarget = ""+rs.getString("TOPIK.HIDENATTARGET");
    				String topik_pinedAtTarget = ""+rs.getString("TOPIK.PINEDATTARGET");
    				String topik_markedAsReasAsTarget = ""+rs.getString("TOPIK.MARKEDASREADATTARGET");
    				String topik_creatorAsAnonymous = ""+rs.getBoolean("TOPIK.CREATORASANONYMOUS");
    				String topik_creatorIsPetugas = ""+rs.getBoolean("TOPIK.CRETORISPETUGAS");
    				String topik_updtm = ""+rs.getTimestamp("TOPIK.UPDTMTOPIK");
				
				
    				String sub_id = ""+rs.getLong("SUBTOPIK.IDSUBTOPIK");
    				String sub_idTopik = ""+rs.getLong("SUBTOPIK.IDTOPIK");
    				String sub_comment = ""+rs.getString("SUBTOPIK.COMMENT");
    				String sub_npmhsSender = ""+rs.getString("SUBTOPIK.NPMHSSENDER");
    				String sub_nmmhsSender = ""+rs.getString("SUBTOPIK.NMMHSSENDER");
    				String sub_anonymousReply = ""+rs.getBoolean("SUBTOPIK.ANONYNOUSREPLY");
    				String sub_shownToCreatorObly = ""+rs.getBoolean("SUBTOPIK.SHOWNTOCREATERONLY");
    				String sub_commenterIsPetugas = ""+rs.getBoolean("SUBTOPIK.COMMENTERISPETUGAS");
    				String sub_markedAsReadAtCreator = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATRECEIVER");
    				String sub_markedAsReadAtSender = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATSENDER");
    				String sub_objNicknameSender = ""+rs.getString("SUBTOPIK.OBJNICKNAMESENDER");
    				String sub_npmhsReceiver =  ""+rs.getString("SUBTOPIK.NPMHSRECEIVER");
    				String sub_nmmhsReceiver = ""+rs.getString("SUBTOPIK.NMMHSRECEIVER");
    				String sub_objNicknameReceiver = ""+rs.getString("SUBTOPIK.OBJNICKNAMERECEIVER");
    				String sub_updtm = ""+rs.getTimestamp("SUBTOPIK.UPDTMTOPIK");
    				//System.out.println("topId="+topId);
    				String tmp = topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+sub_id+"||"+sub_idTopik+"||"+sub_comment+"||"+sub_npmhsSender+"||"+sub_nmmhsSender+"||"+sub_anonymousReply+"||"+sub_shownToCreatorObly+"||"+sub_commenterIsPetugas+"||"+sub_markedAsReadAtCreator+"||"+sub_markedAsReadAtSender+"||"+sub_objNicknameSender+"||"+sub_npmhsReceiver+"||"+sub_nmmhsReceiver+"||"+sub_objNicknameReceiver+"||"+sub_updtm;
    				tmp = tmp.replace("||||", "||null||");
					li.add(tmp);
    			}
    			
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				//System.out.println("baar="+brs);
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
    	return v;
    }	              
    /*
     * deprecated
     */
    public Vector getMostRecentMsgDistinctTopikIdWithRange(String objNickNameUsr,Vector vScopeLawanBicara,long startAt,int quantity) {
    	/*
    	 * recent msg dari inbox jadi bisa ada beberapa tipe objek
    	 * most recent msg = incoming only, kalo sbg creator tidak masuk
    	 * vScopeLawanBicara = vSvopeOwnIbox kan ???
    	 */
    	Vector vtmp = new Vector();
    	ListIterator litmp = vtmp.listIterator();
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	boolean locked  = false;
    	int counter = 0;
    	try {
    		if(vScopeLawanBicara!=null || vScopeLawanBicara.size()>0) {
    			counter = vScopeLawanBicara.size();
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			counter = vScopeLawanBicara.size();
    			//System.out.println("counter="+counter);
    			//sqlStmt = "select * from SUBTOPIK inner join TOPIK on SUBTOPIK.IDTOPIK=TOPIK.IDTOPIK where (SUBTOPIK.NPMHSRECEIVER like ? or ";
				/*
				 * 1. cek  msg at topik table only - blm ada stu komen pun
				 */
    			String sqlStmt = "";
    			boolean first = true;
				int j = 1;
				ListIterator li1 = vScopeLawanBicara.listIterator();    			
				/*
				 * topik table
				 * jika targetNpmhs=null & targetObjectNickname!= null maka tar
				 */
				sqlStmt = "select DISTINCT TOPIK.IDTOPIK from TOPIK left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where (TOPIK.TARGETNPMHS like ? or SUBTOPIK.NPMHSRECEIVER like ?) ";
				
				first = true;
    			String tmp0="",tmp1 = "";
    			String tmp2 = "",tmp3="";
    			for(int i=0;i<counter;i++) {
    				
    				if(first) {
    					first = false;
    					//tmp0 =  "TOPIK.CREATORBJECTNICKNAME like ? or TOPIK.NPMHSCREATOR like ? ";
    					//tmp0a =  "SUBTOPIK.OBJNICKNAMESENDER like ? or SUBTOPIK.NPMHSSENDER like ? ";
    					tmp1 =  "TOPIK.TARGETOBJECTNICKNAME like ? ";
    					tmp2 =  "SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    					tmp3 =  "TOPIK.CREATOROBJNICKNAME not like ? and SUBTOPIK.OBJNICKNAMESENDER not like ? ";
    				}
    				else {
    					//tmp0 =  "or TOPIK.CREATORBJECTNICKNAME like ? or TOPIK.NPMHSCREATOR like ? ";
    					//tmp0a =  "or SUBTOPIK.OBJNICKNAMESENDER like ? or SUBTOPIK.NPMHSSENDER like ? ";
    					tmp1 =  tmp1+"or TOPIK.TARGETOBJECTNICKNAME like ? ";
    					tmp2 =  tmp2+"or SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    					tmp3 =  tmp3+"and TOPIK.CREATOROBJNICKNAME not like ? and SUBTOPIK.OBJNICKNAMESENDER not like ? ";
    				}
			
    			}
    			//sqlStmt=sqlStmt+tmp0+")) or (SUBTOPIK.NPMHSRECEIVER like ? and ("+tmp0a+")) ";
    			tmp1 = "(("+tmp1+") and TOPIK.TARGETNPMHS is null) ";
    			tmp2 = "(("+tmp2+") and SUBTOPIK.NPMHSRECEIVER is null) ";
    			//sqlStmt=sqlStmt+" or "+tmp1+" or "+tmp2+" or TOPIK.NPMHSCREATOR like ? or NPMHSSENDER like ? order by SUBTOPIK.UPDTMTOPIK desc,TOPIK.UPDTMTOPIK desc limit ?,?";
    			sqlStmt=sqlStmt+" or "+tmp1+" or "+tmp2+" and TOPIK.NPMHSCREATOR not like ? and SUBTOPIK.NPMHSSENDER NOT like ? and "+tmp3+" order by SUBTOPIK.UPDTMTOPIK desc ,TOPIK.UPDTMTOPIK  desc limit ?,?";
    			stmt = con.prepareStatement(sqlStmt);
    			//System.out.println("getMostRecentMsgDistinctTopikIdWithRange ="+sqlStmt);
    			j = 1;
    			
    			//System.out.println("operator NPM="+operatorNpm);
    			//System.out.println(j+"%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			//System.out.println(j+"%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			li1 = vScopeLawanBicara.listIterator();
    			while(li1.hasNext()) {// isi tmp1
    				String nicname = (String)li1.next();
    				//System.out.println(j+"%"+nicname+"%");
    				stmt.setString(j++, "%"+nicname+"%");
    				//stmt.setString(j++, "%"+nicname+"%");
    			}
    			li1 = vScopeLawanBicara.listIterator();
    			while(li1.hasNext()) {// isi tmp2
    				String nicname = (String)li1.next();
    				//System.out.println(j+"%"+nicname+"%");
    				stmt.setString(j++, "%"+nicname+"%");
    				//stmt.setString(j++, "%"+nicname+"%");
    			}
    			//System.out.println(j+"%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			//System.out.println(j+"%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			li1 = vScopeLawanBicara.listIterator();
    			while(li1.hasNext()) {// isi tmp3
    				String nicname = (String)li1.next();
    				//System.out.println(j+"%"+nicname+"%");
    				stmt.setString(j++, "%"+nicname+"%");
    				//System.out.println(j+"%"+nicname+"%");
    				stmt.setString(j++, "%"+nicname+"%");
    				//stmt.setString(j++, "%"+nicname+"%");
    			}
        		stmt.setLong(j++, startAt);
        		stmt.setInt(j++, quantity);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String topik_idTopik = ""+rs.getLong("TOPIK.IDTOPIK");
				/*
				 * note: semua topik id yang udag ada komennya maka TOPIK.MARKEDASREADATTARGET contains objnameUsr
				 */
					litmp.add(""+topik_idTopik);
    			}
    			//System.out.println("vTmp size = "+vtmp.size());
    			sqlStmt="select * from TOPIK left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where TOPIK.IDTOPIK=? limit 1";
    			stmt = con.prepareStatement(sqlStmt);
    			litmp = vtmp.listIterator();
    			while(litmp.hasNext()) {
    				String topId = (String)litmp.next();
    				stmt.setLong(1,Long.valueOf(topId).longValue());
    				rs = stmt.executeQuery();
    				rs.next();
    				String topik_idTopik = ""+rs.getLong("TOPIK.IDTOPIK");
    				String topik_conten = ""+rs.getString("TOPIK.TOPIKCONTENT");
    				String topik_npmhsCreator = ""+rs.getString("TOPIK.NPMHSCREATOR");
    				String topik_nmmhsCreator = ""+rs.getString("TOPIK.NMMHSCREATOR");
    				String topik_creatorObjId = ""+rs.getString("TOPIK.CRETOROBJECTID");
    				String topik_creatorObjNickname = ""+rs.getString("TOPIK.CREATOROBJNICKNAME");
    				String topik_targetKdpst = ""+rs.getString("TOPIK.TARGETKDPST");
    				String topik_targetNpmhs = ""+rs.getString("TOPIK.TARGETNPMHS");
    				String topik_targetSmawl = ""+rs.getString("TOPIK.TARGETSMAWL");
    				String topik_targetObjId = ""+rs.getString("TOPIK.TARGETOBJEKID");
    				String topik_targetObjNickname = ""+rs.getString("TOPIK.TARGETOBJECTNICKNAME");
    				String topik_targetGroupId = ""+rs.getLong("TOPIK.TARGETGROUPID");
    				String topik_groupPwd = ""+rs.getString("TOPIK.GROUPPWD");
    				String topik_shownToGroupOnly = ""+rs.getBoolean("TOPIK.SHOWNTOGROUPONLY");
    				String topik_deletedByCreator = ""+rs.getBoolean("TOPIK.DELETEDBYCREATOR");
    				String topik_hidenAtCreator = ""+rs.getBoolean("TOPIK.HIDENATCREATOR");
    				String topik_pinedAtCreator = ""+rs.getBoolean("TOPIK.PINEDATCREATOR");
    				String topik_markedAsReadAtCreator = ""+rs.getBoolean("TOPIK.MARKEDASREADATCREATOR");
    				String topik_deletedAtTarget = ""+rs.getString("TOPIK.DELETEDATTARGET");
    				String topik_hidenAtTarget = ""+rs.getString("TOPIK.HIDENATTARGET");
    				String topik_pinedAtTarget = ""+rs.getString("TOPIK.PINEDATTARGET");
    				String topik_markedAsReasAsTarget = ""+rs.getString("TOPIK.MARKEDASREADATTARGET");
    				String topik_creatorAsAnonymous = ""+rs.getBoolean("TOPIK.CREATORASANONYMOUS");
    				String topik_creatorIsPetugas = ""+rs.getBoolean("TOPIK.CRETORISPETUGAS");
    				String topik_updtm = ""+rs.getTimestamp("TOPIK.UPDTMTOPIK");
				
				
    				String sub_id = ""+rs.getLong("SUBTOPIK.IDSUBTOPIK");
    				String sub_idTopik = ""+rs.getLong("SUBTOPIK.IDTOPIK");
    				String sub_comment = ""+rs.getString("SUBTOPIK.COMMENT");
    				String sub_npmhsSender = ""+rs.getString("SUBTOPIK.NPMHSSENDER");
    				String sub_nmmhsSender = ""+rs.getString("SUBTOPIK.NMMHSSENDER");
    				String sub_anonymousReply = ""+rs.getBoolean("SUBTOPIK.ANONYNOUSREPLY");
    				String sub_shownToCreatorObly = ""+rs.getBoolean("SUBTOPIK.SHOWNTOCREATERONLY");
    				String sub_commenterIsPetugas = ""+rs.getBoolean("SUBTOPIK.COMMENTERISPETUGAS");
    				String sub_markedAsReadAtCreator = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATRECEIVER");
    				String sub_markedAsReadAtSender = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATSENDER");
    				String sub_objNicknameSender = ""+rs.getString("SUBTOPIK.OBJNICKNAMESENDER");
    				String sub_npmhsReceiver =  ""+rs.getString("SUBTOPIK.NPMHSRECEIVER");
    				String sub_nmmhsReceiver = ""+rs.getString("SUBTOPIK.NMMHSRECEIVER");
    				String sub_objNicknameReceiver = ""+rs.getString("SUBTOPIK.OBJNICKNAMERECEIVER");
    				String sub_updtm = ""+rs.getTimestamp("SUBTOPIK.UPDTMTOPIK");
    				String tmp = topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+sub_id+"||"+sub_idTopik+"||"+sub_comment+"||"+sub_npmhsSender+"||"+sub_nmmhsSender+"||"+sub_anonymousReply+"||"+sub_shownToCreatorObly+"||"+sub_commenterIsPetugas+"||"+sub_markedAsReadAtCreator+"||"+sub_markedAsReadAtSender+"||"+sub_objNicknameSender+"||"+sub_npmhsReceiver+"||"+sub_nmmhsReceiver+"||"+sub_objNicknameReceiver+"||"+sub_updtm;
    				tmp = tmp.replace("||||", "||null||");
					li.add(tmp);
    			}
    			
    			//li = v.listIterator();
    			//while(li.hasNext()) {
    			//	String brs = (String)li.next();
    			//}
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

    
    public Vector get100MostRecentMsgDistinctTopikIdWithRange_v2(String objNickNameUsr,Vector vScopeOwnInbox,long startAt,int quantity) {
    	/*
    	 * recent msg dari inbox jadi bisa ada beberapa tipe objek
    	 * most recent msg = incoming only, kalo sbg creator tidak masuk
    	 * vScopeLawanBicara = vSvopeOwnIbox kan ???
    	 */
    	startAt=0;
    	quantity=100;
    	//System.out.println("getMostRecentMsgDistinctTopikIdWithRange_v2");
    	Vector vtmp = new Vector();
    	ListIterator litmp = vtmp.listIterator();
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	Vector v2 = new Vector();
    	ListIterator li2 = v2.listIterator();
    	boolean locked  = false;
    	int counter = 0;
    	try {
    		if(vScopeOwnInbox!=null || vScopeOwnInbox.size()>0) {
    			counter = vScopeOwnInbox.size();
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			counter = vScopeOwnInbox.size();
    			//System.out.println("counter="+counter);
    			//sqlStmt = "select * from SUBTOPIK inner join TOPIK on SUBTOPIK.IDTOPIK=TOPIK.IDTOPIK where (SUBTOPIK.NPMHSRECEIVER like ? or ";
				/*
				 * 1. cek  msg at topik table only - blm ada stu komen pun
				 */
    			String sqlStmt = "";
    			boolean first = true;
				int j = 1;
				ListIterator li1 = vScopeOwnInbox.listIterator();    
				while(li1.hasNext()) {
					String brs = (String)li1.next();
					//System.out.println(brs);
				}
				/*
				 * topik table
				 * jika targetNpmhs=null & targetObjectNickname!= null maka tar
				 */
		
				//sqlStmt = "select DISTINCT TOPIK.IDTOPIK from TOPIK left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where (TOPIK.TARGETNPMHS like ? or SUBTOPIK.NPMHSRECEIVER like ?) ";
				//sqlStmt = "select * from TOPIK left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where ((TOPIK.TARGETNPMHS like ? or SUBTOPIK.NPMHSRECEIVER like ?) ";
				//select * from TOPIK  left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where (TOPIK.TARGETNPMHS like '%0000312100006%' or ((TOPIK.TARGETOBJECTNICKNAME like '%OPERATOR KEPALA BAA%' or TOPIK.TARGETOBJECTNICKNAME like '%OPERATOR BAA%') and TOPIK.TARGETNPMHS is null)) 
				sqlStmt = "select * from TOPIK  left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where (TOPIK.TARGETNPMHS like ?";
				first = true;
    			String tmp0="",tmp1 = "";
    			String tmp2 = "",tmp3="";
    			for(int i=0;i<counter;i++) {
    				if(first) {
    					first = false;
    					tmp1 =  "TOPIK.TARGETOBJECTNICKNAME like ? ";
    					//tmp2 =  "SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    					//tmp3 =  "TOPIK.CREATOROBJNICKNAME not like ? and SUBTOPIK.OBJNICKNAMESENDER not like ? ";
    				}
    				else {
    					tmp1 =  tmp1+"or TOPIK.TARGETOBJECTNICKNAME like ? ";
    					//tmp2 =  tmp2+"or SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    					//tmp3 =  tmp3+"and TOPIK.CREATOROBJNICKNAME not like ? and SUBTOPIK.OBJNICKNAMESENDER not like ? ";
    				}
				}
    			tmp1 = "(("+tmp1+") and TOPIK.TARGETNPMHS is NULL)) ";
    			//tmp2 = "(("+tmp2+") and SUBTOPIK.NPMHSRECEIVER is null) ";
    			//sqlStmt=sqlStmt+" or "+tmp1+" or "+tmp2+") and TOPIK.NPMHSCREATOR not like ? and SUBTOPIK.NPMHSSENDER NOT like ? and "+tmp3+" order by SUBTOPIK.UPDTMTOPIK desc ,TOPIK.UPDTMTOPIK  desc limit ?,?";
    			//sqlStmt=sqlStmt+" or "+tmp1+" or "+tmp2+")  order by SUBTOPIK.UPDTMTOPIK desc ,TOPIK.UPDTMTOPIK  desc limit ?,?";
    			sqlStmt = sqlStmt+" or "+tmp1+" limit ?,?";
    			
    			sqlStmt = sqlStmt+" UNION ";
    			//select * from TOPIK  right outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where (SUBTOPIK.NPMHSRECEIVER like '%0000312100006%' or ((SUBTOPIK.OBJNICKNAMERECEIVER like '%OPERATOR KEPALA BAA%' or SUBTOPIK.OBJNICKNAMERECEIVER like '%OPERATOR BAA%') and SUBTOPIK.NPMHSRECEIVER is null));
    			sqlStmt = sqlStmt+" select * from TOPIK  right outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where (SUBTOPIK.NPMHSRECEIVER like ?";
    			first = true;
    			tmp0="";tmp1 = "";
    			tmp2 = "";tmp3="";
    			for(int i=0;i<counter;i++) {
    				if(first) {
    					first = false;
    					tmp1 =  "SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    				}
    				else {
    					tmp1 =  tmp1+"or SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    				}
				}
    			tmp1 = "(("+tmp1+") and SUBTOPIK.NPMHSRECEIVER is NULL)) ";
    			sqlStmt = sqlStmt+" or "+tmp1+" limit ?,?";
    			
    			stmt = con.prepareStatement(sqlStmt);
    			//System.out.println("getMostRecentMsgDistinctTopikIdWithRange ="+sqlStmt);
    			j = 1;
    			//System.out.println(j+"."+"%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			li1 = vScopeOwnInbox.listIterator();
    			while(li1.hasNext()) {// isi tmp1
    				String nicname = (String)li1.next();
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, nicname);
    			}
    			stmt.setLong(j++,startAt);
    			stmt.setLong(j++,quantity);
    			//union
    			
    			//System.out.println(j+"."+"%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			li1 = vScopeOwnInbox.listIterator();
    			while(li1.hasNext()) {// isi tmp1
    				String nicname = (String)li1.next();
    				//System.out.println(j+"."+"%"+nicname);
    				stmt.setString(j++, nicname);
    			}
    			stmt.setLong(j++,startAt);
    			stmt.setLong(j++,quantity);
    			rs = stmt.executeQuery();
    			int k=0;
    			while(rs.next()) {
    			/*
				 * note: semua topik id yang udag ada komennya maka TOPIK.MARKEDASREADATTARGET contains objnameUsr
				 */
    				String topik_idTopik = ""+rs.getLong("IDTOPIK");
    				String topik_conten = ""+rs.getString("TOPIKCONTENT");
    				String topik_npmhsCreator = ""+rs.getString("NPMHSCREATOR");
    				String topik_nmmhsCreator = ""+rs.getString("NMMHSCREATOR");
    				String topik_creatorObjId = ""+rs.getString("CRETOROBJECTID");
    				String topik_creatorObjNickname = ""+rs.getString("CREATOROBJNICKNAME");
    				String topik_targetKdpst = ""+rs.getString("TARGETKDPST");
    				String topik_targetNpmhs = ""+rs.getString("TARGETNPMHS");
    				String topik_targetSmawl = ""+rs.getString("TARGETSMAWL");
    				String topik_targetObjId = ""+rs.getString("TARGETOBJEKID");
    				String topik_targetObjNickname = ""+rs.getString("TARGETOBJECTNICKNAME");
    				String topik_targetGroupId = ""+rs.getLong("TARGETGROUPID");
    				String topik_groupPwd = ""+rs.getString("GROUPPWD");
    				String topik_shownToGroupOnly = ""+rs.getBoolean("SHOWNTOGROUPONLY");
    				String topik_deletedByCreator = ""+rs.getBoolean("DELETEDBYCREATOR");
    				String topik_hidenAtCreator = ""+rs.getBoolean("HIDENATCREATOR");
    				String topik_pinedAtCreator = ""+rs.getBoolean("PINEDATCREATOR");
    				String topik_markedAsReadAtCreator = ""+rs.getBoolean("MARKEDASREADATCREATOR");
    				String topik_deletedAtTarget = ""+rs.getString("DELETEDATTARGET");
    				String topik_hidenAtTarget = ""+rs.getString("HIDENATTARGET");
    				String topik_pinedAtTarget = ""+rs.getString("PINEDATTARGET");
    				String topik_markedAsReasAsTarget = ""+rs.getString("MARKEDASREADATTARGET");
    				String topik_creatorAsAnonymous = ""+rs.getBoolean("CREATORASANONYMOUS");
    				String topik_creatorIsPetugas = ""+rs.getBoolean("CRETORISPETUGAS");
    				String topik_updtm = ""+rs.getTimestamp("UPDTMTOPIK");
				
				
    				String sub_id = ""+rs.getLong("IDSUBTOPIK");
    				String sub_idTopik = ""+rs.getLong("IDTOPIK");
    				String sub_comment = ""+rs.getString("COMMENT");
    				String sub_npmhsSender = ""+rs.getString("NPMHSSENDER");
    				String sub_nmmhsSender = ""+rs.getString("NMMHSSENDER");
    				String sub_anonymousReply = ""+rs.getBoolean("ANONYNOUSREPLY");
    				String sub_shownToCreatorObly = ""+rs.getBoolean("SHOWNTOCREATERONLY");
    				String sub_commenterIsPetugas = ""+rs.getBoolean("COMMENTERISPETUGAS");
    				String sub_markedAsReadAtCreator = ""+rs.getBoolean("MARKEDASREADATRECEIVER");
    				String sub_markedAsReadAtSender = ""+rs.getBoolean("MARKEDASREADATSENDER");
    				String sub_objNicknameSender = ""+rs.getString("OBJNICKNAMESENDER");
    				String sub_npmhsReceiver =  ""+rs.getString("NPMHSRECEIVER");
    				String sub_nmmhsReceiver = ""+rs.getString("NMMHSRECEIVER");
    				String sub_objNicknameReceiver = ""+rs.getString("OBJNICKNAMERECEIVER");
    				String sub_updtm = ""+rs.getTimestamp("UPDTMTOPIK");
    				String tmp = topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+sub_id+"||"+sub_idTopik+"||"+sub_comment+"||"+sub_npmhsSender+"||"+sub_nmmhsSender+"||"+sub_anonymousReply+"||"+sub_shownToCreatorObly+"||"+sub_commenterIsPetugas+"||"+sub_markedAsReadAtCreator+"||"+sub_markedAsReadAtSender+"||"+sub_objNicknameSender+"||"+sub_npmhsReceiver+"||"+sub_nmmhsReceiver+"||"+sub_objNicknameReceiver+"||"+sub_updtm;
    				//System.out.println(k+"."+tmp);
    				tmp = tmp.replace("||||", "||null||");
					li.add(tmp);
    			}
    			//get jumlah replay di subtopik krn kalo replynya cuma balasan ke pencipta topik, maka harus tetap tampil di 
    			
    			/*
    			 * sorting berdasarkan uploadtm
    			 */
    			li = v.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				//System.out.println("** "+brs);
    				StringTokenizer st = new StringTokenizer(brs,"||");
    				while(st.hasMoreTokens()) {
    					String topik_idTopik = st.nextToken();
        				String topik_conten = st.nextToken();
        				String topik_npmhsCreator = st.nextToken();
        				String topik_nmmhsCreator = st.nextToken();
        				String topik_creatorObjId = st.nextToken();
        				String topik_creatorObjNickname = st.nextToken();
        				String topik_targetKdpst = st.nextToken();
        				String topik_targetNpmhs = st.nextToken();
        				String topik_targetSmawl = st.nextToken();
        				String topik_targetObjId = st.nextToken();
        				String topik_targetObjNickname = st.nextToken();
        				String topik_targetGroupId = st.nextToken();
        				String topik_groupPwd = st.nextToken();
        				String topik_shownToGroupOnly = st.nextToken();
        				String topik_deletedByCreator = st.nextToken();
        				String topik_hidenAtCreator = st.nextToken();
        				String topik_pinedAtCreator = st.nextToken();
        				String topik_markedAsReadAtCreator = st.nextToken();
        				String topik_deletedAtTarget = st.nextToken();
        				String topik_hidenAtTarget = st.nextToken();
        				String topik_pinedAtTarget = st.nextToken();
        				String topik_markedAsReasAsTarget = st.nextToken();
        				String topik_creatorAsAnonymous = st.nextToken();
        				String topik_creatorIsPetugas = st.nextToken();
        				String topik_updtm = st.nextToken();
    				
    				
        				String sub_id = st.nextToken();
        				String sub_idTopik = st.nextToken();
        				String sub_comment = st.nextToken();
        				String sub_npmhsSender = st.nextToken();
        				String sub_nmmhsSender = st.nextToken();
        				String sub_anonymousReply = st.nextToken();
        				String sub_shownToCreatorObly = st.nextToken();
        				String sub_commenterIsPetugas = st.nextToken();
        				String sub_markedAsReadAtCreator = st.nextToken();
        				String sub_markedAsReadAtSender = st.nextToken();
        				String sub_objNicknameSender = st.nextToken();
        				String sub_npmhsReceiver = st.nextToken();
        				String sub_nmmhsReceiver = st.nextToken();
        				String sub_objNicknameReceiver = st.nextToken();
        				String sub_updtm = st.nextToken();
        				String timeUsedForSort = null;
        				
        				boolean topik_targetObjNickname_ada_anda = false;
						String ur_nickname = this.tknOperatorNickname;
						StringTokenizer stt = new StringTokenizer(ur_nickname,",");
						while(stt.hasMoreTokens() && !topik_targetObjNickname_ada_anda) {
							String tmp_nick = stt.nextToken();
							if(topik_targetObjNickname.contains(tmp_nick)) {
								topik_targetObjNickname_ada_anda = true;
							}
						}

						boolean topik_targetNpmhs_ada_anda = false;
						String ur_npm = this.operatorNpm;
						if(topik_targetNpmhs.contains(ur_npm)) {
							topik_targetNpmhs_ada_anda = true;
						}

						//sub_objNicknameReceiver
						boolean sub_objNicknameReceiver_ada_anda = false;
						stt = new StringTokenizer(ur_nickname,",");
						while(stt.hasMoreTokens() && !sub_objNicknameReceiver_ada_anda) {
							String tmp_nick = stt.nextToken();
							if(sub_objNicknameReceiver.contains(tmp_nick)) {
								sub_objNicknameReceiver_ada_anda = true;
							}
						}

						//sub_npmhsReceiver
						boolean sub_npmhsReceiver_ada_anda = false;
						if(sub_npmhsReceiver.contains(ur_npm)) {
							sub_npmhsReceiver_ada_anda = true;
						}
        				
        				//if(sub_updtm==null || sub_updtm.equalsIgnoreCase("null")) {
						if(!sub_npmhsReceiver_ada_anda && !sub_objNicknameReceiver_ada_anda) {
        					timeUsedForSort=""+topik_updtm;
        				}
        				else {
        					timeUsedForSort=""+sub_updtm;
        				}
        				li2.add(timeUsedForSort+"||"+brs);
    				}
    			}
    			Comparator comparator = Collections.reverseOrder();
				Collections.sort(v2,comparator);
				Vector v3 = (Vector) v2.clone();
				
				Vector v4 = new Vector();
				ListIterator li4 = v4.listIterator();
				li2 = v2.listIterator();
				while(li2.hasNext()) {
					int jum = 0;
					//boolean ada_reply_untuk_topik_targetObjNickname = false;
    				//boolean tidak_ada_reply_untuk_topik_targetObjNickname = false;
    				boolean ada_reply_untuk_anda = false;
    				boolean ada_reply_dari_anda = false;
    				//boolean tidak_ada_reply_made_by_topikcreator = false;
    				//boolean tidak_ada_reply_for_topikcreator = false;
					String brs = (String)li2.next();
					StringTokenizer st = new StringTokenizer(brs,"||");
    				while(st.hasMoreTokens()) {
    					String timeUsedForSort = st.nextToken();
    					String topik_idTopik = st.nextToken();
        				String topik_conten = st.nextToken();
        				String topik_npmhsCreator = st.nextToken();
        				String topik_nmmhsCreator = st.nextToken();
        				String topik_creatorObjId = st.nextToken();
        				String topik_creatorObjNickname = st.nextToken();
        				String topik_targetKdpst = st.nextToken();
        				String topik_targetNpmhs = st.nextToken();
        				String topik_targetSmawl = st.nextToken();
        				String topik_targetObjId = st.nextToken();
        				String topik_targetObjNickname = st.nextToken();
        				String topik_targetGroupId = st.nextToken();
        				String topik_groupPwd = st.nextToken();
        				String topik_shownToGroupOnly = st.nextToken();
        				String topik_deletedByCreator = st.nextToken();
        				String topik_hidenAtCreator = st.nextToken();
        				String topik_pinedAtCreator = st.nextToken();
        				String topik_markedAsReadAtCreator = st.nextToken();
        				String topik_deletedAtTarget = st.nextToken();
        				String topik_hidenAtTarget = st.nextToken();
        				String topik_pinedAtTarget = st.nextToken();
        				String topik_markedAsReasAsTarget = st.nextToken();
        				String topik_creatorAsAnonymous = st.nextToken();
        				String topik_creatorIsPetugas = st.nextToken();
        				String topik_updtm = st.nextToken();
    				
    				
        				String sub_id = st.nextToken();
        				String sub_idTopik = st.nextToken();
        				String sub_comment = st.nextToken();
        				String sub_npmhsSender = st.nextToken();
        				String sub_nmmhsSender = st.nextToken();
        				String sub_anonymousReply = st.nextToken();
        				String sub_shownToCreatorObly = st.nextToken();
        				String sub_commenterIsPetugas = st.nextToken();
        				String sub_markedAsReadAtCreator = st.nextToken();
        				String sub_markedAsReadAtSender = st.nextToken();
        				String sub_objNicknameSender = st.nextToken();
        				String sub_npmhsReceiver = st.nextToken();
        				String sub_nmmhsReceiver = st.nextToken();
        				String sub_objNicknameReceiver = st.nextToken();
        				String sub_updtm = st.nextToken();
        				
        				ListIterator li3 = v3.listIterator();
        				while(li3.hasNext()) {
        					String brs_ = (String)li3.next();
        					StringTokenizer st_ = new StringTokenizer(brs_,"||");
            				while(st_.hasMoreTokens()) {
            					String timeUsedForSort_ = st_.nextToken();
            					String topik_idTopik_ = st_.nextToken();
                				String topik_conten_ = st_.nextToken();
                				String topik_npmhsCreator_ = st_.nextToken();
                				String topik_nmmhsCreator_ = st_.nextToken();
                				String topik_creatorObjId_ = st_.nextToken();
                				String topik_creatorObjNickname_ = st_.nextToken();
                				String topik_targetKdpst_ = st_.nextToken();
                				String topik_targetNpmhs_ = st_.nextToken();
                				String topik_targetSmawl_ = st_.nextToken();
                				String topik_targetObjId_ = st_.nextToken();
                				String topik_targetObjNickname_ = st_.nextToken();
                				String topik_targetGroupId_ = st_.nextToken();
                				String topik_groupPwd_ = st_.nextToken();
                				String topik_shownToGroupOnly_ = st_.nextToken();
                				String topik_deletedByCreator_ = st_.nextToken();
                				String topik_hidenAtCreator_ = st_.nextToken();
                				String topik_pinedAtCreator_ = st_.nextToken();
                				String topik_markedAsReadAtCreator_ = st_.nextToken();
                				String topik_deletedAtTarget_ = st_.nextToken();
                				String topik_hidenAtTarget_ = st_.nextToken();
                				String topik_pinedAtTarget_ = st_.nextToken();
                				String topik_markedAsReasAsTarget_ = st_.nextToken();
                				String topik_creatorAsAnonymous_ = st_.nextToken();
                				String topik_creatorIsPetugas_ = st_.nextToken();
                				String topik_updtm_ = st_.nextToken();
            				
            				
                				String sub_id_ = st_.nextToken();
                				String sub_idTopik_ = st_.nextToken();
                				String sub_comment_ = st_.nextToken();
                				String sub_npmhsSender_ = st_.nextToken();
                				String sub_nmmhsSender_ = st_.nextToken();
                				String sub_anonymousReply_ = st_.nextToken();
                				String sub_shownToCreatorObly_ = st_.nextToken();
                				String sub_commenterIsPetugas_ = st_.nextToken();
                				String sub_markedAsReadAtCreator_ = st_.nextToken();
                				String sub_markedAsReadAtSender_ = st_.nextToken();
                				String sub_objNicknameSender_ = st_.nextToken();
                				String sub_npmhsReceiver_ = st_.nextToken();
                				String sub_nmmhsReceiver_ = st_.nextToken();
                				String sub_objNicknameReceiver_ = st_.nextToken();
                				String sub_updtm_ = st_.nextToken();
                				if(topik_idTopik_.equalsIgnoreCase(topik_idTopik)) {
                					jum++;
                					if(this.tknOperatorNickname.contains(sub_objNicknameReceiver_) || sub_npmhsReceiver_.contains(operatorNpm) ) {
                						ada_reply_untuk_anda = true;
                					}
                					if(this.tknOperatorNickname.contains(sub_objNicknameSender_) || sub_npmhsSender_.contains(operatorNpm) ) {
                						ada_reply_dari_anda = true;
                					}
                				}
                				
            				}	
        				}
    				}
    				if(jum==1 && ada_reply_untuk_anda) {
    					li4.add(brs+"||"+jum+",ada_reply_untuk_anda,reply,subtime");
    				}
    				else if(jum==1 && ada_reply_dari_anda) {
    					li4.add(brs+"||"+jum+",ada_reply_dari_anda,topik,toptime");
    				}
    				else if(jum==0) {
    					li4.add(brs+"||"+jum+",no_reply,topik,toptime");
    				}
    				else if(jum>1 && !ada_reply_untuk_anda) {
    					li4.add(brs+"||"+jum+",tidak_ada_reply_untuk_anda,topik,toptime");
    				}
    				else if(jum>1 && ada_reply_untuk_anda) {
    					li4.add(brs+"||"+jum+",ada_reply_untuk_anda,reply,subtime");
    				}
    				else if(jum==1 && !ada_reply_dari_anda && !ada_reply_untuk_anda) { // msg dari buku tamu no replay dari saipa pun
    					li4.add(brs+"||"+jum+",no_reply,topik,toptime");
    				}
    				//System.out.println("jum=="+jum);
    				//System.out.println("ada_reply_untuk_anda=="+ada_reply_untuk_anda);
    				//System.out.println("ada_reply_dari_anda=="+ada_reply_dari_anda);
    				//int jum = 0;
    				//boolean ada_reply_untuk_anda = false;
    				//boolean ada_reply_dari_anda = false;
				}
				//System.out.println("v4 size = "+v4.size());
				v = v4;
				//li4 = v4.listIterator();
				//while(li4.hasNext()) {
				//	String brs = (String)li4.next();
				//	//System.out.println("li4."+brs);
				//}
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
    
    
    public Vector getMostRecentMsgWhereUsrIsTheObjectWithRange(String objNickNameUsr,Vector vScopeLawanBicara,long startAt,int quantity) {
    	/*
    	 * 1.list topik yg dibuat oleh usrNpm and target=listObjNicknameSameKategoriAsUsr, kenapa npm krn usr adalah satu kategori jadi harus private
    	 * 2.list incoming for listObjNicknameSameKategoriAsUsr tapi topik tidak dibuat oleh usrNpm
    	 */
    	Vector vtmp = new Vector();
    	ListIterator litmp = vtmp.listIterator();
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	boolean locked  = false;
    	int counter = 0;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//sent part =====================
			String sqlStmt = "select DISTINCT TOPIK.IDTOPIK from TOPIK left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where (TOPIK.NPMHSCREATOR like ?";
			litmp = vScopeLawanBicara.listIterator();
			if(litmp.hasNext()) {
				sqlStmt = sqlStmt+" and (";
				while(litmp.hasNext()) {
					litmp.next();
					sqlStmt= sqlStmt + "TOPIK.TARGETOBJECTNICKNAME like ?";
					if(litmp.hasNext()) {
						sqlStmt= sqlStmt + " or ";
					}
				}	
				sqlStmt = sqlStmt+"))";
			}
			else {
				sqlStmt = sqlStmt+")";
			}
			//receving part
			sqlStmt=sqlStmt+" ";
			StringTokenizer st = new StringTokenizer(objNickNameUsr,",");
			if(st.hasMoreTokens()) {
				sqlStmt = sqlStmt+" or ((";
				while(st.hasMoreTokens()) {
					st.nextToken();
					sqlStmt= sqlStmt + "TOPIK.TARGETOBJECTNICKNAME like ?";
					if(st.hasMoreTokens()) {
						sqlStmt= sqlStmt + " or ";
					}
				}	
				sqlStmt = sqlStmt+") and (TOPIK.NPMHSCREATOR not like ?";
			}
			litmp = vScopeLawanBicara.listIterator();
			if(litmp.hasNext()) {
				sqlStmt = sqlStmt+" and (";
				while(litmp.hasNext()) {
					litmp.next();
					sqlStmt= sqlStmt + "TOPIK.CREATOROBJNICKNAME like ?";
					if(litmp.hasNext()) {
						sqlStmt= sqlStmt + " or ";
					}
				}	
				sqlStmt = sqlStmt+")))";
			}
			else {
				sqlStmt = sqlStmt+")";
			}
			//updated 23vjanuari2014
			//sqlStmt = sqlStmt+" order by SUBTOPIK.UPDTMTOPIK desc,TOPIK.UPDTMTOPIK desc limit ?,?";
			sqlStmt = sqlStmt+" order by TOPIK.UPDTMTOPIK desc,SUBTOPIK.UPDTMTOPIK desc limit ?,?";
			
			stmt = con.prepareStatement(sqlStmt);
			//System.out.println("sq="+sqlStmt);
			int j=1;
			stmt.setString(j++, this.operatorNpm);
			litmp = vScopeLawanBicara.listIterator();
			while(litmp.hasNext()) {
				stmt.setString(j++, "%"+(String)litmp.next()+"%");
			}
			st = new StringTokenizer(objNickNameUsr,",");
			while(st.hasMoreTokens()) {
				stmt.setString(j++, st.nextToken());
			}
			stmt.setString(j++, this.operatorNpm);
			litmp = vScopeLawanBicara.listIterator();
			while(litmp.hasNext()) {
				stmt.setString(j++, (String)litmp.next());
			}
			stmt.setLong(j++, startAt);
    		stmt.setInt(j++, quantity);
			rs = stmt.executeQuery();
			litmp = vtmp.listIterator();
			while(rs.next()) {
				String topik_idTopik = ""+rs.getLong("TOPIK.IDTOPIK");
			/*
			 * note: semua topik id yang udag ada komennya maka TOPIK.MARKEDASREADATTARGET contains objnameUsr
			 */
				litmp.add(""+topik_idTopik);
			}
			//System.out.println("vTmp size = "+vtmp.size());
			sqlStmt="select * from TOPIK left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where TOPIK.IDTOPIK=? limit 1";
			stmt = con.prepareStatement(sqlStmt);
			litmp = vtmp.listIterator();
			while(litmp.hasNext()) {
				String topId = (String)litmp.next();
				stmt.setLong(1,Long.valueOf(topId).longValue());
				rs = stmt.executeQuery();
				rs.next();
				String topik_idTopik = ""+rs.getLong("TOPIK.IDTOPIK");
				String topik_conten = ""+rs.getString("TOPIK.TOPIKCONTENT");
				String topik_npmhsCreator = ""+rs.getString("TOPIK.NPMHSCREATOR");
				String topik_nmmhsCreator = ""+rs.getString("TOPIK.NMMHSCREATOR");
				String topik_creatorObjId = ""+rs.getString("TOPIK.CRETOROBJECTID");
				String topik_creatorObjNickname = ""+rs.getString("TOPIK.CREATOROBJNICKNAME");
				String topik_targetKdpst = ""+rs.getString("TOPIK.TARGETKDPST");
				String topik_targetNpmhs = ""+rs.getString("TOPIK.TARGETNPMHS");
				String topik_targetSmawl = ""+rs.getString("TOPIK.TARGETSMAWL");
				String topik_targetObjId = ""+rs.getString("TOPIK.TARGETOBJEKID");
				String topik_targetObjNickname = ""+rs.getString("TOPIK.TARGETOBJECTNICKNAME");
				String topik_targetGroupId = ""+rs.getLong("TOPIK.TARGETGROUPID");
				String topik_groupPwd = ""+rs.getString("TOPIK.GROUPPWD");
				String topik_shownToGroupOnly = ""+rs.getBoolean("TOPIK.SHOWNTOGROUPONLY");
				String topik_deletedByCreator = ""+rs.getBoolean("TOPIK.DELETEDBYCREATOR");
				String topik_hidenAtCreator = ""+rs.getBoolean("TOPIK.HIDENATCREATOR");
				String topik_pinedAtCreator = ""+rs.getBoolean("TOPIK.PINEDATCREATOR");
				String topik_markedAsReadAtCreator = ""+rs.getBoolean("TOPIK.MARKEDASREADATCREATOR");
				String topik_deletedAtTarget = ""+rs.getString("TOPIK.DELETEDATTARGET");
				String topik_hidenAtTarget = ""+rs.getString("TOPIK.HIDENATTARGET");
				String topik_pinedAtTarget = ""+rs.getString("TOPIK.PINEDATTARGET");
				String topik_markedAsReasAsTarget = ""+rs.getString("TOPIK.MARKEDASREADATTARGET");
				String topik_creatorAsAnonymous = ""+rs.getBoolean("TOPIK.CREATORASANONYMOUS");
				String topik_creatorIsPetugas = ""+rs.getBoolean("TOPIK.CRETORISPETUGAS");
				String topik_updtm = ""+rs.getTimestamp("TOPIK.UPDTMTOPIK");
			
			
				String sub_id = ""+rs.getLong("SUBTOPIK.IDSUBTOPIK");
				String sub_idTopik = ""+rs.getLong("SUBTOPIK.IDTOPIK");
				String sub_comment = ""+rs.getString("SUBTOPIK.COMMENT");
				String sub_npmhsSender = ""+rs.getString("SUBTOPIK.NPMHSSENDER");
				String sub_nmmhsSender = ""+rs.getString("SUBTOPIK.NMMHSSENDER");
				String sub_anonymousReply = ""+rs.getBoolean("SUBTOPIK.ANONYNOUSREPLY");
				String sub_shownToCreatorObly = ""+rs.getBoolean("SUBTOPIK.SHOWNTOCREATERONLY");
				String sub_commenterIsPetugas = ""+rs.getBoolean("SUBTOPIK.COMMENTERISPETUGAS");
				String sub_markedAsReadAtCreator = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATRECEIVER");
				String sub_markedAsReadAtSender = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATSENDER");
				String sub_objNicknameSender = ""+rs.getString("SUBTOPIK.OBJNICKNAMESENDER");
				String sub_npmhsReceiver =  ""+rs.getString("SUBTOPIK.NPMHSRECEIVER");
				String sub_nmmhsReceiver = ""+rs.getString("SUBTOPIK.NMMHSRECEIVER");
				String sub_objNicknameReceiver = ""+rs.getString("SUBTOPIK.OBJNICKNAMERECEIVER");
				String sub_updtm = ""+rs.getTimestamp("SUBTOPIK.UPDTMTOPIK");
				String tmp = topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+sub_id+"||"+sub_idTopik+"||"+sub_comment+"||"+sub_npmhsSender+"||"+sub_nmmhsSender+"||"+sub_anonymousReply+"||"+sub_shownToCreatorObly+"||"+sub_commenterIsPetugas+"||"+sub_markedAsReadAtCreator+"||"+sub_markedAsReadAtSender+"||"+sub_objNicknameSender+"||"+sub_npmhsReceiver+"||"+sub_nmmhsReceiver+"||"+sub_objNicknameReceiver+"||"+sub_updtm;
				tmp = tmp.replace("||||", "||null||");
				li.add(tmp);
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
					//System.out.println(ignore);
				}
			}
		}
    	return v;
    }	              

    //(usrObjNickname,vScopeLawanBicara,topik_idTopik1);
    public int getValueIndexXMostRecentMsgTargetedTopikIdUsrIsTheObjectWithRange(String objNickNameUsr,Vector vScopeLawanBicara,String targetedTopikId) {
    	/*
    	 * 1.list topik yg dibuat oleh usrNpm and target=listObjNicknameSameKategoriAsUsr, kenapa npm krn usr adalah satu kategori jadi harus private
    	 * 2.list incoming for listObjNicknameSameKategoriAsUsr tapi topik tidak dibuat oleh usrNpm
    	 */
    	//System.out.println("-=-=-=");
    	Vector vtmp = new Vector();
    	ListIterator litmp = vtmp.listIterator();
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	boolean locked  = false;
    	int counter = 0;
    	int x = 0;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//sent part =====================
			String sqlStmt = "select DISTINCT TOPIK.IDTOPIK from TOPIK left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where (TOPIK.NPMHSCREATOR like ?";
			litmp = vScopeLawanBicara.listIterator();
			if(litmp.hasNext()) {
				sqlStmt = sqlStmt+" and (";
				while(litmp.hasNext()) {
					litmp.next();
					sqlStmt= sqlStmt + "TOPIK.TARGETOBJECTNICKNAME like ?";
					if(litmp.hasNext()) {
						sqlStmt= sqlStmt + " or ";
					}
				}	
				sqlStmt = sqlStmt+"))";
			}
			else {
				sqlStmt = sqlStmt+")";
			}
			//receving part
			sqlStmt=sqlStmt+" ";
			StringTokenizer st = new StringTokenizer(objNickNameUsr,",");
			if(st.hasMoreTokens()) {
				sqlStmt = sqlStmt+" or ((";
				while(st.hasMoreTokens()) {
					st.nextToken();
					sqlStmt= sqlStmt + "TOPIK.TARGETOBJECTNICKNAME like ?";
					if(st.hasMoreTokens()) {
						sqlStmt= sqlStmt + " or ";
					}
				}	
				sqlStmt = sqlStmt+") and (TOPIK.NPMHSCREATOR not like ?";
			}
			litmp = vScopeLawanBicara.listIterator();
			if(litmp.hasNext()) {
				sqlStmt = sqlStmt+" and (";
				while(litmp.hasNext()) {
					litmp.next();
					sqlStmt= sqlStmt + "TOPIK.CREATOROBJNICKNAME like ?";
					if(litmp.hasNext()) {
						sqlStmt= sqlStmt + " or ";
					}
				}	
				sqlStmt = sqlStmt+")))";
			}
			else {
				sqlStmt = sqlStmt+")";
			}
			//update 23jan2014
			//sqlStmt = sqlStmt+" order by SUBTOPIK.UPDTMTOPIK desc,TOPIK.UPDTMTOPIK desc limit ?,?";
			sqlStmt = sqlStmt+" order by TOPIK.UPDTMTOPIK desc, SUBTOPIK.UPDTMTOPIK desc limit ?,?";
			stmt = con.prepareStatement(sqlStmt);
			
			//System.out.println("sq1="+sqlStmt);
			stmt = con.prepareStatement(sqlStmt);
			boolean match = false;
			int y=100;
			//System.out.println("targetedTopikId ="+targetedTopikId);
			while(!match) {
				int j=1;
				stmt.setString(j++, this.operatorNpm);
				litmp = vScopeLawanBicara.listIterator();
				while(litmp.hasNext()) {
					stmt.setString(j++, (String)litmp.next());
				}
				st = new StringTokenizer(objNickNameUsr,",");
				while(st.hasMoreTokens()) {
					stmt.setString(j++, st.nextToken());
				}
				stmt.setString(j++, this.operatorNpm);
				litmp = vScopeLawanBicara.listIterator();
				while(litmp.hasNext()) {
					stmt.setString(j++, (String)litmp.next());
				}
				stmt.setLong(j++, x);
				stmt.setInt(j++, y);
				rs = stmt.executeQuery();
				litmp = vtmp.listIterator();
				while(rs.next()) {
					//System.out.println("re.next()");
					String topik_idTopik = ""+rs.getLong("TOPIK.IDTOPIK");
					//System.out.println(topik_idTopik+" vs "+targetedTopikId);
			/*
			 * note: semua topik id yang udag ada komennya maka TOPIK.MARKEDASREADATTARGET contains objnameUsr
			 */
					if(topik_idTopik.equalsIgnoreCase(targetedTopikId)) {
    					match = true;
    					//System.out.println("topik_idTopik = "+topik_idTopik+" match at x="+x);
    				}
    				if(!match) {
    					x++;
    				}
				}	
				//litmp.add(""+topik_idTopik);
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
					//System.out.println(ignore);
				}
			}
		}
    	//System.out.println("-=-END=-=");
    	return x;
    }	              
    
    
    /*
     * deprecated
     */
    public Vector getMostRecentMsgDistinctTopikIdWithRangeUsrIsNotTarget(String objNickNameUsr,Vector vScopeLawanBicara,long startAt,int quantity) {
    	/*
    	 * recent msg dari inbox jadi bisa ada beberapa tipe objek
    	 */
    	//System.out.println("==objNickNameUsr "+objNickNameUsr);
    	Vector vtmp = new Vector();
    	ListIterator litmp = vtmp.listIterator();
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	boolean locked  = false;
    	int counter = 0;
    	try {
    		if(vScopeLawanBicara!=null || vScopeLawanBicara.size()>0) {
    			counter = vScopeLawanBicara.size();
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			counter = vScopeLawanBicara.size();
    			//System.out.println("counter="+counter);
    			//sqlStmt = "select * from SUBTOPIK inner join TOPIK on SUBTOPIK.IDTOPIK=TOPIK.IDTOPIK where (SUBTOPIK.NPMHSRECEIVER like ? or ";
				/*
				 * 1. cek  msg at topik table only - blm ada stu komen pun
				 */
    			String sqlStmt = "";
    			boolean first = true;
				int j = 1;
				ListIterator li1 = vScopeLawanBicara.listIterator();    			
				/*
				 * topik table
				 * jika targetNpmhs=null & targetObjectNickname!= null maka tar
				 */
				sqlStmt = "select DISTINCT TOPIK.IDTOPIK from TOPIK left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where (TOPIK.TARGETNPMHS like ? or SUBTOPIK.NPMHSRECEIVER like ?) ";
				//System.out.println("is this MysqlStmt="+sqlStmt);
				first = true;
    			String tmp0="",tmp1 = "";
    			String tmp2 = "",tmp1a="";
    			for(int i=0;i<counter;i++) {
    				//System.out.println("count="+i);
    				//System.out.println("li1="+li1.next());
    				if(first) {
    					first = false;
    					//tmp0 =  "TOPIK.CREATORBJECTNICKNAME like ? or TOPIK.NPMHSCREATOR like ? ";
    					//tmp0a =  "SUBTOPIK.OBJNICKNAMESENDER like ? or SUBTOPIK.NPMHSSENDER like ? ";
    					tmp1 =  "(TOPIK.TARGETOBJECTNICKNAME like ? and (TOPIK.NPMHSCREATOR like ?";
    					tmp2 =  "(SUBTOPIK.OBJNICKNAMERECEIVER like ? and (SUBTOPIK.NPMHSSENDER like ?";
    					StringTokenizer st = new StringTokenizer(objNickNameUsr,",");
    					while(st.hasMoreTokens()) {
    						String usrNick = st.nextToken();
    						tmp1 = tmp1 + " or TOPIK.CREATOROBJNICKNAME like ?";
    						tmp2 = tmp2 + " or SUBTOPIK.OBJNICKNAMESENDER like ?";
    					}
    					tmp1 = tmp1+"))";
    					tmp2 = tmp2+"))";
    				}
    				else {
    					//tmp0 =  "or TOPIK.CREATORBJECTNICKNAME like ? or TOPIK.NPMHSCREATOR like ? ";
    					//tmp0a =  "or SUBTOPIK.OBJNICKNAMESENDER like ? or SUBTOPIK.NPMHSSENDER like ? ";
    					tmp1 =  tmp1+"or (TOPIK.TARGETOBJECTNICKNAME like ? and (TOPIK.NPMHSCREATOR like ?";
    					tmp2 =  tmp2+"or (SUBTOPIK.OBJNICKNAMERECEIVER like ? and (SUBTOPIK.NPMHSSENDER like ?";
    					StringTokenizer st = new StringTokenizer(objNickNameUsr,",");
    					while(st.hasMoreTokens()) {
    						String usrNick = st.nextToken();
    						tmp1 = tmp1 + " or TOPIK.CREATOROBJNICKNAME like ?";
    						tmp2 = tmp2 + " or SUBTOPIK.OBJNICKNAMESENDER like ?";
    					}
    					tmp1 = tmp1+")) ";
    					tmp2 = tmp2+")) ";
    				}
			
    			}
    			sqlStmt=sqlStmt+" or "+tmp1+" or "+tmp2+" order by SUBTOPIK.UPDTMTOPIK desc,TOPIK.UPDTMTOPIK desc limit ?,?";
    			stmt = con.prepareStatement(sqlStmt);
    			//System.out.println("is MysqlStmt="+sqlStmt);

    			j = 1;
    			
    			//System.out.println("operator NPM="+operatorNpm);
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			li1 = vScopeLawanBicara.listIterator();// isi tmp1
    			while(li1.hasNext()) {// isi tmp1
    				String nicname = (String)li1.next();
    				stmt.setString(j++, nicname);
    				stmt.setString(j++, "%"+this.operatorNpm+"%");
    				StringTokenizer st = new StringTokenizer(objNickNameUsr,",");
					while(st.hasMoreTokens()) {
						String usrNick = st.nextToken();
						stmt.setString(j++, usrNick);
					}	
    			}
    			li1 = vScopeLawanBicara.listIterator();// isi tmp2
    			while(li1.hasNext()) {// isi tmp1
    				String nicname = (String)li1.next();
    				stmt.setString(j++, nicname);
    				stmt.setString(j++, "%"+this.operatorNpm+"%");
    				StringTokenizer st = new StringTokenizer(objNickNameUsr,",");
					while(st.hasMoreTokens()) {
						String usrNick = st.nextToken();
						stmt.setString(j++, usrNick);
					}	
    			}
    			stmt.setLong(j++, startAt);
        		stmt.setInt(j++, quantity);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String topik_idTopik = ""+rs.getLong("TOPIK.IDTOPIK");
				/*
				 * note: semua topik id yang udag ada komennya maka TOPIK.MARKEDASREADATTARGET contains objnameUsr
				 */
					litmp.add(""+topik_idTopik);
    			}
    			//System.out.println("vTmp size = "+vtmp.size());
    			sqlStmt="select * from TOPIK left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where TOPIK.IDTOPIK=? limit 1";
    			stmt = con.prepareStatement(sqlStmt);
    			litmp = vtmp.listIterator();
    			while(litmp.hasNext()) {
    				String topId = (String)litmp.next();
    				stmt.setLong(1,Long.valueOf(topId).longValue());
    				rs = stmt.executeQuery();
    				rs.next();
    				String topik_idTopik = ""+rs.getLong("TOPIK.IDTOPIK");
    				String topik_conten = ""+rs.getString("TOPIK.TOPIKCONTENT");
    				String topik_npmhsCreator = ""+rs.getString("TOPIK.NPMHSCREATOR");
    				String topik_nmmhsCreator = ""+rs.getString("TOPIK.NMMHSCREATOR");
    				String topik_creatorObjId = ""+rs.getString("TOPIK.CRETOROBJECTID");
    				String topik_creatorObjNickname = ""+rs.getString("TOPIK.CREATOROBJNICKNAME");
    				String topik_targetKdpst = ""+rs.getString("TOPIK.TARGETKDPST");
    				String topik_targetNpmhs = ""+rs.getString("TOPIK.TARGETNPMHS");
    				String topik_targetSmawl = ""+rs.getString("TOPIK.TARGETSMAWL");
    				String topik_targetObjId = ""+rs.getString("TOPIK.TARGETOBJEKID");
    				String topik_targetObjNickname = ""+rs.getString("TOPIK.TARGETOBJECTNICKNAME");
    				String topik_targetGroupId = ""+rs.getLong("TOPIK.TARGETGROUPID");
    				String topik_groupPwd = ""+rs.getString("TOPIK.GROUPPWD");
    				String topik_shownToGroupOnly = ""+rs.getBoolean("TOPIK.SHOWNTOGROUPONLY");
    				String topik_deletedByCreator = ""+rs.getBoolean("TOPIK.DELETEDBYCREATOR");
    				String topik_hidenAtCreator = ""+rs.getBoolean("TOPIK.HIDENATCREATOR");
    				String topik_pinedAtCreator = ""+rs.getBoolean("TOPIK.PINEDATCREATOR");
    				String topik_markedAsReadAtCreator = ""+rs.getBoolean("TOPIK.MARKEDASREADATCREATOR");
    				String topik_deletedAtTarget = ""+rs.getString("TOPIK.DELETEDATTARGET");
    				String topik_hidenAtTarget = ""+rs.getString("TOPIK.HIDENATTARGET");
    				String topik_pinedAtTarget = ""+rs.getString("TOPIK.PINEDATTARGET");
    				String topik_markedAsReasAsTarget = ""+rs.getString("TOPIK.MARKEDASREADATTARGET");
    				String topik_creatorAsAnonymous = ""+rs.getBoolean("TOPIK.CREATORASANONYMOUS");
    				String topik_creatorIsPetugas = ""+rs.getBoolean("TOPIK.CRETORISPETUGAS");
    				String topik_updtm = ""+rs.getTimestamp("TOPIK.UPDTMTOPIK");
				
				
    				String sub_id = ""+rs.getLong("SUBTOPIK.IDSUBTOPIK");
    				String sub_idTopik = ""+rs.getLong("SUBTOPIK.IDTOPIK");
    				String sub_comment = ""+rs.getString("SUBTOPIK.COMMENT");
    				String sub_npmhsSender = ""+rs.getString("SUBTOPIK.NPMHSSENDER");
    				String sub_nmmhsSender = ""+rs.getString("SUBTOPIK.NMMHSSENDER");
    				String sub_anonymousReply = ""+rs.getBoolean("SUBTOPIK.ANONYNOUSREPLY");
    				String sub_shownToCreatorObly = ""+rs.getBoolean("SUBTOPIK.SHOWNTOCREATERONLY");
    				String sub_commenterIsPetugas = ""+rs.getBoolean("SUBTOPIK.COMMENTERISPETUGAS");
    				String sub_markedAsReadAtCreator = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATRECEIVER");
    				String sub_markedAsReadAtSender = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATSENDER");
    				String sub_objNicknameSender = ""+rs.getString("SUBTOPIK.OBJNICKNAMESENDER");
    				String sub_npmhsReceiver =  ""+rs.getString("SUBTOPIK.NPMHSRECEIVER");
    				String sub_nmmhsReceiver = ""+rs.getString("SUBTOPIK.NMMHSRECEIVER");
    				String sub_objNicknameReceiver = ""+rs.getString("SUBTOPIK.OBJNICKNAMERECEIVER");
    				String sub_updtm = ""+rs.getTimestamp("SUBTOPIK.UPDTMTOPIK");
    				String tmp = topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+sub_id+"||"+sub_idTopik+"||"+sub_comment+"||"+sub_npmhsSender+"||"+sub_nmmhsSender+"||"+sub_anonymousReply+"||"+sub_shownToCreatorObly+"||"+sub_commenterIsPetugas+"||"+sub_markedAsReadAtCreator+"||"+sub_markedAsReadAtSender+"||"+sub_objNicknameSender+"||"+sub_npmhsReceiver+"||"+sub_nmmhsReceiver+"||"+sub_objNicknameReceiver+"||"+sub_updtm;
    				tmp = tmp.replace("||||", "||null||");
					li.add(tmp);
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
    	return v;
    }	              
    
    
    public Vector getMostRecentMsgWhereUsrIsNotTheObjectWithRange(String objNickNameUsr,Vector vScopeLawanBicara,long startAt,int quantity) {
    	/*
    	 * 1.creator npmusr and target allowkontactXXX
    	 * 
    	 */
    	//System.out.println("==objNickNameUsr== "+objNickNameUsr);
    	//System.out.println("==vScopeLawanBicara== "+vScopeLawanBicara.size());
    	Vector vtmp = new Vector();
    	ListIterator litmp = vtmp.listIterator();
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	boolean locked  = false;
    	int counter = 0;
    	

    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//sent part =====================
			String sqlStmt = "select DISTINCT TOPIK.IDTOPIK from TOPIK left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where ((TOPIK.NPMHSCREATOR like ? or ((";
			StringTokenizer st = new StringTokenizer(objNickNameUsr,","); // ngga mungkin bisa null krn setiap objeck punya nickname
			while(st.hasMoreTokens()) {
				st.nextToken();
				sqlStmt = sqlStmt + "CREATOROBJNICKNAME like ? ";
				if(st.hasMoreTokens()) {
					sqlStmt = sqlStmt + "or ";
				}
			}	
			sqlStmt = sqlStmt + ") and TOPIK.NPMHSCREATOR is NULL))";
			litmp = vScopeLawanBicara.listIterator();
			if(litmp.hasNext()) {
				sqlStmt = sqlStmt+" and (";
				while(litmp.hasNext()) {
					String nic = (String)litmp.next();
					sqlStmt= sqlStmt + "TOPIK.TARGETOBJECTNICKNAME like ?";
					if(litmp.hasNext()) {
						sqlStmt= sqlStmt + " or ";
					}
				}
				sqlStmt = sqlStmt+"))";
			}
			else {
				sqlStmt = sqlStmt+")";
			}
			
			//receving part
			sqlStmt=sqlStmt+" ";
			st = new StringTokenizer(objNickNameUsr,",");
			if(st.hasMoreTokens()) {
				sqlStmt = sqlStmt+" or ((";
				while(st.hasMoreTokens()) {
					st.nextToken();
					sqlStmt= sqlStmt + "TOPIK.TARGETOBJECTNICKNAME like ?";
					if(st.hasMoreTokens()) {
						sqlStmt= sqlStmt + " or ";
					}
				}	
				sqlStmt = sqlStmt+") and (TOPIK.TARGETNPMHS is NULL or TOPIK.TARGETNPMHS like ?)";
			}
			litmp = vScopeLawanBicara.listIterator();
			if(litmp.hasNext()) {
				sqlStmt = sqlStmt+" and (";
				while(litmp.hasNext()) {
					litmp.next();
					sqlStmt= sqlStmt + "TOPIK.CREATOROBJNICKNAME like ?";
					if(litmp.hasNext()) {
						sqlStmt= sqlStmt + " or ";
					}
				}	
				sqlStmt = sqlStmt+"))";
			}
			else {
				sqlStmt = sqlStmt+")";
			}
			
			//updated 23jan2014
			//sqlStmt = sqlStmt+" order by SUBTOPIK.UPDTMTOPIK desc,TOPIK.UPDTMTOPIK desc limit ?,?";
			sqlStmt = sqlStmt+" order by TOPIK.UPDTMTOPIK desc, SUBTOPIK.UPDTMTOPIK desc limit ?,?";
			//System.out.println("sq1="+sqlStmt);
			stmt = con.prepareStatement(sqlStmt);
			int j=1;
			//System.out.println(j+"."+this.operatorNpm);
			stmt.setString(j++, "%"+this.operatorNpm+"%");
			st = new StringTokenizer(objNickNameUsr,","); 
			while(st.hasMoreTokens()) {
				String nic = st.nextToken();
				//System.out.println(j+"."+nic);
				stmt.setString(j++, nic);
			}	
			litmp = vScopeLawanBicara.listIterator();
			while(litmp.hasNext()) {
				String nic = (String)litmp.next();
				//System.out.println(j+"."+nic);
				stmt.setString(j++, nic);
			}	
			st = new StringTokenizer(objNickNameUsr,",");
			while(st.hasMoreTokens()) {
				String nic = st.nextToken();
				//System.out.println(j+"."+nic);
				stmt.setString(j++, nic);
			}
			//System.out.println(j+"."+this.operatorNpm);
			stmt.setString(j++, "%"+this.operatorNpm+"%");
			litmp = vScopeLawanBicara.listIterator();
			while(litmp.hasNext()) {
				String nic = (String)litmp.next();
				//System.out.println(j+"."+nic);
				stmt.setString(j++, nic);
			}	
			//System.out.println(j+"."+startAt);
    		stmt.setLong(j++, startAt);
    		//System.out.println(j+"."+quantity);
        	stmt.setInt(j++, quantity);
    		rs = stmt.executeQuery();
    		litmp = vtmp.listIterator();
    		while(rs.next()) {
    			//System.out.println("add vTmp");
    				String topik_idTopik = ""+rs.getLong("TOPIK.IDTOPIK");
				/*
				 * note: semua topik id yang udag ada komennya maka TOPIK.MARKEDASREADATTARGET contains objnameUsr
				 */
					litmp.add(""+topik_idTopik);
    		}
    		//System.out.println("vTmp size = "+vtmp.size());
    		sqlStmt="select * from TOPIK left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where TOPIK.IDTOPIK=? limit 1";
    		stmt = con.prepareStatement(sqlStmt);
    		litmp = vtmp.listIterator();
    		while(litmp.hasNext()) {
    			String topId = (String)litmp.next();
    			stmt.setLong(1,Long.valueOf(topId).longValue());
    			rs = stmt.executeQuery();
    			rs.next();
    			String topik_idTopik = ""+rs.getLong("TOPIK.IDTOPIK");
    			String topik_conten = ""+rs.getString("TOPIK.TOPIKCONTENT");
    			String topik_npmhsCreator = ""+rs.getString("TOPIK.NPMHSCREATOR");
    			String topik_nmmhsCreator = ""+rs.getString("TOPIK.NMMHSCREATOR");
    			String topik_creatorObjId = ""+rs.getString("TOPIK.CRETOROBJECTID");
    			String topik_creatorObjNickname = ""+rs.getString("TOPIK.CREATOROBJNICKNAME");
    			String topik_targetKdpst = ""+rs.getString("TOPIK.TARGETKDPST");
    			String topik_targetNpmhs = ""+rs.getString("TOPIK.TARGETNPMHS");
    			String topik_targetSmawl = ""+rs.getString("TOPIK.TARGETSMAWL");
    			String topik_targetObjId = ""+rs.getString("TOPIK.TARGETOBJEKID");
    			String topik_targetObjNickname = ""+rs.getString("TOPIK.TARGETOBJECTNICKNAME");
    			String topik_targetGroupId = ""+rs.getLong("TOPIK.TARGETGROUPID");
    			String topik_groupPwd = ""+rs.getString("TOPIK.GROUPPWD");
    			String topik_shownToGroupOnly = ""+rs.getBoolean("TOPIK.SHOWNTOGROUPONLY");
    			String topik_deletedByCreator = ""+rs.getBoolean("TOPIK.DELETEDBYCREATOR");
    			String topik_hidenAtCreator = ""+rs.getBoolean("TOPIK.HIDENATCREATOR");
    			String topik_pinedAtCreator = ""+rs.getBoolean("TOPIK.PINEDATCREATOR");
    			String topik_markedAsReadAtCreator = ""+rs.getBoolean("TOPIK.MARKEDASREADATCREATOR");
    			String topik_deletedAtTarget = ""+rs.getString("TOPIK.DELETEDATTARGET");
    			String topik_hidenAtTarget = ""+rs.getString("TOPIK.HIDENATTARGET");
    			String topik_pinedAtTarget = ""+rs.getString("TOPIK.PINEDATTARGET");
    			String topik_markedAsReasAsTarget = ""+rs.getString("TOPIK.MARKEDASREADATTARGET");
    			String topik_creatorAsAnonymous = ""+rs.getBoolean("TOPIK.CREATORASANONYMOUS");
    			String topik_creatorIsPetugas = ""+rs.getBoolean("TOPIK.CRETORISPETUGAS");
    			String topik_updtm = ""+rs.getTimestamp("TOPIK.UPDTMTOPIK");
				
				
    			String sub_id = ""+rs.getLong("SUBTOPIK.IDSUBTOPIK");
    			String sub_idTopik = ""+rs.getLong("SUBTOPIK.IDTOPIK");
    			String sub_comment = ""+rs.getString("SUBTOPIK.COMMENT");
    			String sub_npmhsSender = ""+rs.getString("SUBTOPIK.NPMHSSENDER");
    			String sub_nmmhsSender = ""+rs.getString("SUBTOPIK.NMMHSSENDER");
    			String sub_anonymousReply = ""+rs.getBoolean("SUBTOPIK.ANONYNOUSREPLY");
    			String sub_shownToCreatorObly = ""+rs.getBoolean("SUBTOPIK.SHOWNTOCREATERONLY");
    			String sub_commenterIsPetugas = ""+rs.getBoolean("SUBTOPIK.COMMENTERISPETUGAS");
    			String sub_markedAsReadAtCreator = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATRECEIVER");
    			String sub_markedAsReadAtSender = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATSENDER");
    			String sub_objNicknameSender = ""+rs.getString("SUBTOPIK.OBJNICKNAMESENDER");
    			String sub_npmhsReceiver =  ""+rs.getString("SUBTOPIK.NPMHSRECEIVER");
    			String sub_nmmhsReceiver = ""+rs.getString("SUBTOPIK.NMMHSRECEIVER");
    			String sub_objNicknameReceiver = ""+rs.getString("SUBTOPIK.OBJNICKNAMERECEIVER");
    			String sub_updtm = ""+rs.getTimestamp("SUBTOPIK.UPDTMTOPIK");
    			String tmp = topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+sub_id+"||"+sub_idTopik+"||"+sub_comment+"||"+sub_npmhsSender+"||"+sub_nmmhsSender+"||"+sub_anonymousReply+"||"+sub_shownToCreatorObly+"||"+sub_commenterIsPetugas+"||"+sub_markedAsReadAtCreator+"||"+sub_markedAsReadAtSender+"||"+sub_objNicknameSender+"||"+sub_npmhsReceiver+"||"+sub_nmmhsReceiver+"||"+sub_objNicknameReceiver+"||"+sub_updtm;
    			tmp = tmp.replace("||||", "||null||");
				li.add(tmp);
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
    
    public int getValueIndexXMostRecentMsgTargetedTopikIdUsrIsNotTheObjectWithRange(String objNickNameUsr,Vector vScopeLawanBicara,String targetedTopikId) {
    	/*
    	 * sql yg dipake sama dengan getMostRecentMsgWhereUsrIsNotTheObjectWithRange
    	 * 
    	 */
    	//System.out.println("==objNickNameUsr== "+objNickNameUsr);
    	//System.out.println("==vScopeLawanBicara== "+vScopeLawanBicara.size());
    	Vector vtmp = new Vector();
    	ListIterator litmp = vtmp.listIterator();
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	boolean locked  = false;
    	int counter = 0;
    	int x = 0;

    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//sent part =====================
			String sqlStmt = "select DISTINCT TOPIK.IDTOPIK from TOPIK left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where ((TOPIK.NPMHSCREATOR like ? or ((";
			StringTokenizer st = new StringTokenizer(objNickNameUsr,","); // ngga mungkin bisa null krn setiap objeck punya nickname
			while(st.hasMoreTokens()) {
				st.nextToken();
				sqlStmt = sqlStmt + "CREATOROBJNICKNAME like ? ";
				if(st.hasMoreTokens()) {
					sqlStmt = sqlStmt + "or ";
				}
			}	
			sqlStmt = sqlStmt + ") and TOPIK.NPMHSCREATOR is NULL))";
			litmp = vScopeLawanBicara.listIterator();
			if(litmp.hasNext()) {
				sqlStmt = sqlStmt+" and (";
				while(litmp.hasNext()) {
					String nic = (String)litmp.next();
					sqlStmt= sqlStmt + "TOPIK.TARGETOBJECTNICKNAME like ?";
					if(litmp.hasNext()) {
						sqlStmt= sqlStmt + " or ";
					}
				}
				sqlStmt = sqlStmt+"))";
			}
			else {
				sqlStmt = sqlStmt+")";
			}
			
			//receving part
			sqlStmt=sqlStmt+" ";
			st = new StringTokenizer(objNickNameUsr,",");
			if(st.hasMoreTokens()) {
				sqlStmt = sqlStmt+" or ((";
				while(st.hasMoreTokens()) {
					st.nextToken();
					sqlStmt= sqlStmt + "TOPIK.TARGETOBJECTNICKNAME like ?";
					if(st.hasMoreTokens()) {
						sqlStmt= sqlStmt + " or ";
					}
				}	
				sqlStmt = sqlStmt+") and (TOPIK.TARGETNPMHS is NULL or TOPIK.TARGETNPMHS like ?)";
			}
			litmp = vScopeLawanBicara.listIterator();
			if(litmp.hasNext()) {
				sqlStmt = sqlStmt+" and (";
				while(litmp.hasNext()) {
					litmp.next();
					sqlStmt= sqlStmt + "TOPIK.CREATOROBJNICKNAME like ?";
					if(litmp.hasNext()) {
						sqlStmt= sqlStmt + " or ";
					}
				}	
				sqlStmt = sqlStmt+"))";
			}
			else {
				sqlStmt = sqlStmt+")";
			}
			//update 23jan2014
			//sqlStmt = sqlStmt+" order by SUBTOPIK.UPDTMTOPIK desc,TOPIK.UPDTMTOPIK desc limit ?,?";
			sqlStmt = sqlStmt+" order by TOPIK.UPDTMTOPIK desc,SUBTOPIK.UPDTMTOPIK desc limit ?,?";
			//System.out.println("sq1="+sqlStmt);
			stmt = con.prepareStatement(sqlStmt);
			boolean match = false;
			int y=2;
			//System.out.println("targetedTopikId ="+targetedTopikId);
			while(!match) {
				int j=1;
				//System.out.println(j+"."+this.operatorNpm);
				stmt.setString(j++, "%"+this.operatorNpm+"%");
				st = new StringTokenizer(objNickNameUsr,","); 
				while(st.hasMoreTokens()) {
					String nic = st.nextToken();
					//System.out.println(j+"."+nic);
					stmt.setString(j++, nic);
				}	
				litmp = vScopeLawanBicara.listIterator();
				while(litmp.hasNext()) {
					String nic = (String)litmp.next();
					//System.out.println(j+"."+nic);
					stmt.setString(j++, nic);
				}	
				st = new StringTokenizer(objNickNameUsr,",");
				while(st.hasMoreTokens()) {
					String nic = st.nextToken();
					//System.out.println(j+"."+nic);
					stmt.setString(j++, nic);
				}
				//System.out.println(j+"."+this.operatorNpm);
				stmt.setString(j++, "%"+this.operatorNpm+"%");
				litmp = vScopeLawanBicara.listIterator();
				while(litmp.hasNext()) {
					String nic = (String)litmp.next();
					//System.out.println(j+"."+nic);
					stmt.setString(j++, nic);
				}	
				//System.out.println(j+"."+x);
				stmt.setLong(j++, x);
				//System.out.println(j+"."+y);
				stmt.setInt(j++, y);
				rs = stmt.executeQuery();
				//litmp = vtmp.listIterator();
				while(rs.next()) {
					//System.out.println("next");
    				String topik_idTopik = ""+rs.getLong("TOPIK.IDTOPIK");
				/*
				 * note: semua topik id yang udag ada komennya maka TOPIK.MARKEDASREADATTARGET contains objnameUsr
				 */
    				if(topik_idTopik.equalsIgnoreCase(targetedTopikId)) {
    					match = true;
    					//System.out.println("topik_idTopik = "+topik_idTopik+" match at x="+x);
    				}
    				if(!match) {
    					x++;
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
    	return x;
    }	              
    
    
    
    public Long getValueIndexXMostRecentMsgDistinctTopikIdWithRangeAndStartAtTargetTopikId(String objNickNameUsr,Vector vScopeObjNicknameOwnInbox, String targetTopikId) {
    	/*
    	 * DEPRECATED!!!!!!!!!!
    	 * diganti 
    	 * getValueIndexXMostRecentMsgTargetedTopikIdUsrIsNotTheObjectWithRange
    	 * getValueIndexXMostRecentMsgTargetedTopikIdUsrIsTheObjectWithRange
    	 * 
    	 * recent msg dari inbox jadi bisa ada beberapa tipe objek
    	 */
    	long index = 0;
    	boolean match = false;
    	int startPointAt = 0;
    	int rangeAt = 100;
    	Vector vtmp = new Vector();
    	ListIterator litmp = vtmp.listIterator();
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	boolean locked  = false;
    	int counter = 0;
    	try {
    		if(vScopeObjNicknameOwnInbox!=null || vScopeObjNicknameOwnInbox.size()>0) {
    			counter = vScopeObjNicknameOwnInbox.size();
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			counter = vScopeObjNicknameOwnInbox.size();
    			//sqlStmt = "select * from SUBTOPIK inner join TOPIK on SUBTOPIK.IDTOPIK=TOPIK.IDTOPIK where (SUBTOPIK.NPMHSRECEIVER like ? or ";
				/*
				 * 1. cek  msg at topik table only - blm ada stu komen pun
				 */
    			String sqlStmt = "";
    			boolean first = true;
				int j = 1;
				ListIterator li1 = vScopeObjNicknameOwnInbox.listIterator();    			
				/*
				 * topik table
				 * jika targetNpmhs=null & targetObjectNickname!= null maka tar
				 */
				sqlStmt = "select DISTINCT TOPIK.IDTOPIK from TOPIK left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where (TOPIK.TARGETNPMHS like ? or SUBTOPIK.NPMHSRECEIVER like ?) ";
    			first = true;
    			String tmp1 = "";
    			String tmp2 = "";
    			for(int i=0;i<counter;i++) {
    				
    				if(first) {
    					first = false;
    					tmp1 =  "TOPIK.TARGETOBJECTNICKNAME like ? ";
    					tmp2 =  "SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    				}
    				else {
    					tmp1 =  tmp1+"or TOPIK.TARGETOBJECTNICKNAME like ? ";
    					tmp2 =  tmp2+"or SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    				}
			
    			}
    			tmp1 = "(("+tmp1+") and TOPIK.TARGETNPMHS is null) ";
    			tmp2 = "(("+tmp2+") and SUBTOPIK.NPMHSRECEIVER is null) ";
    			sqlStmt=sqlStmt+" or "+tmp1+" or "+tmp2+" or TOPIK.NPMHSCREATOR like ? or NPMHSSENDER like ? order by SUBTOPIK.UPDTMTOPIK desc,TOPIK.UPDTMTOPIK desc limit ?,?";
    			stmt = con.prepareStatement(sqlStmt);
    			//System.out.println("mySqlstmt="+sqlStmt);
    			do {
    			
    				j = 1;
    				li1 = vScopeObjNicknameOwnInbox.listIterator();
    				//System.out.println(j+". %"+this.operatorNpm+"%");
    				stmt.setString(j++, "%"+this.operatorNpm+"%");
    				//System.out.println(j+". %"+this.operatorNpm+"%");
    				stmt.setString(j++, "%"+this.operatorNpm+"%");
    				while(li1.hasNext()) {
    					String nicname = (String)li1.next();
    					//System.out.println(j+". %"+nicname+"%");
    					stmt.setString(j++, nicname);
    				}
    				li1 = vScopeObjNicknameOwnInbox.listIterator();
    				while(li1.hasNext()) {
    					String nicname = (String)li1.next();
    					//System.out.println(j+". %"+nicname+"%");
    					stmt.setString(j++, nicname);
    					
    				}
    				//System.out.println(j+". %"+this.operatorNpm+"%");
    				stmt.setString(j++, "%"+this.operatorNpm+"%");
    				//System.out.println(j+". %"+this.operatorNpm+"%");
    				stmt.setString(j++, "%"+this.operatorNpm+"%");
    				stmt.setInt(j++, startPointAt);
    				stmt.setInt(j++, rangeAt);
    				rs = stmt.executeQuery();
    				while(rs.next()) {
    					String topik_idTopik = ""+rs.getLong("TOPIK.IDTOPIK");
				/*
				 * note: semua topik id yang udag ada komennya maka TOPIK.MARKEDASREADATTARGET contains objnameUsr
				 */
    					litmp.add(""+topik_idTopik);
    				}
    			
    				sqlStmt="select * from TOPIK left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where TOPIK.IDTOPIK=? limit 1";
    				stmt = con.prepareStatement(sqlStmt);
    				litmp = vtmp.listIterator();
    				while(litmp.hasNext()&&!match) {
    					
    					String topId = (String)litmp.next();
    					stmt.setLong(1,Long.valueOf(topId).longValue());
    					rs = stmt.executeQuery();
    					rs.next();
    					String topik_idTopik = ""+rs.getLong("TOPIK.IDTOPIK");
    					String topik_conten = ""+rs.getString("TOPIK.TOPIKCONTENT");
    					String topik_npmhsCreator = ""+rs.getString("TOPIK.NPMHSCREATOR");
    					String topik_nmmhsCreator = ""+rs.getString("TOPIK.NMMHSCREATOR");
    					String topik_creatorObjId = ""+rs.getString("TOPIK.CRETOROBJECTID");
    					String topik_creatorObjNickname = ""+rs.getString("TOPIK.CREATOROBJNICKNAME");
    					String topik_targetKdpst = ""+rs.getString("TOPIK.TARGETKDPST");
    					String topik_targetNpmhs = ""+rs.getString("TOPIK.TARGETNPMHS");
    					String topik_targetSmawl = ""+rs.getString("TOPIK.TARGETSMAWL");
    					String topik_targetObjId = ""+rs.getString("TOPIK.TARGETOBJEKID");
    					String topik_targetObjNickname = ""+rs.getString("TOPIK.TARGETOBJECTNICKNAME");
    					String topik_targetGroupId = ""+rs.getLong("TOPIK.TARGETGROUPID");
    					String topik_groupPwd = ""+rs.getString("TOPIK.GROUPPWD");
    					String topik_shownToGroupOnly = ""+rs.getBoolean("TOPIK.SHOWNTOGROUPONLY");
    					String topik_deletedByCreator = ""+rs.getBoolean("TOPIK.DELETEDBYCREATOR");
    					String topik_hidenAtCreator = ""+rs.getBoolean("TOPIK.HIDENATCREATOR");
    					String topik_pinedAtCreator = ""+rs.getBoolean("TOPIK.PINEDATCREATOR");
    					String topik_markedAsReadAtCreator = ""+rs.getBoolean("TOPIK.MARKEDASREADATCREATOR");
    					String topik_deletedAtTarget = ""+rs.getString("TOPIK.DELETEDATTARGET");
    					String topik_hidenAtTarget = ""+rs.getString("TOPIK.HIDENATTARGET");
    					String topik_pinedAtTarget = ""+rs.getString("TOPIK.PINEDATTARGET");
    					String topik_markedAsReasAsTarget = ""+rs.getString("TOPIK.MARKEDASREADATTARGET");
    					String topik_creatorAsAnonymous = ""+rs.getBoolean("TOPIK.CREATORASANONYMOUS");
    					String topik_creatorIsPetugas = ""+rs.getBoolean("TOPIK.CRETORISPETUGAS");
    					String topik_updtm = ""+rs.getTimestamp("TOPIK.UPDTMTOPIK");
    					
				
    					String sub_id = ""+rs.getLong("SUBTOPIK.IDSUBTOPIK");
    					String sub_idTopik = ""+rs.getLong("SUBTOPIK.IDTOPIK");
    					String sub_comment = ""+rs.getString("SUBTOPIK.COMMENT");
    					String sub_npmhsSender = ""+rs.getString("SUBTOPIK.NPMHSSENDER");
    					String sub_nmmhsSender = ""+rs.getString("SUBTOPIK.NMMHSSENDER");
    					String sub_anonymousReply = ""+rs.getBoolean("SUBTOPIK.ANONYNOUSREPLY");
    					String sub_shownToCreatorObly = ""+rs.getBoolean("SUBTOPIK.SHOWNTOCREATERONLY");
    					String sub_commenterIsPetugas = ""+rs.getBoolean("SUBTOPIK.COMMENTERISPETUGAS");
    					String sub_markedAsReadAtCreator = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATRECEIVER");
    					String sub_markedAsReadAtSender = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATSENDER");
    					String sub_objNicknameSender = ""+rs.getString("SUBTOPIK.OBJNICKNAMESENDER");
    					String sub_npmhsReceiver =  ""+rs.getString("SUBTOPIK.NPMHSRECEIVER");
    					String sub_nmmhsReceiver = ""+rs.getString("SUBTOPIK.NMMHSRECEIVER");
    					String sub_objNicknameReceiver = ""+rs.getString("SUBTOPIK.OBJNICKNAMERECEIVER");
    					String sub_updtm = ""+rs.getTimestamp("SUBTOPIK.UPDTMTOPIK");
    					String tmp = topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+sub_id+"||"+sub_idTopik+"||"+sub_comment+"||"+sub_npmhsSender+"||"+sub_nmmhsSender+"||"+sub_anonymousReply+"||"+sub_shownToCreatorObly+"||"+sub_commenterIsPetugas+"||"+sub_markedAsReadAtCreator+"||"+sub_markedAsReadAtSender+"||"+sub_objNicknameSender+"||"+sub_npmhsReceiver+"||"+sub_nmmhsReceiver+"||"+sub_objNicknameReceiver+"||"+sub_updtm;
    					tmp = tmp.replace("||||", "||null||");
    					//li.add(tmp);
    					if(topik_idTopik.equals(targetTopikId)) {
    						match = true;
    						//System.out.println("match at ="+index);
    					}
    					else {
    						index++;
    					}
    				}
    				if(!match) {
    					startPointAt = startPointAt+rangeAt;
    					index = startPointAt;
    				}	
    			}while(!match);
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
    	return index;
    }	              
    
    
    
    
    public Vector getMostRecentMsg(int limitMsg, String objNickNameUsr,Vector vScopeObjNicknameOwnInbox) {
    	/*
    	 * recent msg dari inbox jadi bisa ada beberapa tipe objek
    	 */
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	boolean locked  = false;
    	int counter = 0;
    	//String thsmsPmb = Checker.getThsmsPmb();
    	//boolean ada = false;
    	try {
    		if(vScopeObjNicknameOwnInbox!=null || vScopeObjNicknameOwnInbox.size()>0) {
    			counter = vScopeObjNicknameOwnInbox.size();
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			counter = vScopeObjNicknameOwnInbox.size();
    			//sqlStmt = "select * from SUBTOPIK inner join TOPIK on SUBTOPIK.IDTOPIK=TOPIK.IDTOPIK where (SUBTOPIK.NPMHSRECEIVER like ? or ";
				/*
				 * 1. cek  msg at topik table only - blm ada stu komen pun
				 */
    			String sqlStmt = "";
    			boolean first = true;
				int j = 1;
				ListIterator li1 = vScopeObjNicknameOwnInbox.listIterator();    			
				/*
				 * topik table
				 * jika targetNpmhs=null & targetObjectNickname!= null maka tar
				 */
				sqlStmt = "select * from TOPIK left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where (TOPIK.TARGETNPMHS like ? or SUBTOPIK.NPMHSRECEIVER like ?) ";
    			first = true;
    			String tmp1 = "";
    			String tmp2 = "";
    			for(int i=0;i<counter;i++) {
    				
    				if(first) {
    					first = false;
    					tmp1 =  "TOPIK.TARGETOBJECTNICKNAME like ? ";
    					tmp2 =  "SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    					//sqlStmt = sqlStmt+"SUBTOPIK.OBJNICKNAMERECEIVER like ? or TOPIK.TARGETOBJECTNICKNAME like ? ";
    				}
    				else {
    					tmp1 =  tmp1+"or TOPIK.TARGETOBJECTNICKNAME like ? ";
    					tmp2 =  tmp2+"or SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    					//sqlStmt = sqlStmt+"or SUBTOPIK.OBJNICKNAMERECEIVER like ? or TOPIK.TARGETOBJECTNICKNAME like ? ";
    				}
			
    			}
    			tmp1 = "(("+tmp1+") and TOPIK.TARGETNPMHS is null) ";
    			tmp2 = "(("+tmp2+") and SUBTOPIK.NPMHSRECEIVER is null) ";
    			//sqlStmt=sqlStmt+") and SUBTOPIK.MARKEDASREADATRECEIVER not like ? order by SUBTOPIK.UPDTMTOPIK";
    			//sqlStmt=sqlStmt+") order by SUBTOPIK.UPDTMTOPIK";
    			sqlStmt=sqlStmt+" or "+tmp1+" or "+tmp2+" or TOPIK.NPMHSCREATOR like ? or NPMHSSENDER like ? order by SUBTOPIK.UPDTMTOPIK desc,TOPIK.UPDTMTOPIK desc limit ?";
    			stmt = con.prepareStatement(sqlStmt);
    			//System.out.println("sqlStmt2="+sqlStmt);
    			j = 1;
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			while(li1.hasNext()) {
    				String nicname = (String)li1.next();
    				stmt.setString(j++, nicname);
    			}
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			while(li1.hasNext()) {
    				String nicname = (String)li1.next();
    				stmt.setString(j++, nicname);
    			}
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			stmt.setInt(j++, limitMsg);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String topik_idTopik = ""+rs.getLong("TOPIK.IDTOPIK");
    				String topik_conten = ""+rs.getString("TOPIK.TOPIKCONTENT");
    				String topik_npmhsCreator = ""+rs.getString("TOPIK.NPMHSCREATOR");
    				String topik_nmmhsCreator = ""+rs.getString("TOPIK.NMMHSCREATOR");
    				String topik_creatorObjId = ""+rs.getString("TOPIK.CRETOROBJECTID");
    				String topik_creatorObjNickname = ""+rs.getString("TOPIK.CREATOROBJNICKNAME");
    				String topik_targetKdpst = ""+rs.getString("TOPIK.TARGETKDPST");
    				String topik_targetNpmhs = ""+rs.getString("TOPIK.TARGETNPMHS");
    				String topik_targetSmawl = ""+rs.getString("TOPIK.TARGETSMAWL");
    				String topik_targetObjId = ""+rs.getString("TOPIK.TARGETOBJEKID");
    				String topik_targetObjNickname = ""+rs.getString("TOPIK.TARGETOBJECTNICKNAME");
    				String topik_targetGroupId = ""+rs.getLong("TOPIK.TARGETGROUPID");
    				String topik_groupPwd = ""+rs.getString("TOPIK.GROUPPWD");
    				String topik_shownToGroupOnly = ""+rs.getBoolean("TOPIK.SHOWNTOGROUPONLY");
    				String topik_deletedByCreator = ""+rs.getBoolean("TOPIK.DELETEDBYCREATOR");
    				String topik_hidenAtCreator = ""+rs.getBoolean("TOPIK.HIDENATCREATOR");
    				String topik_pinedAtCreator = ""+rs.getBoolean("TOPIK.PINEDATCREATOR");
    				String topik_markedAsReadAtCreator = ""+rs.getBoolean("TOPIK.MARKEDASREADATCREATOR");
    				String topik_deletedAtTarget = ""+rs.getString("TOPIK.DELETEDATTARGET");
    				String topik_hidenAtTarget = ""+rs.getString("TOPIK.HIDENATTARGET");
    				String topik_pinedAtTarget = ""+rs.getString("TOPIK.PINEDATTARGET");
    				String topik_markedAsReasAsTarget = ""+rs.getString("TOPIK.MARKEDASREADATTARGET");
    				String topik_creatorAsAnonymous = ""+rs.getBoolean("TOPIK.CREATORASANONYMOUS");
    				String topik_creatorIsPetugas = ""+rs.getBoolean("TOPIK.CRETORISPETUGAS");
    				String topik_updtm = ""+rs.getTimestamp("TOPIK.UPDTMTOPIK");
				
				
    				String sub_id = ""+rs.getLong("SUBTOPIK.IDSUBTOPIK");
    				String sub_idTopik = ""+rs.getLong("SUBTOPIK.IDTOPIK");
    				String sub_comment = ""+rs.getString("SUBTOPIK.COMMENT");
    				String sub_npmhsSender = ""+rs.getString("SUBTOPIK.NPMHSSENDER");
    				String sub_nmmhsSender = ""+rs.getString("SUBTOPIK.NMMHSSENDER");
    				String sub_anonymousReply = ""+rs.getBoolean("SUBTOPIK.ANONYNOUSREPLY");
    				String sub_shownToCreatorObly = ""+rs.getBoolean("SUBTOPIK.SHOWNTOCREATERONLY");
    				String sub_commenterIsPetugas = ""+rs.getBoolean("SUBTOPIK.COMMENTERISPETUGAS");
    				String sub_markedAsReadAtCreator = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATRECEIVER");
    				String sub_markedAsReadAtSender = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATSENDER");
    				String sub_objNicknameSender = ""+rs.getString("SUBTOPIK.OBJNICKNAMESENDER");
    				String sub_npmhsReceiver =  ""+rs.getString("SUBTOPIK.NPMHSRECEIVER");
    				String sub_nmmhsReceiver = ""+rs.getString("SUBTOPIK.NMMHSRECEIVER");
    				String sub_objNicknameReceiver = ""+rs.getString("SUBTOPIK.OBJNICKNAMERECEIVER");
    				String sub_updtm = ""+rs.getTimestamp("SUBTOPIK.UPDTMTOPIK");
				 
				/*
				 * note: semua topik id yang udag ada komennya maka TOPIK.MARKEDASREADATTARGET contains objnameUsr
				 */
	
				//if(!topik_markedAsReasAsTarget.equalsIgnoreCase("null") && topik_markedAsReasAsTarget.contains(objNickNameUsr)) {
    				//String tmp = topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+null+"||"+null+"||"+null+"||"+null+"||"+null+"||"+null+"||"+null+"||"+null+"||"+null+"||"+null+"||"+null+"||"+null+"||"+null+"||"+null+"||"+null;
    				String tmp = topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+sub_id+"||"+sub_idTopik+"||"+sub_comment+"||"+sub_npmhsSender+"||"+sub_nmmhsSender+"||"+sub_anonymousReply+"||"+sub_shownToCreatorObly+"||"+sub_commenterIsPetugas+"||"+sub_markedAsReadAtCreator+"||"+sub_markedAsReadAtSender+"||"+sub_objNicknameSender+"||"+sub_npmhsReceiver+"||"+sub_nmmhsReceiver+"||"+sub_objNicknameReceiver+"||"+sub_updtm;
    				tmp = tmp.replace("||||", "||null||");
					li.add(tmp);
				//}
    			}	
    		/*	
    		stmt = con.prepareStatement("select * from SUBTOPIK inner join TOPIK on SUBTOPIK.IDTOPIK=TOPIK.IDTOPIK where (SUBTOPIK.NPMHSRECEIVER like ? or SUBTOPIK.OBJNICKNAMERECEIVER like ?)  order by SUBTOPIK.UPDTMTOPIK desc");
    		stmt.setString(1, "%"+this.operatorNpm+"%");
    		stmt.setString(2, "%"+objNickNameUsr+"%");
    		
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String topik_idTopik = ""+rs.getLong("TOPIK.IDTOPIK");
    			String topik_conten = ""+rs.getString("TOPIK.TOPIKCONTENT");
    			String topik_npmhsCreator = ""+rs.getString("TOPIK.NPMHSCREATOR");
				String topik_nmmhsCreator = ""+rs.getString("TOPIK.NMMHSCREATOR");
    			String topik_creatorObjId = ""+rs.getString("TOPIK.CRETOROBJECTID");
    			String topik_creatorObjNickname = ""+rs.getString("TOPIK.CREATOROBJNICKNAME");
    			String topik_targetKdpst = ""+rs.getString("TOPIK.TARGETKDPST");
    			String topik_targetNpmhs = ""+rs.getString("TOPIK.TARGETNPMHS");
    			String topik_targetSmawl = ""+rs.getString("TOPIK.TARGETSMAWL");
    			String topik_targetObjId = ""+rs.getString("TOPIK.TARGETOBJEKID");
    			String topik_targetObjNickname = ""+rs.getString("TOPIK.TARGETOBJECTNICKNAME");
				String topik_targetGroupId = ""+rs.getLong("TOPIK.TARGETGROUPID");
    			String topik_groupPwd = ""+rs.getString("TOPIK.GROUPPWD");
    			String topik_shownToGroupOnly = ""+rs.getBoolean("TOPIK.SHOWNTOGROUPONLY");
    			String topik_deletedByCreator = ""+rs.getBoolean("TOPIK.DELETEDBYCREATOR");
    			String topik_hidenAtCreator = ""+rs.getBoolean("TOPIK.HIDENATCREATOR");
    			String topik_pinedAtCreator = ""+rs.getBoolean("TOPIK.PINEDATCREATOR");
    			String topik_markedAsReadAtCreator = ""+rs.getBoolean("TOPIK.MARKEDASREADATCREATOR");
    			String topik_deletedAtTarget = ""+rs.getString("TOPIK.DELETEDATTARGET");
    			String topik_hidenAtTarget = ""+rs.getString("TOPIK.HIDENATTARGET");
    			String topik_pinedAtTarget = ""+rs.getString("TOPIK.PINEDATTARGET");
    			String topik_markedAsReasAsTarget = ""+rs.getString("TOPIK.MARKEDASREADATTARGET");
    			String topik_creatorAsAnonymous = ""+rs.getBoolean("TOPIK.CREATORASANONYMOUS");
    			String topik_creatorIsPetugas = ""+rs.getBoolean("TOPIK.CRETORISPETUGAS");
    			String topik_updtm = ""+rs.getTimestamp("TOPIK.UPDTMTOPIK");
    			
    			String sub_id = ""+rs.getLong("SUBTOPIK.IDSUBTOPIK");
    			String sub_idTopik = ""+rs.getLong("SUBTOPIK.IDTOPIK");
    			String sub_comment = ""+rs.getString("SUBTOPIK.COMMENT");
    			String sub_npmhsSender = ""+rs.getString("SUBTOPIK.NPMHSSENDER");
    			String sub_nmmhsSender = ""+rs.getString("SUBTOPIK.NMMHSSENDER");
    			String sub_anonymousReply = ""+rs.getBoolean("SUBTOPIK.ANONYNOUSREPLY");
    			String sub_shownToCreatorObly = ""+rs.getBoolean("SUBTOPIK.SHOWNTOCREATERONLY");
    			String sub_commenterIsPetugas = ""+rs.getBoolean("SUBTOPIK.COMMENTERISPETUGAS");
    			String sub_markedAsReadAtCreator = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATRECEIVER");
    			String sub_markedAsReadAtSender = ""+rs.getBoolean("SUBTOPIK.MARKEDASREADATSENDER");
    			String sub_objNicknameSender = ""+rs.getString("SUBTOPIK.OBJNICKNAMESENDER");
    			String sub_npmhsReceiver =  ""+rs.getString("SUBTOPIK.NPMHSRECEIVER");
    			String sub_nmmhsReceiver = ""+rs.getString("SUBTOPIK.NMMHSRECEIVER");
    			String sub_objNicknameReceiver = ""+rs.getString("SUBTOPIK.OBJNICKNAMERECEIVER");
    			String sub_updtm = ""+rs.getTimestamp("SUBTOPIK.UPDTMTOPIK");
    			
    			li.add(topik_idTopik+"||"+topik_conten+"||"+topik_npmhsCreator+"||"+topik_nmmhsCreator+"||"+topik_creatorObjId+"||"+topik_creatorObjNickname+"||"+topik_targetKdpst+"||"+topik_targetNpmhs+"||"+topik_targetSmawl+"||"+topik_targetObjId+"||"+topik_targetObjNickname+"||"+topik_targetGroupId+"||"+topik_groupPwd+"||"+topik_shownToGroupOnly+"||"+topik_deletedByCreator+"||"+topik_hidenAtCreator+"||"+topik_pinedAtCreator+"||"+topik_markedAsReadAtCreator+"||"+topik_deletedAtTarget+"||"+topik_hidenAtTarget+"||"+topik_pinedAtTarget+"||"+topik_markedAsReasAsTarget+"||"+topik_creatorAsAnonymous+"||"+topik_creatorIsPetugas+"||"+topik_updtm+"||"+sub_id+"||"+sub_idTopik+"||"+sub_comment+"||"+sub_npmhsSender+"||"+sub_nmmhsSender+"||"+sub_anonymousReply+"||"+sub_shownToCreatorObly+"||"+sub_commenterIsPetugas+"||"+sub_markedAsReadAtCreator+"||"+sub_markedAsReadAtSender+"||"+sub_objNicknameSender+"||"+sub_npmhsReceiver+"||"+sub_nmmhsReceiver+"||"+sub_objNicknameReceiver+"||"+sub_updtm);
    			
    			//ada = true;
    		}
    		*/
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

    
    
    public boolean gotNewMsgDeprecated(Vector vScopeObjNicknameOwnInbox) {
    	/*
    	 * get new msg from table NEW_MSG_NOTIFICATION_OBJECTNICKNAME & NEW_MSG_NOTIFICATION_NPM
    	 * vScopeObjNicknameAllowedToMonitor = null or size<1 maka perorangan tidak ada monitoring right
    	 */
    	boolean ada = false;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		if(vScopeObjNicknameOwnInbox!=null || vScopeObjNicknameOwnInbox.size()>0) {
    			//user yg punya hak monitoring
    			int counter = vScopeObjNicknameOwnInbox.size();
    			String sqlStmt = "select * from NEW_MSG_NOTIFICATION_OBJECTNICKNAME where GOT_MSG=? and (";
    			boolean first = true;
    			for(int i=0;i<counter;i++) {
    				if(first) {
    					first = false;
    					sqlStmt = sqlStmt+"OBJNICKNAME=? ";
    				}
    				else {
    					sqlStmt = sqlStmt+"or OBJNICKNAME=? ";
    				}
    				
    			}
    			sqlStmt=sqlStmt+")";
    			stmt = con.prepareStatement(sqlStmt);
    			int j = 1;
    			ListIterator li = vScopeObjNicknameOwnInbox.listIterator();
    			stmt.setBoolean(j++,true);
    			while(li.hasNext()) {
    				String nicname = (String)li.next();
    				//System.out.println("nicname="+nicname);
    				stmt.setString(j++, nicname);
    			}
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				ada = true;
    			}
    		}
			//kalo udah true ngga usah ngecek ke table berikutnya karena otomatis dicek di tahap berikut
			if(!ada) {
				stmt = con.prepareStatement("select * from NEW_MSG_NOTIFICATION_NPM where NPMHS=? and GOT_MSG=?");
				stmt.setString(1, this.operatorNpm);
				stmt.setBoolean(2, true);
				rs = stmt.executeQuery();
				if(rs.next()) {
					ada = true;
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
    	return ada;
    }	           

    /*
     * depricated yang dipake v3
     */
    public boolean gotNewMsg_v1(Vector vScopeObjNicknameOwnInbox) {
    	/*
    	 * get new msg from table NEW_MSG_NOTIFICATION_OBJECTNICKNAME & NEW_MSG_NOTIFICATION_NPM
    	 * vScopeObjNicknameAllowedToMonitor = null or size<1 maka perorangan tidak ada monitoring right
    	 */
    	boolean ada = false;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		if(vScopeObjNicknameOwnInbox!=null || vScopeObjNicknameOwnInbox.size()>0) {
    			//user yg punya hak monitoring
    			int counter = vScopeObjNicknameOwnInbox.size();
    			
    			
    			String sqlStmt = "";
    			boolean first = true;
				int j = 1;
				ListIterator li1 = vScopeObjNicknameOwnInbox.listIterator();    
			//	select * from TOPIK  left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where (TOPIK.TARGETNPMHS like '%0000000000000%' and (TOPIK.MARKEDASREADATTARGET not like '%0000000000000%' or TOPIK.MARKEDASREADATTARGET is null))or (SUBTOPIK.NPMHSRECEIVER like '%0000000000000%' and SUBTOPIK.MARKEDASREADATRECEIVER=false)
			//			or (TOPIK.TARGETOBJECTNICKNAME like '%OPERATOR BAA%' and TOPIK.TARGETNPMHS is null and (TOPIK.MARKEDASREADATTARGET not like '%0000000000000%' or TOPIK.MARKEDASREADATTARGET is null)) or (SUBTOPIK.OBJNICKNAMERECEIVER like '%OPERATOR BAA%' and SUBTOPIK.NPMHSRECEIVER is null and SUBTOPIK.MARKEDASREADATRECEIVER=false)
				sqlStmt = "select * from TOPIK left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where (TOPIK.TARGETNPMHS like ? and (TOPIK.MARKEDASREADATTARGET not like ? or TOPIK.MARKEDASREADATTARGET is null)) or (SUBTOPIK.NPMHSRECEIVER like ? and SUBTOPIK.MARKEDASREADATRECEIVER=false) ";
    			first = true;
    			String tmp1a = "";
    			String tmp1 = "";
    			String tmp2 = "";
    			for(int i=0;i<counter;i++) {		
    				if(first) {
    					first = false;
    					tmp1a = "TOPIK.MARKEDASREADATTARGET not like ? ";
    					tmp1 =  "TOPIK.TARGETOBJECTNICKNAME like ? ";
    					tmp2 =  "SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    					//sqlStmt = sqlStmt+"SUBTOPIK.OBJNICKNAMERECEIVER like ? or TOPIK.TARGETOBJECTNICKNAME like ? ";
    				}
    				else {
    					tmp1a =  tmp1a+"and TOPIK.MARKEDASREADATTARGET not like ?";
    					tmp1 =  tmp1+"or TOPIK.TARGETOBJECTNICKNAME like ? ";
    					tmp2 =  tmp2+"or SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    					//sqlStmt = sqlStmt+"or SUBTOPIK.OBJNICKNAMERECEIVER like ? or TOPIK.TARGETOBJECTNICKNAME like ? ";
    				}
    			}
    			//tmp1 = "(("+tmp1+") and TOPIK.TARGETNPMHS is null  and ((TOPIK.MARKEDASREADATTARGET not like ? and TOPIK.MARKEDASREADATTARGET not like ?) or TOPIK.MARKEDASREADATTARGET is null)) ";
    			tmp1 = "(("+tmp1+") and TOPIK.TARGETNPMHS is null  and (("+tmp1a+" and TOPIK.MARKEDASREADATTARGET not like ?) or TOPIK.MARKEDASREADATTARGET is null)) ";
    			tmp2 = "(("+tmp2+") and SUBTOPIK.NPMHSRECEIVER is null and SUBTOPIK.MARKEDASREADATRECEIVER=false) ";
    			sqlStmt=sqlStmt+" or "+tmp1+" or "+tmp2+" order by SUBTOPIK.UPDTMTOPIK desc,TOPIK.UPDTMTOPIK desc;";
    			stmt = con.prepareStatement(sqlStmt);
    			//System.out.println("got msg = "+sqlStmt);
    			j = 1;
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			String nicname="EmptyNickNmaeSbgDefaultValue";
    			while(li1.hasNext()) { // isi tmp1
    				nicname = (String)li1.next();
    				stmt.setString(j++, nicname);
    			}
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			while(li1.hasNext()) { // isi tmp1
    				nicname = (String)li1.next();
    				stmt.setString(j++, nicname);
    			}
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			//stmt.setString(j++, "%"+nicname+"%");
    			// isi tmp2
    			nicname = null;
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			while(li1.hasNext()) { 
    				nicname = (String)li1.next();
    				stmt.setString(j++, nicname);
    			}
    			//stmt.setString(j++, "%"+this.operatorNpm+"%");
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				ada = true;
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
    	return ada;
    }	           
    
    /*
     * depricated yang dipake v3
     */
    public boolean gotNewMsg_v2(String objNickNameUsr,Vector vScopeObjNicknameOwnInbox) {
    	/*
    	 * harus sama sama fungsi getUnreadMsgInboxWithinRange beda di limit 1 & return boolean
    	 */
    	
    	//range_qtt++;//ditambahin krn untuk ngecek apa masih ada msg di atar range_qtt
    	//System.out.println("limit "+start+","+range_qtt);
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	boolean locked  = false, ada_nu_msg = false;
    	String sqlStmt=null;
    	int counter = 0;
    	//String thsmsPmb = Checker.getThsmsPmb();
    	//boolean ada = false;
    	try {
    		if(vScopeObjNicknameOwnInbox!=null || vScopeObjNicknameOwnInbox.size()>0) {
    			counter = vScopeObjNicknameOwnInbox.size();
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			
    			counter = vScopeObjNicknameOwnInbox.size();
    			sqlStmt = "";
    			boolean first = true;
				int j = 1;
				ListIterator li1 = vScopeObjNicknameOwnInbox.listIterator();    
				sqlStmt = "select * from TOPIK left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where (TOPIK.TARGETNPMHS like ? and (TOPIK.MARKEDASREADATTARGET not like ? or TOPIK.MARKEDASREADATTARGET is null)) or (SUBTOPIK.NPMHSRECEIVER like ? and SUBTOPIK.MARKEDASREADATRECEIVER=false) ";//NPM ONLY PART
    			first = true;
    			String tmp1 = "",tmp3="";
    			String tmp2 = "";
    			String tmp1a = "";
    			for(int i=0;i<counter;i++) {		
    				if(first) {
    					first = false;
    					tmp1 =  "TOPIK.TARGETOBJECTNICKNAME like ? ";
    					//tmp1 =  "(TOPIK.TARGETOBJECTNICKNAME like ? and TOPIK.MARKEDASREADATTARGET not like ?) ";
    					tmp1a=  "TOPIK.MARKEDASREADATTARGET not like ? ";
    					tmp2 =  "SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    					tmp3 =  "TOPIK.CREATOROBJNICKNAME not like ? and SUBTOPIK.OBJNICKNAMESENDER not like ? ";
    				}
    				else {
    					tmp1 =  tmp1+"or TOPIK.TARGETOBJECTNICKNAME like ? ";
    					tmp1a=  tmp1a+" and TOPIK.MARKEDASREADATTARGET not like ? ";
    					tmp2 =  tmp2+"or SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    					tmp3 =  tmp3+" and TOPIK.CREATOROBJNICKNAME not like ? and SUBTOPIK.OBJNICKNAMESENDER not like ? ";
    				}
    			}
    			//tmp1&1a = msg berdasarkan obj nicknam dan blum dibaca @ topik
    			//tmp2&3 = msg berdasarkan obj nicknam dan blum dibaca @ subtopik
    			/*
    			 * tmp1 updated 14juni2014 (1) tmp1 nya doang 1 brs dibawah ini yg diupdate
    			 */
    			//tmp1 = "(("+tmp1+") and TOPIK.TARGETNPMHS is null  and (("+tmp1a+" and TOPIK.MARKEDASREADATTARGET not like ?) or TOPIK.MARKEDASREADATTARGET is null)) ";
    			tmp1 = "(("+tmp1+") and TOPIK.TARGETNPMHS is null  and (("+tmp1a+" and TOPIK.MARKEDASREADATTARGET not like ?) or TOPIK.MARKEDASREADATTARGET is null  and TOPIK.NPMHSCREATOR not like ? )) ";
    			//tmp1 = "(("+tmp1+") and ("+tmp1a+") and TOPIK.TARGETNPMHS is null and  TOPIK.MARKEDASREADATTARGET not like ?) ";
    			tmp2 = "(("+tmp2+") and SUBTOPIK.NPMHSRECEIVER is null and SUBTOPIK.MARKEDASREADATRECEIVER=false) ";
    			sqlStmt=sqlStmt+" or "+tmp1+" or "+tmp2+" and TOPIK.NPMHSCREATOR not like ? and SUBTOPIK.NPMHSSENDER NOT like ? and "+tmp3+" order by SUBTOPIK.UPDTMTOPIK desc,TOPIK.UPDTMTOPIK desc limit 1";
    			stmt = con.prepareStatement(sqlStmt);
    			//System.out.println("sqlStmtgetUnreadMsgInbox="+sqlStmt);
    			j = 1;
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			String nicname="EmptyNickNmaeSbgDefaultValue";
    			while(li1.hasNext()) { // isi tmp1
    				nicname = (String)li1.next();
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, nicname);
    				//System.out.println(j+"%"+nicname+"%");
    			}
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			while(li1.hasNext()) { //isi tmp1a
    				nicname = (String)li1.next();
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, nicname);
    			}
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			/*
    			 * tmp1 updated 14juni2014 (2)
    			 */
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			/*
    			 * end tmp1 updated 14juni2014 (2)
    			 */
    			nicname = null;
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			while(li1.hasNext()) {//isi tmp2 
    				nicname = (String)li1.next();
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, "%"+nicname+"%");
    			}
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			while(li1.hasNext()) {//isi tmp3 
    				nicname = (String)li1.next();
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, nicname);
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, nicname);
    			}
    			//stmt.setInt(j++, start);
    			//stmt.setInt(j++, range_qtt);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				ada_nu_msg = true;
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
    	return ada_nu_msg;
  }
    
    public boolean gotNewMsg_v3(String objNickNameUsr,Vector vScopeObjNicknameOwnInbox) {
    	/*
    	 * harus sama sama fungsi getUnreadMsgInboxWithinRange beda di limit 1 & return boolean
    	 */
    	
    	//range_qtt++;//ditambahin krn untuk ngecek apa masih ada msg di atar range_qtt
    	//System.out.println("limit "+start+","+range_qtt);
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	boolean locked  = false, ada_nu_msg = false;
    	String sqlStmt=null;
    	int counter = 0;
    	//String thsmsPmb = Checker.getThsmsPmb();
    	//boolean ada = false;
    	try {
    		if(vScopeObjNicknameOwnInbox!=null || vScopeObjNicknameOwnInbox.size()>0) {
    			counter = vScopeObjNicknameOwnInbox.size();
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			
    			counter = vScopeObjNicknameOwnInbox.size();
    			sqlStmt = "";
    			boolean first = true;
				int j = 1;
				ListIterator li1 = vScopeObjNicknameOwnInbox.listIterator();    
				sqlStmt = "select * from TOPIK left outer join SUBTOPIK on TOPIK.IDTOPIK=SUBTOPIK.IDTOPIK where (TOPIK.TARGETNPMHS like ? and (TOPIK.MARKEDASREADATTARGET not like ? or TOPIK.MARKEDASREADATTARGET is null)) or (SUBTOPIK.NPMHSRECEIVER like ? and SUBTOPIK.MARKEDASREADATRECEIVER=false) ";//NPM ONLY PART
    			first = true;
    			String tmp1 = "",tmp3="";
    			String tmp2 = "";
    			String tmp1a = "";
    			for(int i=0;i<counter;i++) {		
    				if(first) {
    					first = false;
    					tmp1 =  "TOPIK.TARGETOBJECTNICKNAME like ? ";
    					//tmp1 =  "(TOPIK.TARGETOBJECTNICKNAME like ? and TOPIK.MARKEDASREADATTARGET not like ?) ";
    					tmp1a=  "TOPIK.MARKEDASREADATTARGET not like ? ";
    					tmp2 =  "SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    					tmp3 =  "TOPIK.CREATOROBJNICKNAME not like ? and SUBTOPIK.OBJNICKNAMESENDER not like ? ";
    				}
    				else {
    					tmp1 =  tmp1+"or TOPIK.TARGETOBJECTNICKNAME like ? ";
    					tmp1a=  tmp1a+" and TOPIK.MARKEDASREADATTARGET not like ? ";
    					tmp2 =  tmp2+"or SUBTOPIK.OBJNICKNAMERECEIVER like ? ";
    					tmp3 =  tmp3+" and TOPIK.CREATOROBJNICKNAME not like ? and SUBTOPIK.OBJNICKNAMESENDER not like ? ";
    				}
    			}
    			//tmp1&1a = msg berdasarkan obj nicknam dan blum dibaca @ topik
    			//tmp2&3 = msg berdasarkan obj nicknam dan blum dibaca @ subtopik
    			/*
    			 * tmp1 updated 14juni2014 (1) tmp1 nya doang 1 brs dibawah ini yg diupdate
    			 */
    			//tmp1 = "(("+tmp1+") and TOPIK.TARGETNPMHS is null  and (("+tmp1a+" and TOPIK.MARKEDASREADATTARGET not like ?) or TOPIK.MARKEDASREADATTARGET is null)) ";
    			tmp1 = "(("+tmp1+") and TOPIK.TARGETNPMHS is null  and (("+tmp1a+" and TOPIK.MARKEDASREADATTARGET not like ?) or TOPIK.MARKEDASREADATTARGET is null)  and (TOPIK.NPMHSCREATOR is null or TOPIK.NPMHSCREATOR not like ? )) ";
    			//tmp1 = "(("+tmp1+") and ("+tmp1a+") and TOPIK.TARGETNPMHS is null and  TOPIK.MARKEDASREADATTARGET not like ?) ";
    			tmp2 = "(("+tmp2+") and SUBTOPIK.NPMHSRECEIVER is null and SUBTOPIK.MARKEDASREADATRECEIVER=false) ";
    			sqlStmt=sqlStmt+" or "+tmp1+" or "+tmp2+" and TOPIK.NPMHSCREATOR not like ? and SUBTOPIK.NPMHSSENDER NOT like ? and "+tmp3+" order by SUBTOPIK.UPDTMTOPIK desc,TOPIK.UPDTMTOPIK desc limit 1";
    			stmt = con.prepareStatement(sqlStmt);
    			//System.out.println("sqlStmtgetUnreadMsgInbox="+sqlStmt);
    			j = 1;
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			String nicname="EmptyNickNmaeSbgDefaultValue";
    			while(li1.hasNext()) { // isi tmp1
    				nicname = (String)li1.next();
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, nicname);
    				//System.out.println(j+"%"+nicname+"%");
    			}
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			while(li1.hasNext()) { //isi tmp1a
    				nicname = (String)li1.next();
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, nicname);
    			}
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			/*
    			 * tmp1 updated 14juni2014 (2)
    			 */
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			/*
    			 * end tmp1 updated 14juni2014 (2)
    			 */
    			nicname = null;
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			while(li1.hasNext()) {//isi tmp2 
    				nicname = (String)li1.next();
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, "%"+nicname+"%");
    			}
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			//System.out.println(j+".%"+this.operatorNpm+"%");
    			stmt.setString(j++, "%"+this.operatorNpm+"%");
    			li1 = vScopeObjNicknameOwnInbox.listIterator();
    			while(li1.hasNext()) {//isi tmp3 
    				nicname = (String)li1.next();
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, nicname);
    				//System.out.println(j+"."+nicname);
    				stmt.setString(j++, nicname);
    			}
    			//stmt.setInt(j++, start);
    			//stmt.setInt(j++, range_qtt);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				ada_nu_msg = true;
    			}	
    		}
    		else {
    			//berarti kalo vsize=0, value = own berarti menggunakan npm
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
    	return ada_nu_msg;
  }	       
    
    
  

    public boolean gotMonitoredNewMsg(String usrObjNickname, Vector vScopeObjNicknameAllowedToMonitor) {
    	/*
    	 * get new msg from table NEW_MSG_NOTIFICATION_OBJECTNICKNAME & NEW_MSG_NOTIFICATION_NPM
    	 * vScopeObjNicknameAllowedToMonitor = null or size<1 maka perorangan tidak ada monitoring right
    	 */
    	
    	//hapus own Objnickname from vScopeObjNicknameAllowedToMonitor karena masuk ke new own msg
    	if(vScopeObjNicknameAllowedToMonitor!=null && vScopeObjNicknameAllowedToMonitor.size()>0) {
    		ListIterator li = 	vScopeObjNicknameAllowedToMonitor.listIterator();
    		boolean match = false;
    		while(li.hasNext() && !match) {
    			String nikname = (String)li.next();
    			if(nikname.equalsIgnoreCase(usrObjNickname)) {
    				match = true;
    				li.remove();
    			}
    		}
    	}
    	
    	 
    	boolean ada = false;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		if(vScopeObjNicknameAllowedToMonitor!=null && vScopeObjNicknameAllowedToMonitor.size()>0) {
    			//user yg punya hak monitoring
    			int counter = vScopeObjNicknameAllowedToMonitor.size();
    			String sqlStmt = "select * from NEW_MSG_NOTIFICATION_OBJECTNICKNAME where GOT_MSG=? and (";
    			boolean first = true;
    			for(int i=0;i<counter;i++) {
    				if(first) {
    					first = false;
    					sqlStmt = sqlStmt+"OBJNICKNAME=? ";
    				}
    				else {
    					sqlStmt = sqlStmt+"or OBJNICKNAME=? ";
    				}
    				
    			}
    			sqlStmt=sqlStmt+")";
    			stmt = con.prepareStatement(sqlStmt);
    			//System.out.println("stmt = "+stmt);
    			int j = 1;
    			ListIterator li = vScopeObjNicknameAllowedToMonitor.listIterator();
    			stmt.setBoolean(j++,true);
    			while(li.hasNext()) {
    				String nicname = (String)li.next();
    				//System.out.println("nicname="+nicname);
    				stmt.setString(j++, nicname);
    			}
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				ada = true;
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
    	return ada;
    }	           
    
    public Vector gotListAndSelectedKurikulum(String kdpst,String npmhs,String smawl) {
    	Vector vList = new Vector();
    	ListIterator lil = vList.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//get list kurikulum aktif
    		stmt = con.prepareStatement("select * from KRKLM where KDPSTKRKLM=? and STKURKRKLM=?");
    		stmt.setString(1,kdpst);
    		stmt.setString(2,"A");
    		rs = stmt.executeQuery();
    		Vector vAktif = new Vector();
    		ListIterator lia = vAktif.listIterator();
    		while(rs.next()) {
    			String idkur = ""+rs.getLong("IDKURKRKLM");
    			String nmkur = ""+rs.getString("NMKURKRKLM");
    			nmkur = nmkur.replace(",", "tandaKoma");
    			nmkur = nmkur.replace("&", "tandaDan");
    			nmkur = nmkur.replace("_", "tandaGb");
    			String skstt = ""+rs.getLong("SKSTTKRKLM");
    			String smstt = ""+rs.getLong("SMSTTKRKLM");
    			lia.add(idkur+"__"+nmkur+"__"+skstt+"__"+smstt);
    			//System.out.println("kurikulum aktif = "+idkur+"__"+nmkur+"__"+skstt+"__"+smstt);
    		}
    		//System.out.println("vsize kur aktif = "+vAktif.size());
    		//get selected kurikulum - cek berdasar npm
    		stmt = con.prepareStatement("select * from KRKLM where KDPSTKRKLM=? and TARGTKRKLM like ?");
    		stmt.setString(1,kdpst);
    		stmt.setString(2,"%"+npmhs+"%");
    		rs = stmt.executeQuery();
    		Vector vSelected = new Vector();
    		ListIterator li = vSelected.listIterator();
    		while(rs.next()) {
    			String idkur = ""+rs.getLong("IDKURKRKLM");
    			String nmkur = ""+rs.getString("NMKURKRKLM");
    			nmkur = nmkur.replace(",", "tandaKoma");
    			nmkur = nmkur.replace("&", "tandaDan");
    			nmkur = nmkur.replace("_", "tandaGb");
    			String skstt = ""+rs.getLong("SKSTTKRKLM");
    			String smstt = ""+rs.getLong("SMSTTKRKLM");
    			li.add(idkur+"__"+nmkur+"__"+skstt+"__"+smstt);
    			//System.out.println("kurikulum mahasiswa berdasar npm = "+idkur+"__"+nmkur+"__"+skstt+"__"+smstt);
    		}
    		
    		if(vSelected.size()<1) {
    			//belum ada penetapan berdasar npm maka cek berdasar smawl
    			stmt.setString(1,kdpst);
        		stmt.setString(2,"%"+smawl+"%");
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			String idkur = ""+rs.getLong("IDKURKRKLM");
        			String nmkur = ""+rs.getString("NMKURKRKLM");
        			nmkur = nmkur.replace(",", "tandaKoma");
        			nmkur = nmkur.replace("&", "tandaDan");
        			nmkur = nmkur.replace("_", "tandaGb");
        			String skstt = ""+rs.getLong("SKSTTKRKLM");
        			String smstt = ""+rs.getLong("SMSTTKRKLM");
        			li.add(idkur+"__"+nmkur+"__"+skstt+"__"+smstt);
        			//System.out.println("kurikulum mhs berdasar smawl="+idkur+"__"+nmkur+"__"+skstt+"__"+smstt);
        		}
    		}
    		if(vSelected.size()<1) {
    			//belum ada penetapan berdasarkan npm dan smawl
    			if(vAktif.size()<0) {
        			//ada penetapan tapi ngga ada list kurikulum aktif, kirim blank
        			lil.add("0__TIDAK ADA KURIKULUM AKTIF__0__0__selected");
        		}
    			else {
    				lia = vAktif.listIterator();
    				lil.add("0__PILIH KURIKULUM__0__0__selected");
        			while(lia.hasNext()) {
        				String brsA = (String)lia.next();
            			StringTokenizer st = new StringTokenizer(brsA,"__");
        				String idkurA = st.nextToken();
                		String nmkurA = st.nextToken();
                		nmkurA = nmkurA.replace(",", "tandaKoma");
            			nmkurA = nmkurA.replace("&", "tandaDan");
            			nmkurA = nmkurA.replace("_", "tandaGb");
                		String sksttA = st.nextToken();
                		String smsttA = st.nextToken();
                		lil.add(idkurA+"__"+nmkurA+"__"+sksttA+"__"+smsttA+"__notselected");
                	}		
    			}
    		}
    		else {
    			//sudah ada penetapan /selected
    			li = vSelected.listIterator();
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"__");
        		String idkur = st.nextToken();
        		String nmkur = st.nextToken();
        		String skstt = st.nextToken();
        		String smstt = st.nextToken();
        		if(vAktif.size()<1) {
        			//ada penetapan tapi ngga ada list kurikulum aktif, kirim blank
        			lil.add("0__TIDAK ADA KURIKULUM AKTIF__0__0__selected");
        		}
        		else {
        			//cek pa ada match
        			boolean match = false;
        			lia = vAktif.listIterator();
        			while(lia.hasNext()) {
        				String brsA = (String)lia.next();
        				//System.out.println("brsA="+brsA);
            			st = new StringTokenizer(brsA,"__");
        				String idkurA = st.nextToken();
                		String nmkurA = st.nextToken();
                		nmkurA = nmkurA.replace(",", "tandaKoma");
            			nmkurA = nmkurA.replace("&", "tandaDan");
            			nmkurA = nmkurA.replace("_", "tandaGb");
                		String sksttA = st.nextToken();
                		String smsttA = st.nextToken();
                		if(idkur.equalsIgnoreCase(idkurA)) {
                			match = true;
                			lil.add(idkurA+"__"+nmkurA+"__"+sksttA+"__"+smsttA+"__selected");
                		}
                		else {
                			lil.add(idkurA+"__"+nmkurA+"__"+sksttA+"__"+smsttA+"__notselected");
                		}
        			}
        			if(!match) {
        				if(vAktif.size()>1) {
        					lil.add("0__PILIH KURIKULUM__0__0__selected");
        				}
        				else {
        					lil = vList.listIterator();
        					String brs2 = (String)lil.next();
        					//System.out.println("brs2="+brs2);
        					brs2 = brs2.replace("notselected", "selected");
        					lil.set(brs2);
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
    	return vList;
    }	           
    
    
    public Vector getMakulYangAdaMakulSetara(String kdpst, String npmhs ) {
    	String idkur = getIndividualKurikulum(kdpst, npmhs);
    	Vector vSetara = null;
    	Vector v = null;
    	if(idkur!=null && !Checker.isStringNullOrEmpty(idkur)) {
    		v = getListMatakuliahDalamKurikulum(kdpst,idkur);
    		 
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select ID_MAKUL_SAMA from MAKUL where IDKMKMAKUL=?");
        		if(v!=null && v.size()>0) {
        			vSetara = new Vector();
        			ListIterator lis = vSetara.listIterator();
        			ListIterator li = v.listIterator();
        			while(li.hasNext()) {
        				//String brs = (String)li.next();
        				String idkmk = (String)li.next();
        				String kdkmk = (String)li.next();
        				if(Checker.isStringNullOrEmpty(kdkmk)) {
        					kdkmk="null";
        				}
        				String nakmk = (String)li.next();
        				if(Checker.isStringNullOrEmpty(nakmk)) {
        					nakmk="null";
        				}
        				String skstm = (String)li.next();
        				if(Checker.isStringNullOrEmpty(skstm)) {
        					skstm="null";
        				}
        				String skspr = (String)li.next();
        				if(Checker.isStringNullOrEmpty(skspr)) {
        					skspr="null";
        				}
        				String skslp = (String)li.next();
        				if(Checker.isStringNullOrEmpty(skslp)) {
        					skslp="null";
        				}
        				String kdwpl = (String)li.next();
        				if(Checker.isStringNullOrEmpty(kdwpl)) {
        					kdwpl="null";
        				}
        				String jenis = (String)li.next();
        				if(Checker.isStringNullOrEmpty(jenis)) {
        					jenis="null";
        				}
        				String stkmk = (String)li.next();
        				if(Checker.isStringNullOrEmpty(stkmk)) {
        					stkmk="null";
        				}
        				String nodos = (String)li.next();
        				if(Checker.isStringNullOrEmpty(nodos)) {
        					nodos="null";
        				}
        				String semes = (String)li.next();
        				stmt.setLong(1, Long.parseLong(idkmk));
        				rs = stmt.executeQuery();
        				if(rs.next()) {
        					String tkn_idkmk_sama = rs.getString(1);
        					if(tkn_idkmk_sama!=null && !Checker.isStringNullOrEmpty(tkn_idkmk_sama)) {
        						//System.out.println("add="+idkmk+"#"+kdkmk+"#"+nakmk+"#"+skstm+"#"+skspr+"#"+skslp+"#"+kdwpl+"#"+jenis+"#"+stkmk+"#"+nodos+"#"+semes+"#"+tkn_idkmk_sama);
            					lis.add(idkmk+"#"+kdkmk+"#"+nakmk+"#"+skstm+"#"+skspr+"#"+skslp+"#"+kdwpl+"#"+jenis+"#"+stkmk+"#"+nodos+"#"+semes+"#"+tkn_idkmk_sama);	
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
    		
    	}
    	return vSetara;

    }
    
    public Vector getMakulSetaraYgDibuka(String kdpstTargetNpmhs, String targetNpmhs, String targetThsms, Vector vSetara) {
    	Vector v = new Vector();
    	String kodeKmpDomisiliNpm = Getter.getDomisiliKampus(targetNpmhs);
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
       		con = ds.getConnection();
       		/*
       		 * get seluruh kelas CP yang ngga di cancel
       		 */
       		
       		ListIterator li = v.listIterator();
       		stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where IDKMK=? and THSMS=? and CANCELED=? order by KDPST");
       		ListIterator litmp = vSetara.listIterator();
			while(litmp.hasNext()) {
				String temp = (String)litmp.next();
				StringTokenizer st = new StringTokenizer(temp,"#");
				String idkmk_ori = st.nextToken();
				String kdkmk_ori = st.nextToken();
				String nakmk_ori = st.nextToken();
				String skstm = st.nextToken();
				String skspr = st.nextToken();
				String skslp = st.nextToken();
				String kdwpl = st.nextToken();
				String jenis = st.nextToken();
				String stkmk = st.nextToken();
				String nodos = st.nextToken();
				String semes = st.nextToken();
				String tkn_idkmk_sama = st.nextToken();
		
				
				
				st = new StringTokenizer(tkn_idkmk_sama,",");
				while(st.hasMoreTokens()) {
					String idkmk = st.nextToken();
					stmt.setLong(1, Long.parseLong(idkmk));
					stmt.setString(2, targetThsms);
					stmt.setBoolean(3, false);
					rs = stmt.executeQuery();
					while(rs.next()) {
						String idkmktmp = ""+rs.getLong("IDKMK");
    	    			String kdpsttmp = ""+rs.getString("KDPST");
    	    			String shift = ""+rs.getString("SHIFT");
            			String norutKelasParalel = ""+rs.getString("NORUT_KELAS_PARALEL");
        				String currStatus = ""+rs.getString("CURR_AVAIL_STATUS");
            			
        				String npmdos = ""+rs.getString("NPMDOS");
        				String npmasdos = ""+rs.getString("NPMASDOS");
        				String canceled = ""+rs.getBoolean("CANCELED");
        				String kodeKelas = ""+rs.getString("KODE_KELAS");
        				String kodeRuang = ""+rs.getString("KODE_RUANG");
        				String kodeGedung = ""+rs.getString("KODE_GEDUNG");
        				String kodeKampus = ""+rs.getString("KODE_KAMPUS");
        				String tknDayTime = ""+rs.getString("TKN_HARI_TIME");
        				String nmmdos = ""+rs.getString("NMMDOS");
        				nmmdos = nmmdos.replace(",", "tandaKoma");
        				String nmmasdos = ""+rs.getString("NMMASDOS");
        				nmmasdos = nmmasdos.replace(",", "tandaKoma");
        				String enrolled = ""+rs.getString("ENROLLED");
        				String maxEnrolled = ""+rs.getString("MAX_ENROLLED");
        				String minEnrolled = ""+rs.getString("MIN_ENROLLED");
        				/*
        				 *ambil dari makul
        				 */
        				String kdkmkmakul = ""+rs.getString("KDKMKMAKUL");
        				String nakmkmakul = ""+rs.getString("NAKMKMAKUL");
        				String skstmmakul = ""+rs.getInt("SKSTMMAKUL");
        				String sksprmakul = ""+rs.getInt("SKSPRMAKUL");
        				String skslpmakul = ""+rs.getInt("SKSLPMAKUL");
        				String kdwplmakul = ""+rs.getString("KDWPLMAKUL");
        				String jenismakul = ""+rs.getString("JENISMAKUL");
        				String stkmkmakul = ""+rs.getString("STKMKMAKUL");
        				String uniqueId = ""+rs.getLong("UNIQUE_ID");
                        /*
                         * kode kampus dll jagn lupa masalah kelas gabungan
                         * upd: harus nya dengan exclude canceled class secara otomatis kelas gabungan tidak ada masalah
                         * krn kelas yg digabung otomatis canceled dan hanya kelas inti yang aktif
                         */
        				
        				//li.add(shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+kdpsttmp+","+kdkmkmakul+","+nakmkmakul);
        				/*
        				 * untuk vector kelas yang dibuka ditambah dengan tambahan depan
        				 * kdkmk nakmk original pada konya
        				 */
        				//System.out.println("dibuka="+idkmk_ori+","+kdkmk_ori+","+nakmk_ori+","+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+kdpsttmp+","+kdkmkmakul+","+nakmkmakul);
        				li.add(idkmk_ori+","+kdkmk_ori+","+nakmk_ori+","+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+kdpsttmp+","+kdkmkmakul+","+nakmkmakul+","+uniqueId);
        				        				
        				
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
    		
     	return v;

    }
    
    
}
