package servlets.Get.Object;

import java.io.IOException;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;
import org.owasp.esapi.ESAPI;

import beans.dbase.Param.SearchDbTabelCommand;
import beans.login.InitSessionUsr;
import beans.setting.Constants;
import beans.tools.Checker;
import beans.tools.Getter;
import beans.tools.PathFinder;


/**
 * Servlet implementation class InfoObjectBasedOnObjId
 */
@WebServlet("/InfoObjectBasedOnObjId_v2")
public class InfoObjectBasedOnObjId_v2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InfoObjectBasedOnObjId_v2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("get inof object");
		
		HttpSession session = request.getSession(true);
		InitSessionUsr isu = (InitSessionUsr)session.getAttribute("validUsr");
		if(isu==null) {
		     response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html");
		}
		else {
		//kode here
			String scope = request.getParameter("scope");
			String targetObjId = request.getParameter("targetObj");
			Vector vListAllCampus = Getter.getListAllKampus();
			ListIterator li1 = vListAllCampus.listIterator();
			String listCodeAllKampus = "";
			while(li1.hasNext()) {
				String brs = (String) li1.next();
				StringTokenizer st = new StringTokenizer(brs,"`");
				listCodeAllKampus = listCodeAllKampus + st.nextToken();
				if(li1.hasNext()) {
					listCodeAllKampus=listCodeAllKampus+",";
				}
			}	
			/*
			 * get info command yg tersedia pada tabel command
			 */
			String object_desc = Checker.getObjName(Integer.parseInt(targetObjId));
			int usr_obj_level = isu.getObjLevel();
			SearchDbTabelCommand sdb = new SearchDbTabelCommand();
			Vector v = sdb.getListAllCommand(usr_obj_level);
			Vector v_updated = new Vector();
			
			if(v!=null && v.size()>0) {
				Vector v1 = isu.getKomplitInfoObj(Integer.parseInt(targetObjId));
				String kdpst = null;
				String obj_name = null;
				String obj_desc = null;
				String obj_lvl = null;
				String access_level_con = null;
				String access_level = null;
				String default_val = null;
				String mickname = null;
				String hak_akses = null;
				String scope_kampus = null;
				String kode_kampus_domisili = null;
				//jika targetId is exixting object jd sdh ada record di tabel objeck
				/*
				 * disini kita mencari existing value hak akses, scope kampous, scope val/access_level value
				 */
				if(v1!=null && v1.size()>0) {
					li1 = v1.listIterator();
					kdpst = (String)li1.next();
					obj_name = (String)li1.next();
					obj_desc = (String)li1.next();
					obj_lvl = (String)li1.next();
					access_level_con = (String)li1.next();
					access_level = (String)li1.next();
					default_val = (String)li1.next();
					mickname = (String)li1.next();
					hak_akses = (String)li1.next();
					scope_kampus = (String)li1.next();
					kode_kampus_domisili = (String)li1.next();
					
					ListIterator li = v.listIterator();
					ListIterator lim = v_updated.listIterator();
					while(li.hasNext()) {
						String cmd_code = (String)li.next();
						String cmd_keter = (String)li.next();
						String who_use_it = (String)li.next();
						String dependency = (String)li.next();
						String value_ = (String)li.next();//default from tabel_command
						String value_hak_akses = (String)li.next();//default from tabel_command
						String value_scope_kampus = (String)li.next();//default from tabel_command
						
						//declare variable cuurent value
						String current_access_lvl_conditional_value = null;
						String current_hak_akses_value = null;
						String current_scope_kampus_value = null;
						
						//get no urut ke berapa cmd_code diatas terhadap access_level
						
						StringTokenizer st = new StringTokenizer(access_level,"#");
						int i = 0;
						boolean match = false;
						while(st.hasMoreTokens()&&!match) {
							i++;
							String tmp_access_lvl = st.nextToken();
							if(cmd_code.equals(tmp_access_lvl)) {
								match = true;
							}
						}
						if(match) {
							//1.get access_level conditional value
							st = new StringTokenizer(access_level_con,"#");
							//value_ = new String();
							if(st.countTokens()<i) {
								//belum ada valuenya
								value_ = value_+"$"+null;
							}
							else {
								for(int j=0;j<i;j++) {
									current_access_lvl_conditional_value = st.nextToken();
								}
								value_ = value_+"$"+current_access_lvl_conditional_value;
							}
							
							//2. get hak akses value
							st = new StringTokenizer(hak_akses,"#");
							//value_hak_akses = "";
							if(st.countTokens()<i) {
								/*
								 * match, tapi kelupaan diisi jadi diisi default = r,e,i,d
								 */
								value_hak_akses = value_hak_akses+"$r,e,i,d";
							}
							else {
								for(int j=0;j<i;j++) {
									current_hak_akses_value = st.nextToken();
								}	
								if(current_hak_akses_value.equalsIgnoreCase(cmd_code)) {
										/*
										 * match, tapi isinya masih sama dengan comand code = isi default
										 */	
									value_hak_akses = value_hak_akses+"$r,e,i,d";
								}
								else {
									value_hak_akses = value_hak_akses+"$"+current_hak_akses_value;
								}
							}
								
							
							
							//3. get scopekampus value
							st = new StringTokenizer(scope_kampus,"#");
							if(st.countTokens()<i) {
								//belum ada valuenya
								if(object_desc.contains("admin")||object_desc.contains("ADMIN")) {
									value_scope_kampus = value_scope_kampus+"$"+listCodeAllKampus;
								}
								else {
									value_scope_kampus = value_scope_kampus+"$null";
								}
							}
							else {
								for(int j=0;j<i;j++) {
									current_scope_kampus_value = st.nextToken();
								}
								if(current_scope_kampus_value.equalsIgnoreCase(cmd_code)) {
									/*
									 * match, tapi isinya masih sama dengan comand code = isi default
									 */	
									if(object_desc.contains("admin")||object_desc.contains("ADMIN")) {
										value_scope_kampus = value_scope_kampus+"$"+listCodeAllKampus;
									}
									else {
										value_scope_kampus = value_scope_kampus+"$null";
									}
								}
								else {
									value_scope_kampus = value_scope_kampus+"$"+current_scope_kampus_value;
								}
							}
							
						}
						else {
							//command code = tidak termasuk jadi isinya null
							value_ = value_+"$"+null;
							value_hak_akses = value_hak_akses+"$"+null;
							value_scope_kampus = value_scope_kampus+"$"+null;
						}

						lim.add(cmd_code);
						lim.add(cmd_keter);
						lim.add(who_use_it);
						lim.add(dependency);
						lim.add(value_);
						lim.add(value_hak_akses);
						lim.add(value_scope_kampus);
					}
				}
				else {
					/*
					 * target id = new obj jadi blum ada tecordnya di tabel object
					 * jadi cukup ditampilkan default /option value
					 */
				}
				
				//System.out.println("==================");
				
				
			}
			else {
				System.out.println("empty v");
			}
			
			//request.setAttribute("vInfoKomplitObj", v);
			//JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/search_obj_type/"+isu.getIdObj()+"/hak_akses/"+scope);
			//JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/search_obj_type/"+targetObjId);
			//JSONObject job = null;
			/*
			try {
				job  =  jsoa.getJSONObject(0);
				session.setAttribute("job", job);
				//request.setAttribute("targetObjId", targetObjId);
				System.out.println("job="+job.toString());
			}
			catch (JSONException e) {
				System.out.println("ada error disini");
			}
			*/
			request.setAttribute("vUpdated", v_updated);
			String target = Constants.getRootWeb()+"/InnerFrame/Parameter/Obj/formEditHakAksesObjek_ver2.jsp";
			String uri = request.getRequestURI();
			String url = PathFinder.getPath(uri, target);
			//System.out.println("0.cmd="+cmd+",atMenu="+atMenu+",scope="+scope);
			request.getRequestDispatcher(url+"?targetObjId="+targetObjId+"&nicknameTargetObj="+object_desc).forward(request,response);
			//JSONObject job = jsoa.getJSONObject(0);
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
