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
 * Servlet implementation class ObjectParameter_ver2
 */
@WebServlet("/ObjectParameter_ver2")
public class ObjectParameter_ver2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ObjectParameter_ver2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("update param");
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
		     response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			
			String[]cmd = request.getParameterValues("cmd"); 
			String[]dependency = request.getParameterValues("dependency");
			String[]keter = request.getParameterValues("keter");
			String[]bacaTulis = request.getParameterValues("bacaTulis");
			String[]scopeKampus = request.getParameterValues("scopeKampus");
			String[]value = request.getParameterValues("aksesValue");
			String access_level_cond = "";
			String hak_akses = "";
			String scope_kampus = "";
			String list_cmd = "";
			for(int i=0;i<cmd.length;i++) {
				if(value[i]!=null && !Checker.isStringNullOrEmpty(value[i])) {
					list_cmd = list_cmd + cmd[i] +"#";
					if(bacaTulis[i]==null || Checker.isStringNullOrEmpty(bacaTulis[i])) {
						bacaTulis[i]=""+cmd[i];
					}
					if(scopeKampus[i]==null || Checker.isStringNullOrEmpty(scopeKampus[i])) {
						scopeKampus[i]=""+cmd[i];
					}
					hak_akses = hak_akses + bacaTulis[i] +"#";
					scope_kampus = scope_kampus + scopeKampus[i] +"#";
					//list_cmd = list_cmd + cmd[i] +"#";
					access_level_cond = access_level_cond+value[i]+"#";
					
					//System.out.println(i+". "+cmd[i]+"-"+dependency[i]+"-"+keter[i]+"-"+bacaTulis[i]+"-"+scopeKampus[i]+"-"+value[i]);
				}
				//System.out.println(hak_akses);
				//System.out.println(scope_kampus);
				//System.out.println(list_cmd);
				//System.out.println(access_level_cond);
				
			}
			String targetObjId = request.getParameter("targetObjId");
			/*
			 * JSONObject jobTarget = (JSONObject)session.getAttribute("job");
			 
			session.removeAttribute("job");
			try {
				targetObjId = jobTarget.get("ID_OBJ").toString();	
			}
			catch(Exception e) {
				
			}
			*/
			//System.out.println("targetObjId="+targetObjId);
			
			//String listCmd = "",listValue="";
			//String targetObj = request.getParameter("targetObj");
			
			UpdateDbObj udb = new UpdateDbObj(isu.getNpm());
			udb.updateAccessLevelObj(Long.valueOf(targetObjId).longValue(), list_cmd, access_level_cond, hak_akses, scope_kampus);
			String target = Constants.getRootWeb()+"/InnerFrame/json/tamplete/select/selectObj.jsp";
			response.sendRedirect(target+"?scope=editObjParam&atMenu=editObj&backTo=/InnerFrame/Parameter/dashObjekParam.jsp");
			
		}
		
		/*
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

			
			String target = Constants.getRootWeb()+"/InnerFrame/json/tamplete/select/selectObj.jsp";
			String val = targetObj.toString().replace("\"", "tandaKutip");
			val = val.replace("#", "tandaPagar");
			//System.out.println("tobj="+targetObj.toString().replace("\"", "tandaKutip"));
			response.sendRedirect(target+"?targetObj="+val);
		}
		*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request,response);
	}

}
