<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.SearchDb" %>
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
	//System.out.println("masuk kesnin");
	request.setAttribute("atPage", "editTargetKurikulum");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String kdpst_nmpst = request.getParameter("kdpst_nmpst");
	String idkur_ = ""+request.getParameter("idkur");
	StringTokenizer st = new StringTokenizer(kdpst_nmpst,",");
	String kdpst = st.nextToken();
	String nmpst = "";
	while(st.hasMoreTokens()) {
		nmpst = nmpst+st.nextToken();
		if(st.hasMoreTokens()) {
			nmpst = nmpst+" ";
		}
	}
	//SearchDb sdb = new SearchDb();
	boolean allowEditListKelas = false;
	String callerPage = request.getParameter("callerPage");
	//System.out.println("start-1");
	SearchDb sdb = new SearchDb();
	//System.out.println("start-2");
	Vector vInfoMakur = sdb.getListInfotMakur(kdpst);
	//System.out.println("start-3");

%>


</head>
<body>
<div id="header">
	<ul>
	<%
	
	if(callerPage!=null && !Checker.isStringNullOrEmpty(callerPage) && (!callerPage.equalsIgnoreCase(request.getRequestURI()))) {
		%>
			<li><a href="<%=callerPage%>" target="inner_iframe">GO<span>BACK</span></a></li>
		<%
	}
	
	Vector vEditMkKur = validUsr.getScopeUpd7des2012("hasEditMkKurikulum");
	if(vEditMkKur.size()>0) {
		allowEditListKelas = true;
	%>
		<!-- %@ include file="innerMenu.jsp"% -->
		<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Akademik/innerMenu.jsp" />
	<%
	}
	%>
	</ul>
</div>

<div class="colmask fullpage">
	<div class="col1">
	<section style="text-align:center;background:#eee;font-size:2em;color:#369;font-weight:bold">
		EDIT TARGET / USER KURIKULUM <br/> PROGRAM <%=nmpst.toUpperCase() %>
	</section>
		<br />
		<!-- Column 1 start -->
		<br />
		<p>
		
		<%
		if(idkur_==null || idkur_.equalsIgnoreCase("null")) {
		%>
			<%
			ListIterator li=vInfoMakur.listIterator();
			if(li.hasNext()) {	
				while(li.hasNext()) {
					String baris1 = (String)li.next();
					st = new StringTokenizer(baris1);
					String idkur = st.nextToken();
					String start = st.nextToken();
					String ended = st.nextToken();
					String nmkur = (String)li.next();
					//System.out.println(baris1);
					//System.out.println(nmkur);
					Vector vInfo = (Vector)li.next();
					String ttkls = (String)li.next();
					String skstt = (String)li.next();
					String target_ang="";
					String target_mhs="";
					//System.out.println("start-4");
			
					sdb = new SearchDb();
					Vector vAngMhs = sdb.getListTargetKurikulum(idkur);
					//System.out.println("start-5");
					if(vAngMhs!=null && vAngMhs.size()>0) {
						ListIterator lia = vAngMhs.listIterator();
						target_ang = ""+(String)lia.next();
						target_ang = target_ang.replaceAll("null","");
						target_mhs = ""+(String)lia.next();
						target_mhs = target_mhs.replaceAll("null","");
					}
					
		%>
			<form action="update.updTargetKurikulum">
			<input type="hidden" name="idkur" value="<%=idkur %>" /><input type="hidden" value="<%=kdpst_nmpst%>" name="kdpst_nmpst"/>
			<table align="center" border="1" style="color:#000;width:800px;background:#d9e1e5;">	
			<tr>
				<td style="width:50%;text-align:left;background:#369;color:#fff;font-weight:bold">NAMA/KODE KURIKULUM (total matakuliah / sks)</td>	
				<td style="width:50%;text-align:center;font-weight:bold"><%=nmkur.toUpperCase()%> (<%=ttkls %> mk/<%=skstt %> sks)</td>	
			</tr>
			<tr>
				<td style="text-align:left;background:#87B1DA;;font-weight:bold" colspan="2">TARGET ANGKATAN (jika lebih dari satu, gunakan koma (,) - contoh:20101,20111,...)</td>
			</tr>
			<tr>
				<td style="text-align:left;" colspan="2"><textarea style="width:99%;height:100%" name="target_angkatan"><%=target_ang %></textarea></td>
			</tr>
			<tr>
				<td style="text-align:left;background:#87B1DA;font-weight:bold" colspan="2">TARGET MAHASISWA DILUAR ANGKATAN DIATAS (bila ada, gunakan koma (,) bila lebih dari satu mahasiswa)</td>
			</tr>
			<tr>
				<td style="text-align:left;" colspan="2"><textarea style="width:99%;height:100%" name="target_mhs"><%=target_mhs %></textarea></td>
			</tr>	
			<tr>
				<td style="text-align:right;background:#369" colspan="2"><input type="submit" value="update target user"></td>
			</tr>	
			</table>
			</form>
			<br />
			<br />
		<%	
				}
			}
			else {
			%>
			<table align="center" border="1" style="color:#000;width:800px;background:#d9e1e5;">	
			<tr>
				<td colspan="3">TIDAK ADA KURIKULUM AKTIF UNTUK SAAT INI</td>	
			</tr>
			</table>
			<br />
			<br />
		<%	
			}
		}
		%>	
		</p>
	</div>
</div>		
</body>
</html>