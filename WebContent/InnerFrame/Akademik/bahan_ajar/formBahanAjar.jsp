<!DOCTYPE html>
<head>

<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>	
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.dbase.pengajuan.ua.*" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	Vector vListMakul = (Vector)request.getAttribute("vListMakul"); 
	String infoKur = request.getParameter("infoKur"); 
	Vector v= null;
	String id_obj_mask = ""+request.getParameter("id_obj");
	String nmm_mask = ""+request.getParameter("nmm");
	String npm_mask = ""+request.getParameter("npm");
	String obj_lvl_mask = ""+request.getParameter("obj_lvl");
	String kdpst_mask = ""+request.getParameter("kdpst");
	//String cmd = request.getParameter("cmd");
	//String atMenu = request.getParameter("atMenu");

%>


</head>
<body>
<div id="header">
<%
// via akademik menu
if(id_obj_mask==null || Checker.isStringNullOrEmpty(id_obj_mask)) {
	
	String target1 = Constants.getRootWeb()+"/InnerFrame/Akademik/IndexAkademikSubMenu0.jsp"; 
	String uri1 = request.getRequestURI(); 
	String url1 = PathFinder.getPath(uri1, target1);	
%>
	<jsp:include page="<%=url1 %>" />
<%	
}
else {

%>
	<%@ include file="../../innerMenu.jsp"%>
<%	
}

%>
<!-- include file="../IndexAkademikSubMenu.jsp" % -->

<!--  jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Akademik/IndexAkademikSubMenu0Mhs.jsp" /> -->
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		
		<%
		String idkur = null;
		if(infoKur!=null) {
			StringTokenizer st = new StringTokenizer(infoKur,"#&");
			idkur = st.nextToken();
			String nmkur = st.nextToken();
			String skstt = st.nextToken();
			String smstt = st.nextToken();
			String start = st.nextToken();
			String ended = st.nextToken();
			String targt = st.nextToken();
		%>
	    <br/>

	    <br/>    	
		<%	
		}
		if(vListMakul!=null && vListMakul.size()>0) {
			//String prev_sms = "";
			ListIterator li = vListMakul.listIterator();
			int sksem=0;
			if(li.hasNext()) {
				String brs = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs,"#&");
				String prev_semes=st.nextToken();
				String prev_kdpst=st.nextToken();
				String prev_kdkmk=st.nextToken();
				String prev_nakmk=st.nextToken();
				String prev_skstm=st.nextToken();
				String prev_skspr=st.nextToken();
				String prev_skslp=st.nextToken();
				String prev_skstt=st.nextToken();
				//prev_sms = ""+prev_semes;
				sksem = sksem+(Integer.valueOf(prev_skstt).intValue());
			%>
			<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:550px">
				<tr>
	        		<td style="background:#369;color:#fff;text-align:center" colspan="3"><label><B>SEMESTER <%=prev_semes %></B> </label></td>
	        	</tr>	
				<tr>
	        		<td style="background:#369;color:#fff;text-align:center"><label><B>KODE MK</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center"><label><B>MATAKULIAH</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center"><label><B>SKS</B> </label></td>
	        	</tr>
	        	<tr>
	        		<td style="color:#000;text-align:center"><label><B><%=prev_kdkmk %></B> </label></td>
	        		<td style="color:#000;text-align:center"><label><B>
	        			<a href="prep.bahanAjarGivenMk?kdpst_mask=<%=kdpst_mask %>&obj_lvl=<%=obj_lvl_mask %>&npm=<%=npm_mask %>&nmm=<%=nmm_mask %>&id_obj=<%=id_obj_mask%>&atMenu=mba&cmd=mba&nakmk=<%=prev_nakmk %>&kdkmk=<%=prev_kdkmk %>&kdpst=<%=prev_kdpst %>&idkur=<%=idkur %>"><%=prev_nakmk %></a></B> </label>
	        		</td>
	        		<td style="color:#000;text-align:center"><label><B><%=prev_skstt %></B> </label></td>
	        	</tr>	
			<%
				if(!li.hasNext()) {
				//cuma satu record
				//hitung total sks & tutup table
			%>
				<tr>
		      		<td style="background:#369;color:#fff;text-align:left" colspan="2"><label><B>TOTAL SKS</B> </label></td>
			   		<td style="background:#369;color:#fff;text-align:center"><label><B><%=sksem %></B> </label></td>
		      	</tr>
		    </table>
		    <%
				}
				else {
					while(li.hasNext()) {
						brs = (String)li.next();
						st = new StringTokenizer(brs,"#&");
						String semes=st.nextToken();
						String kdpst=st.nextToken();
						String kdkmk=st.nextToken();
						String nakmk=st.nextToken();
						String skstm=st.nextToken();
						String skspr=st.nextToken();
						String skslp=st.nextToken();
						String skstt=st.nextToken();
						if(prev_semes.equals(semes)) {
						//	prev_semes=""semes;
							sksem = sksem+(Integer.valueOf(skstt).intValue());
						%>
				<tr>
	        		<td style="color:#000;text-align:center"><label><B><%=kdkmk %></B> </label></td>
	        		<td style="color:#000;text-align:center"><label><B>
	        		<a href="prep.bahanAjarGivenMk?kdpst_mask=<%=kdpst_mask %>&obj_lvl=<%=obj_lvl_mask %>&npm=<%=npm_mask %>&nmm=<%=nmm_mask %>&id_obj=<%=id_obj_mask%>&atMenu=mba&cmd=mba&nakmk=<%=nakmk %>&kdkmk=<%=kdkmk %>&kdpst=<%=kdpst %>&idkur=<%=idkur %>"><%=nakmk %></a></B> </label>
	        		</B> </label></td>
	        		<td style="color:#000;text-align:center"><label><B><%=skstt %></B> </label></td>
	        	</tr>
						<%
						}
						else {		
							//pergantian thsms
							//tutup table & create new Table
						%>
				<tr>
		      		<td style="background:#369;color:#fff;text-align:left" colspan="2"><label><B>TOTAL SKS</B> </label></td>
			   		<td style="background:#369;color:#fff;text-align:center"><label><B><%=sksem %></B> </label></td>
		      	</tr>
		    </table>
		    <br/>
		    <br/>
		    <%
		    prev_semes = ""+semes;
		    sksem = (Integer.valueOf(skstt).intValue());
		    %>
		  	<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:550px">	
				<tr>
	        		<td style="background:#369;color:#fff;text-align:center" colspan="3"><label><B>SEMESTER <%=prev_semes %></B> </label></td>
	        	</tr>	
				<tr>
	        		<td style="background:#369;color:#fff;text-align:center"><label><B>KODE MK</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center"><label><B>MATAKULIAH</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center"><label><B>SKS</B> </label></td>
	        	</tr>
	        	<tr>
	        		<td style="color:#000;text-align:center"><label><B><%=kdkmk %></B> </label></td>
	        		<td style="color:#000;text-align:center"><label><B>
	        		<a href="prep.bahanAjarGivenMk?kdpst_mask=<%=kdpst_mask %>&obj_lvl=<%=obj_lvl_mask %>&npm=<%=npm_mask %>&nmm=<%=nmm_mask %>&id_obj=<%=id_obj_mask%>&atMenu=mba&nakmk=<%=nakmk %>&kdkmk=<%=kdkmk %>&kdpst=<%=kdpst %>&idkur=<%=idkur %>"><%=nakmk %></a></B> </label>
	        		</B> </label></td>
	        		<td style="color:#000;text-align:center"><label><B><%=skstt %></B> </label></td>
	        	</tr>
							<%
						}
						if(!li.hasNext()) {
				%>
				<tr>
		     		<td style="background:#369;color:#fff;text-align:left" colspan="2"><label><B>TOTAL SKS</B> </label></td>
			   		<td style="background:#369;color:#fff;text-align:center"><label><B><%=sksem %></B> </label></td>
		      	</tr>
		    </table>
					    <%
						}
					}	
				}	
			}
		%>
		</table>
		<%	
		}
		else {
			if(vListMakul==null) {
			%>
			<h2>BELUM MEMILIKI KURIKULUM</h2>
			<%
			}
		}
		%>
		
		<!-- Column 1 start -->
	</div>
</div>			
</body>
</html>