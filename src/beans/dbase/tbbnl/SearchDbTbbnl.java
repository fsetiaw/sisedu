package beans.dbase.tbbnl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.tomcat.jdbc.pool.DataSource;

import java.util.Comparator;
import java.sql.SQLException;
import java.util.Vector;
import java.util.ListIterator;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.util.Collections;

import org.apache.tomcat.jdbc.pool.*;

import beans.dbase.SearchDb;
import beans.login.InitSessionUsr;
import beans.sistem.AskSystem;
import beans.setting.*;
import beans.tools.*;

import java.util.LinkedHashSet;


/**
 * Session Bean implementation class SearchDbTbbnl
 */
@Stateless
@LocalBean
public class SearchDbTbbnl extends SearchDb {
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
     * Default constructor. 
     */
    public SearchDbTbbnl() {
        // TODO Auto-generated constructor stub
    	
    }
    
    public SearchDbTbbnl(String operatorNpm) {
        // TODO Auto-generated constructor stub
    	super(operatorNpm);
    	this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
    }

    
    public Vector getInfoTabelNilaiYgBerlakuPerKdpst(Vector vObjStringDgnTknPertamaKdpstValue) {
    	/*
    	 * if usrId < 0; berarti unverified usr jharusnya cuma pada saat login
    	 */
    	//String thsms_inp_nilai = Checker.getThsmsInputNilai();
    	//Vector v = new Vector();
    	//ListIterator li = v.listIterator();
    	if(vObjStringDgnTknPertamaKdpstValue!=null && vObjStringDgnTknPertamaKdpstValue.size()>0) {
    		ListIterator li1 = vObjStringDgnTknPertamaKdpstValue.listIterator();
    		
    		try {
        		//String ipAddr =  request.getRemoteAddr();
        		Context initContext  = new InitialContext();
        		Context envContext  = (Context)initContext.lookup("java:/comp/env");
        		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        		con = ds.getConnection();
        		//stmt = con.prepareStatement("SELECT NLAKHTBBNL,BOBOTTBBNL from TBBNL where THSMSTBBNL=? and KDPSTTBBNL=? order by NLAKHTBBNL");
        		stmt = con.prepareStatement("SELECT NLAKHTBBNL,BOBOTTBBNL from TBBNL where KDPSTTBBNL=? and ACTIVE=? order by NLAKHTBBNL");
        		//
        		while(li1.hasNext()) {
        			String brs = (String)li1.next();
        			StringTokenizer st = new StringTokenizer(brs,"`");
        			String kdpst = st.nextToken();
        			stmt.setString(1, kdpst);
            		stmt.setBoolean(2, true);
            		rs = stmt.executeQuery();
            		String tkn_nilai_bobot ="null-null";
            		//boolean first = true;
            		if(rs.next()) {
            			tkn_nilai_bobot = "";
            			do {
            				String nlakh =""+rs.getString(1);
            				nlakh = nlakh.replace("-", "tandaMin");
                			String bobot = ""+rs.getDouble(2);
                			tkn_nilai_bobot =tkn_nilai_bobot + nlakh+"-"+bobot+"-";
            			} while(rs.next());
            		}
            		li1.set(brs+"`"+tkn_nilai_bobot);
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
    	    	return vObjStringDgnTknPertamaKdpstValue;
    }	
}
