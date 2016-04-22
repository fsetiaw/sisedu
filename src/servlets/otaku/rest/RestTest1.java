package servlets.otaku.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Servlet implementation class RestTest1
 */
@WebServlet("/RestTest1")
public class RestTest1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestTest1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doGet1");
		try {
			//JSONObject json = readJsonFromUrl("http://localhost:8080/com.otaku.rest/api/v1/status/usg_db/listMhs");
			JSONArray jArray = readJsonArrayFromUrl("/v1/status/usg_db/listMhs");
			//String data="[{"A":"a","B":"b","C":"c","D":"d","E":"e","F":"f","G":"g"}]";
			//System.out.println(tmp);
			//JSONObject jObject = new JSONObject();
			//JSONArray jArray = jObject.getJSONArray(tmp);
			//JSONArray jArray = new JSONArray(tmp);
			for (int i = 0; i < jArray.length(); i++) {
		        JSONObject jObj = jArray.getJSONObject(i);
		    
		        System.out.println(i + ". " + jObj.toString());
		        System.out.println("-----------------------------------------");
			}
		    //System.out.println(json.get("ID"));
		}
		catch(Exception e) {
			e.printStackTrace();
			
		}
		
		//request.g
		//request.getRequestDispatcher("http://localhost:8080/com.otaku.rest/api/v1/status/usg_db/listMhs").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doPost1");
		
	}
	
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }

	  public static JSONObject readJsonObjFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    } finally {
	      is.close();
	    }
	  }
	  
	  public static JSONArray readJsonArrayFromUrl(String url) throws IOException, JSONException {
		    InputStream is = new URL(url).openStream();
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      String jsonText = readAll(rd);
		      JSONArray jsoa = new JSONArray(jsonText);
		      return jsoa;
		    } finally {
		      is.close();
		    }
	  }
	  
	  public static String readStringFromUrl(String url) throws IOException, JSONException {
		    InputStream is = new URL(url).openStream();
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      String jsonText = readAll(rd);
		      //JSONObject json = new JSONObject(jsonText);
		      return jsonText;
		    } finally {
		      is.close();
		    }
	  }

}
