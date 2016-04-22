<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v= null;  
String objId=request.getParameter("id_obj");
String nmm=request.getParameter("nmm");
String npm=request.getParameter("npm");
String kdpst=request.getParameter("kdpst");
String objLvl=request.getParameter("obj_lvl");
String backTo=request.getParameter("backTo");
String cmd=request.getParameter("cmd");
String msg = request.getParameter("msg");
String atMenu = request.getParameter("atMenu");
String kdjen = Converter.getKdjen(kdpst);
Vector v_list_shift = Converter.getPilihanShiftYgAktif(kdjen);
%>

</head>
<body onload="location.href='#'">
<div id="header">
	<ul>
		<li><a href="get.profile?id_obj=<%=objId%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=objLvl %>&kdpst=<%=kdpst %>&cmd=dashboard" target="inner_iframe">GO<span>BACK</span></a></li>
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<br/>
		<center>
		<br/>
		<form action="go.updateShiftMhs" id="auto_submit_form">
		<input type="hidden" name="id_obj" value="<%=objId%>" />
		<input type="hidden" name="nmm" value="<%=nmm%>" />
		<input type="hidden" name="npm" value="<%=npm%>" />
		<input type="hidden" name="obj_lvl" value="<%=objLvl%>" />
		<input type="hidden" name="kdpst" value="<%=kdpst%>" />
		<input type="hidden" name="cmd" value="<%=cmd%>" />
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:300px">
        	<tr>
        		<td style="background:#369;color:#fff;text-align:center"><label><B>HARAP PILIH TIPE SHIFT MAHASISWA :</B> </label></td></td>
        	</tr>
        	<tr>
        		<td>
					<select name="shiftKelas" style="width:99%">
				
			
		
<%
if(v_list_shift!=null) {
	ListIterator li = v_list_shift.listIterator();
	if(v_list_shift.size()>2) {
	//lebih dari 2 opt
		while(li.hasNext()) {
			String brs = (String)li.next();
//			System.out.println(brs);
			if(!brs.startsWith("N/A")&&!brs.startsWith("n/a")) {
				//update shift
				StringTokenizer st = new StringTokenizer(brs,"#&");
				String value = st.nextToken();
				String shift = st.nextToken();
				String hari = st.nextToken();
				String textTampil = st.nextToken();
				%>
						<option value="<%=value %>"><%=textTampil %></option>
				<%
			}
		}
		%>
					</select>
				</td>
			</tr>
			<tr>
				<td style="background:#369;color:#fff;text-align:center">
					<center><input type="submit" value="UPDATE SHIFT KELAS" style="width:70%;" /></center>
				</td>
			</tr>		
		<%
	}
	//cuma 2 opt yg 1 - N/A (jadi cuma satu valid answer which is d other)
	else {
		while(li.hasNext()) {
			String brs = (String)li.next();
//			System.out.println(brs);
			if(!brs.startsWith("N/A")&&!brs.startsWith("n/a")) {
				//update shift
				StringTokenizer st = new StringTokenizer(brs,"#&");
				String value = st.nextToken();
				String shift = st.nextToken();
				String hari = st.nextToken();
				String textTampil = st.nextToken();
				%>
						<option value="<%=value %>"><%=textTampil %></option>
				<%
			}
		}
		%>
		</select>
				</td>
			</tr>
			<tr>
				<td style="background:#369;color:#fff;text-align:center">
					<center><input type="submit" value="UPDATE SHIFT KELAS" style="width:70%;" /></center>
				</td>
			</tr>		
		<%
	}
	/*
	else {
	//cuma 2 opt yg 1 - N/A (jadi cuma satu valid answer which is d other)
		while(li.hasNext()) {
			String brs = (String)li.next();
			if(!brs.startsWith("N/A")&&!brs.startsWith("n/a")) {
				//update shift
				StringTokenizer st = new StringTokenizer(brs,"#&");
				String shift = st.nextToken();
				UpdateDb udb = new UpdateDb();
				udb.updateShiftKelasMhs(kdpst,npm,shift);
				if(cmd.equalsIgnoreCase("resetUrsPwd")) {
				%>
				<meta http-equiv="Refresh" content="0; url=?id_obj=<%=objId%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=objLvl %>&kdpst=<%=kdpst %>&cmd=resetUsrPwd">
				<%
				}
				else {
					if(cmd.equalsIgnoreCase("insertKrs")) {
						%>
						<meta http-equiv="Refresh" content="0; url=?id_obj=<%=objId%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=objLvl %>&kdpst=<%=kdpst %>&cmd=resetUsrPwd">
						<%
					}
				}
			}
		}
	}
	*/
}	
%>		
			</table>
		</form>
		</center>
		<!-- Column 1 end -->
<%		
if(v_list_shift!=null && v_list_shift.size()<3) {
//pilihan pertama "n/a" jadi cuma satu pilihan jadi di auto submit
%>
<script>
	document.getElementById("auto_submit_form").submit();
</script>
<%
}
%>
	</div>
</div>

</body>
</html>