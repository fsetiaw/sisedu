package servlets.update;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.Beasiswa.UpdateDbBeasiswa;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class JenisBeasiswa
 */
@WebServlet("/JenisBeasiswa")
public class JenisBeasiswa extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JenisBeasiswa() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("update jenis beasiswa");
		String namaPaket = null;
		String scopeKampus = null;
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			String fieldAndValue = (String)session.getAttribute("fieldAndValue");
			StringTokenizer st = new StringTokenizer(fieldAndValue,"||");
			while(st.hasMoreTokens()) {
				String nama = st.nextToken();
				String val = st.nextToken();
				if(nama.equalsIgnoreCase("Berlaku-pada-Kampus_String_Wajib")) {
					scopeKampus = ""+val;
				}
				if(nama.equalsIgnoreCase("Nama-Jenis_String_Wajib")) {
					namaPaket = ""+val;
				}
			}
			//System.out.println("fieldAndValue="+fieldAndValue);
			//
			UpdateDbBeasiswa udb = new UpdateDbBeasiswa(isu.getNpm());
			udb.addJenisBeasiswa(namaPaket, scopeKampus);
			String target = Constants.getRootWeb()+"/InnerFrame/Keu/Beasiswa/indexBeasiswa.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			
			request.getRequestDispatcher(url).forward(request,response);
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
