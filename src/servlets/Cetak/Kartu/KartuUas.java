package servlets.Cetak.Kartu;

import java.io.IOException;
import java.util.Vector;
import java.util.ListIterator;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.StringTokenizer;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ListIterator;
import java.util.Vector;
import beans.dbase.mhs.*;
import beans.setting.*;
import beans.tools.Converter;
import beans.tools.PathFinder;
import beans.folder.file.kartu.*;
import beans.login.InitSessionUsr;

/**
 * Servlet implementation class KartuUas
 */
@WebServlet("/KartuUas")
public class KartuUas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 // Constants ----------------------------------------------------------------------------------

    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    // Properties ---------------------------------------------------------------------------------

    private String filePath;

    // Actions ------------------------------------------------------------------------------------

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public void init(ServletConfig config) throws ServletException {

		super.init(config);
		/*
	    */
	  }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("kartu uas");
	    HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		String pencetak = "("+isu.getFullname()+")";
		String validator = "(MAENI AGUSTIN)";
		SearchDbInfoMhs sdb = new SearchDbInfoMhs(isu.getNpm());
		String thsms = request.getParameter("thsms_aktif");
		//thsms = Converter.convertThsmsValueOnly(thsms);
		String brs = request.getParameter("brs");
		String targetUjian = request.getParameter("targetUjian");
		 
		StringTokenizer st = new StringTokenizer(brs,"||");
		String kdpst=st.nextToken();
		String npmhs=st.nextToken();
		String tknApr=st.nextToken();
		String tknVer=st.nextToken();
		String tknKartuUjian = st.nextToken();
		String tknApprKartuUjian = st.nextToken();
		String tknStatus = st.nextToken();
		String tknRulesApproveeKartu = st.nextToken();
		String tot=st.nextToken();
		String nimhs=st.nextToken();
		String nmmhs=st.nextToken();
		String shift=st.nextToken();
		String smawl=st.nextToken();
		String stpid=st.nextToken();
		String gel=st.nextToken();
		String status=st.nextToken();
		String statusAkhir = "";
		String nmfak = Converter.getNamaFakultas(kdpst);
		String nmpst = Converter.getNamaKdpst(kdpst);
		String outNameFile = "UAS_"+thsms+"_"+npmhs;
		//get krs thsmsTarget
		Vector vMk = sdb.getKrsSmsThsms(thsms, kdpst, npmhs);
		Uas fm = new Uas();
		fm.prepKartuUas(thsms,kdpst,nmfak,nmpst, nimhs, nmmhs, vMk, targetUjian, outNameFile, pencetak,validator);
		
		File file = new File(Constants.getTmpFile()+"/"+outNameFile+".xlsx");
        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
        String contentType = getServletContext().getMimeType(file.getName());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        response.reset(); 
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        // Prepare streams.
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
            //System.out.println("29");
            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            //System.out.println("30");
        } finally {
            // Gently close streams.
            close(output);
            close(input);
        }
       

	}

	private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
                System.out.println("closing");
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it.
                e.printStackTrace();
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
