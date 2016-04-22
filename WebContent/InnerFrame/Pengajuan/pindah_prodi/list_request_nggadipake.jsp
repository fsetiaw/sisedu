<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.dbase.classPoll.*" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.sistem.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%
//System.out.println("yap1");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v= (Vector)request.getAttribute("vListReq");
request.removeAttribute("vListReq");

//Vector vThsmsStmhs = (Vector)session.getAttribute("vThsmsStmhs");
//String tkn_stmhs_kode =  Getter.getListStmhs();
if(validUsr==null) { 
	response.sendRedirect( Constants.getRootWeb()+"/ErrorPage/noUserSession.html"); 
} 

%>
<script type="text/javascript">
</script>
</head>
<body>
<div id="header">
<ul>
	<li>
		<a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">BACK <span>&nbsp</span></a>
	</li>

</ul>	
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
<%
if(v==null) {
%>
	<div style="text-align:center;font-size:1.5em">
	TIDAK ADA PENGAJUAN CUTI
	</div>
<%
}
else {
	ListIterator li = v.listIterator();
	if(li.hasNext()) {
		int i =1;
	%>
	<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:600px"> 
        <tr> 
             <td style="background:#369;color:#fff;text-align:center" colspan="3"><label><B>DAFTAR MAHASISWA YANG MENGAJUKAN CUTI</B> </label></td> 
        </tr> 
        <tr> 
              <td style="background:#369;color:#fff;text-align:center;width:25px">NO</td><td style="background:#369;color:#fff;text-align:center;width:250px">NPM/NAMA</td><td style="background:#369;color:#fff;text-align:center;width:325px">STATUS</td> 
        </tr> 
	<%	
		do {
			String brs = (String)li.next();
			StringTokenizer st = new StringTokenizer(brs,"`");
			System.out.println(brs);
			String id=st.nextToken();
			String thsms_pengajuan=st.nextToken();
			String tipe_pengajuan=st.nextToken();
			String isi_topik_pengajuan=st.nextToken();
			String tkn_target_objnickname=st.nextToken();
			String tkn_target_objid=st.nextToken();
			String target_npm=st.nextToken();
			String creator_obj_id=st.nextToken();
			String creator_npm=st.nextToken();
			String creator_nmm=st.nextToken();
			String shwow_at_target=st.nextToken();
			String show_at_creator=st.nextToken();
			String updtm=st.nextToken();
			String approved=st.nextToken();
			String locked=st.nextToken();
			%>
		<tr> 
        	<td style="text-align:center;width:25px"><%=i++ %>.</td>
        	<td style="text-align:center;width:250px"><%=creator_npm %><br/><%=creator_nmm %></td>
        	<td style="text-align:center;width:325px">
        	<%
        	if(approved==null || approved.equalsIgnoreCase("null")) {
        		out.println("Menunggu persetujuan :<br/>");
        		out.print(tkn_target_objnickname.replace("OPERATOR", ""));
        	}
        	else {
        		//cek siapa yg sudah approve
        		String nickname_blm_approved = "";
        		String id_blm_approved = new String(tkn_target_objid);
        		approved = approved.replace("]", "`");
        		approved = approved.replace("[", "");
        		System.out.println("approved= "+approved);
        		StringTokenizer st1 = new StringTokenizer(approved,"`");
        		while(st1.hasMoreTokens()) {
        			String id_sdh_approved = st1.nextToken();
        			id_blm_approved.replace("["+id_sdh_approved+"]", "");
        		}
        		if(Checker.isStringNullOrEmpty(id_blm_approved)) {
        			//sudah approved semua
        		}
        		else {
        			id_blm_approved=id_blm_approved.replace("]", "`");
        			id_blm_approved=id_blm_approved.replace("[", "");
        			//cari nama yg belum approved
        			System.out.println("id_blm_approved= "+id_blm_approved);
        			st1 = new StringTokenizer(id_blm_approved,"`");
        			StringTokenizer st2 = new StringTokenizer(tkn_target_objid,"]");
        			StringTokenizer st3 = new StringTokenizer(tkn_target_objnickname,"]"); 
        			while(st1.hasMoreTokens()) {
        				String tkn_id_blm_approved = st1.nextToken();
        				boolean match = false;
        				while(st2.hasMoreTokens()&&!match) {
        					String id_ = st2.nextToken();
        					System.out.println("["+tkn_id_blm_approved+" vs. "+id_);
        					String nick_ = st3.nextToken();
        					if(id_.equalsIgnoreCase("["+tkn_id_blm_approved)) {
        						match = true;
        						nickname_blm_approved = nickname_blm_approved+"]";
        					}
        				}
        			}
        		}
        		out.println("Menunggu persetujuan :<br/>");
        		out.print(nickname_blm_approved.replace("OPERATOR", ""));
        	}
        	%>
        	</td> 
        </tr> 
			<%
			
		}
		while(li.hasNext());
	%>
	</table>
	<%	
	}
}
%>
		<!-- Column 1 end -->
		
	</div>
</div>

</body>
</html>