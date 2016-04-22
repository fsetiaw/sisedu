package beans.dbase.bahan_ajar;

import beans.dbase.SearchDb;
import beans.login.InitSessionUsr;
import beans.tools.Checker;
import beans.tools.Tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import java.util.Vector;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class SearchDbBahanAjar
 */
@Stateless
@LocalBean
public class SearchDbBahanAjar extends SearchDb {
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
    public SearchDbBahanAjar() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbBahanAjar(String operatorNpm) {
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
    public SearchDbBahanAjar(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    //kdpst,kdkmk,idkur,null,isu);
    public Vector getKategoriBahanAjarAktifOnlySesuaiScopeForAssignment(String kdpst, String kdkmk, String idkur, String kode_kmp, InitSessionUsr isu) {
    	Vector v  = new Vector();
    	ListIterator li = v.listIterator();
    	
    	boolean readOnlySapGbpp = true;
		boolean addTipe = false;
		boolean addBahan = false;
		if(isu.isUsrAllowTo("sap", kdpst)) {
    		//showSapGbpp = true;
    		if(!isu.isHakAksesReadOnly("sap")){
    			readOnlySapGbpp = false;
    		}
    	}

    	if(isu.isUsrAllowTo("TBA", kdpst)) {
    		addTipe = true;
    		addBahan = true;
    	}
    	else if(isu.isHakAksesReadOnly("mba")){
    		addBahan = false;
    	}
    	String npmUsr = isu.getNpm();
    	
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			if(kode_kmp==null || Checker.isStringNullOrEmpty(kode_kmp)) {
			//berarti all kampus
				stmt = con.prepareStatement("select * from KATEGORI_BAHAN_AJAR where KDPST=? and AKTIF=?");
				stmt.setString(1, kdpst);
				stmt.setBoolean(2, true);
			}
			else {
				stmt = con.prepareStatement("select * from KATEGORI_BAHAN_AJAR where KDPST=? and KODE_KAMPUS=? and AKTIF=?");
				stmt.setString(1, kdpst);
				stmt.setString(2, kode_kmp);
				stmt.setBoolean(3, true);
			}
			rs = stmt.executeQuery();
			while(rs.next()) {
				String tipe = rs.getString("TIPE");
				String path = rs.getString("PATH");
				path = path.replace("kdpst", kdpst);
    			path = path.replace("kdkmk", kdkmk);
    			path = path.replace("idKur", idkur);
    			path = path.replace("npmDosen", npmUsr);
				String session_var = rs.getString("SESSION_VAR");
				li.add(tipe+"`"+path+"`"+session_var);
			}
			v = Tool.removeDuplicateFromVector(v);
			if(v!=null && v.size()>0) {
				li = v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					if(readOnlySapGbpp) {
						//dosen = ngga bisa asign sap dan gbpp tugs prodi
						if(brs.contains("SAP") || brs.contains("sap") || brs.contains("GBPP") || brs.contains("gbpp")) {
							li.remove();
						}
					}
				}
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
    	return v;
    }	
    
    
    public Vector getKategoriBahanAjarAktifOnlySesuaiScopeNotForAssignment(String kdpst, String kdkmk, String idkur, String kode_kmp, InitSessionUsr isu, String target_npm_mask) {
    	Vector v  = new Vector();
    	ListIterator li = v.listIterator();
    	
    	boolean readOnlySapGbpp = true;
		boolean addTipe = false;
		boolean addBahan = false;
		if(isu.isUsrAllowTo("sap", kdpst)) {
    		//showSapGbpp = true;
    		if(!isu.isHakAksesReadOnly("sap")){
    			readOnlySapGbpp = false;
    		}
    	}

    	if(isu.isUsrAllowTo("TBA", kdpst)) {
    		addTipe = true;
    		addBahan = true;
    	}
    	else if(isu.isHakAksesReadOnly("mba")){
    		addBahan = false;
    	}
    	String npmUsr = null;
    	if(target_npm_mask==null || Checker.isStringNullOrEmpty(target_npm_mask)) {
    		npmUsr = new String(isu.getNpm());		
    	}
    	else {
    		npmUsr = new String(target_npm_mask);
    	}
    
    	
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			if(kode_kmp==null || Checker.isStringNullOrEmpty(kode_kmp)) {
			//berarti all kampus
				stmt = con.prepareStatement("select * from KATEGORI_BAHAN_AJAR where KDPST=? and AKTIF=?");
				stmt.setString(1, kdpst);
				stmt.setBoolean(2, true);
			}
			else {
				stmt = con.prepareStatement("select * from KATEGORI_BAHAN_AJAR where KDPST=? and KODE_KAMPUS=? and AKTIF=?");
				stmt.setString(1, kdpst);
				stmt.setString(2, kode_kmp);
				stmt.setBoolean(3, true);
			}
			rs = stmt.executeQuery();
			while(rs.next()) {
				String tipe = rs.getString("TIPE");
				String path = rs.getString("PATH");
				path = path.replace("kdpst", kdpst);
    			path = path.replace("kdkmk", kdkmk);
    			path = path.replace("idKur", idkur);
    			path = path.replace("npmDosen", npmUsr);
				String session_var = rs.getString("SESSION_VAR");
				li.add(tipe+"`"+path+"`"+session_var);
			}
			v = Tool.removeDuplicateFromVector(v);
			/*
			if(v!=null && v.size()>0) {
				li = v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					if(readOnlySapGbpp) {
						//dosen = ngga bisa asign sap dan gbpp tugs prodi
						if(brs.contains("SAP") || brs.contains("sap") || brs.contains("GBPP") || brs.contains("gbpp")) {
							li.remove();
						}
					}
				}
			}
			*/
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
    	return v;
    }	

    
    public Vector getKategoriBahanAjarForMhsOnly(long class_uid) {
    	Vector v  = new Vector();
    	ListIterator li = v.listIterator();
    	//String tkn_tipe_path = "";
    	//System.out.println("class_uid="+class_uid);
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from CLASS_POOL_BAHAN_AJAR where UNIQUE_ID=? order by TIPE");
			stmt.setLong(1, class_uid);
			
			rs = stmt.executeQuery();
			while(rs.next()) {
				String tipe = rs.getString("TIPE");
				String path = rs.getString("FILE_DIR");
				
				li.add(tipe+"`"+path);
			}
			//v = Tool.removeDuplicateFromVector(v);
			/*
			if(v!=null && v.size()>0) {
				li = v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					if(readOnlySapGbpp) {
						//dosen = ngga bisa asign sap dan gbpp tugs prodi
						if(brs.contains("SAP") || brs.contains("sap") || brs.contains("GBPP") || brs.contains("gbpp")) {
							li.remove();
						}
					}
				}
			}
			*/
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
    	return v;
    }	

    
    /*
    public Vector getKategoriBahanAjarAktifOnlySesuaiScope(String kdpst, String kode_kmp, boolean readOnlySapGbpp, boolean addTipe, boolean addBahan) {
    	Vector v  = new Vector();
    	ListIterator li = v.listIterator();
    	
    	
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			if(kode_kmp==null || Checker.isStringNullOrEmpty(kode_kmp)) {
			//berarti all kampus
				stmt = con.prepareStatement("select * from KATEGORI_BAHAN_AJAR where KDPST=? and AKTIF=?");
				stmt.setString(1, kdpst);
				stmt.setBoolean(2, true);
			}
			else {
				stmt = con.prepareStatement("select * from KATEGORI_BAHAN_AJAR where KDPST=? and KODE_KAMPUS=? and AKTIF=?");
				stmt.setString(1, kdpst);
				stmt.setString(2, kode_kmp);
				stmt.setBoolean(3, true);
			}
			rs = stmt.executeQuery();
			while(rs.next()) {
				String tipe = rs.getString("TIPE");
				String path = rs.getString("PATH");
				String session_var = rs.getString("SESSION_VAR");
				li.add(tipe+"`"+path+"`"+session_var);
			}
			v = Tool.removeDuplicateFromVector(v);
			if(v!=null && v.size()>0) {
				li = v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					if(readOnlySapGbpp) {
						//dosen = ngga bisa asign sap dan gbpp tugs prodi
						if(brs.contains("SAP") || brs.contains("sap") || brs.contains("GBPP") || brs.contains("gbpp")) {
							li.remove();
						}
					}
				}
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
    	return v;
    }	
    
    */

    
    public Vector getAssignedBahanAjar(int class_uid) {
    	Vector v  = new Vector();
    	ListIterator li = v.listIterator();
    	
    	
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			
			stmt = con.prepareStatement("select * from CLASS_POOL_BAHAN_AJAR where UNIQUE_ID=?");
			stmt.setInt(1, class_uid);
			rs = stmt.executeQuery();
			while(rs.next()) {
				String tipe = rs.getString("TIPE");
				String path = rs.getString("FILE_DIR");
				li.add(tipe+"`"+path);
			}
			v = Tool.removeDuplicateFromVector(v);
			
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
    	return v;
    }	
    
    

}
