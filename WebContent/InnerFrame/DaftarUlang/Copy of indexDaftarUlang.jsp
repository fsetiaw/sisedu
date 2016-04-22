<!DOCTYPE html>
<head>

<%@ page import="beans.dbase.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.login.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.tools.PathFinder"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<title>Insert title here</title>
<%
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	//System.out.println("dashAkademi.jsp");
	String finalList = (String)request.getAttribute("finalList");
	String totApproval = (String)request.getAttribute("totApproval");
	String listObjApproval = (String)request.getAttribute("listObjApproval");
	request.removeAttribute("finalList");
	request.removeAttribute("totApproval");
	request.removeAttribute("listObjApproval");
	String usrObjNickname = validUsr.getObjNickNameGivenObjId();
	String thsms_regis = (String)request.getParameter("thsms_regis");
%>


</head>
<body>
<!--  div id="header">
	<  %@ include file="innerMenu.jsp" % >
</div -->
<div class="colmask fullpage">
	<div class="col1">

		<br />
		
		<%
		try {
		if(finalList!=null) {
			//System.out.println(finalList);
			StringTokenizer st = new StringTokenizer(finalList,"$");
		%>
		<form action="proses.updateDaftarUlang" method="post">
		<input type="hidden" name="thsms_regis" value="<%=thsms_regis %>">
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:<%=735+(Integer.valueOf(totApproval).intValue()*15)%>px">
        <tr>
	    	<td style="background:#369;color:#fff;text-align:center" colspan="<%=4+Integer.valueOf(totApproval).intValue()%>"><label><B>DAFTAR MAHASISWA YANG MENDAFTAR ULANG</B> </label></td>
	    </tr>
    	<tr>
    		<td style="background:#369;color:#fff;text-align:center;width:20px">NO</td><td style="background:#369;color:#fff;text-align:center;width:150px">PRODI</td><td style="background:#369;color:#fff;text-align:center;width:150px">NPM/NIM</td><td style="background:#369;color:#fff;text-align:center;width:315px">NAMA</td>
    	<%
    	//listObjApproval = listObjApproval.replace("OPERATOR", "");
    	StringTokenizer sttmp = new StringTokenizer(listObjApproval.replace("OPERATOR", ""),",");
    		while(sttmp.hasMoreTokens()) {
    	%>	
    		<td style="background:#369;color:#fff;text-align:center;width:15px"><%=sttmp.nextToken() %></td>
    	<%
    		}
    	%>	
    	</tr>
    	<%
    		boolean show_submit_button = false;
    		int no = 0;
    		while(st.hasMoreTokens()) {
	    		String nmpst=st.nextToken();
    			String kdpst=st.nextToken();
	    		String npmhs=st.nextToken();
    			String nimhs=st.nextToken();
    			String nmmhs=st.nextToken();
    		
	    		String smawl=st.nextToken();
    			String stpid=st.nextToken();
    			String tglAju=st.nextToken();
    			String tknApr=st.nextToken();
    			String tknVerObj=st.nextToken();
    			String urutan=st.nextToken();
    			
    			
    			
    	%>
    	<tr>
    		<td style="text-align:center"><%=++no %></td><td style="text-align:center"><%=nmpst %></td><td style="text-align:center"><%=npmhs %> / <%=nimhs %></td><td><%=nmmhs %></td>
    	<%
	    		String nickMatch="";	
    			sttmp = new StringTokenizer(tknVerObj,",");  
    			while(sttmp.hasMoreTokens()) {
    				boolean match = false;
    				String targetNick = sttmp.nextToken();
					StringTokenizer stt = new StringTokenizer(usrObjNickname,",");
    	    		while(stt.hasMoreTokens()&&!match) {
        				String nick = stt.nextToken();
        				if(targetNick.equalsIgnoreCase(nick)) {
        					match = true;
        					show_submit_button = true;
        					nickMatch = ""+targetNick;
        				}
        			}
    			
    	
	    			if(match) {
    					if(tknApr.contains(nickMatch)) {
    	%>	
    		<td style="text-align:center"><input type="checkbox" name="option1" value="<%=kdpst+"#"+npmhs+"#"+validUsr.getNpm()+"#"+nickMatch %>" checked></td>
    	<%		
    					}
    					else {
    	%>	
    		<td style="text-align:center"><input type="checkbox" name="option1" value="<%=kdpst+"#"+npmhs+"#"+validUsr.getNpm()+"#"+nickMatch %>"></td>
    	<%
    					}
    	
	    			}
    				else {
    					if(tknApr.contains(targetNick)) {
    	%>
    		<td style="text-align:center">&#9745;</td>
    	<%				
    					}
    					else {
    	%>
    		<td style="text-align:center">&#9744;</td>
    	<%			
    					}    	
    				}
    			}	
    	%>	
    	</tr>
    	<%		
			}
    	%>
    </table>
    <%
    		if(show_submit_button) {
    %>
    <div style="text-align:center">
    <input  type="submit" value="UPDATE" style="width:500px">
    </div>
    </form>
    
    	<%
    		}
		}
		}
		catch(Exception e) {
			System.out.println(e);
		}
    	%>		
	</div>
</div>		
</body>
	