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
	Vector vFromGetScopeObjIdFinal = (Vector) session.getAttribute("vScopeObjId");
	//112 65001 MHS_S3_ILMU_PEMERINTAHAN 112 A JST
	session.removeAttribute("vScopeObjId");
	String back_to = request.getParameter("back_to");
	String fwd_to = request.getParameter("fwd_to");//tidak boleh kosong
	fwd_to = Constants.getRootWeb()+fwd_to;
%>

</head>
<body>

<div id="header">
	
	<ul>
		<li>
	<%
	if(back_to!=null && !Checker.isStringNullOrEmpty(back_to)) {
	%>
		<a href="<%=back_to%>" target="_self">GO<span>BACK</span></a>
	<%
	}
	else {
	%>
		<a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">GO<span>BACK</span></a> 
	<%	
	}	
	%>
		</li>	
	</ul>

</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		
		<form action="<%=fwd_to %>" target="_self" method="post">
			<table align="center" border="1" style="background:#d9e1e5;color:#000;width:300px;">
				<tr>
					<td align="center" bgcolor="#369" style="color:#fff" padding-left="2px"><b>PILIH PROGRAM STUDI</b></td>
				</tr>	
				<tr>	
					<td align="left" width="100px" style="padding-left:2px">
						<select name="kdpst_nmpst_kmp" style="width:100%">
				<%
	ListIterator liSc = vFromGetScopeObjIdFinal.listIterator();
	while(liSc.hasNext()){
		String baris = (String)liSc.next();
					//System.out.println("++"+baris);
		StringTokenizer st = new StringTokenizer(baris);
		String idObj = st.nextToken();
    	String kdpst = st.nextToken();
    	String nmpst = st.nextToken().replace("MHS_", "");
    	String obLvl = st.nextToken();
    	String kdjen = st.nextToken();
    	String kodeKampus = null; 
    				//peralihan ada yg ngga ada tkn kodekampus
    	if(st.hasMoreTokens()) {
    		kodeKampus = st.nextToken();
   		}
    	else {
    		kodeKampus = null;
    	}
	
		nmpst = nmpst.replaceAll("MHS", "");
		nmpst = nmpst.replaceAll("_", " ");

		%>
							<option value="<%=kdpst %>`<%=nmpst %>`<%=kodeKampus %>"><%=nmpst %></option>
						<%
				}			
				%>
						</select>
					</td>
				</tr>	
					
				<tr>
					<td align="right" bgcolor="#369"><input type="submit" value="Next" style="width:100px;" /></td>
				</tr>
			</table>
			
		</form>	

	</div>
</div>		
</body>
</html>