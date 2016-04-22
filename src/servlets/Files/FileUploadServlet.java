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
 * Servlet implementation class FileUploadServlet
 */
@WebServlet("/FileUploadServlet")
public class FileUploadServlet extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doget");
		 System.out.println("1");
		PrintWriter out = response.getWriter();
	   HttpSession session = request.getSession(true);
	   FileUploadListener listener = null;
	   StringBuffer buffy = new StringBuffer();
	   long bytesRead = 0, contentLength = 0;
	 
	   // Make sure the session has started
	   if (session == null)
	   //if(false)
	   {
	      return;
	   }
	   else if (session != null)
	   {
	      // Check to see if we've created the listener object yet
	      listener = (FileUploadListener) session.getAttribute("LISTENER");
	 
	      if (listener == null)
	      //if(false)
	      {
	         return;
	      }
	      else
	      {
	         // Get the meta information
	         bytesRead = listener.getBytesRead();
	         contentLength = listener.getContentLength();
	      }
	   }
	   System.out.println("2");
	   response.setContentType("text/xml");
	 
	   buffy.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n");
	   buffy.append("<response>\n");
	   buffy.append("\t<bytes_read>" + bytesRead + "</bytes_read>\n");
	   buffy.append("\t<content_length>" + contentLength +
	                "</content_length>\n");
	 
	   // Check to see if we're done
	   if (bytesRead == contentLength)
	   {
	      buffy.append("\t<finished />\n");
	 
	      // No reason to keep listener in session since we're done
	      session.setAttribute("LISTENER", null);
	   }
	   else
	   {
	      // Calculate the percent complete
	      long percentComplete = ((100 * bytesRead) / contentLength);
	 
	      buffy.append("\t<percent_complete>" + percentComplete +
	                   "</percent_complete>\n");
	   }
	 
	   buffy.append("</response>\n");
	   System.out.println("buffy="+buffy);
	   out.println(buffy.toString());
	   out.flush();
	   out.close();
	  }

	  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	  {
	      // create file upload factory and upload servlet
		  //doGet(request,response);
	      FileItemFactory
	         factory = new DiskFileItemFactory();
	      ServletFileUpload
	         upload = new ServletFileUpload(factory);
	 
	      // set file upload progress listener
	      FileUploadListener listener = new FileUploadListener();
	 
	      HttpSession session = request.getSession(true);
	 
	      session.setAttribute("LISTENER", listener);
	 
	      // upload servlet allows to set upload listener
	      upload.setProgressListener(listener);
	 
	      List
	         uploadedItems = null;
	      FileItem
	         fileItem = null;
	      String
	         // Path to store file on local system
	         filePath = Constants.getFolderBuktiBayaran()+"/";
	 
	      try
	      {
	         // iterate over all uploaded files
	         uploadedItems = upload.parseRequest(request);
	 
	         Iterator
	            i = uploadedItems.iterator();
	 
	         while (i.hasNext())
	         {
	            fileItem = (FileItem) i.next();
	 
	            if (fileItem.isFormField() == false)
	            {
	               if (fileItem.getSize() > 0)
	               {
	                  File
	                     uploadedFile = null;
	                  String
	                     myFullFileName = fileItem.getName(),
	                     myFileName = "",
	                     slashType = (myFullFileName.lastIndexOf("\\")
	                        > 0) ? "\\" : "/";    // Windows or UNIX
	                  int
	                     startIndex =
	                        myFullFileName.lastIndexOf(slashType);
	 
	                  // Ignore the path and get the filename
	                  myFileName = myFullFileName.substring
	                    (startIndex + 1, myFullFileName.length());
	 
	                  // Create new File object
	                  uploadedFile = new File(filePath, myFileName);
	 
	                  // Write the uploaded file to the system
	                  fileItem.write(uploadedFile);
	               }
	            }
	         }
	      }
	      catch (FileUploadException e)
	      {
	         e.printStackTrace();
	      }
	      catch (Exception e)
	      {
	         e.printStackTrace();
	      }
	  } 
}
