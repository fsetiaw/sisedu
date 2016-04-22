package beans.login;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.util.Vector;
import java.util.ListIterator;
import java.util.Collections;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import beans.setting.Constants;
import beans.sistem.AskSystem;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Getter;
import beans.tools.Tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.sql.DriverManager;
/**
 * Session Bean implementation class USR_SESSION
 */
@Stateless
@LocalBean
public class InitSessionUsr {
	//profile
	String fullname, nim, npm,kdpst;
	String dbSchema;
	int objLevel,idObj;
	String kode_kmp_dom;
	String accessLevelConditional,accessLevel;
	String url;     
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	DataSource ds; 
	boolean am_i_stu;
    /**
     * Default constructor. 
     */
    public InitSessionUsr() {
        // TODO Auto-generated constructor stub
    }
    
    public InitSessionUsr(String fullname,String kdpst,String npm,String dbSchema,int objLevel,String accessLevelConditional,String accessLevel, int idObj) {
    	this.fullname=fullname;
    	this.kdpst = kdpst;
    	this.npm=npm;
    	this.dbSchema=dbSchema;
    	this.objLevel=objLevel;
    	this.accessLevelConditional = accessLevelConditional;
    	this.accessLevel = accessLevel;
    	this.idObj = idObj;
    }
    
    public InitSessionUsr(String fullname,String kdpst,String npm,String dbSchema,int objLevel,String accessLevelConditional,String accessLevel, int idObj, String obj_nick) {
    	this.fullname=fullname;
    	this.kdpst = kdpst;
    	this.npm=npm;
    	this.dbSchema=dbSchema;
    	this.objLevel=objLevel;
    	this.accessLevelConditional = accessLevelConditional;
    	this.accessLevel = accessLevel;
    	this.idObj = idObj;
    	if(obj_nick!=null && (obj_nick.contains("MHS")||obj_nick.contains("mhs"))) {
    		am_i_stu =  true;
    	}
    	else {
    		am_i_stu = false;
    	}
    }
    
    public boolean iAmStu() {
    	return this.am_i_stu;
    }
     
    public String getKdpst() {
    	return kdpst;
    }
    
    public int getObjLevel() {
    	return objLevel;
    }
    public int getIdObj() {
    	return idObj;
    }
    public String getNpm() {
    	return npm;
    }    
    public String getFullname() {
    	return fullname;
    }
    public String getDbSchema() {
    	return dbSchema;
    }
    public String getAccessLevelConditional() {
    	return accessLevelConditional;
    }
    public String getAccessLevel() {
    	return accessLevel;
    }
    
    /*
     * TGL:14 DES 2015 
     * semua get scope pake ini sekarang
     * return scope, own or yes
     */
    public Vector getScopeObjIdFinal(String command, boolean prodi_only, boolean convert_own_to_info_prodi, boolean convert_own_to_npm) {
    	Vector v = null;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
			//envContext.close();
    		//initContext.close();
			con = ds.getConnection(); 
			stmt = con.prepareStatement("select * from OBJECT where ID_OBJ=?");
			stmt.setInt(1, this.getIdObj());
			rs = stmt.executeQuery();
			rs.next();
			String usr_obj_id = ""+rs.getInt("ID_OBJ");
			String usr_kdpst = ""+rs.getString("KDPST");
			String usr_obj_name = ""+rs.getString("OBJ_NAME");
			String usr_obj_desc = ""+rs.getString("OBJ_DESC");
			String usr_obj_lvl = ""+rs.getInt("OBJ_LEVEL");
			String usr_acc_lvl_cond = ""+rs.getString("ACCESS_LEVEL_CONDITIONAL");
			String usr_acc_lvl = ""+rs.getString("ACCESS_LEVEL");
			String usr_default_val = ""+rs.getString("DEFAULT_VALUE");
			String usr_obj_nickname = ""+rs.getString("OBJ_NICKNAME");
			String usr_hak_akses = ""+rs.getString("HAK_AKSES");
			String usr_scope_kmp = ""+rs.getString("SCOPE_KAMPUS");
			String usr_kode_kmp_dom = ""+rs.getString("KODE_KAMPUS_DOMISILI");
			if(usr_acc_lvl.contains(command)) {
				//1. get token ke brpnya
				int token_ke=0;
				StringTokenizer st = new StringTokenizer(usr_acc_lvl,"#");
				boolean match = false;
				for(token_ke=0;st.hasMoreTokens() && !match;token_ke++) {
					String tkn = st.nextToken();
					if(tkn.equalsIgnoreCase(command)) {
						match = true;
					}
				}
				if(match) {
					////System.out.println(command+"= token ke "+token_ke);
					String scope_obj_lvl = Tool.getTokenKe(usr_acc_lvl_cond, token_ke, "#");
					////System.out.println("scope_obj_lvl="+scope_obj_lvl);	
					v = getListObjectScope_ver3(scope_obj_lvl,prodi_only, convert_own_to_info_prodi,  convert_own_to_npm); 
					ListIterator li = v.listIterator();
					while(li.hasNext()) {
						String brs = (String)li.next();
						////System.out.println("scopee=="+brs);
					}
					
				}
				
				////System.out.println("scope_obj_lvl="+scope_obj_lvl);
			}
			else {
				//no akses, return null
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
    
    public String getNmmhs(String npmhs) {
    	//first get list obj level dari tabel object
    	String nmmhs= null;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
			//envContext.close();
    		//initContext.close();
			con = ds.getConnection(); 
			stmt = con.prepareStatement("select NMMHSMSMHS from CIVITAS where NPMHSMSMHS=?");
			stmt.setString(1, npmhs);
			rs = stmt.executeQuery();
			if(rs.next()) {
				nmmhs = ""+rs.getString("NMMHSMSMHS");
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
    	return nmmhs;
    }   
    
    
    public String getKode_kmp_dom() {
    	//first get list obj level dari tabel object
    	String kd_kmp= null;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
			//envContext.close();
    		//initContext.close();
			con = ds.getConnection(); 
			stmt = con.prepareStatement("select KODE_KAMPUS_DOMISILI from OBJECT inner join CIVITAS on OBJECT.ID_OBJ=CIVITAS.ID_OBJ where NPMHSMSMHS=?");
			stmt.setString(1, this.npm);
			rs = stmt.executeQuery();
			if(rs.next()) {
				kd_kmp = ""+rs.getString("KODE_KAMPUS_DOMISILI");
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
    	return kd_kmp;
    }   
    
    public String getKurikulumOperationalId(String npmhs) {
    	//first get list obj level dari tabel object
    	String krklm= null;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
			//envContext.close();
    		//initContext.close();
			con = ds.getConnection(); 
			stmt = con.prepareStatement("select KRKLMMSMHS from EXT_CIVITAS where NPMHSMSMHS=?");
			stmt.setString(1, npmhs);
			rs = stmt.executeQuery();
			if(rs.next()) {
				krklm = ""+rs.getString("KRKLMMSMHS");
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
    	return krklm;
    }
    
    public boolean isHakAksesReadOnly(String cmd) {
    	String hak_akses = getHakAkses(cmd);
    	boolean readOnly = false;
    	if(hak_akses.contains("r")&&!hak_akses.contains("e") && !hak_akses.contains("i")&&!hak_akses.contains("d")) {
    		readOnly = true;
    	}
    	return readOnly;
    }
    
    public String getHakAkses(String cmd) {
    	//first get list obj level dari tabel object
    	String hak_akses= null;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
			//envContext.close();
    		//initContext.close();
			con = ds.getConnection(); 
			stmt = con.prepareStatement("select * from OBJECT where ID_OBJ=?");
			stmt.setLong(1, this.getIdObj());
			////System.out.println(this.getIdObj());
			rs = stmt.executeQuery();
			
			String akses_lvl = null;
			//hak_akses = null;
			if(rs.next()) {
				////System.out.println("rsnext");
				akses_lvl = ""+rs.getString("ACCESS_LEVEL");
				
				hak_akses = ""+rs.getString("HAK_AKSES");
				if(akses_lvl!=null && akses_lvl.contains(cmd)) {
					StringTokenizer st = new StringTokenizer(akses_lvl,"#");
					boolean match = false;
					int norut=0;
					while(st.hasMoreTokens() && !match) {
						norut++;
						String tmp = st.nextToken();
						if(tmp.equalsIgnoreCase(cmd)) {
							match = true;
						}
					}
					////System.out.print("match?"+match);
					////System.out.print("norut="+norut);
					
					st = new StringTokenizer(hak_akses,"#");
					hak_akses = "";
					for(int i=0;i<norut;i++) {
						hak_akses = ""+st.nextToken();
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
    	return hak_akses;
    }
    
    public Vector getKomplitInfoObj(int idObj) {
    	Vector v = null;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
			//envContext.close();
    		//initContext.close();
			con = ds.getConnection(); 
			stmt = con.prepareStatement("select * from OBJECT where ID_OBJ=?");
			stmt.setLong(1, idObj);
			////System.out.println(this.getIdObj());
			rs = stmt.executeQuery();
			
			String kdpst = null;
			String obj_name = null;
			String obj_desc = null;
			String obj_lvl = null;
			String access_level_con = null;
			String access_level = null;
			String default_val = null;
			String mickname = null;
			String hak_akses = null;
			String scope_kampus = null;
			String kode_kampus_domisili = null;
			
			
			//hak_akses = null;
			if(rs.next()) {
				////System.out.println("rsnext");

				kdpst = ""+rs.getString("KDPST");
				obj_name = ""+rs.getString("OBJ_NAME");
				obj_desc = ""+rs.getString("OBJ_DESC");
				obj_lvl = ""+rs.getInt("OBJ_LEVEL");
				access_level_con = ""+rs.getString("ACCESS_LEVEL_CONDITIONAL");
				access_level = ""+rs.getString("ACCESS_LEVEL");
				default_val = ""+rs.getString("DEFAULT_VALUE");
				mickname = ""+rs.getString("OBJ_NICKNAME");
				hak_akses = ""+rs.getString("HAK_AKSES");
				scope_kampus = ""+rs.getString("SCOPE_KAMPUS");
				kode_kampus_domisili = ""+rs.getString("KODE_KAMPUS_DOMISILI");
				
				v = new Vector();
				ListIterator li = v.listIterator();
				li.add(kdpst);
				li.add(obj_name);
				li.add(obj_desc);
				li.add(obj_lvl);
				li.add(access_level_con);
				li.add(access_level);
				li.add(default_val);
				li.add(mickname);
				li.add(hak_akses);
				li.add(scope_kampus);
				li.add(kode_kampus_domisili);
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
    
    
    public String getScopeKampus(String cmd) {
    	//first get list obj level dari tabel object
    	//System.out.println("cuman="+cmd);;
    	String scope_kampus= null;
    	String kode_kmp_dom = "";
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
			//envContext.close();
    		//initContext.close();
			con = ds.getConnection(); 
			stmt = con.prepareStatement("select * from OBJECT where ID_OBJ=?");
			stmt.setLong(1, this.getIdObj());
			////System.out.println(this.getIdObj());
			rs = stmt.executeQuery();
			
			String akses_lvl = null;
			//hak_akses = null;
			if(rs.next()) {
				//System.out.println("rsnext");
				akses_lvl = ""+rs.getString("ACCESS_LEVEL");
				scope_kampus = ""+rs.getString("SCOPE_KAMPUS");
				kode_kmp_dom = ""+rs.getString("KODE_KAMPUS_DOMISILI");
				//System.out.println("akses_lvl="+akses_lvl);
				//System.out.println("scope_kampus="+scope_kampus);
				int count_tkn_akses_level = 0;
				int count_tkn_scope_kampus = 0;
				
				StringTokenizer st  = new StringTokenizer(akses_lvl,"#");
				count_tkn_akses_level = st.countTokens();
				st  = new StringTokenizer(scope_kampus,"#");
				count_tkn_scope_kampus = st.countTokens();
				if(akses_lvl!=null && akses_lvl.contains(cmd)) {
					st = new StringTokenizer(akses_lvl,"#");
					boolean match = false;
					int norut=0;
					while(st.hasMoreTokens() && !match) {
						norut++;
						String tmp = st.nextToken();
						if(tmp.equalsIgnoreCase(cmd)) {
							match = true;
						}
					}
					//System.out.println("match?"+match);
					//System.out.println("norut="+norut);
					//System.out.println("scope_kampus="+scope_kampus);
					if(count_tkn_akses_level!=count_tkn_scope_kampus) {
						scope_kampus = kode_kmp_dom;
						String tmp = "";
						for(int i=0;i<count_tkn_akses_level;i++) {
							tmp = tmp+kode_kmp_dom+"#";
						}
						//self fix
						stmt = con.prepareStatement("update OBJECT set SCOPE_KAMPUS=? where ID_OBJ=?");
						stmt.setString(1, tmp);
						stmt.setLong(2, this.getIdObj());
						stmt.executeUpdate();
					}
					else if(scope_kampus.contains(cmd)) {
						//berarti scope kampus lupa diisi
						//System.out.println("scope_kampus contain "+cmd+" = yes");
						scope_kampus = kode_kmp_dom;
						
						//self fix
						stmt = con.prepareStatement("update OBJECT set SCOPE_KAMPUS=? where ID_OBJ=?");
						stmt.setString(1, scope_kampus.replace(cmd, kode_kmp_dom));
						stmt.setLong(2, this.getIdObj());
						stmt.executeUpdate();
					}
					else {
						////System.out.println("scope_kampus contain "+cmd+" = nope");
						st = new StringTokenizer(scope_kampus,"#");
						scope_kampus = "";
						for(int i=0;i<norut;i++) {
							scope_kampus = ""+st.nextToken();
							
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
    	catch (Exception e) {
    		scope_kampus = getKode_kmp_dom();
    	}
		finally {
			if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
    		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
    		if (con!=null) try { con.close();} catch (Exception ignore){}
		}
    	if(scope_kampus==null || Checker.isStringNullOrEmpty(scope_kampus)) {
    		scope_kampus = getKode_kmp_dom();
    	}
    	////System.out.println("cuman="+scope_kampus);
    	return scope_kampus;
    }
    
    public String getKurikulumOperationalId() {
    	//first get list obj level dari tabel object
    	String krklm= null;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
			//envContext.close();
    		//initContext.close();
			//////System.out.println("npm="+this.npm);
			con = ds.getConnection(); 
			stmt = con.prepareStatement("select KRKLMMSMHS from EXT_CIVITAS where NPMHSMSMHS=?");
			stmt.setString(1, this.npm);
			rs = stmt.executeQuery();
			if(rs.next()) {
				////System.out.println("next");
				krklm = ""+rs.getString("KRKLMMSMHS");
				////System.out.println("krklm="+krklm);
			}
			else {
				////System.out.println("getKurikulumOperationalId()error");
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
    	return krklm;
    }    
    
    
    public boolean isUsrAccessCommandContain(String command) {
    	boolean ya = false;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
			//envContext.close();
    		//initContext.close();
			////System.out.println("npm="+this.npm);
			con = ds.getConnection(); 
			stmt = con.prepareStatement("select ACCESS_LEVEL from OBJECT where ID_OBJ=? and ACCESS_LEVEL like ?");
			stmt.setInt(1, this.idObj);
			stmt.setString(2, "%"+command+"%");
			rs = stmt.executeQuery();
			if(rs.next()) {
				ya = true;
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
    	return ya;
    }    
    
    public String getKdjek() {
    	//first get list obj level dari tabel object
    	String kdjek= null;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
			//envContext.close();
    		//initContext.close();
			con = ds.getConnection(); 
			stmt = con.prepareStatement("select KDJEKMSMHS from CIVITAS where NPMHSMSMHS=?");
			stmt.setString(1, this.npm);
			rs = stmt.executeQuery();
			if(rs.next()) {
				kdjek = ""+rs.getString("KDJEKMSMHS");
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
    	return kdjek;
    }        
    public String getNimhs(String npmhs) {
    	//first get list obj level dari tabel object
    	String nimhs= null;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
			//envContext.close();
    		//initContext.close();
			con = ds.getConnection(); 
			stmt = con.prepareStatement("select NIMHSMSMHS from CIVITAS where NPMHSMSMHS=?");
			stmt.setString(1, npmhs);
			rs = stmt.executeQuery();
			if(rs.next()) {
				nimhs = ""+rs.getString("NIMHSMSMHS");
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
    	return nimhs;
    }
    
    
    public String getStmhs(String npmhs) {
    	//first get list obj level dari tabel object
    	String stmhs= null;
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
			//envContext.close();
    		//initContext.close();
			con = ds.getConnection(); 
			stmt = con.prepareStatement("select STMHSMSMHS from CIVITAS where NPMHSMSMHS=?");
			stmt.setString(1, npmhs);
			rs = stmt.executeQuery();
			if(rs.next()) {
				stmhs = ""+rs.getString("STMHSMSMHS");
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
    	return stmhs;
    }
    
    /*
     * depricated - tambah kode kampus
     */
    public Vector getListObjectScope(String token_scope) {
    	//first get list obj level dari tabel object
    	Vector v_obj = new Vector();

    	ListIterator li_obj = v_obj.listIterator();
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
			//envContext.close();
    		//initContext.close();
			con = ds.getConnection(); 
			stmt = con.prepareStatement("select * from OBJECT order by OBJ_DESC");
			rs = stmt.executeQuery();
			while(rs.next()) {
				long id_obj = rs.getLong("ID_OBJ");
				String kdpst = rs.getString("KDPST");
				String obj_dsc = rs.getString("OBJ_DESC");
				obj_dsc = obj_dsc.replaceAll(" ", "_");
				long obj_level = rs.getLong("OBJ_LEVEL");
				li_obj.add(id_obj+" "+kdpst+" "+obj_dsc+" "+obj_level);
			}
			
			stmt = con.prepareStatement("select * from MSPST where KDPSTMSPST=?");
			li_obj = v_obj.listIterator();
			while(li_obj.hasNext()) {
				String brs = (String)li_obj.next();
				StringTokenizer st = new StringTokenizer(brs);
				String id_obj = st.nextToken();
				String kdpst = st.nextToken();
				String obj_dsc = st.nextToken();
				String obj_level = st.nextToken();
				stmt.setString(1, kdpst);
				rs = stmt.executeQuery();
				String kdjen = null;
				if(rs.next()) {
					kdjen = rs.getString("KDJENMSPST");
				}
				li_obj.set(brs+" "+kdjen);
			}
			rs.close();
			stmt.close();
			con.close();
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
    	
    	StringTokenizer st = new StringTokenizer(token_scope,",");
    	//cek apa 1 token ato beberapa
    	Vector v_eq = new Vector();
    	ListIterator li_eq = v_eq.listIterator();
    	if(st.countTokens()>1) {
    		li_obj = v_obj.listIterator();
			while(li_obj.hasNext()) {
				String id_lvl = (String)li_obj.next();
				StringTokenizer st1 = new StringTokenizer(id_lvl);
				String id = st1.nextToken();
				String kdpst = st1.nextToken();
				String obj_dsc = st1.nextToken();
				String lvl = st1.nextToken();
				long tmp_lvl = Long.valueOf(lvl).longValue();
				boolean remove = false;
				boolean match = false;
				st = new StringTokenizer(token_scope,",");
				while(st.hasMoreTokens() && !remove) {
					String user_level = (String)st.nextToken();
					String cs="=";
		    		if(user_level.startsWith(cs)) {
		    			////System.out.println("disini");
		    			user_level = user_level.substring(1,user_level.length());
		    			//li_eq.add(""+user_level);
		    			long usr_lvl = Long.valueOf(user_level).longValue();
		    			if(usr_lvl==tmp_lvl) {
		    				match = true;
		    			}
		    		}
		    		else {
		    			cs="<=";
		    			if(user_level.startsWith(cs)) {
			    			user_level = user_level.substring(2,user_level.length());
			    			long usr_lvl = Long.valueOf(user_level).longValue();
			    			if(usr_lvl<tmp_lvl) {
			    				remove = true;
			    			}
			    		}
		    			else {
		    				cs=">=";
		    				if(user_level.startsWith(cs)) {
				    			user_level = user_level.substring(2,user_level.length());
				    			long usr_lvl = Long.valueOf(user_level).longValue();
				    			if(usr_lvl>tmp_lvl) {
				    				remove = true;
				    			}
				    		}
		    				else {
		    					cs=">";
		    					if(user_level.startsWith(cs)) {
		    		    			user_level = user_level.substring(1,user_level.length());
		    		    			long usr_lvl = Long.valueOf(user_level).longValue();
		    		    			if(usr_lvl>=tmp_lvl) {
		    		    				remove = true;
		    		    			}
		    		    		}
		    					else {
		    						cs="<";
		    						if(user_level.startsWith(cs)) {
		    			    			user_level = user_level.substring(1,user_level.length());
		    			    			long usr_lvl = Long.valueOf(user_level).longValue();
		    			    			if(usr_lvl<=tmp_lvl) {
		    			    				remove = true;
		    			    			}
		    			    		}
		    					}
		    				}
		    			}
		    		}
				}
				if(remove) {
					li_obj.remove();
				}
				else {
					if(!match) {
						li_obj.remove();
					}	
				}
			}
    	}
    	else {
    		//proses 1 token
    		String cs="=";
    		if(token_scope.startsWith(cs)) {
    			String user_level = token_scope.substring(1,token_scope.length());
    			long usr_lvl = Long.valueOf(user_level).longValue();
    			li_obj = v_obj.listIterator();
    			while(li_obj.hasNext()) {
    				String id_lvl = (String)li_obj.next();
    				StringTokenizer st1 = new StringTokenizer(id_lvl);
    				String id = st1.nextToken();
    				String kdpst = st1.nextToken();
    				String obj_dsc = st1.nextToken();
    				String lvl = st1.nextToken();
    				long tmp_lvl = Long.valueOf(lvl).longValue();
    				if(usr_lvl!=tmp_lvl) {
    					li_obj.remove();
    				}
    			}
    		}
    		else {
    			cs="<=";
    			if(token_scope.startsWith(cs)) {
        			String user_level = token_scope.substring(2,token_scope.length());
        			long usr_lvl = Long.valueOf(user_level).longValue();
        			li_obj = v_obj.listIterator();
        			while(li_obj.hasNext()) {
        				String id_lvl = (String)li_obj.next();
        				StringTokenizer st1 = new StringTokenizer(id_lvl);
        				String id = st1.nextToken();
        				String kdpst = st1.nextToken();
        				String obj_dsc = st1.nextToken();
        				String lvl = st1.nextToken();
        				long tmp_lvl = Long.valueOf(lvl).longValue();
        				if(usr_lvl<tmp_lvl) {
        					li_obj.remove();
        				}
        			}
    			}
    			else {
    				cs=">=";
    				if(token_scope.startsWith(cs)) {
    					////System.out.println("masuk >=");
    	    			String user_level = token_scope.substring(2,token_scope.length());
    	    			long usr_lvl = Long.valueOf(user_level).longValue();
    	    			li_obj = v_obj.listIterator();
    	    			while(li_obj.hasNext()) {
    	    				String id_lvl = (String)li_obj.next();
    	    				StringTokenizer st1 = new StringTokenizer(id_lvl);
    	    				String id = st1.nextToken();
    	    				String kdpst = st1.nextToken();
    	    				String obj_dsc = st1.nextToken();
    	    				String lvl = st1.nextToken();
    	    				long tmp_lvl = Long.valueOf(lvl).longValue();
    	    				////System.out.println(usr_lvl+"<"+tmp_lvl);
    	    				if(usr_lvl>tmp_lvl) {
    	    					li_obj.remove();
    	    					////System.out.println("remove");
    	    				}
    	    			}
    				}
    				else {
    					cs=">";
    					if(token_scope.startsWith(cs)) {
        	    			String user_level = token_scope.substring(1,token_scope.length());
        	    			long usr_lvl = Long.valueOf(user_level).longValue();
        	    			li_obj = v_obj.listIterator();
        	    			while(li_obj.hasNext()) {
        	    				String id_lvl = (String)li_obj.next();
        	    				StringTokenizer st1 = new StringTokenizer(id_lvl);
        	    				String id = st1.nextToken();
        	    				String kdpst = st1.nextToken();
        	    				String obj_dsc = st1.nextToken();
        	    				String lvl = st1.nextToken();
        	    				long tmp_lvl = Long.valueOf(lvl).longValue();
        	    				if(usr_lvl>=tmp_lvl) {
        	    					li_obj.remove();
        	    				}
        	    			}
        				}
    					else {
    						cs="<";
    						if(token_scope.startsWith(cs)) {
    	    	    			String user_level = token_scope.substring(1,token_scope.length());
    	    	    			long usr_lvl = Long.valueOf(user_level).longValue();
    	    	    			li_obj = v_obj.listIterator();
    	    	    			while(li_obj.hasNext()) {
    	    	    				String id_lvl = (String)li_obj.next();
    	    	    				StringTokenizer st1 = new StringTokenizer(id_lvl);
    	    	    				String id = st1.nextToken();
    	    	    				String kdpst = st1.nextToken();
    	    	    				String obj_dsc = st1.nextToken();
    	    	    				String lvl = st1.nextToken();
    	    	    				long tmp_lvl = Long.valueOf(lvl).longValue();
    	    	    				if(usr_lvl<=tmp_lvl) {
    	    	    					li_obj.remove();
    	    	    				}
    	    	    			}
    	    				}
    						else {
    							//tambahan 12/10/2012
    							//kalo own berarti hanaya return npm ma kdpst
    							cs="own";
    							////System.out.println("token scope vs own = "+token_scope);
    							if(token_scope.startsWith(cs)) {
        	    	    			v_obj = new Vector();
        	    	    			li_obj = v_obj.listIterator();
        	    	    			li_obj.add(this.npm);
        	    				}
    						}
    					}
    				}
    			}
    		}
    	}
    	//li_eq = v_eq.listIterator();
    	//while(li_eq.hasNext()) {
    	//	li_obj.add((String) li_eq.next());
    	//}
    	//Collections.sort(v_obj);
    	 
    	return v_obj;
    }

    
    public Vector getListObjectScope_ver3(String token_scope, boolean prodi_only, boolean convert_own_to_info_prodi, boolean convert_own_to_npm) {
    	//first get list obj level dari tabel object
    	// kalo value yes return yes
    	Vector v_obj = new Vector();
    	ListIterator li_obj = v_obj.listIterator();
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
			//envContext.close();
    		//initContext.close();
			con = ds.getConnection(); 
			stmt = con.prepareStatement("select * from OBJECT order by OBJ_DESC");
			rs = stmt.executeQuery();
			while(rs.next()) {
				long id_obj = rs.getLong("ID_OBJ");
				String kdpst = rs.getString("KDPST");
				String obj_dsc = rs.getString("OBJ_DESC");
				obj_dsc = obj_dsc.replaceAll(" ", "_");
				long obj_level = rs.getLong("OBJ_LEVEL");
				String kode_kampus = rs.getString("KODE_KAMPUS_DOMISILI");
				li_obj.add(id_obj+" "+kdpst+" "+obj_dsc+" "+obj_level+" "+kode_kampus);
			}
			
			stmt = con.prepareStatement("select KDJENMSPST,PRODI from MSPST where KDPSTMSPST=?");
			li_obj = v_obj.listIterator();
			while(li_obj.hasNext()) {
				String brs = (String)li_obj.next();
				StringTokenizer st = new StringTokenizer(brs);
				String id_obj = st.nextToken();
				String kdpst = st.nextToken();
				String obj_dsc = st.nextToken();
				String obj_level = st.nextToken();
				String kode_kampus = st.nextToken();
				stmt.setString(1, kdpst);
				rs = stmt.executeQuery();
				String kdjen = null;
				boolean is_prodi = false;
				if(rs.next()) {
					kdjen = rs.getString("KDJENMSPST");
					is_prodi = rs.getBoolean("PRODI");
				}
				else {
					kdjen = "null";
				}
				if(prodi_only && !is_prodi) {
					li_obj.remove();
				}
				else {
					li_obj.set(id_obj+" "+kdpst+" "+obj_dsc+" "+obj_level+" "+kdjen+" "+kode_kampus);	
				}
				
				//li_obj.set(brs+" "+kdjen);
			}
			rs.close();
			stmt.close();
			con.close();
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
    	
    	StringTokenizer st = new StringTokenizer(token_scope,",");
    	//cek apa 1 token ato beberapa
    	Vector v_eq = new Vector();
    	ListIterator li_eq = v_eq.listIterator();
    	if(st.countTokens()>1) {
    		li_obj = v_obj.listIterator();
			while(li_obj.hasNext()) {
				String id_lvl = (String)li_obj.next();
				StringTokenizer st1 = new StringTokenizer(id_lvl);
				String id = st1.nextToken();
				String kdpst = st1.nextToken();
				String obj_dsc = st1.nextToken();
				String lvl = st1.nextToken();
				long tmp_lvl = Long.valueOf(lvl).longValue();
				boolean remove = false;
				boolean match = false;
				st = new StringTokenizer(token_scope,",");
				while(st.hasMoreTokens() && !remove) {
					String user_level = (String)st.nextToken();
					String cs="=";
		    		if(user_level.startsWith(cs)) {
		    			////System.out.println("disini");
		    			user_level = user_level.substring(1,user_level.length());
		    			//li_eq.add(""+user_level);
		    			long usr_lvl = Long.valueOf(user_level).longValue();
		    			if(usr_lvl==tmp_lvl) {
		    				match = true;
		    			}
		    		}
		    		else {
		    			cs="<=";
		    			if(user_level.startsWith(cs)) {
			    			user_level = user_level.substring(2,user_level.length());
			    			long usr_lvl = Long.valueOf(user_level).longValue();
			    			if(usr_lvl<tmp_lvl) {
			    				remove = true;
			    			}
			    		}
		    			else {
		    				cs=">=";
		    				if(user_level.startsWith(cs)) {
				    			user_level = user_level.substring(2,user_level.length());
				    			long usr_lvl = Long.valueOf(user_level).longValue();
				    			if(usr_lvl>tmp_lvl) {
				    				remove = true;
				    			}
				    		}
		    				else {
		    					cs=">";
		    					if(user_level.startsWith(cs)) {
		    		    			user_level = user_level.substring(1,user_level.length());
		    		    			long usr_lvl = Long.valueOf(user_level).longValue();
		    		    			if(usr_lvl>=tmp_lvl) {
		    		    				remove = true;
		    		    			}
		    		    		}
		    					else {
		    						cs="<";
		    						if(user_level.startsWith(cs)) {
		    			    			user_level = user_level.substring(1,user_level.length());
		    			    			long usr_lvl = Long.valueOf(user_level).longValue();
		    			    			if(usr_lvl<=tmp_lvl) {
		    			    				remove = true;
		    			    			}
		    			    		}
		    					}
		    				}
		    			}
		    		}
				}
				if(remove) {
					li_obj.remove();
				}
				else {
					if(!match) {
						li_obj.remove();
					}	
				}
			}
    	}
    	else {
    		//proses 1 token
    		String cs="=";
    		if(token_scope.startsWith(cs)) {
    			String user_level = token_scope.substring(1,token_scope.length());
    			long usr_lvl = Long.valueOf(user_level).longValue();
    			li_obj = v_obj.listIterator();
    			while(li_obj.hasNext()) {
    				String id_lvl = (String)li_obj.next();
    				StringTokenizer st1 = new StringTokenizer(id_lvl);
    				String id = st1.nextToken();
    				String kdpst = st1.nextToken();
    				String obj_dsc = st1.nextToken();
    				String lvl = st1.nextToken();
    				long tmp_lvl = Long.valueOf(lvl).longValue();
    				if(usr_lvl!=tmp_lvl) {
    					li_obj.remove();
    				}
    			}
    		}
    		else {
    			cs="<=";
    			if(token_scope.startsWith(cs)) {
        			String user_level = token_scope.substring(2,token_scope.length());
        			long usr_lvl = Long.valueOf(user_level).longValue();
        			li_obj = v_obj.listIterator();
        			while(li_obj.hasNext()) {
        				String id_lvl = (String)li_obj.next();
        				StringTokenizer st1 = new StringTokenizer(id_lvl);
        				String id = st1.nextToken();
        				String kdpst = st1.nextToken();
        				String obj_dsc = st1.nextToken();
        				String lvl = st1.nextToken();
        				long tmp_lvl = Long.valueOf(lvl).longValue();
        				if(usr_lvl<tmp_lvl) {
        					li_obj.remove();
        				}
        			}
    			}
    			else {
    				cs=">=";
    				if(token_scope.startsWith(cs)) {
    					////System.out.println("masuk >=");
    	    			String user_level = token_scope.substring(2,token_scope.length());
    	    			long usr_lvl = Long.valueOf(user_level).longValue();
    	    			li_obj = v_obj.listIterator();
    	    			while(li_obj.hasNext()) {
    	    				String id_lvl = (String)li_obj.next();
    	    				StringTokenizer st1 = new StringTokenizer(id_lvl);
    	    				String id = st1.nextToken();
    	    				String kdpst = st1.nextToken();
    	    				String obj_dsc = st1.nextToken();
    	    				String lvl = st1.nextToken();
    	    				long tmp_lvl = Long.valueOf(lvl).longValue();
    	    				////System.out.println(usr_lvl+"<"+tmp_lvl);
    	    				if(usr_lvl>tmp_lvl) {
    	    					li_obj.remove();
    	    					////System.out.println("remove");
    	    				}
    	    			}
    				}
    				else {
    					cs=">";
    					if(token_scope.startsWith(cs)) {
        	    			String user_level = token_scope.substring(1,token_scope.length());
        	    			long usr_lvl = Long.valueOf(user_level).longValue();
        	    			li_obj = v_obj.listIterator();
        	    			while(li_obj.hasNext()) {
        	    				String id_lvl = (String)li_obj.next();
        	    				StringTokenizer st1 = new StringTokenizer(id_lvl);
        	    				String id = st1.nextToken();
        	    				String kdpst = st1.nextToken();
        	    				String obj_dsc = st1.nextToken();
        	    				String lvl = st1.nextToken();
        	    				long tmp_lvl = Long.valueOf(lvl).longValue();
        	    				if(usr_lvl>=tmp_lvl) {
        	    					li_obj.remove();
        	    				}
        	    			}
        				}
    					else {
    						cs="<";
    						if(token_scope.startsWith(cs)) {
    	    	    			String user_level = token_scope.substring(1,token_scope.length());
    	    	    			long usr_lvl = Long.valueOf(user_level).longValue();
    	    	    			li_obj = v_obj.listIterator();
    	    	    			while(li_obj.hasNext()) {
    	    	    				String id_lvl = (String)li_obj.next();
    	    	    				StringTokenizer st1 = new StringTokenizer(id_lvl);
    	    	    				String id = st1.nextToken();
    	    	    				String kdpst = st1.nextToken();
    	    	    				String obj_dsc = st1.nextToken();
    	    	    				String lvl = st1.nextToken();
    	    	    				long tmp_lvl = Long.valueOf(lvl).longValue();
    	    	    				if(usr_lvl<=tmp_lvl) {
    	    	    					li_obj.remove();
    	    	    				}
    	    	    			}
    	    				}
    						else {
    							//updated 
    							if(token_scope.equalsIgnoreCase("own")) {
    								
        	    	    			if(convert_own_to_info_prodi) {
        	    	    				//112 65001 MHS_S3_ILMU_PEMERINTAHAN 112 A JST
        	    	    				while(li_obj.hasNext()) {
            	    	    				String id_lvl = (String)li_obj.next();
            	    	    				StringTokenizer st1 = new StringTokenizer(id_lvl);
            	    	    				String id = st1.nextToken();
            	    	    				String kdpst = st1.nextToken();
            	    	    				String obj_dsc = st1.nextToken();
            	    	    				String lvl = st1.nextToken();
            	    	    				long tmp_lvl = Long.valueOf(lvl).longValue();
            	    	    				if(!(""+this.idObj).equalsIgnoreCase(id)) {
            	    	    					li_obj.remove();
            	    	    				}
            	    	    			}
        	    	    			}
        	    	    			else if(convert_own_to_npm) {
        	    	    				v_obj = new Vector();
            	    	    			li_obj = v_obj.listIterator();
        	    	    				li_obj.add(this.npm);
        	    	    			}
        	    	    			else { //keep own
        	    	    				v_obj = new Vector();
            	    	    			li_obj = v_obj.listIterator();
        	    	    				li_obj.add("own");
        	    	    			}	
        	    	    			
    							}
    							else if(token_scope.equalsIgnoreCase("yes")) {
    								/*
    								li_obj = v_obj.listIterator();
        	    	    			while(li_obj.hasNext()) {
        	    	    				String id_lvl = (String)li_obj.next();
        	    	    				StringTokenizer st1 = new StringTokenizer(id_lvl);
        	    	    				String id = st1.nextToken();
        	    	    				String kdpst = st1.nextToken();
        	    	    				String obj_dsc = st1.nextToken();
        	    	    				String lvl = st1.nextToken();
        	    	    				long tmp_lvl = Long.valueOf(lvl).longValue();
        	    	    				
        	    	    				if(!(""+this.getIdObj()).equalsIgnoreCase(id)) {
        	    	    					li_obj.remove();
        	    	    				}
        	    	    			}
        	    	    			*/
    								v_obj = new Vector();
        	    	    			li_obj = v_obj.listIterator();
        	    	    			li_obj.add("yes");
        	    				}
    						}
    					}
    				}
    			}
    		}
    	}
    	//li_eq = v_eq.listIterator();
    	//while(li_eq.hasNext()) {
    	//	li_obj.add((String) li_eq.next());
    	//}
    	//Collections.sort(v_obj);
    	return v_obj;
    }
    
    /*
     * DEPRECATED : PAKE YANG VERSI 3
     * FUNGSI INI KALO SCOPE VALUENYA YES MAKA ALL SCOPE
     */
    public Vector getListObjectScope_ver2(String token_scope) {
    	//first get list obj level dari tabel object
    	Vector v_obj = new Vector();
    	ListIterator li_obj = v_obj.listIterator();
    	try {
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
			//envContext.close();
    		//initContext.close();
			con = ds.getConnection(); 
			stmt = con.prepareStatement("select * from OBJECT order by OBJ_DESC");
			rs = stmt.executeQuery();
			while(rs.next()) {
				long id_obj = rs.getLong("ID_OBJ");
				String kdpst = rs.getString("KDPST");
				String obj_dsc = rs.getString("OBJ_DESC");
				obj_dsc = obj_dsc.replaceAll(" ", "_");
				long obj_level = rs.getLong("OBJ_LEVEL");
				String kode_kampus = rs.getString("KODE_KAMPUS_DOMISILI");
				li_obj.add(id_obj+" "+kdpst+" "+obj_dsc+" "+obj_level+" "+kode_kampus);
			}
			
			stmt = con.prepareStatement("select * from MSPST where KDPSTMSPST=?");
			li_obj = v_obj.listIterator();
			while(li_obj.hasNext()) {
				String brs = (String)li_obj.next();
				StringTokenizer st = new StringTokenizer(brs);
				String id_obj = st.nextToken();
				String kdpst = st.nextToken();
				String obj_dsc = st.nextToken();
				String obj_level = st.nextToken();
				String kode_kampus = st.nextToken();
				stmt.setString(1, kdpst);
				rs = stmt.executeQuery();
				String kdjen = null;
				if(rs.next()) {
					kdjen = rs.getString("KDJENMSPST");
				}
				else {
					kdjen = "null";
				}
				li_obj.set(id_obj+" "+kdpst+" "+obj_dsc+" "+obj_level+" "+kdjen+" "+kode_kampus);
				//li_obj.set(brs+" "+kdjen);
			}
			rs.close();
			stmt.close();
			con.close();
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
    	
    	StringTokenizer st = new StringTokenizer(token_scope,",");
    	//cek apa 1 token ato beberapa
    	Vector v_eq = new Vector();
    	ListIterator li_eq = v_eq.listIterator();
    	if(st.countTokens()>1) {
    		li_obj = v_obj.listIterator();
			while(li_obj.hasNext()) {
				String id_lvl = (String)li_obj.next();
				StringTokenizer st1 = new StringTokenizer(id_lvl);
				String id = st1.nextToken();
				String kdpst = st1.nextToken();
				String obj_dsc = st1.nextToken();
				String lvl = st1.nextToken();
				long tmp_lvl = Long.valueOf(lvl).longValue();
				boolean remove = false;
				boolean match = false;
				st = new StringTokenizer(token_scope,",");
				while(st.hasMoreTokens() && !remove) {
					String user_level = (String)st.nextToken();
					String cs="=";
		    		if(user_level.startsWith(cs)) {
		    			////System.out.println("disini");
		    			user_level = user_level.substring(1,user_level.length());
		    			//li_eq.add(""+user_level);
		    			long usr_lvl = Long.valueOf(user_level).longValue();
		    			if(usr_lvl==tmp_lvl) {
		    				match = true;
		    			}
		    		}
		    		else {
		    			cs="<=";
		    			if(user_level.startsWith(cs)) {
			    			user_level = user_level.substring(2,user_level.length());
			    			long usr_lvl = Long.valueOf(user_level).longValue();
			    			if(usr_lvl<tmp_lvl) {
			    				remove = true;
			    			}
			    		}
		    			else {
		    				cs=">=";
		    				if(user_level.startsWith(cs)) {
				    			user_level = user_level.substring(2,user_level.length());
				    			long usr_lvl = Long.valueOf(user_level).longValue();
				    			if(usr_lvl>tmp_lvl) {
				    				remove = true;
				    			}
				    		}
		    				else {
		    					cs=">";
		    					if(user_level.startsWith(cs)) {
		    		    			user_level = user_level.substring(1,user_level.length());
		    		    			long usr_lvl = Long.valueOf(user_level).longValue();
		    		    			if(usr_lvl>=tmp_lvl) {
		    		    				remove = true;
		    		    			}
		    		    		}
		    					else {
		    						cs="<";
		    						if(user_level.startsWith(cs)) {
		    			    			user_level = user_level.substring(1,user_level.length());
		    			    			long usr_lvl = Long.valueOf(user_level).longValue();
		    			    			if(usr_lvl<=tmp_lvl) {
		    			    				remove = true;
		    			    			}
		    			    		}
		    					}
		    				}
		    			}
		    		}
				}
				if(remove) {
					li_obj.remove();
				}
				else {
					if(!match) {
						li_obj.remove();
					}	
				}
			}
    	}
    	else {
    		//proses 1 token
    		String cs="=";
    		if(token_scope.startsWith(cs)) {
    			String user_level = token_scope.substring(1,token_scope.length());
    			long usr_lvl = Long.valueOf(user_level).longValue();
    			li_obj = v_obj.listIterator();
    			while(li_obj.hasNext()) {
    				String id_lvl = (String)li_obj.next();
    				StringTokenizer st1 = new StringTokenizer(id_lvl);
    				String id = st1.nextToken();
    				String kdpst = st1.nextToken();
    				String obj_dsc = st1.nextToken();
    				String lvl = st1.nextToken();
    				long tmp_lvl = Long.valueOf(lvl).longValue();
    				if(usr_lvl!=tmp_lvl) {
    					li_obj.remove();
    				}
    			}
    		}
    		else {
    			cs="<=";
    			if(token_scope.startsWith(cs)) {
        			String user_level = token_scope.substring(2,token_scope.length());
        			long usr_lvl = Long.valueOf(user_level).longValue();
        			li_obj = v_obj.listIterator();
        			while(li_obj.hasNext()) {
        				String id_lvl = (String)li_obj.next();
        				StringTokenizer st1 = new StringTokenizer(id_lvl);
        				String id = st1.nextToken();
        				String kdpst = st1.nextToken();
        				String obj_dsc = st1.nextToken();
        				String lvl = st1.nextToken();
        				long tmp_lvl = Long.valueOf(lvl).longValue();
        				if(usr_lvl<tmp_lvl) {
        					li_obj.remove();
        				}
        			}
    			}
    			else {
    				cs=">=";
    				if(token_scope.startsWith(cs)) {
    					////System.out.println("masuk >=");
    	    			String user_level = token_scope.substring(2,token_scope.length());
    	    			long usr_lvl = Long.valueOf(user_level).longValue();
    	    			li_obj = v_obj.listIterator();
    	    			while(li_obj.hasNext()) {
    	    				String id_lvl = (String)li_obj.next();
    	    				StringTokenizer st1 = new StringTokenizer(id_lvl);
    	    				String id = st1.nextToken();
    	    				String kdpst = st1.nextToken();
    	    				String obj_dsc = st1.nextToken();
    	    				String lvl = st1.nextToken();
    	    				long tmp_lvl = Long.valueOf(lvl).longValue();
    	    				////System.out.println(usr_lvl+"<"+tmp_lvl);
    	    				if(usr_lvl>tmp_lvl) {
    	    					li_obj.remove();
    	    					////System.out.println("remove");
    	    				}
    	    			}
    				}
    				else {
    					cs=">";
    					if(token_scope.startsWith(cs)) {
        	    			String user_level = token_scope.substring(1,token_scope.length());
        	    			long usr_lvl = Long.valueOf(user_level).longValue();
        	    			li_obj = v_obj.listIterator();
        	    			while(li_obj.hasNext()) {
        	    				String id_lvl = (String)li_obj.next();
        	    				StringTokenizer st1 = new StringTokenizer(id_lvl);
        	    				String id = st1.nextToken();
        	    				String kdpst = st1.nextToken();
        	    				String obj_dsc = st1.nextToken();
        	    				String lvl = st1.nextToken();
        	    				long tmp_lvl = Long.valueOf(lvl).longValue();
        	    				if(usr_lvl>=tmp_lvl) {
        	    					li_obj.remove();
        	    				}
        	    			}
        				}
    					else {
    						cs="<";
    						if(token_scope.startsWith(cs)) {
    	    	    			String user_level = token_scope.substring(1,token_scope.length());
    	    	    			long usr_lvl = Long.valueOf(user_level).longValue();
    	    	    			li_obj = v_obj.listIterator();
    	    	    			while(li_obj.hasNext()) {
    	    	    				String id_lvl = (String)li_obj.next();
    	    	    				StringTokenizer st1 = new StringTokenizer(id_lvl);
    	    	    				String id = st1.nextToken();
    	    	    				String kdpst = st1.nextToken();
    	    	    				String obj_dsc = st1.nextToken();
    	    	    				String lvl = st1.nextToken();
    	    	    				long tmp_lvl = Long.valueOf(lvl).longValue();
    	    	    				if(usr_lvl<=tmp_lvl) {
    	    	    					li_obj.remove();
    	    	    				}
    	    	    			}
    	    				}
    						else {
    							//tambahan 12/10/2012
    							//kalo own berarti hanaya return npm ma kdpst
    							cs="own";
    							////System.out.println("token scope vs own = "+token_scope);
    							if(token_scope.startsWith(cs)) {
        	    	    			v_obj = new Vector();
        	    	    			li_obj = v_obj.listIterator();
        	    	    			li_obj.add(this.npm);
        	    				}
    						}
    					}
    				}
    			}
    		}
    	}
    	//li_eq = v_eq.listIterator();
    	//while(li_eq.hasNext()) {
    	//	li_obj.add((String) li_eq.next());
    	//}
    	//Collections.sort(v_obj);
    	return v_obj;
    }

    

    /*
     * jangan pake fungsi ini
     * kalo untuk fakuktas getScopeFakultas
     */
    public Vector getScopeUpd7des2012(String command_code) {
    	//return null kalo ngga ada hak akses
    	//bila scope = own, maka return no usr NPM (1 token only) yg lainnya multiple token
    	//bila 
    	Vector v_list_obj = null;
    	String scope = null;
    	int norut = isAllowTo(command_code);
    	////System.out.println("nourut = "+norut);
    	if(norut>0) {
    		try {

    			String aksesLvlCoditional = ""+this.getAccessLevelConditional();
    	//		if(aksesLvlCoditional!=null && !aksesLvlCoditional.equalsIgnoreCase("null")) {
    			if(aksesLvlCoditional!=null && !Checker.isStringNullOrEmpty(aksesLvlCoditional)) {
    				//if(rs.next()) {
    				String token_akses = ""+aksesLvlCoditional;
    				////System.out.println("akses = "+token_akses);
    				StringTokenizer st = new StringTokenizer(token_akses,"#");
    				for(int i=0;i<norut;i++) {
    					scope = st.nextToken();
    					////System.out.println("isu scope"+i+" = "+scope);
    				}
    				////System.out.println("isu scope = "+scope);
    				//jika scope = own
    				if(scope.contains("own")) {
    					v_list_obj = new Vector();
    					ListIterator liTmp = v_list_obj.listIterator();
    					liTmp.add("own");
    				}
    				else {
    					v_list_obj = getListObjectScope(scope);
    				}	
    				////System.out.println("scopenya = "+scope);
    			}
    		} 
    		catch (Exception e) {
    			e.printStackTrace();
    		}
    	}	
    	return v_list_obj;
    }
    
    //
    public Vector getScopeUpd11Jan2016ProdiOnly(String command_code) {
    	//return null kalo ngga ada hak akses
    	//bila scope = own, maka return no usr NPM (1 token only) yg lainnya multiple token
    	//bila 
    	String[]listKdpstProdi = Constants.getListKdpstProdi();
    	Vector v_list_obj = null;
    	String scope = null;
    	int norut = isAllowTo(command_code);
    	////System.out.println("nourut = "+norut);
    	if(norut>0) {
    		try {

    			String aksesLvlCoditional = ""+this.getAccessLevelConditional();
    	//		if(aksesLvlCoditional!=null && !aksesLvlCoditional.equalsIgnoreCase("null")) {
    			if(aksesLvlCoditional!=null && !Checker.isStringNullOrEmpty(aksesLvlCoditional)) {
    				//if(rs.next()) {
    				String token_akses = ""+aksesLvlCoditional;
    				////System.out.println("akses = "+token_akses);
    				StringTokenizer st = new StringTokenizer(token_akses,"#");
    				for(int i=0;i<norut;i++) {
    					scope = st.nextToken();
    					////System.out.println("isu scope"+i+" = "+scope);
    				}
    				////System.out.println("isu scope = "+scope);
    				//jika scope = own
    				if(scope.contains("own")) {
    					v_list_obj = new Vector();
    					ListIterator liTmp = v_list_obj.listIterator();
    					liTmp.add("own");
    				}
    				else if(scope.contains("yes")) {
    					v_list_obj = new Vector();
    					ListIterator liTmp = v_list_obj.listIterator();
    					liTmp.add("yes");
    				}
    				else {
    					v_list_obj = getListObjectScope(scope);
    					if(v_list_obj!=null && v_list_obj.size()>0) {
    						ListIterator li = v_list_obj.listIterator();
    						while(li.hasNext()) {
    							String brs = (String)li.next();
    							st = new StringTokenizer(brs);
    							st.nextToken();
    							String kdpst = st.nextToken();
    							boolean match = false;
    							for(int i=0;i<listKdpstProdi.length && !match;i++) {
    								if(kdpst.equalsIgnoreCase(listKdpstProdi[i])) {
    									match = true;
    								}
    							}
    							if(!match) {
    								li.remove();
    							}
    						}
    					}
    				}	
    				////System.out.println("scopenya = "+scope);
    			}
    		} 
    		catch (Exception e) {
    			e.printStackTrace();
    		}
    	}	
    	return v_list_obj;
    }
    
    
    public Vector getScopeUpd11Jan2016(String command_code) {
    	//return null kalo ngga ada hak akses
    	//bila scope = own, maka return no usr NPM (1 token only) yg lainnya multiple token
    	//bila 
    	Vector v_list_obj = null;
    	String scope = null;
    	int norut = isAllowTo(command_code);
    	////System.out.println("nourut = "+norut);
    	if(norut>0) {
    		try {

    			String aksesLvlCoditional = ""+this.getAccessLevelConditional();
    	//		if(aksesLvlCoditional!=null && !aksesLvlCoditional.equalsIgnoreCase("null")) {
    			if(aksesLvlCoditional!=null && !Checker.isStringNullOrEmpty(aksesLvlCoditional)) {
    				//if(rs.next()) {
    				String token_akses = ""+aksesLvlCoditional;
    				////System.out.println("akses = "+token_akses);
    				StringTokenizer st = new StringTokenizer(token_akses,"#");
    				for(int i=0;i<norut;i++) {
    					scope = st.nextToken();
    					////System.out.println("isu scope"+i+" = "+scope);
    				}
    				////System.out.println("isu scope = "+scope);
    				//jika scope = own
    				if(scope.contains("own")) {
    					v_list_obj = new Vector();
    					ListIterator liTmp = v_list_obj.listIterator();
    					liTmp.add("own");
    				}
    				else if(scope.contains("yes")) {
    					v_list_obj = new Vector();
    					ListIterator liTmp = v_list_obj.listIterator();
    					liTmp.add("yes");
    				}
    				else {
    					v_list_obj = getListObjectScope(scope);
    				}	
    				////System.out.println("scopenya = "+scope);
    			}
    		} 
    		catch (Exception e) {
    			e.printStackTrace();
    		}
    	}	
    	return v_list_obj;
    }
    


    //deprecated
    //sepertinya ada error, pada saat bikin input nilai..belum di cek kenapanya
    public Vector getScopeUpd7des2012ProdiOnly(String command_code) {
    	//return null kalo ngga ada hak akses
    	//bila scope = own, maka return no usr NPM (1 token only) yg lainnya multiple token
    	//bila 
    	Vector v_list_obj = null;
    	String scope = null;
    	int norut = isAllowTo(command_code);
    	////System.out.println("nourut = "+norut);
    	if(norut>0) {
    		try {

    			String aksesLvlCoditional = ""+this.getAccessLevelConditional();
    	//		if(aksesLvlCoditional!=null && !aksesLvlCoditional.equalsIgnoreCase("null")) {
    			if(aksesLvlCoditional!=null && !Checker.isStringNullOrEmpty(aksesLvlCoditional)) {
    				//if(rs.next()) {
    				String token_akses = ""+aksesLvlCoditional;
    				////System.out.println("akses = "+token_akses);
    				StringTokenizer st = new StringTokenizer(token_akses,"#");
    				for(int i=0;i<norut;i++) {
    					scope = st.nextToken();
    					////System.out.println("isu scope"+i+" = "+scope);
    				}
    				////System.out.println("pass");
    				//jika scope = own
    				if(scope.contains("own")) {
    					v_list_obj = new Vector();
    					ListIterator liTmp = v_list_obj.listIterator();
    					liTmp.add("own");
    				}
    				else {
    					v_list_obj = getListObjectScope(scope);
    				}	
    				//filter prodi only
    				String[]listKdpstProdi = Constants.getListKdpstProdi();
    				if(v_list_obj!=null && v_list_obj.size()>0) {
    					ListIterator liTmp = v_list_obj.listIterator();
    					while(liTmp.hasNext()) {
    						String brs = (String)liTmp.next();
    						st = new StringTokenizer(brs);
    						st.nextToken();
    						String kdpst = st.nextToken();
    						boolean match = false;
    						for(int i=0;i<listKdpstProdi.length && !match;i++) {
    							if(kdpst.equalsIgnoreCase(listKdpstProdi[i])) {
    								match = true;
    							}
    						}
    						if(!match) {
    							liTmp.remove();
    						}
    						////System.out.println("brs1="+brs);
    					}
    				}
    				////System.out.println("scopenya = "+scope);
    			}
    		} 
    		catch (Exception e) {
    			e.printStackTrace();
    		}
    	}	
    	return v_list_obj;
    }

    

    
    public Vector getScopeUpd7des2012ProdiOnly_rev2(String command_code) {
    	//return null kalo ngga ada hak akses
    	//bila scope = own, maka return no usr NPM (1 token only) yg lainnya multiple token
    	//bila 
    	Vector v_list_obj = null;
    	String scope = null;
    	int norut = isAllowTo(command_code);
    	////System.out.println("nourut = "+norut);
    	if(norut>0) {
    		try {

    			String aksesLvlCoditional = ""+this.getAccessLevelConditional();
    	//		if(aksesLvlCoditional!=null && !aksesLvlCoditional.equalsIgnoreCase("null")) {
    			if(aksesLvlCoditional!=null && !Checker.isStringNullOrEmpty(aksesLvlCoditional)) {
    				//if(rs.next()) {
    				String token_akses = ""+aksesLvlCoditional;
    				////System.out.println("akses = "+token_akses);
    				StringTokenizer st = new StringTokenizer(token_akses,"#");
    				for(int i=0;i<norut;i++) {
    					scope = st.nextToken();
    					////System.out.println("isu scope"+i+" = "+scope);
    				}
    				////System.out.println("pass");
    				//jika scope = own
    				if(scope.contains("own")) {
    					v_list_obj = new Vector();
    					ListIterator liTmp = v_list_obj.listIterator();
    					liTmp.add("own");
    				}
    				else {
    					v_list_obj = getListObjectScope(scope);
    					//filter prodi only
        				String[]listKdpstProdi = Constants.getListKdpstProdi();
        				if(v_list_obj!=null && v_list_obj.size()>0) {
        					ListIterator liTmp = v_list_obj.listIterator();
        					while(liTmp.hasNext()) {
        						String brs = (String)liTmp.next();
        						st = new StringTokenizer(brs);
        						st.nextToken();
        						String kdpst = st.nextToken();
        						boolean match = false;
        						for(int i=0;i<listKdpstProdi.length && !match;i++) {
        							if(kdpst.equalsIgnoreCase(listKdpstProdi[i])) {
        								match = true;
        							}
        						}
        						if(!match) {
        							liTmp.remove();
        						}
        						////System.out.println("brs1="+brs);
        					}
        				}
    				}	
    				
    				////System.out.println("scopenya = "+scope);
    			}
    		} 
    		catch (Exception e) {
    			e.printStackTrace();
    		}
    	}	
    	return v_list_obj;
    }
    
    public Vector getScopeUpd7des2012ProdiOnlyButKeepOwn(String command_code) {
    	//return null kalo ngga ada hak akses
    	//bila scope = own, maka return no usr NPM (1 token only) yg lainnya multiple token
    	//bila 
    	Vector v_list_obj = null;
    	String scope = null;
    	int norut = isAllowTo(command_code);
    	////System.out.println("nourut = "+norut);
    	if(norut>0) {
    		try {

    			String aksesLvlCoditional = ""+this.getAccessLevelConditional();
    	//		if(aksesLvlCoditional!=null && !aksesLvlCoditional.equalsIgnoreCase("null")) {
    			if(aksesLvlCoditional!=null && !Checker.isStringNullOrEmpty(aksesLvlCoditional)) {
    				//if(rs.next()) {
    				String token_akses = ""+aksesLvlCoditional;
    				////System.out.println("akses = "+token_akses);
    				StringTokenizer st = new StringTokenizer(token_akses,"#");
    				for(int i=0;i<norut;i++) {
    					scope = st.nextToken();
    					////System.out.println("isu scope"+i+" = "+scope);
    				}
    				////System.out.println("pass");
    				//jika scope = own
    				if(scope.contains("own")) {
    					v_list_obj = new Vector();
    					ListIterator liTmp = v_list_obj.listIterator();
    					liTmp.add("own");
    				}
    				else {
    					v_list_obj = getListObjectScope(scope);
    					//filter prodi only
        				String[]listKdpstProdi = Constants.getListKdpstProdi();
        				if(v_list_obj!=null && v_list_obj.size()>0) {
        					ListIterator liTmp = v_list_obj.listIterator();
        					while(liTmp.hasNext()) {
        						String brs = (String)liTmp.next();
        						////System.out.println("brs prodi="+brs);
        						st = new StringTokenizer(brs);
        						st.nextToken();
        						String kdpst = st.nextToken();
        						boolean match = false;
        						for(int i=0;i<listKdpstProdi.length && !match;i++) {
        							if(kdpst.equalsIgnoreCase(listKdpstProdi[i])) {
        								match = true;
        							}
        						}
        						if(!match && !kdpst.equalsIgnoreCase("own")) {
        							liTmp.remove();
        						}
        						////System.out.println("brs1="+brs);
        					}
        				}
    				}	
    				
    				////System.out.println("scopenya = "+scope);
    			}
    		} 
    		catch (Exception e) {
    			e.printStackTrace();
    		}
    	}	
    	return v_list_obj;
    }
    
    public Vector getScopeUpd7des2012ProdiOnly(String command_code, String target_kampus) {
    	//return null kalo ngga ada hak akses
    	//bila scope = own, maka return no usr NPM (1 token only) yg lainnya multiple token
    	//bila 
    	Vector v_list_obj = null;
    	String scope = null;
    	int norut = isAllowTo(command_code);
    	////System.out.println("nourut = "+norut);
    	if(norut>0) {
    		try {

    			String aksesLvlCoditional = ""+this.getAccessLevelConditional();
    	//		if(aksesLvlCoditional!=null && !aksesLvlCoditional.equalsIgnoreCase("null")) {
    			if(aksesLvlCoditional!=null && !Checker.isStringNullOrEmpty(aksesLvlCoditional)) {
    				//if(rs.next()) {
    				String token_akses = ""+aksesLvlCoditional;
    				////System.out.println("akses = "+token_akses);
    				StringTokenizer st = new StringTokenizer(token_akses,"#");
    				for(int i=0;i<norut;i++) {
    					scope = st.nextToken();
    					////System.out.println("isu scope"+i+" = "+scope);
    				}
    				////System.out.println("pass");
    				//jika scope = own
    				if(scope.contains("own")) {
    					v_list_obj = new Vector();
    					ListIterator liTmp = v_list_obj.listIterator();
    					liTmp.add("own");
    				}
    				else {
    					v_list_obj = getListObjectScope(scope);
    				}	
    				//filter prodi only
    				String[]listKdpstProdi = Constants.getListKdpstProdi();
    				if(v_list_obj!=null && v_list_obj.size()>0) {
    					ListIterator liTmp = v_list_obj.listIterator();
    					while(liTmp.hasNext()) {
    						String brs = (String)liTmp.next();
    						st = new StringTokenizer(brs);
    						st.nextToken();
    						String kdpst = st.nextToken();
    						boolean match = false;
    						for(int i=0;i<listKdpstProdi.length && !match;i++) {
    							if(kdpst.equalsIgnoreCase(listKdpstProdi[i])) {
    								match = true;
    							}
    						}
    						if(!match) {
    							liTmp.remove();
    						}
    						////System.out.println("brs1="+brs);
    					}
    				}
    				////System.out.println("scopenya = "+scope);
    			}
    		} 
    		catch (Exception e) {
    			e.printStackTrace();
    		}
    		//filter only kampus target
    		if(v_list_obj!=null && v_list_obj.size()>0) {
    			try {
        			Context initContext  = new InitialContext();
        			Context envContext  = (Context)initContext.lookup("java:/comp/env");
        			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
        			//envContext.close();
            		//initContext.close();
        			con = ds.getConnection(); 
        			stmt = con.prepareStatement("select KODE_KAMPUS_DOMISILI FROM OBJECT WHERE ID_OBJ=?");
        			ListIterator li = v_list_obj.listIterator();
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				//136 88888 MHS_AGAMA_KAMPUS_JAMSOSTEK 136 C
        				StringTokenizer st = new StringTokenizer(brs);
        				String idObj = st.nextToken();
        				stmt.setLong(1, Long.parseLong(idObj));
        				rs = stmt.executeQuery();
        				String kampus_domisili = null;
        				if(rs.next()) {
        					kampus_domisili = rs.getString("KODE_KAMPUS_DOMISILI");
        					
        				}
        				if(kampus_domisili!=null && !kampus_domisili.equalsIgnoreCase(target_kampus)) {
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
    		
    		
    		
    	}	
    	return v_list_obj;
    }

    /*
     * depricated pake yg keep own
     */
    public Vector getScopeUpd7des2012ReturnDistinctKdpst(String command_code) {
    	//return null kalo ngga ada hak akses
    	//bila scope = own, maka return no usr NPM (1 token only) yg lainnya multiple token
    	//bila 
    	Vector vKdpst = new Vector();
    	ListIterator lipst = vKdpst.listIterator();
    	Vector v_list_obj = null;
    	String scope = null;
    	int norut = isAllowTo(command_code);
    	////System.out.println("nourut = "+norut);
    	if(norut>0) {
    		try {

    			String aksesLvlCoditional = ""+this.getAccessLevelConditional();
    	//		if(aksesLvlCoditional!=null && !aksesLvlCoditional.equalsIgnoreCase("null")) {
    			if(aksesLvlCoditional!=null && !Checker.isStringNullOrEmpty(aksesLvlCoditional)) {
    				//if(rs.next()) {
    				String token_akses = ""+aksesLvlCoditional;
    				////System.out.println("akses = "+token_akses);
    				StringTokenizer st = new StringTokenizer(token_akses,"#");
    				for(int i=0;i<norut;i++) {
    					scope = st.nextToken();
    					////System.out.println("isu scope"+i+" = "+scope);
    				}
    				//jika scope = own
    				if(scope.contains("own")) {
    					v_list_obj = new Vector();
    					ListIterator liTmp = v_list_obj.listIterator();
    					liTmp.add("own");
    				}
    				else {
    					v_list_obj = getListObjectScope(scope);
    				}	
    				////System.out.println("scopenya = "+scope);
    			}
    			if(v_list_obj!=null) {
    	    		ListIterator liTmp = v_list_obj.listIterator();
    	    		while(liTmp.hasNext()) {
    	    			String brs = (String)liTmp.next();
    	    			StringTokenizer st = new StringTokenizer(brs);
    	    			st.nextToken();
    	    			lipst.add(st.nextToken());
    	    		}
    	    	}
    	    	vKdpst = Tool.removeDuplicateFromVector(vKdpst);
    		} 
    		catch (Exception e) {
    			e.printStackTrace();
    		}
    	}	
    	
    	return vKdpst;
    }

    
    public Vector getScopeFakultas(String command_code) {
    	//pengembangan getScopeUpd7des2012
    	//return null kalo ngga ada hak akses
    	//bila scope = own, maka return no usr NPM (1 token only) yg lainnya multiple token
    	//
    	//exldue non fakultas = struk & lain
    	
    	Vector v_list_obj = null;
    	String scope = null;
    	int norut = isAllowTo(command_code);
    	////System.out.println("nourut = "+norut);
    	if(norut>0) {
    		try {

    			String aksesLvlCoditional = ""+this.getAccessLevelConditional();
    	//		if(aksesLvlCoditional!=null && !aksesLvlCoditional.equalsIgnoreCase("null")) {
    			if(aksesLvlCoditional!=null && !Checker.isStringNullOrEmpty(aksesLvlCoditional)) {
    				//if(rs.next()) {
    				String token_akses = ""+aksesLvlCoditional;
    				////System.out.println("akses = "+token_akses);
    				StringTokenizer st = new StringTokenizer(token_akses,"#");
    				for(int i=0;i<norut;i++) {
    					scope = st.nextToken();
    					////System.out.println("scope"+i+" = "+scope);
    				}
    				v_list_obj = getListObjectScope(scope);
    				////System.out.println("scopenya = "+scope);
    			}
    			
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
    			//envContext.close();
        		//initContext.close();
    			con = ds.getConnection(); 
    			stmt = con.prepareStatement("select * from MSPST where KDPSTMSPST=?");
    			
    			ListIterator li = v_list_obj.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs);
        			String id_obj = st.nextToken();
        			String kdpst = st.nextToken();
        			String obj_dsc = st.nextToken();
        			String obj_lvl = st.nextToken();
        			stmt.setString(1,kdpst);
        			rs=stmt.executeQuery();
        			rs.next();
        			String kdfak = ""+rs.getString("KDFAKMSPST");
        			//cek apakah kdfak prodi
        			String []listKdfakNonProdi = Constants.getListKdfakNonProdi();
        			boolean match = false;
        			for(int i=0;i<listKdfakNonProdi.length && !match;i++ ) {
        				if(kdfak.equalsIgnoreCase(listKdfakNonProdi[i])) {
        					match = true;
        				}
        			}
        			//if(kdfak.equalsIgnoreCase("STRUK")||kdfak.equalsIgnoreCase("LAIN")) {
        			if(match) {
        				li.remove();
        			}
        			else {
        				li.set(kdfak+"#&"+id_obj+"#&"+kdpst+"#&"+obj_dsc+"#&"+obj_lvl);
        				////System.out.println(">>"+kdfak);
        			}	
        		}
        		Collections.sort(v_list_obj);
    		} 
    		catch (Exception e) {
    			e.printStackTrace();
    		}
    		
    	}	
    	return v_list_obj;
    }


    public Vector getScopeObjNickname(String command_code) {
    	//pengembangan getScopeUpd7des2012
    	//return null kalo ngga ada hak akses
    	//bila scope = own, maka return no usr NPM (1 token only) yg lainnya multiple token
    	//
    	//exldue non fakultas = struk & lain
    	Vector vListNickname = new Vector();
    	Vector v_list_obj = null;
    	String scope = null;
    	int norut = isAllowTo(command_code);
    	////System.out.println("nourut = "+norut);
    	if(norut>0) {
    		try {

    			String aksesLvlCoditional = ""+this.getAccessLevelConditional();
    	//		if(aksesLvlCoditional!=null && !aksesLvlCoditional.equalsIgnoreCase("null")) {
    			if(aksesLvlCoditional!=null && !Checker.isStringNullOrEmpty(aksesLvlCoditional)) {
    				//if(rs.next()) {
    				String token_akses = ""+aksesLvlCoditional;
    				////System.out.println("akses = "+token_akses);
    				StringTokenizer st = new StringTokenizer(token_akses,"#");
    				for(int i=0;i<norut;i++) {
    					scope = st.nextToken();
    					////System.out.println("scope"+i+" = "+scope);
    				}
    				v_list_obj = getListObjectScope(scope);
    				////System.out.println("scopenya = "+scope);
    			}
    			
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
    			//envContext.close();
        		//initContext.close();
    			con = ds.getConnection(); 
    			stmt = con.prepareStatement("select * from OBJECT where ID_OBJ=?");
    			
    			ListIterator li = v_list_obj.listIterator();
    			ListIterator li1 = vListNickname.listIterator();
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			////System.out.println("baris-- = "+brs);
        			StringTokenizer st = new StringTokenizer(brs);
        			if(st.countTokens()>1) {
        				String id_obj = st.nextToken();
            			String kdpst = st.nextToken();
            			String obj_dsc = st.nextToken();
            			String obj_lvl = st.nextToken();
            			stmt.setLong(1,Long.valueOf(id_obj).longValue());
            			rs=stmt.executeQuery();
            			rs.next();
            			String nickname = ""+rs.getString("OBJ_NICKNAME");
            			st = new StringTokenizer(nickname,",");
            			while(st.hasMoreTokens()) {
            				li1.add(st.nextToken());
            			}	
        			}
        			else {
        			//only one token - npm 	
        			}
        		}
        		Collections.sort(vListNickname);
        		vListNickname=Tool.removeDuplicateFromVector(vListNickname);
    		} 
    		catch (Exception e) {
    			e.printStackTrace();
    		}
    		
    	}	
    	return vListNickname;
    }    
    
    /*
     * deprecated
     */
    public Vector getScope(String command_code) {
    	//return null kalo ngga ada hak akses
    	Vector v_list_obj = null;
    	String scope = null;
    	int norut = isAllowTo(command_code);
    	////System.out.println("nourut = "+norut);
    	if(norut>0) {
    		try {
    			 //Class.forName("com.mysql.jdbc.Driver").newInstance();
    			 //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/USG?user=root&password=b1sm1llah");
    			 //stmt = con.createStatement();
    			 //String s = "Select * from user.login";
    			 //rs = state.executeQuery(s);
    			
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
    			//envContext.close();
        		//initContext.close();
    			con = ds.getConnection();
   			//get id_obj
    			stmt = con.prepareStatement("select * from CIVITAS where npmhsmsmhs=?");
    			stmt.setString(1,this.npm);	
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				long id_obj = rs.getLong("ID_OBJ");
    				stmt = con.prepareStatement("select * from OBJECT where ID_OBJ=?");
    				stmt.setLong(1, id_obj);
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					String token_akses = rs.getString("ACCESS_LEVEL_CONDITIONAL");
    					////System.out.println("akses = "+token_akses);
    					StringTokenizer st = new StringTokenizer(token_akses,"#");
    					for(int i=0;i<norut;i++) {
    						scope = st.nextToken();
    					}
    					//proses token scope jadi vector scope
    					////System.out.println("scope = "+scope);
    					v_list_obj = getListObjectScope(scope);
    					//ListIterator li = v.listIterator();;
    					//while(li.hasNext()) {
    					//	String baris = (String)li.next();
    					//	////System.out.println(baris);
    					//}
    				}
    			}
    			rs.close();
    			stmt.close();
    			con.close();
    			//ds.close();
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
    			if (con!=null) {
    				try {
    					rs.close();
    	    			stmt.close();
    	    			con.close();
    				}
    				catch (Exception ignore) {
    					////System.out.println("aneh deh");
    				}
    			}
    		}
    	}	
    	return v_list_obj;
    }
    //return 1 tkn = objid
    /*
     * depricated ; kenapa return cuma 1 kenapa ngga semua??
     */
    public Vector getScopeObjIdProdiOnly(String command_code) {
    	//return null kalo ngga ada hak akses
    	Vector v_list_obj = null;
    	String scope = null;
    	int norut = isAllowTo(command_code);
    	if(norut>0) {
    		try {
    			     			
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
    			con = ds.getConnection();
   			//get id_obj
    			stmt = con.prepareStatement("select * from CIVITAS where npmhsmsmhs=?");
    			stmt.setString(1,this.npm);	
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				long id_obj = rs.getLong("ID_OBJ");
    				stmt = con.prepareStatement("select * from OBJECT where ID_OBJ=?");
    				stmt.setLong(1, id_obj);
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					String token_akses = rs.getString("ACCESS_LEVEL_CONDITIONAL");
    					StringTokenizer st = new StringTokenizer(token_akses,"#");
    					for(int i=0;i<norut;i++) {
    						scope = st.nextToken();
    					}
    					v_list_obj = getListObjectScope(scope);
    					if(v_list_obj!=null && v_list_obj.size()>0) {
    						ListIterator liv = v_list_obj.listIterator();
    						while(liv.hasNext()) {
    							String tmp = (String) liv.next();
    							st = new StringTokenizer(tmp);
    							String objId = st.nextToken();
    							if(!tmp.contains("MHS") && !tmp.contains("mhs")) {
    								liv.remove();
    							}
    							else {
    								liv.set(objId);
    							}
    						}
    						
    					}
    				}
    			}
    			rs.close();
    			stmt.close();
    			con.close();
    			//ds.close();
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
    			if (con!=null) {
    				try {
    					rs.close();
    	    			stmt.close();
    	    			con.close();
    				}
    				catch (Exception ignore) {
    					////System.out.println("aneh deh");
    				}
    			}
    		}
    	}	
    	return v_list_obj;
    }
    
    
    
    public int isAllowTo(String command_code) {
    	/*
    	 * return 0 - no search access
    	 * return >0 = no urut token access-level-conditional yg boleh di akses
    	 */
    	StringTokenizer st = new StringTokenizer(accessLevel,"#");
    	boolean match = false;
    	int norut = 0;
    	while(st.hasMoreTokens() && !match) {
    		norut++;
    		String tkn = st.nextToken();
    		if(tkn.equalsIgnoreCase(command_code)) {
    			match = true;
    		}
    	}
    	if(!match) {
    		norut=0;
    	}
    	return norut;
    }
    
    public String returnValueIsAllowTo(String command_code) {
    	/*
    	 * return 0 - no search access
    	 * return >0 = no urut token access-level-conditional yg boleh di akses
    	 */
    	StringTokenizer st = new StringTokenizer(accessLevel,"#");
    	boolean match = false;
    	int norut = 0;
    	String value = null;
    	while(st.hasMoreTokens() && !match) {
    		norut++;
    		String tkn = st.nextToken();
    		if(tkn.equalsIgnoreCase(command_code)) {
    			match = true;
    			value = tkn;
    		}
    	}
    	if(!match) {
    		norut=0;
    	}
    	return value;
    }
    

    public Vector searchCivitas(int norut,String kword) {
    	boolean number = true;
    	String akses_cmd = this.getAccessLevel();
		boolean allowSearchDosen = false;
		if(akses_cmd.contains("allowSrcDosen")) {
			allowSearchDosen = true;
		}
		////System.out.println("allowSearchDosen="+allowSearchDosen);
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	rs=null;
   		StringTokenizer st = new StringTokenizer(accessLevelConditional,"#");
   		String token_conditional="";
   		for(int i=0;i<norut;i++) {
   			token_conditional = st.nextToken();
   		}
   	    st = new StringTokenizer(token_conditional,",");
   		String sql_cmd="";
   		sql_cmd="select * from CIVITAS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where (OBJ_LEVEL";
   		//sql_cmd="select * from CIVITAS where (OBJ_LEVEL";
   		while(st.hasMoreTokens()) {
   			String conditional = ""+st.nextToken();
       		sql_cmd=sql_cmd+conditional+" ";
       		if(st.hasMoreTokens()) {
       			sql_cmd=sql_cmd+"OR OBJ_LEVEL";
       		}
   		}
   		////System.out.println("sql_cmd="+sql_cmd);
   		try {
   			long test_key_for_number = Long.valueOf(kword.replaceAll("%", ""));
   			sql_cmd=sql_cmd+") and (NPMHSMSMHS LIKE ? OR NIMHSMSMHS LIKE ?)";
   			
   		}
   		catch(Exception e) {
   			sql_cmd=sql_cmd+") and NMMHSMSMHS LIKE ?";	
   			number=false;
   		}
   		
   		try {
   			Context initContext  = new InitialContext();
   			Context envContext  = (Context)initContext.lookup("java:/comp/env");
   			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
   			//envContext.close();
    		//initContext.close();
   			con = ds.getConnection();
   			stmt = con.prepareStatement(sql_cmd);
   			if(number) {
   				stmt.setString(1, kword);
   				stmt.setString(2, kword);
   			}
   			else {
   				stmt.setString(1,kword);
   			}	
   			rs = stmt.executeQuery();
   			while(rs.next()) {
   				String kdpst = ""+rs.getString("KDPSTMSMHS");
   				////System.out.println("kdpst = "+kdpst);
  				String npm = ""+rs.getString("NPMHSMSMHS");
  				////System.out.println("npm = "+npm);
  				String nim = ""+rs.getString("NIMHSMSMHS");
  				////System.out.println("nim = "+nim);
  				String nmm = ""+rs.getString("NMMHSMSMHS");
  				////System.out.println("nmm = "+nmm);
  				String tplhr = ""+rs.getString("TPLHRMSMHS");
  				////System.out.println("tplhr = "+tplhr);
  				String tglhr = ""+rs.getDate("TGLHRMSMHS");
  				////System.out.println("tglhr = "+tglhr);
  				String stmhs = ""+rs.getString("STMHSMSMHS");
  				////System.out.println("stmhs = "+stmhs);
  				String id_obj = ""+rs.getInt("ID_OBJ");
  				////System.out.println("id_obj = "+id_obj);
  				String obj_lvl = ""+rs.getString("OBJ_LEVEL");
  				////System.out.println("obj_lvl = "+obj_lvl);
  				String obj_desc = ""+rs.getString("OBJ_DESC");
  				////System.out.println("obj_desc = "+obj_desc);
  				li.add(npm);
   				li.add(nim);
   				li.add(kdpst);
   				li.add(nmm);
   				li.add(tplhr);
   				li.add(tglhr);
   				li.add(stmhs);
   				li.add(id_obj);
   				li.add(obj_lvl);
   				li.add(obj_desc);
   			}
   			
   			if(allowSearchDosen) {
   				sql_cmd="select * from EXT_CIVITAS_DATA_DOSEN inner join CIVITAS on NPMHS=NPMHSMSMHS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where NMMHSMSMHS like ? or NPMHSMSMHS like ? or NIMHSMSMHS like ?";
   				//stmt = con.prepareStatement("select * from CIVITAS inner join EXT_CIVITAS_DATA_DOSEN on NPMHSMSMHS=NPMHS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where NMMHSMSMHS like ? or NPMHSMSMHS like ? or NIMHSMSMHS like ?");
   				stmt = con.prepareStatement(sql_cmd);
   				stmt.setString(1,"%"+kword+"%");
   				stmt.setString(2,"%"+kword+"%");
   				stmt.setString(3,"%"+kword+"%");
   				rs = stmt.executeQuery();
   				while(rs.next()) {
   					String kdpst = ""+rs.getString("KDPSTMSMHS");
   	   				String npm = ""+rs.getString("NPMHSMSMHS");
   	  				String nim = ""+rs.getString("NIMHSMSMHS");
   	  				String nmm = ""+rs.getString("NMMHSMSMHS");
   	  				String tplhr = ""+rs.getString("TPLHRMSMHS");
   	  				String tglhr = ""+rs.getDate("TGLHRMSMHS");
   	  				String stmhs = ""+rs.getString("STMHSMSMHS");
   	  				String id_obj = ""+rs.getInt("ID_OBJ");
   	  				String obj_lvl = ""+rs.getString("OBJ_LEVEL");
   	  				String obj_desc = ""+rs.getString("OBJ_DESC");
   	  				li.add(npm);
   	   				li.add(nim);
   	   				li.add(kdpst);
   	   				li.add(nmm);
   	   				li.add(tplhr);
   	   				li.add(tglhr);
   	   				li.add(stmhs);
   	   				li.add(id_obj);
   	   				li.add(obj_lvl);
   	   				li.add(obj_desc);
   				}
   			}
   			rs.close();
			stmt.close();
			con.close();
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
    				rs.close();
        			stmt.close();
        			con.close();
    			}
    			catch (Exception ignore) {
    				////System.out.println(ignore);
    			}
    		}
    	}
   		//try {
   		//	v = Tool.removeDuplicateFromVector(v);	
   		//}
   		//catch(Exception e) {
   		//	e.printStackTrace();
   		//}
   		
    	return v;
    }

    public String filterTokenTargetObjLvlDgnHakAkses(String usr_hak_akses_obj_lvl, String token_target_obj_lvl) {
    	//token_target_obj_lvl = "50,51,48,60"; //always seperated by koma
		//usr_hak_akses_obj_lvl = "<50";//tipe 1 token
		//usr_hak_akses_obj_lvl  = "=50,=48,=60";
		String filtered_objlvl= "";
		//if(usr_hak_akses_obj_lvl.contains("=")||usr_hak_akses_obj_lvl.contains("<")||usr_hak_akses_obj_lvl.contains(">")) {
		if(!usr_hak_akses_obj_lvl.contains(",")) {
			StringTokenizer st = new StringTokenizer(token_target_obj_lvl,",");
			   
			while(st.hasMoreTokens()) {
			   String tmp = "";
			   boolean match = false;
			   String target_objlvl = st.nextToken();
			   ////System.out.println("target_objlvl = "+target_objlvl);
			   if(usr_hak_akses_obj_lvl.startsWith("=")) {
				   tmp=usr_hak_akses_obj_lvl.substring(1,usr_hak_akses_obj_lvl.length());
				   if(Integer.valueOf(tmp).intValue()==Integer.valueOf(target_objlvl).intValue()) {
					   match = true;
				   }
			   }
			   else {
				   if(usr_hak_akses_obj_lvl.startsWith(">=")) {
					   tmp=usr_hak_akses_obj_lvl.substring(2,usr_hak_akses_obj_lvl.length());
					   ////System.out.println(usr_hak_akses_obj_lvl+" - "+target_objlvl);
					   if(Integer.valueOf(tmp).intValue()<=Integer.valueOf(target_objlvl).intValue()) {
						   match = true;
					   }
				   }
				   else {
					   if(usr_hak_akses_obj_lvl.startsWith("<=")) {
						   tmp=usr_hak_akses_obj_lvl.substring(2,usr_hak_akses_obj_lvl.length());
						   if(Integer.valueOf(tmp).intValue()>=Integer.valueOf(target_objlvl).intValue()) {
							   match = true;
						   }
					   }
					   else {
						   if(usr_hak_akses_obj_lvl.startsWith("<")) {
							   tmp=usr_hak_akses_obj_lvl.substring(1,usr_hak_akses_obj_lvl.length());
							   if(Integer.valueOf(tmp).intValue()>Integer.valueOf(target_objlvl).intValue()) {
								   match = true;
							   }
						   }
						   else {
							   if(usr_hak_akses_obj_lvl.startsWith(">")) {
								   tmp=usr_hak_akses_obj_lvl.substring(1,usr_hak_akses_obj_lvl.length());
								   if(Integer.valueOf(tmp).intValue()<Integer.valueOf(target_objlvl).intValue()) {
									   match = true;
								   }
							   }
							   else {
								   if(usr_hak_akses_obj_lvl.startsWith("!=")) {
									   tmp=usr_hak_akses_obj_lvl.substring(2,usr_hak_akses_obj_lvl.length());
									   if(Integer.valueOf(tmp).intValue()!=Integer.valueOf(target_objlvl).intValue()) {
										   match = true;
									   }
								   }
							   }
						   }
					   }
				   }				   
			   }
			   if(match) {
				   filtered_objlvl=filtered_objlvl+target_objlvl+",";
			   }
			}
			if(filtered_objlvl.length()>0) {
				filtered_objlvl = filtered_objlvl.substring(0,filtered_objlvl.length()-1);
			}	
		   
		}
		else {
			   //tipe > 1 tkn
			usr_hak_akses_obj_lvl = usr_hak_akses_obj_lvl.replaceAll("=", "");
			   
			StringTokenizer stt = new StringTokenizer(token_target_obj_lvl,",");
			while(stt.hasMoreTokens()) {
				String target = stt.nextToken();
				boolean match = false;
				StringTokenizer st = new StringTokenizer(usr_hak_akses_obj_lvl,",");
				while(st.hasMoreTokens()&&!match) {
					String hak = st.nextToken();
					if(target.equalsIgnoreCase(hak)) {
						match = true;
					}
				}
				if(match) {
				   filtered_objlvl=filtered_objlvl+target+",";
				}
			}
			if(filtered_objlvl.length()>0) {
				filtered_objlvl = filtered_objlvl.substring(0,filtered_objlvl.length()-1);
			}
		}
		return filtered_objlvl;
    }
    
	
   
    public Vector searchCivitasProdiBased(int norut,String kword,String kdpst) {
    	boolean number = true;
    	String akses_cmd = this.getAccessLevel();
    	boolean allowSearchDosen = false;
		if(akses_cmd.contains("allowSrcDosen")) {
			allowSearchDosen = true;
		}
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	String tkn_objlvl_kdpst = Converter.getObjLvlGiven(kdpst);
    	rs=null;
   		StringTokenizer st = new StringTokenizer(accessLevelConditional,"#");
   		String token_conditional="";
   		for(int i=0;i<norut;i++) {
   			token_conditional = st.nextToken();
   		}
   	    //st = new StringTokenizer(token_conditional,",");
   		String tkn_obj = filterTokenTargetObjLvlDgnHakAkses(token_conditional, tkn_objlvl_kdpst);
   		st = new StringTokenizer(tkn_obj,",");
   		String sql_cmd="";
   	    if(st.countTokens()>0) {
   	    	
   	    	sql_cmd="select * from CIVITAS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where (OBJ_LEVEL=";
   		//sql_cmd="select * from CIVITAS where (OBJ_LEVEL";
   	    	while(st.hasMoreTokens()) {
   	    		String conditional = ""+st.nextToken();
   	    		sql_cmd=sql_cmd+conditional+" ";
   	    		if(st.hasMoreTokens()) {
   	    			sql_cmd=sql_cmd+"OR OBJ_LEVEL=";
   	    		}
   	    	}
   	    	////System.out.println("sql_cmd="+sql_cmd);
   	    	try {
   	    		long test_key_for_number = Long.valueOf(kword.replaceAll("%", ""));
   	    		sql_cmd=sql_cmd+") and (NPMHSMSMHS LIKE ? OR NIMHSMSMHS LIKE ?)";
   			
   	    	}
   	    	catch(Exception e) {
   	    		sql_cmd=sql_cmd+") and NMMHSMSMHS LIKE ?";	
   	    		number=false;
   	    	}
   	    	
   		
   	    	try {
   	    		Context initContext  = new InitialContext();
   	    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
   	    		ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
   			//envContext.close();
    		//initContext.close();
   	    		con = ds.getConnection();
   	    		stmt = con.prepareStatement(sql_cmd);
   	    		if(number) {
   	   				stmt.setString(1, kword);
   	   				stmt.setString(2, kword);
   	   			}
   	   			else {
   	   				stmt.setString(1,kword);
   	   			}	
   	    		rs = stmt.executeQuery();
   	    		while(rs.next()) {
   	    			kdpst = ""+rs.getString("KDPSTMSMHS");
   					String npm = ""+rs.getString("NPMHSMSMHS");
  					String nim = ""+rs.getString("NIMHSMSMHS");
  					String nmm = ""+rs.getString("NMMHSMSMHS");
  					String tplhr = ""+rs.getString("TPLHRMSMHS");
  					String tglhr = ""+rs.getDate("TGLHRMSMHS");
  					String stmhs = ""+rs.getString("STMHSMSMHS");
  					String id_obj = ""+rs.getInt("ID_OBJ");
  					String obj_lvl = ""+rs.getString("OBJ_LEVEL");
  					String obj_desc = ""+rs.getString("OBJ_DESC");
  					li.add(npm);
  					li.add(nim);
  					li.add(kdpst);
  					li.add(nmm);
   					li.add(tplhr);
   					li.add(tglhr);
   					li.add(stmhs);
   					li.add(id_obj);
   					li.add(obj_lvl);
   					li.add(obj_desc);
   				}
   	    		
   	    		if(allowSearchDosen) {
	    			sql_cmd="select * from EXT_CIVITAS_DATA_DOSEN inner join CIVITAS on NPMHS=NPMHSMSMHS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where NMMHSMSMHS like ? or NPMHSMSMHS like ? or NIMHSMSMHS like ?";
	    				//stmt = con.prepareStatement("select * from CIVITAS inner join EXT_CIVITAS_DATA_DOSEN on NPMHSMSMHS=NPMHS inner join OBJECT on CIVITAS.ID_OBJ=OBJECT.ID_OBJ where NMMHSMSMHS like ? or NPMHSMSMHS like ? or NIMHSMSMHS like ?");
	    			stmt = con.prepareStatement(sql_cmd);
	    			stmt.setString(1,"%"+kword+"%");
	    			stmt.setString(2,"%"+kword+"%");
	    			stmt.setString(3,"%"+kword+"%");
	    			rs = stmt.executeQuery();
	    			while(rs.next()) {
	    				kdpst = ""+rs.getString("KDPSTMSMHS");
	    	   			String npm = ""+rs.getString("NPMHSMSMHS");
	    	  			String nim = ""+rs.getString("NIMHSMSMHS");
	    	  			String nmm = ""+rs.getString("NMMHSMSMHS");
	    	  			String tplhr = ""+rs.getString("TPLHRMSMHS");
	    	  			String tglhr = ""+rs.getDate("TGLHRMSMHS");
	    	  			String stmhs = ""+rs.getString("STMHSMSMHS");
	    	  			String id_obj = ""+rs.getInt("ID_OBJ");
	    	  			String obj_lvl = ""+rs.getString("OBJ_LEVEL");
	    	  			String obj_desc = ""+rs.getString("OBJ_DESC");
	    	  			li.add(npm);
	    	   			li.add(nim);
	    	   			li.add(kdpst);
	    	   			li.add(nmm);
	    	   			li.add(tplhr);
	    	   			li.add(tglhr);
	    	   			li.add(stmhs);
	    	   			li.add(id_obj);
	    	   			li.add(obj_lvl);
	    	   			li.add(obj_desc);
	    			}
	    		}
	    		
	    		v = Tool.removeDuplicateFromVector(v);	
   				rs.close();
   				stmt.close();
				con.close();
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
    			if (con!=null) {
    				try {
    					rs.close();
        				stmt.close();
        				con.close();
    				}
    				catch (Exception ignore) {
    					////System.out.println(ignore);
    				}
    			}
    		}
   	    }
   	    
   	    
    	return v;
    }
    


    
    public String getIdUser() {//npm bukan ID usr_dat
    	String id_db_user=null;
   		try {
   			Context initContext  = new InitialContext();
   			Context envContext  = (Context)initContext.lookup("java:/comp/env");
   			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
   			//envContext.close();
    		//initContext.close();
   			con = ds.getConnection();
   			stmt = con.prepareStatement("select * from CIVITAS where NPMHSMSMHS=?");
   			stmt.setString(1, getNpm());
   			rs = stmt.executeQuery();
   			long id = 0;
   			
   			boolean match = false;
   			if(rs.next()) {
   				match = true;
   				id = rs.getLong("ID");
   				id_db_user = ""+id;
   			}
   			rs.close();
			stmt.close();
			con.close();
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
    				rs.close();
        			stmt.close();
        			con.close();
    			}
    			catch (Exception ignore) {
    				////System.out.println(ignore);
    			}
    		}
    	}
    	return id_db_user;
    }
    

    public boolean updateUserPwd(String new_usr, String new_pwd) {
    	boolean sukses = false;
    	
		String id_usr = null;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		//ds = (DataSource)envContext.lookup("jdbc/USER");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		//envContext.close();
    		//initContext.close();
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT * FROM CIVITAS where NPMHSMSMHS=?");
    		stmt.setString(1, getNpm());
    		ResultSet rs = stmt.executeQuery();
    		
    		if(rs.next()) {
    			id_usr = ""+rs.getLong("ID");
    		} 
    		
    		//Context initContext  = new InitialContext();
    		//Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		//ds = (DataSource)envContext.lookup("jdbc/USER");
    		//ds = (DataSource)envContext.lookup("jdbc/USER");
    		//con = ds.getConnection();
			stmt = con.prepareStatement("update USR_DAT set USR_NAME=?,USR_PWD=? where ID=?");
			stmt.setString(1, new_usr);
			stmt.setString(2, new_pwd);
			stmt.setLong(3, Long.valueOf(id_usr).longValue());
			int i = stmt.executeUpdate();
			
			if(i>0) {
				sukses = true;
			}	
			rs.close();
			stmt.close();
			con.close();
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
    	return sukses;
    }

    public String getUserPwd() {
    	String id = getIdUser();
    	String tokenUsrPwd=null;
    	if(id!=null) {

    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			//envContext.close();
        		//initContext.close();
    			//ds = (DataSource)envContext.lookup("jdbc/USER");
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from USR_DAT where ID=?");
   			   	stmt.setLong(1, Long.valueOf(id).longValue());
   			   	rs = stmt.executeQuery();
   			   	if(rs.next()) {
   			   		tokenUsrPwd = rs.getString("USR_NAME")+" "+rs.getString("USR_PWD");
   			   	}
   			   	rs.close();
   			   	stmt.close();
   			   	con.close();
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
    					rs.close();
    	    			stmt.close();
    	    			con.close();
    				}
    				catch (Exception ignore) {
    					////System.out.println(ignore);
    				}
    			}
    		}
    	}
    	return tokenUsrPwd;
    }
    
    /*
     * depricated
     */
    public Vector listObjekYgBolehDiInsertBerdasarHakAksesUser(int norut_from_isAllowTo) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	li.add("--PILIH--");
    	try {
   			Context initContext  = new InitialContext();
   			Context envContext  = (Context)initContext.lookup("java:/comp/env");
   			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
   			//envContext.close();
    		//initContext.close();
   			con = ds.getConnection();
   			stmt = con.prepareStatement("select * from OBJECT");
   			rs = stmt.executeQuery();
   			while(rs.next()) {
   				String id_obj = ""+rs.getInt("ID_OBJ");
   				String kdpst = rs.getString("KDPST");
  				String obj_name = ""+rs.getString("OBJ_NAME");
  				String obj_desc = ""+rs.getString("OBJ_DESC");
   				int obj_level = rs.getInt("OBJ_LEVEL");
   				//isUsrAllowTo("iciv", String target_npm, String target_obj)
   				if(isUsrAllowTo(norut_from_isAllowTo,obj_level,"","iciv")) {
   					li.add(id_obj+" "+kdpst+" "+obj_desc);
   				}
   			}
   			rs.close();
			stmt.close();
			con.close();
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
    				rs.close();
        			stmt.close();
        			con.close();
    			}
    			catch (Exception ignore) {
    				////System.out.println(ignore);
    			}
    		}
    	}
    	return v;
    }
    /*
     * depricated target_obj_lvl tidak diperlukan lagi, diganti dgn isUsrAllowTo_updated
     */
    public boolean isUsrAllowTo(String command_code, String target_npm, String target_obj_lvl) {
    	/*
    	 * fungsi ini menghasilkan true bila operator/user mempunyai hak akses terhadap data target_obj
    	 */
    	////System.out.println("command_code=="+command_code);
    	boolean allow = false;
    	int norut = isAllowTo(command_code);
    	//String valCmdCode = returnValueIsAllowTo(command_code);
    	////System.out.println("is allow to="+norut);
    	if(norut>0) {
    		//allow = true; *1
    		//allow = false;
    		Vector v = getScopeUpd7des2012(command_code);
    		//li_obj.add(id_obj+" "+kdpst+" "+obj_dsc+" "+obj_level);
    		////System.out.println("v--size="+v.size());
    		boolean match = false;
    		ListIterator li = v.listIterator();
    		while(li.hasNext() && !match) {
    			String baris = (String)li.next();
    			////System.out.println("baris isUsrAllowTo "+command_code+"="+baris);
    			//baris isUsrAllowTo=101 20201 MHS_TEKNIK_ELEKTRO 101 C atau own
    			StringTokenizer st = new StringTokenizer(baris);
    			//hrsnya token1 = own
    			if(st.countTokens()==1) {
    				////System.out.println("opt1");
    				if(this.getNpm().equalsIgnoreCase(target_npm)) {
    					allow = true;
    				}
    				//scope = own = usr_npm
    				////System.out.println("nim = "+baris);
    			}
    			else {
    				String akses_level = ""+this.accessLevelConditional;
    				String tkn_akses_level = "";
    				if(akses_level!=null) {
    					StringTokenizer stmp = new StringTokenizer(akses_level,"#");
    					////System.out.println("token stmp = "+stmp.countTokens());
    					for(int i=0;i<norut;i++) {
    						tkn_akses_level = stmp.nextToken();
    					}
    				}
    				////System.out.println("tkn_akses_level="+tkn_akses_level);
    				////System.out.println("opt2");
    				////System.out.println("this obj level="+this.getObjLevel());
    				////System.out.println("tkn scope = "+baris);
    				String hakAksesIdObj = st.nextToken();
    				String hakAksesKdpst = st.nextToken();
    				String hakAksesKeter = st.nextToken();
    				String hakAksesObjLvl = st.nextToken();
    				
    				////System.out.println("hakAksesObjLvl vs target_obj =");
    				////System.out.println(hakAksesObjLvl+" vs. "+target_obj_lvl);
    				if(Integer.valueOf(hakAksesObjLvl).intValue()==Integer.valueOf(target_obj_lvl).intValue()){
    					allow = true;
    					match = true;
    					////System.out.println("allow="+allow);
    					////System.out.println("match");
    				}
    			}
    		}
    	}
    	else {
    		//ngga ada akses, allow = false;
    	}
		////System.out.println("return allow="+allow);
    	return allow;
    }

    
    public boolean isUsrAllowTo_updated(String command_code, String target_npm) {
    	String target_obj_lvl = ""+Getter.getObjLvl(target_npm);
    	////System.out.println("target_obj_lvl="+target_obj_lvl);
    	/*
    	 * fungsi ini menghasilkan true bila operator/user mempunyai hak akses terhadap data target_obj
    	 */
    	////System.out.println("command_code=="+command_code);
    	boolean allow = false;
    	int norut = isAllowTo(command_code);
    	//String valCmdCode = returnValueIsAllowTo(command_code);
    	////System.out.println("is allow to="+norut);
    	if(norut>0) {
    		//allow = true; *1
    		//allow = false;
    		Vector v = getScopeUpd11Jan2016(command_code);
    		//li_obj.add(id_obj+" "+kdpst+" "+obj_dsc+" "+obj_level);
    		////System.out.println("v--size-- "+command_code+"="+v.size());
    		boolean match = false;
    		ListIterator li = v.listIterator();
    		while(li.hasNext() && !match) {
    			String baris = (String)li.next();
    			////System.out.println("baris isUsrAllowTo="+baris);
    			//baris isUsrAllowTo=101 20201 MHS_TEKNIK_ELEKTRO 101 C atau own
    			StringTokenizer st = new StringTokenizer(baris);
    			//hrsnya token1 = own
    			// sSALAH TOKEN 1 BISA JUGA `YES`
    			if(st.countTokens()==1) {
    				////System.out.println("opt1");
    				String tkn1 = st.nextToken();
    				if(tkn1.equalsIgnoreCase("own")) {
    					if(this.getNpm().equalsIgnoreCase(target_npm)) {
        					allow = true;
        				}	
    				}
    				else {
    					//jadi kalo yes, ngga di allow
    					allow = false;
    				}
    				
    				//scope = own = usr_npm
    				////System.out.println("nim = "+baris);
    			}
    			else {
    				////System.out.println("opt2");
    				String akses_level = ""+this.accessLevelConditional;
    				String tkn_akses_level = "";
    				if(akses_level!=null) {
    					StringTokenizer stmp = new StringTokenizer(akses_level,"#");
    					////System.out.println("token stmp = "+stmp.countTokens());
    					for(int i=0;i<norut;i++) {
    						tkn_akses_level = stmp.nextToken();
    					}
    				}
    				////System.out.println("tkn_akses_level="+tkn_akses_level);
    				////System.out.println("opt2");
    				////System.out.println("this obj level="+this.getObjLevel());
    				////System.out.println("tkn scope = "+baris);
    				String hakAksesIdObj = st.nextToken();
    				String hakAksesKdpst = st.nextToken();
    				String hakAksesKeter = st.nextToken();
    				String hakAksesObjLvl = st.nextToken();
    				
    				////System.out.println("hakAksesObjLvl vs target_obj =");
    				////System.out.println(hakAksesObjLvl+" vs. "+target_obj_lvl);
    				if(Integer.valueOf(hakAksesObjLvl).intValue()==Integer.valueOf(target_obj_lvl).intValue()){
    					allow = true;
    					match = true;
    					////System.out.println("allow="+allow);
    					////System.out.println("match");
    				}
    			}
    		}
    	}
    	else {
    		//ngga ada akses, allow = false;
    	}
		////System.out.println("return allow="+allow);
    	return allow;
    }
    
    public boolean isUsrAllowTo(String command_code, String kdpst) {
    	/*
    	 * fungsi ini menghasilkan true bila operator/user mempunyai hak akses terhadap data kdpst
    	 */
    	boolean allow = false;
    	int norut = isAllowTo(command_code);
    	if(norut>0) {
    		Vector v = getScopeUpd7des2012(command_code);
    		boolean match = false;
    		ListIterator li = v.listIterator();
    		while(li.hasNext() && !match) {
    			String baris = (String)li.next();
    			StringTokenizer st = new StringTokenizer(baris);
    			if(st.countTokens()==1 && st.nextToken().equalsIgnoreCase("own")) {
    				if(this.getKdpst().equalsIgnoreCase(kdpst)) {
    					allow = true;
    				}
    			}
    			else {
    				String akses_level = ""+this.accessLevelConditional;
    				String tkn_akses_level = "";
    				if(akses_level!=null) {
    					StringTokenizer stmp = new StringTokenizer(akses_level,"#");
    					////System.out.println("token stmp = "+stmp.countTokens());
    					for(int i=0;i<norut;i++) {
    						tkn_akses_level = stmp.nextToken();
    					}
    				}
    				////System.out.println("tkn_akses_level="+tkn_akses_level);
    				////System.out.println("opt2");
    				////System.out.println("this obj level="+this.getObjLevel());
    				////System.out.println("tkn scope = "+baris);
    				String hakAksesIdObj = st.nextToken();
    				String hakAksesKdpst = st.nextToken();
    				String hakAksesKeter = st.nextToken();
    				String hakAksesObjLvl = st.nextToken();
    				
    				////System.out.println("hakAksesObjLvl vs target_obj =");
    				////System.out.println(hakAksesObjLvl+" vs. "+target_obj);
    				if(hakAksesKdpst.equalsIgnoreCase(kdpst)) {
    				//if(Integer.valueOf(hakAksesObjLvl).intValue()==Integer.valueOf(target_obj_lvl).intValue()){
    					allow = true;
    					match = true;
    					////System.out.println("match");
    				}
    			}
    		}
    	}
    	else {
    		//ngga ada akses, allow = false;
    	}
    	return allow;
    }
    
    
    /*
     * depricated
     */
    public boolean isUsrAllowTo(int norut,int target_objLevel,String npm,String command_code){
    	boolean match = false;
		StringTokenizer st = new StringTokenizer(this.accessLevelConditional,"#");
   		String token_conditional="";
   		for(int i=0;i<norut;i++) {
   			token_conditional = st.nextToken();
   		}
   	    st = new StringTokenizer(token_conditional,",");
   	    while(st.hasMoreTokens() && !match) {
   	    	String conditional = st.nextToken();
   	    	////System.out.println(conditional);
   	    	String cs = ">=";
   	    	if(conditional.startsWith(cs)) {
   	    		int lvl = Integer.valueOf(conditional.substring(2,conditional.length())).intValue();
   	    		if(target_objLevel>=lvl) {
   	    			match=true;
   	    			////System.out.println(">=");
   	    		}
   	    	}
   	    	else {
				cs = ">";
    			if(conditional.startsWith(cs)) {
    				int lvl = Integer.valueOf(conditional.substring(1,conditional.length())).intValue();
    				if(target_objLevel>lvl) {
    					match=true;
    					////System.out.println(">");
    				}
    			}
    			else {
    				cs = "<=";
        			if(conditional.startsWith(cs)) {
        				int lvl = Integer.valueOf(conditional.substring(2,conditional.length())).intValue();
        				if(target_objLevel<=lvl) {
        					match=true;
        					////System.out.println("<=");
        				}
        			}	
        			else {
        				cs = "<";
            			if(conditional.startsWith(cs)) {
            				int lvl = Integer.valueOf(conditional.substring(1,conditional.length())).intValue();
            				if(target_objLevel<lvl) {
            					match=true;
            					////System.out.println("<");
            				}
            			}	
            			else {
            				//cs = "="; kondisi awal 12/10/2012
            				cs = "own";
                			if(conditional.startsWith(cs)) {
                				//cs = "own";
                				if(conditional.contains(cs)) {
                					if(npm.equalsIgnoreCase(this.npm)) {
                						match = true;
                					}
                				}
                				else {
                					cs = "=";
                					if(conditional.startsWith(cs)) {
                						int lvl = Integer.valueOf(conditional.substring(1,conditional.length())).intValue();
                						if(target_objLevel==lvl) {
                							match=true;
                					////System.out.println("=");
                						}	
                					}
                				}
                			}	
            			}
        			}
    			}	
			}
		}
    	return match;
    }
    
    
    //deprecated
    public Vector tryGetInfo(int norut,int target_objLevel,String npm,String command_code,String kdpst) {
    	//npm = target npm yg mo dicari infonya
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	boolean match = false;
    	if(this.npm.equalsIgnoreCase(npm)) {
    		////System.out.println("npm sendiri");
    		match = true;
    	}
    	else {
    		////System.out.println("masuk sini");
    		//match = isUsrAllowTo(norut,target_objLevel,npm,command_code);
    		
    		match = isUsrAllowTo(command_code,npm, ""+target_objLevel);
    		////System.out.println("masuk sini match = "+match);
    	}
    	if(match) {
    		////System.out.println("isu match");
    		if(command_code.equalsIgnoreCase("vop") || command_code.equalsIgnoreCase("viewKrs") || command_code.equalsIgnoreCase("allowViewDataDosen") || command_code.equalsIgnoreCase("insDataDosen")) {
    			v = getProfile(kdpst,npm,""+target_objLevel);
    		}
    		else{
    			if(command_code.equalsIgnoreCase("vbak")) {
    				////System.out.println("isu vbak");
    				Vector v1 = getProfile(kdpst,npm,""+target_objLevel);
    				Vector v2 = getHistoryBak(kdpst,npm);
    				Vector v3 = getHistoryBakUpdated1(kdpst,npm);
    				//li = v1.listIterator();
        			li = v.listIterator();
        			li.add(v1);
        			li.add(v2);
        			li.add(v3);//tambahan updated 3/4/2014
        		}
    		}
    	}
    	else {
    		////System.out.println("no match");
    	}
    	return v;
    }
    
    
    public Vector tryGetInfo(String npm,String command_code,String kdpst) {
    	//npm = target npm yg mo dicari infonya
    	int target_objLevel = Getter.getObjLvl(npm);
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	boolean match = false;
    	if(this.npm.equalsIgnoreCase(npm)) {
    		////System.out.println("npm sendiri");
    		match = true;
    	}
    	else {
    		////System.out.println("masuk sini");
    		//match = isUsrAllowTo(norut,target_objLevel,npm,command_code);
    		
    		match = isUsrAllowTo(command_code,npm, ""+target_objLevel);
    		////System.out.println("masuk sini match = "+match);
    	}
    	if(match) {
    		////System.out.println("isu match");
    		if(command_code.equalsIgnoreCase("vop") || command_code.equalsIgnoreCase("viewKrs") || command_code.equalsIgnoreCase("allowViewDataDosen") || command_code.equalsIgnoreCase("insDataDosen")) {
    			v = getProfile(kdpst,npm,""+target_objLevel);
    		}
    		else{
    			if(command_code.equalsIgnoreCase("vbak")) {
    				////System.out.println("isu vbak");
    				Vector v1 = getProfile(kdpst,npm,""+target_objLevel);
    				Vector v2 = getHistoryBak(kdpst,npm);
    				Vector v3 = getHistoryBakUpdated1(kdpst,npm);
    				//li = v1.listIterator();
        			li = v.listIterator();
        			li.add(v1);
        			li.add(v2);
        			li.add(v3);//tambahan updated 3/4/2014
        		}
    		}
    	}
    	else {
    		////System.out.println("no match");
    	}
    	return v;
    }
    
    
    public Vector tryGetInfo_forced(String npm,String command_code,String kdpst) {
    	//npm = target npm yg mo dicari infonya
    	int target_objLevel = Getter.getObjLvl(npm);
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	boolean match = true;
    	
    	if(match) {
    		////System.out.println("isu match");
    		if(command_code.equalsIgnoreCase("vop") || command_code.equalsIgnoreCase("viewKrs") || command_code.equalsIgnoreCase("allowViewDataDosen") || command_code.equalsIgnoreCase("insDataDosen")) {
    			v = getProfile(kdpst,npm,""+target_objLevel);
    		}
    		else{
    			if(command_code.equalsIgnoreCase("vbak")) {
    				////System.out.println("isu vbak");
    				Vector v1 = getProfile(kdpst,npm,""+target_objLevel);
    				Vector v2 = getHistoryBak(kdpst,npm);
    				Vector v3 = getHistoryBakUpdated1(kdpst,npm);
    				//li = v1.listIterator();
        			li = v.listIterator();
        			li.add(v1);
        			li.add(v2);
        			li.add(v3);//tambahan updated 3/4/2014
        		}
    		}
    	}
    	else {
    		////System.out.println("no match");
    	}
    	return v;
    }
    
    public Vector getProfile(String kdpst,String npm,String target_objLevel) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
   			Context initContext  = new InitialContext();
   			Context envContext  = (Context)initContext.lookup("java:/comp/env");
   			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
   			//envContext.close();
    		//initContext.close();
   			con = ds.getConnection();
   			
   			stmt = con.prepareStatement("select * from CIVITAS left join EXT_CIVITAS on CIVITAS.NPMHSMSMHS=EXT_CIVITAS.NPMHSMSMHS where CIVITAS.NPMHSMSMHS=? AND CIVITAS.KDPSTMSMHS=?");
   			stmt.setString(1,npm);
   			stmt.setString(2,kdpst);
   			rs = stmt.executeQuery();
   			////System.out.println("msk");
   			while(rs.next()) {
   				//update ada di 2 tmp = ProfileCivitas.java && HistoryBakCivitas.java
   				String id_kotaku = ""+rs.getInt("ID");
   				String id_obj = ""+rs.getInt("ID_OBJ");
   				//String obj_lvl =""+rs.getInt("OBJ_LEVEL");
   				String kdpti = ""+rs.getString("KDPTIMSMHS");
   				String kdjen = ""+rs.getString("KDJENMSMHS");
   				String nimhs = ""+rs.getString("NIMHSMSMHS");
   				String nmmhs = ""+rs.getString("NMMHSMSMHS");
   				String shift= ""+rs.getString("SHIFTMSMHS");
   				String tplhr= ""+rs.getString("TPLHRMSMHS");
   				String tglhr= ""+rs.getDate("TGLHRMSMHS");
   				String kdjek= ""+rs.getString("KDJEKMSMHS");
   				String tahun= ""+rs.getString("TAHUNMSMHS");
   				String smawl= ""+rs.getString("SMAWLMSMHS");
   				String btstu= ""+rs.getString("BTSTUMSMHS");
   				String assma= ""+rs.getString("ASSMAMSMHS");
   				String tgmsk= ""+rs.getDate("TGMSKMSMHS");
   				String tglls= ""+rs.getDate("TGLLSMSMHS");
   				String stmhs= ""+rs.getString("STMHSMSMHS");
   				String stpid= ""+rs.getString("STPIDMSMHS");
   				String sksdi= ""+rs.getInt("SKSDIMSMHS");
   				String asnim= ""+rs.getString("ASNIMMSMHS");
   				String aspti= ""+rs.getString("ASPTIMSMHS");
   				String asjen= ""+rs.getString("ASJENMSMHS");
   				String aspst= ""+rs.getString("ASPSTMSMHS");
   				String bistu= ""+rs.getString("BISTUMSMHS");
   				String peksb= ""+rs.getString("PEKSBMSMHS");
   				String nmpek= ""+rs.getString("NMPEKMSMHS");
   				String ptpek= ""+rs.getString("PTPEKMSMHS");
   				String pspek= ""+rs.getString("PSPEKMSMHS");
   				String noprm= ""+rs.getString("NOPRMMSMHS");
   				String nokp1= ""+rs.getString("NOKP1MSMHS");
   				String nokp2= ""+rs.getString("NOKP2MSMHS");
   				String nokp3= ""+rs.getString("NOKP3MSMHS");
   				String nokp4= ""+rs.getString("NOKP4MSMHS");
   				String sttus= ""+rs.getString("STTUSMSMHS");
   				String email= ""+rs.getString("EMAILMSMHS");
   				String nohpe= ""+rs.getString("NOHPEMSMHS");// varchar(15) DEFAULT NULL,
   				String almrm= ""+rs.getString("ALMRMMSMHS");// varchar(255) DEFAULT NULL,
   				String kotrm= ""+rs.getString("KOTRMMSMHS");// varchar(255) DEFAULT NULL,
   				String posrm= ""+rs.getString("POSRMMSMHS");// varchar(7) DEFAULT NULL,
   		   		String telrm= ""+rs.getString("TELRMMSMHS");// varchar(45) DEFAULT NULL,
   		   		String almkt= ""+rs.getString("ALMKTMSMHS");// varchar(255) DEFAULT NULL,
   		   		String kotkt= ""+rs.getString("KOTKTMSMHS");// varchar(255) DEFAULT NULL,
   		   		String poskt= ""+rs.getString("POSKTMSMHS");// varchar(45) DEFAULT NULL,
   		   		String telkt= ""+rs.getString("TELKTMSMHS");// varchar(45) DEFAULT NULL,
   		   		String jbtkt= ""+rs.getString("JBTKTMSMHS");// varchar(45) DEFAULT NULL COMMENT 'jabatan',
   		   		String bidkt= ""+rs.getString("BIDKTMSMHS");// varchar(45) DEFAULT NULL COMMENT 'Bidang Usaha ',
   		   		String jenkt= ""+rs.getString("JENKTMSMHS");// varchar(45) DEFAULT NULL COMMENT 'Jenis = swasta asing, swasta local,\n',
   		   		String nmmsp= ""+rs.getString("NMMSPMSMHS");// varchar(45) DEFAULT NULL,
   		   		String almsp= ""+rs.getString("ALMSPMSMHS");// varchar(255) DEFAULT NULL,
   		   		String possp= ""+rs.getString("POSSPMSMHS");// varchar(45) DEFAULT NULL COMMENT 'TELP\n',
   		   		String kotsp= ""+rs.getString("KOTSPMSMHS");// varchar(45) DEFAULT NULL COMMENT 'TELP\n',
   		   		String negsp= ""+rs.getString("NEGSPMSMHS");// varchar(45) DEFAULT NULL COMMENT 'TELP\n',
   		   		String telsp= ""+rs.getString("TELSPMSMHS");// varchar(45) DEFAULT NULL COMMENT 'TELP\n',
   		   		String neglh= ""+rs.getString("NEGLHMSMHS");// varchar(45) DEFAULT NULL COMMENT 'TELP\n',
   		   		String agama= ""+rs.getString("AGAMAMSMHS");// varchar(45) DEFAULT NULL COMMENT 'TELP\n',
   		   				//li.add(""+objLevel);//idobject user dibutuhkan oleh servlet
   				li.add(target_objLevel);
   				li.add(id_kotaku);
   				li.add(id_obj);
   				li.add(kdpti);
   				li.add(kdjen);
   				li.add(kdpst);
   				li.add(npm);
   				li.add(nimhs);
   				li.add(nmmhs);
   				li.add(shift);
   				li.add(tplhr);
   				li.add(tglhr);
   				li.add(kdjek);
   				li.add(tahun);
   				li.add(smawl);
   				li.add(btstu);
   				li.add(assma);
   				li.add(tgmsk);
   				li.add(tglls);
   				li.add(stmhs);
   				li.add(stpid);
   				li.add(sksdi);
   				li.add(asnim);
   				li.add(aspti);
   				li.add(asjen);
   				li.add(aspst);
   				li.add(bistu);
   				li.add(peksb);
   				li.add(nmpek);
   				li.add(ptpek);
   				li.add(pspek);
   				li.add(noprm);
   				li.add(nokp1);
   				li.add(nokp2);
   				li.add(nokp3);
   				li.add(nokp4);
   				li.add(sttus);
   				li.add(email);
   				li.add(nohpe);
   				li.add(almrm);
   				li.add(kotrm);
   				li.add(posrm);
   				li.add(telrm);
   				li.add(almkt);
   				li.add(kotkt);
   				li.add(poskt);
   				li.add(telkt);
   				li.add(jbtkt);
   				li.add(bidkt);
   				li.add(jenkt);
   				li.add(nmmsp);
   				li.add(almsp);
   				li.add(possp);
   				li.add(kotsp);
   				li.add(negsp);
   				li.add(telsp);
   				li.add(neglh);
   		   		li.add(agama);
   			}
   			////System.out.println("vsize ="+v.size());
   			rs.close();
			stmt.close();
			con.close();
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
    				rs.close();
        			stmt.close();
        			con.close();
    			}
    			catch (Exception ignore) {
    				////System.out.println(ignore);
    			}
    		}
    	}
    	return v;
    }
    
    public Vector getHistoryBak(String kdpst,String npm) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
   			Context initContext  = new InitialContext();
   			Context envContext  = (Context)initContext.lookup("java:/comp/env");
   			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
   			//envContext.close();
    		//initContext.close();
   			con = ds.getConnection();
   			stmt = con.prepareStatement("select * from PYMNT  where NPMHSPYMNT=? AND KDPSTPYMNT=? order by KUIIDPYMNT desc");
   			stmt.setString(1,npm);
   			stmt.setString(2,kdpst);

   			rs = stmt.executeQuery();
   			if(rs.next()) {
   				do {
   					String kuiid = ""+rs.getInt("KUIIDPYMNT");	
   					String norut = ""+rs.getInt("NORUTPYMNT");
   					String tgkui = ""+rs.getDate("TGKUIPYMNT");
   					String tgtrs = ""+rs.getDate("TGTRSPYMNT");
   					String keter = ""+rs.getString("KETERPYMNT");
   					String payee = ""+rs.getString("PAYEEPYMNT");
   					String amont = ""+rs.getDouble("AMONTPYMNT");
   					String pymtp = ""+rs.getString("PYMTPYMNT");
   					String noacc = ""+rs.getString("NOACCPYMNT");
   					String opnpm = ""+rs.getString("OPNPMPYMNT");
   					String opnmm = ""+rs.getString("OPNMMPYMNT");
   					String setor = ""+rs.getBoolean("SETORPYMNT");
   					String nonpm = ""+rs.getString("NONPMPYMNT");
   					String voidd = ""+rs.getBoolean("VOIDDPYMNT");
   					String nokod = ""+rs.getString("NOKODPYMNT");
   					////System.out.println("no kuitansi id = "+kuiid);
   					li.add(kuiid);
   					//li.add(npm);
   					//li.add(kdpst);
   					li.add(norut);
   					li.add(tgkui);
   					li.add(tgtrs);
   					li.add(keter);
   					li.add(payee);
   					li.add(amont);
   					li.add(pymtp);
   					li.add(noacc);
   					li.add(opnpm);
   					li.add(opnmm);
   					li.add(setor);
   					li.add(nonpm);
   					li.add(voidd);
   					li.add(nokod);
   				}while(rs.next());	
   			}
   			else {
   				////System.out.println("kosong brur");
   			}
   			rs.close();
			stmt.close();
			con.close();
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
    				rs.close();
        			stmt.close();
        			con.close();
    			}
    			catch (Exception ignore) {
    				////System.out.println(ignore);
    			}
    		}
    	}
    	return v;
    }

    
    public Vector getHistoryBakUpdated1(String kdpst,String npm) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
   			Context initContext  = new InitialContext();
   			Context envContext  = (Context)initContext.lookup("java:/comp/env");
   			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
   			//envContext.close();
    		//initContext.close();
   			con = ds.getConnection();
   			stmt = con.prepareStatement("select * from PYMNT  where NPMHSPYMNT=? AND KDPSTPYMNT=? order by KUIIDPYMNT desc");
   			stmt.setString(1,npm);
   			stmt.setString(2,kdpst);

   			rs = stmt.executeQuery();
   			if(rs.next()) {
   				do {
   					String kuiid = ""+rs.getInt("KUIIDPYMNT");	
   					String norut = ""+rs.getInt("NORUTPYMNT");
   					String tgkui = ""+rs.getDate("TGKUIPYMNT");
   					String tgtrs = ""+rs.getDate("TGTRSPYMNT");
   					String keter = ""+rs.getString("KETERPYMNT");
   					String keterDetail = ""+rs.getString("KETER_PYMNT_DETAIL");
   					String payee = ""+rs.getString("PAYEEPYMNT");
   					String amont = ""+rs.getDouble("AMONTPYMNT");
   					String pymtp = ""+rs.getString("PYMTPYMNT");
   					String gel = ""+rs.getInt("GELOMBANG");
   					String cicilan = ""+rs.getInt("CICILAN");
   					String krs = ""+rs.getInt("KRS");
   					String noacc = ""+rs.getString("NOACCPYMNT");
   					String opnpm = ""+rs.getString("OPNPMPYMNT");
   					String opnmm = ""+rs.getString("OPNMMPYMNT");
   					String setor = ""+rs.getBoolean("SETORPYMNT");
   					String nonpm = ""+rs.getString("NONPMPYMNT");
   					String voidd = ""+rs.getBoolean("VOIDDPYMNT");
   					String nokod = ""+rs.getString("NOKODPYMNT");
   					String updTm = ""+rs.getTimestamp("UPDTMPYMNT");
   					String voidOpNpm = ""+rs.getString("VOIDOPNPM");
   					String voidKeter = ""+rs.getString("VOIDKETER");
   					String voidOpNmm = ""+rs.getString("VOIDOPNMM");
   					String filename = ""+rs.getString("FILENAME");
   					String uploadTm = ""+rs.getTimestamp("UPLOADTM");
   					String approvTm = ""+rs.getTimestamp("APROVALTM");
   					String rejectTm = ""+rs.getTimestamp("REJECTTM");
   					String rejectNote = ""+rs.getString("REJECTION_NOTE");
   					String npmApprovee = ""+rs.getString("NPM_APPROVEE");
   					//tambahan sept 2014
   					String groupId = ""+rs.getString("GROUP_ID");
   					String idPaketBea = ""+rs.getString("IDPAKETBEASISWA");
   					////System.out.println("no kuitansi id = "+kuiid);
   					li.add(kuiid);	
   					li.add(norut);
   					li.add(tgkui);
   					li.add(tgtrs);
   					li.add(keter);
   					li.add(keterDetail);
   					li.add(payee);
   					li.add(amont);
   					li.add(pymtp);
   					li.add(gel);
   					li.add(cicilan);
   					li.add(krs);
   					li.add(noacc);
   					li.add(opnpm);
   					li.add(opnmm);
   					li.add(setor);
   					li.add(nonpm);
   					li.add(voidd);
   					li.add(nokod);
   					li.add(updTm);
   					li.add(voidOpNpm);
   					li.add(voidKeter);
   					li.add(voidOpNmm);
   					li.add(filename);
   					li.add(uploadTm);
   					li.add(approvTm);
   					li.add(rejectTm);
   					li.add(rejectNote);
   					li.add(npmApprovee);
   					li.add(groupId);
   					li.add(idPaketBea);
   				}while(rs.next());	
   			}
   			else {
   				////System.out.println("kosong brur");
   			}
   			
   			stmt = con.prepareStatement("select NAMAPAKET from BEASISWA where IDPAKETBEASISWA=?");
   			li = v.listIterator();
   			while(li.hasNext()) {
   				li.next();//li.add(kuiid);	
   				li.next();//li.add(norut);
   				li.next();//li.add(tgkui);
   				li.next();//li.add(tgtrs);
   				li.next();//li.add(keter);
   				li.next();//li.add(keterDetail);
   				li.next();//li.add(payee);
   				li.next();//li.add(amont);
   				li.next();//li.add(pymtp);
   				li.next();//li.add(gel);
   				li.next();//li.add(cicilan);
   				li.next();//li.add(krs);
   				li.next();//li.add(noacc);
   				li.next();//li.add(opnpm);
   				li.next();//li.add(opnmm);
   				li.next();//li.add(setor);
   				li.next();//li.add(nonpm);
   				li.next();//li.add(voidd);
   				li.next();//li.add(nokod);
   				li.next();//li.add(updTm);
   				li.next();//li.add(voidOpNpm);
   				li.next();//li.add(voidKeter);
   				li.next();//li.add(voidOpNmm);
   				li.next();//li.add(filename);
   				li.next();//li.add(uploadTm);
   				li.next();//li.add(approvTm);
   				li.next();//li.add(rejectTm);
   				li.next();//li.add(rejectNote);
   				li.next();//li.add(npmApprovee);
   				li.next();//li.add(groupId);
   				String paketId = (String)li.next();//li.add(idPaketBea);
   				stmt.setLong(1, Long.parseLong(paketId));
   				rs = stmt.executeQuery();
   				rs.next();//harus ada value di tabel beasiswa
   				String namaPaket = rs.getString("NAMAPAKET");
   				li.set(paketId+","+namaPaket);
   			}
   			
   			rs.close();
			stmt.close();
			con.close();
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
    				rs.close();
        			stmt.close();
        			con.close();
    			}
    			catch (Exception ignore) {
    				////System.out.println(ignore);
    			}
    		}
    	}
    	return v;
    }
    
    
    public Vector getScopeCommandLike(String commandLike) {
    	Vector vf = new Vector();
		ListIterator lif = vf.listIterator();
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
   			Context initContext  = new InitialContext();
   			Context envContext  = (Context)initContext.lookup("java:/comp/env");
   			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
   			//envContext.close();
    		//initContext.close();
   			con = ds.getConnection();
   			stmt = con.prepareStatement("select * from OBJECT where ID_OBJ=?");
   			stmt.setInt(1, this.getIdObj());
   			rs=stmt.executeQuery();
   			rs.next();
   			String access_level = rs.getString("ACCESS_LEVEL_CONDITIONAL");
   			String access_level_desc = rs.getString("ACCESS_LEVEL");
   			StringTokenizer st = new StringTokenizer(access_level,"#");
   			StringTokenizer st1 = new StringTokenizer(access_level_desc,"#");
   			while(st1.hasMoreTokens()) {
   				String cmd = st1.nextToken();
   				String scope = st.nextToken();
   				if(cmd.contains(commandLike)) {
   					Vector v_list_obj = new Vector();
   					if(scope.contains("own")) { 					
    					ListIterator liTmp = v_list_obj.listIterator();
    					liTmp.add("own");
    				}
    				else {
    					v_list_obj = getListObjectScope(scope);
    				}	
   					li.add(v_list_obj);
   				}
   			}
   			
   			li = v.listIterator();
   			int j=0;
   			////System.out.println("0."+this.getIdObj());
   			////System.out.println("1."+access_level);
   			////System.out.println("2."+access_level_desc);
   			while(li.hasNext()) {
   				j++;
   				int i=0;
   				Vector v1 = (Vector)li.next();
   				ListIterator li1 = v1.listIterator();
   				while(li1.hasNext()) {
   					i++;
   					String brs = (String)li1.next();
   					////System.out.println(j+"."+i+"."+brs);
   					lif.add(brs);
   				}
   			}
   			////System.out.println(vf.size());
   			vf = Tool.removeDuplicateFromVector(vf);
   			////System.out.println(vf.size());
    	} 
    	catch (NamingException e) {
    		e.printStackTrace();
    	}
    	catch (SQLException ex) {
    		ex.printStackTrace();
    	} 
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	finally {
    		if (con!=null) {
    			try {
    				rs.close();
        			stmt.close();
        			con.close();
    			}
    			catch (Exception ignore) {
    				////System.out.println(ignore);
    			}
    		}
    	}
    	return vf;
    }
    
    
    public Vector getScopeCommandLikeProdiOnly(String commandLike) {
    	Vector vf = new Vector();
		ListIterator lif = vf.listIterator();
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
   			Context initContext  = new InitialContext();
   			Context envContext  = (Context)initContext.lookup("java:/comp/env");
   			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
   			//envContext.close();
    		//initContext.close();
   			con = ds.getConnection();
   			stmt = con.prepareStatement("select * from OBJECT where ID_OBJ=?");
   			stmt.setInt(1, this.getIdObj());
   			rs=stmt.executeQuery();
   			rs.next();
   			String access_level = rs.getString("ACCESS_LEVEL_CONDITIONAL");
   			String access_level_desc = rs.getString("ACCESS_LEVEL");
   			StringTokenizer st = new StringTokenizer(access_level,"#");
   			StringTokenizer st1 = new StringTokenizer(access_level_desc,"#");
   			while(st1.hasMoreTokens()) {
   				String cmd = st1.nextToken();
   				String scope = st.nextToken();
   				if(cmd.contains(commandLike)) {
   					Vector v_list_obj = new Vector();
   					if(scope.contains("own")) { 					
    					ListIterator liTmp = v_list_obj.listIterator();
    					liTmp.add("own");
    				}
    				else {
    					v_list_obj = getListObjectScope_ver2(scope);
    				}	
   					li.add(v_list_obj);
   				}
   			}
   			
   			li = v.listIterator();
   			int j=0;
   			////System.out.println("0."+this.getIdObj());
   			////System.out.println("1."+access_level);
   			////System.out.println("2."+access_level_desc);
   			while(li.hasNext()) {
   				j++;
   				int i=0;
   				Vector v1 = (Vector)li.next();
   				ListIterator li1 = v1.listIterator();
   				while(li1.hasNext()) {
   					i++;
   					String brs = (String)li1.next();
   					////System.out.println(j+"."+i+"."+brs);
   					lif.add(brs);
   				}
   			}
   			////System.out.println(vf.size());
   			vf = Tool.removeDuplicateFromVector(vf);
   			lif = vf.listIterator();
   			while(lif.hasNext()) {
   				String brs = (String)lif.next();
   				if(!brs.contains("MHS_")) {
   					lif.remove();
   				}
   			}
   			////System.out.println(vf.size());
    	} 
    	catch (NamingException e) {
    		e.printStackTrace();
    	}
    	catch (SQLException ex) {
    		ex.printStackTrace();
    	} 
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	finally {
    		if (con!=null) {
    			try {
    				rs.close();
        			stmt.close();
        			con.close();
    			}
    			catch (Exception ignore) {
    				////System.out.println(ignore);
    			}
    		}
    	}
    	return vf;
    }
/*    
    public Vector getObjName(String commandLike) {
    	Vector vf = new Vector();
		ListIterator lif = vf.listIterator();
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
   			Context initContext  = new InitialContext();
   			Context envContext  = (Context)initContext.lookup("java:/comp/env");
   			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
   			//envContext.close();
    		//initContext.close();
   			con = ds.getConnection();
   			stmt = con.prepareStatement("select * from OBJECT where ID_OBJ=?");
   			stmt.setInt(1, this.getIdObj());
   			rs=stmt.executeQuery();
   			rs.next();
   			String access_level = rs.getString("ACCESS_LEVEL_CONDITIONAL");
   			String access_level_desc = rs.getString("ACCESS_LEVEL");
   			StringTokenizer st = new StringTokenizer(access_level,"#");
   			StringTokenizer st1 = new StringTokenizer(access_level_desc,"#");
   			while(st1.hasMoreTokens()) {
   				String cmd = st1.nextToken();
   				String scope = st.nextToken();
   				if(cmd.contains(commandLike)) {
   					Vector v_list_obj = new Vector();
   					if(scope.contains("own")) { 					
    					ListIterator liTmp = v_list_obj.listIterator();
    					liTmp.add("own");
    				}
    				else {
    					v_list_obj = getListObjectScope(scope);
    				}	
   					li.add(v_list_obj);
   				}
   			}
   			
   			li = v.listIterator();
   			int j=0;
   			////System.out.println("0."+this.getIdObj());
   			////System.out.println("1."+access_level);
   			////System.out.println("2."+access_level_desc);
   			while(li.hasNext()) {
   				j++;
   				int i=0;
   				Vector v1 = (Vector)li.next();
   				ListIterator li1 = v1.listIterator();
   				while(li1.hasNext()) {
   					i++;
   					String brs = (String)li1.next();
   					////System.out.println(j+"."+i+"."+brs);
   					lif.add(brs);
   				}
   			}
   			////System.out.println(vf.size());
   			vf = Tool.removeDuplicateFromVector(vf);
   			////System.out.println(vf.size());
    	} 
    	catch (NamingException e) {
    		e.printStackTrace();
    	}
    	catch (SQLException ex) {
    		ex.printStackTrace();
    	} 
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	finally {
    		if (con!=null) {
    			try {
    				rs.close();
        			stmt.close();
        			con.close();
    			}
    			catch (Exception ignore) {
    				////System.out.println(ignore);
    			}
    		}
    	}
    	return vf;
    }
*/    
    public String getObjNickNameGivenObjId() {
    	String nickname = null;
    	try {
   			Context initContext  = new InitialContext();
   			Context envContext  = (Context)initContext.lookup("java:/comp/env");
   			ds = (DataSource)envContext.lookup("jdbc/"+dbSchema);
   			//envContext.close();
    		//initContext.close();
   			con = ds.getConnection();
   			stmt = con.prepareStatement("select * from OBJECT where ID_OBJ=?");
   			stmt.setInt(1, this.getIdObj());
   			rs=stmt.executeQuery();
   			rs.next();
   			nickname = rs.getString("OBJ_NICKNAME");
    	} 
    	catch (NamingException e) {
    		e.printStackTrace();
    	}
    	catch (SQLException ex) {
    		ex.printStackTrace();
    	} 
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	finally {
    		if (con!=null) {
    			try {
    				rs.close();
        			stmt.close();
        			con.close();
    			}
    			catch (Exception ignore) {
    				////System.out.println(ignore);
    			}
    		}
    	}
    	return nickname;
    }
    
    public String getTokenTransaction() {
    	double value = 0;
    	String time_stamp = ""+AskSystem.getCurrentTimestamp();
    	//2015-06-06 00:17:04.237
    	StringTokenizer st = new StringTokenizer(time_stamp);
    	String date_part = st.nextToken();
    	date_part = date_part.replace("-", "");
    	Double top = Double.parseDouble(date_part);
    	
    	String time_part = st.nextToken();
    	time_part = time_part.replace(":", "");
    	Double bottom = Double.parseDouble(time_part);
    	
    	value = top.doubleValue()/bottom.doubleValue();
    	String npm = this.getNpm();
    	int npm_db = Integer.valueOf(npm);
    	npm = ""+npm_db;
    	for(int i=0; i<npm.length();i++) {
    		if(i%2==0) {
    			value = value + Double.valueOf(npm.charAt(i));
    		}
    		else {
    			value = value - Double.valueOf(npm.charAt(i));
    		}	
    	}
    	return time_stamp+"`"+this.getNpm()+"`"+value;
    }
    
    public boolean validateTokenTransaction(String token) {
    	double value = 0;
    	boolean boolean_value = false;
    	StringTokenizer st = new StringTokenizer(token,"`");
    	String time_stamp = st.nextToken();
    	String npm = st.nextToken();
    	String value_to_test = st.nextToken();
    	
    	st = new StringTokenizer(time_stamp);
    	String date_part = st.nextToken();
    	date_part = date_part.replace("-", "");
    	Double top = Double.parseDouble(date_part);
    	
    	String time_part = st.nextToken();
    	time_part = time_part.replace(":", "");
    	Double bottom = Double.parseDouble(time_part);
    	
    	value = top.doubleValue()/bottom.doubleValue();
    	int npm_db = Integer.valueOf(npm);
    	npm = ""+npm_db;
    	for(int i=0; i<npm.length();i++) {
    		if(i%2==0) {
    			value = value + Double.valueOf(npm.charAt(i));
    		}
    		else {
    			value = value - Double.valueOf(npm.charAt(i));
    		}	
    	}
    	////System.out.println("value to test = "+value_to_test);
    	if(value_to_test.equalsIgnoreCase(value+"")) {
    		boolean_value = true;
    	}
    	
    	return boolean_value;
    }
    
    public JSONArray sendParamToRestUpdateUrl(String url, String tokens_trans, String tkn_inp_value) throws IOException, JSONException {
		//url = url.replace("&#x2f;", "/");  
    	//tokens_trans = prepVariableBeforCombineToUrlRest(tokens_trans); 
    	//tkn_inp_value = prepVariableBeforCombineToUrlRest(tkn_inp_value); 
    	url = Constants.getAlamatIp()+url+"/"+tokens_trans+"/"+tkn_inp_value;
    	
	  	
		//url = Constants.getAlamatIp()+url+"/"+tokens_trans;
		////System.out.println("send to url1 ="+url);
	    InputStream is = new URL(url).openStream();
	    String jsonText = null;
	    JSONArray jsoa = null;
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      ////System.out.println("jsonText to ="+jsonText);
	      
	      jsonText = beans.tools.Getter.readAll(rd);
	      ////System.out.println("jsonText to ="+jsonText);
	      if(jsonText!=null && !Checker.isStringNullOrEmpty(jsonText)) {
	    	  ////System.out.println("tetep masuk sinsi ");
	    	  jsoa = new JSONArray(jsonText);  
	      }
	      
	    } 
	    catch(IOException je) {
	    	je.printStackTrace();
	    }
	    finally {
	      is.close();
	    }
	  //  return json;
	    return jsoa;
    }
    
    public String prepVariableBeforCombineToUrlRest(String var) {
    	/*
    	 * harus sync dengan OTAKU convertBackVariableFromUrlRest()
    	 */
    	var = var.replace(" ", "-tanda0-");
    	var = var.replace("/", "-tanda1-");
    	var = var.replace("&", "-tanda2-");
    	var = var.replace("#", "-tanda3-");
    	var = var.replace("`", "-tanda4-");
    	var = var.replace("%", "-tanda5-");
    	var = var.replace("|", "-tanda6-");
    	var = var.replace("<", "-tanda7-");
    	var = var.replace(">", "-tanda8-");
    	var = var.replace("\"", "-tanda9-");
	  	return var;
    }
    
    public String convertBackVariableFromUrlRest(String var) {
    	
    	var = var.replace("-tanda0-", " ");
    	var = var.replace("-tanda1-", "/");
    	var = var.replace("-tanda2-", "&");
    	var = var.replace("-tanda3-", "#");
    	var = var.replace("-tanda4-", "`");
    	var = var.replace("-tanda5-", "%");
    	var = var.replace("-tanda6-", "|");
	  	return var;
    }
    
    public Vector returnScopeProdiOnlySortByKampusWithListIdobj(String cmd_scope) {
    	StringTokenizer st = null;
    	StringTokenizer st1 = null;
    	String listBaru="";
    	Vector v_kmp_list_objid = new Vector();
    	ListIterator liv = v_kmp_list_objid.listIterator();
    	ListIterator li1 = null;
    	Vector v_scope_kdpst = getScopeObjIdFinal(cmd_scope, true, false, false);
    	String scope_kmp_seperated_by_koma = getScopeKampus(cmd_scope);
    	//System.out.println("scope_kmp_seperated_by_koma="+scope_kmp_seperated_by_koma);
    	if((v_scope_kdpst!=null && v_scope_kdpst!=null) && (scope_kmp_seperated_by_koma!=null && !Checker.isStringNullOrEmpty(scope_kmp_seperated_by_koma))) {
    		st = new StringTokenizer(scope_kmp_seperated_by_koma,",");
    		while(st.hasMoreTokens()) {
    			String kode_kmp = st.nextToken();
    			//System.out.println("kode_kmp1="+kode_kmp);
    			//get idobj untuk scope kampus ini
    			String kode_kmp_follow_by_list_objid_included = new String(kode_kmp);
    			li1 = v_scope_kdpst.listIterator();
    			while(li1.hasNext()) {
    				String brs = (String)li1.next();
    				if(brs.equalsIgnoreCase("own")) {
    					kode_kmp_follow_by_list_objid_included = kode_kmp_follow_by_list_objid_included+"`own";
    				}
    				else {
    					//System.out.println("bar="+brs);
        				st1 = new StringTokenizer(brs);
        				while(st1.hasMoreTokens()) {
        					String idobj=st1.nextToken();
            				String kdpst=st1.nextToken();
            				String obj_desc=st1.nextToken();
            				String obj_lvl=st1.nextToken();
            				String kdjen=st1.nextToken();
            				String kdkmp=st1.nextToken();
            				if(kdkmp.equalsIgnoreCase(kode_kmp)) {
            					kode_kmp_follow_by_list_objid_included = kode_kmp_follow_by_list_objid_included+"`"+idobj;
            				}
        				}
    				}
    				
    			}
    			liv.add(kode_kmp_follow_by_list_objid_included);
    			
    		}
    	}
    	
		return v_kmp_list_objid;
    }
    
    public Vector returnScopeSortByKampusWithListIdobj(String cmd_scope) {
    	StringTokenizer st = null;
    	StringTokenizer st1 = null;
    	String listBaru="";
    	Vector v_kmp_list_objid = new Vector();
    	ListIterator liv = v_kmp_list_objid.listIterator();
    	ListIterator li1 = null;
    	Vector v_scope_kdpst = getScopeObjIdFinal(cmd_scope, false, false, true);
    	String scope_kmp_seperated_by_koma = getScopeKampus(cmd_scope);
    	if((v_scope_kdpst!=null && v_scope_kdpst!=null) && (scope_kmp_seperated_by_koma!=null && !Checker.isStringNullOrEmpty(scope_kmp_seperated_by_koma))) {
    		st = new StringTokenizer(scope_kmp_seperated_by_koma,",");
    		while(st.hasMoreTokens()) {
    			String kode_kmp = st.nextToken();
    			//get idobj untuk scope kampus ini
    			String kode_kmp_follow_by_list_objid_included = new String(kode_kmp);
    			li1 = v_scope_kdpst.listIterator();
    			while(li1.hasNext()) {
    				String brs = (String)li1.next();
    				////System.out.println("bar="+brs);
    				st1 = new StringTokenizer(brs);
    				while(st1.hasMoreTokens()) {
    					String idobj=st1.nextToken();
        				String kdpst=st1.nextToken();
        				String obj_desc=st1.nextToken();
        				String obj_lvl=st1.nextToken();
        				String kdjen=st1.nextToken();
        				String kdkmp=st1.nextToken();
        				if(kdkmp.equalsIgnoreCase(kode_kmp)) {
        					kode_kmp_follow_by_list_objid_included = kode_kmp_follow_by_list_objid_included+"`"+idobj;
        				}
    				}
    			}
    			liv.add(kode_kmp_follow_by_list_objid_included);
    			
    		}
    	}
    	
		return v_kmp_list_objid;
    }
}
