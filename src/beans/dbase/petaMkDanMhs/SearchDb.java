package beans.dbase.petaMkDanMhs;

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

import beans.tools.Checker;

import java.util.Collections;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;
/**
 * Session Bean implementation class SearchDb
 */
@Stateless
@LocalBean
public class SearchDb extends beans.dbase.SearchDb {
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
     * @see beans.dbase.SearchDb#beans.dbase.SearchDb()
     */
    public SearchDb() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see beans.dbase.SearchDb#beans.dbase.SearchDb(String)
     */
    public SearchDb(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see beans.dbase.SearchDb#beans.dbase.SearchDb(Connection)
     */
    public SearchDb(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }
    
    public Vector getInfoMhsYgBelumAmbilMk(Vector vListMakul,Vector vListMhs,String listShiftAvail,String kdpst) {
    	//listShiftAvail =uniqKeter+"#"+shift+"#"+hari+"#"+tkn_kdjen+"#"+keterKonversi+"#";
    	//System.out.println("1");
    	Vector v = new Vector();
    	int j = 0;
    	ListIterator li = v.listIterator();
    	//cek mhs apa sudah ambil mk
    	try {
    		Context initContext  = new InitialContext();
    		Context envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		con = ds.getConnection();
    		stmt = con.prepareStatement("select NLAKHTRNLM from TRNLM where KDPSTTRNLM=? and NPMHSTRNLM=? and KDKMKTRNLM=? order by NLAKHTRNLM");
    		ListIterator liMk = vListMakul.listIterator();
        	while(liMk.hasNext()) {
        		
        		j++;
        		//System.out.println("j="+j);
        		String idkmk=(String)liMk.next();
        		String kdkmk=(String)liMk.next();
        		String nakmk=(String)liMk.next();
        		String skstm=(String)liMk.next();
        		String skspr=(String)liMk.next();
        		String skslp=(String)liMk.next();
        		String kdwpl=(String)liMk.next();
        		String jenis=(String)liMk.next();
        		String stkmk=(String)liMk.next();
        		String nodos=(String)liMk.next();
        		if(Checker.isStringNullOrEmpty(nodos)) {
        			nodos="null";
        		}
        		String semes=(String)liMk.next();
        		li.add(idkmk+"||"+kdkmk+"||"+nakmk+"||"+skstm+"||"+skspr+"||"+skslp+"||"+kdwpl+"||"+jenis+"||"+stkmk+"||"+nodos+"||"+semes);
        		//System.out.println(idkmk+"||"+kdkmk+"||"+nakmk+"||"+skstm+"||"+skspr+"||"+skslp+"||"+kdwpl+"||"+jenis+"||"+stkmk+"||"+nodos+"||"+semes);
        		
        		Vector vNotTaken = new Vector();
            	ListIterator litkn = vNotTaken.listIterator();
            	Vector vNotLulus = new Vector();
        		ListIterator lilus = vNotLulus.listIterator();
        		Vector vLulus = new Vector();
        		ListIterator lilulus = vLulus.listIterator();
        		
        		ListIterator lis = vListMhs.listIterator();
        		boolean match = false;
        		while(lis.hasNext() && !match) {
        			String brs = (String)lis.next();
        			StringTokenizer st = new StringTokenizer(brs,",");
        			String npmhs = st.nextToken();
        			String smawl = st.nextToken();
        			String shift = st.nextToken();
        			String idkur = st.nextToken();
        			stmt.setString(1,kdpst);
        			stmt.setString(2,npmhs);
        			stmt.setString(3,kdkmk);
        			rs = stmt.executeQuery();
        			boolean taken = false;
        			boolean lulus = false;
        			if(rs.next() && !lulus) {
        				//System.out.println(npmhs+" taken "+kdkmk);
        				taken = true;
        				String nlakh = ""+rs.getString("NLAKHTRNLM");
        				if(nlakh.contains("A")||nlakh.contains("B")||nlakh.contains("C")||nlakh.contains("D")) {
        					lulus = true;
        					lilulus.add(shift+"||"+npmhs+"||"+smawl+"||"+idkur);
        				}
        			}
        			if(!taken) {
        				//belum ambil
        				litkn.add(shift+"||"+npmhs+"||"+smawl+"||"+idkur);
        				//System.out.println("not taken ="+shift+"||"+npmhs+"||"+smawl+"||"+idkur);
        			}
        			else {
        				if(!lulus) {
        				//blum lulus
        					lilus.add(shift+"||"+npmhs+"||"+smawl+"||"+idkur);
        					//System.out.println("not lulus ="+shift+"||"+npmhs+"||"+smawl+"||"+idkur);
        				}	
        			}
        			rs.close();
        			
        		}

    
        		//System.out.println("2");
        		//System.out.println("vNotTaken size = "+vNotTaken.size());
        		//System.out.println("vNotLulus size = "+vNotLulus.size());
        		
        		//sorting dan hitung tiap shift ada brp
        		Collections.sort(vNotTaken);
        		Collections.sort(vNotLulus);
        		//=====================================================================
        		litkn = vNotTaken.listIterator();
        		int tot = 0;
        		if(litkn.hasNext()) {
        			tot++;
        			String brs = (String) litkn.next();
        			StringTokenizer st = new StringTokenizer(brs,"||");
        			String prev_shift = st.nextToken();
        			String prev_npmhs = st.nextToken();
        			String prev_smawl = st.nextToken();
        			String prev_idkur = st.nextToken();
        			//add info keterShift
        			st = new StringTokenizer(listShiftAvail,"#");
        			match = false;
        			while(st.hasMoreTokens() && !match) {
        				String uniqKeter=st.nextToken();
						String shift=st.nextToken();
						String hari=st.nextToken();
						String tkn_kdjen=st.nextToken();
						String keterKonversi=st.nextToken();
						if(uniqKeter.equalsIgnoreCase(prev_shift)) {
							match = true;
							//litkn.set(brs+"||"+keterKonversi+"||"+tot);
							litkn.set(prev_smawl+"||"+prev_shift+"||"+prev_npmhs+"||"+prev_idkur+"||"+keterKonversi+"||"+tot);
						}
        			}
        			//System.out.println("3");
        			while(litkn.hasNext()) {
        				brs = (String) litkn.next();
            			st = new StringTokenizer(brs,"||");
            			String curr_shift = st.nextToken();
            			String curr_npmhs = st.nextToken();
            			String curr_smawl = st.nextToken();
            			String curr_idkur = st.nextToken();
            			tot++;
            			if(!curr_shift.equalsIgnoreCase(prev_shift)) {
            				tot = 1;
            				prev_shift = ""+curr_shift;
            			}
            			//add info keterShift
            			st = new StringTokenizer(listShiftAvail,"#");
            			match = false;
            			while(st.hasMoreTokens() && !match) {
            				String uniqKeter=st.nextToken();
    						String shift=st.nextToken();
    						String hari=st.nextToken();
    						String tkn_kdjen=st.nextToken();
    						String keterKonversi=st.nextToken();
    						if(uniqKeter.equalsIgnoreCase(prev_shift)) {
    							match = true;
    							litkn.set(curr_smawl+"||"+curr_shift+"||"+curr_npmhs+"||"+curr_idkur+"||"+keterKonversi+"||"+tot);
    						}
            			}
            			//System.out.println("4");
        			}
        		}
        		
        		//sort berdasar
        		Vector vNotTakenClone = (Vector)vNotTaken.clone();
        		Collections.sort(vNotTakenClone);
        		
        		litkn = vNotTakenClone.listIterator();
        		tot = 0;
        		if(litkn.hasNext()) {
        			tot++;
        			String brs = (String) litkn.next();
        			StringTokenizer st = new StringTokenizer(brs,"||");
        			String prev_smawl = st.nextToken();
        			String prev_shift = st.nextToken();
        			String prev_npmhs = st.nextToken();
        			String prev_idkur = st.nextToken();
        			String prev_keterShift = st.nextToken();
        			litkn.set(prev_smawl+"||"+prev_shift+"||"+prev_npmhs+"||"+prev_idkur+"||"+prev_keterShift+"||"+tot);
        			//add info keterShift
        			/*
        			st = new StringTokenizer(listShiftAvail,"#");
        			match = false;
        			while(st.hasMoreTokens() && !match) {
        				String uniqKeter=st.nextToken();
						String shift=st.nextToken();
						String hari=st.nextToken();
						String tkn_kdjen=st.nextToken();
						String keterKonversi=st.nextToken();
						if(uniqKeter.equalsIgnoreCase(prev_shift)) {
							match = true;
							//litkn.set(brs+"||"+keterKonversi+"||"+tot);
							litkn.set(prev_smawl+"||"+prev_shift+"||"+prev_npmhs+"||"+prev_idkur+"||"+keterKonversi+"||"+tot);
						}
        			}
        			*/
        			//System.out.println("3");
        			while(litkn.hasNext()) {
        				brs = (String) litkn.next();
            			st = new StringTokenizer(brs,"||");
            			String curr_smawl = st.nextToken();
            			String curr_shift = st.nextToken();
            			String curr_npmhs = st.nextToken();
            			String curr_idkur = st.nextToken();
            			String curr_keterShift = st.nextToken();
            			tot++;
            			
            			if(!curr_smawl.equalsIgnoreCase(prev_smawl)) {
            				tot = 1;
            				prev_smawl = ""+curr_smawl;
            			}
            			if(!curr_shift.equalsIgnoreCase(prev_shift)) {
            				tot = 1;
            				prev_shift = ""+curr_shift;
            			}
            			//add info keterShift
            			/*
            			st = new StringTokenizer(listShiftAvail,"#");
            			match = false;
            			while(st.hasMoreTokens() && !match) {
            				String uniqKeter=st.nextToken();
    						String shift=st.nextToken();
    						String hari=st.nextToken();
    						String tkn_kdjen=st.nextToken();
    						String keterKonversi=st.nextToken();
    						if(uniqKeter.equalsIgnoreCase(prev_shift)) {
    							match = true;
    							litkn.set(curr_smawl+"||"+curr_shift+"||"+curr_npmhs+"||"+curr_idkur+"||"+keterKonversi+"||"+tot);
    						}
            			}
            			*/
            			litkn.set(curr_smawl+"||"+curr_shift+"||"+curr_npmhs+"||"+curr_idkur+"||"+curr_keterShift+"||"+tot);
            			//System.out.println("4");
        			}
        		}
        		//=====================================================================
        		
        		//=====================================================================
        		litkn = vNotLulus.listIterator();
        		tot = 0;
        		if(litkn.hasNext()) {
        			tot++;
        			String brs = (String) litkn.next();
        			StringTokenizer st = new StringTokenizer(brs,"||");
        			String prev_shift = st.nextToken();
        			String prev_npmhs = st.nextToken();
        			String prev_smawl = st.nextToken();
        			String prev_idkur = st.nextToken();
        			//add info keterShift
        			st = new StringTokenizer(listShiftAvail,"#");
        			match = false;
        			while(st.hasMoreTokens() && !match) {
        				String uniqKeter=st.nextToken();
						String shift=st.nextToken();
						String hari=st.nextToken();
						String tkn_kdjen=st.nextToken();
						String keterKonversi=st.nextToken();
						if(uniqKeter.equalsIgnoreCase(prev_shift)) {
							match = true;
							//litkn.set(brs+"||"+keterKonversi+"||"+tot);
							litkn.set(prev_smawl+"||"+prev_shift+"||"+prev_npmhs+"||"+prev_idkur+"||"+keterKonversi+"||"+tot);
						}
        			}
        			//System.out.println("3");
        			while(litkn.hasNext()) {
        				brs = (String) litkn.next();
            			st = new StringTokenizer(brs,"||");
            			String curr_shift = st.nextToken();
            			String curr_npmhs = st.nextToken();
            			String curr_smawl = st.nextToken();
            			String curr_idkur = st.nextToken();
            			tot++;
            			if(!curr_shift.equalsIgnoreCase(prev_shift)) {
            				tot = 1;
            				prev_shift = ""+curr_shift;
            			}
            			//add info keterShift
            			st = new StringTokenizer(listShiftAvail,"#");
            			match = false;
            			while(st.hasMoreTokens() && !match) {
            				String uniqKeter=st.nextToken();
    						String shift=st.nextToken();
    						String hari=st.nextToken();
    						String tkn_kdjen=st.nextToken();
    						String keterKonversi=st.nextToken();
    						if(uniqKeter.equalsIgnoreCase(prev_shift)) {
    							match = true;
    							litkn.set(curr_smawl+"||"+curr_shift+"||"+curr_npmhs+"||"+curr_idkur+"||"+keterKonversi+"||"+tot);
    						}
            			}
            			//System.out.println("4");
        			}
        		}
        		
        		//sort berdasar
        		Vector vNotLulusClone = (Vector)vNotLulus.clone();
        		Collections.sort(vNotLulusClone);
        		
        		litkn = vNotLulusClone.listIterator();
        		tot = 0;
        		if(litkn.hasNext()) {
        			tot++;
        			String brs = (String) litkn.next();
        			StringTokenizer st = new StringTokenizer(brs,"||");
        			String prev_smawl = st.nextToken();
        			String prev_shift = st.nextToken();
        			String prev_npmhs = st.nextToken();
        			String prev_idkur = st.nextToken();
        			String prev_keterShift = st.nextToken();
        			litkn.set(prev_smawl+"||"+prev_shift+"||"+prev_npmhs+"||"+prev_idkur+"||"+prev_keterShift+"||"+tot);
        			while(litkn.hasNext()) {
        				brs = (String) litkn.next();
            			st = new StringTokenizer(brs,"||");
            			String curr_smawl = st.nextToken();
            			String curr_shift = st.nextToken();
            			String curr_npmhs = st.nextToken();
            			String curr_idkur = st.nextToken();
            			String curr_keterShift = st.nextToken();
            			tot++;
            			
            			if(!curr_smawl.equalsIgnoreCase(prev_smawl)) {
            				tot = 1;
            				prev_smawl = ""+curr_smawl;
            			}
            			if(!curr_shift.equalsIgnoreCase(prev_shift)) {
            				tot = 1;
            				prev_shift = ""+curr_shift;
            			}
            			litkn.set(curr_smawl+"||"+curr_shift+"||"+curr_npmhs+"||"+curr_idkur+"||"+curr_keterShift+"||"+tot);
            			//System.out.println("4");
        			}
        		}
        		
        		//=====================================================================
        
        		//System.out.println("5");
        		//li.add(vLulus);
        		li.add(vNotTaken);//sorted shift
        		li.add(vNotTakenClone);//sorted smawl
        		li.add(vNotLulus);//sorted shift
        		li.add(vNotLulusClone);//sorted smawl
        	}	
        	//System.out.println("6");
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
