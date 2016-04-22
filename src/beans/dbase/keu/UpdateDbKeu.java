package beans.dbase.keu;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ListIterator;
import beans.dbase.UpdateDb;
import beans.setting.Constants;
import beans.sistem.AskSystem;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Getter;
import beans.tools.DateFormater;
import beans.tools.Tool;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.EncodingException;
import java.sql.Timestamp;
/**
 * Session Bean implementation class UpdateDbKeu
 */
@Stateless
@LocalBean
public class UpdateDbKeu extends UpdateDb {
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
    public UpdateDbKeu() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbKeu(String operatorNpm) {
        super(operatorNpm);
        // TODO Auto-generated constructor stub
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
    }
    
    
    public int insertPymntTransitTableForm2WithBukti(String objId,String fwdPg,String obj_lvl,String kdpst,String nmm,String npm,String tipeForm,String namaPenyetor,String besaran,String tglTrans,String angsuranKe,String gelombangKe, String biayaJaketAlmamater, String bankAcc, String namaFileBuktiSetoran, String sumberDana) {
    	int ins = 0;
    	//cek jika ada pembayaran lainnta : jakaet almamater
		//bila ada maka persiapkan no group transakasi
    	System.out.println("namaFileBuktiSetoran="+namaFileBuktiSetoran);
    	//sumberDana tidak perlu ditest krn pasti ada isinya - via select-option
    	boolean groupMode = false;
    	double hargaJaket = 0;
    	long lastGroupId = 0;
    	//if(namaFileBuktiSetoran==null || new StringTokenizer(namaFileBuktiSetoran).countTokens()<1) {
    	//	System.out.println("tinggal masalah operator dan mahasiswa");
    	//} else 
    	if((besaran!=null && !Checker.isStringNullOrEmpty(besaran))&&(biayaJaketAlmamater!=null && !Checker.isStringNullOrEmpty(biayaJaketAlmamater))) {
			try {
				hargaJaket = Double.parseDouble(biayaJaketAlmamater);
				double testBenerBilangan = Double.parseDouble(besaran);
				groupMode = true;
			}
			catch(Exception e) {
				//tidak ada biaya jaket
				//hargaJaket=0;
			}
		}
		else {//pastikan harga jaket harus ke input
			try {
				hargaJaket = Double.parseDouble(biayaJaketAlmamater);
			}
			catch(Exception e) {
			//tidak ada biaya jaket
				hargaJaket=0;
			}
		}
    	//String defaultAccount = null;
    	tglTrans = DateFormater.convertFromDdMmYyToYyMmDd(tglTrans);
    	String nokod = null;
    	java.util.Date date= new java.util.Date();
		String tmp = ""+(new Timestamp(date.getTime()));
		StringTokenizer st = new StringTokenizer(tmp);
		while(st.hasMoreTokens()) {
			tmp = st.nextToken();
		}
		tmp = tmp.replaceAll(":","");
		tmp = tmp.replaceAll("\\.","");
		nokod=""+tmp;
    	try {
    		long norut =1+getNoRutTerakhirAtPymntTransitTabel();
    		String kode_kampus = "";
    		//System.out.println("norut ="+norut);
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		if(groupMode) {
    			lastGroupId =getNoGroupIdTerakhirAtPymntTransitTabel();
    			
    			/*
        		 * tambahan nov 2014 untuk colomn 
        		 * kode kampus domisili	
        		 */
        		stmt = con.prepareStatement("select KODE_KAMPUS_DOMISILI  from OBJECT where ID_OBJ=?");
        		stmt.setLong(1, Long.parseLong(objId));
        		rs = stmt.executeQuery();
        		rs.next();
        		kode_kampus = ""+rs.getString("KODE_KAMPUS_DOMISILI");
    			
    			stmt = con.prepareStatement("insert into PYMNT_TRANSIT(KDPSTPYMNT,NPMHSPYMNT,NORUTPYMNT,TGKUIPYMNT,TGTRSPYMNT,KETER_PYMNT_DETAIL,PAYEEPYMNT,AMONTPYMNT,PYMTPYMNT,GELOMBANG,CICILAN,KRS,NOACCPYMNT,OPNPMPYMNT,OPNMMPYMNT,SETORPYMNT,NOKODPYMNT,FILENAME,UPLOADTM,GROUP_ID,IDPAKETBEASISWA,ID_OBJ,KODE_KAMPUS_DOMISILI) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        		
    			//1.insert dpp
    			int i=1;
        		//KDPSTPYMNT
        		stmt.setString(i++,kdpst);//1
        		
        		//NPMHSPYMNT
        		stmt.setString(i++,npm);//2
        		
        		//NORUTPYMNT,
        		stmt.setLong(i++,norut);//3
        		
        		//TGKUIPYMNT,
        		date = new java.util.Date();
        		java.sql.Date todayDate = new java.sql.Date( date.getTime() );
        		stmt.setDate(i++, todayDate);//4
        		
        		//TGTRSPYMNT,
        		try {
        			tglTrans = tglTrans.replace("/", "-");
        			StringTokenizer st_ = new StringTokenizer(tglTrans,"-");
        			if(st_.countTokens()==3) {
        				String token1 = st_.nextToken();
        				String token2 = st_.nextToken();
        				String token3 = st_.nextToken();
        				if(token1.length()==4) {
        					tglTrans = token1+"-"+token2+"-"+token3;
        				}
        				else {
        					if(token3.length()==4) {
        						tglTrans = token3+"-"+token2+"-"+token1;
        					}	
        				}	
        			}
        		
        			java.sql.Date trsdt = java.sql.Date.valueOf(tglTrans);
        			stmt.setDate(i++,trsdt);//5
        		}
        		catch(Exception e) {
        			stmt.setDate(i++, todayDate);
        		}
        		
        		//KETERPYMNT set null diganti keter detail
        		//stmt.setNull(i++,java.sql.Types.VARCHAR);//6
        		
        		//KETER_PYMNT_DETAIL-,
        		String keter = "Pembayaran biaya DPP angsuran ke-"+angsuranKe+", gelombang ke-"+gelombangKe;
        		stmt.setString(i++,keter);//7
        		
        		//PAYEEPYMNT,
        		stmt.setString(i++, namaPenyetor);//8
        		
        		//AMONTPYMNT,
        		stmt.setDouble(i++, Double.valueOf(besaran).doubleValue());//9
        		
        		//PYMTPYMNT,-
        		stmt.setString(i++,"DPP");//10
        		
        		//GELOMBANG-,-
        		stmt.setInt(i++,Integer.valueOf(gelombangKe).intValue());//11
        		
        		//CICILAN-,-
        		stmt.setInt(i++,Integer.valueOf(angsuranKe).intValue());//12
        		
        		//KRS-,
        		stmt.setInt(i++,0);//12
        		
        		//NOACCPYMNT,
        		try {
        			java.sql.Date trsdt = java.sql.Date.valueOf(tglTrans);
        			stmt.setString(i++,bankAcc);//11
        		}
        		catch(Exception e) {
        			stmt.setString(i++, "Kas Pusat");//11
        		}
        		
        		//OPNPMPYMNT,
        		stmt.setString(i++,this.operatorNpm);//11his
        		
        		//OPNMMPYMNT,
        		stmt.setString(i++,this.operatorNmm);//12
        		
        		//SETORPYMNT
        		try {
        			java.sql.Date trsdt = java.sql.Date.valueOf(tglTrans);
        			stmt.setBoolean(i++,true);//13
        		}
        		catch(Exception e) {
        			stmt.setBoolean(i++, false);//13
        		}
        		
        		//,NOKODPYMNT,
        		stmt.setString(i++,nokod);//14
        		
        		//-FILENAME,
        		stmt.setString(i++,namaFileBuktiSetoran);
        		
        		//UPLOADTM-
        		stmt.setTimestamp(i++, AskSystem.getCurrentTimestamp());
        		
        		stmt.setLong(i++,lastGroupId+1);//14
        		
        		//sumberDana
        		stmt.setLong(i++,Long.parseLong(sumberDana));//15
        	//id obj akun terbayar
            	stmt.setLong(i++,Long.parseLong(objId));//16
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(i++,java.sql.Types.VARCHAR);//17
            	}
            	else {
            		stmt.setString(i++,kode_kampus);//17	
            	}
				ins = stmt.executeUpdate();
        		
        		
        		//2.jass almamater
    			i=1;
        		//KDPSTPYMNT
        		stmt.setString(i++,kdpst);//1
        		
        		//NPMHSPYMNT
        		stmt.setString(i++,npm);//2
        		
        		//NORUTPYMNT,
        		stmt.setLong(i++,norut);//3
        		
        		//TGKUIPYMNT,
        		date = new java.util.Date();
        		todayDate = new java.sql.Date( date.getTime() );
        		stmt.setDate(i++, todayDate);//4
        		
        		//TGTRSPYMNT,
        		try {
        			java.sql.Date trsdt = java.sql.Date.valueOf(tglTrans);
        			stmt.setDate(i++,trsdt);//5
        		}
        		catch(Exception e) {
        			stmt.setDate(i++, todayDate);
        		}
        		
        		//KETERPYMNT set null diganti keter detail
        		//stmt.setNull(i++,java.sql.Types.VARCHAR);//6
        		
        		//KETER_PYMNT_DETAIL-,
        		keter = "Pembayaran Jaket Almamater";
        		stmt.setString(i++,keter);//7
        		
        		//PAYEEPYMNT,
        		stmt.setString(i++, namaPenyetor);//8
        		
        		//AMONTPYMNT,
        		stmt.setDouble(i++, Double.valueOf(hargaJaket).doubleValue());//9
        		
        		//PYMTPYMNT,-
        		stmt.setString(i++,"JKT");//10 sesuai table pos_Revenue
        		
        		//GELOMBANG-,-
        		//stmt.setInt(i++,Integer.valueOf(gelombangKe).intValue());//11
        		stmt.setNull(i++,java.sql.Types.INTEGER);//11
        		
        		//CICILAN-,-
        		//stmt.setInt(i++,Integer.valueOf(angsuranKe).intValue());//12
        		stmt.setNull(i++,java.sql.Types.INTEGER);//12
        		
        		//KRS-,
        		//stmt.setInt(i++,0);//12
        		stmt.setNull(i++,java.sql.Types.INTEGER);
        		
        		//NOACCPYMNT,
        		try {
        			java.sql.Date trsdt = java.sql.Date.valueOf(tglTrans);
        			stmt.setString(i++,bankAcc);//11
        		}
        		catch(Exception e) {
        			stmt.setString(i++, "Kas Pusat");//11
        		}
        		
        		//OPNPMPYMNT,
        		stmt.setString(i++,this.operatorNpm);//11his
        		
        		//OPNMMPYMNT,
        		stmt.setString(i++,this.operatorNmm);//12
        		
        		//SETORPYMNT
        		try {
        			java.sql.Date trsdt = java.sql.Date.valueOf(tglTrans);
        			stmt.setBoolean(i++,true);//13
        		}
        		catch(Exception e) {
        			stmt.setBoolean(i++, false);//13
        		}
        		
        		//,NOKODPYMNT,
        		stmt.setString(i++,nokod);//14
        		
        		//-FILENAME,
        		stmt.setString(i++,namaFileBuktiSetoran);
        		
        		//UPLOADTM-
        		stmt.setTimestamp(i++, AskSystem.getCurrentTimestamp());
        		
        		//group id
        		stmt.setLong(i++,lastGroupId+1);//14
        		
        		
        		//sumberDana
        		stmt.setLong(i++,Long.parseLong(sumberDana));//15
        		
        		//id obj akun terbayar
            	stmt.setLong(i++,Long.parseLong(objId));//16
            	//kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(i++,java.sql.Types.VARCHAR);//17
            	}
            	else {
            		stmt.setString(i++,kode_kampus);//17	
            	}
				
        		ins = stmt.executeUpdate();
    			
    		}
    		else {
    			//non group mode
    			stmt = con.prepareStatement("insert into PYMNT_TRANSIT(KDPSTPYMNT,NPMHSPYMNT,NORUTPYMNT,TGKUIPYMNT,TGTRSPYMNT,KETER_PYMNT_DETAIL,PAYEEPYMNT,AMONTPYMNT,PYMTPYMNT,GELOMBANG,CICILAN,KRS,NOACCPYMNT,OPNPMPYMNT,OPNMMPYMNT,SETORPYMNT,NOKODPYMNT,FILENAME,UPLOADTM,IDPAKETBEASISWA,ID_OBJ,KODE_KAMPUS_DOMISILI) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    			int i=1;
    			//System.out.println("groupMode="+groupMode);
    			//System.out.println("hargaJaket="+hargaJaket);
    			//System.out.println("besaran="+besaran);
    			if(hargaJaket<1) {
    				
            		//KDPSTPYMNT
            		//System.out.println(i+","+kdpst);
            		stmt.setString(i++,kdpst);//1
            		
            		//NPMHSPYMNT
            		//System.out.println(i+","+npm);
            		stmt.setString(i++,npm);//2
            		//NORUTPYMNT,
            		//System.out.println(i+","+norut);
            		stmt.setLong(i++,norut);//3
            		//TGKUIPYMNT,
            		
            		date = new java.util.Date();
            		java.sql.Date todayDate = new java.sql.Date( date.getTime() );
            		//System.out.println(i+","+todayDate);
            		stmt.setDate(i++, todayDate);//4
            		//TGTRSPYMNT,
            		
            		try {
            			
            			java.sql.Date trsdt = java.sql.Date.valueOf(tglTrans);
            			//System.out.println(i+","+tglTrans);
            			stmt.setDate(i++,trsdt);//5
            			////System.out.println("5a");
            		}
            		catch(Exception e) {
            			//System.out.println(i+","+todayDate);
            			stmt.setDate(i++, todayDate);
            		}
            		
            		//KETERPYMNT set null diganti keter detail
            		String keter = "Pembayaran biaya DPP angsuran ke-"+angsuranKe+", gelombang ke-"+gelombangKe;
            		stmt.setString(i++,keter);//6
            		//PAYEEPYMNT,
            		stmt.setString(i++, namaPenyetor);//7
            		//AMONTPYMNT,
            		stmt.setDouble(i++, Double.valueOf(besaran).doubleValue());//8
            		//PYMTPYMNT,-
            		stmt.setString(i++,"DPP");//9
            		//GELOMBANG-,-
            		stmt.setInt(i++,Integer.valueOf(gelombangKe).intValue());//10
            		//CICILAN-,-
            		stmt.setInt(i++,Integer.valueOf(angsuranKe).intValue());//11
            		//KRS-,
            		stmt.setInt(i++,0);//12
            		//NOACCPYMNT,
            		try {
            			//System.out.println(i+","+kdpst);
            			//java.sql.Date trsdt = java.sql.Date.valueOf(tglTrans);
            			stmt.setString(i++,bankAcc);//13
            			//System.out.println(i+","+bankAcc);
            		}
            		catch(Exception e) {
            		
            			stmt.setString(i++, "TUNAI");//13
            			//System.out.println(i+",TUNAI");
            		}
            		//OPNPMPYMNT,
            		stmt.setString(i++,this.operatorNpm);//14his
            		//OPNMMPYMNT,
            		stmt.setString(i++,this.operatorNmm);//15
            		//SETORPYMNT
            		try {
            			//System.out.println(i+","+kdpst);
            			java.sql.Date trsdt = java.sql.Date.valueOf(tglTrans);
            			stmt.setBoolean(i++,true);//16
            		}
            		catch(Exception e) {
            			//System.out.println(i+","+kdpst);
            			stmt.setBoolean(i++, false);//16
            		}
            		//,NOKODPYMNT,
            		stmt.setString(i++,nokod);//17
            		//-FILENAME,
            		stmt.setString(i++,namaFileBuktiSetoran);//18
            		//UPLOADTM-
            		stmt.setTimestamp(i++, AskSystem.getCurrentTimestamp());//19
            		//sumberDana
            		stmt.setLong(i++,Long.parseLong(sumberDana));//15
            		
            		//id obj akun terbayar
                	stmt.setLong(i++,Long.parseLong(objId));//16
                	//kodekampus
                	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
                		stmt.setNull(i++,java.sql.Types.VARCHAR);//17
                	}
                	else {
                		stmt.setString(i++,kode_kampus);//17	
                	}
    				
            		ins = stmt.executeUpdate();
    			}
    			else if(hargaJaket>0) {
    				//KDPSTPYMNT
            		stmt.setString(i++,kdpst);//1
            		
            		//NPMHSPYMNT
            		stmt.setString(i++,npm);//2
            		
            		//NORUTPYMNT,
            		stmt.setLong(i++,norut);//3
            		
            		//TGKUIPYMNT,
            		date = new java.util.Date();
            		java.sql.Date todayDate = new java.sql.Date( date.getTime() );
            		stmt.setDate(i++, todayDate);//4
            		
            		//TGTRSPYMNT,
            		try {
            			java.sql.Date trsdt = java.sql.Date.valueOf(tglTrans);
            			stmt.setDate(i++,trsdt);//5
            		}
            		catch(Exception e) {
            			stmt.setDate(i++, todayDate);
            		}
            		
            		//KETERPYMNT set null diganti keter detail
            		//stmt.setNull(i++,java.sql.Types.VARCHAR);//6
            		
            		//KETER_PYMNT_DETAIL-,
            		String keter = "Pembayaran Jaket Almamater";
            		stmt.setString(i++,keter);//6
            		
            		//PAYEEPYMNT,
            		stmt.setString(i++, namaPenyetor);//7
            		
            		//AMONTPYMNT,
            		stmt.setDouble(i++, Double.valueOf(hargaJaket).doubleValue());//8
            		
            		//PYMTPYMNT,-
            		stmt.setString(i++,"JKT");//9 sesuai table pos_Revenue
            		
            		//GELOMBANG-,-
            		//stmt.setInt(i++,Integer.valueOf(gelombangKe).intValue());//11
            		stmt.setNull(i++,java.sql.Types.INTEGER);//10
            		
            		//CICILAN-,-
            		//stmt.setInt(i++,Integer.valueOf(angsuranKe).intValue());//12
            		stmt.setNull(i++,java.sql.Types.INTEGER);//11
            		
            		//KRS-,
            		//stmt.setInt(i++,0);//12
            		stmt.setNull(i++,java.sql.Types.INTEGER);//12
            		
            		//NOACCPYMNT,
            		try {
            			java.sql.Date trsdt = java.sql.Date.valueOf(tglTrans);
            			stmt.setString(i++,bankAcc);//13
            		}
            		catch(Exception e) {
            			stmt.setString(i++, "Kas Pusat");//13
            		}
            		
            		//OPNPMPYMNT,
            		stmt.setString(i++,this.operatorNpm);//14
            		
            		//OPNMMPYMNT,
            		stmt.setString(i++,this.operatorNmm);//15
            		
            		//SETORPYMNT
            		try {
            			java.sql.Date trsdt = java.sql.Date.valueOf(tglTrans);
            			stmt.setBoolean(i++,true);//16
            		}
            		catch(Exception e) {
            			stmt.setBoolean(i++, false);//16
            		}
            		
            		//,NOKODPYMNT,
            		stmt.setString(i++,nokod);//17
            		
            		//-FILENAME,
            		stmt.setString(i++,namaFileBuktiSetoran);
            		
            		//UPLOADTM-
            		stmt.setTimestamp(i++, AskSystem.getCurrentTimestamp());
            		
            		//group id
            	//	stmt.setLong(i++,lastGroupId+1);//14
            		
            	//sumberDana
            		stmt.setLong(i++,Long.parseLong(sumberDana));//15
            		//id obj akun terbayar
                	stmt.setLong(i++,Long.parseLong(objId));//16
                //kodekampus
                	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
                		stmt.setNull(i++,java.sql.Types.VARCHAR);//17
                	}
                	else {
                		stmt.setString(i++,kode_kampus);//17	
                	}
    				ins = stmt.executeUpdate();
            	
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
    	boolean sukses = false;
    	if(ins>0) {
    		sukses = true;
    	}
    	return ins;	
    }

        
	
    public int insertPymntTransitTableForm3WithBukti(String tglTrans,String namaFileBuktiSetoran,String namaPenyetor, String objId,String fwdPg,String obj_lvl,String kdpst,String nmm,String npm,String tipeForm,String bppKe,String besaranBpp,String pendaftaranSmsKe,String besaranHeregistrasi,String totSks,String sksSmsKe,String biayaSks,String biayaDkm,String biayaPraktik,String biayaBimbinganSkripsi,String biayaUjianSkripsi,String biayaSumbanganBuku,String biayaJurnal,String biayaIjazah,String biayaWisuda, String biayaBinaan, String biayaKp, String sumberDana, String biayaAdmBank) {
    //KUIIDPYMNT`,KDPSTPYMNT`,NPMHSPYMNT`,NORUTPYMNT`,TGKUIPYMNT`,TGTRSPYMNT`,
    //PAYEEPYMNT`,OPNPMPYMNT`,OPNMMPYMNT`,SETORPYMNT`,NONPMPYMNT`,NOKODPYMNT`,UPDTMPYMNT`,FILENAME,UPLOADTM,GROUP_ID`)
    //iter	
    //KETERPYMNT`,KETER_PYMNT_DETAIL`,AMONTPYMNT`,PYMTPYMNT`,GELOMBANG`,CICILAN`,KRS`,SMS`,NOACCPYMNT`
    	
    	
    	//	
    	//bank account = acc-pusat / Universitas Satyagama
    	JSONObject jObj = null;
    	String target_shift = null;
    	JSONArray jArray = null;
    	try {
    		jArray = Getter.readJsonArrayFromUrl("/v1/mhs/"+npm+"/shift");
    		if(jArray.length()>0) {
        		JSONObject job = jArray.getJSONObject(0);
        		target_shift = job.getString("KETERANGAN");
        		if(target_shift.contains("&#x2f;")) {
        			target_shift = Constants.getDefaultShift();
        		}
        	}
        	else {
        		target_shift = Constants.getDefaultShift();
        	}
    		//target_shift = target_shift.replace("&#x2f;", "/");
    	}
    	catch(IOException e) {
    		e.printStackTrace();
    	}
    	catch(JSONException e) {
    		e.printStackTrace();
    	}
    	//System.out.println("1aa.targetShift = "+target_shift);
    	
    	int ins = 0;
    	boolean groupMode = false;
    	double hargaJaket = 0;
    	long lastGroupId = 0;
		String bankAcc = null;
		//tentukan bila ada lebih dari satu item maka jadi groupmode
		int item=0;	
		try {
			double testerAja = 0;

			if(besaranBpp!=null && !Checker.isStringNullOrEmpty(besaranBpp)) {
				testerAja = Double.parseDouble(besaranBpp);
				if(testerAja>=1) {
					item++;	
				}
			}

			if(besaranHeregistrasi!=null && !Checker.isStringNullOrEmpty(besaranHeregistrasi)) {
				testerAja = Double.parseDouble(besaranHeregistrasi);
				if(testerAja>=1) {
					item++;	
				}
			}

			if(biayaSks!=null && !Checker.isStringNullOrEmpty(biayaSks)) {
				testerAja = Double.parseDouble(biayaSks);
				if(testerAja>=1) {
					item++;	
				}
			}

			if(biayaDkm!=null && !Checker.isStringNullOrEmpty(biayaDkm)) {
				testerAja = Double.parseDouble(biayaDkm);
				if(testerAja>=1) {
					item++;	
				}
			}

			if(biayaPraktik!=null && !Checker.isStringNullOrEmpty(biayaPraktik)) {
				testerAja = Double.parseDouble(biayaPraktik);
				if(testerAja>=1) {
					item++;	
				}
			}

			if(biayaBimbinganSkripsi!=null && !Checker.isStringNullOrEmpty(biayaBimbinganSkripsi)) {
				testerAja = Double.parseDouble(biayaBimbinganSkripsi);
				if(testerAja>=1) {
					item++;	
				}
			}

			if(biayaUjianSkripsi!=null && !Checker.isStringNullOrEmpty(biayaUjianSkripsi)) {
				testerAja = Double.parseDouble(biayaUjianSkripsi);
				if(testerAja>=1) {
					item++;	
				}
			}

			if(biayaSumbanganBuku!=null && !Checker.isStringNullOrEmpty(biayaSumbanganBuku)) {
				testerAja = Double.parseDouble(biayaSumbanganBuku);
				if(testerAja>=1) {
					item++;	
				}
			}

			if(biayaJurnal!=null && !Checker.isStringNullOrEmpty(biayaJurnal)) {
				testerAja = Double.parseDouble(biayaJurnal);
				if(testerAja>=1) {
					item++;	
				}
			}

			if(biayaIjazah!=null && !Checker.isStringNullOrEmpty(biayaIjazah)) {
				testerAja = Double.parseDouble(biayaIjazah);
				if(testerAja>=1) {
					item++;	
				}
			}
			if(biayaWisuda!=null && !Checker.isStringNullOrEmpty(biayaWisuda)) {
					testerAja = Double.parseDouble(biayaWisuda);
					if(testerAja>=1) {
					item++;	
				}
			}
			if(biayaBinaan!=null && !Checker.isStringNullOrEmpty(biayaBinaan)) {
				testerAja = Double.parseDouble(biayaBinaan);
				if(testerAja>=1) {
				item++;	
				}
			}
			if(biayaKp!=null && !Checker.isStringNullOrEmpty(biayaKp)) {
				testerAja = Double.parseDouble(biayaKp);
				if(testerAja>=1) {
				item++;	
				}
			}
			if(biayaAdmBank!=null && !Checker.isStringNullOrEmpty(biayaAdmBank)) {
				testerAja = Double.parseDouble(biayaAdmBank);
				if(testerAja>=1) {
				item++;	
				}
			}
			if(item>1) {
				groupMode = true;
			}	
		}
		catch(Exception e) {
			//ada error harusnya kalo sampe sini data tidak ada yg salah krn sudah via validasi
			//not group mod:
		}
		
    	String kode_kampus = "";
    	//String defaultAccount = null;
    	tglTrans = DateFormater.convertFromDdMmYyToYyMmDd(tglTrans);
    	String nokod = null;
    	java.util.Date date= new java.util.Date();
		String tmp = ""+(new Timestamp(date.getTime()));
		StringTokenizer st = new StringTokenizer(tmp);
		while(st.hasMoreTokens()) {
			tmp = st.nextToken();
		}
		tmp = tmp.replaceAll(":","");
		tmp = tmp.replaceAll("\\.","");
		nokod=""+tmp;
    	try {
    		long norut =1+getNoRutTerakhirAtPymntTransitTabel();//utk tiap group 1 norut / nokui
    		
    		//System.out.println("norut ="+norut);
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		if(groupMode) {
    			lastGroupId =getNoGroupIdTerakhirAtPymntTransitTabel();
    		}
    		/*
    		 * tambahan nov 2014 untuk colomn 
    		 * kode kampus domisili	
    		 */
    		stmt = con.prepareStatement("select KODE_KAMPUS_DOMISILI  from OBJECT where ID_OBJ=?");
    		stmt.setLong(1, Long.parseLong(objId));
    		rs = stmt.executeQuery();
    		rs.next();
    		kode_kampus = ""+rs.getString("KODE_KAMPUS_DOMISILI");
    			
    		stmt = con.prepareStatement("insert into PYMNT_TRANSIT(KDPSTPYMNT,NPMHSPYMNT,NORUTPYMNT,TGKUIPYMNT,TGTRSPYMNT,PAYEEPYMNT,OPNPMPYMNT,OPNMMPYMNT,SETORPYMNT,NONPMPYMNT,NOKODPYMNT,UPDTMPYMNT,FILENAME,UPLOADTM,GROUP_ID,IDPAKETBEASISWA,KETER_PYMNT_DETAIL,AMONTPYMNT,PYMTPYMNT,GELOMBANG,CICILAN,KRS,SMS,NOACCPYMNT,ID_OBJ,KODE_KAMPUS_DOMISILI) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        		

    		int i=1;
    		//KUIIDPYMNT,
    		//stmt.setString(i++,kdpst);//1
    		//KDPSTPYMNT-fix
        	stmt.setString(i++,kdpst);//2
        	//NPMHSPYMNT-fix
        	stmt.setString(i++,npm);//3
        	//NORUTPYMNT-fix
        	stmt.setLong(i++,norut);//4
        	//TGKUIPYMNT-fix
        	date = new java.util.Date();
        	java.sql.Date todayDate = new java.sql.Date( date.getTime() );
        	stmt.setDate(i++, todayDate);//5
        	//TGTRSPYMNT-fix
        	try {
        		tglTrans = tglTrans.replace("/", "-");
        		StringTokenizer st_ = new StringTokenizer(tglTrans,"-");
    			if(st_.countTokens()==3) {
    				String token1 = st_.nextToken();
    				String token2 = st_.nextToken();
    				String token3 = st_.nextToken();
    				if(token1.length()==4) {
    					tglTrans = token1+"-"+token2+"-"+token3;
    				}
    				else {
    					if(token3.length()==4) {
    						tglTrans = token3+"-"+token2+"-"+token1;
    					}	
    				}	
    			}
    		
        		java.sql.Date trsdt = java.sql.Date.valueOf(tglTrans);
        		stmt.setDate(i++,trsdt);//6
        	}
        	catch(Exception e) {
        		stmt.setDate(i++, todayDate);//6
        	}
        	//PAYEEPYMNT
        	stmt.setString(i++, namaPenyetor);//7
        	//OPNPMPYMNT
        	stmt.setString(i++,this.operatorNpm);//8
        	//OPNMMPYMNT
        	stmt.setString(i++,this.operatorNmm);//9
        	//SETORPYMNT
        	try {
        		java.sql.Date trsdt = java.sql.Date.valueOf(tglTrans);
        		stmt.setBoolean(i++,true);//10
        	}
        	catch(Exception e) {
        		stmt.setBoolean(i++, false);//10
        	}
        	//NONPMPYMNT-ngga tau kolom ini mo dipake utk apa??
        	stmt.setNull(i++,java.sql.Types.VARCHAR);//11
        	//NOKODPYMNT
        	stmt.setString(i++,nokod);//12
        	//UPDTMPYMNT
        	stmt.setTimestamp(i++, AskSystem.getCurrentTimestamp());//13
        	//FILENAME
        	stmt.setString(i++,namaFileBuktiSetoran);//14
        	//UPLOADTM
        	stmt.setTimestamp(i++, AskSystem.getCurrentTimestamp());//15
        	//GROUP_ID,
        	if(groupMode) {
        		stmt.setLong(i++,lastGroupId+1);//16
        	}
        	else {
        		stmt.setNull(i++, java.sql.Types.INTEGER);
        	}
        	//idpaketbeasiswa = sumberdana
        	stmt.setLong(i++, Long.parseLong(sumberDana));
        	
        	int j = i;
        	
        	/*
        	 * non fix value
        	 * 
        	 */
        	String pos = null;
        	String kdjen = Converter.getKdjen(kdpst);
        	if(besaranBpp!=null && !Checker.isStringNullOrEmpty(besaranBpp)) {
        		 j = i;
            	//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
            	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
            	//KETER_PYMNT_DETAIL-,iter
            	String keter = "Pembayaran biaya BPP semester ke-"+bppKe;
            	stmt.setString(j++,keter);//17
            	//AMONTPYMNT,-iter
            	stmt.setDouble(j++, Double.valueOf(besaranBpp).doubleValue());//18
            	//PYMTPYMNT,-iter
            	pos = "BPP";
            	stmt.setString(j++,pos);//19
            	//GELOMBANG-,-iter
            	stmt.setInt(j++,0);//20
            	//CICILAN-,-iter
            	stmt.setInt(j++,0);//21
            	//KRS-,iter
            	stmt.setInt(j++,0);//22
            	//SMS
            	stmt.setInt(j++,0);//23
            	
            	//NOACCPYMNT,-iter
            	//System.out.println("1a.targetShift = "+target_shift);
            	try {
            		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getTargetAkun/"+pos+"/"+target_shift+"/"+kdjen);
            	    jObj = jArray.getJSONObject(0);	
            	    stmt.setString(j++,ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN_LOOKUP")));//24
            	}
            	catch(EncodingException e) {
            		e.printStackTrace();
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
            	catch(JSONException e) {
            		e.printStackTrace();
            	}
            //id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
            	
            	ins = stmt.executeUpdate();
    		}
        		//String tglTrans,String namaFileBuktiSetoran,String namaPenyetor, String objId,String fwdPg,String obj_lvl,String kdpst,String nmm,String npm,String tipeForm,String bankAcc,String totSks,String sksSmsKe,String biayaSks,String biayaDkm,String biayaPraktik,String biayaBimbinganSkripsi,String biayaUjianSkripsi,String biayaSumbanganBuku,String biayaJurnal,String biayaIjazah,String biayaWisuda) {
    		if(besaranHeregistrasi!=null && !Checker.isStringNullOrEmpty(besaranHeregistrasi)) {
    			j = i;
            	//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
            	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
            	//KETER_PYMNT_DETAIL-,iter
            	String keter = "Pembayaran biaya Daftar Ulang Semester ke-"+pendaftaranSmsKe;
            	stmt.setString(j++,keter);//7
            	//AMONTPYMNT,-iter
            	stmt.setDouble(j++, Double.valueOf(besaranHeregistrasi).doubleValue());//9
            	//PYMTPYMNT,-iter
            	pos = "REG";
            	stmt.setString(j++,pos);//10
            	//GELOMBANG-,-iter
            	stmt.setInt(j++,0);//11
            	//CICILAN-,-iter
            	stmt.setInt(j++,0);//12
            	//KRS-,iter
            	stmt.setInt(j++,0);//12
            	//SMS
            	stmt.setInt(j++,Integer.parseInt(pendaftaranSmsKe));//12
            	//NOACCPYMNT,-iter
            	//jgn lupa ada process memnentukan bankAccTarget
            	try {
            		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getTargetAkun/"+pos+"/"+target_shift+"/"+kdjen);
            	    jObj = jArray.getJSONObject(0);	
            	    stmt.setString(j++,ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN_LOOKUP")));//11
            	}
            	catch(EncodingException e) {
            		e.printStackTrace();
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
            	catch(JSONException e) {
            		e.printStackTrace();
            	}
            //id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
            	ins = stmt.executeUpdate();
    		}
    		if(biayaSks!=null && !Checker.isStringNullOrEmpty(biayaSks)) {
    			j = i;
    			//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
            	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
            	//KETER_PYMNT_DETAIL-,iter
            	String keter = "Pembayaran SKS semester ke-"+sksSmsKe+", sebanyak "+totSks;
            	stmt.setString(j++,keter);//7
            	//AMONTPYMNT,-iter
            	stmt.setDouble(j++, Double.valueOf(biayaSks).doubleValue());//9
            	//PYMTPYMNT,-iter
            	pos = "SKS";
            	stmt.setString(j++,pos);//10
            	//GELOMBANG-,-iter
            	stmt.setInt(j++,0);//11
            	//CICILAN-,-iter
            	stmt.setInt(j++,0);//12
            	//KRS-,iter
            	stmt.setInt(j++,Integer.parseInt(totSks));//12
            	//SMS
            	stmt.setInt(j++,Integer.parseInt(sksSmsKe));//12
            	//NOACCPYMNT,-iter
            	try {
            		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getTargetAkun/"+pos+"/"+target_shift+"/"+Converter.getKdjen(kdpst));
            	    jObj = jArray.getJSONObject(0);	
            	    stmt.setString(j++,ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN_LOOKUP")));//11
            	}
            	catch(EncodingException e) {
            		e.printStackTrace();
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
            	catch(JSONException e) {
            		e.printStackTrace();
            	}
            //id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
            	
            	ins = stmt.executeUpdate();
    		}
    		if(biayaDkm!=null && !Checker.isStringNullOrEmpty(biayaDkm)) {
    			j = i;
    			//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
            	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
            	//KETER_PYMNT_DETAIL-,iter
            	String keter = "Dana Kemahasiswaan";
            	stmt.setString(j++,keter);//7
            	//AMONTPYMNT,-iter
            	stmt.setDouble(j++, Double.valueOf(biayaDkm).doubleValue());//9
            	//PYMTPYMNT,-iter
            	pos = "DKM";
            	stmt.setString(j++,pos);//10
            	//GELOMBANG-,-iter
            	stmt.setInt(j++,0);//11
            	//CICILAN-,-iter
            	stmt.setInt(j++,0);//12
            	//KRS-,iter
            	stmt.setInt(j++,0);//12
            	//SMS
            	stmt.setInt(j++,0);//12
            	//NOACCPYMNT,-iter
            	try {
            		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getTargetAkun/"+pos+"/"+target_shift+"/"+kdjen);
            	    jObj = jArray.getJSONObject(0);	
            	    stmt.setString(j++,ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN_LOOKUP")));//11
            	}
            	catch(EncodingException e) {
            		e.printStackTrace();
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
            	catch(JSONException e) {
            		e.printStackTrace();
            	}
            //id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
            	ins = stmt.executeUpdate();
    		}
    		if(biayaPraktik!=null && !Checker.isStringNullOrEmpty(biayaPraktik)) {
    			j = i;
    			//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
            	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
            	//KETER_PYMNT_DETAIL-,iter
            	String keter = "Biaya Praktikum";
            	stmt.setString(j++,keter);//7
            	//AMONTPYMNT,-iter
            	stmt.setDouble(j++, Double.valueOf(biayaPraktik).doubleValue());//9
            	//PYMTPYMNT,-iter
            	pos = "LAB"; 
            	stmt.setString(j++,"LAB");//10
            	//GELOMBANG-,-iter
            	stmt.setInt(j++,0);//11
            	//CICILAN-,-iter
            	stmt.setInt(j++,0);//12
            	//KRS-,iter
            	stmt.setInt(j++,0);//12
            	//SMS
            	stmt.setInt(j++,0);//12
            	//NOACCPYMNT,-iter
            	try {
            		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getTargetAkun/"+pos+"/"+target_shift+"/"+kdjen);
            	    jObj = jArray.getJSONObject(0);	
            	    stmt.setString(j++,ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN_LOOKUP")));//11
            	}
            	catch(EncodingException e) {
            		e.printStackTrace();
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
            	catch(JSONException e) {
            		e.printStackTrace();
            	}
            //id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
            	ins = stmt.executeUpdate();
    		}
    		if(biayaBimbinganSkripsi!=null && !Checker.isStringNullOrEmpty(biayaBimbinganSkripsi)) {
    			j = i;
    			//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
            	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
            	//KETER_PYMNT_DETAIL-,iter
            	String keter = "Biaya Bimbingan Skripsi";
            	stmt.setString(j++,keter);//7
            	//AMONTPYMNT,-iter
            	stmt.setDouble(j++, Double.valueOf(biayaBimbinganSkripsi).doubleValue());//9
            	//PYMTPYMNT,-iter
            	pos = "BIMSKR";
            	stmt.setString(j++,"BIMSKR");//10
            	//GELOMBANG-,-iter
            	stmt.setInt(j++,0);//11
            	//CICILAN-,-iter
            	stmt.setInt(j++,0);//12
            	//KRS-,iter
            	stmt.setInt(j++,0);//12
            	//SMS
            	stmt.setInt(j++,0);//12
            	//NOACCPYMNT,-iter
            	try {
            		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getTargetAkun/"+pos+"/"+target_shift+"/"+kdjen);
            	    jObj = jArray.getJSONObject(0);	
            	    stmt.setString(j++,ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN_LOOKUP")));//11
            	}
            	catch(EncodingException e) {
            		e.printStackTrace();
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
            	catch(JSONException e) {
            		e.printStackTrace();
            	}
            //id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
            	
            	ins = stmt.executeUpdate();
    		}
    		if(biayaUjianSkripsi!=null && !Checker.isStringNullOrEmpty(biayaUjianSkripsi)) {
    			j = i;
    			//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
            	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
            	//KETER_PYMNT_DETAIL-,iter
            	String keter = "Ujian Skripsi";
            	stmt.setString(j++,keter);//7
            	//AMONTPYMNT,-iter
            	stmt.setDouble(j++, Double.valueOf(biayaUjianSkripsi).doubleValue());//9
            	//PYMTPYMNT,-iter
            	pos = "UJISKR";
            	stmt.setString(j++,"UJISKR");//10
            	//GELOMBANG-,-iter
            	stmt.setInt(j++,0);//11
            	//CICILAN-,-iter
            	stmt.setInt(j++,0);//12
            	//KRS-,iter
            	stmt.setInt(j++,0);//12
            	//SMS
            	stmt.setInt(j++,0);//12
            	//NOACCPYMNT,-iter
            	try {
            		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getTargetAkun/"+pos+"/"+target_shift+"/"+kdjen);
            	    jObj = jArray.getJSONObject(0);	
            	    stmt.setString(j++,ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN_LOOKUP")));//11
            	}
            	catch(EncodingException e) {
            		e.printStackTrace();
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
            	catch(JSONException e) {
            		e.printStackTrace();
            	}
            //id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
            	ins = stmt.executeUpdate();
    		}
    		if(biayaSumbanganBuku!=null && !Checker.isStringNullOrEmpty(biayaSumbanganBuku)) {        		//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
    			j = i;
    			//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
            	//KETER_PYMNT_DETAIL-,iter
            	String keter = "Sumbangan Buku";
            	stmt.setString(j++,keter);//7
            	//AMONTPYMNT,-iter
            	stmt.setDouble(j++, Double.valueOf(biayaSumbanganBuku).doubleValue());//9
            	//PYMTPYMNT,-iter
            	pos = "SUMBKU";
            	stmt.setString(j++,pos);//10
            	//GELOMBANG-,-iter
            	stmt.setInt(j++,0);//11
            	//CICILAN-,-iter
            	stmt.setInt(j++,0);//12
            	//KRS-,iter
            	stmt.setInt(j++,0);//12
            	//SMS
            	stmt.setInt(j++,0);//12
            	//NOACCPYMNT,-iter
            	try {
            		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getTargetAkun/"+pos+"/"+target_shift+"/"+kdjen);
            	    jObj = jArray.getJSONObject(0);	
            	    stmt.setString(j++,ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN_LOOKUP")));//11
            	}
            	catch(EncodingException e) {
            		e.printStackTrace();
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
            	catch(JSONException e) {
            		e.printStackTrace();
            	}
            //id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
            	
            	ins = stmt.executeUpdate();
    		}
    		if(biayaJurnal!=null && !Checker.isStringNullOrEmpty(biayaJurnal)) {
    			j = i;
    			//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
            	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
            	//KETER_PYMNT_DETAIL-,iter
            	String keter = "Jurnal";
            	stmt.setString(j++,keter);//7
            	//AMONTPYMNT,-iter
            	stmt.setDouble(j++, Double.valueOf(biayaJurnal).doubleValue());//9
            	//PYMTPYMNT,-iter
            	pos = "PUBJUR";
            	stmt.setString(j++,"PUBJUR");//10
            	//GELOMBANG-,-iter
            	stmt.setInt(j++,0);//11
            	//CICILAN-,-iter
            	stmt.setInt(j++,0);//12
            	//KRS-,iter
            	stmt.setInt(j++,0);//12
            	//SMS
            	stmt.setInt(j++,0);//12
            	//NOACCPYMNT,-iter
            	try {
            		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getTargetAkun/"+pos+"/"+target_shift+"/"+kdjen);
            	    jObj = jArray.getJSONObject(0);	
            	    stmt.setString(j++,ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN_LOOKUP")));//11
            	}
            	catch(EncodingException e) {
            		e.printStackTrace();
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
            	catch(JSONException e) {
            		e.printStackTrace();
            	}
            //id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
            	
            	ins = stmt.executeUpdate();
    		}
    		if(biayaIjazah!=null && !Checker.isStringNullOrEmpty(biayaIjazah)) {
    			j = i;
    			//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
            	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
            	//KETER_PYMNT_DETAIL-,iter
            	String keter = "Cetak Ijazah";
            	stmt.setString(j++,keter);//7
            	//AMONTPYMNT,-iter
            	stmt.setDouble(j++, Double.valueOf(biayaIjazah).doubleValue());//9
            	//PYMTPYMNT,-iter
            	pos = "IJA";
            	stmt.setString(j++,pos);//10
            	//GELOMBANG-,-iter
            	stmt.setInt(j++,0);//11
            	//CICILAN-,-iter
            	stmt.setInt(j++,0);//12
            	//KRS-,iter
            	stmt.setInt(j++,0);//12
            	//SMS
            	stmt.setInt(j++,0);//12
            	//NOACCPYMNT,-iter
            	try {
            		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getTargetAkun/"+pos+"/"+target_shift+"/"+kdjen);
            	    jObj = jArray.getJSONObject(0);	
            	    stmt.setString(j++,ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN_LOOKUP")));//11
            	}
            	catch(EncodingException e) {
            		e.printStackTrace();
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
            	catch(JSONException e) {
            		e.printStackTrace();
            	}
            //id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
            	ins = stmt.executeUpdate();
    		}
    		if(biayaWisuda!=null && !Checker.isStringNullOrEmpty(biayaWisuda)) {
    			j = i;
    			//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
            	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
            	//KETER_PYMNT_DETAIL-,iter
            	String keter = "Wisuda";
            	stmt.setString(j++,keter);//7
            	//AMONTPYMNT,-iter
            	stmt.setDouble(j++, Double.valueOf(biayaWisuda).doubleValue());//9
            	//PYMTPYMNT,-iter
            	pos= "WIS";
            	stmt.setString(j++,pos);//10
            	//GELOMBANG-,-iter
            	stmt.setInt(j++,0);//11
            	//CICILAN-,-iter
            	stmt.setInt(j++,0);//12
            	//KRS-,iter
            	stmt.setInt(j++,0);//12
            	//SMS
            	stmt.setInt(j++,0);//12
            	//NOACCPYMNT,-iter
            	try {
            		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getTargetAkun/"+pos+"/"+target_shift+"/"+kdjen);
            	    jObj = jArray.getJSONObject(0);
            	    stmt.setString(j++,ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN_LOOKUP")));//11
            	}
            	catch(EncodingException e) {
            		e.printStackTrace();
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
           		catch(JSONException e) {
            		e.printStackTrace();
           		}
            //id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
            	
           		ins = stmt.executeUpdate();
    		}
    		
    		if(biayaBinaan!=null && !Checker.isStringNullOrEmpty(biayaBinaan)) {
    			j = i;
    			//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
            	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
            	//KETER_PYMNT_DETAIL-,iter
            	String keter = "Pembinaan / Asistensi";
            	stmt.setString(j++,keter);//7
            	//AMONTPYMNT,-iter
            	stmt.setDouble(j++, Double.valueOf(biayaBinaan).doubleValue());//9
            	//PYMTPYMNT,-iter
            	pos= "BIMBIN";
            	stmt.setString(j++,pos);//10
            	//GELOMBANG-,-iter
            	stmt.setInt(j++,0);//11
            	//CICILAN-,-iter
            	stmt.setInt(j++,0);//12
            	//KRS-,iter
            	stmt.setInt(j++,0);//12
            	//SMS
            	stmt.setInt(j++,0);//12
            	//NOACCPYMNT,-iter
            	try {
            		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getTargetAkun/"+pos+"/"+target_shift+"/"+kdjen);
            	    jObj = jArray.getJSONObject(0);
            	    stmt.setString(j++,ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN_LOOKUP")));//11
            	}
            	catch(EncodingException e) {
            		e.printStackTrace();
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
           		catch(JSONException e) {
            		e.printStackTrace();
           		}
            //id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
            	
           		ins = stmt.executeUpdate();
    		}
    		if(biayaKp!=null && !Checker.isStringNullOrEmpty(biayaKp)) {
    			j = i;
    			//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
            	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
            	//KETER_PYMNT_DETAIL-,iter
            	String keter = "Sidang Kerja Praktek";
            	stmt.setString(j++,keter);//7
            	//AMONTPYMNT,-iter
            	stmt.setDouble(j++, Double.valueOf(biayaKp).doubleValue());//9
            	//PYMTPYMNT,-iter
            	pos= "BIMBIN";
            	stmt.setString(j++,pos);//10
            	//GELOMBANG-,-iter
            	stmt.setInt(j++,0);//11
            	//CICILAN-,-iter
            	stmt.setInt(j++,0);//12
            	//KRS-,iter
            	stmt.setInt(j++,0);//12
            	//SMS
            	stmt.setInt(j++,0);//12
            	//NOACCPYMNT,-iter
            	try {
            		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getTargetAkun/"+pos+"/"+target_shift+"/"+kdjen);
            	    jObj = jArray.getJSONObject(0);
            	    stmt.setString(j++,ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN_LOOKUP")));//11
            	}
            	catch(EncodingException e) {
            		e.printStackTrace();
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
           		catch(JSONException e) {
            		e.printStackTrace();
           		}
            //id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
           		ins = stmt.executeUpdate();
    		}
    		if(biayaAdmBank!=null && !Checker.isStringNullOrEmpty(biayaAdmBank)) {
    			j = i;
    			//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
            	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
            	//KETER_PYMNT_DETAIL-,iter
            	String keter = "Biaya Administrasi Bank";
            	stmt.setString(j++,keter);//7
            	//AMONTPYMNT,-iter
            	stmt.setDouble(j++, Double.valueOf(biayaAdmBank).doubleValue());//9
            	//PYMTPYMNT,-iter
            	pos= "ADMB";
            	stmt.setString(j++,pos);//10
            	//GELOMBANG-,-iter
            	stmt.setInt(j++,0);//11
            	//CICILAN-,-iter
            	stmt.setInt(j++,0);//12
            	//KRS-,iter
            	stmt.setInt(j++,0);//12
            	//SMS
            	stmt.setInt(j++,0);//12
            	//NOACCPYMNT,-iter
            	try {
            		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getTargetAkun/"+pos+"/"+target_shift+"/"+kdjen);
            	    jObj = jArray.getJSONObject(0);
            	    stmt.setString(j++,ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN_LOOKUP")));//11
            	}
            	catch(EncodingException e) {
            		e.printStackTrace();
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
           		catch(JSONException e) {
            		e.printStackTrace();
           		}
            //id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
           		ins = stmt.executeUpdate();
    		}
    		//}
    		//kayaknya nongroup bisa digabung
    		/*
    		else {
    			//non group mode
    			stmt = con.prepareStatement("insert into PYMNT_TRANSIT(KDPSTPYMNT,NPMHSPYMNT,NORUTPYMNT,TGKUIPYMNT,TGTRSPYMNT,KETER_PYMNT_DETAIL,PAYEEPYMNT,AMONTPYMNT,PYMTPYMNT,GELOMBANG,CICILAN,KRS,NOACCPYMNT,OPNPMPYMNT,OPNMMPYMNT,SETORPYMNT,NOKODPYMNT,FILENAME,UPLOADTM) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        		int i=1;
        		//KDPSTPYMNT
        		//System.out.println(i+","+kdpst);
        		stmt.setString(i++,kdpst);//1
        		
        		//NPMHSPYMNT
        		//System.out.println(i+","+npm);
        		stmt.setString(i++,npm);//2
        		//stmt.setLong(i++,1+getNoRutTerakhir());//3
        		//NORUTPYMNT,
        		//System.out.println(i+","+norut);
        		stmt.setLong(i++,norut);//3
        		//TGKUIPYMNT,
        		
        		date = new java.util.Date();
        		java.sql.Date todayDate = new java.sql.Date( date.getTime() );
        		//System.out.println(""+todayDate);;
        		//System.out.println(i+","+todayDate);
        		stmt.setDate(i++, todayDate);//4
        		//System.out.println("tglTrans="+tglTrans);
        		//TGTRSPYMNT,
        		try {
        			java.sql.Date trsdt = java.sql.Date.valueOf(tglTrans);
        			//System.out.println(i+","+trsdt);
        			stmt.setDate(i++,trsdt);//5
        			////System.out.println("5a");
        		}
        		catch(Exception e) {
        			//System.out.println(i+","+todayDate);
        			stmt.setDate(i++, todayDate);
        			//stmt.setNull(i++, java.sql.Types.DATE);//5
        			//System.out.println("5b");
        		}
        		
        		//KETERPYMNT set null diganti keter detail
        		//stmt.setNull(i++,java.sql.Types.VARCHAR);//6
        		//KETER_PYMNT_DETAIL-,
        		String keter = "Pembayaran biaya DPP angsuran ke-"+angsuranKe+", gelombang ke-"+gelombangKe;
        		//System.out.println(i+","+keter);
        		stmt.setString(i++,keter);//7
        		//PAYEEPYMNT,
        		//System.out.println(i+","+namaPenyetor);
        		stmt.setString(i++, namaPenyetor);//8
        		//AMONTPYMNT,
        		//System.out.println(i+","+besaran);
        		stmt.setDouble(i++, Double.valueOf(besaran).doubleValue());//9
        		//PYMTPYMNT,-
        		//System.out.println(i+",DPP");
        		stmt.setString(i++,"DPP");//10
        		//GELOMBANG-,-
        		//System.out.println(i+","+gelombangKe);
        		stmt.setInt(i++,Integer.valueOf(gelombangKe).intValue());//11
        		//CICILAN-,-
        		//System.out.println(i+","+angsuranKe);
        		stmt.setInt(i++,Integer.valueOf(angsuranKe).intValue());//12
        		//KRS-,
        		//System.out.println(i+",0");
        		stmt.setInt(i++,0);//12
        		//NOACCPYMNT,
        		try {
        			java.sql.Date trsdt = java.sql.Date.valueOf(tglTrans);
        			//System.out.println(i+","+bankAcc);
        			stmt.setString(i++,bankAcc);//11
        		}
        		catch(Exception e) {
        			//System.out.println(i+",TUNAI");
        			stmt.setString(i++, "TUNAI");//11
        		}
        		//OPNPMPYMNT,
        		//System.out.println(i+","+this.operatorNpm);
        		stmt.setString(i++,this.operatorNpm);//11his
        		//OPNMMPYMNT,
        		//System.out.println(i+","+this.operatorNmm);
        		stmt.setString(i++,this.operatorNmm);//12
        		//SETORPYMNT
        		try {
        			java.sql.Date trsdt = java.sql.Date.valueOf(tglTrans);
        			//System.out.println(i+","+true);
        			stmt.setBoolean(i++,true);//13
        		}
        		catch(Exception e) {
        			//System.out.println(i+","+false);
        			stmt.setBoolean(i++, false);//13
        		}
        		//,NOKODPYMNT,
        		//System.out.println(i+","+nokod);
        		stmt.setString(i++,nokod);//14
        		//-FILENAME,
        		//System.out.println(i+","+namaFileBuktiSetoran);
        		stmt.setString(i++,namaFileBuktiSetoran);
        		//UPLOADTM-
        		//System.out.println(i+","+AskSystem.getCurrentTimestamp());
        		stmt.setTimestamp(i++, AskSystem.getCurrentTimestamp());
        		ins = stmt.executeUpdate();
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
        	if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		if (con!=null) try { con.close();} catch (Exception ignore){}
        }
    	boolean sukses = false;
    	if(ins>0) {
    		sukses = true;
    	}
    	return ins;	
    }
   
    public int insertCashPymntForm1Tunai(String tglTransCash, String namaPenyetor, String objId, String fwdPg, String obj_lvl, String kdpst, String nmm, String npm, String tipeForm, String select1, String biayaSelect1, String select2, String biayaSelect2, String select3, String biayaSelect3, String select4, String biayaSelect4, String select5, String biayaSelect5, String select6, String biayaSelect6, String select7, String biayaSelect7, String select8, String biayaSelect8, String select9, String biayaSelect9) {
    	JSONObject jObj = null;
    	String target_shift = null;
    	JSONArray jArray = null;
    	try {
    		jArray = Getter.readJsonArrayFromUrl("/v1/mhs/"+npm+"/shift");
    		if(jArray.length()>0) {
        		JSONObject job = jArray.getJSONObject(0);
        		target_shift = job.getString("KETERANGAN");
        		if(target_shift.contains("&#x2f;")) {
        			target_shift = Constants.getDefaultShift();
        		}
        	}
        	else {
        		target_shift = Constants.getDefaultShift();
        	}
    		//target_shift = target_shift.replace("&#x2f;", "/");
    	}
    	catch(IOException e) {
    		e.printStackTrace();
    	}
    	catch(JSONException e) {
    		e.printStackTrace();
    	}
    	
    	
    	int ins = 0;
    	boolean groupMode = false;
    	double hargaJaket = 0;
    	long lastGroupId = 0;
		String bankAcc = null;
		//tentukan bila ada lebih dari satu item maka jadi groupmode
		int item=0;	
		try {
			double testerAja = 0;

			if(biayaSelect1!=null && !Checker.isStringNullOrEmpty(biayaSelect1) && select1!=null  && !Checker.isStringNullOrEmpty(select1)) {
				testerAja = Double.parseDouble(biayaSelect1);
				if(testerAja>=1) {
					item++;	
				}
			}

			if(biayaSelect2!=null && !Checker.isStringNullOrEmpty(biayaSelect2) && select2!=null  && !Checker.isStringNullOrEmpty(select2)) {
				testerAja = Double.parseDouble(biayaSelect2);
				if(testerAja>=1) {
					item++;	
				}
			}

			if(biayaSelect3!=null && !Checker.isStringNullOrEmpty(biayaSelect3) && select3!=null  && !Checker.isStringNullOrEmpty(select3)) {
				testerAja = Double.parseDouble(biayaSelect3);
				if(testerAja>=1) {
					item++;	
				}
			}

			if(biayaSelect4!=null && !Checker.isStringNullOrEmpty(biayaSelect4) && select4!=null  && !Checker.isStringNullOrEmpty(select4)) {
				testerAja = Double.parseDouble(biayaSelect4);
				if(testerAja>=1) {
					item++;	
				}
			}

			if(biayaSelect5!=null && !Checker.isStringNullOrEmpty(biayaSelect5) && select5!=null  && !Checker.isStringNullOrEmpty(select5)) {
				testerAja = Double.parseDouble(biayaSelect5);
				if(testerAja>=1) {
					item++;	
				}
			}

			if(biayaSelect6!=null && !Checker.isStringNullOrEmpty(biayaSelect6) && select6!=null  && !Checker.isStringNullOrEmpty(select6)) {
				testerAja = Double.parseDouble(biayaSelect6);
				if(testerAja>=1) {
					item++;	
				}
			}

			if(biayaSelect7!=null && !Checker.isStringNullOrEmpty(biayaSelect7) && select7!=null  && !Checker.isStringNullOrEmpty(select7)) {
				testerAja = Double.parseDouble(biayaSelect7);
				if(testerAja>=1) {
					item++;	
				}
			}

			if(biayaSelect8!=null && !Checker.isStringNullOrEmpty(biayaSelect8) && select8!=null  && !Checker.isStringNullOrEmpty(select8)) {
				testerAja = Double.parseDouble(biayaSelect8);
				if(testerAja>=1) {
					item++;	
				}
			}
			
			if(biayaSelect9!=null && !Checker.isStringNullOrEmpty(biayaSelect9) && select9!=null  && !Checker.isStringNullOrEmpty(select9)) {
				testerAja = Double.parseDouble(biayaSelect9);
				if(testerAja>=1) {
					item++;	
				}
			}
			
		
			if(item>1) {
				groupMode = true;
			}	
		}
		catch(Exception e) {
			//ada error harusnya kalo sampe sini data tidak ada yg salah krn sudah via validasi
			//not group mod:
		}
		
		String kdjen = Converter.getKdjen(kdpst);
    	//String defaultAccount = null;
    	tglTransCash = DateFormater.convertFromDdMmYyToYyMmDd(tglTransCash);
    	String nokod = null;
    	java.util.Date date= new java.util.Date();
		String tmp = ""+(new Timestamp(date.getTime()));
		StringTokenizer st = new StringTokenizer(tmp);
		while(st.hasMoreTokens()) {
			tmp = st.nextToken();
		}
		tmp = tmp.replaceAll(":","");
		tmp = tmp.replaceAll("\\.","");
		nokod=""+tmp;
    	try {
    		//long norut =1+getNoRutTerakhirAtPymntTransitTabel();//utk tiap group 1 norut / nokui
    		long norut =1+getNoRutTerakhir();//utk tiap group 1 norut / nokui
    		
    		//System.out.println("norut ="+norut);
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		if(groupMode) {
    			lastGroupId =getNoGroupIdTerakhirAtPymntTable();
    		}
    		//else {
    		//	lastGroupId = 0;
    		//}
    		/*
    		 * tambahan nov 2014 untuk colomn 
    		 * kode kampus domisili	
    		 */
    		stmt = con.prepareStatement("select KODE_KAMPUS_DOMISILI  from OBJECT where ID_OBJ=?");
    		stmt.setLong(1, Long.parseLong(objId));
    		rs = stmt.executeQuery();
    		rs.next();
    		String kode_kampus = ""+rs.getString("KODE_KAMPUS_DOMISILI");
    		
    		stmt = con.prepareStatement("insert into PYMNT(KDPSTPYMNT,NPMHSPYMNT,NORUTPYMNT,TGKUIPYMNT,TGTRSPYMNT,PAYEEPYMNT,OPNPMPYMNT,OPNMMPYMNT,SETORPYMNT,NONPMPYMNT,NOKODPYMNT,UPDTMPYMNT,FILENAME,UPLOADTM,GROUP_ID,KETER_PYMNT_DETAIL,AMONTPYMNT,PYMTPYMNT,GELOMBANG,CICILAN,KRS,SMS,NOACCPYMNT,ID_OBJ,KODE_KAMPUS_DOMISILI) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        		

    		int i=1;
    		//KUIIDPYMNT,
    		//stmt.setString(i++,kdpst);//1
    		//KDPSTPYMNT-fix
        	stmt.setString(i++,kdpst);//2
        	//NPMHSPYMNT-fix
        	stmt.setString(i++,npm);//3
        	//NORUTPYMNT-fix
        	stmt.setLong(i++,norut);//4
        	//TGKUIPYMNT-fix
        	date = new java.util.Date();
        	java.sql.Date todayDate = new java.sql.Date( date.getTime() );
        	stmt.setDate(i++, todayDate);//5
        	//TGTRSPYMNT-fix
        	try {
        		java.sql.Date trsdt = java.sql.Date.valueOf(tglTransCash);
        		stmt.setDate(i++,trsdt);//6
        	}
        	catch(Exception e) {
        		stmt.setDate(i++, todayDate);//6
        	}
        	//PAYEEPYMNT
        	stmt.setString(i++, namaPenyetor);//7
        	//OPNPMPYMNT
        	stmt.setString(i++,this.operatorNpm);//8
        	//OPNMMPYMNT
        	stmt.setString(i++,this.operatorNmm);//9
        	//SETORPYMNT krn ini bayaran cash jadi always blum ke setor
        	//try {
        		//java.sql.Date trsdt = java.sql.Date.valueOf(tglTransCash);
        		//stmt.setBoolean(i++,true);//10
        	//}
        	//catch(Exception e) {
        		stmt.setBoolean(i++, false);//10
        	//}
        	//NONPMPYMNT-ngga tau kolom ini mo dipake utk apa??
        	stmt.setNull(i++,java.sql.Types.VARCHAR);//11
        	//NOKODPYMNT
        	stmt.setString(i++,nokod);//12
        	//UPDTMPYMNT
        	stmt.setTimestamp(i++, AskSystem.getCurrentTimestamp());//13
        	//FILENAME
        	stmt.setNull(i++,java.sql.Types.VARCHAR);//14
        	//UPLOADTM
        	stmt.setTimestamp(i++, AskSystem.getCurrentTimestamp());//15
        	//GROUP_ID,
        	if(groupMode) {
        		stmt.setLong(i++,lastGroupId+1);//16
        	}
        	else {
        		stmt.setNull(i++, java.sql.Types.INTEGER);
        	}
        	String targetAkunTunai = null;
        	try {
        		//http://localhost:8080/com.otaku.rest/api/v1/akun/getAkunTunai/B
        		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getAkunTunai/"+kdjen);
        	    jObj = jArray.getJSONObject(0);	
        	    targetAkunTunai = ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN"));//24
        	}
        	catch(EncodingException e) {
        		e.printStackTrace();
        	}
        	catch(IOException e) {
        		e.printStackTrace();
        	}
        	catch(JSONException e) {
        		e.printStackTrace();
        	}
        	
        	
        	int j = i;
        	
        	/*
        	 * non fix value
        	 * 
        	 */
        	String pos = null;
        	
        	//if(besaranBpp!=null && !Checker.isStringNullOrEmpty(besaranBpp)) {
        	if(biayaSelect1!=null && !Checker.isStringNullOrEmpty(biayaSelect1) && select1!=null  && !Checker.isStringNullOrEmpty(select1)) {
        		 j = i;
            	//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
            	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
            	//KETER_PYMNT_DETAIL-,iter
        		StringTokenizer stt = new StringTokenizer(select1,":");
        		String id = stt.nextToken();
				String kdpos = stt.nextToken();
				String ketpos = stt.nextToken();
				ketpos = ketpos.toLowerCase();
				ketpos = Tool.capFirstLetterInWord(ketpos); 
            	String keter = ""+ketpos;
            	stmt.setString(j++,keter);//17
            	//AMONTPYMNT,-iter
            	stmt.setDouble(j++, Double.valueOf(biayaSelect1).doubleValue());//18
            	//PYMTPYMNT,-iter
            	stmt.setString(j++,kdpos);//19
            	//GELOMBANG-,-iter
            	stmt.setInt(j++,0);//20
            	//CICILAN-,-iter
            	stmt.setInt(j++,0);//21
            	//KRS-,iter
            	stmt.setInt(j++,0);//22
            	//SMS
            	stmt.setInt(j++,0);//23
            	
            	//NOACCPYMNT,-iter
            	stmt.setString(j++,targetAkunTunai);//24
            	//id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            	//kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
            	
            	ins = stmt.executeUpdate();
    		}
        	
        	if(biayaSelect2!=null && !Checker.isStringNullOrEmpty(biayaSelect2) && select2!=null  && !Checker.isStringNullOrEmpty(select2)) {
       		 	j = i;
           	//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
           	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
           	//KETER_PYMNT_DETAIL-,iter
       		 	StringTokenizer stt = new StringTokenizer(select2,":");
       		 	String id = stt.nextToken();
				String kdpos = stt.nextToken();
				String ketpos = stt.nextToken();
				ketpos = ketpos.toLowerCase();
				ketpos = Tool.capFirstLetterInWord(ketpos); 
				String keter = ""+ketpos;
				stmt.setString(j++,keter);//17
           	//AMONTPYMNT,-iter
				stmt.setDouble(j++, Double.valueOf(biayaSelect2).doubleValue());//18
           	//PYMTPYMNT,-iter
				stmt.setString(j++,kdpos);//19
           	//GELOMBANG-,-iter
				stmt.setInt(j++,0);//20
           	//CICILAN-,-iter
				stmt.setInt(j++,0);//21
           	//KRS-,iter
				stmt.setInt(j++,0);//22
           	//SMS
				stmt.setInt(j++,0);//23
           	
           	//NOACCPYMNT,-iter
				stmt.setString(j++,targetAkunTunai);//24
			//id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
				ins = stmt.executeUpdate();
        	}
    		
        	if(biayaSelect3!=null && !Checker.isStringNullOrEmpty(biayaSelect3) && select3!=null  && !Checker.isStringNullOrEmpty(select3)) {
       		 	j = i;
           	//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
           	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
           	//KETER_PYMNT_DETAIL-,iter
       		 	StringTokenizer stt = new StringTokenizer(select3,":");
       		 	String id = stt.nextToken();
				String kdpos = stt.nextToken();
				String ketpos = stt.nextToken();
				ketpos = ketpos.toLowerCase();
				ketpos = Tool.capFirstLetterInWord(ketpos); 
				String keter = ""+ketpos;
				stmt.setString(j++,keter);//17
           	//AMONTPYMNT,-iter
				stmt.setDouble(j++, Double.valueOf(biayaSelect3).doubleValue());//18
           	//PYMTPYMNT,-iter
				stmt.setString(j++,kdpos);//19
           	//GELOMBANG-,-iter
				stmt.setInt(j++,0);//20
           	//CICILAN-,-iter
				stmt.setInt(j++,0);//21
           	//KRS-,iter
				stmt.setInt(j++,0);//22
           	//SMS
				stmt.setInt(j++,0);//23
           	
           	//NOACCPYMNT,-iter
				stmt.setString(j++,targetAkunTunai);//24
			//id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
				ins = stmt.executeUpdate();
        	}
        	
        	if(biayaSelect4!=null && !Checker.isStringNullOrEmpty(biayaSelect4) && select4!=null  && !Checker.isStringNullOrEmpty(select4)) {
       		 	j = i;
           	//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
           	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
           	//KETER_PYMNT_DETAIL-,iter
       		 	StringTokenizer stt = new StringTokenizer(select4,":");
       		 	String id = stt.nextToken();
				String kdpos = stt.nextToken();
				String ketpos = stt.nextToken();
				ketpos = ketpos.toLowerCase();
				ketpos = Tool.capFirstLetterInWord(ketpos); 
				String keter = ""+ketpos;
				stmt.setString(j++,keter);//17
           	//AMONTPYMNT,-iter
				stmt.setDouble(j++, Double.valueOf(biayaSelect4).doubleValue());//18
           	//PYMTPYMNT,-iter
				stmt.setString(j++,kdpos);//19
           	//GELOMBANG-,-iter
				stmt.setInt(j++,0);//20
           	//CICILAN-,-iter
				stmt.setInt(j++,0);//21
           	//KRS-,iter
				stmt.setInt(j++,0);//22
           	//SMS
				stmt.setInt(j++,0);//23
           	
           	//NOACCPYMNT,-iter
				stmt.setString(j++,targetAkunTunai);//24
			//id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
				ins = stmt.executeUpdate();
        	}
        	if(biayaSelect5!=null && !Checker.isStringNullOrEmpty(biayaSelect5) && select5!=null  && !Checker.isStringNullOrEmpty(select5)) {
       		 	j = i;
           	//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
           	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
           	//KETER_PYMNT_DETAIL-,iter
       		 	StringTokenizer stt = new StringTokenizer(select5,":");
       		 	String id = stt.nextToken();
				String kdpos = stt.nextToken();
				String ketpos = stt.nextToken();
				ketpos = ketpos.toLowerCase();
				ketpos = Tool.capFirstLetterInWord(ketpos); 
				String keter = ""+ketpos;
				stmt.setString(j++,keter);//17
           	//AMONTPYMNT,-iter
				stmt.setDouble(j++, Double.valueOf(biayaSelect5).doubleValue());//18
           	//PYMTPYMNT,-iter
				stmt.setString(j++,kdpos);//19
           	//GELOMBANG-,-iter
				stmt.setInt(j++,0);//20
           	//CICILAN-,-iter
				stmt.setInt(j++,0);//21
           	//KRS-,iter
				stmt.setInt(j++,0);//22
           	//SMS
				stmt.setInt(j++,0);//23
           	
           	//NOACCPYMNT,-iter
				stmt.setString(j++,targetAkunTunai);//24
			//id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
				ins = stmt.executeUpdate();
        	}
        	
        	if(biayaSelect6!=null && !Checker.isStringNullOrEmpty(biayaSelect6) && select6!=null  && !Checker.isStringNullOrEmpty(select6)) {
       		 	j = i;
           	//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
           	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
           	//KETER_PYMNT_DETAIL-,iter
       		 	StringTokenizer stt = new StringTokenizer(select6,":");
       		 	String id = stt.nextToken();
				String kdpos = stt.nextToken();
				String ketpos = stt.nextToken();
				ketpos = ketpos.toLowerCase();
				ketpos = Tool.capFirstLetterInWord(ketpos); 
				String keter = ""+ketpos;
				stmt.setString(j++,keter);//17
           	//AMONTPYMNT,-iter
				stmt.setDouble(j++, Double.valueOf(biayaSelect6).doubleValue());//18
           	//PYMTPYMNT,-iter
				stmt.setString(j++,kdpos);//19
           	//GELOMBANG-,-iter
				stmt.setInt(j++,0);//20
           	//CICILAN-,-iter
				stmt.setInt(j++,0);//21
           	//KRS-,iter
				stmt.setInt(j++,0);//22
           	//SMS
				stmt.setInt(j++,0);//23
           	
           	//NOACCPYMNT,-iter
				stmt.setString(j++,targetAkunTunai);//24
			//id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
				ins = stmt.executeUpdate();
        	}
        	if(biayaSelect7!=null && !Checker.isStringNullOrEmpty(biayaSelect7) && select7!=null  && !Checker.isStringNullOrEmpty(select7)) {
       		 	j = i;
           	//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
           	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
           	//KETER_PYMNT_DETAIL-,iter
       		 	StringTokenizer stt = new StringTokenizer(select7,":");
       		 	String id = stt.nextToken();
				String kdpos = stt.nextToken();
				String ketpos = stt.nextToken();
				ketpos = ketpos.toLowerCase();
				ketpos = Tool.capFirstLetterInWord(ketpos); 
				String keter = ""+ketpos;
           		stmt.setString(j++,keter);//17
           	//AMONTPYMNT,-iter
           		stmt.setDouble(j++, Double.valueOf(biayaSelect7).doubleValue());//18
           	//PYMTPYMNT,-iter
           		stmt.setString(j++,kdpos);//19
           	//GELOMBANG-,-iter
           		stmt.setInt(j++,0);//20
           	//CICILAN-,-iter
           		stmt.setInt(j++,0);//21
           	//KRS-,iter
           		stmt.setInt(j++,0);//22
           	//SMS
           		stmt.setInt(j++,0);//23
           	
           	//NOACCPYMNT,-iter
           		stmt.setString(j++,targetAkunTunai);//24
           	//id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
           		ins = stmt.executeUpdate();
        	}
        	
        	if(biayaSelect8!=null && !Checker.isStringNullOrEmpty(biayaSelect8) && select8!=null  && !Checker.isStringNullOrEmpty(select8)) {
       		 	j = i;
           	//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
           	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
           	//KETER_PYMNT_DETAIL-,iter
       		 	StringTokenizer stt = new StringTokenizer(select8,":");
       		 	String id = stt.nextToken();
				String kdpos = stt.nextToken();
				String ketpos = stt.nextToken();
				ketpos = ketpos.toLowerCase();
				ketpos = Tool.capFirstLetterInWord(ketpos); 
				String keter = ""+ketpos;
				stmt.setString(j++,keter);//17
           	//AMONTPYMNT,-iter
				stmt.setDouble(j++, Double.valueOf(biayaSelect8).doubleValue());//18
           	//PYMTPYMNT,-iter
				stmt.setString(j++,kdpos);//19
           	//GELOMBANG-,-iter
				stmt.setInt(j++,0);//20
           	//CICILAN-,-iter
				stmt.setInt(j++,0);//21
           	//KRS-,iter
				stmt.setInt(j++,0);//22
           	//SMS
				stmt.setInt(j++,0);//23
           	
           	//NOACCPYMNT,-iter
				stmt.setString(j++,targetAkunTunai);//24
			//id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
				ins = stmt.executeUpdate();
        	}
        	
        	if(biayaSelect9!=null && !Checker.isStringNullOrEmpty(biayaSelect9) && select9!=null  && !Checker.isStringNullOrEmpty(select9)) {
       		 	j = i;
           	//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
           	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
           	//KETER_PYMNT_DETAIL-,iter
       		 	StringTokenizer stt = new StringTokenizer(select9,":");
       		 	String id = stt.nextToken();
				String kdpos = stt.nextToken();
				String ketpos = stt.nextToken();
				ketpos = ketpos.toLowerCase();
				ketpos = Tool.capFirstLetterInWord(ketpos); 
				String keter = ""+ketpos;
				stmt.setString(j++,keter);//17
           	//AMONTPYMNT,-iter
				stmt.setDouble(j++, Double.valueOf(biayaSelect9).doubleValue());//18
           	//PYMTPYMNT,-iter
				stmt.setString(j++,kdpos);//19
           	//GELOMBANG-,-iter
				stmt.setInt(j++,0);//20
           	//CICILAN-,-iter
				stmt.setInt(j++,0);//21
           	//KRS-,iter
				stmt.setInt(j++,0);//22
           	//SMS
				stmt.setInt(j++,0);//23
           	
           	//NOACCPYMNT,-iter
				stmt.setString(j++,targetAkunTunai);//24
			//id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}	
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
    	boolean sukses = false;
    	if(ins>0) {
    		sukses = true;
    	}
    	return ins;	
    }

    
    public int insertPymntTransitTableForm4WithBukti(String tglTrans, String namaFileBuktiSetoran, String namaPenyetor, String objId, String fwdPg, String obj_lvl, String kdpst, String nmm, String npm, String tipeForm, String select1, String biayaSelect1, String select2, String biayaSelect2, String select3, String biayaSelect3, String select4, String biayaSelect4, String select5, String biayaSelect5, String select6, String biayaSelect6, String select7, String biayaSelect7, String select8, String biayaSelect8, String select9, String biayaSelect9, String sumberDana) {
    	JSONObject jObj = null;
    	String target_shift = null;
    	JSONArray jArray = null;
    	try {
    		jArray = Getter.readJsonArrayFromUrl("/v1/mhs/"+npm+"/shift");
    		if(jArray.length()>0) {
        		JSONObject job = jArray.getJSONObject(0);
        		target_shift = job.getString("KETERANGAN");
        		if(target_shift.contains("&#x2f;")) {
        			target_shift = Constants.getDefaultShift();
        		}
        	}
        	else {
        		target_shift = Constants.getDefaultShift();
        	}
    		//target_shift = target_shift.replace("&#x2f;", "/");
    	}
    	catch(IOException e) {
    		e.printStackTrace();
    	}
    	catch(JSONException e) {
    		e.printStackTrace();
    	}
    	
    	
    	int ins = 0;
    	boolean groupMode = false;
    	double hargaJaket = 0;
    	long lastGroupId = 0;
		String bankAcc = null;
		//tentukan bila ada lebih dari satu item maka jadi groupmode
		int item=0;	
		try {
			double testerAja = 0;

			if(biayaSelect1!=null && !Checker.isStringNullOrEmpty(biayaSelect1) && select1!=null  && !Checker.isStringNullOrEmpty(select1)) {
				testerAja = Double.parseDouble(biayaSelect1);
				if(testerAja>=1) {
					item++;	
				}
			}

			if(biayaSelect2!=null && !Checker.isStringNullOrEmpty(biayaSelect2) && select2!=null  && !Checker.isStringNullOrEmpty(select2)) {
				testerAja = Double.parseDouble(biayaSelect2);
				if(testerAja>=1) {
					item++;	
				}
			}

			if(biayaSelect3!=null && !Checker.isStringNullOrEmpty(biayaSelect3) && select3!=null  && !Checker.isStringNullOrEmpty(select3)) {
				testerAja = Double.parseDouble(biayaSelect3);
				if(testerAja>=1) {
					item++;	
				}
			}

			if(biayaSelect4!=null && !Checker.isStringNullOrEmpty(biayaSelect4) && select4!=null  && !Checker.isStringNullOrEmpty(select4)) {
				testerAja = Double.parseDouble(biayaSelect4);
				if(testerAja>=1) {
					item++;	
				}
			}

			if(biayaSelect5!=null && !Checker.isStringNullOrEmpty(biayaSelect5) && select5!=null  && !Checker.isStringNullOrEmpty(select5)) {
				testerAja = Double.parseDouble(biayaSelect5);
				if(testerAja>=1) {
					item++;	
				}
			}

			if(biayaSelect6!=null && !Checker.isStringNullOrEmpty(biayaSelect6) && select6!=null  && !Checker.isStringNullOrEmpty(select6)) {
				testerAja = Double.parseDouble(biayaSelect6);
				if(testerAja>=1) {
					item++;	
				}
			}

			if(biayaSelect7!=null && !Checker.isStringNullOrEmpty(biayaSelect7) && select7!=null  && !Checker.isStringNullOrEmpty(select7)) {
				testerAja = Double.parseDouble(biayaSelect7);
				if(testerAja>=1) {
					item++;	
				}
			}

			if(biayaSelect8!=null && !Checker.isStringNullOrEmpty(biayaSelect8) && select8!=null  && !Checker.isStringNullOrEmpty(select8)) {
				testerAja = Double.parseDouble(biayaSelect8);
				if(testerAja>=1) {
					item++;	
				}
			}
			
			if(biayaSelect9!=null && !Checker.isStringNullOrEmpty(biayaSelect9) && select9!=null  && !Checker.isStringNullOrEmpty(select9)) {
				testerAja = Double.parseDouble(biayaSelect9);
				if(testerAja>=1) {
					item++;	
				}
			}
			
		
			if(item>1) {
				groupMode = true;
			}	
		}
		catch(Exception e) {
			//ada error harusnya kalo sampe sini data tidak ada yg salah krn sudah via validasi
			//not group mod:
		}
		String kode_kampus = "";
		String kdjen = Converter.getKdjen(kdpst);
    	//String defaultAccount = null;
    	//tglTransCash = DateFormater.convertFromDdMmYyToYyMmDd(tglTransCash);
    	String nokod = null;
    	java.util.Date date= new java.util.Date();
		String tmp = ""+(new Timestamp(date.getTime()));
		StringTokenizer st = new StringTokenizer(tmp);
		while(st.hasMoreTokens()) {
			tmp = st.nextToken();
		}
		tmp = tmp.replaceAll(":","");
		tmp = tmp.replaceAll("\\.","");
		nokod=""+tmp;
    	try {
    		//long norut =1+getNoRutTerakhirAtPymntTransitTabel();//utk tiap group 1 norut / nokui
    		long norut =1+getNoRutTerakhirAtPymntTransitTabel();//utk tiap group 1 norut / nokui
    		
    		//System.out.println("norut ="+norut);
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		if(groupMode) {
    			lastGroupId =getNoGroupIdTerakhirAtPymntTransitTabel();
    		}
    		//else {
    		//	lastGroupId = 0;
    		//}
    		/*
    		 * tambahan nov 2014 untuk colomn 
    		 * kode kampus domisili	
    		 */
    		stmt = con.prepareStatement("select KODE_KAMPUS_DOMISILI  from OBJECT where ID_OBJ=?");
    		stmt.setLong(1, Long.parseLong(objId));
    		rs = stmt.executeQuery();
    		rs.next();
    		kode_kampus = ""+rs.getString("KODE_KAMPUS_DOMISILI");
    			
    		stmt = con.prepareStatement("insert into PYMNT_TRANSIT(KDPSTPYMNT,NPMHSPYMNT,NORUTPYMNT,TGKUIPYMNT,TGTRSPYMNT,PAYEEPYMNT,OPNPMPYMNT,OPNMMPYMNT,SETORPYMNT,NONPMPYMNT,NOKODPYMNT,UPDTMPYMNT,FILENAME,UPLOADTM,GROUP_ID,IDPAKETBEASISWA,KETER_PYMNT_DETAIL,AMONTPYMNT,PYMTPYMNT,GELOMBANG,CICILAN,KRS,SMS,NOACCPYMNT,ID_OBJ,KODE_KAMPUS_DOMISILI) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        		

    		int i=1;
    		//KUIIDPYMNT,
    		//stmt.setString(i++,kdpst);//1
    		//KDPSTPYMNT-fix
        	stmt.setString(i++,kdpst);//1
        	//NPMHSPYMNT-fix
        	stmt.setString(i++,npm);//2
        	//NORUTPYMNT-fix
        	stmt.setLong(i++,norut);//3
        	//TGKUIPYMNT-fix
        	date = new java.util.Date();
        	java.sql.Date todayDate = new java.sql.Date( date.getTime() );
        	stmt.setDate(i++, todayDate);//4
        	//TGTRSPYMNT-fix
        	try {
        		//System.out.println("tglTrans at bean="+tglTrans);
        		tglTrans = tglTrans.replace("/", "-");
        		StringTokenizer st_ = new StringTokenizer(tglTrans,"-");
        		if(st_.countTokens()==3) {
        			String token1 = st_.nextToken();
        			String token2 = st_.nextToken();
        			String token3 = st_.nextToken();
        			if(token1.length()==4) {
        				tglTrans = token1+"-"+token2+"-"+token3;
        			}
        			else {
        				if(token3.length()==4) {
        					tglTrans = token3+"-"+token2+"-"+token1;
        				}	
        			}	
        		}
        		//System.out.println("tglTrans at bean="+tglTrans);
        		java.sql.Date trsdt = java.sql.Date.valueOf(tglTrans);
        		stmt.setDate(i++,trsdt);//5
        	}
        	catch(Exception e) {
        		//System.out.println("tglTrans at bean error");
        		stmt.setDate(i++, todayDate);//5
        	}
        	//PAYEEPYMNT
        	stmt.setString(i++, namaPenyetor);//7
        	//OPNPMPYMNT
        	stmt.setString(i++,this.operatorNpm);//8
        	//OPNMMPYMNT
        	stmt.setString(i++,this.operatorNmm);//9
        	//SETORPYMNT 
        	try {
        		java.sql.Date trsdt = java.sql.Date.valueOf(tglTrans);
        		stmt.setBoolean(i++,true);//10
        	}
        	catch(Exception e) {
        		stmt.setBoolean(i++, false);//10
        	}
        	//NONPMPYMNT-ngga tau kolom ini mo dipake utk apa??
        	stmt.setNull(i++,java.sql.Types.VARCHAR);//11
        	//NOKODPYMNT
        	stmt.setString(i++,nokod);//12
        	//UPDTMPYMNT
        	stmt.setTimestamp(i++, AskSystem.getCurrentTimestamp());//13
        	//FILENAME
        	stmt.setString(i++,namaFileBuktiSetoran);//14
        	//UPLOADTM
        	stmt.setTimestamp(i++, AskSystem.getCurrentTimestamp());//15
        	//GROUP_ID,
        	if(groupMode) {
        		stmt.setLong(i++,lastGroupId+1);//16
        	}
        	else {
        		stmt.setNull(i++, java.sql.Types.INTEGER);
        	}
        	//sumberDana
        	stmt.setLong(i++, Long.parseLong(sumberDana));
        	/*
        	String targetAkunTunai = null;
        	try {
        		//http://localhost:8080/com.otaku.rest/api/v1/akun/getAkunTunai/B
        		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getAkunTunai/"+kdjen);
        	    jObj = jArray.getJSONObject(0);	
        	    targetAkunTunai = ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN"));//24
        	}
        	catch(EncodingException e) {
        		e.printStackTrace();
        	}
        	catch(IOException e) {
        		e.printStackTrace();
        	}
        	catch(JSONException e) {
        		e.printStackTrace();
        	}
        	*/
        	
        	int j = i;
        	
        	/*
        	 * non fix value
        	 * 
        	 */
        	String pos = null;
        	
        	//if(besaranBpp!=null && !Checker.isStringNullOrEmpty(besaranBpp)) {
        	if(biayaSelect1!=null && !Checker.isStringNullOrEmpty(biayaSelect1) && select1!=null  && !Checker.isStringNullOrEmpty(select1)) {
        		 j = i;
            	//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
            	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
            	//KETER_PYMNT_DETAIL-,iter
        		StringTokenizer stt = new StringTokenizer(select1,":");
        		String id = stt.nextToken();
				String kdpos = stt.nextToken();
				String ketpos = stt.nextToken();
				ketpos = ketpos.toLowerCase();
				ketpos = Tool.capFirstLetterInWord(ketpos); 
            	String keter = ""+ketpos;
            	stmt.setString(j++,keter);//17
            	//AMONTPYMNT,-iter
            	stmt.setDouble(j++, Double.valueOf(biayaSelect1).doubleValue());//18
            	//PYMTPYMNT,-iter
            	stmt.setString(j++,kdpos);//19
            	//GELOMBANG-,-iter
            	stmt.setInt(j++,0);//20
            	//CICILAN-,-iter
            	stmt.setInt(j++,0);//21
            	//KRS-,iter
            	stmt.setInt(j++,0);//22
            	//SMS
            	stmt.setInt(j++,0);//23
            	
            	//NOACCPYMNT,-iter
            	//NOACCPYMNT,-iter
            	//jgn lupa ada process memnentukan bankAccTarget
            	try {
            		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getTargetAkun/"+kdpos+"/"+target_shift+"/"+kdjen);
            	    jObj = jArray.getJSONObject(0);	
            	    stmt.setString(j++,ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN_LOOKUP")));//11
            	}
            	catch(EncodingException e) {
            		e.printStackTrace();
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
            	catch(JSONException e) {
            		e.printStackTrace();
            	}
            //id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
            	ins = stmt.executeUpdate();
    		}
        	
        	if(biayaSelect2!=null && !Checker.isStringNullOrEmpty(biayaSelect2) && select2!=null  && !Checker.isStringNullOrEmpty(select2)) {
       		 	j = i;
           	//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
           	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
           	//KETER_PYMNT_DETAIL-,iter
       		 	StringTokenizer stt = new StringTokenizer(select2,":");
       		 	String id = stt.nextToken();
				String kdpos = stt.nextToken();
				String ketpos = stt.nextToken();
				ketpos = ketpos.toLowerCase();
				ketpos = Tool.capFirstLetterInWord(ketpos); 
				String keter = ""+ketpos;
				stmt.setString(j++,keter);//17
           	//AMONTPYMNT,-iter
				stmt.setDouble(j++, Double.valueOf(biayaSelect2).doubleValue());//18
           	//PYMTPYMNT,-iter
				stmt.setString(j++,kdpos);//19
           	//GELOMBANG-,-iter
				stmt.setInt(j++,0);//20
           	//CICILAN-,-iter
				stmt.setInt(j++,0);//21
           	//KRS-,iter
				stmt.setInt(j++,0);//22
           	//SMS
				stmt.setInt(j++,0);//23
				//NOACCPYMNT,-iter
            	//jgn lupa ada process memnentukan bankAccTarget
            	try {
            		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getTargetAkun/"+kdpos+"/"+target_shift+"/"+kdjen);
            	    jObj = jArray.getJSONObject(0);	
            	    stmt.setString(j++,ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN_LOOKUP")));//11
            	}
            	catch(EncodingException e) {
            		e.printStackTrace();
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
            	catch(JSONException e) {
            		e.printStackTrace();
            	}
            //id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
				ins = stmt.executeUpdate();
        	}
    		
        	if(biayaSelect3!=null && !Checker.isStringNullOrEmpty(biayaSelect3) && select3!=null  && !Checker.isStringNullOrEmpty(select3)) {
       		 	j = i;
           	//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
           	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
           	//KETER_PYMNT_DETAIL-,iter
       		 	StringTokenizer stt = new StringTokenizer(select3,":");
       		 	String id = stt.nextToken();
				String kdpos = stt.nextToken();
				String ketpos = stt.nextToken();
				ketpos = ketpos.toLowerCase();
				ketpos = Tool.capFirstLetterInWord(ketpos); 
				String keter = ""+ketpos;
				stmt.setString(j++,keter);//17
           	//AMONTPYMNT,-iter
				stmt.setDouble(j++, Double.valueOf(biayaSelect3).doubleValue());//18
           	//PYMTPYMNT,-iter
				stmt.setString(j++,kdpos);//19
           	//GELOMBANG-,-iter
				stmt.setInt(j++,0);//20
           	//CICILAN-,-iter
				stmt.setInt(j++,0);//21
           	//KRS-,iter
				stmt.setInt(j++,0);//22
           	//SMS
				stmt.setInt(j++,0);//23
           	
				//NOACCPYMNT,-iter
            	//jgn lupa ada process memnentukan bankAccTarget
            	try {
            		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getTargetAkun/"+kdpos+"/"+target_shift+"/"+kdjen);
            	    jObj = jArray.getJSONObject(0);	
            	    stmt.setString(j++,ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN_LOOKUP")));//11
            	}
            	catch(EncodingException e) {
            		e.printStackTrace();
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
            	catch(JSONException e) {
            		e.printStackTrace();
            	}
            //id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
            	
				ins = stmt.executeUpdate();
        	}
        	
        	if(biayaSelect4!=null && !Checker.isStringNullOrEmpty(biayaSelect4) && select4!=null  && !Checker.isStringNullOrEmpty(select4)) {
       		 	j = i;
           	//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
           	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
           	//KETER_PYMNT_DETAIL-,iter
       		 	StringTokenizer stt = new StringTokenizer(select4,":");
       		 	String id = stt.nextToken();
				String kdpos = stt.nextToken();
				String ketpos = stt.nextToken();
				ketpos = ketpos.toLowerCase();
				ketpos = Tool.capFirstLetterInWord(ketpos); 
				String keter = ""+ketpos;
				stmt.setString(j++,keter);//17
           	//AMONTPYMNT,-iter
				stmt.setDouble(j++, Double.valueOf(biayaSelect4).doubleValue());//18
           	//PYMTPYMNT,-iter
				stmt.setString(j++,kdpos);//19
           	//GELOMBANG-,-iter
				stmt.setInt(j++,0);//20
           	//CICILAN-,-iter
				stmt.setInt(j++,0);//21
           	//KRS-,iter
				stmt.setInt(j++,0);//22
           	//SMS
				stmt.setInt(j++,0);//23
           	
				//NOACCPYMNT,-iter
            	//jgn lupa ada process memnentukan bankAccTarget
            	try {
            		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getTargetAkun/"+kdpos+"/"+target_shift+"/"+kdjen);
            	    jObj = jArray.getJSONObject(0);	
            	    stmt.setString(j++,ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN_LOOKUP")));//11
            	}
            	catch(EncodingException e) {
            		e.printStackTrace();
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
            	catch(JSONException e) {
            		e.printStackTrace();
            	}
            //id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
				ins = stmt.executeUpdate();
        	}
        	if(biayaSelect5!=null && !Checker.isStringNullOrEmpty(biayaSelect5) && select5!=null  && !Checker.isStringNullOrEmpty(select5)) {
       		 	j = i;
           	//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
           	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
           	//KETER_PYMNT_DETAIL-,iter
       		 	StringTokenizer stt = new StringTokenizer(select5,":");
       		 	String id = stt.nextToken();
				String kdpos = stt.nextToken();
				String ketpos = stt.nextToken();
				ketpos = ketpos.toLowerCase();
				ketpos = Tool.capFirstLetterInWord(ketpos); 
				String keter = ""+ketpos;
				stmt.setString(j++,keter);//17
           	//AMONTPYMNT,-iter
				stmt.setDouble(j++, Double.valueOf(biayaSelect5).doubleValue());//18
           	//PYMTPYMNT,-iter
				stmt.setString(j++,kdpos);//19
           	//GELOMBANG-,-iter
				stmt.setInt(j++,0);//20
           	//CICILAN-,-iter
				stmt.setInt(j++,0);//21
           	//KRS-,iter
				stmt.setInt(j++,0);//22
           	//SMS
				stmt.setInt(j++,0);//23
           	
				//NOACCPYMNT,-iter
            	//jgn lupa ada process memnentukan bankAccTarget
            	try {
            		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getTargetAkun/"+kdpos+"/"+target_shift+"/"+kdjen);
            	    jObj = jArray.getJSONObject(0);	
            	    stmt.setString(j++,ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN_LOOKUP")));//11
            	}
            	catch(EncodingException e) {
            		e.printStackTrace();
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
            	catch(JSONException e) {
            		e.printStackTrace();
            	}
            //id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
				ins = stmt.executeUpdate();
        	}
        	
        	if(biayaSelect6!=null && !Checker.isStringNullOrEmpty(biayaSelect6) && select6!=null  && !Checker.isStringNullOrEmpty(select6)) {
       		 	j = i;
           	//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
           	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
           	//KETER_PYMNT_DETAIL-,iter
       		 	StringTokenizer stt = new StringTokenizer(select6,":");
       		 	String id = stt.nextToken();
				String kdpos = stt.nextToken();
				String ketpos = stt.nextToken();
				ketpos = ketpos.toLowerCase();
				ketpos = Tool.capFirstLetterInWord(ketpos); 
				String keter = ""+ketpos;
				stmt.setString(j++,keter);//17
           	//AMONTPYMNT,-iter
				stmt.setDouble(j++, Double.valueOf(biayaSelect6).doubleValue());//18
           	//PYMTPYMNT,-iter
				stmt.setString(j++,kdpos);//19
           	//GELOMBANG-,-iter
				stmt.setInt(j++,0);//20
           	//CICILAN-,-iter
				stmt.setInt(j++,0);//21
           	//KRS-,iter
				stmt.setInt(j++,0);//22
           	//SMS
				stmt.setInt(j++,0);//23
           	
				//NOACCPYMNT,-iter
            	//jgn lupa ada process memnentukan bankAccTarget
            	try {
            		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getTargetAkun/"+kdpos+"/"+target_shift+"/"+kdjen);
            	    jObj = jArray.getJSONObject(0);	
            	    stmt.setString(j++,ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN_LOOKUP")));//11
            	}
            	catch(EncodingException e) {
            		e.printStackTrace();
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
            	catch(JSONException e) {
            		e.printStackTrace();
            	}
            //id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
				ins = stmt.executeUpdate();
        	}
        	if(biayaSelect7!=null && !Checker.isStringNullOrEmpty(biayaSelect7) && select7!=null  && !Checker.isStringNullOrEmpty(select7)) {
       		 	j = i;
           	//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
           	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
           	//KETER_PYMNT_DETAIL-,iter
       		 	StringTokenizer stt = new StringTokenizer(select7,":");
       		 	String id = stt.nextToken();
				String kdpos = stt.nextToken();
				String ketpos = stt.nextToken();
				ketpos = ketpos.toLowerCase();
				ketpos = Tool.capFirstLetterInWord(ketpos); 
				String keter = ""+ketpos;
           		stmt.setString(j++,keter);//17
           	//AMONTPYMNT,-iter
           		stmt.setDouble(j++, Double.valueOf(biayaSelect7).doubleValue());//18
           	//PYMTPYMNT,-iter
           		stmt.setString(j++,kdpos);//19
           	//GELOMBANG-,-iter
           		stmt.setInt(j++,0);//20
           	//CICILAN-,-iter
           		stmt.setInt(j++,0);//21
           	//KRS-,iter
           		stmt.setInt(j++,0);//22
           	//SMS
           		stmt.setInt(j++,0);//23
           	
           	//NOACCPYMNT,-iter
            	//jgn lupa ada process memnentukan bankAccTarget
            	try {
            		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getTargetAkun/"+kdpos+"/"+target_shift+"/"+kdjen);
            	    jObj = jArray.getJSONObject(0);	
            	    stmt.setString(j++,ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN_LOOKUP")));//11
            	}
            	catch(EncodingException e) {
            		e.printStackTrace();
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
            	catch(JSONException e) {
            		e.printStackTrace();
            	}
            //id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
            	
           		ins = stmt.executeUpdate();
        	}
        	
        	if(biayaSelect8!=null && !Checker.isStringNullOrEmpty(biayaSelect8) && select8!=null  && !Checker.isStringNullOrEmpty(select8)) {
       		 	j = i;
           	//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
           	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
           	//KETER_PYMNT_DETAIL-,iter
       		 	StringTokenizer stt = new StringTokenizer(select8,":");
       		 	String id = stt.nextToken();
				String kdpos = stt.nextToken();
				String ketpos = stt.nextToken();
				ketpos = ketpos.toLowerCase();
				ketpos = Tool.capFirstLetterInWord(ketpos); 
				String keter = ""+ketpos;
				stmt.setString(j++,keter);//17
           	//AMONTPYMNT,-iter
				stmt.setDouble(j++, Double.valueOf(biayaSelect8).doubleValue());//18
           	//PYMTPYMNT,-iter
				stmt.setString(j++,kdpos);//19
           	//GELOMBANG-,-iter
				stmt.setInt(j++,0);//20
           	//CICILAN-,-iter
				stmt.setInt(j++,0);//21
           	//KRS-,iter
				stmt.setInt(j++,0);//22
           	//SMS
				stmt.setInt(j++,0);//23
           	
				//NOACCPYMNT,-iter
            	//jgn lupa ada process memnentukan bankAccTarget
            	try {
            		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getTargetAkun/"+kdpos+"/"+target_shift+"/"+kdjen);
            	    jObj = jArray.getJSONObject(0);	
            	    stmt.setString(j++,ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN_LOOKUP")));//11
            	}
            	catch(EncodingException e) {
            		e.printStackTrace();
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
            	catch(JSONException e) {
            		e.printStackTrace();
            	}
            //id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
				ins = stmt.executeUpdate();
        	}
        	
        	if(biayaSelect9!=null && !Checker.isStringNullOrEmpty(biayaSelect9) && select9!=null  && !Checker.isStringNullOrEmpty(select9)) {
       		 	j = i;
           	//KETERPYMNT ignore kasih default vaue aja, diganti keter detail
           	//stmt.setNull(j++,java.sql.Types.VARCHAR);//6
           	//KETER_PYMNT_DETAIL-,iter
       		 	StringTokenizer stt = new StringTokenizer(select9,":");
       		 	String id = stt.nextToken();
				String kdpos = stt.nextToken();
				String ketpos = stt.nextToken();
				ketpos = ketpos.toLowerCase();
				ketpos = Tool.capFirstLetterInWord(ketpos); 
				String keter = ""+ketpos;
				stmt.setString(j++,keter);//17
           	//AMONTPYMNT,-iter
				stmt.setDouble(j++, Double.valueOf(biayaSelect9).doubleValue());//18
           	//PYMTPYMNT,-iter
				stmt.setString(j++,kdpos);//19
           	//GELOMBANG-,-iter
				stmt.setInt(j++,0);//20
           	//CICILAN-,-iter
				stmt.setInt(j++,0);//21
           	//KRS-,iter
				stmt.setInt(j++,0);//22
           	//SMS
				stmt.setInt(j++,0);//23
           	
				//NOACCPYMNT,-iter
            	//jgn lupa ada process memnentukan bankAccTarget
            	try {
            		jArray = Getter.readJsonArrayFromUrl("/v1/akun/getTargetAkun/"+kdpos+"/"+target_shift+"/"+kdjen);
            	    jObj = jArray.getJSONObject(0);	
            	    stmt.setString(j++,ESAPI.encoder().decodeFromURL(jObj.getString("NAMA_AKUN_LOOKUP")));//11
            	}
            	catch(EncodingException e) {
            		e.printStackTrace();
            	}
            	catch(IOException e) {
            		e.printStackTrace();
            	}
            	catch(JSONException e) {
            		e.printStackTrace();
            	}
            //id obj akun terbayar
            	stmt.setLong(j++,Long.parseLong(objId));//25
            //kodekampus
            	if(kode_kampus==null || Checker.isStringNullOrEmpty(kode_kampus)) {
            		stmt.setNull(j++,java.sql.Types.VARCHAR);//26
            	}
            	else {
            		stmt.setString(j++,kode_kampus);//26	
            	}
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
    	boolean sukses = false;
    	if(ins>0) {
    		sukses = true;
    	}
    	return ins;	
    }

    
    public int terimaBuktiPembayaran(String field_name, Vector vReqAprKeu) {
    	int upd=0;
    	String targetNpmhs=null;
    	if(field_name!=null) {
    		StringTokenizer st = new StringTokenizer(field_name,"||");
    		//System.out.println("apasih = "+field_name);
    		boolean match = false;
    		//get id target
    		String element = null;
			String value = null;
    		while(st.hasMoreTokens() && !match) {
    			element = st.nextToken();
    			value = st.nextToken();
    			if(element.contains("kuiidReqested")) {
    				match = true;
    			}
    		}
    		try {
    			if(match) {
    				Context initContext  = new InitialContext();
    				Context envContext  = (Context)initContext.lookup("java:/comp/env");
    				ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    				con = ds.getConnection();
    				stmt = con.prepareStatement("select * from PYMNT_TRANSIT where KUIIDPYMNT=?");
    				stmt.setLong(1, Long.valueOf(value).longValue());
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					//procedd
    					String kuiid = ""+rs.getInt("KUIIDPYMNT");
    	    			kuiid = kuiid.replace("#", "_");
    	    			String kdpst = ""+rs.getString("KDPSTPYMNT");
    	    			kdpst = kdpst.replace("#", "_");
    	    			String npmhs = ""+rs.getString("NPMHSPYMNT");
    	    			npmhs = npmhs.replace("#", "_");
    	    			targetNpmhs = ""+npmhs;
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
    	    			//System.out.println("keterDetail isi = "+keterDetail);
    	    			String penyetor = ""+rs.getString("PAYEEPYMNT");
    	    			if(penyetor==null || Checker.isStringNullOrEmpty(penyetor)) {
    	    				penyetor = "null";
    	    			}
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
    	    		
    	    			//stmt = con.prepareStatement("delete from PYMNT_TRANSIT where KUIIDPYMNT=?");
    	    			//stmt.setLong(1, Long.valueOf(value).longValue());
    	    			//stmt.executeUpdate();
    	    			
    	    			
    	    			//insert to pymnt table
    	    			long nomrut =1+getNoRutTerakhir();
    	    			String sql = "INSERT INTO PYMNT (KDPSTPYMNT,NPMHSPYMNT,NORUTPYMNT,TGKUIPYMNT,TGTRSPYMNT,KETERPYMNT,KETER_PYMNT_DETAIL,PAYEEPYMNT,AMONTPYMNT,PYMTPYMNT,GELOMBANG,CICILAN,KRS,NOACCPYMNT,OPNPMPYMNT,OPNMMPYMNT,SETORPYMNT,NONPMPYMNT,VOIDDPYMNT,NOKODPYMNT,VOIDOPNPM,VOIDKETER,VOIDOPNMM,FILENAME,UPLOADTM,APROVALTM,REJECTTM,REJECTION_NOTE,NPM_APPROVEE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    	    			stmt=con.prepareStatement(sql);
    	    			if(kdpst!=null && !Checker.isStringNullOrEmpty(kdpst)) {
    	    				stmt.setString(1, kdpst);
    	    			}
    	    			else {
    	    				stmt.setNull(1, java.sql.Types.VARCHAR);
    	    			}
    	    			
    	    			if(npmhs!=null && !Checker.isStringNullOrEmpty(npmhs)) {
    	    				stmt.setString(2, npmhs);
    	    			}
    	    			else {
    	    				stmt.setNull(2, java.sql.Types.VARCHAR);
    	    			}
    	    			stmt.setLong(3, nomrut);
    	    			
    	    			try {
    	        			java.sql.Date trsdt = java.sql.Date.valueOf(tgkui);
    	        			stmt.setDate(4,trsdt);//5
    	        		}
    	        		catch(Exception e) {
    	        			stmt.setNull(4, java.sql.Types.DATE);
    	        		}
    	    			try {
    	        			java.sql.Date trsdt = java.sql.Date.valueOf(tgtrs);
    	        			stmt.setDate(5,trsdt);//5
    	        		}
    	        		catch(Exception e) {
    	        			stmt.setNull(5, java.sql.Types.DATE);
    	        		}
    	    			//keter disamakan dengan keter detail
    	    			if(keter!=null && !Checker.isStringNullOrEmpty(keter)) {
    	    				stmt.setString(6, keter);
    	    			}
    	    			else {
    	    				stmt.setNull(6, java.sql.Types.VARCHAR);
    	    			}
    	    			if(keterDetail!=null && !Checker.isStringNullOrEmpty(keterDetail)) {
    	    				stmt.setString(7, keterDetail);
    	    			}
    	    			else {
    	    				stmt.setNull(7, java.sql.Types.VARCHAR);
    	    			}
    	    			//if(penyetor!=null && !Checker.isStringNullOrEmpty(penyetor)) {
    	    			stmt.setString(8, penyetor);//tidak boleh null - value="null" berarti nyetor sendiri
    	    			//}
    	    			//else {
    	    			//	stmt.setNull(8, java.sql.Types.VARCHAR);
    	    			//}
    	    			stmt.setDouble(9, Double.valueOf(besaran).doubleValue());
    	    			if(posBiaya!=null && !Checker.isStringNullOrEmpty(posBiaya)) {
    	    				stmt.setString(10, posBiaya);
    	    			}
    	    			else {
    	    				stmt.setNull(10, java.sql.Types.VARCHAR);
    	    			}
    	    			stmt.setInt(11, Integer.valueOf(gelombangKe).intValue());
    	    			stmt.setInt(12, Integer.valueOf(cicilanKe).intValue());
    	    			stmt.setInt(13, Integer.valueOf(krs).intValue());
    	    			if(targetBankAcc!=null && !Checker.isStringNullOrEmpty(targetBankAcc)) {
    	    				stmt.setString(14, targetBankAcc);
    	    			}
    	    			else {
    	    				stmt.setNull(14, java.sql.Types.VARCHAR);
    	    			}
    	    			if(opnpm!=null && !Checker.isStringNullOrEmpty(opnpm)) {
    	    				stmt.setString(15, opnpm);
    	    			}
    	    			else {
    	    				stmt.setNull(15, java.sql.Types.VARCHAR);
    	    			}
    	    			if(opnmm!=null && !Checker.isStringNullOrEmpty(opnmm)) {
    	    				stmt.setString(16, opnmm);
    	    			}
    	    			else {
    	    				stmt.setNull(16, java.sql.Types.VARCHAR);
    	    			}
    	    			stmt.setBoolean(17, Boolean.valueOf(sdhDstorKeBank).booleanValue());
    	    			if(nonpmNggaTauUtkApa!=null && !Checker.isStringNullOrEmpty(nonpmNggaTauUtkApa)) {
    	    				stmt.setString(18, nonpmNggaTauUtkApa);
    	    			}
    	    			else {
    	    				stmt.setNull(18, java.sql.Types.VARCHAR);
    	    			}
    	    			stmt.setBoolean(19, Boolean.valueOf(batal).booleanValue());
    	    			
    	    			if(noKodePmnt!=null && !Checker.isStringNullOrEmpty(noKodePmnt)) {
    	    				stmt.setString(20, noKodePmnt);
    	    			}
    	    			else {
    	    				stmt.setNull(20, java.sql.Types.VARCHAR);
    	    			}
    	    			if(npmVoider!=null && !Checker.isStringNullOrEmpty(npmVoider)) {
    	    				stmt.setString(21, npmVoider);
    	    			}
    	    			else {
    	    				stmt.setNull(21, java.sql.Types.VARCHAR);
    	    			}
    	    			if(keterVoid!=null && !Checker.isStringNullOrEmpty(keterVoid)) {
    	    				stmt.setString(22, keterVoid);
    	    			}
    	    			else {
    	    				stmt.setNull(22, java.sql.Types.VARCHAR);
    	    			}
    	    			
    	    			if(nmmVoider!=null && !Checker.isStringNullOrEmpty(nmmVoider)) {
    	    				stmt.setString(23, nmmVoider);
    	    			}
    	    			else {
    	    				stmt.setNull(23, java.sql.Types.VARCHAR);
    	    			}
    	    			if(namaBuktiFile!=null && !Checker.isStringNullOrEmpty(namaBuktiFile)) {
    	    				stmt.setString(24, namaBuktiFile);
    	    			}
    	    			else {
    	    				stmt.setNull(24, java.sql.Types.VARCHAR);
    	    			}
    	    			if(uploadTm!=null && !Checker.isStringNullOrEmpty(uploadTm)) {
    	    				stmt.setTimestamp(25, Timestamp.valueOf(uploadTm));
    	    			}
    	    			else {
    	    				stmt.setNull(25, java.sql.Types.TIMESTAMP);
    	    			}
    	    			if(aprovalTm!=null && !Checker.isStringNullOrEmpty(aprovalTm)) {
    	    				stmt.setTimestamp(26, Timestamp.valueOf(aprovalTm));
    	    			}
    	    			else {
    	    				stmt.setNull(26, java.sql.Types.TIMESTAMP);
    	    			}
    	    			if(rejectedTm!=null && !Checker.isStringNullOrEmpty(rejectedTm)) {
    	    				stmt.setTimestamp(27, Timestamp.valueOf(rejectedTm));
    	    			}
    	    			else {
    	    				stmt.setNull(27, java.sql.Types.TIMESTAMP);
    	    			}
    	    			if(rejectedNote!=null && !Checker.isStringNullOrEmpty(rejectedNote)) {
    	    				stmt.setString(28, rejectedNote);
    	    			}
    	    			else {
    	    				stmt.setNull(28, java.sql.Types.VARCHAR);
    	    			}
    	    			if(npmApprovee!=null && !Checker.isStringNullOrEmpty(npmApprovee)) {
    	    				stmt.setString(29, npmApprovee);
    	    			}
    	    			else {
    	    				//stmt.setNull(29, java.sql.Types.VARCHAR);
    	    				stmt.setString(29, this.operatorNpm);
    	    			}
    	    			upd=stmt.executeUpdate();
    	    			//juka berhasil input hapus t
    	    			if(upd>0) {
    	    				
    	    				stmt = con.prepareStatement("delete from PYMNT_TRANSIT where KUIIDPYMNT=?");
    	    				stmt.setLong(1, Long.valueOf(value).longValue());
    	    				stmt.executeUpdate();
    	    			}	
    				}
    				else {
    					//orang lain lebih dulu ngupdate
    				}
    				//int i=1;
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
    	
    	
    	return upd;	
    }	

    /*
     * menggunakan sumberdana
     */
    public int terimaBuktiPembayaranJsonStyle(String field_name, JSONArray jsoa, String[]updatedTargetAkun, String validatedTransDate, String idSumberDana) {
    	int upd=0;
    	long nomrut =0;
    	String groupId = null;
    	boolean norutSdhDitentukan = false;//perhitungan norut cukup 1 x saja
    	boolean nogroupSdhDitentukan = false;//perhitungan nofroup cukup 1 x saja
    	//System.out.println("accept");
    	//System.out.println("field_name="+field_name);
    	//System.out.println("jsoa="+jsoa.toString());
    	String targetNpmhs=null;
    	if(field_name!=null) {
    		StringTokenizer st = new StringTokenizer(field_name,"||");
    		boolean match = false;
    		//get id target
    		String element = null;
			String value = null;
    		while(st.hasMoreTokens() && !match) {
    			element = st.nextToken();
    			value = st.nextToken();
    			if(element.contains("noKuiReq")) {
    				match = true;
    			}
    		}
    		
    		try {
    			if(match) {
    				Context initContext  = new InitialContext();
    				Context envContext  = (Context)initContext.lookup("java:/comp/env");
    				ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    				con = ds.getConnection();
    				//stmt = con.prepareStatement("select * from PYMNT_TRANSIT where KUIIDPYMNT=?");
    				stmt = con.prepareStatement("select * from PYMNT_TRANSIT where NORUTPYMNT=?");
    				stmt.setLong(1, Long.valueOf(value).longValue());
    				rs = stmt.executeQuery();
    				while(rs.next()) {
    					//procedd
    					String kuiid = ""+rs.getInt("KUIIDPYMNT");
    	    			kuiid = kuiid.replace("#", "_");
    	    			String kdpst = ""+rs.getString("KDPSTPYMNT");
    	    			kdpst = kdpst.replace("#", "_");
    	    			String npmhs = ""+rs.getString("NPMHSPYMNT");
    	    			npmhs = npmhs.replace("#", "_");
    	    			targetNpmhs = ""+npmhs;
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
    	    			//System.out.println("keterDetail isi = "+keterDetail);
    	    			String penyetor = ""+rs.getString("PAYEEPYMNT");
    	    			if(penyetor==null || Checker.isStringNullOrEmpty(penyetor)) {
    	    				penyetor = "null";
    	    			}
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
    	    			String idObj = ""+rs.getLong("ID_OBJ");
    	    			String kodeKampus = ""+rs.getString("KODE_KAMPUS_DOMISILI");
    	    			//String groupId = ""+rs.getLong("GROUP_ID");
    	    			//stmt = con.prepareStatement("delete from PYMNT_TRANSIT where KUIIDPYMNT=?");
    	    			//stmt.setLong(1, Long.valueOf(value).longValue());
    	    			//stmt.executeUpdate();
    	    			
    	    			
    	    			//insert to pymnt table
    	    			//nourut cukup satu kali saja dihitungnya = no kuitansi , efek terhadap group transaksi
    	    			//begitu juga group id
    	    			if(!norutSdhDitentukan) {
    	    				nomrut =1+getNoRutTerakhir();
    	    				norutSdhDitentukan = true;
    	    			}
    	    			if(!nogroupSdhDitentukan) {
    	    				groupId = ""+rs.getLong("GROUP_ID");
    	    				nogroupSdhDitentukan = true;
    	    				if(groupId!=null && Long.parseLong(groupId)>0) {
    	    					groupId = ""+(getNoGroupIdTerakhirAtPymntTable()+1);
    	    				}
    	    				else {
    	    					groupId = null;
    	    				}
    	    			}
    	    			
    	    			String sql = "INSERT INTO PYMNT (KDPSTPYMNT,NPMHSPYMNT,NORUTPYMNT,TGKUIPYMNT,TGTRSPYMNT,KETERPYMNT,KETER_PYMNT_DETAIL,PAYEEPYMNT,AMONTPYMNT,PYMTPYMNT,GELOMBANG,CICILAN,KRS,NOACCPYMNT,OPNPMPYMNT,OPNMMPYMNT,SETORPYMNT,NONPMPYMNT,VOIDDPYMNT,NOKODPYMNT,VOIDOPNPM,VOIDKETER,VOIDOPNMM,FILENAME,UPLOADTM,APROVALTM,REJECTTM,REJECTION_NOTE,NPM_APPROVEE,GROUP_ID,IDPAKETBEASISWA,ID_OBJ,KODE_KAMPUS_DOMISILI) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    	    			stmt=con.prepareStatement(sql);
    	    			if(kdpst!=null && !Checker.isStringNullOrEmpty(kdpst)) {
    	    				stmt.setString(1, kdpst);
    	    			}
    	    			else {
    	    				stmt.setNull(1, java.sql.Types.VARCHAR);
    	    			}
    	    			
    	    			if(npmhs!=null && !Checker.isStringNullOrEmpty(npmhs)) {
    	    				stmt.setString(2, npmhs);
    	    			}
    	    			else {
    	    				stmt.setNull(2, java.sql.Types.VARCHAR);
    	    			}
    	    			//stmt.setLong(3, getNoRutTerakhir()+1);//
    	    			stmt.setLong(3, nomrut);
    	    			
    	    			try {
    	        			java.sql.Date trsdt = java.sql.Date.valueOf(tgkui);
    	        			stmt.setDate(4,trsdt);//5
    	        		}
    	        		catch(Exception e) {
    	        			stmt.setNull(4, java.sql.Types.DATE);
    	        		}
    	    			try {
    	    				//validatedTransDate
    	    				System.out.println("validatedTransDate="+validatedTransDate);
    	    				//validatedTransDate = validatedTransDate.replace("/", "-");
    	        			//java.sql.Date trsdt = java.sql.Date.valueOf(tgtrs); diganti ma validate tgl
    	    				java.sql.Date trsdt = Converter.formatDateBeforeInsert(validatedTransDate);
    	        			stmt.setDate(5,trsdt);//5
    	        		}
    	        		catch(Exception e) {
    	        			stmt.setNull(5, java.sql.Types.DATE);
    	        		}
    	    			//keter disamakan dengan keter detail
    	    			if(keter!=null && !Checker.isStringNullOrEmpty(keter)) {
    	    				stmt.setString(6, keter);
    	    			}
    	    			else {
    	    				stmt.setNull(6, java.sql.Types.VARCHAR);
    	    			}
    	    			if(keterDetail!=null && !Checker.isStringNullOrEmpty(keterDetail)) {
    	    				stmt.setString(7, keterDetail);
    	    			}
    	    			else {
    	    				stmt.setNull(7, java.sql.Types.VARCHAR);
    	    			}
    	    			//if(penyetor!=null && !Checker.isStringNullOrEmpty(penyetor)) {
    	    			stmt.setString(8, penyetor);//tidak boleh null - value="null" berarti nyetor sendiri
    	    			//}
    	    			//else {
    	    			//	stmt.setNull(8, java.sql.Types.VARCHAR);
    	    			//}
    	    			stmt.setDouble(9, Double.valueOf(besaran).doubleValue());
    	    			if(posBiaya!=null && !Checker.isStringNullOrEmpty(posBiaya)) {
    	    				stmt.setString(10, posBiaya);
    	    			}
    	    			else {
    	    				stmt.setNull(10, java.sql.Types.VARCHAR);
    	    			}
    	    			stmt.setInt(11, Integer.valueOf(gelombangKe).intValue());
    	    			stmt.setInt(12, Integer.valueOf(cicilanKe).intValue());
    	    			stmt.setInt(13, Integer.valueOf(krs).intValue());
    	    			//targetBankAkunNggaMungkinKosong--cek sama updatedTargetBankAkum
    	    			boolean matchPos = false;
    	    			for(int j=0;j<updatedTargetAkun.length&&!matchPos;j++) {
    	    				StringTokenizer stt = new StringTokenizer(updatedTargetAkun[j],":");
    	    				String pos = stt.nextToken();
    	    				String nuAkun = stt.nextToken();
    	    				if(pos.equalsIgnoreCase(posBiaya)) {
    	    					matchPos = true;
    	    					targetBankAcc = ""+nuAkun;
    	    				}
    	    			}
    	    			if(targetBankAcc!=null && !Checker.isStringNullOrEmpty(targetBankAcc)) {
    	    				stmt.setString(14, targetBankAcc);
    	    			}
    	    			else {
    	    				stmt.setNull(14, java.sql.Types.VARCHAR);
    	    			}
    	    			if(opnpm!=null && !Checker.isStringNullOrEmpty(opnpm)) {
    	    				stmt.setString(15, opnpm);
    	    			}
    	    			else {
    	    				stmt.setNull(15, java.sql.Types.VARCHAR);
    	    			}
    	    			if(opnmm!=null && !Checker.isStringNullOrEmpty(opnmm)) {
    	    				stmt.setString(16, opnmm);
    	    			}
    	    			else {
    	    				stmt.setNull(16, java.sql.Types.VARCHAR);
    	    			}
    	    			stmt.setBoolean(17, Boolean.valueOf(sdhDstorKeBank).booleanValue());
    	    			if(nonpmNggaTauUtkApa!=null && !Checker.isStringNullOrEmpty(nonpmNggaTauUtkApa)) {
    	    				stmt.setString(18, nonpmNggaTauUtkApa);
    	    			}
    	    			else {
    	    				stmt.setNull(18, java.sql.Types.VARCHAR);
    	    			}
    	    			stmt.setBoolean(19, Boolean.valueOf(batal).booleanValue());
    	    			
    	    			if(noKodePmnt!=null && !Checker.isStringNullOrEmpty(noKodePmnt)) {
    	    				stmt.setString(20, noKodePmnt);
    	    			}
    	    			else {
    	    				stmt.setNull(20, java.sql.Types.VARCHAR);
    	    			}
    	    			if(npmVoider!=null && !Checker.isStringNullOrEmpty(npmVoider)) {
    	    				stmt.setString(21, npmVoider);
    	    			}
    	    			else {
    	    				stmt.setNull(21, java.sql.Types.VARCHAR);
    	    			}
    	    			if(keterVoid!=null && !Checker.isStringNullOrEmpty(keterVoid)) {
    	    				stmt.setString(22, keterVoid);
    	    			}
    	    			else {
    	    				stmt.setNull(22, java.sql.Types.VARCHAR);
    	    			}
    	    			
    	    			if(nmmVoider!=null && !Checker.isStringNullOrEmpty(nmmVoider)) {
    	    				stmt.setString(23, nmmVoider);
    	    			}
    	    			else {
    	    				stmt.setNull(23, java.sql.Types.VARCHAR);
    	    			}
    	    			if(namaBuktiFile!=null && !Checker.isStringNullOrEmpty(namaBuktiFile)) {
    	    				stmt.setString(24, namaBuktiFile);
    	    			}
    	    			else {
    	    				stmt.setNull(24, java.sql.Types.VARCHAR);
    	    			}
    	    			if(uploadTm!=null && !Checker.isStringNullOrEmpty(uploadTm)) {
    	    				stmt.setTimestamp(25, Timestamp.valueOf(uploadTm));
    	    			}
    	    			else {
    	    				stmt.setNull(25, java.sql.Types.TIMESTAMP);
    	    			}
    	    			if(aprovalTm!=null && !Checker.isStringNullOrEmpty(aprovalTm)) {
    	    				stmt.setTimestamp(26, Timestamp.valueOf(aprovalTm));
    	    			}
    	    			else {
    	    				stmt.setNull(26, java.sql.Types.TIMESTAMP);
    	    			}
    	    			if(rejectedTm!=null && !Checker.isStringNullOrEmpty(rejectedTm)) {
    	    				stmt.setTimestamp(27, Timestamp.valueOf(rejectedTm));
    	    			}
    	    			else {
    	    				stmt.setNull(27, java.sql.Types.TIMESTAMP);
    	    			}
    	    			if(rejectedNote!=null && !Checker.isStringNullOrEmpty(rejectedNote)) {
    	    				stmt.setString(28, rejectedNote);
    	    			}
    	    			else {
    	    				stmt.setNull(28, java.sql.Types.VARCHAR);
    	    			}
    	    			if(npmApprovee!=null && !Checker.isStringNullOrEmpty(npmApprovee)) {
    	    				stmt.setString(29, npmApprovee);
    	    			}
    	    			else {
    	    				//stmt.setNull(29, java.sql.Types.VARCHAR);
    	    				stmt.setString(29, this.operatorNpm);
    	    			}
    	    			if(Checker.isStringNullOrEmpty(groupId)) {
    	    				stmt.setNull(30, java.sql.Types.INTEGER);
    	    			}
    	    			else {
    	    				stmt.setLong(30, Long.parseLong(groupId));
    	    			}
    	    			
    	    			if(Checker.isStringNullOrEmpty(idSumberDana)) {
    	    				stmt.setNull(31, java.sql.Types.INTEGER);
    	    			}
    	    			else {
    	    				stmt.setLong(31, Long.parseLong(idSumberDana));
    	    			}
    	    			
    	    			if(Checker.isStringNullOrEmpty(idObj)) {
    	    				stmt.setNull(32, java.sql.Types.INTEGER);
    	    			}
    	    			else {
    	    				stmt.setLong(32, Long.parseLong(idObj));
    	    			}
    	    			if(Checker.isStringNullOrEmpty(kodeKampus)) {
    	    				stmt.setNull(33, java.sql.Types.VARCHAR);
    	    			}
    	    			else {
    	    				stmt.setString(33, kodeKampus);
    	    			}

    	    			upd=stmt.executeUpdate();
    	    			//juka berhasil input hapus t
    	    			if(upd>0) {
    	    				
    	    				//stmt = con.prepareStatement("delete from PYMNT_TRANSIT where KUIIDPYMNT=?");
    	    				stmt = con.prepareStatement("delete from PYMNT_TRANSIT where NORUTPYMNT=?");
    	    				stmt.setLong(1, Long.valueOf(value).longValue());
    	    				stmt.executeUpdate();
    	    			}
    	    			else {
    					//orang lain lebih dulu ngupdate
    	    			}
    				//int i=1;
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
    	return upd;			
    }

    
    
    
    	/*
    	 * tanpa sumberdana
    	 */
    public int terimaBuktiPembayaranJsonStyle(String field_name, JSONArray jsoa, String[]updatedTargetAkun, String validatedTransDate) {
    	int upd=0;
    	long nomrut =0;
    	String groupId = null;
    	boolean norutSdhDitentukan = false;//perhitungan norut cukup 1 x saja
    	boolean nogroupSdhDitentukan = false;//perhitungan nofroup cukup 1 x saja
    	//System.out.println("accept");
    	//System.out.println("field_name="+field_name);
    	//System.out.println("jsoa="+jsoa.toString());
    	String targetNpmhs=null;
    	if(field_name!=null) {
    		StringTokenizer st = new StringTokenizer(field_name,"||");
    		boolean match = false;
    		//get id target
    		String element = null;
			String value = null;
    		while(st.hasMoreTokens() && !match) {
    			element = st.nextToken();
    			value = st.nextToken();
    			if(element.contains("noKuiReq")) {
    				match = true;
    			}
    		}
    		
    		try {
    			if(match) {
    				Context initContext  = new InitialContext();
    				Context envContext  = (Context)initContext.lookup("java:/comp/env");
    				ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    				con = ds.getConnection();
    				//stmt = con.prepareStatement("select * from PYMNT_TRANSIT where KUIIDPYMNT=?");
    				stmt = con.prepareStatement("select * from PYMNT_TRANSIT where NORUTPYMNT=?");
    				stmt.setLong(1, Long.valueOf(value).longValue());
    				rs = stmt.executeQuery();
    				while(rs.next()) {
    					//procedd
    					String kuiid = ""+rs.getInt("KUIIDPYMNT");
    	    			kuiid = kuiid.replace("#", "_");
    	    			String kdpst = ""+rs.getString("KDPSTPYMNT");
    	    			kdpst = kdpst.replace("#", "_");
    	    			String npmhs = ""+rs.getString("NPMHSPYMNT");
    	    			npmhs = npmhs.replace("#", "_");
    	    			targetNpmhs = ""+npmhs;
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
    	    			//System.out.println("keterDetail isi = "+keterDetail);
    	    			String penyetor = ""+rs.getString("PAYEEPYMNT");
    	    			if(penyetor==null || Checker.isStringNullOrEmpty(penyetor)) {
    	    				penyetor = "null";
    	    			}
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
    	    			String idObj = ""+rs.getLong("ID_OBJ");
    	    			String kodeKampus = ""+rs.getString("KODE_KAMPUS_DOMISILI");
    	    			//String groupId = ""+rs.getLong("GROUP_ID");
    	    			//stmt = con.prepareStatement("delete from PYMNT_TRANSIT where KUIIDPYMNT=?");
    	    			//stmt.setLong(1, Long.valueOf(value).longValue());
    	    			//stmt.executeUpdate();
    	    			
    	    			
    	    			//insert to pymnt table
    	    			//nourut cukup satu kali saja dihitungnya = no kuitansi , efek terhadap group transaksi
    	    			//begitu juga group id
    	    			if(!norutSdhDitentukan) {
    	    				nomrut =1+getNoRutTerakhir();
    	    				norutSdhDitentukan = true;
    	    			}
    	    			if(!nogroupSdhDitentukan) {
    	    				groupId = ""+rs.getLong("GROUP_ID");
    	    				nogroupSdhDitentukan = true;
    	    				if(groupId!=null && Long.parseLong(groupId)>0) {
    	    					groupId = ""+(getNoGroupIdTerakhirAtPymntTable()+1);
    	    				}
    	    				else {
    	    					groupId = null;
    	    				}
    	    			}
    	    			
    	    			String sql = "INSERT INTO PYMNT (KDPSTPYMNT,NPMHSPYMNT,NORUTPYMNT,TGKUIPYMNT,TGTRSPYMNT,KETERPYMNT,KETER_PYMNT_DETAIL,PAYEEPYMNT,AMONTPYMNT,PYMTPYMNT,GELOMBANG,CICILAN,KRS,NOACCPYMNT,OPNPMPYMNT,OPNMMPYMNT,SETORPYMNT,NONPMPYMNT,VOIDDPYMNT,NOKODPYMNT,VOIDOPNPM,VOIDKETER,VOIDOPNMM,FILENAME,UPLOADTM,APROVALTM,REJECTTM,REJECTION_NOTE,NPM_APPROVEE,GROUP_ID,ID_OBJ,KODE_KAMPUS_DOMISILI) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    	    			stmt=con.prepareStatement(sql);
    	    			if(kdpst!=null && !Checker.isStringNullOrEmpty(kdpst)) {
    	    				stmt.setString(1, kdpst);
    	    			}
    	    			else {
    	    				stmt.setNull(1, java.sql.Types.VARCHAR);
    	    			}
    	    			
    	    			if(npmhs!=null && !Checker.isStringNullOrEmpty(npmhs)) {
    	    				stmt.setString(2, npmhs);
    	    			}
    	    			else {
    	    				stmt.setNull(2, java.sql.Types.VARCHAR);
    	    			}
    	    			//stmt.setLong(3, getNoRutTerakhir()+1);//
    	    			stmt.setLong(3, nomrut);
    	    			
    	    			try {
    	        			java.sql.Date trsdt = java.sql.Date.valueOf(tgkui);
    	        			stmt.setDate(4,trsdt);//5
    	        		}
    	        		catch(Exception e) {
    	        			stmt.setNull(4, java.sql.Types.DATE);
    	        		}
    	    			try {
    	    				//validatedTransDate
    	    				//System.out.println("validatedTransDate="+validatedTransDate);
    	    				//validatedTransDate = validatedTransDate.replace("/", "-");
    	        			//java.sql.Date trsdt = java.sql.Date.valueOf(tgtrs); diganti ma validate tgl
    	    				java.sql.Date trsdt = Converter.formatDateBeforeInsert(validatedTransDate);
    	        			stmt.setDate(5,trsdt);//5
    	        		}
    	        		catch(Exception e) {
    	        			stmt.setNull(5, java.sql.Types.DATE);
    	        		}
    	    			//keter disamakan dengan keter detail
    	    			if(keter!=null && !Checker.isStringNullOrEmpty(keter)) {
    	    				stmt.setString(6, keter);
    	    			}
    	    			else {
    	    				stmt.setNull(6, java.sql.Types.VARCHAR);
    	    			}
    	    			if(keterDetail!=null && !Checker.isStringNullOrEmpty(keterDetail)) {
    	    				stmt.setString(7, keterDetail);
    	    			}
    	    			else {
    	    				stmt.setNull(7, java.sql.Types.VARCHAR);
    	    			}
    	    			//if(penyetor!=null && !Checker.isStringNullOrEmpty(penyetor)) {
    	    			stmt.setString(8, penyetor);//tidak boleh null - value="null" berarti nyetor sendiri
    	    			//}
    	    			//else {
    	    			//	stmt.setNull(8, java.sql.Types.VARCHAR);
    	    			//}
    	    			stmt.setDouble(9, Double.valueOf(besaran).doubleValue());
    	    			if(posBiaya!=null && !Checker.isStringNullOrEmpty(posBiaya)) {
    	    				stmt.setString(10, posBiaya);
    	    			}
    	    			else {
    	    				stmt.setNull(10, java.sql.Types.VARCHAR);
    	    			}
    	    			stmt.setInt(11, Integer.valueOf(gelombangKe).intValue());
    	    			stmt.setInt(12, Integer.valueOf(cicilanKe).intValue());
    	    			stmt.setInt(13, Integer.valueOf(krs).intValue());
    	    			//targetBankAkunNggaMungkinKosong--cek sama updatedTargetBankAkum
    	    			boolean matchPos = false;
    	    			for(int j=0;j<updatedTargetAkun.length&&!matchPos;j++) {
    	    				StringTokenizer stt = new StringTokenizer(updatedTargetAkun[j],":");
    	    				String pos = stt.nextToken();
    	    				String nuAkun = stt.nextToken();
    	    				if(pos.equalsIgnoreCase(posBiaya)) {
    	    					matchPos = true;
    	    					targetBankAcc = ""+nuAkun;
    	    				}
    	    			}
    	    			if(targetBankAcc!=null && !Checker.isStringNullOrEmpty(targetBankAcc)) {
    	    				stmt.setString(14, targetBankAcc);
    	    			}
    	    			else {
    	    				stmt.setNull(14, java.sql.Types.VARCHAR);
    	    			}
    	    			if(opnpm!=null && !Checker.isStringNullOrEmpty(opnpm)) {
    	    				stmt.setString(15, opnpm);
    	    			}
    	    			else {
    	    				stmt.setNull(15, java.sql.Types.VARCHAR);
    	    			}
    	    			if(opnmm!=null && !Checker.isStringNullOrEmpty(opnmm)) {
    	    				stmt.setString(16, opnmm);
    	    			}
    	    			else {
    	    				stmt.setNull(16, java.sql.Types.VARCHAR);
    	    			}
    	    			stmt.setBoolean(17, Boolean.valueOf(sdhDstorKeBank).booleanValue());
    	    			if(nonpmNggaTauUtkApa!=null && !Checker.isStringNullOrEmpty(nonpmNggaTauUtkApa)) {
    	    				stmt.setString(18, nonpmNggaTauUtkApa);
    	    			}
    	    			else {
    	    				stmt.setNull(18, java.sql.Types.VARCHAR);
    	    			}
    	    			stmt.setBoolean(19, Boolean.valueOf(batal).booleanValue());
    	    			
    	    			if(noKodePmnt!=null && !Checker.isStringNullOrEmpty(noKodePmnt)) {
    	    				stmt.setString(20, noKodePmnt);
    	    			}
    	    			else {
    	    				stmt.setNull(20, java.sql.Types.VARCHAR);
    	    			}
    	    			if(npmVoider!=null && !Checker.isStringNullOrEmpty(npmVoider)) {
    	    				stmt.setString(21, npmVoider);
    	    			}
    	    			else {
    	    				stmt.setNull(21, java.sql.Types.VARCHAR);
    	    			}
    	    			if(keterVoid!=null && !Checker.isStringNullOrEmpty(keterVoid)) {
    	    				stmt.setString(22, keterVoid);
    	    			}
    	    			else {
    	    				stmt.setNull(22, java.sql.Types.VARCHAR);
    	    			}
    	    			
    	    			if(nmmVoider!=null && !Checker.isStringNullOrEmpty(nmmVoider)) {
    	    				stmt.setString(23, nmmVoider);
    	    			}
    	    			else {
    	    				stmt.setNull(23, java.sql.Types.VARCHAR);
    	    			}
    	    			if(namaBuktiFile!=null && !Checker.isStringNullOrEmpty(namaBuktiFile)) {
    	    				stmt.setString(24, namaBuktiFile);
    	    			}
    	    			else {
    	    				stmt.setNull(24, java.sql.Types.VARCHAR);
    	    			}
    	    			if(uploadTm!=null && !Checker.isStringNullOrEmpty(uploadTm)) {
    	    				stmt.setTimestamp(25, Timestamp.valueOf(uploadTm));
    	    			}
    	    			else {
    	    				stmt.setNull(25, java.sql.Types.TIMESTAMP);
    	    			}
    	    			if(aprovalTm!=null && !Checker.isStringNullOrEmpty(aprovalTm)) {
    	    				stmt.setTimestamp(26, Timestamp.valueOf(aprovalTm));
    	    			}
    	    			else {
    	    				stmt.setNull(26, java.sql.Types.TIMESTAMP);
    	    			}
    	    			if(rejectedTm!=null && !Checker.isStringNullOrEmpty(rejectedTm)) {
    	    				stmt.setTimestamp(27, Timestamp.valueOf(rejectedTm));
    	    			}
    	    			else {
    	    				stmt.setNull(27, java.sql.Types.TIMESTAMP);
    	    			}
    	    			if(rejectedNote!=null && !Checker.isStringNullOrEmpty(rejectedNote)) {
    	    				stmt.setString(28, rejectedNote);
    	    			}
    	    			else {
    	    				stmt.setNull(28, java.sql.Types.VARCHAR);
    	    			}
    	    			if(npmApprovee!=null && !Checker.isStringNullOrEmpty(npmApprovee)) {
    	    				stmt.setString(29, npmApprovee);
    	    			}
    	    			else {
    	    				//stmt.setNull(29, java.sql.Types.VARCHAR);
    	    				stmt.setString(29, this.operatorNpm);
    	    			}
    	    			if(Checker.isStringNullOrEmpty(groupId)) {
    	    				stmt.setNull(30, java.sql.Types.INTEGER);
    	    			}
    	    			else {
    	    				stmt.setLong(30, Long.parseLong(groupId));
    	    			}
    	    			
    	    			if(Checker.isStringNullOrEmpty(idObj)) {
    	    				stmt.setNull(31, java.sql.Types.INTEGER);
    	    			}
    	    			else {
    	    				stmt.setLong(31, Long.parseLong(idObj));
    	    			}
    	    			if(Checker.isStringNullOrEmpty(kodeKampus)) {
    	    				stmt.setNull(32, java.sql.Types.VARCHAR);
    	    			}
    	    			else {
    	    				stmt.setString(32, kodeKampus);
    	    			}
    	    			
    	    			upd=stmt.executeUpdate();
    	    			//juka berhasil input hapus t
    	    			if(upd>0) {
    	    				
    	    				//stmt = con.prepareStatement("delete from PYMNT_TRANSIT where KUIIDPYMNT=?");
    	    				stmt = con.prepareStatement("delete from PYMNT_TRANSIT where NORUTPYMNT=?");
    	    				stmt.setLong(1, Long.valueOf(value).longValue());
    	    				stmt.executeUpdate();
    	    			}
    	    			else {
    					//orang lain lebih dulu ngupdate
    	    			}
    				//int i=1;
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
    	
    	
    	return upd;	
    }	
   
    
    public String tolakBuktiPembayaran(String field_name, Vector vReqAprKeu) {
    	String keterDetail=null,besaran=null;
    	String targetNpmhs=null;
    	if(field_name!=null) {
    		StringTokenizer st = new StringTokenizer(field_name,"||");
    		//System.out.println("apasih = "+field_name);
    		boolean match = false;
    		//get id target
    		String element = null;
			String value = null;
    		while(st.hasMoreTokens() && !match) {
    			element = st.nextToken();
    			value = st.nextToken();
    			if(element.contains("kuiidReqested")) {
    				match = true;
    			}
    		}
    		try {
    			if(match) {
    				Context initContext  = new InitialContext();
    				Context envContext  = (Context)initContext.lookup("java:/comp/env");
    				ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    				con = ds.getConnection();
    				stmt = con.prepareStatement("select * from PYMNT_TRANSIT where KUIIDPYMNT=?");
    				stmt.setLong(1, Long.valueOf(value).longValue());
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					//procedd
    					String kuiid = ""+rs.getInt("KUIIDPYMNT");
    	    			kuiid = kuiid.replace("#", "_");
    	    			String kdpst = ""+rs.getString("KDPSTPYMNT");
    	    			kdpst = kdpst.replace("#", "_");
    	    			String npmhs = ""+rs.getString("NPMHSPYMNT");
    	    			npmhs = npmhs.replace("#", "_");
    	    			targetNpmhs = ""+npmhs;
    	    			String norut = ""+rs.getInt("NORUTPYMNT");
    	    			norut = norut.replace("#", "_");
    	    			String tgkui = ""+rs.getDate("TGKUIPYMNT");
    	    			tgkui = tgkui.replace("#", "_");
    	    			String tgtrs = ""+rs.getDate("TGTRSPYMNT");
    	    			tgtrs = tgtrs.replace("#", "_");
    	    			String keter = ""+rs.getString("KETERPYMNT");
    	    			keter = keter.replace("#", "_");
    	    			keterDetail = ""+rs.getString("KETER_PYMNT_DETAIL");
    	    			keterDetail = keterDetail.replace("#", "_");
    	    			//System.out.println("keterDetail isi = "+keterDetail);
    	    			String penyetor = ""+rs.getString("PAYEEPYMNT");
    	    			if(penyetor==null || Checker.isStringNullOrEmpty(penyetor)) {
    	    				penyetor = "null";
    	    			}
    	    			penyetor = penyetor.replace("#", "_");
    	    			besaran = ""+rs.getDouble("AMONTPYMNT");
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
    	    		
    	    			//stmt = con.prepareStatement("delete from PYMNT_TRANSIT where KUIIDPYMNT=?");
    	    			//stmt.setLong(1, Long.valueOf(value).longValue());
    	    			//stmt.executeUpdate();
    	    			
    	    			  
    	    			
    	    				
    	    			stmt = con.prepareStatement("delete from PYMNT_TRANSIT where KUIIDPYMNT=?");
    	    			stmt.setLong(1, Long.valueOf(value).longValue());
    	    			stmt.executeUpdate();
    	    				
    				}
    				else {
    					//orang lain lebih dulu ngupdate
    				}
    				//int i=1;
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
    	
    	
    	return keterDetail+"||"+besaran;	
    }	

    
    public String tolakBuktiPembayaranJsonStyle(String field_name, JSONArray jsoa) {
    	//System.out.println("action");
    	String keterDetail=null,besaran=null;
    	String targetNpmhs=null;
    	//System.out.println("-"+jsoa.toString());
    	//System.out.println("-"+field_name);
    	
    	if(field_name!=null) {
    		StringTokenizer st = new StringTokenizer(field_name,"||");
    		//System.out.println("apasih = "+field_name);
    		boolean match = false;
    		//get id target
    		String element = null;
			String value = null;
    		while(st.hasMoreTokens() && !match) {
    			element = st.nextToken();
    			value = st.nextToken();
    			if(element.contains("noKuiReq")) {
    				match = true;
    			}
    		}
    		try {
    			if(match) {
    				Context initContext  = new InitialContext();
    				Context envContext  = (Context)initContext.lookup("java:/comp/env");
    				ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    				con = ds.getConnection();
    				//stmt = con.prepareStatement("select * from PYMNT_TRANSIT where KUIIDPYMNT=?");
    				stmt = con.prepareStatement("select * from PYMNT_TRANSIT where NORUTPYMNT=?");
    				stmt.setLong(1, Long.valueOf(value).longValue());
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					//procedd
    					String kuiid = ""+rs.getInt("KUIIDPYMNT");
    	    			kuiid = kuiid.replace("#", "_");
    	    			String kdpst = ""+rs.getString("KDPSTPYMNT");
    	    			kdpst = kdpst.replace("#", "_");
    	    			String npmhs = ""+rs.getString("NPMHSPYMNT");
    	    			npmhs = npmhs.replace("#", "_");
    	    			targetNpmhs = ""+npmhs;
    	    			String norut = ""+rs.getInt("NORUTPYMNT");
    	    			norut = norut.replace("#", "_");
    	    			String tgkui = ""+rs.getDate("TGKUIPYMNT");
    	    			tgkui = tgkui.replace("#", "_");
    	    			String tgtrs = ""+rs.getDate("TGTRSPYMNT");
    	    			tgtrs = tgtrs.replace("#", "_");
    	    			String keter = ""+rs.getString("KETERPYMNT");
    	    			keter = keter.replace("#", "_");
    	    			keterDetail = ""+rs.getString("KETER_PYMNT_DETAIL");
    	    			keterDetail = keterDetail.replace("#", "_");
    	    			//System.out.println("keterDetail isi = "+keterDetail);
    	    			String penyetor = ""+rs.getString("PAYEEPYMNT");
    	    			if(penyetor==null || Checker.isStringNullOrEmpty(penyetor)) {
    	    				penyetor = "null";
    	    			}
    	    			penyetor = penyetor.replace("#", "_");
    	    			besaran = ""+rs.getDouble("AMONTPYMNT");
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
    	    		
    	    			//stmt = con.prepareStatement("delete from PYMNT_TRANSIT where KUIIDPYMNT=?");
    	    			//stmt.setLong(1, Long.valueOf(value).longValue());
    	    			//stmt.executeUpdate();
    	    			
    	    			  
    	    			
    	    				
    	    			//stmt = con.prepareStatement("delete from PYMNT_TRANSIT where KUIIDPYMNT=?");
    	    			stmt = con.prepareStatement("delete from PYMNT_TRANSIT where NORUTPYMNT=?");
    	    			stmt.setLong(1, Long.valueOf(value).longValue());
    	    			stmt.executeUpdate();
    	    				
    				}
    				else {
    					//orang lain lebih dulu ngupdate
    				}
    				//int i=1;
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
    	
    	
    	return keterDetail+"||"+besaran;	
    }	
    
}
