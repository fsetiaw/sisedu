package beans.dbase.status_kehadiran_dosen;

import beans.dbase.UpdateDb;
import beans.sistem.AskSystem;
import beans.tools.Checker;
import beans.tools.Converter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
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
 * Session Bean implementation class SearchDbkehadiranDosen
 */
@Stateless
@LocalBean
public class UpdateDbkehadiranDosen extends UpdateDb {
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
    public UpdateDbkehadiranDosen() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public UpdateDbkehadiranDosen(String operatorNpm) {
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

    

    
    public void updateStatusKehadiranDosen(String[]cuid, String[]tgl_ttm, String[]delay_tm, String[]batal, String thsms_now) {
    	Vector vIns = new Vector();
		ListIterator lins = vIns.listIterator();
    	if(cuid!=null && cuid.length>0) {
    		try {
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//	select bila ada remove
        		stmt = con.prepareStatement("UPDATE STATUS_KEHADIRAN_DOSEN SET PERKIRAAN_LAMA_KETERLAMBATAN=?,NPM_OPERATOR=?,BATAL=? WHERE CLASS_UNIQUE_ID=? AND TANGGAL=? and THSMS=?");
        		for(int i=0;i<cuid.length;i++) {
        			//System.out.println("cuid="+cuid.length);
        			//System.out.println("tgl_ttm="+tgl_ttm.length);
        			//System.out.println("delay_tm="+delay_tm.length);
        			//System.out.println("batal="+batal.length);
        			//if(delay_tm[i]!=null && !Checker.isStringNullOrEmpty(delay_tm[i]) && tgl_ttm[i]!=null && !Checker.isStringNullOrEmpty(tgl_ttm[i])) {
        			if(tgl_ttm[i]!=null && !Checker.isStringNullOrEmpty(tgl_ttm[i])) {
        				try {
        					if(delay_tm[i]==null || Checker.isStringNullOrEmpty(delay_tm[i])) {
        						delay_tm[i] = "0";
        					}
            				stmt.setInt(1, Integer.parseInt(delay_tm[i]));
            				stmt.setString(2, this.operatorNpm);
            				stmt.setBoolean(3, false);
            				stmt.setInt(4, Integer.parseInt(cuid[i]));
            				stmt.setDate(5,Converter.formatDateBeforeInsert(tgl_ttm[i]));
            				stmt.setString(6, thsms_now);
            				
            				int j = stmt.executeUpdate();
            				if(j<1) {
            					lins.add(cuid[i]+"`"+delay_tm[i]+"`"+tgl_ttm[i]);
            				}
                		}
                		catch (Exception ex1) {
                			//igore;
                			lins.add(cuid[i]+"`"+delay_tm[i]+"`"+tgl_ttm[i]);
                		}	
    				}
        				
        		}
        		if(vIns.size()>0) {
        			stmt = con.prepareStatement("INSERT INTO STATUS_KEHADIRAN_DOSEN (CLASS_UNIQUE_ID,TANGGAL,BATAL,PERKIRAAN_LAMA_KETERLAMBATAN,NPM_OPERATOR,THSMS) VALUES (?,?,?,?,?,?)");
        			
        			lins = vIns.listIterator();
        			while(lins.hasNext()) {
        				String brs = (String)lins.next();
        				//System.out.println(brs);
        				StringTokenizer st = new StringTokenizer(brs,"`");
        				String cuid_ = st.nextToken();
        				String delay_tm_ = st.nextToken();
        				String tgl_ttm_ = st.nextToken();
        				try {
        					stmt.setInt(1, Integer.parseInt(cuid_));
        					stmt.setDate(2,Converter.formatDateBeforeInsert(tgl_ttm_));
        					stmt.setBoolean(3, false);
        					stmt.setInt(4, Integer.parseInt(delay_tm_));
        					stmt.setString(5, this.operatorNpm);
        					stmt.setString(6, thsms_now);
        					stmt.executeUpdate();
        				}
        	    		catch(Exception e1) {
        	        		e1.printStackTrace();
        	        	}
        			}
        		}
        		
        		if(batal!=null && batal.length>0) {
        			//insert dulu
        			stmt = con.prepareStatement("INSERT INTO STATUS_KEHADIRAN_DOSEN (CLASS_UNIQUE_ID,TANGGAL,BATAL,PERKIRAAN_LAMA_KETERLAMBATAN,NPM_OPERATOR,THSMS) VALUES (?,?,?,?,?,?)");
        			for(int i=0;i<batal.length;i++) {
        				String norut_batal = batal[i];
        				String cuid_batal = cuid[Integer.parseInt(norut_batal)];
        				String tgl_batal = tgl_ttm[Integer.parseInt(norut_batal)];
            			String delay_tm_batal = delay_tm[Integer.parseInt(norut_batal)];
            			if(delay_tm_batal==null || Checker.isStringNullOrEmpty(delay_tm_batal)) {
            				delay_tm_batal = new String("0");
            			}
            			try {
            				stmt.setInt(1, Integer.parseInt(cuid_batal));
            				stmt.setDate(2,Converter.formatDateBeforeInsert(tgl_batal));
            				stmt.setBoolean(3, false);
            				stmt.setInt(4, Integer.parseInt(delay_tm_batal));
            				stmt.setString(5, this.operatorNpm);
            				stmt.setString(6, thsms_now);
            				stmt.executeUpdate();
            			}
            	    	catch(Exception e1) {
            	        	e1.printStackTrace();
            	        }
            		}
        			
        			stmt = con.prepareStatement("UPDATE STATUS_KEHADIRAN_DOSEN SET BATAL=? WHERE CLASS_UNIQUE_ID=? AND TANGGAL=? and THSMS=?");
        			for(int i=0;i<batal.length;i++) {
        				String norut_batal = batal[i];
        				String cuid_batal = cuid[Integer.parseInt(norut_batal)];
        				String tgl_batal = tgl_ttm[Integer.parseInt(norut_batal)];
        				try {
        					stmt.setBoolean(1, true);
        					stmt.setInt(2, Integer.parseInt(cuid_batal));
        					stmt.setDate(3,Converter.formatDateBeforeInsert(tgl_batal));
        					stmt.setString(4, thsms_now);
        					stmt.executeUpdate();
        				}
        	    		catch(Exception e1) {
        	        		e1.printStackTrace();
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
    	    	
    }

}
