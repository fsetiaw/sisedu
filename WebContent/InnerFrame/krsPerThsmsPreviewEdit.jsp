<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<%@page import="beans.tools.Converter"%>
<%@page import="beans.tools.Tool"%>
<%@page import="java.util.Vector" %>
<%@page import="java.util.ListIterator" %>
<%@page import="java.util.StringTokenizer" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%
Vector v = null;
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector vKrs = (Vector)request.getAttribute("vhistkrs");
//String forceTo = "get.histKrs?id_obj="+id_obj+"&nmm="+nmm+"&npm="+npm+"&obj_lvl="+obj_lvl+"&kdpst="+kdpst+"&cmd=editKrs";
//session.setAttribute("forceBackTo", forceTo);
%>


</head>
<body onload="location.href='#'">
<div id="header">
<%@ include file="krsKhsSubMenu.jsp"%>

</div>

<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<form action="proses.editKrsKhsPerThsms" >
		
		<%
		if(vKrs!=null) {
			ListIterator li = vKrs.listIterator();
        	if(li.hasNext()) {
        		
        		String brs = (String)li.next();
        		StringTokenizer st = new StringTokenizer(brs,"#&");
        		String thsms1 = st.nextToken();
        		String kdkmk1 = st.nextToken();
        		String nakmk1 = st.nextToken();
        		nakmk1 = Tool.kembaliSpecialChar(nakmk1); 
        		String nlakh1 = st.nextToken();
        		String bobot1 = st.nextToken();
        		String sksmk1 = st.nextToken();
        		String kelas1 = st.nextToken();
        		String sksem1 = st.nextToken();
        		String nlips1 = st.nextToken();
        		String skstt1 = st.nextToken();
        		String nlipk1 = st.nextToken();
        		String shift1 = st.nextToken();
        		String krsdown1 = st.nextToken();
        		String khsdown1 = st.nextToken();
        		String bakprove1 = st.nextToken();
        		String paprove1 = st.nextToken();
        		String note1 = st.nextToken();
        		%>
        		
        		<input type="hidden" name="id_obj" value="<%=objId %>" />
        		<input type="hidden" name="nmm" value="<%=nmm %>" />
        		<input type="hidden" name="npm" value="<%=npm %>" />
        		<input type="hidden" name="kdpst" value="<%=kdpst %>" />
        		<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
        		<input type="hidden" name="cmd" value="histKrs" />
        		
        		
        		<input type="hidden" name="kdkmk" value="<%=kdkmk1 %>" />
        		<input type="hidden" name="thsms" value="<%=thsms1 %>" />
        		 
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#750;width:600px">
			<tr>
        		<td style="background:#369;color:#fff;text-align:center;" colspan="4"><label><B>KRS <%=Converter.convertThsmsKeterOnly(thsms1) %></B> </label></td></td>
        	</tr>	
        	<tr>
        		<td style="background:#369;color:#fff;text-align:center;width:100px"><label><B>KODE</B> </label></td></td>
        		<td style="background:#369;color:#fff;text-align:left;width:400px"><label><B>MATAKULIAH</B> </label></td></td>
        		<td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>NILAI</B> </label></td></td>
        		<!--  td style="background:#369;color:#fff;text-align:center;width:55px"><label><B>KELAS</B> </label></td></td -->
        		<td style="background:#369;color:#fff;text-align:center;width:200px"><label><B>SHIFT</B> </label></td></td>
        	</tr>		
        	<tr>
        		<td style="color:#000;text-align:center;"><%=kdkmk1 %></td>
        		<td style="color:#000;text-align:left;"><%=nakmk1 %></td>
        		<td style="color:#000;text-align:center;"><input type="text" name="nlakh" value="<%=nlakh1 %>" style="width:97%;text-align:center"/></td>
        		<td style="color:#000;text-align:center;">
        			<select name="shift" style="width:98%">
        		<%
        		String kdjen = Converter.getKdjen(kdpst);
        		Vector vShift = Converter.getPilihanShiftYgAktif(kdjen);
        		ListIterator lis = vShift.listIterator();
        		while(lis.hasNext()) {
        			brs = (String)lis.next();
        			st = new StringTokenizer(brs,"#&");
        			while(st.hasMoreTokens()) {
        				String ket = st.nextToken();
        				String shift = st.nextToken();
        				String hari = st.nextToken();
        				String konversi_kod = st.nextToken();
        				if(konversi_kod.equalsIgnoreCase(shift1)) {
        				%>
        				<option value="<%=ket%>" selected><%=konversi_kod %></option>
        				<%
        				}
        				else {
        				%>
        				<option value="<%=ket%>"><%=konversi_kod %></option>
        				<%
        				}
        			}
        			
        		} 
        		%>
        			</select>
        		</td>
        	</tr>
        	<%
        		while(li.hasNext()) {
        			brs = (String)li.next();
            		st = new StringTokenizer(brs,"#&");
            		thsms1 = st.nextToken();
            		kdkmk1 = st.nextToken();
            		nakmk1 = st.nextToken();
            		nakmk1 = Tool.kembaliSpecialChar(nakmk1);
            		nlakh1 = st.nextToken();
            		bobot1 = st.nextToken();
            		sksmk1 = st.nextToken();
            		kelas1 = st.nextToken();
            		sksem1 = st.nextToken();
            		nlips1 = st.nextToken();
            		skstt1 = st.nextToken();
            		nlipk1 = st.nextToken();
            		shift1 = st.nextToken();
            		krsdown1 = st.nextToken();
            		khsdown1 = st.nextToken();
            		bakprove1 = st.nextToken();
            		paprove1 = st.nextToken();
            		note1 = st.nextToken();
            	%>	
            <tr>
            	<input type="hidden" name="kdkmk" value="<%=kdkmk1 %>" />
            	<td style="color:#000;text-align:center;"><%=kdkmk1 %></td>
            	<td style="color:#000;text-align:left;"><%=nakmk1 %></td>
            	<td style="color:#000;text-align:center;"><input type="text" name="nlakh" value="<%=nlakh1 %>" style="width:97%;text-align:center"/></td>
            	<td style="color:#000;text-align:center;">
            		<select name="shift" style="width:98%">
            	<%
            		//String kdjen = Converter.getKdjen(kdpst);
            		//Vector vShift = Converter.getPilihanShiftYgAktif(kdjen);
            		lis = vShift.listIterator();
            		while(lis.hasNext()) {
            			brs = (String)lis.next();
            			st = new StringTokenizer(brs,"#&");
            			while(st.hasMoreTokens()) {
            				String ket = st.nextToken();
            				String shift = st.nextToken();
            				String hari = st.nextToken();
            				String konversi_kod = st.nextToken();
            				if(konversi_kod.equalsIgnoreCase(shift1)) {
      			%>
            			<option value="<%=ket%>" selected><%=konversi_kod %></option>
            			<%
       	     				}
            				else {
            			%>
            			<option value="<%=ket%>"><%=konversi_kod %></option>
            			<%
            				}
            			}
            		}	
            	} 
            	%>
            		</select>
            	</td>
            </tr>
        		<%	
        	}//end if li
        	%>
        	<tr>
        		<td style="background:#369;color:#fff;text-align:center;" colspan="4"><label><input type="submit" value="UPDATE KRS" style="width:50%" /></label></td></td>
        	</tr>
        </table>
        </form>
        <%
        }//end if vKrs!=null
        %>	
		<!-- Column 1 end -->
	</div>
</div>		
</body>
</html>