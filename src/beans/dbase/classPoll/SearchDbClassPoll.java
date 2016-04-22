package beans.dbase.classPoll;

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

import beans.tools.*;

import org.apache.tomcat.jdbc.pool.DataSource;

import java.util.ConcurrentModificationException;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ListIterator;
import java.util.Collections;
/**
 * Session Bean implementation class SearchDbClassPoll
 */
@Stateless
@LocalBean
public class SearchDbClassPoll extends SearchDb {
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
    public SearchDbClassPoll() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbClassPoll(String operatorNpm) {
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
    public SearchDbClassPoll(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }

/*
 * deprecated : pake yg dengan scope kampus
 */
    public Vector getDistinctClassPerKdpst(String thsms, String kdpst) {
    	/*
    	 * tambah filter scope scope kampus - scope "viewAbsen"
    	 */
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and KDPST=?");
			stmt.setString(1, thsms);
			stmt.setString(2, kdpst);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				String tmp = "";
				String kode_gabung = ""+rs.getString("KODE_PENGGABUNGAN");
				String idkur = ""+rs.getLong("IDKUR");
				String idkmk = ""+rs.getLong("IDKMK");
				String shift = ""+rs.getString("SHIFT");
				String nopll = ""+rs.getString("NORUT_KELAS_PARALEL");
				String npmdos = ""+rs.getString("NPMDOS");
				String nmmdos = ""+rs.getString("NMMDOS");
				String kdkmk = ""+rs.getString("KDKMKMAKUL");
				String nakmk = ""+rs.getString("NAKMKMAKUL");
				String cancel = ""+rs.getBoolean("CANCELED");
				String kodeKampus = ""+rs.getString("KODE_KAMPUS");
				tmp = kode_gabung+"`"+nakmk+"`"+nopll+"`"+shift+"`"+idkur+"`"+idkmk+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+cancel+"`"+kdpst+"`"+kodeKampus;
				li.add(tmp);
			}
			Collections.sort(v);
			
			v = Tool.removeDuplicateFromVector(v);
			li = v.listIterator();
			//proses kalo ada kelas gabungan
			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and KODE_PENGGABUNGAN=?");
			while(li.hasNext()) {
				String brs = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs,"`");
				String kode_gabung = ""+st.nextToken();
				if(kode_gabung!=null && !Checker.isStringNullOrEmpty(kode_gabung)) {
					stmt.setString(1, thsms);
					stmt.setString(2, kode_gabung);
					rs = stmt.executeQuery();
					String tmp = "";
					while(rs.next()) {
						String kod_gabung = ""+rs.getString("KODE_PENGGABUNGAN");
						String idkur = ""+rs.getLong("IDKUR");
						String idkmk = ""+rs.getLong("IDKMK");
						String shift = ""+rs.getString("SHIFT");
						String nopll = ""+rs.getString("NORUT_KELAS_PARALEL");
						String npmdos = ""+rs.getString("NPMDOS");
						String nmmdos = ""+rs.getString("NMMDOS");
						String kdkmk = ""+rs.getString("KDKMKMAKUL");
						String nakmk = ""+rs.getString("NAKMKMAKUL");
						String cancel = ""+rs.getBoolean("CANCELED");
						String kodeKampus = ""+rs.getString("KODE_KAMPUS");
						tmp = tmp+kod_gabung+"`"+nakmk+"`"+nopll+"`"+shift+"`"+idkur+"`"+idkmk+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+cancel+"`"+kdpst+"`"+kodeKampus+"||";
					}
					li.set(tmp);
				}
				
			}
			//stmt = con.prepareStatement("");
			
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
    	
    	return v;
    }

    
    public Vector getClassPollRules(Vector vScope, String kode_kampus) {
    	//Vector v = new Vector();
    	//ListIterator li = v.listIterator();
    	if(vScope!=null && vScope.size()>0) {
    		try {
        		
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from CLASS_POOL_RULES where KDPST=? and KODE_KAMPUS=? order by THSMS DESC limit 1");
    			ListIterator liv = vScope.listIterator();
    			while(liv.hasNext()) {
    				String brs = (String)liv.next();
    				StringTokenizer st = new StringTokenizer(brs);
    				String idObj = st.nextToken();
    				String kdpst = st.nextToken();
    				String keter = st.nextToken();
    				String lvl = st.nextToken();
    				String kdjen = st.nextToken();
    				stmt.setString(1, kdpst);
    				stmt.setString(2, kode_kampus);
    				rs = stmt.executeQuery();
    				if(rs.next()) {
    					String tkn_verificator = ""+rs.getString("TKN_VERIFICATOR");
    					String urutan = ""+rs.getBoolean("URUTAN");
    					
    					tkn_verificator = tkn_verificator.replace(" ", "_");
    					liv.set(brs+" "+tkn_verificator+" "+urutan+" "+kode_kampus);
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
    		
    	
    	return vScope;
    }
    
    /*
     * deprecated : ada tambahan cuid pada _v1
     */
    public Vector getDistinctClassPerKdpst(String thsms, String kdpst, String tknScopeKampus) {
    	/*
    	 * tambah filter scope scope kampus - scope "viewAbsen"
    	 */
    	
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			////////System.out.println(thsms);
	    	////////System.out.println(kdpst);
	    	////////System.out.println(tknScopeKampus);
			//get list kelas dari class polll
			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and KDPST=?");
			stmt.setString(1, thsms);
			stmt.setString(2, kdpst);
			rs = stmt.executeQuery();
			////////System.out.println("tmp0");
			while(rs.next()) {
				String tmp = "";
				String kode_gabung = ""+rs.getString("KODE_PENGGABUNGAN");
				String idkur = ""+rs.getLong("IDKUR");
				String idkmk = ""+rs.getLong("IDKMK");
				String shift = ""+rs.getString("SHIFT");
				String nopll = ""+rs.getString("NORUT_KELAS_PARALEL");
				String npmdos = ""+rs.getString("NPMDOS");
				String nmmdos = ""+rs.getString("NMMDOS");
				String kdkmk = ""+rs.getString("KDKMKMAKUL");
				String nakmk = ""+rs.getString("NAKMKMAKUL");
				String cancel = ""+rs.getBoolean("CANCELED");
				String kodeKampus = ""+rs.getString("KODE_KAMPUS");
				if(tknScopeKampus.contains(kodeKampus)) {
					tmp = kode_gabung+"`"+nakmk+"`"+nopll+"`"+shift+"`"+idkur+"`"+idkmk+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+cancel+"`"+kdpst+"`"+kodeKampus;
					li.add(tmp);	
					////////System.out.println("tmp1="+tmp);
				}
			}
			Collections.sort(v);
			
			//v = Tool.removeDuplicateFromVector(v);
			li = v.listIterator();
			//proses kalo ada kelas gabungan
			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and KODE_PENGGABUNGAN=?");
			while(li.hasNext()) {
				String brs = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs,"`");
				String kode_gabung = ""+st.nextToken();
				if(kode_gabung!=null && !Checker.isStringNullOrEmpty(kode_gabung)) {
					stmt.setString(1, thsms);
					stmt.setString(2, kode_gabung);
					rs = stmt.executeQuery();
					String tmp = "";
					while(rs.next()) {
						String kod_gabung = ""+rs.getString("KODE_PENGGABUNGAN");
						String idkur = ""+rs.getLong("IDKUR");
						String idkmk = ""+rs.getLong("IDKMK");
						String shift = ""+rs.getString("SHIFT");
						String nopll = ""+rs.getString("NORUT_KELAS_PARALEL");
						String npmdos = ""+rs.getString("NPMDOS");
						String nmmdos = ""+rs.getString("NMMDOS");
						String kdkmk = ""+rs.getString("KDKMKMAKUL");
						String nakmk = ""+rs.getString("NAKMKMAKUL");
						String cancel = ""+rs.getBoolean("CANCELED");
						String kodeKampus = ""+rs.getString("KODE_KAMPUS");
						tmp = tmp+kod_gabung+"`"+nakmk+"`"+nopll+"`"+shift+"`"+idkur+"`"+idkmk+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+cancel+"`"+kdpst+"`"+kodeKampus+"||";
					}
					li.set(tmp);
					////////System.out.println(tmp);
				}
				
			}
			//v = Tool.removeDuplicateFromVector(v);
			/*
			 * get jumlah mhs
			 */
			//String thsmsNow = Checker.getThsmsNow();
			
			li = v.listIterator();
			
			stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM where THSMSTRNLM=? AND KDKMKTRNLM=? AND KELASTRNLM=? AND SHIFTTRNLM=? AND KODE_KAMPUS=?");
			while(li.hasNext()) {
				String brs = (String)li.next();
				String nuTmp = "";
				StringTokenizer st = new StringTokenizer(brs,"||");
				Vector vTmp =new Vector();
				ListIterator liTmp = vTmp.listIterator();
				if(st.countTokens()>1) { //kelas gabungan
					while(st.hasMoreTokens()) {
						String tmp = st.nextToken();
						StringTokenizer stt = new StringTokenizer(tmp,"`");
						//tmp = tmp+kod_gabung+"`"+nakmk+"`"+nopll+"`"+shift+"`"+idkur+"`"+idkmk+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+cancel+"`"+kdpst+"`"+kodeKampus+"||";
						String kod_gabung = stt.nextToken();
						String nakmk = stt.nextToken();
						String nopll = stt.nextToken();
						String shift = stt.nextToken();
						String idkur = stt.nextToken();
						String idkmk = stt.nextToken();
						String npmdos = stt.nextToken();
						String nmmdos = stt.nextToken();
						String kdkmk = stt.nextToken();
						String cancel = stt.nextToken();
						kdpst = stt.nextToken();
						String kodeKampus = stt.nextToken();
						stmt.setString(1,thsms);
						stmt.setString(2,kdkmk);
						stmt.setString(3,nopll);
						stmt.setString(4,shift);
						stmt.setString(5,kodeKampus);
						rs = stmt.executeQuery();
						//nuTmp = "";
						while(rs.next()) {
							String npmhs = rs.getString("NPMHSTRNLM");
							liTmp.add(npmhs);
						}
						//keep looping adding npmhs dgn kode gabung yg sama tapi beda MK
					}
					String listNpm = "";
					if(vTmp.size()<1) {
						listNpm = "null";
					}
					else {
						vTmp = Tool.removeDuplicateFromVector(vTmp);
						liTmp = vTmp.listIterator();
						while(liTmp.hasNext()) {
							String npm = (String)liTmp.next();
							listNpm = listNpm+npm;
							if(liTmp.hasNext()) {
								listNpm = listNpm+",";
							}
						}	
					}
					//nuTmp = nuTmp+brs+"`"+listNpm+"||";
					
					 //tambah info
					 
					nuTmp=null;
					st = new StringTokenizer(brs,"||");
					while(st.hasMoreTokens()) {
						String tmp = st.nextToken();
						//StringTokenizer stt = new StringTokenizer(tmp,"`");
						//String kod_gabung = stt.nextToken();
						//String idkur = stt.nextToken();
						//String idkmk = stt.nextToken();
						//String shift = stt.nextToken();
						//String nopll = stt.nextToken();
						//String npmdos = stt.nextToken();
						//String nmmdos = stt.nextToken();
						//String kdkmk = stt.nextToken();
						//String nakmk = stt.nextToken();
						//String cancel = stt.nextToken();
						//String kodeKampus = stt.nextToken();
						nuTmp = nuTmp+tmp+"`"+listNpm+"||";
					}
					////////System.out.println("nuTmp="+nuTmp);
					li.set(nuTmp);
				}
				else {
					StringTokenizer stt = new StringTokenizer(brs,"`");
					String kod_gabung = stt.nextToken();
					String nakmk = stt.nextToken();
					String nopll = stt.nextToken();
					String shift = stt.nextToken();
					String idkur = stt.nextToken();
					String idkmk = stt.nextToken();
					String npmdos = stt.nextToken();
					String nmmdos = stt.nextToken();
					String kdkmk = stt.nextToken();
					String cancel = stt.nextToken();
					kdpst = stt.nextToken();
					String kodeKampus = stt.nextToken();
					stmt.setString(1,thsms);
					stmt.setString(2,kdkmk);
					stmt.setString(3,nopll);
					stmt.setString(4,shift);
					stmt.setString(5,kodeKampus);
					rs = stmt.executeQuery();
					
					while(rs.next()) {
						String npmhs = rs.getString("NPMHSTRNLM");
						liTmp.add(npmhs);
					}
					String listNpm = "";
					
					if(vTmp.size()<1) {
						listNpm = "null";
					}
					else {
						vTmp = Tool.removeDuplicateFromVector(vTmp);
						liTmp = vTmp.listIterator();
						while(liTmp.hasNext()) {
							String npm = (String)liTmp.next();
							listNpm = listNpm+npm;
							if(liTmp.hasNext()) {
								listNpm = listNpm+",";
							}
						}	
					}
					////////System.out.println("nuTmp1="+brs+"`"+listNpm);
					li.set(brs+"`"+listNpm);
				}		
			}
    
			//stmt = con.prepareStatement("");
			
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
    	
    	return v;
    }
    
    
    public Vector getDistinctClassPerKdpst_v1(String thsms, String kdpst, String tknScopeKampus) {
    	/*
    	 * tambah filter scope scope kampus - scope "viewAbsen"
    	 */
    	
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			////////System.out.println(thsms);
	    	////////System.out.println(kdpst);
	    	////////System.out.println(tknScopeKampus);
			//get list kelas dari class polll
			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and KDPST=?");
			stmt.setString(1, thsms);
			stmt.setString(2, kdpst);
			rs = stmt.executeQuery();
			////////System.out.println("tmp0");
			while(rs.next()) {
				String tmp = "";
				String kode_gabung = ""+rs.getString("KODE_PENGGABUNGAN");
				String idkur = ""+rs.getLong("IDKUR");
				String idkmk = ""+rs.getLong("IDKMK");
				String shift = ""+rs.getString("SHIFT");
				String nopll = ""+rs.getString("NORUT_KELAS_PARALEL");
				String npmdos = ""+rs.getString("NPMDOS");
				String nmmdos = ""+rs.getString("NMMDOS");
				String kdkmk = ""+rs.getString("KDKMKMAKUL");
				String nakmk = ""+rs.getString("NAKMKMAKUL");
				String cancel = ""+rs.getBoolean("CANCELED");
				String kodeKampus = ""+rs.getString("KODE_KAMPUS");
				String cuid = ""+rs.getString("UNIQUE_ID");
				if(tknScopeKampus.contains(kodeKampus)) {
					tmp = kode_gabung+"`"+nakmk+"`"+nopll+"`"+shift+"`"+idkur+"`"+idkmk+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+cancel+"`"+kdpst+"`"+kodeKampus+"`"+cuid;
					li.add(tmp);	
					////////System.out.println("tmp1="+tmp);
				}
			}
			Collections.sort(v);
			
			//v = Tool.removeDuplicateFromVector(v);
			li = v.listIterator();
			//proses kalo ada kelas gabungan
			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and KODE_PENGGABUNGAN=?");
			while(li.hasNext()) {
				String brs = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs,"`");
				String kode_gabung = ""+st.nextToken();
				if(kode_gabung!=null && !Checker.isStringNullOrEmpty(kode_gabung)) {
					stmt.setString(1, thsms);
					stmt.setString(2, kode_gabung);
					rs = stmt.executeQuery();
					String tmp = "";
					while(rs.next()) {
						String kod_gabung = ""+rs.getString("KODE_PENGGABUNGAN");
						String idkur = ""+rs.getLong("IDKUR");
						String idkmk = ""+rs.getLong("IDKMK");
						String shift = ""+rs.getString("SHIFT");
						String nopll = ""+rs.getString("NORUT_KELAS_PARALEL");
						String npmdos = ""+rs.getString("NPMDOS");
						String nmmdos = ""+rs.getString("NMMDOS");
						String kdkmk = ""+rs.getString("KDKMKMAKUL");
						String nakmk = ""+rs.getString("NAKMKMAKUL");
						String cancel = ""+rs.getBoolean("CANCELED");
						String kodeKampus = ""+rs.getString("KODE_KAMPUS");
						String cuid = ""+rs.getString("UNIQUE_ID");
						tmp = tmp+kod_gabung+"`"+nakmk+"`"+nopll+"`"+shift+"`"+idkur+"`"+idkmk+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+cancel+"`"+kdpst+"`"+kodeKampus+"`"+cuid+"||";
					}
					li.set(tmp);
					////////System.out.println(tmp);
				}
				
			}
			//v = Tool.removeDuplicateFromVector(v);
			/*
			 * get jumlah mhs
			 */
			//String thsmsNow = Checker.getThsmsNow();
			
			li = v.listIterator();
			
			stmt = con.prepareStatement("select distinct NPMHSTRNLM from TRNLM where THSMSTRNLM=? AND KDKMKTRNLM=? AND KELASTRNLM=? AND SHIFTTRNLM=? AND KODE_KAMPUS=?");
			while(li.hasNext()) {
				String brs = (String)li.next();
				String nuTmp = "";
				StringTokenizer st = new StringTokenizer(brs,"||");
				Vector vTmp =new Vector();
				ListIterator liTmp = vTmp.listIterator();
				if(st.countTokens()>1) { //kelas gabungan
					while(st.hasMoreTokens()) {
						String tmp = st.nextToken();
						StringTokenizer stt = new StringTokenizer(tmp,"`");
						//tmp = tmp+kod_gabung+"`"+nakmk+"`"+nopll+"`"+shift+"`"+idkur+"`"+idkmk+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+cancel+"`"+kdpst+"`"+kodeKampus+"||";
						String kod_gabung = stt.nextToken();
						String nakmk = stt.nextToken();
						String nopll = stt.nextToken();
						String shift = stt.nextToken();
						String idkur = stt.nextToken();
						String idkmk = stt.nextToken();
						String npmdos = stt.nextToken();
						String nmmdos = stt.nextToken();
						String kdkmk = stt.nextToken();
						String cancel = stt.nextToken();
						kdpst = stt.nextToken();
						String kodeKampus = stt.nextToken();
						String cuid = stt.nextToken();
						stmt.setString(1,thsms);
						stmt.setString(2,kdkmk);
						stmt.setString(3,nopll);
						stmt.setString(4,shift);
						stmt.setString(5,kodeKampus);
						rs = stmt.executeQuery();
						//nuTmp = "";
						while(rs.next()) {
							String npmhs = rs.getString("NPMHSTRNLM");
							liTmp.add(npmhs);
						}
						//keep looping adding npmhs dgn kode gabung yg sama tapi beda MK
					}
					String listNpm = "";
					if(vTmp.size()<1) {
						listNpm = "null";
					}
					else {
						vTmp = Tool.removeDuplicateFromVector(vTmp);
						liTmp = vTmp.listIterator();
						while(liTmp.hasNext()) {
							String npm = (String)liTmp.next();
							listNpm = listNpm+npm;
							if(liTmp.hasNext()) {
								listNpm = listNpm+",";
							}
						}	
					}
					//nuTmp = nuTmp+brs+"`"+listNpm+"||";
					
					 //tambah info
					 
					nuTmp=null;
					st = new StringTokenizer(brs,"||");
					while(st.hasMoreTokens()) {
						String tmp = st.nextToken();
						//StringTokenizer stt = new StringTokenizer(tmp,"`");
						//String kod_gabung = stt.nextToken();
						//String idkur = stt.nextToken();
						//String idkmk = stt.nextToken();
						//String shift = stt.nextToken();
						//String nopll = stt.nextToken();
						//String npmdos = stt.nextToken();
						//String nmmdos = stt.nextToken();
						//String kdkmk = stt.nextToken();
						//String nakmk = stt.nextToken();
						//String cancel = stt.nextToken();
						//String kodeKampus = stt.nextToken();
						nuTmp = nuTmp+tmp+"`"+listNpm+"||";
					}
					////////System.out.println("nuTmp="+nuTmp);
					li.set(nuTmp);
				}
				else {
					StringTokenizer stt = new StringTokenizer(brs,"`");
					String kod_gabung = stt.nextToken();
					String nakmk = stt.nextToken();
					String nopll = stt.nextToken();
					String shift = stt.nextToken();
					String idkur = stt.nextToken();
					String idkmk = stt.nextToken();
					String npmdos = stt.nextToken();
					String nmmdos = stt.nextToken();
					String kdkmk = stt.nextToken();
					String cancel = stt.nextToken();
					kdpst = stt.nextToken();
					String kodeKampus = stt.nextToken();
					String cuid = stt.nextToken();
					stmt.setString(1,thsms);
					stmt.setString(2,kdkmk);
					stmt.setString(3,nopll);
					stmt.setString(4,shift);
					stmt.setString(5,kodeKampus);
					rs = stmt.executeQuery();
					
					while(rs.next()) {
						String npmhs = rs.getString("NPMHSTRNLM");
						liTmp.add(npmhs);
					}
					String listNpm = "";
					
					if(vTmp.size()<1) {
						listNpm = "null";
					}
					else {
						vTmp = Tool.removeDuplicateFromVector(vTmp);
						liTmp = vTmp.listIterator();
						while(liTmp.hasNext()) {
							String npm = (String)liTmp.next();
							listNpm = listNpm+npm;
							if(liTmp.hasNext()) {
								listNpm = listNpm+",";
							}
						}	
					}
					////////System.out.println("nuTmp1="+brs+"`"+listNpm);
					li.set(brs+"`"+listNpm);
				}		
			}
    
			//stmt = con.prepareStatement("");
			
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
    	
    	return v;
    }
    
    public Vector getDistinctClassPerKdpst_v1_simple(String thsms, String kdpst, String tknScopeKampus) {
    	/*
    	 * output : list seluruh kelas kecuali yang di cancel & tidak ada kode penggabungan
    	 */
    	
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			////////System.out.println(thsms);
	    	////////System.out.println(kdpst);
	    	////////System.out.println(tknScopeKampus);
			/*
			 * 1. Get kelas non penggabungan yang tidak di cancel
			 */
			//stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and KDPST=? order by KODE_PENGGABUNGAN,CANCELED,IDKMK");
			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and KDPST=? and CANCELED=? and KODE_PENGGABUNGAN is null");
			stmt.setString(1, thsms);
			stmt.setString(2, kdpst);
			stmt.setBoolean(3,false);
			rs = stmt.executeQuery();
			while(rs.next()) {
				String tmp = "";
				//String kode_gabung = ""+rs.getString("KODE_PENGGABUNGAN");
				
				
				String idkur_cp = ""+rs.getInt("IDKUR");
				String idkmk_cp = ""+rs.getInt("IDKMK");
				String thsms_cp = ""+rs.getString("THSMS");
				String kdpst_cp = ""+rs.getString("KDPST");
				String shift_cp = ""+rs.getString("SHIFT");
				String nopll_cp = ""+rs.getInt("NORUT_KELAS_PARALEL");
				String init_npm_inp_cp = ""+rs.getString("INIT_NPM_INPUT");
				String lates_npm_upd_cp = ""+rs.getString("LATEST_NPM_UPDATE");
				String lates_stat_cp = ""+rs.getString("LATEST_STATUS_INFO");
				String cur_avail_stat_cp = ""+rs.getString("CURR_AVAIL_STATUS");
				String locked_cp = ""+rs.getBoolean("LOCKED");
				String npmdos_cp = ""+rs.getString("NPMDOS");
				String nodos_cp = ""+rs.getString("NODOS");
				String npmasdos_cp = ""+rs.getString("NPMASDOS");
				String noasdos_cp = ""+rs.getString("NOASDOS");
				String canceled_cp = ""+rs.getBoolean("CANCELED");
				String kode_kls_cp = ""+rs.getString("KODE_KELAS");
				String kode_ruang_cp = ""+rs.getString("KODE_RUANG");
				String kode_gedung_cp = ""+rs.getString("KODE_GEDUNG");
				String kode_kampus_cp = ""+rs.getString("KODE_KAMPUS");
				String tkn_hr_tm_cp = ""+rs.getString("TKN_HARI_TIME");
				String nmmdos_cp = ""+rs.getString("NMMDOS");
				String nmmasdos_cp = ""+rs.getString("NMMASDOS");
				String enrolled_cp = ""+rs.getInt("ENROLLED");
				String max_enrolled_cp = ""+rs.getInt("MAX_ENROLLED");
				String min_enrolled_cp = ""+rs.getInt("MIN_ENROLLED");
				String sub_keter_kdmk_cp = ""+rs.getString("SUB_KETER_KDKMK");
				String init_req_tm_cp = ""+rs.getTimestamp("INIT_REQ_TIME");
				String tkn_npm_approval_cp = ""+rs.getString("TKN_NPM_APPROVAL");
				String tkn_apr_tm_cp = ""+rs.getString("TKN_APPROVAL_TIME");
				String target_ttmhs_cp = ""+rs.getInt("TARGET_TTMHS");
				String passed_cp = ""+rs.getBoolean("PASSED");
				String rejected_cp = ""+rs.getBoolean("REJECTED");
				String kode_gabung_cp = ""+rs.getString("KODE_PENGGABUNGAN");
				String kode_gabung_univ_cp = ""+rs.getString("KODE_GABUNGAN_UNIV");
				String cuid_cp = ""+rs.getLong("UNIQUE_ID");
				
				if(tknScopeKampus.contains(kode_kampus_cp)) {
					//tmp = idkmk+"`"+nopll+"`"+shift+"`"+idkur+"`"+nakmk+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+cancel+"`"+kdpst+"`"+kodeKampus+"`"+cuid;
					tmp = shift_cp+","+nopll_cp+","+cur_avail_stat_cp+","+npmdos_cp+","+npmasdos_cp+","+canceled_cp+","+kode_kls_cp+","+kode_ruang_cp+","+kode_gedung_cp+","+kode_kampus_cp+","+tkn_hr_tm_cp+","+nmmdos_cp+","+nmmasdos_cp+","+enrolled_cp+","+max_enrolled_cp+","+min_enrolled_cp+","+kode_gabung_cp+","+cuid_cp+","+idkmk_cp;
					li.add(tmp);	
				}
			}
			/*
			 * 2. Get kelas penggabungan 
			 * kalo kelas penggabungan tidak perlu ada filter kampus, fak , dsb karena disatukan jadi avail to take
			 */
			stmt = con.prepareStatement("select distinct KODE_PENGGABUNGAN from CLASS_POOL where THSMS=? and KDPST=? and KODE_PENGGABUNGAN is not null");
			stmt.setString(1, thsms);
			stmt.setString(2, kdpst);
			rs = stmt.executeQuery();
			String list_kode = null;
			if(rs.next()) {
				list_kode = new String();
				do {
					list_kode = list_kode+rs.getString("KODE_PENGGABUNGAN")+"`";
				}
				while(rs.next());
			}
			//////System.out.println("list_kode="+list_kode);
			if(list_kode!=null && !Checker.isStringNullOrEmpty(list_kode)) {
				stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=?  and KODE_PENGGABUNGAN=? order by CANCELED,SHIFT");
				StringTokenizer st = new StringTokenizer(list_kode,"`");
				while(st.hasMoreTokens()) {
					String tmp = "";
					String kode = st.nextToken();
					stmt.setString(1, thsms);
					stmt.setLong(2, Long.parseLong(kode));
					rs = stmt.executeQuery();
					while(rs.next()) {
						String idkur_cp = ""+rs.getInt("IDKUR");
						String idkmk_cp = ""+rs.getInt("IDKMK");
						String thsms_cp = ""+rs.getString("THSMS");
						String kdpst_cp = ""+rs.getString("KDPST");
						String shift_cp = ""+rs.getString("SHIFT");
						String nopll_cp = ""+rs.getInt("NORUT_KELAS_PARALEL");
						String init_npm_inp_cp = ""+rs.getString("INIT_NPM_INPUT");
						String lates_npm_upd_cp = ""+rs.getString("LATEST_NPM_UPDATE");
						String lates_stat_cp = ""+rs.getString("LATEST_STATUS_INFO");
						String cur_avail_stat_cp = ""+rs.getString("CURR_AVAIL_STATUS");
						String locked_cp = ""+rs.getBoolean("LOCKED");
						String npmdos_cp = ""+rs.getString("NPMDOS");
						String nodos_cp = ""+rs.getString("NODOS");
						String npmasdos_cp = ""+rs.getString("NPMASDOS");
						String noasdos_cp = ""+rs.getString("NOASDOS");
						String canceled_cp = ""+rs.getBoolean("CANCELED");
						String kode_kls_cp = ""+rs.getString("KODE_KELAS");
						String kode_ruang_cp = ""+rs.getString("KODE_RUANG");
						String kode_gedung_cp = ""+rs.getString("KODE_GEDUNG");
						String kode_kampus_cp = ""+rs.getString("KODE_KAMPUS");
						String tkn_hr_tm_cp = ""+rs.getString("TKN_HARI_TIME");
						String nmmdos_cp = ""+rs.getString("NMMDOS");
						String nmmasdos_cp = ""+rs.getString("NMMASDOS");
						String enrolled_cp = ""+rs.getInt("ENROLLED");
						String max_enrolled_cp = ""+rs.getInt("MAX_ENROLLED");
						String min_enrolled_cp = ""+rs.getInt("MIN_ENROLLED");
						String sub_keter_kdmk_cp = ""+rs.getString("SUB_KETER_KDKMK");
						String init_req_tm_cp = ""+rs.getTimestamp("INIT_REQ_TIME");
						String tkn_npm_approval_cp = ""+rs.getString("TKN_NPM_APPROVAL");
						String tkn_apr_tm_cp = ""+rs.getString("TKN_APPROVAL_TIME");
						String target_ttmhs_cp = ""+rs.getInt("TARGET_TTMHS");
						String passed_cp = ""+rs.getBoolean("PASSED");
						String rejected_cp = ""+rs.getBoolean("REJECTED");
						String kode_gabung_cp = ""+rs.getString("KODE_PENGGABUNGAN");
						String kode_gabung_univ_cp = ""+rs.getString("KODE_GABUNGAN_UNIV");
						String cuid_cp = ""+rs.getLong("UNIQUE_ID");
						
						
						
						//if(tknScopeKampus.contains(kodeKampus)) {
						//shift2+","+norutKelasParalel2+","+currStatus2+","+npmdos2+","+npmasdos2+","+canceled2+","+kodeKelas2+","+kodeRuang2+","+kodeGedung2+","+kodeKampus2+","+tknDayTime2+","+nmmdos2+","+nmmasdos2+","+enrolled2+","+maxEnrolled2+","+minEnrolled2);
						//shift_cp+","+nopll_cp+","+cur_avail_stat_cp+","+npmdos_cp+","+npmasdos_cp+","+canceled_cp+","+kode_kls_cp+","+kode_ruang_cp+","+kode_gedung_cp+","+kode_kampus_cp+","+tkn_hr_tm_cp+","+nmmdos_cp+","+nmmasdos_cp+","+enrolled_cp+","+max_enrolled_cp+","+min_enrolled_cp);
						tmp = tmp+shift_cp+","+nopll_cp+","+cur_avail_stat_cp+","+npmdos_cp+","+npmasdos_cp+","+canceled_cp+","+kode_kls_cp+","+kode_ruang_cp+","+kode_gedung_cp+","+kode_kampus_cp+","+tkn_hr_tm_cp+","+nmmdos_cp+","+nmmasdos_cp+","+enrolled_cp+","+max_enrolled_cp+","+min_enrolled_cp+","+kode_gabung_cp+","+cuid_cp+","+idkmk_cp+"||";	
					}
					li.add(tmp);	
				}
				
			}
			Collections.sort(v);

			
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
    	
    	return v;
    }
    
    
    /*
     * PRUBAHAN DISINI HARUS DIRUBAH DI adhockAddInfoMkToVsdh
     */
    public Vector adhockAddInfoMkToVblm(Vector vBlmOrSdh, String thsms_target, String kdpst) {
    	/*
    	 * tambah detail makul
    	 * + alternativ kdkmk = matakuliah sama yg ada pada prodi fakltas atau kampus lainnya
    	 */
    	if(vBlmOrSdh!=null && vBlmOrSdh.size()>0) {
    		ListIterator li = vBlmOrSdh.listIterator();
    		
	    	
	    	try {
				Context initContext  = new InitialContext();
				Context envContext  = (Context)initContext.lookup("java:/comp/env");
				ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
				con = ds.getConnection();
				stmt = con.prepareStatement("select * from CLASS_POOL where IDKMK=? and THSMS=? and KDPST=? and SHIFT=? and NORUT_KELAS_PARALEL=?");
				while(li.hasNext()) {
					String brs = (String)li.next();
					////////System.out.println("barisx="+brs);
					StringTokenizer st=new StringTokenizer(brs,",");
					String idkmk=st.nextToken();
					String kdkmk=st.nextToken();
					String nakmk=st.nextToken();
					//nakmk = nakmk.replace("tandaKoma", ",");
					String skstm=st.nextToken();
					String skspr=st.nextToken();
					String skslp=st.nextToken();
					String semes=st.nextToken();
					String tmp = "";
					boolean first = true;
					while(st.hasMoreTokens()) {
						//liTmp.add(shift2+","+norutKelasParalel2+","+currStatus2+","+npmdos2+","+npmasdos2+","+canceled2+","+kodeKelas2+","+kodeRuang2+","+kodeGedung2+","+kodeKampus2+","+tknDayTime2+","+nmmdos2+","+nmmasdos2+","+enrolled2+","+maxEnrolled2+","+minEnrolled2);
						
						String shift = st.nextToken();
						String norutKelasParalel = st.nextToken();
						String currStatus = st.nextToken();
						String npmdos = st.nextToken();
						String npmasdos = st.nextToken();
						String canceled = st.nextToken();
						String kodeKelas = st.nextToken();
						String kodeRuang = st.nextToken();
						String kodeGedung = st.nextToken();
						String kodeKampus = st.nextToken();
						String tknDayTime = st.nextToken();
						String nmmdos = st.nextToken();
						String nmmasdos = st.nextToken();
						String enrolled = st.nextToken();
						String maxEnrolled = st.nextToken();
						String minEnrolled = st.nextToken();
					
						stmt.setLong(1, Long.parseLong(idkmk));
						stmt.setString(2, thsms_target);
						stmt.setString(3,kdpst);
						stmt.setString(4, shift);
						stmt.setInt(5, Integer.parseInt(norutKelasParalel));
						rs = stmt.executeQuery();
						rs.next();//harus ada di class pool
						String subKeterKdkmk =""+rs.getString("SUB_KETER_KDKMK");
						String uniqueId = ""+rs.getLong("UNIQUE_ID");
						if(first) {
							first = false;
							tmp = tmp+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+subKeterKdkmk+","+uniqueId;
						}
						else {
							tmp = tmp+","+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+subKeterKdkmk+","+uniqueId;
						}
					}

					li.set(idkmk+","+kdkmk+","+nakmk+","+skstm+","+skspr+","+skslp+","+semes+","+tmp);
				}
				
				/*update 13Maret2015
				//add info idkkmk alternatif
				li = vBlmOrSdh.listIterator();
				stmt = con.prepareStatement("select * from MAKUL where IDKMKMAKUL=?");
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,",");
					String idkmk=st.nextToken();
					String kdkmk=st.nextToken();
					String nakmk=st.nextToken();
					String skstm=st.nextToken();
					String skspr=st.nextToken();
					String skslp=st.nextToken();
					String semes=st.nextToken();
					String tmp ="";
					boolean first = true;
					while(st.hasMoreTokens()) {
						String shift = st.nextToken();
						String norutKelasParalel = st.nextToken();
						String currStatus = st.nextToken();
						String npmdos = st.nextToken();
						String npmasdos = st.nextToken();
						String canceled = st.nextToken();
						String kodeKelas = st.nextToken();
						String kodeRuang = st.nextToken();
						String kodeGedung = st.nextToken();
						String kodeKampus = st.nextToken();
						String tknDayTime = st.nextToken();
						String nmmdos = st.nextToken();
						String nmmasdos = st.nextToken();
						String enrolled = st.nextToken();
						String maxEnrolled = st.nextToken();
						String minEnrolled = st.nextToken();
						String subKeterKdkmk = st.nextToken();
						stmt.setLong(1, Long.parseLong(idkmk));
						rs = stmt.executeQuery();
						rs.next();
						String idMakulSama = ""+rs.getString("ID_MAKUL_SAMA");
						idMakulSama = idMakulSama.replace(","," ");
						if(first) {
							first = false;
							tmp = tmp+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+subKeterKdkmk+","+idMakulSama;
						}
						else {
							tmp = tmp+","+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+subKeterKdkmk+","+idMakulSama;
						}
						li.set(idkmk+","+kdkmk+","+nakmk+","+skstm+","+skspr+","+skslp+","+semes+","+tmp);
						//if(idkmk.equalsIgnoreCase("205")) {
						//	//////System.out.println(idkmk+","+kdkmk+","+nakmk+","+skstm+","+skspr+","+skslp+","+semes+","+tmp);
						//}
					}
					
				}
				////////System.out.println("done");
				//end update 13Maret2015
				 * 
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
	    	catch(Exception e) {
	    		e.printStackTrace();
	    	}
			finally {
				if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
				if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
				if (con!=null) try { con.close();} catch (Exception ignore){}
			}	
    	}
    	
    	
    	return vBlmOrSdh;
    }
    
    
    public Vector adhockAddInfoMkToVblm_v1(Vector vBlmOrSdh, String thsms_target, String kdpst) {
    	/*
    	 * tambah detail makul
    	 * + alternativ kdkmk = matakuliah sama yg ada pada prodi fakltas atau kampus lainnya
    	 */
    	if(vBlmOrSdh!=null && vBlmOrSdh.size()>0) {
    		ListIterator li = vBlmOrSdh.listIterator();
    		
	    	
	    	try {
				Context initContext  = new InitialContext();
				Context envContext  = (Context)initContext.lookup("java:/comp/env");
				ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
				con = ds.getConnection();
				//stmt = con.prepareStatement("select * from CLASS_POOL where IDKMK=? and THSMS=? and KDPST=? and SHIFT=? and NORUT_KELAS_PARALEL=?");
				stmt = con.prepareStatement("select * from CLASS_POOL where UNIQUE_ID=?");
				while(li.hasNext()) {
					String brs = (String)li.next();
					////////System.out.println("barisx="+brs);
					StringTokenizer st=new StringTokenizer(brs,",");
					String idkmk=st.nextToken();
					String kdkmk=st.nextToken();
					String nakmk=st.nextToken();
					//nakmk = nakmk.replace("tandaKoma", ",");
					String skstm=st.nextToken();
					String skspr=st.nextToken();
					String skslp=st.nextToken();
					String semes=st.nextToken();
					String tmp = "";
					boolean first = true;
					while(st.hasMoreTokens()) {
						//liTmp.add(shift2+","+norutKelasParalel2+","+currStatus2+","+npmdos2+","+npmasdos2+","+canceled2+","+kodeKelas2+","+kodeRuang2+","+kodeGedung2+","+kodeKampus2+","+tknDayTime2+","+nmmdos2+","+nmmasdos2+","+enrolled2+","+maxEnrolled2+","+minEnrolled2);
						
						String shift = st.nextToken();
						String norutKelasParalel = st.nextToken();
						String currStatus = st.nextToken();
						String npmdos = st.nextToken();
						String npmasdos = st.nextToken();
						String canceled = st.nextToken();
						String kodeKelas = st.nextToken();
						String kodeRuang = st.nextToken();
						String kodeGedung = st.nextToken();
						String kodeKampus = st.nextToken();
						String tknDayTime = st.nextToken();
						String nmmdos = st.nextToken();
						String nmmasdos = st.nextToken();
						String enrolled = st.nextToken();
						String maxEnrolled = st.nextToken();
						String minEnrolled = st.nextToken();
						String kode_gab_cp = st.nextToken();
						String cuid_cp = st.nextToken();
						String idkmk_cp = st.nextToken();
					
						stmt.setLong(1, Long.parseLong(cuid_cp));
						//stmt.setString(2, thsms_target);
					//	stmt.setString(3,kdpst);
					//	stmt.setString(4, shift);
					//	stmt.setInt(5, Integer.parseInt(norutKelasParalel));
						rs = stmt.executeQuery();
						rs.next();//harus ada di class pool
						String subKeterKdkmk =""+rs.getString("SUB_KETER_KDKMK");
						//String uniqueId = ""+rs.getLong("UNIQUE_ID");
						if(first) {
							first = false;
							tmp = tmp+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+cuid_cp+","+idkmk_cp+","+subKeterKdkmk+","+kode_gab_cp;
						}
						else {
							tmp = tmp+","+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+cuid_cp+","+idkmk_cp+","+subKeterKdkmk+","+kode_gab_cp;
						}
					}

					li.set(idkmk+","+kdkmk+","+nakmk+","+skstm+","+skspr+","+skslp+","+semes+","+tmp);
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
    	
    	
    	return vBlmOrSdh;
    }

    
    public Vector adhockAddInfoMkToVsdh_v1(Vector vBlmOrSdh, String thsms_target, String kdpst) {
    	/*
    	 * REMINDER!!!!
    	 * vBlm TIDAK SAMA STRUKTURNYA DENGAN vSdh
    	 */
    	if(vBlmOrSdh!=null && vBlmOrSdh.size()>0) {
    		ListIterator li = vBlmOrSdh.listIterator();
    		
	    	
	    	try {
				Context initContext  = new InitialContext();
				Context envContext  = (Context)initContext.lookup("java:/comp/env");
				ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
				con = ds.getConnection();
				//stmt = con.prepareStatement("select * from CLASS_POOL where IDKMK=? and THSMS=? and KDPST=? and SHIFT=? and NORUT_KELAS_PARALEL=?");
				stmt = con.prepareStatement("select * from CLASS_POOL where UNIQUE_ID=?");
				while(li.hasNext()) {
					String brs = (String)li.next();
					////////System.out.println("barisx="+brs);
					StringTokenizer st=new StringTokenizer(brs,",");
					String idkmk=st.nextToken();
					String thsms=st.nextToken();
					String kdkmk=st.nextToken();
					String nakmk=st.nextToken();
					//nakmk = nakmk.replace("tandaKoma", ",");
					String nlakh=st.nextToken();
					String bobot=st.nextToken();
					String cuid=st.nextToken();
					String cuid_init=st.nextToken();
					String tmp="";
					boolean first = true;
					while(st.hasMoreTokens()) {
						//liTmp.add(shift2+","+norutKelasParalel2+","+currStatus2+","+npmdos2+","+npmasdos2+","+canceled2+","+kodeKelas2+","+kodeRuang2+","+kodeGedung2+","+kodeKampus2+","+tknDayTime2+","+nmmdos2+","+nmmasdos2+","+enrolled2+","+maxEnrolled2+","+minEnrolled2);
						String shift = st.nextToken();
						String norutKelasParalel = st.nextToken();
						String currStatus = st.nextToken();
						String npmdos = st.nextToken();
						String npmasdos = st.nextToken();
						String canceled = st.nextToken();
						String kodeKelas = st.nextToken();
						String kodeRuang = st.nextToken();
						String kodeGedung = st.nextToken();
						String kodeKampus = st.nextToken();
						String tknDayTime = st.nextToken();
						String nmmdos = st.nextToken();
						String nmmasdos = st.nextToken();
						String enrolled = st.nextToken();
						String maxEnrolled = st.nextToken();
						String minEnrolled = st.nextToken();
						String kode_gab_cp = st.nextToken();
						String cuid_cp = st.nextToken();
						String idkmk_cp = st.nextToken();
					
						stmt.setLong(1, Long.parseLong(cuid_cp));
						/*
						stmt.setLong(1, Long.parseLong(idkmk));
						stmt.setString(2, thsms_target);
						stmt.setString(3,kdpst);
						stmt.setString(4, shift);
						stmt.setInt(5, Integer.parseInt(norutKelasParalel));
						*/
						rs = stmt.executeQuery();
						rs.next();//harus ada di class pool
						String subKeterKdkmk =""+rs.getString("SUB_KETER_KDKMK");
						//String uniqueId = ""+rs.getLong("UNIQUE_ID");
						
						
						if(first) {
							first = false;
							tmp = tmp+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+cuid_cp+","+idkmk_cp+","+subKeterKdkmk+","+kode_gab_cp;
						}
						else {
							tmp = tmp+","+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+cuid_cp+","+idkmk_cp+","+subKeterKdkmk+","+kode_gab_cp;
						}
						
					}

					li.set(idkmk+","+thsms+","+kdkmk+","+nakmk+","+nlakh+","+bobot+","+cuid+","+cuid_init+","+tmp);
				}
				
				
				/*update 13Maret2015
				//add info idkkmk alternatif
				li = vBlmOrSdh.listIterator();
				stmt = con.prepareStatement("select * from MAKUL where IDKMKMAKUL=?");
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,",");
					String idkmk=st.nextToken();
					String kdkmk=st.nextToken();
					String nakmk=st.nextToken();
					String skstm=st.nextToken();
					String skspr=st.nextToken();
					String skslp=st.nextToken();
					String semes=st.nextToken();
					String tmp ="";
					boolean first = true;
					while(st.hasMoreTokens()) {
						String shift = st.nextToken();
						String norutKelasParalel = st.nextToken();
						String currStatus = st.nextToken();
						String npmdos = st.nextToken();
						String npmasdos = st.nextToken();
						String canceled = st.nextToken();
						String kodeKelas = st.nextToken();
						String kodeRuang = st.nextToken();
						String kodeGedung = st.nextToken();
						String kodeKampus = st.nextToken();
						String tknDayTime = st.nextToken();
						String nmmdos = st.nextToken();
						String nmmasdos = st.nextToken();
						String enrolled = st.nextToken();
						String maxEnrolled = st.nextToken();
						String minEnrolled = st.nextToken();
						String subKeterKdkmk = st.nextToken();
						stmt.setLong(1, Long.parseLong(idkmk));
						rs = stmt.executeQuery();
						rs.next();
						String idMakulSama = ""+rs.getString("ID_MAKUL_SAMA");
						idMakulSama = idMakulSama.replace(","," ");
						if(first) {
							first = false;
							tmp = tmp+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+subKeterKdkmk+","+idMakulSama;
						}
						else {
							tmp = tmp+","+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+subKeterKdkmk+","+idMakulSama;
						}
						li.set(idkmk+","+kdkmk+","+nakmk+","+skstm+","+skspr+","+skslp+","+semes+","+tmp);
						//if(idkmk.equalsIgnoreCase("205")) {
						//	//////System.out.println(idkmk+","+kdkmk+","+nakmk+","+skstm+","+skspr+","+skslp+","+semes+","+tmp);
						//}
					}
					
				}
				////////System.out.println("done");
				//end update 13Maret2015
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
	    	catch(Exception e) {
	    		e.printStackTrace();
	    	}
			finally {
				if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
				if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
				if (con!=null) try { con.close();} catch (Exception ignore){}
			}	
    	}
    	
    	
    	return vBlmOrSdh;
    }    
    
    /*
     * PRUBAHAN DISINI HARUS DIRUBAH DI adhockAddInfoMkToVblm
     */
    public Vector adhockAddInfoMkToVsdh(Vector vBlmOrSdh, String thsms_target, String kdpst) {
    	if(vBlmOrSdh!=null && vBlmOrSdh.size()>0) {
    		ListIterator li = vBlmOrSdh.listIterator();
    		
	    	
	    	try {
				Context initContext  = new InitialContext();
				Context envContext  = (Context)initContext.lookup("java:/comp/env");
				ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
				con = ds.getConnection();
				stmt = con.prepareStatement("select * from CLASS_POOL where IDKMK=? and THSMS=? and KDPST=? and SHIFT=? and NORUT_KELAS_PARALEL=?");
				while(li.hasNext()) {
					String brs = (String)li.next();
					////////System.out.println("barisx="+brs);
					StringTokenizer st=new StringTokenizer(brs,",");
					String idkmk=st.nextToken();
					String thsms=st.nextToken();
					String kdkmk=st.nextToken();
					String nakmk=st.nextToken();
					//nakmk = nakmk.replace("tandaKoma", ",");
					String nlakh=st.nextToken();
					String bobot=st.nextToken();
					String tmp="";
					boolean first = true;
					while(st.hasMoreTokens()) {
						//liTmp.add(shift2+","+norutKelasParalel2+","+currStatus2+","+npmdos2+","+npmasdos2+","+canceled2+","+kodeKelas2+","+kodeRuang2+","+kodeGedung2+","+kodeKampus2+","+tknDayTime2+","+nmmdos2+","+nmmasdos2+","+enrolled2+","+maxEnrolled2+","+minEnrolled2);
						String shift = st.nextToken();
						String norutKelasParalel = st.nextToken();
						String currStatus = st.nextToken();
						String npmdos = st.nextToken();
						String npmasdos = st.nextToken();
						String canceled = st.nextToken();
						String kodeKelas = st.nextToken();
						String kodeRuang = st.nextToken();
						String kodeGedung = st.nextToken();
						String kodeKampus = st.nextToken();
						String tknDayTime = st.nextToken();
						String nmmdos = st.nextToken();
						String nmmasdos = st.nextToken();
						String enrolled = st.nextToken();
						String maxEnrolled = st.nextToken();
						String minEnrolled = st.nextToken();
						stmt.setLong(1, Long.parseLong(idkmk));
						stmt.setString(2, thsms_target);
						stmt.setString(3,kdpst);
						stmt.setString(4, shift);
						stmt.setInt(5, Integer.parseInt(norutKelasParalel));
						rs = stmt.executeQuery();
						rs.next();//harus ada di class pool
						String subKeterKdkmk =""+rs.getString("SUB_KETER_KDKMK");
						String uniqueId = ""+rs.getLong("UNIQUE_ID");
						
						
						if(first) {
							first = false;
							tmp = tmp+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+subKeterKdkmk+","+uniqueId;
						}
						else {
							tmp = tmp+","+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+subKeterKdkmk+","+uniqueId;
						}
						
					}

					li.set(idkmk+","+thsms+","+kdkmk+","+nakmk+","+nlakh+","+bobot+","+tmp);
				}
				
				
				/*update 13Maret2015
				//add info idkkmk alternatif
				li = vBlmOrSdh.listIterator();
				stmt = con.prepareStatement("select * from MAKUL where IDKMKMAKUL=?");
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,",");
					String idkmk=st.nextToken();
					String kdkmk=st.nextToken();
					String nakmk=st.nextToken();
					String skstm=st.nextToken();
					String skspr=st.nextToken();
					String skslp=st.nextToken();
					String semes=st.nextToken();
					String tmp ="";
					boolean first = true;
					while(st.hasMoreTokens()) {
						String shift = st.nextToken();
						String norutKelasParalel = st.nextToken();
						String currStatus = st.nextToken();
						String npmdos = st.nextToken();
						String npmasdos = st.nextToken();
						String canceled = st.nextToken();
						String kodeKelas = st.nextToken();
						String kodeRuang = st.nextToken();
						String kodeGedung = st.nextToken();
						String kodeKampus = st.nextToken();
						String tknDayTime = st.nextToken();
						String nmmdos = st.nextToken();
						String nmmasdos = st.nextToken();
						String enrolled = st.nextToken();
						String maxEnrolled = st.nextToken();
						String minEnrolled = st.nextToken();
						String subKeterKdkmk = st.nextToken();
						stmt.setLong(1, Long.parseLong(idkmk));
						rs = stmt.executeQuery();
						rs.next();
						String idMakulSama = ""+rs.getString("ID_MAKUL_SAMA");
						idMakulSama = idMakulSama.replace(","," ");
						if(first) {
							first = false;
							tmp = tmp+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+subKeterKdkmk+","+idMakulSama;
						}
						else {
							tmp = tmp+","+shift+","+norutKelasParalel+","+currStatus+","+npmdos+","+npmasdos+","+canceled+","+kodeKelas+","+kodeRuang+","+kodeGedung+","+kodeKampus+","+tknDayTime+","+nmmdos+","+nmmasdos+","+enrolled+","+maxEnrolled+","+minEnrolled+","+subKeterKdkmk+","+idMakulSama;
						}
						li.set(idkmk+","+kdkmk+","+nakmk+","+skstm+","+skspr+","+skslp+","+semes+","+tmp);
						//if(idkmk.equalsIgnoreCase("205")) {
						//	//////System.out.println(idkmk+","+kdkmk+","+nakmk+","+skstm+","+skspr+","+skslp+","+semes+","+tmp);
						//}
					}
					
				}
				////////System.out.println("done");
				//end update 13Maret2015
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
	    	catch(Exception e) {
	    		e.printStackTrace();
	    	}
			finally {
				if (rs!=null) try  { rs.close(); } catch (Exception ignore){}
				if (stmt!=null) try  { stmt.close(); } catch (Exception ignore){}
				if (con!=null) try { con.close();} catch (Exception ignore){}
			}	
    	}
    	
    	
    	return vBlmOrSdh;
    }

    
    /*
     * Ngga tau yg make siapa
     * kemungkinana sih DEPRECATED
     */
    public Vector getListKelasYgSedangDiajar(Vector vScopeKdpst,String scopeHakAkses, String scopeKmp, String npmUsr) {
    	/*
    	 * get list kelas yang harus diajarkan dan harus dinilai
    	 * jadi berdasarkan THSMS_INP_NILAI_MK columnn pada CALENDER tabel
    	 * return v length 0 bila kosong
    	 */
    	
    	/*
		 * untuk scope KDPST, bila own artinya dia dosen jadi hanya kelas yang diajar
		 * untuk ktu kdpst, dst
		 */
    	Vector v = new Vector();
    	ListIterator li = null;
    	//hanya untuk pembuatan krn admin ngga ngajar jadi dirubah sama yg ngajar
    	//di comment after programs works
    	/*
    	if(npmUsr.equalsIgnoreCase("0000000000001")) {
    	 
    		npmUsr = "0001113200006";//nuzelmar
    	}
    	*/
    	
    	
    	try {
    		
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			String thsms_inp_nilai = null;
			stmt = con.prepareStatement("select  THSMS_INP_NILAI_MK from CALENDAR where AKTIF=true");
			rs = stmt.executeQuery();
			rs.next();
			thsms_inp_nilai = rs.getString(1);
			
			//cek apa usr adalah only dosen = kdpstscope = own
	    	if(vScopeKdpst!=null && vScopeKdpst.size()==1) {
	    		//////System.out.println("masuk sinidosen="+npmUsr);
	    		li = vScopeKdpst.listIterator();
	    		String scope = (String)li.next(); 
	    		if(scope.contains("own")) {
	    			//yes dosen 
	    			
	    			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and CANCELED=? and NPMDOS=?");
	    			stmt.setString(1, thsms_inp_nilai);
	    			stmt.setBoolean(2,false);
	    			stmt.setString(3, npmUsr);
	    		}
	    	}
	    	else {
	    		//berarti non dosen - 
	    		//scope  kdpst
	    		//////System.out.println("NONdosen");
	    		li = vScopeKdpst.listIterator();
	    		String sql = "(";
	    		while(li.hasNext()) {
	    			String kdpst = (String)li.next();
	    			sql = sql+"KDPST='"+kdpst+"'";
	    			if(li.hasNext()) {
	    				sql = sql+" OR ";
	    			}
	    		}
	    		sql = sql+")";
	    		////////System.out.println("sql1 = "+sql);
	    		//scope kampus
	    		//////System.out.println("scopeKmp="+scopeKmp);
	    		StringTokenizer st = new StringTokenizer(scopeKmp,",");
	    		String sql1 = "(";
	    		while(st.hasMoreTokens()) {
	    			String kmp = st.nextToken();
	    			sql1 = sql1+"KODE_KAMPUS='"+kmp+"'";
	    			if(st.hasMoreTokens()) {
	    				sql1 = sql1+" OR ";
	    			}
	    		}
	    		sql1 = sql1+")";
	    		//////System.out.println("sql2 = "+sql1);
	    		//////System.out.println("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and CANCELED=? and "+sql+" AND "+sql1);
	    		stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and CANCELED=? and "+sql+" AND "+sql1);
	    		stmt.setString(1, thsms_inp_nilai);
    			stmt.setBoolean(2,false);
	    	}
			li = v.listIterator();
			rs = stmt.executeQuery();
			/*
			 * get all kelas dibuka at thsms
			 */
			while(rs.next()) {
				String idkur = ""+rs.getLong("IDKUR");
				String idkmk = ""+rs.getLong("IDKMK");
				//thsms ignoeee
				String kdpst = ""+rs.getString("KDPST");
				String shift = ""+rs.getString("SHIFT");
				String noKlsPll = ""+rs.getInt("NORUT_KELAS_PARALEL");
				String npm_pertama_input = ""+rs.getString("INIT_NPM_INPUT");
				String npm_terakhir_updat = ""+rs.getString("LATEST_NPM_UPDATE");
				String status_akhir = ""+rs.getString("LATEST_STATUS_INFO");
				String curr_avail_status = ""+rs.getString("CURR_AVAIL_STATUS");
				String locked_or_editable = ""+rs.getBoolean("LOCKED");
				String npmdos = ""+rs.getString("NPMDOS");
				String nodos = ""+rs.getString("NODOS");
				String npmasdos = ""+rs.getString("NPMASDOS");
				String noasdos = ""+rs.getString("NOASDOS");
				String canceled = ""+rs.getBoolean("CANCELED");
				String kode_kelas = ""+rs.getString("KODE_KELAS");
				String kode_ruang = ""+rs.getString("KODE_RUANG");
				String kode_gedung = ""+rs.getString("KODE_GEDUNG");
				String kode_kampus = ""+rs.getString("KODE_KAMPUS");
				String tkn_day_time = ""+rs.getString("TKN_HARI_TIME");
				String nmmdos = ""+rs.getString("NMMDOS");
				String nmmasdos = ""+rs.getString("NMMASDOS");
				String enrolled = ""+rs.getInt("ENROLLED");//jum mhs
				String max_enrolled = ""+rs.getInt("MAX_ENROLLED");//jum mhs
				String min_enrolled = ""+rs.getInt("MIN_ENROLLED");//jum mhs
				String sub_keter_kdkmk = ""+rs.getString("SUB_KETER_KDKMK");
				String init_req_time = ""+rs.getTimestamp("INIT_REQ_TIME");
				String tkn_npm_approval = ""+rs.getString("TKN_NPM_APPROVAL");
				String tkn_approval_time = ""+rs.getString("TKN_APPROVAL_TIME");
				String target_ttmhs = ""+rs.getInt("TARGET_TTMHS");
				String passed = ""+rs.getBoolean("PASSED");
				String rejected = ""+rs.getBoolean("REJECTED");
				String kode_gabung_kls = ""+rs.getString("KODE_PENGGABUNGAN");
				String kode_gabung_kmp = ""+rs.getString("KODE_GABUNGAN_UNIV");
				String unique_id = ""+rs.getLong("UNIQUE_ID");
				String kdkmk = ""+rs.getString("KDKMKMAKUL");
				String nakmk = ""+rs.getString("NAKMKMAKUL");
				String skstm = ""+rs.getInt("SKSTMMAKUL");
				String skspr = ""+rs.getInt("SKSPRMAKUL");
				String skslp = ""+rs.getInt("SKSLPMAKUL");
				li.add(idkur+"`"+idkmk+"`"+thsms_inp_nilai+"`"+kdpst+"`"+shift+"`"+noKlsPll+"`"+npm_pertama_input+"`"+npm_terakhir_updat+"`"+status_akhir+"`"+curr_avail_status+"`"+locked_or_editable+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+canceled+"`"+kode_kelas+"`"+kode_ruang+"`"+kode_gedung+"`"+kode_kampus+"`"+tkn_day_time+"`"+nmmdos+"`"+nmmasdos+"`"+enrolled+"`"+max_enrolled+"`"+min_enrolled+"`"+sub_keter_kdkmk+"`"+init_req_time+"`"+tkn_npm_approval+"`"+tkn_approval_time+"`"+target_ttmhs+"`"+passed+"`"+rejected+"`"+kode_gabung_kls+"`"+kode_gabung_kmp+"`"+unique_id+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp);
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
    	return v;
    }

    
    public Vector getListKelasYgSedangDiajarAtThsmsNow() {
    	String thsmsNow = Checker.getThsmsNow();
    	Vector v = null;
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			
			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS=? and CANCELED=? order by KODE_KAMPUS,IDKMK,SHIFT");
			//stmt = con.prepareStatement("select  * from CLASS_POOL where THSMS=? and NPMDOS=? and CANCELED=? order by KODE_KAMPUS,IDKMK,SHIFT");
			stmt.setString(1, thsmsNow);
			stmt.setString(2, this.operatorNpm);
			stmt.setBoolean(3, false);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				ListIterator li = v.listIterator();
				do {
					String idkur = ""+rs.getLong("IDKUR");
					String idkmk = ""+rs.getLong("IDKMK");
					//thsms ignoeee
					String kdpst = ""+rs.getString("KDPST");
					String shift = ""+rs.getString("SHIFT");
					String noKlsPll = ""+rs.getInt("NORUT_KELAS_PARALEL");
					String npm_pertama_input = ""+rs.getString("INIT_NPM_INPUT");
					String npm_terakhir_updat = ""+rs.getString("LATEST_NPM_UPDATE");
					String status_akhir = ""+rs.getString("LATEST_STATUS_INFO");
					String curr_avail_status = ""+rs.getString("CURR_AVAIL_STATUS");
					String locked_or_editable = ""+rs.getBoolean("LOCKED");
					String npmdos = ""+rs.getString("NPMDOS");
					String nodos = ""+rs.getString("NODOS");
					String npmasdos = ""+rs.getString("NPMASDOS");
					String noasdos = ""+rs.getString("NOASDOS");
					String canceled = ""+rs.getBoolean("CANCELED");
					String kode_kelas = ""+rs.getString("KODE_KELAS");
					String kode_ruang = ""+rs.getString("KODE_RUANG");
					String kode_gedung = ""+rs.getString("KODE_GEDUNG");
					String kode_kampus = ""+rs.getString("KODE_KAMPUS");
					String tkn_day_time = ""+rs.getString("TKN_HARI_TIME");
					String nmmdos = ""+rs.getString("NMMDOS");
					String nmmasdos = ""+rs.getString("NMMASDOS");
					String enrolled = ""+rs.getInt("ENROLLED");//jum mhs
					String max_enrolled = ""+rs.getInt("MAX_ENROLLED");//jum mhs
					String min_enrolled = ""+rs.getInt("MIN_ENROLLED");//jum mhs
					String sub_keter_kdkmk = ""+rs.getString("SUB_KETER_KDKMK");
					String init_req_time = ""+rs.getTimestamp("INIT_REQ_TIME");
					String tkn_npm_approval = ""+rs.getString("TKN_NPM_APPROVAL");
					String tkn_approval_time = ""+rs.getString("TKN_APPROVAL_TIME");
					String target_ttmhs = ""+rs.getInt("TARGET_TTMHS");
					String passed = ""+rs.getBoolean("PASSED");
					String rejected = ""+rs.getBoolean("REJECTED");
					String kode_gabung_kls = ""+rs.getString("KODE_PENGGABUNGAN");
					String kode_gabung_kmp = ""+rs.getString("KODE_GABUNGAN_UNIV");
					String unique_id = ""+rs.getLong("UNIQUE_ID");
					String kdkmk = ""+rs.getString("KDKMKMAKUL");
					String nakmk = ""+rs.getString("NAKMKMAKUL");
					String skstm = ""+rs.getInt("SKSTMMAKUL");
					String skspr = ""+rs.getInt("SKSPRMAKUL");
					String skslp = ""+rs.getInt("SKSLPMAKUL");
					li.add(idkur+"`"+idkmk+"`"+thsmsNow+"`"+kdpst+"`"+shift+"`"+noKlsPll+"`"+npm_pertama_input+"`"+npm_terakhir_updat+"`"+status_akhir+"`"+curr_avail_status+"`"+locked_or_editable+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+canceled+"`"+kode_kelas+"`"+kode_ruang+"`"+kode_gedung+"`"+kode_kampus+"`"+tkn_day_time+"`"+nmmdos+"`"+nmmasdos+"`"+enrolled+"`"+max_enrolled+"`"+min_enrolled+"`"+sub_keter_kdkmk+"`"+init_req_time+"`"+tkn_npm_approval+"`"+tkn_approval_time+"`"+target_ttmhs+"`"+passed+"`"+rejected+"`"+kode_gabung_kls+"`"+kode_gabung_kmp+"`"+unique_id+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp);
				}
				while(rs.next());
				//////System.out.println("jum tot kelas ajar = "+v.size());
			}
			//add konsentrasi
			if(v!=null && v.size()>0) {
				stmt = con.prepareStatement("select KONSENTRASI from KRKLM where IDKURKRKLM=?");
				ListIterator li = v.listIterator();
				while(li.hasNext()) {
					String brs = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					//
					String idkur = st.nextToken();
					stmt.setInt(1, Integer.parseInt(idkur));
					rs = stmt.executeQuery();
					rs.next();
					String konsen = ""+rs.getString("KONSENTRASI");
					li.set(brs+"`"+konsen);
					//String idkmk+"`"+thsmsNow+"`"+kdpst+"`"+shift+"`"+noKlsPll+"`"+npm_pertama_input+"`"+npm_terakhir_updat+"`"+status_akhir+"`"+curr_avail_status+"`"+locked_or_editable+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+canceled+"`"+kode_kelas+"`"+kode_ruang+"`"+kode_gedung+"`"+kode_kampus+"`"+tkn_day_time+"`"+nmmdos+"`"+nmmasdos+"`"+enrolled+"`"+max_enrolled+"`"+min_enrolled+"`"+sub_keter_kdkmk+"`"+init_req_time+"`"+tkn_npm_approval+"`"+tkn_approval_time+"`"+target_ttmhs+"`"+passed+"`"+rejected+"`"+kode_gabung_kls+"`"+kode_gabung_kmp+"`"+unique_id+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp);
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
    	return v;
    }
    /*
    public Vector getListKelasYgBisaDiUpdateStatusKehadiranAtauNilainya(Vector vScopeKdpst) {
    	String thsms_now = Checker.getThsmsNow();
    	Vector vListKls = new Vector();
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//1. cek kelas yg diajar sendiri
			//stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS=? and CANCELED=? order by KODE_KAMPUS,KDPST,IDKMK,SHIFT");
			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS=? and CANCELED=?");
			stmt.setString(1, thsms_now);
			stmt.setString(2, this.operatorNpm);
			stmt.setBoolean(3, false);
			rs = stmt.executeQuery();
			ListIterator lis = vListKls.listIterator();
			
			while(rs.next()) {
				String nakmk = ""+rs.getString("NAKMKMAKUL");
				String idkur  = ""+rs.getLong("IDKUR");
				String idkmk = ""+rs.getLong("IDKMK");				
				String thsms = ""+rs.getString("THSMS");
				String kdpst = ""+rs.getString("KDPST");
				String shift = ""+rs.getString("SHIFT");
				String nopll = ""+rs.getInt("NORUT_KELAS_PARALEL");
				String initNpmInp = ""+rs.getString("INIT_NPM_INPUT");
				String lasNpmUpd = ""+rs.getString("LATEST_NPM_UPDATE");
				String lasStatInf = ""+rs.getString("LATEST_STATUS_INFO");
				String curAvailStat = ""+rs.getString("CURR_AVAIL_STATUS");
				String locked = ""+rs.getBoolean("LOCKED");
				String npmdos = ""+rs.getString("NPMDOS");
				String nodos = ""+rs.getString("NODOS");
				String npmasdos = ""+rs.getString("NPMASDOS");
				String noasdos = ""+rs.getString("NOASDOS");
				String batal = ""+rs.getBoolean("CANCELED");
				String kodeKls = ""+rs.getString("KODE_KELAS");
				String kodeRuang = ""+rs.getString("KODE_RUANG");
				String kodeGdg = ""+rs.getString("KODE_GEDUNG");
				String kodeKmp = ""+rs.getString("KODE_KAMPUS");
				String tknDayTime = ""+rs.getString("TKN_HARI_TIME");
				String nmmDos = ""+rs.getString("NMMDOS");			
				String nmmAsdos = ""+rs.getString("NMMASDOS");
				String totEnrol = ""+rs.getInt("ENROLLED");
				String maxEnrol = ""+rs.getInt("MAX_ENROLLED");
				String minEnrol = ""+rs.getInt("MIN_ENROLLED");
				String subKeterKdkmk = ""+rs.getString("SUB_KETER_KDKMK");
				String initReqTime = ""+rs.getTimestamp("INIT_REQ_TIME");
				String tknNpmApr = ""+rs.getString("TKN_NPM_APPROVAL");
				String tknAprTime = ""+rs.getString("TKN_APPROVAL_TIME");
				String targetTtmhs = ""+rs.getInt("TARGET_TTMHS");
				String passed = ""+rs.getBoolean("PASSED");
				String rejected = ""+rs.getBoolean("REJECTED");
				String kodeGabung = ""+rs.getString("KODE_PENGGABUNGAN");
				String kodeGabungUniv = ""+rs.getString("KODE_GABUNGAN_UNIV");
				String cuid = ""+rs.getLong("UNIQUE_ID");
				String kdkmk = ""+rs.getString("KDKMKMAKUL");
				lis.add(kdpst+"`"+nakmk+"`"+idkur+"`"+idkmk+"`"+shift+"`"+thsms+"`"+nopll+"`"+initNpmInp+"`"+lasNpmUpd+"`"+lasStatInf+"`"+curAvailStat+"`"+locked+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+batal+"`"+kodeKls+"`"+kodeRuang+"`"+kodeGdg+"`"+kodeKmp+"`"+tknDayTime+"`"+nmmDos+"`"+nmmAsdos+"`"+totEnrol+"`"+maxEnrol+"`"+minEnrol+"`"+subKeterKdkmk+"`"+initReqTime+"`"+tknNpmApr+"`"+tknAprTime+"`"+targetTtmhs+"`"+passed+"`"+rejected+"`"+kodeGabung+"`"+kodeGabungUniv+"`"+cuid+"`"+kdkmk);

			}
			//2. get kelas yg tidak diajar tapi boleh ikutan update
			/*
			 * kalo own skip process ini;
			 */
    /*
			if(vScopeKdpst!=null && vScopeKdpst.size()>0) {
				//String sql_cmd = "select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS<>? and CANCELED=? and (";
				String sql_cmd = "select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS<>? and CANCELED=?";
				ListIterator li = vScopeKdpst.listIterator();
				//////System.out.println("sql_cmd = "+sql_cmd);
				boolean own_only = false;
				if(li.hasNext() && !own_only) {
					//64 00007 TU_EKONOMI 64 0
					sql_cmd = sql_cmd+" and (";
					do {
						String brs = (String)li.next();
						//////System.out.println("brs__="+brs);
						if(brs.contains("own") || brs.contains("OWN")) {
							own_only = true;
						}
						else {
							String kdpst = Tool.getTokenKe(brs, 2);
							//////System.out.println("sckpe kdpst = "+kdpst);
							sql_cmd = sql_cmd + "KDPST='"+kdpst+"'";
							if(li.hasNext()) {
								sql_cmd = sql_cmd + " OR ";
							}
						}
					}
					while(li.hasNext() && !own_only);
					sql_cmd = sql_cmd + ")";
				}
				/*
				while(li.hasNext()) {
					String kdpst = (String)li.next();
					//64 00007 TU_EKONOMI 64 0
					
				}
				*/
				
				//////System.out.println("sql_cmd = "+sql_cmd);
				//sql_cmd =sql_cmd.replace("and ()", "");//kalo own
				//////System.out.println("sql_cmd after= "+sql_cmd);
    /*
				if(!own_only) {
					stmt = con.prepareStatement(sql_cmd);
					stmt.setString(1, thsms_now);
					stmt.setString(2, this.operatorNpm);
					stmt.setBoolean(3, false);
					rs = stmt.executeQuery();
					while(rs.next()) {
						//////System.out.println("rs.next()");
						String nakmk = ""+rs.getString("NAKMKMAKUL");
						String idkur  = ""+rs.getLong("IDKUR");
						String idkmk = ""+rs.getLong("IDKMK");				
						String thsms = ""+rs.getString("THSMS");
						String kdpst = ""+rs.getString("KDPST");
						String shift = ""+rs.getString("SHIFT");
						String nopll = ""+rs.getInt("NORUT_KELAS_PARALEL");
						String initNpmInp = ""+rs.getString("INIT_NPM_INPUT");
						String lasNpmUpd = ""+rs.getString("LATEST_NPM_UPDATE");
						String lasStatInf = ""+rs.getString("LATEST_STATUS_INFO");
						String curAvailStat = ""+rs.getString("CURR_AVAIL_STATUS");
						String locked = ""+rs.getBoolean("LOCKED");
						String npmdos = ""+rs.getString("NPMDOS");
						String nodos = ""+rs.getString("NODOS");
						String npmasdos = ""+rs.getString("NPMASDOS");
						String noasdos = ""+rs.getString("NOASDOS");
						String batal = ""+rs.getBoolean("CANCELED");
						String kodeKls = ""+rs.getString("KODE_KELAS");
						String kodeRuang = ""+rs.getString("KODE_RUANG");
						String kodeGdg = ""+rs.getString("KODE_GEDUNG");
						String kodeKmp = ""+rs.getString("KODE_KAMPUS");
						String tknDayTime = ""+rs.getString("TKN_HARI_TIME");
						String nmmDos = ""+rs.getString("NMMDOS");			
						String nmmAsdos = ""+rs.getString("NMMASDOS");
						String totEnrol = ""+rs.getInt("ENROLLED");
						String maxEnrol = ""+rs.getInt("MAX_ENROLLED");
						String minEnrol = ""+rs.getInt("MIN_ENROLLED");
						String subKeterKdkmk = ""+rs.getString("SUB_KETER_KDKMK");
						String initReqTime = ""+rs.getTimestamp("INIT_REQ_TIME");
						String tknNpmApr = ""+rs.getString("TKN_NPM_APPROVAL");
						String tknAprTime = ""+rs.getString("TKN_APPROVAL_TIME");
						String targetTtmhs = ""+rs.getInt("TARGET_TTMHS");
						String passed = ""+rs.getBoolean("PASSED");
						String rejected = ""+rs.getBoolean("REJECTED");
						String kodeGabung = ""+rs.getString("KODE_PENGGABUNGAN");
						String kodeGabungUniv = ""+rs.getString("KODE_GABUNGAN_UNIV");
						String cuid = ""+rs.getLong("UNIQUE_ID");
						String kdkmk = ""+rs.getString("KDKMKMAKUL");	
						lis.add(kdpst+"`"+nakmk+"`"+idkur+"`"+idkmk+"`"+shift+"`"+thsms+"`"+nopll+"`"+initNpmInp+"`"+lasNpmUpd+"`"+lasStatInf+"`"+curAvailStat+"`"+locked+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+batal+"`"+kodeKls+"`"+kodeRuang+"`"+kodeGdg+"`"+kodeKmp+"`"+tknDayTime+"`"+nmmDos+"`"+nmmAsdos+"`"+totEnrol+"`"+maxEnrol+"`"+minEnrol+"`"+subKeterKdkmk+"`"+initReqTime+"`"+tknNpmApr+"`"+tknAprTime+"`"+targetTtmhs+"`"+passed+"`"+rejected+"`"+kodeGabung+"`"+kodeGabungUniv+"`"+cuid+"`"+kdkmk);

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
    	Collections.sort(vListKls);
    	return vListKls;
    }
    */
    
    public Vector getListKelasYgBisaDiUpdateStatusKehadiranAtauNilainya(Vector vScopeKdpst, String target_thsms) {
    	//String thsms_now = Checker.getThsmsNow();
    	Vector vListKls = new Vector();
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			//1. cek kelas yg diajar sendiri
			//stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS=? and CANCELED=? order by KODE_KAMPUS,KDPST,IDKMK,SHIFT");
			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS=? and CANCELED=?");
			stmt.setString(1, target_thsms);
			stmt.setString(2, this.operatorNpm);
			stmt.setBoolean(3, false);
			rs = stmt.executeQuery();
			ListIterator lis = vListKls.listIterator();
			
			while(rs.next()) {
				String nakmk = ""+rs.getString("NAKMKMAKUL");
				String idkur  = ""+rs.getLong("IDKUR");
				String idkmk = ""+rs.getLong("IDKMK");				
				String thsms = ""+rs.getString("THSMS");
				String kdpst = ""+rs.getString("KDPST");
				String shift = ""+rs.getString("SHIFT");
				String nopll = ""+rs.getInt("NORUT_KELAS_PARALEL");
				String initNpmInp = ""+rs.getString("INIT_NPM_INPUT");
				String lasNpmUpd = ""+rs.getString("LATEST_NPM_UPDATE");
				String lasStatInf = ""+rs.getString("LATEST_STATUS_INFO");
				String curAvailStat = ""+rs.getString("CURR_AVAIL_STATUS");
				String locked = ""+rs.getBoolean("LOCKED");
				String npmdos = ""+rs.getString("NPMDOS");
				String nodos = ""+rs.getString("NODOS");
				String npmasdos = ""+rs.getString("NPMASDOS");
				String noasdos = ""+rs.getString("NOASDOS");
				String batal = ""+rs.getBoolean("CANCELED");
				String kodeKls = ""+rs.getString("KODE_KELAS");
				String kodeRuang = ""+rs.getString("KODE_RUANG");
				String kodeGdg = ""+rs.getString("KODE_GEDUNG");
				String kodeKmp = ""+rs.getString("KODE_KAMPUS");
				String tknDayTime = ""+rs.getString("TKN_HARI_TIME");
				String nmmDos = ""+rs.getString("NMMDOS");			
				String nmmAsdos = ""+rs.getString("NMMASDOS");
				String totEnrol = ""+rs.getInt("ENROLLED");
				String maxEnrol = ""+rs.getInt("MAX_ENROLLED");
				String minEnrol = ""+rs.getInt("MIN_ENROLLED");
				String subKeterKdkmk = ""+rs.getString("SUB_KETER_KDKMK");
				String initReqTime = ""+rs.getTimestamp("INIT_REQ_TIME");
				String tknNpmApr = ""+rs.getString("TKN_NPM_APPROVAL");
				String tknAprTime = ""+rs.getString("TKN_APPROVAL_TIME");
				String targetTtmhs = ""+rs.getInt("TARGET_TTMHS");
				String passed = ""+rs.getBoolean("PASSED");
				String rejected = ""+rs.getBoolean("REJECTED");
				String kodeGabung = ""+rs.getString("KODE_PENGGABUNGAN");
				String kodeGabungUniv = ""+rs.getString("KODE_GABUNGAN_UNIV");
				String cuid = ""+rs.getLong("UNIQUE_ID");
				String kdkmk = ""+rs.getString("KDKMKMAKUL");
				lis.add(kdpst+"`"+nakmk+"`"+idkur+"`"+idkmk+"`"+shift+"`"+thsms+"`"+nopll+"`"+initNpmInp+"`"+lasNpmUpd+"`"+lasStatInf+"`"+curAvailStat+"`"+locked+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+batal+"`"+kodeKls+"`"+kodeRuang+"`"+kodeGdg+"`"+kodeKmp+"`"+tknDayTime+"`"+nmmDos+"`"+nmmAsdos+"`"+totEnrol+"`"+maxEnrol+"`"+minEnrol+"`"+subKeterKdkmk+"`"+initReqTime+"`"+tknNpmApr+"`"+tknAprTime+"`"+targetTtmhs+"`"+passed+"`"+rejected+"`"+kodeGabung+"`"+kodeGabungUniv+"`"+cuid+"`"+kdkmk);

			}
			//2. get kelas yg tidak diajar tapi boleh ikutan update
			/*
			 * kalo own skip process ini;
			 */
			if(vScopeKdpst!=null && vScopeKdpst.size()>0) {
				//String sql_cmd = "select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS<>? and CANCELED=? and (";
				String sql_cmd = "select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and NPMDOS<>? and CANCELED=?";
				ListIterator li = vScopeKdpst.listIterator();
				//////System.out.println("sql_cmd = "+sql_cmd);
				boolean own_only = false;
				if(li.hasNext() && !own_only) {
					//64 00007 TU_EKONOMI 64 0
					sql_cmd = sql_cmd+" and (";
					do {
						String brs = (String)li.next();
						//////System.out.println("brs__="+brs);
						if(brs.contains("own") || brs.contains("OWN")) {
							own_only = true;
						}
						else {
							String kdpst = Tool.getTokenKe(brs, 2);
							//////System.out.println("sckpe kdpst = "+kdpst);
							sql_cmd = sql_cmd + "KDPST='"+kdpst+"'";
							if(li.hasNext()) {
								sql_cmd = sql_cmd + " OR ";
							}
						}
					}
					while(li.hasNext() && !own_only);
					sql_cmd = sql_cmd + ")";
				}
				/*
				while(li.hasNext()) {
					String kdpst = (String)li.next();
					//64 00007 TU_EKONOMI 64 0
					
				}
				*/
				
				//////System.out.println("sql_cmd = "+sql_cmd);
				//sql_cmd =sql_cmd.replace("and ()", "");//kalo own
				//////System.out.println("sql_cmd after= "+sql_cmd);
				if(!own_only) {
					stmt = con.prepareStatement(sql_cmd);
					stmt.setString(1, target_thsms);
					stmt.setString(2, this.operatorNpm);
					stmt.setBoolean(3, false);
					rs = stmt.executeQuery();
					while(rs.next()) {
						//////System.out.println("rs.next()");
						String nakmk = ""+rs.getString("NAKMKMAKUL");
						String idkur  = ""+rs.getLong("IDKUR");
						String idkmk = ""+rs.getLong("IDKMK");				
						String thsms = ""+rs.getString("THSMS");
						String kdpst = ""+rs.getString("KDPST");
						String shift = ""+rs.getString("SHIFT");
						String nopll = ""+rs.getInt("NORUT_KELAS_PARALEL");
						String initNpmInp = ""+rs.getString("INIT_NPM_INPUT");
						String lasNpmUpd = ""+rs.getString("LATEST_NPM_UPDATE");
						String lasStatInf = ""+rs.getString("LATEST_STATUS_INFO");
						String curAvailStat = ""+rs.getString("CURR_AVAIL_STATUS");
						String locked = ""+rs.getBoolean("LOCKED");
						String npmdos = ""+rs.getString("NPMDOS");
						String nodos = ""+rs.getString("NODOS");
						String npmasdos = ""+rs.getString("NPMASDOS");
						String noasdos = ""+rs.getString("NOASDOS");
						String batal = ""+rs.getBoolean("CANCELED");
						String kodeKls = ""+rs.getString("KODE_KELAS");
						String kodeRuang = ""+rs.getString("KODE_RUANG");
						String kodeGdg = ""+rs.getString("KODE_GEDUNG");
						String kodeKmp = ""+rs.getString("KODE_KAMPUS");
						String tknDayTime = ""+rs.getString("TKN_HARI_TIME");
						String nmmDos = ""+rs.getString("NMMDOS");			
						String nmmAsdos = ""+rs.getString("NMMASDOS");
						String totEnrol = ""+rs.getInt("ENROLLED");
						String maxEnrol = ""+rs.getInt("MAX_ENROLLED");
						String minEnrol = ""+rs.getInt("MIN_ENROLLED");
						String subKeterKdkmk = ""+rs.getString("SUB_KETER_KDKMK");
						String initReqTime = ""+rs.getTimestamp("INIT_REQ_TIME");
						String tknNpmApr = ""+rs.getString("TKN_NPM_APPROVAL");
						String tknAprTime = ""+rs.getString("TKN_APPROVAL_TIME");
						String targetTtmhs = ""+rs.getInt("TARGET_TTMHS");
						String passed = ""+rs.getBoolean("PASSED");
						String rejected = ""+rs.getBoolean("REJECTED");
						String kodeGabung = ""+rs.getString("KODE_PENGGABUNGAN");
						String kodeGabungUniv = ""+rs.getString("KODE_GABUNGAN_UNIV");
						String cuid = ""+rs.getLong("UNIQUE_ID");
						String kdkmk = ""+rs.getString("KDKMKMAKUL");	
						lis.add(kdpst+"`"+nakmk+"`"+idkur+"`"+idkmk+"`"+shift+"`"+thsms+"`"+nopll+"`"+initNpmInp+"`"+lasNpmUpd+"`"+lasStatInf+"`"+curAvailStat+"`"+locked+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+batal+"`"+kodeKls+"`"+kodeRuang+"`"+kodeGdg+"`"+kodeKmp+"`"+tknDayTime+"`"+nmmDos+"`"+nmmAsdos+"`"+totEnrol+"`"+maxEnrol+"`"+minEnrol+"`"+subKeterKdkmk+"`"+initReqTime+"`"+tknNpmApr+"`"+tknAprTime+"`"+targetTtmhs+"`"+passed+"`"+rejected+"`"+kodeGabung+"`"+kodeGabungUniv+"`"+cuid+"`"+kdkmk);

					}
				}
				
			}
			//filter hanya yg ada mhsnya aja
			if(vListKls!=null && vListKls.size()>0) {
				lis = vListKls.listIterator();
				stmt = con.prepareStatement("select CLASS_POOL_UNIQUE_ID from TRNLM where CLASS_POOL_UNIQUE_ID=?");
				while(lis.hasNext()) {
					String brs = (String)lis.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					//lis.add(kdpst+"`"+nakmk+"`"+idkur+"`"+idkmk+"`"+shift+"`"+thsms+"`"+nopll+"`"+initNpmInp+"`"+lasNpmUpd+"`"+lasStatInf+"`"+curAvailStat+"`"+locked+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+batal+"`"+kodeKls+"`"+kodeRuang+"`"+kodeGdg+"`"+kodeKmp+"`"+tknDayTime+"`"+nmmDos+"`"+nmmAsdos+"`"+totEnrol+"`"+maxEnrol+"`"+minEnrol+"`"+subKeterKdkmk+"`"+initReqTime+"`"+tknNpmApr+"`"+tknAprTime+"`"+targetTtmhs+"`"+passed+"`"+rejected+"`"+kodeGabung+"`"+kodeGabungUniv+"`"+cuid+"`"+kdkmk);
					String kdpst = st.nextToken();
					String nakmk = st.nextToken();
					String idkur = st.nextToken();
					String idkmk = st.nextToken();
					String shift = st.nextToken();
					String thsms = st.nextToken();
					String nopll = st.nextToken();
					String initNpmInp = st.nextToken();
					String lasNpmUpd = st.nextToken();
					String lasStatInf = st.nextToken();
					String curAvailStat = st.nextToken();
					String locked = st.nextToken();
					String npmdos = st.nextToken();
					String nodos = st.nextToken();
					String npmasdos = st.nextToken();
					String noasdos = st.nextToken();
					String batal = st.nextToken();
					String kodeKls = st.nextToken();
					String kodeRuang = st.nextToken();
					String kodeGdg = st.nextToken();
					String kodeKmp = st.nextToken();
					String tknDayTime = st.nextToken();
					String nmmDos = st.nextToken();
					String nmmAsdos = st.nextToken();
					String totEnrol = st.nextToken();
					String maxEnrol = st.nextToken();
					String minEnrol = st.nextToken();
					String subKeterKdkmk = st.nextToken();
					String initReqTime = st.nextToken();
					String tknNpmApr = st.nextToken();
					String tknAprTime = st.nextToken();
					String targetTtmhs = st.nextToken();
					String passed = st.nextToken();
					String rejected = st.nextToken();
					String kodeGabung = st.nextToken();
					String kodeGabungUniv = st.nextToken();
					String cuid = st.nextToken();
					String kdkmk = st.nextToken();
					stmt.setLong(1, Long.parseLong(cuid));
					rs = stmt.executeQuery();
					if(!rs.next()) {
						lis.remove();
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
    	Collections.sort(vListKls);
    	return vListKls;
    }
    
    
    /*
     * ada dua versi
     * yg satunya pake scope, kalo ini seluruh universitas
     */
    public Vector getListOpenedClass(String target_thsms) {
    	Vector v = null;
    	//ListIterator li = v.listIterator();
    	
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			
			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and CANCELED=? order by KDPST, IDKMK, SHIFT");
			stmt.setString(1, target_thsms);
			stmt.setBoolean(2, false);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v=new Vector();
				ListIterator li = v.listIterator();
				do {
					String idkur = ""+rs.getLong("IDKUR");
					String idkmk = ""+rs.getLong("IDKMK");
					//thsms ignoeee
					String kdpst = ""+rs.getString("KDPST");
					String shift = ""+rs.getString("SHIFT");
					String noKlsPll = ""+rs.getInt("NORUT_KELAS_PARALEL");
					String npm_pertama_input = ""+rs.getString("INIT_NPM_INPUT");
					String npm_terakhir_updat = ""+rs.getString("LATEST_NPM_UPDATE");
					String status_akhir = ""+rs.getString("LATEST_STATUS_INFO");
					String curr_avail_status = ""+rs.getString("CURR_AVAIL_STATUS");
					String locked_or_editable = ""+rs.getBoolean("LOCKED");
					String npmdos = ""+rs.getString("NPMDOS");
					String nodos = ""+rs.getString("NODOS");
					String npmasdos = ""+rs.getString("NPMASDOS");
					String noasdos = ""+rs.getString("NOASDOS");
					String canceled = ""+rs.getBoolean("CANCELED");
					String kode_kelas = ""+rs.getString("KODE_KELAS");
					String kode_ruang = ""+rs.getString("KODE_RUANG");
					String kode_gedung = ""+rs.getString("KODE_GEDUNG");
					String kode_kampus = ""+rs.getString("KODE_KAMPUS");
					String tkn_day_time = ""+rs.getString("TKN_HARI_TIME");
					String nmmdos = ""+rs.getString("NMMDOS");
					String nmmasdos = ""+rs.getString("NMMASDOS");
					String enrolled = ""+rs.getInt("ENROLLED");//jum mhs
					String max_enrolled = ""+rs.getInt("MAX_ENROLLED");//jum mhs
					String min_enrolled = ""+rs.getInt("MIN_ENROLLED");//jum mhs
					String sub_keter_kdkmk = ""+rs.getString("SUB_KETER_KDKMK");
					String init_req_time = ""+rs.getTimestamp("INIT_REQ_TIME");
					String tkn_npm_approval = ""+rs.getString("TKN_NPM_APPROVAL");
					String tkn_approval_time = ""+rs.getString("TKN_APPROVAL_TIME");
					String target_ttmhs = ""+rs.getInt("TARGET_TTMHS");
					String passed = ""+rs.getBoolean("PASSED");
					String rejected = ""+rs.getBoolean("REJECTED");
					String kode_gabung_kls = ""+rs.getString("KODE_PENGGABUNGAN");
					String kode_gabung_kmp = ""+rs.getString("KODE_GABUNGAN_UNIV");
					String unique_id = ""+rs.getLong("UNIQUE_ID");
					String kdkmk = ""+rs.getString("KDKMKMAKUL");
					String nakmk = ""+rs.getString("NAKMKMAKUL");
					String skstm = ""+rs.getInt("SKSTMMAKUL");
					String skspr = ""+rs.getInt("SKSPRMAKUL");
					String skslp = ""+rs.getInt("SKSLPMAKUL");
					
					li.add(idkur+"`"+idkmk+"`"+kdpst+"`"+shift+"`"+noKlsPll+"`"+npm_pertama_input+"`"+npm_terakhir_updat+"`"+status_akhir+"`"+curr_avail_status+"`"+locked_or_editable+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+canceled+"`"+kode_kelas+"`"+kode_ruang+"`"+kode_gedung+"`"+kode_kampus+"`"+tkn_day_time+"`"+nmmdos+"`"+nmmasdos+"`"+enrolled+"`"+max_enrolled+"`"+min_enrolled+"`"+sub_keter_kdkmk+"`"+init_req_time+"`"+tkn_npm_approval+"`"+tkn_approval_time+"`"+target_ttmhs+"`"+passed+"`"+rejected+"`"+kode_gabung_kls+"`"+kode_gabung_kmp+"`"+unique_id+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp);
			
				}
				while(rs.next());
			}	
			/*
			stmt = con.prepareStatement("select distinct UNIQUE_ID from CLASS_POOL where THSMS=? and CANCELED=? order by KDPST, IDKMK");
			stmt.setString(1, target_thsms);
			stmt.setBoolean(2, false);
			rs = stmt.executeQuery();
			if(rs.next()) {
				v = new Vector();
				ListIterator li = v.listIterator();
				do {
					String cuid = ""+rs.getLong(1);
					//////System.out.println(cuid);
					li.add(cuid);
				}
				while(rs.next());
			}
			*/
			//if(v!=null && v.size()>0) {
				//get info kelas berdasarkan CUID
				
			//}
    			
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
    		
    	
    	return v;
    }
    
    public Vector getListOpenedClass(String target_thsms, Vector vlist_scope_viewAbsen) {
    	//vlist_scope_viewAbsen baris format = 114 65201 MHS_ILMU_PEMERINTAHAN 114 C
    	Vector v = null;
    	//ListIterator li = v.listIterator();
    	if(vlist_scope_viewAbsen!=null && vlist_scope_viewAbsen.size()>0) {
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			ListIterator li = vlist_scope_viewAbsen.listIterator(); 
    			String sql = "";
    			while(li.hasNext()) {
    				
    				String brs = (String)li.next();
    				String kdpst = Tool.getTokenKe(brs, 2);
    				sql = sql + "KDPST='"+kdpst+"'";
    				if(li.hasNext()) {
    					sql = sql +" or ";
    				}
    			}
    			
    			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and ("+sql+") and CANCELED=? order by KDPST, IDKMK, SHIFT");
    			stmt.setString(1, target_thsms);
    			stmt.setBoolean(2, false);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				v=new Vector();
    				li = v.listIterator();
    				do {
    					String idkur = ""+rs.getLong("IDKUR");
    					String idkmk = ""+rs.getLong("IDKMK");
    					//thsms ignoeee
    					String kdpst = ""+rs.getString("KDPST");
    					String shift = ""+rs.getString("SHIFT");
    					String noKlsPll = ""+rs.getInt("NORUT_KELAS_PARALEL");
    					String npm_pertama_input = ""+rs.getString("INIT_NPM_INPUT");
    					String npm_terakhir_updat = ""+rs.getString("LATEST_NPM_UPDATE");
    					String status_akhir = ""+rs.getString("LATEST_STATUS_INFO");
    					String curr_avail_status = ""+rs.getString("CURR_AVAIL_STATUS");
    					String locked_or_editable = ""+rs.getBoolean("LOCKED");
    					String npmdos = ""+rs.getString("NPMDOS");
    					String nodos = ""+rs.getString("NODOS");
    					String npmasdos = ""+rs.getString("NPMASDOS");
    					String noasdos = ""+rs.getString("NOASDOS");
    					String canceled = ""+rs.getBoolean("CANCELED");
    					String kode_kelas = ""+rs.getString("KODE_KELAS");
    					String kode_ruang = ""+rs.getString("KODE_RUANG");
    					String kode_gedung = ""+rs.getString("KODE_GEDUNG");
    					String kode_kampus = ""+rs.getString("KODE_KAMPUS");
    					String tkn_day_time = ""+rs.getString("TKN_HARI_TIME");
    					String nmmdos = ""+rs.getString("NMMDOS");
    					String nmmasdos = ""+rs.getString("NMMASDOS");
    					String enrolled = ""+rs.getInt("ENROLLED");//jum mhs
    					String max_enrolled = ""+rs.getInt("MAX_ENROLLED");//jum mhs
    					String min_enrolled = ""+rs.getInt("MIN_ENROLLED");//jum mhs
    					String sub_keter_kdkmk = ""+rs.getString("SUB_KETER_KDKMK");
    					String init_req_time = ""+rs.getTimestamp("INIT_REQ_TIME");
    					String tkn_npm_approval = ""+rs.getString("TKN_NPM_APPROVAL");
    					String tkn_approval_time = ""+rs.getString("TKN_APPROVAL_TIME");
    					String target_ttmhs = ""+rs.getInt("TARGET_TTMHS");
    					String passed = ""+rs.getBoolean("PASSED");
    					String rejected = ""+rs.getBoolean("REJECTED");
    					String kode_gabung_kls = ""+rs.getString("KODE_PENGGABUNGAN");
    					String kode_gabung_kmp = ""+rs.getString("KODE_GABUNGAN_UNIV");
    					String unique_id = ""+rs.getLong("UNIQUE_ID");
    					String kdkmk = ""+rs.getString("KDKMKMAKUL");
    					String nakmk = ""+rs.getString("NAKMKMAKUL");
    					String skstm = ""+rs.getInt("SKSTMMAKUL");
    					String skspr = ""+rs.getInt("SKSPRMAKUL");
    					String skslp = ""+rs.getInt("SKSLPMAKUL");
    					
    					li.add(idkur+"`"+idkmk+"`"+kdpst+"`"+shift+"`"+noKlsPll+"`"+npm_pertama_input+"`"+npm_terakhir_updat+"`"+status_akhir+"`"+curr_avail_status+"`"+locked_or_editable+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+canceled+"`"+kode_kelas+"`"+kode_ruang+"`"+kode_gedung+"`"+kode_kampus+"`"+tkn_day_time+"`"+nmmdos+"`"+nmmasdos+"`"+enrolled+"`"+max_enrolled+"`"+min_enrolled+"`"+sub_keter_kdkmk+"`"+init_req_time+"`"+tkn_npm_approval+"`"+tkn_approval_time+"`"+target_ttmhs+"`"+passed+"`"+rejected+"`"+kode_gabung_kls+"`"+kode_gabung_kmp+"`"+unique_id+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp);
    			
    				}
    				while(rs.next());
    			}	
    			/*
    			stmt = con.prepareStatement("select distinct UNIQUE_ID from CLASS_POOL where THSMS=? and CANCELED=? order by KDPST, IDKMK");
    			stmt.setString(1, target_thsms);
    			stmt.setBoolean(2, false);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				v = new Vector();
    				ListIterator li = v.listIterator();
    				do {
    					String cuid = ""+rs.getLong(1);
    					//////System.out.println(cuid);
    					li.add(cuid);
    				}
    				while(rs.next());
    			}
    			*/
    			//if(v!=null && v.size()>0) {
    				//get info kelas berdasarkan CUID
    				
    			//}
        			
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
    
    
    public Vector getListOpenedClassYgAdaMhsnyaOnly(String target_thsms, Vector vlist_scope_viewAbsen) {
    	//vlist_scope_viewAbsen baris format = 114 65201 MHS_ILMU_PEMERINTAHAN 114 C
    	Vector v = null;
    	//ListIterator li = v.listIterator();
    	if(vlist_scope_viewAbsen!=null && vlist_scope_viewAbsen.size()>0) {
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			ListIterator li = vlist_scope_viewAbsen.listIterator(); 
    			String sql = "";
    			while(li.hasNext()) {
    				
    				String brs = (String)li.next();
    				String kdpst = Tool.getTokenKe(brs, 2);
    				sql = sql + "KDPST='"+kdpst+"'";
    				if(li.hasNext()) {
    					sql = sql +" or ";
    				}
    			}
    			
    			stmt = con.prepareStatement("select * from CLASS_POOL inner join MAKUL on IDKMK=IDKMKMAKUL where THSMS=? and ("+sql+") and CANCELED=? order by KDPST, IDKMK, SHIFT");
    			stmt.setString(1, target_thsms);
    			stmt.setBoolean(2, false);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				v=new Vector();
    				li = v.listIterator();
    				do {
    					String idkur = ""+rs.getLong("IDKUR");
    					String idkmk = ""+rs.getLong("IDKMK");
    					//thsms ignoeee
    					String kdpst = ""+rs.getString("KDPST");
    					String shift = ""+rs.getString("SHIFT");
    					String noKlsPll = ""+rs.getInt("NORUT_KELAS_PARALEL");
    					String npm_pertama_input = ""+rs.getString("INIT_NPM_INPUT");
    					String npm_terakhir_updat = ""+rs.getString("LATEST_NPM_UPDATE");
    					String status_akhir = ""+rs.getString("LATEST_STATUS_INFO");
    					String curr_avail_status = ""+rs.getString("CURR_AVAIL_STATUS");
    					String locked_or_editable = ""+rs.getBoolean("LOCKED");
    					String npmdos = ""+rs.getString("NPMDOS");
    					String nodos = ""+rs.getString("NODOS");
    					String npmasdos = ""+rs.getString("NPMASDOS");
    					String noasdos = ""+rs.getString("NOASDOS");
    					String canceled = ""+rs.getBoolean("CANCELED");
    					String kode_kelas = ""+rs.getString("KODE_KELAS");
    					String kode_ruang = ""+rs.getString("KODE_RUANG");
    					String kode_gedung = ""+rs.getString("KODE_GEDUNG");
    					String kode_kampus = ""+rs.getString("KODE_KAMPUS");
    					String tkn_day_time = ""+rs.getString("TKN_HARI_TIME");
    					String nmmdos = ""+rs.getString("NMMDOS");
    					String nmmasdos = ""+rs.getString("NMMASDOS");
    					String enrolled = ""+rs.getInt("ENROLLED");//jum mhs
    					String max_enrolled = ""+rs.getInt("MAX_ENROLLED");//jum mhs
    					String min_enrolled = ""+rs.getInt("MIN_ENROLLED");//jum mhs
    					String sub_keter_kdkmk = ""+rs.getString("SUB_KETER_KDKMK");
    					String init_req_time = ""+rs.getTimestamp("INIT_REQ_TIME");
    					String tkn_npm_approval = ""+rs.getString("TKN_NPM_APPROVAL");
    					String tkn_approval_time = ""+rs.getString("TKN_APPROVAL_TIME");
    					String target_ttmhs = ""+rs.getInt("TARGET_TTMHS");
    					String passed = ""+rs.getBoolean("PASSED");
    					String rejected = ""+rs.getBoolean("REJECTED");
    					String kode_gabung_kls = ""+rs.getString("KODE_PENGGABUNGAN");
    					String kode_gabung_kmp = ""+rs.getString("KODE_GABUNGAN_UNIV");
    					String unique_id = ""+rs.getLong("UNIQUE_ID");
    					String kdkmk = ""+rs.getString("KDKMKMAKUL");
    					String nakmk = ""+rs.getString("NAKMKMAKUL");
    					String skstm = ""+rs.getInt("SKSTMMAKUL");
    					String skspr = ""+rs.getInt("SKSPRMAKUL");
    					String skslp = ""+rs.getInt("SKSLPMAKUL");
    					
    					li.add(idkur+"`"+idkmk+"`"+kdpst+"`"+shift+"`"+noKlsPll+"`"+npm_pertama_input+"`"+npm_terakhir_updat+"`"+status_akhir+"`"+curr_avail_status+"`"+locked_or_editable+"`"+npmdos+"`"+nodos+"`"+npmasdos+"`"+noasdos+"`"+canceled+"`"+kode_kelas+"`"+kode_ruang+"`"+kode_gedung+"`"+kode_kampus+"`"+tkn_day_time+"`"+nmmdos+"`"+nmmasdos+"`"+enrolled+"`"+max_enrolled+"`"+min_enrolled+"`"+sub_keter_kdkmk+"`"+init_req_time+"`"+tkn_npm_approval+"`"+tkn_approval_time+"`"+target_ttmhs+"`"+passed+"`"+rejected+"`"+kode_gabung_kls+"`"+kode_gabung_kmp+"`"+unique_id+"`"+kdkmk+"`"+nakmk+"`"+skstm+"`"+skspr+"`"+skslp);
    			
    				}
    				while(rs.next());
    				if(v!=null && v.size()>0) {
    					li = v.listIterator();
    					stmt = con.prepareStatement("select CLASS_POOL_UNIQUE_ID from TRNLM where THSMSTRNLM=? and CLASS_POOL_UNIQUE_ID=?");
    					while(li.hasNext()) {
    						String brs = (String)li.next();
    						StringTokenizer st = new StringTokenizer(brs,"`");
    						String idkur=st.nextToken();
    						String idkmk=st.nextToken();
    						String kdpst=st.nextToken();
    						String shift=st.nextToken();
    						String noKlsPll=st.nextToken();
    						String npm_pertama_input=st.nextToken();
    						String npm_terakhir_updat=st.nextToken();
    						String status_akhir=st.nextToken();
    						String curr_avail_status=st.nextToken();
    						String locked_or_editable=st.nextToken();
    						String npmdos=st.nextToken();
    						String nodos=st.nextToken();
    						String npmasdos=st.nextToken();
    						String noasdos=st.nextToken();
    						String canceled=st.nextToken();
    						String kode_kelas=st.nextToken();
    						String kode_ruang=st.nextToken();
    						String kode_gedung=st.nextToken();
    						String kode_kampus=st.nextToken();
    						String tkn_day_time=st.nextToken();
    						String nmmdos=st.nextToken();
    						String nmmasdos=st.nextToken();
    						String enrolled=st.nextToken();
    						String max_enrolled=st.nextToken();
    						String min_enrolled=st.nextToken();
    						String sub_keter_kdkmk=st.nextToken();
    						String init_req_time=st.nextToken();
    						String tkn_npm_approval=st.nextToken();
    						String tkn_approval_time=st.nextToken();
    						String target_ttmhs=st.nextToken();
    						String passed=st.nextToken();
    						String rejected=st.nextToken();
    						String kode_gabung_kls=st.nextToken();
    						String kode_gabung_kmp=st.nextToken();
    						String unique_id= st.nextToken();
    						System.out.println("---"+target_thsms+"  "+unique_id);
    						stmt.setString(1, target_thsms);
    						stmt.setLong(2, Long.parseLong(unique_id));
    						rs = stmt.executeQuery();
    						
    						if(!rs.next()) {
    							li.remove();
    						}
    					}
    				}
    			}	
    			/*
    			stmt = con.prepareStatement("select distinct UNIQUE_ID from CLASS_POOL where THSMS=? and CANCELED=? order by KDPST, IDKMK");
    			stmt.setString(1, target_thsms);
    			stmt.setBoolean(2, false);
    			rs = stmt.executeQuery();
    			if(rs.next()) {
    				v = new Vector();
    				ListIterator li = v.listIterator();
    				do {
    					String cuid = ""+rs.getLong(1);
    					//////System.out.println(cuid);
    					li.add(cuid);
    				}
    				while(rs.next());
    			}
    			*/
    			//if(v!=null && v.size()>0) {
    				//get info kelas berdasarkan CUID
    				
    			//}
        			
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
