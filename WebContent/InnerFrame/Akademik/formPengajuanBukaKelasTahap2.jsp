<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"> </script>
<script type="text/javascript">
$(document).ready(function()
{
	$("#somebutton").click(function()	
	{
		//$.get('go.uploadFile2', function(responseText) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
            //             // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
        $.post('process.validasiFormBukaKelasTahap2', $('#formUpload1').serialize(), function(data) {
        	document.getElementById('div_msg').style.visibility='visible';
        	$('#div_msg').html(); 
        	$('#div_msg').html(data); 
        });
	});

});			
</script>

<%
/*
* viewKurikulumStdTamplete (based on)
*/
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
 	

	String err_msg = request.getParameter("errmsg");
	if(err_msg==null || Checker.isStringNullOrEmpty(err_msg)) {
		err_msg="";
	}
	String[] klsInfo = request.getParameterValues("klsInfo"); 
	String[] totKls = request.getParameterValues("totKls");
	if(klsInfo==null) {
		klsInfo = (String[])session.getAttribute("klsInfo");
		totKls = (String[])session.getAttribute("totKls");
	}
	else {
		session.setAttribute("klsInfo",klsInfo);
		session.setAttribute("totKls",totKls);
	}	
	String infoKur = request.getParameter("infoKur");
	String kdjen = request.getParameter("kdjen");
	String kdpst_nmpst = request.getParameter("kdpst_nmpst");
	String kelasTambahan = request.getParameter("kelasTambahan");
	String kodeKampus = request.getParameter("kodeKampus");
	String kdpst = null;
	String nmpst = null;
	if(kdpst_nmpst!=null) {
		StringTokenizer st = new StringTokenizer(kdpst_nmpst,",");
		kdpst= st.nextToken();
		nmpst= st.nextToken();
	}
	Vector vDos = Checker.getListDosenPengajar(kdpst);
	ListIterator liDos = vDos.listIterator();
	
	boolean atFormPengajuanBukaKelasTahap1 = false;
	boolean atFormPengajuanBukaKelasTahap2 = true;
	String backward2 ="go.prepFormRequestBukaKelas?cmd=bukakelas&atMenu=bukakelas&scope=reqBukaKelas&kdpst_nmpst="+kdpst_nmpst+"&infoKur="+infoKur;
%>

<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
</head>
<body>
<div id="header">
<%@ include file="IndexAkademikPengajuanSubMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<%
	String kdkmk = null,nakmk=null,shift=null;
	if(totKls!=null && totKls.length>0) {
		JSONArray jsoa = Getter.readJsonArrayFromUrl("/v1/search_dsn/status/A");//get dosen aktif
			
		%>
	<!--  form action="formPengajuanBukaKelasTahap3.jsp" method="post" -->
	
	<!--  form action="process.validasiFormBukaKelasTahap2" method="post" -->	
	<form name="formUpload1" id="formUpload1" method="post">
	<input type="hidden" name="kelasTambahan" value="<%=kelasTambahan %>" />
	<input type="hidden" name="kodeKampus" value="<%=kodeKampus %>" />  
	<input type="hidden" name="infoKur" value="<%=infoKur %>" />
	<input type="hidden" name="kdpst_nmpst" value="<%=kdpst_nmpst %>" />
	<input type="hidden" name="kdjen" value="<%=kdjen %>" />
	<%
	if(!Checker.isStringNullOrEmpty(err_msg)) {
	%>
	<p style="color:red;font-style:italic;text-align:center"><%=err_msg%></p>
	<%	
	}
	
	%>
	<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:750px">
		
		<tr>
			<td style="background:#369;color:#fff;text-align:center;" colspan="4"><label><B>FORM PENGISIAN DOSEN PENGAJAR</B> </label></td>
		</tr>
		<tr>
	        <td style="background:#369;color:#fff;text-align:center;width:100px"><label><B>KODE MK</B> </label></td>
	        <td style="background:#369;color:#fff;text-align:center;width:265px"><label><B>MATAKULIAH</B> </label></td>
	        <td style="background:#369;color:#fff;text-align:center;width:100px"><label><B>SHIFT</B> </label></td>
	        <td style="background:#369;color:#fff;text-align:center;width:285px"><label><B>NO URUT KELAS / DOSEN</B> </label></td>     
	    </tr>
		<%	
		for(int i=0;i<totKls.length;i++) {
			if(!Checker.isStringNullOrEmpty(totKls[i]) && Integer.valueOf(totKls[i]).intValue()>0) {
				StringTokenizer st = new StringTokenizer(klsInfo[i],"||");
				kdkmk = st.nextToken();
				nakmk = st.nextToken();
				shift = st.nextToken();
				//out.print(klsInfo[i]+" "+totKls[i]+"<br/>");
		%>
		<tr>
	        		<td style="color:#000;text-align:center"><label><B><%=kdkmk %></B> </label></td>
	        		<td style="color:#000;text-align:left"><label><B><%=nakmk %></B> </label></td>
	        		<td style="color:#000;text-align:center"><label><B><%=shift %></B> </label></td>
	        		<td style="color:#000;text-align:left"><B>
	        		<%
	        		for(int j=1;j<=Integer.valueOf(totKls[i]).intValue();j++) {
	        			//String tmp = nodos+"||"+nidn+"||"+noKtp+"||"+kdptiHome+"||"+kdpstHome+"||"+nmdos+"||"+gelar+"||"+smawl+"||"+kdpstAjar+"||"+email+"||"+tknTelp+"||"+tknHp+"||"+status;
	        			
	        		%>
	        			&nbsp<%=j %>.&nbsp
	        			
	        			<select name="infoKelasDosen" style="width:90%"/>
	        				<!--  option value="<%= klsInfo[i]%>||<%=j %>||tba||tba">Belum Ditentukan</option -->
	        		<%	
	        		//digandi dengan json
	        		/*
	        			if(vDos!=null) {
	        				ListIterator li = vDos.listIterator();
	        				while(li.hasNext()) {
	        					String brs = (String)li.next();
	        					st = new StringTokenizer(brs,"||");
	        					String nodos = st.nextToken();
	        					String nidn = st.nextToken();
	        					String noKtp = st.nextToken();
	        					String kdptiHome = st.nextToken();
	        					String kdpstHome = st.nextToken();
	        					String nmdos = st.nextToken();
	        					String gelar = st.nextToken();
	        					String smawl = st.nextToken();
	        					String kdpstAjar = st.nextToken();
	        					String email = st.nextToken();
	        					String tknTelp = st.nextToken();
	        					String tknHp = st.nextToken();
	        					String status= st.nextToken();
	        			*/
	        			if(jsoa!=null && jsoa.length()>0) {
		        			for(int k=0;k<jsoa.length();k++) {
		        				JSONObject job = jsoa.getJSONObject(k);	
		        				String nodos = job.getString("NPMHS");
		        				String nmdos = job.getString("NMMHSMSMHS");
	        					%>
	        					<option value="<%= klsInfo[i]%>||<%=j %>||<%=nodos%>||<%=nmdos%>"><%=nmdos %></option>
	        					<%
	        				}
	        			}
	        		%>
	        			</select>
	        			<br/>
	        			&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Jumalah Target Mhs:&nbsp&nbsp<input type="number" name="infoKelasMhs" value="1" style="text-align:center;width:39px"/>
	        			<br/>
	        		<%
	        		}
	        		%>
	        		</B>
	        		
	        		
	    </tr>	
		<%		
			}
		}
	%>
	</table>
	<br/>
		<div style="text-align:center"><input type="button" id="somebutton" name="somebutton" value="Next" style="width:500px;height:35px;font-weght:bold;margin:5px"/></div>
	</form>
	<%	
	}
	else {
			
	}
		%>
		<br/>
		<div id="div_msg" style="font-style:italic;color:red;text-align:left;background-color:#d9e1e5;width:750px;height:100%;visibility:hidden;margin:0px 0 0 132px;" ></div>

		    
		<!-- Column 1 start -->
	</div>
</div>			
</body>
</html>