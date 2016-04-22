package beans.dbase.kartu_ujian_rules;

import beans.dbase.UpdateDb;
import beans.tools.Checker;

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
 * Session Bean implementation class UpdateDbKartuUjianRules
 */
@Stateless
@LocalBean
public class UpdateDbKartuUjianRules extends UpdateDb {
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
    public UpdateDbKartuUjianRules() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbKartuUjianRules(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
    
    
    
    public void updateKartuUjianRules(String submit, String thsms, String[]kode_kampus, String[]kdpst, String[]tkn_ver, String[]urutan, String[]tipe_ujian, String thsms_copy_base) {
    	
    	try {   
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			String verificator = null;
			String urutkah = null;
			Vector vIns = new Vector();
			ListIterator li = vIns.listIterator();
    		if(submit.equalsIgnoreCase("update")) {
    			stmt = con.prepareStatement("update KARTU_UJIAN_RULES set TKN_VERIFICATOR_KARTU=?,URUTAN=? where THSMS=? and KDPST=? and TIPE_UJIAN=? and KODE_KAMPUS=?"); 
    			for(int i=0; i<kdpst.length;i++) {
    				int j=0;
    				int k = 1;
    				//System.out.println(kode_kampus[i]+","+kdpst[i]+","+tkn_ver[i]+","+urutan[i]+","+tipe_ujian[i]+","+thsms);
    				verificator = new String(""+tkn_ver[i]);
    				if(Checker.isStringNullOrEmpty(verificator)) {
    					stmt.setNull(k++, java.sql.Types.VARCHAR);
    					verificator = "null";
    				}
    				else {
    					stmt.setString(k++, verificator);
    				}
    				urutkah = new String(""+urutan[i]);
    				if(Checker.isStringNullOrEmpty(urutkah)) {
    					stmt.setNull(k++, java.sql.Types.BOOLEAN);
    					urutkah = "null";
    				}
    				else {
    					stmt.setBoolean(k++, Boolean.parseBoolean(urutkah));
    				}
    				stmt.setString(k++,thsms);
    				stmt.setString(k++, kdpst[i]);
    				stmt.setString(k++, tipe_ujian[i]);
    				stmt.setString(k++, kode_kampus[i]);
    				j = stmt.executeUpdate();
    				if(j<1) {
    					li.add(""+verificator+"`"+urutkah+"`"+thsms+"`"+kdpst[i]+"`"+tipe_ujian[i]+"`"+kode_kampus[i]);
    					//System.out.println("add "+verificator+"`"+urutkah+"`"+thsms+"`"+kdpst[i]+"`"+tipe_ujian[i]+"`"+kode_kampus[i]);
    				}
    			}
    			//insert utk mhs yg tidak terupdate
    			if(vIns.size()>0) {
    				String verificator1 = "";
    				String urutkah1 = "";
    				String thsms1 = "";
    				String kdpst1 = "";
    				String tipe_ujian1 = "";
    				String kode_kampus1 = "";
    				li = vIns.listIterator();
    				stmt = con.prepareStatement("INSERT INTO KARTU_UJIAN_RULES (THSMS,KDPST,TIPE_UJIAN,TKN_VERIFICATOR_KARTU,URUTAN,KODE_KAMPUS) VALUES (?,?,?,?,?,?)");
    				while(li.hasNext()) {
    					int k = 1;
    					String brs = (String)li.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					//li.add(""+verificator+"`"+urutkah+"`"+thsms+"`"+kdpst[i]+"`"+tipe_ujian[i]+"`"+kode_kampus[i]);
    					
    					verificator1 = st.nextToken();
        				urutkah1 = st.nextToken();
        				thsms1 = st.nextToken();
        				kdpst1 = st.nextToken();
        				tipe_ujian1 = st.nextToken();
        				kode_kampus1 = st.nextToken();
    					
        				
        				stmt.setString(k++,thsms1);
        				stmt.setString(k++, kdpst1);
        				stmt.setString(k++, tipe_ujian1);
    					if(Checker.isStringNullOrEmpty(verificator1)) {
        					stmt.setNull(k++, java.sql.Types.VARCHAR);
        				}
        				else {
        					stmt.setString(k++, verificator1);
        				}
        				
        				if(Checker.isStringNullOrEmpty(urutkah1)) {
        					stmt.setNull(k++, java.sql.Types.BOOLEAN);
        				}
        				else {
        					stmt.setBoolean(k++, Boolean.parseBoolean(urutkah1));
        				}
        				stmt.setString(k++,kode_kampus1);
        				stmt.executeUpdate();
    				}

    			}
    		}
    		else if(submit.equalsIgnoreCase("copy")) {
    			String kdpst2 = null;
				String tipe_ujian2 = null;
				String tkn_verif2 = null;
				String tkn_urut2 = null;
				String tkn_kmp2 = null;
    			Vector vTmp = new Vector();
    			li = vTmp.listIterator();
    			
    			stmt = con.prepareStatement("select * from KARTU_UJIAN_RULES where THSMS=?");
    			stmt.setString(1,thsms_copy_base);
    			rs = stmt.executeQuery();
    			while(rs.next()) {
    				kdpst2 = new String(""+rs.getString("KDPST"));
    				tipe_ujian2 = new String(""+rs.getString("TIPE_UJIAN"));
    				tkn_verif2 = new String(""+rs.getString("TKN_VERIFICATOR_KARTU"));
    				tkn_urut2 = new String(""+rs.getBoolean("URUTAN"));
    				tkn_kmp2 = new String(""+rs.getString("KODE_KAMPUS"));
    				li.add(kdpst2+"`"+tipe_ujian2+"`"+tkn_verif2+"`"+tkn_urut2+"`"+tkn_kmp2);
    			}
    			
    			if(vTmp.size()>0) {
    				stmt = con.prepareStatement("delete from KARTU_UJIAN_RULES where THSMS=?");
    				stmt.setString(1, thsms);
    				stmt.executeUpdate();
    				
    				li = vTmp.listIterator();
    				stmt = con.prepareStatement("INSERT INTO KARTU_UJIAN_RULES (THSMS,KDPST,TIPE_UJIAN,TKN_VERIFICATOR_KARTU,URUTAN,KODE_KAMPUS) VALUES (?,?,?,?,?,?)");
    				while(li.hasNext()) {
    					int k = 1;
    					String brs = (String)li.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					//li.add(kdpst2+"`"+tipe_ujian2+"`"+tkn_verif2+"`"+tkn_urut2+"`"+tkn_kmp2);
    					
    					kdpst2 = st.nextToken();
    					tipe_ujian2 = st.nextToken();
    					tkn_verif2 = st.nextToken();
    					tkn_urut2 = st.nextToken();
    					tkn_kmp2 = st.nextToken();
        				
    					
        				
        				stmt.setString(k++,thsms);
        				stmt.setString(k++, kdpst2);
        				stmt.setString(k++, tipe_ujian2);
    					if(Checker.isStringNullOrEmpty(tkn_verif2)) {
        					stmt.setNull(k++, java.sql.Types.VARCHAR);
        				}
        				else {
        					stmt.setString(k++, tkn_verif2);
        				}
        				
        				if(Checker.isStringNullOrEmpty(tkn_urut2)) {
        					stmt.setNull(k++, java.sql.Types.BOOLEAN);
        				}
        				else {
        					stmt.setBoolean(k++, Boolean.parseBoolean(tkn_urut2));
        				}
        				stmt.setString(k++,tkn_kmp2);
        				stmt.executeUpdate();
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
    }	  

}
