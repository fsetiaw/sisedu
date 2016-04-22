package beans.dbase.topik;

import beans.dbase.UpdateDb;
import beans.tools.AddHocFunction;
import beans.tools.Checker;
import beans.tools.Getter;

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

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class UpdateDbTopik
 */
@Stateless
@LocalBean
public class UpdateDbTopik extends UpdateDb {
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
    public UpdateDbTopik() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbTopik(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
    
    /*
     * untuk PENGAJUAN TOPIK HARUS DENGAN NPM TIDAK OBJ ID, OBJ ID UNTUK SPT PENGUMUMAN
     * return value : 
     * String null bila tidak ada masalah
     * ato return String msg, misalkan pengajuan cuti sudah di approved dan locked & pengajuan dobel
     */
    public String postTopikPengajuanCuti(String target_thsms, String kdpst, String npmhs, String alasan, String nmmhs) {
    	int i=0;
    	String msg = null;
    	try {
           	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//cek apa sudah ada pengajuan sebelumnya yg tidak di reject
        	//
        	stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TIPE_PENGAJUAN=? and TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=?");
        	stmt.setString(1, "CUTI");
        	stmt.setString(2, target_thsms);
        	stmt.setString(3, npmhs);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		//System.out.println("1");
        		//sudah diajukan
        		//
        		int id = rs.getInt("ID");
        		String rejected = rs.getString("REJECTED");
        		boolean lock = rs.getBoolean("LOCKED");
        		String approved = rs.getString("APPROVED");
        		//1.cek apa rejected 
        		if(rejected!=null && !Checker.isStringNullOrEmpty(rejected)) {
        			//pernah direjected - kalo rejected != empty berarti otomati locked
        			//insert pengajuan baru , thus msg == null
        		}
        		else {
        			//rejected null or empty
        			//2. cek apa sudah disetujui
        			if(approved!=null && !Checker.isStringNullOrEmpty(approved)) {
        				if(lock) {
        					//sudah disetujui
        					msg = new String("Pengajuan cuti untuk tahun/sms "+target_thsms+" sudah diajukan dan sudah disetujui");
        				}
        				else {
        					//inprogress
        					msg = new String("Pengajuan cuti untuk tahun/sms "+target_thsms+" sudah diajukan dan dalam proses persetujuan");
        	        		
        				}
        				stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_CREATOR=?");
    	        		stmt.setBoolean(1, false); //tidak ditampilkan notifikasi, hanya update saja yg merubah statusnya
    	        		stmt.executeUpdate();
        			}
        			else {
        				//insert pengajuan = cmd==null
        			}
        		}
        		
        	}
        	
        	if(msg==null) {
        		//System.out.println("2 "+kdpst);
        		//cek tabel cuti rule, sipa approveenya = tkn_objid
        		stmt = con.prepareStatement("select TKN_VERIFICATOR,TKN_VERIFICATOR_ID from CUTI_RULES where THSMS=? and KDPST=?");
        		stmt.setString(++i, target_thsms);
        		stmt.setString(++i, kdpst);
        		rs = stmt.executeQuery();
        		if(!rs.next()) {
        			
        			//System.out.println("3");
        			msg = new String("Aturan Cuti untuk tahun/sms "+target_thsms+" belum diisi");
        			//System.out.println("msg="+msg);
        		}
        		else {
        			//System.out.println("4");
        			String tkn_target_obj_nickmane = rs.getString("TKN_VERIFICATOR");
        			String tkn_target_objid = rs.getString("TKN_VERIFICATOR_ID");
        			
        			//post pengajuna
        			//sebelum insert cek dulu apa ada record pengajuan yg masih aktif
        			i=0;
        			stmt = con.prepareStatement("select ID from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_NPM=? and LOCKED=? and REJECTED=? and BATAL=?");
        			stmt.setString(++i, target_thsms);
        			stmt.setString(++i, "CUTI");
        			stmt.setString(++i, npmhs);
        			stmt.setBoolean(++i, false);
        			stmt.setNull(++i, java.sql.Types.VARCHAR);
        			stmt.setBoolean(++i, false);
        			rs = stmt.executeQuery();
        			if(!rs.next()) {
        				i=0;
                		stmt = con.prepareStatement("INSERT INTO TOPIK_PENGAJUAN(TARGET_THSMS_PENGAJUAN,TIPE_PENGAJUAN,ISI_TOPIK_PENGAJUAN,TOKEN_TARGET_OBJ_NICKNAME,TOKEN_TARGET_OBJID,CREATOR_OBJ_ID,CREATOR_NPM,CREATOR_NMM,SHOW_AT_TARGET,SHOW_AT_CREATOR,CREATOR_KDPST)values(?,?,?,?,?,?,?,?,?,?,?)");
                		stmt.setString(++i, target_thsms);
                		stmt.setString(++i, "CUTI");
                		stmt.setString(++i, alasan);
                		stmt.setString(++i, tkn_target_obj_nickmane);
                		stmt.setString(++i, tkn_target_objid);
                		stmt.setLong(++i,Checker.getObjectId(npmhs));
                		stmt.setString(++i, npmhs);
                		stmt.setString(++i, nmmhs);
                		stmt.setString(++i, tkn_target_objid);
                		stmt.setBoolean(++i, false);//tidak ditampilkan notifikasi, hanya update saja yg merubah statusnya
                		stmt.setString(++i, kdpst);
                		stmt.executeUpdate();
        			}
        			
        			//update overview table
            		long npm_id = Checker.getObjectId(npmhs);
            		//cek apa sudah ada record utk objid terkait
            		stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
            		stmt.setLong(1, npm_id);
            		stmt.setString(2, target_thsms);
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			int tot_cuti_req = rs.getInt("TOT_CUTI_REQ");
            			int tot_cuti_req_unapproved = rs.getInt("TOT_CUTI_REQ_UNAPPROVED"); 
            			String list_npm_unapproved = ""+rs.getString("LIST_NPM_CUTI_UNAPPROVED");
            			//karena add req
            			if(list_npm_unapproved==null || Checker.isStringNullOrEmpty(list_npm_unapproved) || (list_npm_unapproved!=null && !list_npm_unapproved.contains(npmhs))) {
            				if(list_npm_unapproved==null || Checker.isStringNullOrEmpty(list_npm_unapproved)) {
            					list_npm_unapproved = new String(npmhs);	
            				}
            				else {
            					list_npm_unapproved = list_npm_unapproved+","+npmhs;
                				if(list_npm_unapproved.startsWith(",")) {
                					list_npm_unapproved = list_npm_unapproved.substring(1, list_npm_unapproved.length());
                				}
            				}
            				tot_cuti_req++;
        					tot_cuti_req_unapproved++;
        					stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_CUTI_REQ=?,TOT_CUTI_REQ_UNAPPROVED=?,LIST_NPM_CUTI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
            				stmt.setInt(1, tot_cuti_req);
            				stmt.setInt(2, tot_cuti_req_unapproved);
            				stmt.setString(3, list_npm_unapproved);
            				stmt.setLong(4, npm_id);
            				stmt.setString(5, target_thsms);
            				stmt.executeUpdate();
            			}
            			else {
            				//if(list_npm_unapproved.contains(npmhs)) {
            					//sudah ada, ignore ngga perlu di update
            				/*
            				}
            				else {
            					tot_cuti_req++;
            					tot_cuti_req_unapproved++;
            					list_npm_unapproved = list_npm_unapproved+","+npmhs;
                				if(list_npm_unapproved.startsWith(",")) {
                					list_npm_unapproved = list_npm_unapproved.substring(1, list_npm_unapproved.length());
                				}
                				stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_CUTI_REQ=?,TOT_CUTI_REQ_UNAPPROVED=?,LIST_NPM_CUTI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
                				stmt.setInt(1, tot_cuti_req);
                				stmt.setInt(2, tot_cuti_req_unapproved);
                				stmt.setString(3, list_npm_unapproved);
                				stmt.setLong(4, npm_id);
                				stmt.setString(5, target_thsms);
                				stmt.executeUpdate();
            				}
            				*/
            			}
            			
            			//tot_cuti_req++; //ngga jadi ditambah krn = yang sudah di approved
            			//tot_cuti_req_unapproved++;
            			
            		}
            		else {
            			//insert
            			stmt = con.prepareStatement("insert into OVERVIEW_SEBARAN_TRLSM (ID_OBJ,THSMS,KDPST,TOT_CUTI_REQ,TOT_CUTI_REQ_UNAPPROVED,LIST_NPM_CUTI_UNAPPROVED)values(?,?,?,?,?,?)");
            			stmt.setLong(1, npm_id);
            			stmt.setString(2, target_thsms);
            			stmt.setString(3, kdpst);
            			stmt.setInt(4, 1);
            			stmt.setInt(5, 1);
            			stmt.setString(6, npmhs);
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
    	return msg;
    }	
    
    public String cancelPengajuan(String target_npmhs, String target_thsms, String tipe_pengajuan, long usr_obj_id) {
    	int i=0; 
    	String msg = null;
    	try {
           	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//get id topik
        	stmt = con.prepareStatement("select ID,TOKEN_TARGET_OBJID from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_NPM=? and LOCKED=?");
    		stmt.setString(1, target_thsms);
    		stmt.setString(2, tipe_pengajuan);
    		stmt.setString(3, target_npmhs);
    		stmt.setBoolean(4, false);
    		rs = stmt.executeQuery();
    		rs.next();
    		long id = rs.getLong(1);
    		String tkn_target_objid = rs.getString(2);
    		//insert  subtopik cancel
    		stmt=con.prepareStatement("insert into SUBTOPIK_PENGAJUAN (ID_TOPIK,ISI_SUBTOPIK,CREATOR_OBJ_ID,CREATOR_NPM,VERDICT)values(?,?,?,?,?)");
    		stmt.setLong(1,id);
    		stmt.setString(2, "BATAL PERMOHONAN");
    		stmt.setLong(3,usr_obj_id);
    		stmt.setString(4, this.operatorNpm);
    		stmt.setString(5, "BATAL");
    		stmt.executeUpdate();
    		
    		//update topik 
    		stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_TARGET=?,SHOW_AT_CREATOR=?,LOCKED=?,BATAL=? where ID=?");
    		stmt.setString(1, tkn_target_objid);
    		stmt.setBoolean(2, false);
    		stmt.setBoolean(3, true);
    		stmt.setBoolean(4, true);
    		stmt.setLong(5,id);
    		stmt.executeUpdate();
    		
    		//update table overview
    		//TOT CUTI REQ = selain yg ditolak
    		//jadi kalo di tolak/batal TOT CUTI REQ - 1 
    		//TOT_CUTI_REQ_UNAPPROVED -1 krn kalo ditolak langsung ke lock = artinya sudah final verdict
    		//if(creator_obj_id_topik==null || Checker.isStringNullOrEmpty(creator_obj_id_topik) || creator_obj_id_topik.equalsIgnoreCase("0")) {
    		String	creator_obj_id_topik = ""+Checker.getObjectId(target_npmhs);
    		//}
    		stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
    		stmt.setLong(1, Long.parseLong(creator_obj_id_topik));
    		stmt.setString(2, target_thsms);
    		rs = stmt.executeQuery();
    		rs.next();
    		int tot_req = rs.getInt("TOT_CUTI_REQ");
    		int tot_req_unapproved = rs.getInt("TOT_CUTI_REQ_UNAPPROVED");
    		String list_npm_unappproved = rs.getString("LIST_NPM_CUTI_UNAPPROVED");
    		list_npm_unappproved = list_npm_unappproved.replace(target_npmhs, "");
    		list_npm_unappproved = list_npm_unappproved.replace(",,", "");
    		if(list_npm_unappproved.startsWith(",")) {
    			list_npm_unappproved = list_npm_unappproved.substring(1, list_npm_unappproved.length());
    		}
    		if(list_npm_unappproved.endsWith(",")) {
    			list_npm_unappproved = list_npm_unappproved.substring(0, list_npm_unappproved.length()-1);
    		}
    		
    		stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_CUTI_REQ=?,TOT_CUTI_REQ_UNAPPROVED=?,LIST_NPM_CUTI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
    		--tot_req;
    		if(tot_req<0) {
    			tot_req=0;
    		}
    		--tot_req_unapproved;
    		if(tot_req_unapproved<0) {
    			tot_req_unapproved=0;
    		}
    		stmt.setInt(1, tot_req);
    		stmt.setInt(2, tot_req_unapproved);
    		if(Checker.isStringNullOrEmpty(list_npm_unappproved)) {
    			stmt.setNull(3, java.sql.Types.VARCHAR);
    		}
    		else {
    			stmt.setString(3,list_npm_unappproved);
    		}
    		stmt.setLong(4, Long.parseLong(creator_obj_id_topik));
    		stmt.setString(5,target_thsms);
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
    	return msg;
    }		
    
    public String approvalPengajuanCuti(String id_topik, int approvee_objid, String approvee_npm, String approval_verdict, String alasan) {
    	int i=0; 
    	String msg = null;
    	try {
           	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where ID=?");
    		stmt.setLong(1, Long.parseLong(id_topik));
    		rs = stmt.executeQuery();
    		rs.next();
    		
			String thsms_pengajuan_topik=""+rs.getString("TARGET_THSMS_PENGAJUAN");
			String tipe_pengajuan_topik=""+rs.getString("TIPE_PENGAJUAN");
			String isi_topik_pengajuan_topik=""+rs.getString("ISI_TOPIK_PENGAJUAN");
			String tkn_target_objnickname_topik=""+rs.getString("TOKEN_TARGET_OBJ_NICKNAME");
			String tkn_target_objid_topik=""+rs.getString("TOKEN_TARGET_OBJID");
			String target_npm_topik=""+rs.getString("TARGET_NPM");
			String creator_obj_id_topik=""+rs.getLong("CREATOR_OBJ_ID");
			String creator_npm_topik=""+rs.getString("CREATOR_NPM");
			String creator_nmm_topik=""+rs.getString("CREATOR_NMM");
			String shwow_at_target_topik=""+rs.getString("SHOW_AT_TARGET");
			String show_at_creator_topik=""+rs.getBoolean("SHOW_AT_CREATOR");
			String updtm_topik=""+rs.getTimestamp("UPDTM");
			String approved_topik=""+rs.getString("APPROVED");
			String locked_topik=""+rs.getBoolean("LOCKED");
			String rejected_topik=""+rs.getString("REJECTED");
			
        	if(approval_verdict.contains("TOLAK")) { 
        		stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_TARGET=?,SHOW_AT_CREATOR=?,LOCKED=?,REJECTED=? where ID=?");
        		int j=1;
        		//kalo ditolak maka hide at target = SHOW_AT_TARGET set null, karena kalo udah ada yg cancel yg belum ngasih verdic ngga perlu liat
        		//hidden at target
        		stmt.setNull(j++,java.sql.Types.VARCHAR);
        		/*
        		if(shwow_at_target_topik==null || Checker.isStringNullOrEmpty(shwow_at_target_topik)) {
        			stmt.setNull(j++,java.sql.Types.VARCHAR);
        		}
        		else {
        			shwow_at_target_topik = shwow_at_target_topik.replace("["+approvee_objid+"]","");
        			if(Checker.isStringNullOrEmpty(shwow_at_target_topik)) {
        				stmt.setNull(j++,java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(j++,shwow_at_target_topik);	
        			}
        		}
        		*/
        		stmt.setBoolean(j++, true);
        		stmt.setBoolean(j++, true);//locked
        		//if contain aprooved objid disini
        		if(rejected_topik==null || Checker.isStringNullOrEmpty(rejected_topik)) {
        			rejected_topik = new String("["+approvee_objid+"]");
        		}
        		else if(!rejected_topik.contains("["+approvee_objid+"]")) {
        			rejected_topik = rejected_topik+"["+approvee_objid+"]";
        			
        		}
        		stmt.setString(j++, rejected_topik);
        		
        		
        		stmt.setLong(j++, Long.parseLong(id_topik));
        		stmt.executeUpdate();//update topik
        		//2. insert subtopik
        		j=1;
        		stmt = con.prepareStatement("insert into SUBTOPIK_PENGAJUAN(ID_TOPIK,ISI_SUBTOPIK,CREATOR_OBJ_ID,CREATOR_NPM,VERDICT)values(?,?,?,?,?)");
        		stmt.setLong(j++, Long.parseLong(id_topik));
        		stmt.setString(j++, alasan);
        		stmt.setInt(j++, approvee_objid);
        		stmt.setString(j++, approvee_npm);
        		stmt.setString(j++, approval_verdict);
        		stmt.executeUpdate();
        		//update table overview
        		//TOT CUTI REQ = selain yg ditolak
        		//jadi kalo di tolak/batal TOT CUTI REQ - 1 
        		//TOT_CUTI_REQ_UNAPPROVED -1 krn kalo ditolak langsung ke lock = artinya sudah final verdict
        		if(creator_obj_id_topik==null || Checker.isStringNullOrEmpty(creator_obj_id_topik) || creator_obj_id_topik.equalsIgnoreCase("0")) {
        			creator_obj_id_topik = ""+Checker.getObjectId(creator_npm_topik);
        		}
        		stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
        		stmt.setLong(1, Long.parseLong(creator_obj_id_topik));
        		stmt.setString(2, thsms_pengajuan_topik);
        		rs = stmt.executeQuery();
        		rs.next();
        		int tot_req = rs.getInt("TOT_CUTI_REQ");
        		int tot_req_unapproved = rs.getInt("TOT_CUTI_REQ_UNAPPROVED");
        		String list_npm_unappproved = rs.getString("LIST_NPM_CUTI_UNAPPROVED");
        		list_npm_unappproved = list_npm_unappproved.replace(creator_npm_topik, "");
        		list_npm_unappproved = list_npm_unappproved.replace(",,", "");
        		if(list_npm_unappproved.startsWith(",")) {
        			list_npm_unappproved = list_npm_unappproved.substring(1, list_npm_unappproved.length());
        		}
        		if(list_npm_unappproved.endsWith(",")) {
        			list_npm_unappproved = list_npm_unappproved.substring(0, list_npm_unappproved.length()-1);
        		}
        		
        		stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_CUTI_REQ=?,TOT_CUTI_REQ_UNAPPROVED=?,LIST_NPM_CUTI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
        		--tot_req;
        		if(tot_req<0) {
        			tot_req=0;
        		}
        		--tot_req_unapproved;
        		if(tot_req_unapproved<0) {
        			tot_req_unapproved=0;
        		}
        		stmt.setInt(1, tot_req);
        		stmt.setInt(2, tot_req_unapproved);
        		if(Checker.isStringNullOrEmpty(list_npm_unappproved)) {
        			stmt.setNull(3, java.sql.Types.VARCHAR);
        		}
        		else {
        			stmt.setString(3,list_npm_unappproved);
        		}
        		stmt.setLong(4, Long.parseLong(creator_obj_id_topik));
        		stmt.setString(5,thsms_pengajuan_topik);
    			stmt.executeUpdate();
        	}
        	else if(approval_verdict.contains("TERIMA")) { 
        		//terima
        		//tkn_target_objid_topik = id approvees
        		//set approved topik value
        		
        		
        		
        		//if contain aprooved objid disini
        		if(approved_topik==null || Checker.isStringNullOrEmpty(approved_topik)) {
        			approved_topik = new String("["+approvee_objid+"]");
        		}
        		else if(!approved_topik.contains("["+approvee_objid+"]")) {
        			approved_topik = approved_topik+"["+approvee_objid+"]";
        		}
        		
        		//cek sudah approved semua ato belum
        		boolean all_approved = AddHocFunction.isAllApproved(tkn_target_objid_topik, approved_topik);
        		/*
        		String tkn_requird_approvee_id = new String(tkn_target_objid_topik);
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
        				if(!approved_topik.contains("["+required_id_approvee+"]")) {
        					all_approved = false;
        				}
        			}
        		}
        		else {
        			all_approved = false;
        		}
        		*/
        		//System.out.println("all_approved="+all_approved);
        		stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_TARGET=?,SHOW_AT_CREATOR=?,LOCKED=?,APPROVED=? where ID=?");
        		int j=1;
        		//hidden at target
        		if(shwow_at_target_topik==null || Checker.isStringNullOrEmpty(shwow_at_target_topik)) {
        			stmt.setNull(j++,java.sql.Types.VARCHAR);
        		}
        		else {
        			shwow_at_target_topik = shwow_at_target_topik.replace("["+approvee_objid+"]","");
        			if(Checker.isStringNullOrEmpty(shwow_at_target_topik)) {
        				stmt.setNull(j++,java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(j++,shwow_at_target_topik);	
        			}
        			
        		}
        		stmt.setBoolean(j++, true);
        		if(all_approved) {
        			stmt.setBoolean(j++, true);//locked - belum approved semua
        		}
        		else {
        			stmt.setBoolean(j++, false);//locked - belum approved semua	
        		}
        		
        		
        		stmt.setString(j++, approved_topik);
        		stmt.setLong(j++, Long.parseLong(id_topik));
        		stmt.executeUpdate();//update topik
        		//2. insert subtopik
        		j=1;
        		stmt = con.prepareStatement("insert into SUBTOPIK_PENGAJUAN(ID_TOPIK,ISI_SUBTOPIK,CREATOR_OBJ_ID,CREATOR_NPM,VERDICT)values(?,?,?,?,?)");
        		stmt.setLong(j++, Long.parseLong(id_topik));
        		stmt.setString(j++, approval_verdict);
        		stmt.setInt(j++, approvee_objid);
        		stmt.setString(j++, approvee_npm);
        		stmt.setString(j++, approval_verdict);
        		stmt.executeUpdate();
        		
        		if(all_approved) {
        			//update table overview
            		//TOT CUTI REQ = selain yg ditolak
            		//jadi kalo di tolak/batal TOT CUTI REQ - 1 
            		//TOT_CUTI_REQ_UNAPPROVED -1 krn kalo ditolak langsung ke lock = artinya sudah final verdict
            		if(creator_obj_id_topik==null || Checker.isStringNullOrEmpty(creator_obj_id_topik) || creator_obj_id_topik.equalsIgnoreCase("0")) {
            			creator_obj_id_topik = ""+Checker.getObjectId(creator_npm_topik);
            		}
            		stmt = con.prepareStatement("select * from OVERVIEW_SEBARAN_TRLSM where ID_OBJ=? and THSMS=?");
            		stmt.setLong(1, Long.parseLong(creator_obj_id_topik));
            		stmt.setString(2, thsms_pengajuan_topik);
            		rs = stmt.executeQuery();
            		rs.next();
            		int tot_req = rs.getInt("TOT_CUTI_REQ");
            		int tot_req_unapproved = rs.getInt("TOT_CUTI_REQ_UNAPPROVED");
            		String list_npm_unappproved = rs.getString("LIST_NPM_CUTI_UNAPPROVED");
            		list_npm_unappproved = list_npm_unappproved.replace(creator_npm_topik, "");
            		list_npm_unappproved = list_npm_unappproved.replace(",,", "");
            		if(list_npm_unappproved.startsWith(",")) {
            			list_npm_unappproved = list_npm_unappproved.substring(1, list_npm_unappproved.length());
            		}
            		if(list_npm_unappproved.endsWith(",")) {
            			list_npm_unappproved = list_npm_unappproved.substring(0, list_npm_unappproved.length()-1);
            		}
            		--tot_req_unapproved;
            		if(tot_req_unapproved<0) {
            			tot_req_unapproved=0;
            		}
            		stmt = con.prepareStatement("update OVERVIEW_SEBARAN_TRLSM set TOT_CUTI_REQ=?,TOT_CUTI_REQ_UNAPPROVED=?,LIST_NPM_CUTI_UNAPPROVED=? where ID_OBJ=? and THSMS=?");
            		stmt.setInt(1, tot_req);
            		stmt.setInt(2, tot_req_unapproved);
            		if(Checker.isStringNullOrEmpty(list_npm_unappproved)) {
            			stmt.setNull(3, java.sql.Types.VARCHAR);
            		}
            		else {
            			stmt.setString(3,list_npm_unappproved);
            		}
            		stmt.setLong(4, Long.parseLong(creator_obj_id_topik));
            		stmt.setString(5,thsms_pengajuan_topik);
        			stmt.executeUpdate();
        		}
        		//else {
        			
        		//}
        	}
        	else if(approval_verdict.contains("RESET")) {
        		//remove verdict dari sub topik
        		stmt = con.prepareStatement("delete from SUBTOPIK_PENGAJUAN where ID_TOPIK=? and CREATOR_OBJ_ID=?");
        		stmt.setLong(1, Long.parseLong(id_topik));
        		stmt.setInt(2, approvee_objid);
        		stmt.executeUpdate();
        		//update topik
        		//1.show at creator
        		//2.show at target & show at creator
        		//3.remove verdicr dari approved & rejected
        		
    			
        		stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_TARGET=?,SHOW_AT_CREATOR=?,APPROVED=?,REJECTED=? where ID=?");
        		if(shwow_at_target_topik==null || Checker.isStringNullOrEmpty(shwow_at_target_topik)) {
        			shwow_at_target_topik = new String("["+approvee_objid+"]");
        		}
        		else if(!shwow_at_target_topik.contains("["+approvee_objid+"]")) {
        			shwow_at_target_topik = shwow_at_target_topik+"["+approvee_objid+"]";
        		}
        		stmt.setString(1, shwow_at_target_topik);
        		stmt.setBoolean(2, true);
        		if(approved_topik==null || Checker.isStringNullOrEmpty(approved_topik)) {
        			stmt.setNull(3, java.sql.Types.VARCHAR);
        		}
        		else {
        			approved_topik = approved_topik.replace("["+approvee_objid+"]", "");
        			if(Checker.isStringNullOrEmpty(approved_topik)) {
        				stmt.setNull(3, java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(3, approved_topik);
        			}
        			
        		}
        		if(rejected_topik==null || Checker.isStringNullOrEmpty(rejected_topik)) {
        			stmt.setNull(4, java.sql.Types.VARCHAR);
        		}
        		else {
        			rejected_topik = rejected_topik.replace("["+approvee_objid+"]", "");
        			if(Checker.isStringNullOrEmpty(rejected_topik)) {
        				stmt.setNull(4, java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(4, rejected_topik);
        			}
        			
        		}
        		stmt.setLong(5, Long.parseLong(id_topik));
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
    	return msg;
    }	
}
