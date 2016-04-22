<!DOCTYPE html>
<html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<title>Insert title here</title>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>	
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/css/progressBar/normalize.css">
<link rel="stylesheet" href="<%=Constants.getRootWeb() %>/css/progressBar/style.css">
<script src="<%=Constants.getRootWeb() %>/js/jquery.js" type="text/javascript"></script>
<script src="<%=Constants.getRootWeb() %>/js/modernizr.js" type="text/javascript"></script>
<%
//System.out.println("siop");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v = null;
//long millis = System.currentTimeMillis();
%>


<script type="text/javascript">
	var objId="<%=""%>";
	var nmm="<%=""%>";
	var npm="<%=""%>";
	var obj_lvl="<%=""%>";
	var kdpst="<%=""%>";

	
	$(document).ready(function()
	{
		
		
		$("#buttonPart1").click(function()
		//$("#formUpload1").bind("submit",function()
				//$("#formUpload1").submit(function (event) 	
		{
			$.post( 'go.validateFormTamplete', $('#formUpload1').serialize(), function(data) {
				if(data && data !="") {
					document.getElementById('div_msg').style.visibility='visible';
					
					$('#div_msg').html(); 
					$('#div_msg').html(data); 
				}
				
			});
					
		});	
		
	});	
</script>      
</head>
<body>
<div id="header">
<%@ include file="innerMenu.jsp"%>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		
		<!-- Column 1 start -->
<% 
//out.println("milis="+millis);
//session.setAttribute("target_npm", v_npmhs);
Vector vTipeUa= Getter.getListJenisMakulUjian(kdpst); 
if(vTipeUa==null || vTipeUa.size()<1) {
	out.print("belum ada jenis");
}
else {
	//Vector vInfo_jenis_makul = Getter.getListJenisMakul();
	//format(id+"`"+keter+"`"+kode);
	
			//System.out.println("id_obj="+v_id_obj);
			//System.out.println("v_nmmhs="+v_nmmhs);
			//System.out.println("npm="+v_npmhs);
			//System.out.println("v_obj_lvl="+v_obj_lvl);
			//System.out.println("kdpst="+kdpst);
			//System.out.println("cmd="+cmd);
%>
	<!--  form id="form1" enctype="multipart/form-data" method="post" action="go.processUploadFileTamplete" -->
	<form id="formUpload1"  enctype="multipart/form-data">
		<input type="hidden" name="StringfwdPageIfValid_String_Opt" value="DashUjianAkhir_part2A.jsp" />
		<input type="hidden" name="npmhs_String_Opt" id="npmhs_String_Opt" value="<%=v_npmhs %>" />
		<input type="hidden" name="kdpst_String_Opt" id="npmhs_String_Opt" value="<%=v_kdpst %>" />
		<input type="hidden" name="idobj_String_Opt" id="idobj_String_Opt" value="<%=v_id_obj %>" />
		<input type="hidden" name="nmmhs_String_Opt" id="nmmhs_String_Opt" value="<%=v_nmmhs %>" />
		<input type="hidden" name="objlv_String_Opt" id="objlv_String_Opt" value="<%=v_obj_lvl %>" />
		<input type="hidden" name="cmd_String_Opt" id="cmd_String_Opt" value="dashboard" />

	

		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:500px"> 
        <tr> 
        	<td style="background:#369;color:#fff;text-align:center" colspan="5"><label><B>PENGAJUAN UJIAN / SIDANG</B> </label></td> 
        </tr> 
        <tr> 
            <td style="background:#369;color:#fff;text-align:center;width:50%">Tipe Ujian Akhir</td>
            <td style="text-align:center;width:250px">
            <select id="Tipe-Ujian-Akhir_Selection_Wajib" name="Tipe-Ujian-Akhir_Selection_Wajib" style="width:99%">
            	<option value="<%="null" %>">Pilih Tipe Ujian</option>
<%
	ListIterator li = vTipeUa.listIterator();
	while(li.hasNext()) {
		String list_mk_ujian = (String)li.next();
		StringTokenizer st = new StringTokenizer(list_mk_ujian,"`");
		String idkmk = st.nextToken();
		String kdkmk = st.nextToken();
		String nakmk = st.nextToken();
%>
				<option value="<%=idkmk %>`<%=kdkmk %>`<%=nakmk %>"><%=nakmk %></option>
<%		
	}
	
%>            	
            </select>
            </td> 
        </tr> 
        <tr> 
            <td style="background:#369;color:#fff;text-align:center;width:50%">Judul Naskah Ujian</td>
            <td style="text-align:center;width:250px">
            	<input type="text" name="Judul-Naskah-Ujian_String_Wajib" id="Judul-Naskah-Ujian_String_Wajib" style="width:99%" placeholder="wajib diisi" />
            </td>
        </tr>
        
        <tr id="part1_tr" style="display:table-row">
        
        	<td colspan="2" style="text-align:right">
        		
        		<input type="button" id="buttonPart1" value="Next" style="width:50%;height:35px;margin:5px;visibility:visible"/>
        	</td>
        
        </tr>
    	</table>
	</form> 
		<br />

		<%
}
		%>
		
			<div id="div_msg" style="font-style:italic;color:red;text-align:left;background-color:#d9e1e5;width:500px;height:100%;visibility:hidden;margin:0px 0 0 218px;" ></div>

		
		
	</div>
</div>	

</body>
</html>