package beans.dbase.keu;

import beans.dbase.SearchDb;
import beans.tools.Checker;
import beans.tools.ToJson;

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
 * Servlet implementation class SearchDbKeu
 */
@WebServlet("/SearchDbKeu")
public class SearchDbKeu extends SearchDb implements Servlet {
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
    public SearchDbKeu() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbKeu(String operatorNpm) {
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
    public SearchDbKeu(Connection con) {
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

    public Vector getInfoKeuFromPymntTransit(String KdpstPayee,String npmhsPayee) {
    	/*
    	 * ambil  list reuquest pembayaran dengan bukti setoran dari pymnt transit table
    	 */
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT * FROM PYMNT_TRANSIT WHERE KDPSTPYMNT=? AND NPMHSPYMNT=?");
    		stmt.setString(1,KdpstPayee);
    		stmt.setString(2,npmhsPayee);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			
    			String kuiid = ""+rs.getInt("KUIIDPYMNT");
    			kuiid = kuiid.replace("#", "_");
    			String kdpst = ""+rs.getString("KDPSTPYMNT");
    			kdpst = kdpst.replace("#", "_");
    			String npmhs = ""+rs.getString("NPMHSPYMNT");
    			npmhs = npmhs.replace("#", "_");
    			String norut = ""+rs.getInt("NORUTPYMNT");
    			norut = norut.replace("#", "_");
    			String tgkui = ""+rs.getDate("TGKUIPYMNT");
    			tgkui = tgkui.replace("#", "_");
    			String tgtrs = ""+rs.getDate("TGTRSPYMNT");
    			tgtrs = tgtrs.replace("#", "_");
    			String keter = ""+rs.getString("KETERPYMNT");
    			keter = keter.replace("#", "_");
    			String keterDetail = ""+rs.getString("KETER_PYMNT_DETAIL");
    			keterDetail = keterDetail.replace("#", "_");
    			String penyetor = ""+rs.getString("PAYEEPYMNT");
    			penyetor = penyetor.replace("#", "_");
    			String besaran = ""+rs.getDouble("AMONTPYMNT");
    			besaran = besaran.replace("#", "_");
    			String posBiaya = ""+rs.getString("PYMTPYMNT");
    			posBiaya = posBiaya.replace("#", "_");
    			String gelombangKe = ""+rs.getInt("GELOMBANG");
    			gelombangKe = gelombangKe.replace("#", "_");
    			String cicilanKe =  ""+rs.getInt("CICILAN");
    			cicilanKe = cicilanKe.replace("#", "_");
    			String krs =  ""+rs.getInt("KRS");
    			krs = krs.replace("#", "_");
    			String targetBankAcc = ""+rs.getString("NOACCPYMNT");
    			targetBankAcc = targetBankAcc.replace("#", "_");
    			String opnpm = ""+rs.getString("OPNPMPYMNT");
    			opnpm = opnpm.replace("#", "_");
    			String opnmm = ""+rs.getString("OPNMMPYMNT");
    			opnmm = opnmm.replace("#", "_");
    			String sdhDstorKeBank = ""+rs.getBoolean("SETORPYMNT");
    			sdhDstorKeBank = sdhDstorKeBank.replace("#", "_");
    			String nonpmNggaTauUtkApa = ""+rs.getString("NONPMPYMNT");
    			nonpmNggaTauUtkApa = nonpmNggaTauUtkApa.replace("#", "_");
    			String batal = ""+rs.getBoolean("VOIDDPYMNT");
    			batal = batal.replace("#", "_");
    			String noKodePmnt = ""+rs.getString("NOKODPYMNT");
    			noKodePmnt = noKodePmnt.replace("#", "_");
    			String initUpdTm = ""+rs.getTimestamp("UPDTMPYMNT");
    			initUpdTm = initUpdTm.replace("#", "_");
    			String npmVoider = ""+rs.getString("VOIDOPNPM");
    			npmVoider = npmVoider.replace("#", "_");
    			String keterVoid = ""+rs.getString("VOIDKETER");
    			keterVoid = keterVoid.replace("#", "_");
    			String nmmVoider = ""+rs.getString("VOIDOPNMM");
    			nmmVoider = nmmVoider.replace("#", "_");
    			String namaBuktiFile = ""+rs.getString("FILENAME");
    			//cek apa filenya ada
    			String requestedImage = Constants.getFolderBuktiBayaran()+"/"+npmhs+"/"+namaBuktiFile;
    	        //System.out.println("requestedImage="+requestedImage);
    	        File image = new File(requestedImage);
    	        // Check if file actually exists in filesystem.
    	        if (!image.exists()) {
    	        	namaBuktiFile = "null";
    	        }
    			namaBuktiFile = namaBuktiFile.replace("#", "_");
    			String uploadTm = ""+rs.getTimestamp("UPLOADTM");
    			uploadTm = uploadTm.replace("#", "_");
    			String aprovalTm = ""+rs.getTimestamp("APROVALTM");
    			aprovalTm = aprovalTm.replace("#", "_");
    			String rejectedTm = ""+rs.getTimestamp("REJECTTM");
    			rejectedTm = rejectedTm.replace("#", "_");
    			String rejectedNote = ""+rs.getString("REJECTION_NOTE");
    			rejectedNote = rejectedNote.replace("#", "_");
    			String npmApprovee = ""+rs.getString("NPM_APPROVEE");
    			npmApprovee = npmApprovee.replace("#", "_");
    			String idBeasiswa = ""+rs.getLong("IDPAKETBEASISWA");
    			li.add(kuiid+"#"+kdpst+"#"+npmhs+"#"+norut+"#"+tgkui+"#"+tgtrs+"#"+keter+"#"+keterDetail+"#"+penyetor+"#"+besaran+"#"+posBiaya+"#"+gelombangKe+"#"+cicilanKe+"#"+krs+"#"+targetBankAcc+"#"+opnpm+"#"+opnmm+"#"+sdhDstorKeBank+"#"+nonpmNggaTauUtkApa+"#"+batal+"#"+noKodePmnt+"#"+initUpdTm+"#"+npmVoider+"#"+keterVoid+"#"+nmmVoider+"#"+namaBuktiFile+"#"+uploadTm+"#"+aprovalTm+"#"+rejectedTm+"#"+rejectedNote+"#"+npmApprovee+"#"+idBeasiswa);
    		}
    		
    		
    		stmt = con.prepareStatement("SELECT * FROM BEASISWA where IDPAKETBEASISWA=?");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"#");
    			String kuiid = st.nextToken();
    			String kdpst = st.nextToken();
    			String npmhs = st.nextToken();
    			String norut = st.nextToken();
    			String tgkui = st.nextToken();
    			String tgtrs = st.nextToken();
    			String keter = st.nextToken();
    			String keterDetail = st.nextToken();
    			String penyetor = st.nextToken();
    			String besaran = st.nextToken();
    			String posBiaya = st.nextToken();
    			String gelombangKe = st.nextToken();
    			String cicilanKe = st.nextToken();
    			String krs = st.nextToken();
    			String targetBankAcc = st.nextToken();
    			String opnpm = st.nextToken();
    			String opnmm = st.nextToken();
    			String sdhDstorKeBank = st.nextToken();
    			String nonpmNggaTauUtkApa = st.nextToken();
    			String batal = st.nextToken();
    			String noKodePmnt = st.nextToken();
    			String initUpdTm = st.nextToken();
    			String npmVoider = st.nextToken();
    			String keterVoid = st.nextToken();
    			String nmmVoider = st.nextToken();
    			String namaBuktiFile = st.nextToken();
    			String uploadTm = st.nextToken();
    			String aprovalTm = st.nextToken();
    			String rejectedTm = st.nextToken();
    			String rejectedNote = st.nextToken();
    			String npmApprovee = st.nextToken();
    			String idBeasiswa = st.nextToken();
    			stmt.setLong(1, Long.parseLong(idBeasiswa));
    			rs = stmt.executeQuery();
    			String nmmpaket = "null";
    			if(rs.next()) {
    				nmmpaket = ""+rs.getString("NAMAPAKET");
    			}
    			li.set(brs+"#"+nmmpaket);
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
    	//System.out.println(v.size());
    	return v;
    }
	
    
    public Vector getPymntApprovalRequest(Vector vScope) {
    	/*
    	 * ambil  list reuquest pembayaran dengan bukti setoran dari pymnt transit table
    	 */
    	Vector v = new Vector();
    	ListIterator li = vScope.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		String sqlcmd="SELECT * FROM PYMNT_TRANSIT INNER JOIN CIVITAS ON NPMHSPYMNT=NPMHSMSMHS WHERE KDPSTPYMNT='";
    		while(li.hasNext()) {
    			String kdpst = (String)li.next();
    			sqlcmd=sqlcmd+kdpst+"'";
    			if(li.hasNext()) {
    				sqlcmd=sqlcmd+" or KDPSTPYMNT='";
    			}
    		}
    		//System.out.println(sqlcmd);
    		stmt = con.prepareStatement(sqlcmd+" ORDER BY NORUTPYMNT,GROUP_ID");
    		rs = stmt.executeQuery();
    		li = v.listIterator();
    		while(rs.next()) {
    			String kuiid = ""+rs.getInt("KUIIDPYMNT");
    			kuiid = kuiid.replace("#", "_");
    			String kdpst = ""+rs.getString("KDPSTPYMNT");
    			kdpst = kdpst.replace("#", "_");
    			String npmhs = ""+rs.getString("NPMHSPYMNT");
    			npmhs = npmhs.replace("#", "_");
    			String norut = ""+rs.getInt("NORUTPYMNT");
    			norut = norut.replace("#", "_");
    			String tgkui = ""+rs.getDate("TGKUIPYMNT");
    			tgkui = tgkui.replace("#", "_");
    			String tgtrs = ""+rs.getDate("TGTRSPYMNT");
    			tgtrs = tgtrs.replace("#", "_");
    			String keter = ""+rs.getString("KETERPYMNT");
    			keter = keter.replace("#", "_");
    			String keterDetail = ""+rs.getString("KETER_PYMNT_DETAIL");
    			keterDetail = keterDetail.replace("#", "_");
    			String penyetor = ""+rs.getString("PAYEEPYMNT");
    			penyetor = penyetor.replace("#", "_");
    			String besaran = ""+rs.getDouble("AMONTPYMNT");
    			besaran = besaran.replace("#", "_");
    			String posBiaya = ""+rs.getString("PYMTPYMNT");
    			posBiaya = posBiaya.replace("#", "_");
    			String gelombangKe = ""+rs.getInt("GELOMBANG");
    			gelombangKe = gelombangKe.replace("#", "_");
    			String cicilanKe =  ""+rs.getInt("CICILAN");
    			cicilanKe = cicilanKe.replace("#", "_");
    			String krs =  ""+rs.getInt("KRS");
    			krs = krs.replace("#", "_");
    			String targetBankAcc = ""+rs.getString("NOACCPYMNT");
    			targetBankAcc = targetBankAcc.replace("#", "_");
    			String opnpm = ""+rs.getString("OPNPMPYMNT");
    			opnpm = opnpm.replace("#", "_");
    			String opnmm = ""+rs.getString("OPNMMPYMNT");
    			opnmm = opnmm.replace("#", "_");
    			String sdhDstorKeBank = ""+rs.getBoolean("SETORPYMNT");
    			sdhDstorKeBank = sdhDstorKeBank.replace("#", "_");
    			String nonpmNggaTauUtkApa = ""+rs.getString("NONPMPYMNT");
    			nonpmNggaTauUtkApa = nonpmNggaTauUtkApa.replace("#", "_");
    			String batal = ""+rs.getBoolean("VOIDDPYMNT");
    			batal = batal.replace("#", "_");
    			String noKodePmnt = ""+rs.getString("NOKODPYMNT");
    			noKodePmnt = noKodePmnt.replace("#", "_");
    			String initUpdTm = ""+rs.getTimestamp("UPDTMPYMNT");
    			initUpdTm = initUpdTm.replace("#", "_");
    			String npmVoider = ""+rs.getString("VOIDOPNPM");
    			npmVoider = npmVoider.replace("#", "_");
    			String keterVoid = ""+rs.getString("VOIDKETER");
    			keterVoid = keterVoid.replace("#", "_");
    			String nmmVoider = ""+rs.getString("VOIDOPNMM");
    			nmmVoider = nmmVoider.replace("#", "_");
    			String namaBuktiFile = ""+rs.getString("FILENAME");
    			//cek apa filenya ada
    			String requestedImage = Constants.getFolderBuktiBayaran()+"/"+npmhs+"/"+namaBuktiFile;
    	        //System.out.println("requestedImage="+requestedImage);
    	        File image = new File(requestedImage);
    	        // Check if file actually exists in filesystem.
    	        if (!image.exists()) {
    	        	namaBuktiFile = "null";
    	        }
    			namaBuktiFile = namaBuktiFile.replace("#", "_");
    			String uploadTm = ""+rs.getTimestamp("UPLOADTM");
    			uploadTm = uploadTm.replace("#", "_");
    			String aprovalTm = ""+rs.getTimestamp("APROVALTM");
    			aprovalTm = aprovalTm.replace("#", "_");
    			String rejectedTm = ""+rs.getTimestamp("REJECTTM");
    			rejectedTm = rejectedTm.replace("#", "_");
    			String rejectedNote = ""+rs.getString("REJECTION_NOTE");
    			rejectedNote = rejectedNote.replace("#", "_");
    			String npmApprovee = ""+rs.getString("NPM_APPROVEE");
    			npmApprovee = npmApprovee.replace("#", "_");
    			String nmmhs = ""+rs.getString("NMMHSMSMHS");
    			nmmhs = nmmhs.replace("#", "_");
    			String nimhs = ""+rs.getString("NIMHSMSMHS");
    			nimhs = nimhs.replace("#", "_");
    		
    			li.add(kuiid+"#"+kdpst+"#"+npmhs+"#"+norut+"#"+tgkui+"#"+tgtrs+"#"+keter+"#"+keterDetail+"#"+penyetor+"#"+besaran+"#"+posBiaya+"#"+gelombangKe+"#"+cicilanKe+"#"+krs+"#"+targetBankAcc+"#"+opnpm+"#"+opnmm+"#"+sdhDstorKeBank+"#"+nonpmNggaTauUtkApa+"#"+batal+"#"+noKodePmnt+"#"+initUpdTm+"#"+npmVoider+"#"+keterVoid+"#"+nmmVoider+"#"+namaBuktiFile+"#"+uploadTm+"#"+aprovalTm+"#"+rejectedTm+"#"+rejectedNote+"#"+npmApprovee+"#"+nmmhs+"#"+nimhs);
    		}
    		//stmt.setString(1,KdpstPayee);
    		//stmt.setString(2,npmhsPayee);
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
    
    public JSONArray getPymntApprovalRequestJsonStyle(Vector vScope) {
    	/*
    	 * ambil  list reuquest pembayaran dengan bukti setoran dari pymnt transit table
    	 */
    	JSONArray jsoa = null;
    	Vector v = new Vector();
    	ListIterator li = vScope.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		String sqlcmd="SELECT KUIIDPYMNT,KDPSTPYMNT,NPMHSPYMNT,NORUTPYMNT,TGKUIPYMNT,TGTRSPYMNT,KETERPYMNT,KETER_PYMNT_DETAIL,PAYEEPYMNT,AMONTPYMNT,PYMTPYMNT,GELOMBANG,CICILAN,KRS,NOACCPYMNT,OPNPMPYMNT,OPNMMPYMNT,SETORPYMNT,NONPMPYMNT,VOIDDPYMNT,NOKODPYMNT,UPDTMPYMNT,VOIDOPNPM,VOIDKETER,VOIDOPNMM,FILENAME,UPLOADTM,APROVALTM,REJECTTM,REJECTION_NOTE,NPM_APPROVEE,GROUP_ID,NMMHSMSMHS,NIMHSMSMHS,IDPAKETBEASISWA,CIVITAS.ID_OBJ,KODE_KAMPUS_DOMISILI FROM PYMNT_TRANSIT INNER JOIN CIVITAS ON NPMHSPYMNT=NPMHSMSMHS WHERE KDPSTPYMNT='";
    		//String sqlcmd="SELECT KUIIDPYMNT,KDPSTPYMNT,NPMHSPYMNT,NORUTPYMNT,TGKUIPYMNT,TGTRSPYMNT,KETERPYMNT,KETER_PYMNT_DETAIL,PAYEEPYMNT,AMONTPYMNT,PYMTPYMNT,GELOMBANG,CICILAN,KRS,NOACCPYMNT,OPNPMPYMNT,OPNMMPYMNT,SETORPYMNT,NONPMPYMNT,VOIDDPYMNT,NOKODPYMNT,UPDTMPYMNT,VOIDOPNPM,VOIDKETER,VOIDOPNMM,FILENAME,UPLOADTM,APROVALTM,REJECTTM,REJECTION_NOTE,NPM_APPROVEE,GROUP_ID,NMMHSMSMHS,NIMHSMSMHS,IDPAKETBEASISWA FROM PYMNT_TRANSIT INNER JOIN CIVITAS ON NPMHSPYMNT=NPMHSMSMHS WHERE KDPSTPYMNT='";
    		while(li.hasNext()) {
    			String kdpst = (String)li.next();
    			sqlcmd=sqlcmd+kdpst+"'";
    			if(li.hasNext()) {
    				sqlcmd=sqlcmd+" or KDPSTPYMNT='";
    			}
    		}
    		//System.out.println(sqlcmd);
    		stmt = con.prepareStatement(sqlcmd+" ORDER BY NORUTPYMNT,GROUP_ID");
    		rs = stmt.executeQuery();
    		jsoa = ToJson.toJSONArray(rs);
    		//stmt.setString(1,KdpstPayee);
    		//stmt.setString(2,npmhsPayee);
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
    	//System.out.println(v.size());
    	return jsoa;
    }
    
    

    

    public Vector getTotPembayaranTiapMhs(Vector vSummaryPMB,String target_kdpst,String target_thsms ) {
    	/*
    	 * menambahkan info total pembayaran ke dalam vector vSummaryPmb
    	 */
    	//Vector v = new Vector();
    	
    	ListIterator li = null;
    	if(vSummaryPMB!=null && vSummaryPMB.size()>0) {
    		li = vSummaryPMB.listIterator();
    		double tot_approved = 0, tot_unapproved=0;
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("SELECT * FROM PYMNT_TRANSIT WHERE KDPSTPYMNT=? AND NPMHSPYMNT=? and VOIDDPYMNT=?");
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs);
        			String id_obj = st.nextToken();
        			String kdpst = st.nextToken();
        			String obj_dsc = st.nextToken();
        			String obj_level = st.nextToken();
        			st = new StringTokenizer(obj_dsc,"_");
        			System.out.println(kdpst+"<br/>==========================<br/>");

        			Vector vMhs = (Vector)li.next();
        			ListIterator liMhs = vMhs.listIterator();
        			int baru=0,pindahan=0,laki=0,wanita=0;
        			String kdjen = null;
        			while(liMhs.hasNext()) {
        				brs = (String)liMhs.next();
        				st = new StringTokenizer(brs,"#&");
        				kdjen = st.nextToken();
        				String npmhs = st.nextToken();
        				String nmmhs = st.nextToken();
        				String stpid = st.nextToken();
        				String kdjek = st.nextToken();
        				System.out.println(brs);
        				if(kdpst.equalsIgnoreCase(target_kdpst)) {
        					stmt.setString(1, kdpst);
        					stmt.setString(2, npmhs);
        					stmt.setBoolean(3, false);
        					rs = stmt.executeQuery();
        					tot_approved = 0; tot_unapproved=0;
        					while(rs.next()) {
        						tot_unapproved = tot_unapproved+rs.getDouble("AMONTPYMNT");
        					}
        					liMhs.set(brs+"#&"+tot_unapproved);
        				}
        				else {
        					liMhs.set(brs+"#&"+tot_unapproved);
        				}
        				
        			}
        			li.set(vMhs);
        		}
        			//tabel pymnt approved
        		stmt = con.prepareStatement("SELECT * FROM PYMNT WHERE KDPSTPYMNT=? AND NPMHSPYMNT=? and VOIDDPYMNT=?");
        		li = vSummaryPMB.listIterator();
        		while(li.hasNext()) {
            		String brs = (String)li.next();
            		StringTokenizer st = new StringTokenizer(brs);
            		String id_obj = st.nextToken();
            		String kdpst = st.nextToken();
            		String obj_dsc = st.nextToken();
            		String obj_level = st.nextToken();
            		st = new StringTokenizer(obj_dsc,"_");
            		//System.out.println(kdpst+"<br/>==========================<br/>");

            		Vector vMhs = (Vector)li.next();
            		ListIterator liMhs = vMhs.listIterator();
            		String kdjen = null;
            		while(liMhs.hasNext()) {
            			brs = (String)liMhs.next();
            			st = new StringTokenizer(brs,"#&");
            			kdjen = st.nextToken();
            			String npmhs = st.nextToken();
            			String nmmhs = st.nextToken();
            			String stpid = st.nextToken();
            			String kdjek = st.nextToken();
            			//System.out.println(brs);
            			if(kdpst.equalsIgnoreCase(target_kdpst)) {
            				stmt.setString(1, kdpst);
            				stmt.setString(2, npmhs);
            				stmt.setBoolean(3, false);
            				rs = stmt.executeQuery();
            				tot_approved = 0; tot_unapproved=0;
            				while(rs.next()) {
            					tot_approved = tot_approved+rs.getDouble("AMONTPYMNT");
            				}
            				liMhs.set(brs+"#&"+tot_approved);
            			}
            			else {
            				liMhs.set(brs+"#&"+tot_approved);
            			}
            		}
            		li.set(vMhs);	
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
    	    	//System.out.println(v.size());
    	return vSummaryPMB;
    }

}
