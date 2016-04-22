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
 * Servlet implementation class InsertDataPaketBeasiswa
 */
@WebServlet("/InsertDataPaketBeasiswa")
public class InsertDataPaketBeasiswa extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertDataPaketBeasiswa() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
		     response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			System.out.println("mpe juga");
			String namaPaket = null;
			String jenisBea = null;
			String jumlahDana = null;
			String periode = null;
			String namaInstansi = null;
			String jenisInstansi = null;
			String syarat = null;
			String fieldAndValue = (String)session.getAttribute("fieldAndValue");
			session.removeAttribute("fieldAndValue");
			System.out.println("fieldAndValue="+fieldAndValue);
			StringTokenizer st = new StringTokenizer(fieldAndValue,"||");
			while(st.hasMoreTokens()) {
				String nama = st.nextToken();
				String val = st.nextToken();
				val = val.toUpperCase();
				if(nama.equalsIgnoreCase("Jenis-Beasiswa_String_Opt")) {
					jenisBea = ""+val;
				}
				else if(nama.equalsIgnoreCase("Nama-Paket_String_Wajib")) {
					namaPaket = ""+val;
				}
				else if(nama.equalsIgnoreCase("Total-Biaya_Double_Wajib")) {
					jumlahDana = ""+val;
				}
				else if(nama.equalsIgnoreCase("Unit-Periode_String_Opt")) {
					periode = ""+val;
				}
				else if(nama.equalsIgnoreCase("Nama-Instansi_String_Wajib")) {
					namaInstansi = ""+val;
				}
				else if(nama.equalsIgnoreCase("Jenis-Instansi_String_Opt")) {
					jenisInstansi = ""+val;
				}
				else if(nama.equalsIgnoreCase("Persyaratan_String_Wajib")) {
					syarat = ""+val;
					syarat = syarat.replace("\n", "<br/>");
				}
			}
			System.out.println("namaPaket="+namaPaket);
			System.out.println("jenisBea="+jenisBea);
			System.out.println("jumlahDana="+jumlahDana);
			System.out.println("periode="+periode);
			System.out.println("namaInstansi="+namaInstansi);
			System.out.println("jenisInstansi="+jenisInstansi);
			System.out.println("syarat="+syarat);
			UpdateDbBeasiswa udb = new UpdateDbBeasiswa(isu.getNpm());
			udb.addPaketBeasiswa(namaPaket, jenisBea, jumlahDana, periode, namaInstansi, jenisInstansi, syarat);
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
