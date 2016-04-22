package beans.dbase.arsip;

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
 * Session Bean implementation class SearchDbArsip
 */
@Stateless
@LocalBean
public class SearchDbArsip extends SearchDb {
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
    public SearchDbArsip() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbArsip(String operatorNpm) {
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
    public SearchDbArsip(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }

    //deprecated - pake byObjid version
    public Vector getAllowableFileFolder(String listObjNickNameSeperateByCommma) {
    	/*
    	 * get initial root folder yg bisa di akses oleh user 
    	 */
    	Vector v = null;
    	if(listObjNickNameSeperateByCommma!=null && !Checker.isStringNullOrEmpty(listObjNickNameSeperateByCommma)) {
    		v = new Vector();
    		ListIterator li = v.listIterator();
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			StringTokenizer st = new StringTokenizer(listObjNickNameSeperateByCommma,",");
    			String tmp = "select * from MASTER_ARSIP_TABLE where ";
    			while(st.hasMoreTokens()) {
    				tmp = tmp+" TKN_NICKNAME_AND_ACCESS like '%"+st.nextToken()+"%'";
    				if(st.hasMoreTokens()) {
    					tmp = tmp+" OR "; 
    				}
    			}
    			//System.out.println("sql tmp = "+tmp);
    			stmt = con.prepareStatement(tmp);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String keter_folder = ""+rs.getString("NAMA_ROOT_FOLDER");
    				String alamat_folder = ""+rs.getString("REAL_ROOT_FOLDER");
    				String nick_and_hak = ""+rs.getString("TKN_NICKNAME_AND_ACCESS");
    				li.add(keter_folder+"`"+alamat_folder+"`"+nick_and_hak);
    				//System.out.println(keter_folder+"`"+alamat_folder+"`"+nick_and_hak);
    			}
    			Collections.sort(v);
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
    	return v;
    }
    
    public Vector getAllowableFileFolderByObjid(int objid) {
    	/*
    	 * get initial root folder yg bisa di akses oleh user 
    	 */
    	Vector v = null;
    	//if(listObjNickNameSeperateByCommma!=null && !Checker.isStringNullOrEmpty(listObjNickNameSeperateByCommma)) {
    		v = new Vector();
    		ListIterator li = v.listIterator();
    		try {
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			
    			String tmp = "select * from MASTER_ARSIP_TABLE where TKN_OBJID_AND_ACCESS like '%["+objid+",%'";
    				
    			
    			//System.out.println("sql tmp = "+tmp);
    			stmt = con.prepareStatement(tmp);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				String keter_folder = ""+rs.getString("NAMA_ROOT_FOLDER");
    				String alamat_folder = ""+rs.getString("REAL_ROOT_FOLDER");
    				String hak_akses = ""+rs.getString("TKN_OBJID_AND_ACCESS");
    				//System.out.println(keter_folder+" -- "+hak_akses);
    				StringTokenizer st = new StringTokenizer(hak_akses,"`");
    				boolean match = false;
    				while(st.hasMoreTokens() && !match) {
    					String token = st.nextToken();
        				if(token.startsWith("["+objid+",")) {
        					match = true;
        					StringTokenizer stt = new StringTokenizer(token,",");
        					String id = stt.nextToken();
        					hak_akses = stt.nextToken();
        				}
    				}
    				if(match) {
    					li.add(keter_folder+"`"+alamat_folder+"`"+hak_akses);	
    				}
    				
    				//System.out.println(keter_folder+"`"+alamat_folder+"`"+nick_and_hak);
    			}
    			Collections.sort(v);
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
    	//}
    	return v;
    }
    
    public String getHakAksesGiven(String tknHakAksesfolder, String tknObjNicknameUser) {
    	/*
    	 * jika hak akses folder ada beberapa token = berbeda utk tiap objek dan usr punya macam2
    	 * nickname
    	 */
    	String hak = null;
    	//System.out.println("tknHakAksesfolder="+tknHakAksesfolder);
    	//System.out.println("tknObjNicknameUser="+tknObjNicknameUser);
    	StringTokenizer st = new StringTokenizer(tknObjNicknameUser,",");
    	while(st.hasMoreTokens()) {
    		String nick = st.nextToken();
    		//get hak akses untuk nick ini
    		if(tknHakAksesfolder.contains(nick)) {
    			StringTokenizer st1 = new StringTokenizer(tknHakAksesfolder,",");
    			boolean match = false;
    			while(st1.hasMoreTokens() && !match) {
    				String nick_hak = st1.nextToken();
    				if(nick_hak.contains(nick)) {
    					match = true;
    					StringTokenizer st2 = new StringTokenizer(nick_hak,"-");
    					st2.nextToken();//nickname
    					String hak_tmp = st2.nextToken();
    					if(hak==null || Checker.isStringNullOrEmpty(hak)) {
    						hak = new String(hak_tmp);
    					}
    					else {
    						//break down tkn hak jadi char
    						for(int i=0;i<hak_tmp.length();i++) {
    							String kode_hak = ""+hak_tmp.charAt(i);
    							if(!hak.contains(kode_hak)) {
    								hak = hak+kode_hak;
    							}
    						}
    					}
    				}
    			}
    		}
    	}
    	return hak;
    }

 
}
