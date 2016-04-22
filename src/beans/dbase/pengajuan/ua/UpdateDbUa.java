package beans.dbase.pengajuan.ua;

import beans.dbase.UpdateDb;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Getter;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.fileupload.FileItem;
import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class UpdateDbUa
 */
@Stateless
@LocalBean
public class UpdateDbUa extends UpdateDb {
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
    public UpdateDbUa() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbUa(String operatorNpm) {
        super(operatorNpm);
    	this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
    
    public int tolakPengajuan(int id_pengajuan_ua, String alasan, String my_role_nickname) {
    	int i = 0;
    	String operatorObjId=""+Checker.getObjectId(this.operatorNpm);
    	String newValueApproval = new String("/"+operatorObjId+"`no");
    	String oldValueApproval = null;
    	
    	String newValueShowApproval = new String("/"+operatorObjId+"-no");
    	String oldValueShowApproval = null;
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select TKN_ID_APPROVEE,TKN_SHOW_APPROVEE from EXT_PENGAJUAN_UA where ID=?");
    		stmt.setLong(1,id_pengajuan_ua);
    		rs = stmt.executeQuery();
    		rs.next();
    		String tkn_id_approvee = rs.getString("TKN_ID_APPROVEE"); //value dlm format /id`yes or no/ dan dipisahkan oleh spasi utk tkn berikutnya
    		String tkn_show_approvee = rs.getString("TKN_SHOW_APPROVEE"); //value dlm format id`yes/no dan dipisahkan oleh spasi utk tkn berikutnya
    		//cek value yg sebelumnya
    		if(tkn_id_approvee!=null  && !Checker.isStringNullOrEmpty(tkn_id_approvee)) {//udah ada valuenua == udah pernah ada pihak yg ngisi
    			StringTokenizer st = new StringTokenizer(tkn_id_approvee);
    			boolean match = false;
    			System.out.println("token id approvee2 ="+tkn_id_approvee);
    			while(st.hasMoreTokens()&&!match) {
    				String tkn = st.nextToken();
    				StringTokenizer st1 = new StringTokenizer(tkn,"`");
    				String tmpId = st1.nextToken();
    				System.out.println("tmpId >" + tmpId);
					System.out.println("operatorObjId >" +operatorObjId);
    				if(tmpId.equalsIgnoreCase("/"+operatorObjId)) {
    					match = true;
    					oldValueApproval = new String(tkn);
    					System.out.println("oldValueApproval >" + oldValueApproval);
    					System.out.println("newValueApproval >" +newValueApproval);
    					newValueApproval = tkn_id_approvee.replace(oldValueApproval, newValueApproval); // rplace value yg lama
    					System.out.println("replace >");
    					System.out.println("tkn_id_approvee >" +tkn_id_approvee);
    					
    					
    				}
    			}
    			System.out.println("match > "+match);
    			if(!match) {
    				newValueApproval = tkn_id_approvee + " "+newValueApproval;
    			}
    		}
    		else {
    			System.out.println("malah kesinsi");
    			//blum ada yg ngisi jadi langsung diisi dgn newValueApproval
    		}
    		if(tkn_show_approvee!=null  && !Checker.isStringNullOrEmpty(tkn_show_approvee)) {//udah ada valuenua == udah pernah ada pihak yg ngisi
    			StringTokenizer st = new StringTokenizer(tkn_show_approvee);
    			boolean match = false;
    			System.out.println("token show approvee3 ="+tkn_show_approvee);
    			while(st.hasMoreTokens()&&!match) {
    				String tkn = st.nextToken();
    				System.out.println("tkn ="+tkn);
    				StringTokenizer st1 = new StringTokenizer(tkn,"-");
    				String tmpId = st1.nextToken();
    				System.out.println("tmpId =>" + tmpId);
					System.out.println("operatorObjId =>" +operatorObjId);
    				if(tmpId.equalsIgnoreCase("/"+operatorObjId)) {
    					match = true;
    					oldValueShowApproval = new String(tkn);
    					newValueShowApproval = tkn_show_approvee.replace(oldValueShowApproval, newValueShowApproval); // rplace value yg lama
    				}
    			}
    			if(!match) {
    				newValueShowApproval = tkn_show_approvee+" "+newValueShowApproval;
    			}
    		}
    		else {
    			//blum ada yg ngisi jadi langsung diisi dgn newValueApproval
    		}
    		
    		//update newValueApproval
    		stmt = con.prepareStatement("update EXT_PENGAJUAN_UA set STATUS_AKHIR=?,TKN_SHOW_APPROVEE=?,TKN_ID_APPROVEE=?,SHOW_OWNER=? where ID=?");
    		stmt.setString(1, "TOLAK");
    		stmt.setString(2, newValueShowApproval);
    		stmt.setString(3, newValueApproval);
    		stmt.setBoolean(4, true);
    		stmt.setLong(5, id_pengajuan_ua);
    		stmt.executeUpdate();
    		stmt = con.prepareStatement("insert into RIWAYAT_PENGAJUAN_UA (ID,NPM_APPROVEE,STATUS,KOMEN,NPM_APPROVEE_ID,OBJ_NICKNAME_APPROVE) values (?,?,?,?,?,?)");
    		stmt.setLong(1, id_pengajuan_ua);
    		stmt.setString(2, this.operatorNpm);
    		stmt.setString(3, "TOLAK");
    		stmt.setString(4, alasan);
    		stmt.setLong(5, Long.parseLong(operatorObjId));
    		stmt.setString(6, my_role_nickname);
    		i = stmt.executeUpdate();
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
    	return i;
    }	
    /*
     * DEPRICATED
     */
    public void setSudahdibaca(String id, String tkn_show_appovee, int oprObjId) {
    	if(Checker.isStringNullOrEmpty(tkn_show_appovee)) {
    		tkn_show_appovee = new String();
    	}
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("update EXT_PENGAJUAN_UA set TKN_SHOW_APPROVEE=? where ID=?");
    		if(!tkn_show_appovee.contains("/"+oprObjId+"-no")) {
    			if(tkn_show_appovee.contains("/"+oprObjId+"-yes")) {
    				tkn_show_appovee = tkn_show_appovee.replace("/"+oprObjId+"-yes", "/"+oprObjId+"-no");
    			}
    			else {
    				tkn_show_appovee = tkn_show_appovee+" "+"/"+oprObjId+"-no";
    			}
    		}
    		stmt.setString(1, tkn_show_appovee);
    		stmt.setLong(2,Long.parseLong(id));
    		stmt.executeUpdate();
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
    }
    
    public void setSudahdibaca_v1(String id, String rule_tkn_approvee_id, String tkn_show_appovee, int oprObjId, String targetNpmhs, boolean allowUa) {
    	boolean owner = false,  allowUaa = false;
    	if(targetNpmhs.equalsIgnoreCase(this.operatorNpm)) {
    		owner = true;
    	}
    	if(rule_tkn_approvee_id.contains("/"+oprObjId+"/")) {
    		allowUaa = true;
    	}
    	
    	//allowUa = monitoree
    	//allowUaa = approvee
    	if(Checker.isStringNullOrEmpty(tkn_show_appovee)) {
    		tkn_show_appovee = new String();
    	}
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		/*
    		stmt = con.prepareStatement("update PENGAJUAN_UA set TKN_SHOW_APPROVEE=? where ID=?");
    		if(!tkn_show_appovee.contains("/"+oprObjId+"-no")) {
    			if(tkn_show_appovee.contains("/"+oprObjId+"-yes")) {
    				tkn_show_appovee = tkn_show_appovee.replace("/"+oprObjId+"-yes", "/"+oprObjId+"-no");
    			}
    			else {
    				tkn_show_appovee = tkn_show_appovee+" "+"/"+oprObjId+"-no";
    			}
    		}
    		stmt.setString(1, tkn_show_appovee);
    		stmt.setLong(2,Long.parseLong(id));
    		stmt.executeUpdate();
    		*/
    		if(owner) {
    			//artinya mhs itu sendiri
    			stmt = con.prepareStatement("update EXT_PENGAJUAN_UA set SHOW_OWNER=? where ID=?");
    			stmt.setBoolean(1, false);
    			stmt.setLong(2,Long.parseLong(id));
        		stmt.executeUpdate();
    		}
    		else if(allowUa && allowUaa) {
    			//pengaju dan approvee
    			stmt = con.prepareStatement("update EXT_PENGAJUAN_UA set SHOW_MONITOREE=?,TKN_SHOW_APPROVEE=? where ID=?");
        		if(!tkn_show_appovee.contains("/"+oprObjId+"-no")) {
        			if(tkn_show_appovee.contains("/"+oprObjId+"-yes")) {
        				tkn_show_appovee = tkn_show_appovee.replace("/"+oprObjId+"-yes", "/"+oprObjId+"-no");
        			}
        			else {
        				tkn_show_appovee = tkn_show_appovee+" "+"/"+oprObjId+"-no";
        			}
        		}
        		stmt.setBoolean(1, true); //monitoree terttup hanaya bila DONE_SO_HIDE
        		stmt.setString(2, tkn_show_appovee);
        		stmt.setLong(3,Long.parseLong(id));
        		stmt.executeUpdate();
    		}
    		else if(!allowUa && allowUaa) {
    			//approvee only
    			stmt = con.prepareStatement("update EXT_PENGAJUAN_UA set TKN_SHOW_APPROVEE=? where ID=?");
        		if(!tkn_show_appovee.contains("/"+oprObjId+"-no")) {
        			if(tkn_show_appovee.contains("/"+oprObjId+"-yes")) {
        				tkn_show_appovee = tkn_show_appovee.replace("/"+oprObjId+"-yes", "/"+oprObjId+"-no");
        			}
        			else {
        				tkn_show_appovee = tkn_show_appovee+" "+"/"+oprObjId+"-no";
        			}
        		}
        		stmt.setString(1, tkn_show_appovee);
        		stmt.setLong(2,Long.parseLong(id));
        		stmt.executeUpdate();
    		}
    		else if(allowUa && !allowUaa) {
    			//pengaju only
    			// PENGAJU tertutup bila DONE_SO_HIDDEN
    			/*
    			stmt = con.prepareStatement("update PENGAJUAN_UA set SHOW_MONITOREE=? where ID=?");
        		stmt.setBoolean(1, false);
        		stmt.setLong(2,Long.parseLong(id));
        		stmt.executeUpdate();
        		*/
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
    }
    
    /*
    public void setSudahAdaTindakan(String id, String tkn_id_appovee, int oprObjId) {
    	if(Checker.isStringNullOrEmpty(tkn_id_appovee)) {
    		tkn_id_appovee = new String();
    	}
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("update PENGAJUAN_UA set TKN_SHOW_APPROVEE=? where ID=?");
    		if(!tkn_show_appovee.contains("/"+oprObjId+"-no")) {
    			if(tkn_show_appovee.contains("/"+oprObjId+"-yes")) {
    				tkn_show_appovee = tkn_show_appovee.replace("/"+oprObjId+"-yes", "/"+oprObjId+"-no");
    			}
    			else {
    				tkn_show_appovee = tkn_show_appovee+" "+"/"+oprObjId+"-no";
    			}
    		}
    		stmt.setString(1, tkn_show_appovee);
    		stmt.setLong(2,Long.parseLong(id));
    		stmt.executeUpdate();
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
    }
    */
    public void updateStatusAkhirTabelPengajuanUa(String id, Vector vRiwayatPengajuan, String rule_tkn_approvee_id, String rule_tkn_approvee_nickname) {
    	//String status_akhir = "";
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		
    		String status_akhir = Getter.currentStatusAkhirPengajuanPadaTabelPengajuanUa(vRiwayatPengajuan, rule_tkn_approvee_id, rule_tkn_approvee_nickname);
    		stmt = con.prepareStatement("update EXT_PENGAJUAN_UA set STATUS_AKHIR=? where ID=?");
    		stmt.setString(1,status_akhir);
    		stmt.setLong(2,Long.parseLong(id));
    		stmt.executeUpdate();
    		
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
    }
    
    public int terimaPengajuan(int id_pengajuan_ua, String alasan, String my_role_nickname) {
    	int i = 0;
    	String operatorObjId=""+Checker.getObjectId(this.operatorNpm);
    	String newValueApproval = new String("/"+operatorObjId+"`yes");
    	String oldValueApproval = null;
    	
    	String newValueShowApproval = new String("/"+operatorObjId+"-no");
    	String oldValueShowApproval = null;
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select TKN_ID_APPROVEE,TKN_SHOW_APPROVEE from EXT_PENGAJUAN_UA where ID=?");
    		stmt.setLong(1,id_pengajuan_ua);
    		rs = stmt.executeQuery();
    		rs.next();
    		String tkn_id_approvee = rs.getString("TKN_ID_APPROVEE"); //value dlm format /id`yes or no/ dan dipisahkan oleh spasi utk tkn berikutnya
    		String tkn_show_approvee = rs.getString("TKN_SHOW_APPROVEE"); //value dlm format id`yes/no dan dipisahkan oleh spasi utk tkn berikutnya
    		//cek value yg sebelumnya
    		//System.out.println("token id approvee1 ="+tkn_id_approvee);
    		if(tkn_id_approvee!=null  && !Checker.isStringNullOrEmpty(tkn_id_approvee)) {//udah ada valuenua == udah pernah ada pihak yg ngisi
    			StringTokenizer st = new StringTokenizer(tkn_id_approvee);
    			boolean match = false;
    			//System.out.println("token id approvee2 ="+tkn_id_approvee);
    			while(st.hasMoreTokens()&&!match) {
    				String tkn = st.nextToken();
    				StringTokenizer st1 = new StringTokenizer(tkn,"`");
    				String tmpId = st1.nextToken();
    				//System.out.println("tmpId >" + tmpId);
					//System.out.println("operatorObjId >" +operatorObjId);
    				if(tmpId.equalsIgnoreCase("/"+operatorObjId)) {
    					match = true;
    					oldValueApproval = new String(tkn);
    					//System.out.println("oldValueApproval >" + oldValueApproval);
    					//System.out.println("newValueApproval >" +newValueApproval);
    					newValueApproval = tkn_id_approvee.replace(oldValueApproval, newValueApproval); // rplace value yg lama
    					//System.out.println("replace >");
    					//System.out.println("tkn_id_approvee >" +tkn_id_approvee);
    					
    					
    				}
    			}
    			//System.out.println("match > "+match);
    			if(!match) {
    				newValueApproval = tkn_id_approvee + " "+newValueApproval;
    			}
    		}
    		else {
    			//System.out.println("malah kesinsi");
    			//blum ada yg ngisi jadi langsung diisi dgn newValueApproval
    		}
    		
    		if(tkn_show_approvee!=null  && !Checker.isStringNullOrEmpty(tkn_show_approvee)) {//udah ada valuenua == udah pernah ada pihak yg ngisi
    			StringTokenizer st = new StringTokenizer(tkn_show_approvee);
    			boolean match = false;
    			//System.out.println("token show approvee3 ="+tkn_show_approvee);
    			while(st.hasMoreTokens()&&!match) {
    				String tkn = st.nextToken();
    				//System.out.println("tkn ="+tkn);
    				StringTokenizer st1 = new StringTokenizer(tkn,"-");
    				String tmpId = st1.nextToken();
    				//System.out.println("tmpId =>" + tmpId);
					//System.out.println("operatorObjId =>" +operatorObjId);
    				if(tmpId.equalsIgnoreCase("/"+operatorObjId)) {
    					match = true;
    					oldValueShowApproval = new String(tkn);
    					newValueShowApproval = tkn_show_approvee.replace(oldValueShowApproval, newValueShowApproval); // rplace value yg lama
    				}
    			}
    			if(!match) {
    				newValueShowApproval = tkn_show_approvee+" "+newValueShowApproval;
    			}
    		}
    		else {
    			//blum ada yg ngisi jadi langsung diisi dgn newValueApproval
    		}
    		
    		//update newValueApproval
    		//Getter.currentStatusAkhirPengajuanPadaTabelPengajuanUa(Vector vRiwayatPengajuan, String rule_tkn_approvee_id, String tkn_approvee_nickname)
    		/*
    		 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    		 * STATUS AKHIR disini akan di OVERIDE pada SERVLET.PengajuanUaApproval
    		 */
    		stmt = con.prepareStatement("update EXT_PENGAJUAN_UA set STATUS_AKHIR=?,TKN_SHOW_APPROVEE=?,TKN_ID_APPROVEE=?,SHOW_OWNER=? where ID=?");
    		stmt.setString(1, "TERIMA");
    		stmt.setString(2, newValueShowApproval);
    		stmt.setString(3, newValueApproval);
    		stmt.setBoolean(4, true);
    		stmt.setLong(5, id_pengajuan_ua);
    		stmt.executeUpdate();
    		stmt = con.prepareStatement("insert into RIWAYAT_PENGAJUAN_UA (ID,NPM_APPROVEE,STATUS,KOMEN,NPM_APPROVEE_ID,OBJ_NICKNAME_APPROVE) values (?,?,?,?,?,?)");
    		stmt.setLong(1, id_pengajuan_ua);
    		stmt.setString(2, this.operatorNpm);
    		stmt.setString(3, "TERIMA");
    		if(alasan==null || Checker.isStringNullOrEmpty(alasan)) {
    			stmt.setNull(4, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(4, alasan);	
    		}
    		
    		stmt.setLong(5, Long.parseLong(operatorObjId));
    		stmt.setString(6, my_role_nickname);
    		i = stmt.executeUpdate();
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
    	return i;
    }	
    
    public int updateJadwalUa(String id, String sked_dt, String sked_tm, String real_dt, String real_tm) {
    	java.sql.Date skdt,rldt = null;
    	java.sql.Time sktm,rltm = null;
    	int j=0;
    	skdt = Converter.formatDateBeforeInsert(sked_dt);
    	rldt = Converter.formatDateBeforeInsert(real_dt);
    	sktm = Converter.formatTimeBeforeInsert(sked_tm);
    	rltm = Converter.formatTimeBeforeInsert(real_tm);
    	//System.out.println("skdt = "+skdt);
    	//System.out.println("rldt = "+rldt);
    	//System.out.println("sktm = "+sktm);
    	//System.out.println("rltm = "+rltm);
    
    	boolean sudah_ujian = false;
    	if(rldt!=null && rltm!=null) {
    		sudah_ujian = true;
    	}
    	System.out.println("sudah_ujian = "+sudah_ujian);
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("update EXT_PENGAJUAN_UA set SCHEDULE_DATE=?,REALISASI_DATE=?,SCHEDULE_TIME=?,REALISASI_TIME=?,SHOW_OWNER=?,SHOW_MONITOREE=?,DONE_SO_HIDDEN=? where ID=?");
    		
    		
    		int i=1;
    		if(skdt == null) {
    			stmt.setNull(i++,java.sql.Types.DATE );
    		}
    		else {
    			stmt.setDate(i++, skdt);
    		}
    		
    		if(rldt == null) {
    			stmt.setNull(i++,java.sql.Types.DATE );
    		}
    		else {
    			stmt.setDate(i++, rldt);
    		}
    		
    		if(sktm == null) {
    			stmt.setNull(i++,java.sql.Types.TIME );
    		}
    		else {
    			
    			stmt.setTime(i++, sktm);
    		}
    		
    		if(rltm == null) {
    			stmt.setNull(i++,java.sql.Types.TIME );
    		}
    		else {
    			
    			stmt.setTime(i++, rltm);
    		}
    		
    		if(sudah_ujian) {
    			stmt.setBoolean(i++, false);
    			stmt.setBoolean(i++, false);
    			stmt.setBoolean(i++, true);
    		}
    		else {
    			stmt.setBoolean(i++, true);
    			stmt.setBoolean(i++, true);
    			stmt.setBoolean(i++, false);
    		}
    		
    		stmt.setLong(i++,Long.parseLong(id));
    		j = stmt.executeUpdate();
    		
    		
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
    	return j;
    }	
    
    public void uploadPengajuanUa(String fieldAndValue,FileItem item) {
    	String thsms_now = Checker.getThsmsNow();
    	String nmmhs = null;
    	String judul = "";
        String npmhs = "";
        String tipe_ua = "";
        String idkmk = "";
        String nakmk = "";
        String kdkmk = "";
        String kdpst = "";
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//reset
    		
            StringTokenizer st = new StringTokenizer(fieldAndValue,"||");
            while(st.hasMoreTokens()) {
            	String fieldNmm = st.nextToken();
            	String fieldval = st.nextToken();
            	if(fieldNmm.contains("Judul")) {
            		judul = new String(fieldval); 
            	}
            	else if(fieldNmm.contains("npmhs")) {
            		npmhs = new String(fieldval); 
            	}
            	else if(fieldNmm.contains("Tipe")) {
            		tipe_ua = new String(fieldval); 
            	}
            	else if(fieldNmm.contains("kdpst")) {
            		kdpst = new String(fieldval); 
            	}
            }
            st = new StringTokenizer(tipe_ua,"`");
            while(st.hasMoreTokens()) {
            	idkmk = st.nextToken();
            	kdkmk = st.nextToken();
            	kdkmk = kdkmk.replace(" ", "_");
            	nakmk = st.nextToken();
            	
            }
            ////System.out.println(i+".fieldName2="+fieldName);
            
            long millis = System.currentTimeMillis();
            
            String fullNameFile = item.getName();
            String fileName="";
            String fileExt="";
            st = new StringTokenizer(fullNameFile,"."); 
            int count_token = st.countTokens();
            for(int j=1; j<count_token; j++) { //untuk file yg namanya ada titik pula
            	fileName = fileName+st.nextToken();
            }
            
            
            /*
             * disini nama original file tidak dibutuhkan dan diganti dengan millis;
             */
            
            fileExt = st.nextToken();
            fileExt = fileExt.toLowerCase();
            File fileTo = null;
            //String namaFile = kdkmk+"_"+millis;
            String nuFileName = kdkmk+"_"+millis+"."+fileExt;
            String root_folder_distinc_mhs = Checker.getRootFolderIndividualMhs("ARSIP MHS");
            String target_folder = root_folder_distinc_mhs+"/"+npmhs+"/UA/"+kdkmk;
            
            fileTo = new File(target_folder);
            fileTo.mkdirs();
            fileTo = new File(target_folder +"/"+ nuFileName);
            item.write(fileTo);
            //PART 2
            //cek apa sudah ada record sebelumnya yg status nya belum tuntas = status_akhir belum lulus
            /*
             * value status akhir
             * 1. PROSES PENGAJUAN
             * 2. DISETUJUI
             * 3. LULUS
             */
            //System.out.println("npmhs - kdkmk = "+npmhs+" - "+kdkmk);
            stmt = con.prepareStatement("select * from EXT_PENGAJUAN_UA where NPMHS=? and KDKMK=? and STATUS_AKHIR<>?");
            stmt.setString(1, npmhs);
            stmt.setString(2, kdkmk);
            stmt.setString(3, "LULUS");
    		rs = stmt.executeQuery();
    		if(!rs.next()) {
    			//System.out.println("!rs.next");
    			//blum ada record = so insert
    			stmt = con.prepareStatement("INSERT INTO EXT_PENGAJUAN_UA (THSMS,KDPST,NPMHS,STATUS_AKHIR,KDKMK,FILE_NAME) VALUES (?,?,?,?,?,?)");
    			stmt.setString(1, thsms_now);
    			stmt.setString(2, kdpst);
    			stmt.setString(3, npmhs);
    			stmt.setString(4, "PROSES PENGAJUAN");		
    			stmt.setString(5, kdkmk);
    			stmt.setString(6, nuFileName);
    			stmt.executeUpdate();		

    		}
    		else {
    			//sudah ada recordnya = so update
    			//System.out.println("rs.next");
    			String id = ""+rs.getLong("ID");
    			stmt = con.prepareStatement("UPDATE EXT_PENGAJUAN_UA set STATUS_AKHIR=? where ID=?");
    			stmt.setString(1, "PROSES PENGAJUAN");
    			stmt.setLong(2, Long.parseLong(id) );
    			stmt.executeUpdate();
    		}
    		//end PART 2
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
    	//finally {
		//	if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		//    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		// if (con!=null) try { con.close();} catch (Exception ignore){}
		//}
    }    
    
    
    public String uploadInfoPengajuanAtDb(String nuFileName,String fieldAndValue, long idobj) {
    	//System.out.println("fieldAndValue@uploadInfoPengajuanAtDb="+fieldAndValue);
    	String status_update = "";
    	String thsms_now = Checker.getThsmsNow();
    	String nmmhs = null;
    	String judul = "";
        String npmhs = "";
        String tipe_ua = "";
        String idkmk = "";
        String nakmk = "";
        String kdkmk = "";
        String kdpst = "";
        
        StringTokenizer st = new StringTokenizer(fieldAndValue,"||");
 		while(st.hasMoreTokens()) {
 			String fieldNmm = st.nextToken();
 			//System.out.println("fieldNmm=="+fieldNmm);
 		    String fieldval = st.nextToken();
 		   
 		    if(fieldNmm.contains("nmmhs")) {
 		    	nmmhs = new String(fieldval); 
 		    }
 		    else if(fieldNmm.contains("Judul")) {
 		    	judul = new String(fieldval); 
 		    }
 		    //else if(fieldNmm.contains("idobj")) {
 	    	//	objId = new String(fieldval); 
 	    	//}
 	    	//else if(fieldNmm.contains("objlv")) {
 	    	//	obj_lvl = new String(fieldval); 
 	    	//}
 	    	//else if(fieldNmm.contains("cmd")) {
 	    	//	cmd = new String(fieldval); 
 	    	//}
 	    	else if(fieldNmm.contains("npmhs")) {
 	    		npmhs = new String(fieldval); 
 	    	}
 	    	else if(fieldNmm.contains("kdpst")) {
 	    		kdpst = new String(fieldval); 
 	    	}
 	    	else if(fieldNmm.contains("Tipe")) {
            	tipe_ua = new String(fieldval); 
            }
 		    
 		    
 	    }
 		st = new StringTokenizer(tipe_ua,"`");
		    while(st.hasMoreTokens()) {
          	idkmk = st.nextToken();
          	kdkmk = st.nextToken();
          	kdkmk = kdkmk.replace(" ", "_");
          	nakmk = st.nextToken();
        }
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//reset
    		//getobjid
    		
            //cek apa sudah ada record sebelumnya yg status nya belum tuntas = status_akhir belum lulus
            /*
             * value status akhir
             * 1. PROSES PENGAJUAN
             * 2. DISETUJUI
             * 3. LULUS
             */
            //stmt = con.prepareStatement("select * from PENGAJUAN_UA where THSMS=? and NPMHS=? and KDKMK=? and STATUS_AKHIR<>?");
    		
    		
    		/*
    		 * ambil record terakhir saja, krn kalo ada multiple penolakan akan numpuk
    		 */
    		stmt = con.prepareStatement("select * from EXT_PENGAJUAN_UA where THSMS=? and NPMHS=? and KDKMK=? ORDER BY ID DESC LIMIT 1");
            //System.out.println("select "+kdpst+" - "+npmhs+" - "+kdkmk);
            stmt.setString(1, thsms_now);
            stmt.setString(2, npmhs);
            stmt.setString(3, kdkmk);
            //stmt.setString(4, "LULUS");
    		rs = stmt.executeQuery();
    		if(!rs.next()) {
    			//blum ada record = so insert
    			//System.out.println("!next");
    			stmt = con.prepareStatement("INSERT INTO EXT_PENGAJUAN_UA (THSMS,KDPST,NPMHS,STATUS_AKHIR,KDKMK,FILE_NAME,JUDUL,IDOBJ) VALUES (?,?,?,?,?,?,?,?)");
    			stmt.setString(1, thsms_now);
    			stmt.setString(2, kdpst);
    			stmt.setString(3, npmhs);
    			stmt.setString(4, "PROSES PENGAJUAN");		
    			stmt.setString(5, kdkmk);
    			stmt.setString(6, nuFileName);
    			stmt.setString(7, judul);
    			stmt.setLong(8, idobj);
    			////System.out.println("insert "+kdpst+" - "+npmhs+" - "+kdkmk);
    			int i = stmt.executeUpdate();
    			if(i>0) {
    				//get ID record yg baru diinput
    				stmt = con.prepareStatement("select ID from EXT_PENGAJUAN_UA order by UPDTM desc limit 1");
    				rs = stmt.executeQuery();
    				rs.next();
    				String id = ""+rs.getLong(1);
    				status_update = id+"`Update Data Berhasil";
    			}
    			else {
    				status_update = "Update Data Gagal";
    			}

    		}
    		else {
    			//sudah ada recordnya = so update
    			////System.out.println("next");
    			////System.out.println("update ");
    			String id = ""+rs.getLong("ID");
    			String status_akhir = rs.getString("STATUS_AKHIR");
    			
    			if(status_akhir.equalsIgnoreCase("PROSES PENGAJUAN")) {
    				//belum ada pemeriksaan jadi diperbolehkan upload file yg baru
    				status_update = "Update Data Berhasil";
    				stmt = con.prepareStatement("UPDATE EXT_PENGAJUAN_UA set FILE_NAME=?,JUDUL=? where ID=?");
    				stmt.setString(1, nuFileName);
    				stmt.setString(2, judul);
    				stmt.setLong(3, Long.parseLong(id));
    				int i = stmt.executeUpdate();
    				if(i<1) {
    					status_update = id+"`Update Data Gagal, Harap Hubungi Sekretariat";
    				}
    			}
    			else if(status_akhir.equalsIgnoreCase("PROSES PENILAIAN")) {
    				//salah satu promotor sudh ada yg menyetujui, sehingga sudah tidak bisa, harus minta di tolak
    				status_update = "Anda Tidak Diperkenankan Mengirim Naskah Baru, Karena Naskah yg Terdahulu Sedang Dalam Proses Penilaian <br/> Silahkan Hubungi Sekretariat  Bila Hendak Membatalkan Naskah Sebelumnya";
    			}
    			else if(status_akhir.equalsIgnoreCase("Diterima")) {
    				//harus ditolak dulu baru resubmit
    				status_update = "And Tidak Diperkenankan Mengirim Naskah Baru, Karena Naskah yg Terdahulu Sudah Diterima dan Disetujui <br/> Silahkan Hubungi Sekretariat Bila Hendak Membatalkan Naskah Sebelumnya";
    			}
    			else if(status_akhir.equalsIgnoreCase("Ditolak")) { 
    				//PENGAJUAN ULANG
    				//1. get ID penolakan sebelumnya
    				String riwayat_penolakan_sebelumnya = ""+rs.getString("RIWAYAT_PENOLAKAN");
    				if(Checker.isStringNullOrEmpty(riwayat_penolakan_sebelumnya)) {
    					riwayat_penolakan_sebelumnya = new String();
    				}
    				
    				stmt = con.prepareStatement("select * from RIWAYAT_PENGAJUAN_UA where ID=? and STATUS=? order by ID_RI");
    				stmt.setLong(1,  Long.parseLong(id));
    				stmt.setString(2, "TOLAK");
    				rs = stmt.executeQuery();
    				while(rs.next()) {
    					String updtm = ""+rs.getTimestamp("UPDTM");
    					String obj_nick_penolak = ""+rs.getString("OBJ_NICKNAME_APPROVE");
    					String komen = rs.getString("KOMEN");
    					riwayat_penolakan_sebelumnya = riwayat_penolakan_sebelumnya+"Pengajuan Ujian Ditolak oleh "+obj_nick_penolak+" ("+updtm+")<br/>Alasan : "+komen+"<br/><br/>";
    					//Pengajuan Ujian Ditolak oleh ADMIN (2015-11-25 20:47:11.0)
    					//Alasan : oke	
    				}
    				//2. set DONE_SO_HIDDEN == karena transaksi ini sudah ada yg menggantikan
    				stmt = con.prepareStatement("UPDATE EXT_PENGAJUAN_UA set DONE_SO_HIDDEN=? where ID=?");
    				stmt.setBoolean(1, true);
    				stmt.setLong(2, Long.parseLong(id));
    				stmt.executeUpdate();
    				//3. insert
    				stmt = con.prepareStatement("INSERT INTO EXT_PENGAJUAN_UA (THSMS,KDPST,NPMHS,STATUS_AKHIR,KDKMK,FILE_NAME,JUDUL,IDOBJ,RIWAYAT_PENOLAKAN) VALUES (?,?,?,?,?,?,?,?,?)");
        			stmt.setString(1, thsms_now);
        			stmt.setString(2, kdpst);
        			stmt.setString(3, npmhs);
        			stmt.setString(4, "PROSES PENGAJUAN");		
        			stmt.setString(5, kdkmk);
        			stmt.setString(6, nuFileName);
        			stmt.setString(7, judul);
        			stmt.setLong(8, idobj);
        			stmt.setString(9, riwayat_penolakan_sebelumnya);
        			int i = stmt.executeUpdate();
        			if(i>0) {
        				//get ID record yg baru diinput
        				stmt = con.prepareStatement("select ID from EXT_PENGAJUAN_UA order by UPDTM desc limit 1");
        				rs = stmt.executeQuery();
        				rs.next();
        				id = ""+rs.getLong(1);
        				status_update = id+"`Pengajuan Ulang Berhasil";
        			}
        			else {
        				status_update = "Pengajuan Ulang Gagal";
        			}
    				//status_update = "Pengajuan Ulang Berhasil";
    			}
    			else if(status_akhir.equalsIgnoreCase("LULUS")) {
    				//tidak bisa update karena ujian sudah dilalui
    				status_update = "Update Gagal, Anda Telah Lulus Ujian Sidang ini";
    			}
    			//else if(status_akhir.equalsIgnoreCase("UJIAN ULANG")) {
    				//diperbolehkan update
    			//	status_update = "Update Data Berhasil";
    			//}
    			
    		}
    		//end PART 2
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
    	/*
    	 * status_update dimulai dgn ID+"`"+status update
    	 */
    	return status_update;
    }    

}
