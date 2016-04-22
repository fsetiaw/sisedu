package beans.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ListIterator;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import beans.setting.Constants;
//import servlets.Get.PengajuanAu.$missing$;

/**
 * Session Bean implementation class Getter
 */
@Stateless
@LocalBean
public class Getter {

    /**
     * Default constructor. 
     */
    public Getter() {
        // TODO Auto-generated constructor stub
    }

    public static String getListPersonalFolder() {
    	String list_folder = "";;     
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
    		stmt = con.prepareStatement("SELECT VALUE FROM CONSTANT where KETERANGAN=?");
    		stmt.setString(1,"FOLDER_FILE_MHS");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			list_folder = rs.getString("VALUE");
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
    	return list_folder;
    }
    
    
    public static String getDefaultNpmNamaBerdasarkanJabatan(String thsms, String kdpst, String jabatan) {
    	/*
    	 * DEAFAULT = 1 orang outputnya
    	 */
    	String npm_nmm_sinkatan = null;;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from JABATAN where NAMA_JABATAN=?");
    		stmt.setString(1, jabatan);
    		rs = stmt.executeQuery();
    		rs.next();
    		String singkatan = rs.getString("SINGKATAN");
    		//cretae NPM auto increment
    		//stmt = con.prepareStatement("SELECT JABATAN.SINGKATAN,STRUKTUR_ORG.NPM,CIVITAS.NMMHSMSMHS from CIVITAS inner join STRUKTUR_ORG on NPM=NPMHSMSMHS where THSMS=? and KDPST=? and NAMA_JABATAN=? and DEFAULT=?");
    		stmt = con.prepareStatement("SELECT NPM,NMMHSMSMHS from STRUKTUR_ORG inner join CIVITAS on NPM=NPMHSMSMHS where THSMS=? and KDPST=? and NAMA_JABATAN=? and DEFAULT_VALUE=?");
    		stmt.setString(1,thsms);
    		stmt.setString(2,kdpst);
    		stmt.setString(3,jabatan);
    		stmt.setBoolean(4,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			npm_nmm_sinkatan = new String(rs.getString("NPM")+"`"+rs.getString("NMMHSMSMHS")+"`"+singkatan);
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
    	return npm_nmm_sinkatan;
    }
    
    public static String getMasterTampletAbsenPath() {
    	String path = "";;     
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
    		stmt = con.prepareStatement("SELECT VALUE FROM CONSTANT where KETERANGAN=?");
    		stmt.setString(1,"FOLDER_MASTER_ABSEN");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			path = rs.getString("VALUE");
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
    	return path;
    }
    
    public static String getListHiddenFolder() {
    	String list_folder = "";;     
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
    		stmt = con.prepareStatement("SELECT VALUE FROM CONSTANT where KETERANGAN=?");
    		stmt.setString(1,"HIDDEN_FOLDER");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			list_folder = rs.getString("VALUE");
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
    	return list_folder;
    }
    
    public static String getKodeKonversiShift(String shift_keter_value) {
    	String kode="N/A";     
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
    		stmt = con.prepareStatement("SELECT KODE_KONVERSI FROM SHIFT where KETERANGAN=?");
    		stmt.setString(1,shift_keter_value);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			kode = rs.getString("KODE_KONVERSI");
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
    	return kode;
    }
    
    public static String[] getVisiMisiTujuan(String kdpst) {
    	String []info=new String[3];     
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
    		stmt = con.prepareStatement("SELECT VISI,MISI,TUJUAN from VISI_MISI where KDPST=?");
    		stmt.setString(1,kdpst);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			info[0] = ""+rs.getString("VISI");
    			info[1] = ""+rs.getString("MISI");
    			info[2] = ""+rs.getString("TUJUAN");
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
    
    
    public static String getSmawlCivitas(String npmhs) {
    	String smawl = new String();     
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
    		stmt = con.prepareStatement("SELECT SMAWLMSMHS from CIVITAS where NPMHSMSMHS=?");
    		stmt.setString(1,npmhs);
    		rs = stmt.executeQuery();
    		rs.next();
    		smawl = rs.getString(1);
    		
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
    	return smawl;
    }
    
    
    public static String[] getTipeKartuUjuanFromConstant() {
    	String []tipe=null;     
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
    		stmt = con.prepareStatement("SELECT * from CONSTANT where KETERANGAN=?");
    		stmt.setString(1,"TIPE_KARTU_UJIAN");
    		rs = stmt.executeQuery();
    		rs.next();
    		String token_tipe = rs.getString("VALUE");
    		StringTokenizer st = new StringTokenizer(token_tipe);
    		int size = st.countTokens();
    		tipe = new String[size];
    		
    		for(int i=0;i<size;i++) {
    			tipe[i]=st.nextToken();
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
    	return tipe;
    }
    

    
    public static String currentStatusAkhirPengajuanPadaTabelPengajuanUa(Vector vRiwayatPengajuan, String rule_tkn_approvee_id, String tkn_approvee_nickname) {
    	String status_akhir = "PROSES PENGAJUAN"; //defaut value = dengan value saat pengajuan pertama kali dibuat
    	String tkn_who_missing = getSiapaYgBlumNgasihTindakanPengajuanUa(vRiwayatPengajuan, rule_tkn_approvee_id, tkn_approvee_nickname);
    	String approval_status = getStatusPengajuanUa(vRiwayatPengajuan, rule_tkn_approvee_id, tkn_approvee_nickname);
    	//System.out.println("approval_status="+approval_status);
    	StringTokenizer st1 = new StringTokenizer(tkn_who_missing,"-");
		StringTokenizer st2 = new StringTokenizer(rule_tkn_approvee_id,"-");
		if(st1.countTokens()==st2.countTokens()) {
			//belum ada yg ngaish tindakan
			//nothing change status tetap PROSES PENGAJUAN
		}
		else if(st1.countTokens()<1) {
			//semua sudh ngasih tindakan
			if(approval_status.equalsIgnoreCase("tolak")) {
				status_akhir = "Ditolak";
			}
			else if(approval_status.equalsIgnoreCase("terima")) {
				status_akhir = "Diterima";
			}
			else if(approval_status.equalsIgnoreCase("proses")) {
				status_akhir = "PROSES PENILAIAN";
			}
		}
		else if(st1.countTokens()>0 && (st1.countTokens()<st2.countTokens())) {
			//baru sebagian
			if(approval_status.equalsIgnoreCase("tolak")) {
				status_akhir = "Ditolak";
			}
			else if(approval_status.equalsIgnoreCase("terima")) {
				status_akhir = "PROSES PENILAIAN";
			}
			else if(approval_status.equalsIgnoreCase("proses")) {
				status_akhir = "PROSES PENILAIAN";
			}
			
		}
    	return status_akhir;
    }
    
    
    public static String getSiapaYgBlumNgasihTindakanPengajuanUa(Vector vRiwayatPengajuan, String rule_tkn_approvee_id, String tkn_approvee_nickname) {
    	/* 
    	 * /56/-/62/1/ rule tkn id
    	   /SEKRETARIAT PASCA/-/BIRO KEUANGAN PASCA/ADMIN/ = rule tkn nic
    	 * 
    	 */
    	String who = "";
    	StringTokenizer st = null;
    	ListIterator li = null;
    	StringTokenizer st1 = new StringTokenizer(rule_tkn_approvee_id,"-");
		StringTokenizer st2 = new StringTokenizer(tkn_approvee_nickname,"-");
    	if(vRiwayatPengajuan!=null && vRiwayatPengajuan.size()>0) {
    		try {
    			//StringTokenizer st1 = new StringTokenizer(rule_tkn_approvee_id,"-");
    			//StringTokenizer st2 = new StringTokenizer(tkn_approvee_nickname,"-");
				while(st1.hasMoreTokens()) {
					String tkn_id = st1.nextToken();
					String tkn_nick = st2.nextToken();
					boolean match = false;
					li = vRiwayatPengajuan.listIterator();
	    			while(li.hasNext() && !match) {
	    				String brs = (String)li.next();
	    				//lif.add(id_ri+"`"+id+"`"+npm_approvee+"`"+status+"`"+updtm+"`"+komen+"`"+approvee_id+"`"+approvee_nickname);
	    				st = new StringTokenizer(brs,"`");
	    				String id_ri = st.nextToken();
	    				String id = st.nextToken();
	    				String npm_approvee = st.nextToken();
	    				String status = st.nextToken();
	    				String updtm = st.nextToken();
	    				String komen = st.nextToken();
	    				String approvee_id = st.nextToken();
	    				String approvee_nickname = st.nextToken();
	    				////System.out.println("tkn_id=="+tkn_id);
	    				////System.out.println("approvee_id=="+approvee_id);
	    				if(tkn_id.contains("/"+approvee_id+"/")) {
						//sudah ada tindakan
	    					match = true;
	    				}
					}
	    			if(!match) {
	    				who = who + tkn_nick +"-";
	    			}
				}	

        	}
            catch (Exception ex) {
            	ex.printStackTrace();
            }	
    	}
    	else {
    		//belum ada yg ngasih tindahan
    		while(st2.hasMoreTokens()) {
    			who = who + st2.nextToken() +"-";
    		}
    	}
    	 
        
    	return who;
    }
    
    public static String getStatusPengajuanUa(Vector vRiwayatPengajuan, String rule_tkn_approvee_id, String rule_tkn_approvee_nickname) {
    	/* 
    	 * /56/-/62/1/ rule tkn id
    	   /SEKRETARIAT PASCA/-/BIRO KEUANGAN PASCA/ADMIN/ = rule tkn nic
    	 * 
    	 */
    	String tkn_latest_verdict = "";
    	boolean all_yes = false;
    	String approved = "proses";
    	String who = "";
    	StringTokenizer st = null;
    	ListIterator li = null;
    	if(vRiwayatPengajuan!=null && vRiwayatPengajuan.size()>0) {
    		try {
    			StringTokenizer st1 = new StringTokenizer(rule_tkn_approvee_id,"-");
    			StringTokenizer st2 = new StringTokenizer(rule_tkn_approvee_nickname,"-");
				while(st1.hasMoreTokens()) {
					String tkn_id = st1.nextToken();
					String tkn_nick = st2.nextToken();
					boolean match = false;
					li = vRiwayatPengajuan.listIterator();
	    			while(li.hasNext() && !match) {
	    				String brs = (String)li.next();
	    				//lif.add(id_ri+"`"+id+"`"+npm_approvee+"`"+status+"`"+updtm+"`"+komen+"`"+approvee_id+"`"+approvee_nickname);
	    				st = new StringTokenizer(brs,"`");
	    				String id_ri = st.nextToken();
	    				String id = st.nextToken();
	    				String npm_approvee = st.nextToken();
	    				String status = st.nextToken();
	    				if(status.equalsIgnoreCase("tolak")) {
	    					all_yes = false;
	    				}
	    				String updtm = st.nextToken();
	    				String komen = st.nextToken();
	    				String approvee_id = st.nextToken();
	    				String approvee_nickname = st.nextToken();
	    				
	    				////System.out.println("tkn_id=="+tkn_id);
	    				////System.out.println("approvee_id=="+approvee_id);
	    				if(tkn_id.contains("/"+approvee_id+"/")) {
						//sudah ada tindakan
	    					match = true;
	    				}
					}
	    			if(!match) {
	    				who = who + tkn_nick +"-";
	    			}
				}	
				//System.out.println("woh="+who);
				if(Checker.isStringNullOrEmpty(who)) { //artinya semua approved
					if(all_yes) {
						approved = "terima";
					}
					else {
						//berarti ada yg nolak
						
						String tkn_verdict = ""; 
						boolean first = true;
						li = vRiwayatPengajuan.listIterator();
			    		while(li.hasNext()) {
			    			String brs = (String)li.next();
			    			//System.out.println("brss="+brs);
			    			//lif.add(id_ri+"`"+id+"`"+npm_approvee+"`"+status+"`"+updtm+"`"+komen+"`"+approvee_id+"`"+approvee_nickname);
			    			st = new StringTokenizer(brs,"`");
			    			String id_ri = st.nextToken();
			    			//System.out.println("id_ri="+id_ri);
			    			String id = st.nextToken();
			    			String npm_approvee = st.nextToken();
			    			String status = st.nextToken();
			    			String updtm = st.nextToken();
			    			String komen = st.nextToken();
			    			String approvee_id = st.nextToken();
			    			String approvee_nickname = st.nextToken();
			    			if(first) {
			    				first = false;
			    				tkn_verdict = "/"+approvee_id+"/"+status;
			    			}
			    			else {
			    				if(tkn_verdict.contains("/"+approvee_id+"/")) {
			    					StringTokenizer st3 = new StringTokenizer(tkn_verdict);
			    					while(st3.hasMoreTokens()) {
			    						String tkn = st3.nextToken();
			    						if(tkn.contains("/"+approvee_id+"/")) {
			    							tkn_verdict = tkn_verdict.replace(tkn,"/"+approvee_id+"/"+status);
			    						}
			    					}
			    				}
			    				else {
			    					tkn_verdict = tkn_verdict+" /"+approvee_id+"/"+status;
			    				}
			    			}
						}
			    		if(tkn_verdict.contains("TOLAK") || tkn_verdict.contains("tolak")) {
			    			approved = "tolak";
			    		}
			    		else {
			    			approved = "terima";
			    		}
					}
				}
				else {
				//belum semua approve
				//namun fungsi ini blum membedakan anatara belum sama ada sama sekali atau sdh ada yg ngasih tindakan.	
					String tkn_verdict = ""; 
					boolean first = true;
					li = vRiwayatPengajuan.listIterator();
		    		while(li.hasNext()) {
		    			String brs = (String)li.next();
		    			//System.out.println("brss_="+brs);
		    			//lif.add(id_ri+"`"+id+"`"+npm_approvee+"`"+status+"`"+updtm+"`"+komen+"`"+approvee_id+"`"+approvee_nickname);
		    			st = new StringTokenizer(brs,"`");
		    			String id_ri = st.nextToken();
		    			//System.out.println("id_ri_="+id_ri);
		    			String id = st.nextToken();
		    			String npm_approvee = st.nextToken();
		    			String status = st.nextToken();
		    			String updtm = st.nextToken();
		    			String komen = st.nextToken();
		    			String approvee_id = st.nextToken();
		    			String approvee_nickname = st.nextToken();
		    			if(first) {
		    				//System.out.println("firts = "+status);
		    				first = false;
		    				tkn_verdict = "/"+approvee_id+"/"+status;
		    			}
		    			else {
		    				//System.out.println("tkn_verdict contains = "+tkn_verdict);
		    				//System.out.println("not firts = "+status);
		    				if(tkn_verdict.contains("/"+approvee_id+"/")) {
		    					StringTokenizer st3 = new StringTokenizer(tkn_verdict);
		    					while(st3.hasMoreTokens()) {
		    						String tkn = st3.nextToken();
		    						//System.out.println("tkn3 contains = "+tkn);
		    						if(tkn.contains("/"+approvee_id+"/")) {
		    							tkn_verdict = tkn_verdict.replace(tkn,"/"+approvee_id+"/"+status);
		    							//System.out.println("tkn_verdict1 contains = "+tkn_verdict);
		    						}
		    					}
		    				}
		    				else {
		    					tkn_verdict = tkn_verdict+" /"+approvee_id+"/"+status;
		    				}
		    			}
					}
		    		//System.out.println("tkn_verdict2 contains = "+tkn_verdict);
		    		if(tkn_verdict.contains("TOLAK") || tkn_verdict.contains("tolak")) {
		    			approved = "tolak";
		    		}
		    		else {
		    			approved = "terima";
		    		}
				}
        	}
            catch (Exception ex) {
            	ex.printStackTrace();
            }	
    	}
    	return approved;
    }
    
    public static String isTindakanPengajuanUaSdhDiwakilkan(Vector vRiwayatPengajuan, String rule_tkn_approvee_id, long oper_obj_id) {
    	/* 
    	 * /56/-/62/1/ rule tkn id
    	   /SEKRETARIAT PASCA/-/BIRO KEUANGAN PASCA/ADMIN/ = rule tkn nic
    	 * 
    	 */
    	String oleh_siapa = "false/null";
    	boolean sdh = false;
    	StringTokenizer st = null;
    	ListIterator li = null;
    	if(vRiwayatPengajuan!=null && vRiwayatPengajuan.size()>0) {
    		try {
    			StringTokenizer st1 = new StringTokenizer(rule_tkn_approvee_id,"-");
    			//StringTokenizer st2 = new StringTokenizer(tkn_approvee_nickname,"-");
				while(st1.hasMoreTokens()) {
					String tkn_id = st1.nextToken();
					//String tkn_nick = st2.nextToken();
					////System.out.println("tkn_id11="+tkn_id);
					boolean match = false;
					li = vRiwayatPengajuan.listIterator();
	    			while(li.hasNext() && !match) {
	    				String brs = (String)li.next();
	    				//lif.add(id_ri+"`"+id+"`"+npm_approvee+"`"+status+"`"+updtm+"`"+komen+"`"+approvee_id+"`"+approvee_nickname);
	    				st = new StringTokenizer(brs,"`");
	    				String id_ri = st.nextToken();
	    				String id = st.nextToken();
	    				String npm_approvee = st.nextToken();
	    				String status = st.nextToken();
	    				String updtm = st.nextToken();
	    				String komen = st.nextToken();
	    				String approvee_id = st.nextToken();
	    				String approvee_nickname = st.nextToken();
	    				////System.out.println("tkn_id=="+tkn_id);
	    				////System.out.println("approvee_id=="+approvee_id);
	    				if(tkn_id.contains("/"+approvee_id+"/") && tkn_id.contains("/"+oper_obj_id+"/") && !approvee_id.equalsIgnoreCase(""+oper_obj_id)) {
						//sudah diwakilkan
	    					sdh = true;
	    					match = true;
	    					oleh_siapa = true+"/"+approvee_nickname;
	    				}
					}
	    			//if(!match) {
	    			//	who = who + tkn_nick +"-";
	    			//}
				}	

        	}
            catch (Exception ex) {
            	ex.printStackTrace();
            }	
    	}
    	 
        
    	return oleh_siapa;
    }    
    
    public static String getKdjenKurikulum(String idkur) {
    	String kdpst="N/A"; 
    	String kdjen = null;
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
    		stmt = con.prepareStatement("SELECT KDPSTKRKLM FROM KRKLM where IDKURKRKLM=?");
    		stmt.setInt(1,Integer.parseInt(idkur));
    		rs = stmt.executeQuery();
    		rs.next();
    		kdpst = rs.getString(1);
    		
    		stmt = con.prepareStatement("select KDJENMSPST from MSPST where KDPSTMSPST=?");
    		stmt.setString(1, kdpst);
    		rs = stmt.executeQuery();
    		rs.next();
    		kdjen = new String(rs.getString(1));
    		
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
    	//System.out.println("kdjen kur = "+kdjen);
    	return kdjen;
    }
    
    
    public static Vector getKurikulumAktif(String kdpst) {
    	//String kdpst="N/A";
    	String thsms_now = Checker.getThsmsNow();
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
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
    		stmt = con.prepareStatement("select * from KRKLM where KDPSTKRKLM=? and (STKURKRKLM='A' or (STARTTHSMS<=? and (ENDEDTHSMS>=? or ENDEDTHSMS is null)))");
    		stmt.setString(1, kdpst);
    		stmt.setString(2, thsms_now);
    		stmt.setString(3, thsms_now);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String idkur = ""+rs.getInt("IDKURKRKLM");
        		String nmkur = ""+rs.getString("NMKURKRKLM");
        		String start = ""+rs.getString("STARTTHSMS");
        		String end = ""+rs.getString("ENDEDTHSMS");
        		String stkur  = ""+rs.getString("STKURKRKLM");
        		li.add(idkur+"`"+nmkur+"`"+stkur+"`"+start+"`"+end);
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
    	////System.out.println("kdjen kur = "+kdjen);
    	return v;
    }
    
    public static Vector getListJenisMakulUjian(String kdpst) {
    	Vector v = getKurikulumAktif(kdpst);
    	Vector vUa = new Vector();
    	ListIterator li,li1 = null;
    	String list = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	if(v!=null && v.size()>0) {
    		//System.out.println("vKurAktif size = = "+v.size());
    		li = v.listIterator();
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("SELECT * from JENIS_MAKUL where KETERANGAN like ? or KETERANGAN like ?");
        		stmt.setString(1, "%UJIAN%");
        		stmt.setString(2, "%ujian%");
        		rs = stmt.executeQuery();
        		rs.next();
        		String kode_makul_ujian = ""+rs.getString("KODE_JENIS");
        		//System.out.println("kode_makul_ujian="+kode_makul_ujian);
        		stmt = con.prepareStatement("SELECT * from MAKUR inner join MAKUL on IDKMKMAKUR=IDKMKMAKUL where IDKURMAKUR=? and JENISMAKUL=?");
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			//System.out.println("brs="+brs);
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String idkur = st.nextToken();
        			String nmkur = st.nextToken();
        			String stkur = st.nextToken();
        			String start = st.nextToken();
        			String end = st.nextToken();
        			stmt.setInt(1,Integer.parseInt(idkur));
        			stmt.setString(2, kode_makul_ujian);
        			rs =stmt.executeQuery();
        			if(rs.next()) {
        				li1 = vUa.listIterator();
        				do {
        					String idkmk = ""+rs.getInt("IDKMKMAKUL");
        					String kdkmk = rs.getString("KDKMKMAKUL");
        					String nakmk = rs.getString("NAKMKMAKUL");
        					String jenisMakul = rs.getString("JENISMAKUL");
        					li1.add(idkmk+"`"+kdkmk+"`"+nakmk);
        				} 
        				while(rs.next());
        			}
        		}
        		vUa = Tool.removeDuplicateFromVector(vUa);

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
    		
    	return vUa;
    }
    
    
    public static String getListStmhs() {
    	String tkn_stmhs_kode = "";
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT VALUE from CONSTANT where KETERANGAN='STMHS'");
    		rs = stmt.executeQuery();
    		rs.next();
    		tkn_stmhs_kode = ""+rs.getString(1);
    		

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
    	
    		
    	return tkn_stmhs_kode;
    }
    
    
    
    public static String getListKategoriBahanAjar(String kdpst) {
    	//String kdpst="N/A"; 
    	//String kdjen = getKdjenKurikulum(idkur);
    	String tkn_tipe = null;
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
    		stmt = con.prepareStatement("SELECT distinct TIPE FROM KATEGORI_BAHAN_AJAR where KDPST=? and AKTIF=?");
    		stmt.setString(1,kdpst);
    		stmt.setBoolean(2,true);
    		rs = stmt.executeQuery();
    		
    		if(rs.next()) {
    			tkn_tipe = new String();
    			do {
    				tkn_tipe = tkn_tipe+rs.getString("TIPE")+"`";
    			}while(rs.next());
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
    	//System.out.println("tkn_tipe = "+tkn_tipe);
    	return tkn_tipe;
    }
    
    
    public static Vector getListJenisMakul() {
    	//String kdpst="N/A"; 
    	//String kdjen = getKdjenKurikulum(idkur);
    	String tkn_tipe = null;
    	Vector v = new Vector();
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
    		stmt = con.prepareStatement("SELECT ID,KETERANGAN,KODE_JENIS from JENIS_MAKUL order by KODE_JENIS");
    		
    		rs = stmt.executeQuery();
    		
    		if(rs.next()) {
    			ListIterator li = v.listIterator();
    			do {
    				String id = ""+rs.getInt("ID");
					String keter = ""+rs.getString("KETERANGAN");
					String kode = ""+rs.getString("KODE_JENIS");
					li.add(id+"`"+keter+"`"+kode);
    			}while(rs.next());
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
    
    
    
    public static String getDomisiliKampus(String npmhs) {
    	String kodeKampus="N/A";     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		stmt = con.prepareStatement("SELECT KODE_KAMPUS_DOMISILI from OBJECT inner join CIVITAS on OBJECT.ID_OBJ=CIVITAS.ID_OBJ where NPMHSMSMHS=?");
    		stmt.setString(1,npmhs);
    		rs = stmt.executeQuery();
    		rs.next();
    		kodeKampus = rs.getString(1);
    		
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
    	return kodeKampus;
    }
    
    
    public static String getDomisiliKampus(int id_obj) {
    	String kodeKampus="N/A";     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		stmt = con.prepareStatement("SELECT KODE_KAMPUS_DOMISILI from OBJECT  where ID_OBJ=?");
    		stmt.setInt(1,id_obj);
    		rs = stmt.executeQuery();
    		rs.next();
    		kodeKampus = rs.getString(1);
    		
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
    	return kodeKampus;
    }

    
    public static Vector getListAllKampus() {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		stmt = con.prepareStatement("SELECT * from KAMPUS");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String code_campus = ""+rs.getString("KODE_KAMPUS");
    			String name_campus = ""+rs.getString("NAMA_KAMPUS");
    			String nickname_campus = ""+rs.getString("NICKNAME_KAMPUS");
    			li.add(code_campus+"`"+name_campus+"`"+nickname_campus);
    			
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

    public static Vector returnListProdiOnlySortByKampusWithListIdobj() {
    	Vector v_prodi = getListProdi();//li.add(kdpst+"`"+kdfak+"`"+kdjen+"`"+nmpst);
    	Vector v_kmp = getListAllKampus(); //li.add(code_campus+"`"+name_campus+"`"+nickname_campus);
    	Vector vf = new Vector();
    	ListIterator lif = vf.listIterator();
    	String tkn_kdjen = null;
    	ListIterator li = null, li1 = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		String cmd_list = "";
    		li = v_prodi.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String kdpst = st.nextToken();
    			cmd_list = cmd_list+"KDPST='"+kdpst+"'";
    			if(li.hasNext()) {
    				cmd_list=cmd_list+" or ";
    			}
    		}
    		stmt = con.prepareStatement("SELECT ID_OBJ from OBJECT where ("+cmd_list+") and KODE_KAMPUS_DOMISILI=? order by KDPST");
    		li1 = v_kmp.listIterator();
    		while(li1.hasNext()) {
    			String brs = (String)li1.next();
    			StringTokenizer st = new StringTokenizer(brs,"`");
    			String kmp = st.nextToken();
    			stmt.setString(1, kmp);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				String list_idobj = "";
    				do {
    					list_idobj=list_idobj+"`"+rs.getLong(1);
    				}
    				while(rs.next());
    				lif.add(kmp+list_idobj);
    			}
    			else {
    				lif.add(kmp);
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
    	return vf;
    }
    
    public static String  getListKdjenProdi() {
    	String tkn_kdjen = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		stmt = con.prepareStatement("SELECT distinct KDJENMSPST from MSPST where KDJENMSPST<=? && KDJENMSPST<>? order by KDJENMSPST");
    		stmt.setString(1, "G");
    		stmt.setString(2, "0");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			tkn_kdjen = new String();
    			do {
    				tkn_kdjen = tkn_kdjen+rs.getString(1)+"`";	
    			} 
    			while(rs.next());	
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
    	return tkn_kdjen;
    }

    public static String getListProdiDalam1Fakultas(String kdpst) {
    	String tkn_prodi="N/A";   
    	String info_fak = Checker.getFakInfo(kdpst);
    	StringTokenizer st = new StringTokenizer(info_fak);
    	String kdfak = st.nextToken();
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		stmt = con.prepareStatement("SELECT KDPSTMSPST from MSPST where KDFAKMSPST=?");
    		stmt.setString(1,kdfak);
    		rs = stmt.executeQuery();
    		if(!rs.next()) {
    			tkn_prodi = "null";	
    		}
    		else {
    			tkn_prodi = "";
    			tkn_prodi = tkn_prodi+rs.getString("KDPSTMSPST")+",";
    			while(rs.next()) {
        			tkn_prodi = tkn_prodi+rs.getString("KDPSTMSPST")+",";
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
    	return tkn_prodi;
    }
    
    public static String getListProdiDariLainFakultas(String tkn_fak) {
    	String tkn_prodi="N/A";   
    	if(tkn_fak!=null && new StringTokenizer(tkn_fak).countTokens()>0) {
    		StringTokenizer st = new StringTokenizer(tkn_fak);
        	
        	Connection con=null;
        	PreparedStatement stmt=null;
        	ResultSet rs=null;
        	DataSource ds=null;
        	try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		tkn_prodi = null;	
        		stmt = con.prepareStatement("SELECT KDPSTMSPST from MSPST where KDFAKMSPST=?");
        		while(st.hasMoreTokens()) {
        			String kdfak = st.nextToken();
        			stmt.setString(1,kdfak);
            		rs = stmt.executeQuery();
            		boolean first = true;
            		while(rs.next()) {
            			if(first) {
            				first = false;
            				if(tkn_prodi==null) {
            					tkn_prodi = "";
            				}	
            			}
            			else {
            				tkn_prodi = tkn_prodi+rs.getString("KDPSTMSPST")+",";
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
    		
    	return tkn_prodi;
    }
    
    public static Vector addKodeKampusToVscope(Vector vScope) {
    	     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	if(vScope!=null && vScope.size()>0) {
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//cretae NPM auto increment
        		stmt = con.prepareStatement("SELECT KODE_KAMPUS_DOMISILI FROM OBJECT where ID_OBJ=?");
        		ListIterator li = vScope.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs);
    				String idObj = st.nextToken();
    				String kdpst = st.nextToken();
    				String nmfak = st.nextToken().replace("MHS_", "");
    				String obLvl = st.nextToken();
    				String kdjen = st.nextToken();
    				stmt.setInt(1, Integer.parseInt(idObj));
    				rs = stmt.executeQuery();
    				rs.next();
    				li.set(brs+" "+rs.getString("KODE_KAMPUS_DOMISILI"));
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
    		
    	return vScope;
    }
    
    public static Vector getListDosen() {
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * FROM MSDOS order by NMDOS");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String nmdos  = rs.getString("NMDOS");
    			String nodos  = rs.getString("nodos");
    			li.add(nodos+"__"+nmdos);
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

    
    public static Vector getListProdi() {
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * FROM MSPST where KDJENMSPST=? or KDJENMSPST=? or KDJENMSPST=? or KDJENMSPST=? or KDJENMSPST=? or KDJENMSPST=? or KDJENMSPST=? order by NMPSTMSPST");
    		int i=1;
    		stmt.setString(i++, "A");
    		stmt.setString(i++, "B");
    		stmt.setString(i++, "C");
    		stmt.setString(i++, "D");
    		stmt.setString(i++, "E");
    		stmt.setString(i++, "F");
    		stmt.setString(i++, "G");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String kdpst  = rs.getString("KDPSTMSPST");
    			String kdfak  = rs.getString("KDFAKMSPST");
    			String kdjen  = rs.getString("KDJENMSPST");
    			String nmpst  = rs.getString("NMPSTMSPST");
    			li.add(kdpst+"`"+kdfak+"`"+kdjen+"`"+nmpst);
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
    
    
	public static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }

	  public static JSONObject readJsonObjFromUrl(String url) throws IOException, JSONException {
		//url = url.replace("&#x2f;", "/");  
		url = url.replace(" ", "%20");
		url = Constants.getAlamatIp()+url;
	    InputStream is = new URL(url).openStream();
	    String jsonText = null;
	    JSONObject json = null;
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      jsonText = readAll(rd);
	      json = new JSONObject(jsonText);
	    } 
	    catch(JSONException je) {
	    	je.printStackTrace();
	    }
	    finally {
	      is.close();
	    }
	    return json;
	  }
	  
	  
	  
	  public static JSONArray readJsonArrayFromUrl(String url) throws IOException{
		  	//url = url.replace("&#x2f;", "/");  url mulai dari /v1
		  	url = url.replace(" ", "%20");
		  	url = url.replace("|", "%7C");
		  	url = Constants.getAlamatIp()+url;
		  	////System.out.println("url="+url);
		    InputStream is = new URL(url).openStream();
		    JSONArray jsoa = null;
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      String jsonText = readAll(rd);
		      jsoa = new JSONArray(jsonText);
		      
		    } 
		    catch(JSONException je) {
		    	je.printStackTrace();
		    }
		    finally {
		      is.close();
		    }
		    return jsoa;
	  }
	  
	  public static String readStringFromUrl(String url) throws IOException {
		  	//url = url.replace("&#x2f;", "/");  
		  	url = url.replace(" ", "%20");
		  	url = Constants.getAlamatIp()+url;
		    InputStream is = new URL(url).openStream();
		    String jsonText = null;
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      jsonText = readAll(rd);
		    } 
		    finally {
		      is.close();
		    }
		    return jsonText;
	  }
	  
	  public static String getListPembimbingAkademik(String tkn_kdpst) {
	    	//String kdpst="N/A"; 
	    	//String kdjen = getKdjenKurikulum(idkur);
	    	String tkn_npmPa = null;
	    	Connection con=null;
	    	PreparedStatement stmt=null;
	    	ResultSet rs=null;
	    	DataSource ds=null;
	    	if(tkn_kdpst!=null && !Checker.isStringNullOrEmpty(tkn_kdpst)) {
	    		try {
		    		Context initContext  = new InitialContext();
		    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
		    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
		    		con = ds.getConnection();
		    		//cretae NPM auto increment
		    		
		    		stmt = con.prepareStatement("SELECT distinct NPM_PA FROM EXT_CIVITAS WHERE KDPSTMSMHS=?");
		    		StringTokenizer st = new StringTokenizer(tkn_kdpst,"`");
		    		if(st.hasMoreTokens()) {
		    			tkn_npmPa = new String();
		    			while(st.hasMoreTokens()) {
		    				String kdpst = st.nextToken();
			    			stmt.setString(1,kdpst);
				    		rs = stmt.executeQuery();
				    		
				    		if(rs.next()) {		
				    			do {
				    				String npm_pa = rs.getString(1);
				    				////System.out.println("npm_pa="+npm_pa);
				    				if(npm_pa!=null && !tkn_npmPa.contains(npm_pa)) {
				    					tkn_npmPa = tkn_npmPa+npm_pa+"`";	
				    				}
				    			}while(rs.next());
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
	    		
	    	////System.out.println("tkn_tipe = "+tkn_tipe);
	    	return tkn_npmPa;
	    }
    
	  
	  public static int getObjLvl(String target_npm) {
	    	//String kdpst="N/A"; 
	    	//String kdjen = getKdjenKurikulum(idkur);
	    int obj_lvl = -1;
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
		    		
	    	stmt = con.prepareStatement("SELECT OBJ_LEVEL FROM CIVITAS INNER JOIN OBJECT ON CIVITAS.ID_OBJ=OBJECT.ID_OBJ WHERE NPMHSMSMHS=?");
		   	stmt.setString(1, target_npm);
		   	rs = stmt.executeQuery();
		   	rs.next();
		   	obj_lvl	= rs.getInt(1);	
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
	  
	    		
	    	////System.out.println("tkn_tipe = "+tkn_tipe);
	   	return obj_lvl;
	}
}
