<!DOCTYPE html>
<head>
<title>Insert title here</title>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.login.*" %>
<%@ page import="beans.tmp.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%
	request.setAttribute("atPage", "civitas");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	Vector v = (Vector) session.getAttribute("v_totMhs");
	session.removeAttribute("v_totMhs");
	SearchDb sdb = new SearchDb(); 
%>


</head>
<body onload="location.href='#'">
<div id="header">
<!--  jsp:include page="Summary/subSummaryMenu.jsp" / -->
<jsp:include page="Summary/subSummaryCivitasMenu.jsp" />
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
<br />

<%
if(v!=null && v.size()>0) {
	ListIterator li = v.listIterator();
	while(li.hasNext()) {
		String baris = (String) li.next();//baris 1
		StringTokenizer st = new StringTokenizer(baris);
		String id_obj = st.nextToken();
		String kdpst = st.nextToken();
		String obj_dsc = st.nextToken();
		String obj_lvl = st.nextToken();
		String tt_mhs = st.nextToken();
		Vector v_smawl_ttmhs = (Vector) li.next();//baris2
		if(v_smawl_ttmhs!=null && v_smawl_ttmhs.size()>0) {
			ListIterator li2 = v_smawl_ttmhs.listIterator();
			double tt_pymnt = 0;
			//System.out.println("2");	
%>
		
		<table border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:450px">
			<tr>
        		<td colspan="3" style="background:#369;color:#fff;text-align:center"><label><B><%=obj_dsc.replaceAll("_", " ") %></B> </label></td></td>
        	</tr>
        	<tr>
        		<td style="background:#369;color:#fff;text-align:center" rowspan="2"><label><B>ANGKATAN</B> </label></td><td colspan="2" style="background:#369;color:#fff;text-align:center">JUMLAH</td>
        	</tr>
        	<tr>
        		<td style="background:#369;color:#fff;text-align:center">MHS</td><td style="background:#369;color:#fff;text-align:center">BAYARAN</td>
        	</tr>
        <%
        //System.out.println("3");
	        String smawl = null;
   	    	while(li2.hasNext()) {
   	    		String baris_smawl_tt = (String) li2.next();
   	    		//System.out.println(baris_smawl_tt);
   	    		st = new StringTokenizer(baris_smawl_tt);
   	    		smawl = st.nextToken();
   	    		String subttmhs = st.nextToken();
   	    		//if(con==null || con.isClosed()) {
				sdb = new SearchDb();
				//}
   	    		double subpay = sdb.getTotPymntMhsPerAngkatan(kdpst, smawl);
   	    		//System.out.println("subpay = "+subpay);
   	    		tt_pymnt = tt_pymnt + subpay;
       	%>	
       		<form action="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/listCivitas.jsp">
			<input type="hidden" value="<%=id_obj%>" name="idobj"><input type="hidden" value="<%=smawl%>" name="smawl">
        	<tr>
    			<td align="left"><label><b><%=smawl %> </b> </label></td><td align="center"><label><b><%=subttmhs %></b></td><td align="right"><%=NumberFormater.indoNumberFormat(""+subpay) %></td><td style="width:50px"><input type="submit" value="NEXT" formtarget="_self" style="width: 50px;height:30px"/></td>
    		</tr>
    		</form>
		<%
		//System.out.println("4");
   	    	}	
   	    	//System.out.println("5");
        %>
        	<form action="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/listCivitas.jsp">
        	<input type="hidden" value="<%=id_obj%>" name="idobj">
        	<tr>
        		<td style="background:#369;color:#fff;text-align:center"><label><B>TOTAL </b> </label></td><td style="background:#369;color:#fff;text-align:center"><label><b><%=tt_mhs %></b></label></td><td align="right" style="background:#369;color:#fff"><%=NumberFormater.indoNumberFormat(""+tt_pymnt) %></td><td style="background:#369;color:#fff;text-align:center;width:50px"><input type="submit" value="NEXT" formtarget="_self" style="width: 50px;height:30px"/></td>
        	</tr>
        	</form>
        </table>
        </form>
        <br />
<%		
//System.out.println("6");
		}
		else {
			//out.print("satu");
			%>
			<%
		}
	}
	//System.out.println("7");
}
else {
	out.print("dua");
	%>
	
	<meta http-equiv="refresh" content="10;url=<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.summary">
	<%
}

%>
	</div>
</div>	
</body>
</html>