package beans.dbase.cuti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import beans.dbase.UpdateDb;
import beans.tools.Checker;
import beans.tools.DateFormater;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;



/**
 * Session Bean implementation class UpdateDbCuti
 */
@Stateless
@LocalBean
public class UpdateDbCuti extends UpdateDb {
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
     * @see UpdateDb#UpdateDb()
     */
    public UpdateDbCuti() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbCuti(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }

    
    public void updCutiRuleViaForm(String target_thsms,String[]kdpst,String[]tkn_verificator,String[]urutan,String[]kode_kampus) {
    	try {
        		//String ipAddr =  request.getRemoteAddr();
    		Vector vIns = new Vector();
    		ListIterator lins = vIns.listIterator();
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("UPDATE CUTI_RULES set TKN_VERIFICATOR=?,URUTAN=? where THSMS=? and KDPST=? and KODE_KAMPUS=?");
        	for(int i=0;i<kdpst.length;i++) {
        		int j=1;
        		if(tkn_verificator[i]==null || Checker.isStringNullOrEmpty(tkn_verificator[i])) {
        			stmt.setNull(j++, java.sql.Types.VARCHAR);
        			tkn_verificator[i] = new String("null");
        		}
        		else {
        			stmt.setString(j++,tkn_verificator[i]);	
        		}
        		//urutan
        		if(urutan[i]==null || Checker.isStringNullOrEmpty(urutan[i])) {
        			stmt.setNull(j++, java.sql.Types.BOOLEAN);
        			urutan[i] = new String("null");
        		}
        		else {
        			stmt.setBoolean(j++,Boolean.parseBoolean(urutan[i]));	
        		}
        		
        		stmt.setString(j++, target_thsms);
        		stmt.setString(j++, kdpst[i]);
        		stmt.setString(j++, kode_kampus[i]);
        		int k = stmt.executeUpdate();
        		if(k<1) {
        			lins.add(kdpst[i]+"`"+kode_kampus[i]+"`"+tkn_verificator[i]+"`"+urutan[i]);
        		}
        	}
        	
        	
        	if(vIns.size()>0) {
        		lins = vIns.listIterator();
        		stmt = con.prepareStatement("insert into CUTI_RULES(THSMS,KDPST,TKN_VERIFICATOR,URUTAN,KODE_KAMPUS)values(?,?,?,?,?)");
        		while(lins.hasNext()) {
        			String brs = (String)lins.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String kdpst1 = st.nextToken();
        			String kode_kampus1 = st.nextToken();
        			String verificator = st.nextToken();
        			String urutan1 = st.nextToken();
        			stmt.setString(1, target_thsms);
        			stmt.setString(2, kdpst1);
        			if(Checker.isStringNullOrEmpty(verificator)) {
        				stmt.setNull(3, java.sql.Types.VARCHAR);
        			}
        			else {
        				stmt.setString(3, verificator);	
        			}
        			if(Checker.isStringNullOrEmpty(urutan1)) {
        				stmt.setNull(4, java.sql.Types.BOOLEAN);
        			}
        			else {
        				stmt.setString(4, urutan1);	
        			}
        			stmt.setString(5, kode_kampus1);
        			stmt.executeUpdate();
        		}
        	}
        	//stmt.setString(1, this.operatorNpm);
        		
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
    
    
    public void copyCutyRule(String target_thsms, String thsms_based) {
    	
    	try {
        		//String ipAddr =  request.getRemoteAddr();
    		Vector vBased = new Vector();
    		ListIterator lib = vBased.listIterator();
    		Vector vIns = new Vector();
    		ListIterator lins = vIns.listIterator();
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select * from CUTI_RULES where THSMS=? order by KDPST,KODE_KAMPUS");
        	stmt.setString(1, thsms_based);
        	rs = stmt.executeQuery();
        	while(rs.next()) {
        		String prodi = rs.getString("KDPST");
        		String tkn_ver = rs.getString("TKN_VERIVICATOR");
        		if(Checker.isStringNullOrEmpty(tkn_ver)) {
        			tkn_ver="null";
        		}	
        		String urut = rs.getString("URUTAN");
        		if(Checker.isStringNullOrEmpty(urut)) {
        			urut="null";
        		}
        		String kd_kmp = rs.getString("KODE_KAMPUS");
        		lib.add(prodi+"`"+kd_kmp+"`"+tkn_ver+"`"+urut);
        	}
        	if(vBased.size()>0) {
        		lib = vBased.listIterator();
        		stmt = con.prepareStatement("UPDATE CUTI_RULES set TKN_VERIFICATOR=?,URUTAN=? where THSMS=? and KDPST=? and KODE_KAMPUS=?");
            	while(lib.hasNext()) {
            		String brs = (String)lib.next();
            		StringTokenizer st = new StringTokenizer(brs,"`");
            		String kdpst = st.nextToken();
            		String kode_kmp = st.nextToken();
            		String tkn_ver = st.nextToken();
            		String urut = st.nextToken();
            		//for(int i=0;i<kdpst.length;i++) {
            		int j=1;
            		if(tkn_ver==null || Checker.isStringNullOrEmpty(tkn_ver)) {
            			stmt.setNull(j++, java.sql.Types.VARCHAR);
            			tkn_ver = new String("null");
            		}
            		else {
            			stmt.setString(j++,tkn_ver);	
            		}
            		//urutan
            		if(urut==null || Checker.isStringNullOrEmpty(urut)) {
            			stmt.setNull(j++, java.sql.Types.BOOLEAN);
            			urut = new String("null");
            		}
            		else {
            			stmt.setBoolean(j++,Boolean.parseBoolean(urut));	
            		}
            		
            		stmt.setString(j++, target_thsms);
            		stmt.setString(j++, kdpst);
            		stmt.setString(j++, kode_kmp);
            		int k = stmt.executeUpdate();
            		if(k<1) {
            			lins.add(kdpst+"`"+kode_kmp+"`"+tkn_ver+"`"+urut);
            		}
            	}
            	
            	
            	if(vIns.size()>0) {
            		lins = vIns.listIterator();
            		stmt = con.prepareStatement("insert into CUTI_RULES(THSMS,KDPST,TKN_VERIFICATOR,URUTAN,KODE_KAMPUS)values(?,?,?,?,?)");
            		while(lins.hasNext()) {
            			String brs = (String)lins.next();
            			StringTokenizer st = new StringTokenizer(brs,"`");
            			String kdpst1 = st.nextToken();
            			String kode_kampus1 = st.nextToken();
            			String verificator = st.nextToken();
            			String urutan1 = st.nextToken();
            			stmt.setString(1, target_thsms);
            			stmt.setString(2, kdpst1);
            			if(Checker.isStringNullOrEmpty(verificator)) {
            				stmt.setNull(3, java.sql.Types.VARCHAR);
            			}
            			else {
            				stmt.setString(3, verificator);	
            			}
            			if(Checker.isStringNullOrEmpty(urutan1)) {
            				stmt.setNull(4, java.sql.Types.BOOLEAN);
            			}
            			else {
            				stmt.setString(4, urutan1);	
            			}
            			stmt.setString(5, kode_kampus1);
            			stmt.executeUpdate();
            		}
            	}
        	}
        	
        	//stmt.setString(1, this.operatorNpm);
        		
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
