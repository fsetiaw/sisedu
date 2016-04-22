package beans.dbase.angket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import beans.dbase.UpdateDb;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.DateFormater;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;



/**
 * Session Bean implementation class UpdateDbAngket
 */
@Stateless
@LocalBean
public class UpdateDbAngket extends UpdateDb {
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
    public UpdateDbAngket() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbAngket(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }

    
    public void updateDataAngket1(String fieldAndValue,String usrname, String usrpwd) {
    	String gender = null;
		String thnLulus = null;
		String hape = null;
		String tplhr = null;
		String nglhr = null;
		String ktsma = null;
		String telp = null;
		String npmhs = null;
		String kdpos = null;
		String almrm = null;
		String email = null;
		String nmsma = null;
		String status = null;
		String tglhr = null;
		String ktarm = null;
		String button = null;
		String fwdPage = null;
		String agama = null;
		if(fieldAndValue!=null && !Checker.isStringNullOrEmpty(fieldAndValue)) {
			StringTokenizer st = new StringTokenizer(fieldAndValue,"||");
			while(st.hasMoreTokens()) {
				String elementName = st.nextToken();
				String elementValue = st.nextToken();
				if(elementName.equalsIgnoreCase("Gender_Huruf_Wajib")){
					gender = elementValue;
				}
				else if(elementName.equalsIgnoreCase("Tahun-Lulus_Int_Wajib")){
					thnLulus = elementValue;
				}
				else if(elementName.equalsIgnoreCase("No-Hape_Hape_Wajib")){
					hape = elementValue;
				}
				else if(elementName.equalsIgnoreCase("Kota-Kelahiran_String_Wajib")){
					tplhr = elementValue;
				}
				else if(elementName.equalsIgnoreCase("Negara-Kelahiran_Huruf_Wajib")){
					nglhr = elementValue;
				}
				else if(elementName.equalsIgnoreCase("Kota-SMA_String_Wajib")){
					ktsma = elementValue;
				}
				else if(elementName.equalsIgnoreCase("Telp-Rumah_Telp_Wajib")){
					telp = elementValue;
				}
				else if(elementName.equalsIgnoreCase("Npmhs_String_Wajib")){
					npmhs = elementValue;
				}
				else if(elementName.equalsIgnoreCase("Kode-Pos_String_Opt")){
					kdpos = elementValue;
				}
				else if(elementName.equalsIgnoreCase("Alamat-Rumah_String_Wajib")){
					almrm = elementValue;
				}
				else if(elementName.equalsIgnoreCase("Email_Email_Wajib")){
					email = elementValue;
				}
				else if(elementName.equalsIgnoreCase("Nama-SMA_String_Wajib")){
					nmsma = elementValue;
				}
				else if(elementName.equalsIgnoreCase("Status_Huruf_Wajib")){
					status = elementValue;
				}
				else if(elementName.equalsIgnoreCase("Tgl-Lahir_Date_Wajib")){
					tglhr = elementValue;
				}
				else if(elementName.equalsIgnoreCase("Kota-Tempat-Tinggal_String_Wajib")){
					ktarm = elementValue;
				}
				else if(elementName.equalsIgnoreCase("somebutton_String_Opt")){
					button = elementValue;
				}
				else if(elementName.equalsIgnoreCase("StringfwdPageIfValid_String_Opt")){
					fwdPage = elementValue;
				}
				else if(elementName.equalsIgnoreCase("Agama_Huruf_Wajib")){
					agama = elementValue;
				}
				try {
		        	//String ipAddr =  request.getRemoteAddr();
		        	Context initContext  = new InitialContext();
		        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
		        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
		        	con = ds.getConnection();
		        	//update civitas
		        	stmt = con.prepareStatement("UPDATE CIVITAS set KDJEKMSMHS=?,TPLHRMSMHS=?,TGLHRMSMHS=? where NPMHSMSMHS=?");
		        	if(gender==null || Checker.isStringNullOrEmpty(gender)) {
		        		stmt.setNull(1, java.sql.Types.VARCHAR);
		        	}
		        	else {
		        		stmt.setString(1, gender);
		        	}
		        	if(tplhr==null || Checker.isStringNullOrEmpty(tplhr)) {
		        		stmt.setNull(2, java.sql.Types.VARCHAR);
		        	}
		        	else {
		        		stmt.setString(2, tplhr);
		        	}
		        	if(tglhr==null || Checker.isStringNullOrEmpty(tglhr)) {
		        		stmt.setNull(3,java.sql.Types.DATE);
		        	}
		        	else {
		        		stmt.setDate(3, Converter.formatDateBeforeInsert(tglhr));
		        	}
		        	
		        	stmt.setString(4, npmhs);
		        	stmt.executeUpdate();
		        	
		        	stmt = con.prepareStatement("UPDATE EXT_CIVITAS set LULUS_SMA=?,NOHPEMSMHS=?,NEGLHMSMHS=?,KOTA_SMA=?,TELRMMSMHS=?,POSRMMSMHS=?,ALMRMMSMHS=?,EMAILMSMHS=?,ASAL_SMA=?,STTUSMSMHS=?,KOTRMMSMHS=?,AGAMAMSMHS=? where NPMHSMSMHS=?");
		        	if(thnLulus==null || Checker.isStringNullOrEmpty(thnLulus)) {
		        		stmt.setNull(1, java.sql.Types.VARCHAR);
		        	}
		        	else {
		        		stmt.setString(1, thnLulus);
		        	}
		        	if(hape==null || Checker.isStringNullOrEmpty(hape)) {
		        		stmt.setNull(2, java.sql.Types.VARCHAR);
		        	}
		        	else {
		        		stmt.setString(2, hape);
		        	}
		        	if(nglhr==null || Checker.isStringNullOrEmpty(nglhr)) {
		        		stmt.setNull(3, java.sql.Types.VARCHAR);
		        	}
		        	else {
		        		stmt.setString(3, nglhr);
		        	}
		        	if(ktsma==null || Checker.isStringNullOrEmpty(ktsma)) {
		        		stmt.setNull(4, java.sql.Types.VARCHAR);
		        	}
		        	else {
		        		stmt.setString(4, ktsma);
		        	}
		        	if(telp==null || Checker.isStringNullOrEmpty(telp)) {
		        		stmt.setNull(5, java.sql.Types.VARCHAR);
		        	}
		        	else {
		        		stmt.setString(5, telp);
		        	}
		        	if(kdpos==null || Checker.isStringNullOrEmpty(kdpos)) {
		        		stmt.setNull(6, java.sql.Types.VARCHAR);
		        	}
		        	else {
		        		stmt.setString(6, kdpos);
		        	}
		        	if(almrm==null || Checker.isStringNullOrEmpty(almrm)) {
		        		stmt.setNull(7, java.sql.Types.VARCHAR);
		        	}
		        	else {
		        		stmt.setString(7, almrm);
		        	}
		        	if(email==null || Checker.isStringNullOrEmpty(email)) {
		        		stmt.setNull(8, java.sql.Types.VARCHAR);
		        	}
		        	else {
		        		stmt.setString(8, email);
		        	}
		        	if(nmsma==null || Checker.isStringNullOrEmpty(nmsma)) {
		        		stmt.setNull(9, java.sql.Types.VARCHAR);
		        	}
		        	else {
		        		stmt.setString(9, nmsma);
		        	}
		        	if(status==null || Checker.isStringNullOrEmpty(status)) {
		        		stmt.setNull(10, java.sql.Types.VARCHAR);
		        	}
		        	else {
		        		stmt.setString(10, status);
		        	}
		        	if(ktarm==null || Checker.isStringNullOrEmpty(ktarm)) {
		        		stmt.setNull(11, java.sql.Types.VARCHAR);
		        	}
		        	else {
		        		stmt.setString(11, ktarm);
		        	}
		        	if(agama==null || Checker.isStringNullOrEmpty(ktarm)) {
		        		stmt.setNull(12, java.sql.Types.VARCHAR);
		        	}
		        	else {
		        		stmt.setString(12, ktarm);
		        	}
		        	if(npmhs==null || Checker.isStringNullOrEmpty(npmhs)) {
		        		stmt.setNull(13, java.sql.Types.VARCHAR);
		        	}
		        	else {
		        		stmt.setString(13, npmhs);
		        	}
		        	stmt.executeUpdate();
		        	
		        	stmt = con.prepareStatement("UPDATE USR_DAT SET FORCE_REDIRECT=? WHERE USR_NAME=? AND USR_PWD=?");
		        	stmt.setNull(1, java.sql.Types.VARCHAR);
		        	stmt.setString(2, usrname);
		        	stmt.setString(3, usrpwd);
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
		}	
    }
}
