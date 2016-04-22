package beans.dbase.notification;

import beans.dbase.SearchDb;
import beans.tools.Checker;
import beans.tools.ToJson;
import beans.tools.Tool;

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
import org.codehaus.jettison.json.JSONArray;

/**
 * Session Bean implementation class SearchDbMainNotification
 */
@Stateless
@LocalBean
public class SearchDbMainNotification extends SearchDb {
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
    public SearchDbMainNotification() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbMainNotification(String operatorNpm) {
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
    public SearchDbMainNotification(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public boolean getPengajuanUa(Vector vScopeUa, Vector vScopeUaA, int oprObjId) {
    	//Vector v = null;
    	/*
    	 *  vScopeUa = scope pengajuan = owner & monitoree
    	 *  vScopeUaA = scope approval
    	 */
    	boolean ada_pengajuan =false;
    	try {

    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
    		StringTokenizer st = null;
    		//bagian pengajuan dan monitoree
    		if(vScopeUa!=null && vScopeUa.size()>0) {
    			//System.out.println("vscope ua not null");
    			ListIterator li = vScopeUa.listIterator();	
    			String kdpst = "";
    			String sql_stmt = "";
    			kdpst = (String)li.next();
    			//System.out.println("kdpst ua ="+kdpst);
    			String usrObjNick = ""+this.tknOperatorNickname;//
    			if(kdpst.equalsIgnoreCase("own")) { 
    				/*
    				 * kalo own hanya untuk mhs , kalo monitoree pake scope
    				 */
    				//if(usrObjNick.contains("MHS")||usrObjNick.contains("mhs")) {
    					//bagian untul mahasiswa
    				//System.out.println("masuk owner");
    				sql_stmt = "select * from EXT_PENGAJUAN_UA where NPMHS=? and SHOW_OWNER=?"; 
					stmt = con.prepareStatement(sql_stmt);	
					stmt.setString(1, this.operatorNpm);
					stmt.setBoolean(2, true);
					rs = stmt.executeQuery();
					if(rs.next()) {
    					ada_pengajuan = true;
    				}
    				//}
    			}//end own
    			else { 
    				//bagian monitoree
    				kdpst = Tool.getTokenKe(kdpst, 1);
    				String sql_cmd = "IDOBJ="+kdpst+"";
    				
    				do {
    					if(li.hasNext()) {
    						kdpst = (String)li.next();
        					kdpst = Tool.getTokenKe(kdpst, 1);
        					sql_cmd = sql_cmd+" or IDOBJ="+kdpst+"";	
    					}
    				}
    				while(li.hasNext());
    				sql_stmt = "select * from EXT_PENGAJUAN_UA where SHOW_MONITOREE=? and ("+sql_cmd+") limit 1";
    				//System.out.println(sql_stmt);
    				stmt = con.prepareStatement(sql_stmt);
    				stmt.setBoolean(1, true);
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					ada_pengajuan = true;
    				}
    			}
    		}
    		else {
    			//System.out.println("vscope ua  null");
    		}
    		
    		if(!ada_pengajuan) {
    			//System.out.println("masuk sini dong");
    			//kalo masih blum ada pengajuan - cek bagian approvee
    			if(vScopeUaA!=null && vScopeUaA.size()>0) {
    				//System.out.println("vscope UAA not null");
        			ListIterator li = vScopeUaA.listIterator();	
        			String kdpst = "";
        			String sql_stmt = "";
        			kdpst = (String)li.next();
        			String usrObjNick = ""+this.tknOperatorNickname;
        			if(kdpst.equalsIgnoreCase("own") && false) {//berarti penguji ujian
        				/*
        				 * UTK SAAT INI BELUM DIGUNAKAN KAREN APPROVAL HANYA VIA BAA & BAUK
        				 */
        				
        				//cari npmmhs yg jatahnya
        				stmt = con.prepareStatement("select NPMHSMSMSH from CIVITAS where STMHSMSMHS='A' and (NOPRMMSMHS=? or NOKP1MSMHS=? or NOKP2MSMHS=? or NOKP3MSMHS=? or NOKP4MSMHS=?)");
    					stmt.setString(1, this.operatorNpm);
    					stmt.setString(2, this.operatorNpm);
    					stmt.setString(3, this.operatorNpm);
    					stmt.setString(4, this.operatorNpm);
    					rs = stmt.executeQuery();
    					Vector vNpm = new Vector();;
    					ListIterator liNpm = vNpm.listIterator();
    					while(rs.next()) {
    						String npm = rs.getString(1);
    						liNpm.add(npm);
    					}
    					
    					if(vNpm.size()>0) {
    						
        					stmt = con.prepareStatement("select * from EXT_PENGAJUAN_UA where NPMHS=? and SHOW_APPROVEE=?");	
    						liNpm = vNpm.listIterator();
    						while(liNpm.hasNext() && !ada_pengajuan) {
    							String npm = (String)liNpm.next();
    							stmt.setString(1, this.operatorNpm);
    	    					stmt.setBoolean(2, true);
    	    					rs = stmt.executeQuery();
    	    					if(rs.next()) {
    	    						ada_pengajuan = true;
    	    					}
    						}
    					}	
        			}
        			else {
        				/*
        				 * YG DITAMPILKAN ADALAH PENGAJUAN YG BELUM DEBERIKAN TINDAKAN OLEH 
        				 * PIHAK TERKAIT, BILA BISA ADA PENGGANTI BLUM DIPERHITUNGKAN, DIA
        				 * SEPERTI MONITOREE DAN BARU HILANG BILA SUDAH ADA JADWAL UJIAN
        				 */
        				kdpst = Tool.getTokenKe(kdpst, 1);
        				String sql_cmd = "IDOBJ="+kdpst+"";
        				do {
        					if(li.hasNext()) {
        						kdpst = (String)li.next();	
            					kdpst = Tool.getTokenKe(kdpst, 1);
            					sql_cmd = sql_cmd+" or IDOBJ="+kdpst+"";	
        					}
        				}
        				while(li.hasNext());
        				sql_stmt = "select * from EXT_PENGAJUAN_UA where (TKN_ID_APPROVEE is NULL or TKN_ID_APPROVEE NOT LIKE ?) and ("+sql_cmd+") limit 1";
        				//System.out.println(sql_stmt);
        				stmt = con.prepareStatement(sql_stmt);
        				stmt.setString(1, "%/"+oprObjId+"`%");
        				//System.out.println("%/"+oprObjId+"`%");
        				rs = stmt.executeQuery();
        				if(rs.next()) {
        					ada_pengajuan = true;
        				}
        				else {
        					//System.out.println("nope next");
        				}
        			}
        				
    			}
    			else {
    				//System.out.println("vscope UAA  null");
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
    	return ada_pengajuan;
    }
    //deprecated
    public boolean getNotificationPengajuanCuti() {
    	//Vector v = null;
    	/*
    	 *  vScopeUa = scope pengajuan = owner & monitoree
    	 *  vScopeUaA = scope approval
    	 */
    	long thisObjId = Checker.getObjectId(this.operatorNpm);
    	boolean ada_pengajuan =false;
    	try {

    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//cek sebagai approvee
    		stmt = con.prepareStatement("select ID from TOPIK_PENGAJUAN where TIPE_PENGAJUAN=? and TOKEN_TARGET_OBJID like ? and SHOW_AT_TARGET like ? ");
    		stmt.setString(1, "CUTI");
    		stmt.setString(2, "%["+thisObjId+"]%");
    		stmt.setString(3, "%["+thisObjId+"]%");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			ada_pengajuan = true;
    		}
    		if(!ada_pengajuan) {
    			//check sbg creator
    			stmt = con.prepareStatement("select ID from TOPIK_PENGAJUAN where TIPE_PENGAJUAN=? and CREATOR_NPM=? and SHOW_AT_CREATOR=? ");
    			stmt.setString(1, "CUTI");
        		stmt.setString(2, this.operatorNpm);
        		stmt.setBoolean(3, true);
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			ada_pengajuan = true;
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
    	return ada_pengajuan;
    }
    
    public boolean isTherePengajuanCuti(Vector v_scope_id) {
    	//Vector v = null;
    	/*
    	 *  vScopeUa = scope pengajuan = owner & monitoree
    	 *  vScopeUaA = scope approval
    	 */
    	
    	String thsms_now = Checker.getThsmsNow();
    	boolean ada_pengajuan =false;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		try {

        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			String addon_cmd = "";
    			ListIterator li = v_scope_id.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String kmp = st.nextToken();
    				boolean sy_mhs = false;
    				while(st.hasMoreTokens()) {
    					String id = st.nextToken();
    					if(id.equalsIgnoreCase("own")) {
    						//sbg mhs
    						sy_mhs = true;
    					}
    					else {
    						//sbg approval ato monitor
    						if(addon_cmd.contains("ID_OBJ")) {
        						//klao ada value dari kampus lainnya
        						addon_cmd = addon_cmd+" OR ID_OBJ="+id;
        					}
        					else {
        						//first record
        						addon_cmd = addon_cmd+"ID_OBJ="+id;	
        					}
    					}
    					
    					
    					//if(st.hasMoreTokens()) {
    					//	addon_cmd = addon_cmd+" OR ";
    					//}
    				}
    				if(!sy_mhs) {
    					//cek sebagai approvee
    	    			String cmd = "";
    	    			if(addon_cmd!=null && !Checker.isStringNullOrEmpty(addon_cmd)) {
    	    				cmd = "select ID_OBJ from OVERVIEW_SEBARAN_TRLSM where THSMS=? and  LIST_NPM_CUTI_UNAPPROVED is not null and ("+addon_cmd+") limit 1";
    	    				//System.out.println("cmd="+cmd);
    	            		stmt = con.prepareStatement(cmd);
    	            		stmt.setString(1,thsms_now);
    	            		rs = stmt.executeQuery();
    	            		if(rs.next()) {
    	            			ada_pengajuan = true;
    	            		}
    	    			}
    				}
    				else {
    					stmt = con.prepareStatement("select ID from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TIPE_PENGAJUAN=? and CREATOR_NPM=? and SHOW_AT_CREATOR=?");
    					stmt.setString(1, thsms_now);
    					stmt.setString(2, "CUTI");
    					stmt.setString(3, this.operatorNpm);
    					stmt.setBoolean(4, true);
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						ada_pengajuan=true;
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
    	return ada_pengajuan;
    }
    
    
    public boolean isThereProdiYgBelumMengajukanKelasPerkuliahan(Vector v_scope_id) {
    	//Vector v = null;
    	/*
    	 *  vScopeUa = scope pengajuan = owner & monitoree
    	 *  vScopeUaA = scope approval
    	 */
    	;
    	String thsms_buka_kls = Checker.getThsmsBukaKelas();
    	boolean ada =false;
    	ListIterator li = null;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		try {

        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			String addon_cmd = "";
    			
    			//cek sebagai approvee
    			Vector v_kdpst_kmp = new Vector();
    			stmt = con.prepareStatement("select KDPST,KODE_KAMPUS from CLASS_POOL where THSMS=? and CANCELED=?");
    			stmt.setString(1,thsms_buka_kls);
    			stmt.setBoolean(2, false);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				li = v_kdpst_kmp.listIterator();
    				do {
    					String kdpst = ""+rs.getString("KDPST");
    					String kmp = ""+rs.getString("KODE_KAMPUS");
    					li.add(kdpst+"`"+kmp);
    				}
    				while(rs.next());
    			}
    			if(v_kdpst_kmp!=null && v_kdpst_kmp.size()>0) {
    				v_kdpst_kmp = Tool.removeDuplicateFromVector(v_kdpst_kmp);
    				li = v_kdpst_kmp.listIterator();
    				String list_id = "";
    				stmt = con.prepareStatement("select ID_OBJ from OBJECT where KDPST=? and KODE_KAMPUS_DOMISILI=?");
    				while(li.hasNext()) {
    					String brs = (String)li.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String kdpst = st.nextToken();
    					String kmp = st.nextToken();
    					stmt.setString(1, kdpst);
    					stmt.setString(2, kmp);
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						list_id = list_id+"["+rs.getInt(1)+"]";
    					}
    				}
    				if(!Checker.isStringNullOrEmpty(list_id)) {
    					li = v_scope_id.listIterator();
    	    			while(li.hasNext()&&!ada) {
    	    				String brs = (String)li.next();
    	    				StringTokenizer st = new StringTokenizer(brs,"`");
    	    				String kmp = st.nextToken();
    	    				while(st.hasMoreTokens()) {
    	    					String id = st.nextToken();
    	    					if(!list_id.contains("["+id+"]")) {
    	    						ada = true;
    	    						//System.out.println("["+id+"]");
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
    		catch (Exception ex) {
        		ex.printStackTrace();
        	}
        	finally {
        		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
        		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
        		if (con!=null) try { con.close();} catch (Exception ignore){}
        	}   

    	}
    	return ada;
    }
    
   
    
    public Vector listProdiYgBelumMengajukanKelasPerkuliahan(Vector v_scope_id) {
    	//Vector v = null;
    	/*
    	 *  vScopeUa = scope pengajuan = owner & monitoree
    	 *  vScopeUaA = scope approval
    	 */
    	Vector v_list_yg_blm = null;;
    	String thsms_buka_kls = Checker.getThsmsBukaKelas();
    	boolean ada =false;
    	ListIterator li = null;
    	if(v_scope_id!=null && v_scope_id.size()>0) {
    		try {

        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			String addon_cmd = "";
    			
    			//cek sebagai approvee
    			Vector v_kdpst_kmp = new Vector();
    			stmt = con.prepareStatement("select KDPST,KODE_KAMPUS from CLASS_POOL where THSMS=? and CANCELED=?");
    			stmt.setString(1,thsms_buka_kls);
    			stmt.setBoolean(2, false);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				li = v_kdpst_kmp.listIterator();
    				do {
    					String kdpst = ""+rs.getString("KDPST");
    					String kmp = ""+rs.getString("KODE_KAMPUS");
    					li.add(kdpst+"`"+kmp);
    				}
    				while(rs.next());
    			}
    			if(v_kdpst_kmp!=null && v_kdpst_kmp.size()>0) {
    				v_kdpst_kmp = Tool.removeDuplicateFromVector(v_kdpst_kmp);
    				li = v_kdpst_kmp.listIterator();
    				String list_id = "";
    				stmt = con.prepareStatement("select ID_OBJ from OBJECT where KDPST=? and KODE_KAMPUS_DOMISILI=?");
    				while(li.hasNext()) {
    					String brs = (String)li.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String kdpst = st.nextToken();
    					String kmp = st.nextToken();
    					stmt.setString(1, kdpst);
    					stmt.setString(2, kmp);
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						list_id = list_id+"["+rs.getInt(1)+"]";
    					}
    				}
    				if(!Checker.isStringNullOrEmpty(list_id)) {
    					v_list_yg_blm = new Vector();
    					ListIterator lit = v_list_yg_blm.listIterator();
    					stmt = con.prepareStatement("select * from OBJECT inner join MSPST on KDPST=KDPSTMSPST where ID_OBJ=? order by KODE_KAMPUS_DOMISILI,KDPST");
    					li = v_scope_id.listIterator();
    	    			while(li.hasNext()) {
    	    				String brs = (String)li.next();
    	    				StringTokenizer st = new StringTokenizer(brs,"`");
    	    				String kmp = st.nextToken();
    	    				while(st.hasMoreTokens()) {
    	    					String id = st.nextToken();
    	    					if(!list_id.contains("["+id+"]")) {
    	    						stmt.setInt(1, Integer.parseInt(id));
    	    						rs = stmt.executeQuery();
    	    						rs.next();
    	    						String kmpus = ""+rs.getString("KODE_KAMPUS_DOMISILI");
    	    						String kdpst = ""+rs.getString("KDPSTMSPST");
    	    						String nmpst = ""+rs.getString("NMPSTMSPST");
    	    						String kdjen = ""+rs.getString("KDJENMSPST");
    	    						
    	    						lit.add(kmpus+"`"+kdpst+"`"+nmpst+"`"+kdjen+"`"+id);
    	    						//System.out.println("["+id+"]");
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
    		catch (Exception ex) {
        		ex.printStackTrace();
        	}
        	finally {
        		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
        		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
        		if (con!=null) try { con.close();} catch (Exception ignore){}
        	}   

    	}
    	return v_list_yg_blm;
    }
    
    
    
    public boolean adaPengajuanKrsApprovalUntukSaya(String my_npm) {
    	//Vector v = null;
    	/*
    	 *  vScopeUa = scope pengajuan = owner & monitoree
    	 *  vScopeUaA = scope approval
    	 */
    	
    	String thsms_krs = Checker.getThsmsKrs();
    	boolean ada =false;
    	//ListIterator li = null;
    	try {

    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select ID from KRS_NOTIFICATION where KATEGORI='KRS APPROVAL' and THSMS_TARGET=? and NPM_RECEIVER=? and APPROVED=? and DECLINED=? limit 1");
			stmt.setString(1, thsms_krs);
			stmt.setString(2, my_npm);
			stmt.setBoolean(3, false);
			stmt.setBoolean(4, false);
			rs = stmt.executeQuery();
			if(rs.next()) {
				//System.out.println("id krs = "+rs.getInt(1));
				ada = true;
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

    	
    	return ada;
    }
    
    public boolean adaRequestPymntApproval(Vector vScope_id) {
    	/*
    	 * ambil  list reuquest pembayaran dengan bukti setoran dari pymnt transit table
    	 */
    	boolean ada = false;
    	if(vScope_id!=null && vScope_id.size()>0) {
    		ListIterator li = vScope_id.listIterator();
        	String list_id = null;
        	try {
        		
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			st.nextToken();
        			while(st.hasMoreTokens()) {
        				if(list_id==null) {
        					list_id = new String("ID_OBJ="+st.nextToken());
        				}
        				else {
        					list_id = list_id+" OR ID_OBJ="+st.nextToken();	
        				}
        			}
        		}
        		if(list_id!=null && !Checker.isStringNullOrEmpty(list_id)) {
        			String sql_cmd = "select KUIIDPYMNT from PYMNT_TRANSIT where "+list_id+" limit 1";
        			//System.out.println("sql_cmd="+sql_cmd);
        			Context initContext  = new InitialContext();
            		Context envContext  = (Context)initContext.lookup("java:/comp/env");
            		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            		con = ds.getConnection();
        			stmt = con.prepareStatement(sql_cmd);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				ada = true;
        			}
        		}
        		else {
        			//ignore = ngga ada akses
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
    	}
    		
    	//System.out.println(v.size());
    	return ada;
    }
    
    public boolean adaRequestDaftarUlang(Vector vScope_id) {
    	/*
    	 * ambil  list reuquest pembayaran dengan bukti setoran dari pymnt transit table
    	 */
    	String thsms_reg = Checker.getThsmsHeregistrasi();
    	boolean ada = false;
    	if(vScope_id!=null && vScope_id.size()>0) {
    		ListIterator li = vScope_id.listIterator();
        	String list_id = null;
        	try {
        		
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			st.nextToken();
        			while(st.hasMoreTokens()) {
        				if(list_id==null) {
        					list_id = new String("ID_OBJ="+st.nextToken());
        				}
        				else {
        					list_id = list_id+" OR ID_OBJ="+st.nextToken();	
        				}
        			}
        		}
        		if(list_id!=null && !Checker.isStringNullOrEmpty(list_id)) {
        			//String sql_cmd = "select ID_OBJ from DAFTAR_ULANG_NOTIFICATION where THSMS=? and ("+list_id+") and LIST_NPM_INPROGRESS is not null limit 1";
        			String sql_cmd = "select ID_OBJ from DAFTAR_ULANG where THSMS=? and ("+list_id+") and ALL_APPROVED=? limit 1";
        			//System.out.println("sql_cmd="+sql_cmd);
        			Context initContext  = new InitialContext();
            		Context envContext  = (Context)initContext.lookup("java:/comp/env");
            		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            		con = ds.getConnection();
        			stmt = con.prepareStatement(sql_cmd);
        			stmt.setString(1, thsms_reg);
        			stmt.setBoolean(2,false);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				ada = true;
        			}
        		}
        		else {
        			//ignore = ngga ada akses
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
    	}
    		
    	//System.out.println(v.size());
    	return ada;
    }
    
}
