<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>	
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");

Vector v= null; 
//String id_obj = request.getParameter("id_obj");
//String nmm = request.getParameter("nmm");
//String npm = request.getParameter("npm");
//String obj_lvl= request.getParameter("obj_lvl");
//String kdpst=request.getParameter("kdpst");
//String cmd=request.getParameter("cmd");
%>


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
		int col=0;
		int x=0;
		int y=0;
		boolean first_col = true;
		boolean new_row = true;
		
		%>
	
		<table  align="center" style="background:#d9e1e5;color:#000;">
		<%//==========COPY DARI SINI======================TAMPLETE START========================================================
		if(validUsr.isUsrAllowTo("allowCetakKrs", v_npmhs, v_obj_lvl)) {
			if(first_col && new_row) {
				//paling kiri
				first_col=false;
				new_row = false;
				x=1;
			%>
			<tr>
				<td style="width:25px"></td>
			<%
				x++;
			%>	
				<td style="width:150px">
					<form action="get.histKrs" >
						<input type="hidden" value="<%=v_id_obj %>" name="id_obj"/>
						<input type="hidden" value="<%=v_nmmhs %>" name="nmm"/>
						<input type="hidden" value="<%=v_npmhs %>" name="npm"/>
						<input type="hidden" value="<%=v_obj_lvl %>" name="obj_lvl"/>
						<input type="hidden" value="<%=v_kdpst %>" name="kdpst" />
						<input type="hidden" value="cetakKrs" name="cmd" />
						<center><input type="submit" style="width:150px;height:50px;text-align:center;font-size:1.5em" value="K R S"/></center>
					</form>
				</td>
			<%	
			}
			else {
				if(x<6) {
					//tengah2
					x++;
			%>
				<td style="width:25px"></td>
			<%
					x++;
			%>	
				<td style="width:150px">
					<form action="get.histKrs" >
						<input type="hidden" value="<%=v_id_obj %>" name="id_obj"/>
						<input type="hidden" value="<%=v_nmmhs %>" name="nmm"/>
						<input type="hidden" value="<%=v_npmhs %>" name="npm"/>
						<input type="hidden" value="<%=v_obj_lvl %>" name="obj_lvl"/>
						<input type="hidden" value="<%=v_kdpst %>" name="kdpst" />
						<input type="hidden" value="cetakKrs" name="cmd" />
						<center><input type="submit" style="width:150px;height:50px;text-align:center;font-size:1.5em" value="K R S"/></center>
					</form>
				</td>
			<% 	
				}
				else {
					//paling pojok
					first_col=true;
					new_row = true;
					%>
				<td style="width:25px"></td>
				<td style="width:150px">
					<form action="get.histKrs" >
						<input type="hidden" value="<%=v_id_obj %>" name="id_obj"/>
						<input type="hidden" value="<%=v_nmmhs %>" name="nmm"/>
						<input type="hidden" value="<%=v_npmhs %>" name="npm"/>
						<input type="hidden" value="<%=v_obj_lvl %>" name="obj_lvl"/>
						<input type="hidden" value="<%=v_kdpst %>" name="kdpst" />
						<input type="hidden" value="cetakKrs" name="cmd" />
						<center><input type="submit" style="width:150px;height:50px;text-align:center;font-size:1.5em" value="K R S"/></center>
					</form>
				</td>
				<td style="width:25px"></td>
					<%
				}
				
			}
		}
		
		//===========================================================================================================%>

		<%//==========COPY DARI SINI======================TAMPLETE START========================================================
		if(validUsr.isUsrAllowTo("allowCetakKhs", v_npmhs, v_obj_lvl)) {
			if(first_col && new_row) {
				//paling kiri
				first_col=false;
				new_row = false;
				x=1;
			%>
			<tr>
				<td style="width:25px"></td>
			<%
				x++;
			%>	
				<td style="width:150px">
					<form action="get.histKrs" >
						<input type="hidden" value="<%=v_id_obj %>" name="id_obj"/>
						<input type="hidden" value="<%=v_nmmhs %>" name="nmm"/>
						<input type="hidden" value="<%=v_npmhs %>" name="npm"/>
						<input type="hidden" value="<%=v_obj_lvl %>" name="obj_lvl"/>
						<input type="hidden" value="<%=v_kdpst %>" name="kdpst" />
						<input type="hidden" value="cetakKhs" name="cmd" />
						<center><input type="submit" style="width:150px;height:50px;text-align:center;font-size:1.5em" value="K H S"/></center>
					</form>
				</td>
			<%	
			}
			else {
				if(x<6) {
					//tengah2
					x++;
			%>
				<td style="width:25px"></td>
			<%
					x++;
			%>	
				<td style="width:150px">
					<form action="get.histKrs" >
						<input type="hidden" value="<%=v_id_obj %>" name="id_obj"/>
						<input type="hidden" value="<%=v_nmmhs %>" name="nmm"/>
						<input type="hidden" value="<%=v_npmhs %>" name="npm"/>
						<input type="hidden" value="<%=v_obj_lvl %>" name="obj_lvl"/>
						<input type="hidden" value="<%=v_kdpst %>" name="kdpst" />
						<input type="hidden" value="cetakKhs" name="cmd" />
						<center><input type="submit" style="width:150px;height:50px;text-align:center;font-size:1.5em" value="K H S"/></center>
					</form>
				</td>
			<% 	
				}
				else {
					//paling pojok
					first_col=true;
					new_row = true;
					%>
				<td style="width:25px"></td>
				<td style="width:150px">
					<form action="get.histKrs" >
						<input type="hidden" value="<%=v_id_obj %>" name="id_obj"/>
						<input type="hidden" value="<%=v_nmmhs %>" name="nmm"/>
						<input type="hidden" value="<%=v_npmhs %>" name="npm"/>
						<input type="hidden" value="<%=v_obj_lvl %>" name="obj_lvl"/>
						<input type="hidden" value="<%=v_kdpst %>" name="kdpst" />
						<input type="hidden" value="cetakKhs" name="cmd" />
						<center><input type="submit" style="width:150px;height:50px;text-align:center;font-size:1.5em" value="K H S"/></center>
					</form>
				</td>
				<td style="width:25px"></td>
					<%
				}
			}
		}
		
		//===========================================================================================================%>
		<%
		
		
		
		
		
		
		
		//=====end part - semua paste tamplete harus di atas bagian ini=====
		if(x==2||x==4||x==6) {
			%>
			<td style="width:25px"></td>
			<%
		}
		%>
		
			</tr>
		</table>
		
		
		
		
		
		
		
		<%
		/*
		%>
		
		<table  align="center" style="background:#d9e1e5;color:#000;">
			<tr>
		<%
		//design 8col = col ganjil utk btn submit dan genap utk pemisah
		//tamplet start - copy dari sini
		if(validUsr.isUsrAllowTo("allowEditIjazah", v_npmhs, v_obj_lvl)) {
			if(col>7) {
				//buat row baru
				col=0;	
			%>
			</tr>
			<tr>
			<%	
			}
			col++;
			%>
				<td style="width:150px">
					<form action="go.cetakIjazah" >
						<input type="hidden" value="<%=v_id_obj %>" name="id_obj"/>
						<input type="hidden" value="<%=v_nmmhs %>" name="nmm"/>
						<input type="hidden" value="<%=v_npmhs %>" name="npm"/>
						<input type="hidden" value="<%=v_obj_lvl %>" name="obj_lvl"/>
						<input type="hidden" value="<%=v_kdpst %>" name="kdpst" />
						<input type="hidden" value="percetakan" name="cmd" />
						<center><input type="submit" style="width:150px;height:50px;text-align:center;font-size:1.5em" value="I J A Z A H"/></center>
					</form>
					<br/>
					<br/>
					<!--  a href="go.cetakIjazah?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=percetakan">Cetak Ijazah</a -->
				</td>
			<%	
			col++;
			if(col<7) {
			%>
				<td style="width:50px"></td>
			<%
			}
			else {
				col=0;
				%>
			</tr>
			<tr>
			<%
			}
		}
		
		//end copy tamplte
		if(validUsr.isUsrAllowTo("allowCetakKrs", v_npmhs, v_obj_lvl)) {
			if(col>7) {
				//buat row baru
				col=0;	
			%>
			</tr>
			<tr>
			<%	
			}
			col++;
			%>
				<td style="width:150px">
					<form action="get.Step1CetakKrs" >
						<input type="hidden" value="<%=v_id_obj %>" name="id_obj"/>
						<input type="hidden" value="<%=v_nmmhs %>" name="nmm"/>
						<input type="hidden" value="<%=v_npmhs %>" name="npm"/>
						<input type="hidden" value="<%=v_obj_lvl %>" name="obj_lvl"/>
						<input type="hidden" value="<%=v_kdpst %>" name="kdpst" />
						<input type="hidden" value="percetakan" name="cmd" />
						<center><input type="submit" style="width:150px;height:50px;text-align:center;font-size:1.5em" value="K R S"/></center>
					</form>
					<br/>
					<br/>
					<!--  a href="go.cetakIjazah?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=percetakan">Cetak KRS</a -->
				</td>
			<%	
			col++;
			if(col<7) {
			%>
				<td style="width:50px"></td>
			<%
			}
			else {
				col=0;
				%>
			</tr>
			<tr>
			<%
			}
		}
		
		//end copy tamplte

		%>
		</tr>
	</table>	
	<%
	*/
	%>
	</div>
</div>	
</body>
</html>