package beans.dbase.Beasiswa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import beans.dbase.UpdateDb;
import beans.tools.Checker;
import beans.tools.DateFormater;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.jdbc.pool.DataSource;



/**
 * Session Bean implementation class UpdateDbBeasiswa
 */
@Stateless
@LocalBean
public class UpdateDbBeasiswa extends UpdateDb {
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
    public UpdateDbBeasiswa() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see UpdateDb#UpdateDb(String)
     */
    public UpdateDbBeasiswa(String operatorNpm) {
        super(operatorNpm);
        this.operatorNpm = operatorNpm;
    	this.operatorNmm = getNmmOperator();
    	this.petugas = cekApaUsrPetugas();
    	this.tknOperatorNickname = getTknOprNickname();
        // TODO Auto-generated constructor stub
    }

    
    public int insertRecBeasiswaMhs(String kdpst,String npmhs,String thsms,String idJenisBea,String namaPaket,String nmmBank,String  noRek,String accOwnerName) {
    	int i=0;
    	try {
           	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("INSERT INTO CIVITAS_BEASISWA_BRIDGE(KDPST,NPMHS,THSMS,JENIS_BEASISWA,NAMA_PAKET,NAMA_BANK,NO_REKENING,NAMA_PEMILIK_REKENING)values(?,?,?,?,?,?,?,?)");
        	stmt.setString(1, kdpst);
        	stmt.setString(2, npmhs);
        	stmt.setString(3, thsms);
        	stmt.setInt(4, Integer.parseInt(idJenisBea));
        	stmt.setString(5, namaPaket);
        	stmt.setString(6, nmmBank);
        	stmt.setString(7, noRek);
        	stmt.setString(8, accOwnerName);
        	i = stmt.executeUpdate();
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
    
    
    public int addJenisBeasiswa(String namaPaket, String scopeKampus) {
    	int i=0;
    	try {
        	//String ipAddr =  request.getRemoteAddr();
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("INSERT INTO BEASISWA (NAMAPAKET,AVAIL_AT_KAMPUS)VALUES(?,?)");
        	stmt.setString(1, namaPaket);
        	stmt.setString(2, scopeKampus);
        	i = stmt.executeUpdate();
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
    

    public int addPaketBeasiswa(String namaPaket, String jenisBea, String jumlahDana, String periode,String namaInstansi, String jenisInstansi,String syarat) {
    	int i=0;
    	try {
        	//String ipAddr =  request.getRemoteAddr();
        	Context initContext  = new InitialContext();
        	Context envContext  = (Context)initContext.lookup("java:/comp/env");
        	ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
        	con = ds.getConnection();
        	stmt = con.prepareStatement("select IDPAKETBEASISWA from BEASISWA where NAMAPAKET=?");
        	stmt.setString(1, jenisBea);
        	rs = stmt.executeQuery();
        	if(rs.next()) {
        		long id = rs.getLong("IDPAKETBEASISWA");
        		
        		System.out.println("id = "+id);
        		stmt = con.prepareStatement("INSERT INTO PAKET_BEASISWA (NAMA_PAKET,ID_JENIS,JUMLAH_DANA_PER_PERIODE,UNIT_PERIODE,NAMA_INSTANSI_SUMBER_DANA,JENIS_INSTANSI,KETERANGAN_PERSYARATAN)VALUES(?,?,?,?,?,?,?)");
            	stmt.setString(1, namaPaket);
            	stmt.setLong(2, id);
            	stmt.setDouble(3, Double.parseDouble(jumlahDana));
            	stmt.setString(4, periode);
            	stmt.setString(5, namaInstansi);
            	stmt.setString(6, jenisInstansi);
            	stmt.setString(7, syarat);
            	i = stmt.executeUpdate();
            	System.out.println("i = "+i);
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
    
 }
