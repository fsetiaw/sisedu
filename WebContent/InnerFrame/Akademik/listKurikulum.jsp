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
	request.setAttribute("atPage", "listKurikulum");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String kdpst_nmpst = request.getParameter("kdpst_nmpst");
	String idkur_ = ""+request.getParameter("idkur_");
	StringTokenizer st = new StringTokenizer(kdpst_nmpst,",");
	String kdpst = st.nextToken();
	String nmpst = "";
	while(st.hasMoreTokens()) {
		nmpst = nmpst+st.nextToken();
		if(st.hasMoreTokens()) {
			nmpst = nmpst+" ";
		}
	}
	SearchDb sdb = new SearchDb();
	boolean allowEditListKur = false;
	String callerPage = request.getParameter("callerPage");
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
		allowEditListKur = true;
	%>
		<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Akademik/innerMenu.jsp" />
	<%
	}
	%>
	</ul>
	
</div>

<div class="colmask fullpage">
	<div class="col1">
	<section style="text-align:center;background:#eee;font-size:2em;color:#369;font-weight:bold">
		TAMBAH / EDIT KURIKULUM
	</section>
		<br />
		<!-- Column 1 start -->
		<p>
		<form action="get.listKurikulum">
		<input type="hidden" value="<%=kdpst %>,<%=nmpst %>" name="kdpst_nmpst" />
		<%
		
		%>
		<input type="hidden" name="idkur" value="<%=idkur_ %>" />
		<table align="center" border="1" style="color:#000;width:800px;background:#d9e1e5;">	
			<tr>
				<td style="background:#369;color:#fff;text-align:center;font-size:1.5em" colspan="4"><b>FORM TAMBAH / EDIT KURIKULUM <%=Converter.getNamaKdpst(kdpst) %></b></td>
			</tr>
			<tr>
				<td width="35%">NAMA/KODE KURIKULUM</td>	
				<%
					String kdkur_1 = request.getParameter("kdkur_1");
					if(kdkur_1!=null) {
				%>	
				<td colspan="3"><input type="text"  name="kdkur" value="<%=kdkur_1 %>" style="width:99%"/></td>
				<%		
					}
					else {
				%>	
				<td colspan="3"><input type="text"  name="kdkur" value="" style="width:99%"/></td>
				<%
					}
				%>
			</tr>
			<tr>
				<td width="35%">KETERANGAN KONSENTRASI</td>	
				<%
					String konsen_1 = request.getParameter("konsen_1");
					if(!Checker.isStringNullOrEmpty(konsen_1)) {
				%>	
				<td colspan="3"><input type="text"  name="konsen" value="<%=Checker.pnn(konsen_1)%>" style="width:99%"/></td>
				<%		
					}
					else {
				%>	
				<td colspan="3"><input type="text"  name="konsen" placeholder="Diisi bila prodi memiliki beberapa konsentrasi (optional)" value="" style="width:99%"/></td>
				<%
					}
				%>
			</tr>
			<tr>
				<td width="35%">THSMS AWAL KURIKULUM BERLAKU</td>	
				<%
					String thsms_1 = request.getParameter("thsms_1");
					if(thsms_1!=null) {
				%>
				<td width="15%"><input type="text"  name="thsms1" value="<%=thsms_1 %>" style="width:98%"/></td>
				<%		
					}
					else {
				%>
				<td width="15%"><input type="text"  name="thsms1" value="" style="width:98%"/></td>
				<%
					}
				%>				
				
				<td width="35%">THSMS AKHIR KURIKULUM BERLAKU</td>		
				<%
					String thsms_2 = request.getParameter("thsms_2");
					if(thsms_2!=null) {
				%>		
				<td width="15%"><input type="text"  name="thsms2" value="<%=thsms_2 %>" style="width:98%"/></td>
				<%
					}
					else {
				%>		
				<td width="15%"><input type="text"  name="thsms2" value="" style="width:98%"/></td>
				<%		
					}
				%>
			</tr>
			<tr>
				<td style="background:#369;color:#fff;text-align:right;" colspan="4"><input type="submit" value="Next" style="width:100px;"/></td>
			</tr>
		</table>
		</form>
		</p>
				<br/>
		<p>
		<%
			Vector vKrklm = (Vector)request.getAttribute("vkrklm");
		//System.out.println("vKrklm = ");
		//System.out.println("vKrklm = "+vKrklm.size());
			if(vKrklm!=null && vKrklm.size()>0) {
				ListIterator li = vKrklm.listIterator();
				if(li.hasNext()){
					//SearchDb sdb = new SearchDb();
					int i=0;		
			%>
			<table align="center" border="1" style="color:#000;width:800px;background:#d9e1e5;">	
			
			<tr>
				<td style="background:#369;color:#fff;text-align:center;font-size:1.5em" colspan="8"><b>LIST KURIKULUM <%=nmpst %></b></td>
			</tr>
			<tr>
				<td style="width:5%;text-align:center;background:#87B1DA"><b>NO.</b></td>
				<td style="width:55%;;background:#87B1DA"><b>NAMA / KODE KURIKULUM</b></td>
				<td style="width:15%;text-align:center;background:#87B1DA"><b>THSMS AWAL</b></td>
				<td style="width:15%;text-align:center;background:#87B1DA"><b>THSMS AKHIR</b></td>
				<td style="width:10%;text-align:center;background:#87B1DA"><b>STATUS</b></td>
			</tr>
			<%
			
					while(li.hasNext()) {
						String baris = (String)li.next();
						//System.out.println("baris ="+baris);
						st = new StringTokenizer(baris,",");
						String idkur1 = st.nextToken();
						String kdkur1 = st.nextToken();
						String stkur1 = st.nextToken();
						
						if(stkur1.equalsIgnoreCase("null")) {
							stkur1 = "";
						}
						String thsms11 = st.nextToken();;
						if(thsms11.equalsIgnoreCase("null")) {
							thsms11 = "";
						}
						String thsms12 = st.nextToken();
						if(thsms12.equalsIgnoreCase("null")) {
							thsms12 = "";
						}
						
						String konsen = st.nextToken();
						
						//li.add(idkur+","+kdkur+","+stkur+","+thsms1+","+thsms2);
			%>		
						<tr>
						<form action="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Akademik/listKurikulum.jsp">
						<input type="hidden" name="idkur_" value="<%=""+idkur1 %>" />
						<td style="width:5%;text-align:center"><b><%=++i %><input type="hidden" value="<%=kdpst_nmpst%>" name="kdpst_nmpst"/></b></td>
						<td style="width:55%;text-align:left"><b>
						<%
						out.print(kdkur1);
						if(!Checker.isStringNullOrEmpty(konsen)) {
							out.print("<br/>Konsentrasi "+konsen);
						}
						%><input type="hidden" value="<%=kdkur1%>" name="kdkur_1"/></b></td>
						<td style="width:15%;text-align:center"><b><%=thsms11 %><input type="hidden" value="<%=thsms11%>" name="thsms_1" /></b></td>
						<td style="width:15%;text-align:center"><b><%=thsms12 %><input type="hidden" value="<%=thsms12%>" name="thsms_2" /></b></td>
						<input type="hidden" value="<%=konsen%>" name="konsen_1" />
						<td style="width:10%;text-align:center"><b><%=stkur1 %><input type="hidden" value="<%=stkur1%>" name="stkur_1" /></b></td>
			<%
					if(allowEditListKur==true) {
			%>
						<td style="background:#87B1DA"><input type="submit" value="edit" /></td>		
			<%	
					}
			%>
					</form>
			<%
					Vector vHapusKur = validUsr.getScopeUpd7des2012("allowHapusKurikulum");
					if(vHapusKur!=null && vHapusKur.size()>0) {
						%>
						<form action="go.hapusKurikulum">
						<input type="hidden" name="idkur_" value="<%=""+idkur1 %>" />
						<input type="hidden" value="<%=kdpst_nmpst%>" name="kdpst_nmpst"/>
						<td style="background:#87B1DA"><input type="submit" value="Hapus" /></td>
						</form>		
			<%	
					}
			%>
			
						</tr>
						
			<%
					}
				}
			%>
			</table>
			<%	
			}		
			%>
			
	</div>
</div>		
</body>
</html>