package beans.dbase.makul;

import beans.dbase.SearchDb;
import beans.tools.Checker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import beans.tools.*;
import org.apache.tomcat.jdbc.pool.DataSource;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ListIterator;
import java.util.Collections;
/**
 * Session Bean implementation class SearchDbMk
 */
@Stateless
@LocalBean
public class SearchDbMk extends SearchDb {
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
    public SearchDbMk() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbMk(String operatorNpm) {
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
    public SearchDbMk(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }

/*    
    public Vector getPenggabunganKelasRules(String thsms,Vector vScope) {
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	Vector vGroup = new Vector();
    	ListIterator lig = vGroup.listIterator();
    	Vector vNon = new Vector();
    	ListIterator lin = vNon.listIterator();
    	if(vScope!=null && vScope.size()>0) {
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
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
    	return vf;
    }
*/    
    /*
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * DEPRICATED use VER1
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    public Vector getListKelasAtCp(String thsms,Vector vScope) {
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	Vector vGroup = new Vector();
    	ListIterator lig = vGroup.listIterator();
    	Vector vNon = new Vector();
    	ListIterator lin = vNon.listIterator();
    	if(vScope!=null && vScope.size()>0) {
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			ListIterator lisc = vScope.listIterator();
    			String listKdpst="";
    			boolean first = true;
    			while(lisc.hasNext()) {
    				String brs = (String)lisc.next();
    				StringTokenizer st = new StringTokenizer(brs);
    				String idObj = st.nextToken();
    				String kdpst = st.nextToken();
    				String nmobj = st.nextToken();
    				String level = st.nextToken();
    				String kdjen = st.nextToken();
    				if(first) {
    					first =false;
    					listKdpst = "KDPST='"+kdpst+"'";
    				}
    				else {
    					listKdpst = listKdpst+"KDPST='"+kdpst+"'";
    				}
    				if(lisc.hasNext()) {
    					listKdpst=listKdpst+" or ";
    				}
    			}
    			//stmt = con.prepareStatement("select * from CLASS_POOL where THSMS=? and ("+listKdpst+") order by KDPST,IDKMK,SHIFT,NORUT_KELAS_PARALEL");
    			stmt = con.prepareStatement("select * from CLASS_POOL inner join KRKLM on IDKUR=IDKURKRKLM where THSMS=? and ("+listKdpst+") order by KDPST,IDKMK,SHIFT,NORUT_KELAS_PARALEL");
    			stmt.setString(1,thsms);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String idkur1 = ""+rs.getLong("IDKUR");
    				if(Checker.isStringNullOrEmpty(idkur1)) {
    					idkur1 = "null";
    				}
    				String idkmk1 = ""+rs.getLong("IDKMK");
    				if(Checker.isStringNullOrEmpty(idkmk1)) {
    					idkmk1 = "null";
    				}
    				String thsms1 = ""+rs.getString("THSMS");
    				if(Checker.isStringNullOrEmpty(thsms1)) {
    					thsms1 = "null";
    				}
    				String kdpst1 = ""+rs.getString("KDPST");
    				if(Checker.isStringNullOrEmpty(kdpst1)) {
    					kdpst1 = "null";
    				}
    				String shift1 = ""+rs.getString("SHIFT");
    				if(Checker.isStringNullOrEmpty(shift1)) {
    					shift1 = "null";
    				}
    				String norutKlsPll1 = ""+rs.getInt("NORUT_KELAS_PARALEL");
    				if(Checker.isStringNullOrEmpty(norutKlsPll1)) {
    					norutKlsPll1 = "null";
    				}
    				String initNpmInput1 = ""+rs.getString("INIT_NPM_INPUT");
    				if(Checker.isStringNullOrEmpty(initNpmInput1)) {
    					initNpmInput1 = "null";
    				}
    				String latestNpmUpdate1 = ""+rs.getString("LATEST_NPM_UPDATE");
    				if(Checker.isStringNullOrEmpty(latestNpmUpdate1)) {
    					latestNpmUpdate1 = "null";
    				}
    				String latesStatusInfo1 = ""+rs.getString("LATEST_STATUS_INFO");
    				if(Checker.isStringNullOrEmpty(latesStatusInfo1)) {
    					latesStatusInfo1 = "null";
    				}
    				String currAvailStatus1 = ""+rs.getString("CURR_AVAIL_STATUS");
    				if(Checker.isStringNullOrEmpty(currAvailStatus1)) {
    					currAvailStatus1 = "null";
    				}
    				String locked1 = ""+rs.getBoolean("LOCKED");
    				if(Checker.isStringNullOrEmpty(locked1)) {
    					locked1 = "null";
    				}
    				String npmdos1 = ""+rs.getString("NPMDOS");
    				if(Checker.isStringNullOrEmpty(npmdos1)) {
    					npmdos1 = "null";
    				}
    				String nodos1 = ""+rs.getString("NODOS");
    				if(Checker.isStringNullOrEmpty(nodos1)) {
    					nodos1 = "null";
    				}
    				String npmasdos1 = ""+rs.getString("NPMASDOS");
    				if(Checker.isStringNullOrEmpty(npmasdos1)) {
    					npmasdos1 = "null";
    				}
    				String noasdos1 = ""+rs.getString("NOASDOS");
    				if(Checker.isStringNullOrEmpty(noasdos1)) {
    					noasdos1 = "null";
    				}
    				String canceled1 = ""+rs.getBoolean("CANCELED");
    				if(Checker.isStringNullOrEmpty(canceled1)) {
    					canceled1 = "null";
    				}
    				String kodeKelas1 = ""+rs.getString("KODE_KELAS");
    				if(Checker.isStringNullOrEmpty(kodeKelas1)) {
    					kodeKelas1 = "null";
    				}
    				String kodeRuang1 = ""+rs.getString("KODE_RUANG");
    				if(Checker.isStringNullOrEmpty(kodeRuang1)) {
    					kodeRuang1 = "null";
    				}
    				String kodeGedung1 = ""+rs.getString("KODE_GEDUNG");
    				if(Checker.isStringNullOrEmpty(kodeGedung1)) {
    					kodeGedung1 = "null";
    				}
    				String kodeKampus1 = ""+rs.getString("KODE_KAMPUS");
    				if(Checker.isStringNullOrEmpty(kodeKampus1)) {
    					kodeKampus1 = "null";
    				}
    				String tknHrTime1 = ""+rs.getString("TKN_HARI_TIME");
    				if(Checker.isStringNullOrEmpty(tknHrTime1)) {
    					tknHrTime1 = "null";
    				}
    				String nmdos1 = ""+rs.getString("NMMDOS");
    				if(Checker.isStringNullOrEmpty(nmdos1)) {
    					nmdos1 = "null";
    				}
    				String nmasdos1 = ""+rs.getString("NMMASDOS");
    				if(Checker.isStringNullOrEmpty(nmasdos1)) {
    					nmasdos1 = "null";
    				}
    				String enrolled1 = ""+rs.getInt("ENROLLED");
    				if(Checker.isStringNullOrEmpty(enrolled1)) {
    					enrolled1 = "null";
    				}
    				String maxEnrolled1 = ""+rs.getInt("MAX_ENROLLED");
    				if(Checker.isStringNullOrEmpty(maxEnrolled1)) {
    					maxEnrolled1 = "null";
    				}
    				String minEnrolled1 = ""+rs.getInt("MIN_ENROLLED");
    				if(Checker.isStringNullOrEmpty(minEnrolled1)) {
    					minEnrolled1 = "null";
    				}
    				String subKeterKdkmk1 = ""+rs.getString("SUB_KETER_KDKMK");
    				if(Checker.isStringNullOrEmpty(subKeterKdkmk1)) {
    					subKeterKdkmk1 = "null";
    				}
    				String initReqTime1 = ""+rs.getTimestamp("INIT_REQ_TIME");
    				if(Checker.isStringNullOrEmpty(initReqTime1)) {
    					initReqTime1 = "null";
    				}
    				String tknNpmApr1 = ""+rs.getString("TKN_NPM_APPROVAL");
    				if(Checker.isStringNullOrEmpty(tknNpmApr1)) {
    					tknNpmApr1 = "null";
    				}
    				String tknAprTime1 = ""+rs.getString("TKN_APPROVAL_TIME");
    				if(Checker.isStringNullOrEmpty(tknAprTime1)) {
    					tknAprTime1 = "null";
    				}
    				String targetTtmhs1 = ""+rs.getInt("TARGET_TTMHS");
    				if(Checker.isStringNullOrEmpty(targetTtmhs1)) {
    					targetTtmhs1 = "null";
    				}
    				String passed1 = ""+rs.getBoolean("PASSED");
    				if(Checker.isStringNullOrEmpty(passed1)) {
    					passed1 = "null";
    				}
    				String rejected1 = ""+rs.getBoolean("REJECTED");
    				if(Checker.isStringNullOrEmpty(rejected1)) {
    					rejected1 = "null";
    				}
    				String konsen1 = ""+rs.getString("KONSENTRASI");
    				if(Checker.isStringNullOrEmpty(konsen1)) {
    					konsen1 = "null";
    				}
    				
    				
    				String kodeGabungan = ""+rs.getString("KODE_PENGGABUNGAN");
    				if(Checker.isStringNullOrEmpty(kodeGabungan)) {
    					kodeGabungan = "null";
    				}
    				if(kodeGabungan.equalsIgnoreCase("null")) {
    					//lin.add(idkur1+"$"+idkmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+kodeGabungan);
    					lin.add(kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+konsen1);
    				}
    				else {
    					lig.add(kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+konsen1);
    				}
    				//lif.add(idkur1+"$"+idkmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+kodeGabungan);
    			}
    			Collections.sort(vNon);
    			Collections.sort(vGroup);
    			stmt = con.prepareStatement("select * from MAKUL inner join MSPST on KDPSTMAKUL=KDPSTMSPST where IDKMKMAKUL=?");
    			lig = vGroup.listIterator();
    			while(lig.hasNext()){
    				String brs = (String)lig.next();
    				StringTokenizer st = new StringTokenizer(brs,"$");
    				String kodeGabungan=st.nextToken();
    				String idkmk1=st.nextToken();
    				String idkur1=st.nextToken();
    				String thsms1=st.nextToken();
    				String kdpst1=st.nextToken();
    				String shift1=st.nextToken();
    				String norutKlsPll1=st.nextToken();
    				String initNpmInput1=st.nextToken();
    				String latestNpmUpdate1=st.nextToken();
    				String latesStatusInfo1=st.nextToken();
    				String currAvailStatus1=st.nextToken();
    				String locked1=st.nextToken();
    				String npmdos1=st.nextToken();
    				String nodos1=st.nextToken();
    				String npmasdos1=st.nextToken();
    				String noasdos1=st.nextToken();
    				String canceled1=st.nextToken();
    				String kodeKelas1=st.nextToken();
    				String kodeRuang1=st.nextToken();
    				String kodeGedung1=st.nextToken();
    				String kodeKampus1=st.nextToken();
    				String tknHrTime1=st.nextToken();
    				String nmdos1=st.nextToken();
    				String nmasdos1=st.nextToken();
    				String enrolled1=st.nextToken();
    				String maxEnrolled1=st.nextToken();
    				String minEnrolled1=st.nextToken();
    				String subKeterKdkmk1=st.nextToken();
    				String initReqTime1=st.nextToken();
    				String tknNpmApr1=st.nextToken();
    				String tknAprTime1=st.nextToken();
    				String targetTtmhs1=st.nextToken();
    				String passed1=st.nextToken();
    				String rejected1=st.nextToken();
    				String konsen1=st.nextToken();
    				stmt.setLong(1, Long.valueOf(idkmk1).longValue());
    				rs = stmt.executeQuery();
    				rs.next();
    				String kdkmk1 = ""+rs.getString("KDKMKMAKUL");
    				String nakmk1 = rs.getString("NAKMKMAKUL");
    				//nakmk1=nakmk1.replace("+", "tandaTambah");
    				
    				String nmpst1 = rs.getString("NMPSTMSPST");
    				if(Checker.isStringNullOrEmpty(nakmk1)) {
    					nakmk1="null";
    				}
    				lig.set(kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+kdkmk1+"$"+nakmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+konsen1+"$"+nmpst1);
    				//lif.set(nakmk+"$"+brs);
    			}
    			
    			/*====sorting group====
    			lig = vGroup.listIterator();
    			while(lig.hasNext()){
    				String brs = (String)lig.next();
    				StringTokenizer st = new StringTokenizer(brs,"$");
    				String kodeGabungan=st.nextToken();
    				String idkmk1=st.nextToken();
    				String idkur1=st.nextToken();
    				String kdkmk1=st.nextToken();
    				String nakmk1=st.nextToken();
    				String thsms1=st.nextToken();
    				String kdpst1=st.nextToken();
    				String shift1=st.nextToken();
    				String norutKlsPll1=st.nextToken();
    				String initNpmInput1=st.nextToken();
    				String latestNpmUpdate1=st.nextToken();
    				String latesStatusInfo1=st.nextToken();
    				String currAvailStatus1=st.nextToken();
    				String locked1=st.nextToken();
    				String npmdos1=st.nextToken();
    				String nodos1=st.nextToken();
    				String npmasdos1=st.nextToken();
    				String noasdos1=st.nextToken();
    				String canceled1=st.nextToken();
    				String kodeKelas1=st.nextToken();
    				String kodeRuang1=st.nextToken();
    				String kodeGedung1=st.nextToken();
    				String kodeKampus1=st.nextToken();
    				String tknHrTime1=st.nextToken();
    				String nmdos1=st.nextToken();
    				String nmasdos1=st.nextToken();
    				String enrolled1=st.nextToken();
    				String maxEnrolled1=st.nextToken();
    				String minEnrolled1=st.nextToken();
    				String subKeterKdkmk1=st.nextToken();
    				String initReqTime1=st.nextToken();
    				String tknNpmApr1=st.nextToken();
    				String tknAprTime1=st.nextToken();
    				String targetTtmhs1=st.nextToken();
    				String passed1=st.nextToken();
    				String rejected1=st.nextToken();
    				String nmpst1=st.nextToken();
    				//lig.set(kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+kdkmk1+"$"+nakmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+nmpst1);
    				lig.set(kdpst1+"$"+nakmk1+"$"+kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+kdkmk1+"$"+thsms1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+nmpst1);
    				//lif.set(nakmk+"$"+brs);
    			}
    			Collections.sort(vGroup);
    			lig = vGroup.listIterator();
    			while(lig.hasNext()){
    				String brs = (String)lig.next();
    				StringTokenizer st = new StringTokenizer(brs,"$");
    				String kdpst1=st.nextToken();
    				String nakmk1=st.nextToken();
    				String kodeGabungan=st.nextToken();
    				String idkmk1=st.nextToken();
    				String idkur1=st.nextToken();
    				String kdkmk1=st.nextToken();
    				//String nakmk1=st.nextToken();
    				String thsms1=st.nextToken();
    				
    				String shift1=st.nextToken();
    				String norutKlsPll1=st.nextToken();
    				String initNpmInput1=st.nextToken();
    				String latestNpmUpdate1=st.nextToken();
    				String latesStatusInfo1=st.nextToken();
    				String currAvailStatus1=st.nextToken();
    				String locked1=st.nextToken();
    				String npmdos1=st.nextToken();
    				String nodos1=st.nextToken();
    				String npmasdos1=st.nextToken();
    				String noasdos1=st.nextToken();
    				String canceled1=st.nextToken();
    				String kodeKelas1=st.nextToken();
    				String kodeRuang1=st.nextToken();
    				String kodeGedung1=st.nextToken();
    				String kodeKampus1=st.nextToken();
    				String tknHrTime1=st.nextToken();
    				String nmdos1=st.nextToken();
    				String nmasdos1=st.nextToken();
    				String enrolled1=st.nextToken();
    				String maxEnrolled1=st.nextToken();
    				String minEnrolled1=st.nextToken();
    				String subKeterKdkmk1=st.nextToken();
    				String initReqTime1=st.nextToken();
    				String tknNpmApr1=st.nextToken();
    				String tknAprTime1=st.nextToken();
    				String targetTtmhs1=st.nextToken();
    				String passed1=st.nextToken();
    				String rejected1=st.nextToken();
    				String nmpst1=st.nextToken();
    				lig.set(kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+kdkmk1+"$"+nakmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+nmpst1);
    				//lig.set(kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+kdkmk1+"$"+nakmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+nmpst1);
    				//lif.set(nakmk+"$"+brs);
    			}
    			//====end sorting group====*/
    			
    			
    			lin = vNon.listIterator();
    			while(lin.hasNext()){
    				String brs = (String)lin.next();
    				StringTokenizer st = new StringTokenizer(brs,"$");
    				String kodeGabungan=st.nextToken();
    				String idkmk1=st.nextToken();
    				String idkur1=st.nextToken();
    				String thsms1=st.nextToken();
    				String kdpst1=st.nextToken();
    				String shift1=st.nextToken();
    				String norutKlsPll1=st.nextToken();
    				String initNpmInput1=st.nextToken();
    				String latestNpmUpdate1=st.nextToken();
    				String latesStatusInfo1=st.nextToken();
    				String currAvailStatus1=st.nextToken();
    				String locked1=st.nextToken();
    				String npmdos1=st.nextToken();
    				String nodos1=st.nextToken();
    				String npmasdos1=st.nextToken();
    				String noasdos1=st.nextToken();
    				String canceled1=st.nextToken();
    				String kodeKelas1=st.nextToken();
    				String kodeRuang1=st.nextToken();
    				String kodeGedung1=st.nextToken();
    				String kodeKampus1=st.nextToken();
    				String tknHrTime1=st.nextToken();
    				String nmdos1=st.nextToken();
    				String nmasdos1=st.nextToken();
    				String enrolled1=st.nextToken();
    				String maxEnrolled1=st.nextToken();
    				String minEnrolled1=st.nextToken();
    				String subKeterKdkmk1=st.nextToken();
    				String initReqTime1=st.nextToken();
    				String tknNpmApr1=st.nextToken();
    				String tknAprTime1=st.nextToken();
    				String targetTtmhs1=st.nextToken();
    				String passed1=st.nextToken();
    				String rejected1=st.nextToken();
    				String konsen1=st.nextToken();
    				stmt.setLong(1, Long.valueOf(idkmk1).longValue());
    				rs = stmt.executeQuery();
    				rs.next();
    				String kdkmk1 = ""+rs.getString("KDKMKMAKUL");
    				String nakmk1 = ""+rs.getString("NAKMKMAKUL");
    				//nakmk1=nakmk1.replace("+", "tandaTambah");
    				String nmpst1 = rs.getString("NMPSTMSPST");
    				if(Checker.isStringNullOrEmpty(nakmk1)) {
    					nakmk1="null";
    				}
    				
    				lin.set(kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+kdkmk1+"$"+nakmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+konsen1+"$"+nmpst1);
    				//lif.set(nakmk+"$"+brs);
    			}
    			
    			//sorting vNon based on nakmk
    			lin = vNon.listIterator();
    			while(lin.hasNext()){
    				String brs = (String)lin.next();
    				StringTokenizer st = new StringTokenizer(brs,"$");
    				String kodeGabungan=st.nextToken();
    				String idkmk1=st.nextToken();
    				String idkur1=st.nextToken();
    				String kdkmk1=st.nextToken();
    				String nakmk1=st.nextToken();
    				String thsms1=st.nextToken();
    				String kdpst1=st.nextToken();
    				String shift1=st.nextToken();
    				String norutKlsPll1=st.nextToken();
    				String initNpmInput1=st.nextToken();
    				String latestNpmUpdate1=st.nextToken();
    				String latesStatusInfo1=st.nextToken();
    				String currAvailStatus1=st.nextToken();
    				String locked1=st.nextToken();
    				String npmdos1=st.nextToken();
    				String nodos1=st.nextToken();
    				String npmasdos1=st.nextToken();
    				String noasdos1=st.nextToken();
    				String canceled1=st.nextToken();
    				String kodeKelas1=st.nextToken();
    				String kodeRuang1=st.nextToken();
    				String kodeGedung1=st.nextToken();
    				String kodeKampus1=st.nextToken();
    				String tknHrTime1=st.nextToken();
    				String nmdos1=st.nextToken();
    				String nmasdos1=st.nextToken();
    				String enrolled1=st.nextToken();
    				String maxEnrolled1=st.nextToken();
    				String minEnrolled1=st.nextToken();
    				String subKeterKdkmk1=st.nextToken();
    				String initReqTime1=st.nextToken();
    				String tknNpmApr1=st.nextToken();
    				String tknAprTime1=st.nextToken();
    				String targetTtmhs1=st.nextToken();
    				String passed1=st.nextToken();
    				String rejected1=st.nextToken();
    				String konsen1=st.nextToken();
    				String nmpst1=st.nextToken();
    				lin.set(nakmk1+"$"+shift1+"$"+kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+kdkmk1+"$"+thsms1+"$"+kdpst1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+konsen1+"$"+nmpst1);
    				//lif.set(nakmk+"$"+brs);
    			}
    			Collections.sort(vNon);
    			lin = vNon.listIterator();
    			while(lin.hasNext()){
    				String brs = (String)lin.next();
    				StringTokenizer st = new StringTokenizer(brs,"$");
    				String nakmk1=st.nextToken();
    				String shift1=st.nextToken();
    				String kodeGabungan=st.nextToken();
    				String idkmk1=st.nextToken();
    				String idkur1=st.nextToken();
    				String kdkmk1=st.nextToken();
    				//String nakmk1=st.nextToken();
    				String thsms1=st.nextToken();
    				String kdpst1=st.nextToken();
    				
    				String norutKlsPll1=st.nextToken();
    				String initNpmInput1=st.nextToken();
    				String latestNpmUpdate1=st.nextToken();
    				String latesStatusInfo1=st.nextToken();
    				String currAvailStatus1=st.nextToken();
    				String locked1=st.nextToken();
    				String npmdos1=st.nextToken();
    				String nodos1=st.nextToken();
    				String npmasdos1=st.nextToken();
    				String noasdos1=st.nextToken();
    				String canceled1=st.nextToken();
    				String kodeKelas1=st.nextToken();
    				String kodeRuang1=st.nextToken();
    				String kodeGedung1=st.nextToken();
    				String kodeKampus1=st.nextToken();
    				String tknHrTime1=st.nextToken();
    				String nmdos1=st.nextToken();
    				String nmasdos1=st.nextToken();
    				String enrolled1=st.nextToken();
    				String maxEnrolled1=st.nextToken();
    				String minEnrolled1=st.nextToken();
    				String subKeterKdkmk1=st.nextToken();
    				String initReqTime1=st.nextToken();
    				String tknNpmApr1=st.nextToken();
    				String tknAprTime1=st.nextToken();
    				String targetTtmhs1=st.nextToken();
    				String passed1=st.nextToken();
    				String rejected1=st.nextToken();
    				String konsen1=st.nextToken();
    				String nmpst1=st.nextToken();
    				lin.set(kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+kdkmk1+"$"+nakmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+konsen1+"$"+nmpst1);
    				//lif.set(nakmk+"$"+brs);
    			}
    			//end sorting
    			
    			lif.add(vNon);
    			lif.add(vGroup);
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
    	return vf;
    }
    
    public Vector getListKelasAtCp_v1(String thsms,Vector vScope) {
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	Vector vGroup = new Vector();
    	ListIterator lig = vGroup.listIterator();
    	Vector vNon = new Vector();
    	ListIterator lin = vNon.listIterator();
    	if(vScope!=null && vScope.size()>0) {
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			ListIterator lisc = vScope.listIterator();
    			String listKdpst="";
    			boolean first = true;
    			while(lisc.hasNext()) {
    				String brs = (String)lisc.next();
    				StringTokenizer st = new StringTokenizer(brs);
    				String idObj = st.nextToken();
    				String kdpst = st.nextToken();
    				String nmobj = st.nextToken();
    				String level = st.nextToken();
    				String kdjen = st.nextToken();
    				if(first) {
    					first =false;
    					listKdpst = "KDPST='"+kdpst+"'";
    				}
    				else {
    					listKdpst = listKdpst+"KDPST='"+kdpst+"'";
    				}
    				if(lisc.hasNext()) {
    					listKdpst=listKdpst+" or ";
    				}
    			}
    			//stmt = con.prepareStatement("select * from CLASS_POOL where THSMS=? and ("+listKdpst+") order by KDPST,IDKMK,SHIFT,NORUT_KELAS_PARALEL");
    			stmt = con.prepareStatement("select * from CLASS_POOL inner join KRKLM on IDKUR=IDKURKRKLM where THSMS=? and ("+listKdpst+") order by KDPST,IDKMK,SHIFT,NORUT_KELAS_PARALEL");
    			stmt.setString(1,thsms);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String idkur1 = ""+rs.getLong("IDKUR");
    				if(Checker.isStringNullOrEmpty(idkur1)) {
    					idkur1 = "null";
    				}
    				String idkmk1 = ""+rs.getLong("IDKMK");
    				if(Checker.isStringNullOrEmpty(idkmk1)) {
    					idkmk1 = "null";
    				}
    				String thsms1 = ""+rs.getString("THSMS");
    				if(Checker.isStringNullOrEmpty(thsms1)) {
    					thsms1 = "null";
    				}
    				String kdpst1 = ""+rs.getString("KDPST");
    				if(Checker.isStringNullOrEmpty(kdpst1)) {
    					kdpst1 = "null";
    				}
    				String shift1 = ""+rs.getString("SHIFT");
    				if(Checker.isStringNullOrEmpty(shift1)) {
    					shift1 = "null";
    				}
    				String norutKlsPll1 = ""+rs.getInt("NORUT_KELAS_PARALEL");
    				if(Checker.isStringNullOrEmpty(norutKlsPll1)) {
    					norutKlsPll1 = "null";
    				}
    				String initNpmInput1 = ""+rs.getString("INIT_NPM_INPUT");
    				if(Checker.isStringNullOrEmpty(initNpmInput1)) {
    					initNpmInput1 = "null";
    				}
    				String latestNpmUpdate1 = ""+rs.getString("LATEST_NPM_UPDATE");
    				if(Checker.isStringNullOrEmpty(latestNpmUpdate1)) {
    					latestNpmUpdate1 = "null";
    				}
    				String latesStatusInfo1 = ""+rs.getString("LATEST_STATUS_INFO");
    				if(Checker.isStringNullOrEmpty(latesStatusInfo1)) {
    					latesStatusInfo1 = "null";
    				}
    				String currAvailStatus1 = ""+rs.getString("CURR_AVAIL_STATUS");
    				if(Checker.isStringNullOrEmpty(currAvailStatus1)) {
    					currAvailStatus1 = "null";
    				}
    				String locked1 = ""+rs.getBoolean("LOCKED");
    				if(Checker.isStringNullOrEmpty(locked1)) {
    					locked1 = "null";
    				}
    				String npmdos1 = ""+rs.getString("NPMDOS");
    				if(Checker.isStringNullOrEmpty(npmdos1)) {
    					npmdos1 = "null";
    				}
    				String nodos1 = ""+rs.getString("NODOS");
    				if(Checker.isStringNullOrEmpty(nodos1)) {
    					nodos1 = "null";
    				}
    				String npmasdos1 = ""+rs.getString("NPMASDOS");
    				if(Checker.isStringNullOrEmpty(npmasdos1)) {
    					npmasdos1 = "null";
    				}
    				String noasdos1 = ""+rs.getString("NOASDOS");
    				if(Checker.isStringNullOrEmpty(noasdos1)) {
    					noasdos1 = "null";
    				}
    				String canceled1 = ""+rs.getBoolean("CANCELED");
    				if(Checker.isStringNullOrEmpty(canceled1)) {
    					canceled1 = "null";
    				}
    				String kodeKelas1 = ""+rs.getString("KODE_KELAS");
    				if(Checker.isStringNullOrEmpty(kodeKelas1)) {
    					kodeKelas1 = "null";
    				}
    				String kodeRuang1 = ""+rs.getString("KODE_RUANG");
    				if(Checker.isStringNullOrEmpty(kodeRuang1)) {
    					kodeRuang1 = "null";
    				}
    				String kodeGedung1 = ""+rs.getString("KODE_GEDUNG");
    				if(Checker.isStringNullOrEmpty(kodeGedung1)) {
    					kodeGedung1 = "null";
    				}
    				String kodeKampus1 = ""+rs.getString("KODE_KAMPUS");
    				if(Checker.isStringNullOrEmpty(kodeKampus1)) {
    					kodeKampus1 = "null";
    				}
    				String tknHrTime1 = ""+rs.getString("TKN_HARI_TIME");
    				if(Checker.isStringNullOrEmpty(tknHrTime1)) {
    					tknHrTime1 = "null";
    				}
    				String nmdos1 = ""+rs.getString("NMMDOS");
    				if(Checker.isStringNullOrEmpty(nmdos1)) {
    					nmdos1 = "null";
    				}
    				String nmasdos1 = ""+rs.getString("NMMASDOS");
    				if(Checker.isStringNullOrEmpty(nmasdos1)) {
    					nmasdos1 = "null";
    				}
    				String enrolled1 = ""+rs.getInt("ENROLLED");
    				if(Checker.isStringNullOrEmpty(enrolled1)) {
    					enrolled1 = "null";
    				}
    				String maxEnrolled1 = ""+rs.getInt("MAX_ENROLLED");
    				if(Checker.isStringNullOrEmpty(maxEnrolled1)) {
    					maxEnrolled1 = "null";
    				}
    				String minEnrolled1 = ""+rs.getInt("MIN_ENROLLED");
    				if(Checker.isStringNullOrEmpty(minEnrolled1)) {
    					minEnrolled1 = "null";
    				}
    				String subKeterKdkmk1 = ""+rs.getString("SUB_KETER_KDKMK");
    				if(Checker.isStringNullOrEmpty(subKeterKdkmk1)) {
    					subKeterKdkmk1 = "null";
    				}
    				String initReqTime1 = ""+rs.getTimestamp("INIT_REQ_TIME");
    				if(Checker.isStringNullOrEmpty(initReqTime1)) {
    					initReqTime1 = "null";
    				}
    				String tknNpmApr1 = ""+rs.getString("TKN_NPM_APPROVAL");
    				if(Checker.isStringNullOrEmpty(tknNpmApr1)) {
    					tknNpmApr1 = "null";
    				}
    				String tknAprTime1 = ""+rs.getString("TKN_APPROVAL_TIME");
    				if(Checker.isStringNullOrEmpty(tknAprTime1)) {
    					tknAprTime1 = "null";
    				}
    				String targetTtmhs1 = ""+rs.getInt("TARGET_TTMHS");
    				if(Checker.isStringNullOrEmpty(targetTtmhs1)) {
    					targetTtmhs1 = "null";
    				}
    				String passed1 = ""+rs.getBoolean("PASSED");
    				if(Checker.isStringNullOrEmpty(passed1)) {
    					passed1 = "null";
    				}
    				String rejected1 = ""+rs.getBoolean("REJECTED");
    				if(Checker.isStringNullOrEmpty(rejected1)) {
    					rejected1 = "null";
    				}
    				String konsen1 = ""+rs.getString("KONSENTRASI");
    				if(Checker.isStringNullOrEmpty(konsen1)) {
    					konsen1 = "null";
    				}
    				String cuid = ""+rs.getLong("UNIQUE_ID");
    				if(Checker.isStringNullOrEmpty(cuid)) {
    					cuid = "null";
    				}
    				
    				String kodeGabungan = ""+rs.getString("KODE_PENGGABUNGAN");
    				if(Checker.isStringNullOrEmpty(kodeGabungan)) {
    					kodeGabungan = "null";
    				}
    				if(kodeGabungan.equalsIgnoreCase("null")) {
    					//lin.add(idkur1+"$"+idkmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+kodeGabungan);
    					lin.add(kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+konsen1+"$"+cuid);
    				}
    				else {
    					lig.add(kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+konsen1+"$"+cuid);
    				}
    				//lif.add(idkur1+"$"+idkmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+kodeGabungan);
    			}
    			Collections.sort(vNon);
    			Collections.sort(vGroup);
    			stmt = con.prepareStatement("select * from MAKUL inner join MSPST on KDPSTMAKUL=KDPSTMSPST where IDKMKMAKUL=?");
    			lig = vGroup.listIterator();
    			while(lig.hasNext()){
    				String brs = (String)lig.next();
    				StringTokenizer st = new StringTokenizer(brs,"$");
    				String kodeGabungan=st.nextToken();
    				String idkmk1=st.nextToken();
    				String idkur1=st.nextToken();
    				String thsms1=st.nextToken();
    				String kdpst1=st.nextToken();
    				String shift1=st.nextToken();
    				String norutKlsPll1=st.nextToken();
    				String initNpmInput1=st.nextToken();
    				String latestNpmUpdate1=st.nextToken();
    				String latesStatusInfo1=st.nextToken();
    				String currAvailStatus1=st.nextToken();
    				String locked1=st.nextToken();
    				String npmdos1=st.nextToken();
    				String nodos1=st.nextToken();
    				String npmasdos1=st.nextToken();
    				String noasdos1=st.nextToken();
    				String canceled1=st.nextToken();
    				String kodeKelas1=st.nextToken();
    				String kodeRuang1=st.nextToken();
    				String kodeGedung1=st.nextToken();
    				String kodeKampus1=st.nextToken();
    				String tknHrTime1=st.nextToken();
    				String nmdos1=st.nextToken();
    				String nmasdos1=st.nextToken();
    				String enrolled1=st.nextToken();
    				String maxEnrolled1=st.nextToken();
    				String minEnrolled1=st.nextToken();
    				String subKeterKdkmk1=st.nextToken();
    				String initReqTime1=st.nextToken();
    				String tknNpmApr1=st.nextToken();
    				String tknAprTime1=st.nextToken();
    				String targetTtmhs1=st.nextToken();
    				String passed1=st.nextToken();
    				String rejected1=st.nextToken();
    				String konsen1=st.nextToken();
    				String cuid1=st.nextToken();
    				stmt.setLong(1, Long.valueOf(idkmk1).longValue());
    				rs = stmt.executeQuery();
    				rs.next();
    				String kdkmk1 = ""+rs.getString("KDKMKMAKUL");
    				String nakmk1 = rs.getString("NAKMKMAKUL");
    				//nakmk1=nakmk1.replace("+", "tandaTambah");
    				
    				String nmpst1 = rs.getString("NMPSTMSPST");
    				if(Checker.isStringNullOrEmpty(nakmk1)) {
    					nakmk1="null";
    				}
    				lig.set(kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+kdkmk1+"$"+nakmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+konsen1+"$"+nmpst1+"$"+cuid1);
    				//lif.set(nakmk+"$"+brs);
    			}
    			
    			
    			lin = vNon.listIterator();
    			while(lin.hasNext()){
    				String brs = (String)lin.next();
    				StringTokenizer st = new StringTokenizer(brs,"$");
    				String kodeGabungan=st.nextToken();
    				String idkmk1=st.nextToken();
    				String idkur1=st.nextToken();
    				String thsms1=st.nextToken();
    				String kdpst1=st.nextToken();
    				String shift1=st.nextToken();
    				String norutKlsPll1=st.nextToken();
    				String initNpmInput1=st.nextToken();
    				String latestNpmUpdate1=st.nextToken();
    				String latesStatusInfo1=st.nextToken();
    				String currAvailStatus1=st.nextToken();
    				String locked1=st.nextToken();
    				String npmdos1=st.nextToken();
    				String nodos1=st.nextToken();
    				String npmasdos1=st.nextToken();
    				String noasdos1=st.nextToken();
    				String canceled1=st.nextToken();
    				String kodeKelas1=st.nextToken();
    				String kodeRuang1=st.nextToken();
    				String kodeGedung1=st.nextToken();
    				String kodeKampus1=st.nextToken();
    				String tknHrTime1=st.nextToken();
    				String nmdos1=st.nextToken();
    				String nmasdos1=st.nextToken();
    				String enrolled1=st.nextToken();
    				String maxEnrolled1=st.nextToken();
    				String minEnrolled1=st.nextToken();
    				String subKeterKdkmk1=st.nextToken();
    				String initReqTime1=st.nextToken();
    				String tknNpmApr1=st.nextToken();
    				String tknAprTime1=st.nextToken();
    				String targetTtmhs1=st.nextToken();
    				String passed1=st.nextToken();
    				String rejected1=st.nextToken();
    				String konsen1=st.nextToken();
    				String cuid1=st.nextToken();
    				stmt.setLong(1, Long.valueOf(idkmk1).longValue());
    				rs = stmt.executeQuery();
    				rs.next();
    				String kdkmk1 = ""+rs.getString("KDKMKMAKUL");
    				String nakmk1 = ""+rs.getString("NAKMKMAKUL");
    				//nakmk1=nakmk1.replace("+", "tandaTambah");
    				String nmpst1 = rs.getString("NMPSTMSPST");
    				if(Checker.isStringNullOrEmpty(nakmk1)) {
    					nakmk1="null";
    				}
    				
    				lin.set(kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+kdkmk1+"$"+nakmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+konsen1+"$"+nmpst1+"$"+cuid1);
    				//lif.set(nakmk+"$"+brs);
    			}
    			
    			//sorting vNon based on nakmk
    			lin = vNon.listIterator();
    			while(lin.hasNext()){
    				String brs = (String)lin.next();
    				StringTokenizer st = new StringTokenizer(brs,"$");
    				String kodeGabungan=st.nextToken();
    				String idkmk1=st.nextToken();
    				String idkur1=st.nextToken();
    				String kdkmk1=st.nextToken();
    				String nakmk1=st.nextToken();
    				String thsms1=st.nextToken();
    				String kdpst1=st.nextToken();
    				String shift1=st.nextToken();
    				String norutKlsPll1=st.nextToken();
    				String initNpmInput1=st.nextToken();
    				String latestNpmUpdate1=st.nextToken();
    				String latesStatusInfo1=st.nextToken();
    				String currAvailStatus1=st.nextToken();
    				String locked1=st.nextToken();
    				String npmdos1=st.nextToken();
    				String nodos1=st.nextToken();
    				String npmasdos1=st.nextToken();
    				String noasdos1=st.nextToken();
    				String canceled1=st.nextToken();
    				String kodeKelas1=st.nextToken();
    				String kodeRuang1=st.nextToken();
    				String kodeGedung1=st.nextToken();
    				String kodeKampus1=st.nextToken();
    				String tknHrTime1=st.nextToken();
    				String nmdos1=st.nextToken();
    				String nmasdos1=st.nextToken();
    				String enrolled1=st.nextToken();
    				String maxEnrolled1=st.nextToken();
    				String minEnrolled1=st.nextToken();
    				String subKeterKdkmk1=st.nextToken();
    				String initReqTime1=st.nextToken();
    				String tknNpmApr1=st.nextToken();
    				String tknAprTime1=st.nextToken();
    				String targetTtmhs1=st.nextToken();
    				String passed1=st.nextToken();
    				String rejected1=st.nextToken();
    				String konsen1=st.nextToken();
    				String nmpst1=st.nextToken();
    				String cuid1=st.nextToken();
    				lin.set(nakmk1+"$"+shift1+"$"+kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+kdkmk1+"$"+thsms1+"$"+kdpst1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+konsen1+"$"+nmpst1+"$"+cuid1);
    				//lif.set(nakmk+"$"+brs);
    			}
    			Collections.sort(vNon);
    			lin = vNon.listIterator();
    			while(lin.hasNext()){
    				String brs = (String)lin.next();
    				StringTokenizer st = new StringTokenizer(brs,"$");
    				String nakmk1=st.nextToken();
    				String shift1=st.nextToken();
    				String kodeGabungan=st.nextToken();
    				String idkmk1=st.nextToken();
    				String idkur1=st.nextToken();
    				String kdkmk1=st.nextToken();
    				//String nakmk1=st.nextToken();
    				String thsms1=st.nextToken();
    				String kdpst1=st.nextToken();
    				
    				String norutKlsPll1=st.nextToken();
    				String initNpmInput1=st.nextToken();
    				String latestNpmUpdate1=st.nextToken();
    				String latesStatusInfo1=st.nextToken();
    				String currAvailStatus1=st.nextToken();
    				String locked1=st.nextToken();
    				String npmdos1=st.nextToken();
    				String nodos1=st.nextToken();
    				String npmasdos1=st.nextToken();
    				String noasdos1=st.nextToken();
    				String canceled1=st.nextToken();
    				String kodeKelas1=st.nextToken();
    				String kodeRuang1=st.nextToken();
    				String kodeGedung1=st.nextToken();
    				String kodeKampus1=st.nextToken();
    				String tknHrTime1=st.nextToken();
    				String nmdos1=st.nextToken();
    				String nmasdos1=st.nextToken();
    				String enrolled1=st.nextToken();
    				String maxEnrolled1=st.nextToken();
    				String minEnrolled1=st.nextToken();
    				String subKeterKdkmk1=st.nextToken();
    				String initReqTime1=st.nextToken();
    				String tknNpmApr1=st.nextToken();
    				String tknAprTime1=st.nextToken();
    				String targetTtmhs1=st.nextToken();
    				String passed1=st.nextToken();
    				String rejected1=st.nextToken();
    				String konsen1=st.nextToken();
    				String nmpst1=st.nextToken();
    				String cuid1=st.nextToken();
    				lin.set(kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+kdkmk1+"$"+nakmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+konsen1+"$"+nmpst1+"$"+cuid1);
    				//lif.set(nakmk+"$"+brs);
    			}
    			//end sorting
    			
    			lif.add(vNon);
    			lif.add(vGroup);
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
    	return vf;
    }
    

    public Vector cekAndRenameGroupKode(Vector vGrp) {
    	
    	ListIterator ligr = null;
    	if(vGrp!=null && vGrp.size()>0) {
    		//sort biar npm berururtan
    		Collections.sort(vGrp);
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			ligr = vGrp.listIterator();
    			int i=1;
    			if(ligr.hasNext()){
    				//lig.set(kodeGabungan+"$"+idkmk1+"$"+idkur1+"$"+kdkmk1+"$"+nakmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+nmpst1);
    				String brs = (String)ligr.next();
    				StringTokenizer st = new StringTokenizer(brs,"$");
    				String prev_kodeGab = st.nextToken();
    				String prev_npm = prev_kodeGab.substring(0,13);
    				String prev_kode = prev_kodeGab.substring(13,prev_kodeGab.length());
    				String prev_kodeOnly = null;
    				if(prev_kode.contains("inti")) {
    					prev_kodeOnly = prev_kode.substring(0,prev_kode.length()-4);
    				}
    				else {
    					prev_kodeOnly=""+prev_kode;
    				}
    				//========
    				if(prev_kode.contains("inti")) {
    					ligr.set(this.operatorNpm+i+"inti$"+(brs.substring(prev_kodeGab.length()+1,brs.length())));	
    				}
    				else {
    					ligr.set(this.operatorNpm+i+"$"+(brs.substring(prev_kodeGab.length()+1,brs.length())));
    				}
    				//=========
    				while(ligr.hasNext()){
        				String brs1 = (String)ligr.next();
        				st = new StringTokenizer(brs1,"$");
        				String curr_kodeGab = st.nextToken();
        				String curr_npm = curr_kodeGab.substring(0,13);
        				String curr_kode = curr_kodeGab.substring(13,curr_kodeGab.length());
        				String curr_kodeOnly = null;
        				
        				if(curr_kode.contains("inti")) {
        					curr_kodeOnly = curr_kode.substring(0,curr_kode.length()-4);
        				}
        				else {
        					curr_kodeOnly=""+curr_kode;
        				}
        				if(curr_npm.equalsIgnoreCase(prev_npm)) {
        					if(curr_kodeOnly.equalsIgnoreCase(prev_kodeOnly)) {
        						//========
        	    				if(curr_kode.contains("inti")) {
        	    					ligr.set(this.operatorNpm+i+"inti$"+(brs1.substring(curr_kodeGab.length()+1,brs1.length())));	
        	    				}
        	    				else {
        	    					ligr.set(this.operatorNpm+i+"$"+(brs1.substring(curr_kodeGab.length()+1,brs1.length())));
        	    				}
        	    				//=========
        					}
        					else {
        						i++;
        						prev_kodeOnly=""+curr_kodeOnly;
        						//========
        	    				if(curr_kode.contains("inti")) {
        	    					ligr.set(this.operatorNpm+i+"inti$"+(brs1.substring(curr_kodeGab.length()+1,brs1.length())));	
        	    				}
        	    				else {
        	    					ligr.set(this.operatorNpm+i+"$"+(brs1.substring(curr_kodeGab.length()+1,brs1.length())));
        	    				}
        	    				//=========
        						
        					}
        					//System.out.println("curr_kodeOnly="+curr_kodeOnly);
        				}
        				else {
        					i++;
        					prev_npm = ""+curr_npm;
        					prev_kodeOnly=""+curr_kodeOnly;
        					//========
    	    				if(curr_kode.contains("inti")) {
    	    					ligr.set(this.operatorNpm+i+"inti$"+(brs1.substring(curr_kodeGab.length()+1,brs1.length())));	
    	    				}
    	    				else {
    	    					ligr.set(this.operatorNpm+i+"$"+(brs1.substring(curr_kodeGab.length()+1,brs1.length())));
    	    				}
    	    				//=========
        				}
    				}	
    				return vGrp;

    				
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
    	return vGrp;
    }
    
    
    
    public Vector getTotMhsPerMakul(String thsms,String kdpst) {
    	Vector v1 = new Vector();
    	Vector v2 = new Vector();
    	Vector v3 = new Vector();//shift
    	Vector vf = new Vector();
    	
    	ListIterator li1 = v1.listIterator();
    	ListIterator li2 = v2.listIterator();
    	ListIterator li3 = v3.listIterator();
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
    				String npmhs = rs.getString("NPMHSTRNLM");
    				String kdkmk = rs.getString("KDKMKTRNLM");
    				int sksmk = rs.getInt("SKSMKTRNLM");
    				String shift = rs.getString("SHIFTTRNLM");
    				//li1.add(kdkmk+"$"+sksmk+"$"+shift);
    				li1.add(kdkmk+"$"+sksmk);
    				li2.add(kdkmk+"$"+sksmk+"$"+shift+"$"+npmhs);
    				li3.add(shift);
    			}
    			
    			if(v3!=null) {
    				//untu di html ada berapa tipe shift
    				v3 = Tool.removeDuplicateFromVector(v3);
    				Collections.sort(v3);
    			}
    			
    			
    			Vector v0 = new Vector();
    			ListIterator li0 = v0.listIterator();
    			if(v1!=null) {
    				v1 = Tool.removeDuplicateFromVector(v1);
    				li1 = v1.listIterator();
    				while(li1.hasNext()) {
    					String brs = (String)li1.next();
    					li3 = v3.listIterator();
    					for(int i=0;i<v3.size();i++) {
    						String shift = (String)li3.next();
    						li0.add(brs+"$"+shift);
    					}
    				}
    				
    				v1 = (Vector)v0.clone();
    				
    				
    				
    				li1 = v1.listIterator();
    				while(li1.hasNext()) {
    					String brs = (String)li1.next();
    					String listNpm = "0";
    					li2 = v2.listIterator();
    					boolean first = true;
    					//boolean match = true;
    					while(li2.hasNext()) {
    						String tmp = (String)li2.next();
    						if(tmp.startsWith(brs)) {
    							
    							StringTokenizer st = new StringTokenizer(tmp,"$");
    							String kdkmk = st.nextToken();
    							String sksmk = st.nextToken();
    							String shift = st.nextToken();
    							String npmhs = st.nextToken();
    							if(first) {
    								listNpm = "";
    								first = false;
    								listNpm = ""+npmhs;
    							}
    							else {
    								listNpm = listNpm+","+npmhs;
    							}
    						}
    					}
    					li1.set(brs+"$"+listNpm);
    					
    				}
    			}//end ifv1!=null
    			//li1 = v1.listIterator();
    			//while(li1.hasNext()) {
    			//	String brs = (String)li1.next();
    			//	System.out.println(brs);
    			//}
    			//System.out.println("v1 size after ="+v1.size());
    			Collections.sort(v1);
    			//get nama makul
    			stmt = con.prepareStatement("select * from MAKUL where KDPSTMAKUL=? and KDKMKMAKUL=?");
    			li1 = v1.listIterator();
    			while(li1.hasNext()) {
    				String brs = (String)li1.next();
    				StringTokenizer st = new StringTokenizer(brs,"$");
    				String kdkmk = st.nextToken();
					String sksmk = st.nextToken();
					String shift = st.nextToken();
					stmt.setString(1, kdpst);
					stmt.setString(2, kdkmk);
					rs = stmt.executeQuery();
					rs.next();
					String nakmk = ""+rs.getString("NAKMKMAKUL");
					if(Checker.isStringNullOrEmpty(nakmk)) {
						nakmk = "null";
					}
					li1.set(nakmk+"$"+brs);
    				//System.out.println(brs);
    			}
    			
    			li1 = v1.listIterator();
    			while(li1.hasNext()) {
    				String brs = (String)li1.next();
    				//System.out.println(brs);
    			}	
    			Collections.sort(v1);
    			lif.add(v1);//mpe shift
    			//lif.add(v2);//mpe mhs
    			lif.add(v3);//shift only
    		}
    	}
    	catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		System.out.println(e);
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return vf;
    }  
    
    /*
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * DEPERECATED PAKE VERSION2
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    public Vector getTotMhsPerMakul_v1(String thsms,String kdpst) {
    	Vector vlik = new Vector();
    	Vector vf = new Vector();
    	Vector vshift  = new Vector();
    	Vector v1 = new Vector();
    	ListIterator li1 = v1.listIterator();
    	ListIterator lif = vf.listIterator();
    	ListIterator lis = vshift.listIterator();
    	//String distinct_shift = "";
    	try {
    		if(!Checker.isStringNullOrEmpty(thsms) && !Checker.isStringNullOrEmpty(kdpst)) {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			
    			//1. GET KELAS TRNLM BY idkmk
    			
    			ListIterator litmp = vlik.listIterator();
    			stmt = con.prepareStatement("select distinct IDKMKTRNLM from TRNLM where THSMSTRNLM=? and KDPSTTRNLM=? order by KDKMKTRNLM,SHIFTTRNLM");
    			stmt.setString(1,thsms);
    			stmt.setString(2,kdpst);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				litmp.add(""+rs.getLong(1));
    			}
    			//2. get info makul
    			if(vlik.size()>0) {
    				litmp = vlik.listIterator();
    				//System.out.println("vlik1="+vlik.size());
    				//stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and IDKMK=? order by THSMSTRNLM");
    				stmt = con.prepareStatement("select * from MAKUL where IDKMKMAKUL=?");
    				while(litmp.hasNext()) {
    					String idkmk = (String)litmp.next();
    					//System.out.println("cuid="+cuid);
    					//stmt.setString(1, thsms);
    					stmt.setLong(1, Long.parseLong(idkmk));
    					rs = stmt.executeQuery();
    					while(rs.next()) {
    						//String idkur_mk = ""+rs.getInt("IDKUR");
        					//String kdpst_mk = ""+rs.getString("KDPST");
        					//String shift_mk = ""+rs.getString("SHIFT");
        					//String no_pll = ""+rs.getInt("NORUT_KELAS_PARALEL");
        					//String npmdos = ""+rs.getString("NPMDOS");
        					//String nmmdos = ""+rs.getString("NMMDOS");
        					//String cuid = ""+rs.getLong("UNIQUE_ID");
        					String kdkmk = ""+rs.getString("KDKMKMAKUL");
        					String nakmk = ""+rs.getString("NAKMKMAKUL");
        					int skstm = rs.getInt("SKSTMMAKUL");
        					int skspr = rs.getInt("SKSPRMAKUL");
        					int skslp = rs.getInt("SKSLPMAKUL");
        					int sksmk = skstm+skspr+skslp;
        					//lis.add(shift_mk);
        					//litmp.set(cuid+"`"+idkmk+"`"+idkur_mk+"`"+kdpst_mk+"`"+shift_mk+"`"+no_pll+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+nakmk+"`"+sksmk);
        					litmp.set(idkmk+"`"+kdkmk+"`"+nakmk+"`"+sksmk);
    					//System.out.println(cuid+"`"+idkur_mk+"`"+kdpst_mk+"`"+shift_mk+"`"+no_pll+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+nakmk+"`"+sksmk);
    					}
    				}
    				//System.out.println("vlik2="+vlik.size());
    				//3.get list shift
    				String kdjen = Checker.getKdjen(kdpst);
    				String info_shift_info = Checker.getListShift(kdjen);
    				//System.out.println("info_shift_info="+info_shift_info);
    				//tmp = tmp + uniqKeter+"#"+shift+"#"+hari+"#"+tkn_kdjen+"#"+keterKonversi+"#";
    				StringTokenizer st = new StringTokenizer(info_shift_info,"#");
    				while(st.hasMoreTokens()) {
    					String shift_ = st.nextToken();
    					if(!shift_.equalsIgnoreCase("N/A")) {
    						lis.add(shift_);	
    					}
    					
    					st.nextToken();
    					st.nextToken();
    					st.nextToken();
    					st.nextToken();
    				}
    				Collections.sort(vshift);
    				//lis = vshift.listIterator();
    				//while(lis.hasNext()) {
    				//	System.out.println("shift="+(String)lis.next());
    				//}
    				//4.get kls plpl
    				stmt = con.prepareStatement("select distinct NORUT_KELAS_PARALEL from CLASS_POOL where THSMS=? and KDPST=? and IDKMK=? order by IDKMK,SHIFT,NORUT_KELAS_PARALEL");
    				litmp = vlik.listIterator();
    				while(litmp.hasNext()) {
    					//litmp.set(idkmk+"`"+kdkmk+"`"+nakmk+"`"+sksmk);
    					String brs = (String)litmp.next();
    					st = new StringTokenizer(brs,"`");
    					String idkmk = st.nextToken();
    					String kdkmk = st.nextToken();
    					String nakmk = st.nextToken();
    					String sksmk = st.nextToken();
    					stmt.setString(1, thsms);
    					stmt.setString(2, kdpst);
    					stmt.setLong(3, Long.parseLong(idkmk));
    					rs = stmt.executeQuery();
    					while(rs.next()) {
    						int nopll = rs.getInt("NORUT_KELAS_PARALEL");
    						li1.add(idkmk+"`"+kdkmk+"`"+nakmk+"`"+sksmk+"`"+nopll);
    					}
    				}	
    				//4.get cuid from classpool;
    				vlik = new Vector();
    				stmt = con.prepareStatement("select * from CLASS_POOL where THSMS=? and KDPST=? and IDKMK=? and SHIFT=? and NORUT_KELAS_PARALEL=? order by IDKMK,SHIFT");
    				litmp = vlik.listIterator();
    				li1 = v1.listIterator();
    				while(li1.hasNext()) {
    					//litmp.set(idkmk+"`"+kdkmk+"`"+nakmk+"`"+sksmk);
    					String brs = (String)li1.next();
    					st = new StringTokenizer(brs,"`");
    					String idkmk = st.nextToken();
    					String kdkmk = st.nextToken();
    					String nakmk = st.nextToken();
    					String sksmk = st.nextToken();
    					String nopll = st.nextToken();
    					lis = vshift.listIterator();
    					while(lis.hasNext()) {
    						String target_shift = (String)lis.next();
    						stmt.setString(1, thsms);
    						stmt.setString(2, kdpst);
    						stmt.setLong(3, Long.parseLong(idkmk));
    						stmt.setString(4, target_shift);
    						stmt.setLong(5, Long.parseLong(nopll));
    						//System.out.println()
    						rs = stmt.executeQuery();
    						if(rs.next()) {
    							do {
    								//looping kalo ada kelas pralel
    								litmp.add(brs+"`"+rs.getLong("UNIQUE_ID")+"`"+target_shift);
    								//System.out.println("li1 = "+brs+"`"+rs.getLong("UNIQUE_ID")+"`"+target_shift);
    							}
    							while(rs.next());
    						}
    						else {
    							litmp.add(brs+"`null`"+target_shift);
    							//System.out.println("li1 = "+brs+"`null`"+target_shift);
    						}
    					}
    				}
    				//5.get jum mhs
    				if(vlik.size()>0) {
    					
    					
    					stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM where CLASS_POOL_UNIQUE_ID=? or CUID_INIT=?");
    					li1 = vlik.listIterator();
    					while(li1.hasNext()) {
    						String brs = (String)li1.next();
    						st = new StringTokenizer(brs,"`");
        					String idkmk = st.nextToken();
        					String kdkmk = st.nextToken();
        					String nakmk = st.nextToken();
        					String sksmk = st.nextToken();
        					String no_kls_pll = st.nextToken();
        					String cuid = st.nextToken();
        					String target_shift = st.nextToken();
        					if(cuid.equalsIgnoreCase("null")) {
        						li1.set(brs+"`0");
        						//System.out.println(brs+"`0");
        					}
        					else {
        						
        						stmt.setLong(1,Long.parseLong(cuid));
        						stmt.setLong(2,Long.parseLong(cuid));
        						rs = stmt.executeQuery();
        						int ttmhs = 0;
        						while(rs.next()) {
        							ttmhs++;
        						}
        						//System.out.println(brs+"`"+ttmhs);
        						li1.set(brs+"`"+ttmhs);
        					}
    					}
    				}
    				
    				lif.add(vlik);
    				lif.add(vshift);//
    			}
    		}
    	}
    	catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		System.out.println(e);
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	//Collections.
    	return vf;
    }  
    
    
    
    public Vector getTotMhsPerMakul_v2(String thsms,String kdpst) {
    	Vector vlik = new Vector();
    	Vector vf = new Vector();
    	Vector vshift  = new Vector();
    	Vector v1 = new Vector();
    	ListIterator li1 = v1.listIterator();
    	ListIterator lif = vf.listIterator();
    	ListIterator lis = vshift.listIterator();
    	//String distinct_shift = "";
    	try {
    		if(!Checker.isStringNullOrEmpty(thsms) && !Checker.isStringNullOrEmpty(kdpst)) {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			
    			//1. GET KELAS TRNLM BY idkmk
    			
    			ListIterator litmp = vlik.listIterator();
    			stmt = con.prepareStatement("select distinct IDKMKTRNLM from TRNLM where THSMSTRNLM=? and KDPSTTRNLM=? order by KDKMKTRNLM,SHIFTTRNLM");
    			stmt.setString(1,thsms);
    			stmt.setString(2,kdpst);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				litmp.add(""+rs.getLong(1));
    			}
    			//2. get info makul
    			if(vlik.size()>0) {
    				litmp = vlik.listIterator();
    				//System.out.println("vlik1="+vlik.size());
    				//stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and IDKMK=? order by THSMSTRNLM");
    				stmt = con.prepareStatement("select * from MAKUL where IDKMKMAKUL=?");
    				while(litmp.hasNext()) {
    					String idkmk = (String)litmp.next();
    					//System.out.println("cuid="+cuid);
    					//stmt.setString(1, thsms);
    					stmt.setLong(1, Long.parseLong(idkmk));
    					rs = stmt.executeQuery();
    					while(rs.next()) {
    						//String idkur_mk = ""+rs.getInt("IDKUR");
        					//String kdpst_mk = ""+rs.getString("KDPST");
        					//String shift_mk = ""+rs.getString("SHIFT");
        					//String no_pll = ""+rs.getInt("NORUT_KELAS_PARALEL");
        					//String npmdos = ""+rs.getString("NPMDOS");
        					//String nmmdos = ""+rs.getString("NMMDOS");
        					//String cuid = ""+rs.getLong("UNIQUE_ID");
        					String kdkmk = ""+rs.getString("KDKMKMAKUL");
        					String nakmk = ""+rs.getString("NAKMKMAKUL");
        					int skstm = rs.getInt("SKSTMMAKUL");
        					int skspr = rs.getInt("SKSPRMAKUL");
        					int skslp = rs.getInt("SKSLPMAKUL");
        					int sksmk = skstm+skspr+skslp;
        					//lis.add(shift_mk);
        					//litmp.set(cuid+"`"+idkmk+"`"+idkur_mk+"`"+kdpst_mk+"`"+shift_mk+"`"+no_pll+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+nakmk+"`"+sksmk);
        					litmp.set(idkmk+"`"+kdkmk+"`"+nakmk+"`"+sksmk);
    					//System.out.println(cuid+"`"+idkur_mk+"`"+kdpst_mk+"`"+shift_mk+"`"+no_pll+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+nakmk+"`"+sksmk);
    					}
    					
    				}
    				
    				//System.out.println("vlik1="+vlik.size());
					//2b. get idkur
					litmp = vlik.listIterator();
    				Vector vlikupd = new Vector();
    				ListIterator lit2 = vlikupd.listIterator();
    				stmt = con.prepareStatement("select distinct IDKUR from CLASS_POOL where THSMS=? and KDPST=? and IDKMK=? order by IDKUR,SHIFT");
    				while(litmp.hasNext()) {
    					String brs = (String)litmp.next();
    					//System.out.println("barsi1="+brs);
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String idkmk = st.nextToken();
    					String kdkmk = st.nextToken();
    					String nakmk = st.nextToken();
    					String sksmk = st.nextToken();
    					stmt.setString(1, thsms);
    					stmt.setString(2, kdpst);
    					stmt.setInt(3, Integer.parseInt(idkmk));
    					rs = stmt.executeQuery();
    					while(rs.next()) {
    						int idkur = rs.getInt(1);
    						lit2.add(idkmk+"`"+kdkmk+"`"+nakmk+"`"+sksmk+"`"+idkur);
    					}
    				}
    				vlik = vlikupd;
    				
    				
    				//System.out.println("vlik2="+vlik.size());
    				//3.get list shift
    				String kdjen = Checker.getKdjen(kdpst);
    				String info_shift_info = Checker.getListShift(kdjen);
    				//System.out.println("info_shift_info="+info_shift_info);
    				//tmp = tmp + uniqKeter+"#"+shift+"#"+hari+"#"+tkn_kdjen+"#"+keterKonversi+"#";
    				StringTokenizer st = new StringTokenizer(info_shift_info,"#");
    				while(st.hasMoreTokens()) {
    					String shift_ = st.nextToken();
    					if(!shift_.equalsIgnoreCase("N/A")) {
    						lis.add(shift_);	
    					}
    					
    					st.nextToken();
    					st.nextToken();
    					st.nextToken();
    					st.nextToken();
    				}
    				Collections.sort(vshift);
    				//lis = vshift.listIterator();
    				//while(lis.hasNext()) {
    				//	System.out.println("shift="+(String)lis.next());
    				//}
    				//4.get kls plpl
    				stmt = con.prepareStatement("select distinct NORUT_KELAS_PARALEL from CLASS_POOL where IDKUR=? and IDKMK=? and THSMS=? and KDPST=? order by IDKMK,SHIFT,NORUT_KELAS_PARALEL");
    				litmp = vlik.listIterator();
    				while(litmp.hasNext()) {
    					//litmp.set(idkmk+"`"+kdkmk+"`"+nakmk+"`"+sksmk);
    					String brs = (String)litmp.next();
    					st = new StringTokenizer(brs,"`");
    					String idkmk = st.nextToken();
    					String kdkmk = st.nextToken();
    					String nakmk = st.nextToken();
    					String sksmk = st.nextToken();
    					String idkur = st.nextToken();
    					stmt.setLong(1, Long.parseLong(idkur));
    					stmt.setLong(2, Long.parseLong(idkmk));
    					stmt.setString(3, thsms);
    					stmt.setString(4, kdpst);
    					
    					rs = stmt.executeQuery();
    					while(rs.next()) {
    						int nopll = rs.getInt("NORUT_KELAS_PARALEL");
    						li1.add(idkmk+"`"+kdkmk+"`"+nakmk+"`"+sksmk+"`"+nopll+"`"+idkur);
    					}
    				}	
    				//4.get cuid from classpool;
    				vlik = new Vector();
    				stmt = con.prepareStatement("select * from CLASS_POOL where IDKUR=? and IDKMK=? and THSMS=? and KDPST=? and SHIFT=? and NORUT_KELAS_PARALEL=? order by IDKMK,SHIFT");
    				litmp = vlik.listIterator();
    				li1 = v1.listIterator();
    				while(li1.hasNext()) {
    					//litmp.set(idkmk+"`"+kdkmk+"`"+nakmk+"`"+sksmk);
    					String brs = (String)li1.next();
    					st = new StringTokenizer(brs,"`");
    					String idkmk = st.nextToken();
    					String kdkmk = st.nextToken();
    					String nakmk = st.nextToken();
    					String sksmk = st.nextToken();
    					String nopll = st.nextToken();
    					String idkur = st.nextToken();
    					lis = vshift.listIterator();
    					while(lis.hasNext()) {
    						String target_shift = (String)lis.next();
    						stmt.setLong(1, Long.parseLong(idkur));
    						stmt.setLong(2, Long.parseLong(idkmk));
    						stmt.setString(3, thsms);
    						stmt.setString(4, kdpst);
    						stmt.setString(5, target_shift);
    						stmt.setLong(6, Long.parseLong(nopll));
    						//System.out.println()
    						rs = stmt.executeQuery();
    						if(rs.next()) {
    							do {
    								//looping kalo ada kelas pralel
    								litmp.add(brs+"`"+rs.getLong("UNIQUE_ID")+"`"+target_shift);
    								//System.out.println("li1 = "+brs+"`"+rs.getLong("UNIQUE_ID")+"`"+target_shift);
    							}
    							while(rs.next());
    						}
    						else {
    							litmp.add(brs+"`null`"+target_shift);
    							//System.out.println("li1 = "+brs+"`null`"+target_shift);
    						}
    					}
    				}
    				//5.get jum mhs
    				if(vlik.size()>0) {
    					
    					
    					stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM where CLASS_POOL_UNIQUE_ID=? or CUID_INIT=?");
    					li1 = vlik.listIterator();
    					while(li1.hasNext()) {
    						String brs = (String)li1.next();
    						st = new StringTokenizer(brs,"`");
        					String idkmk = st.nextToken();
        					String kdkmk = st.nextToken();
        					String nakmk = st.nextToken();
        					String sksmk = st.nextToken();
        					String no_kls_pll = st.nextToken();
        					String idkur = st.nextToken();
        					String cuid = st.nextToken();
        					String target_shift = st.nextToken();
        					if(cuid.equalsIgnoreCase("null")) {
        						li1.set(brs+"`N/A");
        						//System.out.println(brs+"`0");
        					}
        					else {
        						
        						stmt.setLong(1,Long.parseLong(cuid));
        						stmt.setLong(2,Long.parseLong(cuid));
        						rs = stmt.executeQuery();
        						int ttmhs = 0;
        						while(rs.next()) {
        							ttmhs++;
        						}
        						//System.out.println(brs+"`"+ttmhs);
        						li1.set(brs+"`"+ttmhs);
        					}
    					}
    				}
    				
    				lif.add(vlik);
    				lif.add(vshift);//
    			}
    		}
    	}
    	catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		System.out.println(e);
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		    if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		    if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	//Collections.
    	return vf;
    }  
    
    
}
