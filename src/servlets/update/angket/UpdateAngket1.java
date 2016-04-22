package servlets.update.angket;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.dbase.angket.UpdateDbAngket;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;

/**
 * Servlet implementation class UpdateAngket1
 */
@WebServlet("/UpdateAngket1")
public class UpdateAngket1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateAngket1() {
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
			response.sendRedirect(Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			System.out.println("update angket1");
			
			//session.setAttribute("submitButtonValue", submitButtonValue);
			String fieldAndValue = (String)session.getAttribute("fieldAndValue");
			session.removeAttribute("fieldAndValue");
			String nmpw = (String)session.getAttribute("nmpw");
			session.removeAttribute("nmpw");
			StringTokenizer st = new StringTokenizer(nmpw,"||");
			String usrnama = st.nextToken();
			String usrpwd = st.nextToken();
			UpdateDbAngket udb = new UpdateDbAngket(isu.getNpm());
			udb.updateDataAngket1(fieldAndValue,  usrnama, usrpwd);
			request.getRequestDispatcher("main_login.login?usr="+usrnama+"&pwd="+usrpwd).forward(request,response);
			
					/*
Gender_Huruf_Wajib=L
Tahun-Lulus_Int_Wajib=1111
No-Hape_Hape_Wajib=333
Kota-Kelahiran_String_Wajib=afdasd
Negara-Kelahiran_Huruf_Wajib=INDONESIA
Kota-SMA_String_Wajib=asfas
Telp-Rumah_Telp_Wajib=333
Npmhs_String_Wajib=0000000000001
Kode-Pos_String_Opt=333
Alamat-Rumah_String_Wajib=safd
Email_Email_Wajib=a@vom.com
Nama-SMA_String_Wajib=aasd
Status_Huruf_Wajib=Sendiri
Tgl-Lahir_Date_Wajib=11/11/2001
Kota-Tempat-Tinggal_String_Wajib=adsfa
somebutton_String_Opt=Update Data
StringfwdPageIfValid_String_Opt=update.angket1
Agama_Huruf_Wajib=Islam
					 */
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
