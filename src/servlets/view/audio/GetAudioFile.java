package servlets.view.audio;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.setting.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;

/**
 * Servlet implementation class GetAudioFile
 */
@WebServlet("/GetAudioFile")
public class GetAudioFile extends HttpServlet {
    // Constants ----------------------------------------------------------------------------------

    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    // Properties ---------------------------------------------------------------------------------

    private String audioPath;

    // Actions ------------------------------------------------------------------------------------

    public void init() throws ServletException {

        // Define base path somehow. You can define it as init-param of the servlet.
        //this.audioPath = Constants.getFolderPassPhoto()+"/6510110200012.jpg";
        //this.audioPath = Constants.getFolderAudio();
       // System.out.println("audioPath="+audioPath);
        // In a Windows environment with the Applicationserver running on the
        // c: volume, the above path is exactly the same as "c:\images".
        // In UNIX, it is just straightforward "/images".
        // If you have stored files in the WebContent of a WAR, for example in the
        // "/WEB-INF/images" folder, then you can retrieve the absolute path by:
        // this.audioPath = getServletContext().getRealPath("/WEB-INF/images");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
    	
        // Get requested image by path info.
        //String requestedAudio = request.getPathInfo();
    	String requestedAudio = request.getParameter("audioFile"); // complete file path
        // Check if file name is actually supplied to the request URI.

        if (requestedAudio == null) {
            // Do your thing if the image is not supplied to the request URI.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
        	System.out.println("requestedAudio = null");
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        // Decode the file name (might contain spaces and on) and prepare file object.
        //File image = new File(audioPath, URLDecoder.decode(requestedAudio, "UTF-8"));
        //requestedAudio = audioPath+"/"+requestedAudio; - 
        System.out.println("requestedAudio="+requestedAudio);
        File audio = new File(requestedAudio);
        // Check if file actually exists in filesystem.
        if (!audio.exists()) {
            // Do your thing if the file appears to be non-existing.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
        	/*System.out.println("image dosnt exixt");
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
            */
    	//==========================
        	//requestedAudio = audioPath+"/android.jpg";
            System.out.println("requestedAudio="+requestedAudio);
            audio = new File(requestedAudio);
            
        }

        // Get content type by filename.
        String contentType = getServletContext().getMimeType(audio.getName());
        System.out.println("contentType="+contentType);
        // Check if file is actually an image (avoid download of other files by hackers!).
        // For all content types, see: http://www.w3schools.com/media/media_mimeref.asp
        //if (contentType == null || !contentType.startsWith("image")) {
        if (contentType == null) {
            // Do your thing if the file appears not being a real image.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
        	System.out.println("not start images");
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        // Init servlet response.
        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(audio.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + audio.getName() + "\"");

        // Prepare streams.
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            // Open streams.
            input = new BufferedInputStream(new FileInputStream(audio), DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } finally {
            // Gently close streams.
            close(output);
            close(input);
        }
      
    }

    // Helpers (can be refactored to public utility class) ----------------------------------------

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it.
                e.printStackTrace();
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}
    
}
