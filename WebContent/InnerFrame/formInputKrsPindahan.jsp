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
int i=0;
String aspti = request.getParameter("aspti");
String aspst = request.getParameter("aspst");
String fwdTo = request.getParameter("fwdTo");
String fwdPg = request.getParameter("fwdPg");
%>

</head>
<!--  body onload="location.href='#'" -->
<body>
<div id="header">
<%@ include file="dataMhsPindahanSubMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<form action="update.KrsAsalPT">
		<input type="hidden" name="id_obj" value="<%=objId %>" /><input type="hidden" name="nmm" value="<%= nmm%>" />
		<input type="hidden" name="npm" value="<%=npm %>" /><input type="hidden" name="obj_lvl" value="<%= obj_lvl%>" />
		<input type="hidden" name="kdpst" value="<%= kdpst%>" /><input type="hidden" name="aspti" value="<%= aspti%>" />
		<input type="hidden" name="cmd" value="<%=cmd %>" /><input type="hidden" name="fwdTo" value="<%= fwdTo%>" />
		<input type="hidden" name="fwdPg" value="indexMhsPindahan.jsp" /><input type="hidden" name="aspst" value="<%=aspst %>" />
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:765px">
        	<tr>
        		<td style="background:#369;color:#fff;text-align:center" colspan="6"><label><B>INPUT FORM TRANSKRIPT ASAL PT</B> </label></td></td>
       		</tr>
      		<tr>
      			<td style="background:#369;color:#fff;text-align:left;width:15px"><label><B>NO.</B> </label></td></td>
      			<td style="background:#369;color:#fff;text-align:left;width:515px"><label><B>MATAKULIAH</B> </label></td></td>
      			<td style="background:#369;color:#fff;text-align:center;width:100px"><label><B>KODE MK ASAL</B> </label></td></td>
     			<td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>NILAI</B> </label></td></td>
        		<td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>BOBOT</B> </label></td></td>
        		<td style="background:#369;color:#fff;text-align:center;width:35px"><label><B>SKS</B> </label></td></td>
        	</tr>
        	
        		
		<%
		////System.out.println("btrnlp="+vtrnlp);
		if(vtrnlm) {
			//System.out.println("v_-trnlm- = "+v0.size());
		}	
		if(vtrnlp) {
			//System.out.println("v_-trnlp- = "+v0.size());
			ListIterator li0 = v0.listIterator();
			while(li0.hasNext()) {
				i++;
			%>
			<tr>
			<%
			
				String thsms = (String)li0.next();
				String kdkmk = (String)li0.next();
				String nakmk = (String)li0.next();
				String nlakh = (String)li0.next();
				String bobot = (String)li0.next();
				String kdasl = (String)li0.next();
				String nmasl = (String)li0.next();
				String sksmk = (String)li0.next();
				String totSks = (String)li0.next();//ignore
				String sksas = (String)li0.next();
				String transferred = (String)li0.next();
				//System.out.println(thsms+"-"+kdkmk+"-"+kdasl+"-"+nmasl+"-"+transferred);
		%>
				<td style="color:#000;text-align:center;"><label><B><%=i %></B> </label></td>
				<td style="color:#000;text-align:center;"><label><B><input type="text" name="nakmkasal" value="<%=nmasl %>" style="width:98%" /></B> </label></td>
        		<td style="color:#000;text-align:left;"><label><B><input type="text" name="kdkmkasal" value="<%=kdasl %>" style="width:96%;text-align:center" /></B> </label></td>
        		<td style="color:#000;text-align:center;"><label><B><input type="text" name="nlakhasal" value="<%=nlakh %>" style="width:96%;text-align:center" /></B> </label></td>
        		<td style="color:#000;text-align:center;"><label><B><input type="text" name="bobotasal" value="<%=bobot %>" style="width:96%;text-align:center" /></B> </label></td>
        		<td style="color:#000;text-align:center;"><label><B><input type="text" name="sksmkasal" value="<%=sksas %>" style="width:96%;text-align:center" /></B> </label></td>
        				
        	</tr>
        <%
			}
		}
		for(i=i+1;i<=70;i++) {
			%>
			<tr>
				<td style="color:#000;text-align:center;"><label><B><%=i %></B> </label></td>
				<td style="color:#000;text-align:left;"><label><B><input type="text" name="nakmkasal" value="" style="width:98%" /></B> </label></td>
        		<td style="color:#000;text-align:left;"><label><B><input type="text" name="kdkmkasal" value="" style="width:96%;text-align:center" /></B> </label></td>
        		<td style="color:#000;text-align:center;"><label><B><input type="text" name="nlakhasal" value="" style="width:96%;text-align:center" /></B> </label></td>
        		<td style="color:#000;text-align:center;"><label><B><input type="text" name="bobotasal" value="" style="width:96%;text-align:center" /></B> </label></td>
        		<td style="color:#000;text-align:center;"><label><B><input type="text" name="sksmkasal" value="" style="width:96%;text-align:center" /></B> </label></td>
        	</tr>
			<%
		}

        %>	
        	<tr>
        		<td style="background:#369;color:#fff;text-align:center" colspan="6"><label><B><input type="submit" value="UPDATE TRANSKRIP PINDAHAN" style="width:80%;height:30px"/></B> </label></td></td>
       		</tr>
        </table>
		</form>		
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>