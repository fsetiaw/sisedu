package servlets.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.io.PrintWriter;
import java.util.Vector;

import beans.sistem.*;

import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.tomcat.jdbc.pool.DataSource;

import beans.dbase.*;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;
import beans.tools.Tool;

import java.util.ListIterator;
/**
 * Servlet implementation class Summary
 */
@WebServlet("/Summary")
public class Summary extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;   
    DataSource ds;
    /**
     * @see HttpServlet#HttpServlet()
     */
//    public Summary() {
//        super();
        // TODO Auto-generated constructor stub
//    }
	public void init(ServletConfig config) throws ServletException {
	
		super.init(config);
		/*	
		Context initContext = null;
		Context envContext = null;
	    try {
	      // Look up the JNDI data source only once at init time
    		initContext  = new InitialContext();
    		envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		
    		//envContext.close();
    		initContext.close();
    		if(ds==null) {
    			throw new ServletException("missing data source");
    		}		
	    }
	    catch (NamingException e) {
	      e.printStackTrace();
	    }
*/	    
	  }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		SearchDb sdb = new SearchDb();
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		Vector v_scope = isu.getScope("hasSummaryMenu");
		String targetPage = request.getParameter("targetPage");
		//System.out.println("v_scope size = "+v_scope.size());
	
	try {
		//con = ds.getConnection();
	
		if(v_scope!=null && v_scope.size()>0) {
			ListIterator li_sc = v_scope.listIterator();	
			if(targetPage.equalsIgnoreCase("cashFlow")) {
				//Vector v_cf = isu.getScope("showCFmhs");
				Vector v_cf = isu.getScopeUpd7des2012ProdiOnly("showCFmhs");
				ListIterator lit = v_cf.listIterator();
				while(lit.hasNext()) {
					System.out.println((String)lit.next());
				}
				if(v_cf!=null && v_cf.size()>0) {
				/*
				 * cfMhs.jsp - menampilkan summary pembayaran mahasiswa per prodi per bulan
				 * sekaligus menjdaikan default view summary
				 */
					String today = ""+AskSystem.getCurrentTime();
				//System.out.println("tidao = "+today);
					StringTokenizer st = new StringTokenizer(today,"-");
					int tahun = Integer.valueOf(st.nextToken()).intValue();
					int bulan = Integer.valueOf(st.nextToken()).intValue();
					String tknTahun = ""+(tahun-1)+" "+tahun;
					//System.out.println(tknTahun);
				
					String tknYyMm = "";
					tahun = tahun -1;
					boolean sudahGantiTahun=false;
					for(int i=0;i<13;i++) {
						String tmp = "";
						if(bulan<=12) {
							tmp = ""+tahun+bulan;
						}
						else {
							if(!sudahGantiTahun) {
								sudahGantiTahun = true;
								tahun = tahun+1;
								bulan = 1;
							}	
							tmp = ""+tahun+bulan;
						}
						bulan++;
						tknYyMm = tknYyMm+" "+tmp;
					}
				//	System.out.println(tknYyMm);
				
					Vector v1 = new Vector();
					ListIterator li1 = v1.listIterator();
					ListIterator liTmp = v_cf.listIterator();
					/*
					 * patch : perbaikan penampakan dobel untuk 1 prodi tapi ada di beberapa kampus
					 * note: total pembayaran belum dipisahkan antar kampus
					 */
					con = sdb.getCon();
					boolean belum = true;
					String prev_kdpst = "";
					while(liTmp.hasNext()) {
						String lvl = (String)liTmp.next();
						st = new StringTokenizer(lvl);
						String id_obj = st.nextToken();
						String kdpst = st.nextToken();
						String keter = st.nextToken();
						keter = keter.replaceAll("_", " ");
						String obj_lvl = st.nextToken();
						//sdb = new SearchDb();
						//con = sdb.getCon(); pindah ke atas
						if(!kdpst.equalsIgnoreCase(prev_kdpst)) {
							prev_kdpst = new String(kdpst);
							if(con==null || con.isClosed()) {
								sdb = new SearchDb();
								con = sdb.getCon();
							}
						    //System.out.println("siniiii");	
							//sdb = new SearchDb(con);
							//System.out.println("sinaaaaa");
							Vector vKdpstDtAmnt = sdb.getVectorPaymentMhs(kdpst, tknTahun);
							li1.add(kdpst);
							li1.add(vKdpstDtAmnt);
						}
					}
				
					Vector v10 = new Vector();
					ListIterator li10 = v10.listIterator();
				
				
					double yAmnt=0,yAmntPsc=0;
					Vector vPsc = new Vector();
					ListIterator liPsc = vPsc.listIterator();
					Vector vff = new Vector();
					ListIterator liff = vff.listIterator();
					li1 = v1.listIterator();
				
					StringTokenizer stt = new StringTokenizer(tknYyMm);
					Double[]yTot = new Double[stt.countTokens()];
					for(int j=0; j<stt.countTokens();j++) {
						yTot[j]=new Double(0);
					}
					Double[]yTotPsc = new Double[stt.countTokens()];
					for(int j=0; j<stt.countTokens();j++) {
						yTotPsc[j]=new Double(0);
					}
					while(li1.hasNext()) {
						String kdpst = (String)li1.next();
					//System.out.println("kdpst= "+kdpst);
						Vector vKdpstDtAmnt = (Vector)li1.next();
						int i=0;
						if(Constants.isThisKdpstPasca(kdpst)) {
							liPsc.add(kdpst);
						}
						else {
							liff.add(kdpst);
						}	
						//System.out.println(kdpst);
						stt = new StringTokenizer(tknYyMm);
						double xAmnt = 0,xAmntPsc=0;
						int urut = 0;
					
					
						while(stt.hasMoreTokens()) {
							urut++;
							String tkn = stt.nextToken();
							String thn = tkn.substring(0,4);
							String bln = tkn.substring(4,tkn.length());
							if(bln.length()==1) {
								bln = "0"+bln;
							}
							//System.out.println("tahun/bln ="+thn+"/"+bln);
							double ttAmnt = 0,ttAmntPsc=0;
							
							ListIterator liKda = vKdpstDtAmnt.listIterator();
							while(liKda.hasNext()) {
								i++;
								String baris = (String)liKda.next();
							//	System.out.println("likda = "+baris);
								StringTokenizer stb = new StringTokenizer(baris);
								String tgl = stb.nextToken();
								String jum = stb.nextToken();
							
								StringTokenizer tkn_tgl = new StringTokenizer(tgl,"-");
								String yy = tkn_tgl.nextToken();
								String mm = tkn_tgl.nextToken();
								if(mm.length()==1) {
									mm = "0"+mm;
								}
							//	System.out.println(tkn+" vs "+tgl);
							//System.out.println(thn+"/"+bln+" vs. "+yy+"/"+mm);
								if(thn.equalsIgnoreCase(yy) && bln.equalsIgnoreCase(mm)) {
									if(Constants.isThisKdpstPasca(kdpst)) {
										ttAmntPsc = ttAmntPsc+Double.valueOf(jum).doubleValue();
										xAmntPsc = xAmntPsc+Double.valueOf(jum).doubleValue();
										double tmp = yTotPsc[urut-1].doubleValue()+Double.valueOf(jum).doubleValue();
										yTotPsc[urut-1] = Double.valueOf(tmp);
									}
									else {
										ttAmnt = ttAmnt+Double.valueOf(jum).doubleValue();
										xAmnt = xAmnt+Double.valueOf(jum).doubleValue();
										double tmp = yTot[urut-1].doubleValue()+Double.valueOf(jum).doubleValue();
										yTot[urut-1] = Double.valueOf(tmp);
									}
								}
							}//end while
							if(Constants.isThisKdpstPasca(kdpst)) {
								liPsc.add(thn+" "+bln+" "+ttAmntPsc);
							}
							else {
								liff.add(thn+" "+bln+" "+ttAmnt);
							}
						}
						if(Constants.isThisKdpstPasca(kdpst)) {
							liPsc.add("x= "+xAmntPsc);
						}
						else {
							liff.add("x= "+xAmnt);
						}
					}
					
	            /*
	             * proses utk summary jumlah mhs
	             */
					Vector vf = new Vector();
					ListIterator lif = vf.listIterator();
					while(li_sc.hasNext()) {
						//sdb = new SearchDb();
						//sdb = new SearchDb(con);
						con = sdb.getCon();
						if(con==null || con.isClosed()) {
							sdb = new SearchDb();
						}
						String id_lvl = (String)li_sc.next();
						st = new StringTokenizer(id_lvl);
						String id_obj = st.nextToken();
						String kdpst = st.nextToken();
						String obj_dsc = st.nextToken();
						String obj_lvl = st.nextToken();
						int tt_mhs = sdb.getTotMhs(id_obj);
						con = sdb.getCon();
						if(con==null || con.isClosed()) {
							sdb = new SearchDb();
						}
						Vector v_smawl_ttmhs = sdb.getTotMhsPerSmawl(id_obj);
						lif.add(id_obj+" "+kdpst+" "+obj_dsc+" "+obj_lvl+" "+tt_mhs);
						lif.add(v_smawl_ttmhs);					
					}
					session.setAttribute("v_totMhs", vf);
					session.setAttribute("tknYyMm",tknYyMm);
					session.setAttribute("vSum", vff);
					session.setAttribute("yTot", yTot);
					session.setAttribute("vSumPsc", vPsc);
					session.setAttribute("yTotPsc", yTotPsc);
					String target = Constants.getRootWeb()+"/InnerFrame/Summary/Keu/cfMhs.jsp";
					String uri = request.getRequestURI();
					String url_ff = PathFinder.getPath(uri, target);
					request.getRequestDispatcher(url_ff).forward(request,response);
				
				}	
			}
			else {
				if(targetPage.equalsIgnoreCase("totMhs")) {
				/*
				 * cek access show = summary total mhs
				 * 
				 */

					Vector vTtMhs = isu.getScope("showSummaryTTmhs");
					li_sc = vTtMhs.listIterator();
					if(vTtMhs!=null && vTtMhs.size()>0) {
			
						Vector vf = new Vector();
						ListIterator lif = vf.listIterator();
						while(li_sc.hasNext()) {
							//sdb = new SearchDb();
							con = sdb.getCon();
							if(con==null || con.isClosed()) {
								sdb = new SearchDb();
							}
							//sdb = new SearchDb(con);
							String id_lvl = (String)li_sc.next();
							StringTokenizer st = new StringTokenizer(id_lvl);
							String id_obj = st.nextToken();
							String kdpst = st.nextToken();
							String obj_dsc = st.nextToken();
							String obj_lvl = st.nextToken();
							//get totoal mhs
							int tt_mhs = sdb.getTotMhs(id_obj);
							//lif.add(id_obj+" "+kdpst+" "+obj_dsc+" "+obj_lvl+" "+tt_mhs);
							//get total mhs per smawl
							con = sdb.getCon();
							if(con==null || con.isClosed()) {
								sdb = new SearchDb();
							}
							Vector v_smawl_ttmhs = sdb.getTotMhsPerSmawl(id_obj);
							lif.add(id_obj+" "+kdpst+" "+obj_dsc+" "+obj_lvl+" "+tt_mhs);
							lif.add(v_smawl_ttmhs);
						}
						session.setAttribute("v_totMhs", vf);
						String target = Constants.getRootWeb()+"/InnerFrame/viewSummaryTTmhs.jsp";
						String uri = request.getRequestURI();
						String url_ff = PathFinder.getPath(uri, target);
						request.getRequestDispatcher(url_ff).forward(request,response);
					}
				}	
				else {
					/*
					 * reserved for 3rd default
					 */
					out.print("<html>");
					out.print("<head>");
					out.print("</head>");
					out.print("<body>");
					out.println("3rd opt, tp bisa sampe sini?");
					out.print("</body>");
					out.print("</html>");
				}
			}
		}// end if (hasSummaryMenu)	
		else {
			out.print("<html>");
			out.print("<head>");
			out.print("</head>");
			out.print("<body>");
			out.println("tidak punya summary menu, tp bisa sampe sini?");
			out.print("</body>");
			out.print("</html>");
		}
	
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
        		System.out.println(ignore);
    		}
    	}
    }
   
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
