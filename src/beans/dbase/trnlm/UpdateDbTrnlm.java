package beans.dbase.trnlm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import beans.dbase.UpdateDb;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Tool;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;

/**
 * Session Bean implementation class UpdateDbTrnlm
 */
@Stateless
@LocalBean
public class UpdateDbTrnlm extends UpdateDb {
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
    public UpdateDbTrnlm() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbTrnlm(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }

    /*
     * depricated
     */
    public void insertKrs(Vector vContinuSistemAdjustment,String npmhs, String kdpst) {
    	//linfo.add(TargetThsmsKrs+","+idkmk[i]+","+npm+","+kdpst+","+obj_lvl);
    	/*
    	 * fungsi ini menghapus prev record di trnlm baru kemudian insert yg baru
    	 */
    	if(vContinuSistemAdjustment!=null && vContinuSistemAdjustment.size()>0) {
    		try {
    			String kdjen = Checker.getKdjen(kdpst);
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		//get default shift
        		stmt = con.prepareStatement("select SHIFTMSMHS from CIVITAS where NPMHSMSMHS=?");
        		stmt.setString(1, npmhs);
        		rs = stmt.executeQuery();
        		rs.next();
        		String shiftKelas = rs.getString("SHIFTMSMHS");
        		if(shiftKelas==null) {
        			shiftKelas="N/A";
        		}
        		
        											   
        		Vector vDistincThsms = new Vector();
        		ListIterator lid = vDistincThsms.listIterator();
        		stmt = con.prepareStatement("insert into TRNLM(THSMSTRNLM,KDPTITRNLM,KDJENTRNLM,KDPSTTRNLM,NPMHSTRNLM,KDKMKTRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,KELASTRNLM,SHIFTTRNLM,IDKMKTRNLM)values(?,?,?,?,?,?,?,?,?,?,?,?) ");
        		ListIterator litmp = vContinuSistemAdjustment.listIterator();
        		while(litmp.hasNext()) {
        			//////System.out.println("pre-insert");
        			String thsms = (String)litmp.next();
        			lid.add(thsms);
        			String kdkmk = (String)litmp.next();
        			String nakmkTrnlm = (String)litmp.next();
        			String nlakhTrnlm = (String)litmp.next();
        			String bobotTrnlm = (String)litmp.next();
        			String sksmk = (String)litmp.next();
        			String noKlsPll = (String)litmp.next();
        			String sksemTrnlm = (String)litmp.next();
        			String nlipsTrnlm = (String)litmp.next();
        			String sksttTrnlm = (String)litmp.next();
        			String nlipkTrnlm = (String)litmp.next();	
        			String shiftTrnlm = (String)litmp.next();	
        			String krsdownTrnlm = (String)litmp.next();//tambahan baru
        			String khsdownTrnlm = (String)litmp.next();//tambahan baru
        			String bakproveTrnlm = (String)litmp.next();//tambahan baru
        			String paproveTrnlm = (String)litmp.next();//tambahan baru
        			String noteTrnlm = (String)litmp.next();//tambahan baru
        			String lockTrnlm = (String)litmp.next();//tambahan baru
        			String baukproveTrnlm = (String)litmp.next();//tambahan baru
					//tambahan
        			String idkmk = (String)litmp.next();
        			String addReqTrnlm = (String)litmp.next();
        			String drpReqTrnlm = (String)litmp.next();
        			String npmPaTrnlm = (String)litmp.next();
        			String npmBakTrnlm = (String)litmp.next();
        			String npmBaaTrnlm = (String)litmp.next();
        			String npmBaukTrnlm = (String)litmp.next();
        			String baaProveTrnlm = (String)litmp.next();
        			String ktuProveTrnlm = (String)litmp.next();
        			String dknProveTrnlm = (String)litmp.next();
        			String npmKtuTrnlm = (String)litmp.next();
        			String npmDekanTrnlm = (String)litmp.next();
        			String lockMhsTrnlm = (String)litmp.next();
        			String kodeKampusTrnlm = (String)litmp.next();
        			
        			
        			
        			
        			
        			//thsms = (String)li2.next();
        			//String idkmk = (String)li2.next();
        			//npmhs = (String)li2.next();
        			//kdpst = (String)li2.next();
        			//String objLvl = (String)li2.next();
        			//String kdkmk = (String)li2.next();
        			//String sksmk = (String)li2.next();
        			//String noKlsPll = (String)li2.next();
        			if(noKlsPll==null || Checker.isStringNullOrEmpty(noKlsPll)) {
        				noKlsPll="00";
        			}
        			//String currStatus = (String)li2.next();
        			//String npmdos = (String)li2.next();
        			//String npmasdos = (String)li2.next();
        			//1THSMSTRNLM,2KDPTITRNLM,3KDJENTRNLM,4KDPSTTRNLM,5NPMHSTRNLM,6KDKMKTRNLM,7NLAKHTRNLM,8BOBOTTRNLM,9SKSMKTRNLM,10KELASTRNLM,11SHIFTTRNLM
        			stmt.setString(1,thsms);
        			stmt.setString(2,Constants.getKdpti());
        			//stmt.setNull(3,java.sql.Types.VARCHAR);
        			stmt.setString(3,kdjen.toUpperCase());
        			stmt.setString(4,kdpst);
        			stmt.setString(5,npmhs);
        			stmt.setString(6,kdkmk);
        			stmt.setString(7,"T");
        			stmt.setDouble(8,0);
        			if(Checker.isStringNullOrEmpty(sksmk)) {
        				sksmk = "0";
        			}
        			stmt.setInt(9,Integer.valueOf(sksmk).intValue());
        			stmt.setString(10,noKlsPll);
        			stmt.setString(11, shiftKelas);
        			if(idkmk!=null && idkmk.equalsIgnoreCase("0")) {
        				stmt.setNull(12, java.sql.Types.INTEGER);
        			}
        			else {
        				if(Checker.isStringNullOrEmpty(idkmk) || idkmk.equalsIgnoreCase("N/A")) {
        					stmt.setNull(12, java.sql.Types.INTEGER);
        				}
        				else {
        					stmt.setInt(12,Integer.valueOf(idkmk).intValue());
        				}
        			}	
        			////System.out.println("insert thsms/kdkmk = "+thsms+"/"+kdkmk);
        			stmt.executeUpdate();
        		}
        		
        		vDistincThsms = Tool.removeDuplicateFromVector(vDistincThsms);
        		lid = vDistincThsms.listIterator();
        		stmt = con.prepareStatement("delete from TRAKM where THSMSTRAKM=? and KDPSTTRAKM=? and NPMHSTRAKM=?");
        		while(lid.hasNext()) {
        			String thsms = (String)lid.next();
        			stmt.setString(1, thsms);
            		stmt.setString(2, kdpst);
            		stmt.setString(3, npmhs);
            		stmt.executeUpdate();
        		}
        		//harus insert trakm juga
        		//delete prev record
        		
        		
        		//insert fresh record
        		lid = vDistincThsms.listIterator();
        		stmt = con.prepareStatement("INSERT into TRAKM(THSMSTRAKM,KDPTITRAKM,KDJENTRAKM,KDPSTTRAKM,NPMHSTRAKM,SKSEMTRAKM,NLIPSTRAKM,SKSTTTRAKM,NLIPKTRAKM)values(?,?,?,?,?,?,?,?,?)");
        		while(lid.hasNext()) {
        			String thsms = (String)lid.next();
        			stmt.setString(1, thsms);
            		stmt.setString(2, Constants.getKdpti());
            		stmt.setNull(3,java.sql.Types.VARCHAR);
            		stmt.setString(4, kdpst);
            		stmt.setString(5, npmhs);
            		stmt.setInt(6, 0);
            		stmt.setDouble(7, 0);
            		stmt.setInt(8, 0);
            		stmt.setDouble(9, 0);
            		stmt.executeUpdate();
        		}
        		
        		
    		} 
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
    		catch (Exception ex) {
    			ex.printStackTrace();
    		} 
    		finally {
    			if (con!=null) {
    				try {
    					con.close();
    				}
    				catch (Exception ignore) {
    					////System.out.println(ignore);
    				}
    			}
    		}
    	}	
    }
    
    
    public void insertKrs_v1(Vector vContinuSistemAdjustment,String npmhs, String kdpst) {
    	//linfo.add(TargetThsmsKrs+","+idkmk[i]+","+npm+","+kdpst+","+obj_lvl);
    	/*
    	 * fungsi ini menghapus prev record di trnlm baru kemudian insert yg baru
    	 */
    	if(vContinuSistemAdjustment!=null && vContinuSistemAdjustment.size()>0) {
    		try {
    			String kdjen = Checker.getKdjen(kdpst);
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		
        		//get default shift
        		stmt = con.prepareStatement("select SHIFTMSMHS from CIVITAS where NPMHSMSMHS=?");
        		stmt.setString(1, npmhs);
        		rs = stmt.executeQuery();
        		rs.next();
        		String shiftKelas = rs.getString("SHIFTMSMHS");
        		if(shiftKelas==null) {
        			shiftKelas="N/A";
        		}
        		
        											   
        		Vector vDistincThsms = new Vector();
        		ListIterator lid = vDistincThsms.listIterator();
        		stmt = con.prepareStatement("insert into TRNLM(THSMSTRNLM,KDPTITRNLM,KDJENTRNLM,KDPSTTRNLM,NPMHSTRNLM,KDKMKTRNLM,NLAKHTRNLM,BOBOTTRNLM,SKSMKTRNLM,KELASTRNLM,SHIFTTRNLM,IDKMKTRNLM)values(?,?,?,?,?,?,?,?,?,?,?,?) ");
        		ListIterator litmp = vContinuSistemAdjustment.listIterator();
        		while(litmp.hasNext()) {
        			//////System.out.println("pre-insert");
        			String thsms = (String)litmp.next();
        			lid.add(thsms);
        			String kdkmk = (String)litmp.next();
        			String nakmkTrnlm = (String)litmp.next();
        			String nlakhTrnlm = (String)litmp.next();
        			String bobotTrnlm = (String)litmp.next();
        			String sksmk = (String)litmp.next();
        			String noKlsPll = (String)litmp.next();
        			String sksemTrnlm = (String)litmp.next();
        			String nlipsTrnlm = (String)litmp.next();
        			String sksttTrnlm = (String)litmp.next();
        			String nlipkTrnlm = (String)litmp.next();	
        			String shiftTrnlm = (String)litmp.next();	
        			String krsdownTrnlm = (String)litmp.next();//tambahan baru
        			String khsdownTrnlm = (String)litmp.next();//tambahan baru
        			String bakproveTrnlm = (String)litmp.next();//tambahan baru
        			String paproveTrnlm = (String)litmp.next();//tambahan baru
        			String noteTrnlm = (String)litmp.next();//tambahan baru
        			String lockTrnlm = (String)litmp.next();//tambahan baru
        			String baukproveTrnlm = (String)litmp.next();//tambahan baru
					//tambahan
        			String idkmk = (String)litmp.next();
        			String addReqTrnlm = (String)litmp.next();
        			String drpReqTrnlm = (String)litmp.next();
        			String npmPaTrnlm = (String)litmp.next();
        			String npmBakTrnlm = (String)litmp.next();
        			String npmBaaTrnlm = (String)litmp.next();
        			String npmBaukTrnlm = (String)litmp.next();
        			String baaProveTrnlm = (String)litmp.next();
        			String ktuProveTrnlm = (String)litmp.next();
        			String dknProveTrnlm = (String)litmp.next();
        			String npmKtuTrnlm = (String)litmp.next();
        			String npmDekanTrnlm = (String)litmp.next();
        			String lockMhsTrnlm = (String)litmp.next();
        			String kodeKampusTrnlm = (String)litmp.next();
        			
        			
        			
        			
        			
        			//thsms = (String)li2.next();
        			//String idkmk = (String)li2.next();
        			//npmhs = (String)li2.next();
        			//kdpst = (String)li2.next();
        			//String objLvl = (String)li2.next();
        			//String kdkmk = (String)li2.next();
        			//String sksmk = (String)li2.next();
        			//String noKlsPll = (String)li2.next();
        			if(noKlsPll==null || Checker.isStringNullOrEmpty(noKlsPll)) {
        				noKlsPll="00";
        			}
        			//String currStatus = (String)li2.next();
        			//String npmdos = (String)li2.next();
        			//String npmasdos = (String)li2.next();
        			//1THSMSTRNLM,2KDPTITRNLM,3KDJENTRNLM,4KDPSTTRNLM,5NPMHSTRNLM,6KDKMKTRNLM,7NLAKHTRNLM,8BOBOTTRNLM,9SKSMKTRNLM,10KELASTRNLM,11SHIFTTRNLM
        			stmt.setString(1,thsms);
        			stmt.setString(2,Constants.getKdpti());
        			//stmt.setNull(3,java.sql.Types.VARCHAR);
        			stmt.setString(3,kdjen.toUpperCase());
        			stmt.setString(4,kdpst);
        			stmt.setString(5,npmhs);
        			stmt.setString(6,kdkmk);
        			stmt.setString(7,"T");
        			stmt.setDouble(8,0);
        			stmt.setInt(9,Integer.valueOf(sksmk).intValue());
        			stmt.setString(10,noKlsPll);
        			stmt.setString(11, shiftKelas);
        			if(idkmk!=null && idkmk.equalsIgnoreCase("0")) {
        				stmt.setNull(12, java.sql.Types.INTEGER);
        			}
        			else {
        				if(Checker.isStringNullOrEmpty(idkmk)) {
        					stmt.setNull(12, java.sql.Types.INTEGER);
        				}
        				else {
        					stmt.setInt(12,Integer.valueOf(idkmk).intValue());
        				}
        			}	
        			////System.out.println("insert thsms/kdkmk = "+thsms+"/"+kdkmk);
        			stmt.executeUpdate();
        		}
        		
        		vDistincThsms = Tool.removeDuplicateFromVector(vDistincThsms);
        		lid = vDistincThsms.listIterator();
        		stmt = con.prepareStatement("delete from TRAKM where THSMSTRAKM=? and KDPSTTRAKM=? and NPMHSTRAKM=?");
        		while(lid.hasNext()) {
        			String thsms = (String)lid.next();
        			stmt.setString(1, thsms);
            		stmt.setString(2, kdpst);
            		stmt.setString(3, npmhs);
            		stmt.executeUpdate();
        		}
        		//harus insert trakm juga
        		//delete prev record
        		
        		
        		//insert fresh record
        		lid = vDistincThsms.listIterator();
        		stmt = con.prepareStatement("INSERT into TRAKM(THSMSTRAKM,KDPTITRAKM,KDJENTRAKM,KDPSTTRAKM,NPMHSTRAKM,SKSEMTRAKM,NLIPSTRAKM,SKSTTTRAKM,NLIPKTRAKM)values(?,?,?,?,?,?,?,?,?)");
        		while(lid.hasNext()) {
        			String thsms = (String)lid.next();
        			stmt.setString(1, thsms);
            		stmt.setString(2, Constants.getKdpti());
            		stmt.setNull(3,java.sql.Types.VARCHAR);
            		stmt.setString(4, kdpst);
            		stmt.setString(5, npmhs);
            		stmt.setInt(6, 0);
            		stmt.setDouble(7, 0);
            		stmt.setInt(8, 0);
            		stmt.setDouble(9, 0);
            		stmt.executeUpdate();
        		}
        		
        		
    		} 
    		catch (NamingException e) {
    			e.printStackTrace();
    		}
    		catch (SQLException ex) {
    			ex.printStackTrace();
    		} 
    		catch (Exception ex) {
    			ex.printStackTrace();
    		} 
    		finally {
    			if (con!=null) {
    				try {
    					con.close();
    				}
    				catch (Exception ignore) {
    					////System.out.println(ignore);
    				}
    			}
    		}
    	}	
    }

    
    public void deleteKrs(Vector vGetRiwayatTrlsm, String npmhs) {
    	if(vGetRiwayatTrlsm!=null && vGetRiwayatTrlsm.size()>0) {
    		try {
        		//String ipAddr =  request.getRemoteAddr();
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//stmt = con.prepareStatement("delete from TRAKM where NPMHSTRAKM=?");
        		//stmt.setString(1,npmhs);
        		//stmt.executeUpdate();
        		
        		//delete @ thsmstrlsm
        		stmt = con.prepareStatement("delete from TRAKM where THSMSTRAKM=? and NPMHSTRAKM=?");
    			ListIterator litmp = vGetRiwayatTrlsm.listIterator();
    			
    			boolean done = false;
    			while(litmp.hasNext() && !done) {
    				String brs = (String)litmp.next();
    				//////System.out.println("baris vTrlsm="+brs);
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String thsmsTrlms = st.nextToken();
    				String stmhsTrlms = st.nextToken();
    				if(stmhsTrlms.equalsIgnoreCase("C")||stmhsTrlms.equalsIgnoreCase("N")) {
    					stmt.setString(1, thsmsTrlms);
    					stmt.setString(2, npmhs);
    					stmt.executeUpdate();
    				}
    				else if(stmhsTrlms.equalsIgnoreCase("K")||stmhsTrlms.equalsIgnoreCase("D")||stmhsTrlms.equalsIgnoreCase("P")){ //P=pindah jurusan
    					stmt = con.prepareStatement("delete from TRAKM where THSMSTRAKM>=? and NPMHSTRAKM=?");
    					stmt.setString(1, thsmsTrlms);
    					stmt.setString(2, npmhs);
    					int i = stmt.executeUpdate();
    					////System.out.println("delete TRNLM "+thsmsTrlms+" - "+npmhs+" = "+i);
    					done = true;
    				}
    			}
    			
    			stmt = con.prepareStatement("delete from TRNLM where THSMSTRNLM=? and NPMHSTRNLM=?");
    			litmp = vGetRiwayatTrlsm.listIterator();
    			
    			done = false;
    			while(litmp.hasNext() && !done) {
    				String brs = (String)litmp.next();
    				//////System.out.println("baris vTrlsm="+brs);
    				StringTokenizer st = new StringTokenizer(brs,"`");
    				String thsmsTrlms = st.nextToken();
    				String stmhsTrlms = st.nextToken();
    				if(stmhsTrlms.equalsIgnoreCase("C")||stmhsTrlms.equalsIgnoreCase("N")) {
    					stmt.setString(1, thsmsTrlms);
    					stmt.setString(2, npmhs);
    					stmt.executeUpdate();
    				}
    				else if(stmhsTrlms.equalsIgnoreCase("K")||stmhsTrlms.equalsIgnoreCase("D")||stmhsTrlms.equalsIgnoreCase("P")){ //P=pindah jurusan
    					stmt = con.prepareStatement("delete from TRNLM where THSMSTRNLM>=? and NPMHSTRNLM=?");
    					stmt.setString(1, thsmsTrlms);
    					stmt.setString(2, npmhs);
    					int i = stmt.executeUpdate();
    					//hapus juga riwayat trlsm pasca keluar
    					stmt = con.prepareStatement("delete from TRLSM where THSMS>? and NPMHS=?");
    					stmt.setString(1, thsmsTrlms);
    					stmt.setString(2, npmhs);
    					i = stmt.executeUpdate();
    					done = true;
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
    
    public void updateNilaiPerKelas(String thsms, String kdkmk, int idkmk, String[] npm_nlakh_bobot, String npmdos, String saya_dosennya, String cuid) {
    	//System.out.println("target cuid = "+cuid);
    	boolean syDsnNya = false;
    	if(saya_dosennya!=null && saya_dosennya.equalsIgnoreCase("true")) {
    		syDsnNya = true;
    	}
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		//if(syDsnNya) {
    		if(Checker.isStringNullOrEmpty(cuid)) {
    			stmt = con.prepareStatement("update TRNLM set NLAKHTRNLM=?,BOBOTTRNLM=?,NLAKH_BY_DOSEN=? where (THSMSTRNLM=? and NPMHSTRNLM=? and IDKMKTRNLM=?)");
    		}
    		else {
    			stmt = con.prepareStatement("update TRNLM set NLAKHTRNLM=?,BOBOTTRNLM=?,NLAKH_BY_DOSEN=? where (THSMSTRNLM=? and NPMHSTRNLM=? and IDKMKTRNLM=?) or (CLASS_POOL_UNIQUE_ID=? and NPMHSTRNLM=?)");	
    		}
    		
    		//}
    		//else {
    		//	stmt = con.prepareStatement("update TRNLM set NLAKHTRNLM=?,BOBOTTRNLM=? where THSMSTRNLM=? and NPMHSTRNLM=? and IDKMKTRNLM=?");	
    		//}
    		
    		if(npm_nlakh_bobot!=null && npm_nlakh_bobot.length>0) {
    			for(int i=0;i<npm_nlakh_bobot.length;i++) {
    				StringTokenizer st = new StringTokenizer(npm_nlakh_bobot[i],",");
    				String npmhs = st.nextToken();
    				String nlakh = st.nextToken();
    				String bobot = st.nextToken();
    				int j=1;
    				stmt.setString(1, nlakh);
    				stmt.setDouble(2, Double.parseDouble(bobot));
    				stmt.setBoolean(3, syDsnNya);
    				stmt.setString(4, thsms);
    				stmt.setString(5, npmhs);
    				stmt.setInt(6, idkmk);
    				if(!Checker.isStringNullOrEmpty(cuid)) {
    					stmt.setLong(7, Long.parseLong(cuid));
    					stmt.setString(8, npmhs);
    				}	
    				int l = stmt.executeUpdate();
    				//System.out.println(nlakh+" > "+bobot+" > "+npmhs+" > "+cuid+" = "+l);
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
    
    public void kelasKrsAdjustmen(String[]info_kelas, String npmhs) {
    	//System.out.println("target cuid = "+cuid);
    	//<%=cuid_awal+"`"+kdpst_+"`"+shift_+"`"+nopll_+"`"+npmdos_+"`"+nodos_+"`"+npmasdos_+"`"+noasdos_+"`"+kmp_+"`"+nmmdos_+"`"+nmmasdos_+"`"+sub_keter_kdkmk_+"`"+cuid%>
    	try {
    		//String ipAddr =  request.getRemoteAddr();
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("update TRNLM set SHIFTTRNLM=?,CLASS_POOL_UNIQUE_ID=? where NPMHSTRNLM=? and CLASS_POOL_UNIQUE_ID=?");
    		for(int i = 0; i< info_kelas.length;i++) {
    			StringTokenizer st = new StringTokenizer(info_kelas[i],"`");
    			
    			String cuid_awal = st.nextToken();
    			String kdpst_ = st.nextToken();
    			String shift_ = st.nextToken();
    			String nopll_ = st.nextToken();
    			String npmdos_ = st.nextToken();
    			String nodos_ = st.nextToken();
    			String npmasdos_ = st.nextToken();
    			String noasdos_ = st.nextToken();
    			String kmp_ = st.nextToken();
    			String nmmdos_ = st.nextToken();
    			String nmmasdos_ = st.nextToken();
    			String sub_keter_kdkmk_ = st.nextToken();
    			String cuid_ = st.nextToken();
    			stmt.setString(1, shift_);
    			stmt.setLong(2, Long.parseLong(cuid_));
    			stmt.setString(3, npmhs);
    			stmt.setLong(4, Long.parseLong(cuid_awal));
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
    }
}
