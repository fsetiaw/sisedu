package servlets.process;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.mhs.*;
import beans.login.InitSessionUsr;
/**
 * Servlet implementation class UpdateDaftarUlang
 */
@WebServlet("/UpdateDaftarUlang")
public class UpdateDaftarUlang extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateDaftarUlang() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("dafatrulang");
		String from = request.getParameter("from");
		String[]opt1 = request.getParameterValues("option1");
		String thsms_regis = (String)request.getParameter("thsms_regis");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		UpdateDbInfoMhs udb = new UpdateDbInfoMhs(isu.getNpm());
		if(from!=null && from.equalsIgnoreCase("notification")) {
			if(opt1!=null) {
				udb.updateCekListTabelDaftarUlang(opt1, thsms_regis);
			}
		}
		else {
			if(opt1!=null) {
				//System.out.println("opt1 Not Null");
				udb.setNullCekListTabelDaftarUlang(opt1, thsms_regis);
				udb.updateCekListTabelDaftarUlang(opt1, thsms_regis);
			}
			else {
				//System.out.println("Null");
				udb.setNullCekListTabelDaftarUlang(opt1, thsms_regis);
			}	
		}
		if(from!=null && from.equalsIgnoreCase("notification")) {
			request.getRequestDispatcher("get.whoRegisterWip").forward(request,response);	
		}
		else {
			request.getRequestDispatcher("get.whoRegister").forward(request,response);
		}
		//
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
