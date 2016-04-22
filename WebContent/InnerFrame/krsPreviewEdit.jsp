<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<%@page import="java.util.Vector" %>
<%@page import="java.util.ListIterator" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
/*
String msg = request.getParameter("msg");
String objId = request.getParameter("id_obj");
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String kdpst = request.getParameter("kdpst");
String obj_lvl =  request.getParameter("obj_lvl");
*/
String thsms = request.getParameter("prev_thsms");
if(thsms!=null&&thsms.length()>5) {
	thsms = thsms.substring(0,4)+thsms.substring(5,6);
}
System.out.println("preview "+thsms);
String kdkmk =  request.getParameter("kdkmk");
String nakmk =  request.getParameter("nakmk");
String nlakh =  request.getParameter("nlakh");
String bobot =  request.getParameter("bobot");
String sksmk =  request.getParameter("sksmk");
String krs_pindahan =  ""+request.getParameter("krs_pindahan");
Vector v = null;
String tdBgColor = "#369";
String tableBgColoe = "#d9e1e5";
String txtColor = "#000";
if(krs_pindahan.equalsIgnoreCase("krs_pindahan")) {
	tdBgColor = "#29A329";
	tableBgColoe = "#D6EB99";
	txtColor = "#000";
}
//System.out.println("msg disini= "+msg);
%>
<script language="Javascript">

function editData()
{
    document.Form1.action = "proses.editKrsKhs"
    document.Form1.target = "_self";    // Open in a new window
    document.Form1.submit();             // Submit the page
    return true;
}

function hapusData()
{
    document.Form1.action = "proses.hapusKrsKhs"
    document.Form1.target = "_self";    // Open in a new window
    document.Form1.submit();             // Submit the page
    return true;
}

</script>
<noscript>You need Javascript enabled for this to work</noscript>


</head>
<body onload="location.href='#'">
<div id="header">
<%@ include file="krsKhsSubMenu.jsp"%>

</div>

<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
<%
	System.out.println("okes");
%>
	 <form action="" name="Form1">
	 	<input type="hidden" value="<%=krs_pindahan %>" name="krs_pindahan" />
	 	<input type="hidden" value="<%=thsms %>" name="thsms" />
	 	<input type="hidden" value="histKrs" name="cmd" />
	 	<input type="hidden" value="<%=objId %>" name="id_obj" />
	 	<input type="hidden" value="<%=nmm %>" name="nmm" />
	 	<input type="hidden" value="<%=npm %>" name="npm" />
	 	<input type="hidden" value="<%=kdpst %>" name="kdpst" />
	 	<input type="hidden" value="<%=obj_lvl %>" name="obj_lvl" />
		<table align="center" border="1px" bordercolor="<%=tdBgColor %>" style="background:<%=tableBgColoe %>;color:<%=txtColor %>;width:700px">
		<tr>
        	<td style="background:<%=tdBgColor %>;color:#fff;text-align:center;width:60px"><label><B>KODE</B> </label></td>
        	<td style="background:<%=tdBgColor %>;color:#fff;text-align:left;width:495px"><label><B>MATAKULIAH</B> </label></td>
        	<td style="background:<%=tdBgColor %>;color:#fff;text-align:center;width:55px"><label><B>NILAI</B> </label></td>
        	<%
        	if(krs_pindahan.equalsIgnoreCase("krs_pindahan")) {
        	%>
        	<td style="background:<%=tdBgColor %>;color:#fff;text-align:center;width:55px"><label><B>BOBOT</B> </label></td>
        	<%
        	}
        	%>
        	<td style="background:<%=tdBgColor %>;color:#fff;text-align:center;width:35px"><label><B>SKS</B> </label></td>
        </tr>
        <tr>
        	<td style="color:<%=txtColor %>;text-align:center;"><label><B><%=kdkmk %><input type="hidden" value="<%=kdkmk %>" name="kdkmk" /></B> </label></td>
        	<td style="color:<%=txtColor %>;text-align:left;"><label><B><%=nakmk %><input type="hidden" value="<%=nakmk %>" name="nakmk" /></B> </label></td>
        	<td style="color:<%=txtColor %>;text-align:center;"><label><B><input type="text" value="<%=nlakh %>" name="nlakh" style="text-align:center"/></B> </label></td>
        	<%
        	if(krs_pindahan.equalsIgnoreCase("krs_pindahan")) {
        	%>
        	<td style="color:<%=txtColor %>;text-align:center;"><label><B><input type="text" value="<%=bobot %>" name="bobot" style="text-align:center"/></B> </label></td>
        	<%
        	}
        	%>
        	<td style="color:<%=txtColor %>;text-align:center;"><label><B><%=sksmk %><input type="hidden" value="<%=sksmk %>" name="sksmk" /></B> </label></td>
        </tr>
        </table> 
        <table align="center" border="1px" bordercolor="<%=tdBgColor %>" style="background:<%=tdBgColor %>;color:<%=txtColor %>;width:700px">
        <tr>
        	<td style="background:<%=tdBgColor %>;color:#fff;text-align:left;" colspan="2"><input type="submit" value="HAPUS DATA" onclick="hapusData();" style="font-weight:bold;color:RED;"/>
			<%
        	if(krs_pindahan.equalsIgnoreCase("krs_pindahan")) {
        	%> 
   			<td style="background:<%=tdBgColor %>;color:#fff;text-align:right" colspan="3"><input type="submit" value="UPDATE DATA" onclick="editData();"/></td>
			<%
			}
			%>   		
   		</tr>
   		</table>
   	</form>		
	</div>
</div>		
</body>
</html>