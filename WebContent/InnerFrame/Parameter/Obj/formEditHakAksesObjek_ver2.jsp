<!DOCTYPE html>
<head>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/jquery/jquery.min.js"></script>
</script>
<script>
/*
 * 
$(document).ready(function(){
  $("button").click(function(){
    $.getJSON("http://localhost:8080/com.otaku.rest/api/v1/status/usg_db/listMhs",function(result){
      $.each(result, function(){
        $('#div_msg').append(result[0].NMMHSMSMHS + " ");
      });
    });
  });
});
 */
</script>
<%

 %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>version 2</title>
<%

	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String targetObjId = request.getParameter("targetObjId");
	Vector vUpdated = (Vector) request.getAttribute("vUpdated");
	String nicknameTargetObj = request.getParameter("nicknameTargetObj");
	Vector vList = validUsr.getScopeUpd7des2012("editObjParam");
	//System.out.println(targetObjId);
	String obj_desc = Checker.getObjDesc(Integer.parseInt(targetObjId));
	ListIterator li = null;
	/*
	lim.add(cmd_code);
	lim.add(cmd_keter);
	lim.add(who_use_it);
	lim.add(dependency);
	lim.add(value_);
	lim.add(value_hak_akses);
	lim.add(value_scope_kampus);
	*/
	//ListIterator li = vUpdated.listIterator();
	//while(li.hasNext()) {
	//	System.out.println((String)li.next());
	//}
%>
</head>

<body>

<div id="header">
	<ul>
		<li>
		<%
		String target = Constants.getRootWeb()+"/InnerFrame/json/tamplete/select/selectObj.jsp";
		String uri = request.getRequestURI();
		String url = PathFinder.getPath(uri, target);
		%>
		<!-- a href="<%=url %>?scope=editObjParam&atMenu=editObj&backTo=<%="/InnerFrame/Parameter/dashObjekParam.jsp" %>" target="_self">GO<span>BACK</span></a  -->
		<a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">GO<span>BACK</span></a>
		</li>
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
<%
if(vUpdated!=null && vUpdated.size()>0) {
%>			

   	   	
	<h2 align="center" style="color:#369"><%=obj_desc.toUpperCase() %></h2>
	
	<form action="go.updParamByCopy">
	<input type="hidden" name="targetObjId" value="<%=targetObjId %>" />
	<table align="center" border="1" style="background:#d9e1e5;color:#000;width:780px;">
		<tr>
			<td align="center" bgcolor="#369" style="color:#fff" padding-left="2px" colspan="2"><b>COPY DARI OBJEK LAIN</b></td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:center;width:50%" >PILIH BASED OBJEK</td>
   			<td valign="top" style="text-align:left;width:50%" >
   				<select name="based_obj" style="width:100%">
<%
		ListIterator li1 = vList.listIterator();
		while(li1.hasNext()) {
			String brs = (String)li1.next();
			//116 88888 MHS_AGAMA 116 C
			StringTokenizer st = new StringTokenizer(brs);
			String objId = st.nextToken();
			String kdpst = st.nextToken();
			String objDesc = st.nextToken();		
			objDesc = objDesc.replace("MHS_", "");
			String objLvl = st.nextToken();
			String kdjen = st.nextToken();
%>
					<option value="<%=objId%>"> <%=objDesc %> </option>
<%	
		}
%>			
				</select>
   			</td>
		</tr>	
		<tr>
			<td style="background:#369;color:#fff;text-align:center;width:50%" >PAIR ID OWN INBOX</td>
   			<td valign="top" style="text-align:left;width:50%"><input type="text" name="nu_own_inbox_id" value="" style="width:99%" placeholder="oldValue|nuValue`oldValue|nuValue"/>  </td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:center;width:50%" >PAIR ID TU</td>
   			<td valign="top" style="text-align:left;width:50%" ><input type="text" name="nu_tu_id" value="" style="width:99%" placeholder="oldValue|nuValue`oldValue|nuValue"/>  </td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:center;width:50%" >PAIR ID BAA</td>
   			<td valign="top" style="text-align:left;width:50%" ><input type="text" name="nu_baa_id" value="" style="width:99%" placeholder="oldValue|nuValue`oldValue|nuValue"/>  </td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:center;width:50%" >PAIR ID BAK</td>
   			<td valign="top" style="text-align:left;width:50%" ><input type="text" name="nu_bak_id" value="" style="width:99%" placeholder="oldValue|nuValue`oldValue|nuValue"/>  </td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:center;width:50%" >PAIR ID SCOPE MHS</td>
   			<td valign="top" style="text-align:left;width:50%" ><input type="text" name="nu_mhs_id" value="" style="width:99%" placeholder="oldValue|nuValue`oldValue|nuValue"/>  </td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:center;width:50%" >SCOPE KAMPUS</td>
   			<td valign="top" style="text-align:left;width:50%" ><input type="text" name="nu_scp_kmp" value="" style="width:99%" placeholder="kode,kode,..."/>  </td>
		</tr>		
		<tr>
			<td style="background:#369;color:#fff;text-align:center;width:50%" >KAMPUS DOMISILI</td>
			<td valign="top" style="text-align:left;width:50%" >
				<select name="kode_kmp" style="width:99%">
<%	
				Vector vKmp = Getter.getListAllKampus();
				li1 = vKmp.listIterator();
				while(li1.hasNext()) { 
					String brs = (String)li1.next();
					StringTokenizer st = new StringTokenizer(brs,"`");
					String kode = st.nextToken();
					String nama = st.nextToken();
					String nick = st.nextToken();
%>
					<option value="<%=kode %>"><%=nick %></option>
<%
				}
%>					
				</select>
			</td>
		</tr>
		<tr>
			<td style="background:#369;color:#fff;text-align:center;width:50%" >SISTEM PERKULIAHAN</td>
			<td valign="top" style="text-align:left;width:50%" >
				<select name="sis_kul" style="width:99%">
					<option value="null">N/A</option>
<%	
				Vector vTmp = Constant.getIt("SISTEM_PERKULIAHAN");
				ListIterator liTmp = vTmp.listIterator();
				String keter = (String)liTmp.next();
				String values = (String)liTmp.next();
				StringTokenizer st = new StringTokenizer(values);
				while(st.hasMoreTokens()) {
				
					String value = st.nextToken();
%>
					<option value="<%=value %>"><%=value %></option>
<%
				}				
%>					
				</select>
			</td>
		</tr>
		
		<tr>
			<td align="right" bgcolor="#369" colspan="2"><input type="submit" value="Copy" style="width:200px;height:30px;padding:5px 5px" /></td>
		</tr>
	</table>	
	</form>
<%
	li = vUpdated.listIterator();
%> 		
			<form action="update.objParam_ver2" method="post" >
				<input type="hidden" name="targetObjId" value="<%=targetObjId %>" />

<%
	//boolean first = true;
	int k = 0;
	while(li.hasNext()) {
		k++;
		String cmd_code = (String) li.next();
		String cmd_keter = (String) li.next();
		String who_use_it = (String) li.next();
		String dependency = (String) li.next();
		String value_ = (String) li.next();
		String value_hak_akses = (String) li.next();
		String value_scope_kampus = (String) li.next();
		/*
		System.out.println(k+"."+cmd_code);
		System.out.println(k+"."+cmd_keter);
		System.out.println(k+"."+who_use_it);
		System.out.println(k+"."+dependency);
		System.out.println(k+"."+value_);
		System.out.println(k+"."+value_hak_akses);
		System.out.println(k+"."+value_scope_kampus);
		*/
		//if(first) {
	///		first = false;	
	//	}	
%>	
				<br/>
				<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:750px;table-layout: fixed;">
    			<tr>	
   					<td style="background:#369;color:#fff;text-align:center;width:100px" colspan="1">KODE</td>
   					<td valign="top" style="text-align:left;width:150px" colspan="1">
   		<%
   		out.print(cmd_code);
   		%>
   					<input type="hidden" name="cmd" value="<%=cmd_code %>" />
   					</td>
   					<td style="background:#369;color:#fff;text-align:center;width:100px" colspan="1">DEPENDENCY</td>
   					<td style="text-align:center;width:400px" colspan="1">
   		<%
   		
   		out.print("dependency");
   		%>
   					<input type="hidden" name="dependency" value="<%=dependency %>" />
   					</td>
   				</tr>
   				<tr>	
   					<td style="background:#369;color:#fff;text-align:center;width:100px" colspan="1">KETERANGAN</td>
   					<td style="text-align:left" colspan="3">
   		<%
   		
   		out.print(cmd_keter);	
   			 
   		%>
   					<input type="hidden" name="keter" value="<%=cmd_keter %>" />
   					</td>
   				</tr>
   				<tr>	
   					<td style="background:#369;color:#fff;text-align:center;width:100px" colspan="1">DIGUNAKAN OLEH</td>
   					<td style="text-align:left;word-wrap:break-word" colspan="3">
   		<%
   		out.print(who_use_it);
   		%>
			   		</td>
			   	</tr>
   				<tr>
			   		<td style="background:#369;color:#fff;text-align:center;width:100px" >SCOPE EDIT</td>
   					<td style="text-align:center;width:150px" >
   		<%
   		st = new StringTokenizer(value_hak_akses,"$");
   		//out.print(value_hak_akses);
   		String default_hak_akses_value = st.nextToken(); //if null maka default r`e`i`d
   		String curr_hak_akses_value = st.nextToken();
   		//out.println("curr_hak_akses_value="+curr_hak_akses_value);
   		//out.println("default_hak_akses_value="+default_hak_akses_value);
   		String value_to_show = "";
   		boolean show_placeholder = true;//placeholder = default value
   		if(curr_hak_akses_value!=null && !Checker.isStringNullOrEmpty(curr_hak_akses_value)) {
   			//out.print("masuk1<br/>");
   			value_to_show = ""+curr_hak_akses_value;
   			show_placeholder = false;
   		}

   		if(show_placeholder) {
   		%>
			   			<input type="text" name="bacaTulis" style="width:98%;height:98%" placeholder="r,e,i,d"/>
   		<%	
   		}
   		else {
   		%>
   						<input type="text" name="bacaTulis" style="width:98%;height:98%" value="<%=value_to_show%>"/>
   	   	<%
   		}	
   		%>
   					</td>	
   					<td style="background:#369;color:#fff;text-align:center;width:100px" >SCOPE KAMPUS</td>
   		
   		<%
   		st = new StringTokenizer(value_scope_kampus,"$");
   		//out.print(value_hak_akses);
   		String default_scope_kampus_value = st.nextToken(); //if null maka default r`e`i`d
   		String curr_scope_kampus = st.nextToken();

   		//value_to_show = "";
   		
   		show_placeholder = true;//placeholder = default value
   		if(curr_scope_kampus!=null && !Checker.isStringNullOrEmpty(curr_scope_kampus)) {
   			//out.print("masuk1<br/>");
   			//value_to_show = ""+curr_hak_akses_value;
   			show_placeholder = false;
   		}
   		if(show_placeholder) {
   		%>
   	   				<td style="text-align:center;" colspan="1"><input type="text" name="scopeKampus" placeholder="<%=default_scope_kampus_value %>"  style="width:98%;height:98%"/></td>
   	   	<%
   	   	}
   	   	else {
   	   	%>
   	   				<td style="text-align:center;" colspan="1"><input type="text" name="scopeKampus" value="<%=curr_scope_kampus %>"  style="width:98%;height:98%"/></td>
   	   	<%	
   	   	}%>
   				</tr>	
   				<tr>	
   					<td style="background:#369;color:#fff;text-align:center;width:100px" >VALUE / SCOPE LVL CIVITAS</td>
   					<td style="text-align:center;" colspan="3">
   		<%
   		st = new StringTokenizer(value_,"$");
   		//out.print(value_hak_akses);
   		String default_scope__value = st.nextToken(); //if null maka default "own"
   		String curr_scope_value = st.nextToken();

   		String value_at_placeholder = "";
   		
   		show_placeholder = true;//placeholder = default value
   		if(curr_scope_value!=null && !Checker.isStringNullOrEmpty(curr_scope_value)) {
   			//out.print("masuk1<br/>");
   			//value_to_show = ""+curr_hak_akses_value;
   			show_placeholder = false;
   		}
   		else if(default_scope__value!=null && !Checker.isStringNullOrEmpty(default_scope__value)) {
   			//kalo defaultnya ngga null value place holder = default value
   			value_at_placeholder = ""+default_scope__value;
   		}
   		else {
   			value_at_placeholder = "own";
   		}
   		if(show_placeholder) {
   		%>
   						<textarea name="aksesValue" style="rows:4;width:99%" placeholder="<%=value_at_placeholder%>"></textarea>	
   		<%
   		}
   		else {
   		%>
   						<textarea name="aksesValue" style="rows:4;width:99%"><%=curr_scope_value%></textarea>	
   		<%
   		}
   		%>	
   					</td>
   				</tr>
   			
   	<!--  tr>
   		<td style="text-align:center" colspan="4">
   		<table>
			<tr>
   				<td style="background:#369;color:#fff;text-align:left;width:225px" colspan="1">descripsi objek</td>
   				<td style="background:#369;color:#fff;text-align:center;width:25px" colspan="1">obkek level</td>
   				<td style="background:#369;color:#fff;text-align:left;width:225px" colspan="1">
   				<%
   					out.print(("OBJ_DESC"));
   				%>
   				</td>
   				<td style="background:#369;color:#fff;text-align:center;width:25px" colspan="1">
   				<%
   				
   					out.print(("OBJ_LEVEL"));
   			
				%>
				</td>
   				<td style="background:#369;color:#fff;text-align:left;width:225px" colspan="1">
   				<%
	   				out.print(("OBJ_DESC"));
   				%>
   				</td>
   				<td style="background:#369;color:#fff;text-align:center;width:25px" colspan="1">
   				<%
   				
   					out.print(("OBJ_LEVEL"));
   					
				%>
				</td>
				
   			</tr -->
   		<%

   		%>	
   			</table>
				
   	<%
 	}
   	%>
   	<br/>	
	<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:50%">
   	<tr>
   			<td style="background:#369;color:#fff;text-align:center" colspan="4">
   			<input type="submit" value="UPDATE" style="width:100%;height:35px" />
   			</td>
   		</tr>	
   	</table>
   	
   	<br/><br/>
   	<!--  table>
	<tr>
   		<td style="background:#369;color:#fff;text-align:center" colspan="4">
   			<input type="submit" value="UPDATE" style="width:80%;height:35px" />
   		</td>
   	</tr>
	</table-->
	</form>
   	<%
}
   	%>	
   	<br/>
	</div>
</div>
		
</body>
</html>