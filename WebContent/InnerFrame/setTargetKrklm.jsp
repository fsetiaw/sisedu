<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
//	String callerPage = request.getParameter("callerPage");
	//System.out.println("@settargetkurikul");
	String thisPage = request.getRequestURI();
	//String cmd = ""+request.getParameter("cmd");
	Vector vListKurikulum = (Vector) request.getAttribute("vListKurikulum");
	//System.out.println("vListKurikulum size ="+vListKurikulum.size());
	String kdpst_nmpst = ""+request.getAttribute("kdpst_nmpst");
	//System.out.println(kdpst_nmpst);
	request.removeAttribute("kdpst_nmpst");
	String cmd = ""+request.getParameter("cmd");
	String atMenu = ""+request.getParameter("atMenu");
	String scope = request.getParameter("scope");
	String kelasTambahan = (String)request.getParameter("kelasTambahan");
	String kodeKampus = (String)request.getParameter("kodeKampus");
	
	String id_obj_mask = request.getParameter("id_obj");
	String nmm_mask = request.getParameter("nmm");
	String npm_mask = request.getParameter("npm");
	String obj_lvl_mask = request.getParameter("obj_lvl");
	String kdpst_mask = request.getParameter("kdpst");
	
%>


</head>
<body>
<div id="header">
	<%
	Vector vSc = validUsr.getScopeUpd7des2012("hasAkademikMenu");
	boolean match = false;
	if(vSc!=null && vSc.size()>0) {
		//System.out.println("vScin");
		if(cmd.equalsIgnoreCase("viewKurikulum")) {
			match = true;
	%>
	<ul>
		<li><a href="get.listScope?scope=allowViewKurikulum&callerPage=<%=Constants.getRootWeb()%>/InnerFrame/Akademik/indexAkademik.jsp&cmd=viewKurikulum&scopeType=prodiOnly" target="_self">GO<span>BACK</span></a></li>
	</ul>
	<%		
		}
		else if(!match && cmd.equalsIgnoreCase("mba")) {
			match = true;
			
	%>
	<ul>
		<li><a href="get.listScope?scope=mba&callerPage=<%=Constants.getRootWeb()%>/InnerFrame/Akademik/indexAkademik.jsp&atMenu=mba&cmd=mba&scopeType=prodiOnly" target="_self">GO<span>BACK</span></a></li>
	</ul>
	<%
		}
		else if(!match && cmd.equalsIgnoreCase("bukaKelas")) {
			match = true;
			
	%>
	<ul>
		<li><a href="get.listScope?cmd=<%=cmd %>&atMenu=<%=atMenu %>&scope=<%=scope %>&callerPage=<%=Constants.getRootWeb()%>/InnerFrame/Akademik/indexAkademik.jsp&cmd=bukaKelas&scopeType=prodiOnly" target="_self">GO<span>BACK</span></a></li>
	</ul>
	<%
		}
		else if(!match) {
	%>
	<ul>
		<li><a href="get.listScope?callerPage=<%=Constants.getRootWeb()%>/InnerFrame/Akademik/indexAkademik.jsp&cmd=viewKurikulum" target="inner_iframe">GO<span>BACK</span></a></li>
	</ul>
	<%					
				
				
		}
	}	
	%>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->

		<%
		//lif.add(idkur+"#&"+nmkur+"#&"+stkur+"#&"+start+"#&"+ended+"#&"+targt);
		if(vListKurikulum.size()>0) {
			ListIterator liSc = vListKurikulum.listIterator();
			
			//if(!tmp.equalsIgnoreCase("own")){
			if(vSc.size()>0) {
				//System.out.println("00.cmd="+cmd+",atMenu="+atMenu+",scope="+scope);
			
		%>
			<form action="go.prepKurikulumForViewing" target="_self" >
			<input type="hidden" name="kdpst_nmpst" value="<%=kdpst_nmpst %>" />
			<input type="hidden" name="cmd" value="<%=cmd %>" />
			<input type="hidden" name="atMenu" value="<%=atMenu %>" />
			<input type="hidden" name="scope" value="<%=scope %>" />
			<input type="hidden" name="kodeKampus" value="<%=kodeKampus %>" />
			<input type="hidden" name="id_obj" value="<%=id_obj_mask %>" />
			<input type="hidden" name="nmm" value="<%=nmm_mask %>" />
			<input type="hidden" name="npm" value="<%=npm_mask %>" />
			<input type="hidden" name="obj_lvl" value="<%=obj_lvl_mask %>" />
			<input type="hidden" name="kdpst" value="<%=kdpst_mask %>" />
 			<table align="center" border="1" style="background:#369;color:#d9e1e5;">	
				<tr>
					<td align="center" bgcolor="#d9e1e5" style="color:#369" padding-left="2px"><b>PILIH KURIKULUM</b></td>
				</tr>	
				<tr>	
					<td align="left" width="100px" style="padding-left:2px">
						<select name="infoKur">
				<%
				while(liSc.hasNext()) {
					String baris = (String)liSc.next();
					//System.out.println("00baris="+baris);
					StringTokenizer st = new StringTokenizer(baris,"#&");
					//lif.add(idkur+"#&"+nmkur+"#&"+stkur+"#&"+start+"#&"+ended+"#&"+targt+"#&"+skstt+"#&"+smstt);
					String idkur = st.nextToken();
					String nmkur = st.nextToken();
					String stkur = st.nextToken();
					String start = st.nextToken();
					if(start.equalsIgnoreCase("null")) {
						start = "N/A";
					}
					String ended = st.nextToken();
					if(ended.equalsIgnoreCase("null")) {
						ended = "N/A";
					}
					String targt = st.nextToken();
					String skstt = st.nextToken();
					String smstt = st.nextToken();
				%>
							<option value="<%=idkur+"#&"+nmkur+"#&"+skstt+"#&"+smstt+"#&"+start+"#&"+ended+"#&"+targt%>"><%=nmkur %>(<%=skstt %> sks / <%=smstt %> sms) (<%=start %> s/d <%=ended %>)</option>
				<%	
				}	
				%>
						</select>
					</td>
				</tr>	
				<%
				
				if(cmd.equalsIgnoreCase("bukaKelas")&&kelasTambahan.equalsIgnoreCase("yes")) {
				%>
				<tr>	
					<td align="center" width="100px" style="padding-left:2px">KELAS TAMBAHAN<input type="hidden" name="kelasTambahan" value="<%=kelasTambahan%>"></td>
				</tr>		
				<%	
				}
				
				%>
				<tr>
					<td align="right" bgcolor="#d9e1e5"><input type="submit" value="Next" style="width:100px;" /></td>
				</tr>
			</table>
			</form>	
		<%
			}
			else {
				//jsp ini kepanggil onli vectr > 1, semua diatur di servlet Get.ListKurikulum.java
				System.out.println("ada error di ListKurikulum untuk Vector = null / size=0 / size=1");
						
			}
		}	
		%>
		
	</div>
</div>		
</body>
</html>