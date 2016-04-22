package beans.dbase.daftarUlang;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ListIterator;

import beans.dbase.SearchDb;
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
 * Session Bean implementation class SearchDbInfoDaftarUlangTable
 */
@Stateless
@LocalBean
public class SearchDbInfoDaftarUlangTable extends SearchDb {
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
    public SearchDbInfoDaftarUlangTable() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbInfoDaftarUlangTable(String operatorNpm) {
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
    public SearchDbInfoDaftarUlangTable(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
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
    
    public String getInfoDaftarUlangFilterByScopeAndUnapproved(String thsms,Vector vFilterScope) {
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
    		//filter yg blum approved only
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
    			String msg = Checker.sudahDaftarUlang(kdpst, npmhs, thsms);
    			if(msg==null) {
    				li.remove();
    			}
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
    
    
    public String getDistinctNicknameDaftarUlangApprovee(String thsms) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	String list_tkn_approvee = "";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get
    		stmt = con.prepareStatement("select TKN_VERIFICATOR from DAFTAR_ULANG_RULES where THSMS=?");
    		stmt.setString(1,thsms);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String tkn_approvee = rs.getString(1);
    			StringTokenizer st = new StringTokenizer(tkn_approvee,",");
    			while(st.hasMoreTokens()) {
    				li.add(st.nextToken());
    			}
    		}
    		v = Tool.removeDuplicateFromVector(v);
    		li = v.listIterator();
    		while(li.hasNext()) {
    			list_tkn_approvee = list_tkn_approvee+","+(String)li.next();
    		}
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
    	return list_tkn_approvee;
    }
    
    public Vector getListMhsYgSudahDaftarUlangAndApproved(String thsms, String kdpst) {
    	/*
    	 * BELUM BERDASARKAN KODE KAMPUS
    	 * PALING @ JSPNYA DIKASIH MENU TAB PILIHAN KAMPUS MANA
    	 */
    	//Vector vListApproveeNickname = new Vector();
    	Vector vListMhs = new Vector();
    	ListIterator li = null;
    	String list_tkn_approvee = "";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	get approvee
    		stmt = con.prepareStatement("select TKN_VERIFICATOR from DAFTAR_ULANG_RULES where THSMS=? and KDPST=?");
    		stmt.setString(1,thsms);
    		stmt.setString(2,kdpst);
    		//stmt.setString(3,kode_kmp);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			list_tkn_approvee = rs.getString(1);
    		}
    		//System.out.println("list_tkn_approvee="+list_tkn_approvee);
    		//2. get list npm  from daftar ulang
    		vListMhs = new Vector();
    		li = vListMhs.listIterator();
    		stmt = con.prepareStatement("select NPMHS,TOKEN_APPROVAL from DAFTAR_ULANG where THSMS=? and KDPST=?");
    		stmt.setString(1,thsms);
    		stmt.setString(2,kdpst);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String npmhs = rs.getString(1);
    			//System.out.println("npmhs1="+npmhs);
    			String tkn_approval_approved = rs.getString(2);
    			boolean approved = true;
    			StringTokenizer st = new StringTokenizer(list_tkn_approvee,",");
    			while(st.hasMoreTokens()) {
    				String nick_approvee = st.nextToken();
    				if(!tkn_approval_approved.contains(nick_approvee)) {
    					approved = false;
    				}
    			}
    			if(approved) {
    				li.add(npmhs);
    			}
    		}
    		
    		
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
    	return vListMhs;
    }
    
    

}
