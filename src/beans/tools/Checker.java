package beans.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.tomcat.jdbc.pool.DataSource;
import beans.setting.Constants;
import beans.sistem.AskSystem;
import beans.tools.Checker;
import java.util.Vector;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.StringTokenizer;
import java.math.BigDecimal;
import javax.servlet.http.HttpSession;
/**
 * Session Bean implementation class Checker
 */
@Stateless
@LocalBean
public class Checker {

    /**
     * Default constructor. 
     */
    public Checker() {
        // TODO Auto-generated constructor stub
    }
/*   
    public boolean doIHaveAccesToThisEntity(Vector vScope,String targetObjectLevel,String iNpm,String targetNpm) {
    	boolean iDo = false;
    	ListIterator liScope = vScope.listIterator();
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	try {
    		if(vScope.size()==1) {
    			//cek apa only own = artinya boleh ngecek hanya punyaynya sendiri
    			String lvlScope = (String) liScope.next();
    			StringTokenizer st = new StringTokenizer(lvlScope);
    			if(st.countTokens()==1) {
    				String tkn = st.nextToken();
    				if(tkn.equalsIgnoreCase("own")) {
    					if(iNpm.equalsIgnoreCase(targetNpm)) {
    						iDo = true;
    					}
    					else {
    						iDo = false;
    					}
    				}
    				else {
    					
    				}
    			}
    			else {
    				//kalo bukan own  berarti mus be objek lvl
    				//if()
    			}
    		}
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
*/   
    /*
     * ada di GETTER
    
    public static String getObjKampusHomeBase(int id_obj) {
    	
    	String url=null, kode_kmp=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Long uid = null;
    	
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			
			stmt = con.prepareStatement("select KODE_KAMPUS_DOMISILI from OBJECT where ID_OBJ=?");
			stmt.setInt(1,id_obj);
			rs = stmt.executeQuery();
			kode_kmp = new String(""+rs.getString(1));
			
			
			
    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return kode_kmp;
    }	
     */
    
    public static Long getClassUID(String kdkmk, String thsms, String npmMhs) {
    	
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	Long uid = null;
    	
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			
			stmt = con.prepareStatement("select CLASS_POOL_UNIQUE_ID from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=? and KDKMKTRNLM=?");
			stmt.setString(1,thsms);
			stmt.setString(2,npmMhs);
			stmt.setString(3,kdkmk);
			rs = stmt.executeQuery();
			if(rs.next()) {
				uid  = rs.getLong(1);
			}
			
			
    	}
    	catch(ConcurrentModificationException e) {
    		e.printStackTrace();
    	}
		catch (NamingException e) {
			e.printStackTrace();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		} 
    	catch(Exception e) {
    		e.printStackTrace();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
			if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
			if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	return uid;
    }	
    
    public static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
    
    
    public static boolean isStringNullOrEmpty(String word) {
    	boolean empty = true;
    	if(word!=null) {
    		StringTokenizer st = new StringTokenizer(word);
    		if(st.countTokens()>0) {
    			String tkn = st.nextToken();
    			if(!tkn.equalsIgnoreCase("null") && !tkn.equalsIgnoreCase("NULL")) {
    				empty = false;
    			}
    		}
    	}
    	//System.out.println("checker word="+word+" "+empty);
    	return empty;
    }

    public static boolean bilanganGanjilKah(String value) {
    	boolean benar = false;
    	BigDecimal bg1, bg2;

        bg1 = new BigDecimal(value);
        bg2 = new BigDecimal("2");

	// BigDecimal array bg stores result of bg1/bg2
        BigDecimal bg[] = bg1.divideAndRemainder(bg2);
        if(!bg[1].equals(BigDecimal.ZERO)) {
        	benar=true;
        }
        return benar;
    }
    
    public static String pnn(String word) {//printnotnull
    	String tmp="";
    	if(word!=null && !Checker.isStringNullOrEmpty(word)) {
    		StringTokenizer st = new StringTokenizer(word);
    		if(st.countTokens()>0) {
    			while(st.hasMoreTokens()) {
    				String tkn = st.nextToken();
    				if(tkn.equalsIgnoreCase("null")) {
    					tmp = tmp+"";
    				}
    				else {
    					tmp=tmp+tkn;
    				}
    				if(st.hasMoreTokens()) {
    					tmp = tmp+" ";
    				}
    			}
    			if(isStringNullOrEmpty(tmp)) {
    				word="";
    			}
    		}
    		else {
    			word="";
    		}
    	}
    	else {
    		word="";
    	}
    	return word;
    }
    
    public static String getThsmsPmb() {
    	String thsms =null;
    	String url=null;     
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
    		stmt = con.prepareStatement("SELECT * FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			thsms = rs.getString("THSMS_PMB");
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
    	return thsms;	
    }
    
    public static String getRootPathDir(String name_code) {
    	
    	String path=null;     
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
    		stmt = con.prepareStatement("SELECT REAL_ROOT_FOLDER FROM MASTER_ARSIP_TABLE where NAMA_ROOT_FOLDER=?");
    		stmt.setString(1, name_code);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			path = new String(rs.getString(1));
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
    
    
    public static String getBahanAjarTargetPath(String tipe_bahan_ajar, String kdpst, String kdkmk, String npm, String idkur) {
    	
    	String path=null;     
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
    		stmt = con.prepareStatement("SELECT PATH from KATEGORI_BAHAN_AJAR where TIPE=? and KDPST=?");
    		stmt.setString(1, tipe_bahan_ajar);
    		stmt.setString(2, kdpst);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			path = new String(rs.getString(1));
    			path = path.replace("kdpst", kdpst);
    			path = path.replace("kdkmk", kdkmk);
    			path = path.replace("idKur", idkur);
    			path = path.replace("npmDosen", npm);
    			
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

public static String getRootFolderIndividualMhs(String npmhs) {
    	
    	String path=null;     
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
    		stmt = con.prepareStatement("SELECT REAL_ROOT_FOLDER FROM MASTER_ARSIP_TABLE where NAMA_ROOT_FOLDER=?");
    		stmt.setString(1, "ARSIP MHS");
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			path = new String(rs.getString(1))+"/"+npmhs;
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
    public static String getNamaPaketBeasiswa(int idpaket) {
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String nmmpaket = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * FROM BEASISWA where IDPAKETBEASISWA=?");
    		stmt.setLong(1,idpaket);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			nmmpaket = rs.getString("NAMAPAKET");
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
    	return nmmpaket;	
    }
   
    public static boolean wajibDaftarUlangUntukIsiKrs() {
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	boolean value = false;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT REGISTRASI_FOR_KRS FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			value = rs.getBoolean(1);
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
    	return value;	
    }

    
    public static String getThsmsKrsWhiteList(String kdpst, String npmhs) {
    	String tkn_thsms =null;
    	String url=null;     
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
    		stmt = con.prepareStatement("SELECT * FROM KRS_WHITE_LIST where KDPST=? and NPMHS=?");
    		stmt.setString(1,kdpst);
    		stmt.setString(2,npmhs);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			tkn_thsms = rs.getString("TOKEN_THSMS");
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
    	return tkn_thsms;	
    }
    
    public static boolean getIsOperatorAllowInsKrs() {
    	boolean allow =false;
    	String url=null;     
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
    		stmt = con.prepareStatement("SELECT OPERATOR_ALLOW_INS_KRS FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			allow = rs.getBoolean("OPERATOR_ALLOW_INS_KRS");
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
    	return allow;	
    }
    
    public static boolean getIsOperatorAllowEditNilai() {
    	boolean allow =false;
    	String url=null;     
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
    		stmt = con.prepareStatement("SELECT OPERATOR_ALLOW_EDIT_NILAI FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			allow = rs.getBoolean("OPERATOR_ALLOW_EDIT_NILAI");
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
    	return allow;	
    }
    
    public static boolean getIsOperatorAllowEditNilaiAllThsms() {
    	boolean allow =false;
    	String url=null;     
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
    		stmt = con.prepareStatement("SELECT ALLOW_EDIT_NILAI_ALL_THSMS FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			allow = rs.getBoolean("ALLOW_EDIT_NILAI_ALL_THSMS");
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
    	return allow;	
    }
    
    public static String getThsmsHeregistrasi() {
    	String thsms =null;
    	String url=null;     
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
    		stmt = con.prepareStatement("SELECT * FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			thsms = rs.getString("THSMS_HEREGISTRASI");
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
    	return thsms;	
    }
    
    public static String getThsmsInputNilai() {
    	String thsms =null;
    	String url=null;     
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
    		stmt = con.prepareStatement("SELECT THSMS_INP_NILAI_MK FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			thsms = rs.getString("THSMS_INP_NILAI_MK");
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
    	return thsms;	
    }
    
    public static String getThsmsKrs() {
    	String thsms =null;
    	String url=null;     
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
    		stmt = con.prepareStatement("SELECT * FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			thsms = rs.getString("THSMS_KRS");
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
    	return thsms;	
    }    
    /*
     * redundan
     
    public static Vector getListAvailShift(String kdjen) {
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
    		stmt = con.prepareStatement("select * from SHIFT where TOKEN_KDJEN_AVAILABILITY like ? and AKTIF=true order by KETERANGAN");
    		stmt.setString(1,"%"+kdjen+"%");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String desc = ""+rs.getString("KETERANGAN");
    			String shift = ""+rs.getString("SHIFT");
    			String hari = ""+rs.getString("HARI");
    			String value = ""+rs.getString("KODE_KONVERSI");
    			li.add(desc+","+shift+","+hari+","+value);
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
    */
    public static boolean isMhsAllowPengajuanKrs() {
    	boolean allow =false;
    	String url=null;     
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
    		stmt = con.prepareStatement("SELECT MHS_ALLOW_PENGAJUAN_KRS,TGL_MULAI_PENGAJUAN_KRS,TGL_AKHIR_PENGAJUAN_KRS FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		java.sql.Date tmp = java.sql.Date.valueOf(AskSystem.getCurrentDate());
    		java.sql.Date sta = null;
    		java.sql.Date end = null;
    		if(rs.next()) {
    			//allow = rs.getBoolean("MHS_ALLOW_PENGAJUAN_KRS");
    			sta = rs.getDate("TGL_MULAI_PENGAJUAN_KRS");
    			end = rs.getDate("TGL_AKHIR_PENGAJUAN_KRS");
    			if(tmp.compareTo(sta)>=0 && tmp.compareTo(end)<=0) {
    				allow = true;
    			}
    			else {
    				allow = false;
    			}
    		}
    		//System.out.println("allow="+allow);
    		//update col mhs_allow = deprecated krn dulu blum pake range tanggal
    		stmt = con.prepareStatement("update CALENDAR set MHS_ALLOW_PENGAJUAN_KRS=? where AKTIF=?");
    		stmt.setBoolean(1, allow);
    		stmt.setBoolean(2, true);
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
    	return allow;	
    }  
    
    /*
     * kedepannya bisa ada
     * getAllowedKdpstPilihKelas, dst sampai kampus
     */
    public static String getAllowedShiftPilihKelas( String target_thsms, String npmhs, String target_kdpst) {
    	boolean allow =false;
    	long idObj = getObjectId(npmhs);
    	String url=null;     
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String keterangan_shift = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * from PILIH_KELAS_RULES where THSMS=? and ID_OBJ_MHS=?");
    		stmt.setString(1,target_thsms);
    		stmt.setLong(2, idObj);
    		rs = stmt.executeQuery();
    		rs.next();
    		//boolean shift_only = rs.getBoolean("SHIFT_ONLY"); // shift only jgn dipake aja
    		boolean all_shift = rs.getBoolean("ALL_SHIFT");
    		boolean all_prodi = rs.getBoolean("ALL_PRODI");
    		boolean all_fak = rs.getBoolean("ALL_FAKULTAS");
    		boolean all_kmp = rs.getBoolean("ALL_KAMPUS");
    		String tkn_prodi = ""+rs.getString("TKN_PRODI");
    		String tkn_fak = ""+rs.getString("TKN_FAKULTAS");
    		String tkn_kmp = ""+rs.getString("TKN_KAMPUS");
    		String tkn_shift = ""+rs.getString("TKN_SHIFT"); //value =  KETERANGAN SHIFT pd table SHIFT
    		String npm_whitelist_shift = ""+rs.getString("NPMHS_WHITELIST_SHIFT");
    		String npm_whitelist_prodi = ""+rs.getString("NPMHS_WHITELIST_PRODI");
    		String npm_whitelist_fak = ""+rs.getString("NPMHS_WHITELIST_FAK");
    		String npm_whitelist_kmp = ""+rs.getString("NPMHS_WHITELIST_KMP");
    		if(all_shift && (npm_whitelist_shift!=null && npm_whitelist_shift.contains(npmhs))) {
    			
    			all_shift = false;
    			
    		}
    		else if(!all_shift && (npm_whitelist_shift!=null && npm_whitelist_shift.contains(npmhs))) {
    			all_shift = true;
    		}
    		
    		if(!all_shift) {
    			//berarti yg boleh shift pada profile mhs
    			keterangan_shift = new String(Checker.getShiftMhs(npmhs));
    		}
    		else {
    			if(tkn_shift==null || Checker.isStringNullOrEmpty(tkn_shift)) {
    				//berarti semua shift yg ada di table SHIFt = sesuai dgn kdjennya
    				keterangan_shift = new String("");
    				String kdjen = Checker.getKdjen(target_kdpst);
    				stmt = con.prepareStatement("select * from SHIFT where TOKEN_KDJEN_AVAILABILITY like ? and AKTIF=?");
    				stmt.setString(1, "%"+kdjen+"%");
    				stmt.setBoolean(2, true);
    				rs = stmt.executeQuery();
    				while(rs.next()) {
    					String keter = rs.getString("KETERANGAN");
    					keterangan_shift = keterangan_shift+keter+"`";
    				}
    			}
    			else {
    				//berarti yang diperbolehkan yg ada pada token shift
    				//value shitf disini = KETERANGN pada table shift
    				keterangan_shift = new String(tkn_shift);
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
    	return keterangan_shift;	
    }  
    
    

    
    public static String getThsmsBukaKelas() {
    	String thsms =null;
    	String url=null;     
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
    		stmt = con.prepareStatement("SELECT * FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			thsms = rs.getString("THSMS_BUKA_KELAS");
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
    	return thsms;	
    }    
    
    public static int getSksmk(String idkmk) {
    	int sksmk = 0;
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
    		stmt = con.prepareStatement("SELECT * FROM MAKUL where IDKMKMAKUL=?");
    		stmt.setLong(1,Long.valueOf(idkmk).longValue());
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			sksmk = rs.getInt("SKSTMMAKUL")+rs.getInt("SKSPRMAKUL")+rs.getInt("SKSLPMAKUL");
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
    	return sksmk;	
    }
    
    
    public static boolean isUsrNameAvailable(String usrnm, String npm) {
    	//cek apa udah ada yg punya, kalaupun ada apa punya sendiri
    	boolean avail = false;
    	String id_usr = null;
    	Connection con = null;
    	DataSource ds = null;
    	PreparedStatement stmt = null;
    	try {

    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		//ds = (DataSource)envContext.lookup("jdbc/USER");
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT * FROM USR_DAT where USR_NAME=?");
    		stmt.setString(1, usrnm);
    		ResultSet rs = stmt.executeQuery();
    		if(rs.next()) {
    			id_usr = ""+rs.getInt("ID");
    		} 	
    		else {
    			avail = true;
    		}
    		if(id_usr!=null && !avail) {
    			String nonpm = "";
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select * from CIVITAS where ID=?");
        		stmt.setInt(1, Integer.valueOf(id_usr).intValue());
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			nonpm = rs.getString("NPMHSMSMHS");
        			if(nonpm.equalsIgnoreCase(npm)) {
        				avail=true;
        			}
        			else {
        				avail=false;
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
    		if (con!=null) {
    			try {
    				con.close();
    			}
    			catch (Exception ignore) {
    				//System.out.println(ignore);
    			}
    		}
    	}
    	return avail;
    }
    
    public static String getThsmsNow() {
    	String thsms =null;
    	String url=null;     
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
    		stmt = con.prepareStatement("SELECT * FROM CALENDAR where AKTIF=?");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			thsms = rs.getString("THSMS");
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
    	return thsms;	
    }

    
    public static String getObjName(int idObj) {
    	//cek apa udah ada yg punya, kalaupun ada apa punya sendiri
    	String objName = null;
    	Connection con = null;
    	DataSource ds = null;
    	PreparedStatement stmt = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		//ds = (DataSource)envContext.lookup("jdbc/USER");
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT OBJ_NAME FROM OBJECT where ID_OBJ=?");
    		stmt.setLong(1, idObj);
    		ResultSet rs = stmt.executeQuery();
    		if(rs.next()) {
    			objName = ""+rs.getString("OBJ_NAME");
    		}
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
    	return objName;
    }
    
    public static String getObjDesc(int idObj) {
    	//cek apa udah ada yg punya, kalaupun ada apa punya sendiri
    	String objName = null;
    	Connection con = null;
    	DataSource ds = null;
    	PreparedStatement stmt = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		//ds = (DataSource)envContext.lookup("jdbc/USER");
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT OBJ_DESC FROM OBJECT where ID_OBJ=?");
    		stmt.setLong(1, idObj);
    		ResultSet rs = stmt.executeQuery();
    		if(rs.next()) {
    			objName = ""+rs.getString(1);
    		}
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
    	return objName;
    }
    /*
     * DEPRECATED - PAKE YG STRING NPMHS
     */
    public static String getObjNickname(int idObj) {
    	//cek apa udah ada yg punya, kalaupun ada apa punya sendiri
    	String objName = null;
    	Connection con = null;
    	DataSource ds = null;
    	PreparedStatement stmt = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		//ds = (DataSource)envContext.lookup("jdbc/USER");
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT OBJ_NICKNAME FROM OBJECT where ID_OBJ=?");
    		stmt.setLong(1, idObj);
    		ResultSet rs = stmt.executeQuery();
    		if(rs.next()) {
    			objName = ""+rs.getString("OBJ_NICKNAME");
    		}
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
    	return objName;
    }
    
    
    public static String adaDiMoodle(String npmhs) {
    	//cek apa udah ada yg punya, kalaupun ada apa punya sendiri
    	String objName = null;
    	String pwd = null;
    	Connection con = null;
    	DataSource ds = null;
    	PreparedStatement stmt = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc/MyMoodle");
    		//ds = (DataSource)envContext.lookup("jdbc/USER");
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT * from mdl_user where username=?");
    		stmt.setString(1, npmhs+"_Usr");
    		ResultSet rs = stmt.executeQuery();
    		if(rs.next()) {
    			pwd = new String(rs.getString("password"));
    		}
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
    	return pwd;
    }
    
    public static String getObjNickname(String npmhs) {
    	//cek apa udah ada yg punya, kalaupun ada apa punya sendiri
    	String objName = null;
    	long idObj = 0;
    	Connection con = null;
    	DataSource ds = null;
    	PreparedStatement stmt = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT OBJECT.ID_OBJ FROM OBJECT inner join CIVITAS on OBJECT.ID_OBJ=CIVITAS.ID_OBJ where NPMHSMSMHS=?");
    		stmt.setString(1,npmhs);
    		ResultSet rs = stmt.executeQuery();
    		if(rs.next()) {
    			idObj = rs.getLong(1);	
    			stmt = con.prepareStatement("SELECT OBJ_NICKNAME FROM OBJECT where ID_OBJ=?");
        		stmt.setLong(1, idObj);
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			objName = ""+rs.getString("OBJ_NICKNAME");
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
    		if (con!=null) {
    			try {
    				con.close();
    			}
    			catch (Exception ignore) {
    				//System.out.println(ignore);
    			}
    		}
    	}
    	return objName;
    }
    
    public static String getCurPa(String npmhs) {
    	//cek apa udah ada yg punya, kalaupun ada apa punya sendiri
    	String infoCurPa = null;
    	Connection con = null;
    	DataSource ds = null;
    	PreparedStatement stmt = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		//ds = (DataSource)envContext.lookup("jdbc/USER");
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from EXT_CIVITAS where NPMHSMSMHS=?");
    		stmt.setString(1, npmhs);
    		ResultSet rs = stmt.executeQuery();
    		if(rs.next()) {
    			infoCurPa = rs.getString("NPM_PA")+","+rs.getString("NMM_PA");
    		}
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
    	return infoCurPa;
    }    
   
    public static String getListShift() {
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String tmp="";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * FROM SHIFT where AKTIF=? order by KETERANGAN");
    		stmt.setBoolean(1,true);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String uniqKeter = rs.getString("KETERANGAN");
    			String shift = rs.getString("SHIFT");
    			String hari = rs.getString("HARI");
    			String tkn_kdjen = rs.getString("TOKEN_KDJEN_AVAILABILITY");
    			String keterKonversi = rs.getString("KODE_KONVERSI");
    			
    			tmp = tmp + uniqKeter+"#"+shift+"#"+hari+"#"+tkn_kdjen+"#"+keterKonversi+"#";
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
    	return tmp;	
    }
    
    
    public static String getShiftMhs(String npmhs) {
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String shift = "";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT SHIFTMSMHS FROM CIVITAS where NPMHSMSMHS=?");
    		stmt.setString(1,npmhs);
    		rs = stmt.executeQuery();
    		rs.next();
    		shift = rs.getString(1);
    		
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
    	return shift;	
    }
    
    public static long getObjectId(String npmhs) {
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	long idObj = -100;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT OBJECT.ID_OBJ FROM OBJECT inner join CIVITAS on OBJECT.ID_OBJ=CIVITAS.ID_OBJ where NPMHSMSMHS=?");
    		stmt.setString(1,npmhs);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			idObj = rs.getLong(1);	
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
    	return idObj;	
    }
    
    public static boolean isNpmInWhitelistShift(long id_obj, String target_npm) {
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	boolean value = false;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT NPMHS_WHITELIST_SHIFT FROM PILIH_KELAS_RULES where ID_OBJ_MHS=? ");
    		stmt.setLong(1,id_obj);
    		rs = stmt.executeQuery();
    		rs.next();
    		String tkn_shift = rs.getString(1);
    		if(tkn_shift.contains("ALL")||tkn_shift.contains("all")||tkn_shift.contains(target_npm)) {
    			value = true;
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
    	return value;	
    }
    
    public static String getListShift(String kdjen) {
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String tmp="";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("select * from SHIFT where TOKEN_KDJEN_AVAILABILITY like ? and AKTIF=true order by KETERANGAN");
    		//stmt = con.prepareStatement("SELECT * FROM SHIFT where AKTIF=? order by KETERANGAN");
    		stmt.setString(1,"%"+kdjen+"%");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String uniqKeter = rs.getString("KETERANGAN");
    			String shift = rs.getString("SHIFT");
    			String hari = rs.getString("HARI");
    			String tkn_kdjen = rs.getString("TOKEN_KDJEN_AVAILABILITY");
    			String keterKonversi = rs.getString("KODE_KONVERSI");
    			//if(tkn_kdjen.contains(kdjen)) {
    				tmp = tmp + uniqKeter+"#"+shift+"#"+hari+"#"+tkn_kdjen+"#"+keterKonversi+"#";
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
    	return tmp;	
    }    
 
    public static String getKdjen(String kdpst) {
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String kdjen="";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT KDJENMSPST FROM MSPST where KDPSTMSPST=?");
    		stmt.setString(1,kdpst);
    		rs = stmt.executeQuery();
    		rs.next();
    		kdjen = rs.getString("KDJENMSPST");
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
    	return kdjen;	
    }    
    
    public static Boolean isAllowRequestBukaKelas(String kdpst) {
    	String thsmsPmb = getThsmsPmb();
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	boolean boleh=false;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//cretae NPM auto increment
    		stmt = con.prepareStatement("SELECT * FROM CALENDAR inner join CALENDAR_BUKA_KELAS on CALENDAR.ID=CALENDAR_BUKA_KELAS.ID_CALENDAR where AKTIF=? and CALENDAR_BUKA_KELAS.KDPST=?");
    		stmt.setBoolean(1,true);
    		stmt.setString(2, kdpst);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			boleh = rs.getBoolean("ALLOW_REQ_KELAS");
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
    	return boleh;	
    }     
    
    public static Vector getListDosenPengajar(String kdpst) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	//System.out.println("kdpst11=="+kdpst);
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		stmt = con.prepareStatement("SELECT * FROM MSDOS where KDPST_HOMEBASE=? or TKN_KDPST_TEACH like ?");
    		stmt.setString(1,kdpst);
    		stmt.setString(2,"%"+kdpst+"%");
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String nodos = ""+rs.getString("NODOS");
    			if(Checker.isStringNullOrEmpty(nodos)) {
    				nodos = "null";
    			}
    			String nidn = ""+rs.getString("NIDN");
    			if(Checker.isStringNullOrEmpty(nidn)) {
    				nidn = "null";
    			}
    			String noKtp = ""+rs.getString("NO_KTP");
    			if(Checker.isStringNullOrEmpty(noKtp)) {
    				noKtp = "null";
    			}
    			String kdptiHome = ""+rs.getString("KDPTI_HOMEBASE");
    			if(Checker.isStringNullOrEmpty(kdptiHome)) {
    				kdptiHome = "null";
    			}
    			String kdpstHome = ""+rs.getString("KDPST_HOMEBASE");
    			if(Checker.isStringNullOrEmpty(kdpstHome)) {
    				kdpstHome = "null";
    			}
    			String nmdos = ""+rs.getString("NMDOS");
    			if(Checker.isStringNullOrEmpty(nmdos)) {
    				nmdos = "null";
    			}
    			String gelar = ""+rs.getString("GELAR");
    			if(Checker.isStringNullOrEmpty(gelar)) {
    				gelar = "null";
    			}
    			String smawl = ""+rs.getString("SMAWL");
    			if(Checker.isStringNullOrEmpty(smawl)) {
    				smawl = "null";
    			}
    			String kdpstAjar = ""+rs.getString("TKN_KDPST_TEACH");
    			if(Checker.isStringNullOrEmpty(kdpstAjar)) {
    				kdpstAjar = "null";
    			}
    			String email = ""+rs.getString("EMAIL");
    			if(Checker.isStringNullOrEmpty(email)) {
    				email = "null";
    			}
    			String tknTelp = ""+rs.getString("TKN_TELP");
    			if(Checker.isStringNullOrEmpty(tknTelp)) {
    				tknTelp = "null";
    			}
    			String tknHp = ""+rs.getString("TKN_HP");
    			if(Checker.isStringNullOrEmpty(tknHp)) {
    				tknHp = "null";
    			}
    			String status = ""+rs.getString("STATUS");
    			if(Checker.isStringNullOrEmpty(status)) {
    				status = "null";
    			}
    			String tmp = nodos+"||"+nidn+"||"+noKtp+"||"+kdptiHome+"||"+kdpstHome+"||"+nmdos+"||"+gelar+"||"+smawl+"||"+kdpstAjar+"||"+email+"||"+tknTelp+"||"+tknHp+"||"+status;
    			//System.out.println("tmpo="+tmp);
    			li.add(tmp);
    			
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
  
    public static String[] getOptKeterMakul(String[]infoKelasDosen,String[]infoKelasMhs,String kdpst) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	if(infoKelasDosen!=null && infoKelasDosen.length>0) {
    	//System.out.println("kdpst11=="+kdpst);
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    		
    			stmt = con.prepareStatement("SELECT OPTKETER FROM MAKUL where IDKMKMAKUL=?");
    			for(int i=0;i<infoKelasDosen.length;i++) {
    				String tmp  = infoKelasDosen[i];
    				StringTokenizer st = new StringTokenizer(tmp,"||");
    				String kdkmk = st.nextToken();
    				String nakmk = st.nextToken();
    				String shift = st.nextToken();
    				String idkmk = st.nextToken();
    				String norut = st.nextToken();
    				String nodos = st.nextToken();
    				String nmdos = st.nextToken();
    				stmt.setString(1, idkmk);
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					String optKeter = ""+rs.getString("OPTKETER");
    					if(!isStringNullOrEmpty(optKeter)) {
    						infoKelasDosen[i]=infoKelasDosen[i]+"||yesketer||"+optKeter;
    					}
    					else {
    						infoKelasDosen[i]=infoKelasDosen[i]+"||noketer||null||"+infoKelasMhs[i];
    					}
    				}
    				else {
    					infoKelasDosen[i]=infoKelasDosen[i]+"||noketer||null||"+infoKelasMhs[i];
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
    	return infoKelasDosen;	
    }     
    
    /*
     * depricated - ganti pake kodeKampus
     */
    public static String getRuleBukaKelas(String kdpst) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	String thsmsPmb = Checker.getThsmsBukaKelas();
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String tmp = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		stmt = con.prepareStatement("select * from CLASS_POOL_RULES where THSMS=? and KDPST=?");
    		stmt.setString(1, thsmsPmb);
    		stmt.setString(2, kdpst);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String tknVerificator = ""+rs.getString("TKN_VERIFICATOR");
    			StringTokenizer st = new StringTokenizer(tknVerificator,",");
    			tmp=thsmsPmb+"||"+kdpst+"||"+tknVerificator+"||"+st.countTokens();
    		}
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
    	return tmp;	
    }
    
    public static Vector getRuleDaftarUlang(String target_thsms) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String tmp = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		stmt = con.prepareStatement("select * from DAFTAR_ULANG_RULES where THSMS=? order by KDPST,KODE_KAMPUS");
    		stmt.setString(1, target_thsms);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String kdpst = ""+rs.getString("KDPST");
    			String tknVerificatorNickname = ""+rs.getString("TKN_VERIFICATOR");
    			boolean urut = rs.getBoolean("URUTAN");
    			String kdkmp = ""+rs.getString("KODE_KAMPUS");
    			li.add(kdpst+"`"+kdkmp+"`"+tknVerificatorNickname+"`"+urut);
    			
    		}
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
    	return v;	
    }
    
    public static String getRuleBukaKelas(String kdpst, String kodeKampus) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	String thsmsPmb = Checker.getThsmsBukaKelas();
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String tmp = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		stmt = con.prepareStatement("select * from CLASS_POOL_RULES where THSMS=? and KDPST=? and KODE_KAMPUS=?");
    		stmt.setString(1, thsmsPmb);
    		stmt.setString(2, kdpst);
    		stmt.setString(3, kodeKampus);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String tknVerificator = ""+rs.getString("TKN_VERIFICATOR");
    			StringTokenizer st = new StringTokenizer(tknVerificator,",");
    			tmp=thsmsPmb+"||"+kdpst+"||"+tknVerificator+"||"+st.countTokens();
    		}
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
    	return tmp;	
    }     
    
    
    
    public static String getNickNameKampus(String kodeKampus) {
    	
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String nickname = null;
    	if(kodeKampus==null || Checker.isStringNullOrEmpty(kodeKampus)) {
    		nickname = kodeKampus+"`DATA ERROR";
    	}
    	else {
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		stmt = con.prepareStatement("select NICKNAME_KAMPUS from KAMPUS where KODE_KAMPUS=?");
        		stmt.setString(1, kodeKampus);
        		rs = stmt.executeQuery();
        		rs.next();
        		nickname = kodeKampus+"`"+rs.getString("NICKNAME_KAMPUS");
        		
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
    		
    	return nickname;	
    }    
    
    
    public static Vector getNickNameKampus(Vector vKodeKampus) {
    	
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String nickname = null;
    	if(vKodeKampus!=null && vKodeKampus.size()>0) {
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		stmt = con.prepareStatement("select NICKNAME_KAMPUS from KAMPUS where KODE_KAMPUS=?");
        		ListIterator li = vKodeKampus.listIterator();
        		while(li.hasNext()) {
        			String kodeKampus = (String)li.next();
        			//System.out.println("kodeKampus="+kodeKampus);
        			stmt.setString(1, kodeKampus);
            		rs = stmt.executeQuery();
            		if(rs.next()) {
            			nickname = kodeKampus+"`"+rs.getString("NICKNAME_KAMPUS");	
            		}
            		else {
            			nickname = kodeKampus+"`DATA ERROR";
            		}
            		li.set(nickname);
        		}
        		
        		
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
    		
    	return vKodeKampus;	
    }    
    
    public static Vector addNakmkAndSemesToVbk(Vector vBk) {
    	ListIterator liBk = vBk.listIterator();
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String tmp = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		
    		//stmt = con.prepareStatement("select * from MAKUL where IDKMKMAKUL=?");
    		stmt = con.prepareStatement("select * from MAKUL inner join MAKUR on IDKMKMAKUL=IDKMKMAKUR where IDKMKMAKUL=? and IDKURMAKUR=?");
			while(liBk.hasNext()) {
				boolean match = false;
				String brs = (String)liBk.next();
				//System.out.println(">>"+brs);
				StringTokenizer st1 = new StringTokenizer(brs,"||");
				//System.out.println("countToekns >"+st1.countTokens());
				/*
				 * dibutuhkan untuk fungsi go back
				 */
				//System.out.println("checker tokens = "+st1.countTokens());
				if(st1.countTokens()==34) {
					String cmd1 = st1.nextToken();
					String idkur1 = st1.nextToken();
					String idkmk1 = st1.nextToken();
    	    		String thsms1 = st1.nextToken();
    	    		String kdpst1 = st1.nextToken();
    	    		String shift1 = st1.nextToken();
    	    		String noKlsPll1 = st1.nextToken();
    	    		String initNpmInput1 = st1.nextToken();
    	    		String latestNpmUpdate1 = st1.nextToken();
    	    		String latestStatusInfo1 = st1.nextToken();
    	    		String locked1 = st1.nextToken();
    	    		String npmdos1 = st1.nextToken();
    	    		String nodos1 = st1.nextToken();
    	    		String npmasdos1 = st1.nextToken();
    	    		String noasdos1 = st1.nextToken();
    	    		String cancel = st1.nextToken();
    	    		String kodeKelas1 = st1.nextToken();
    	    		String kodeRuang1 = st1.nextToken();
    	    		String kodeGedung1 = st1.nextToken();
    	    		String kodeKampus1 = st1.nextToken();
    	    		String tknHrTime1 = st1.nextToken();
    	    		String nmmdos1 = st1.nextToken();
    	    		String nmmasdos1 = st1.nextToken();
    	    		String enrolled1 = st1.nextToken();
    	    		String maxEnrol1 = st1.nextToken();
    	    		String minEnrol1 = st1.nextToken();
    	    		String subKeterMk1 = st1.nextToken();
    	    		String initReqTime1 = st1.nextToken();
    	    		String tknNpmApproval1 = st1.nextToken();
    	    		String tknApprovalTime1 = st1.nextToken();
    	    		String targetTotMhs1 = st1.nextToken();
    	    		String passed1 = st1.nextToken();
    	    		String rejected1 = st1.nextToken();
    	    		String konsen1 = st1.nextToken();
    	    		//System.out.println("idkmk1,idkur1="+idkmk1+","+idkur1);
					stmt.setLong(1,Long.valueOf(idkmk1).longValue());
					stmt.setLong(2,Long.valueOf(idkur1).longValue());
					rs = stmt.executeQuery();
					rs.next();
					String nakmk = rs.getString("NAKMKMAKUL");
					String smsmk = rs.getString("SEMESMAKUR");
					liBk.set(nakmk+"||"+smsmk+"||"+brs);
				}
				else {
					tmp = "";
					String nakmk1 = st1.nextToken();
					String smsmk1 = st1.nextToken();
					String cmd1 = st1.nextToken();
					tmp = tmp+cmd1+"||";
					String idkur1 = st1.nextToken();
					tmp = tmp+idkur1+"||";
					String idkmk1 = st1.nextToken();
					tmp = tmp+idkmk1+"||";
					String thsms1 = st1.nextToken();
					tmp = tmp+thsms1+"||";
					String kdpst1 = st1.nextToken();
					tmp = tmp+kdpst1+"||";
					String shift1 = st1.nextToken();
					tmp = tmp+shift1+"||";
					String noKlsPll1 = st1.nextToken();
					tmp = tmp+noKlsPll1+"||";
					String initNpmInput1 = st1.nextToken();
					tmp = tmp+initNpmInput1+"||";
					String latestNpmUpdate1 = st1.nextToken();
					tmp = tmp+latestNpmUpdate1+"||";
					String latestStatusInfo1 = st1.nextToken();
					tmp = tmp+latestStatusInfo1+"||";
					
					String locked1 = st1.nextToken();
					tmp = tmp+locked1+"||";
					
					String npmdos1 = st1.nextToken();
					tmp = tmp+npmdos1+"||";
					String nodos1 = st1.nextToken();
					tmp = tmp+nodos1+"||";
					String npmasdos1 = st1.nextToken();
					tmp = tmp+npmasdos1+"||";
					String noasdos1 = st1.nextToken();
					tmp = tmp+noasdos1+"||";
					String cancel = st1.nextToken();
					tmp = tmp+cancel+"||";
					
					String kodeKelas1 = st1.nextToken();
					tmp = tmp+kodeKelas1+"||";
					String kodeRuang1 = st1.nextToken();
					tmp = tmp+kodeRuang1+"||";
					String kodeGedung1 = st1.nextToken();
					tmp = tmp+kodeGedung1+"||";
					String kodeKampus1 = st1.nextToken();
					tmp = tmp+kodeKampus1+"||";
					String tknHrTime1 = st1.nextToken();
					tmp = tmp+tknHrTime1+"||";
					String nmmdos1 = st1.nextToken();
					tmp = tmp+nmmdos1+"||";
					String nmmasdos1 = st1.nextToken();
					tmp = tmp+nmmasdos1+"||";
					String enrolled1 = st1.nextToken();
					tmp = tmp+enrolled1+"||";
					String maxEnrol1 = st1.nextToken();
					tmp = tmp+maxEnrol1+"||";
					String minEnrol1 = st1.nextToken();
					tmp = tmp+minEnrol1+"||";
					String subKeterMk1 = st1.nextToken();
					tmp = tmp+subKeterMk1+"||";
					String initReqTime1 = st1.nextToken();
					tmp = tmp+initReqTime1+"||";
					String tknNpmApproval1 = st1.nextToken();
					tmp = tmp+tknNpmApproval1+"||";
					
					String tknApprovalTime1 = st1.nextToken();
					tmp = tmp+tknApprovalTime1+"||";
					
					String targetTotMhs1 = st1.nextToken();
					tmp = tmp+targetTotMhs1+"||";;
					
					String passed1 = st1.nextToken();
					tmp = tmp+passed1+"||";
					String rejected1 = st1.nextToken();
					tmp = tmp+rejected1+"||";
					String konsen1 = st1.nextToken();
					tmp = tmp+konsen1;
					//System.out.println("idkmk1,idkur1="+idkmk1+","+idkur1);
					stmt.setLong(1,Long.valueOf(idkmk1).longValue());
					stmt.setLong(2,Long.valueOf(idkur1).longValue());
					rs = stmt.executeQuery();
					rs.next();
					String nakmk = rs.getString("NAKMKMAKUL");
					String smsmk = rs.getString("SEMESMAKUR");
					liBk.set(nakmk+"||"+smsmk+"||"+tmp);
				}
			}
			
			//stmt = con.prepareStatement("select * from MAKUR where IDKMKMAKUR=? && ");
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
    	return vBk;	
    }     
    
    public static String sudahDaftarUlang(String kdpst, String npmhs) {
    	/*
    	 * artinya sudah di approved oleh semua pihak
    	 */
    	String pesan = null;
    	//boolean status = false;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String nickname = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String thsms_heregistrasi = getThsmsHeregistrasi(); 	
        	stmt = con.prepareStatement("SELECT * FROM DAFTAR_ULANG where THSMS=? and NPMHS=?");
        	stmt.setString(1,thsms_heregistrasi);
        	stmt.setString(2, npmhs);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		String tkn_approval = ""+rs.getString("TOKEN_APPROVAL");
        		stmt = con.prepareStatement("select TKN_VERIFICATOR from DAFTAR_ULANG_RULES where THSMS=? and KDPST=?");
        		stmt.setString(1, thsms_heregistrasi);
        		stmt.setString(2, kdpst);
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			String tkn_verificator = ""+rs.getString("TKN_VERIFICATOR");
        			if(tkn_verificator!=null) {
        				StringTokenizer st = new StringTokenizer(tkn_verificator,",");
        				//boolean incomplete = false;
        				while(st.hasMoreTokens()) {
        					String aprovee = st.nextToken();
        					if(!tkn_approval.contains(aprovee)) {
        					//	incomplete = true;
        						if(pesan == null) {
        							pesan = new String("PROSES DAFTAR ULANG BELUM SELESAI, HARAP HUBUNGI : <br>"+aprovee.replace("OPERATOR", ""));	
        						}
        						else {
        							pesan = pesan + "<br>"+aprovee.replace("OPERATOR", "");
        						}
        						 
        					}
        				}
        			}
        		}
        		else {
        			pesan = null;
        		}
        	}
        	else {
        		pesan = "HARAP MELAKUKAN PENDAFTARAN ULANG TERLEBIH DAHULU";
        	}
        	/*
        	if(pesan==null) {
        		//long objid = getObjectId(npmhs);
        		String npmhs_nickname = getObjNickname((int)getObjectId(npmhs));
        		if(npmhs_nickname.contains("MHS")||npmhs_nickname.contains("mhs")) {
        			stmt = con.prepareStatement("UPDATE CIVITAS SET STMHSMSMHS='A' where NPMHSMSMHS=?");
        			stmt.setString(1, npmhs);
        			stmt.executeUpdate();
        		}
        		//update stmhsmsmhs = A
        		
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
       		if (con!=null) {
       			try {
       				con.close();
       			}
       			catch (Exception ignore) {
       				//System.out.println(ignore);
       			}
       		}
       	}
    	return pesan;	
    }    
    
    
    public static String getKdpst(String npmhs) {
    	String kdpst = null;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String nickname = null;
    	if(npmhs!=null && !Checker.isStringNullOrEmpty(npmhs)) {
    		try {
            	Context initContext  = new InitialContext();
            	Context envContext  = (Context)initContext.lookup("java:/comp/env");
            	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
            	con = ds.getConnection();
            	 	
            	stmt = con.prepareStatement("SELECT KDPSTMSMHS from CIVITAS where NPMHSMSMHS=?");
            	stmt.setString(1, npmhs);
            	rs = stmt.executeQuery();
            	rs.next();
            	kdpst = new String(rs.getString(1));
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
    		
    	return kdpst;
    }
    
    public static String sudahDaftarUlang(String kdpst, String npmhs, String target_thsms) {
    	/*
    	 * artinya sudah di approved oleh semua pihak
    	 * return null bila semua sudah approved
    	 */
    	
    	if(kdpst==null || Checker.isStringNullOrEmpty(kdpst)) {
    		kdpst = new String(getKdpst(npmhs));
    	}
    	String pesan = null;
    	//boolean status = false;
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String nickname = null;
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String thsms_heregistrasi = new String(target_thsms); 	
        	stmt = con.prepareStatement("SELECT * FROM DAFTAR_ULANG where THSMS=? and NPMHS=?");
        	stmt.setString(1,thsms_heregistrasi);
        	stmt.setString(2, npmhs);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		String tkn_approval = ""+rs.getString("TOKEN_APPROVAL");
        		stmt = con.prepareStatement("select TKN_VERIFICATOR from DAFTAR_ULANG_RULES where THSMS=? and KDPST=?");
        		stmt.setString(1, thsms_heregistrasi);
        		stmt.setString(2, kdpst);
        		rs = stmt.executeQuery();
        		if(rs.next()) {
        			String tkn_verificator = ""+rs.getString("TKN_VERIFICATOR");
        			if(tkn_verificator!=null) {
        				StringTokenizer st = new StringTokenizer(tkn_verificator,",");
        				//boolean incomplete = false;
        				while(st.hasMoreTokens()) {
        					String aprovee = st.nextToken();
        					if(!tkn_approval.contains(aprovee)) {
        					//	incomplete = true;
        						if(pesan == null) {
        							pesan = new String("PROSES DAFTAR ULANG BELUM SELESAI, HARAP HUBUNGI : <br>"+aprovee.replace("OPERATOR", ""));	
        						}
        						else {
        							pesan = pesan + "<br>"+aprovee.replace("OPERATOR", "");
        						}
        						 
        					}
        				}
        			}
        		}
        		else {
        			pesan = null;
        		}
        	}
        	else {
        		pesan = "HARAP MELAKUKAN PENDAFTARAN ULANG TERLEBIH DAHULU";
        	}
        	/*
        	if(pesan==null) {
        		//long objid = getObjectId(npmhs);
        		String npmhs_nickname = getObjNickname((int)getObjectId(npmhs));
        		if(npmhs_nickname.contains("MHS")||npmhs_nickname.contains("mhs")) {
        			stmt = con.prepareStatement("UPDATE CIVITAS SET STMHSMSMHS='A' where NPMHSMSMHS=?");
        			stmt.setString(1, npmhs);
        			stmt.executeUpdate();
        		}
        		//update stmhsmsmhs = A
        		
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
       		if (con!=null) {
       			try {
       				con.close();
       			}
       			catch (Exception ignore) {
       				//System.out.println(ignore);
       			}
       		}
       	}
    	return pesan;	
    }    

    public static boolean sudahDaftarUlang(HttpSession session) {
    	/*
    	 * KALO sdu yg dicek adalah user itu sendiri, jadi digunakan bila yg login adalah mhs, jadi dia update dirri sendiri
    	 */
    	boolean sdu = false;
    	String thsms = (String)session.getAttribute("sdu");
    	if(thsms!=null) {
    		sdu = true;
    	}
    	return sdu;
    }
    
    public static String getCurrStmhs(HttpSession session) {
    	/*
    	 * KALO sdu yg dicek adalah user itu sendiri, jadi digunakan bila yg login adalah mhs, jadi dia update dirri sendiri
    	 */
    	//boolean sdu = false;
    	String curr_stmhsmsmhs = (String)session.getAttribute("curr_stmhsmsmhs");
    	
    	return curr_stmhsmsmhs;
    }
    
    public static String yourApproveNicknameAtPengajuanUa(long idObjPengaju, String usrNpm) {
    	//System.out.println("idObjPengaju="+idObjPengaju);
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String MyNick = null;
    	long myObjId = Checker.getObjectId(usrNpm);
    	try {
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	String thsms_heregistrasi = getThsmsHeregistrasi(); 	
        	stmt = con.prepareStatement("SELECT * FROM PENGAJUAN_UA_RULES where IDOBJ=?");
        	stmt.setLong(1,idObjPengaju);
        	rs = stmt.executeQuery();
        	rs.next();
        	String tkn_approvee_id = rs.getString("TKN_APPROVEE_ID");
        	String tkn_approvee_niknem = rs.getString("TKN_APPROVEE_NICKNAME");
        	StringTokenizer st = new StringTokenizer(tkn_approvee_id,"`");
        	StringTokenizer st1 = new StringTokenizer(tkn_approvee_niknem,"`");
        	boolean match = false;
        	while(st.hasMoreTokens()&&!match) {
        		String tkn_id = st.nextToken();
        		String tkn_nick = st1.nextToken();
        		//System.out.println("tkn_id="+tkn_id);
        		//System.out.println("tkn_nick="+tkn_nick);
        		if(tkn_id.contains("/"+myObjId+"/")) {
        			StringTokenizer stt = new StringTokenizer(tkn_id,"/");
        			StringTokenizer stt1 = new StringTokenizer(tkn_nick,"/");
        			while(stt.hasMoreTokens()&&!match) {
        				String tmpId = stt.nextToken();
        				MyNick = new String(stt1.nextToken());
        				if(tmpId.equalsIgnoreCase(""+myObjId)) {
        					match = true;
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
       		if (con!=null) {
       			try {
       				con.close();
       			}
       			catch (Exception ignore) {
       				//System.out.println(ignore);
       			}
       		}
       	}
    	return MyNick;	
    }    
    
    
    public static String getFakInfo(String kdpst) {
    	String kdfak =null;
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
    		stmt = con.prepareStatement("SELECT * FROM MSPST inner join MSFAK on KDFAKMSPST=KDFAKMSFAK where KDPSTMSPST=?");
    		stmt.setString(1,kdpst);
    		rs = stmt.executeQuery();
    		if(!rs.next()) {
    			//System.out.println("error @Checker.getFakInfo, col fak pada MSPST tidak diisi");
    		}
    		else {
    			kdfak = ""+rs.getString("KDFAKMSPST");
    			nmfak = ""+rs.getString("NMFAKMSFAK");
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
    	return kdfak+" "+nmfak;	
    }
    
    /*
     * DEPRECATED
     * USE YG PAKE OBJEK ID
     */
    public static String getSistemPerkuliahan(String  objId, String kdpst) {
    	String siskul = getSistemPerkuliahan_v1(objId);
    	/*      
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String siskul = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT KODE_KAMPUS_DOMISILI from OBJECT  where ID_OBJ=?");
    		stmt.setInt(1, Integer.parseInt(objId));
    		rs = stmt.executeQuery();
    		rs.next();
    		String kode_kmp = rs.getString(1);
    		
    		stmt = con.prepareStatement("SELECT * from CALENDAR_BUKA_KELAS where KDPST=? and (KODE_KAMPUS=? or KODE_KAMPUS=?)");
    		stmt.setString(1, kdpst);
    		stmt.setString(2, kode_kmp);
    		stmt.setString(3, kode_kmp);
    		rs = stmt.executeQuery();
    		if(!rs.next()) {
    			stmt.setString(1, kdpst);
        		stmt.setString(2, "ALL");
        		stmt.setString(3, "all");
        		rs=stmt.executeQuery();
        		rs.next();
        		siskul = new String(rs.getString("SISTEM_PERKULIAHAN"));
    		}
    		else {
    			siskul = new String(rs.getString("SISTEM_PERKULIAHAN"));
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
        */
    	return siskul;	
    }
    
    public static String getSistemPerkuliahan_v1(String  objId) {
	      
    	Connection con=null;
    	PreparedStatement stmt=null;
    	ResultSet rs=null;
    	DataSource ds=null;
    	String siskul = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT SISTEM_PERKULIAHAN from OBJECT  where ID_OBJ=?");
    		stmt.setInt(1, Integer.parseInt(objId));
    		rs = stmt.executeQuery();
    		rs.next();
    		siskul = new String(rs.getString("SISTEM_PERKULIAHAN"));
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
    	return siskul;	
    }
    
    public static boolean am_i_stu(HttpSession session) {
    	String am_i_stu = (String) session.getAttribute("ObjGenderAndNickname");
    	if(am_i_stu.contains("MHS")) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

}
