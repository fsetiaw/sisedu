package beans.dbase.mhs;

import beans.dbase.SearchDb;
import beans.tools.Checker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ListIterator;
import java.util.Vector;
import java.util.StringTokenizer;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class SearchDbInfoMhs
 */
@Stateless
@LocalBean
public class SearchDbInfoMhs extends SearchDb {
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
    public SearchDbInfoMhs() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbInfoMhs(String operatorNpm) {
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
    public SearchDbInfoMhs(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public String getNmmhsGiven(String listNpmByComa, String kdpst) {
    	//Vector v = null;
    	StringTokenizer st = null;
    	String listBaru="";
    	try {
    		if(listNpmByComa!=null && (st = new StringTokenizer(listNpmByComa,",")).countTokens()>0) {
    			
    		
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    		//	get
    			stmt = con.prepareStatement("select NMMHSMSMHS from CIVITAS where KDPSTMSMHS=? and NPMHSMSMHS=?");
    			while(st.hasMoreTokens()) {
    				String npmhs = st.nextToken();
    				stmt.setString(1, kdpst);
    				stmt.setString(2, npmhs);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				String nmmhs = rs.getString("NMMHSMSMHS");
        				listBaru=listBaru+npmhs+"-"+nmmhs;
        			}
        			else {
        				listBaru=listBaru+npmhs+"-ERROR TDK ADA NAMA";
        			}
        			if(st.hasMoreTokens()) {
        				listBaru=listBaru+",";
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
    	return listBaru;
    }
    
    
    
    
    public String getNmmhsDanProdiGiven(String listNpmByComa) {
    	//Vector v = null;
    	StringTokenizer st = null;
    	String listBaru="";
    	try {
    		if(listNpmByComa!=null && (st = new StringTokenizer(listNpmByComa,",")).countTokens()>0) {
    			
    		
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    		//	get
    			stmt = con.prepareStatement("select NMMHSMSMHS,KDPSTMSMHS from CIVITAS where NPMHSMSMHS=?");
    			while(st.hasMoreTokens()) {
    				String npmhs = st.nextToken();
    				stmt.setString(1, npmhs);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				String nmmhs = rs.getString("NMMHSMSMHS");
        				String kdpst = rs.getString("KDPSTMSMHS");
        				listBaru=listBaru+npmhs+"-"+nmmhs+"-"+kdpst;
        			}
        			else {
        				listBaru=listBaru+npmhs+"-ERROR TDK ADA NAMA-ERROR TDK ADA KDPST";
        			}
        			if(st.hasMoreTokens()) {
        				listBaru=listBaru+",";
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
    	return listBaru;
    }
    
    
    
    
    public Vector getListMhsYgSdhHeregistrasi(String thsms,Vector vScopeViewWhoRegister) {
    	Vector v = null;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	String sql = null;
    	if(vScopeViewWhoRegister!=null && vScopeViewWhoRegister.size()>0) {
    		sql = "";
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		li = vScopeViewWhoRegister.listIterator();
        		while(li.hasNext()) {
        			String kdpst = (String)li.next();
        			sql = sql + "DAFTAR_ULANG.KDPST='"+kdpst+"'";
        			if(li.hasNext()) {
        				sql = sql+" OR ";
        			}
        		}
        		v = new Vector();
        		li = v.listIterator();
        		sql = "SELECT * FROM DAFTAR_ULANG INNER JOIN DAFTAR_ULANG_RULES dur1 ON DAFTAR_ULANG.THSMS = dur1.THSMS INNER JOIN DAFTAR_ULANG_RULES dur2 ON DAFTAR_ULANG.KDPST = dur2.KDPST WHERE DAFTAR_ULANG.THSMS='"+thsms+"' AND ("+sql+")";
        		//System.out.println("sql=>"+sql);
        		stmt = con.prepareStatement(sql);
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			String kdpst_ = rs.getString("KDPST");
        			String npmhs_ = rs.getString("NPMHS");
        			String tknApr = rs.getString("TOKEN_APPROVAL");// yg sudah veriied 
        			if(Checker.isStringNullOrEmpty(tknApr)) {
        				tknApr = "null";
        			}
        			String tknVer = rs.getString("TKN_VERIFICATOR");//verificator heregitrasi
        			if(Checker.isStringNullOrEmpty(tknVer)) {
        				tknVer = "null";
        			}
        			String tknKartuUjian = rs.getString("TOKEN_KARTU_UJIAN");
        			if(Checker.isStringNullOrEmpty(tknKartuUjian)) {
        				tknKartuUjian = "null";
        			}
        			String tknApprKartuUjian = rs.getString("TOKEN_APPROVAL_KARTU_UJIAN");
        			if(Checker.isStringNullOrEmpty(tknApprKartuUjian)) {
        				tknApprKartuUjian = "null";
        			}
        			String tknStatus = rs.getString("STATUS_APPROVAL_KARTU_UJIAN");
        			if(Checker.isStringNullOrEmpty(tknStatus)) {
        				tknStatus = "null";
        			}
        			//System.out.println("^."+kdpst_+"||"+npmhs_+"||"+tknApr+"||"+tknVer+"||"+tknKartuUjian+"||"+tknApprKartuUjian);
        			//jika verificator tidak komplit, exclude
        			if(tknApr!=null && !Checker.isStringNullOrEmpty(tknApr)) {
        				boolean pass = true;
            			//System.out.println("tknVer=="+tknVer);
            			st = new StringTokenizer(tknVer,",");
            			while(st.hasMoreTokens() && pass) {
            				String nickNameVerificator = st.nextToken();
            				//System.out.println("tknVer==vs nickNameVerificator=="+tknVer+" vs "+nickNameVerificator);
            				if(!tknApr.contains(nickNameVerificator)) {
            					pass = false;
            				}
            			}
            			if(pass) {
            				//System.out.println("pass");
            				li.add(kdpst_+"||"+npmhs_+"||"+tknApr+"||"+tknVer+"||"+tknKartuUjian+"||"+tknApprKartuUjian+"||"+tknStatus);
            				//if(npmhs_.equalsIgnoreCase("2020113100003")) {
            					//System.out.println("disini == "+kdpst_+"||"+npmhs_+"||"+tknApr+"||"+tknVer+"||"+tknKartuUjian+"||"+tknApprKartuUjian+"||"+tknStatus);
            				//}
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
    	   
    	return v;
    }
    
    public Vector getRuleKartuUjian(String thsms,String targetUjian, Vector vGetListMhsYgSdhHeregistrasi) {
    	//Vector v = null;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	String sql = null;
    	if(vGetListMhsYgSdhHeregistrasi!=null && vGetListMhsYgSdhHeregistrasi.size()>0) {
    		sql = "";
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select TKN_VERIFICATOR_KARTU FROM KARTU_UJIAN_RULES WHERE THSMS=? and KDPST=? and TIPE_UJIAN=?");
        		li = vGetListMhsYgSdhHeregistrasi.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			//System.out.println("brs--"+brs);
        			//li.add(kdpst_+"||"+npmhs_+"||"+tknApr+"||"+tknVer+"||"+tknKartuUjian+"||"+tknApprKartuUjian+"||"+tknStatus);
        			st = new StringTokenizer(brs,"||");
        			String kdpst = st.nextToken();
        			String npmhs = st.nextToken();
        			String tknApr = st.nextToken();
        			String tknVer = st.nextToken();
        			String tknKartuUjian = st.nextToken();
        			String tknApprKartuUjian = st.nextToken();
        			String tknStatus = st.nextToken();
        			stmt.setString(1, thsms);
        			stmt.setString(2, kdpst);
        			stmt.setString(3, targetUjian);
        			rs = stmt.executeQuery();
        			rs.next();
        			String approvee = ""+rs.getString("TKN_VERIFICATOR_KARTU");
        			
        			li.set(brs+"||"+approvee);
        			//if(npmhs.equalsIgnoreCase("2020113100003")) {
        			//	//System.out.println("baris == "+brs+"||"+approvee);
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
    	}
    	   
    	return vGetListMhsYgSdhHeregistrasi;
    }          

    
    public Vector getTotPembayaran(Vector vGetRuleKartuUjian) {
    	Vector v = null;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	String sql = null;
    	if(vGetRuleKartuUjian!=null && vGetRuleKartuUjian.size()>0) {
    		sql = "";
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select AMONTPYMNT FROM PYMNT WHERE KDPSTPYMNT=? AND NPMHSPYMNT=? AND VOIDDPYMNT=? and NPM_APPROVEE IS NOT NULL");
        		li = vGetRuleKartuUjian.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			//li.add(kdpst_+"||"+npmhs_+"||"+tknApr+"||"+tknVer);
        			st = new StringTokenizer(brs,"||");
        			String kdpst = st.nextToken();
        			String npmhs = st.nextToken();
        			String tknApr = st.nextToken();
        			String tknVer = st.nextToken();
        			String tknKartuUjian = st.nextToken();
        			String tknApprKartuUjian = st.nextToken();
        			String tknStatus = st.nextToken();
        			String tknRulesApproveeKartu = st.nextToken();
        			
        			stmt.setString(1, kdpst);
        			stmt.setString(2, npmhs);
        			stmt.setBoolean(3, false);
        			rs = stmt.executeQuery();
        			double tot = 0;
        			while(rs.next()) {
        				double sub = rs.getDouble("AMONTPYMNT");
        				//System.out.println("Rp."+sub);
        				tot = tot+sub;
        			}
        			li.set(brs+"||"+tot);
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
    	   
    	return vGetRuleKartuUjian;
    }        
    
    public Vector getInfoProfileMhs(Vector vGetTotPembayaran) {
    	//Vector v = null;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	String sql = null;
    	if(vGetTotPembayaran!=null && vGetTotPembayaran.size()>0) {
    		sql = "";
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select * from CIVITAS where KDPSTMSMHS=? and NPMHSMSMHS=?");
        		li = vGetTotPembayaran.listIterator();
        		int rut = 0;
        		while(li.hasNext()) {
        			rut++;
        			String brs = (String)li.next();
        			//li.add(kdpst_+"||"+npmhs_+"||"+tknApr+"||"+tknVer+"||"+tot);
        			st = new StringTokenizer(brs,"||");
        			//System.out.println(rut+". "+st.countTokens()+"-"+brs);
        			String kdpst = st.nextToken();
        			String npmhs = st.nextToken();
        			String tknApr = st.nextToken();
        			String tknVer = st.nextToken();
        			String tknKartuUjian = st.nextToken();
        			String tknApprKartuUjian = st.nextToken();
        			String tknStatus = st.nextToken();
        			String tknRulesApproveeKartu = st.nextToken();
        			String tot = st.nextToken();
        			stmt.setString(1, kdpst);
        			stmt.setString(2, npmhs);
        			rs = stmt.executeQuery();
        			if(rs.next()) { // masalah FK waktu mhs dihapus di civiatas tabel tidak dihapus di tabel daftar ulang 
        				String nimhs = ""+rs.getString("NIMHSMSMHS");
            			if(Checker.isStringNullOrEmpty(nimhs)) {
            				nimhs = "null";
            			}
            			String nmmhs = ""+rs.getString("NMMHSMSMHS");
            			if(Checker.isStringNullOrEmpty(nmmhs)) {
            				nmmhs = "null";
            			}
            			String shift = ""+rs.getString("SHIFTMSMHS");
            			if(Checker.isStringNullOrEmpty(shift)) {
            				shift = "null";
            			}
            			String smawl = ""+rs.getString("SMAWLMSMHS");
            			if(Checker.isStringNullOrEmpty(smawl)) {
            				smawl = "null";
            			}
            			String stpid = ""+rs.getString("STPIDMSMHS");
            			if(Checker.isStringNullOrEmpty(stpid)) {
            				stpid = "null";
            			}
            			String gel = ""+rs.getString("GELOMMSMHS");
            			if(Checker.isStringNullOrEmpty(gel)) {
            				gel = "null";
            			}
            			li.set(brs+"||"+nimhs+"||"+nmmhs+"||"+shift+"||"+smawl+"||"+stpid+"||"+gel);
        			}
        			else {
        				li.remove();
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
    	   
    	return vGetTotPembayaran;
    } 
    
    public Vector prosesStatusApprovalKartuUjian(String targetUjian,Vector vGetInfoProfileMhs) {
    	/*
    	 * proses mo opo toh??/
    	 * hanya untuk menentukan status akhir kartu ujian apakah siap dicetak ato menunggu validasi
    	 * 
    	 */
    			
    	
    	StringTokenizer st = null;
    	ListIterator li = null;
    	String sql = null;
    	if(vGetInfoProfileMhs!=null && vGetInfoProfileMhs.size()>0) {

        	li = vGetInfoProfileMhs.listIterator();
        	int io =0;
        	while(li.hasNext()) {
        		io++;
        		String brs = (String)li.next();
        		
        			//li.add(kdpst_+"||"+npmhs_+"||"+tknApr+"||"+tknVer+"||"+tot);
        		st = new StringTokenizer(brs,"||");
        		//if(brs.contains("2020100000120")) {
        		//	//System.out.println("tokens=="+st.countTokens());
        		//}
        		//System.out.println(io+".countTokens = "+st.countTokens());
        		if(st.countTokens()==15) {
        			String kdpst = st.nextToken();
            		String npmhs = st.nextToken();
            		//if(npmhs.equalsIgnoreCase("2020100000120")) {
            		//	//System.out.println(io+".baris = "+brs);
            		//}
            		String tknApr = st.nextToken();
            		String tknVer = st.nextToken();
            		String tknKartuUjian = st.nextToken();
            		String tknApprKartuUjian = st.nextToken();
            		String tknStatus = st.nextToken();
            		String tknRulesApproveeKartu = st.nextToken();
            		
            		String tot = st.nextToken();
            		String nimhs = st.nextToken();
            		String nmmhs = st.nextToken();
            		String shift = st.nextToken();
            		String smawl = st.nextToken();
            		String stpid = st.nextToken();
            		String gel = st.nextToken();
            		//System.out.println("-done-");
            		//cek status apa sudah di approve pa blum
            		//1.cek urutan target ujian lalu sek statusnya
            		//  kalo dah ada di tknKartuUjian berarti harus ada juga di tknApprKartuUjian dan status
            		//String status = ""
            		int norut = 0;
            		String status = "";
            		if(tknKartuUjian!=null && !Checker.isStringNullOrEmpty(tknKartuUjian)) {
            			//System.out.println("-tknKartuUjian-"+tknKartuUjian);
            			//System.out.println("-targetUjian-"+targetUjian);
            			st = new StringTokenizer(tknKartuUjian,",");
                		
                		boolean match = false;
                		while(st.hasMoreTokens() && !match) {
                			norut++;
                			String tmpUjian = st.nextToken();
                			if(tmpUjian.equalsIgnoreCase(targetUjian)) {
                				match = true;
                			}
                		}
                		//System.out.println("-match-"+match);
                		if(match){
                			//get Status
                			/*
                			 * ini adalah STATUS AKHIR
                			 */
                			//System.out.println("-tknStatus-"+tknStatus);
                			//System.out.println("-norut-"+norut);
                			st = new StringTokenizer(tknStatus,"#");
                			int tokens = st.countTokens();
                			if(tokens>0) {
                				if(tokens>=norut) {
                					//sudah ada status sebelumnya
                					for(int k=0;k<norut;k++) {
                						status = st.nextToken();
                					}
                					//kalo null == status menunggu validasi
                					if(Checker.isStringNullOrEmpty(status)) {
                						status = "Menunggu Validasi";
                					}
                				}
                				else {
                					//berarti belum ada statusnya - jadi pasti status akhirnya = menunggu validasi
                					status = "Menunggu Validasi";
                				}
                				
                			}
                			else {
                				status = "Menunggu Validasi";
                			}
                			/*
                			if(st.countTokens()<norut) {
                				status = "Menunggu Validasi";
                			}
                			else if(st.countTokens()==norut) {
                				for(int i=0;i<norut;i++) {
                        			status = st.nextToken();
                        		}
                			}
                			*/
                			//for(int i=0;i<norut;i++) {
                			//	status = st.nextToken();
                			//}
                		}
                		else {
                			//blum ada approval untuk ujian ini
                			status = "Menunggu Validasi";
                		}
            		}
            		else {
            			//blum pernah ada approval untuk ujian apapun
            			status = "Menunggu Validasi";
            		}
            		
            		li.set(brs+"||"+status);
            		//if(npmhs.equalsIgnoreCase("2020100000120")) {
            			//System.out.println("status == "+status);
            			//System.out.println("li.add == "+brs+"||"+status);
            		//}
        		}
        		else {
        			li.remove();;
        			//System.out.println("brs == remove");
        		}
        		
        	}
    	}
    	   
    	return vGetInfoProfileMhs;
    } 
    
    
    public String prepForGetProfileMhs(String npmhs, String kdpst) {
    	//Vector v = null;
    	String needByGetProfile = "";
    	try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    		//	get
    			stmt = con.prepareStatement("select * from CIVITAS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where KDPSTMSMHS=? and NPMHSMSMHS=?");
    			stmt.setString(1, kdpst);
    			stmt.setString(2, npmhs);
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			String nmmhs = ""+rs.getString("NMMHSMSMHS");
        			//String npmhs = rs.getString("NMMHSMSMHS");
            		//String cmd = ""+rs.getString("NMMHSMSMHS");
            		String nimhs = ""+rs.getString("NIMHSMSMHS");
            		String stmhs = ""+rs.getString("STMHSMSMHS");
            		//String kdpst = rs.getString("NMMHSMSMHS");
            		String id_obj = ""+rs.getLong("ID_OBJ");
            		String obj_lvl = ""+rs.getLong("OBJ_LEVEL");
            		needByGetProfile = nmmhs+"||"+nimhs+"||"+stmhs+"||"+id_obj+"||"+obj_lvl;
    				
        		}
        		else {
        			//System.out.println("beans.dbase.mhs.prepForGetProfileMhs(String npmhs, String kdpst)");
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
    	return needByGetProfile;
    }
    
    
    public String prepForGetProfileMhs(String npmhs) {
    	//Vector v = null;
    	String needByGetProfile = "";
    	try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    		//	get
    			stmt = con.prepareStatement("select * from CIVITAS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where NPMHSMSMHS=?");
    			stmt.setString(1, npmhs);
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			String kdpst = rs.getString("KDPSTMSMHS");
        			String nmmhs = ""+rs.getString("NMMHSMSMHS");
        			
        			//String npmhs = rs.getString("NMMHSMSMHS");
            		//String cmd = ""+rs.getString("NMMHSMSMHS");
            		String nimhs = ""+rs.getString("NIMHSMSMHS");
            		String stmhs = ""+rs.getString("STMHSMSMHS");
            		
            		String id_obj = ""+rs.getLong("ID_OBJ");
            		String obj_lvl = ""+rs.getLong("OBJ_LEVEL");
            		needByGetProfile = nmmhs+"||"+nimhs+"||"+stmhs+"||"+id_obj+"||"+obj_lvl+"||"+kdpst;
    				
        		}
        		else {
        			//System.out.println("beans.dbase.mhs.prepForGetProfileMhs(String npmhs, String kdpst)");
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
    	return needByGetProfile;
    }

    public Vector getKrsSmsThsms(String thsms, String kdpst, String npmhs) {
    	Vector v = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get
    		stmt = con.prepareStatement("select KDKMKTRNLM,NAKMKMAKUL from TRNLM inner join MAKUL on IDKMKTRNLM=IDKMKMAKUL where THSMSTRNLM=? and KDPSTTRNLM=? and NPMHSTRNLM=?");
    		stmt.setString(1, thsms);
    		stmt.setString(2, kdpst);
    		stmt.setString(3, npmhs);
        	rs = stmt.executeQuery();
        	v = new Vector();
        	ListIterator li = v.listIterator();
        	while(rs.next()) {
        		String kdkmk = rs.getString("KDKMKTRNLM");
        		String nakmk = rs.getString("NAKMKMAKUL");
        		li.add(kdkmk+"$"+nakmk);
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

    
    
    public String getListMhsTrnlm(String thsms) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	String listThsmsNpmhs = "";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get
    		stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM where THSMSTRNLM=? order by KDPSTTRNLM,NPMHSTRNLM");
    		stmt.setString(1, thsms);
    		rs = stmt.executeQuery();
        	while(rs.next()) {
        		//String kdpst = ""+rs.getString("KDPSTTRNLM");
        		String npmhs = ""+rs.getString("NPMHSTRNLM");
        		//li.add(kdpst);
        		li.add(npmhs);
        	}
        	if(v.size()>0) {
        		stmt = con.prepareStatement("select * from CIVITAS WHERE NPMHSMSMHS=?");
        		li = v.listIterator();
        		while(li.hasNext()) {
        			//String kdpst = (String)li.next();
        			String npmhs = (String)li.next();
        			stmt.setString(1, npmhs);
        			//stmt.setString(2, npmhs);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				listThsmsNpmhs = listThsmsNpmhs+"$";
        				String kdpst = ""+rs.getString("KDPSTMSMHS");
        				String nmmhs = ""+rs.getString("NMMHSMSMHS");
        				if(Checker.isStringNullOrEmpty(nmmhs)) {
        					nmmhs="null";
        				}
        				String nimhs = ""+rs.getString("NIMHSMSMHS");
        				if(Checker.isStringNullOrEmpty(nimhs)) {
        					nimhs="null";
        				}
        				String smawl = ""+rs.getString("SMAWLMSMHS");
        				if(Checker.isStringNullOrEmpty(smawl)) {
        					smawl="null";
        				}
        				String stmhs = ""+rs.getString("STMHSMSMHS");
        				if(Checker.isStringNullOrEmpty(stmhs)) {
        					stmhs="null";
        				}
        				listThsmsNpmhs =listThsmsNpmhs+"$"+kdpst+"$"+npmhs+"$"+nimhs+"$"+nmmhs+"$"+smawl+"$"+stmhs;
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
    	return listThsmsNpmhs;
    }
    
    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * DEPRECATED GANTI YG byObjId
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    public String getListMhsDaftarUlang(String thsms) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	String listThsmsNpmhs = "";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get
    		stmt = con.prepareStatement("select distinct NPMHS from DAFTAR_ULANG where THSMS=? order by KDPST,NPMHS");
    		stmt.setString(1, thsms);
    		rs = stmt.executeQuery();
        	while(rs.next()) {
        		//String kdpst = ""+rs.getString("KDPSTTRNLM");
        		String npmhs = ""+rs.getString("NPMHS");
        		//li.add(kdpst);
        		li.add(npmhs);
        	}
        	if(v.size()>0) {
        		stmt = con.prepareStatement("select * from CIVITAS WHERE NPMHSMSMHS=?");
        		li = v.listIterator();
        		while(li.hasNext()) {
        			//String kdpst = (String)li.next();
        			String npmhs = (String)li.next();
        			stmt.setString(1, npmhs);
        			//stmt.setString(2, npmhs);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				listThsmsNpmhs = listThsmsNpmhs+"$";
        				String kdpst = ""+rs.getString("KDPSTMSMHS");
        				String nmmhs = ""+rs.getString("NMMHSMSMHS");
        				if(Checker.isStringNullOrEmpty(nmmhs)) {
        					nmmhs="null";
        				}
        				String nimhs = ""+rs.getString("NIMHSMSMHS");
        				if(Checker.isStringNullOrEmpty(nimhs)) {
        					nimhs="null";
        				}
        				String smawl = ""+rs.getString("SMAWLMSMHS");
        				if(Checker.isStringNullOrEmpty(smawl)) {
        					smawl="null";
        				}
        				String stmhs = ""+rs.getString("STMHSMSMHS");
        				if(Checker.isStringNullOrEmpty(stmhs)) {
        					stmhs="null";
        				}
        				listThsmsNpmhs =listThsmsNpmhs+"$"+kdpst+"$"+npmhs+"$"+nimhs+"$"+nmmhs+"$"+smawl+"$"+stmhs;
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
    	return listThsmsNpmhs;
    }
    
    
    public String getListMhsDaftarUlang_byObjid(String thsms,Vector vScope_id) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	String listThsmsNpmhs = "";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get
    		stmt = con.prepareStatement("select distinct NPMHS from DAFTAR_ULANG where THSMS=? order by KDPST,NPMHS");
    		stmt.setString(1, thsms);
    		rs = stmt.executeQuery();
        	while(rs.next()) {
        		//String kdpst = ""+rs.getString("KDPSTTRNLM");
        		String npmhs = ""+rs.getString("NPMHS");
        		//li.add(kdpst);
        		li.add(npmhs);
        	}
        	if(v.size()>0) {
        		stmt = con.prepareStatement("select * from CIVITAS WHERE NPMHSMSMHS=?");
        		li = v.listIterator();
        		while(li.hasNext()) {
        			//String kdpst = (String)li.next();
        			String npmhs = (String)li.next();
        			stmt.setString(1, npmhs);
        			//stmt.setString(2, npmhs);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				listThsmsNpmhs = listThsmsNpmhs+"$";
        				String kdpst = ""+rs.getString("KDPSTMSMHS");
        				String nmmhs = ""+rs.getString("NMMHSMSMHS");
        				if(Checker.isStringNullOrEmpty(nmmhs)) {
        					nmmhs="null";
        				}
        				String nimhs = ""+rs.getString("NIMHSMSMHS");
        				if(Checker.isStringNullOrEmpty(nimhs)) {
        					nimhs="null";
        				}
        				String smawl = ""+rs.getString("SMAWLMSMHS");
        				if(Checker.isStringNullOrEmpty(smawl)) {
        					smawl="null";
        				}
        				String stmhs = ""+rs.getString("STMHSMSMHS");
        				if(Checker.isStringNullOrEmpty(stmhs)) {
        					stmhs="null";
        				}
        				listThsmsNpmhs =listThsmsNpmhs+"$"+kdpst+"$"+npmhs+"$"+nimhs+"$"+nmmhs+"$"+smawl+"$"+stmhs;
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
    	return listThsmsNpmhs;
    }
    
    public String getInfoDaftarUlangFilterByScope(String thsms,Vector vFilterScope) {
    	Vector v = new Vector();
    	String finalList="";
    	ListIterator li = v.listIterator();
    	String listThsmsNpmhs = "";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get
    		stmt = con.prepareStatement("select * from DAFTAR_ULANG INNER JOIN CIVITAS on NPMHS=NPMHSMSMHS where THSMS=? order by KDPST,NPMHS");
    		stmt.setString(1,thsms);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String kdpst = rs.getString("KDPST");
    			String npmhs = rs.getString("NPMHS");
    			String nimhs = rs.getString("NIMHSMSMHS");
    			String nmmhs = rs.getString("NMMHSMSMHS");
    			String smawl = rs.getString("SMAWLMSMHS");
    			String stpid = rs.getString("STPIDMSMHS");
    			String tglAju = rs.getString("TGL_PENGAJUAN");
    			String tknApr = rs.getString("TOKEN_APPROVAL");
    			String idObj = ""+rs.getInt("ID_OBJ");
    			li.add(kdpst+"$"+npmhs+"$"+nimhs+"$"+nmmhs+"$"+smawl+"$"+stpid+"$"+tglAju+"$"+tknApr+"$"+idObj);
    		}
    		
    		
    		//filter dengan scope user
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"$");
    			String kdpst=st.nextToken();
    			String npmhs=st.nextToken();
    			String nimhs=st.nextToken();
    			String nmmhs=st.nextToken();
    			String smawl=st.nextToken();
    			String stpid=st.nextToken();
    			String tglAju=st.nextToken();
    			String tknApr=st.nextToken();
    			String idObj=st.nextToken();
    			ListIterator lif = vFilterScope.listIterator();
    			boolean match = false;
    			while(lif.hasNext() && !match) {
    				String brs1 = (String)lif.next();
    				//System.out.println("lif=="+brs1);
    				st = new StringTokenizer(brs1);
    				String id_obj_scope = st.nextToken();
    				/*
    				 * filter diganti dgn obj id ajah
    				 */
    				//String kdpst1=st.nextToken();
    				//if(kdpst.equalsIgnoreCase(kdpst1)) {
    				
    				if(idObj.equalsIgnoreCase(id_obj_scope)) {
    					match = true;
    					//System.out.println(idObj+" <> "+id_obj_scope);
    				}
    			}
    			if(!match) {
    				li.remove();
    			}
    		}
    		
    		boolean first = true;
    		stmt=con.prepareStatement("select * from DAFTAR_ULANG_RULES where THSMS=? and KDPST=?");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			//System.out.println("bss="+brs);
    			StringTokenizer st = new StringTokenizer(brs,"$");
    			String kdpst=st.nextToken();
    			String npmhs=st.nextToken();
    			String nimhs=st.nextToken();
    			String nmmhs=st.nextToken();
    			String smawl=st.nextToken();
    			String stpid=st.nextToken();
    			String tglAju=st.nextToken();
    			String tknApr=st.nextToken();
    			String idObj=st.nextToken();
    			stmt.setString(1,thsms);
    			stmt.setString(2,kdpst);
    			
    			
    			rs = stmt.executeQuery();
    			rs.next();//kalo error berarti tabelnya belum diinput utk thsms terkait
    			String tknVerObj = rs.getString("TKN_VERIFICATOR");
    			String urutan = ""+rs.getBoolean("URUTAN");
    			if(first) {
    				first = false;
    				finalList = brs+"$"+tknVerObj+"$"+urutan;
    			}
    			else {
    				finalList = finalList+brs+"$"+tknVerObj+"$"+urutan;
    			}
    			if(li.hasNext()) {
    				finalList=finalList+"$";
    			}
    			//li.set(brs+"$"+tknVerObj+"$"+urutan);
    		}
    		stmt = con.prepareStatement("select NMPSTMSPST from MSPST where KDPSTMSPST=?");
    		String nuFinalList = "";
    		first = true;
    		StringTokenizer st  = new StringTokenizer(finalList,"$");
    		while(st.hasMoreTokens()) {
    			String kdpst=st.nextToken();
    			String npmhs=st.nextToken();
    			String nimhs=st.nextToken();
    			String nmmhs=st.nextToken();
    			String smawl=st.nextToken();
    			String stpid=st.nextToken();
    			String tglAju=st.nextToken();
    			String tknApr=st.nextToken();
    			String idObj=st.nextToken();
    			String tknVerObj=st.nextToken();
    			String urutan=st.nextToken();
    			stmt.setString(1,kdpst);
    			rs=stmt.executeQuery();
    			rs.next();
    			String nmpst = rs.getString("NMPSTMSPST");
    			if(first) {
    				first = false;
    				nuFinalList = nmpst+"$"+kdpst+"$"+npmhs+"$"+nimhs+"$"+nmmhs+"$"+smawl+"$"+stpid+"$"+tglAju+"$"+tknApr+"$"+tknVerObj+"$"+urutan+"$"+idObj;
    			}
    			else {
    				nuFinalList = nuFinalList+nmpst+"$"+kdpst+"$"+npmhs+"$"+nimhs+"$"+nmmhs+"$"+smawl+"$"+stpid+"$"+tglAju+"$"+tknApr+"$"+tknVerObj+"$"+urutan+"$"+idObj;
    			}
    			if(st.hasMoreTokens()) {
    				nuFinalList=nuFinalList+"$";
    			}
    		}
    		finalList=nuFinalList;
    		
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
    	return finalList;
    }    
    
    public String getListTipeObj() {
    	//Vector v = null;
    	String tokn = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get
    		stmt = con.prepareStatement("select * from OBJECT order by OBJ_DESC");
    		
    		boolean first = true;
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			if(first) {
    				first = false;
    				tokn = "";
    			}
    			else {
    				tokn = tokn+"$";
    			}
    			String idObj = ""+rs.getLong("ID_OBJ");
    			if(Checker.isStringNullOrEmpty(idObj)) {
    				idObj = "null";
    			}
    			String kdpst = ""+rs.getString("KDPST");
    			if(Checker.isStringNullOrEmpty(kdpst)) {
    				kdpst = "null";
    			}
				String objName = ""+rs.getString("OBJ_NAME");
				if(Checker.isStringNullOrEmpty(objName)) {
					objName = "null";
    			}
				String objDesc = ""+rs.getString("OBJ_DESC");
				if(Checker.isStringNullOrEmpty(objDesc)) {
					objDesc = "null";
    			}
				String objLvl = ""+rs.getString("OBJ_LEVEL");
				if(Checker.isStringNullOrEmpty(objLvl)) {
					objLvl = "null";
    			}
				String acl = ""+rs.getString("ACCESS_LEVEL_CONDITIONAL");
				if(Checker.isStringNullOrEmpty(acl)) {
    				acl = "null";
    			}
				String al = ""+rs.getString("ACCESS_LEVEL");
				if(Checker.isStringNullOrEmpty(al)) {
    				al = "null";
    			}
				String dvalue = ""+rs.getString("DEFAULT_VALUE");
				if(Checker.isStringNullOrEmpty(dvalue)) {
					dvalue = "null";
    			}
				String nicknm = ""+rs.getString("OBJ_NICKNAME");
				if(Checker.isStringNullOrEmpty(nicknm)) {
					nicknm = "null";
    			}
				
				
    			tokn = tokn + idObj+"$"+kdpst+"$"+objName+"$"+objDesc+"$"+objLvl+"$"+acl+"$"+al+"$"+dvalue+"$"+nicknm;
	
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
    	return tokn;
    }
    
    public String getInfoPA(String kdpst,String npmhs) {
    	//Vector v = null;
    	String tokn = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get
    		stmt = con.prepareStatement("select NPM_PA,NMM_PA from EXT_CIVITAS where KDPSTMSMHS=? and NPMHSMSMHS=?");
    		stmt.setString(1,kdpst);
    		stmt.setString(2, npmhs);
    		rs = stmt.executeQuery();
    		boolean first = true;
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String npmPa = ""+rs.getString("NPM_PA");
    			String nmmPa = ""+rs.getString("NMM_PA");
    			tokn = npmPa+"|"+nmmPa;
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
    	return tokn;
    }
    
    public String getInfoKrklm(String kdpst,String npmhs) {
    	//Vector v = null;
    	String krklm = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get
    		stmt = con.prepareStatement("select KRKLMMSMHS from EXT_CIVITAS where KDPSTMSMHS=? and NPMHSMSMHS=?");
    		stmt.setString(1,kdpst);
    		stmt.setString(2, npmhs);
    		rs = stmt.executeQuery();
    		boolean first = true;
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			krklm = ""+rs.getInt("KRKLMMSMHS");
    			if(krklm==null || Checker.isStringNullOrEmpty(krklm) || krklm.equalsIgnoreCase("0")) {
    				krklm = "null";
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
    	return krklm; //return 0 kalo blum ditentukan
    }
    
    public Vector getStatusMhsPerThsms(Vector vThsms, String npmhs) {
    	//Vector v = null;
    	ListIterator li = vThsms.listIterator();
    	try {
    		
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select KDKMKTRNLM from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=? limit 1");
			while(li.hasNext()) {
				String thsms=(String)li.next();
				stmt.setString(1, thsms);
				stmt.setString(2, npmhs);
				rs = stmt.executeQuery();
				if(rs.next()) {
					li.set(thsms+"`A");
				}
				else {
					li.set(thsms+"`null");
				}
			}
			li = vThsms.listIterator();
			StringTokenizer st = null;
			stmt = con.prepareStatement("select * from TRLSM where THSMS=? and NPMHS=?");
					
			while(li.hasNext()) {
				String brs = (String)li.next();
				if(brs.contains("null")||brs.contains("NULL")) {
					st = new StringTokenizer(brs,"`");
					String thsms = st.nextToken();
					stmt.setString(1, thsms);
					stmt.setString(2, npmhs);
					rs = stmt.executeQuery();
					if(rs.next()) {
						String stmhs = ""+rs.getString("STMHS");
						if(!Checker.isStringNullOrEmpty(stmhs)) {
							li.set(thsms+'`'+stmhs);
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
    	return vThsms;
    }
    
}
