package servlets.update;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import beans.dbase.obj.UpdateDbObj;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;

/**
 * Servlet implementation class ObjectParameter
 */
@WebServlet("/ObjectParameter")
public class ObjectParameter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ObjectParameter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("update param");
		String[]cmd = request.getParameterValues("komando"); 
		String[]value = request.getParameterValues("aksesValue");
		JSONObject jobTarget = null;
		String listCmd = "",listValue="";
		String targetObj = request.getParameter("targetObj");
		String targetObjId = null;

		if(targetObj!=null) {
			try {
				jobTarget=new JSONObject(targetObj.replace("tandaKutip", "\""));
				targetObjId = jobTarget.getString("ID_OBJ");
			}
			catch(JSONException e) {
				e.printStackTrace();
			}
		}	
		
		if(cmd!=null && value!=null && targetObjId!=null) {
			HttpSession session = request.getSession(true);
			InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
			boolean first = true;
			for(int i=0;i<cmd.length;i++) {
				if(!Checker.isStringNullOrEmpty(value[i])) {
					if(first) {
						listCmd=listCmd+cmd[i];
						listValue=listValue+value[i];
						first=false;
					}
					else {
						listCmd=listCmd+"#"+cmd[i];
						listValue=listValue+"#"+value[i];
					}
					
				}
				
			}
			//System.out.println(listCmd+" and "+listValue);
			UpdateDbObj udb = new UpdateDbObj(isu.getNpm());
			udb.updateAccessLevelObj(Long.valueOf(targetObjId).longValue(), listCmd, listValue);

			
			String target = Constants.getRootWeb()+"/InnerFrame/json/form/edit/object/formEditHakAksesObjek.jsp";
			String val = targetObj.toString().replace("\"", "tandaKutip");
			val = val.replace("#", "tandaPagar");
			//System.out.println("tobj="+targetObj.toString().replace("\"", "tandaKutip"));
			response.sendRedirect(target+"?targetObj="+val);
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
