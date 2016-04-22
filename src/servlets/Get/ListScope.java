package servlets.Get;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.ListIterator;
import beans.dbase.SearchDb;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.jdbc.pool.DataSource;

import beans.setting.*;
import beans.tools.*;
import beans.login.InitSessionUsr;

/**
 * Servlet implementation class ListScope
 */
@WebServlet("/ListScope")
public class ListScope extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;   
    DataSource ds;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListScope() {
        super();
        // TODO Auto-generated constructor stub
    }
/*    
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		Context initContext = null;
		Context envContext = null;
	    try {
	      // Look up the JNDI data source only once at init time
    		initContext  = new InitialContext();
    		envContext  = (Context)initContext.lookup("java:/comp/env");
    		ds = (DataSource)envContext.lookup("jdbc"+beans.setting.Constants.getDbschema());
    		
    		envContext.close();
    		initContext.close();
    		if(ds==null) {
    			throw new ServletException("missing data source");
    		}		
	    }
	    catch (NamingException e) {
	      e.printStackTrace();
	    }
	  }
*/
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//////System.out.println("list Scope");
		String cp = request.getParameter("callerPage");
		String cmd = ""+request.getParameter("cmd");
		String atMenu = ""+request.getParameter("atMenu");
		String fwdPage = ""+request.getParameter("fwdPage");
		String nuFwdPage = ""+request.getParameter("nuFwdPage");
		String scope = request.getParameter("scope");
		String scopeType = ""+request.getParameter("scopeType");
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.
		response.setContentType("text/html");
		String url = "";
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		Vector vScope = null;
		vScope = isu.getScopeUpd7des2012("hasEditMkKurikulum");
		
		//khusus urusan request buka kelas jadi kalo sudah disetujui rektor
		//tidak bisa mengajukan yg baru sblm diUnlock oleh rektor
		if(atMenu!=null && atMenu.equalsIgnoreCase("BukaKelas")) {
			String lockedMsg="";
			String tknKdjenKdpst = Constants.getTknKdjenKdpst();
			
			//vScope = isu.getScopeUpd7des2012("reqBukaKelas");
			vScope = isu.getScopeUpd7des2012ProdiOnly("reqBukaKelas");
			//screeneing vscope yg sdh di lock / non kdpst
			ListIterator li = vScope.listIterator();
			/*
			 * update tgl 20sept2014
			 * dalam rangka tambahan pilihan berdasarkan kampus
			 * jadi ditambahakan tokrn kampus domisili
			 */
			vScope = Getter.addKodeKampusToVscope(vScope);
			
			while(li.hasNext()) {
				String brs = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs);
				String idObj = st.nextToken();
				String kdpst = st.nextToken();
				String nmfak = st.nextToken().replace("MHS_", "");
				/*
				 * nama fakultas = diambil dari OBJECT.OBJ_DESC
				 */
				String obLvl = st.nextToken();
				String kdjen = st.nextToken();
				String kodeKampus = st.nextToken();
				//only kdjen kdpst yg diperhitungkan yg lain di ignore
				boolean match = false;
				st = new StringTokenizer(tknKdjenKdpst);
				while(st.hasMoreTokens()&&!match) {
					String kdjen1 = st.nextToken();
					String keterKdjen1 = st.nextToken();
					if(kdjen.equalsIgnoreCase(kdjen1)) {
						match = true;
					}
				}
				if(match) {
					//cek class pool bila sdh ada record untuk thsmsPmb dan locked(sdh di approve) 
					SearchDb sdb = new SearchDb(isu.getNpm());
					boolean locked = sdb.cekApaRequestKelasTelahDiLock(kdpst,kodeKampus);
					if(locked) {
						lockedMsg = lockedMsg+"PRODI "+nmfak+" TELAH DIAJUKAN DAN DISETUJUI<br/>";
						li.remove();
					}
//					////System.out.println(brs);
				}
				else {
					//remove kalo bukan kdjen kdpst krn cuma kdpst yg butuh req kelas
					li.remove();
				}
			}
			//////System.out.println("vScope="+vScope.size());
			session.setAttribute("vScopeBukaKelas", vScope);
			session.setAttribute("lockedMsg", lockedMsg);
			//vscope after screening
			String target = Constants.getRootWeb()+"/InnerFrame/setTargetKdpst.jsp";
			String uri = request.getRequestURI();
			url = PathFinder.getPath(uri, target);
			request.getRequestDispatcher(url+"?scope="+scope+"&atMenu="+atMenu+"&cmd="+cmd).forward(request,response);
		}
		else {
			//Vector vScope = isu.getScopeUpd7des2012("hasEditMkKurikulum");
			////System.out.println("scope1@ListScope="+scope);
			if(scopeType!=null && scopeType.equalsIgnoreCase("prodiOnly")) {
				vScope = isu.getScopeUpd7des2012ProdiOnly(scope);
			}
			else {
				vScope = isu.getScopeUpd7des2012(scope);	
			}
			
				
		//if(vScope)
			////System.out.println("size vis="+vScope.size());
			ListIterator liScope = vScope.listIterator();
			/*
			if(vScope==null) {
				////System.out.println("VscopeSize@ListScope=null");
			}
			else {
				////System.out.println("VscopeSize@ListScope="+vScope.size());
			}
			*/
			
			if(vScope.size()==0) {
			//harusnya ada scope,cek object table = access_conditinal & access
			}
			else {
				if(vScope.size()==1) {
					String baris = (String)liScope.next();
					StringTokenizer st = new StringTokenizer(baris);
					String tknScp = st.nextToken();
					if(tknScp.equalsIgnoreCase("own")) {
					//	unutk object yg ngga bisa edit, seperti mhs
					
						if(scope.equalsIgnoreCase("allowViewKurikulum")) {
							//prosess view ko sendiri
							////System.out.println("own1");
							SearchDb  sdb = new SearchDb(isu.getNpm());
							String kurOprId = isu.getKurikulumOperationalId();
							//////System.out.println("kurOprId="+kurOprId);
							if(kurOprId!=null && !Checker.isStringNullOrEmpty(kurOprId)) {
								Vector vKur = sdb.getListKurikulumInDetail(isu.getKdpst());
								ListIterator liKur = vKur.listIterator();
								boolean match = false;
								String infoKur = null;
								while(liKur.hasNext() && !match) {//harus match kalo ngga error
									String baris1 = (String)liKur.next();
									////System.out.println("="+baris1);
									//if(baris1.startsWith(kurOprId)) {
									//	match = true;
									StringTokenizer stt = new StringTokenizer(baris1,"#&");
										//lif.add(idkur+"#&"+nmkur+"#&"+stkur+"#&"+start+"#&"+ended+"#&"+targt+"#&"+skstt+"#&"+smstt);
									String idkur = stt.nextToken();
									String nmkur = stt.nextToken();
									String stkur = stt.nextToken();
									String start = stt.nextToken();
									if(start.equalsIgnoreCase("null")) {
										start = "N/A";
									}
									String ended = stt.nextToken();
									if(ended.equalsIgnoreCase("null")) {
										ended = "N/A";
									}
									String targt = stt.nextToken();
									String skstt = stt.nextToken();
									String smstt = stt.nextToken();
										
									infoKur = idkur+"##"+nmkur+"##"+skstt+"##"+smstt+"##"+start+"##"+ended+"##"+targt ;
									if(idkur.equalsIgnoreCase(kurOprId)) {
										match = true;
									}
									    //////System.out.println("0.infoKur="+infoKur);
									    //////System.out.println("2.cmd="+cmd+",atMenu="+atMenu+",scope="+scope);
									//    request.getRequestDispatcher("go.prepKurikulumForViewing?infoKur="+infoKur+"&cmd="+cmd+"&atMenu="+atMenu+"&scope="+scope).forward(request,response);
									//}
								}
								if(!match) {
									String msg = "Data Kurikulum belum di-update, Harap hubungi TU melalui layananan menu 'Kirim Pesan'.<br/> Mohon maaf atas ketidak nyamanan ini.<br/> Terima Kasih";
									//String backTo = request.getParameter("backTo");
									atMenu = "ko";
									String target = Constants.getRootWeb()+"/ErrorPage/ErrorPageTamplete.jsp";
									String uri = request.getRequestURI();
								//////System.out.println(target+" / "+uri);
									url = PathFinder.getPath(uri, target);
									nuFwdPage = url+"?errMsg="+msg+"&atMenu="+atMenu;
									//request.getRequestDispatcher(url+"?msg="+msg+"&atMenu="+atMenu).forward(request,response);
								}
								else {
									//match
									////System.out.println("sinikan??");
									nuFwdPage = "go.prepKurikulumForViewing?infoKur="+infoKur+"&cmd="+cmd+"&atMenu="+atMenu+"&scope="+scope;
									//request.getRequestDispatcher("go.prepKurikulumForViewing?infoKur="+infoKur+"&cmd="+cmd+"&atMenu="+atMenu+"&scope="+scope).forward(request,response);
								}	
							}
							else {
								String msg = "Data Kurikulum belum di-update, Harap hubungi TU melalui layananan menu 'Kirim Pesan'.<br/> Mohon maaf atas ketidak nyamanan ini.<br/> Terima Kasih";
								//String backTo = request.getParameter("backTo");
								atMenu = "ko";
								String target = Constants.getRootWeb()+"/ErrorPage/ErrorPageTamplete.jsp";
								String uri = request.getRequestURI();
							//////System.out.println(target+" / "+uri);
								url = PathFinder.getPath(uri, target);
								nuFwdPage = url+"?errMsg="+msg+"&atMenu="+atMenu;
								
							}
							
						}
						
					}
					else {
						//System.out.println("sini-1-");
						String target = Constants.getRootWeb()+"/InnerFrame/setTargetKdpst.jsp";
						String uri = request.getRequestURI();
						//////System.out.println("this is path");
						url = PathFinder.getPath(uri, target);
					}	
				}
				else {
				//many scope
					//System.out.println("sini-2-");
					String target = Constants.getRootWeb()+"/InnerFrame/setTargetKdpst.jsp";
					String uri = request.getRequestURI();
				////System.out.println(target+" / "+uri);
					url = PathFinder.getPath(uri, target);
				}
				
				/*
				 * if nuFwdPage used = maka redirect ke sini
				 */
				if(nuFwdPage!=null && !Checker.isStringNullOrEmpty(nuFwdPage)) {
					url = ""+nuFwdPage;	
				}
				//System.out.println("nuFwdPage="+nuFwdPage);
				if(!url.contains("?")) { 
					request.getRequestDispatcher(url+"?scope="+scope+"&atMenu="+atMenu+"&cmd="+cmd+"&fwdPage="+fwdPage).forward(request,response);
				}
				else {
					request.getRequestDispatcher(url).forward(request,response);
				}
				//System.out.println("url is "+url);
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
