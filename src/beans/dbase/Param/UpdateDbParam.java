package beans.dbase.Param;

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
 * Session Bean implementation class UpdateDbParam
 */
@Stateless
@LocalBean
public class UpdateDbParam extends UpdateDb {
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
    public UpdateDbParam() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbParam(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
    
    public int copyObjParam(long targetObjId, long basedObjId, String nu_own_inbox_id, String nu_tu_id, String nu_baa_id, String nu_bak_id, String nu_mhs_id, String nu_scp_kmp, String kode_kmp_dom, String sis_kul) {
    	String tkn_look_up = new String();
    	boolean first = true;
    	if(nu_own_inbox_id!=null && !Checker.isStringNullOrEmpty(nu_own_inbox_id)) {
    		if(first) {
    			first = false;
    		}
    		else {
    			if(!tkn_look_up.startsWith("`")) {
    				tkn_look_up = tkn_look_up + "`";
    			}
    		}
    		tkn_look_up = tkn_look_up+nu_own_inbox_id;
    	}
    	
    	if(nu_tu_id!=null && !Checker.isStringNullOrEmpty(nu_tu_id)) {
    		if(first) {
    			first = false;
    		}
    		else {
    			if(!tkn_look_up.startsWith("`")) {
    				tkn_look_up = tkn_look_up + "`";
    			}
    		}
    		tkn_look_up = tkn_look_up+nu_tu_id;
    	}
    	
    	if(nu_baa_id!=null && !Checker.isStringNullOrEmpty(nu_baa_id)) {
    		if(first) {
    			first = false;
    		}
    		else {
    			if(!tkn_look_up.startsWith("`")) {
    				tkn_look_up = tkn_look_up + "`";
    			}
    		}
    		tkn_look_up = tkn_look_up+nu_baa_id;
    	}
    	
    	if(nu_bak_id!=null && !Checker.isStringNullOrEmpty(nu_bak_id)) {
    		if(first) {
    			first = false;
    		}
    		else {
    			if(!tkn_look_up.startsWith("`")) {
    				tkn_look_up = tkn_look_up + "`";
    			}
    		}
    		tkn_look_up = tkn_look_up+nu_bak_id;
    	}
    	
    	if(nu_mhs_id!=null && !Checker.isStringNullOrEmpty(nu_mhs_id)) {
    		if(first) {
    			first = false;
    		}
    		else {
    			if(!tkn_look_up.startsWith("`")) {
    				tkn_look_up = tkn_look_up + "`";
    			}
    		}
    		tkn_look_up = tkn_look_up+nu_mhs_id;
    	}
    	
    	//System.out.println("tkn_look_up="+tkn_look_up);
    	//tkn_look_up=1|1`2|2`1|1`2|2`1|1`2|2`1|1`2|2`1|1`2|2
    	int i=0;
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from OBJECT where ID_OBJ=?");
    		stmt.setLong(1, basedObjId);
    		rs = stmt.executeQuery();
    		rs.next();
    		String alc = rs.getString("ACCESS_LEVEL_CONDITIONAL");
    		String nu_alc = replaceTknScope(alc, tkn_look_up);
    		
    		String al = rs.getString("ACCESS_LEVEL");
    		String dv = rs.getString("DEFAULT_VALUE");
    		//String on = rs.getString("OBJ_NICKNAME");
    		String ha = rs.getString("HAK_AKSES");
    		String sk = rs.getString("SCOPE_KAMPUS");
    		String nu_sk = nuScopeKampus(nu_alc, nu_scp_kmp);
    		if(nu_sk==null || Checker.isStringNullOrEmpty(nu_sk)) {
    			nu_sk = new String(sk);
    		}
    		String kkd = rs.getString("KODE_KAMPUS_DOMISILI");
    		if(kode_kmp_dom==null || Checker.isStringNullOrEmpty(kode_kmp_dom)) {
    			kode_kmp_dom = new String(kkd);
    		}
    		String sp = rs.getString("SISTEM_PERKULIAHAN");
    		//StringTokenizer st = new StringTokenizer(alc,"#");
    		//System.out.println(st.countTokens());
    		//st = new StringTokenizer(sk,"#");
    		//System.out.println(st.countTokens());
    		//replaceTknScope(alc, tkn_look_up);
    		if(nu_alc!=null && !Checker.isStringNullOrEmpty(nu_alc)) {
    			stmt = con.prepareStatement("update OBJECT set ACCESS_LEVEL_CONDITIONAL=?,ACCESS_LEVEL=?,DEFAULT_VALUE=?,HAK_AKSES=?,SCOPE_KAMPUS=?,KODE_KAMPUS_DOMISILI=?,SISTEM_PERKULIAHAN=? where ID_OBJ=?");
    			int norut=1;
    			stmt.setString(norut++,nu_alc);
    			System.out.println("alc = "+alc);
    			stmt.setString(norut++,al);
    			System.out.println("al = "+al);
    			if(dv==null || Checker.isStringNullOrEmpty(dv)) {
    				stmt.setNull(norut++,java.sql.Types.VARCHAR);
    			}
    			else {
    				stmt.setString(norut++,dv);
    			}
    			System.out.println("dv = "+dv);
    			if(ha==null || Checker.isStringNullOrEmpty(ha)) {
    				stmt.setNull(norut++,java.sql.Types.VARCHAR);
    			}
    			else {
    				stmt.setString(norut++,ha);
    			}
    			System.out.println("ha = "+ha);
    			if(nu_sk==null || Checker.isStringNullOrEmpty(nu_sk)) {
    				stmt.setNull(norut++,java.sql.Types.VARCHAR);
    			}
    			else {
    				stmt.setString(norut++,nu_sk);
    			}
    			System.out.println("nu_sk = "+nu_sk);
    			if(kode_kmp_dom==null || Checker.isStringNullOrEmpty(kode_kmp_dom)) {
    				stmt.setNull(norut++,java.sql.Types.VARCHAR);
    			}
    			else {
    				stmt.setString(norut++,kode_kmp_dom);
    			}
    			System.out.println("kode_kmp_dom = "+kode_kmp_dom);
    			if(sis_kul==null || Checker.isStringNullOrEmpty(sis_kul)) {
    				stmt.setNull(norut++,java.sql.Types.VARCHAR);
    			}
    			else {
    				stmt.setString(norut++,sis_kul);
    			}
    			System.out.println("sis_kul = "+sis_kul);
    			stmt.setLong(norut++,targetObjId);
    			stmt.executeUpdate();
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
    
    public String replaceTknScope(String tkn_asal, String tkn_lookup) {
    	//tkn_look_up=1|1`2|2`1|1`2|2`1|1`2|2`1|1`2|2`1|1`2|2
    	System.out.println("tkn_asal = "+tkn_asal);
    	String edited = null;
    	if((tkn_asal!=null && !Checker.isStringNullOrEmpty(tkn_asal)) && (tkn_lookup!=null && !Checker.isStringNullOrEmpty(tkn_lookup))) {
    		edited = new String();
    		StringTokenizer stl1 = null;
    		StringTokenizer stl2 = null;
    		StringTokenizer st1 = new StringTokenizer(tkn_asal,"#");
    		while(st1.hasMoreTokens()) {
    			String tokens = st1.nextToken();
    			StringTokenizer st2 = new StringTokenizer(tokens,",");
    			while(st2.hasMoreTokens()) {
    				String original_single_token_with_sign = st2.nextToken();
    				String single_token_val = new String(original_single_token_with_sign);
    				single_token_val = single_token_val.replace("<", "");
    				single_token_val = single_token_val.replace(">", "");
    				single_token_val = single_token_val.replace("=", "");
    				single_token_val = single_token_val.replace(" ", "");
    				//System.out.println("original_single_token_with_sign="+original_single_token_with_sign);
    				//cek ke token lookup apa single_token harus di replace
    				boolean match = false;
    				stl1 = new StringTokenizer(tkn_lookup,"`");
    				while(stl1.hasMoreTokens() && !match) {
    					String a_pair_tkn_lookup = stl1.nextToken();
    					//harus pair (old|nu)
    					stl2 = new StringTokenizer(a_pair_tkn_lookup,"|");
    					if(stl2.countTokens()==2) {
    						String old_val = stl2.nextToken();
    						String nu_val = stl2.nextToken();
    						if(single_token_val.equalsIgnoreCase(old_val)) {
    							match = true;
    							
    							//replace with nu_val;
    							//forget about original sign -- tiban ajah
    							//String edited_single_token_with_sign = original_single_token_with_sign.replace(old_val, nu_val);
    							String edited_single_token_with_sign = new String(nu_val);
    							edited=edited+edited_single_token_with_sign;
    						}
    					}
    					else {
    						//skip aja = value tidak komplit
    					}
    					
    				}
    				if(!match) {
    					edited = edited+original_single_token_with_sign;
					}
    				if(st2.hasMoreTokens()) {
    					//String edited add koma
    					edited = edited+",";
    				}
    			}
    			//tidak seperti st2, st1 selalu diakhiri # 
    			//
    			edited = edited+"#";
    		}
    	}
    	else if((tkn_asal!=null && !Checker.isStringNullOrEmpty(tkn_asal)) && (tkn_lookup==null || Checker.isStringNullOrEmpty(tkn_lookup))) {
    		edited = new String(tkn_asal);
    	}
    	System.out.println("edited = "+edited);
    	return edited;
    }
    
    public String nuScopeKampus(String tkn_alc, String nu_scp_kmp) {
    	//tkn_look_up=1|1`2|2`1|1`2|2`1|1`2|2`1|1`2|2`1|1`2|2
    	//System.out.println("tkn_asal = "+tkn_asal);
    	String edited = null;
    	if(tkn_alc!=null && !Checker.isStringNullOrEmpty(tkn_alc)) {
    		edited = new String();
    		StringTokenizer stl1 = null;
    		StringTokenizer stl2 = null;
    		StringTokenizer st1 = new StringTokenizer(tkn_alc,"#");
    		
    		while(st1.hasMoreTokens()) {
    			st1.nextToken();
    			edited = edited+nu_scp_kmp+"#";	
    		}
    	}
    	System.out.println("edited = "+edited);
    	return edited;
    }
    	
    	  

}
