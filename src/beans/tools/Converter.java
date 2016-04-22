package beans.tools;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

import beans.setting.Constants;

import java.util.Collections;
import java.util.Vector;
import java.util.ListIterator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
/**
 * Session Bean implementation class Converter
 */
@Stateless
@LocalBean
public class Converter {

    /**
     * Default constructor. 
     */
    public Converter() {
        // TODO Auto-generated constructor stub
    }

    public static java.sql.Date formatDateBeforeInsert(String tanggal) {
    	java.sql.Date dt = null;
    	if(!Checker.isStringNullOrEmpty(tanggal)) {
    		try {
    			tanggal = tanggal.replace("/", "-");
            	StringTokenizer st = new StringTokenizer(tanggal,"-");
            	String tgl = st.nextToken();
            	String bln = st.nextToken();
            	String thn = st.nextToken();
            	if(tgl.length()==4) {
            		String tmp_thn = ""+tgl;
            		tgl = ""+thn;
            		thn = ""+tmp_thn;
            	}
            	dt = java.sql.Date.valueOf(thn+"-"+bln+"-"+tgl);	
    		}
    		catch(Exception e) {
    			
    		}
    			
    	}
    	
    	return dt;
    }
    
    public static String ubahKeformatTahunAkademik(String thsms) {
    	
    	String th = thsms.substring(0,4);
    	return th = th+"/"+(Integer.parseInt(th)+1);
    	
    }
    
    public static String getSemesterGenapGanjil(String thsms) {
    	
    	String sms = thsms.substring(4,thsms.length());
    	if(sms.equalsIgnoreCase("1")) {
    		sms = "GANJIL";
    	}
    	else {
    		sms = "GENAP";
    	}
    	return sms;
    	
    }
    
    public static java.sql.Time formatTimeBeforeInsert(String time) {
    	java.sql.Time tm = null;
    	StringTokenizer st = new StringTokenizer(time,":");
    	if(!Checker.isStringNullOrEmpty(time) && st.countTokens()>1) {
    		try {
    			long ss = 0;
    			long mm = 0;
    			long hh = 0;
    			
    			hh = Long.parseLong(st.nextToken());
    			mm = Long.parseLong(st.nextToken());
    			if(st.hasMoreTokens()) {
    				ss = Long.parseLong(st.nextToken());
    			}
            	
            	tm = java.sql.Time.valueOf(hh+":"+mm+":"+ss);	
    		}
    		catch(Exception e) {
    			
    		}
    			
    	}
    	
    	return tm;
    }
    
    public static String reformatSqlDateToTglBlnThn(String tanggalDalamFormatSql) {
    	//java.sql.Date dt = null;
    	//tanggalDalamFormatSql = tanggalDalamFormatSql.replace("/", "-");
    	StringTokenizer st = new StringTokenizer(tanggalDalamFormatSql,"-");
    	String thn = st.nextToken();
    	String bln = st.nextToken();
    	String tgl = st.nextToken();
    	tanggalDalamFormatSql = ""+tgl+"/"+bln+"/"+thn;
    	
    	return tanggalDalamFormatSql;
    }
    
    public static String convertTandaKoma(String stmn) {
    	if(stmn.contains(",")) {
    		stmn = stmn.replaceAll(",", "tandaKoma");
    	}
    	return stmn;
    }
    
    public static String revertTandaKoma(String stmn) {
    	if(stmn.contains("tandaKoma")) {
    		stmn = stmn.replaceAll("tandaKoma", ",");
    	}
    	return stmn;
    }
    
    public static String formatDdSlashMmSlashYy(String tanggal) {
    	//java.sql.Date dt = null;
    	tanggal = tanggal.replace("-", "/");
    	StringTokenizer st = new StringTokenizer(tanggal,"/");
    	String tgl = st.nextToken();
    	String bln = st.nextToken();
    	String thn = st.nextToken();
    	if(tgl.length()==4) {
    		String tmp_thn = ""+tgl;
    		tgl = ""+thn;
    		thn = ""+tmp_thn;
    	}
    	tanggal = (tgl+"/"+bln+"/"+thn);
    	return tanggal;
    }

    
    public static String convertThsms(String thsms) {
    	String tahun = thsms.substring(0,4);
		String sms = thsms.substring(4,thsms.length());
		String keter_thsms="";
		if(sms.equalsIgnoreCase("1")) {
			//keter_thsms = "SMS GANJIL / "+tahun+"#&"+tahun+"1";
			keter_thsms = tahun+"1 (SMS GANJIL)#&"+tahun+"1";
		}
		else {
			if(sms.equalsIgnoreCase("1A")||sms.equalsIgnoreCase("A")) {
				//keter_thsms = "SMS ANTARA I / "+tahun+"#&"+tahun+"A";
				keter_thsms = tahun+"1 (SMS antara I)#&"+tahun+"A";
			}
			else {
				if(sms.equalsIgnoreCase("2")) {
					//keter_thsms = "SMS GENAP / "+tahun+"#&"+tahun+"2";
					keter_thsms = tahun+"2 (SMS GENAP)#&"+tahun+"2";
				}
				else {
					if(sms.equalsIgnoreCase("2B")||sms.equalsIgnoreCase("B")) {
						//keter_thsms = "SMS ANTARA II / "+tahun+"#&"+tahun+"B";
						keter_thsms = tahun+"2 (SMS antara II)#&"+tahun+"B";
					}
				}
			}
		}
		return keter_thsms;
    }

    public static String convertThsmsKeterOnlyFromatThnAkademik(String thsms) {
    	String keter_thsms="";
    	if(thsms!=null && !thsms.equalsIgnoreCase("null")&& !thsms.equalsIgnoreCase("NULL")&& !thsms.equalsIgnoreCase("n/a")&& !thsms.equalsIgnoreCase("N/A")) {
    		String tahun = thsms.substring(0,4);
    		String sms = thsms.substring(4,thsms.length());
    		
    		if(sms.equalsIgnoreCase("1")) {
			//keter_thsms = "SMS GANJIL / "+tahun+"#&"+tahun+"1";
    			keter_thsms = "GANJIL "+tahun+"/"+(Integer.valueOf(tahun).intValue()+1);
    		}
    		else {
    			if(sms.equalsIgnoreCase("1A")||sms.equalsIgnoreCase("A")) {
				//keter_thsms = "SMS ANTARA I / "+tahun+"#&"+tahun+"A";
    				keter_thsms = "ANTARA I "+tahun+"/"+(Integer.valueOf(tahun).intValue()+1);
    			}
    			else {
    				if(sms.equalsIgnoreCase("2")) {
					//keter_thsms = "SMS GENAP / "+tahun+"#&"+tahun+"2";
    					keter_thsms = "GENAP "+tahun+"/"+(Integer.valueOf(tahun).intValue()+1);
    				}
    				else {
    					if(sms.equalsIgnoreCase("2B")||sms.equalsIgnoreCase("B")) {
						//keter_thsms = "SMS ANTARA II / "+tahun+"#&"+tahun+"B";
    						keter_thsms = "ANTARA II "+tahun+"/"+(Integer.valueOf(tahun).intValue()+1);
    					}
    				}	
				}
			}
		}
    	else {
    		keter_thsms="N/A";
    	}
		return keter_thsms;
    }
    
    
    public static String convertThsmsKeterOnly(String thsms) {
    	String keter_thsms="";
    	if(thsms!=null && !thsms.equalsIgnoreCase("null")&& !thsms.equalsIgnoreCase("NULL")&& !thsms.equalsIgnoreCase("n/a")&& !thsms.equalsIgnoreCase("N/A")) {
    		String tahun = thsms.substring(0,4);
    		String sms = thsms.substring(4,thsms.length());
    		
    		if(sms.equalsIgnoreCase("1")) {
			//keter_thsms = "SMS GANJIL / "+tahun+"#&"+tahun+"1";
    			keter_thsms = tahun+"1 (SMS GANJIL)";
    		}
    		else {
    			if(sms.equalsIgnoreCase("1A")||sms.equalsIgnoreCase("A")) {
				//keter_thsms = "SMS ANTARA I / "+tahun+"#&"+tahun+"A";
    				keter_thsms = tahun+"1 (SMS antara I)";
    			}
    			else {
    				if(sms.equalsIgnoreCase("2")) {
					//keter_thsms = "SMS GENAP / "+tahun+"#&"+tahun+"2";
    					keter_thsms = tahun+"2 (SMS GENAP)";
    				}
    				else {
    					if(sms.equalsIgnoreCase("2B")||sms.equalsIgnoreCase("B")) {
						//keter_thsms = "SMS ANTARA II / "+tahun+"#&"+tahun+"B";
    						keter_thsms = tahun+"2 (SMS antara II)";
    					}
    				}	
				}
			}
		}
    	else {
    		keter_thsms="N/A";
    	}
		return keter_thsms;
    }
    
    public static String convertThsmsValueOnly(String thsms) {
    	if(thsms!=null && thsms.length()>5) {
    		thsms = thsms.substring(0,4)+thsms.substring(5,6);
    	}
		return thsms;
    }

    
    
    public static String convertKdjek(String kdjek) {
    	String[]gender = Constants.getOptionGender();
    	boolean match = false;
    	for(int i=0;i<gender.length && !match;i++) {
    		StringTokenizer st = new StringTokenizer(gender[i]);
    		String val = st.nextToken();
    		String ket = st.nextToken();
    		if(val.equalsIgnoreCase(kdjek)) {
    			match = true;
    			kdjek = ket;
    		}
    	}
    	return kdjek.toUpperCase();
    }

    public static String getDetailKdjen(String kdjen) {
    	String jenjang = "null";
    	if(kdjen.equalsIgnoreCase("A")) {
    		jenjang = "S-3";
    	}
    	else {
    		if(kdjen.equalsIgnoreCase("B")) {
    			jenjang = "S-2";
        	}
        	else {
        		if(kdjen.equalsIgnoreCase("C")) {
        			jenjang = "S-1";
            	}
            	else {
            		if(kdjen.equalsIgnoreCase("D")) {
            			jenjang = "D-IV";
                	}
                	else {
                		if(kdjen.equalsIgnoreCase("E")) {
                			jenjang = "D-III";
                    	}
                    	else {
                    		if(kdjen.equalsIgnoreCase("G")) {
                    			jenjang = "D-II";
                        	}
                        	else {
                        		if(kdjen.equalsIgnoreCase("H")) {
                        			jenjang = "D-I";
                            	}
                            	else {
                            		
                            	}
                        	}
                    	}
                	}
            	}
        	}
    	}
    	return jenjang;
    }
    
    public static String getKeterTipeIka(String kode_tipe_ika) {
    	String []list_ika = Constants.getTipeIkatanKerjaDosen();
    	String keter = null;
    	boolean match = false;
    	for(int i=0;i<list_ika.length && !match;i++) {
    		StringTokenizer st = new StringTokenizer(list_ika[i],"-");
    		String kode = st.nextToken();
    		keter = st.nextToken();
    		if(kode.equalsIgnoreCase(kode_tipe_ika)) {
    			match = true;
    		}
    	}
    	return keter;
    }
    
    public static String getDetailKdpst(String kdpst) {
    	String info = "null#&null";
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * FROM MSPST where KDPSTMSPST=?");
    		stmt.setString(1, kdpst);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String nmpst = ""+rs.getString("NMPSTMSPST");
    			String kdjen = ""+rs.getString("KDJENMSPST");
    			info = nmpst+"#&"+kdjen;
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
    	return info;
    }
    
    
    public static String getNamaKdpst(String kdpst) {
    	String nmpst = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * FROM MSPST where KDPSTMSPST=?");
    		stmt.setString(1, kdpst);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			nmpst = ""+rs.getString("NMPSTMSPST");
    			//String kdjen = ""+rs.getString("KDJENMSPST");
    			//info = nmpst+"#&"+kdjen;
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
    	return nmpst;
    }    
   
    public static String getNamaFakultas(String kdpst) {
    	String nmfak = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT NMFAKMSFAK FROM MSFAK inner join MSPST on KDFAKMSFAK=KDFAKMSPST where KDPSTMSPST=?");
    		stmt.setString(1, kdpst);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			nmfak = ""+rs.getString("NMFAKMSFAK");
    			//String kdjen = ""+rs.getString("KDJENMSPST");
    			//info = nmpst+"#&"+kdjen;
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
    	return nmfak;
    }     
    

    public static String getKdjen(String kdpst) {
    	String info = "null#&null";
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * FROM MSPST where KDPSTMSPST=?");
    		stmt.setString(1, kdpst);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			//String nmpst = ""+rs.getString("NMPSTMSPST");
    			String kdjen = ""+rs.getString("KDJENMSPST");
    			info = kdjen;
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
    	return info;
    }
    
    
    public static String prepNumberString(String numberString) {
    	//remove white space
    	StringTokenizer st = new StringTokenizer(numberString);
    	return st.nextToken();
    }
    public static String convertStpid(String stpid) {
    	String[]tipe = Constants.getTipeCivitas();
    	boolean match = false;
    	for(int i=0;i<tipe.length && !match;i++) {
    		StringTokenizer st = new StringTokenizer(tipe[i]);
    		String val = st.nextToken();
    		String ket = st.nextToken();
    		if(val.equalsIgnoreCase(stpid)) {
    			match = true;
    			stpid = ket;
    		}
    	}
    	return stpid.toUpperCase();
    }

    
    public static int convertNamaBulanToInt(String nama_bulan) {
    	int bln = 0;
    	if(nama_bulan.equalsIgnoreCase("januari")||nama_bulan.equalsIgnoreCase("january")||nama_bulan.equalsIgnoreCase("jan")) {
    		bln=1;
    	}
    	if(nama_bulan.equalsIgnoreCase("feb")||nama_bulan.equalsIgnoreCase("februari")||nama_bulan.equalsIgnoreCase("february")||nama_bulan.equalsIgnoreCase("pebruari")) {
    		bln=2;
    	}
    	if(nama_bulan.equalsIgnoreCase("maret")||nama_bulan.equalsIgnoreCase("march")||nama_bulan.equalsIgnoreCase("mar")) {
    		bln=3;
    	}
    	if(nama_bulan.equalsIgnoreCase("april")||nama_bulan.equalsIgnoreCase("apr")) {
    		bln=4;
    	}
    	if(nama_bulan.equalsIgnoreCase("mei")||nama_bulan.equalsIgnoreCase("may")) {
    		bln=5;
    	}
    	if(nama_bulan.equalsIgnoreCase("jun")||nama_bulan.equalsIgnoreCase("juni")||nama_bulan.equalsIgnoreCase("jun")) {
    		bln=6;
    	}
    	if(nama_bulan.equalsIgnoreCase("jul")||nama_bulan.equalsIgnoreCase("july")||nama_bulan.equalsIgnoreCase("juli")) {
    		bln=7;
    	}
    	if(nama_bulan.equalsIgnoreCase("aug")||nama_bulan.equalsIgnoreCase("agustus")||nama_bulan.equalsIgnoreCase("august")) {
    		bln=8;
    	}
    	if(nama_bulan.equalsIgnoreCase("sept")||nama_bulan.equalsIgnoreCase("sep")||nama_bulan.equalsIgnoreCase("september")) {
    		bln=9;
    	}
    	if(nama_bulan.equalsIgnoreCase("okt")||nama_bulan.equalsIgnoreCase("oct")||nama_bulan.equalsIgnoreCase("oktober")||nama_bulan.equalsIgnoreCase("october")) {
    		bln=10;
    	}
    	if(nama_bulan.equalsIgnoreCase("nov")||nama_bulan.equalsIgnoreCase("november")||nama_bulan.equalsIgnoreCase("nopember")) {
    		bln=11;
    	}
    	if(nama_bulan.equalsIgnoreCase("desember")||nama_bulan.equalsIgnoreCase("december")) {
    		bln=12;
    	}
    	return bln;
    }
    /*
     * pindah ke Tool
     
    public static String gantikanSpecialChar(String brs) {
    	brs = brs.replace("&", "tandaDan");
    	brs = brs.replace("#","tandaPagar");
    	return brs;
    }
    
    public static String kembalikanSpecialChar(String brs) {
    	brs = brs.replace("tandaDan","&");
    	brs = brs.replace("tandaPagar","#");
    	return brs;
    }
    */
    public static String convertIntToNamaBulan(int month) {
    	String namaBulan = "";
    	if(month==1) {
    		namaBulan = "JANUARI";
    	}
    	if(month==2) {
    		namaBulan = "FEBRUARI";
    	}
    	if(month==3) {
    		namaBulan = "MARET";
    	}
    	if(month==4) {
    		namaBulan = "APRIL";
    	}
    	if(month==5) {
    		namaBulan = "MEI";
    	}
    	if(month==6) {
    		namaBulan = "JUNI";
    	}
    	if(month==7) {
    		namaBulan = "JULI";
    	}
    	if(month==8) {
    		namaBulan = "AGUSTUS";
    	}
    	if(month==9) {
    		namaBulan = "SEPTEMBER";
    	}
    	if(month==10) {
    		namaBulan = "OKTOBER";
    	}
    	if(month==11) {
    		namaBulan = "NOVEMBER";
    	}
    	if(month==12) {
    		namaBulan = "DESEMBER";
    	}
    	return namaBulan;
    }
    
    public static String convertFormatTanggalKeFormatDeskriptif(String tglToString) {
    	StringTokenizer st = new StringTokenizer(tglToString,"-");
    	String dd = "",mm="",yy="";
    	String tmp = "";
    	if(st.countTokens()==3) {
    		yy = st.nextToken();
    		mm = st.nextToken();
    		dd = st.nextToken();
    		int month = Integer.valueOf(mm).intValue();
    		int day = Integer.valueOf(dd).intValue();
    		mm=convertIntToNamaBulan(month);
    		mm=beans.tools.Tool.capFirstLetterInWord(mm);
    		tmp = dd+" "+mm+" "+yy;
    	}
    	return tmp;
    }
    
    public static String getObjLvlGiven(String kdpst) {
    	//fungsi ini mengambil objec lvl utk KDPST tertulis (1 kdpst bisa ada beberapa level)
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String tkn_obj_lvl = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * FROM OBJECT where KDPST=?");
    		stmt.setString(1, kdpst);
    		rs = stmt.executeQuery();
    		tkn_obj_lvl = "";
    		while(rs.next()) {
    			tkn_obj_lvl = tkn_obj_lvl + rs.getInt("OBJ_LEVEL")+",";
    		}
    		if(tkn_obj_lvl.length()>0) {
    			tkn_obj_lvl = tkn_obj_lvl.substring(0,tkn_obj_lvl.length()-1);
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
    	return tkn_obj_lvl;	
    }
    
    public static String prepForInputTextToDb(String text) {
    	//convert return char \n -> <br/>;
    	text = text.replace("\n", "<br/>");
    	return text;
    }
    
    public static String reversePrepForInputTextToDb(String text) {
    	//convert return char \n -> <br/>;
    	text = text.replace("<br/>","\n");
    	return text;
    }
    
    public static Vector getPilihanShiftYgAktif(String kdjen) {
    	//fungsi ini mengambil objec lvl utk KDPST tertulis (1 kdpst bisa ada beberapa level)
    	Vector v = new Vector();
    	if(kdjen!=null) {
    		kdjen = kdjen.toLowerCase();
    		String url=null;     
    		Connection con=null;
    		PreparedStatement stmt=null;
    		ResultSet rs=null;
    		DataSource ds=null;
    		String tkn_obj_lvl = null;
    	
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    		//cretae NPM auto increment
    			stmt = con.prepareStatement("SELECT * FROM SHIFT where AKTIF=?");
    			stmt.setBoolean(1, true);
    			rs = stmt.executeQuery();
    			ListIterator li = v.listIterator();
    			while(rs.next()) {
    				String ket = rs.getString("KETERANGAN");
    				//ket = ket.toUpperCase();
    				String shift = rs.getString("SHIFT");
    				String hari = rs.getString("HARI");
    				String tkn_kdjen = rs.getString("TOKEN_KDJEN_AVAILABILITY");
    				tkn_kdjen = tkn_kdjen.toLowerCase();
    				String konversi_kod = rs.getString("KODE_KONVERSI");
    				if(tkn_kdjen.contains(kdjen)) {
    					li.add(ket+"#&"+shift+"#&"+hari+"#&"+konversi_kod);
    				}	
    			}
    			Collections.sort(v);
    			li = v.listIterator();
    			String tmp = "";
    			while(li.hasNext()) {
    				String brs = (String) li.next();
    				if(brs.startsWith("N/A")||brs.startsWith("n/a")) {
    					tmp = ""+brs;
    					li.remove();
    				}
    			}
    			li.add(tmp);
    		}	
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
    		finally {
    			if (con!=null) {
    				try {
    					con.close();
    				}
    				catch (Exception ignore) {
    					//System.out.println(ignore);
    				}
    			}
    		}
    	}	
    	return v;	
    }

    public static String getAlphabetUntukNorut(int norut) {
    	//A = 1
    	String huruf = null;
    	switch (norut) {
        	case 1:  huruf = "A";
                 break;
        	case 2:  huruf = "B";
            	break ; 
        	case 3:  huruf = "C";
        		break;
        	case 4:  huruf = "D";
        		break;
        	case 5:  huruf = "E";
        		break;
        	case 6:  huruf = "F";
        		break;
        	case 7:  huruf = "G";
        		break;
        	case 8:  huruf = "H";
        		break;
        	case 9:  huruf = "I";
        		break;
        	case 10:  huruf = "J";
        		break;
        	case 11:  huruf = "K";
        		break;
        	case 12:  huruf = "L";
        		break;
        	case 13:  huruf = "M";
        		break;
        	case 14:  huruf = "N";
        		break;
        	case 15:  huruf = "O";
        		break;
        	case 16:  huruf = "P";
        		break;
        	case 17:  huruf = "Q";
        		break;
        	case 18:  huruf = "R";
        		break;
        	case 19:  huruf = "S";
        		break;
        	case 20:  huruf = "T";
        		break;
        	case 21:  huruf = "U";
        		break;
        	case 22:  huruf = "V";
        		break;
        	case 23:  huruf = "W";
        		break;
        	case 24:  huruf = "X";
        		break;
        	case 25:  huruf = "Y";
        		break;
        	case 26:  huruf = "Z";
        		break;
        	default:  huruf = "null";
        		break;
    	}	
    	return huruf;
    }
    
    public static String getDateFromTimestamp(String timestamp_value) {
    	String tgl_section = null;
    	if(timestamp_value!=null && !Checker.isStringNullOrEmpty(timestamp_value)) {
    		StringTokenizer st = new StringTokenizer(timestamp_value);
    		tgl_section = st.nextToken();
    	}
    	if(tgl_section!=null && !Checker.isStringNullOrEmpty(tgl_section)) {
    		return formatDdSlashMmSlashYy(tgl_section);
    	}
    	else {
    		return null;
    	}
    	
    }
}
