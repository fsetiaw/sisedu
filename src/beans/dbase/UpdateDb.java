package beans.dbase;

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
public class UpdateDb {
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
    public UpdateDb() {
        // TODO Auto-generated constructor stub
    }

    public UpdateDb(String operatorNpm) {
        // TODO Auto-generated constructor stub
    	this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
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
    		//////System.out.println("this.operatorNpm="+this.operatorNpm);
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
    
    public void removeTargetThsmsFromWhiteList(String targetThsms,String targetKdpst,String targetNpmhs) {
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT TOKEN_THSMS from KRS_WHITE_LIST where KDPST=? and NPMHS=?");
    		stmt.setString(1, targetKdpst);
    		stmt.setString(2, targetNpmhs);
    		//////System.out.println("this.operatorNpm="+this.operatorNpm);
    		rs = stmt.executeQuery();
    		String tkn_thsms = null;
    		if(rs.next()) {
    			tkn_thsms = rs.getString("TOKEN_THSMS");
    			
    		}
    		if(tkn_thsms==null || Checker.isStringNullOrEmpty(tkn_thsms)) {
    			//ignore - ngga ada data di whitelist untuk dihapus
    		}
    		else {
    			StringTokenizer st = new StringTokenizer(tkn_thsms,",");
    			////System.out.println("tkn_thsms=="+tkn_thsms);
    			String sisa_tkn_thsms = "";
    			boolean first = true;
    			while(st.hasMoreTokens()) {
    				String thsms = st.nextToken();
    				if(!thsms.equalsIgnoreCase(targetThsms)) {
    					if(first) {
    						first = false;
    						sisa_tkn_thsms = new String(thsms);
    					}
    					else {
    						sisa_tkn_thsms = sisa_tkn_thsms+","+thsms;
    					}
    				}
    			}
    			//////System.out.println("sisa_tkn_thsms=="+sisa_tkn_thsms);
    			if(sisa_tkn_thsms!=null) {
    				st = new StringTokenizer(sisa_tkn_thsms,",");
        			if(st.countTokens()<1) {
        				//sudah tidak ada sisa - delete from whitelist
        				stmt = con.prepareStatement("delete from KRS_WHITE_LIST where KDPST=? and NPMHS=?");
        				stmt.setString(1,targetKdpst);
        				stmt.setString(2,targetNpmhs);
        				stmt.executeUpdate();
        			}
        			else {
        				//update whitelist
        				stmt = con.prepareStatement("update KRS_WHITE_LIST set TOKEN_THSMS=? where KDPST=? and NPMHS=?");
        				stmt.setString(1,sisa_tkn_thsms);
        				stmt.setString(2,targetKdpst);
        				stmt.setString(3,targetNpmhs);
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
    	finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
		}	
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
    		//////System.out.println("thiss.operatorNpm="+this.operatorNpm);
    		rs = stmt.executeQuery();
    		rs.next();
    		nickname = rs.getString("OBJ_NICKNAME");
    		//////System.out.println("thiss.nickname="+nickname);
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
    		//////System.out.println("this.operatorNpm="+this.operatorNpm);
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
    
    public void insertLogMe(int usrDatId,String targetNpmhs,String tipeAction,String keteranganTransaksi,String ipAddr) {
    	/*
    	 * if usrId < 0; berarti unverified usr jharusnya cuma pada saat login
    	 */
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("INSERT INTO LOG_ME(USR_DAT_ID,TARGET_NPMHS,TIPE_AKSES,KETERANGAN,IP_CLIENT)VALUES(?,?,?,?,?);");
    		stmt.setInt(1, usrDatId);
    		if(Checker.isStringNullOrEmpty(targetNpmhs)) {
    			stmt.setNull(2,java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(2, targetNpmhs);
    		}
    		if(Checker.isStringNullOrEmpty(tipeAction)) {
    			stmt.setNull(3,java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(3,tipeAction);
    		}
    		if(Checker.isStringNullOrEmpty(keteranganTransaksi)) {
    			stmt.setNull(4,java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(4,keteranganTransaksi);
    		}
    		stmt.setString(5, ipAddr);
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
    }
    
    //public int getNoRutTerakhir() {
    public long getNoRutTerakhir() {
    	long i=0;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select NORUTPYMNT from PYMNT order by NORUTPYMNT desc limit 1");
    		rs = stmt.executeQuery();
    		
    		if(rs.next()) {
    			i = rs.getLong("NORUTPYMNT");
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
    
    public long getNoRutTerakhirAtPymntTransitTabel() {
    	long i=0;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select NORUTPYMNT from PYMNT_TRANSIT order by NORUTPYMNT desc limit 1");
    		rs = stmt.executeQuery();
    		
    		if(rs.next()) {
    			i = rs.getLong("NORUTPYMNT");
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
    
    public long getNoGroupIdTerakhirAtPymntTable() {
    	long i=0;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select GROUP_ID from PYMNT where GROUP_ID IS NOT NULL order by GROUP_ID DESC limit 1");
    		//stmt = con.prepareStatement("select NORUTPYMNT from PYMNT order by NORUTPYMNT desc limit 1");
    		rs = stmt.executeQuery();
    		
    		if(rs.next()) {
    			i = rs.getLong("GROUP_ID");
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
    
    public long getNoGroupIdTerakhirAtPymntTransitTabel() {
    	long i=0;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select GROUP_ID from PYMNT_TRANSIT order by GROUP_ID DESC limit 1");
    		//stmt = con.prepareStatement("select NORUTPYMNT from PYMNT order by NORUTPYMNT desc limit 1");
    		rs = stmt.executeQuery();
    		
    		if(rs.next()) {
    			i = rs.getLong("GROUP_ID");
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
    
    public void addNpmhsAsTargetKurikulum(String idkur,String kdpst, String npmhs) {
    	boolean update=false;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//update di EXT_CIVITAS
    		stmt = con.prepareStatement("update EXT_CIVITAS set KRKLMMSMHS=? where KDPSTMSMHS=? and NPMHSMSMHS=?");
    		stmt.setString(1, idkur);
    		stmt.setString(2, kdpst);
    		stmt.setString(3, npmhs);
    		//////System.out.println("updte kurOpr="+idkur+" -- "+stmt.executeUpdate());
    		stmt.executeUpdate();
    		
    		//versi update di KRKLM
    		stmt = con.prepareStatement("select TARGTKRKLM from KRKLM where IDKURKRKLM=?");
    		stmt.setLong(1, Long.valueOf(idkur).longValue());
    		rs = stmt.executeQuery();
    		//rs.next();
    		String target = null;
    		if(rs.next()) {
    			target = rs.getString("TARGTKRKLM");
    			if(target==null) {
    				target = ""+npmhs;
    				update = true;
    			}
    			else {
    				if(!target.contains(npmhs)) {
    					update= true;
    					if(target.endsWith(",")) {
    						target = target+npmhs;
    					}
    					else {
    						target = target+","+npmhs;
    					}
    				}
    			}
    				
    		}
    		else {
    			update = false;
    		}
    		
    		//jika harus update
    		if(update) {
    			//update = add npmhs at idkur
    			stmt = con.prepareStatement("update KRKLM set TARGTKRKLM=? where IDKURKRKLM=?");
    			stmt.setString(1,target);
    			stmt.setLong(2,Long.valueOf(idkur).longValue());
    			stmt.executeUpdate();
    			
    			//update = remove npmhs if contain at !idkur
    			//1.cek idkur mana yg contains npmhs
    			Vector v = new Vector();
    			ListIterator li = v.listIterator();
    			stmt = con.prepareStatement("select * from KRKLM where TARGTKRKLM like ? and IDKURKRKLM<>?");
    			stmt.setString(1,"%"+npmhs+"%");
    			stmt.setLong(2, Long.valueOf(idkur).longValue());
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String id = ""+rs.getLong("IDKURKRKLM");
    				String tgt = ""+rs.getString("TARGTKRKLM");
    				tgt = tgt.replace(npmhs, "");
    				tgt = tgt.replace(",,", ",");
    				tgt = tgt.replace(", ,", ",");
    				tgt = tgt.replace(",  ,", ",");
    				li.add(id);
    				li.add(tgt);
    			}
    			//2.delete npm dari target
    			if(v.size()>1) {
    				stmt = con.prepareStatement("update KRKLM set TARGTKRKLM=? where IDKURKRKLM=?");
    				li = v.listIterator();
    				while(li.hasNext()) {
    					String id = (String)li.next();
        				String tgt = (String)li.next();
        				stmt.setString(1,tgt);
            			stmt.setLong(2,Long.valueOf(id).longValue());
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
    	finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
		}	
    }

    
    public int deleteRecordTrnlm(String thsms,String kdkmk,String kdpst,String npmhs) {
    	int i=0;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("delete from TRNLM where THSMSTRNLM=? and KDPSTTRNLM=? and NPMHSTRNLM=? and KDKMKTRNLM=?");
    		stmt.setString(1,thsms);
    		stmt.setString(2,kdpst);
    		stmt.setString(3,npmhs);
    		stmt.setString(4,kdkmk);
    		i = stmt.executeUpdate();
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
    
    
    public void updateJenisMakul(String[]infoJenisMakul) {
    	
    	try {
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("UPDATE MAKUL set JENISMAKUL=? where IDKMKMAKUL=?");
    		for(int i=0;i<infoJenisMakul.length;i++) {
    			StringTokenizer st = new StringTokenizer(infoJenisMakul[i],"`");
    			//////System.out.println("infoJenisMaku["+i+"]="+infoJenisMakul[i]);
    			String idkmk = st.nextToken();
    			String kdkmk = st.nextToken();
    			String nakmk = st.nextToken();
    			String skstm = st.nextToken();
    			String skspr = st.nextToken();
    			String skslp = st.nextToken();
    			String kdwpl = st.nextToken();
    			String kode  = st.nextToken();
    			
    			if(kode==null || Checker.isStringNullOrEmpty(kode)) {
    				kode = "0";
    			}
    			stmt.setString(1, kode);
    			stmt.setLong(2, Long.parseLong(idkmk));
    			stmt.executeUpdate();
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
    	//return i;
    }

    
    public int deleteRecordTrnlp(String kdkmk,String kdpst,String npmhs) {
    	int i=0;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("delete from TRNLP where KDPSTTRNLP=? and NPMHSTRNLP=? and KDKMKTRNLP=?");
    		stmt.setString(1,kdpst);
    		stmt.setString(2,npmhs);
    		stmt.setString(3,kdkmk);
    		i = stmt.executeUpdate();
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
    
    public int updateTargetKurikulum(String idkur,String target_angkatan,String target_mahasiswa) {
    	int i =0;
    	String target = "";
    	if(target_angkatan!=null && !target_angkatan.equalsIgnoreCase("null")) {
        	StringTokenizer st = new StringTokenizer(target_angkatan,",");
        	target_angkatan = "";
        	while(st.hasMoreTokens()) {
        		target_angkatan = target_angkatan+st.nextToken();
        		if(st.hasMoreTokens()) {
        			target_angkatan = target_angkatan+",";
        		}
        	}
        	target = target+target_angkatan;
    	}
    	if(target_mahasiswa!=null && !target_mahasiswa.equalsIgnoreCase("null")) {
        	StringTokenizer st = new StringTokenizer(target_mahasiswa,",");
        	target_mahasiswa = "";
        	while(st.hasMoreTokens()) {
        		target_mahasiswa = target_mahasiswa+st.nextToken();
        		if(st.hasMoreTokens()) {
        			target_mahasiswa = target_mahasiswa+",";
        		}
        	}
        	if(target.length()>0 && target_mahasiswa.length()>0) {
        		target = target+","+target_mahasiswa;
        	}
        	else {
        		if(target.length()==0 && target_mahasiswa.length()>0) {
        			target = ""+target_mahasiswa;
        		}
        	}
    	}
    	
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();

    		stmt = con.prepareStatement("update KRKLM set TARGTKRKLM=? where IDKURKRKLM=?");
    		stmt.setString(1, target);
    		stmt.setInt(2, Integer.valueOf(idkur).intValue());
    		i = stmt.executeUpdate();

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

    
    
	public void updateLogHistory(String kdpst, String npmhs,int ttlog,int tmlog) {
		try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();

    		stmt = con.prepareStatement("update EXT_CIVITAS set TTLOGMSMHS=?,TMLOGMSMHS=? where KDPSTMSMHS=? and NPMHSMSMHS=?");
    		stmt.setInt(1, ttlog);
    		stmt.setInt(2, tmlog);
    		stmt.setString(3,kdpst);
    		stmt.setString(4,npmhs);
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
	}

    public boolean updateProfile(String v_id_obj,String v_kdpst,String v_npmhs,String v_nimhs,String v_nmmhs,String v_smawl,String v_almrm,String v_kdjek,String v_sttus,String v_email,String v_nohpe,String v_tplhr,String v_telrm,String v_neglh,String v_posrm,String v_tglhr,String v_kotrm,String v_nmpek,String v_almkt,String v_jbtkt,String v_bidkt,String v_jenkt,String v_kotkt,String v_telkt,String v_poskt,String v_nmmsp,String v_telsp,String v_almsp,String v_stpid,String v_possp,String v_kotsp,String v_negsp,String v_agama,String v_shift) {
    	int ins = 0;
    	try {
    		long norut =1+getNoRutTerakhir();
    		//////System.out.println("norut ="+norut);
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("update CIVITAS set NMMHSMSMHS=?,TPLHRMSMHS=?,TGLHRMSMHS=?,KDJEKMSMHS=?,TAHUNMSMHS=?,SMAWLMSMHS=?,STPIDMSMHS=?,NMPEKMSMHS=?,NIMHSMSMHS=?,SHIFTMSMHS=?,ID_OBJ=? where NPMHSMSMHS=? and KDPSTMSMHS=?");
    		if(Checker.isStringNullOrEmpty(v_nmmhs)) {
    			stmt.setNull(1, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(1, v_nmmhs.toUpperCase());
    		}
    		//////System.out.println("1."+v_nmmhs);
    		if(Checker.isStringNullOrEmpty(v_tplhr)) {
    			stmt.setNull(2, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(2, v_tplhr.toUpperCase());
    		}
    		//////System.out.println("2."+v_tplhr);
      		try {
    			java.sql.Date tglhr = java.sql.Date.valueOf(v_tglhr);
    			stmt.setDate(3,tglhr);//5
    		}
    		catch(Exception e) {
    			stmt.setNull(3, java.sql.Types.DATE);//5
    		}
      		if(Checker.isStringNullOrEmpty(v_kdjek)) {
    			stmt.setNull(4, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(4, v_kdjek.toUpperCase());
    		}
      		if(Checker.isStringNullOrEmpty(v_smawl)) {
    			stmt.setNull(5, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(5, v_smawl.substring(0,4));
    		}
      		if(Checker.isStringNullOrEmpty(v_smawl)) {
    			stmt.setNull(6, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(6, v_smawl.toUpperCase());
    		}
      		if(Checker.isStringNullOrEmpty(v_smawl)) {
    			stmt.setNull(7, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(7, v_stpid.toUpperCase());
    		}
      		if(Checker.isStringNullOrEmpty(v_nmpek)) {
    			stmt.setNull(8, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(8, v_nmpek.toUpperCase());
    		}
      		if(Checker.isStringNullOrEmpty(v_nimhs)) {
    			stmt.setNull(9, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(9, v_nimhs.toUpperCase());
    		}
      		if(Checker.isStringNullOrEmpty(v_shift)) {
    			stmt.setString(10, "N/A");
    		}
    		else {
    			stmt.setString(10, v_shift.toUpperCase());
    		}
      		
    		stmt.setLong(11, Long.valueOf(v_id_obj).longValue());
    		
      		
      		
      		if(Checker.isStringNullOrEmpty(v_npmhs)) {
    			stmt.setNull(12, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(12, v_npmhs.toUpperCase());
    		}
      		if(Checker.isStringNullOrEmpty(v_kdpst)) {
    			stmt.setNull(13, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(13, v_kdpst.toUpperCase());
    		}  
      		stmt.executeUpdate();
      		
      		
      		
      		
      		//stmt=con.prepareStatement("update EXT_CIVITAS set STTUSMSMHS=?,EMAILMSMHS=?,NOHPEMSMHS=?,ALMRMMSMHS=?,KOTRMMSMHS=?,POSRMMSMHS=?,TELRMMSMHS=?,ALMKTMSMHS=?,KOTKTMSMHS=?,POSKTMSMHS=?,TELKTMSMHS=?,JBTKTMSMHS=?,BIDKTMSMHS=?,JENKTMSMHS=?,NMMSPMSMHS=?,ALMSPMSMHS=?,POSSPMSMHS=?,KOTSPMSMHS=?,NEGSPMSMHS=?,TELSPMSMHS=?,NEGLHMSMHS=?,AGAMAMSMHS=? where NPMHSMSMHS=? and KDPSTMSMHS=?");
      		//delete prev record di EXt_CIVITAS
      		/*
      		stmt=con.prepareStatement("DELETE FROM EXT_CIVITAS  where NPMHSMSMHS=? and KDPSTMSMHS=?");
      		stmt.setString(1,v_npmhs.toUpperCase());
      		stmt.setString(2, v_kdpst.toUpperCase());
      		stmt.executeUpdate();
      		*
      		*/
      		//update dengan record baru
   
      		stmt=con.prepareStatement("select * FROM EXT_CIVITAS  where NPMHSMSMHS=? and KDPSTMSMHS=?");
      		stmt.setString(1,v_npmhs.toUpperCase());
      		stmt.setString(2, v_kdpst.toUpperCase());
      		rs = stmt.executeQuery();
      		if(rs.next()) {
      			stmt=con.prepareStatement("UPDATE EXT_CIVITAS set STTUSMSMHS=?,EMAILMSMHS=?,NOHPEMSMHS=?,ALMRMMSMHS=?,KOTRMMSMHS=?,POSRMMSMHS=?,TELRMMSMHS=?,ALMKTMSMHS=?,KOTKTMSMHS=?,POSKTMSMHS=?,TELKTMSMHS=?,JBTKTMSMHS=?,BIDKTMSMHS=?,JENKTMSMHS=?,NMMSPMSMHS=?,ALMSPMSMHS=?,POSSPMSMHS=?,KOTSPMSMHS=?,NEGSPMSMHS=?,TELSPMSMHS=?,NEGLHMSMHS=?,AGAMAMSMHS=? where NPMHSMSMHS=? and KDPSTMSMHS=?");
      			
      			if(Checker.isStringNullOrEmpty(v_sttus)) {
      				stmt.setNull(1, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(1, v_sttus.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_email)) {
      				stmt.setNull(2, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(2, v_email);
      			}
      			if(Checker.isStringNullOrEmpty(v_nohpe)) {
      				stmt.setNull(3, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(3, v_nohpe.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_almrm)) {
      				stmt.setNull(4, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(4, v_almrm.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_kotrm)) {
      				stmt.setNull(5, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(5, v_kotrm.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_posrm)) {
      				stmt.setNull(6, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(6, v_posrm.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_telrm)) {
      				stmt.setNull(7, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(7, v_telrm.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_almkt)) {
      				stmt.setNull(8, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(8, v_almkt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_kotkt)) {
      				stmt.setNull(9, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(9, v_kotkt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_poskt)) {
      				stmt.setNull(10, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(10, v_poskt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_telkt)) {
      				stmt.setNull(11, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(11, v_telkt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_jbtkt)) {
      				stmt.setNull(12, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(12, v_jbtkt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_bidkt)) {
      				stmt.setNull(13, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(13, v_bidkt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_jenkt)) {
      				stmt.setNull(14, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(14, v_jenkt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_nmmsp)) {
      				stmt.setNull(15, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(15, v_nmmsp.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_almsp)) {
      				stmt.setNull(16, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(16, v_almsp.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_possp)) {
      				stmt.setNull(17, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(17, v_possp.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_kotsp)) {
      				stmt.setNull(18, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(18, v_kotsp.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_negsp)) {
      				stmt.setNull(19, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(19, v_negsp.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_telsp)) {
      				stmt.setNull(20, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(20, v_telsp.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_neglh)) {
      				stmt.setNull(21, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(21, v_neglh.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_agama)) {
      				stmt.setNull(22, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(22, v_agama.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_npmhs)) {
      				stmt.setNull(23, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(23, v_npmhs.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_kdpst)) {
      				stmt.setNull(24, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(24, v_kdpst.toUpperCase());
      			}  
      			stmt.executeUpdate();
      		}
      		else {
      			stmt=con.prepareStatement("INSERT INTO EXT_CIVITAS (NPMHSMSMHS,KDPSTMSMHS,STTUSMSMHS,EMAILMSMHS,NOHPEMSMHS,ALMRMMSMHS,KOTRMMSMHS,POSRMMSMHS,TELRMMSMHS,ALMKTMSMHS,KOTKTMSMHS,POSKTMSMHS,TELKTMSMHS,JBTKTMSMHS,BIDKTMSMHS,JENKTMSMHS,NMMSPMSMHS,ALMSPMSMHS,POSSPMSMHS,KOTSPMSMHS,NEGSPMSMHS,TELSPMSMHS,NEGLHMSMHS,AGAMAMSMHS) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
      			if(Checker.isStringNullOrEmpty(v_npmhs)) {
      				stmt.setNull(1, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(1, v_npmhs.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_kdpst)) {
      				stmt.setNull(2, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(2, v_kdpst.toUpperCase());
      			}  
      			if(Checker.isStringNullOrEmpty(v_sttus)) {
      				stmt.setNull(3, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(3, v_sttus.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_email)) {
      				stmt.setNull(4, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(4, v_email);
      			}
      			if(Checker.isStringNullOrEmpty(v_nohpe)) {
      				stmt.setNull(5, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(5, v_nohpe.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_almrm)) {
      				stmt.setNull(6, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(6, v_almrm.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_kotrm)) {
      				stmt.setNull(7, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(7, v_kotrm.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_posrm)) {
      				stmt.setNull(8, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(8, v_posrm.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_telrm)) {
      				stmt.setNull(9, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(9, v_telrm.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_almkt)) {
      				stmt.setNull(10, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(10, v_almkt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_kotkt)) {
      				stmt.setNull(11, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(11, v_kotkt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_poskt)) {
      				stmt.setNull(12, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(12, v_poskt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_telkt)) {
      				stmt.setNull(13, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(13, v_telkt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_jbtkt)) {
      				stmt.setNull(14, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(14, v_jbtkt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_bidkt)) {
      				stmt.setNull(15, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(15, v_bidkt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_jenkt)) {
      				stmt.setNull(16, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(16, v_jenkt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_nmmsp)) {
      				stmt.setNull(17, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(17, v_nmmsp.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_almsp)) {
      				stmt.setNull(18, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(18, v_almsp.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_possp)) {
      				stmt.setNull(19, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(19, v_possp.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_kotsp)) {
      				stmt.setNull(20, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(20, v_kotsp.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_negsp)) {
      				stmt.setNull(21, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(21, v_negsp.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_telsp)) {
      				stmt.setNull(22, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(22, v_telsp.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_neglh)) {
      				stmt.setNull(23, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(23, v_neglh.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_agama)) {
      				stmt.setNull(24, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(24, v_agama.toUpperCase());
      			}
      			stmt.executeUpdate();
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
    	boolean sukses = false;
    	if(ins>0) {
    		sukses = true;
    	}
    	return sukses;	
    }
    
    
    public boolean updateProfile_kokd_debel_nih(String v_id_obj,String v_kdpst,String v_npmhs,String v_nimhs,String v_nmmhs,String v_smawl,String v_almrm,String v_kdjek,String v_sttus,String v_email,String v_nohpe,String v_tplhr,String v_telrm,String v_neglh,String v_posrm,String v_tglhr,String v_kotrm,String v_nmpek,String v_almkt,String v_jbtkt,String v_bidkt,String v_jenkt,String v_kotkt,String v_telkt,String v_poskt,String v_nmmsp,String v_telsp,String v_almsp,String v_stpid,String v_possp,String v_kotsp,String v_negsp,String v_agama,String v_shift) {
    	int ins = 0;
    	try {
    		long norut =1+getNoRutTerakhir();
    		//////System.out.println("norut ="+norut);
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("update CIVITAS set NMMHSMSMHS=?,TPLHRMSMHS=?,TGLHRMSMHS=?,KDJEKMSMHS=?,TAHUNMSMHS=?,SMAWLMSMHS=?,STPIDMSMHS=?,NMPEKMSMHS=?,NIMHSMSMHS=?,SHIFTMSMHS=?,ID_OBJ=? where NPMHSMSMHS=? and KDPSTMSMHS=?");
    		if(Checker.isStringNullOrEmpty(v_nmmhs)) {
    			stmt.setNull(1, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(1, v_nmmhs.toUpperCase());
    		}
    		//////System.out.println("1."+v_nmmhs);
    		if(Checker.isStringNullOrEmpty(v_tplhr)) {
    			stmt.setNull(2, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(2, v_tplhr.toUpperCase());
    		}
    		//////System.out.println("2."+v_tplhr);
      		try {
    			java.sql.Date tglhr = java.sql.Date.valueOf(v_tglhr);
    			stmt.setDate(3,tglhr);//5
    		}
    		catch(Exception e) {
    			stmt.setNull(3, java.sql.Types.DATE);//5
    		}
      		if(Checker.isStringNullOrEmpty(v_kdjek)) {
    			stmt.setNull(4, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(4, v_kdjek.toUpperCase());
    		}
      		if(Checker.isStringNullOrEmpty(v_smawl)) {
    			stmt.setNull(5, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(5, v_smawl.substring(0,4));
    		}
      		if(Checker.isStringNullOrEmpty(v_smawl)) {
    			stmt.setNull(6, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(6, v_smawl.toUpperCase());
    		}
      		if(Checker.isStringNullOrEmpty(v_smawl)) {
    			stmt.setNull(7, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(7, v_stpid.toUpperCase());
    		}
      		if(Checker.isStringNullOrEmpty(v_nmpek)) {
    			stmt.setNull(8, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(8, v_nmpek.toUpperCase());
    		}
      		if(Checker.isStringNullOrEmpty(v_nimhs)) {
    			stmt.setNull(9, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(9, v_nimhs.toUpperCase());
    		}
      		if(Checker.isStringNullOrEmpty(v_shift)) {
    			stmt.setString(10, "N/A");
    		}
    		else {
    			stmt.setString(10, v_shift.toUpperCase());
    		}
      		
    		stmt.setLong(11, Long.valueOf(v_id_obj).longValue());
    		
      		
      		
      		if(Checker.isStringNullOrEmpty(v_npmhs)) {
    			stmt.setNull(12, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(12, v_npmhs.toUpperCase());
    		}
      		if(Checker.isStringNullOrEmpty(v_kdpst)) {
    			stmt.setNull(13, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(13, v_kdpst.toUpperCase());
    		}  
      		stmt.executeUpdate();
      		
      		
      		
      		
      		//stmt=con.prepareStatement("update EXT_CIVITAS set STTUSMSMHS=?,EMAILMSMHS=?,NOHPEMSMHS=?,ALMRMMSMHS=?,KOTRMMSMHS=?,POSRMMSMHS=?,TELRMMSMHS=?,ALMKTMSMHS=?,KOTKTMSMHS=?,POSKTMSMHS=?,TELKTMSMHS=?,JBTKTMSMHS=?,BIDKTMSMHS=?,JENKTMSMHS=?,NMMSPMSMHS=?,ALMSPMSMHS=?,POSSPMSMHS=?,KOTSPMSMHS=?,NEGSPMSMHS=?,TELSPMSMHS=?,NEGLHMSMHS=?,AGAMAMSMHS=? where NPMHSMSMHS=? and KDPSTMSMHS=?");
      		//delete prev record di EXt_CIVITAS
      		/*
      		stmt=con.prepareStatement("DELETE FROM EXT_CIVITAS  where NPMHSMSMHS=? and KDPSTMSMHS=?");
      		stmt.setString(1,v_npmhs.toUpperCase());
      		stmt.setString(2, v_kdpst.toUpperCase());
      		stmt.executeUpdate();
      		*
      		*/
      		//update dengan record baru
   
      		stmt=con.prepareStatement("select * FROM EXT_CIVITAS  where NPMHSMSMHS=? and KDPSTMSMHS=?");
      		stmt.setString(1,v_npmhs.toUpperCase());
      		stmt.setString(2, v_kdpst.toUpperCase());
      		rs = stmt.executeQuery();
      		if(rs.next()) {
      			stmt=con.prepareStatement("UPDATE EXT_CIVITAS set STTUSMSMHS=?,EMAILMSMHS=?,NOHPEMSMHS=?,ALMRMMSMHS=?,KOTRMMSMHS=?,POSRMMSMHS=?,TELRMMSMHS=?,ALMKTMSMHS=?,KOTKTMSMHS=?,POSKTMSMHS=?,TELKTMSMHS=?,JBTKTMSMHS=?,BIDKTMSMHS=?,JENKTMSMHS=?,NMMSPMSMHS=?,ALMSPMSMHS=?,POSSPMSMHS=?,KOTSPMSMHS=?,NEGSPMSMHS=?,TELSPMSMHS=?,NEGLHMSMHS=?,AGAMAMSMHS=? where NPMHSMSMHS=? and KDPSTMSMHS=?");
      			
      			if(Checker.isStringNullOrEmpty(v_sttus)) {
      				stmt.setNull(1, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(1, v_sttus.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_email)) {
      				stmt.setNull(2, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(2, v_email);
      			}
      			if(Checker.isStringNullOrEmpty(v_nohpe)) {
      				stmt.setNull(3, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(3, v_nohpe.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_almrm)) {
      				stmt.setNull(4, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(4, v_almrm.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_kotrm)) {
      				stmt.setNull(5, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(5, v_kotrm.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_posrm)) {
      				stmt.setNull(6, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(6, v_posrm.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_telrm)) {
      				stmt.setNull(7, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(7, v_telrm.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_almkt)) {
      				stmt.setNull(8, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(8, v_almkt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_kotkt)) {
      				stmt.setNull(9, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(9, v_kotkt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_poskt)) {
      				stmt.setNull(10, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(10, v_poskt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_telkt)) {
      				stmt.setNull(11, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(11, v_telkt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_jbtkt)) {
      				stmt.setNull(12, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(12, v_jbtkt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_bidkt)) {
      				stmt.setNull(13, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(13, v_bidkt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_jenkt)) {
      				stmt.setNull(14, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(14, v_jenkt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_nmmsp)) {
      				stmt.setNull(15, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(15, v_nmmsp.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_almsp)) {
      				stmt.setNull(16, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(16, v_almsp.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_possp)) {
      				stmt.setNull(17, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(17, v_possp.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_kotsp)) {
      				stmt.setNull(18, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(18, v_kotsp.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_negsp)) {
      				stmt.setNull(19, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(19, v_negsp.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_telsp)) {
      				stmt.setNull(20, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(20, v_telsp.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_neglh)) {
      				stmt.setNull(21, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(21, v_neglh.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_agama)) {
      				stmt.setNull(22, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(22, v_agama.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_npmhs)) {
      				stmt.setNull(23, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(23, v_npmhs.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_kdpst)) {
      				stmt.setNull(24, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(24, v_kdpst.toUpperCase());
      			}  
      			stmt.executeUpdate();
      		}
      		else {
      			stmt=con.prepareStatement("INSERT INTO EXT_CIVITAS (NPMHSMSMHS,KDPSTMSMHS,STTUSMSMHS,EMAILMSMHS,NOHPEMSMHS,ALMRMMSMHS,KOTRMMSMHS,POSRMMSMHS,TELRMMSMHS,ALMKTMSMHS,KOTKTMSMHS,POSKTMSMHS,TELKTMSMHS,JBTKTMSMHS,BIDKTMSMHS,JENKTMSMHS,NMMSPMSMHS,ALMSPMSMHS,POSSPMSMHS,KOTSPMSMHS,NEGSPMSMHS,TELSPMSMHS,NEGLHMSMHS,AGAMAMSMHS) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
      			if(Checker.isStringNullOrEmpty(v_npmhs)) {
      				stmt.setNull(1, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(1, v_npmhs.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_kdpst)) {
      				stmt.setNull(2, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(2, v_kdpst.toUpperCase());
      			}  
      			if(Checker.isStringNullOrEmpty(v_sttus)) {
      				stmt.setNull(3, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(3, v_sttus.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_email)) {
      				stmt.setNull(4, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(4, v_email);
      			}
      			if(Checker.isStringNullOrEmpty(v_nohpe)) {
      				stmt.setNull(5, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(5, v_nohpe.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_almrm)) {
      				stmt.setNull(6, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(6, v_almrm.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_kotrm)) {
      				stmt.setNull(7, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(7, v_kotrm.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_posrm)) {
      				stmt.setNull(8, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(8, v_posrm.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_telrm)) {
      				stmt.setNull(9, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(9, v_telrm.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_almkt)) {
      				stmt.setNull(10, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(10, v_almkt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_kotkt)) {
      				stmt.setNull(11, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(11, v_kotkt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_poskt)) {
      				stmt.setNull(12, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(12, v_poskt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_telkt)) {
      				stmt.setNull(13, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(13, v_telkt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_jbtkt)) {
      				stmt.setNull(14, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(14, v_jbtkt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_bidkt)) {
      				stmt.setNull(15, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(15, v_bidkt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_jenkt)) {
      				stmt.setNull(16, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(16, v_jenkt.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_nmmsp)) {
      				stmt.setNull(17, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(17, v_nmmsp.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_almsp)) {
      				stmt.setNull(18, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(18, v_almsp.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_possp)) {
      				stmt.setNull(19, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(19, v_possp.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_kotsp)) {
      				stmt.setNull(20, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(20, v_kotsp.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_negsp)) {
      				stmt.setNull(21, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(21, v_negsp.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_telsp)) {
      				stmt.setNull(22, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(22, v_telsp.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_neglh)) {
      				stmt.setNull(23, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(23, v_neglh.toUpperCase());
      			}
      			if(Checker.isStringNullOrEmpty(v_agama)) {
      				stmt.setNull(24, java.sql.Types.VARCHAR);
      			}
      			else {
      				stmt.setString(24, v_agama.toUpperCase());
      			}
      			stmt.executeUpdate();
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
    	boolean sukses = false;
    	if(ins>0) {
    		sukses = true;
    	}
    	return sukses;	
    }
    
    public void updateDataDosenPembimbing(String kdpst, String npmhs, String npmpa, String nmmpa) {
    	String thsms_krs = Checker.getThsmsKrs();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("update EXT_CIVITAS set NPM_PA=?,NMM_PA=? where KDPSTMSMHS=? and NPMHSMSMHS=?");
    		stmt.setString(1, npmpa);
    		stmt.setString(2, nmmpa);
    		stmt.setString(3, kdpst);
    		stmt.setString(4, npmhs);
    		stmt.executeUpdate();
    		
    		//update juga krs notification yang blum di approv
    		stmt = con.prepareStatement("update KRS_NOTIFICATION set NPM_RECEIVER=?,NMM_RECEIVER=? where THSMS_TARGET=? and NPM_SENDER=? and APPROVED=? and DECLINED=?");
    		////System.out.println("--->"+npmpa+","+nmmpa+","+npmhs);
    		stmt.setString(1, npmpa);
    		stmt.setString(2, nmmpa);
    		stmt.setString(3, thsms_krs);
    		stmt.setString(4, npmhs);
    		stmt.setBoolean(5, false);
    		stmt.setBoolean(6, false);
    		
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
    }
    
    public boolean insertPymntTable(String kdpst,String npmhs,String tgtrs,String keter,String payee,String amont,String pmntp,String noacc,String opnpm,String opnmm,String nokod) {
    	int ins = 0;
    	try {
    		long norut =1+getNoRutTerakhir();
    		//////System.out.println("norut ="+norut);
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("insert into PYMNT (KDPSTPYMNT,NPMHSPYMNT,NORUTPYMNT,TGKUIPYMNT,TGTRSPYMNT,KETERPYMNT,PAYEEPYMNT,AMONTPYMNT,PYMTPYMNT,NOACCPYMNT,OPNPMPYMNT,OPNMMPYMNT,SETORPYMNT,NOKODPYMNT) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    		int i=1;
    		stmt.setString(i++,kdpst);//1
    		stmt.setString(i++,npmhs);//2
    		//stmt.setLong(i++,1+getNoRutTerakhir());//3
    		stmt.setLong(i++,norut);//3
    		java.util.Date date = new java.util.Date();
    		java.sql.Date todayDate = new java.sql.Date( date.getTime() );
    		//////System.out.println(""+todayDate);;		
    		stmt.setDate(i++, todayDate);//4
    		//////System.out.println("4="+i);
    		try {
    			java.sql.Date trsdt = java.sql.Date.valueOf(tgtrs);
    			stmt.setDate(i++,trsdt);//5
    			//////System.out.println("5a");
    		}
    		catch(Exception e) {
    			stmt.setDate(i++, todayDate);
    			//stmt.setNull(i++, java.sql.Types.DATE);//5
    			//////System.out.println("5b");
    		}
    		stmt.setString(i++,keter);//6
    		stmt.setString(i++, payee);//7
    		stmt.setDouble(i++, Double.valueOf(amont).doubleValue());//8
    		stmt.setString(i++,pmntp);//9
    		try {
    			java.sql.Date trsdt = java.sql.Date.valueOf(tgtrs);
    			stmt.setString(i++,noacc);//10
    		}
    		catch(Exception e) {
    			stmt.setString(i++, "TUNAI");//10
    		}
    		stmt.setString(i++,opnpm);//11
    		stmt.setString(i++,opnmm);//12
    		try {
    			java.sql.Date trsdt = java.sql.Date.valueOf(tgtrs);
    			stmt.setBoolean(i++,true);//13
    		}
    		catch(Exception e) {
    			stmt.setBoolean(i++, false);//13
    		}
    		stmt.setString(i++,nokod);//14
    		ins = stmt.executeUpdate();
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
    	boolean sukses = false;
    	if(ins>0) {
    		sukses = true;
    	}
    	return sukses;	
    }

    public String generateNoNpm(String kdpst) {
    	String npm=null;
    	return npm;
    }

    public String generateNpm(String thsms,String kdpst) {
    	int ins = 0;
    	String npm =null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * FROM CIVITAS where NPMHSMSMHS like ? order by NPMHSMSMHS desc");
    		if(Checker.isStringNullOrEmpty(thsms)) {
    			npm = kdpst+"000";//3 angka 000 - 3 digit thsms
    		}
    		else {
    			npm = kdpst+thsms.substring(2,5);
    		}
    		stmt.setString(1,npm+"%");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			npm = rs.getString("NPMHSMSMHS");
    			String first8dig = npm.substring(0,8);
    			String tmp = npm.substring(npm.length()-5,npm.length());
    			//////System.out.println("norut ="+tmp);
    			String norut = ""+(Long.valueOf(tmp).longValue()+1);
    			for(int i=norut.length();i<5;i++) {
    				norut = "0"+norut;
    			}
    			npm = first8dig+norut;
    		}
    		else {
    			npm = npm+"00001";
    		}
    		//////System.out.println(npm);
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
    	return npm;	
    }


    public void updateMakul(String idkmk,String kdpst,String kdkmk,String nakmk,String kdwpl,String jenis,String skstm,String skspr,String skslp,String stkmk,String nodos){
    	kdkmk = kdkmk.toUpperCase();
    	nakmk = nakmk.toUpperCase();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//updtae recorrd
    		//stmt = con.prepareStatement("update MAKUL set NAKMKMAKUL=?,SKSTMMAKUL=?,SKSPRMAKUL=?,SKSLPMAKUL=?,KDWPLMAKUL=?,JENISMAKUL=?,NODOSMAKUL=?,STKMKMAKUL=? where KDPSTMAKUL=? and KDKMKMAKUL=?");
    		stmt = con.prepareStatement("update MAKUL set KDKMKMAKUL=?,NAKMKMAKUL=?,SKSTMMAKUL=?,SKSPRMAKUL=?,SKSLPMAKUL=?,KDWPLMAKUL=?,JENISMAKUL=?,NODOSMAKUL=?,STKMKMAKUL=? where IDKMKMAKUL=?");
    		if(kdkmk!=null) {
    			stmt.setString(1,kdkmk);
    		}
    		else {
    			stmt.setNull(1,java.sql.Types.VARCHAR);
    		}
    		if(nakmk!=null) {
    			stmt.setString(2,nakmk);
    		}
    		else {
    			stmt.setNull(2,java.sql.Types.VARCHAR);
    		}
    		if(skstm!=null) {
    			StringTokenizer st = new StringTokenizer(skstm);
    			if(st.countTokens()<1) {
    				stmt.setNull(3,java.sql.Types.INTEGER);
    			}
    			else {
    				stmt.setInt(3,Integer.valueOf(skstm).intValue());
    			}
    		}
    		else {
    			stmt.setNull(3,java.sql.Types.INTEGER);
    		}
    		if(skspr!=null) {
    			StringTokenizer st = new StringTokenizer(skspr);
    			if(st.countTokens()<1) {
    				stmt.setNull(4,java.sql.Types.INTEGER);
    			}
    			else {
    				stmt.setInt(4,Integer.valueOf(skspr).intValue());
    			}
    		}
    		else {
    			stmt.setNull(4,java.sql.Types.INTEGER);
    		}
    		if(skslp!=null) {
    			StringTokenizer st = new StringTokenizer(skslp);
    			if(st.countTokens()<1) {
    				stmt.setNull(5,java.sql.Types.INTEGER);
    			}
    			else {
    				stmt.setInt(5,Integer.valueOf(skslp).intValue());
    			}
    		}
    		else {
    			stmt.setNull(5,java.sql.Types.INTEGER);
    		}
    		if(kdwpl!=null) {
    			stmt.setString(6,kdwpl);
    		}
    		else {
    			stmt.setNull(6,java.sql.Types.VARCHAR);
    		}
    		if(jenis!=null) {
    			stmt.setString(7,jenis);
    		}
    		else {
    			stmt.setNull(7,java.sql.Types.VARCHAR);
    		}
    		if(nodos!=null) {
    			stmt.setString(8,nodos);
    		}
    		else {
    			stmt.setNull(8,java.sql.Types.VARCHAR);
    		}
    		if(stkmk!=null) {
    			stmt.setString(9,stkmk);
    		}
    		else {
    			stmt.setNull(9,java.sql.Types.VARCHAR);
    		}
    		//////System.out.println("idkmk="+idkmk);
    		stmt.setInt(10, Integer.valueOf(idkmk).intValue());
    		//stmt.setString(10, kdkmk);
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
    }
    
    
    
    /*
     * DEPRECATED 
     * GUNAKAN YG ADA VARIABLE KONSEN
     */
    public void updateKurikulum(String idkur,String kdpst,String nmkur,String start,String ended){
    	nmkur = nmkur.toUpperCase();
    	//nakmk = nakmk.toUpperCase();
    	SearchDb sdb = new SearchDb();
    	String thsms_now = sdb.getThsmsAktif();
    	String stkur = "A";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//updtae recorrd
    		stmt = con.prepareStatement("update KRKLM set KDPSTKRKLM=?,NMKURKRKLM=?,STARTTHSMS=?,ENDEDTHSMS=?,STKURKRKLM=? where IDKURKRKLM=?");
    		stmt.setString(1,kdpst);
    		if(nmkur!=null) {
    			stmt.setString(2,nmkur);
    		}
    		else {
    			stmt.setNull(2,java.sql.Types.VARCHAR);
    		}
    		if(start!=null) {
    			stmt.setString(3,start);
    		}
    		else {
    			stmt.setNull(3,java.sql.Types.VARCHAR);
    		}
    		if(ended!=null) {
    			stmt.setString(4,ended);
    		}
    		else {
    			stmt.setNull(4,java.sql.Types.VARCHAR);
    		}
    		if(ended!=null && !ended.equalsIgnoreCase("null")) {
    			if(ended.compareToIgnoreCase(thsms_now)<0) {
    				stkur = "H";
    			}
    		}
    		stmt.setString(5,stkur);
    		stmt.setLong(6, Long.valueOf(idkur).longValue());
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
    }
    
    
    public void updateKurikulum(String idkur,String kdpst,String nmkur,String start,String ended, String konsen){
    	nmkur = nmkur.toUpperCase();
    	//nakmk = nakmk.toUpperCase();
    	SearchDb sdb = new SearchDb();
    	String thsms_now = sdb.getThsmsAktif();
    	String stkur = "A";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//updtae recorrd
    		stmt = con.prepareStatement("update KRKLM set KDPSTKRKLM=?,NMKURKRKLM=?,STARTTHSMS=?,ENDEDTHSMS=?,STKURKRKLM=?,KONSENTRASI=? where IDKURKRKLM=?");
    		stmt.setString(1,kdpst);
    		if(nmkur!=null) {
    			stmt.setString(2,nmkur);
    		}
    		else {
    			stmt.setNull(2,java.sql.Types.VARCHAR);
    		}
    		if(start!=null) {
    			stmt.setString(3,start);
    		}
    		else {
    			stmt.setNull(3,java.sql.Types.VARCHAR);
    		}
    		if(ended!=null) {
    			stmt.setString(4,ended);
    		}
    		else {
    			stmt.setNull(4,java.sql.Types.VARCHAR);
    		}
    		if(ended!=null && !ended.equalsIgnoreCase("null")) {
    			if(ended.compareToIgnoreCase(thsms_now)<0) {
    				stkur = "H";
    			}
    		}
    		stmt.setString(5,stkur);
    		if(Checker.isStringNullOrEmpty(konsen)) {
    			stmt.setNull(6, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(6, konsen);
    		}
    		stmt.setLong(7, Long.valueOf(idkur).longValue());
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
    }
    
    public void deletePrevThanInserNewMakul(String kdpst,String kdkmk) {
    	/*
    	 * deprecated
    	 */
    	kdkmk = kdkmk.toUpperCase();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//delete prev recorrd
    		stmt = con.prepareStatement("delete from MAKUL where KDPSTMAKUL=? and KDKMKMAKUL=?");
    		stmt.setString(1,kdpst);
    		stmt.setString(2,kdkmk);
    		stmt.executeUpdate();
    		//insert new recorrd
    		stmt = con.prepareStatement("INSERT INTO MAKUL(KDPSTMAKUL,KDKMKMAKUL,NAKMKMAKUL) VALUES (?,?,?)");
    		stmt.setString(1,kdpst);
    		stmt.setString(2,kdkmk);
    		stmt.setString(3, "null");
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
    }
    
    public String insertNewMakul(String kdpst,String kdkmk) {
    	/*
    	 * 
    	 */
    	String id=null;;
    	kdkmk = kdkmk.toUpperCase();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//delete prev recorrd
    		//stmt = con.prepareStatement("delete from MAKUL where KDPSTMAKUL=? and KDKMKMAKUL=?");
    		//stmt.setString(1,kdpst);
    		//stmt.setString(2,kdkmk);
    		//stmt.executeUpdate();
    		//insert new recorrd
    		stmt = con.prepareStatement("INSERT INTO MAKUL(KDPSTMAKUL,KDKMKMAKUL,NAKMKMAKUL) VALUES (?,?,?)");
    		stmt.setString(1,kdpst);
    		stmt.setString(2,kdkmk);
    		stmt.setString(3, "null");
    		int i = stmt.executeUpdate();
    		if(i>0) {
    			stmt = con.prepareStatement("select * from MAKUL order by IDKMKMAKUL desc LIMIT 1");
    			rs = stmt.executeQuery();
    			rs.next();
    			id = ""+rs.getInt("IDKMKMAKUL");
    			//updateMakul(kdpst,kdkmk,nakmk,kdwpl,jenis,skstm,skspr,skslp,sttmk,nodos);
    		}
    		else {
    			id="null";
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
    	return id;
    }
  
    
    	
    /*
     * DEPRECATED
     * USE YAG DA VARIABLE KONSENTRASI
     */
    public String insertNewKurikulum(String kdpst,String kdkur,String thsms1, String thsms2) {
    	/*
    	 * 
    	 */
    	SearchDb sdb = new SearchDb();
    	String thsms_now = sdb.getThsmsAktif();
    	String stkur = "A";
    	String id=null;;
    	kdkur = kdkur.toUpperCase();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//delete prev recorrd
    		//stmt = con.prepareStatement("delete from MAKUL where KDPSTMAKUL=? and KDKMKMAKUL=?");
    		//stmt.setString(1,kdpst);
    		//stmt.setString(2,kdkmk);
    		//stmt.executeUpdate();
    		//insert new recorrd
    		stmt = con.prepareStatement("INSERT INTO KRKLM(KDPSTKRKLM,NMKURKRKLM,STARTTHSMS,ENDEDTHSMS,STKURKRKLM,SKSTTKRKLM,SMSTTKRKLM) VALUES (?,?,?,?,?,?,?)");
    		stmt.setString(1,kdpst);
    		stmt.setString(2,kdkur);
    		if(thsms1!=null && !thsms1.equalsIgnoreCase("null")) {
    			stmt.setString(3, thsms1);
    		}
    		else {
    			stmt.setNull(3, java.sql.Types.VARCHAR);
    		}
    		if(thsms2!=null && !thsms2.equalsIgnoreCase("null")) {
    			stmt.setString(4, thsms2);
    		}
    		else {
    			stmt.setNull(4, java.sql.Types.VARCHAR);
    		}
    		if(thsms2!=null && !thsms2.equalsIgnoreCase("null")) {
    			if(thsms2.compareToIgnoreCase(thsms_now)<0) {
    				stkur = "H";
    			}
    		}
    		stmt.setString(5,stkur);
    		stmt.setInt(6, 0);
    		stmt.setInt(7, 0);
    		int i = stmt.executeUpdate();
    		if(i>0) {
    			stmt = con.prepareStatement("select * from KRKLM order by IDKURKRKLM desc LIMIT 1");
    			rs = stmt.executeQuery();
    			rs.next();
    			id = ""+rs.getInt("IDKURKRKLM");
    			//updateMakul(kdpst,kdkmk,nakmk,kdwpl,jenis,skstm,skspr,skslp,sttmk,nodos);
    		}
    		else {
    			id="null";
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
    	return id;
    }
    
    public String insertNewKurikulum(String kdpst,String kdkur,String thsms1, String thsms2, String konsen) {
    	/*
    	 * 
    	 */
    	SearchDb sdb = new SearchDb();
    	String thsms_now = sdb.getThsmsAktif();
    	String stkur = "A";
    	String id=null;;
    	kdkur = kdkur.toUpperCase();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//delete prev recorrd
    		//stmt = con.prepareStatement("delete from MAKUL where KDPSTMAKUL=? and KDKMKMAKUL=?");
    		//stmt.setString(1,kdpst);
    		//stmt.setString(2,kdkmk);
    		//stmt.executeUpdate();
    		//insert new recorrd
    		stmt = con.prepareStatement("INSERT INTO KRKLM(KDPSTKRKLM,NMKURKRKLM,STARTTHSMS,ENDEDTHSMS,STKURKRKLM,SKSTTKRKLM,SMSTTKRKLM,KONSENTRASI) VALUES (?,?,?,?,?,?,?,?)");
    		stmt.setString(1,kdpst);
    		stmt.setString(2,kdkur);
    		if(thsms1!=null && !thsms1.equalsIgnoreCase("null")) {
    			stmt.setString(3, thsms1);
    		}
    		else {
    			stmt.setNull(3, java.sql.Types.VARCHAR);
    		}
    		if(thsms2!=null && !thsms2.equalsIgnoreCase("null")) {
    			stmt.setString(4, thsms2);
    		}
    		else {
    			stmt.setNull(4, java.sql.Types.VARCHAR);
    		}
    		if(thsms2!=null && !thsms2.equalsIgnoreCase("null")) {
    			if(thsms2.compareToIgnoreCase(thsms_now)<0) {
    				stkur = "H";
    			}
    		}
    		stmt.setString(5,stkur);
    		stmt.setInt(6, 0);
    		stmt.setInt(7, 0);
    		if(Checker.isStringNullOrEmpty(konsen)) {
    			stmt.setNull(8, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(8, konsen);
    		}
    		int i = stmt.executeUpdate();
    		if(i>0) {
    			stmt = con.prepareStatement("select * from KRKLM order by IDKURKRKLM desc LIMIT 1");
    			rs = stmt.executeQuery();
    			rs.next();
    			id = ""+rs.getInt("IDKURKRKLM");
    			//updateMakul(kdpst,kdkmk,nakmk,kdwpl,jenis,skstm,skspr,skslp,sttmk,nodos);
    		}
    		else {
    			id="null";
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
    	return id;
    }
    
    public String getKdjen(String kdpst) {
    	String kdjen =null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * FROM MSPST where KDPSTMSPST=?");
    		stmt.setString(1,kdpst);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			kdjen = rs.getString("KDJENMSPST");
    		}
    		else {
    			kdjen="Z";
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
    	return kdjen;	
    }

    public String getThsms() {
    	String thsms =null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			thsms = rs.getString("THSMS");
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
    
    public String getThsmsPmb() {
    	String thsms =null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			thsms = rs.getString("THSMS_PMB");
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

    
    public boolean insertCivitasSimple(String id_obj,String kdpst,String nmmhs,String kdjek, String stpid, String agama,String tplhr,String nglhr, String tglhr,String email,String nohpe) {
    	//kdjen ngga dipake
    	int ins = 0;
    	String npm =null,kdjen=null;
    	try {
    		String thsms = getThsmsPmb();
    		npm = generateNpm(thsms,kdpst);
    		kdjen = getKdjen(kdpst);
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("INSERT INTO CIVITAS(ID_OBJ,KDPTIMSMHS,KDJENMSMHS,KDPSTMSMHS,NPMHSMSMHS,NMMHSMSMHS,TPLHRMSMHS,TGLHRMSMHS,KDJEKMSMHS,TAHUNMSMHS,SMAWLMSMHS,STPIDMSMHS)VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
    		stmt.setLong(1, Long.valueOf(id_obj).longValue());
    		stmt.setString(2,Constants.getKdpti());
    		//stmt.setString(3, kdjen);
    		if(Checker.isStringNullOrEmpty(kdjen)) {
    			stmt.setNull(3, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(3, kdjen.toUpperCase());
    		}
      		if(Checker.isStringNullOrEmpty(kdpst)) {
    			stmt.setNull(4, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(4, kdpst.toUpperCase());
    		}
    		stmt.setString(5,npm);
    		stmt.setString(6, nmmhs.toUpperCase());
    		stmt.setString(7, tplhr.toUpperCase());
    		stmt.setDate(8, java.sql.Date.valueOf(tglhr));
    		stmt.setString(9, kdjek.toUpperCase());
    		stmt.setString(10,thsms.substring(0,4));
    		stmt.setString(11,thsms);
    		stmt.setString(12, stpid.toUpperCase());
    		ins = stmt.executeUpdate();
    		stmt = con.prepareStatement("INSERT INTO EXT_CIVITAS(KDPSTMSMHS,NPMHSMSMHS,EMAILMSMHS,NEGLHMSMHS,NOHPEMSMHS,AGAMAMSMHS)VALUES(?,?,?,?,?,?)");
    		stmt.setString(1, kdpst);
    		stmt.setString(2, npm);
    		stmt.setString(3, email);
    		stmt.setString(4, nglhr.toUpperCase());
    		stmt.setString(5, nohpe);
    		stmt.setString(6, agama.toUpperCase());
    		ins = ins + stmt.executeUpdate();
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
    	boolean sukses = false;
    	if(ins>0) {
    		sukses = true;
    	}
    	return sukses;	
    }

    /*
     *depricated 
     */
    /*
    public int updateMataKuliahKrsKhs(String thsms,String kdpst,String npmhs,String kdkmk,String nlakh,String bobot) {
    	//kdjen ngga dipake
    	int ins = 0;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("UPDATE TRNLM SET NLAKHTRNLM=?,BOBOTTRNLM=? where THSMSTRNLM=? and KDPSTTRNLM=? AND NPMHSTRNLM=? and KDKMKTRNLM=?");
    		stmt.setString(1,nlakh);
    		stmt.setDouble(2,Double.valueOf(bobot).doubleValue());
    		stmt.setString(3,thsms);
    		stmt.setString(4,kdpst);
    		stmt.setString(5,npmhs);
    		stmt.setString(6,kdkmk);
    		ins = stmt.executeUpdate();
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
    	return ins;	
    }
    */

    /*
     * Dipake untuk edit nilai matakuliah pindahan
     * karena bobot diinput secara manual 
    */
    public int updateMataKuliahKrsKhs(String thsms,String kdpst,String npmhs,String kdkmk,String nlakh,String bobot,String TipeKrs) {
    	//kdjen ngga dipake
    	//untuk trnlp - krn nlakh diisi manual
    	int ins = 0;
    	try {
    		String cmd = "";
    		if(TipeKrs.equalsIgnoreCase("null")) {
    			cmd="UPDATE TRNLM SET NLAKHTRNLM=?,BOBOTTRNLM=? where THSMSTRNLM=? and KDPSTTRNLM=? AND NPMHSTRNLM=? and KDKMKTRNLM=?";
    		}
    		else {
    			if(TipeKrs.equalsIgnoreCase("krs_pindahan")) {
    				cmd="UPDATE TRNLP SET NLAKHTRNLP=?,BOBOTTRNLP=? where KDPSTTRNLP=? AND NPMHSTRNLP=? and KDKMKTRNLP=?";
    			}
    		}
    		//////System.out.println("cmd="+cmd);
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		stmt = con.prepareStatement(cmd);
    		stmt.setString(1,nlakh.toUpperCase());
    		stmt.setDouble(2,Double.valueOf(bobot).doubleValue());
    		//stmt.setString(3,thsms);
    		stmt.setString(3,kdpst);
    		stmt.setString(4,npmhs);
    		stmt.setString(5,kdkmk);
    		ins = stmt.executeUpdate();
    		//////System.out.println("ins="+ins);
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
    	return ins;	
    }
   
    //depricated - ngga return npm
    // without aspti
    public boolean insertCivitasSimpleGivenSmawl(String smawl,String id_obj,String kdpst,String nmmhs,String nimhs,String kdjek, String stpid, String agama,String tplhr,String nglhr, String tglhr,String email,String nohpe) {
    	//kdjen ngga dipake
    	int ins = 0;
    	String npm =null,kdjen=null;
    	try {
    		String thsms = ""+smawl;
    		npm = generateNpm(thsms,kdpst);
    		kdjen = getKdjen(kdpst);
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//stmt = con.prepareStatement("select * from MSPST where KDPSTMSPST=?");
    		//stmt.setString(1,kdpst);
    		//rs = stmt.executeQuery();
    		//rs.next();
    		//String kdjen = rs.getString("KDJENMSPST");
    		stmt = con.prepareStatement("INSERT INTO CIVITAS(ID_OBJ,KDPTIMSMHS,KDJENMSMHS,KDPSTMSMHS,NPMHSMSMHS,NMMHSMSMHS,TPLHRMSMHS,TGLHRMSMHS,KDJEKMSMHS,TAHUNMSMHS,SMAWLMSMHS,STPIDMSMHS,NIMHSMSMHS)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
    		stmt.setLong(1, Long.valueOf(id_obj).longValue());
    		stmt.setString(2,Constants.getKdpti());
    		//stmt.setString(3, kdjen);
    		if(Checker.isStringNullOrEmpty(kdjen)) {
    			stmt.setNull(3, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(3, kdjen.toUpperCase());
    		}
      		if(Checker.isStringNullOrEmpty(kdpst)) {
    			stmt.setNull(4, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(4, kdpst.toUpperCase());
    		}
    		stmt.setString(5,npm);
    		
    		stmt.setString(6, nmmhs.toUpperCase());
    		if(Checker.isStringNullOrEmpty(tplhr)) {
    			stmt.setNull(7, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(7, tplhr.toUpperCase());
    		}
    		
    		//////System.out.println("tgl lahir ="+tglhr);
    		if(Checker.isStringNullOrEmpty(tglhr)) {
    			stmt.setNull(8, java.sql.Types.DATE);
    		}
    		else {
    			stmt.setDate(8, java.sql.Date.valueOf(tglhr));
    		}
    		if(Checker.isStringNullOrEmpty(kdjek)) {
    			stmt.setNull(9, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(9, kdjek.toUpperCase());
    		}
    		
    		stmt.setString(10,thsms.substring(0,4));
    		stmt.setString(11,thsms);
    		stmt.setString(12, stpid.toUpperCase());
    		//////System.out.println("nimhs ="+nimhs);
    		if(Checker.isStringNullOrEmpty(nimhs)) {
    			
    			stmt.setNull(13, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(13, nimhs);
    		}
    		ins = stmt.executeUpdate();
    		stmt = con.prepareStatement("INSERT INTO EXT_CIVITAS(KDPSTMSMHS,NPMHSMSMHS,EMAILMSMHS,NEGLHMSMHS,NOHPEMSMHS,AGAMAMSMHS)VALUES(?,?,?,?,?,?)");
    		stmt.setString(1, kdpst);
    		stmt.setString(2, npm);
    		if(Checker.isStringNullOrEmpty(email)) {	
    			stmt.setNull(3, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(3, email);
    		}
    		if(Checker.isStringNullOrEmpty(nglhr)) {	
    			stmt.setNull(4, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(4, nglhr);
    		}
    		if(Checker.isStringNullOrEmpty(nohpe)) {	
    			stmt.setNull(5, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(5, nohpe);
    		}
    		if(Checker.isStringNullOrEmpty(agama)) {	
    			stmt.setNull(6, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(6, agama.toUpperCase());
    		}
    		ins = ins + stmt.executeUpdate();
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
    	boolean sukses = false;
    	if(ins>0) {
    		sukses = true;
    	}
    	return sukses;	
    }

    // with aspti
    public String insertCivitasSimpleGivenSmawl(String smawl,String id_obj,String kdpst,String nmmhs,String nimhs,String kdjek, String stpid, String agama,String tplhr,String nglhr, String tglhr,String email,String nohpe,String aspti,String shift) {
    	/*
    	 * return npmhs, null bila gagal
    	 */
    	//kdjen ngga dipake
    	//////System.out.println("oonohpe="+nohpe);
    	int ins = 0;
    	String npm =null,kdjen=null;
    	try {
    		String thsms = ""+smawl;
    		npm = generateNpm(thsms,kdpst);
    		kdjen = getKdjen(kdpst);
    		//////System.out.println("shift="+shift);
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("INSERT INTO CIVITAS(ID_OBJ,KDPTIMSMHS,KDJENMSMHS,KDPSTMSMHS,NPMHSMSMHS,NMMHSMSMHS,TPLHRMSMHS,TGLHRMSMHS,KDJEKMSMHS,TAHUNMSMHS,SMAWLMSMHS,STPIDMSMHS,NIMHSMSMHS,ASPTIMSMHS,SHIFTMSMHS)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    		stmt.setLong(1, Long.valueOf(id_obj).longValue());
    		stmt.setString(2,Constants.getKdpti());
    		//stmt.setString(3, kdjen);
    		if(Checker.isStringNullOrEmpty(kdjen)) {
    			stmt.setNull(3, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(3, kdjen.toUpperCase());
    		}
      		if(Checker.isStringNullOrEmpty(kdpst)) {
    			stmt.setNull(4, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(4, kdpst.toUpperCase());
    		}
    		stmt.setString(5,npm);
    		
    		stmt.setString(6, nmmhs.toUpperCase());
    		if(Checker.isStringNullOrEmpty(tplhr)) {
    			stmt.setNull(7, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(7, tplhr.toUpperCase());
    		}
    		
    		//////System.out.println("tgl lahir ="+tglhr);
    		if(Checker.isStringNullOrEmpty(tglhr)) {
    			stmt.setNull(8, java.sql.Types.DATE);
    		}
    		else {
    			stmt.setDate(8, java.sql.Date.valueOf(tglhr));
    		}
    		if(Checker.isStringNullOrEmpty(kdjek)) {
    			stmt.setNull(9, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(9, kdjek.toUpperCase());
    		}
    		stmt.setString(10,thsms.substring(0,4));
    		stmt.setString(11,thsms);
    		stmt.setString(12, stpid.toUpperCase());
    		//////System.out.println("nimhs ="+nimhs);
    		if(Checker.isStringNullOrEmpty(nimhs)) {
    			
    			stmt.setNull(13, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(13, nimhs);
    		}
    		//////System.out.println("aspti dbase ="+aspti);
    		if(Checker.isStringNullOrEmpty(aspti)) {
    			
    			stmt.setNull(14, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(14, aspti);
    		}
    		
    		if(Checker.isStringNullOrEmpty(shift)) {
    			stmt.setString(15, "N/A");//ngga boleh null
    			//stmt.setNull(15, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(15, shift);
    		}
    		ins = stmt.executeUpdate();
    		stmt = con.prepareStatement("INSERT INTO EXT_CIVITAS(KDPSTMSMHS,NPMHSMSMHS,EMAILMSMHS,NEGLHMSMHS,NOHPEMSMHS,AGAMAMSMHS)VALUES(?,?,?,?,?,?)");
    		stmt.setString(1, kdpst);
    		stmt.setString(2, npm);
    		if(Checker.isStringNullOrEmpty(email)) {	
    			stmt.setNull(3, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(3, email);
    		}
    		if(Checker.isStringNullOrEmpty(nglhr)) {	
    			stmt.setNull(4, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(4, nglhr);
    		}
    		if(Checker.isStringNullOrEmpty(nohpe)) {	
    			stmt.setNull(5, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(5, nohpe);
    		}
    		if(Checker.isStringNullOrEmpty(agama)) {	
    			stmt.setNull(6, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(6, agama.toUpperCase());
    		}
    		
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
    	boolean sukses = false;
    	if(ins>0) {
    		sukses = true;
    	}
    	else {
    		npm=null;
    	}
    	return npm;	
    }
    
    public String create2tokenRandomString() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replace("-","");
		//////System.out.println(uuid);
		Vector v = new Vector();
		ListIterator li = v.listIterator();
		for(int i=0;i<uuid.length();i++) {
			int j = i+1;
			if(j<uuid.length()) {
				li.add(uuid.substring(i, j));
			}
		}
		String nuid="";
		Collections.shuffle(v);
		li = v.listIterator();
		while(li.hasNext()) {
			nuid=nuid+(String)li.next();
		}
		String usrPed = nuid.substring(0,8)+" "+nuid.substring(10,18);
		return usrPed;
    }
    
    public String createRandomUserPwd(String kdpst, String npmhs) {
    	String tknString = null;
    	try {
    		boolean pass = false;
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT * from USR_DAT where USR_NAME=?");
    		tknString = create2tokenRandomString();
    		StringTokenizer st = new StringTokenizer(tknString);
    		String usr = st.nextToken();
    		String pwd = st.nextToken();
    		stmt.setString(1, usr);
    		rs = stmt.executeQuery();
    		while(!pass) {
    			if(!rs.next()) {
    				pass = true;
    			}
    			else {
    				tknString = create2tokenRandomString();
            		st = new StringTokenizer(tknString);
            		usr = st.nextToken();
            		pwd = st.nextToken();
            		stmt.setString(1, usr);
            		rs = stmt.executeQuery();
        			if(!rs.next()) {
        				pass = true;
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
    	return tknString;
    }
    
    public String insertUsrDatTable(String tknUsrPwd) {
    	String id=null;
    	try {
    		StringTokenizer st = new StringTokenizer(tknUsrPwd);
    		String usr = st.nextToken();
    		String pwd = st.nextToken();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt=con.prepareStatement("INSERT INTO USR_DAT(SCHEMA_OWNER,USR_NAME,USR_PWD,FWD_ADDR)VALUES(?,?,?,?)");
    		stmt.setString(1, Constants.getDbschema().replace("/", ""));
    		stmt.setString(2, usr);
    		stmt.setString(3, pwd);
    		stmt.setString(4, Constants.getInitCivFwdAddr());
    		int i = stmt.executeUpdate();
    		if(i>0) {
    			stmt=con.prepareStatement("select ID from USR_DAT where USR_NAME=?");
    			stmt.setString(1,usr);
    			rs = stmt.executeQuery();
    			rs.next();
    			id = ""+rs.getLong("ID");
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
    	return id;
    }
    
    public void insertCivitasJadwalBridgeMhsBaru(String tknUjian,String kdpst,String npmhs) {
    	/*
    	 * format tknUjian
    	 * idOnlineTest+"$$"+kodeNamaTest+"$$"+keterTest+"$$"+totSoal+"$$"+totTime+"$$"+passGrade+"$$"+idJadwalTest+"$$"+jadwalTest+"$$"+rilTestTimeStart+"$$"+rilTestTimeEnd+"$$"+canceled+"$$"+done+"$$"+inprogress+"$$"+pause+"$$"+npmOper+"$$"+nmmOper+"$$"+totMhs+"$$"+room+"$$"+ipAllow+"$$"+npmOprAllow+"$$"+note+"$$"+reusable+"||";
    	 */
    	
    	
    	try {
    		//////System.out.println("process civitas ujian bridge");
    		//////System.out.println("process tknUjian="+tknUjian);
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt=con.prepareStatement("INSERT INTO CIVITAS_JADWAL_BRIDGE(ID_JADWAL_TEST,KDPST,NPMHS,WAJIB)VALUES(?,?,?,?)");
    		if(tknUjian!=null && !Checker.isStringNullOrEmpty(tknUjian)) {
    			StringTokenizer st = new StringTokenizer(tknUjian,"||");
    			while(st.hasMoreTokens()) {
    				//idOnlineTest+"$$"+kodeNamaTest+"$$"+keterTest+"$$"+totSoal+"$$"+totTime+"$$"+passGrade+"$$"+idJadwalTest
    				String tkn = st.nextToken();
    				StringTokenizer st1 = new StringTokenizer(tkn,"$$");
    				st1.nextToken();st1.nextToken();st1.nextToken();st1.nextToken();st1.nextToken();st1.nextToken();
    				String idJadwalTest = st1.nextToken();
    				//////System.out.println("========");
    				//////System.out.println(tkn);
    				//////System.out.println(idJadwalTest);
    				stmt.setLong(1, Long.valueOf(idJadwalTest).longValue());
    				stmt.setString(2, kdpst);
    				stmt.setString(3, npmhs);
    				stmt.setBoolean(4, true);
    				int i = stmt.executeUpdate();
    				if(i<1) {
    					////System.out.println("gagal input process insertCivitasJadwalBridgeMhsBaru(String tknUjian,String kdpst,String npmhs)");
    				}
    				//cek apa sdh terdaftar pada idOnlineTest 
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
    	//return id;
    }
 
    public String resetUsrPwd(String kdpst,String npmhs) {
    	String tknUsrPwd = createRandomUserPwd(kdpst, npmhs);
    	
    	String idUsrDat = insertUsrDatTable(tknUsrPwd);
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("UPDATE CIVITAS SET ID=? where KDPSTMSMHS=? and NPMHSMSMHS=?");
    		stmt.setLong(1,Long.valueOf(idUsrDat).longValue());
    		stmt.setString(2,kdpst);
    		stmt.setString(3,npmhs);
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
    	return tknUsrPwd;
    }
    
    
    public void deletePrevRecTbkmk(String thsms,String kdpst) {
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("delete from TBKMK where THSMSTBKMK=? and KDPSTTBKMK=?");
    		stmt.setString(1,thsms);
    		stmt.setString(2,kdpst);
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
    }

    public void setKurikulumSatus(Vector v_token_status_kurikulum,String thsms,String kdpst){
    	ListIterator li = v_token_status_kurikulum.listIterator();
    	String kur_status = "";
    	while(li.hasNext()) {
    		kur_status = kur_status+(String)li.next();
    		if(li.hasNext()) {
    			kur_status=kur_status+" ";
    		}
    	}
    	try {	
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("update TBKMK set STKURTBKMK=? where THSMSTBKMK=? and KDPSTTBKMK=?");
    		stmt.setString(1,kur_status);
    		stmt.setString(2, thsms);
    		stmt.setString(3, kdpst);
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
    }
   
    public void setPengampuKodeDanJenisMataKuliah(Vector v_nokur_stkur_sms_kdkmk_nakmk_sks_kodekelompok_keterkelompok_kodejenis_keterjenis_tkn_dosen, String thsms,String kdpst) {
    	//liv.add(nokur+","+stkur+","+sms+","+kdkmk+","+nakmk+","+sks+","+kode_kelompok+","+keter_kelompok+","+kode_jenis+","+keter_jenis+","+tkn_dosen);
    	//////System.out.println("masuk");
    	try {	
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//stmt = con.prepareStatement("update TBKMK set KDKELTBKMK=?,KDWPLTBKMK=?,NODOSTBKMK=? where THSMSTBKMK=? and KDPSTTBKMK=? and KDKMKTBKMK=? and NAKMKTBKMK=? and SEMESTBKMK=? and SKSMKTBKMK=? and TOKURTBKMK like ?");
    		stmt = con.prepareStatement("update TBKMK set KDKELTBKMK=? where THSMSTBKMK=? and KDPSTTBKMK=? and KDKMKTBKMK=? and NAKMKTBKMK=? and SEMESTBKMK=? and SKSMKTBKMK=? and TOKURTBKMK like ?");
    		String tmp_kdkel=null,tmp_kdwpl=null,tmp_dos=null;
    		ListIterator li = v_nokur_stkur_sms_kdkmk_nakmk_sks_kodekelompok_keterkelompok_kodejenis_keterjenis_tkn_dosen.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,",");
    			String nokur = null;
    			String stkur = null;
			
    			if(st.countTokens()==2) {
    				nokur = st.nextToken();
        			stkur = st.nextToken();
    			}
    			else {
    				nokur = st.nextToken();
        			stkur = st.nextToken();
    				String semes = st.nextToken();
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String sksmk = st.nextToken();
    				String kodeKelompok = st.nextToken();
    				String keterKelompok = st.nextToken();
    				String kodeJenis = st.nextToken();
    				String keterJenis = st.nextToken();
    				String tknDosen = st.nextToken();
    				if(!kodeKelompok.equalsIgnoreCase("null")) {
    					stmt.setString(1, kodeKelompok);
    					stmt.setString(2, thsms);
    					stmt.setString(3, kdpst);
    					stmt.setString(4, kdkmk);
    					stmt.setString(5, nakmk);
    					stmt.setString(6, semes);
    					stmt.setDouble(7, Double.valueOf(sksmk).doubleValue());
    					stmt.setString(8, "%"+nokur+"%");
    					stmt.executeUpdate();
    					//////System.out.println("kdkel "+thsms+" "+kdpst+" "+kdkmk+" "+nakmk);
    					//////System.out.println(kodeKelompok+" "+kodeJenis+" ="+stmt.executeUpdate());
    				}	
    			}	
    		} 
    		
    		//stmt = con.prepareStatement("update TBKMK set KDKELTBKMK=?,KDWPLTBKMK=?,NODOSTBKMK=? where THSMSTBKMK=? and KDPSTTBKMK=? and KDKMKTBKMK=? and NAKMKTBKMK=? and SEMESTBKMK=? and SKSMKTBKMK=? and TOKURTBKMK like ?");
    		stmt = con.prepareStatement("update TBKMK set KDWPLTBKMK=? where THSMSTBKMK=? and KDPSTTBKMK=? and KDKMKTBKMK=? and NAKMKTBKMK=? and SEMESTBKMK=? and SKSMKTBKMK=? and TOKURTBKMK like ?");
    		li = v_nokur_stkur_sms_kdkmk_nakmk_sks_kodekelompok_keterkelompok_kodejenis_keterjenis_tkn_dosen.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,",");
    			String nokur = null;
    			String stkur = null;
			
    			if(st.countTokens()==2) {
    				nokur = st.nextToken();
        			stkur = st.nextToken();
    			}
    			else {
    				nokur = st.nextToken();
        			stkur = st.nextToken();
    				String semes = st.nextToken();
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String sksmk = st.nextToken();
    				String kodeKelompok = st.nextToken();
    				String keterKelompok = st.nextToken();
    				String kodeJenis = st.nextToken();
    				String keterJenis = st.nextToken();
    				String tknDosen = st.nextToken();
    				if(!kodeJenis.equalsIgnoreCase("null")) {
    					stmt.setString(1, kodeJenis);
       					stmt.setString(2, thsms);
    					stmt.setString(3, kdpst);
    					stmt.setString(4, kdkmk);
    					stmt.setString(5, nakmk);
    					stmt.setString(6, semes);
    					stmt.setDouble(7, Double.valueOf(sksmk).doubleValue());
    					stmt.setString(8, "%"+nokur+"%");
    					stmt.executeUpdate();
    					//////System.out.println("kdwpl "+thsms+" "+kdpst+" "+kdkmk+" "+nakmk);
    					//////System.out.println(kodeKelompok+" "+kodeJenis+" ="+stmt.executeUpdate());
    				}	
    			}	
    		}

    		stmt = con.prepareStatement("update TBKMK set NODOSTBKMK=? where THSMSTBKMK=? and KDPSTTBKMK=? and KDKMKTBKMK=? and NAKMKTBKMK=? and SEMESTBKMK=? and SKSMKTBKMK=? and TOKURTBKMK like ?");
    		li = v_nokur_stkur_sms_kdkmk_nakmk_sks_kodekelompok_keterkelompok_kodejenis_keterjenis_tkn_dosen.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,",");
    			String nokur = null;
    			String stkur = null;
			
    			if(st.countTokens()==2) {
    				nokur = st.nextToken();
        			stkur = st.nextToken();
    			}
    			else {
    				nokur = st.nextToken();
        			stkur = st.nextToken();
    				String semes = st.nextToken();
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String sksmk = st.nextToken();
    				String kodeKelompok = st.nextToken();
    				String keterKelompok = st.nextToken();
    				String kodeJenis = st.nextToken();
    				String keterJenis = st.nextToken();
    				String tknDosen = st.nextToken();
    				StringTokenizer st2 = new StringTokenizer(tknDosen);
    				if(!tknDosen.equalsIgnoreCase("null") && st2!=null && st2.countTokens()>0) {
    					stmt.setString(1, tknDosen);
    					stmt.setString(2, thsms);
    					stmt.setString(3, kdpst);
    					stmt.setString(4, kdkmk);
    					stmt.setString(5, nakmk);
    					stmt.setString(6, semes);
    					stmt.setDouble(7, Double.valueOf(sksmk).doubleValue());
    					stmt.setString(8, "%"+nokur+"%");
    					stmt.executeUpdate();
    					//////System.out.println("dos "+thsms+" "+kdpst+" "+kdkmk+" "+nakmk);
    					//////System.out.println(kodeKelompok+" "+kodeJenis+" ="+stmt.executeUpdate());
    				}	
    			}	
    			//THSMSTBKMK=? and KDPSTTBKMK=? and KDKMKTBKMK=? and NAKMKTBKMK=? and SEMESTTBKMK=? and SKSMKTBKMK=? and TOKURTBKMK like ?");
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
               		////System.out.println(ignore);
            	}
            }
        }
    }
    
    public void setKodeKurikulumPerMk(Vector v_semes_sksmk_kdkmk_nakmk_listkur,String thsms,String kdpst){
    	ListIterator li = v_semes_sksmk_kdkmk_nakmk_listkur.listIterator();
    	try {	
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("update TBKMK set TOKURTBKMK=? where THSMSTBKMK=? and KDPSTTBKMK=? and KDKMKTBKMK=? and NAKMKTBKMK=? and SKSMKTBKMK=? and SEMESTBKMK=?");
    		while(li.hasNext()) {
    			String baris = (String)li.next();
    			StringTokenizer st = new StringTokenizer(baris,",");
    			while(st.hasMoreTokens()){
    				String semes = st.nextToken();
    				String sksmk = st.nextToken();
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String liskur = st.nextToken();
    				stmt.setString(1,liskur);
    				stmt.setString(2,thsms);
    				stmt.setString(3,kdpst);
    				stmt.setString(4,kdkmk);
    				stmt.setString(5,nakmk);
    				stmt.setDouble(6,Double.valueOf(sksmk).doubleValue());
    				stmt.setString(7,semes);
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
    
    public void setStatusMkTo(Vector v_thsms_kdpst_semes_kdkmk_nakmk_kdkel_kdwpl_skstm_skspr_skslp_sksmk_nodos_tkndos, String aktif_hapus) {
    	ListIterator litmp = v_thsms_kdpst_semes_kdkmk_nakmk_kdkel_kdwpl_skstm_skspr_skslp_sksmk_nodos_tkndos.listIterator();
    	try {	
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("update TBKMK set STKMKTBKMK=? where THSMSTBKMK=? and KDPSTTBKMK=? and KDKMKTBKMK=? and NAKMKTBKMK=? and SKSMKTBKMK=? and SEMESTBKMK=?");
    		while(litmp.hasNext()){
    			String brs = (String)litmp.next();
    			StringTokenizer st = new StringTokenizer(brs,",");
    			String thsms = st.nextToken();
    			String kdpst = st.nextToken();
    			String semes = st.nextToken();
    			String kdkmk = st.nextToken();
    			String nakmk = st.nextToken();
    			String kdkel = st.nextToken();
    			String kdwpl = st.nextToken();
    			String skstm = st.nextToken();
    			String skspr = st.nextToken();
    			String skslp = st.nextToken();
    			String sksmk = st.nextToken();
    			String nodos = st.nextToken();
    			//String tkndos = st.nextToken();
    			stmt.setString(1,aktif_hapus);
    			stmt.setString(2,thsms);
    			stmt.setString(3,kdpst);
    			stmt.setString(4,kdkmk);
    			stmt.setString(5,nakmk);
    			stmt.setDouble(6,Double.valueOf(sksmk).doubleValue());
    			stmt.setString(7,semes);
    			stmt.executeUpdate();
    			//////System.out.println("set "+aktif_hapus+" "+kdkmk+" "+nakmk+" ="+stmt.executeUpdate());
    			//////System.out.println(brs);
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
    
    public void insertInitTbkmk(String thsms,String kdpst,Vector v_kdkmk_sksmk_smsmk_nakmk) {
    	Collections.sort(v_kdkmk_sksmk_smsmk_nakmk);
    	ListIterator li = v_kdkmk_sksmk_smsmk_nakmk.listIterator();
    	try {
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("INSERT INTO  TBKMK (THSMSTBKMK,KDPSTTBKMK,KDKMKTBKMK,NAKMKTBKMK,SKSMKTBKMK,SEMESTBKMK) VALUES (?,?,?,?,?,?)");
    		while(li.hasNext()) {
				String baris = (String)li.next();
				StringTokenizer st = new StringTokenizer(baris);
				String smsmk = st.nextToken();
				String kdkmk = st.nextToken();
				//////System.out.println(kdkmk);
				String sksmk = st.nextToken();
				String nakmk = "";
				while(st.hasMoreTokens()) {
					nakmk = nakmk + st.nextToken();
					if(st.hasMoreTokens()) {
						nakmk = nakmk+" ";
					}
				}
				stmt.setString(1, thsms);
				stmt.setString(2, kdpst);
				stmt.setString(3, kdkmk);
				stmt.setString(4, nakmk);
				stmt.setDouble(5, Double.valueOf(sksmk).doubleValue());
				stmt.setString(6, smsmk);
				stmt.executeUpdate();
				//////System.out.println(baris);
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

    public void deleteFromMakur(String idkur) {
    	try {
    		/*
        	 * delete  record
        	 */
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("delete from MAKUR where IDKURMAKUR=?");
    		stmt.setInt(1, Integer.valueOf(idkur).intValue());
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
    }	
    
    public void updateMakur(Vector vIdkurIdkmkSmsmk) {
    	
    	ListIterator li = vIdkurIdkmkSmsmk.listIterator();
    	try {
    		/*
        	 * delete prev record
        	 */
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("delete from MAKUR where IDKURMAKUR=?");
    		while(li.hasNext()) {
    			String idkur = (String)li.next();
    			String idkmk = (String)li.next();
    			String smsmk = (String)li.next();
    			//////System.out.println("upd makue="+idkur+","+idkmk+","+smsmk);
    			stmt.setInt(1, Integer.valueOf(idkur).intValue());
    			
    			stmt.executeUpdate();
    		}
    		/*
        	 * insert new record
        	 */
    		stmt = con.prepareStatement("insert into MAKUR (IDKMKMAKUR,IDKURMAKUR,SEMESMAKUR) values (?,?,?)");
    		li = vIdkurIdkmkSmsmk.listIterator();
    		while(li.hasNext()) {
    			String idkur = (String)li.next();
    			String idkmk = (String)li.next();
    			String smsmk = (String)li.next();
    			stmt.setInt(1, Integer.valueOf(idkmk).intValue());
    			stmt.setInt(2, Integer.valueOf(idkur).intValue());
    			if(smsmk!=null && !Checker.isStringNullOrEmpty(smsmk)) {
    				stmt.setString(3,smsmk);
    			}
    			else {
    				stmt.setNull(3,java.sql.Types.VARCHAR);
    			}
    			stmt.executeUpdate();
    		}	
    		//hitung totsks & tot sms lalu update makur
    		stmt=con.prepareStatement("select * from MAKUR inner join MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURMAKUR=?");
    		li = vIdkurIdkmkSmsmk.listIterator();
    		String id="";
    		Vector vsms = null;
    		int skstt = 0;
    		while(li.hasNext()) {
    			String idkur = (String)li.next();
    			id=""+idkur;
    			String idkmk = (String)li.next();
    			String smsmk = (String)li.next();
    			if(smsmk!=null && !Checker.isStringNullOrEmpty(smsmk)) {
    				;
    			}
    			else {
    				smsmk="1";
    			}
    			stmt.setLong(1, Long.valueOf(idkur).longValue());
    			rs = stmt.executeQuery();
    			vsms = new Vector();
    			ListIterator lisms = vsms.listIterator();
    			skstt = 0;
    			while(rs.next()) {
    				skstt = skstt+(rs.getInt("SKSTMMAKUL")+rs.getInt("SKSPRMAKUL")+rs.getInt("SKSLPMAKUL"));
    				String smsmakul = rs.getString("SEMESMAKUR");
    				if(smsmakul==null || Checker.isStringNullOrEmpty(smsmakul)) {
    					smsmakul="1";
    				}
    				lisms.add(smsmakul);
    			}
    		}
    		
    		int smstt = 0;
    		if(vsms!=null) {
    			vsms = Tool.removeDuplicateFromVector(vsms);
    			smstt = vsms.size();
    		}
    		stmt=con.prepareStatement("update KRKLM set SKSTTKRKLM=?,SMSTTKRKLM=? where IDKURKRKLM=?");
    		stmt.setInt(1,skstt);
    		stmt.setInt(2,smstt);
    		stmt.setLong(3,Long.valueOf(id).longValue());
    		stmt.executeUpdate();
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
    
    
    public void updateMakurMkKelulusan(Vector vIdkurIdkmkSmsmk) {
    	
    	ListIterator li = vIdkurIdkmkSmsmk.listIterator();
    	try {
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("update MAKUR set FINAL_MK=? where IDKMKMAKUR=? and IDKURMAKUR=?");
    		while(li.hasNext()) {
    			String idkur = (String)li.next();
    			String idkmk = (String)li.next();
    			String smsmk = (String)li.next();
    			//////System.out.println("upd makue="+idkur+","+idkmk+","+smsmk);
    			stmt.setBoolean(1, true);
    			stmt.setInt(2, Integer.valueOf(idkmk).intValue());
    			stmt.setInt(3, Integer.valueOf(idkur).intValue());
    			stmt.executeUpdate();
    		}
    		
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
 
    
    public void updateKrs(Vector vInfo,String npm) {
    	//linfo.add(TargetThsmsKrs+","+idkmk[i]+","+npm+","+kdpst+","+obj_lvl);
    	/*
    	 * fungsi ini menghapus prev record di trnlm baru kemudian insert yg baru
    	 */
    	if(vInfo!=null && vInfo.size()>0) {
    		try {
        		/*
            	 * delete prev record
            	 */
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		//get default shift
        		stmt = con.prepareStatement("select SHIFTMSMHS from CIVITAS where NPMHSMSMHS=?");
        		stmt.setString(1, npm);
        		rs = stmt.executeQuery();
        		rs.next();
        		String shiftKelas = rs.getString("SHIFTMSMHS");
        		if(shiftKelas==null) {
        			shiftKelas="N/A";
        		}
        		
        		ListIterator linfo = vInfo.listIterator();
        		//get target thsms
        		//hapus prev record cukup dgn if krn thsms sama utk tiap recordnya;
        		stmt = con.prepareStatement("delete from TRNLM where THSMSTRNLM=? and KDPSTTRNLM=? and NPMHSTRNLM=?");
        		//////System.out.println("pre-delete");
        		String thsms=null,kdpst=null,npmhs=null;
        		
        		if(linfo.hasNext()) {
        			String baris = (String)linfo.next();
        			//<%=idkmk %>,<%=noKlsPll %>,<%= currStatus%>,<%=npmdos %>,<%=npmasdos %>
        			StringTokenizer st = new StringTokenizer(baris,",");
        			thsms = st.nextToken();
        			String idkmk = st.nextToken();
        			String noKlsPll = st.nextToken();
        			String currStatus = st.nextToken();
        			String npmdos = st.nextToken();
        			String npmasdos = st.nextToken();
        			npmhs = st.nextToken();
        			kdpst = st.nextToken();
        			String objLvl = st.nextToken();
        			stmt.setString(1,thsms);
        			stmt.setString(2,kdpst);
        			stmt.setString(3,npmhs);
        			stmt.executeUpdate();
        		}
        		//////System.out.println("post-delete");
        		
        		
        		//insert
        		Vector v2 = new Vector();
        		ListIterator li2 = v2.listIterator();
        		stmt = con.prepareStatement("select * from MAKUL where IDKMKMAKUL=?");
        		linfo = vInfo.listIterator();
        		//////System.out.println("udb vinfo "+vInfo.size());
        		while(linfo.hasNext()) {
        			String baris = (String)linfo.next();
        			StringTokenizer st = new StringTokenizer(baris,",");
        			thsms = st.nextToken();
        			String idkmk = st.nextToken();
        			String noKlsPll = st.nextToken();
        			String currStatus = st.nextToken();
        			String npmdos = st.nextToken();
        			String npmasdos = st.nextToken();
        			npmhs = st.nextToken();
        			kdpst = st.nextToken();
        			String objLvl = st.nextToken();
        			stmt.setInt(1, Integer.valueOf(idkmk).intValue());
        			rs = stmt.executeQuery();
        			String kdkmk=null;
        			int sksmk=0;
        			//////System.out.println("prep-");
        			if(rs.next()) {
        				kdkmk = rs.getString("KDKMKMAKUL");
        				sksmk = rs.getInt("SKSTMMAKUL")+rs.getInt("SKSPRMAKUL")+rs.getInt("SKSLPMAKUL"); 
        			}
        			else {
        				kdkmk="N/A";
        			}
        			li2.add(thsms);
        			li2.add(idkmk);
        			li2.add(npmhs);
        			li2.add(kdpst);
        			li2.add(objLvl);
        			li2.add(kdkmk);
        			li2.add(sksmk+"");
        			//======
        			li2.add(noKlsPll);
        			li2.add(currStatus);
        			li2.add(npmdos);
        			li2.add(npmasdos);
        		}	
        		
        		//THSMSTRNLM,KDPTITRNLM,KDJENTRNLM,KDPSTTRNLM,NPMHSTRNLM,KDKMKTRNLM,SKSMKTRNLM
        		//////System.out.println("pre-insert");
        		stmt=con.prepareStatement("select KDJENMSPST from MSPST where KDPSTMSPST=?");
        		stmt.setString(1, kdpst);
        		rs = stmt.executeQuery();
        		rs.next();
        		String kdjen = rs.getString("KDJENMSPST");
        											   

        		stmt = con.prepareStatement("insert into TRNLM(THSMSTRNLM,KDPTITRNLM,KDJENTRNLM,KDPSTTRNLM,NPMHSTRNLM,KDKMKTRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,KELASTRNLM,SHIFTTRNLM,IDKMKTRNLM)values(?,?,?,?,?,?,?,?,?,?,?,?) ");
        		li2 = v2.listIterator();
        		while(li2.hasNext()) {
        			//////System.out.println("pre-insert");
        			thsms = (String)li2.next();
        			String idkmk = (String)li2.next();
        			npmhs = (String)li2.next();
        			kdpst = (String)li2.next();
        			String objLvl = (String)li2.next();
        			String kdkmk = (String)li2.next();
        			String sksmk = (String)li2.next();
        			String noKlsPll = (String)li2.next();
        			if(noKlsPll==null || Checker.isStringNullOrEmpty(noKlsPll)) {
        				noKlsPll="00";
        			}
        			String currStatus = (String)li2.next();
        			String npmdos = (String)li2.next();
        			String npmasdos = (String)li2.next();
        			//1THSMSTRNLM,2KDPTITRNLM,3KDJENTRNLM,4KDPSTTRNLM,5NPMHSTRNLM,6KDKMKTRNLM,7NLAKHTRNLM,8BOBOTTRNLM,9SKSMKTRNLM,10KELASTRNLM,11SHIFTTRNLM
        			stmt.setString(1,thsms);
        			stmt.setString(2,Constants.getKdpti());
        			//stmt.setNull(3,java.sql.Types.VARCHAR);
        			stmt.setString(3,kdjen.toUpperCase());
        			stmt.setString(4,kdpst);
        			stmt.setString(5,npmhs);
        			stmt.setString(6,kdkmk);
        			stmt.setString(7,"T");
        			stmt.setDouble(8,0);
        			stmt.setInt(9,Integer.valueOf(sksmk).intValue());
        			stmt.setString(10,noKlsPll);
        			stmt.setString(11, shiftKelas);
        			if(idkmk!=null && idkmk.equalsIgnoreCase("0")) {
        				stmt.setNull(12, java.sql.Types.INTEGER);
        			}
        			else {
        				if(Checker.isStringNullOrEmpty(idkmk)) {
        					stmt.setNull(12, java.sql.Types.INTEGER);
        				}
        				else {
        					stmt.setInt(12,Integer.valueOf(idkmk).intValue());
        				}
        			}	
        			stmt.executeUpdate();
        		}
        		//harus insert trakm juga
        		//delete prev record
        		stmt = con.prepareStatement("delete from TRAKM where THSMSTRAKM=? and KDPSTTRAKM=? and NPMHSTRAKM=?");
        		stmt.setString(1, thsms);
        		stmt.setString(2, kdpst);
        		stmt.setString(3, npmhs);
        		stmt.executeUpdate();
        		//insert fresh record
        		stmt = con.prepareStatement("INSERT into TRAKM(THSMSTRAKM,KDPTITRAKM,KDJENTRAKM,KDPSTTRAKM,NPMHSTRAKM,SKSEMTRAKM,NLIPSTRAKM,SKSTTTRAKM,NLIPKTRAKM)values(?,?,?,?,?,?,?,?,?)");
        		stmt.setString(1, thsms);
        		stmt.setString(2, Constants.getKdpti());
        		stmt.setNull(3,java.sql.Types.VARCHAR);
        		stmt.setString(4, kdpst);
        		stmt.setString(5, npmhs);
        		stmt.setInt(6, 0);
        		stmt.setDouble(7, 0);
        		stmt.setInt(8, 0);
        		stmt.setDouble(9, 0);
        		stmt.executeUpdate();
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
    					////System.out.println(ignore);
    				}
    			}
    		}
    	}	
    }

    
    public void addMakulKeKrs(Vector vInfo,String npm) {
    	//linfo.add(TargetThsmsKrs+","+idkmk[i]+","+npm+","+kdpst+","+obj_lvl);
    	/*
    	 * fungsi ini menghapus prev record di trnlm baru kemudian insert yg baru
    	 */
    	if(vInfo!=null && vInfo.size()>0) {
    		try {
        		/*
            	 * delete prev record
            	 */
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//get default shift
        		stmt = con.prepareStatement("select SHIFTMSMHS from CIVITAS where NPMHSMSMHS=?");
        		stmt.setString(1, npm);
        		rs = stmt.executeQuery();
        		rs.next();
        		String shiftKelas = rs.getString("SHIFTMSMHS");
        		if(shiftKelas==null) {
        			shiftKelas="N/A";
        		}
        		
        		
        		ListIterator linfo = vInfo.listIterator();
        		String thsms=null,kdpst=null,npmhs=null;
        		
        		//insert
        		Vector v2 = new Vector();
        		ListIterator li2 = v2.listIterator();
        		stmt = con.prepareStatement("select * from MAKUL where IDKMKMAKUL=?");
        		linfo = vInfo.listIterator();
        		//////System.out.println("udb vinfo "+vInfo.size());
        		while(linfo.hasNext()) {
        			String baris = (String)linfo.next();
        			StringTokenizer st = new StringTokenizer(baris,",");
        			thsms = st.nextToken();
        			String idkmk = st.nextToken();
        			npmhs = st.nextToken();
        			kdpst = st.nextToken();
        			String objLvl = st.nextToken();
        			stmt.setInt(1, Integer.valueOf(idkmk).intValue());
        			rs = stmt.executeQuery();
        			String kdkmk=null;
        			int sksmk=0;
        			//////System.out.println("prep-");
        			if(rs.next()) {
        				kdkmk = rs.getString("KDKMKMAKUL");
        				sksmk = rs.getInt("SKSTMMAKUL")+rs.getInt("SKSPRMAKUL")+rs.getInt("SKSLPMAKUL"); 
        			}
        			else {
        				kdkmk="N/A";
        			}
        			li2.add(thsms);
        			li2.add(idkmk);
        			li2.add(npmhs);
        			li2.add(kdpst);
        			li2.add(objLvl);
        			li2.add(kdkmk);
        			li2.add(sksmk+"");
        		}	
        		
        		//THSMSTRNLM,KDPTITRNLM,KDJENTRNLM,KDPSTTRNLM,NPMHSTRNLM,KDKMKTRNLM,SKSMKTRNLM
        		//////System.out.println("pre-insert");
        		stmt = con.prepareStatement("insert into TRNLM(THSMSTRNLM,KDPTITRNLM,KDJENTRNLM,KDPSTTRNLM,NPMHSTRNLM,KDKMKTRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,KELASTRNLM,SHIFTTRNLM)values(?,?,?,?,?,?,?,?,?,?,?) ");
        		li2 = v2.listIterator();
        		while(li2.hasNext()) {
        			//////System.out.println("pre-insert");
        			thsms = (String)li2.next();
        			String idkmk = (String)li2.next();
        			npmhs = (String)li2.next();
        			kdpst = (String)li2.next();
        			String objLvl = (String)li2.next();
        			String kdkmk = (String)li2.next();
        			String sksmk = (String)li2.next();
        			stmt.setString(1,thsms);
        			stmt.setString(2,Constants.getKdpti());
        			stmt.setNull(3,java.sql.Types.VARCHAR);
        			stmt.setString(4,kdpst);
        			stmt.setString(5,npmhs);
        			stmt.setString(6,kdkmk);
        			stmt.setString(7,"T");
        			stmt.setDouble(8,0);
        			stmt.setInt(9,Integer.valueOf(sksmk).intValue());
        			stmt.setString(10,"00");
        			stmt.setString(11,shiftKelas);
        			stmt.executeUpdate();
        		}
        		//harus insert trakm juga
        		//delete prev record
        		stmt = con.prepareStatement("delete from TRAKM where THSMSTRAKM=? and KDPSTTRAKM=? and NPMHSTRAKM=?");
        		stmt.setString(1, thsms);
        		stmt.setString(2, kdpst);
        		stmt.setString(3, npmhs);
        		stmt.executeUpdate();
        		//insert fresh record
        		stmt = con.prepareStatement("INSERT into TRAKM(THSMSTRAKM,KDPTITRAKM,KDJENTRAKM,KDPSTTRAKM,NPMHSTRAKM,SKSEMTRAKM,NLIPSTRAKM,SKSTTTRAKM,NLIPKTRAKM)values(?,?,?,?,?,?,?,?,?)");
        		stmt.setString(1, thsms);
        		stmt.setString(2, Constants.getKdpti());
        		stmt.setNull(3,java.sql.Types.VARCHAR);
        		stmt.setString(4, kdpst);
        		stmt.setString(5, npmhs);
        		stmt.setInt(6, 0);
        		stmt.setDouble(7, 0);
        		stmt.setInt(8, 0);
        		stmt.setDouble(9, 0);
        		stmt.executeUpdate();
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
    					////System.out.println(ignore);
    				}
    			}
    		}
    	}	
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
			//////System.out.println("ipsta = "+ipsta+"-"+sksta);
			stmt = con.prepareStatement("select * from MAKUL where KDPSTMAKUL=? and KDKMKMAKUL=?");
			liTrnlp = vTrnlp.listIterator();
			while(liTrnlp.hasNext()) {
				String brs =(String)liTrnlp.next();
				StringTokenizer st = new StringTokenizer(brs,"#&");
				String thsms = st.nextToken();
				//////System.out.println("thsms="+thsms);
				String kdkmk = st.nextToken();
				//////System.out.println("kdkmk="+kdkmk);
				String nlakh = st.nextToken();
				//////System.out.println("nlakh="+nlakh);
				String bobot = st.nextToken();
				//////System.out.println("bobot="+bobot);
				String sksmk = st.nextToken();
				//////System.out.println("sksmk="+sksmk);
				String kelas = st.nextToken();
				//////System.out.println("kelas="+kelas);
				stmt.setString(1,"20201");
				stmt.setString(2,kdkmk);
				//////System.out.println("kdkmk="+kdkmk);
				rs = stmt.executeQuery();
				String nakmk = "TIDAK TERDAFTAR";
				if(rs.next()) {
					nakmk = rs.getString("NAKMKMAKUL");
				}
				liTrnlp.set(thsms+"#&"+kdkmk+"#&"+nakmk+"#&"+nlakh+"#&"+bobot+"#&"+sksmk+"#&"+kelas);
				//////System.out.println(thsms+","+kdkmk+","+nakmk+","+nlakh+","+bobot+","+sksmk+","+kelas);

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
					//////System.out.println("semesteran = "+baris);
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
					//////System.out.println("tknThsms ="+tknThsms);
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
							//////System.out.println("first ="+thsms);		
							stmt = con.prepareStatement("select * from TRNLM where THSMSTRNLM=? and KDPSTTRNLM=? and NPMHSTRNLM=?");
							stmt.setString(1,thsms);
							stmt.setString(2,kdpst);
							stmt.setString(3,npmhs);
							rs = stmt.executeQuery();
							//////System.out.println("bbtXnlakhSta awal= "+bbtXnlakhSta+"-"+sksta);
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
							//////System.out.println("bbtXnlakhSta first = "+bbtXnlakhSta+"-"+sksta);
							//////System.out.println("nilai ipk = "+thsms+" "+sksInit+" "+(sksta)+" "+(nilai/sksta));
							//////System.out.println("1. thsms="+thsms);
							//////System.out.println("2. sksta="+sksta);
							//////System.out.println("3. nilai="+nilai);
							//////System.out.println("4. sksta="+sksta);
							if(nilai==0 && sksta==0) {
								lit.add(thsms+" "+sksta+" 0.0");
							}
							else {
								lit.add(thsms+" "+sksta+" "+(nilai/sksta));
							}	
						}
						else {
							tknSqlThsms = "";
							//////System.out.println("i ="+i);							
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
							//////System.out.println("liThsms ="+thsms);
							stmt = con.prepareStatement(sqlCmd);
							stmt.setString(1,kdpst);
							stmt.setString(2,npmhs);
							rs = stmt.executeQuery();
							//////System.out.println("bbtXnlakhSta awal= "+bbtXnlakhSta+"-"+sksta);
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
							
							//////System.out.println("sampe1");
							//boolean barisPertama = true;
							/*
							 * filtering matakuliah yg mengulang, utk bestBobot diambil nilai terbaik
							 */
							String prevKdkmk="";
							if(Constants.getTipePerhitunganTranscript().equalsIgnoreCase("bestBobot")) {
								liTmp = vTmp.listIterator();
								if(liTmp.hasNext()) {
									//process baris pertama
									//////System.out.println("sampe2");
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
										//////System.out.println("sampe3");
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
							//////System.out.println("nilaiTT awal ="+ nilaiTt);
							liTmp = vTmp.listIterator();
							while(liTmp.hasNext()) {
								String baris = (String)liTmp.next();
								//////System.out.println(baris);
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
							//////System.out.println("nilai ipk = "+thsms+" "+sksInit+" "+(skstt)+" "+(pembagi)+" "+(nilaiTt)+" "+(nilaiTt/pembagi));
							
						}
						i = i + 1;
					}
					
					stmt=con.prepareStatement("update TRAKM set SKSTTTRAKM=?,NLIPKTRAKM=? where THSMSTRAKM=? and KDPSTTRAKM=? and NPMHSTRAKM=?");
					lit = vThsmsSksttIpk.listIterator();
					while(lit.hasNext()) {
						String baris = (String)lit.next();
						//////System.out.println("ini-"+baris);
						StringTokenizer stt = new StringTokenizer(baris);
						String thsms = stt.nextToken();
						String skstt = stt.nextToken();
						String ipk = stt.nextToken();
						//////System.out.println("skstt="+skstt);
						stmt.setInt(1,Integer.valueOf(skstt).intValue());
						stmt.setDouble(2,Double.valueOf(ipk).doubleValue());
						stmt.setString(3,thsms);
						stmt.setString(4,kdpst);
						stmt.setString(5,npmhs);
						stmt.executeUpdate();
						
						//////System.out.println("IPK="+baris);
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
					////System.out.println(ignore);
				}
			}
		}
    }

    public int deleteMakul(int idkmkmakul, String ipClient,InitSessionUsr isu) {
    	int i=0;
    	try {
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("delete from MAKUL where IDKMKMAKUL=?");
    		stmt.setInt(1,idkmkmakul);
    		i = stmt.executeUpdate();
    		if(i>0) {
    			insertLogMe(Integer.valueOf(isu.getIdUser()).intValue(),"N/A","DELETE",isu.getFullname()+" berhasil hapus makul dgn id="+idkmkmakul,ipClient);
    		}
    		else {
    			insertLogMe(Integer.valueOf(isu.getIdUser()).intValue(),"N/A","DELETE",isu.getFullname()+" gagal hapus makul dgn id="+idkmkmakul,ipClient);	
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

    
    public int deleteKurikulum(int idkurkrklm, String ipClient,InitSessionUsr isu) {
    	int i=0;
    	try {
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("delete from KRKLM where IDKURKRKLM=?");
    		stmt.setInt(1,idkurkrklm);
    		i = stmt.executeUpdate();
    		if(i>0) {
    			insertLogMe(Integer.valueOf(isu.getIdUser()).intValue(),"N/A","DELETE",isu.getFullname()+" berhasil hapus kurikulum dgn id="+idkurkrklm,ipClient);
    		}
    		else {
    			insertLogMe(Integer.valueOf(isu.getIdUser()).intValue(),"N/A","DELETE",isu.getFullname()+" gagal hapus kurikulum dgn id="+idkurkrklm,ipClient);	
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

    
    public void updateForecast_1(Vector VsdbGetPerkiraanJumlahMhsPerMakul, String targetForcastThsms) {
    	//int i=0;
    	ListIterator li = null;
    	String idkmk = null;
    	String kdkmk = null;
    	String nakmk = null;
    	String skstp = null;
    	String skspr = null;
    	String skslp = null;
    	String kdwpl = null;
    	String jenis = null;
    	String ttmhs = null;
    	String kdpst = null;
    	try {
    		
    		if(VsdbGetPerkiraanJumlahMhsPerMakul!=null && VsdbGetPerkiraanJumlahMhsPerMakul.size()>0) {
    			//hapus prev record dulu
    			Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();

        		stmt = con.prepareStatement("DELETE FROM FORECAST_1 where THSMSFCAST=? AND KDPSTFCAST=? AND IDKMKFCAST=?");
    			li = VsdbGetPerkiraanJumlahMhsPerMakul.listIterator();
    			int i=0;
    			while(li.hasNext()) {
    				String baris9token = (String)li.next();
    				Vector vNpm = (Vector)li.next();
    				Vector vKur = (Vector)li.next();
    				ListIterator liTmp = vNpm.listIterator();
    				String tknNpm = "";
    				while(liTmp.hasNext()) {
    					tknNpm = tknNpm+(String)liTmp.next();
    					if(liTmp.hasNext()) {
    						tknNpm=tknNpm+",";
    					}
    				}
    				
    				liTmp = vKur.listIterator();
    				String tknKur = (String)liTmp.next();
    				//////System.out.println(baris9token);
    				//////System.out.println(tknNpm);
    				//////System.out.println(tknKur);
    				StringTokenizer st = new StringTokenizer(baris9token,",");
    				idkmk = st.nextToken();
        			kdkmk = st.nextToken();
        			nakmk = st.nextToken();
        			skstp = st.nextToken();
        			skspr = st.nextToken();
        			skslp = st.nextToken();
        			kdwpl = st.nextToken();
        			jenis = st.nextToken();
        			ttmhs = st.nextToken();
        			kdpst = st.nextToken();
        		    stmt.setString(1, targetForcastThsms);
        		    stmt.setString(2,kdpst);
        		    stmt.setInt(3, Integer.valueOf(idkmk).intValue());
        		    //////System.out.println(baris9token+" "+stmt.executeUpdate());
        		    stmt.executeUpdate();
    			}
    			
    			//insert record terkini
    			
    			stmt = con.prepareStatement("INSERT INTO FORECAST_1(THSMSFCAST,KDPSTFCAST,IDKMKFCAST,KDKMKFCAST,NAKMKFCAST,SKSMKFCAST,TTMHSFCAST,NPMHSFCAST,INFKRFCAST)VALUES(?,?,?,?,?,?,?,?,?);");
    			li = VsdbGetPerkiraanJumlahMhsPerMakul.listIterator();
    			i=0;
    			while(li.hasNext()) {
    				String baris9token = (String)li.next();
    				Vector vNpm = (Vector)li.next();
    				Vector vKur = (Vector)li.next();
    				ListIterator liTmp = vNpm.listIterator();
    				String tknNpm = "";
    				while(liTmp.hasNext()) {
    					tknNpm = tknNpm+(String)liTmp.next();
    					if(liTmp.hasNext()) {
    						tknNpm=tknNpm+",";
    					}
    				}
    				
    				liTmp = vKur.listIterator();
    				String tknKur = (String)liTmp.next();
    				StringTokenizer st = new StringTokenizer(baris9token,",");
    				//////System.out.println(baris9token);
    				idkmk = st.nextToken();
        			kdkmk = st.nextToken();
        			nakmk = st.nextToken();
        			skstp = st.nextToken();
        			skspr = st.nextToken();
        			skslp = st.nextToken();
        			int sksmk = Integer.valueOf(skstp).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue();
        			kdwpl = st.nextToken();
        			jenis = st.nextToken();
        			ttmhs = st.nextToken();
        			kdpst = st.nextToken();
//THSMSFCAST,KDPSTFCAST,IDKMKFCAST,KDKMKFCAST,NAKMKFCAST,SKSMKFCAST,TTMHSFCAST,NPMHSFCAST,INFKRFCAST
        		    stmt.setString(1, targetForcastThsms);
        		    stmt.setString(2,kdpst);
        		    stmt.setInt(3, Integer.valueOf(idkmk).intValue());
        		    stmt.setString(4,kdkmk);
        		    stmt.setString(5,nakmk);
        		    stmt.setInt(6, Integer.valueOf(sksmk).intValue());
        		    stmt.setInt(7, Integer.valueOf(ttmhs).intValue());
        		    stmt.setString(8,tknNpm);
        		    stmt.setString(9,tknKur);
        		    //////System.out.println("insert "+baris9token+" "+stmt.executeUpdate());
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
    
    public int updateDataIjazah(String kdpst,String npmhs,String nmmija,String nimija, String gelar,String tplhrija,String tglhrija,String nonirl,String noija) {
    	int i=0;
    	try {
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from IJAZAH where NPMHS=?");
    		stmt.setString(1, npmhs);
    		rs = stmt.executeQuery();
    		if(rs!=null && rs.next()) {
    			boolean editable = rs.getBoolean("EDITABLE");
    			if(editable) {
    			//do update
    				//////System.out.println("do update");
    				stmt = con.prepareStatement("update IJAZAH set NONIRL=?,NOIJA=?,NAMADIIJAZAH=?,NIMHSDIIJAZAH=?,TPTGLHRDIIJAZAH=?,GELARIJA=? where NPMHS=?");
    				stmt.setString(1, nonirl);
    				stmt.setString(2, noija);
    				stmt.setString(3, nmmija);
    				stmt.setString(4, nimija);
    				stmt.setString(5, tplhrija+", "+tglhrija);
    				stmt.setString(6, gelar);
    				stmt.setString(7,npmhs);
    			}
    		}
    		else {
    			//do insert
    			//////System.out.println("do insert");
    			stmt = con.prepareStatement("INSERT INTO IJAZAH (KDPST,NPMHS,NONIRL,NOIJA,NAMADIIJAZAH,NIMHSDIIJAZAH,TPTGLHRDIIJAZAH,STATUS,EDITABLE,CETAKABLE)VALUES(?,?,?,?,?,?,?,?,?,?)");
    			stmt.setString(1, kdpst);
    			stmt.setString(2, npmhs);
    			stmt.setString(3, nonirl);
    			stmt.setString(4, noija);
    			stmt.setString(5, nmmija);
    			stmt.setString(6, nimija);
    			stmt.setString(7, tplhrija+", "+tglhrija);
    			stmt.setNull(8, java.sql.Types.VARCHAR);
    			stmt.setBoolean(9,true);
    			stmt.setBoolean(10,true);
    		}
    		i = stmt.executeUpdate();

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
    
    public int updateMsmhsTpTglhr(String npmhs,String tplhr,java.sql.Date tglhr) {
    	int i=0;
    	tplhr = tplhr.toUpperCase();
    	try {
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("update CIVITAS set TPLHRMSMHS=?,TGLHRMSMHS=? where NPMHSMSMHS=?");
    		stmt.setString(1, tplhr);
    		stmt.setDate(2, tglhr);
    		stmt.setString(3, npmhs);
    		i = stmt.executeUpdate();
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
   
    public int setIjazahEditableAndCetakableToFalse(String npmhs) {
    	int i=0;
    	//tplhr = tplhr.toUpperCase();
    	try {
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("update IJAZAH set EDITABLE=?,CETAKABLE=? where NPMHS=?");
    		stmt.setBoolean(1, false);
    		stmt.setBoolean(2, false);
    		stmt.setString(3, npmhs);
    		i = stmt.executeUpdate();
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
    
    public int updKrsAsalPT(String kdpst,String npm,Vector v_upd) {
    	int i=0;
    	ListIterator li =null;
    	Vector v_del = new Vector();
    	Vector v_ins = new Vector();
    	Vector v_hist = new Vector();
    	//filter v_upd for non numeric for sks ma bobot
    	li=v_upd.listIterator();
    	while(li.hasNext()) {
    		String brs = (String)li.next();
			StringTokenizer st = new StringTokenizer(brs,"#");
			String nakmkasal=st.nextToken();
			String kdkmkasal=st.nextToken();
			String nlakhasal=st.nextToken();
			String bobotasal=st.nextToken();
			String sksmkasal=st.nextToken();
			try {
				double bbbt = Double.valueOf(bobotasal).doubleValue();
				int sks = Integer.valueOf(sksmkasal).intValue();
			}
			catch(Exception e) {
				li.remove();
			}
    	}
    	
    	
    	try {
    		li = v_hist.listIterator();
    		ListIterator lin = v_ins.listIterator();
    		ListIterator lid = v_del.listIterator();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//get prev record
    		stmt = con.prepareStatement("select * from TRNLP where KDPSTTRNLP=? and NPMHSTRNLP=?");
    		stmt.setString(1,kdpst);
    		stmt.setString(2,npm);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String kdkmkasal = rs.getString("KDKMKASALP");
    			li.add(kdkmkasal);
    		}
    		//filter mhs yg hilang = mo dihaous
    		li = v_hist.listIterator();
    		while(li.hasNext()) {
    			//kalo ngga dapet match dgn v_upd maka mk akan dihapus
    			boolean match = false;
    			String kdkmk = (String)li.next();
    			ListIterator liu = v_upd.listIterator();
    			while(liu.hasNext()&&!match) {
    				String brs = (String)liu.next();
    				StringTokenizer st = new StringTokenizer(brs,"#");
    				String nakmkasal=st.nextToken();
    				nakmkasal = nakmkasal.toUpperCase();
    				String kdkmkasal=st.nextToken();
    				kdkmkasal = kdkmkasal.toUpperCase();
    				String nlakhasal=st.nextToken();
    				nlakhasal = nlakhasal.toUpperCase();
    				String bobotasal=st.nextToken();
    				String sksmkasal=st.nextToken();
    				if(kdkmk.equalsIgnoreCase(kdkmkasal)) {
    					match = true;
    				}
    			}
    			if(!match) {
    				lid.add(kdkmk);
    			}
    		}
    		
    		//update
    		stmt=con.prepareStatement("update TRNLP set NAKMKASALP=?,NLAKHTRNLP=?,BOBOTTRNLP=?,SKSMKASAL=? where KDPSTTRNLP=? and NPMHSTRNLP=? and KDKMKASALP=?");
    		ListIterator liu = v_upd.listIterator();
    		while(liu.hasNext()) {
				String brs = (String)liu.next();
				StringTokenizer st = new StringTokenizer(brs,"#");
				String nakmkasal=st.nextToken();
				nakmkasal = nakmkasal.toUpperCase();
				String kdkmkasal=st.nextToken();
				kdkmkasal = kdkmkasal.toUpperCase();
				String nlakhasal=st.nextToken();
				nlakhasal = nlakhasal.toUpperCase();
				String bobotasal=st.nextToken();
				String sksmkasal=st.nextToken();
				stmt.setString(1, nakmkasal);
				stmt.setString(2, nlakhasal);
				stmt.setDouble(3, Double.valueOf(bobotasal).doubleValue());
				stmt.setInt(4, Integer.valueOf(sksmkasal).intValue());
				stmt.setString(5, kdpst);
				stmt.setString(6, npm);
				stmt.setString(7, kdkmkasal);
				int j = 0;
				j = stmt.executeUpdate();
				//////System.out.println("update "+nakmkasal+"="+j);
				if(j<1) {
					lin.add(brs);
				}
    		}
    		
    		//insert
    		lin = v_ins.listIterator();
    		stmt=con.prepareStatement("insert into TRNLP(THSMSTRNLP,KDPSTTRNLP,NPMHSTRNLP,NLAKHTRNLP,BOBOTTRNLP,KDKMKASALP,NAKMKASALP,SKSMKASAL)values(?,?,?,?,?,?,?,?)");
    		
    		while(lin.hasNext()) {
    			String brs = (String)lin.next();
				StringTokenizer st = new StringTokenizer(brs,"#");
				String nakmkasal=st.nextToken();
				nakmkasal = nakmkasal.toUpperCase();
				String kdkmkasal=st.nextToken();
				kdkmkasal = kdkmkasal.toUpperCase();
				String nlakhasal=st.nextToken();
				nlakhasal = nlakhasal.toUpperCase();
				String bobotasal=st.nextToken();
				String sksmkasal=st.nextToken();
				stmt.setString(1, "00000");
				stmt.setString(2, kdpst);
				stmt.setString(3, npm);
				stmt.setString(4, nlakhasal);
				stmt.setDouble(5, Double.valueOf(bobotasal).doubleValue());
				stmt.setString(6, kdkmkasal);
				stmt.setString(7, nakmkasal);
				stmt.setInt(8, Integer.valueOf(sksmkasal).intValue());
				stmt.executeUpdate();
    		}
    		
    		//delete mk
    		stmt = con.prepareStatement("delete from TRNLP where KDPSTTRNLP=? and NPMHSTRNLP=? and KDKMKASALP=?");
    		lid = v_del.listIterator();
    		while(lid.hasNext()) {
    			String kdkmk = (String)lid.next();
    			stmt.setString(1,kdpst);
    			stmt.setString(2,npm);
    			stmt.setString(3,kdkmk);
    			stmt.executeUpdate();
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
    
    public void updateMataKuliahPenyetaraanTrnlp(String kdpst,String npmhs,String[]kdkmk_kdasl) {
    	//int i=0;
    	//tplhr = tplhr.toUpperCase();
    	try {
    		
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("update TRNLP set KDKMKTRNLP=?,TRANSFERRED=? where KDPSTTRNLP=? and NPMHSTRNLP=? and KDKMKASALP=?");
    		if(kdkmk_kdasl!=null) {
    			for(int i=0;i<kdkmk_kdasl.length;i++) {
    				StringTokenizer st = new StringTokenizer(kdkmk_kdasl[i],"#");
    				String kdkmk1 = st.nextToken();
    				if(!kdkmk1.equalsIgnoreCase("0")) {
    					String kdasl = st.nextToken();
    					String nakmk1 = st.nextToken();
    					stmt.setString(1, kdkmk1);
    					stmt.setBoolean(2, true);
    					stmt.setString(3, kdpst);
    					stmt.setString(4, npmhs);
    					stmt.setString(5, kdasl);
    					stmt.executeUpdate();
    					//////System.out.println(kdkmk1+"-"+kdasl+"="+stmt.executeUpdate());
    				}
    			}	
				//
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
    	//return i;
    }

    public String getBobotGivenNlakh(String thsms,String kdpst,String nlakh) {
    	//fungsi nini ada di searchDb & UpdateDb
    	//hanya untuk krs tidak untuk krs pindahan krn sekolah lain bisa punya bobot sendiri
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
    			bobot = ""+rs.getDouble("BOBOTTBBNL");
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
			if (con!=null) {
				try {
					con.close();
				}
				catch (Exception ignore) {
					////System.out.println(ignore);
				}
			}
		}
    	return bobot;
    }    

    public int updateMataKuliahKrsKhs(String thsms,String kdpst,String npmhs,String kdkmk,String nlakh) {
    	//kdjen ngga dipake
    	//khusus untuk update trnlm
    	int ins = 0;
    	String bobot = getBobotGivenNlakh(thsms,kdpst,nlakh);
    	//////System.out.println("bobot "+thsms+" "+kdpst+" "+nlakh+" = "+bobot);
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("UPDATE TRNLM SET NLAKHTRNLM=?,BOBOTTRNLM=? where THSMSTRNLM=? and KDPSTTRNLM=? AND NPMHSTRNLM=? and KDKMKTRNLM=?");
    		stmt.setString(1,nlakh.toUpperCase());
    		stmt.setDouble(2,Double.valueOf(bobot).doubleValue());
    		stmt.setString(3,thsms);
    		stmt.setString(4,kdpst);
    		stmt.setString(5,npmhs);
    		stmt.setString(6,kdkmk);
    		ins = stmt.executeUpdate();
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
    	return ins;	
    }
    
    public int updateMataKuliahKrsKhsPerThsms(String thsms,String kdpst,String npmhs,Vector vKdkmkNlakhShift) {
    	//kdjen ngga dipake
    	//khusus untuk update trnlm
    	ListIterator li = vKdkmkNlakhShift.listIterator();
    	while(li.hasNext()) {
    		String brs = (String)li.next();
    		StringTokenizer st = new StringTokenizer(brs,"#&");
    		String kdkmk = st.nextToken();
    		String nlakh = st.nextToken();
    		nlakh = nlakh.toUpperCase();
    		nlakh = Tool.removeWhiteSpace(nlakh);
    		String bobot = getBobotGivenNlakh(thsms,kdpst,nlakh);
    		String shift = st.nextToken();
    		li.set(brs+"#&"+bobot);
    	}
    	int ins = 0;
    	//String bobot = getBobotGivenNlakh(thsms,kdpst,nlakh);
    	//////System.out.println("bobot "+thsms+" "+kdpst+" "+nlakh+" = "+bobot);
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("UPDATE TRNLM SET NLAKHTRNLM=?,BOBOTTRNLM=? where THSMSTRNLM=? and KDPSTTRNLM=? AND NPMHSTRNLM=? and KDKMKTRNLM=?");
    		li = vKdkmkNlakhShift.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
        		StringTokenizer st = new StringTokenizer(brs,"#&");
        		String kdkmk = st.nextToken();
        		String nlakh = st.nextToken();
        		nlakh = nlakh.toUpperCase();
        		nlakh = Tool.removeWhiteSpace(nlakh);
        		String shift = st.nextToken();
        		String bobot = st.nextToken();
        		stmt.setString(1,nlakh);
        		stmt.setDouble(2,Double.valueOf(bobot).doubleValue());
        		stmt.setString(3,thsms);
        		stmt.setString(4,kdpst);
        		stmt.setString(5,npmhs);
        		stmt.setString(6,kdkmk);
        		ins = stmt.executeUpdate();
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
    	return ins;	
    }

    
    public int updateShiftKelasMhs(String kdpst,String npmhs,String shift) {
    	int ins = 0;
    	
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("UPDATE CIVITAS set SHIFTMSMHS=? where KDPSTMSMHS=? and NPMHSMSMHS=?");
    		stmt.setString(1,shift);
    		stmt.setString(2,kdpst);
    		stmt.setString(3,npmhs);
    		ins = stmt.executeUpdate();
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
    	return ins;	
    }
    
    public int voidKui(String kuiid,String opnpm, String opnmm,String void_keter) {
    	int ins = 0;
    	if(void_keter==null) {
    		void_keter="";
    	}
    	else {
    		StringTokenizer st = new StringTokenizer(void_keter);
    		void_keter = "";
    		while(st.hasMoreTokens()) {
    			void_keter = void_keter+st.nextToken();
    			if(st.hasMoreTokens()) {
    				void_keter = void_keter+" ";
    			}
    		}
    	}
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("UPDATE PYMNT set VOIDDPYMNT=?,VOIDOPNPM=?,VOIDKETER=?,VOIDOPNMM=? where KUIIDPYMNT=?");
    		stmt.setBoolean(1,true);
    		stmt.setString(2,opnpm);
    		stmt.setString(3,void_keter);
    		stmt.setString(4,opnmm);
    		stmt.setLong(5,Long.valueOf(kuiid).longValue());
    		ins = stmt.executeUpdate();
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
    	return ins;	
    }
    
    public int deleteMhs(String kdpst,String npmhs) {
    	int upd = 0;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("UPDATE PYMNT set KDPSTPYMNT=?,NPMHSPYMNT=? where NPMHSPYMNT=?");
    		stmt.setString(1, Constants.getKdpstMhsTdkTerdaftar());
    		stmt.setString(2, Constants.getNpmMhsTdkTerdaftar());
    		stmt.setString(3, npmhs);
    		upd = stmt.executeUpdate();
    		//////System.out.println("upd="+upd);
    		stmt = con.prepareStatement("DELETE FROM EXT_CIVITAS where KDPSTMSMHS=? and NPMHSMSMHS=?");
    		stmt.setString(1,kdpst);
    		stmt.setString(2,npmhs);
    		upd = stmt.executeUpdate();
    		//////System.out.println("upd="+upd);
    		stmt = con.prepareStatement("DELETE FROM CIVITAS where KDPSTMSMHS=? and NPMHSMSMHS=?");
    		stmt.setString(1,kdpst);
    		stmt.setString(2,npmhs);
    		upd = stmt.executeUpdate();
    		//////System.out.println("upd="+upd);
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
    	return upd;	
    }
    
    
    //depricated gunakan yg 5 param
    public int processKrsApproval(String statusAproval,String thsms,String npmhs) {
    	int i = 0;
    	String curInfoPa = Checker.getCurPa(npmhs);
    	String curNpmPa = null;
    	String curNmmPa = null;
    	if(curInfoPa!=null) {
    		StringTokenizer st = new StringTokenizer(curInfoPa,",");
    		curNpmPa = st.nextToken();
    		curNmmPa = st.nextToken();
    	}
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		if(statusAproval.equalsIgnoreCase("reset")) {
    			stmt = con.prepareStatement("UPDATE TRNLM set LOCKMHS=? where THSMSTRNLM=? and NPMHSTRNLM=?");
    			stmt.setBoolean(1,false);
    			stmt.setString(2,thsms);
    			stmt.setString(3,npmhs);
    			i = stmt.executeUpdate();
    		}
    		else {
    			if(statusAproval.equalsIgnoreCase("approved")) {
    				stmt = con.prepareStatement("UPDATE TRNLM set PA_APPROVAL=?,NPM_PA=?,LOCKMHS=?  where THSMSTRNLM=? and NPMHSTRNLM=?");
    				stmt.setBoolean(1, true);
    				if(curNpmPa==null) {
    					stmt.setNull(2,java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(2,curNpmPa );
    				}
    				stmt.setBoolean(3,true);
    				stmt.setString(4,thsms);
    				stmt.setString(5,npmhs);
    				i = stmt.executeUpdate();
    			}
    			else {
    				if(statusAproval.equalsIgnoreCase("undoApproval")) {
        				stmt = con.prepareStatement("UPDATE TRNLM set PA_APPROVAL=?,NPM_PA=?,LOCKMHS=?  where THSMSTRNLM=? and NPMHSTRNLM=?");
        				stmt.setBoolean(1, false);
        				if(curNpmPa==null) {
        					stmt.setNull(2,java.sql.Types.VARCHAR);
        				}
        				else {
        					stmt.setString(2,curNpmPa );
        				}
        				stmt.setBoolean(3,false);
        				stmt.setString(4,thsms);
        				stmt.setString(5,npmhs);
        				i = stmt.executeUpdate();
    				}
    				else {
    					if(statusAproval.equalsIgnoreCase("reject")) {
    					//do nothing
    						stmt = con.prepareStatement("UPDATE TRNLM set PA_APPROVAL=?,NPM_PA=?,LOCKMHS=?  where THSMSTRNLM=? and NPMHSTRNLM=?");
    						stmt.setBoolean(1, false);
            				if(curNpmPa==null) {
            					stmt.setNull(2,java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(2,curNpmPa );
            				}
            				stmt.setBoolean(3,false);
            				stmt.setString(4,thsms);
            				stmt.setString(5,npmhs);
            				i = stmt.executeUpdate();
    						//i=1; //nandain update oke
    					}
    					else {
    						if(statusAproval.equalsIgnoreCase("reject_unlock")) {
    	    					//do nothing
    	    						stmt = con.prepareStatement("UPDATE TRNLM set PA_APPROVAL=?,NPM_PA=?,LOCKMHS=?  where THSMSTRNLM=? and NPMHSTRNLM=?");
    	    						stmt.setBoolean(1, false);
    	            				if(curNpmPa==null) {
    	            					stmt.setNull(2,java.sql.Types.VARCHAR);
    	            				}
    	            				else {
    	            					stmt.setString(2,curNpmPa );
    	            				}
    	            				stmt.setBoolean(3,true);
    	            				stmt.setString(4,thsms);
    	            				stmt.setString(5,npmhs);
    	            				i = stmt.executeUpdate();
    	    						//i=1; //nandain update oke
    	    				}
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
    	return i;	
    }
    
    
    public int processKrsApproval(String statusAproval,String thsms,String npmhs, String oprNpm, String oprNmm) {
    	int i = 0;
    	//String curInfoPa = Checker.getCurPa(npmhs);
    	//String curInfoPa = new String(oprNpm); //tidak lg npmPa = jadi bisa diwakilkan
    	String curNpmPa = new String(oprNpm);
    	String curNmmPa = new String(oprNmm);
    	/*
    	 * if(curInfoPa!=null) {
    	 
    		StringTokenizer st = new StringTokenizer(curInfoPa,",");
    		curNpmPa = st.nextToken();
    		curNmmPa = st.nextToken();
    	}
    	*/
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		if(statusAproval.equalsIgnoreCase("reset")) {
    			stmt = con.prepareStatement("UPDATE TRNLM set LOCKMHS=? where THSMSTRNLM=? and NPMHSTRNLM=?");
    			stmt.setBoolean(1,false);
    			stmt.setString(2,thsms);
    			stmt.setString(3,npmhs);
    			i = stmt.executeUpdate();
    		}
    		else {
    			if(statusAproval.equalsIgnoreCase("approved")) {
    				stmt = con.prepareStatement("UPDATE TRNLM set PA_APPROVAL=?,NPM_PA=?,LOCKMHS=?  where THSMSTRNLM=? and NPMHSTRNLM=?");
    				stmt.setBoolean(1, true);
    				if(curNpmPa==null) {
    					stmt.setNull(2,java.sql.Types.VARCHAR);
    				}
    				else {
    					stmt.setString(2,curNpmPa );
    				}
    				stmt.setBoolean(3,true);
    				stmt.setString(4,thsms);
    				stmt.setString(5,npmhs);
    				i = stmt.executeUpdate();
    			}
    			else {
    				if(statusAproval.equalsIgnoreCase("undoApproval")) {
        				stmt = con.prepareStatement("UPDATE TRNLM set PA_APPROVAL=?,NPM_PA=?,LOCKMHS=?  where THSMSTRNLM=? and NPMHSTRNLM=?");
        				stmt.setBoolean(1, false);
        				if(curNpmPa==null) {
        					stmt.setNull(2,java.sql.Types.VARCHAR);
        				}
        				else {
        					stmt.setString(2,curNpmPa );
        				}
        				stmt.setBoolean(3,false);
        				stmt.setString(4,thsms);
        				stmt.setString(5,npmhs);
        				i = stmt.executeUpdate();
    				}
    				else {
    					if(statusAproval.equalsIgnoreCase("reject")) {
    					//do nothing
    						stmt = con.prepareStatement("UPDATE TRNLM set PA_APPROVAL=?,NPM_PA=?,LOCKMHS=?  where THSMSTRNLM=? and NPMHSTRNLM=?");
    						stmt.setBoolean(1, false);
            				if(curNpmPa==null) {
            					stmt.setNull(2,java.sql.Types.VARCHAR);
            				}
            				else {
            					stmt.setString(2,curNpmPa );
            				}
            				stmt.setBoolean(3,false);
            				stmt.setString(4,thsms);
            				stmt.setString(5,npmhs);
            				i = stmt.executeUpdate();
    						//i=1; //nandain update oke
    					}
    					else {
    						if(statusAproval.equalsIgnoreCase("reject_unlock")) {
    	    					//do nothing
    	    						stmt = con.prepareStatement("UPDATE TRNLM set PA_APPROVAL=?,NPM_PA=?,LOCKMHS=?  where THSMSTRNLM=? and NPMHSTRNLM=?");
    	    						stmt.setBoolean(1, false);
    	            				if(curNpmPa==null) {
    	            					stmt.setNull(2,java.sql.Types.VARCHAR);
    	            				}
    	            				else {
    	            					stmt.setString(2,curNpmPa );
    	            				}
    	            				stmt.setBoolean(3,true);
    	            				stmt.setString(4,thsms);
    	            				stmt.setString(5,npmhs);
    	            				i = stmt.executeUpdate();
    	    						//i=1; //nandain update oke
    	    				}
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
    	return i;	
    }

    /*
     * KRS NOTIFICATION TABLE AREA
     */
    
    public int hideKrsNotificationById(String hiddenAt,String id) {
       	int upd = 0;
       	
       	try {
       		Context initContext  = new InitialContext();
       		Context envContext  = (Context)initContext.lookup("java:/comp/env");
       		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
       		con = ds.getConnection();
       		if(hiddenAt.equalsIgnoreCase("sender")) {
       			stmt=con.prepareStatement("Update KRS_NOTIFICATION set HIDDEN_AT_SENDER=? WHERE ID=?");
       		}
       		else {
       			if(hiddenAt.equalsIgnoreCase("receiver")) {
           			stmt=con.prepareStatement("Update KRS_NOTIFICATION set HIDDEN_AT_RECEIVER=? WHERE ID=?");
           		}	
       		}
       		stmt.setBoolean(1,true);
       		stmt.setLong(2, Long.valueOf(id).longValue());
       		upd = stmt.executeUpdate();   
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
               		////System.out.println(ignore);
           		}
           	}
        }
       	return upd;	
    }
	
    
    public int individualRequestApprovalKrsNotificationTable(String thsms,String kdpst,String npmhs, String nmmhs) {
       	int upd = 0;
       	//get npm pa
       	String curPa = Checker.getCurPa(npmhs);
       	StringTokenizer st = new StringTokenizer(curPa,",");
       	String npmPa = st.nextToken();
       	String nmmPa = st.nextToken();
       	try {
       		Context initContext  = new InitialContext();
       		Context envContext  = (Context)initContext.lookup("java:/comp/env");
       		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
       		con = ds.getConnection();
       		String berita = "PERMOHONAN PERSETUJUAN KRS"; 
       		/*
       		 * biar hemat coba update dulu
       		 */
       		upd = 0;
       		stmt = con.prepareStatement("UPDATE KRS_NOTIFICATION set HIDDEN_AT_SENDER=?,HIDDEN_AT_RECEIVER=?,DELETE_BY_SENDER=?,DELETE_BY_RECEIVER=?,APPROVED=?,DECLINED=? where KATEGORI=? and THSMS_TARGET=?  and NPM_SENDER=? and NPM_RECEIVER=? and KDPST_RECEIVER is NULL");
       		stmt.setBoolean(1,false);
       		stmt.setBoolean(2,false);
       		stmt.setBoolean(3,false);
       		stmt.setBoolean(4,false);
       		stmt.setBoolean(5,false);
       		stmt.setBoolean(6,false);
       		//stmt.setNull(5, java.sql.Types.VARCHAR);
       		stmt.setString(7,"KRS APPROVAL");
       		stmt.setString(8,thsms);
       		//stmt.setString(9,berita);
       		stmt.setString(9,npmhs);
       		stmt.setString(10,npmPa);
       		upd = stmt.executeUpdate();
       		//if no prev record then insert
       		if(upd<1) {
       			stmt = con.prepareStatement("INSERT INTO KRS_NOTIFICATION (THSMS_TARGET,BERITA,NPM_SENDER,NMM_SENDER,NPM_RECEIVER,NMM_RECEIVER)VALUES(?,?,?,?,?,?)");
       			stmt.setString(1, thsms);
       			stmt.setString(2, berita);
       			stmt.setString(3, npmhs);
       			stmt.setString(4, nmmhs);
       			stmt.setString(5, npmPa);
       			stmt.setString(6, nmmPa);
       			upd = stmt.executeUpdate();
       		}
       		if(upd<1) {
       			////System.out.println("gagal at UpdateDb.individualRequestApprovalKrsNotificationTable()");
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
               		////System.out.println(ignore);
           		}
           	}
        }
       	return upd;	
    }

    public int paReplyRequestApprovalKrsNotificationTable(String thsms,String npmhs, String nmmhs, String statusApproval, String catatan, String berita,String kategori) {
       	int upd = 0;
       	/*
       	 * value koma (,) tercatat dalam DB sbg $
       	 */
       	//get npm pa
       	String curPa = Checker.getCurPa(npmhs);
       	StringTokenizer st = new StringTokenizer(curPa,",");
       	String npmPa = st.nextToken();
       	String nmmPa = st.nextToken();
       	try {
       		Context initContext  = new InitialContext();
       		Context envContext  = (Context)initContext.lookup("java:/comp/env");
       		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
       		con = ds.getConnection();
       		//String berita = "KRS APPROVAL REQUEST";
       		
/*
 * ID`,KATEGORI`,THSMS_TARGET`,BERITA`,NPM_SENDER`,NMM_SENDER`,NPM_RECEIVER`,NMM_RECEIVER`,KDPST_RECEIVER`,
   SMAWL_RECEIVER`,HIDDEN_AT_SENDER`,HIDDEN_AT_RECEIVER`,DELETE_BY_SENDER`,DELETE_BY_RECEIVER`,APPROVED`,DECLINED`,
   NOTE`,UPDTM`)
*/       		
       		if(statusApproval.equalsIgnoreCase("approved")) {
       			if(catatan==null || Checker.isStringNullOrEmpty(catatan)) {
       				catatan = "KRS "+thsms+" telah disetujui";
       			}
       			else {
       				catatan = "KRS "+thsms+" telah disetujui<br/>NOTE:<br/>"+catatan;
       			}
       			stmt = con.prepareStatement("update KRS_NOTIFICATION set NPM_RECEIVER=?,NMM_RECEIVER=?,HIDDEN_AT_SENDER=?,HIDDEN_AT_RECEIVER=?,APPROVED=?,DECLINED=?,UNLOCK_APPROVED=?,UNLOCK_DECLINED=?,NOTE=? WHERE KATEGORI=? and THSMS_TARGET=? and NPM_SENDER=?");
       			stmt.setString(1,this.operatorNpm);
       			stmt.setString(2,this.operatorNmm);
       			stmt.setBoolean(3,false);
       			stmt.setBoolean(4,true);
       			stmt.setBoolean(5,true);
       			stmt.setBoolean(6,false);
       			stmt.setBoolean(7,false);
       			stmt.setBoolean(8,false);
       			/*
       			if(note==null || Checker.isStringNullOrEmpty(note)) {
       				stmt.setNull(5, java.sql.Types.VARCHAR);
       			}
       			else {
       				stmt.setString(5,note);
       			}
       			*/	
       			stmt.setString(9,catatan);
       			stmt.setString(10,"KRS APPROVAL");
       			stmt.setString(11,thsms);
       			stmt.setString(12,npmhs);
       		
       			//upd = stmt.executeUpdate();
       		}
       		else {
       			if(statusApproval.equalsIgnoreCase("reset")) {
       				//////System.out.println("UPDATE DB berita = "+berita);
       				/*
       				 * bila reset untuk ngga jadi approved
       				 */
       				if(berita.equalsIgnoreCase("PERMOHONAN PERSETUJUAN KRS")) {
       					processKrsApproval("undoApproval",thsms,npmhs);
       					initContext  = new InitialContext();
       		       		envContext  = (Context)initContext.lookup("java:/comp/env");
       		       		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
       		       		con = ds.getConnection();
       					stmt = con.prepareStatement("update KRS_NOTIFICATION set NPM_RECEIVER=?,NMM_RECEIVER=?,HIDDEN_AT_SENDER=?,HIDDEN_AT_RECEIVER=?,APPROVED=?,DECLINED=?,UNLOCK_APPROVED=?,UNLOCK_DECLINED=?,NOTE=? WHERE KATEGORI=? and THSMS_TARGET=? and NPM_SENDER=?");
       					stmt.setString(1,this.operatorNpm);
       					stmt.setString(2,this.operatorNmm);
       					stmt.setBoolean(3,false);
       					stmt.setBoolean(4,false);
       					stmt.setBoolean(5,false);
       					stmt.setBoolean(6,false);
       					stmt.setBoolean(7,false);
       					stmt.setBoolean(8,false);
       					stmt.setNull(9, java.sql.Types.VARCHAR);
       					stmt.setString(10,kategori);
       					stmt.setString(11,thsms);
       					stmt.setString(12,npmhs);
       				}
       				else {
       				// reset unlock
       					if(catatan==null || Checker.isStringNullOrEmpty(catatan)) {
       						catatan = "Status kunci telah dibuka, anda dapat mengedit KRS "+thsms;
       					}
       					else {
       						catatan = "Status kunci telah dibuka, anda dapat mengedit KRS "+thsms+"<br/>NOTE:<br/>"+catatan;
       					}
       					stmt = con.prepareStatement("update KRS_NOTIFICATION set NPM_RECEIVER=?,NMM_RECEIVER=?,HIDDEN_AT_SENDER=?,HIDDEN_AT_RECEIVER=?,UNLOCK_APPROVED=?,UNLOCK_DECLINED=?,NOTE=? WHERE KATEGORI=? and THSMS_TARGET=? and NPM_SENDER=?");
       					stmt.setString(1,this.operatorNpm);
       					stmt.setString(2,this.operatorNmm);
       					stmt.setBoolean(3,false);
       					stmt.setBoolean(4,true);
       					stmt.setBoolean(5,true);
       					stmt.setBoolean(6,false);
       				/*
           			if(note==null || Checker.isStringNullOrEmpty(note)) {
           				stmt.setNull(7, java.sql.Types.VARCHAR);
           			}
           			else {
           				stmt.setString(7,note);
           			}
           			*/	
       					stmt.setString(7,catatan);
       					stmt.setString(8,"KRS APPROVAL");
       					stmt.setString(9,thsms);
       					stmt.setString(10,npmhs);
       				}
       			}
       			else {
       				if(statusApproval.equalsIgnoreCase("reject")) {
           				if(catatan==null || Checker.isStringNullOrEmpty(catatan)) {
               				catatan = "Harap Hubungi Pembinbing Akademik Anda";
               			}
               			/*
               			 * delete trnlm at thsms pmb 
               			 */
           				stmt = con.prepareStatement("delete from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=?");
           				stmt.setString(1, thsms);
           				stmt.setString(2,npmhs);
           				stmt.executeUpdate();
           				stmt = con.prepareStatement("update KRS_NOTIFICATION set NPM_RECEIVER=?,NMM_RECEIVER=?,HIDDEN_AT_SENDER=?,HIDDEN_AT_RECEIVER=?,APPROVED=?,DECLINED=?,UNLOCK_APPROVED=?,UNLOCK_DECLINED=?,NOTE=? WHERE KATEGORI=? and THSMS_TARGET=? and NPM_SENDER=?");
           				stmt.setString(1,this.operatorNpm);
           				stmt.setString(2,this.operatorNmm);
           				stmt.setBoolean(3,false);
           				stmt.setBoolean(4,true);
           				stmt.setBoolean(5,false);
           				stmt.setBoolean(6,true);
           				stmt.setBoolean(7,false);
           				stmt.setBoolean(8,false);
           				/*
               			if(note==null || Checker.isStringNullOrEmpty(note)) {
               				stmt.setNull(7, java.sql.Types.VARCHAR);
               			}
               			else {
               				stmt.setString(7,note);
               			}
               			*/	
           				stmt.setString(9,catatan);
           				stmt.setString(10,"KRS APPROVAL");
           				stmt.setString(11,thsms);
           				stmt.setString(12,npmhs);
           			}
       				else {
       					if(statusApproval.equalsIgnoreCase("reject_unlock")) {
       						if(catatan==null || Checker.isStringNullOrEmpty(catatan)) {
       							catatan = "Permohonan buka kunci ditolak";
       						}
       						else {
       							catatan = "Permohonan buka kunci ditolak<br/>NOTE:<br/>"+catatan;
       						}
       						stmt = con.prepareStatement("update KRS_NOTIFICATION set NPM_RECEIVER=?,NMM_RECEIVER=?,HIDDEN_AT_SENDER=?,HIDDEN_AT_RECEIVER=?,APPROVED=?,DECLINED=?,UNLOCK_APPROVED=?,UNLOCK_DECLINED=?,NOTE=? WHERE KATEGORI=? and THSMS_TARGET=? and NPM_SENDER=?");
       						stmt.setString(1,this.operatorNpm);
       						stmt.setString(2,this.operatorNmm);
       						stmt.setBoolean(3,false);
       						stmt.setBoolean(4,true);
       						stmt.setBoolean(5,true);
       						stmt.setBoolean(6,false);
       						stmt.setBoolean(7,false);
       						stmt.setBoolean(8,true);
           				
           				/*
               			if(note==null || Checker.isStringNullOrEmpty(note)) {
               				stmt.setNull(7, java.sql.Types.VARCHAR);
               			}
               			else {
               				stmt.setString(7,note);
               			}
               			*/	
       						stmt.setString(9,catatan);
       						stmt.setString(10,"KRS APPROVAL");
       						stmt.setString(11,thsms);
       						stmt.setString(12,npmhs);
       					}
       				}	
       			}
       		}	
       		upd = stmt.executeUpdate();
       		if(upd<1) {
       			////System.out.println("gagal at UpdateDb.individualRequestApprovalKrsNotificationTable()");
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
               		////System.out.println(ignore);
           		}
           	}
        }
       	return upd;	
    }

    public int requestUnlockUpdateKrs(String alasan) {
    	int i =0;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("update KRS_NOTIFICATION set BERITA=?,HIDDEN_AT_SENDER=?,HIDDEN_AT_RECEIVER=?,UNLOCK_APPROVED=?,UNLOCK_DECLINED=?,NOTE=? WHERE KATEGORI=? and THSMS_TARGET=? and NPM_SENDER=? and DELETE_BY_SENDER=? and DELETE_BY_RECEIVER=?");
    		
    		stmt.setString(1, "PERMOHONAN BUKA KUNCI");
    		stmt.setBoolean(2,false);
    		stmt.setBoolean(3,false);
    		stmt.setBoolean(4,false);
    		stmt.setBoolean(5,false);
    		stmt.setString(6,alasan);
    		stmt.setString(7,"KRS APPROVAL");
    		stmt.setString(8,Checker.getThsmsPmb());
    		stmt.setString(9,this.operatorNpm);
    		stmt.setBoolean(10,false);
    		stmt.setBoolean(11,false);
    		i = stmt.executeUpdate();
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
					////System.out.println(ignore);
				}
			}
		}
    	return i;
    }
    
    
    
    /*
     * END KRS NOTIFICATION TABLE AREA
    */
    
    /*!! DEPRECATED
     * ada versi v1 - add variable cuid_init
     * !!! perhatian ada versi updateKrsCpContinuSys
     * !!
     */
    public int updateKrsCp(String thsms,String kdjen,String kdpst,String npmhs,Vector VkelasSelected) {
    	int i = 0;
    	if(VkelasSelected!=null && VkelasSelected.size()>0) {
    		ListIterator li = VkelasSelected.listIterator();
    		//fix sks==null
    		while(li.hasNext()) {
				String brs = (String)li.next();
				////System.out.println("baris-00="+brs);
				if(brs.startsWith("add_req")) {
					StringTokenizer st = new StringTokenizer(brs,",");
					String reqType = st.nextToken();
					String idkmk = st.nextToken();
					String shift = st.nextToken();
					String kelas = st.nextToken();
					String kdkmk = st.nextToken();
					String sksmk = st.nextToken();
					if(sksmk.equalsIgnoreCase("null")) {
						sksmk = ""+Checker.getSksmk(idkmk);
					}
					String currStatus = st.nextToken();
					String npmdos = st.nextToken();
					String npmasdos = st.nextToken();
					String canceled = st.nextToken();
					String kodeKelas = st.nextToken();
					String kodeRuang = st.nextToken();
					String kodeGedung = st.nextToken();
					String kodeKampus = st.nextToken();
					String tknDayTime = st.nextToken();
					String nmmdos = st.nextToken();
					String nmmasdos = st.nextToken();
					String enrolled = st.nextToken();
					String maxEnrolled = st.nextToken();
					String minEnrolled = st.nextToken();
					String unique_classpool_id = st.nextToken();
					
					li.set(reqType+","+idkmk+","+shift+","+kelas+","+kdkmk+","+sksmk+","+sksmk+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+unique_classpool_id);
				}
    		}	
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			
    			
    			li = VkelasSelected.listIterator();
    			//proses drop_req
    			//stmt = con.prepareStatement("UPDATE TRNLM set ADD_REQ=?,DRP_REQ=? where THSMSTRNLM=? and KDPSTTRNLM=? and NPMHSTRNLM=? and KDKMKTRNLM=? and SHIFTTRNLM=? and KELASTRNLM=? ");
    			stmt = con.prepareStatement("DELETE FROM TRNLM where THSMSTRNLM=? and KDPSTTRNLM=? and NPMHSTRNLM=? and KDKMKTRNLM=? and SHIFTTRNLM=? and KELASTRNLM=? ");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				if(brs.startsWith("drop_req")) {
    					StringTokenizer st = new StringTokenizer(brs,",");
    					String reqType = st.nextToken();
    					String idkmk = st.nextToken();
    					String shift = st.nextToken();
    					String kelas = st.nextToken();
    					String kdkmk = st.nextToken();
    					
    					stmt.setString(1, thsms);
    					stmt.setString(2, kdpst);
    					stmt.setString(3, npmhs);
    					stmt.setString(4, kdkmk);
    					stmt.setString(5, shift);
    					stmt.setString(6, kelas);
    					i = i + stmt.executeUpdate();
    				}
    			}
    			
    			//harus insert trakm juga
        		//delete prev record
        		stmt = con.prepareStatement("delete from TRAKM where THSMSTRAKM=? and KDPSTTRAKM=? and NPMHSTRAKM=?");
        		stmt.setString(1, thsms);
        		stmt.setString(2, kdpst);
        		stmt.setString(3, npmhs);
        		stmt.executeUpdate();
        		//insert fresh record
        		stmt = con.prepareStatement("INSERT into TRAKM(THSMSTRAKM,KDPTITRAKM,KDJENTRAKM,KDPSTTRAKM,NPMHSTRAKM,SKSEMTRAKM,NLIPSTRAKM,SKSTTTRAKM,NLIPKTRAKM)values(?,?,?,?,?,?,?,?,?)");
        		stmt.setString(1, thsms);
        		stmt.setString(2, Constants.getKdpti());
        		stmt.setNull(3,java.sql.Types.VARCHAR);
        		stmt.setString(4, kdpst);
        		stmt.setString(5, npmhs);
        		stmt.setInt(6, 0);
        		stmt.setDouble(7, 0);
        		stmt.setInt(8, 0);
        		stmt.setDouble(9, 0);
        		stmt.executeUpdate();
        		
        		//ignore if start with ignore
    			//proses add_req
    			li = VkelasSelected.listIterator();
    			stmt = con.prepareStatement("INSERT INTO TRNLM(THSMSTRNLM,KDPTITRNLM,KDJENTRNLM,KDPSTTRNLM,NPMHSTRNLM,KDKMKTRNLM,SKSMKTRNLM,KELASTRNLM,SHIFTTRNLM,IDKMKTRNLM,ADD_REQ,KODE_KAMPUS,CLASS_POOL_UNIQUE_ID)values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				if(brs.startsWith("add_req")) {
    					StringTokenizer st = new StringTokenizer(brs,",");
    					
    					String reqType = st.nextToken();
    					String idkmk = st.nextToken();
    					String shift = st.nextToken();
    					String kelas = st.nextToken();
    					String kdkmk = st.nextToken();
    					String sksmk = st.nextToken();
    					String currStatus = st.nextToken();
    					String npmdos = st.nextToken();
    					String npmasdos = st.nextToken();
    					String canceled = st.nextToken();
    					String kodeKelas = st.nextToken();
    					String kodeRuang = st.nextToken();
    					String kodeGedung = st.nextToken();
    					String kodeKampus = st.nextToken();
    					String tknDayTime = st.nextToken();
    					String nmmdos = st.nextToken();
    					String nmmasdos = st.nextToken();
    					String enrolled = st.nextToken();
    					String maxEnrolled = st.nextToken();
    					String minEnrolled = st.nextToken();
    					String unique_classpool_id = st.nextToken();
    					
    					stmt.setString(1, thsms);//THSMSTRNLM
    					stmt.setString(2, Constants.getKdpti());//KDPTITRNLM
    					stmt.setString(3, kdjen);//KDJENTRNLM
    					stmt.setString(4, kdpst);//KDPSTTRNLM,
    					stmt.setString(5, npmhs);//NPMHSTRNLM,
    					stmt.setString(6, kdkmk);//KDKMKTRNLM,
    					stmt.setInt(7, Integer.valueOf(sksmk).intValue());//SKSMKTRNLM,
    					stmt.setString(8, ""+kelas);//KELASTRNLM,
    					stmt.setString(9, shift);//SHIFTTRNLM,
    					stmt.setLong(10, Long.valueOf(idkmk).longValue());//IDKMKTRNLM,
    					stmt.setBoolean(11, true);//ADD_REQ
    					if(kodeKampus==null || Checker.isStringNullOrEmpty(kodeKampus)) {
    						stmt.setNull(12, java.sql.Types.VARCHAR);
    					}
    					else {
    						stmt.setString(12, kodeKampus);//kode kampus
    					}	
    					stmt.setInt(13, Integer.parseInt(unique_classpool_id));
    					i = stmt.executeUpdate();
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
    					////System.out.println(ignore);
    				}
    			}
    		}	
        }
    	return i;	
    }
    
    
    public int updateKrsCp_v1(String thsms,String kdjen,String kdpst,String npmhs,Vector VkelasSelected) {
    	int i = 0;
    	if(VkelasSelected!=null && VkelasSelected.size()>0) {
    		ListIterator li = VkelasSelected.listIterator();
    		//fix sks==null
    		while(li.hasNext()) {
				String brs = (String)li.next();
				////System.out.println("baris-00="+brs);
				if(brs.startsWith("add_req")) {
					StringTokenizer st = new StringTokenizer(brs,",");
					String reqType = st.nextToken();
					String idkmk = st.nextToken();
					String shift = st.nextToken();
					String kelas = st.nextToken();
					String kdkmk = st.nextToken();
					String sksmk = st.nextToken();
					if(sksmk.equalsIgnoreCase("null")) {
						sksmk = ""+Checker.getSksmk(idkmk);
					}
					String currStatus = st.nextToken();
					String npmdos = st.nextToken();
					String npmasdos = st.nextToken();
					String canceled = st.nextToken();
					String kodeKelas = st.nextToken();
					String kodeRuang = st.nextToken();
					String kodeGedung = st.nextToken();
					String kodeKampus = st.nextToken();
					String tknDayTime = st.nextToken();
					String nmmdos = st.nextToken();
					String nmmasdos = st.nextToken();
					String enrolled = st.nextToken();
					String maxEnrolled = st.nextToken();
					String minEnrolled = st.nextToken();
					String unique_classpool_id = st.nextToken();
					String cuid_init = null;
					if(st.hasMoreTokens()) {
						cuid_init = st.nextToken();
					}
					
					li.set(reqType+","+idkmk+","+shift+","+kelas+","+kdkmk+","+sksmk+","+sksmk+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+unique_classpool_id+","+cuid_init);
				}
    		}	
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			
    			
    			li = VkelasSelected.listIterator();
    			//proses drop_req
    			//stmt = con.prepareStatement("UPDATE TRNLM set ADD_REQ=?,DRP_REQ=? where THSMSTRNLM=? and KDPSTTRNLM=? and NPMHSTRNLM=? and KDKMKTRNLM=? and SHIFTTRNLM=? and KELASTRNLM=? ");
    			stmt = con.prepareStatement("DELETE FROM TRNLM where THSMSTRNLM=? and KDPSTTRNLM=? and NPMHSTRNLM=? and KDKMKTRNLM=? and SHIFTTRNLM=? and KELASTRNLM=? ");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				if(brs.startsWith("drop_req")) {
    					StringTokenizer st = new StringTokenizer(brs,",");
    					String reqType = st.nextToken();
    					String idkmk = st.nextToken();
    					String shift = st.nextToken();
    					String kelas = st.nextToken();
    					String kdkmk = st.nextToken();
    					
    					stmt.setString(1, thsms);
    					stmt.setString(2, kdpst);
    					stmt.setString(3, npmhs);
    					stmt.setString(4, kdkmk);
    					stmt.setString(5, shift);
    					stmt.setString(6, kelas);
    					i = i + stmt.executeUpdate();
    				}
    			}
    			
    			//harus insert trakm juga
        		//delete prev record
        		stmt = con.prepareStatement("delete from TRAKM where THSMSTRAKM=? and KDPSTTRAKM=? and NPMHSTRAKM=?");
        		stmt.setString(1, thsms);
        		stmt.setString(2, kdpst);
        		stmt.setString(3, npmhs);
        		stmt.executeUpdate();
        		//insert fresh record
        		stmt = con.prepareStatement("INSERT into TRAKM(THSMSTRAKM,KDPTITRAKM,KDJENTRAKM,KDPSTTRAKM,NPMHSTRAKM,SKSEMTRAKM,NLIPSTRAKM,SKSTTTRAKM,NLIPKTRAKM)values(?,?,?,?,?,?,?,?,?)");
        		stmt.setString(1, thsms);
        		stmt.setString(2, Constants.getKdpti());
        		stmt.setNull(3,java.sql.Types.VARCHAR);
        		stmt.setString(4, kdpst);
        		stmt.setString(5, npmhs);
        		stmt.setInt(6, 0);
        		stmt.setDouble(7, 0);
        		stmt.setInt(8, 0);
        		stmt.setDouble(9, 0);
        		stmt.executeUpdate();
        		
        		//ignore if start with ignore
    			//proses add_req
    			li = VkelasSelected.listIterator();
    			stmt = con.prepareStatement("INSERT INTO TRNLM(THSMSTRNLM,KDPTITRNLM,KDJENTRNLM,KDPSTTRNLM,NPMHSTRNLM,KDKMKTRNLM,SKSMKTRNLM,KELASTRNLM,SHIFTTRNLM,IDKMKTRNLM,ADD_REQ,KODE_KAMPUS,CLASS_POOL_UNIQUE_ID,CUID_INIT)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				if(brs.startsWith("add_req")) {
    					StringTokenizer st = new StringTokenizer(brs,",");
    					
    					String reqType = st.nextToken();
    					String idkmk = st.nextToken();
    					String shift = st.nextToken();
    					String kelas = st.nextToken();
    					String kdkmk = st.nextToken();
    					String sksmk = st.nextToken();
    					String currStatus = st.nextToken();
    					String npmdos = st.nextToken();
    					String npmasdos = st.nextToken();
    					String canceled = st.nextToken();
    					String kodeKelas = st.nextToken();
    					String kodeRuang = st.nextToken();
    					String kodeGedung = st.nextToken();
    					String kodeKampus = st.nextToken();
    					String tknDayTime = st.nextToken();
    					String nmmdos = st.nextToken();
    					String nmmasdos = st.nextToken();
    					String enrolled = st.nextToken();
    					String maxEnrolled = st.nextToken();
    					String minEnrolled = st.nextToken();
    					String unique_classpool_id = st.nextToken();
    					String cuid_init = null;
    					if(st.hasMoreTokens()) {
    						cuid_init = st.nextToken();
    					}
    						
    					stmt.setString(1, thsms);//THSMSTRNLM
    					stmt.setString(2, Constants.getKdpti());//KDPTITRNLM
    					stmt.setString(3, kdjen);//KDJENTRNLM
    					stmt.setString(4, kdpst);//KDPSTTRNLM,
    					stmt.setString(5, npmhs);//NPMHSTRNLM,
    					stmt.setString(6, kdkmk);//KDKMKTRNLM,
    					stmt.setInt(7, Integer.valueOf(sksmk).intValue());//SKSMKTRNLM,
    					stmt.setString(8, ""+kelas);//KELASTRNLM,
    					stmt.setString(9, shift);//SHIFTTRNLM,
    					stmt.setLong(10, Long.valueOf(idkmk).longValue());//IDKMKTRNLM,
    					stmt.setBoolean(11, true);//ADD_REQ
    					if(kodeKampus==null || Checker.isStringNullOrEmpty(kodeKampus)) {
    						stmt.setNull(12, java.sql.Types.VARCHAR);
    					}
    					else {
    						stmt.setString(12, kodeKampus);//kode kampus
    					}	
    					stmt.setLong(13, Long.parseLong(unique_classpool_id));
    					if(cuid_init==null || Checker.isStringNullOrEmpty(cuid_init)) {
    						stmt.setNull(14, java.sql.Types.INTEGER);
    					}
    					else {
    						stmt.setLong(14, Long.parseLong(cuid_init));
    					}
    					i = stmt.executeUpdate();
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
    					////System.out.println(ignore);
    				}
    			}
    		}	
        }
    	return i;	
    }
    
    
    public int updateKrsCpContinuSystem(String npmhs, String[]infoKelas) {
    	int i = 0;
    	if(infoKelas!=null && infoKelas.length>0) {
    		try {
    			//cari kdkmk di trnlm mhs terkait, bila >1 x ngambil maka input tahun terkini
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from TRNLM where NPMHSTRNLM=? and IDKMKTRNLM=? order by THSMSTRNLM");
    			
    			for(int j=0;j<infoKelas.length;j++) {
    				//////System.out.println(infoKelas[j]);
    				//<option value="<%=idkur_cp%>`<%=idkmk_cp%>`<%=thsmsKrs%>`<%=kdpst_cp%>`<%=shift_cp%>`<%=nopll_cp%>"><%=nmmdos_cp %> (<%=shift_cp %>)</option>
    				if(infoKelas[j]!=null && !Checker.isStringNullOrEmpty(infoKelas[j])) {
    					StringTokenizer st = new StringTokenizer(infoKelas[j],"`");
    					//////System.out.println("infoKelas["+j+"]="+infoKelas[j]);
    					//////System.out.println("infoKelas["+j+"]="+st.countTokens());
    					if(st.countTokens()>8) {
    						st.nextToken();//ignore null
    					}
    					String idkur = st.nextToken();
    					String idkmk = st.nextToken();
    					String thsmsKrs = st.nextToken();
    					String kdpst = st.nextToken();
    					String shiftKls = st.nextToken();
    					String noKlsPll = st.nextToken();
    					String kodeKmp = st.nextToken();
    					String cuid = st.nextToken();
    					stmt.setString(1, npmhs);
    					stmt.setInt(2, Integer.parseInt(idkmk));
    					rs = stmt.executeQuery();
    					String targetThsms = "";
    					while(rs.next()) { // jadi thsms terakhir yg keplih kalo sebelumnya dah pernah ambl MKnya
    						targetThsms = rs.getString("THSMSTRNLM"); 
    					}
    					infoKelas[j] = infoKelas[j]+"`"+targetThsms;
    				}
    			}
    			//update TRNLM update classpoolid && kodeKampus
    			stmt=con.prepareStatement("update TRNLM set KODE_KAMPUS=?, CLASS_POOL_UNIQUE_ID=? where THSMSTRNLM=? and NPMHSTRNLM=? and IDKMKTRNLM=? and NLAKH_BY_DOSEN=?");
    			for(int j=0;j<infoKelas.length;j++) {
    				//////System.out.println(infoKelas[j]);
    				boolean reset = false;
    				if(infoKelas[j]!=null && !Checker.isStringNullOrEmpty(infoKelas[j])) {
    					StringTokenizer st = new StringTokenizer(infoKelas[j],"`");
    					//////System.out.println("st tokens = "+st.countTokens());
    					if(st.countTokens()>9) {
    						
    						reset = true;
    						st.nextToken();
    					}
    					//////System.out.println("reset="+reset);
    					String idkur = st.nextToken();
    					String idkmk = st.nextToken();
    					String thsmsKrs = st.nextToken();
    					String kdpst = st.nextToken();
    					String shiftKls = st.nextToken();
    					String noKlsPll = st.nextToken();
    					String kodeKmp = st.nextToken();
    					String cuid = st.nextToken();
    					String targetThsms = st.nextToken();
    					if(!reset) {
    						stmt.setString(1, kodeKmp);
        					stmt.setInt(2, Integer.parseInt(cuid));
    					}
    					else {
    						stmt.setNull(1, java.sql.Types.VARCHAR);
        					stmt.setNull(2, java.sql.Types.INTEGER);
    					}	
        					stmt.setString(3, targetThsms);
        					stmt.setString(4, npmhs);
        					stmt.setInt(5, Integer.parseInt(idkmk));
        					stmt.setBoolean(6, false);
        					//////System.out.println(targetThsms+","+npmhs+","+idkmk+"="+stmt.executeUpdate());
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
        		if (con!=null) {
        			try {
        				con.close();
        			}
        			catch (Exception ignore) {
        				////System.out.println(ignore);
        			}
        		}
        	}
    	}
    	return i;	
    }
    
    public void updateMyCurPa(String myKdpst, String myNpm) {
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			String thsmsPmb = Checker.getThsmsPmb();
			stmt = con.prepareStatement("select * from ACADEMIC_ADVISOR where THSMS=? and KDPST=?");
			stmt.setString(1, thsmsPmb);
			stmt.setString(2, myKdpst);
			rs = stmt.executeQuery();
			if(rs.next()) {
				String npmpa = rs.getString("NPMPA");
				stmt = con.prepareStatement("select NMMHSMSMHS from CIVITAS where NPMHSMSMHS=?");
				stmt.setString(1, npmpa);
				rs = stmt.executeQuery();
				String nmmpa = null;
				if(rs.next()) {
					nmmpa = rs.getString("NMMHSMSMHS");
				}
				stmt = con.prepareStatement("update EXT_CIVITAS set NPM_PA=?,NMM_PA=? where NPMHSMSMHS=?");
				stmt.setString(1,npmpa);
				if(nmmpa!=null) {
					stmt.setString(2,nmmpa);
				}
				else {
					stmt.setNull(2,java.sql.Types.VARCHAR);
				}
				stmt.setString(3,myNpm);
				stmt.executeUpdate();
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
					////System.out.println(ignore);
				}
			}
		}	
    }
    
    
    
    /*
     * deprecated diganti ma 5 input variable (tambahan kelas)
     */
    public void insertRequestBukaKelas(String[] infoKelasDosen,String[] infoKelasMhs,String kdpst,String idkur) {
    	boolean allowBukaKelas = Checker.isAllowRequestBukaKelas(kdpst);
    	if(allowBukaKelas && infoKelasDosen!=null && infoKelasDosen.length>0) {
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			//////System.out.println("idkir = "+idkur);
    			int idKur = Integer.valueOf(idkur).intValue();
    			String thsmsBukaKelas = Checker.getThsmsBukaKelas();
    			//1. delete prev record
    			stmt = con.prepareStatement("delete from CLASS_POOL where THSMS=? and KDPST=? and IDKUR=?");
    			stmt.setString(1, thsmsBukaKelas);
    			stmt.setString(2, kdpst);
    			stmt.setLong(3,idKur);
    			stmt.executeUpdate();
    			//stmt = con.prepareStatement("INSERT INTO CLASS_POOL(IDKMK,THSMS,KDPST,SHIFT,NORUT_KELAS_PARALEL,INIT_NPM_INPUT,LATEST_NPM_UPDATE,NODOS,NOASDOS,NMMDOS,NMMASDOS,SUB_KETER_KDKMK,INIT_REQ_TIME,TARGET_TTMHS,IDKUR)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    			stmt = con.prepareStatement("INSERT INTO CLASS_POOL(IDKMK,THSMS,KDPST,SHIFT,NORUT_KELAS_PARALEL,INIT_NPM_INPUT,LATEST_NPM_UPDATE,NPMDOS,NOASDOS,NMMDOS,NMMASDOS,SUB_KETER_KDKMK,INIT_REQ_TIME,TARGET_TTMHS,IDKUR)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    			for(int i=0;i<infoKelasDosen.length;i++) {
    				//<%=kdkmk%>||<%=nakmk%>||<%=shift%>||<%=idkmk%>||<%=nodos%>||<%=nmdos%>||<%=optKeter%>||<%=val %>
    				StringTokenizer st = new StringTokenizer(infoKelasDosen[i],"||");
    				//////System.out.println(infoKelasDosen[i]);
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String shift = st.nextToken();
    				String idkmk = st.nextToken();
    				String norut = st.nextToken();
    				String nodos = st.nextToken();
    				String nmdos = st.nextToken();
    				String optKeter = st.nextToken();
    				String val = st.nextToken();
    				String ttmhs = st.nextToken();
    				stmt.setLong(1,Long.valueOf(idkmk).longValue());
    				stmt.setString(2,thsmsBukaKelas);
    				stmt.setString(3,kdpst);
    				stmt.setString(4,shift);
    				stmt.setInt(5, Integer.valueOf(norut).intValue());
    				stmt.setString(6, this.operatorNpm);
    				stmt.setString(7, this.operatorNpm);
    				stmt.setString(8,nodos);
    				stmt.setNull(9,java.sql.Types.VARCHAR);
    				stmt.setString(10,nmdos);
    				stmt.setNull(11,java.sql.Types.VARCHAR);
    				
    				if(optKeter.equalsIgnoreCase("yesketer") && !Checker.isStringNullOrEmpty(val)) {
    					stmt.setString(12,val);
    				}
    				else {
    					stmt.setNull(12,java.sql.Types.VARCHAR);
    				}
    				stmt.setTimestamp(13, AskSystem.getCurrentTimestamp());
    				stmt.setInt(14, Integer.valueOf(ttmhs).intValue());
    				stmt.setLong(15,idKur);
    				stmt.executeUpdate();
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
    	else {
    		//not allow buka kelas
    	}
    }
    
    
    public void insertRequestBukaKelas(String[] infoKelasDosen,String[] infoKelasMhs,String kdpst,String idkur,String tambahanKelas, String kodeKampus) {
    	boolean allowBukaKelas = Checker.isAllowRequestBukaKelas(kdpst);
    	if(allowBukaKelas && infoKelasDosen!=null && infoKelasDosen.length>0) {
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			//////System.out.println("idkir = "+idkur);
    			int idKur = Integer.valueOf(idkur).intValue();
    			String thsmsBukaKelas = Checker.getThsmsBukaKelas();
    			//1. delete prev record kalo tidak ada tambahan kelas
    			if(tambahanKelas==null || !tambahanKelas.equalsIgnoreCase("yes")) {
    				stmt = con.prepareStatement("delete from CLASS_POOL where THSMS=? and KDPST=? and IDKUR=?");
        			stmt.setString(1, thsmsBukaKelas);
        			stmt.setString(2, kdpst);
        			stmt.setLong(3,idKur);
        			stmt.executeUpdate();
    			}
    			else {
    				//kalo kelas tambahan : remove status rejected pada class poll
    				stmt = con.prepareStatement("update CLASS_POOL set REJECTED=? where THSMS=? and KDPST=? and IDKUR=?");
        			stmt.setBoolean(1, false);
        			stmt.setString(2, thsmsBukaKelas);
        			stmt.setString(3, kdpst);
        			stmt.setLong(4,idKur);
        			stmt.executeUpdate();
    			}
    			//stmt = con.prepareStatement("INSERT INTO CLASS_POOL(IDKMK,THSMS,KDPST,SHIFT,NORUT_KELAS_PARALEL,INIT_NPM_INPUT,LATEST_NPM_UPDATE,NODOS,NOASDOS,NMMDOS,NMMASDOS,SUB_KETER_KDKMK,INIT_REQ_TIME,TARGET_TTMHS,IDKUR)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    			stmt = con.prepareStatement("INSERT INTO CLASS_POOL(IDKMK,THSMS,KDPST,SHIFT,NORUT_KELAS_PARALEL,INIT_NPM_INPUT,LATEST_NPM_UPDATE,NPMDOS,NOASDOS,NMMDOS,NMMASDOS,SUB_KETER_KDKMK,INIT_REQ_TIME,TARGET_TTMHS,IDKUR,KODE_KAMPUS)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    			for(int i=0;i<infoKelasDosen.length;i++) {
    				//<%=kdkmk%>||<%=nakmk%>||<%=shift%>||<%=idkmk%>||<%=nodos%>||<%=nmdos%>||<%=optKeter%>||<%=val %>
    				StringTokenizer st = new StringTokenizer(infoKelasDosen[i],"||");
    				//////System.out.println(infoKelasDosen[i]);
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String shift = st.nextToken();
    				String idkmk = st.nextToken();
    				String norut = st.nextToken();
    				String nodos = st.nextToken();
    				String nmdos = st.nextToken();
    				String optKeter = st.nextToken();
    				String val = st.nextToken();
    				String ttmhs = st.nextToken();
    				stmt.setLong(1,Long.valueOf(idkmk).longValue());
    				stmt.setString(2,thsmsBukaKelas);
    				stmt.setString(3,kdpst);
    				stmt.setString(4,shift);
    				stmt.setInt(5, Integer.valueOf(norut).intValue());
    				stmt.setString(6, this.operatorNpm);
    				stmt.setString(7, this.operatorNpm);
    				stmt.setString(8,nodos);
    				stmt.setNull(9,java.sql.Types.VARCHAR);
    				stmt.setString(10,nmdos);
    				stmt.setNull(11,java.sql.Types.VARCHAR);
    				
    				if(optKeter.equalsIgnoreCase("yesketer") && !Checker.isStringNullOrEmpty(val)) {
    					stmt.setString(12,val);
    				}
    				else {
    					stmt.setNull(12,java.sql.Types.VARCHAR);
    				}
    				stmt.setTimestamp(13, AskSystem.getCurrentTimestamp());
    				stmt.setInt(14, Integer.valueOf(ttmhs).intValue());
    				stmt.setLong(15,idKur);
    				stmt.setString(16,kodeKampus);
    				stmt.executeUpdate();
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
    	else {
    		//not allow buka kelas
    	}
    }
    
    public int tolakRequestBukaKelas(String thsms,String kdpst, String needAprBy,String alasan) {
    	int i=0;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//String thsmsPmb = Checker.getThsmsPmb();
			stmt = con.prepareStatement("select * from CLASS_POOL where THSMS=? and KDPST=?");
			stmt.setString(1, thsms);
			stmt.setString(2, kdpst);
			rs = stmt.executeQuery();
			rs.next();
			String latestStatusInfo = ""+rs.getString("LATEST_STATUS_INFO");
			if(Checker.isStringNullOrEmpty(latestStatusInfo)) {
				latestStatusInfo = "";
			}	
			
			stmt = con.prepareStatement("update CLASS_POOL set LATEST_STATUS_INFO=?,REJECTED=?,PASSED=? where KDPST=? and THSMS=?");
			String status = "DITOLAK "+needAprBy+"";
			String latesStatusInfo = latestStatusInfo+"<br/><div style=\"color:red;font-weight:bold\">"+status+" - "+AskSystem.getCurrentTimestamp()+"</div>";
			if(!Checker.isStringNullOrEmpty(alasan)) {
				latesStatusInfo = latesStatusInfo+"-&nbsp&nbsp&nbsp<i>"+alasan+"</i><br/>";
			}
			stmt.setString(1, latesStatusInfo);
			stmt.setBoolean(2, true);
			stmt.setBoolean(3, false);
			stmt.setString(4, kdpst);
			stmt.setString(5, thsms);
			
			i=stmt.executeUpdate();
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
					////System.out.println(ignore);
				}
			}
		}
    	return i;
    }    
    
    
    public int grantedRequestBukaKelas(String thsms,String kdpst, String needAprBy,String alasan,String tknApr) {
    	int i=0;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//////System.out.println(tknApr);
			stmt = con.prepareStatement("select * from CLASS_POOL where THSMS=? and KDPST=?");
			stmt.setString(1, thsms);
			stmt.setString(2, kdpst);
			rs = stmt.executeQuery();
			rs.next();
			String latestStatusInfo = ""+rs.getString("LATEST_STATUS_INFO");
			String tknNpmApproval = ""+rs.getString("TKN_NPM_APPROVAL");
			String tknTimeApproval = ""+rs.getString("TKN_APPROVAL_TIME");
			if(Checker.isStringNullOrEmpty(latestStatusInfo)) {
				latestStatusInfo = "";
			}	
			if(Checker.isStringNullOrEmpty(tknNpmApproval)) {
				tknNpmApproval = "";
			}	
			if(Checker.isStringNullOrEmpty(tknTimeApproval)) {
				tknTimeApproval = "";
			}	
			
			stmt = con.prepareStatement("update CLASS_POOL set LATEST_STATUS_INFO=?,LOCKED=?,TKN_NPM_APPROVAL=?,TKN_APPROVAL_TIME=?,REJECTED=?,PASSED=? where KDPST=? and THSMS=?");
			String status = "DISETUJUI "+needAprBy+"";
			//String latesStatusInfo = latestStatusInfo+"<br/><div style=\"color:red;font-weight:bold\">"+status+" - "+AskSystem.getCurrentTimestamp()+"</div>-&nbsp&nbsp&nbsp<i>"+alasan+"</i><br/>";
			String latesStatusInfo = latestStatusInfo+"<br/><div style=\"color:#369;font-weight:bold\">"+status+" - "+AskSystem.getCurrentTimestamp()+"</div>";
			if(!Checker.isStringNullOrEmpty(alasan)) {
				latesStatusInfo = latesStatusInfo+"-&nbsp&nbsp&nbsp<i>"+alasan+"</i><br/>";
			}
			//cek next apptoval
			StringTokenizer st = new StringTokenizer(tknApr,",");
			boolean match = false;
			while(st.hasMoreTokens()&&!match) {
				String curApprovee = st.nextToken();
				if(curApprovee.equalsIgnoreCase(needAprBy)) {
					match = true;
				}
			}
			if(st.hasMoreTokens()) {
				stmt.setString(1,latesStatusInfo);
				stmt.setBoolean(2, false);
				stmt.setString(3, tknNpmApproval+","+this.operatorNpm);
				stmt.setString(4, tknTimeApproval+","+AskSystem.getCurrentTimestamp());
				stmt.setBoolean(5, false);
				stmt.setBoolean(6, true);
				stmt.setString(7, kdpst);
				stmt.setString(8, thsms);
				//stmt.executeUpdate();
			}
			else {
				//approval teakhir
				
				stmt.setString(1,latesStatusInfo);
				stmt.setBoolean(2, true);
				stmt.setString(3, tknNpmApproval+","+this.operatorNpm);
				stmt.setString(4, tknTimeApproval+","+AskSystem.getCurrentTimestamp());
				stmt.setBoolean(5, false);
				stmt.setBoolean(6, true);
				stmt.setString(7, kdpst);
				stmt.setString(8, thsms);
				//stmt.executeUpdate();
			}
			i = stmt.executeUpdate();
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
					////System.out.println(ignore);
				}
			}
		}
    	return i;
    }   
    
    public String getObjNicknameGivenNpmhs(String kdpst,String npmhs) {
    	String objNickName=null;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select OBJ_NICKNAME from OBJECT INNER JOIN CIVITAS ON OBJECT.ID_OBJ=CIVITAS.ID_OBJ WHERE KDPSTMSMHS=? and NPMHSMSMHS=?");
			stmt.setString(1, kdpst);
			stmt.setString(2, npmhs);
			rs = stmt.executeQuery();
			if(rs.next()) {
				objNickName=""+rs.getString("OBJ_NICKNAME");
			}
			else {
				objNickName = null;
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
					////System.out.println(ignore);
				}
			}
		}
    	return objNickName;
    }
    
    public int postMainTopicTargetedIndividualNpmhs(String isitopic, long objLvlCreator, long objIdCreator, String nicknameCreator, String targetObjNickName, String targetNpmhs, String hiddenAtCreator, String isCreatorPetugas) {
    	//dipake untuk post dgn tujuan :
    	//1. BAA
    	int i=0;
    	//////System.out.println("isitopik=="+isitopic);
    	isitopic = isitopic.replace("\"", "'");
    	//////System.out.println("isitopik=="+isitopic);
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("INSERT INTO TOPIK (TOPIKCONTENT,NPMHSCREATOR,NMMHSCREATOR,CRETOROBJECTID,CREATOROBJNICKNAME,TARGETNPMHS,TARGETOBJECTNICKNAME,HIDENATCREATOR,CRETORISPETUGAS)VALUES(?,?,?,?,?,?,?,?,?)");
			stmt.setString(1, isitopic);
			stmt.setString(2, this.operatorNpm);
			stmt.setString(3, this.operatorNmm);
			stmt.setLong(4,objIdCreator);
			stmt.setString(5,nicknameCreator);
			if(targetNpmhs==null) {
				stmt.setNull(6,java.sql.Types.VARCHAR);
			}
			else {
				stmt.setString(6,targetNpmhs);
			}	
			stmt.setString(7,targetObjNickName);
			stmt.setBoolean(8, Boolean.valueOf(hiddenAtCreator).booleanValue());
			stmt.setBoolean(9, Boolean.valueOf(isCreatorPetugas).booleanValue());
			//stmt.setString(2, kdpst);
			i = stmt.executeUpdate();
			/*
			if(i>0) {
				//insert notification
				stmt = con.prepareStatement("update NEW_MSG_NOTIFICATION_OBJECTNICKNAME set GOT_MSG=? where OBJNICKNAME=?");
				stmt.setBoolean(1, true);
				stmt.setString(2, targetObjNickName);
				i = stmt.executeUpdate();
				if(i<1) {
					stmt = con.prepareStatement("insert into NEW_MSG_NOTIFICATION_OBJECTNICKNAME (OBJNICKNAME,GOT_MSG)VALUES(?,?)");
					stmt.setString(1,targetObjNickName);
					stmt.setBoolean(2, true);
					stmt.executeUpdate();
				}
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
					////System.out.println(ignore);
				}
			}
		}
    	return i;
    }   	
    
    public int postMainTopicTargetedIndividualNpmhsMarkedAsRead(String isitopic, long objLvlCreator, long objIdCreator, String nicknameCreator, String targetObjNickName, String targetNpmhs, String hiddenAtCreator, String isCreatorPetugas) {
    	//dipake untuk post dgn tujuan :
    	//1. BAA
    	int i=0;
    	//////System.out.println("isitopik=="+isitopic);
    	isitopic = isitopic.replace("\"", "'");
    	//////System.out.println("isitopik=="+isitopic);
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("INSERT INTO TOPIK (TOPIKCONTENT,NPMHSCREATOR,NMMHSCREATOR,CRETOROBJECTID,CREATOROBJNICKNAME,TARGETNPMHS,TARGETOBJECTNICKNAME,HIDENATCREATOR,MARKEDASREADATTARGET,CRETORISPETUGAS)VALUES(?,?,?,?,?,?,?,?,?,?)");
			stmt.setString(1, isitopic);
			stmt.setString(2, this.operatorNpm);
			stmt.setString(3, this.operatorNmm);
			stmt.setLong(4,objIdCreator);
			stmt.setString(5,nicknameCreator);
			if(targetNpmhs==null) {
				stmt.setNull(6,java.sql.Types.VARCHAR);
			}
			else {
				stmt.setString(6,targetNpmhs);
			}	
			stmt.setString(7,targetObjNickName);
			stmt.setBoolean(8, Boolean.valueOf(hiddenAtCreator).booleanValue());
			stmt.setString(9,targetObjNickName);
			stmt.setBoolean(10, Boolean.valueOf(isCreatorPetugas).booleanValue());
			//stmt.setString(2, kdpst);
			i = stmt.executeUpdate();
			/*
			if(i>0) {
				//insert notification
				stmt = con.prepareStatement("update NEW_MSG_NOTIFICATION_OBJECTNICKNAME set GOT_MSG=? where OBJNICKNAME=?");
				stmt.setBoolean(1, true);
				stmt.setString(2, targetObjNickName);
				i = stmt.executeUpdate();
				if(i<1) {
					stmt = con.prepareStatement("insert into NEW_MSG_NOTIFICATION_OBJECTNICKNAME (OBJNICKNAME,GOT_MSG)VALUES(?,?)");
					stmt.setString(1,targetObjNickName);
					stmt.setBoolean(2, true);
					stmt.executeUpdate();
				}
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
					////System.out.println(ignore);
				}
			}
		}
    	return i;
    }   	

    
    
    public int postMainTopicByTargetObjectNickName(String isitopic, long objLvlCreator, long objIdCreator, String nickname, String targetObjNickName) {
    	//dipake untuk post dgn tujuan :
    	//1. BAA
    	int i=0;
    	//////System.out.println("isitopik=="+isitopic);
    	isitopic = isitopic.replace("\"", "'");
    	//////System.out.println("isitopik=="+isitopic);
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("INSERT INTO TOPIK (TOPIKCONTENT,NPMHSCREATOR,NMMHSCREATOR,CRETOROBJECTID,CREATOROBJNICKNAME,TARGETOBJECTNICKNAME)VALUES(?,?,?,?,?,?)");
			stmt.setString(1, isitopic);
			stmt.setString(2, this.operatorNpm);
			stmt.setString(3, this.operatorNmm);
			stmt.setLong(4,objIdCreator);
			stmt.setString(5,nickname);
			stmt.setString(6,targetObjNickName);
			//stmt.setString(2, kdpst);
			i = stmt.executeUpdate();
			/*
			if(i>0) {
				//insert notification
				stmt = con.prepareStatement("update NEW_MSG_NOTIFICATION_OBJECTNICKNAME set GOT_MSG=? where OBJNICKNAME=?");
				stmt.setBoolean(1, true);
				stmt.setString(2, targetObjNickName);
				i = stmt.executeUpdate();
				if(i<1) {
					stmt = con.prepareStatement("insert into NEW_MSG_NOTIFICATION_OBJECTNICKNAME (OBJNICKNAME,GOT_MSG)VALUES(?,?)");
					stmt.setString(1,targetObjNickName);
					stmt.setBoolean(2, true);
					stmt.executeUpdate();
				}
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
					////System.out.println(ignore);
				}
			}
		}
    	return i;
    }   	

    public int postMainTopicByTamu(String nama, String email, String[]targetReceiver, String pesan) {
    	//dipake untuk post dgn tujuan :
    	
    	//1. BAA
    	int j=0;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			String targetNickname = null;
			String SenderNickname = "TAMU";
			//stmt = con.prepareStatement("INSERT INTO TOPIK (TOPIKCONTENT,NMMHSCREATOR,CRETOROBJECTID,CREATOROBJNICKNAME,TARGETOBJECTNICKNAME)VALUES(?,?,?,?,?,?)");
			stmt = con.prepareStatement("INSERT INTO TOPIK (TOPIKCONTENT,NMMHSCREATOR,CREATOROBJNICKNAME,TARGETOBJECTNICKNAME)VALUES(?,?,?,?)");
			for(int i=0;i<targetReceiver.length;i++) {
				if(targetReceiver[i].equalsIgnoreCase("pasca")) {
					targetNickname = "OPERATOR TU PASCA";
				}
				else {
					targetNickname = "OPERATOR PMB SATYAGAMA";
				}
			
				stmt.setString(1, pesan);
				stmt.setString(2, nama.toUpperCase()+"<br/>"+email);
				stmt.setString(3,SenderNickname);
				stmt.setString(4,targetNickname);
			    j = stmt.executeUpdate();
			}
			/*
			if(i>0) {
				//insert notification
				stmt = con.prepareStatement("update NEW_MSG_NOTIFICATION_OBJECTNICKNAME set GOT_MSG=? where OBJNICKNAME=?");
				stmt.setBoolean(1, true);
				stmt.setString(2, targetObjNickName);
				i = stmt.executeUpdate();
				if(i<1) {
					stmt = con.prepareStatement("insert into NEW_MSG_NOTIFICATION_OBJECTNICKNAME (OBJNICKNAME,GOT_MSG)VALUES(?,?)");
					stmt.setString(1,targetObjNickName);
					stmt.setBoolean(2, true);
					stmt.executeUpdate();
				}
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
					////System.out.println(ignore);
				}
			}
		}
    	return j;
    }   
    
    public int postMainTopicByTamuPasca(String nama, String email, String phone, String pesan) {
    	//dipake untuk post dgn tujuan :
    	
    	//1. BAA
    	int j=0;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			String targetNickname = null;
			String SenderNickname = "TAMU";
			//stmt = con.prepareStatement("INSERT INTO TOPIK (TOPIKCONTENT,NMMHSCREATOR,CRETOROBJECTID,CREATOROBJNICKNAME,TARGETOBJECTNICKNAME)VALUES(?,?,?,?,?,?)");
			stmt = con.prepareStatement("INSERT INTO TOPIK (TOPIKCONTENT,NMMHSCREATOR,CREATOROBJNICKNAME,TARGETOBJECTNICKNAME)VALUES(?,?,?,?)");
			
			targetNickname = "OPERATOR TU PASCA";
			
			
			stmt.setString(1, pesan);
			stmt.setString(2, nama.toUpperCase()+"<br/>"+email+"<br/>"+phone);
			stmt.setString(3,SenderNickname);
			stmt.setString(4,targetNickname);
			j = stmt.executeUpdate();
			
			/*
			if(i>0) {
				//insert notification
				stmt = con.prepareStatement("update NEW_MSG_NOTIFICATION_OBJECTNICKNAME set GOT_MSG=? where OBJNICKNAME=?");
				stmt.setBoolean(1, true);
				stmt.setString(2, targetObjNickName);
				i = stmt.executeUpdate();
				if(i<1) {
					stmt = con.prepareStatement("insert into NEW_MSG_NOTIFICATION_OBJECTNICKNAME (OBJNICKNAME,GOT_MSG)VALUES(?,?)");
					stmt.setString(1,targetObjNickName);
					stmt.setBoolean(2, true);
					stmt.executeUpdate();
				}
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
					////System.out.println(ignore);
				}
			}
		}
    	return j;
    }   
    
    
    public int postSubTopikBetweenMhsAndBiro(long topicId, String comment, String npmhsTopicCreator, String nmmhsCreator, String usrObjNickName, String creatorObjNickName, String targetObjNickName) {
    	int i=0;
    	comment = comment.replace("\"", "'");
    	try {
    		boolean senderIsCreator = false;
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();			
			stmt = con.prepareStatement("INSERT INTO SUBTOPIK (IDTOPIK,COMMENT,NPMHSSENDER,NMMHSSENDER,COMMENTERISPETUGAS,OBJNICKNAMESENDER,NPMHSRECEIVER,NMMHSRECEIVER,OBJNICKNAMERECEIVER)VALUES(?,?,?,?,?,?,?,?,?)");
			stmt.setLong(1, topicId);
			stmt.setString(2,comment);
			if(npmhsTopicCreator.equalsIgnoreCase(this.operatorNpm) || usrObjNickName.contains(creatorObjNickName)) {
				senderIsCreator = true;
			}
			if(senderIsCreator) { // artinya  mahasiswa yang ngirim
				
				stmt.setString(3,this.operatorNpm);
				stmt.setString(4,this.operatorNmm);
				stmt.setBoolean(5,this.petugas);
				//stmt.setString(6,usrObjNickName);
				stmt.setString(6,creatorObjNickName);
				stmt.setNull(7,java.sql.Types.VARCHAR);
				stmt.setNull(8,java.sql.Types.VARCHAR);
				stmt.setString(9,targetObjNickName);
			}
			else { //bagian biro yg ngirim 
				stmt.setString(3,this.operatorNpm);
				stmt.setString(4,this.operatorNmm);
				stmt.setBoolean(5,this.petugas);
				//stmt.setString(6,usrObjNickName);
				stmt.setString(6,targetObjNickName);
				stmt.setString(7,npmhsTopicCreator);
				stmt.setString(8,nmmhsCreator);
				stmt.setString(9,creatorObjNickName);
			}
			i = stmt.executeUpdate();
			if(i>0) {
				if(!senderIsCreator) {
				//update topik table =  update markedAsread
					stmt = con.prepareStatement("select MARKEDASREADATTARGET from TOPIK where IDTOPIK=?");
					stmt.setLong(1, topicId);
					rs = stmt.executeQuery();
					rs.next();
					boolean update = false;
					String MARKEDASREADATTARGET = rs.getString("MARKEDASREADATTARGET");
					if(MARKEDASREADATTARGET==null) {
						MARKEDASREADATTARGET = ""+targetObjNickName;
						update = true;
					}
					else {
						if(!MARKEDASREADATTARGET.contains(targetObjNickName)) {
							MARKEDASREADATTARGET = MARKEDASREADATTARGET+","+targetObjNickName;
							update = true;
						}	
					}
					if(update) {
						stmt = con.prepareStatement("UPDATE TOPIK set MARKEDASREADATTARGET=? where IDTOPIK=?");
						stmt.setString(1, MARKEDASREADATTARGET);
						stmt.setLong(2, topicId);
						stmt.executeUpdate();
						//////System.out.println("EXECUTING "+stmt.executeUpdate());
					}
					
				}	
				//ditch this idea for now
				/*
				if(senderIsCreator) { // artinya  mahasiswa yang ngirim
					//update new msg notification objnickname
					stmt = con.prepareStatement("update NEW_MSG_NOTIFICATION_OBJECTNICKNAME set GOT_MSG=? where OBJNICKNAME=?");
					stmt.setBoolean(1, true);
					stmt.setString(2,targetObjNickName);
					i = stmt.executeUpdate();
					if(i<1) {
						stmt = con.prepareStatement("insert into NEW_MSG_NOTIFICATION_OBJECTNICKNAME (OBJNICKNAME,GOT_MSG)VALUES(?,?)");
						stmt.setString(1, targetObjNickName);
					}
				}
				else { //
					//update new msg notification objnickname
					
					stmt = con.prepareStatement("update NEW_MSG_NOTIFICATION_NPM set GOT_MSG=? where NPMHS=?");
					stmt.setBoolean(1, true);
					stmt.setString(2,npmhsTopicCreator);
					i = stmt.executeUpdate();
					if(i<1) {
						stmt = con.prepareStatement("insert into NEW_MSG_NOTIFICATION_NPM (NPMHS,GOT_MSG)VALUES(?,?)");
						stmt.setString(1, npmhsTopicCreator);
						stmt.setBoolean(2, true);
						stmt.executeUpdate();
					}
				}
				*/
				//update new notification table
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
					////System.out.println(ignore);
				}
			}
		}
    	return i;
    }
    
    /* ngga jadi dipake 
     * works
     */
    /*
    public int hideTopik(long topicId,String npmhsCreator) {
    	int i=0;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();	
			String sqlstmt = null;
			
			if(npmhsCreator.equalsIgnoreCase(this.operatorNpm)) {
				//hidden at creator
				//cek current status
				stmt = con.prepareStatement("update TOPIK set HIDENATCREATOR=? where IDTOPIK=?");
						
				stmt.setBoolean(1, true);
				stmt.setLong(2, topicId);
				stmt.executeUpdate();
			}
			else {
				//hiddenattarget
				//cek current status
				sqlstmt="select * from TOPIK where IDTOPIK=?";
				stmt = con.prepareStatement(sqlstmt);
				stmt.setLong(1,topicId);
				rs = stmt.executeQuery();
				rs.next();
				String hiddenAtTarget = ""+rs.getString("HIDENATTARGET");
				if(Checker.isStringNullOrEmpty(hiddenAtTarget)) {
					//update
					stmt = con.prepareStatement("update TOPIK set HIDENATTARGET=? where IDTOPIK=?");
					stmt.setString(1, this.operatorNpm);
					stmt.setLong(2, topicId);
					stmt.executeUpdate();
				}
				else {
					if(!hiddenAtTarget.contains(this.operatorNpm)) {
						//update
						stmt = con.prepareStatement("update TOPIK set HIDENATTARGET=? where IDTOPIK=?");
						stmt.setString(1, hiddenAtTarget+","+this.operatorNpm);
						stmt.setLong(2, topicId);
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
		finally {
			if (con!=null) {
				try {
					con.close();
				}
				catch (Exception ignore) {
					////System.out.println(ignore);
				}
			}
		}
    	return i;
    }
    */
    public int hapusTopik(long topicId,String npmhsCreator) {
    	int i=0;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();	
			String sqlstmt = null;
			
			if(npmhsCreator.equalsIgnoreCase(this.operatorNpm)) {
				//hidden at creator
				//cek current status
				stmt = con.prepareStatement("update TOPIK set DELETEDBYCREATOR=? where IDTOPIK=?");
						
				stmt.setBoolean(1, true);
				stmt.setLong(2, topicId);
				stmt.executeUpdate();
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
					////System.out.println(ignore);
				}
			}
		}
    	return i;
    }
    
    public void markedAsReadAtTopikByObjekNickname(long topikid, String usrObjeckNickname) {
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		//////System.out.println("markedAsReadAtTopikByObjekNickname = "+usrObjeckNickname);
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select MARKEDASREADATTARGET from TOPIK where IDTOPIK=?");
    		stmt.setLong(1, topikid);
    		rs = stmt.executeQuery();
    		rs.next();
    		String tknWhoHadReed= rs.getString("MARKEDASREADATTARGET");
    		boolean kosong = false;
    		if(tknWhoHadReed==null || Checker.isStringNullOrEmpty(tknWhoHadReed)) {
    			tknWhoHadReed ="";
    			kosong = true;
    		}
    		boolean executeStmt = false;
    		//////System.out.println("tknWhoHadReed="+tknWhoHadReed);
    		stmt = con.prepareStatement("update TOPIK set  MARKEDASREADATTARGET=? where IDTOPIK=?");
    		if(kosong) {
    			//////System.out.println("hosong");
    			stmt.setString(1,usrObjeckNickname);
    			executeStmt = true;
    		}
    		else {
    			if(!tknWhoHadReed.contains(usrObjeckNickname)) {
    				//////System.out.println("hosong1");
    				stmt.setString(1, tknWhoHadReed+","+usrObjeckNickname);
    				executeStmt = true;
    			}	
    			else {
    				//////System.out.println("hosong2");
    			}
    		}	
    		if(executeStmt) {
    			stmt.setLong(2, topikid);
    			stmt.executeUpdate();
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
    
    
    public void markedAsReadAtTopikByTargetedNpmhs(long topikid, String targetTopikNpmhs) {
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		//////System.out.println("markedAsReadAtTopikByObjekNickname = "+usrObjeckNickname);
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select MARKEDASREADATTARGET from TOPIK where IDTOPIK=?");
    		stmt.setLong(1, topikid);
    		rs = stmt.executeQuery();
    		rs.next();
    		String tknWhoHadReed= rs.getString("MARKEDASREADATTARGET");
    		boolean kosong = false;
    		if(tknWhoHadReed==null || Checker.isStringNullOrEmpty(tknWhoHadReed)) {
    			tknWhoHadReed ="";
    			kosong = true;
    		}
    		boolean executeStmt = false;
    		//////System.out.println("tknWhoHadReed="+tknWhoHadReed);
    		stmt = con.prepareStatement("update TOPIK set  MARKEDASREADATTARGET=? where IDTOPIK=?");
    		if(kosong) {
    			//////System.out.println("hosong");
    			stmt.setString(1,targetTopikNpmhs);
    			executeStmt = true;
    		}
    		else {
    			if(!tknWhoHadReed.contains(targetTopikNpmhs)) {
    				//////System.out.println("hosong1");
    				stmt.setString(1, tknWhoHadReed+","+targetTopikNpmhs);
    				executeStmt = true;
    			}	
    			else {
    				//////System.out.println("hosong2");
    			}
    		}	
    		if(executeStmt) {
    			stmt.setLong(2, topikid);
    			stmt.executeUpdate();
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
    
    public void markedAsReadAtSubTopikByObjekNickname(long subtopikid, String usrObjeckNickname) {
    	//////System.out.println("subtopikid="+subtopikid+",usrObjeckNickname="+usrObjeckNickname);
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//stmt = con.prepareStatement("select MARKEDASREADATRECEIVER from SUBTOPIK where IDSUBTOPIK=?");
    		//stmt.setLong(1, subtopikid);
    		//rs = stmt.executeQuery();
    		//rs.next();
    		//String tknWhoHadReed= rs.getString("MARKEDASREADATRECEIVER");
    		stmt = con.prepareStatement("update SUBTOPIK set  MARKEDASREADATRECEIVER=? where IDSUBTOPIK=?");
    		stmt.setBoolean(1, true);
    		stmt.setLong(2, subtopikid);
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
    }	    
    
    //update table NEW_MSG_NOTIFICATION_OBJECTNICKNAME
    public void togleNewMsgObjectNicknameGotMsg(String targetObjeckNickname, boolean gotMsg) {
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		//////System.out.println("togledUsrNickname="+targetObjeckNickname);
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("update NEW_MSG_NOTIFICATION_OBJECTNICKNAME set GOT_MSG=? where OBJNICKNAME=?");
    		stmt.setBoolean(1, gotMsg);
    		stmt.setString(2, targetObjeckNickname);
    		int i = stmt.executeUpdate();
    		if(i<1) {
    			stmt = con.prepareStatement("INSERT INTO NEW_MSG_NOTIFICATION_OBJECTNICKNAME (OBJNICKNAME,GOT_MSG)VALUES(?,?)");
    			stmt.setString(1, targetObjeckNickname);
    			stmt.setBoolean(2,gotMsg);
    			stmt.executeUpdate();
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
    
    public void updateMsdos(String infodos, Vector vScopeProdi) {
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		//////System.out.println("togledUsrNickname="+targetObjeckNickname);
    		StringTokenizer st = new StringTokenizer(infodos,"__");
    		String nodos = null;
    		String nmdos = null;
    		boolean update = true;
    		if(st.countTokens()>1) {
    			nodos = st.nextToken();
    			nmdos = st.nextToken();
    		}
    		else {
    			nmdos = ""+infodos;
    			update = false; //input
    		}
    		//////System.out.println("nodosnama="+nodos+","+nmdos);
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cek apa udah ada
    		if(update) {
    			stmt = con.prepareStatement("select TKN_KDPST_TEACH from MSDOS where NODOS=?");
    			stmt.setString(1, nodos);
    			rs=stmt.executeQuery();
    			rs.next();
    			String scope = rs.getString("TKN_KDPST_TEACH");
    			if((scope==null||Checker.isStringNullOrEmpty(scope)) && vScopeProdi!=null) {
    				ListIterator lip = vScopeProdi.listIterator();
    				scope = "";
    				while(lip.hasNext()) {
    					scope=scope+(String)lip.next();
    					if(lip.hasNext()) {
    						scope = scope+",";
    					}
    				}
    			}
    			else {
    				ListIterator lip = vScopeProdi.listIterator();
    				while(lip.hasNext()) {
    					String kdpst = (String)lip.next();
    					if(scope.contains(kdpst)) {
    						lip.remove();
    					}
    				}
    				if(vScopeProdi.size()>0) {
    					scope = scope+",";
    				}
    				lip = vScopeProdi.listIterator();
    				while(lip.hasNext()) {
    					scope=scope+(String)lip.next();
    					if(lip.hasNext()) {
    						scope = scope+",";
    					}
    				}
    			}
    			stmt = con.prepareStatement("update MSDOS set TKN_KDPST_TEACH=?,STATUS='A' where NODOS=?");
    			stmt.setString(1, scope);
    			stmt.setString(2, nodos);
    			stmt.executeUpdate();
    		}
    		else {
    			//insert dosen baru
    			//calc nodos
    			nodos = "0000000001";
    			stmt = con.prepareStatement("select NODOS from MSDOS where NODOS like '00000%' order by nodos desc");
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				String nodosLates = rs.getString("NODOS");
    				long nextNodos = Long.valueOf(nodosLates).longValue()+1;
    				int j = (""+nextNodos).length();
    				nodos = "";
    				for(int i=0;i<(10-(""+nextNodos).length());i++) {
    					nodos = nodos+"0";
    				}
    				nodos = nodos + nextNodos;
    			}
    			ListIterator lip = vScopeProdi.listIterator();
				String scope = "";
				while(lip.hasNext()) {
					scope=scope+(String)lip.next();
					if(lip.hasNext()) {
						scope = scope+",";
					}
				}
    			stmt = con.prepareStatement("insert into MSDOS(NODOS,NIDN,KDPST_HOMEBASE,NMDOS,TKN_KDPST_TEACH,STATUS)values(?,?,?,?,?,?)");
    			stmt.setString(1, nodos);
    			stmt.setString(2, nodos);
    			stmt.setString(3, "65001");
    			stmt.setString(4, nmdos);
    			stmt.setString(5, scope);
    			stmt.setString(6, "A");
    			stmt.executeUpdate();
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
