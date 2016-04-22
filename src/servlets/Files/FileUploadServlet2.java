package servlets.Files;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.Servlet;
import java.io.*;
import java.util.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
//import com.missiondata.fileupload.*;
import beans.setting.Constants;
import listeners.*;
/**
 * Servlet implementation class FileUploadServlet2
 */
@WebServlet("/FileUploadServlet2")
public class FileUploadServlet2 extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
    String msg = "ampun";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadServlet2() {
        super();
        
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doGet");
		
		
		//msg = request.getParameter("minor");
		//msg = "ampun";

	    response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
	    response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
	    response.getWriter().write(msg); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("doPusr");
		//String msg = "oke";
		msg=""+request.getParameter("minor");
		System.out.println(msg);
		doGet(request,response);
	} 
}
