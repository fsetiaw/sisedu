package beans.dbase.cuti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import beans.dbase.UpdateDb;
import beans.tools.Checker;
import beans.tools.DateFormater;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;



/**
 * Session Bean implementation class UpdateDbDsn
 */
@Stateless
@LocalBean
public class UpdateDbDsn extends UpdateDb {
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
    public UpdateDbDsn() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbDsn(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }

    
    public void updateDosenAjarCpThsmsKrs(String[]infoDsn) {
    	/*
    	 * if usrId < 0; berarti unverified usr jharusnya cuma pada saat login
    	 */
    	boolean petugas = false;
    	String thsmsTarget = Checker.getThsmsKrs();
    	
    	//System.out.println("thsmsTarget="+thsmsTarget);
    	if(infoDsn!=null && infoDsn.length>0) {
    		try {
        		//String ipAddr =  request.getRemoteAddr();
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("UPDATE CLASS_POOL SET NPMDOS=?,NODOS=?,NMMDOS=? WHERE IDKUR=? AND IDKMK=? AND THSMS=? AND KDPST=? AND SHIFT=? AND NORUT_KELAS_PARALEL=?");
        		//stmt.setString(1, this.operatorNpm);
        		for(int i=0;i<infoDsn.length;i++) {
        			String brs = infoDsn[i];
        			//System.out.println("info dosen = "+brs);
        			if(brs!=null && !brs.equalsIgnoreCase("null") && !Checker.isStringNullOrEmpty(brs)) {
        				StringTokenizer st = new StringTokenizer(brs,"$");
        				//String value=""+idkur1+"$"+idkmk1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"id_obj+"$"+npm+"$"+nmm;;;
        				String idkur1=st.nextToken();
        				String idkmk1=st.nextToken();
        				String kdpst1=st.nextToken();
        				String shift1=st.nextToken();
        				String norutKlsPll1=st.nextToken();
        				//String nodos=st.nextToken();
        				String id_obj=st.nextToken();
        				String npmdos=st.nextToken();
        				//String nidndos=st.nextToken();
        				//String noKtp=st.nextToken();
        				//String ptihbase=st.nextToken();
        				//String psthbase=st.nextToken();
        				String nmdos=st.nextToken();
        				//System.out.println("nmdos="+nmdos);
        				//String gelar=st.nextToken();
        				//String smawl=st.nextToken();
        				//String tknKdpstAjar=st.nextToken();
        				//String email=st.nextToken();
        				//String tknTelp=st.nextToken();
        				//String tknHp=st.nextToken();
        				//String status=st.nextToken();
        				
        				stmt.setString(1, npmdos);
        				//stmt.setString(2, nodos);
        				stmt.setNull(2, java.sql.Types.VARCHAR);
        				stmt.setString(3, nmdos);
        				stmt.setLong(4, Long.valueOf(idkur1).longValue());
        				stmt.setLong(5, Long.valueOf(idkmk1).longValue());
        				stmt.setString(6, thsmsTarget);
        				stmt.setString(7, kdpst1);
        				stmt.setString(8, shift1);
        				stmt.setInt(9, Integer.valueOf(norutKlsPll1).intValue());
        				int j = stmt.executeUpdate();
        				//System.out.println("brs=["+i+"]="+brs+"="+j);
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
    	//return petugas;
    }	
    
    
    //dipake pas menggunakan insert civitas
    public int insertTableEXT_CIVITAS_DATA_DOSEN(String kdpst,String npmhs,String nodos,String glr_dpn,String glr_end,String nidnn,String tipe_id,String no_id,String status,String pt_s1,String jur_s1,String kdpst_s1,String tkn_bid_ahli_s1,String noija_s1,String file_ija_s1,String jdl_ta_s1,String pt_s2,String jur_s2,String kdpst_s2,String tkn_bid_ahli_s2,String noija_s2,String file_ija_s2,String jdl_ta_s2,String pt_s3,String jur_s3,String kdpst_s3,String tkn_bid_ahli_s3,String noija_s3,String file_ija_s3,String jdl_ta_s3,String pt_prof,String jur_prof,String kdpst_prof,String tkn_bid_ahli_prof,String noija_prof,String file_ija_prof,String jdl_ta_prof,String tot_kum,String jbt_akdmk_dikti,String jbt_akdmk_local,String jbt_struk,String ika,String tgl_sta,String tgl_out,String serdos,String pt_home,String prodi_home,String email_inst,String pangkat_gol) {
    	/*
    	 */
    	int j = 0;
    	/*
    	 * kalo no_id = null maka tipe_id=null
    	 */
    	try {
        	//String ipAddr =  request.getRemoteAddr();
    		if(tgl_sta!=null && !Checker.isStringNullOrEmpty(tgl_sta)) {
    			tgl_sta = DateFormater.prepStringDateToDbInputFormat(tgl_sta);
    		}
    		if(tgl_out!=null && !Checker.isStringNullOrEmpty(tgl_out)) {
    			tgl_out = DateFormater.prepStringDateToDbInputFormat(tgl_out);
    		}
    		
    		
    		
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String sql = "INSERT INTO EXT_CIVITAS_DATA_DOSEN (KDPST,NPMHS,NODOS_LOCAL,GELAR_DEPAN,GELAR_BELAKANG,NIDNN,TIPE_ID,NOMOR_ID,STATUS,PT_S1,JURUSAN_S1,KDPST_S1,TKN_BIDANG_KEAHLIAN_S1,NOIJA_S1,FILE_IJA_S1,JUDUL_TA_S1,PT_S2,JURUSAN_S2,KDPST_S2,TKN_BIDANG_KEAHLIAN_S2,NOIJA_S2,FILE_IJA_S2,JUDUL_TA_S2,PT_S3,JURUSAN_S3,KDPST_S3,TKN_BIDANG_KEAHLIAN_S3,NOIJA_S3,FILE_IJA_S3,JUDUL_TA_S3,PT_PROF,JURUSAN_PROF,KDPST_PROF,TKN_BIDANG_KEAHLIAN_PROF,NOIJA_PROF,FILE_IJA_PROF,JUDUL_TA_PROF,TOTAL_KUM,JABATAN_AKADEMIK_DIKTI,JABATAN_AKADEMIK_LOCAL,JABATAN_STRUKTURAL,IKATAN_KERJA_DOSEN,TANGGAL_MULAI_KERJA,TANGGAL_KELUAR_KERJA,SERDOS,KDPTI_HOMEBASE,KDPST_HOMEBASE,EMAIL_INSTITUSI,PANGKAT_GOLONGAN)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        	stmt = con.prepareStatement(sql);
        	int i=1;
        	stmt.setString(i++, kdpst);
        	stmt.setString(i++, npmhs);
        	if(nodos==null || Checker.isStringNullOrEmpty(nodos)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, nodos);
        	}
        	if(glr_dpn==null || Checker.isStringNullOrEmpty(glr_dpn)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, glr_dpn);
        	}
        	if(glr_end==null || Checker.isStringNullOrEmpty(glr_end)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, glr_end);
        	}
        	if(nidnn==null || Checker.isStringNullOrEmpty(nidnn)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, nidnn);
        	}
        	if(tipe_id==null || Checker.isStringNullOrEmpty(tipe_id)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		if(no_id!=null && !Checker.isStringNullOrEmpty(no_id)) {
        			stmt.setString(i++, tipe_id);
        		}
        		else {
        			stmt.setNull(i++, java.sql.Types.VARCHAR);
        		}
        	}
        	if(no_id==null || Checker.isStringNullOrEmpty(no_id)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, no_id);
        	}
        	if(status==null || Checker.isStringNullOrEmpty(status)) {
        		stmt.setNull(i++, java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++, status);
        	}
       		if(pt_s1==null || Checker.isStringNullOrEmpty(pt_s1)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, pt_s1);
       		}
       		if(jur_s1==null || Checker.isStringNullOrEmpty(jur_s1)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, jur_s1);
       		}
       		if(kdpst_s1==null || Checker.isStringNullOrEmpty(kdpst_s1)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, kdpst_s1);
       		}
       		if(tkn_bid_ahli_s1==null || Checker.isStringNullOrEmpty(tkn_bid_ahli_s1)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, tkn_bid_ahli_s1);
       		}
       		if(noija_s1==null || Checker.isStringNullOrEmpty(noija_s1)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, noija_s1);
       		}
       		if(file_ija_s1==null || Checker.isStringNullOrEmpty(file_ija_s1)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, file_ija_s1);
       		}
       		if(jdl_ta_s1==null || Checker.isStringNullOrEmpty(jdl_ta_s1)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, jdl_ta_s1);
       		}
       		
       		if(pt_s2==null || Checker.isStringNullOrEmpty(pt_s2)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, pt_s2);
       		}
       		if(jur_s2==null || Checker.isStringNullOrEmpty(jur_s2)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, jur_s2);
       		}
       		if(kdpst_s2==null || Checker.isStringNullOrEmpty(kdpst_s2)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, kdpst_s2);
       		}
       		if(tkn_bid_ahli_s2==null || Checker.isStringNullOrEmpty(tkn_bid_ahli_s2)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, tkn_bid_ahli_s2);
       		}
       		if(noija_s2==null || Checker.isStringNullOrEmpty(noija_s2)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, noija_s2);
       		}
       		if(file_ija_s2==null || Checker.isStringNullOrEmpty(file_ija_s2)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, file_ija_s2);
       		}
       		if(jdl_ta_s2==null || Checker.isStringNullOrEmpty(jdl_ta_s2)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, jdl_ta_s2);
       		}
       		
       		if(pt_s3==null || Checker.isStringNullOrEmpty(pt_s3)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, pt_s3);
       		}
       		if(jur_s3==null || Checker.isStringNullOrEmpty(jur_s3)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, jur_s3);
       		}
       		if(kdpst_s3==null || Checker.isStringNullOrEmpty(kdpst_s3)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, kdpst_s3);
       		}
       		if(tkn_bid_ahli_s3==null || Checker.isStringNullOrEmpty(tkn_bid_ahli_s3)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, tkn_bid_ahli_s3);
       		}
       		if(noija_s3==null || Checker.isStringNullOrEmpty(noija_s3)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, noija_s3);
       		}
       		if(file_ija_s3==null || Checker.isStringNullOrEmpty(file_ija_s3)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, file_ija_s3);
       		}
       		if(jdl_ta_s3==null || Checker.isStringNullOrEmpty(jdl_ta_s3)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, jdl_ta_s3);
       		}

       		if(pt_prof==null || Checker.isStringNullOrEmpty(pt_prof)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, pt_prof);
       		}
       		if(jur_prof==null || Checker.isStringNullOrEmpty(jur_prof)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, jur_prof);
       		}
       		if(kdpst_prof==null || Checker.isStringNullOrEmpty(kdpst_prof)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, kdpst_prof);
       		}
       		if(tkn_bid_ahli_prof==null || Checker.isStringNullOrEmpty(tkn_bid_ahli_prof)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, tkn_bid_ahli_prof);
       		}
       		if(noija_prof==null || Checker.isStringNullOrEmpty(noija_prof)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, noija_prof);
       		}
       		if(file_ija_prof==null || Checker.isStringNullOrEmpty(file_ija_prof)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, file_ija_prof);
       		}
       		if(jdl_ta_prof==null || Checker.isStringNullOrEmpty(jdl_ta_prof)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, jdl_ta_prof);
       		}

       		if(tot_kum==null || Checker.isStringNullOrEmpty(tot_kum)) {
       			stmt.setNull(i++, java.sql.Types.INTEGER);
      		}
       		else {
       			stmt.setInt(i++, Integer.parseInt(tot_kum));
       		}
       		if(jbt_akdmk_dikti==null || Checker.isStringNullOrEmpty(jbt_akdmk_dikti)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, jbt_akdmk_dikti);
       		}
       		if(jbt_akdmk_local==null || Checker.isStringNullOrEmpty(jbt_akdmk_local)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, jbt_akdmk_local);
       		}
       		if(jbt_struk==null || Checker.isStringNullOrEmpty(jbt_struk)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, jbt_struk);
       		}
       		if(ika==null || Checker.isStringNullOrEmpty(ika)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, ika);
       		}
       		if(tgl_sta==null || Checker.isStringNullOrEmpty(tgl_sta)) {
       			stmt.setNull(i++, java.sql.Types.DATE);
       		}
       		else {
       			stmt.setDate(i++, java.sql.Date.valueOf(tgl_sta));
       		}
       		if(tgl_out==null || Checker.isStringNullOrEmpty(tgl_out)) {
       			stmt.setNull(i++, java.sql.Types.DATE);
       		}
       		else {
       			stmt.setDate(i++, java.sql.Date.valueOf(tgl_out));
       		}
       		
       		if(serdos==null || Checker.isStringNullOrEmpty(serdos)) {
       			stmt.setNull(i++, java.sql.Types.BOOLEAN);
       		}
       		else {
       			stmt.setBoolean(i++,Boolean.parseBoolean(serdos));
       		}
       		if(pt_home==null || Checker.isStringNullOrEmpty(pt_home)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, pt_home);
       		}
       		if(prodi_home==null || Checker.isStringNullOrEmpty(prodi_home)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, prodi_home);
       		}
       		if(email_inst==null || Checker.isStringNullOrEmpty(email_inst)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, email_inst);
       		}
       		if(pangkat_gol==null || Checker.isStringNullOrEmpty(pangkat_gol)) {
       			stmt.setNull(i++, java.sql.Types.VARCHAR);
       		}
       		else {
       			stmt.setString(i++, pangkat_gol);
       		}
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
    
    
    public int updateDataTableExtCivitasDosen(String kdpstDsn,String npmDsn,String statusDsn,String tipeIkaDsn,String jabStruk,String ptBase,String jja_loco,String baseProdi,String jja_dikti,String gol,String kum,String aspti1,String aspti2,String gelar1,String gelar2,String kdpst1,String kdpst2,String jur1,String jur2,String jur3,String jur4,String bidil1,String bidil2,String tglls1,String tglls2,String noija1,String noija2,String aspti3,String aspti4,String gelar3,String gelar4,String kdpst3,String kdpst4,String bidil3,String bidil4,String tglls3,String tglls4,String noija3,String noija4,String riwayat,String fileija1,String fileija2,String fileija3,String fileija4,String judul1,String judul2,String judul3,String judul4,String tgl_in,String tgl_out,String serdos,String email_loco,String frontTitle,String endTitle,String tipeKtp,String noKtp,String noNidnn,String tipeIdDsn,String noIdDsn,String kdjekmsmhs) {
    	int j=0;
    	try {
        	//String ipAddr =  request.getRemoteAddr();
    		if(riwayat!=null && !Checker.isStringNullOrEmpty(riwayat)) {
    			StringTokenizer st = new StringTokenizer(riwayat);
    			riwayat = "";
    			while(st.hasMoreTokens()) {
    				riwayat = riwayat+st.nextToken()+" ";
    			}
    		}
    		if(tgl_in!=null && !Checker.isStringNullOrEmpty(tgl_in)) {
    			tgl_in = DateFormater.prepStringDateToDbInputFormat(tgl_in);
    		}
    		if(tgl_out!=null && !Checker.isStringNullOrEmpty(tgl_out)) {
    			tgl_out = DateFormater.prepStringDateToDbInputFormat(tgl_out);
    		}
    		
    		if(tglls1!=null && !Checker.isStringNullOrEmpty(tglls1)) {
    			tglls1 = DateFormater.prepStringDateToDbInputFormat(tglls1);
    		}
    		if(tglls2!=null && !Checker.isStringNullOrEmpty(tglls2)) {
    			tglls2 = DateFormater.prepStringDateToDbInputFormat(tglls2);
    		}
    		if(tglls3!=null && !Checker.isStringNullOrEmpty(tglls3)) {
    			tglls3 = DateFormater.prepStringDateToDbInputFormat(tglls3);
    		}
    		if(tglls4!=null && !Checker.isStringNullOrEmpty(tglls4)) {
    			tglls4 = DateFormater.prepStringDateToDbInputFormat(tglls4);
    		}
    		
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//update table CIVITAS
        	stmt = con.prepareStatement("update CIVITAS set KDJEKMSMHS=? where NPMHSMSMHS=?");
        	stmt.setString(1, kdjekmsmhs);
        	stmt.setString(2, npmDsn);
        	stmt.executeUpdate();
        	
        	//lanjut ke tabel dosen
        	String sql_stmt = "UPDATE EXT_CIVITAS_DATA_DOSEN SET NODOS_LOCAL=?,GELAR_DEPAN=?,GELAR_BELAKANG=?,NIDNN=?,TIPE_ID=?,NOMOR_ID=?,STATUS=?,PT_S1=?,JURUSAN_S1=?,KDPST_S1=?,GELAR_S1=?,TKN_BIDANG_KEAHLIAN_S1=?,NOIJA_S1=?,TGLLS_S1=?,FILE_IJA_S1=?,JUDUL_TA_S1=?,PT_S2=?,JURUSAN_S2=?,KDPST_S2=?,GELAR_S2=?,TKN_BIDANG_KEAHLIAN_S2=?,NOIJA_S2=?,TGLLS_S2=?,FILE_IJA_S2=?,JUDUL_TA_S2=?,PT_S3=?,JURUSAN_S3=?,KDPST_S3=?,GELAR_S3=?,TKN_BIDANG_KEAHLIAN_S3=?,NOIJA_S3=?,TGLLS_S3=?,FILE_IJA_S3=?,JUDUL_TA_S3=?,PT_PROF=?,JURUSAN_PROF=?,KDPST_PROF=?,GELAR_PROF=?,TKN_BIDANG_KEAHLIAN_PROF=?,NOIJA_PROF=?,TGLLS_PROF=?,FILE_IJA_PROF=?,JUDUL_TA_PROF=?,TOTAL_KUM=?,JABATAN_AKADEMIK_DIKTI=?,JABATAN_AKADEMIK_LOCAL=?,JABATAN_STRUKTURAL=?,IKATAN_KERJA_DOSEN=?,TANGGAL_MULAI_KERJA=?,TANGGAL_KELUAR_KERJA=?,SERDOS=?,KDPTI_HOMEBASE=?,KDPST_HOMEBASE=?,EMAIL_INSTITUSI=?,PANGKAT_GOLONGAN=?,CATATAN_RIWAYAT=?,TIPE_KTP=?,NO_KTP=? where KDPST=? and NPMHS=?";
        	stmt = con.prepareStatement(sql_stmt);
        	int i=1;
        	
        	stmt.setNull(i++,java.sql.Types.VARCHAR);//NODOS_LOCAL,
        	//GELAR_DEPAN,
        	if(frontTitle==null||Checker.isStringNullOrEmpty(frontTitle)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,frontTitle);//GELAR_DEPAN,
        	}
        	//GELAR_BELAKANG,
        	if(endTitle==null||Checker.isStringNullOrEmpty(endTitle)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,endTitle);//GELAR_BELAKANG,
        	}
        	
        	//NIDNN,
        	if(noNidnn==null||Checker.isStringNullOrEmpty(noNidnn)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,noNidnn);//NIDNN,
        	}
        	
        	//tipeIdDsn
        	//TIPE_ID,
        	if(tipeIdDsn==null||Checker.isStringNullOrEmpty(tipeIdDsn)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,tipeIdDsn);//STATUS,
        	}
        
        	//NOMOR_ID,
        	if(noIdDsn==null||Checker.isStringNullOrEmpty(noIdDsn)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,noIdDsn);//STATUS,
        	}
        	if(statusDsn==null||Checker.isStringNullOrEmpty(statusDsn)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,statusDsn);//STATUS,
        	}
        	
        	if(aspti1==null||Checker.isStringNullOrEmpty(aspti1)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,aspti1);//PT_S1,
    		}
        	if(jur1==null||Checker.isStringNullOrEmpty(jur1)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,jur1);//JURUSAN_S1,
    		}
        	if(kdpst1==null||Checker.isStringNullOrEmpty(kdpst1)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,kdpst1);//KDPST_S1,
        	}
        	if(gelar1==null||Checker.isStringNullOrEmpty(gelar1)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,gelar1);//GELAR_S1,
			}
        	if(bidil1==null||Checker.isStringNullOrEmpty(bidil1)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,bidil1);//TKN_BIDANG_KEAHLIAN_S1,
        	}
        	if(noija1==null||Checker.isStringNullOrEmpty(noija1)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,noija1);//NOIJA_S1,
        	}
        	if(tglls1==null||Checker.isStringNullOrEmpty(tglls1)) {
        		stmt.setNull(i++,java.sql.Types.DATE);
        	}
        	else {
        		stmt.setDate(i++,java.sql.Date.valueOf(tglls1));//TGLLS_S1,
        	}
        	if(fileija1==null||Checker.isStringNullOrEmpty(fileija1)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,fileija1);//FILE_IJA_S1,
        	}
        	if(judul1==null||Checker.isStringNullOrEmpty(judul1)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,judul1);//JUDUL_TA_S1,
        	}
        	if(aspti2==null||Checker.isStringNullOrEmpty(aspti2)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,aspti2);//PT_S2,
        	}
        	if(jur2==null||Checker.isStringNullOrEmpty(jur2)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,jur2);//JURUSAN_S2,
        	}
        	if(kdpst2==null||Checker.isStringNullOrEmpty(kdpst2)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,kdpst2);//KDPST_S2,
        	}
        	if(gelar2==null||Checker.isStringNullOrEmpty(gelar2)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,gelar2);//GELAR_S2,
        	}
        	if(bidil2==null||Checker.isStringNullOrEmpty(bidil2)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,bidil2);//TKN_BIDANG_KEAHLIAN_S2,
        	}
        	if(noija2==null||Checker.isStringNullOrEmpty(noija2)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,noija2);//NOIJA_S2,
        	}
        	if(tglls2==null||Checker.isStringNullOrEmpty(tglls2)) {
        		stmt.setNull(i++,java.sql.Types.DATE);
        	}
        	else {
        		stmt.setDate(i++,java.sql.Date.valueOf(tglls2));//TGLLS_S2,
        	}
        	if(fileija2==null||Checker.isStringNullOrEmpty(fileija2)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,fileija2);//FILE_IJA_S2,
        	}
        	if(judul2==null||Checker.isStringNullOrEmpty(judul2)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,judul2);//JUDUL_TA_S2,
        	}
        	if(aspti3==null||Checker.isStringNullOrEmpty(aspti3)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,aspti3);//PT_S3,
        	}
        	if(jur3==null||Checker.isStringNullOrEmpty(jur3)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,jur3);////JURUSAN_S3,
        	}
        	if(kdpst3==null||Checker.isStringNullOrEmpty(kdpst3)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,kdpst3);//KDPST_S3,
        	}
        	if(gelar3==null||Checker.isStringNullOrEmpty(gelar3)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,gelar3);//GELAR_S3,
        	}
        	if(bidil3==null||Checker.isStringNullOrEmpty(bidil3)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,bidil3);//TKN_BIDANG_KEAHLIAN_S3,
        	}
        	if(noija3==null||Checker.isStringNullOrEmpty(noija3)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,noija3);//NOIJA_S3,
        	}
        	if(tglls3==null||Checker.isStringNullOrEmpty(tglls3)) {
        		stmt.setNull(i++,java.sql.Types.DATE);
        	}
        	else {
        		stmt.setDate(i++,java.sql.Date.valueOf(tglls3));//TGLLS_S3,
        	}
        	if(fileija3==null||Checker.isStringNullOrEmpty(fileija3)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,fileija3);//FILE_IJA_S3,
        	}
        	if(judul3==null||Checker.isStringNullOrEmpty(judul3)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,judul3);//JUDUL_TA_S3,
        	}
        	if(aspti4==null||Checker.isStringNullOrEmpty(aspti4)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,aspti4);//PT_PROF,
        	}
        	if(jur4==null||Checker.isStringNullOrEmpty(jur4)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,jur4);//JURUSAN_PROF,
        	}
        	if(kdpst4==null||Checker.isStringNullOrEmpty(kdpst4)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,kdpst4);//KDPST_PROF,
        	}
        	if(gelar4==null||Checker.isStringNullOrEmpty(gelar4)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,gelar4);//GELAR_PROF,
        	}
        	if(bidil4==null||Checker.isStringNullOrEmpty(bidil4)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,bidil4);//TKN_BIDANG_KEAHLIAN_PROF,
        	}
        	if(noija4==null||Checker.isStringNullOrEmpty(noija4)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,noija4);//NOIJA_PROF,
        	}
        	if(tglls4==null||Checker.isStringNullOrEmpty(tglls4)) {
        		stmt.setNull(i++,java.sql.Types.DATE);
        	}
        	else {
        		stmt.setDate(i++,java.sql.Date.valueOf(tglls4));//TGLLS_PROF,
        	}
        	if(fileija4==null||Checker.isStringNullOrEmpty(fileija4)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,fileija4);//FILE_IJA_PROF,
        	}
        	if(judul4==null||Checker.isStringNullOrEmpty(judul4)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,judul4);//JUDUL_TA_PROF,
        	}
        	if(kum==null||Checker.isStringNullOrEmpty(kum)) {
        		stmt.setNull(i++,java.sql.Types.INTEGER);
        	}
        	else {
        		stmt.setInt(i++,Integer.parseInt(kum));//TOTAL_KUM,
        	}
        	if(jja_dikti==null||Checker.isStringNullOrEmpty(jja_dikti)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	//String kdpstDsn,String npmDsn,String statusDsn,
        	//String tipeIkaDsn,String jabStruk,String ptBase,String jja_loco,String baseProdi,String jja_dikti,String gol,String kum,
        	//String aspti1,String aspti2,String gelar1,String gelar2,String kdpst1,String kdpst2,String bidil1,String bidil2,String tglls1,
        	//String tglls2,String noija1,String noija2,String aspti3,String aspti4,String gelar3,String gelar4,String kdpst3,String kdpst4,
        	//String bidil3,String bidil4,String tglls3,String tglls4,String noija3,String noija4,String riwayat
        	else {
        		stmt.setString(i++,jja_dikti);//JABATAN_AKADEMIK_DIKTI,
        	}
        	if(jja_loco==null||Checker.isStringNullOrEmpty(jja_loco)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,jja_loco);//JABATAN_AKADEMIK_LOCAL,
        	}
        	if(jabStruk==null||Checker.isStringNullOrEmpty(jabStruk)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,jabStruk);//JABATAN_STRUKTURAL,
        	}
        	if(tipeIkaDsn==null||Checker.isStringNullOrEmpty(tipeIkaDsn)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,tipeIkaDsn);//IKATAN_KERJA_DOSEN,
        	}
        	if(tgl_in==null||Checker.isStringNullOrEmpty(tgl_in)) {
        		stmt.setNull(i++,java.sql.Types.DATE);
        	}
        	else {
        		stmt.setDate(i++,java.sql.Date.valueOf(tgl_in));//TANGGAL_MULAI_KERJA,
        	}
        	if(tgl_out==null||Checker.isStringNullOrEmpty(tgl_out)) {
        		stmt.setNull(i++,java.sql.Types.DATE);
        	}
        	else {
        		stmt.setDate(i++,java.sql.Date.valueOf(tgl_out));//TANGGAL_KELUAR_KERJA,
        	}
        	if(serdos==null||Checker.isStringNullOrEmpty(serdos)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,serdos);//SERDOS,
        	}
        	if(ptBase==null||Checker.isStringNullOrEmpty(ptBase)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,ptBase);//KDPTI_HOMEBASE,
        	}
        	if(baseProdi==null||Checker.isStringNullOrEmpty(baseProdi)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,baseProdi);//KDPST_HOMEBASE,
        	}
        	if(email_loco==null||Checker.isStringNullOrEmpty(email_loco)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,email_loco);//EMAIL_INSTITUSI,
        	}
        	if(gol==null||Checker.isStringNullOrEmpty(gol)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,gol);//PANGKAT_GOLONGAN,
        	}
        	if(riwayat==null||Checker.isStringNullOrEmpty(riwayat)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,riwayat);//CATATAN_RIWAYAT
        	}
        	//tipe_ktp
        	if(tipeKtp==null||Checker.isStringNullOrEmpty(tipeKtp)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,tipeKtp);
        	}
        	//no_ktp
        	if(noKtp==null||Checker.isStringNullOrEmpty(noKtp)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,noKtp);
        	}
        	//where
        	stmt.setString(i++,kdpstDsn);
        	stmt.setString(i++,npmDsn);//NPMHS,
        	
        	j = stmt.executeUpdate();

        	//====================================================================================================================================
        	if(j<1) {
        	sql_stmt = "INSERT INTO EXT_CIVITAS_DATA_DOSEN (KDPST,NPMHS,NODOS_LOCAL,GELAR_DEPAN,GELAR_BELAKANG,NIDNN,TIPE_ID,NOMOR_ID,STATUS,PT_S1,JURUSAN_S1,KDPST_S1,GELAR_S1,TKN_BIDANG_KEAHLIAN_S1,NOIJA_S1,TGLLS_S1,FILE_IJA_S1,JUDUL_TA_S1,PT_S2,JURUSAN_S2,KDPST_S2,GELAR_S2,TKN_BIDANG_KEAHLIAN_S2,NOIJA_S2,TGLLS_S2,FILE_IJA_S2,JUDUL_TA_S2,PT_S3,JURUSAN_S3,KDPST_S3,GELAR_S3,TKN_BIDANG_KEAHLIAN_S3,NOIJA_S3,TGLLS_S3,FILE_IJA_S3,JUDUL_TA_S3,PT_PROF,JURUSAN_PROF,KDPST_PROF,GELAR_PROF,TKN_BIDANG_KEAHLIAN_PROF,NOIJA_PROF,TGLLS_PROF,FILE_IJA_PROF,JUDUL_TA_PROF,TOTAL_KUM,JABATAN_AKADEMIK_DIKTI,JABATAN_AKADEMIK_LOCAL,JABATAN_STRUKTURAL,IKATAN_KERJA_DOSEN,TANGGAL_MULAI_KERJA,TANGGAL_KELUAR_KERJA,SERDOS,KDPTI_HOMEBASE,KDPST_HOMEBASE,EMAIL_INSTITUSI,PANGKAT_GOLONGAN,CATATAN_RIWAYAT,TIPE_KTP,NO_KTP)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        	stmt = con.prepareStatement(sql_stmt);
        	i=1;
        	stmt.setString(i++,kdpstDsn);
        	stmt.setString(i++,npmDsn);//NPMHS,
        	stmt.setNull(i++,java.sql.Types.VARCHAR);//NODOS_LOCAL,
        	//GELAR_DEPAN,
        	if(frontTitle==null||Checker.isStringNullOrEmpty(frontTitle)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,frontTitle);//STATUS,
        	}
        	//GELAR_BELAKANG,
        	if(endTitle==null||Checker.isStringNullOrEmpty(endTitle)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,endTitle);//STATUS,
        	}
        	//NIDNN,
        	if(noNidnn==null||Checker.isStringNullOrEmpty(noNidnn)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,noNidnn);//STATUS,
        	}
        	//TIPE_ID,
        	if(tipeIdDsn==null||Checker.isStringNullOrEmpty(tipeIdDsn)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,tipeIdDsn);//STATUS,
        	}
        	//NOMOR_ID,
        	if(noIdDsn==null||Checker.isStringNullOrEmpty(noIdDsn)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,noIdDsn);//STATUS,
        	}
        	if(statusDsn==null||Checker.isStringNullOrEmpty(statusDsn)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,statusDsn);//STATUS,
        	}
        	
        	if(aspti1==null||Checker.isStringNullOrEmpty(aspti1)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,aspti1);//PT_S1,
    		}
        	if(jur1==null||Checker.isStringNullOrEmpty(jur1)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,jur1);//JURUSAN_S1,
    		}
        	if(kdpst1==null||Checker.isStringNullOrEmpty(kdpst1)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,kdpst1);//KDPST_S1,
        	}
        	if(gelar1==null||Checker.isStringNullOrEmpty(gelar1)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,gelar1);//GELAR_S1,
			}
        	if(bidil1==null||Checker.isStringNullOrEmpty(bidil1)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,bidil1);//TKN_BIDANG_KEAHLIAN_S1,
        	}
        	if(noija1==null||Checker.isStringNullOrEmpty(noija1)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,noija1);//NOIJA_S1,
        	}
        	if(tglls1==null||Checker.isStringNullOrEmpty(tglls1)) {
        		stmt.setNull(i++,java.sql.Types.DATE);
        	}
        	else {
        		stmt.setDate(i++,java.sql.Date.valueOf(tglls1));//TGLLS_S1,
        	}
        	if(fileija1==null||Checker.isStringNullOrEmpty(fileija1)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,fileija1);//FILE_IJA_S1,
        	}
        	if(judul1==null||Checker.isStringNullOrEmpty(judul1)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,judul1);//JUDUL_TA_S1,
        	}
        	if(aspti2==null||Checker.isStringNullOrEmpty(aspti2)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,aspti2);//PT_S2,
        	}
        	if(jur2==null||Checker.isStringNullOrEmpty(jur2)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,jur2);//JURUSAN_S2,
        	}
        	if(kdpst2==null||Checker.isStringNullOrEmpty(kdpst2)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,kdpst2);//KDPST_S2,
        	}
        	if(gelar2==null||Checker.isStringNullOrEmpty(gelar2)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,gelar2);//GELAR_S2,
        	}
        	if(bidil2==null||Checker.isStringNullOrEmpty(bidil2)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,bidil2);//TKN_BIDANG_KEAHLIAN_S2,
        	}
        	if(noija2==null||Checker.isStringNullOrEmpty(noija2)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,noija2);//NOIJA_S2,
        	}
        	if(tglls2==null||Checker.isStringNullOrEmpty(tglls2)) {
        		stmt.setNull(i++,java.sql.Types.DATE);
        	}
        	else {
        		stmt.setDate(i++,java.sql.Date.valueOf(tglls2));//TGLLS_S2,
        	}
        	if(fileija2==null||Checker.isStringNullOrEmpty(fileija2)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,fileija2);//FILE_IJA_S2,
        	}
        	if(judul2==null||Checker.isStringNullOrEmpty(judul2)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,judul2);//JUDUL_TA_S2,
        	}
        	if(aspti3==null||Checker.isStringNullOrEmpty(aspti3)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,aspti3);//PT_S3,
        	}
        	if(jur3==null||Checker.isStringNullOrEmpty(jur3)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,jur3);////JURUSAN_S3,
        	}
        	if(kdpst3==null||Checker.isStringNullOrEmpty(kdpst3)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,kdpst3);//KDPST_S3,
        	}
        	if(gelar3==null||Checker.isStringNullOrEmpty(gelar3)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,gelar3);//GELAR_S3,
        	}
        	if(bidil3==null||Checker.isStringNullOrEmpty(bidil3)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,bidil3);//TKN_BIDANG_KEAHLIAN_S3,
        	}
        	if(noija3==null||Checker.isStringNullOrEmpty(noija3)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,noija3);//NOIJA_S3,
        	}
        	if(tglls3==null||Checker.isStringNullOrEmpty(tglls3)) {
        		stmt.setNull(i++,java.sql.Types.DATE);
        	}
        	else {
        		stmt.setDate(i++,java.sql.Date.valueOf(tglls3));//TGLLS_S3,
        	}
        	if(fileija3==null||Checker.isStringNullOrEmpty(fileija3)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,fileija3);//FILE_IJA_S3,
        	}
        	if(judul3==null||Checker.isStringNullOrEmpty(judul3)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,judul3);//JUDUL_TA_S3,
        	}
        	if(aspti4==null||Checker.isStringNullOrEmpty(aspti4)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,aspti4);//PT_PROF,
        	}
        	if(jur4==null||Checker.isStringNullOrEmpty(jur4)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,jur4);//JURUSAN_PROF,
        	}
        	if(kdpst4==null||Checker.isStringNullOrEmpty(kdpst4)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,kdpst4);//KDPST_PROF,
        	}
        	if(gelar4==null||Checker.isStringNullOrEmpty(gelar4)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,gelar4);//GELAR_PROF,
        	}
        	if(bidil4==null||Checker.isStringNullOrEmpty(bidil4)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,bidil4);//TKN_BIDANG_KEAHLIAN_PROF,
        	}
        	if(noija4==null||Checker.isStringNullOrEmpty(noija4)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,noija4);//NOIJA_PROF,
        	}
        	if(tglls4==null||Checker.isStringNullOrEmpty(tglls4)) {
        		stmt.setNull(i++,java.sql.Types.DATE);
        	}
        	else {
        		stmt.setDate(i++,java.sql.Date.valueOf(tglls4));//TGLLS_PROF,
        	}
        	if(fileija4==null||Checker.isStringNullOrEmpty(fileija4)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,fileija4);//FILE_IJA_PROF,
        	}
        	if(judul4==null||Checker.isStringNullOrEmpty(judul4)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,judul4);//JUDUL_TA_PROF,
        	}
        	if(kum==null||Checker.isStringNullOrEmpty(kum)) {
        		stmt.setNull(i++,java.sql.Types.INTEGER);
        	}
        	else {
        		stmt.setInt(i++,Integer.parseInt(kum));//TOTAL_KUM,
        	}
        	if(jja_dikti==null||Checker.isStringNullOrEmpty(jja_dikti)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	//String kdpstDsn,String npmDsn,String statusDsn,
        	//String tipeIkaDsn,String jabStruk,String ptBase,String jja_loco,String baseProdi,String jja_dikti,String gol,String kum,
        	//String aspti1,String aspti2,String gelar1,String gelar2,String kdpst1,String kdpst2,String bidil1,String bidil2,String tglls1,
        	//String tglls2,String noija1,String noija2,String aspti3,String aspti4,String gelar3,String gelar4,String kdpst3,String kdpst4,
        	//String bidil3,String bidil4,String tglls3,String tglls4,String noija3,String noija4,String riwayat
        	else {
        		stmt.setString(i++,jja_dikti);//JABATAN_AKADEMIK_DIKTI,
        	}
        	if(jja_loco==null||Checker.isStringNullOrEmpty(jja_loco)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,jja_loco);//JABATAN_AKADEMIK_LOCAL,
        	}
        	if(jabStruk==null||Checker.isStringNullOrEmpty(jabStruk)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,jabStruk);//JABATAN_STRUKTURAL,
        	}
        	if(tipeIkaDsn==null||Checker.isStringNullOrEmpty(tipeIkaDsn)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,tipeIkaDsn);//IKATAN_KERJA_DOSEN,
        	}
        	if(tgl_in==null||Checker.isStringNullOrEmpty(tgl_in)) {
        		stmt.setNull(i++,java.sql.Types.DATE);
        	}
        	else {
        		stmt.setDate(i++,java.sql.Date.valueOf(tgl_in));//TANGGAL_MULAI_KERJA,
        	}
        	if(tgl_out==null||Checker.isStringNullOrEmpty(tgl_out)) {
        		stmt.setNull(i++,java.sql.Types.DATE);
        	}
        	else {
        		stmt.setDate(i++,java.sql.Date.valueOf(tgl_out));//TANGGAL_KELUAR_KERJA,
        	}
        	if(serdos==null||Checker.isStringNullOrEmpty(serdos)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,serdos);//SERDOS,
        	}
        	if(ptBase==null||Checker.isStringNullOrEmpty(ptBase)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,ptBase);//KDPTI_HOMEBASE,
        	}
        	if(baseProdi==null||Checker.isStringNullOrEmpty(baseProdi)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,baseProdi);//KDPST_HOMEBASE,
        	}
        	if(email_loco==null||Checker.isStringNullOrEmpty(email_loco)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,email_loco);//EMAIL_INSTITUSI,
        	}
        	if(gol==null||Checker.isStringNullOrEmpty(gol)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,gol);//PANGKAT_GOLONGAN,
        	}
        	if(riwayat==null||Checker.isStringNullOrEmpty(riwayat)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,riwayat);//CATATAN_RIWAYAT
        	}
        	//tipe_ktp
        	if(tipeKtp==null||Checker.isStringNullOrEmpty(tipeKtp)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,tipeKtp);
        	}
        	//no_ktp
        	if(noKtp==null||Checker.isStringNullOrEmpty(noKtp)) {
        		stmt.setNull(i++,java.sql.Types.VARCHAR);
        	}
        	else {
        		stmt.setString(i++,noKtp);
        	}
        	j = stmt.executeUpdate();
        	}//end if
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
}
