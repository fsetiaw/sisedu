package beans.dbase.pengajuan.ua;

import beans.dbase.SearchDb;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.ToJson;
import beans.tools.Tool;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.owasp.esapi.ESAPI;
import org.apache.tomcat.jdbc.pool.DataSource;

import beans.setting.Constants;
/**
 * Servlet implementation class SearchDbUa
 */
@WebServlet("/SearchDbUa")
public class SearchDbUa extends SearchDb implements Servlet {
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
    public SearchDbUa() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbUa(String operatorNpm) {
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
    public SearchDbUa(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#getServletConfig()
	 */
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see Servlet#getServletInfo()
	 */
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null; 
	}

	/**
	 * @see Servlet#service(ServletRequest request, ServletResponse response)
	 */
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	public Vector addInfoNmmhs(Vector vPengajuan) {
		try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select NMMHSMSMHS from CIVITAS where NPMHSMSMHS=?");
			ListIterator lip = vPengajuan.listIterator();
			while(lip.hasNext()) {
				String brs = (String)lip.next();
				////System.out.println("baris = "+brs);
				String npmhs = Tool.getTokenKe(brs, 5, "`");
				////System.out.println("baris npmhs= "+npmhs);
				//String np
				stmt.setString(1, npmhs);
				rs = stmt.executeQuery();
				rs.next();
				String nmmhs = ""+rs.getString(1);
				lip.set(brs+"`"+nmmhs);
			}	
		}
    	catch (NamingException e) {
    		e.printStackTrace();
    	}
    	catch (SQLException ex) {
    		ex.printStackTrace();
    	}
		return vPengajuan;
	}
		
    public Vector getYourRoleAtasPengajuan(Vector vPengajuan) {
    	//vPengajuan format =
    	//id+"`"+thsms+"`"+kdpst+"`"+npmhs+"`"+status_akhir+"`"+skedul_date+"`"+realisasi_date+"`"+kdkmk+"`"+file_name+"`"+updtm+"`"+skedul_time+"`"+judul+"`"+show_owner+"`"+show_approvee+"`"+show_monitoree+"`"+idobj);
    	Vector vRules = new Vector();
    	String idUsr = ""+Checker.getObjectId(this.operatorNpm);
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//get PENGAJUAN_UA_RULES
			stmt = con.prepareStatement("select * from PENGAJUAN_UA_RULES");
			rs = stmt.executeQuery();
			ListIterator lir = vRules.listIterator();
			while(rs.next()) {
				String idobj = ""+rs.getInt("IDOBJ");
				String kdpst = ""+rs.getString("KDPST");
				String tkn_app_id = ""+rs.getString("TKN_APPROVEE_ID");
				lir.add(idobj+" "+kdpst+" "+tkn_app_id);
			}
			ListIterator lip = vPengajuan.listIterator();
			while(lip.hasNext()) {
				String brs = (String)lip.next();
				StringTokenizer st1 = new StringTokenizer(brs,"`");
				String id = st1.nextToken();
				String thsms = st1.nextToken();
				String kdpst = st1.nextToken();
				String npmhs = st1.nextToken();
				String status_akhir = st1.nextToken();
				String skedul_date = st1.nextToken();
				String realisasi_date = st1.nextToken();
				String kdkmk = st1.nextToken();
				String file_name = st1.nextToken();
				String updtm = st1.nextToken();
				String skedul_time = st1.nextToken();
				String judul = st1.nextToken();
				String show_owner = st1.nextToken();
				//String show_approvee = st1.nextToken();
				String show_monitoree = st1.nextToken();
				String idobj = st1.nextToken();
				String tkn_id_approvee = st1.nextToken();
				String tkn_show_approvee = st1.nextToken();
				boolean match = false;
				lir = vRules.listIterator();
				while(lir.hasNext()&&!match) {
					String rules = (String)lir.next();
					////System.out.println("rules=="+rules);
					st1 = new StringTokenizer(rules);
					String idobj1 = st1.nextToken();
					String kdpst1 = st1.nextToken();
					String tkn_app_id1 = st1.nextToken();
					if(idobj.equalsIgnoreCase(idobj1)) {
						match = true;
						if(tkn_app_id1.contains("/"+idUsr+"/")) {
							//berarti approvee
							lip.set("approve`"+brs);
						}
						else {
							lip.set("monitoree`"+brs);
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
    	//catch (Exception ex) {
    	//	ex.printStackTrace();
    	//} 
    	finally {
    		if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		if (con!=null) try { con.close();} catch (Exception ignore){}
    	}
    	return vPengajuan;
    }

    public Vector getRiwayatPengajuanUa(String id) {
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	//boolean ada_pengajuan =false;
    	try {

    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection(); 
			stmt = con.prepareStatement("select * from RIWAYAT_PENGAJUAN_UA where ID=? order by UPDTM");
			//stmt = con.prepareStatement("select * from RIWAYAT_PENGAJUAN_UA inner join PENGAJUAN_UA on RIWAYAT_PENGAJUAN_UA.ID=PENGAJUAN_UA.ID where RIWAYAT_PENGAJUAN_UA.ID=? order by RIWAYAT_PENGAJUAN_UA.UPDTM");
			stmt.setLong(1, Long.parseLong(id));
			rs = stmt.executeQuery();
			while(rs.next()) {
				String id_ri = ""+rs.getLong("ID_RI");
				String npm_approvee = ""+rs.getString("NPM_APPROVEE");
				String status = ""+rs.getString("STATUS");
				String updtm = ""+rs.getTimestamp("RIWAYAT_PENGAJUAN_UA.UPDTM");
				String komen = ""+rs.getString("KOMEN");
				String approvee_id = ""+rs.getLong("NPM_APPROVEE_ID");
				String approvee_nickname = ""+rs.getString("OBJ_NICKNAME_APPROVE");
				//String riwayat_penolakan = ""+rs.getString("RIWAYAT_PENOLAKAN");
				//lif.add(id_ri+"`"+id+"`"+npm_approvee+"`"+status+"`"+updtm+"`"+komen+"`"+approvee_id+"`"+approvee_nickname+"`"+riwayat_penolakan);
				lif.add(id_ri+"`"+id+"`"+npm_approvee+"`"+status+"`"+updtm+"`"+komen+"`"+approvee_id+"`"+approvee_nickname);
				
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
    
    public String getRiwayatPenolakan(String id) {
    	String riwayat_penolakan = null;

    	//boolean ada_pengajuan =false;
    	try {

    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection(); 
			stmt = con.prepareStatement("select RIWAYAT_PENOLAKAN from EXT_PENGAJUAN_UA where ID=?");
			stmt.setLong(1, Long.parseLong(id));
			rs = stmt.executeQuery();
			if(rs.next()) {
				riwayat_penolakan = new String(""+rs.getString(1));
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
    	return riwayat_penolakan;
    }
    
    /*
     * !!!!!!!!!!!!!! PERUBAHAN DISINI BISA BERIMBAS KE getPengajuanUa(Vector vScopeUa, Vector vScopeUaA, int oprObjId)  !!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    public Vector getPengajuanUa(long id) {
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	try {

    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			
			
			stmt = con.prepareStatement("select * from EXT_PENGAJUAN_UA where ID=?");	
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			rs.next();
			//String id = ""+rs.getLong("ID");
			String thsms = ""+rs.getString("THSMS");
			String kdpst = ""+rs.getString("KDPST");
			String npmhs = ""+rs.getString("NPMHS");
			String status_akhir = ""+rs.getString("STATUS_AKHIR");
			String skedul_date = ""+rs.getDate("SCHEDULE_DATE");
			String realisasi_date = ""+rs.getDate("REALISASI_DATE");
			String kdkmk = ""+rs.getString("KDKMK");
			String file_name = ""+rs.getString("FILE_NAME");
			String updtm = ""+rs.getTimestamp("UPDTM");
			String skedul_time = ""+rs.getTime("SCHEDULE_TIME");
			String judul = ""+rs.getString("JUDUL");
			String show_owner = ""+rs.getString("SHOW_OWNER");
			//String show_approvee = ""+rs.getString("SHOW_APPROVEE");
			String show_monitoree = ""+rs.getString("SHOW_MONITOREE");
			String idobj = ""+rs.getInt("IDOBJ");
			String tkn_id_approvee = ""+rs.getString("TKN_ID_APPROVEE");//tindakan
			String tkn_show_approvee = ""+rs.getString("TKN_SHOW_APPROVEE");
			tkn_id_approvee = tkn_id_approvee.replace("`", "-");//convert dulu kalo ngga masalah
			tkn_show_approvee = tkn_show_approvee.replace("`", "-");
			////System.out.println("monitoree=="+id+"`"+thsms+"`"+kdpst+"`"+npmhs+"`"+status_akhir+"`"+skedul_date+"`"+realisasi_date+"`"+kdkmk+"`"+file_name+"`"+updtm+"`"+skedul_time+"`"+judul+"`"+show_owner+"`"+show_approvee+"`"+show_monitoree+"`"+idobj);
			lif.add(id+"`"+thsms+"`"+kdpst+"`"+npmhs+"`"+status_akhir+"`"+skedul_date+"`"+realisasi_date+"`"+kdkmk+"`"+file_name+"`"+updtm+"`"+skedul_time+"`"+judul+"`"+show_owner+"`"+show_monitoree+"`"+idobj+"`"+tkn_id_approvee+"`"+tkn_show_approvee);
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
     * !!!!!!!!!!!!!! PERUBAHAN DISINI BISA BERIMBAS KE getPengajuanUa(long id)  !!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    public Vector getPengajuanUa(Vector vScopeUa, Vector vScopeUaA, int oprObjId) {
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	//boolean ada_pengajuan =false;
    	try {

    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
    		StringTokenizer st = null;
    		//bagian pengajuan dan monitoree
    		if(vScopeUa!=null && vScopeUa.size()>0) {
    			ListIterator li = vScopeUa.listIterator();	
    			String kdpst = "";
    			String sql_stmt = "";
    			kdpst = (String)li.next();
    			////System.out.println("kdpst ua[0] ="+kdpst);
    			String usrObjNick = ""+this.tknOperatorNickname;//
    			if(kdpst.equalsIgnoreCase("own")) { 
    				System.out.println("in own");
    				/*
    				 * kalo own hanya untuk mhs , kalo monitoree pake scope
    				 */
    				//if(usrObjNick.contains("MHS")||usrObjNick.contains("mhs")) {
    					//bagian untul mahasiswa
    				//sql_stmt = "select * from PENGAJUAN_UA where NPMHS=? and SHOW_OWNER=?  and DONE_SO_HIDDEN=false";
    				sql_stmt = "select * from EXT_PENGAJUAN_UA where NPMHS=? and DONE_SO_HIDDEN=false";
					stmt = con.prepareStatement(sql_stmt);	
					stmt.setString(1, this.operatorNpm);
					//stmt.setBoolean(2, true);
					rs = stmt.executeQuery();
					rs.next();
					String id = ""+rs.getLong("ID");
    				String thsms = ""+rs.getString("THSMS");
    				kdpst = ""+rs.getString("KDPST");
    				String npmhs = ""+rs.getString("NPMHS");
    				String status_akhir = ""+rs.getString("STATUS_AKHIR");
    				String skedul_date = ""+rs.getDate("SCHEDULE_DATE");
    				String realisasi_date = ""+rs.getDate("REALISASI_DATE");
    				String kdkmk = ""+rs.getString("KDKMK");
    				String file_name = ""+rs.getString("FILE_NAME");
    				String updtm = ""+rs.getTimestamp("UPDTM");
    				String skedul_time = ""+rs.getTime("SCHEDULE_TIME");
    				String judul = ""+rs.getString("JUDUL");
    				String show_owner = ""+rs.getString("SHOW_OWNER");
    				//String show_approvee = ""+rs.getString("SHOW_APPROVEE");
    				String show_monitoree = ""+rs.getString("SHOW_MONITOREE");
    				String idobj = ""+rs.getInt("IDOBJ");
    				String tkn_id_approvee = ""+rs.getString("TKN_ID_APPROVEE");//tindakan
    				String tkn_show_approvee = ""+rs.getString("TKN_SHOW_APPROVEE");
    				
    				
    				
    				
    				tkn_id_approvee = tkn_id_approvee.replace("`", "-");//convert dulu kalo ngga masalah
    				tkn_show_approvee = tkn_show_approvee.replace("`", "-");
    				lif.add(id+"`"+thsms+"`"+kdpst+"`"+npmhs+"`"+status_akhir+"`"+skedul_date+"`"+realisasi_date+"`"+kdkmk+"`"+file_name+"`"+updtm+"`"+skedul_time+"`"+judul+"`"+show_owner+"`"+show_monitoree+"`"+idobj+"`"+tkn_id_approvee+"`"+tkn_show_approvee);
    				
    			}//end own
    			else { 
    				//bagian monitoree
    				kdpst = Tool.getTokenKe(kdpst, 1);
    				String sql_cmd = "IDOBJ="+kdpst+"";
    				do {
    					kdpst = (String)li.next();	
    					kdpst = Tool.getTokenKe(kdpst, 1);
    					sql_cmd = sql_cmd+" or IDOBJ="+kdpst+"";
    				}
    				while(li.hasNext());
    				//sql_stmt = "select * from PENGAJUAN_UA where SHOW_MONITOREE=? and ("+sql_cmd+") and DONE_SO_HIDDEN=false";
    				//SHOW MONITOREE HANYA UNTUK DI NOTIFICATION
    				sql_stmt = "select * from EXT_PENGAJUAN_UA where ("+sql_cmd+") and DONE_SO_HIDDEN=false";
    				////System.out.println("sql="+sql_stmt);
    				stmt = con.prepareStatement(sql_stmt);
    				//stmt.setBoolean(1, true);
    				rs = stmt.executeQuery();
    				while(rs.next()) {
    					String id = ""+rs.getLong("ID");
        				String thsms = ""+rs.getString("THSMS");
        				kdpst = ""+rs.getString("KDPST");
        				String npmhs = ""+rs.getString("NPMHS");
        				String status_akhir = ""+rs.getString("STATUS_AKHIR");
        				String skedul_date = ""+rs.getDate("SCHEDULE_DATE");
        				String realisasi_date = ""+rs.getDate("REALISASI_DATE");
        				String kdkmk = ""+rs.getString("KDKMK");
        				String file_name = ""+rs.getString("FILE_NAME");
        				String updtm = ""+rs.getTimestamp("UPDTM");
        				String skedul_time = ""+rs.getTime("SCHEDULE_TIME");
        				String judul = ""+rs.getString("JUDUL");
        				String show_owner = ""+rs.getString("SHOW_OWNER");
        				//String show_approvee = ""+rs.getString("SHOW_APPROVEE");
        				String show_monitoree = ""+rs.getString("SHOW_MONITOREE");
        				String idobj = ""+rs.getInt("IDOBJ");
        				String tkn_id_approvee = ""+rs.getString("TKN_ID_APPROVEE");//tindakan
        				String tkn_show_approvee = ""+rs.getString("TKN_SHOW_APPROVEE");
        				tkn_id_approvee = tkn_id_approvee.replace("`", "-");//convert dulu kalo ngga masalah
        				tkn_show_approvee = tkn_show_approvee.replace("`", "-");
        				////System.out.println("monitoree=="+id+"`"+thsms+"`"+kdpst+"`"+npmhs+"`"+status_akhir+"`"+skedul_date+"`"+realisasi_date+"`"+kdkmk+"`"+file_name+"`"+updtm+"`"+skedul_time+"`"+judul+"`"+show_owner+"`"+show_approvee+"`"+show_monitoree+"`"+idobj);
        				lif.add(id+"`"+thsms+"`"+kdpst+"`"+npmhs+"`"+status_akhir+"`"+skedul_date+"`"+realisasi_date+"`"+kdkmk+"`"+file_name+"`"+updtm+"`"+skedul_time+"`"+judul+"`"+show_owner+"`"+show_monitoree+"`"+idobj+"`"+tkn_id_approvee+"`"+tkn_show_approvee);
    				}
    			}
    		}	
    		if(vScopeUaA!=null && vScopeUaA.size()>0) {
    			ListIterator li = vScopeUaA.listIterator();	
    			String kdpst = "";
    			String sql_stmt = "";
    			kdpst = (String)li.next();
    			String usrObjNick = ""+this.tknOperatorNickname;
    			if(kdpst.equalsIgnoreCase("own") && false) {//berarti penguji ujian
    				//cari npmmhs yg jatahnya
    				/*
    				 * UTK SAAT INI BELUM DIGUNAKAN KAREN APPROVAL HANYA VIA BAA & BAUK
    				 */
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
						
    					//stmt = con.prepareStatement("select * from PENGAJUAN_UA where NPMHS=? and SHOW_APPROVEE=?");
						stmt = con.prepareStatement("select * from EXT_PENGAJUAN_UA where NPMHS=?");
						liNpm = vNpm.listIterator();
						while(liNpm.hasNext()) {
							String npm = (String)liNpm.next();
							stmt.setString(1, this.operatorNpm);
	    					//stmt.setBoolean(2, true);
	    					rs = stmt.executeQuery();
	    					while(rs.next()) {
	    						String id = ""+rs.getLong("ID");
	            				String thsms = ""+rs.getString("THSMS");
	            				kdpst = ""+rs.getString("KDPST");
	            				String npmhs = ""+rs.getString("NPMHS");
	            				String status_akhir = ""+rs.getString("STATUS_AKHIR");
	            				String skedul_date = ""+rs.getDate("SCHEDULE_DATE");
	            				String realisasi_date = ""+rs.getDate("REALISASI_DATE");
	            				String kdkmk = ""+rs.getString("KDKMK");
	            				String file_name = ""+rs.getString("FILE_NAME");
	            				String updtm = ""+rs.getTimestamp("UPDTM");
	            				String skedul_time = ""+rs.getTime("SCHEDULE_TIME");
	            				String judul = ""+rs.getString("JUDUL");
	            				String show_owner = ""+rs.getString("SHOW_OWNER");
	            				//String show_approvee = ""+rs.getString("SHOW_APPROVEE");
	            				String show_monitoree = ""+rs.getString("SHOW_MONITOREE");
	            				String idobj = ""+rs.getInt("IDOBJ");
	            				String tkn_id_approvee = ""+rs.getString("TKN_ID_APPROVEE");//tindakan
	            				String tkn_show_approvee = ""+rs.getString("TKN_SHOW_APPROVEE");
	            				tkn_id_approvee = tkn_id_approvee.replace("`", "-");//convert dulu kalo ngga masalah
	            				tkn_show_approvee = tkn_show_approvee.replace("`", "-");
	            				////System.out.println("monitoree=="+id+"`"+thsms+"`"+kdpst+"`"+npmhs+"`"+status_akhir+"`"+skedul_date+"`"+realisasi_date+"`"+kdkmk+"`"+file_name+"`"+updtm+"`"+skedul_time+"`"+judul+"`"+show_owner+"`"+show_approvee+"`"+show_monitoree+"`"+idobj);
	            				lif.add(id+"`"+thsms+"`"+kdpst+"`"+npmhs+"`"+status_akhir+"`"+skedul_date+"`"+realisasi_date+"`"+kdkmk+"`"+file_name+"`"+updtm+"`"+skedul_time+"`"+judul+"`"+show_owner+"`"+show_monitoree+"`"+idobj+"`"+tkn_id_approvee+"`"+tkn_show_approvee);
	            				//lif.add(id+"`"+thsms+"`"+kdpst+"`"+npmhs+"`"+status_akhir+"`"+skedul_date+"`"+realisasi_date+"`"+kdkmk+"`"+file_name+"`"+updtm+"`"+skedul_time+"`"+judul+"`"+show_owner+"`"+show_approvee+"`"+show_monitoree+"`"+idobj);
	    					}
						}
					}	
    			}
    			else {
    				System.out.println("kesini toh??");
    				kdpst = Tool.getTokenKe(kdpst, 1);
    				String sql_cmd = "IDOBJ="+kdpst+"";
    				do {
    					kdpst = (String)li.next();	
    					kdpst = Tool.getTokenKe(kdpst, 1);
    					sql_cmd = sql_cmd+" or IDOBJ="+kdpst+"";
    				}
    				while(li.hasNext());
    				//sql_stmt = "select * from PENGAJUAN_UA where SHOW_APPROVEE=? and ("+sql_cmd+")";
    				//sql_stmt = "select * from PENGAJUAN_UA where ("+sql_cmd+")";
    				//sql_stmt = "select * from PENGAJUAN_UA where DONE_SO_HIDDEN=false and (TKN_ID_APPROVEE is NULL or TKN_ID_APPROVEE NOT LIKE ?) and ("+sql_cmd+") limit 1";
    				/*
    				 * KARENA INI BUKAN FUNGSI UNTUK NOTIFICATION TAPI RIWYAT JADI TETAP DITAMPILKAN RECORD WALOAUPUN SUDAH DIBERI TINDAKAN
    				 */
    				sql_stmt = "select * from EXT_PENGAJUAN_UA where DONE_SO_HIDDEN=false and ("+sql_cmd+")";
    				
    				////System.out.println(sql_stmt);
    				stmt = con.prepareStatement(sql_stmt);
    				//stmt.setString(1, "%/"+oprObjId+"`%");
    				rs = stmt.executeQuery();
    				while(rs.next()) {
    					String id = ""+rs.getLong("ID");
        				String thsms = ""+rs.getString("THSMS");
        				kdpst = ""+rs.getString("KDPST");
        				String npmhs = ""+rs.getString("NPMHS");
        				String status_akhir = ""+rs.getString("STATUS_AKHIR");
        				String skedul_date = ""+rs.getDate("SCHEDULE_DATE");
        				String realisasi_date = ""+rs.getDate("REALISASI_DATE");
        				String kdkmk = ""+rs.getString("KDKMK");
        				String file_name = ""+rs.getString("FILE_NAME");
        				String updtm = ""+rs.getTimestamp("UPDTM");
        				String skedul_time = ""+rs.getTime("SCHEDULE_TIME");
        				String judul = ""+rs.getString("JUDUL");
        				String show_owner = ""+rs.getString("SHOW_OWNER");
        				//String show_approvee = ""+rs.getString("SHOW_APPROVEE");
        				String show_monitoree = ""+rs.getString("SHOW_MONITOREE");
        				String idobj = ""+rs.getInt("IDOBJ");
        				String tkn_id_approvee = ""+rs.getString("TKN_ID_APPROVEE");//tindakan
        				String tkn_show_approvee = ""+rs.getString("TKN_SHOW_APPROVEE");
        				tkn_id_approvee = tkn_id_approvee.replace("`", "-");//convert dulu kalo ngga masalah
        				tkn_show_approvee = tkn_show_approvee.replace("`", "-");
        				////System.out.println("monitoree=="+id+"`"+thsms+"`"+kdpst+"`"+npmhs+"`"+status_akhir+"`"+skedul_date+"`"+realisasi_date+"`"+kdkmk+"`"+file_name+"`"+updtm+"`"+skedul_time+"`"+judul+"`"+show_owner+"`"+show_approvee+"`"+show_monitoree+"`"+idobj);
        				lif.add(id+"`"+thsms+"`"+kdpst+"`"+npmhs+"`"+status_akhir+"`"+skedul_date+"`"+realisasi_date+"`"+kdkmk+"`"+file_name+"`"+updtm+"`"+skedul_time+"`"+judul+"`"+show_owner+"`"+show_monitoree+"`"+idobj+"`"+tkn_id_approvee+"`"+tkn_show_approvee);
        				////System.out.println("approvee=="+id+"`"+thsms+"`"+kdpst+"`"+npmhs+"`"+status_akhir+"`"+skedul_date+"`"+realisasi_date+"`"+kdkmk+"`"+file_name+"`"+updtm+"`"+skedul_time+"`"+judul+"`"+show_owner+"`"+show_approvee+"`"+show_monitoree+"`"+idobj);
        				//lif.add(id+"`"+thsms+"`"+kdpst+"`"+npmhs+"`"+status_akhir+"`"+skedul_date+"`"+realisasi_date+"`"+kdkmk+"`"+file_name+"`"+updtm+"`"+skedul_time+"`"+judul+"`"+show_owner+"`"+show_approvee+"`"+show_monitoree+"`"+idobj);
    				}
    			}
    				
			}	
    		
    		vf = Tool.removeDuplicateFromVector(vf);
    		////System.out.println("vf1="+vf.size());
    		
    		////System.out.println("vf2="+vf.size());
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
    
    public Vector getRiwayatUaForDashboardMhs(String npmhs) {
    	Vector v = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection(); 
			//stmt = con.prepareStatement("select * from PENGAJUAN_UA inner join RIWAYAT_PENGAJUAN_UA on PENGAJUAN_UA.ID=RIWAYAT_PENGAJUAN_UA.ID where NPMHS=? and DONE_SO_HIDDEN=?");
			stmt = con.prepareStatement("select * from EXT_PENGAJUAN_UA  where NPMHS=? and  STATUS_AKHIR like ? and DONE_SO_HIDDEN=?");
			stmt.setString(1, npmhs);
			stmt.setString(2, "%terima%");
			stmt.setBoolean(3, true);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				ListIterator li = v.listIterator();
				do {
					String kdkmk = rs.getString("KDKMK");
					String status = rs.getString("STATUS_AKHIR");
					String date = ""+rs.getDate("REALISASI_DATE");
					if(!Checker.isStringNullOrEmpty(date)) {
						//date = Converter.convertFormatTanggalKeFormatDeskriptif(date);	
					}
					else {
						date = "null";
					}
					String time = ""+rs.getTime("REALISASI_TIME");
					if(!Checker.isStringNullOrEmpty(time)) {
						//time = Converter.convertFormatTanggalKeFormatDeskriptif(time);	
					}
					else {
						time = "null";
					}
					li.add(kdkmk+"`"+date+"`"+time+"`"+status);
				}
				while(rs.next());
				
			}
			
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
    	return v;
    }
    
    public Vector addInfoPengajuanRules(Vector vPengajuan) {
    	ListIterator lif = vPengajuan.listIterator();
    	StringTokenizer st = null;
    	try {

    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection(); 
			stmt = con.prepareStatement("select * from PENGAJUAN_UA_RULES where IDOBJ=?");
    		
    		//add rules
    		//lif = vf.listIterator();
    		while(lif.hasNext()) {
    			String brs = (String)lif.next();
    			////System.out.println("barsan="+brs);
    			st = new StringTokenizer(brs,"`");
    			////System.out.println("bar = "+brs);
    			//String id+"`"+thsms+"`"+kdpst+"`"+npmhs+"`"+status_akhir+"`"+skedul_date+"`"+realisasi_date+"`"+kdkmk+"`"+file_name+"`"+updtm+"`"+skedul_time+"`"+judul+"`"+show_owner+"`"+show_monitoree+"`"+idobj+"`"+tkn_id_approvee+"`"+tkn_show_approvee);
    			String targetIdObj = Tool.getTokenKe(brs, 16,"`");
    			////System.out.println("targetIdObj="+targetIdObj);
    			stmt.setLong(1, Long.parseLong(targetIdObj));
    			rs = stmt.executeQuery();
    			rs.next();
    			String rule_tkn_approvee_id = rs.getString("TKN_APPROVEE_ID");
    			rule_tkn_approvee_id = rule_tkn_approvee_id.replace("`", "-");
    			String urutan = ""+rs.getBoolean("URUTAN");
    			String tkn_approvee_nickname = rs.getString("TKN_APPROVEE_NICKNAME");
    			tkn_approvee_nickname = tkn_approvee_nickname.replace("`", "-");
    			lif.set(brs+"`"+rule_tkn_approvee_id+"`"+urutan+"`"+tkn_approvee_nickname);
    			////System.out.println(":targetIdObj:"+targetIdObj);
    		}
    		
    		//ketinggalan RIWYAT_PENOLAKAN,DONE,REALISASI TIME
    		lif = vPengajuan.listIterator();
    		stmt = con.prepareStatement("select RIWAYAT_PENOLAKAN,DONE_SO_HIDDEN,REALISASI_TIME from EXT_PENGAJUAN_UA where ID=?");
    		while(lif.hasNext()) {
    			String brs = (String)lif.next();
    			//System.out.println("barsdd = "+brs);
    			st = new StringTokenizer(brs,"`");
    			String id = Tool.getTokenKe(brs, 2,"`");
    			
    			stmt.setLong(1, Long.parseLong(id));
    			rs = stmt.executeQuery();
    			rs.next();
    			String riwayat = ""+rs.getString("RIWAYAT_PENOLAKAN");
    			String done = ""+rs.getBoolean("DONE_SO_HIDDEN");
    			String time = ""+rs.getTime("REALISASI_TIME");
    			lif.set(brs+"`"+done+"`"+time+"`"+riwayat);
    			//System.out.println(brs+"`"+done+"`"+time+"`"+riwayat);
    			////System.out.println(":targetIdObj:"+targetIdObj);
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
    	return vPengajuan;
    }	

}
