package beans.dbase.dosen;

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

import org.apache.tomcat.jdbc.pool.DataSource;
import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;
/**
 * Session Bean implementation class SearchDbDsn
 */
@Stateless
@LocalBean
public class SearchDbDsn extends SearchDb {
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
    public SearchDbDsn() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbDsn(String operatorNpm) {
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
    public SearchDbDsn(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    /*
     * fungsi in tmp saja..!!!!! jangan dipake
     */
    public Vector getListKaprodi(String kdpst_dosen) {
    	Vector v = new Vector();
    	ListIterator li = null;
    	try {
    		li = v.listIterator();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from CIVITAS where KDPSTMSMHS=? order by NMMHSMSMHS");
    		stmt.setString(1, kdpst_dosen);
    		rs = stmt.executeQuery();
    		//String tmp = "";
    		while(rs.next()) {
    			String id_obj = ""+rs.getString("ID_OBJ");
    			String npm = ""+rs.getString("NPMHSMSMHS");
    			String nmm = ""+rs.getString("NMMHSMSMHS");
    			
    			li.add(id_obj+"||"+npm+"||"+nmm);
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
    
    
    public Vector getListDosenAktif() {
    	Vector v = new Vector();
    	ListIterator li = null;
    	try {
    		li = v.listIterator();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select ID_OBJ,NPMHSMSMHS,NMMHSMSMHS from CIVITAS inner join EXT_CIVITAS_DATA_DOSEN on NPMHSMSMHS=NPMHS where STATUS=? order by NMMHSMSMHS");
    		stmt.setString(1, "A");
    		rs = stmt.executeQuery();
    		//String tmp = "";
    		while(rs.next()) {
    			String id_obj = ""+rs.getString("ID_OBJ");
    			String npm = ""+rs.getString("NPMHSMSMHS");
    			String nmm = ""+rs.getString("NMMHSMSMHS");
    			
    			li.add(id_obj+"||"+npm+"||"+nmm);
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
    /*
     * depreccated
     */
    public Vector getListInfoDosen_v1(String listKdpstDollarSeperator) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		/*
    		 * get total login
    		 */
    		String sql = "(";
    		if(listKdpstDollarSeperator!=null) {
    			StringTokenizer st = new StringTokenizer(listKdpstDollarSeperator,"$");
    			while(st.hasMoreTokens()) {
    				String kdpst = st.nextToken();
    				sql = sql + "TKN_KDPST_TEACH like '%"+kdpst+"%'";
    				if(st.hasMoreTokens()) {
    					sql = sql + " or ";
    				}
    			}
    			sql = sql+")";
    			sql = "select * from MSDOS where "+sql+" and STATUS=?";
    			//System.out.println("ini="+sql);
    			stmt = con.prepareStatement(sql);
        		stmt.setString(1,"A");
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			String nodos = ""+rs.getString ("NODOS");
        			if(Checker.isStringNullOrEmpty(nodos)) {
        				nodos="null";
        			}
        			String npmdos = ""+rs.getString ("NPMDOS");
        			if(Checker.isStringNullOrEmpty(npmdos)) {
        				npmdos="null";
        			}
        			String nidndos = ""+rs.getString ("NIDN");
        			if(Checker.isStringNullOrEmpty(nidndos)) {
        				nidndos="null";
        			}
        			String noKtp = ""+rs.getString("NO_KTP");
        			if(Checker.isStringNullOrEmpty(noKtp)) {
        				noKtp="null";
        			}
        			String ptihbase = ""+rs.getString("KDPTI_HOMEBASE");
        			if(Checker.isStringNullOrEmpty(ptihbase)) {
        				ptihbase="null";
        			}
        			String psthbase = ""+rs.getString("KDPST_HOMEBASE");
        			if(Checker.isStringNullOrEmpty(psthbase)) {
        				psthbase="null";
        			}
        			String nmdos = ""+rs.getString("NMDOS");
        			if(Checker.isStringNullOrEmpty(nmdos)) {
        				nmdos="null";
        			}
        			String gelar = ""+rs.getString("GELAR");
        			if(Checker.isStringNullOrEmpty(gelar)) {
        				gelar="null";
        			}
        			String smawl = ""+rs.getString("SMAWL");
        			if(Checker.isStringNullOrEmpty(smawl)) {
        				smawl="null";
        			}
        			String tknKdpstAjar = ""+rs.getString("TKN_KDPST_TEACH");
        			if(Checker.isStringNullOrEmpty(tknKdpstAjar)) {
        				tknKdpstAjar="null";
        			}
        			String email = ""+rs.getString("EMAIL");
        			if(Checker.isStringNullOrEmpty(email)) {
        				email="null";
        			}
        			String tknTelp = ""+rs.getString("TKN_TELP");
        			if(Checker.isStringNullOrEmpty(tknTelp)) {
        				tknTelp="null";
        			}
        			String tknHp = ""+rs.getString("TKN_HP");
        			if(Checker.isStringNullOrEmpty(tknHp)) {
        				tknHp="null";
        			}
        			String status = ""+rs.getString("STATUS");
        			if(Checker.isStringNullOrEmpty(status)) {
        				status="null";
        			}
        			li.add(nodos+"$"+npmdos+"$"+nidndos+"$"+noKtp+"$"+ptihbase+"$"+psthbase+"$"+nmdos+"$"+gelar+"$"+smawl+"$"+tknKdpstAjar+"$"+email+"$"+tknTelp+"$"+tknHp+"$"+status);
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
    	//System.out.println(v.size());
    	return v;
    }

    public Vector getListInfoDosen_v2(String listKdpstDollarSeperator) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		/*
    		 * get total login
    		 */
    		String sql = "(";
    		if(listKdpstDollarSeperator!=null) {
    			StringTokenizer st = new StringTokenizer(listKdpstDollarSeperator,"$");
    			while(st.hasMoreTokens()) {
    				String kdpst = st.nextToken();
    				sql = sql + "TKN_KDPST_TEACH like '%"+kdpst+"%'";
    				if(st.hasMoreTokens()) {
    					sql = sql + " or ";
    				}
    			}
    			sql = sql+")";
    			sql = "select * from MSDOS where "+sql+" and STATUS=?";
    			//System.out.println("ini="+sql);
    			stmt = con.prepareStatement(sql);
        		stmt.setString(1,"A");
        		rs = stmt.executeQuery();
        		while(rs.next()) {
        			String nodos = ""+rs.getString ("NODOS");
        			if(Checker.isStringNullOrEmpty(nodos)) {
        				nodos="null";
        			}
        			String npmdos = ""+rs.getString ("NPMDOS");
        			if(Checker.isStringNullOrEmpty(npmdos)) {
        				npmdos="null";
        			}
        			String nidndos = ""+rs.getString ("NIDN");
        			if(Checker.isStringNullOrEmpty(nidndos)) {
        				nidndos="null";
        			}
        			String noKtp = ""+rs.getString("NO_KTP");
        			if(Checker.isStringNullOrEmpty(noKtp)) {
        				noKtp="null";
        			}
        			String ptihbase = ""+rs.getString("KDPTI_HOMEBASE");
        			if(Checker.isStringNullOrEmpty(ptihbase)) {
        				ptihbase="null";
        			}
        			String psthbase = ""+rs.getString("KDPST_HOMEBASE");
        			if(Checker.isStringNullOrEmpty(psthbase)) {
        				psthbase="null";
        			}
        			String nmdos = ""+rs.getString("NMDOS");
        			if(Checker.isStringNullOrEmpty(nmdos)) {
        				nmdos="null";
        			}
        			String gelar = ""+rs.getString("GELAR");
        			if(Checker.isStringNullOrEmpty(gelar)) {
        				gelar="null";
        			}
        			String smawl = ""+rs.getString("SMAWL");
        			if(Checker.isStringNullOrEmpty(smawl)) {
        				smawl="null";
        			}
        			String tknKdpstAjar = ""+rs.getString("TKN_KDPST_TEACH");
        			if(Checker.isStringNullOrEmpty(tknKdpstAjar)) {
        				tknKdpstAjar="null";
        			}
        			String email = ""+rs.getString("EMAIL");
        			if(Checker.isStringNullOrEmpty(email)) {
        				email="null";
        			}
        			String tknTelp = ""+rs.getString("TKN_TELP");
        			if(Checker.isStringNullOrEmpty(tknTelp)) {
        				tknTelp="null";
        			}
        			String tknHp = ""+rs.getString("TKN_HP");
        			if(Checker.isStringNullOrEmpty(tknHp)) {
        				tknHp="null";
        			}
        			String status = ""+rs.getString("STATUS");
        			if(Checker.isStringNullOrEmpty(status)) {
        				status="null";
        			}
        			li.add(nodos+"$"+npmdos+"$"+nidndos+"$"+noKtp+"$"+ptihbase+"$"+psthbase+"$"+nmdos+"$"+gelar+"$"+smawl+"$"+tknKdpstAjar+"$"+email+"$"+tknTelp+"$"+tknHp+"$"+status);
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
    	//System.out.println(v.size());
    	return v;
    }

    
}
