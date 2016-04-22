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
%>

</head>
<body>
<div id="header">
<%@ include file="krsKhsSubMenu.jsp" %>
<%
SearchDb sdb = new SearchDb();
String nmpst = sdb.getNmpst(kdpst);
String idkur = sdb.getIndividualKurikulum(kdpst, npm);
Vector vMk=null;
if(idkur!=null) {
	vMk = sdb.getListMatakuliahDalamKurikulum(kdpst,idkur);
}	
//System.out.println("v_shift="+v_shift);
String kdjen = Converter.getKdjen(kdpst);
Vector v_list_shift = Converter.getPilihanShiftYgAktif(kdjen);

%>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<br/>
		<center>
		<br/>
		<form action="go.updateShiftMhs">
		<input type="hidden" name="id_obj" value="<%=v_id_obj%>" />
		<input type="hidden" name="nmm" value="<%=v_nmmhs%>" />
		<input type="hidden" name="npm" value="<%=v_npmhs%>" />
		<input type="hidden" name="obj_lvl" value="<%=v_obj_lvl%>" />
		<input type="hidden" name="kdpst" value="<%=v_kdpst%>" />
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
				if(cmd.equalsIgnoreCase("editKrs")) {
				%>
				<meta http-equiv="Refresh" content="0; url=get.histKrs?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=editKrs">
				<%
				}
				else {
					if(cmd.equalsIgnoreCase("insertKrs")) {
						%>
						<meta http-equiv="Refresh" content="0; url=get.updateKrsKhs?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=insertKrs">
						<%
					}
				}
			}
		}
	}
}	
%>		
			</table>
		</form>
		</center>
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>