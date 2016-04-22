package beans.dbase.migrasi;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ListIterator;
import beans.dbase.UpdateDb;
import beans.setting.Constants;
import beans.sistem.AskSystem;
import beans.tools.Checker;
import beans.tools.Converter;
import beans.tools.Getter;
import beans.tools.DateFormater;
import beans.tools.Tool;
import java.util.Iterator;
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
 * Session Bean implementation class UpdateDbKeu
 */
@Stateless
@LocalBean
public class UpdateDbMigrasi extends UpdateDb {
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
    public UpdateDbMigrasi() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbMigrasi(String operatorNpm) {
        super(operatorNpm);
        // TODO Auto-generated constructor stub
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
    }
    
    
    public int copyPosRevenueTableFromBackup(JSONArray jsoa) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	Vector v1 = new Vector();
    	ListIterator li1 = v1.listIterator();
    	JSONArray jsoaIns = new JSONArray();
    	int i=0;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("SELECT NAMA_MASTER_POS from POS_REVENUE");	
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String pos = rs.getString("NAMA_MASTER_POS");
    			li1.add(pos);
    		}
    		li1 = v1.listIterator();
    		
    		while(li1.hasNext()) {
    			String pos = (String)li1.next();
    	    	if(jsoa!=null && jsoa.length()>0 ) {
    	    		boolean match = false;
    				for(int j=0;j<jsoa.length() && !match;j++) {
    						
    					JSONObject job = null; 
    					boolean pass = false;
    					String tmp = null;
    					//1
    					try {
    						job = jsoa.getJSONObject(j);
    						System.out.println(job.toString());
    							
    						tmp = job.getString("NAMA_MASTER_POS");
    						if(tmp!=null && tmp.equalsIgnoreCase(pos)) {
    							match = true;
    						}
    					}
    					catch(JSONException e) {}
    				}
    				if(match) {
    					li1.remove();
    				}
    	    	}	
    			
    		}
    		li1 = v1.listIterator();
    		stmt = con.prepareStatement("delete from POS_REVENUE where NAMA_MASTER_POS=?");
    		
    		while(li1.hasNext()) {
    			String pos = (String)li1.next();
    			stmt.setString(1, pos);
    			stmt.executeUpdate();
    		}
    			
    	}
    	catch (Exception e) {}
    	
    	try {
    		
    		stmt = con.prepareStatement("update POS_REVENUE set NAMA_MASTER_POS=?,NICKNAME_POS=?,HAPUS=?,KETERANGAN=?,ADA_SUB_POS=?,TKN_KDJEN_AVAILIBILITY=?,TKN_KDPST_AVAILIBILITY=?,GROUP_ID=? where  NAMA_MASTER_POS=?");	
    	}
    	catch (Exception e) {}
    	
    	if(jsoa!=null && jsoa.length()>0) {
    		if(jsoa!=null && jsoa.length()>0) {
				for(int j=0;j<jsoa.length();j++) {
					
					JSONObject job = null; 
					boolean pass = false;
					String tmp = null;
					//1
					try {
						job = jsoa.getJSONObject(j);
						System.out.println(job.toString());
						
						tmp = job.getString("NAMA_MASTER_POS");
						System.out.println("tmp="+tmp);
						stmt.setString(1, tmp);
					}
					catch(JSONException e) {}
					catch(SQLException sq) {}
					if(tmp==null) {
						try {
							stmt.setNull(1,java.sql.Types.VARCHAR);
						}
						catch (SQLException sq) {
							sq.printStackTrace();
						}
					}
					//2
					tmp = null;
					try {
						job = jsoa.getJSONObject(j);
						tmp = job.getString("NICKNAME_POS");
						stmt.setString(2, tmp);
					}
					catch(JSONException e) {}
					catch(SQLException sq) {}
					if(tmp==null) {
						try {
							stmt.setNull(2,java.sql.Types.VARCHAR);
						}
						catch (SQLException sq) {
							sq.printStackTrace();
						}
					}
					//3
					tmp = null;
					try {
						job = jsoa.getJSONObject(j);
						tmp = job.getString("HAPUS");
						if(tmp!=null && tmp.equalsIgnoreCase("1")) {
							tmp = "true";
						}
						else {
							tmp = "false";
						}
						System.out.println("hapus="+tmp);
						stmt.setBoolean(3, Boolean.parseBoolean(tmp));
					}
					catch(JSONException e) {}
					catch(SQLException sq) {}
					if(tmp==null) {
						try {
							stmt.setNull(3,java.sql.Types.BOOLEAN);
						}
						catch (SQLException sq) {
							sq.printStackTrace();
						}
					}
					//4
					tmp = null;
					try {
						job = jsoa.getJSONObject(j);
						tmp = job.getString("KETERANGAN");
						stmt.setString(4, tmp);
					}
					catch(JSONException e) {}
					catch(SQLException sq) {}
					if(tmp==null) {
						try {
							stmt.setNull(4,java.sql.Types.VARCHAR);
						}
						catch (SQLException sq) {
							sq.printStackTrace();
						}
					}
					//5
					tmp = null;
					try {
						job = jsoa.getJSONObject(j);
						tmp = job.getString("ADA_SUB_POS");
						if(tmp!=null && tmp.equalsIgnoreCase("1")) {
							tmp = "true";
						}
						else {
							tmp = "false";
						}
						System.out.println("hapus="+tmp);
					
						stmt.setBoolean(5, Boolean.parseBoolean(tmp));
					}
					catch(JSONException e) {}
					catch(SQLException sq) {}
					if(tmp==null) {
						try {
							stmt.setNull(5,java.sql.Types.BOOLEAN);
						}
						catch (SQLException sq) {
							sq.printStackTrace();
						}
					}
					//6
					tmp = null;
					try {
						job = jsoa.getJSONObject(j);
						tmp = job.getString("TKN_KDJEN_AVAILIBILITY");
						stmt.setString(6, tmp);
					}
					catch(JSONException e) {}
					catch(SQLException sq) {}
					if(tmp==null) {
						try {
							stmt.setNull(6,java.sql.Types.VARCHAR);
						}
						catch (SQLException sq) {
							sq.printStackTrace();
						}
					}
					//7
					tmp = null;
					try {
						job = jsoa.getJSONObject(j);
						tmp = job.getString("TKN_KDPST_AVAILIBILITY");
						stmt.setString(7, tmp);
					}
					catch(JSONException e) {}
					catch(SQLException sq) {}
					if(tmp==null) {
						try {
							stmt.setNull(7,java.sql.Types.VARCHAR);
						}
						catch (SQLException sq) {
							sq.printStackTrace();
						}
					}
					//8
					tmp = null;
					try {
						job = jsoa.getJSONObject(j);
						tmp = job.getString("GROUP_ID");
						stmt.setString(8, tmp);
					}
					catch(JSONException e) {}
					catch(SQLException sq) {}
					if(tmp==null) {
						try {
							stmt.setNull(8,java.sql.Types.VARCHAR);
						}
						catch (SQLException sq) {
							sq.printStackTrace();
						}
					}
					//9
					try {
						job = jsoa.getJSONObject(j);
						System.out.println(job.toString());
						
						tmp = job.getString("NAMA_MASTER_POS");
						System.out.println("tmp="+tmp);
						stmt.setString(9, tmp);
					}
					catch(JSONException e) {}
					catch(SQLException sq) {}
					if(tmp==null) {
						try {
							stmt.setNull(9,java.sql.Types.VARCHAR);
						}
						catch (SQLException sq) {
							sq.printStackTrace();
						}
					}
					
					try {
						int l = stmt.executeUpdate();
						System.out.println("update obj = "+l);
						if(l<1) {
							System.out.println("job 2 array");
							jsoaIns.put(job);
						}
					}
					catch(Exception e) {
						e.printStackTrace();
					}	
				}
				System.out.println("jsoaIns size= "+jsoaIns.length());
				//hapus prev rec
				if(jsoaIns!=null && jsoaIns.length()>0) {
					try {
			    		stmt = con.prepareStatement("delete from POS_REVENUE where NAMA_MASTER_POS=? OR NICKNAME_POS=?");	
			    	}
			    	catch (Exception e) {}
			    	
			    	for(int j=0;j<jsoaIns.length();j++) {
								
						JSONObject job = null; 
						boolean pass = false;
						String tmp = null;
						//1
						try {
							job = jsoaIns.getJSONObject(j);
							System.out.println(job.toString());
									
							tmp = job.getString("NAMA_MASTER_POS");
							
							System.out.println("tmp="+tmp);
							stmt.setString(1, tmp);
							tmp = job.getString("NICKNAME_POS");
							stmt.setString(2, tmp);
							int k = stmt.executeUpdate();
							System.out.println("delete "+tmp+" = "+k);
						}
						catch(JSONException e) {}
						catch(SQLException sq) {}
			    	}
				}	
						
				if(jsoaIns!=null && jsoaIns.length()>0) {
					try {
			    		//Context initContext  = new InitialContext();
			    		//Context envContext  = (Context)initContext.lookup("java:/comp/env");
			    		//ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			    		//con = ds.getConnection();
			    		stmt = con.prepareStatement("INSERT into POS_REVENUE (NAMA_MASTER_POS,NICKNAME_POS,HAPUS,KETERANGAN,ADA_SUB_POS,TKN_KDJEN_AVAILIBILITY,TKN_KDPST_AVAILIBILITY,GROUP_ID) VALUES (?,?,?,?,?,?,?,?)");	
			    	}
			    	catch (Exception e) {}
			    	
			    	for(int j=0;j<jsoaIns.length();j++) {
								
						JSONObject job = null; 
						boolean pass = false;
						String tmp = null;
						//1
						try {
							job = jsoaIns.getJSONObject(j);
							System.out.println(job.toString());
									
							tmp = job.getString("NAMA_MASTER_POS");
							System.out.println("tmp="+tmp);
							stmt.setString(1, tmp);
						}
						catch(JSONException e) {}
						catch(SQLException sq) {}
						if(tmp==null) {
							try {
								stmt.setNull(1,java.sql.Types.VARCHAR);
							}
							catch (SQLException sq) {
								sq.printStackTrace();
							}
						}
						//2
						tmp = null;
						try {
							job = jsoaIns.getJSONObject(j);
							tmp = job.getString("NICKNAME_POS");
							stmt.setString(2, tmp);
						}
						catch(JSONException e) {}
						catch(SQLException sq) {}
						if(tmp==null) {
							try {
								stmt.setNull(2,java.sql.Types.VARCHAR);
							}
							catch (SQLException sq) {
								sq.printStackTrace();
							}
						}
						//3
						tmp = null;
						try {
							job = jsoaIns.getJSONObject(j);
							tmp = job.getString("HAPUS");
							stmt.setBoolean(3, Boolean.parseBoolean(tmp));
						}
						catch(JSONException e) {}
						catch(SQLException sq) {}
						if(tmp==null) {
							try {
								stmt.setNull(3,java.sql.Types.BOOLEAN);
							}
							catch (SQLException sq) {
								sq.printStackTrace();
							}
						}
						//4
						tmp = null;
						try {
							job = jsoaIns.getJSONObject(j);
							tmp = job.getString("KETERANGAN");
							stmt.setString(4, tmp);
						}
						catch(JSONException e) {}
						catch(SQLException sq) {}
						if(tmp==null) {
							try {
								stmt.setNull(4,java.sql.Types.VARCHAR);
							}
							catch (SQLException sq) {
								sq.printStackTrace();
							}
						}
						//5
						tmp = null;
						try {
							job = jsoaIns.getJSONObject(j);
							tmp = job.getString("ADA_SUB_POS");
							stmt.setBoolean(5, Boolean.parseBoolean(tmp));
						}
						catch(JSONException e) {}
						catch(SQLException sq) {}
						if(tmp==null) {
							try {
								stmt.setNull(5,java.sql.Types.BOOLEAN);
							}
							catch (SQLException sq) {
								sq.printStackTrace();
							}
						}
						//6
						tmp = null;
						try {
							job = jsoaIns.getJSONObject(j);
							tmp = job.getString("TKN_KDJEN_AVAILIBILITY");
							stmt.setString(6, tmp);
						}
						catch(JSONException e) {}
						catch(SQLException sq) {}
						if(tmp==null) {
							try {
								stmt.setNull(6,java.sql.Types.VARCHAR);
							}
							catch (SQLException sq) {
								sq.printStackTrace();
							}
						}
						//7
						tmp = null;
						try {
							job = jsoaIns.getJSONObject(j);
							tmp = job.getString("TKN_KDPST_AVAILIBILITY");
							stmt.setString(7, tmp);
						}
						catch(JSONException e) {}
						catch(SQLException sq) {}
						if(tmp==null) {
							try {
								stmt.setNull(7,java.sql.Types.VARCHAR);
							}
							catch (SQLException sq) {
								sq.printStackTrace();
							}
						}
						//8
						tmp = null;
						try {
							job = jsoaIns.getJSONObject(j);
							tmp = job.getString("GROUP_ID");
							stmt.setString(8, tmp);
						}
						catch(JSONException e) {}
						catch(SQLException sq) {}
						if(tmp==null) {
							try {
								stmt.setNull(8,java.sql.Types.VARCHAR);
							}
							catch (SQLException sq) {
								sq.printStackTrace();
							}
						}
						try {
							int l = stmt.executeUpdate();
							System.out.println("insert obj = "+l);
							//if(l<1) {
							//	System.out.println("insert array");
							//	jsoaIns.put(job);
							//}
						}
						catch(Exception e) {
							e.printStackTrace();
						}	
					}	
				}
				
				
				
			}
    	}
    	if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
		if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
		if (con!=null) try { con.close();} catch (Exception ignore){}
		
    	/*
    	SELECT
    	`POS_REVENUE`.`ID`,
    	`POS_REVENUE`.`NAMA_MASTER_POS`,
    	`POS_REVENUE`.`NICKNAME_POS`,
    	`POS_REVENUE`.`HAPUS`,
    	`POS_REVENUE`.`KETERANGAN`,
    	`POS_REVENUE`.`ADA_SUB_POS`,
    	`POS_REVENUE`.`TKN_KDJEN_AVAILIBILITY`,
    	`POS_REVENUE`.`TKN_KDPST_AVAILIBILITY`,
    	`POS_REVENUE`.`GROUP_ID`
    	FROM `USG`.`POS_REVENUE`;
		*/

    	/*
    	try {
    		
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
    	return i;
    }	
    
}
