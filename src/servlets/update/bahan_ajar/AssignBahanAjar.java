package servlets.update.bahan_ajar;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.bahan_ajar.UpdateDbBahanAjar;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.PathFinder;

/**
 * Servlet implementation class AssignBahanAjar
 */
@WebServlet("/AssignBahanAjar")
public class AssignBahanAjar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssignBahanAjar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession(true); 
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr"); 
		if(isu==null) { 
			response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
		} 
		else { 
		//kode here
			//System.out.println("assign bahan ajar");
			String tipe = request.getParameter("tipe");
			String atMenu = request.getParameter("atMenu");
			String nakmk = request.getParameter("nakmk");
			String kdkmk = request.getParameter("kdkmk");
			String kdpst = request.getParameter("kdpst");
			String idkur = request.getParameter("idkur");
			String shift = request.getParameter("shift");
			String unique_id = request.getParameter("unique_id");
			//System.out.println("unique_id="+unique_id);	
			String []file_path = request.getParameterValues("file_path");
			UpdateDbBahanAjar udb = new UpdateDbBahanAjar(isu.getNpm());
			if(file_path!=null && file_path.length>0) {
				
				int ins = udb.assignBahanAjar(unique_id, tipe, file_path);
				if(ins>0) {
					//System.out.println("updated");
				}
			}
			else {
				//tidak ada assignment utk tipe ini / direset
				int ins = udb.resetBahanAjar(unique_id, tipe);
			}
			
			//<a href="get.listBahanAjarDosen?unique_id=<%=unique_id %>&atMenu=pba&nakmk=<%=nakmk %>&kdkmk=<%=kdkmk %>&kdpst=<%=kdpst %>&idkur=<%=idkur %>&shift=<%=shift%>"><%=shift %></a>
			//String target = Constants.getRootWeb()+"/InnerFrame/Parameter/InnerMenu0_pageVersion.jsp"; 
			//String uri = request.getRequestURI(); 
			//String url = PathFinder.getPath(, target);
			request.getRequestDispatcher("get.listBahanAjarDosen?unique_id="+unique_id+"&atMenu="+atMenu+"&nakmk="+nakmk+"&kdkmk="+kdkmk+"&kdpst="+kdpst+"&idkur="+idkur+"&shift="+shift).forward(request,response);

		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
