package servlets.Files;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class CreateNewFolder_vPojo
 */
@WebServlet("/CreateNewFolder_vPojo")
public class CreateNewFolder_vPojo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String foldername;
	String alm;
	String keter;
	String hak; 
	String root_dir;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateNewFolder_vPojo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	//String msg = "<meta http-equiv=\"refresh\" content=\"0; url=get.folderContent?keter="+keter+"&alm="+alm+"&hak="+hak+"&root_dir="+root_dir+"\" >";
	//request.getRequestDispatcher("get.folderContent?keter="+keter+"&alm="+alm+"&hak="+hak).forward(request,response);
		//String target = Constants.getRootWeb()+"/InnerFrame/searchResult.jsp";
		//String uri = request.getRequestURI();
		//String url_ff = PathFinder.getPath(uri, target);
		request.getRequestDispatcher("get.folderContent?keter="+keter+"&alm="+alm+"&hak="+hak+"&root_dir="+root_dir).forward(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			System.out.println("create folder");
			foldername = request.getParameter("foldername");
			foldername = foldername.replace(" ", "_");
			System.out.println("create foldername="+alm+"/"+foldername);
			alm = request.getParameter("alm");
			keter = request.getParameter("keter");
			hak = request.getParameter("hak");
			root_dir = request.getParameter("root_dir");//masalah kalo dipajke
			//System.out.println(foldername);
			//System.out.println(alm);
			File dir = new File(alm+"/"+foldername);
			//try {
			dir.mkdir();
				//System.out.println("created");
			//}
			//catch (Exception e) {
			//	System.out.println("error");
			//}
			//request.getRequestDispatcher("get.folderContent?keter="+keter+"&alm="+alm+"&hak="+hak).forward(request,response);
		}
		doGet(request,response);
	}

}
