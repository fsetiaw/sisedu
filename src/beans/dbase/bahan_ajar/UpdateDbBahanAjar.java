package beans.dbase.bahan_ajar;

import beans.dbase.UpdateDb;
import beans.tools.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 * Session Bean implementation class UpdateDbBahanAjar
 */
@Stateless
@LocalBean
public class UpdateDbBahanAjar extends UpdateDb {
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
    public UpdateDbBahanAjar() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbBahanAjar(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
    
    public int insertTipeBahanAjarBaru(String nuType, String prodiVal, String jenjangVal, String kampusVal, String target_path) {
    	int i=0;
    	try {
           	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	Vector vKmp = Getter.getListAllKampus();
        	//li.add(code_campus+"`"+name_campus+"`"+nickname_campus);
        	Vector vProdi = Getter.getListProdi();
        	String list_kdjen  = Getter.getListKdjenProdi();
        	ListIterator li = null;
        	ListIterator li1 = null;
        	
        	
        	
        	if(prodiVal.equalsIgnoreCase("all") && jenjangVal.equalsIgnoreCase("all") && kampusVal.equalsIgnoreCase("all")) {
        		//System.out.println("alloke");
        		Vector v = new Vector();
        		ListIterator lif = v.listIterator();
        		li = vProdi.listIterator();
        		
        		//System.out.println("vProdiSize="+vProdi.size());
        		//li.add(kdpst+"`"+kdfak+"`"+kdjen+"`"+nmpst);
        		
        		stmt = con.prepareStatement("select * from KATEGORI_BAHAN_AJAR where TIPE=? and KDPST=? and KODE_KAMPUS=?");
        		while(li.hasNext()) {
        			String brs = (String)li.next();
        			//System.out.println(brs);
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String kdpst = st.nextToken();
        			String kdfak = st.nextToken();
        			String kdjen = st.nextToken();
        			String nmpst = st.nextToken();
        			li1 = vKmp.listIterator();
        			while(li1.hasNext()) {
        				String  brs1 = (String) li1.next();
        				st = new StringTokenizer(brs1,"`");
        				String kode_kmp = st.nextToken();
        				stmt.setString(1, nuType);
        				stmt.setString(2, kdpst);
        				stmt.setString(3, kode_kmp);
        				rs = stmt.executeQuery();
        				if(!rs.next()) {
        					lif.add(brs+"`"+brs1);
        				}
        			}
        		}	
        		if(v!=null && v.size()>0) {
        			stmt = con.prepareStatement("insert into  KATEGORI_BAHAN_AJAR (TIPE,KDPST,KDJEN,KODE_KAMPUS,AKTIF,PATH)VALUES(?,?,?,?,?,?)");
        			lif = v.listIterator();
        			while(lif.hasNext()) {
        				String brs = (String)lif.next();
        				StringTokenizer st = new StringTokenizer(brs,"`");
        				String kdpst = st.nextToken();
            			String kdfak = st.nextToken();
            			String kdjen = st.nextToken();
            			String nmpst = st.nextToken();
            			String kode_kmp = st.nextToken();
        				
        				stmt.setString(1, nuType);
        				stmt.setString(2, kdpst);
        				stmt.setString(3, kdjen);
        				stmt.setString(4, kode_kmp);
        				stmt.setBoolean(5, true);
        				stmt.setString(5, target_path);
        				stmt.executeUpdate();		
        				
        			}
        		}
        	}
        	else if(!prodiVal.equalsIgnoreCase("all")) {
        		if(kampusVal.equalsIgnoreCase("all")) {
        			
        		}
        		else {
        			
        		}
        		
        	}
        	else if(!jenjangVal.equalsIgnoreCase("all")) {
        		if(kampusVal.equalsIgnoreCase("all")) {
        			
        		}
        		else {
        			
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
    	return i;
    }	

    
    public int assignBahanAjar(String uid, String tipe,String[]path) {
    	int ins=0;
    	try {
           	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//reset prev value
        	stmt = con.prepareStatement("delete from CLASS_POOL_BAHAN_AJAR where UNIQUE_ID=? and TIPE=?");
        	stmt.setInt(1, Integer.parseInt(uid));
        	stmt.setString(2, tipe);
        	stmt.executeUpdate();
        	stmt = con.prepareStatement("insert into CLASS_POOL_BAHAN_AJAR(UNIQUE_ID,TIPE,FILE_DIR)values(?,?,?)");
        	stmt.setInt(1, Integer.parseInt(uid));
        	stmt.setString(2, tipe);
        	for(int i=0;i<path.length;i++) {
        		stmt.setString(3, path[i]);
        		//System.out.println("path[]="+path[i]);
        		ins = ins+stmt.executeUpdate();
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
    	return ins;
    }	


    public int resetBahanAjar(String uid, String tipe) {
    	int ins=0;
    	try {
           	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	//reset prev value
        	stmt = con.prepareStatement("delete from CLASS_POOL_BAHAN_AJAR where UNIQUE_ID=? and TIPE=?");
        	stmt.setInt(1, Integer.parseInt(uid));
        	stmt.setString(2, tipe);
        	ins = stmt.executeUpdate();
        	
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
    	return ins;
    }	
}
