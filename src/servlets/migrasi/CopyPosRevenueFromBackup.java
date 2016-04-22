package servlets.migrasi;
import beans.dbase.migrasi.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.tools.*;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.owasp.esapi.ESAPI;

/**
 * Servlet implementation class PosRevenue
 */
@WebServlet("/CopyPosRevenueFromBackup")
public class CopyPosRevenueFromBackup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CopyPosRevenueFromBackup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("migrasi");
		JSONArray jsoa = null;
		try {
			jsoa =  Getter.readJsonArrayFromUrl("/v1/pos_biaya/backup");
			UpdateDbMigrasi udb = new UpdateDbMigrasi();
			udb.copyPosRevenueTableFromBackup(jsoa);
		}
		catch(Exception e) {
			e.printStackTrace();
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
