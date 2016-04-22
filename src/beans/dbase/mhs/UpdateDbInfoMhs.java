package beans.dbase.mhs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import beans.dbase.UpdateDb;
import beans.setting.Constants;
import beans.sistem.AskSystem;
import beans.tools.Checker;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ListIterator;
/**
 * Session Bean implementation class UpdateDbInfoMhs
 */
@Stateless
@LocalBean
public class UpdateDbInfoMhs extends UpdateDb {
	String operatorNpm;
	String tknOperatorNickname;
	String operatorNmm;
	boolean petugas;
	String url;     
	Connection con;
	PreparedStatement stmt;
	ResultSet rs;
	DataSource ds;
    /**
     * @see UpdateDb#UpdateDb()
     */
    public UpdateDbInfoMhs() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbInfoMhs(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.tknOperatorNickname = getTknOprNickname();
    	//System.out.println("tknOperatorNickname1="+this.tknOperatorNickname);
    	this.petugas = cekApaUsrPetugas();
        // TODO Auto-generated constructor stub
    }
    
    public void updateTabelDaftarUlang(String getListMhsTrnlm, String thsms) {
    	Vector v = new Vector();;
    	ListIterator li = v.listIterator();
    	//istThsmsNpmhs =listThsmsNpmhs+"$"+kdpst+"$"+npmhs+"$"+nimhs+"$"+nmmhs+"$"+smawl+"$"+stmhs;
    	StringTokenizer st = new StringTokenizer(getListMhsTrnlm,"$");
    	String needByGetProfile = "";
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	select bila ada remove
    		//stmt = con.prepareStatement("select * from DAFTAR_ULANG where KDPST=? and NPMHS=?");
    		stmt = con.prepareStatement("select * from DAFTAR_ULANG where THSMS=? and KDPST=? and NPMHS=?");
    		while(st.hasMoreTokens()) {
    			String kdpst = st.nextToken();
    			String npmhs = st.nextToken();
    			String nimhs = st.nextToken();
    			String nmmhs = st.nextToken();
    			String smawl = st.nextToken();
    			String stmhs = st.nextToken();
    			stmt.setString(1, thsms);
    			stmt.setString(2, kdpst);
    			stmt.setString(3, npmhs);
    			rs = stmt.executeQuery();
    			if(!rs.next()) {
    				li.add(kdpst+"$"+npmhs+"$"+nimhs+"$"+nmmhs+"$"+smawl+"$"+stmhs);
    			}
    		}
    		
    		//insert ke daftar ulang
    		
    		stmt = con.prepareStatement("insert into DAFTAR_ULANG(THSMS,KDPST,NPMHS)values(?,?,?)");
    		li = v.listIterator();
    		String list_npm_inserted = "";
    		while(li.hasNext()) {
    			
    			String brs = (String)li.next();
    			st = new StringTokenizer(brs,"$");
    			String kdpst = st.nextToken();
    			String npmhs = st.nextToken();
    			String nimhs = st.nextToken();
    			String nmmhs = st.nextToken();
    			String smawl = st.nextToken();
    			String stmhs = st.nextToken();
    			stmt.setString(1, thsms);
    			stmt.setString(2, kdpst);
    			stmt.setString(3, npmhs);
    			stmt.executeUpdate();
    			list_npm_inserted = list_npm_inserted+"`"+npmhs+"`"+kdpst;
    		}
    		
    		//kalo ada yg diinsert == update Notification
    		if(!Checker.isStringNullOrEmpty(list_npm_inserted)) { 
    			//update notification
    			st = new StringTokenizer(list_npm_inserted,"`");
    			list_npm_inserted = new String();
    			stmt = con.prepareStatement("select ID_OBJ from CIVITAS where NPMHSMSMHS=?");
    			while(st.hasMoreTokens()) {
    				String npmhs = st.nextToken();
    				String kdpst = st.nextToken();
    				stmt.setString(1, npmhs);
    				rs = stmt.executeQuery();
    				rs.next();
    				long idobj = rs.getLong(1); 
    				list_npm_inserted = list_npm_inserted+npmhs+"`"+kdpst+"`"+idobj;
    				if(st.hasMoreTokens()) {
    					list_npm_inserted = list_npm_inserted+"`";
    				}
    			}
    			String list_need_insert = "";
    			String list_need_update = "";
    			if(!Checker.isStringNullOrEmpty(list_npm_inserted)) { 
    				st = new StringTokenizer(list_npm_inserted,"`");
        			stmt = con.prepareStatement("select * from DAFTAR_ULANG_NOTIFICATION where THSMS=? and ID_OBJ=?");
        			while(st.hasMoreTokens()) {
        				String npmhs = st.nextToken();
        				String kdpst = st.nextToken();
        				String idobj = st.nextToken();
        				stmt.setString(1, thsms);
        				stmt.setInt(2, Integer.parseInt(idobj));
        				rs = stmt.executeQuery();
        				if(rs.next()) {
        					String list_npm_wip = rs.getString("LIST_NPM_INPROGRESS");
        					boolean ada_pengajuan = rs.getBoolean("ADA_PENGAJUAN_INPROGRESS");
        					if(list_npm_wip==null) {
        						list_npm_wip = new String(npmhs);
        						list_need_update = list_need_update+"`"+npmhs+"`"+kdpst+"`"+idobj+"`"+list_npm_wip;
        					}
        					else if(!list_npm_wip.contains(npmhs)){
        						if(Checker.isStringNullOrEmpty(list_npm_wip)) {
        							list_npm_wip = new String(npmhs);
        							list_need_update = list_need_update+"`"+npmhs+"`"+kdpst+"`"+idobj+"`"+list_npm_wip;
        						}
        						else {
        							list_npm_wip = list_npm_wip+","+npmhs;
        							list_need_update = list_need_update+"`"+npmhs+"`"+kdpst+"`"+idobj+"`"+list_npm_wip;
        						}
        					}
        					else {
        						if(!ada_pengajuan) { //update juga set ada pengajun = true
        							list_need_update = list_need_update+"`"+npmhs+"`"+kdpst+"`"+idobj+"`"+list_npm_wip;
        						}
        					}
        				}
        				else {
        					list_need_insert = list_need_insert+"`"+npmhs+"`"+kdpst+"`"+idobj;
        				}
        			}	
        			
        			//update part
        			if(!Checker.isStringNullOrEmpty(list_need_update)) {
        				st = new StringTokenizer(list_need_update,"`");
        				stmt = con.prepareStatement("update DAFTAR_ULANG_NOTIFICATION set ADA_PENGAJUAN_INPROGRESS=?,LIST_NPM_INPROGRESS=? where THSMS=? and ID_OBJ=?");
        				while(st.hasMoreTokens()) {
        					String npmhs = st.nextToken();
            				String kdpst = st.nextToken();
            				String idobj = st.nextToken();
            				String list_npm_wip = st.nextToken();	
            				stmt.setBoolean(1, true);
            				stmt.setString(2, list_npm_wip);
            				stmt.setString(3, thsms);
            				stmt.setLong(4, Long.parseLong(idobj));
            				stmt.executeUpdate();
        				}
        				
        				
        			}
        			//insert part
        			if(!Checker.isStringNullOrEmpty(list_need_insert)) {
        				st = new StringTokenizer(list_need_update,"`");
        				stmt = con.prepareStatement("insert into DAFTAR_ULANG_NOTIFICATION(THSMS,ID_OBJ,ADA_PENGAJUAN_INPROGRESS,LIST_NPM_INPROGRESS)values(?,?,?,?)");
        				while(st.hasMoreTokens()) {
        					String npmhs = st.nextToken();
            				String kdpst = st.nextToken();
            				String idobj = st.nextToken();
            				
            				stmt.setString(1, thsms);
            				stmt.setLong(2, Long.parseLong(idobj));
            				stmt.setBoolean(3, true);
            				stmt.setString(4, npmhs);
            				stmt.executeUpdate();
        				}
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
    	//return needByGetProfile;
    }

    public void updateDaftarUlangTableByOperator(String kdpst, String npmhs) {
    	long objid = Checker.getObjectId(npmhs);
    	String thsms_herregistrasi=Checker.getThsmsHeregistrasi();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select * from DAFTAR_ULANG_RULES where THSMS=? and KDPST=?");
    		stmt.setString(1, thsms_herregistrasi);
    		stmt.setString(2, kdpst);
    		rs = stmt.executeQuery();
    		rs.next();
    		String tkn_ver = rs.getString("TKN_VERIFICATOR");
    		StringTokenizer st = new StringTokenizer(tkn_ver,",");
    		String verificator_match_nickname = null;
    		boolean match = false;
    		while(st.hasMoreTokens() && !match) {
    			String tmp_nickname = st.nextToken();
    			if(this.tknOperatorNickname.contains(tmp_nickname)) {
    				verificator_match_nickname = ""+tmp_nickname;
    				match = true;
    			}
    		}
    		if(!match) {
    			verificator_match_nickname = Constants.getDefaultNicknameUntukHeregistrasiByOperator();
    		}
    		stmt = con.prepareStatement("select * from DAFTAR_ULANG where THSMS=? and KDPST=? and NPMHS=?");
    		stmt.setString(1, thsms_herregistrasi);
    		stmt.setString(2, kdpst);
    		stmt.setString(3, npmhs);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			//sudah ada yg insert dan approve
    			String tgl_pengajuan = ""+rs.getTimestamp("TGL_PENGAJUAN");
    			String tkn_apr = ""+rs.getString("TOKEN_APPROVAL");
    			if(!tkn_apr.contains(verificator_match_nickname)) {
    				//artinya anda belum pernah approve--proses lanjutkan
    				stmt = con.prepareStatement("UPDATE DAFTAR_ULANG SET TOKEN_APPROVAL=? where THSMS=? and KDPST=? and NPMHS=?,");
    				if(tkn_apr!=null && !Checker.isStringNullOrEmpty(tkn_apr)) {
    					stmt.setString(1, "#"+this.operatorNpm+"#"+verificator_match_nickname+"#"+AskSystem.getCurrentTimestamp());
    				}
    				else {
    					stmt.setString(1, this.operatorNpm+"#"+verificator_match_nickname+"#"+AskSystem.getCurrentTimestamp());
    				}
    				stmt.setString(2, thsms_herregistrasi);
    				stmt.setString(3, kdpst);
    				stmt.setString(4, npmhs);
    				stmt.executeUpdate();
    			}
    		}
    		else {
    			//belum pernah ajukan jadi insert
    			stmt = con.prepareStatement("INSERT INTO DAFTAR_ULANG (THSMS,KDPST,NPMHS,TGL_PENGAJUAN,TOKEN_APPROVAL,ID_OBJ)values(?,?,?,?,?,?)");
    			stmt.setString(1,thsms_herregistrasi );
    			stmt.setString(2, kdpst);
    			stmt.setString(3, npmhs);
    			stmt.setTimestamp(4, AskSystem.getCurrentTimestamp());
    			stmt.setString(5, this.operatorNpm+"#"+verificator_match_nickname+"#"+AskSystem.getCurrentTimestamp());
    			stmt.setLong(6, objid);
    			stmt.executeUpdate();
    		}
    		//update
    		//DAFTAR_ULANG_NOTIFICATION
    		//get list npm inprog rec
    		/*
    		stmt = con.prepareStatement("select LIST_NPM_INPROGRESS from DAFTAR_ULANG_NOTIFICATION where THSMS=? and ID_OBJ=?");
    		stmt.setString(1, thsms_herregistrasi);
    		stmt.setLong(2, objid);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String list_npm = ""+rs.getString(1);
    			if(Checker.isStringNullOrEmpty(list_npm)) {
    				list_npm = new String(npmhs);
    			}
    			else {
    				if(!list_npm.contains(npmhs)) {
    					list_npm = list_npm+"`"+npmhs;
    					//update
    					stmt = con.prepareStatement("update DAFTAR_ULANG_NOTIFICATION set ADA_PENGAJUAN_INPROGRESS=?,LIST_NPM_INPROGRESS=? where THSMS=? and ID_OBJ=?");
    				    stmt.setBoolean(1, true);
    				    stmt.setString(2, list_npm);
    				    stmt.setString(3, thsms_herregistrasi);
    				    stmt.setLong(4, objid);;
    				    stmt.executeUpdate();
    				}
    				else {
    					//sudah ada = ignore saja
    				}
    			}
    		}
    		else {
    			//insert
    			stmt = con.prepareStatement("insert into DAFTAR_ULANG_NOTIFICATION(THSMS,ID_OBJ,ADA_PENGAJUAN_INPROGRESS,LIST_NPM_INPROGRESS)values(?,?,?,?)");
    			stmt.setString(1, thsms_herregistrasi);
    			stmt.setLong(2, objid);
    			stmt.setBoolean(3, true);
    			stmt.setString(4, npmhs);
    			stmt.executeUpdate();
    		}
    		*/
    		
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
    
    public void updateCekListTabelDaftarUlang(String[]lisNpmUsrIdUsrNic, String thsms_registrasi) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	select bila ada remove
    		stmt = con.prepareStatement("select * from DAFTAR_ULANG where THSMS=? and KDPST=? and NPMHS=?");
    		for(int i=0;i<lisNpmUsrIdUsrNic.length;i++) {
    			//kdpst+"#"+npmhs+"#"+validUsr.getNpm()+"#"+nickMatch 
    			StringTokenizer st = new StringTokenizer(lisNpmUsrIdUsrNic[i],"#");
    			//System.out.println("lisNpmUsrIdUsrNic[i]="+lisNpmUsrIdUsrNic[i]);
    			String kdpst = st.nextToken();
    			String npmhs = st.nextToken();
    			String npmOperator = st.nextToken();
    			String nickname = st.nextToken();
    			stmt.setString(1, thsms_registrasi);
    			stmt.setString(2, kdpst);
    			stmt.setString(3, npmhs);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				String tknApr= rs.getString("TOKEN_APPROVAL");
    				if(tknApr==null || !tknApr.contains(nickname)) {
    					li.add(lisNpmUsrIdUsrNic[i]);
    				}
    			}
    		}
    		
    		stmt = con.prepareStatement("update DAFTAR_ULANG set TOKEN_APPROVAL=concat(ifnull(TOKEN_APPROVAL,?),?) where THSMS=? and KDPST=? and NPMHS=?");
    		li = v.listIterator();
    		while(li.hasNext()){
    			String brs = (String)li.next();
    			//System.out.println("brs"+brs);
    			StringTokenizer st = new StringTokenizer(brs,"#");
    			String kdpst = st.nextToken();
    			String npmhs = st.nextToken();
    			String npmOperator = st.nextToken();
    			String nickname = st.nextToken();
    			String valueIfNull = "";
    			String valueNotNull = "#"+npmOperator+"#"+nickname+"#"+AskSystem.getCurrentTimestamp();
    			stmt.setString(1, valueIfNull);
    			stmt.setString(2, valueNotNull);
    			stmt.setString(3, thsms_registrasi);
    			stmt.setString(4, kdpst);
    			stmt.setString(5, npmhs);
    			stmt.executeUpdate();
    		}
    		//cek all approved
    		li = v.listIterator();
    		while(li.hasNext()){
    			String brs = (String)li.next();
    			//System.out.println("brs"+brs);
    			StringTokenizer st = new StringTokenizer(brs,"#");
    			String kdpst = st.nextToken();
    			String npmhs = st.nextToken();
    			String psn = Checker.sudahDaftarUlang(kdpst, npmhs, thsms_registrasi);
    			if(psn!=null) {//remove stil in progress
    				li.remove();
    			}
    		}
    		if(v!=null && v.size()>0) {
    			li = v.listIterator();
    			stmt = con.prepareStatement("update DAFTAR_ULANG set ALL_APPROVED=? where THSMS=? and NPMHS=?");
        		while(li.hasNext()){
        			String brs = (String)li.next();
        			//System.out.println("brs"+brs);
        			StringTokenizer st = new StringTokenizer(brs,"#");
        			String kdpst = st.nextToken();
        			String npmhs = st.nextToken();
        			stmt.setBoolean(1, true);
        			stmt.setString(2, thsms_registrasi);
        			stmt.setString(3, npmhs);
        			stmt.executeUpdate();
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
    	//return needByGetProfile;
    }
    
    public void setNullCekListTabelDaftarUlang(String[]lisNpmUsrIdUsrNic, String thsms_registrasi) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	select bila ada remove only objek usr saat ini
    		//stmt = con.prepareStatement("update DAFTAR_ULANG set TOKEN_APPROVAL=? where THSMS=?");
    		//stmt.setNull(1, java.sql.Types.VARCHAR);
    		//stmt.setString(2, thsms_registrasi);
    		//stmt.executeUpdate();
    		String nuVal="";
    		//1. proces yg di uncheck
    		stmt = con.prepareStatement("select * from DAFTAR_ULANG WHERE THSMS=?");
    		stmt.setString(1, thsms_registrasi);
    		rs = stmt.executeQuery();
    		while(rs.next()) {
    			String kdpst0 = rs.getString("KDPST");
    			String npmhs0 = rs.getString("NPMHS");
    			String tknApr0 = rs.getString("TOKEN_APPROVAL");
    			boolean match = false;
    			if(lisNpmUsrIdUsrNic!=null && lisNpmUsrIdUsrNic.length>0) {
    				for(int i=0;i<lisNpmUsrIdUsrNic.length  && !match;i++) {
        			//kdpst+"#"+npmhs+"#"+validUsr.getNpm()+"#"+nickMatch 
    					StringTokenizer st = new StringTokenizer(lisNpmUsrIdUsrNic[i],"#");
        			//System.out.println("lisNpmUsrIdUsrNic[i]="+lisNpmUsrIdUsrNic[i]);
    					while(st.hasMoreTokens()) {
    						String kdpst = st.nextToken();
    						String npmhs = st.nextToken();
    						String npmOperator = st.nextToken();
    						String nickname = st.nextToken();
    						if(npmhs.equalsIgnoreCase(npmhs0)) {
    							match = true;
    						}
    					}
        			
    				}	
    				if(!match) {
    					li.add(kdpst0+"$"+npmhs0+"$"+tknApr0);
    				}
    			}
    			else{
    				li.add(kdpst0+"$"+npmhs0+"$"+tknApr0);
    			}
    		}
    		
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"$");
    			String kdpst = st.nextToken();
    			String npmhs = st.nextToken();
    			String tknApr = st.nextToken();
    			if(tknApr!=null && !Checker.isStringNullOrEmpty(tknApr)) {
					StringTokenizer stmp = new StringTokenizer(tknApr,"#");
					boolean first = true;
					while(stmp.hasMoreTokens()) {
						String oprNpm = stmp.nextToken();
						String oprNick = stmp.nextToken();
						String tmstm = stmp.nextToken();
						//System.out.print("tknOperatorNickname=");
						//System.out.println(this.tknOperatorNickname);
						//System.out.println("oprNick="+oprNick);
						if(!this.tknOperatorNickname.contains(oprNick)) {
							if(first) {
								first = false;
								nuVal=oprNpm+"#"+oprNick+"#"+tmstm;
							}
							else {
								nuVal=nuVal+"#"+oprNpm+"#"+oprNick+"#"+tmstm;
							}
						}	
					}
				}
				else {
					nuVal=tknApr;
				}
				if(Checker.isStringNullOrEmpty(nuVal)) {
					nuVal="null";
				}
				li.set(kdpst+"$"+npmhs+"$"+nuVal);
    		}
    		
    		
    		stmt = con.prepareStatement("update DAFTAR_ULANG set TOKEN_APPROVAL=? where THSMS=?and KDPST=? and NPMHS=?");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();
    			StringTokenizer st = new StringTokenizer(brs,"$");
    			String kdpst = st.nextToken();
    			String npmhs = st.nextToken();
    			String nuValTknApr = st.nextToken();
    			//System.out.println("nuValTknApr="+nuValTknApr);
    			if(Checker.isStringNullOrEmpty(nuValTknApr)) {
    				stmt.setNull(1,java.sql.Types.VARCHAR);
    			}
    			else {
    				stmt.setString(1,nuValTknApr);
    			}	
    			stmt.setString(2,thsms_registrasi);
    			stmt.setString(3,kdpst);
    			stmt.setString(4,npmhs);
    			stmt.executeUpdate();
    		}
    		
    		//2.process yg di cek
    		v = new Vector();
    		stmt=con.prepareStatement("select * from DAFTAR_ULANG where THSMS=? and KDPST=? and NPMHS=?");
    		if(lisNpmUsrIdUsrNic!=null && lisNpmUsrIdUsrNic.length>0) {
    			for(int i=0;i<lisNpmUsrIdUsrNic.length;i++) {
    			//kdpst+"#"+npmhs+"#"+validUsr.getNpm()+"#"+nickMatch 
    				StringTokenizer st = new StringTokenizer(lisNpmUsrIdUsrNic[i],"#");
    				//System.out.println("lisNpmUsrIdUsrNic[i]="+lisNpmUsrIdUsrNic[i]);
    				boolean first = true;
    				while(st.hasMoreTokens()) {
    					String kdpst = st.nextToken();
    					String npmhs = st.nextToken();
    					String npmOperator = st.nextToken();
    					String nickname = st.nextToken();
    			
    					stmt.setString(1, thsms_registrasi);
    					stmt.setString(2, kdpst);
    					stmt.setString(3, npmhs);
    					//System.out.println(thsms_registrasi+","+kdpst+","+npmhs);
    					rs = stmt.executeQuery();
    					//System.out.println("query");
    					rs.next();
    					String tknApr=rs.getString("TOKEN_APPROVAL");
    					//System.out.println("tknApr="+tknApr);
    					if(tknApr!=null && !Checker.isStringNullOrEmpty(tknApr) && tknApr.contains(nickname)) {
    						StringTokenizer stmp = new StringTokenizer(tknApr,"#");
    						while(stmp.hasMoreTokens()) {
    							String oprNpm = stmp.nextToken();
    							String oprNick = stmp.nextToken();
    							String tmstm = stmp.nextToken();
    							//System.out.print("tknOperatorNickname=");
    							
    							//System.out.println(this.tknOperatorNickname);
    							//System.out.println("oprNick="+oprNick);
    							if(!this.tknOperatorNickname.contains(oprNick)) {
    								if(first) {
    									first = false;
    									nuVal=oprNpm+"#"+oprNick+"#"+tmstm;
    								}
    								else {
    									nuVal=nuVal+"#"+oprNpm+"#"+oprNick+"#"+tmstm;
    								}
    							}	
    						}
    					}
    					else {
    						nuVal=tknApr;
    					}
    					if(Checker.isStringNullOrEmpty(nuVal)) {
    						nuVal="null";
    					}
    					li.add(kdpst+"$"+npmhs+"$"+npmOperator+"$"+nickname+"$"+nuVal);
    					//System.out.println("add="+kdpst+"$"+npmhs+"$"+npmOperator+"$"+nickname+"$"+nuVal);
    				}
    			}
    		}	
    		//else {
    			
    		//}
    		
    		stmt = con.prepareStatement("update DAFTAR_ULANG set TOKEN_APPROVAL=? where THSMS=?and KDPST=? and NPMHS=?");
    		li = v.listIterator();
    		while(li.hasNext()) {
    			String brs = (String)li.next();//System.out.println("brs2="+brs);
    			StringTokenizer st = new StringTokenizer(brs,"$");
    			String kdpst = st.nextToken();
    			String npmhs = st.nextToken();
    			String npmOperator = st.nextToken();
    			String nickname = st.nextToken();
    			String nuValTknApr = st.nextToken();
    			//System.out.println("nuValTknApr="+nuValTknApr);
    			if(Checker.isStringNullOrEmpty(nuValTknApr)) {
    				stmt.setNull(1,java.sql.Types.VARCHAR);
    			}
    			else {
    				stmt.setString(1,nuValTknApr);
    			}	
    			stmt.setString(2,thsms_registrasi);
    			stmt.setString(3,kdpst);
    			stmt.setString(4,npmhs);
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
    	//return needByGetProfile;
    }
    

	
    public int updateStatusKartuUjian(String thsms,String kdpst, String npmhs, String targetUjian, String nuTknKartuUjianValue, 	String nuTknApprKartuUjianValue, String nuTknStatusValue) {
    	int upd=0;
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//	select bila ada remove
    		stmt = con.prepareStatement("update DAFTAR_ULANG set TOKEN_KARTU_UJIAN=?,TOKEN_APPROVAL_KARTU_UJIAN=?,STATUS_APPROVAL_KARTU_UJIAN=? where THSMS=? and KDPST=? and NPMHS=?");
    		stmt.setString(1, nuTknKartuUjianValue);
    		stmt.setString(2, nuTknApprKartuUjianValue);
    		stmt.setString(3, nuTknStatusValue);
    		stmt.setString(4, thsms);
    		stmt.setString(5, kdpst);
    		stmt.setString(6, npmhs);
    		//System.out.println(thsms+","+kdpst+","+npmhs+","+targetUjian+","+nuTknKartuUjianValue+","+nuTknApprKartuUjianValue+","+nuTknStatusValue);
    		upd = stmt.executeUpdate();
    		//System.out.println("updated upd = "+upd);
    		
    		//stmt.executeUpdate();
    		//con.commit();
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
    	return upd;
    }
    
    public Vector mergeDataCivitas(String npmYgMoDiMerge, String npmTujuanMerge) {
    	Vector v = new Vector();
    	//System.out.println("npmYgMoDiMerge="+npmYgMoDiMerge);
    	//System.out.println("npmTujuanMerge="+npmTujuanMerge);
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select KDPSTMSMHS from CIVITAS where NPMHSMSMHS=?");
    		stmt.setString(1, npmTujuanMerge);
    		rs = stmt.executeQuery();
    		rs.next();
    		String kdpstTujuanMerge = rs.getString(1);
    		//	get data dari npmYgMoDiMerge
    		/*1. get info dari tabel civitas -- ngga jadi tidak dibutuuhkan
    		
    		stmt = con.prepareStatement("select * from CIVITAS where NPMHSMSMHS=?");
    		stmt.setString(1, npmYgMoDiMerge);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String id = ""+rs.getInt("ID");
    			String id_obj = ""+rs.getInt("ID_OBJ");
    			String kdpti = ""+rs.getString("KDPTIMSMHS");
    			String kdjen = ""+rs.getString("KDJENMSMHS");
    			String kdpst = ""+rs.getString("KDPSTMSMHS");
    			String npmhs = ""+rs.getString("NPMHSMSMHS");
    			String nimhs = ""+rs.getString("NIMHSMSMHS");
    			String nmmhs = ""+rs.getString("NMMHSMSMHS");
    			String shift = ""+rs.getString("SHIFTMSMHS");
    			String tplhr = ""+rs.getString("TPLHRMSMHS");
    			String tglhr = ""+rs.getDate("TGLHRMSMHS");
    			String kdjek = ""+rs.getString("KDJEKMSMHS");
    			String tahun = ""+rs.getString("TAHUNMSMHS");
    			String smawl = ""+rs.getString("SMAWLMSMHS");
    			String btstu = ""+rs.getString("BTSTUMSMHS");
    			String assma = ""+rs.getString("ASSMAMSMHS");
    			String tgmsk = ""+rs.getDate("TGMSKMSMHS");
    			String tglls = ""+rs.getDate("TGLLSMSMHS");
    			String stmhs = ""+rs.getString("STMHSMSMHS");
    			String stpid = ""+rs.getString("STPIDMSMHS");
    			String sksdi = ""+rs.getFloat("SKSDIMSMHS");
    			String asnim = ""+rs.getString("ASNIMMSMHS");
    			String aspti = ""+rs.getString("ASPTIMSMHS");
    			String asjen = ""+rs.getString("ASJENMSMHS");
    			String aspst = ""+rs.getString("ASPSTMSMHS");
    			String bistu = ""+rs.getString("BISTUMSMHS");
    			String peksb = ""+rs.getString("PEKSBMSMHS");
    			String nmpek = ""+rs.getString("NMPEKMSMHS");
    			String ptpek = ""+rs.getString("PTPEKMSMHS");
    			String pspek = ""+rs.getString("PSPEKMSMHS");
    			String noprm = ""+rs.getString("NOPRMMSMHS");
    			String nokp1 = ""+rs.getString("NOKP1MSMHS");
    			String nokp2 = ""+rs.getString("NOKP2MSMHS");
    			String nokp3 = ""+rs.getString("NOKP3MSMHS");
    			String nokp4 = ""+rs.getString("NOKP4MSMHS");
    			String updtm = ""+rs.getTimestamp("UPDTMMSMHS");
    			String gelom = ""+rs.getString("GELOMMSMHS");
    			
    			li.add(id+"`"+id_obj+"`"+kdpti+"`"+kdjen+"`"+kdpst+"`"+npmhs+"`"+nimhs+"`"+nmmhs+"`"+shift+"`"+tplhr+"`"+tglhr+"`"+kdjek+"`"+tahun+"`"+smawl+"`"+btstu+"`"+assma+"`"+tgmsk+"`"+tglls+"`"+stmhs+"`"+stpid+"`"+sksdi+"`"+asnim+"`"+aspti+"`"+asjen+"`"+aspst+"`"+bistu+"`"+peksb+"`"+nmpek+"`"+ptpek+"`"+pspek+"`"+noprm+"`"+nokp1+"`"+nokp2+"`"+nokp3+"`"+nokp4+"`"+updtm+"`"+gelom);
    		
    		}
    		else {
    			li.add("null");
    		}
    		
    		//1.tabel EXT_CIVITAS -- ngga jadi
    		stmt = con.prepareStatement("select * from EXT_CIVITAS where NPMHSMSMHS=?");
    		stmt.setString(1, npmYgMoDiMerge);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String kdpst = ""+rs.getString("KDPSTMSMHS");
        	    String npmhs = ""+rs.getString("NPMHSMSMHS");
        	    String sttus = ""+rs.getString("STTUSMSMHS");
        	    String email = ""+rs.getString("EMAILMSMHS");
        	    String nohape = ""+rs.getString("NOHPEMSMHS");
        	    String almrm = ""+rs.getString("ALMRMMSMHS");
        	    String kotrm = ""+rs.getString("KOTRMMSMHS");
        	    String posrm = ""+rs.getString("POSRMMSMHS");
        	    String telrm = ""+rs.getString("TELRMMSMHS");
        	    String almkt = ""+rs.getString("ALMKTMSMHS");
        	    String kotkt = ""+rs.getString("KOTKTMSMHS");
        	    String poskt = ""+rs.getString("POSKTMSMHS");
        	    String telkt = ""+rs.getString("TELKTMSMHS");
        	    String jbtkt = ""+rs.getString("JBTKTMSMHS");
        	    String bidkt = ""+rs.getString("BIDKTMSMHS");
        	    String jenkt = ""+rs.getString("JENKTMSMHS");
        	    String nmmsp = ""+rs.getString("NMMSPMSMHS");
        	    String almsp = ""+rs.getString("ALMSPMSMHS");
        	    String possp = ""+rs.getString("POSSPMSMHS");
        	    String kotsp = ""+rs.getString("KOTSPMSMHS");
        	    String negsp = ""+rs.getString("NEGSPMSMHS");
        	    String telsp = ""+rs.getString("TELSPMSMHS");
        	    String neglh = ""+rs.getString("NEGLHMSMHS");
        	    String agama = ""+rs.getString("AGAMAMSMHS");
        	    String krklm = ""+rs.getString("KRKLMMSMHS");
        	    String ttlog = ""+rs.getInt("TTLOGMSMHS");
        	    String tmlog = ""+rs.getInt("TMLOGMSMHS");
        	    String dtlog = ""+rs.getTimestamp("DTLOGMSMHS");
        	    String updtm = ""+rs.getTimestamp("UPDTMMSMHS");
        	    String idpaketbea = ""+rs.getInt("IDPAKETBEASISWA");
        	    String npmpa = ""+rs.getString("NPM_PA");
        	    String nmmpa = ""+rs.getString("NMM_PA");
        	    String petugas = ""+rs.getBoolean("PETUGAS");
        	    String assma = ""+rs.getString("ASAL_SMA");
        	    String kotsma = ""+rs.getString("KOTA_SMA");
        	    String thLulusSma = ""+rs.getString("LULUS_SMA");
        	    li.add(kdpst+"`"+npmhs+"`"+sttus+"`"+email+"`"+nohape+"`"+almrm+"`"+kotrm+"`"+posrm+"`"+telrm+"`"+almkt+"`"+kotkt+"`"+poskt+"`"+telkt+"`"+jbtkt+"`"+bidkt+"`"+jenkt+"`"+nmmsp+"`"+almsp+"`"+possp+"`"+kotsp+"`"+negsp+"`"+telsp+"`"+neglh+"`"+agama+"`"+krklm+"`"+ttlog+"`"+tmlog+"`"+dtlog+"`"+updtm+"`"+idpaketbea+"`"+npmpa+"`"+nmmpa+"`"+petugas+"`"+assma+"`"+kotsma+"`"+thLulusSma);
    		}
    		else {
    			li.add("null");
    		}
    		*/
    		//1.tabel EXT_CIVITAS_DATA_DOSEM
    		stmt = con.prepareStatement("select * from EXT_CIVITAS_DATA_DOSEN where NPMHS=?");
    		stmt.setString(1, npmYgMoDiMerge);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String kdpst = ""+rs.getString("KDPST");
        	    String npmhs = ""+rs.getString("NPMHS");
        	    String nodos_loco = ""+rs.getString("NODOS_LOCAL");
        	    String gelardp = ""+rs.getString("GELAR_DEPAN");
        	    String gelarbl = ""+rs.getString("GELAR_BELAKANG");
        	    String nidnn = ""+rs.getString("NIDNN");
        	    String tipeid = ""+rs.getString("TIPE_ID");
        	    String nomid = ""+rs.getString("NOMOR_ID");
        	    String status = ""+rs.getString("STATUS");
        	    String pt1 = ""+rs.getString("PT_S1");
        	    String jur1 = ""+rs.getString("JURUSAN_S1");
        	    String kdpst1 = ""+rs.getString("KDPST_S1");
        	    String gelar1 = ""+rs.getString("GELAR_S1");
        	    String bidil1 = ""+rs.getString("TKN_BIDANG_KEAHLIAN_S1");
        	    String noija1 = ""+rs.getString("NOIJA_S1");
        	    String tglls1 = ""+rs.getDate("TGLLS_S1");
        	    String fileija1 = ""+rs.getString("FILE_IJA_S1");
        	    String judul1 = ""+rs.getString("JUDUL_TA_S1");
        	    String pt2 = ""+rs.getString("PT_S2");
        	    String jur2 = ""+rs.getString("JURUSAN_S2");
        	    String kdpst2 = ""+rs.getString("KDPST_S2");
        	    String gelar2 = ""+rs.getString("GELAR_S2");
        	    String bidil2 = ""+rs.getString("TKN_BIDANG_KEAHLIAN_S2");
        	    String noija2 = ""+rs.getString("NOIJA_S2");
        	    String tglls2 = ""+rs.getDate("TGLLS_S2");
        	    String fileija2 = ""+rs.getString("FILE_IJA_S2");
        	    String judul2 = ""+rs.getString("JUDUL_TA_S2");
        	    String pt3 = ""+rs.getString("PT_S3");
        	    String jur3 = ""+rs.getString("JURUSAN_S3");
        	    String kdpst3 = ""+rs.getString("KDPST_S3");
        	    String gelar3 = ""+rs.getString("GELAR_S3");
        	    String bidil3 = ""+rs.getString("TKN_BIDANG_KEAHLIAN_S3");
        	    String noija3 = ""+rs.getString("NOIJA_S3");
        	    String tglls3 = ""+rs.getDate("TGLLS_S3");
        	    String fileija3 = ""+rs.getString("FILE_IJA_S3");
        	    String judul3 = ""+rs.getString("JUDUL_TA_S3");
        	    String pt4 = ""+rs.getString("PT_PROF");
        	    String jur4 = ""+rs.getString("JURUSAN_PROF");
        	    String kdpst4 = ""+rs.getString("KDPST_PROF");
        	    String gelar4 = ""+rs.getString("GELAR_PROF");
        	    String bidil4 = ""+rs.getString("TKN_BIDANG_KEAHLIAN_PROF");
        	    String noija4 = ""+rs.getString("NOIJA_PROF");
        	    String tglls4 = ""+rs.getDate("TGLLS_PROF");
        	    String fileija4 = ""+rs.getString("FILE_IJA_PROF");
        	    String judul4 = ""+rs.getString("JUDUL_TA_PROF");
        	    
        	    String ttkum = ""+rs.getInt("TOTAL_KUM");
        	    String jabak = ""+rs.getString("JABATAN_AKADEMIK_DIKTI");
        	    String jabak_loco = ""+rs.getString("JABATAN_AKADEMIK_LOCAL");
        	    String jabstruk = ""+rs.getString("JABATAN_STRUKTURAL");
        	    String ika = ""+rs.getString("IKATAN_KERJA_DOSEN");
        	    String tgmsk = ""+rs.getDate("TANGGAL_MULAI_KERJA");
        	    String tgklr = ""+rs.getDate("TANGGAL_KELUAR_KERJA");
        	    String serdos = ""+rs.getString("SERDOS");
        	    String kdpti_homebase = ""+rs.getString("KDPTI_HOMEBASE");
        	    String kdpst_homebase = ""+rs.getString("KDPST_HOMEBASE");
        	    String email_loco = ""+rs.getString("EMAIL_INSTITUSI");
        	    String pangkat = ""+rs.getString("PANGKAT_GOLONGAN");
        	    String note_history = ""+rs.getString("CATATAN_RIWAYAT");
        	    String tipe_id = ""+rs.getString("TIPE_KTP");
        	    String no_id = ""+rs.getString("NO_KTP");
        	    
        	    
        	    li.add(kdpst+"`"+npmhs+"`"+nodos_loco+"`"+gelardp+"`"+gelarbl+"`"+nidnn+"`"+tipeid+"`"+nomid+"`"+status+"`"+pt1+"`"+jur1+"`"+kdpst1+"`"+gelar1+"`"+bidil1+"`"+noija1+"`"+tglls1+"`"+fileija1+"`"+judul1+"`"+pt2+"`"+jur2+"`"+kdpst2+"`"+gelar2+"`"+bidil2+"`"+noija2+"`"+tglls2+"`"+fileija2+"`"+judul2+"`"+pt3+"`"+jur3+"`"+kdpst3+"`"+gelar3+"`"+bidil3+"`"+noija3+"`"+tglls3+"`"+fileija3+"`"+judul3+"`"+pt4+"`"+jur4+"`"+kdpst4+"`"+gelar4+"`"+bidil4+"`"+noija4+"`"+tglls4+"`"+fileija4+"`"+judul4+"`"+ttkum+"`"+jabak+"`"+jabak_loco+"`"+jabstruk+"`"+ika+"`"+tgmsk+"`"+tgklr+"`"+serdos+"`"+kdpti_homebase+"`"+kdpst_homebase+"`"+email_loco+"`"+pangkat+"`"+note_history+"`"+tipe_id+"`"+no_id);
    		}
    		else {
    			li.add("null");
    		}

    		
    		//2.tabel TRNLM
    		stmt = con.prepareStatement("select * from TRNLM where NPMHSTRNLM=?");
    		stmt.setString(1, npmYgMoDiMerge);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String brs = "";
    			do {
    				String thsms = ""+rs.getString("THSMSTRNLM");
        			String kdpti = ""+rs.getString("KDPTITRNLM");
        			String kdjen = ""+rs.getString("KDJENTRNLM");
        			String kdpst = ""+rs.getString("KDPSTTRNLM");
        			String npmhs = ""+rs.getString("NPMHSTRNLM");
        			String kdkmk = ""+rs.getString("KDKMKTRNLM");
        			String nilai = ""+rs.getFloat("NILAITRNLM");
        			String nlakh = ""+rs.getString("NLAKHTRNLM");
        			String bobot = ""+rs.getFloat("BOBOTTRNLM");
        			String skskmk = ""+rs.getInt("SKSMKTRNLM");
        			String kelas = ""+rs.getString("KELASTRNLM");
        			String updtm = ""+rs.getString("UPDTMTRNLM");
        			String krsdown = ""+rs.getBoolean("KRSDONWLOADED");
        			String khsdown = ""+rs.getBoolean("KHSDONWLOADED");
        			String bak_apr = ""+rs.getBoolean("BAK_APPROVAL");
        			String shift_kls = ""+rs.getString("SHIFTTRNLM");
        			String pa_apr = ""+rs.getBoolean("PA_APPROVAL");
        			String note = ""+rs.getString("NOTE");
        			String lock = ""+rs.getBoolean("LOCKTRNLM");
        			String bauk_apr = ""+rs.getBoolean("BAUK_APPROVAL");
        			String idkmk = ""+rs.getInt("IDKMKTRNLM");
        			String add_req = ""+rs.getBoolean("ADD_REQ");
        			String drp_req = ""+rs.getBoolean("DRP_REQ");
        			String npm_pa = ""+rs.getString("NPM_PA");
        			String npm_bak = ""+rs.getString("NPM_BAK");
        			String npm_baa = ""+rs.getString("NPM_BAA");
        			String npm_bauk = ""+rs.getString("NPM_BAUK");
        			String baa_apr = ""+rs.getBoolean("BAA_APPROVAL");
        			String ktu_apr = ""+rs.getBoolean("KTU_APPROVAL");
        			String dkn_apr = ""+rs.getBoolean("DEKAN_APPROVAL");
        			String npm_ktu = ""+rs.getString("NPM_KTU");
        			String npm_dkn = ""+rs.getString("NPM_DEKAN");
        			String lockMhs = ""+rs.getBoolean("LOCKMHS");
        			String kode_kmp = ""+rs.getString("KODE_KAMPUS");
        			String cuid = ""+rs.getInt("CLASS_POOL_UNIQUE_ID");
        			brs = brs+thsms+"`"+kdpti+"`"+kdjen+"`"+kdpst+"`"+npmhs+"`"+kdkmk+"`"+nilai+"`"+nlakh+"`"+bobot+"`"+skskmk+"`"+kelas+"`"+updtm+"`"+krsdown+"`"+khsdown+"`"+bak_apr+"`"+shift_kls+"`"+pa_apr+"`"+note+"`"+lock+"`"+bauk_apr+"`"+idkmk+"`"+add_req+"`"+drp_req+"`"+npm_pa+"`"+npm_bak+"`"+npm_baa+"`"+npm_bauk+"`"+baa_apr+"`"+ktu_apr+"`"+dkn_apr+"`"+npm_ktu+"`"+npm_dkn+"`"+lockMhs+"`"+kode_kmp+"`"+cuid+"`";
        		}
    			while(rs.next());
    			li.add(brs);
    		}
    		else {
    			li.add("null");
    		}
    		
    		
    		//3.tabel TRNLP
    		stmt = con.prepareStatement("select * from TRNLP where NPMHSTRNLP=?");
    		stmt.setString(1, npmYgMoDiMerge);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String brs = "";
    			do {
    				String thsms = ""+rs.getString("THSMSTRNLP");
    				String kdpst = ""+rs.getString("KDPSTTRNLP");
    				String npmhs = ""+rs.getString("NPMHSTRNLP");
    				String kdkmk = ""+rs.getString("KDKMKTRNLP");
    				String nlakh = ""+rs.getString("NLAKHTRNLP");
    				String bobot = ""+rs.getFloat("BOBOTTRNLP");
    				String sksmk = ""+rs.getInt("SKSMKTRNLP");
    				String kdkmk_as = ""+rs.getString("KDKMKASALP");
    				String nakmk_as = ""+rs.getString("NAKMKASALP");
    				String sksmk_as = ""+rs.getInt("SKSMKASAL");
    				String keter = ""+rs.getString("KETERTRNLP");
    				String updtm = ""+rs.getTimestamp("UPDTMTRNLP");
    				String transfered = ""+rs.getBoolean("TRANSFERRED");
    				String approved = ""+rs.getString("APPROVED");
    			
    				brs = brs+thsms+"`"+kdpst+"`"+npmhs+"`"+kdkmk+"`"+nlakh+"`"+bobot+"`"+sksmk+"`"+kdkmk_as+"`"+nakmk_as+"`"+sksmk_as+"`"+keter+"`"+updtm+"`"+transfered+"`"+approved+"`";
    			}
    			while(rs.next());
    			li.add(brs);
    		}
    		else {
    			li.add("null");
    		}

    		
    		//4.tabel TRLSM
    		stmt = con.prepareStatement("select * from TRLSM where NPMHS=?");
    		stmt.setString(1, npmYgMoDiMerge);
    		rs = stmt.executeQuery();
    		if(rs.next()) {
    			String brs = "";
    			do {
    				String thsms = ""+rs.getString("THSMS");
        			String kdpst = ""+rs.getString("KDPST");
        			String npmhs = ""+rs.getString("NPMHS");
        			String stmhs = ""+rs.getString("STMHS");
        			String tgl_aju = ""+rs.getTimestamp("TGL_PENGAJUAN");
        			String tkn_apr = ""+rs.getString("TOKEN_APPROVEE");
        			
        			brs = brs + thsms+"`"+kdpst+"`"+npmhs+"`"+stmhs+"`"+tgl_aju+"`"+tkn_apr+"`";
    			}
    			while(rs.next());
    			li.add(brs);
    		}
    		else {
    			li.add("null");
    		}
    		
    		//start process merging
    		if(v!=null && v.size()>0) {
    			try {
    				li = v.listIterator();
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				//System.out.println("brs1 = "+brs);
        				if(!brs.equalsIgnoreCase("null")) {
        					//1. merge ext data dosen date
        					//stmt = con.prepareStatement("UPDATE CIVITAS SET ID=?,ID_OBJ=?,KDPTIMSMHS=?,KDJENMSMHS=?,KDPSTMSMHS=?,NPMHSMSMHS=?,NIMHSMSMHS=?,NMMHSMSMHS=?,SHIFTMSMHS=?,TPLHRMSMHS=?,TGLHRMSMHS=?,KDJEKMSMHS=?,TAHUNMSMHS=?,SMAWLMSMHS=?,BTSTUMSMHS=?,ASSMAMSMHS=?,TGMSKMSMHS=?,TGLLSMSMHS=?,STMHSMSMHS=?,STPIDMSMHS=?,SKSDIMSMHS=?,ASNIMMSMHS=?,ASPTIMSMHS=?,ASJENMSMHS=?,ASPSTMSMHS=?,BISTUMSMHS=?,PEKSBMSMHS=?,NMPEKMSMHS=?,PTPEKMSMHS=?,PSPEKMSMHS=?,NOPRMMSMHS=?,NOKP1MSMHS=?,NOKP2MSMHS=?,NOKP3MSMHS=?,NOKP4MSMHS=?,UPDTMMSMHS=?,GELOMMSMHS=? WHERE NPMHSMSMHS=?");
        					stmt = con.prepareStatement("UPDATE EXT_CIVITAS_DATA_DOSEN SET NODOS_LOCAL=?,GELAR_DEPAN=?,GELAR_BELAKANG=?,NIDNN=?,TIPE_ID=?,NOMOR_ID=?,STATUS=?,PT_S1=?,JURUSAN_S1=?,KDPST_S1=?,GELAR_S1=?,TKN_BIDANG_KEAHLIAN_S1=?,NOIJA_S1=?,TGLLS_S1=?,FILE_IJA_S1=?,JUDUL_TA_S1=?,PT_S2=?,JURUSAN_S2=?,KDPST_S2=?,GELAR_S2=?,TKN_BIDANG_KEAHLIAN_S2=?,NOIJA_S2=?,TGLLS_S2=?,FILE_IJA_S2=?,JUDUL_TA_S2=?,PT_S3=?,JURUSAN_S3=?,KDPST_S3=?,GELAR_S3=?,TKN_BIDANG_KEAHLIAN_S3=?,NOIJA_S3=?,TGLLS_S3=?,FILE_IJA_S3=?,JUDUL_TA_S3=?,PT_PROF=?,JURUSAN_PROF=?,KDPST_PROF=?,GELAR_PROF=?,TKN_BIDANG_KEAHLIAN_PROF=?,NOIJA_PROF=?,TGLLS_PROF=?,FILE_IJA_PROF=?,JUDUL_TA_PROF=?,TOTAL_KUM=?,JABATAN_AKADEMIK_DIKTI=?,JABATAN_AKADEMIK_LOCAL=?,JABATAN_STRUKTURAL=?,IKATAN_KERJA_DOSEN=?,TANGGAL_MULAI_KERJA=?,TANGGAL_KELUAR_KERJA=?,SERDOS=?,KDPTI_HOMEBASE=?,KDPST_HOMEBASE=?,EMAIL_INSTITUSI=?,PANGKAT_GOLONGAN=?,CATATAN_RIWAYAT=?,TIPE_KTP=?,NO_KTP=? WHERE KDPST=? AND NPMHS=?");
        					StringTokenizer st = new StringTokenizer(brs,"`");
        					
        					int i = 1;
        					String kdpst = st.nextToken();
        	        	    String npmhs = st.nextToken();
        	        	    String nodos_loco = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(nodos_loco)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, nodos_loco);
        					}
        	        	    String gelardp = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(gelardp)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, gelardp);
        					}
        	        	    String gelarbl = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(gelarbl)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, gelarbl);
        					}
        	        	    String nidnn = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(nidnn)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, nidnn);
        					}
        	        	    String tipeid = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(tipeid)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, tipeid);
        					}
        	        	    String nomid = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(nomid)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, nomid);
        					}
        	        	    String status = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(status)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, status);
        					}
        	        	    String pt1 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(pt1)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, pt1);
        					}
        	        	    String jur1 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(jur1)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, jur1);
        					}
        	        	    String kdpst1 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(kdpst1)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, kdpst1);
        					}
        	        	    String gelar1 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(gelar1)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, gelar1);
        					}
        	        	    String bidil1 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(bidil1)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, bidil1);
        					}
        	        	    String noija1 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(noija1)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, noija1);
        					}
        	        	    String tglls1 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(tglls1)) {
        						stmt.setNull(i++, java.sql.Types.DATE);
        					}
        					else {
        						stmt.setDate(i++, java.sql.Date.valueOf(tglls1));
        					}
        	        	    String fileija1 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(fileija1)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, fileija1);
        					}
        	        	    String judul1 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(judul1)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, judul1);
        					}
        	        	    String pt2 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(pt2)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, pt2);
        					}
        	        	    String jur2 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(jur2)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, jur2);
        					}
        	        	    String kdpst2 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(kdpst2)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, kdpst2);
        					}
        	        	    String gelar2 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(gelar2)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, gelar2);
        					}
        	        	    String bidil2 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(bidil2)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, bidil2);
        					}
        	        	    String noija2 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(noija2)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, noija2);
        					}
        	        	    String tglls2 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(tglls2)) {
        						stmt.setNull(i++, java.sql.Types.DATE);
        					}
        					else {
        						stmt.setDate(i++, java.sql.Date.valueOf(tglls2));
        					}
        	        	    String fileija2 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(fileija2)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, fileija2);
        					}
        	        	    String judul2 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(judul2)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, judul2);
        					}
        	        	    String pt3 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(pt3)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, pt3);
        					}
        	        	    String jur3 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(jur3)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, jur3);
        					}
        	        	    String kdpst3 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(kdpst3)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, kdpst3);
        					}
        	        	    String gelar3 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(gelar3)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, gelar3);
        					}
        	        	    String bidil3 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(bidil3)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, bidil3);
        					}
        	        	    String noija3 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(noija3)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, noija3);
        					}
        	        	    String tglls3 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(tglls3)) {
        						stmt.setNull(i++, java.sql.Types.DATE);
        					}
        					else {
        						stmt.setDate(i++, java.sql.Date.valueOf(tglls3));
        					}
        	        	    String fileija3 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(fileija3)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, fileija3);
        					}
        	        	    String judul3 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(judul3)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, judul3);
        					}
        	        	    String pt4 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(pt4)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, pt4);
        					}
        	        	    String jur4 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(jur4)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, jur4);
        					}
        	        	    String kdpst4 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(kdpst4)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, kdpst4);
        					}
        	        	    String gelar4 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(gelar4)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, gelar4);
        					}
        	        	    String bidil4 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(bidil4)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, bidil4);
        					}
        	        	    String noija4 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(noija4)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, noija4);
        					}
        	        	    String tglls4 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(tglls4)) {
        						stmt.setNull(i++, java.sql.Types.DATE);
        					}
        					else {
        						stmt.setDate(i++, java.sql.Date.valueOf(tglls4));
        					}
        	        	    String fileija4 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(fileija4)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, fileija4);
        					}
        	        	    String judul4 = st.nextToken();
        	        	    if(Checker.isStringNullOrEmpty(judul4)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, judul4);
        					}
        	        	    String ttkum =  st.nextToken();//""+rs.getInt("TOTAL_KUM");
        	        	    if(Checker.isStringNullOrEmpty(ttkum)) {
        						stmt.setNull(i++, java.sql.Types.INTEGER);
        					}
        					else {
        						stmt.setInt(i++, Integer.parseInt(ttkum));
        					}
        	        	    String jabak = st.nextToken();// ""+rs.getString("JABATAN_AKADEMIK_DIKTI");
        	        	    if(Checker.isStringNullOrEmpty(jabak)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, jabak);
        					}
        	        	    String jabak_loco = st.nextToken();// ""+rs.getString("JABATAN_AKADEMIK_LOCAL");
        	        	    if(Checker.isStringNullOrEmpty(jabak_loco)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, jabak_loco);
        					}
        	        	    String jabstruk = st.nextToken();// ""+rs.getString("JABATAN_STRUKTURAL");
        	        	    if(Checker.isStringNullOrEmpty(jabstruk)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, jabstruk);
        					}
        	        	    String ika = st.nextToken();// ""+rs.getString("IKATAN_KERJA_DOSEN");
        	        	    if(Checker.isStringNullOrEmpty(ika)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, ika);
        					}
        	        	    String tgmsk = st.nextToken();// ""+rs.getDate("TANGGAL_MULAI_KERJA");
        	        	    if(Checker.isStringNullOrEmpty(tgmsk)) {
        						stmt.setNull(i++, java.sql.Types.DATE);
        					}
        					else {
        						stmt.setDate(i++, java.sql.Date.valueOf(tgmsk));
        					}
        	        	    String tgklr = st.nextToken();// ""+rs.getDate("TANGGAL_KELUAR_KERJA");
        	        	    //System.out.println("tgklr = "+tgklr);
        	        	    if(Checker.isStringNullOrEmpty(tgklr)) {
        						stmt.setNull(i++, java.sql.Types.DATE);
        					}
        					else {
        						stmt.setDate(i++, java.sql.Date.valueOf(tgklr));
        					}
        	        	    String serdos = st.nextToken();// ""+rs.getString("SERDOS");
        	        	    if(Checker.isStringNullOrEmpty(serdos)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, serdos);
        					}
        	        	    String kdpti_homebase = st.nextToken();// ""+rs.getString("KDPTI_HOMEBASE");
        	        	    if(Checker.isStringNullOrEmpty(kdpti_homebase)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, kdpti_homebase);
        					}
        	        	    String kdpst_homebase = st.nextToken();// ""+rs.getString("KDPST_HOMEBASE");
        	        	    if(Checker.isStringNullOrEmpty(kdpst_homebase)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, kdpst_homebase);
        					}
        	        	    String email_loco = st.nextToken();// ""+rs.getString("EMAIL_INSTITUSI");
        	        	    if(Checker.isStringNullOrEmpty(email_loco)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, email_loco);
        					}
        	        	    String pangkat = st.nextToken();// ""+rs.getString("PANGKAT_GOLONGAN");
        	        	    if(Checker.isStringNullOrEmpty(pangkat)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, pangkat);
        					}
        	        	    String note_history = st.nextToken();// ""+rs.getString("CATATAN_RIWAYAT");
        	        	    if(Checker.isStringNullOrEmpty(note_history)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, note_history);
        					}
        	        	    String tipe_id = st.nextToken();// ""+rs.getString("TIPE_KTP");
        	        	    if(Checker.isStringNullOrEmpty(tipe_id)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, tipe_id);
        					}
        	        	    String no_id = st.nextToken();// ""+rs.getString("NO_KTP");
        	        	    if(Checker.isStringNullOrEmpty(no_id)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, no_id);
        					}
        	        	    //where 
        	        	    stmt.setString(i++, kdpstTujuanMerge);
        	        	    stmt.setString(i++, npmTujuanMerge);
        	        	    int j = stmt.executeUpdate();
        	        	    //System.out.println("update data dosen = "+j);
        	        	    if(j<1) { //belum ada data jadi harus insert
        	        	    	stmt = con.prepareStatement("INSERT INTO EXT_CIVITAS_DATA_DOSEN (KDPST,NPMHS,NODOS_LOCAL,GELAR_DEPAN,GELAR_BELAKANG,NIDNN,TIPE_ID,NOMOR_ID,STATUS,PT_S1,JURUSAN_S1,KDPST_S1,GELAR_S1,TKN_BIDANG_KEAHLIAN_S1,NOIJA_S1,TGLLS_S1,FILE_IJA_S1,JUDUL_TA_S1,PT_S2,JURUSAN_S2,KDPST_S2,GELAR_S2,TKN_BIDANG_KEAHLIAN_S2,NOIJA_S2,TGLLS_S2,FILE_IJA_S2,JUDUL_TA_S2,PT_S3,JURUSAN_S3,KDPST_S3,GELAR_S3,TKN_BIDANG_KEAHLIAN_S3,NOIJA_S3,TGLLS_S3,FILE_IJA_S3,JUDUL_TA_S3,PT_PROF,JURUSAN_PROF,KDPST_PROF,GELAR_PROF,TKN_BIDANG_KEAHLIAN_PROF,NOIJA_PROF,TGLLS_PROF,FILE_IJA_PROF,JUDUL_TA_PROF,TOTAL_KUM,JABATAN_AKADEMIK_DIKTI,JABATAN_AKADEMIK_LOCAL,JABATAN_STRUKTURAL,IKATAN_KERJA_DOSEN,TANGGAL_MULAI_KERJA,TANGGAL_KELUAR_KERJA,SERDOS,KDPTI_HOMEBASE,KDPST_HOMEBASE,EMAIL_INSTITUSI,PANGKAT_GOLONGAN,CATATAN_RIWAYAT,TIPE_KTP,NO_KTP) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        	        	    	
        	        	    	//(KDPST,NPMHS,NODOS_LOCAL,GELAR_DEPAN,GELAR_BELAKANG,NIDNN,TIPE_ID,NOMOR_ID,STATUS,PT_S1,JURUSAN_S1,KDPST_S1,GELAR_S1,TKN_BIDANG_KEAHLIAN_S1,NOIJA_S1,TGLLS_S1,FILE_IJA_S1,JUDUL_TA_S1,PT_S2,JURUSAN_S2,KDPST_S2,GELAR_S2,TKN_BIDANG_KEAHLIAN_S2,NOIJA_S2,TGLLS_S2,FILE_IJA_S2,JUDUL_TA_S2,PT_S3,JURUSAN_S3,KDPST_S3,GELAR_S3,TKN_BIDANG_KEAHLIAN_S3,NOIJA_S3,TGLLS_S3,FILE_IJA_S3,JUDUL_TA_S3,PT_PROF,JURUSAN_PROF,KDPST_PROF,GELAR_PROF,TKN_BIDANG_KEAHLIAN_PROF,NOIJA_PROF,TGLLS_PROF,FILE_IJA_PROF,JUDUL_TA_PROF,TOTAL_KUM,JABATAN_AKADEMIK_DIKTI,JABATAN_AKADEMIK_LOCAL,JABATAN_STRUKTURAL,IKATAN_KERJA_DOSEN,TANGGAL_MULAI_KERJA,TANGGAL_KELUAR_KERJA,SERDOS,KDPTI_HOMEBASE,KDPST_HOMEBASE,EMAIL_INSTITUSI,PANGKAT_GOLONGAN,CATATAN_RIWAYAT,TIPE_KTP,NO_KTP)
        	        	    	//Stringkdpst = st.nextToken();
        	        	    	i=1;
        	        	    	if(Checker.isStringNullOrEmpty(kdpstTujuanMerge)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, kdpstTujuanMerge);
            					}
            	        	    //String npmhs = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(npmTujuanMerge)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, npmTujuanMerge);
            					}
            	        	    //String nodos_loco = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(nodos_loco)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, nodos_loco);
            					}
            	        	    //String gelardp = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(gelardp)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, gelardp);
            					}
            	        	    //String gelarbl = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(gelarbl)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, gelarbl);
            					}
            	        	    //String nidnn = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(nidnn)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, nidnn);
            					}
            	        	    //String tipeid = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(tipeid)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, tipeid);
            					}
            	        	    //String nomid = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(nomid)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, nomid);
            					}
            	        	    //String status = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(status)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, status);
            					}
            	        	    //String pt1 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(pt1)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, pt1);
            					}
            	        	    //String jur1 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(jur1)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, jur1);
            					}
            	        	    //String kdpst1 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(kdpst1)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, kdpst1);
            					}
            	        	    //String gelar1 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(gelar1)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, gelar1);
            					}
            	        	    //String bidil1 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(bidil1)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, bidil1);
            					}
            	        	    //String noija1 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(noija1)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, noija1);
            					}
            	        	    //String tglls1 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(tglls1)) {
            						stmt.setNull(i++, java.sql.Types.DATE);
            					}
            					else {
            						stmt.setDate(i++, java.sql.Date.valueOf(tglls1));
            					}
            	        	    //String fileija1 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(fileija1)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, fileija1);
            					}
            	        	    //String judul1 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(judul1)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, judul1);
            					}
            	        	    //String pt2 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(pt2)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, pt2);
            					}
            	        	    //String jur2 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(jur2)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, jur2);
            					}
            	        	    //String kdpst2 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(kdpst2)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, kdpst2);
            					}
            	        	    //String gelar2 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(gelar2)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, gelar2);
            					}
            	        	    //String bidil2 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(bidil2)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, bidil2);
            					}
            	        	    //String noija2 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(noija2)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, noija2);
            					}
            	        	    //String tglls2 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(tglls2)) {
            						stmt.setNull(i++, java.sql.Types.DATE);
            					}
            					else {
            						stmt.setDate(i++, java.sql.Date.valueOf(tglls2));
            					}
            	        	    //String fileija2 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(fileija2)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, fileija2);
            					}
            	        	    //String judul2 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(judul2)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, judul2);
            					}
            	        	    //String pt3 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(pt3)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, pt3);
            					}
            	        	    //String jur3 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(jur3)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, jur3);
            					}
            	        	    //String kdpst3 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(kdpst3)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, kdpst3);
            					}
            	        	    //String gelar3 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(gelar3)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, gelar3);
            					}
            	        	    //String bidil3 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(bidil3)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, bidil3);
            					}
            	        	    //String noija3 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(noija3)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, noija3);
            					}
            	        	    //String tglls3 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(tglls3)) {
            						stmt.setNull(i++, java.sql.Types.DATE);
            					}
            					else {
            						stmt.setDate(i++, java.sql.Date.valueOf(tglls3));
            					}
            	        	    //String fileija3 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(fileija3)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, fileija3);
            					}
            	        	    //String judul3 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(judul3)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, judul3);
            					}
            	        	    //String pt4 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(pt4)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, pt4);
            					}
            	        	    //String jur4 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(jur4)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, jur4);
            					}
            	        	    //String kdpst4 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(kdpst4)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, kdpst4);
            					}
            	        	    //String gelar4 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(gelar4)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, gelar4);
            					}
            	        	    //String bidil4 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(bidil4)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, bidil4);
            					}
            	        	    //String noija4 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(noija4)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, noija4);
            					}
            	        	    //String tglls4 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(tglls4)) {
            						stmt.setNull(i++, java.sql.Types.DATE);
            					}
            					else {
            						stmt.setDate(i++, java.sql.Date.valueOf(tglls4));
            					}
            	        	    //String fileija4 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(fileija4)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, fileija4);
            					}
            	        	    //String judul4 = st.nextToken();
            	        	    if(Checker.isStringNullOrEmpty(judul4)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, judul4);
            					}
            	        	    //String ttkum =  st.nextToken();//""+rs.getInt("TOTAL_KUM");
            	        	    if(Checker.isStringNullOrEmpty(ttkum)) {
            						stmt.setNull(i++, java.sql.Types.INTEGER);
            					}
            					else {
            						stmt.setInt(i++, Integer.parseInt(ttkum));
            					}
            	        	    //String jabak = st.nextToken();// ""+rs.getString("JABATAN_AKADEMIK_DIKTI");
            	        	    if(Checker.isStringNullOrEmpty(jabak)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, jabak);
            					}
            	        	    //String jabak_loco = st.nextToken();// ""+rs.getString("JABATAN_AKADEMIK_LOCAL");
            	        	    if(Checker.isStringNullOrEmpty(jabak_loco)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, jabak_loco);
            					}
            	        	    //String jabstruk = st.nextToken();// ""+rs.getString("JABATAN_STRUKTURAL");
            	        	    if(Checker.isStringNullOrEmpty(jabstruk)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, jabstruk);
            					}
            	        	    //String ika = st.nextToken();// ""+rs.getString("IKATAN_KERJA_DOSEN");
            	        	    if(Checker.isStringNullOrEmpty(ika)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, ika);
            					}
            	        	    //String tgmsk = st.nextToken();// ""+rs.getDate("TANGGAL_MULAI_KERJA");
            	        	    if(Checker.isStringNullOrEmpty(tgmsk)) {
            						stmt.setNull(i++, java.sql.Types.DATE);
            					}
            					else {
            						stmt.setDate(i++, java.sql.Date.valueOf(tgmsk));
            					}
            	        	    //String tgklr = st.nextToken();// ""+rs.getDate("TANGGAL_KELUAR_KERJA");
            	        	    //System.out.println("tgklr = "+tgklr);
            	        	    if(Checker.isStringNullOrEmpty(tgklr)) {
            						stmt.setNull(i++, java.sql.Types.DATE);
            					}
            					else {
            						stmt.setDate(i++, java.sql.Date.valueOf(tgklr));
            					}
            	        	    //String serdos = st.nextToken();// ""+rs.getString("SERDOS");
            	        	    if(Checker.isStringNullOrEmpty(serdos)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, serdos);
            					}
            	        	    //String kdpti_homebase = st.nextToken();// ""+rs.getString("KDPTI_HOMEBASE");
            	        	    if(Checker.isStringNullOrEmpty(kdpti_homebase)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, kdpti_homebase);
            					}
            	        	    //String kdpst_homebase = st.nextToken();// ""+rs.getString("KDPST_HOMEBASE");
            	        	    if(Checker.isStringNullOrEmpty(kdpst_homebase)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, kdpst_homebase);
            					}
            	        	    //String email_loco = st.nextToken();// ""+rs.getString("EMAIL_INSTITUSI");
            	        	    if(Checker.isStringNullOrEmpty(email_loco)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, email_loco);
            					}
            	        	    //String pangkat = st.nextToken();// ""+rs.getString("PANGKAT_GOLONGAN");
            	        	    if(Checker.isStringNullOrEmpty(pangkat)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, pangkat);
            					}
            	        	    //String note_history = st.nextToken();// ""+rs.getString("CATATAN_RIWAYAT");
            	        	    if(Checker.isStringNullOrEmpty(note_history)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, note_history);
            					}
            	        	    //String tipe_id = st.nextToken();// ""+rs.getString("TIPE_KTP");
            	        	    if(Checker.isStringNullOrEmpty(tipe_id)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, tipe_id);
            					}
            	        	    //String no_id = st.nextToken();// ""+rs.getString("NO_KTP");
            	        	    if(Checker.isStringNullOrEmpty(no_id)) {
            						stmt.setNull(i++, java.sql.Types.VARCHAR);
            					}
            					else {
            						stmt.setString(i++, no_id);
            					}
            	        	    j = stmt.executeUpdate();
            	        	    //System.out.println("inerst dat dos = "+j);
        	        	    }
        	        	    
        				}
        				
        				
        				//2. merge trnlm 
        				brs = (String)li.next();
        				//System.out.println("brs2 = "+brs);
        				if(!brs.equalsIgnoreCase("null")) {
        					stmt = con.prepareStatement("INSERT INTO TRNLM (THSMSTRNLM,KDPTITRNLM,KDJENTRNLM,KDPSTTRNLM,NPMHSTRNLM,KDKMKTRNLM,NILAITRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,KELASTRNLM,KRSDONWLOADED,KHSDONWLOADED,BAK_APPROVAL,SHIFTTRNLM,PA_APPROVAL,NOTE,LOCKTRNLM,BAUK_APPROVAL,IDKMKTRNLM,ADD_REQ,DRP_REQ,NPM_PA,PM_BAK,NPM_BAA,NPM_BAUK,BAA_APPROVAL,KTU_APPROVAL,DEKAN_APPROVAL,NPM_KTU,NPM_DEKAN,LOCKMHS,KODE_KAMPUS,CLASS_POOL_UNIQUE_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        					
        					
        					StringTokenizer st = new StringTokenizer(brs,"`");
        					
        					int i = 1;
        					String thsms = st.nextToken();// ""+rs.getString("THSMSTRNLM");
        					if(Checker.isStringNullOrEmpty(thsms)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, thsms);
        					}
                			String kdpti = st.nextToken();// ""+rs.getString("KDPTITRNLM");
                			if(Checker.isStringNullOrEmpty(kdpti)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, kdpti);
        					}
                			String kdjen =  st.nextToken();// ""+rs.getString("KDJENTRNLM");
                			if(Checker.isStringNullOrEmpty(kdjen)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, kdjen);
        					}
                			String kdpst =  st.nextToken();// ""+rs.getString("KDPSTTRNLM");
                			if(Checker.isStringNullOrEmpty(kdpstTujuanMerge)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, kdpstTujuanMerge);
        					}
                			String npmhs =  st.nextToken();// ""+rs.getString("NPMHSTRNLM");
                			if(Checker.isStringNullOrEmpty(npmTujuanMerge)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, npmTujuanMerge);
        					}
                			String kdkmk =  st.nextToken();// ""+rs.getString("KDKMKTRNLM");
                			if(Checker.isStringNullOrEmpty(kdkmk)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, kdkmk);
        					}
                			String nilai =  st.nextToken();// ""+rs.getFloat("NILAITRNLM");
                			if(Checker.isStringNullOrEmpty(nilai)) {
        						stmt.setNull(i++, java.sql.Types.FLOAT);
        					}
        					else {
        						stmt.setFloat(i++, Float.parseFloat(nilai));
        					}
                			String nlakh =  st.nextToken();// ""+rs.getString("NLAKHTRNLM");
                			if(Checker.isStringNullOrEmpty(nlakh)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, nlakh);
        					}
                			String bobot =  st.nextToken();// ""+rs.getFloat("BOBOTTRNLM");
                			if(Checker.isStringNullOrEmpty(bobot)) {
        						stmt.setNull(i++, java.sql.Types.FLOAT);
        					}
        					else {
        						stmt.setFloat(i++, Float.parseFloat(bobot));
        					}
                			String skskmk =  st.nextToken();// ""+rs.getInt("SKSMKTRNLM");
                			if(Checker.isStringNullOrEmpty(skskmk)) {
        						stmt.setNull(i++, java.sql.Types.INTEGER);
        					}
        					else {
        						stmt.setInt(i++, Integer.parseInt(skskmk));
        					}
                			String kelas =  st.nextToken();// ""+rs.getString("KELASTRNLM");
                			if(Checker.isStringNullOrEmpty(kelas)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, kelas);
        					}
                			String updtm =  st.nextToken();// ""+rs.getString("UPDTMTRNLM");
                			//if(Checker.isStringNullOrEmpty(note_history)) {
        					//	stmt.setNull(i++, java.sql.Types.VARCHAR);
        					//}
        					//else {
        					//	stmt.setString(i++, note_history);
        					//}
                			String krsdown =  st.nextToken();// ""+rs.getBoolean("KRSDONWLOADED");
                			if(Checker.isStringNullOrEmpty(krsdown)) {
        						stmt.setNull(i++, java.sql.Types.BOOLEAN);
        					}
        					else {
        						stmt.setBoolean(i++, Boolean.parseBoolean(krsdown));
        					}
                			String khsdown =  st.nextToken();// ""+rs.getBoolean("KHSDONWLOADED");
                			if(Checker.isStringNullOrEmpty(khsdown)) {
        						stmt.setNull(i++, java.sql.Types.BOOLEAN);
        					}
        					else {
        						stmt.setBoolean(i++, Boolean.parseBoolean(khsdown));
        					}
                			String bak_apr =  st.nextToken();// ""+rs.getBoolean("BAK_APPROVAL");
                			if(Checker.isStringNullOrEmpty(bak_apr)) {
        						stmt.setNull(i++, java.sql.Types.BOOLEAN);
        					}
        					else {
        						stmt.setBoolean(i++, Boolean.parseBoolean(bak_apr));
        					}
                			String shift_kls =  st.nextToken();// ""+rs.getString("SHIFTTRNLM");
                			if(Checker.isStringNullOrEmpty(shift_kls)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, shift_kls);
        					}
                			String pa_apr =  st.nextToken();// ""+rs.getBoolean("PA_APPROVAL");
                			if(Checker.isStringNullOrEmpty(pa_apr)) {
        						stmt.setNull(i++, java.sql.Types.BOOLEAN);
        					}
        					else {
        						stmt.setBoolean(i++, Boolean.parseBoolean(pa_apr));
        					}
                			String note =  st.nextToken();// ""+rs.getString("NOTE");
                			if(Checker.isStringNullOrEmpty(note)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, note);
        					}
                			String lock =  st.nextToken();// ""+rs.getBoolean("LOCKTRNLM");
                			if(Checker.isStringNullOrEmpty(lock)) {
        						stmt.setNull(i++, java.sql.Types.BOOLEAN);
        					}
        					else {
        						stmt.setBoolean(i++, Boolean.parseBoolean(lock));
        					}
                			String bauk_apr =  st.nextToken();// ""+rs.getBoolean("BAUK_APPROVAL");
                			if(Checker.isStringNullOrEmpty(bauk_apr)) {
        						stmt.setNull(i++, java.sql.Types.BOOLEAN);
        					}
        					else {
        						stmt.setBoolean(i++, Boolean.parseBoolean(bauk_apr));
        					}
                			String idkmk =  st.nextToken();// ""+rs.getInt("IDKMKTRNLM");
                			if(Checker.isStringNullOrEmpty(idkmk)) {
        						stmt.setNull(i++, java.sql.Types.INTEGER);
        					}
        					else {
        						stmt.setInt(i++, Integer.parseInt(idkmk));
        					}
                			String add_req =  st.nextToken();// ""+rs.getBoolean("ADD_REQ");
                			if(Checker.isStringNullOrEmpty(add_req)) {
        						stmt.setNull(i++, java.sql.Types.BOOLEAN);
        					}
        					else {
        						stmt.setBoolean(i++, Boolean.parseBoolean(add_req));
        					}
                			String drp_req =  st.nextToken();// ""+rs.getBoolean("DRP_REQ");
                			if(Checker.isStringNullOrEmpty(drp_req)) {
        						stmt.setNull(i++, java.sql.Types.BOOLEAN);
        					}
        					else {
        						stmt.setBoolean(i++, Boolean.parseBoolean(drp_req));
        					}
                			String npm_pa =  st.nextToken();// ""+rs.getString("NPM_PA");
                			if(Checker.isStringNullOrEmpty(npm_pa)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, npm_pa);
        					}
                			String npm_bak =  st.nextToken();// ""+rs.getString("NPM_BAK");
                			if(Checker.isStringNullOrEmpty(npm_bak)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, npm_bak);
        					}
                			String npm_baa =  st.nextToken();// ""+rs.getString("NPM_BAA");
                			if(Checker.isStringNullOrEmpty(npm_baa)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, npm_baa);
        					}
                			String npm_bauk =  st.nextToken();// ""+rs.getString("NPM_BAUK");
                			if(Checker.isStringNullOrEmpty(npm_bauk)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, npm_bauk);
        					}
                			String baa_apr =  st.nextToken();// ""+rs.getBoolean("BAA_APPROVAL");
                			if(Checker.isStringNullOrEmpty(baa_apr)) {
        						stmt.setNull(i++, java.sql.Types.BOOLEAN);
        					}
        					else {
        						stmt.setBoolean(i++, Boolean.parseBoolean(baa_apr));
        					}
                			String ktu_apr =  st.nextToken();// ""+rs.getBoolean("KTU_APPROVAL");
                			if(Checker.isStringNullOrEmpty(ktu_apr)) {
        						stmt.setNull(i++, java.sql.Types.BOOLEAN);
        					}
        					else {
        						stmt.setBoolean(i++, Boolean.parseBoolean(ktu_apr));
        					}
                			String dkn_apr =  st.nextToken();// ""+rs.getBoolean("DEKAN_APPROVAL");
                			if(Checker.isStringNullOrEmpty(dkn_apr)) {
        						stmt.setNull(i++, java.sql.Types.BOOLEAN);
        					}
        					else {
        						stmt.setBoolean(i++, Boolean.parseBoolean(dkn_apr));
        					}
                			String npm_ktu =  st.nextToken();// ""+rs.getString("NPM_KTU");
                			if(Checker.isStringNullOrEmpty(npm_ktu)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, npm_ktu);
        					}
                			String npm_dkn =  st.nextToken();// ""+rs.getString("NPM_DEKAN");
                			if(Checker.isStringNullOrEmpty(npm_dkn)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, npm_dkn);
        					}
                			String lockMhs =  st.nextToken();// ""+rs.getBoolean("LOCKMHS");
                			if(Checker.isStringNullOrEmpty(lockMhs)) {
        						stmt.setNull(i++, java.sql.Types.BOOLEAN);
        					}
        					else {
        						stmt.setBoolean(i++, Boolean.parseBoolean(lockMhs));
        					}
                			String kode_kmp =  st.nextToken();// ""+rs.getString("KODE_KAMPUS");
                			if(Checker.isStringNullOrEmpty(kode_kmp)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, kode_kmp);
        					}
                			String cuid =  st.nextToken();// ""+rs.getInt("CLASS_POOL_UNIQUE_ID");
                			if(Checker.isStringNullOrEmpty(cuid)) {
        						stmt.setNull(i++, java.sql.Types.INTEGER);
        					}
        					else {
        						stmt.setInt(i++, Integer.parseInt(cuid));
        					}
                			try {
                				stmt.executeUpdate();
                			}
                			catch(Exception esq) {
                				//ignore
                			}
        				}
        				
        				//3. trmlp
        				brs = (String)li.next();
        				//System.out.println("brs3 = "+brs);
        				if(!brs.equalsIgnoreCase("null")) {
        					stmt = con.prepareStatement("INSERT INTO TRNLP (THSMSTRNLP,KDPSTTRNLP,NPMHSTRNLP,KDKMKTRNLP,NLAKHTRNLP,BOBOTTRNLP,SKSMKTRNLP,KDKMKASALP,NAKMKASALP,SKSMKASAL,KETERTRNLP,TRANSFERRED,APPROVED)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
        					
        					StringTokenizer st = new StringTokenizer(brs,"`");
        					
        					int i = 1;
        					String thsms = st.nextToken();//""+rs.getString("THSMSTRNLP");
        					if(Checker.isStringNullOrEmpty(thsms)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, thsms);
        					}
            				String kdpst = st.nextToken();// ""+rs.getString("KDPSTTRNLP");
            				if(Checker.isStringNullOrEmpty(kdpstTujuanMerge)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, kdpstTujuanMerge);
        					}
            				String npmhs = st.nextToken();// ""+rs.getString("NPMHSTRNLP");
            				if(Checker.isStringNullOrEmpty(npmTujuanMerge)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, npmTujuanMerge);
        					}
            				String kdkmk = st.nextToken();// ""+rs.getString("KDKMKTRNLP");
            				if(Checker.isStringNullOrEmpty(kdkmk)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, kdkmk);
        					}
            				String nlakh = st.nextToken();// ""+rs.getString("NLAKHTRNLP");
            				if(Checker.isStringNullOrEmpty(nlakh)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, nlakh);
        					}
            				String bobot = st.nextToken();// ""+rs.getFloat("BOBOTTRNLP");
            				if(Checker.isStringNullOrEmpty(bobot)) {
        						stmt.setNull(i++, java.sql.Types.FLOAT);
        					}
        					else {
        						stmt.setFloat(i++, Float.parseFloat(bobot));
        					}
            				String sksmk = st.nextToken();// ""+rs.getInt("SKSMKTRNLP");
            				if(Checker.isStringNullOrEmpty(sksmk)) {
        						stmt.setNull(i++, java.sql.Types.INTEGER);
        					}
        					else {
        						stmt.setInt(i++, Integer.parseInt(sksmk));
        					}
            				String kdkmk_as = st.nextToken();// ""+rs.getString("KDKMKASALP");
            				if(Checker.isStringNullOrEmpty(kdkmk_as)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, kdkmk_as);
        					}
            				String nakmk_as = st.nextToken();// ""+rs.getString("NAKMKASALP");
            				if(Checker.isStringNullOrEmpty(nakmk_as)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, nakmk_as);
        					}
            				String sksmk_as = st.nextToken();// ""+rs.getInt("SKSMKASAL");
            				if(Checker.isStringNullOrEmpty(sksmk_as)) {
        						stmt.setNull(i++, java.sql.Types.INTEGER);
        					}
        					else {
        						stmt.setInt(i++, Integer.parseInt(sksmk_as));
        					}
            				String keter = st.nextToken();// ""+rs.getString("KETERTRNLP");
            				if(Checker.isStringNullOrEmpty(keter)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, keter);
        					}
            				String updtm = st.nextToken();// ""+rs.getTimestamp("UPDTMTRNLP"); -- ignore not used
            				String transfered = st.nextToken();// ""+rs.getBoolean("TRANSFERRED");
            				if(Checker.isStringNullOrEmpty(transfered)) {
        						stmt.setNull(i++, java.sql.Types.BOOLEAN);
        					}
        					else {
        						stmt.setBoolean(i++, Boolean.parseBoolean(transfered));
        					}
            				String approved = st.nextToken();// ""+rs.getString("APPROVED");
            				if(Checker.isStringNullOrEmpty(approved)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, approved);
        					}
            				try {
            					stmt.executeUpdate();
            				}
            				catch (Exception e) {
            					
            				}
        				}	
        				
        				
        				
        				//trnsm
        				brs = (String)li.next();
        				//System.out.println("brs4 = "+brs);
        				if(!brs.equalsIgnoreCase("null")) {
        					stmt = con.prepareStatement("INSERT INTO TRLSM (THSMS,KDPST,NPMHS,STMHS,TGL_PENGAJUAN,TOKEN_APPROVEE) VALUES (?,?,?,?,?,?)");
        					
        					StringTokenizer st = new StringTokenizer(brs,"`");
        					
        					int i = 1;
        					String thsms = st.nextToken();//""+rs.getString("THSMS");
        					if(Checker.isStringNullOrEmpty(thsms)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, thsms);
        					}
                			String kdpst = st.nextToken();// ""+rs.getString("KDPST");
                			if(Checker.isStringNullOrEmpty(kdpstTujuanMerge)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, kdpstTujuanMerge);
        					}
                			String npmhs = st.nextToken();// ""+rs.getString("NPMHS");
                			if(Checker.isStringNullOrEmpty(npmTujuanMerge)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, npmTujuanMerge);
        					}
                			String stmhs = st.nextToken();// ""+rs.getString("STMHS");
                			if(Checker.isStringNullOrEmpty(stmhs)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, stmhs);
        					}
                			String tgl_aju = st.nextToken();// ""+rs.getTimestamp("TGL_PENGAJUAN");
                			if(Checker.isStringNullOrEmpty(tgl_aju)) {
        						stmt.setNull(i++, java.sql.Types.TIMESTAMP);
        					}
        					else {
        						stmt.setTimestamp(i++, Timestamp.valueOf(tgl_aju));
        					}
                			String tkn_apr = st.nextToken();// ""+rs.getString("TOKEN_APPROVEE");
                			if(Checker.isStringNullOrEmpty(tkn_apr)) {
        						stmt.setNull(i++, java.sql.Types.VARCHAR);
        					}
        					else {
        						stmt.setString(i++, tkn_apr);
        					}
                			try {
                				stmt.executeUpdate();
                			}
                			catch (Exception e) {}
        				}	
        				
        				//update pymnt && 
        				java.sql.Timestamp ts = AskSystem.getCurrentTimestamp();
        				stmt = con.prepareStatement("UPDATE PYMNT set NPMHSPYMNT=?,ASAL_NPM_TRANSAKSI=?,MERGE_TIME=? where NPMHSPYMNT=?");
        				stmt.setString(1, npmTujuanMerge);
        				stmt.setString(2, npmYgMoDiMerge);
        				stmt.setTimestamp(3, ts);
        				stmt.setString(4, npmYgMoDiMerge);
        				stmt.executeUpdate();
        				//update pymnt_transit
        				stmt = con.prepareStatement("UPDATE PYMNT_TRANSIT set NPMHSPYMNT=?,ASAL_NPM_TRANSAKSI=?,MERGE_TIME=? where NPMHSPYMNT=?");
        				stmt.setString(1, npmTujuanMerge);
        				stmt.setString(2, npmYgMoDiMerge);
        				stmt.setTimestamp(3, ts);
        				stmt.setString(4, npmYgMoDiMerge);
        				stmt.executeUpdate();
        				//update CLASS_POOL
        				stmt = con.prepareStatement("update CLASS_POOL set NPMDOS=? where NPMDOS=? and THSMS=?");
        				stmt.setString(1, npmTujuanMerge);
        				stmt.setString(2, npmYgMoDiMerge);
        				stmt.setString(3, Checker.getThsmsNow());
        				stmt.executeUpdate();
        			}
    	    	//catch (NamingException e) {
    	    	//	e.printStackTrace();
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
}
