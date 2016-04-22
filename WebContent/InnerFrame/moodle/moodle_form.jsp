<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.tools.*" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
Vector v_role = (Vector)request.getAttribute("v_role");
ListIterator li = v_role.listIterator();
request.removeAttribute("v_role");
//out.println(v_role.size());
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String objId = request.getParameter("objId");
String obj_lvl = request.getParameter("obj_lvl");
String kdpst = request.getParameter("kdpst");
String backTo = request.getParameter("backTo");	
String obj_nick = Checker.getObjNickname(npm);
boolean is_target_mhs = false;
if(obj_nick.contains("MHS")) {
	is_target_mhs = true;
}
%>
</head>
<body>
<form action="proses.updateAksesMoodle?nmm=<%=nmm %>&npm=<%=npm %>&objId=<%=objId %>&kdpst=<%=kdpst %>&backTo=<%=backTo %>&obj_nick=<%=obj_nick %>" method="post">
	<select name="role">
<%
while(li.hasNext()) {
	String info = (String)li.next();
	System.out.println("info="+info);
	StringTokenizer st = new StringTokenizer(info,"`");
	//  li.add(id+"`"+shortname+"`"+description+"`"+sortorder+"`"+archetype);
	String id = st.nextToken();
	String shortname = st.nextToken();
	String description = st.nextToken();
	String sortorder = st.nextToken();
	String archetype = st.nextToken();
	if(is_target_mhs) {
		if(info.contains("student")) {
		%>
			<option value="info"><%=shortname %></option>
		<%	
		}
	}	
	else {
			%>
			<option value="info"><%=shortname %></option>
			<%		
		
	}
				
}

%>
	</select>
	<br/>
	<input type="submit" value="UPDATE AKSES" />
</form>
<br/>
moodle form
</body>
</html>