package beans.dbase.obj;

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
import java.util.Vector;
import java.util.ListIterator;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.owasp.esapi.ESAPI;

import java.util.StringTokenizer;
/**
 * Session Bean implementation class SearchDbObj
 */
@Stateless
@LocalBean
public class SearchDbObj extends SearchDb {
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
    public SearchDbObj() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbObj(String operatorNpm) {
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
    public SearchDbObj(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }

   
    public Vector getObjNicknameFromScopeUpd7des2012(Vector vInfoObj) {
    	
    	if(vInfoObj!=null && vInfoObj.size()>0) {
    		ListIterator li = vInfoObj.listIterator();
    		try {
    			
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select * from OBJECT where ID_OBJ=?");
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs);
        			String idObj = st.nextToken();
    				String kdpst = st.nextToken();
    				String ketObj = st.nextToken();
    				String objLvl = st.nextToken();
    				String kdjen = st.nextToken();
    				stmt.setLong(1,Long.valueOf(idObj).longValue());
    				rs = stmt.executeQuery();
    				rs.next();
    				String nickname = ""+rs.getString("OBJ_NICKNAME");
    				if(Checker.isStringNullOrEmpty(nickname)) {
    					nickname="null";
    				}
    				li.set(idObj+"$"+kdpst+"$"+ketObj+"$"+objLvl+"$"+kdjen+"$"+nickname);
    				//System.out.println(idObj+"$"+kdpst+"$"+ketObj+"$"+objLvl+"$"+kdjen+"$"+nickname);
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
    	return vInfoObj;
    }

    public JSONArray getObjNicknameFromScopeUpd7des2012InJson(Vector vInfoObj) {
    	JSONArray json = new JSONArray();
		String tmp = null;
		
    	if(vInfoObj!=null && vInfoObj.size()>0) {
    		ListIterator li = vInfoObj.listIterator();
    		try {
    			
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		stmt = con.prepareStatement("select * from OBJECT where ID_OBJ=?");
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			StringTokenizer st = new StringTokenizer(brs);
        			String idObj = st.nextToken();
    				String kdpst = st.nextToken();
    				String ketObj = st.nextToken();
    				String objLvl = st.nextToken();
    				String kdjen = st.nextToken();
    				stmt.setLong(1,Long.valueOf(idObj).longValue());
    				rs = stmt.executeQuery();
    				rs.next();
    				String nickname = ""+rs.getString("OBJ_NICKNAME");
    				if(Checker.isStringNullOrEmpty(nickname)) {
    					nickname="null";
    				}
    				//li.set(idObj+"$"+kdpst+"$"+ketObj+"$"+objLvl+"$"+kdjen+"$"+nickname);
    				
    				JSONObject job = new JSONObject();
    				
    				job.put("ID_OBJ", idObj);
    				job.put("KDPST", kdpst);
    				job.put("OBJ_DESC", ketObj);
    				job.put("OBJ_LEVEL", objLvl);
    				job.put("KDJEN", kdjen);
    				job.put("OBJ_NICKNAME", nickname);
    				json.put(job);
    				
        		}
        		
        		
        	}
    		catch (JSONException e) {
    			e.printStackTrace();
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
    	return json;
    }
}
