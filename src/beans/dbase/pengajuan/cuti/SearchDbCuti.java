package beans.dbase.pengajuan.cuti;

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
import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import java.util.StringTokenizer;
/**
 * Session Bean implementation class SearchDbCuti
 */
@Stateless
@LocalBean
public class SearchDbCuti extends SearchDb {
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
    public SearchDbCuti() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbCuti(String operatorNpm) {
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
    public SearchDbCuti(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }

    //untuk target = operator
    public Vector getStatusCutiRequest_basi(String target_thsms, boolean show) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			if(show) {
				//for notification = jadi yg show at target aja
				stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and SHOW_AT_TARGET like ?");	
	    		stmt.setString(1, target_thsms);
	    		stmt.setString(2, "%["+Checker.getObjectId(this.operatorNpm)+"]%");
	    		rs = stmt.executeQuery();
	    		v = addHocFn(rs);
			}
			else {
				//liat riwayet  
				//1. bagi target
				stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TOKEN_TARGET_OBJID like ?");	
				stmt.setString(1, target_thsms);
	    		stmt.setString(2, "%["+Checker.getObjectId(this.operatorNpm)+"]%");
	    		rs = stmt.executeQuery();
	    		v = addHocFn(rs);
	    		//2.
	    		
			}
    		/*
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			v = new Vector();
    			li = v.listIterator();
    			do {
    				String id = ""+rs.getLong("ID");
        			String thsms_pengajuan = ""+rs.getString("TARGET_THSMS_PENGAJUAN");
        		    String tipe_pengajuan = ""+rs.getString("TIPE_PENGAJUAN");
        		    String isi_topik_pengajuan = rs.getString("ISI_TOPIK_PENGAJUAN");
        		    String tkn_target_objnickname = ""+rs.getString("TOKEN_TARGET_OBJ_NICKNAME");
        		    String tkn_target_objid = ""+rs.getString("TOKEN_TARGET_OBJID");
        		    String target_npm = ""+rs.getString("TARGET_NPM");
        		    String creator_obj_id = ""+rs.getLong("CREATOR_OBJ_ID");
        		    String creator_npm = ""+rs.getString("CREATOR_NPM");
        		    String creator_nmm = ""+rs.getString("CREATOR_NMM");
        		    String shwow_at_target = ""+rs.getString("SHOW_AT_TARGET");
        		    String show_at_creator = ""+rs.getLong("SHOW_AT_CREATOR");
        		    String updtm = ""+rs.getTimestamp("UPDTM");
        		    String approved = ""+rs.getString("APPROVED");
        		    String locked = rs.getString("LOCKED");
        		    String rejected = rs.getString("REJECTED");
        		    
        		    li.add(id+"`"+thsms_pengajuan+"`"+tipe_pengajuan+"`"+isi_topik_pengajuan+"`"+tkn_target_objnickname+"`"+tkn_target_objid+"`"+target_npm+"`"+creator_obj_id+"`"+creator_npm+"`"+creator_nmm+"`"+shwow_at_target+"`"+show_at_creator+"`"+updtm+"`"+approved+"`"+locked+"`"+rejected);
    			}
    			while(rs.next());
    			
    			//add info subtopik
    			if(v!=null && v.size()>0) {
    				stmt = con.prepareStatement("select * from SUBTOPIK_PENGAJUAN where ID_TOPIK=?");
    				li = v.listIterator();
    				while(li.hasNext()) {
    					String brs = (String)li.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String id = st.nextToken();
    					stmt.setLong(1, Long.parseLong(id));
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						String addhoc_info = "";
    						do {
    							String isi = ""+rs.getString("ISI_SUBTOPIK");
    							String updtm =  ""+rs.getTimestamp("UPDTM");
    							String creator_id = ""+rs.getString("CREATOR_OBJ_ID");
    							String verdict =  ""+rs.getString("VERDICT");
    							addhoc_info = addhoc_info+"`"+isi+"`"+creator_id+"`"+verdict+"`"+updtm;
    						}
    						while(rs.next());
    						li.set(brs+"`"+addhoc_info);
    					}
    				}
    			}
    		}
    		*/
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
    	
    	
    	return v;
    }
    
    public Vector addHocFn(ResultSet rs) {
    	ListIterator li = null;
    	Vector v = null;
    	long opr_obj_id = Checker.getObjectId(this.operatorNpm);
    	try {
    		if(rs!=null && rs.next()) {
    			v = new Vector();
    			li = v.listIterator();
    			do {
    				String id = ""+rs.getLong("ID");
        			String thsms_pengajuan = ""+rs.getString("TARGET_THSMS_PENGAJUAN");
        		    String tipe_pengajuan = ""+rs.getString("TIPE_PENGAJUAN");
        		    String isi_topik_pengajuan = ""+rs.getString("ISI_TOPIK_PENGAJUAN");
        		    String tkn_target_objnickname = ""+rs.getString("TOKEN_TARGET_OBJ_NICKNAME");
        		    String tkn_target_objid = ""+rs.getString("TOKEN_TARGET_OBJID");
        		    String target_npm = ""+rs.getString("TARGET_NPM");
        		    String creator_obj_id = ""+rs.getLong("CREATOR_OBJ_ID");
        		    String creator_npm = ""+rs.getString("CREATOR_NPM");
        		    String creator_nmm = ""+rs.getString("CREATOR_NMM");
        		    String shwow_at_target = ""+rs.getString("SHOW_AT_TARGET");
        		    //if(Checker.isStringNullOrEmpty(shwow_at_target)) {
        		    //	shwow_at_target = "null";
        		    //}
        		    String show_at_creator = ""+rs.getLong("SHOW_AT_CREATOR");
        		    String updtm = ""+rs.getTimestamp("UPDTM");
        		    String approved = ""+rs.getString("APPROVED");
        		    String locked = ""+rs.getBoolean("LOCKED");
        		    String rejected = ""+rs.getString("REJECTED");
        		    String creator_kdpst = ""+rs.getString("CREATOR_KDPST");
        		    String target_kdpst = ""+rs.getString("TARGET_KDPST");
        		    String batal = ""+rs.getBoolean("BATAL");
        		    //System.out.println("add="+id+"`"+thsms_pengajuan+"`"+tipe_pengajuan+"`"+isi_topik_pengajuan+"`"+tkn_target_objnickname+"`"+tkn_target_objid+"`"+target_npm+"`"+creator_obj_id+"`"+creator_npm+"`"+creator_nmm+"`"+shwow_at_target+"`"+show_at_creator+"`"+updtm+"`"+approved+"`"+locked+"`"+rejected+"`"+creator_kdpst+"`"+target_kdpst+"`"+batal);
        		    li.add(id+"`"+thsms_pengajuan+"`"+tipe_pengajuan+"`"+isi_topik_pengajuan+"`"+tkn_target_objnickname+"`"+tkn_target_objid+"`"+target_npm+"`"+creator_obj_id+"`"+creator_npm+"`"+creator_nmm+"`"+shwow_at_target+"`"+show_at_creator+"`"+updtm+"`"+approved+"`"+locked+"`"+rejected+"`"+creator_kdpst+"`"+target_kdpst+"`"+batal);
    			}
    			while(rs.next());
    			
    			//add info subtopik
    			if(v!=null && v.size()>0) {
    				stmt = con.prepareStatement("select * from SUBTOPIK_PENGAJUAN where ID_TOPIK=?");
    				li = v.listIterator();
    				while(li.hasNext()) {
    					String brs = (String)li.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String id = st.nextToken();
    					stmt.setLong(1, Long.parseLong(id));
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						String addhoc_info = "";
    						do {
    							String isi = ""+rs.getString("ISI_SUBTOPIK");
    							String updtm =  ""+rs.getTimestamp("UPDTM");
    							String creator_id = ""+rs.getString("CREATOR_OBJ_ID");
    							String verdict =  ""+rs.getString("VERDICT");
    							addhoc_info = addhoc_info+"`"+isi+"`"+creator_id+"`"+verdict+"`"+updtm;
    						}
    						while(rs.next());
    						li.set(brs+"`"+addhoc_info);
    					}
    				}
    			}
    			//cek yg dibatalin ma mhs, set hidden at target
    			if(v!=null && v.size()>0) {
    				stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_TARGET=? where ID=?");
    				li = v.listIterator();
    				while(li.hasNext()) {
    					String brs = (String)li.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String id = st.nextToken();
            			String thsms_pengajuan = st.nextToken();
            		    String tipe_pengajuan = st.nextToken();
            		    String isi_topik_pengajuan = st.nextToken();
            		    String tkn_target_objnickname = st.nextToken();
            		    String tkn_target_objid = st.nextToken();
            		    String target_npm = st.nextToken();
            		    String creator_obj_id = st.nextToken();
            		    String creator_npm = st.nextToken();
            		    String creator_nmm = st.nextToken();
            		    String shwow_at_target = st.nextToken();
            		    String show_at_creator = st.nextToken();
            		    String updtm = st.nextToken();
            		    String approved = st.nextToken();
            		    String locked = st.nextToken();
            		    String rejected = st.nextToken();
            		    String creator_kdpst = st.nextToken();
            		    String target_kdpst = st.nextToken();
            		    String batal = st.nextToken();
            		    if(batal.equalsIgnoreCase("true")) {
            		    	shwow_at_target = shwow_at_target.replace("["+opr_obj_id+"]", "");
            		    	if(Checker.isStringNullOrEmpty(shwow_at_target)) {
            		    		stmt.setNull(1, java.sql.Types.VARCHAR);
            		    	}
            		    	else {
            		    		stmt.setString(1, shwow_at_target);
            		    	}
            		    	
            		    	stmt.setLong(2, Long.parseLong(id));
            		    	stmt.executeUpdate();
            		    }
    				}
    			}	
    		}
    	}
    	catch (SQLException ex) {
    		ex.printStackTrace();
    	}
    	return v;
    }
    
    //untuk creator = mhs
    public Vector getStatusCutiRequest_basi(String target_thsms, String target_npmhs, boolean show) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			if(show) {
				//berarti untuk notifikasi jadi hanya yg show at creator
				stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=? and SHOW_AT_CREATOR=?");	//show at creator hanya untuk notificasi dashboard
				stmt.setString(1,target_thsms);
	    		stmt.setString(2, target_npmhs);
	    		stmt.setBoolean(3, show);}
			else {
				stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=?");
				stmt.setString(1,target_thsms);
	    		stmt.setString(2, target_npmhs);
	    	}
    		
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			v = new Vector();
    			li = v.listIterator();
    			do {
    				String id = ""+rs.getLong("ID");
        			String thsms_pengajuan = ""+rs.getString("TARGET_THSMS_PENGAJUAN");
        		    String tipe_pengajuan = ""+rs.getString("TIPE_PENGAJUAN");
        		    String isi_topik_pengajuan = rs.getString("ISI_TOPIK_PENGAJUAN");
        		    String tkn_target_objnickname = ""+rs.getString("TOKEN_TARGET_OBJ_NICKNAME");
        		    String tkn_target_objid = ""+rs.getString("TOKEN_TARGET_OBJID");
        		    String target_npm = ""+rs.getString("TARGET_NPM");
        		    String creator_obj_id = ""+rs.getLong("CREATOR_OBJ_ID");
        		    String creator_npm = ""+rs.getString("CREATOR_NPM");
        		    String creator_nmm = ""+rs.getString("CREATOR_NMM");
        		    String shwow_at_target = ""+rs.getString("SHOW_AT_TARGET");
        		    String show_at_creator = ""+rs.getLong("SHOW_AT_CREATOR");
        		    String updtm = ""+rs.getTimestamp("UPDTM");
        		    String approved = ""+rs.getString("APPROVED");
        		    String locked = ""+rs.getString("LOCKED");
        		    String rejected = ""+rs.getString("REJECTED");
        		    
        		    li.add(id+"`"+thsms_pengajuan+"`"+tipe_pengajuan+"`"+isi_topik_pengajuan+"`"+tkn_target_objnickname+"`"+tkn_target_objid+"`"+target_npm+"`"+creator_obj_id+"`"+creator_npm+"`"+creator_nmm+"`"+shwow_at_target+"`"+show_at_creator+"`"+updtm+"`"+approved+"`"+locked+"`"+rejected);
    			}
    			while(rs.next());
    			
    			//add info subtopik
    			if(v!=null && v.size()>0) {
    				stmt = con.prepareStatement("select * from SUBTOPIK_PENGAJUAN where ID_TOPIK=?");
    				li = v.listIterator();
    				while(li.hasNext()) {
    					String brs = (String)li.next();
    					StringTokenizer st = new StringTokenizer(brs,"`");
    					String id = st.nextToken();
    					stmt.setLong(1, Long.parseLong(id));
    					rs = stmt.executeQuery();
    					if(rs.next()) {
    						String addhoc_info = "";
    						do {
    							String isi = ""+rs.getString("ISI_SUBTOPIK");
    							String updtm =  ""+rs.getTimestamp("UPDTM");
    							String creator_id = ""+rs.getString("CREATOR_OBJ_ID");
    							String verdict =  ""+rs.getString("VERDICT");
    							addhoc_info = addhoc_info+"`"+isi+"`"+creator_id+"`"+verdict+"`"+updtm;
    						}
    						while(rs.next());
    						li.set(brs+"`"+addhoc_info);
    					}
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
    	
    	
    	return v;
    }
    
    //!! jangan dipake
    /*
     * DEPRECATED pake yg V1
     */
    //untuk creator = mhs
    public Vector getStatusCutiRequest(String target_thsms, String target_npmhs, boolean show, boolean am_i_stu, boolean boleh_liat_berdasarkan_scope) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			if(show) {
				if(am_i_stu) {
					//berarti untuk notifikasi jadi hanya yg show at creator
					stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=? and SHOW_AT_CREATOR=?");	//show at creator hanya untuk notificasi dashboard
					stmt.setString(1,target_thsms);
		    		stmt.setString(2, target_npmhs);
		    		stmt.setBoolean(3, show);	
		    		rs = stmt.executeQuery();
		    		v = addHocFn(rs);
		    		//show berarti dari home maka update set show_atCreator = false
		    		stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_CREATOR=? where TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=? and SHOW_AT_CREATOR=?");
		    		stmt.setBoolean(1, !show);
		    		stmt.setString(2,target_thsms);
		    		stmt.setString(3, target_npmhs);
		    		stmt.setBoolean(4, show);	
		    		stmt.executeUpdate();
				}
				else {
					//for notification = jadi yg show at target aja
					//untuk operator
					
					
					stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and SHOW_AT_TARGET like ?");	
		    		stmt.setString(1, target_thsms);
		    		stmt.setString(2, "%["+Checker.getObjectId(this.operatorNpm)+"]%");
		    		
		    		rs = stmt.executeQuery();
		    		v = addHocFn(rs);
		    		
				}
			}
			else {
				//liat riwayat individual
				
				if(am_i_stu) {
					stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=?");
					stmt.setString(1,target_thsms);
		    		stmt.setString(2, target_npmhs);
		    		rs = stmt.executeQuery();
		    		v = addHocFn(rs);
				}
				else {
					//target_obj atau yg scopenya termasuk mhs terkait
					//liat riwayet  
					if(boleh_liat_berdasarkan_scope) {
						stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=?");	
						stmt.setString(1, target_thsms);
						stmt.setString(2, target_npmhs);
			    		rs = stmt.executeQuery();
			    		v = addHocFn(rs);
					}
					else {
						//cuma target aja
						stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TOKEN_TARGET_OBJID like ?");	
						stmt.setString(1, target_thsms);
			    		stmt.setString(2, "%["+Checker.getObjectId(this.operatorNpm)+"]%");
			    		rs = stmt.executeQuery();
			    		v = addHocFn(rs);
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
    	
    	
    	return v;
    }
    
    public Vector getStatusCutiRequest_v1(String target_thsms, String target_npmhs, boolean show, boolean am_i_stu, boolean boleh_liat_berdasarkan_scope, Vector v_cuti_scope) {
    	Vector v = null;
    	ListIterator li = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			if(show) {
				if(am_i_stu) {
					//berarti untuk notifikasi jadi hanya yg show at creator
					stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=? and SHOW_AT_CREATOR=?");	//show at creator hanya untuk notificasi dashboard
					stmt.setString(1,target_thsms);
		    		stmt.setString(2, target_npmhs);
		    		stmt.setBoolean(3, show);	
		    		rs = stmt.executeQuery();
		    		v = addHocFn(rs);
		    		//show berarti dari home maka update set show_atCreator = false
		    		stmt = con.prepareStatement("update TOPIK_PENGAJUAN set SHOW_AT_CREATOR=? where TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=? and SHOW_AT_CREATOR=?");
		    		stmt.setBoolean(1, !show);
		    		stmt.setString(2,target_thsms);
		    		stmt.setString(3, target_npmhs);
		    		stmt.setBoolean(4, show);	
		    		stmt.executeUpdate();
				}
				else {
					//untuk operator
					//dari notification = jadi yg show yg idobj termasuk ke dalam scope
					//get pengauan yg belum lock & batal
					//System.out.println("masuk sii");
					if(v_cuti_scope!=null && v_cuti_scope.size()>0) {
						String cmd = null;
						li = v_cuti_scope.listIterator();
						while(li.hasNext()) {
							String scope_info = (String)li.next();
							//System.out.println("scope_info = "+scope_info);
							StringTokenizer st = new StringTokenizer(scope_info,"`");
							String kmp = st.nextToken();
							while(st.hasMoreTokens()) {
								String id = st.nextToken();
								if(cmd==null) {
									cmd = new String("CREATOR_OBJ_ID="+id);
								}
								else {
									cmd = cmd+" OR CREATOR_OBJ_ID="+id;
								}
							}	
						}
						
						cmd = "select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and LOCKED=? and BATAL=? and ("+cmd+")";
						//System.out.println("command = "+cmd);
						stmt = con.prepareStatement(cmd);
						//stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and SHOW_AT_TARGET like ?");	
			    		stmt.setString(1, target_thsms);
			    		stmt.setBoolean(2, false);
			    		stmt.setBoolean(3, false);
			    		rs = stmt.executeQuery();
			    		v = addHocFn(rs);
					}
					
					
					
		    		
				}
			}
			else {
				//liat riwayat individual
				
				if(am_i_stu) {
					stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=?");
					stmt.setString(1,target_thsms);
		    		stmt.setString(2, target_npmhs);
		    		rs = stmt.executeQuery();
		    		v = addHocFn(rs);
				}
				else {
					//target_obj atau yg scopenya termasuk mhs terkait
					//liat riwayet  
					if(boleh_liat_berdasarkan_scope) {
						stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and CREATOR_NPM=?");	
						stmt.setString(1, target_thsms);
						stmt.setString(2, target_npmhs);
			    		rs = stmt.executeQuery();
			    		v = addHocFn(rs);
					}
					else {
						//cuma target aja
						stmt = con.prepareStatement("select * from TOPIK_PENGAJUAN where TARGET_THSMS_PENGAJUAN=? and TOKEN_TARGET_OBJID like ?");	
						stmt.setString(1, target_thsms);
			    		stmt.setString(2, "%["+Checker.getObjectId(this.operatorNpm)+"]%");
			    		rs = stmt.executeQuery();
			    		v = addHocFn(rs);
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
    	
    	
    	return v;
    }
    
}
