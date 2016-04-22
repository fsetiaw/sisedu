package beans.dbase.kartu_ujian_rules;

import beans.dbase.SearchDb;
import beans.tools.Checker;
import beans.tools.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class SearchDbKartuUjianRules
 */
@Stateless
@LocalBean
public class SearchDbKartuUjianRules extends SearchDb {
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
    public SearchDbKartuUjianRules() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbKartuUjianRules(String operatorNpm) {
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
    public SearchDbKartuUjianRules(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }

    public Vector getRules(Vector vScope_cmd) {

    	Vector v = null;
    	String thsms_now = Checker.getThsmsNow();
    	ListIterator li = null;
    	if(vScope_cmd!=null && vScope_cmd.size()>0) {
    		v = new Vector();
    		ListIterator li1 = v.listIterator();
    		try {
        		String[]tipe_kartu_ujian = Getter.getTipeKartuUjuanFromConstant();
        		
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from KARTU_UJIAN_RULES where THSMS=? and KDPST=? and TIPE_UJIAN=? and KODE_KAMPUS=? order by KDPST,TIPE_UJIAN");
    			li = vScope_cmd.listIterator();
    			while(li.hasNext()) {
    				String brs = (String)li.next();
    				li1.add(brs);
    				//122 22201 MHS_TEKNIK_SIPIL_KAMPUS_JAMSOSTEK 122 C JST
    				StringTokenizer st = new StringTokenizer(brs);
    				String id_obj = st.nextToken();
    				String kdpst = st.nextToken();
    				String nmpst = st.nextToken();
    				String obj_lvl = st.nextToken();
    				String kdjen = st.nextToken();
    				String kd_kmp = st.nextToken();
    				for(int i=0;i<tipe_kartu_ujian.length;i++) {
    					stmt.setString(1, thsms_now); 
        				stmt.setString(2, kdpst);
        				stmt.setString(3, tipe_kartu_ujian[i]);
        				stmt.setString(4, kd_kmp);
        				rs = stmt.executeQuery();
        				if(rs.next()) {
        					//String tipe_ujian = ""+rs.getString("TIPE_UJIAN"); == tipe_kartu_ujian[i])
        					String tkn_ver = ""+rs.getString("TKN_VERIFICATOR_KARTU");
        					String urutan = ""+rs.getBoolean("URUTAN");
        					li1.add(tipe_kartu_ujian[i]+"`"+tkn_ver+"`"+urutan);	
        				}
        				else {
        					li1.add(tipe_kartu_ujian[i]+"`null`null");	
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
    	}
    		
    	
    	return v;
    }	
}
