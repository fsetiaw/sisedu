package beans.dbase.Beasiswa;

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
 * Session Bean implementation class SearchDbBeasiswa
 */
@Stateless
@LocalBean
public class SearchDbBeasiswa extends SearchDb {
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
    public SearchDbBeasiswa() {
        super();
        // TODO Auto-generated constructor stub
    }
       
    /**
     * @see SearchDb#SearchDb(String)
     */
    public SearchDbBeasiswa(String operatorNpm) {
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
    public SearchDbBeasiswa(Connection con) {
        super(con);
        // TODO Auto-generated constructor stub
    }


    public boolean cekIfNamaJenisExist(String namaPaket) {
    	boolean match = false;
    	if(namaPaket!=null) {
    		StringTokenizer st = new StringTokenizer(namaPaket);
    		namaPaket = "";
    		while(st.hasMoreTokens()) {
    			namaPaket = namaPaket+st.nextToken();
    			if(st.hasMoreTokens()) {
    				namaPaket = namaPaket+" ";
    			}
    		}
        	try {
        		
    			Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select NAMAPAKET from BEASISWA");
    			rs = stmt.executeQuery();
    			while(rs.next()&&!match) {
    				String brs = ""+rs.getString("NAMAPAKET");
    				st = new StringTokenizer(brs);
    				brs = "";
    				while(st.hasMoreTokens()) {
    					brs = brs+st.nextToken();
    					if(st.hasMoreTokens()) {
    						brs = brs +" ";
    					}
    				}
    				if(brs.equalsIgnoreCase(namaPaket)) {
    					match = true;
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
    	return match;
    }
    
    public Vector getHistBeasiswa(String kdpst, String npmhs) {
    	Vector v  = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from CIVITAS_BEASISWA_BRIDGE INNER JOIN BEASISWA on JENIS_BEASISWA=IDPAKETBEASISWA where KDPST=? and NPMHS=? order by THSMS");
			stmt.setString(1, kdpst);
			stmt.setString(2, npmhs);
			rs = stmt.executeQuery();
			while(rs.next()) {
				String thsms = rs.getString("THSMS");
				String namaPaket = rs.getString("NAMA_PAKET");
				String namaBank = rs.getString("NAMA_BANK");
				String noRekBank = rs.getString("NO_REKENING");
				String nmPemilik = rs.getString("NAMA_PEMILIK_REKENING");
				String jenisBea = rs.getString("NAMAPAKET");
				li.add(thsms+"`"+kdpst+"`"+npmhs+"`"+namaPaket+"`"+namaBank+"`"+noRekBank+"`"+nmPemilik+"`"+jenisBea);
			}
			stmt = con.prepareStatement("select * from PAKET_BEASISWA where NAMA_PAKET=?");
			
			li = v.listIterator();
			while(li.hasNext()) {
				String brs = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs,"`");
				String thsms = st.nextToken();
				kdpst = st.nextToken();
				npmhs = st.nextToken();
				String namaPaket = st.nextToken();
				String namaBank = st.nextToken();
				String noRekBank = st.nextToken();
				String nmPemilik = st.nextToken();
				String jenisBea = st.nextToken();
				stmt.setString(1, namaPaket);
				System.out.println("namaPaket=="+namaPaket);
				rs = stmt.executeQuery();
				if(rs.next()) {
					String jumDana = ""+rs.getDouble("JUMLAH_DANA_PER_PERIODE");
					String unit = rs.getString("UNIT_PERIODE");
					String nmmSponsor = rs.getString("NAMA_INSTANSI_SUMBER_DANA");
					String jenisSponsor = rs.getString("JENIS_INSTANSI");
					String syarat = rs.getString("KETERANGAN_PERSYARATAN");
					String aktif = ""+rs.getBoolean("AKTIF");
					brs = brs+"`"+jumDana+"`"+unit+"`"+nmmSponsor+"`"+jenisSponsor+"`"+syarat+"`"+aktif;
					li.set(brs);
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
    
    public Vector getLastBeasiswa(Vector vFromSearchCivitas) {
    	Vector v  = new Vector();
    	ListIterator li = v.listIterator();
    	if(vFromSearchCivitas!=null && vFromSearchCivitas.size()>0) {
    		ListIterator li1 = vFromSearchCivitas.listIterator();
    		
    		try {
        		Context initContext  = new InitialContext();
    			Context envContext  = (Context)initContext.lookup("java:/comp/env");
    			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    			con = ds.getConnection();
    			stmt = con.prepareStatement("select * from CIVITAS_BEASISWA_BRIDGE INNER JOIN BEASISWA on JENIS_BEASISWA=IDPAKETBEASISWA where KDPST=? and NPMHS=? order by THSMS desc");
    			while(li1.hasNext()) {
    				String npmhs = (String)li1.next();
    				String nim = (String)li1.next();
    				String kdpst = (String)li1.next();
    				String nmm = (String)li1.next();
    				String tplhr = (String)li1.next();
    				String tglhr = (String)li1.next();
    				String stmhs = (String)li1.next();
    				String id_obj = (String)li1.next();
    				String obj_lvl = (String)li1.next();
    				String obj_desc = (String)li1.next();
    				stmt.setString(1, kdpst);
        			stmt.setString(2, npmhs);
        			rs = stmt.executeQuery();
        			if(rs.next()) {
        				String thsms = rs.getString("THSMS");
        				String namaPaket = rs.getString("NAMA_PAKET");
        				String namaBank = rs.getString("NAMA_BANK");
        				String noRekBank = rs.getString("NO_REKENING");
        				String nmPemilik = rs.getString("NAMA_PEMILIK_REKENING");
        				String jenisBea = rs.getString("NAMAPAKET");
        				li.add(thsms+"`"+kdpst+"`"+npmhs+"`"+namaPaket+"`"+namaBank+"`"+noRekBank+"`"+nmPemilik+"`"+jenisBea);
        			}
        			else {
        				li.add("null");
        			}
        			/*
        			stmt = con.prepareStatement("select * from PAKET_BEASISWA where NAMA_PAKET=?");
        			
        			li = v.listIterator();
        			while(li.hasNext()) {
        				String brs = (String)li.next();
        				StringTokenizer st = new StringTokenizer(brs,"`");
        				String thsms = st.nextToken();
        				kdpst = st.nextToken();
        				npmhs = st.nextToken();
        				String namaPaket = st.nextToken();
        				String namaBank = st.nextToken();
        				String noRekBank = st.nextToken();
        				String nmPemilik = st.nextToken();
        				String jenisBea = st.nextToken();
        				stmt.setString(1, namaPaket);
        				System.out.println("namaPaket=="+namaPaket);
        				rs = stmt.executeQuery();
        				if(rs.next()) {
        					String jumDana = ""+rs.getDouble("JUMLAH_DANA_PER_PERIODE");
        					String unit = rs.getString("UNIT_PERIODE");
        					String nmmSponsor = rs.getString("NAMA_INSTANSI_SUMBER_DANA");
        					String jenisSponsor = rs.getString("JENIS_INSTANSI");
        					String syarat = rs.getString("KETERANGAN_PERSYARATAN");
        					String aktif = ""+rs.getBoolean("AKTIF");
        					brs = brs+"`"+jumDana+"`"+unit+"`"+nmmSponsor+"`"+jenisSponsor+"`"+syarat+"`"+aktif;
        					li.set(brs);
        				}
        			}
        			*/
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
    			
    	
    	return v;
    }
 
    public boolean cekIfNamaPaketExist(String namaPaket) {
       	boolean match = false;
       	if(namaPaket!=null && !Checker.isStringNullOrEmpty(namaPaket)) {
       		StringTokenizer st = new StringTokenizer(namaPaket);
       		namaPaket = "";
       		while(st.hasMoreTokens()) {
       			namaPaket = namaPaket+st.nextToken();
       			if(st.hasMoreTokens()) {
       				namaPaket = namaPaket+" ";
       			}
       		}
           	try {
           		
       			Context initContext  = new InitialContext();
       			Context envContext  = (Context)initContext.lookup("java:/comp/env");
       			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
       			con = ds.getConnection();
       			stmt = con.prepareStatement("select NAMA_PAKET from PAKET_BEASISWA");
       			rs = stmt.executeQuery();
       			while(rs.next()&&!match) {
       				String brs = ""+rs.getString("NAMA_PAKET");
       				st = new StringTokenizer(brs);
       				brs = "";
       				while(st.hasMoreTokens()) {
       					brs = brs+st.nextToken();
       					if(st.hasMoreTokens()) {
       						brs = brs +" ";
       					}
       				}
       				if(brs.equalsIgnoreCase(namaPaket)) {
       					match = true;
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

    	return match;
    }
    
    
    public String getListJenisBeasiswaSesuaiScopeKampus(String tknScopeKampus) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	String namaJenis_kampus = "";
    	//tknScopeKampus tidak mungkin null karena sdah di filter @ servlet PrepFormAddPaketBeasiswa
    	try {
    		
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select NAMAPAKET,AVAIL_AT_KAMPUS from BEASISWA");
			rs = stmt.executeQuery();
			if(rs.next()) {
				
				while(rs.next()) {
					String namaJenis = ""+rs.getString("NAMAPAKET");
					String kampusAvail = ""+rs.getString("AVAIL_AT_KAMPUS");
					StringTokenizer st = new StringTokenizer(tknScopeKampus,",");
					boolean match = false;
					while(st.hasMoreTokens() && !match) {
						String scope = st.nextToken();
						if(kampusAvail.contains(scope)) {
							match = true;
						}
					}
					if(match) {
						li.add(namaJenis+"`"+kampusAvail);
					}
				}
			}
			li = v.listIterator();
			while(li.hasNext()) {
				namaJenis_kampus =namaJenis_kampus +li.next();
				if(li.hasNext()) {
					namaJenis_kampus = namaJenis_kampus+"`";
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
    	return namaJenis_kampus;
    }
    /*
     * belum dipake karena list beasiswa blum perlu pake scope kampus
     */
    public Vector getListPaketBeasiswaSesuaiScopeKampus(String tknScopeKampus) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	String namaJenis_kampus = "";
    	//tknScopeKampus tidak mungkin null karena sdah di filter @ servlet PrepFormAddPaketBeasiswa
    	try {
    		
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from PAKET_BEASISWA inner join BEASISWA on ID_JENIS=IDPAKETBEASISWA ORDER BY NAMA_PAKET");
			rs = stmt.executeQuery();
			while(rs.next()) {
				String nmmPaket = rs.getString("NAMA_PAKET");
				String idJenis = ""+rs.getLong("ID_JENIS");
				String jumDana = ""+rs.getDouble("JUMLAH_DANA_PER_PERIODE");
				String unitPeriode = rs.getString("UNIT_PERIODE");
				String namaInstansi = rs.getString("NAMA_INSTANSI_SUMBER_DANA");
				String jenisInstansi = rs.getString("JENIS_INSTANSI");
				String keter = rs.getString("KETERANGAN_PERSYARATAN");
				String jenisBea = rs.getString("NAMAPAKET ");//jenis
				String scopeKampus = rs.getString("AVAIL_AT_KAMPUS");
				StringTokenizer st = new StringTokenizer(tknScopeKampus,",");
				boolean match = false;
				while(st.hasMoreTokens() && !match) {
					String scp = st.nextToken();
					if(scopeKampus.contains(scp)) {
						li.add(nmmPaket+"`"+idJenis+"`"+jumDana+"`"+unitPeriode+"`"+namaInstansi+"`"+jenisInstansi+"`"+keter+"`"+jenisBea+"`"+scopeKampus);
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
    	return v;
    }
 
 
    public Vector getListPaketBeasiswa() {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	String namaJenis_kampus = "";
    	//tknScopeKampus tidak mungkin null karena sdah di filter @ servlet PrepFormAddPaketBeasiswa
    	try {
    		
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from PAKET_BEASISWA inner join BEASISWA on ID_JENIS=IDPAKETBEASISWA ORDER BY NAMA_PAKET");
			rs = stmt.executeQuery();
			while(rs.next()) {
				String nmmPaket = rs.getString("NAMA_PAKET");
				String idJenis = ""+rs.getLong("ID_JENIS");
				String jumDana = ""+rs.getDouble("JUMLAH_DANA_PER_PERIODE");
				String unitPeriode = rs.getString("UNIT_PERIODE");
				String namaInstansi = rs.getString("NAMA_INSTANSI_SUMBER_DANA");
				String jenisInstansi = rs.getString("JENIS_INSTANSI");
				String keter = rs.getString("KETERANGAN_PERSYARATAN");
				String jenisBea = rs.getString("NAMAPAKET");//jenis
				String scopeKampus = rs.getString("AVAIL_AT_KAMPUS");
				
				li.add(nmmPaket+"`"+idJenis+"`"+jumDana+"`"+unitPeriode+"`"+namaInstansi+"`"+jenisInstansi+"`"+keter+"`"+jenisBea+"`"+scopeKampus);
				
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
    
    public Vector getListPaketBeasiswaAktif(String id_jenis) {
    	Vector v = new Vector();
    	ListIterator li = v.listIterator();
    	String namaJenis_kampus = "";
    	//tknScopeKampus tidak mungkin null karena sdah di filter @ servlet PrepFormAddPaketBeasiswa
    	try {
    		
			Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from PAKET_BEASISWA inner join BEASISWA on ID_JENIS=IDPAKETBEASISWA where ID_JENIS=? and AKTIF=? ORDER BY NAMA_PAKET");
			stmt.setInt(1, Integer.parseInt(id_jenis));
			stmt.setBoolean(2, true);
			rs = stmt.executeQuery();
			while(rs.next()) {
				String nmmPaket = rs.getString("NAMA_PAKET");
				String idJenis = ""+rs.getLong("ID_JENIS");
				String jumDana = ""+rs.getDouble("JUMLAH_DANA_PER_PERIODE");
				String unitPeriode = rs.getString("UNIT_PERIODE");
				String namaInstansi = rs.getString("NAMA_INSTANSI_SUMBER_DANA");
				String jenisInstansi = rs.getString("JENIS_INSTANSI");
				String keter = rs.getString("KETERANGAN_PERSYARATAN");
				String jenisBea = rs.getString("NAMAPAKET");//jenis
				String scopeKampus = rs.getString("AVAIL_AT_KAMPUS");
				
				li.add(nmmPaket+"`"+idJenis+"`"+jumDana+"`"+unitPeriode+"`"+namaInstansi+"`"+jenisInstansi+"`"+keter+"`"+jenisBea+"`"+scopeKampus);
				
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
    public Vector getListPaketBeasiswa(String kdpst, String npmhs) {
    	Vector v  = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from PAKET_BEASISWA INNER JOIN BEASISWA ON  ID_JENIS=IDPAKETBEASISWA ORDER BY NAMA_PAKET ");

			rs = stmt.executeQuery();
			while(rs.next()) {
				String idJenis = rs.getString("ID_JENIS");
				String namaPaket = rs.getString("NAMA_PAKET");
				String namaBank = rs.getString("NAMA_BANK");
				String noRekBank = rs.getString("NO_REKENING");
				String nmPemilik = rs.getString("NAMA_PEMILIK_REKENING");
				li.add(thsms+"`"+kdpst+"`"+npmhs+"`"+namaPaket+"`"+namaBank+"`"+noRekBank+"`"+nmPemilik);
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
    */
    	
    
    public Vector getListJenisBeasiswa() {
    	Vector v  = new Vector();
    	ListIterator li = v.listIterator();
    	try {
    		Context initContext  = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
			con = ds.getConnection();
			stmt = con.prepareStatement("select * from BEASISWA ORDER BY NAMAPAKET ");

			rs = stmt.executeQuery();
			while(rs.next()) {
				String idJenis = ""+rs.getLong("IDPAKETBEASISWA");
				String namaPaket = rs.getString("NAMAPAKET");
				li.add(idJenis+"`"+namaPaket);
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
    
    	
}
