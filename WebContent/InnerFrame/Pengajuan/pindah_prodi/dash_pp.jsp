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
Vector v= null;
Vector vThsmsStmhs = (Vector)session.getAttribute("vThsmsStmhs");
String tkn_stmhs_kode =  Getter.getListStmhs();

String id_obj= request.getParameter("id_obj");
String nmm= request.getParameter("nmm");
String npm= request.getParameter("npm");
String obj_lvl= request.getParameter("obj_lvl");
String kdpst= request.getParameter("kdpst");

String cmd= request.getParameter("cuti");
String msg= request.getParameter("msg");
%>
<script type="text/javascript">
</script>
</head>
<body>
<div id="header">
<ul>
	<li>
		<a href="<%=Constants.getRootWeb()+"/" %>InnerFrame/indexPengajuanMhs.jsp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=reqMhs" target="_self">BACK <span><b style="color:#eee">&nbsp</b></span></a>
	</li>
</ul>	
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<%
		if(msg!=null && msg.equalsIgnoreCase("upd")) {
		%>
			<h3 align="center">UPDATED : <%=AskSystem.getDateTime() %></h3>
		<%
		}
		%>
		
		<form action="go.updStmhs?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=pp" method="post">
		
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:350px"> 
        <tr> 
              <td style="background:#369;color:#fff;text-align:center" colspan="5"><label><B>RIWAYAT STATUS MAHSISWA</B> </label></td> 
         </tr> 
         <tr> 
              <td style="background:#369;color:#fff;text-align:center;width:150px">THSMS</td>
              <td style="background:#369;color:#fff;text-align:center;width:200px">STATUS</td> 
         </tr>
         <%
         StringTokenizer st = null;
         StringTokenizer st1 = null;
         StringTokenizer st2 = null;
         ListIterator li =vThsmsStmhs.listIterator();
         while(li.hasNext()) {
        	 String brs= (String)li.next();
        	 st = new StringTokenizer(brs,"`");
        	 String thsms = st.nextToken();
        	 String stmhs = st.nextToken();
        	 //System.out.println("brs="+brs);
         %>
         <tr> 
              <td style="text-align:center"><%=thsms %></td>
              <td style="text-align:center;">
              	<select name="thsms_stmhs" style="width:100%" >
              <%
              //CEK DULU APA MATCH AT KODE A - KRN DISABLE OPTION KALO AKTIF
              boolean match = false;
              boolean matchAktif = false;
              st1 = new StringTokenizer(tkn_stmhs_kode,"`");
              while(st1.hasMoreTokens()) {
            	  String tkn_keter_kode = st1.nextToken();
            	  
                  st2 = new StringTokenizer(tkn_keter_kode,"-");
                  String keter = st2.nextToken();
                  String kode = st2.nextToken();
                  //System.out.println(stmhs+" vs "+kode);
                  if(stmhs.equalsIgnoreCase(kode)) {
                	  match = true;
                	  //KALO STATUSNYA AKTIF MAKA TIDAK DAPAT DIRUBAH / READ ONLY
                	  //UNTUK MERUBAH HARUS MENHAPUS TRNLM TERLEBIHH DAHULU
                	  if(kode.equalsIgnoreCase("A")) {
                		  matchAktif = true;
                	  }
                  }
              }    
              // END CEK DULU APA MATCH AT KODE A - KRN DISABLE OPTION KALO AKTIF
              
              //redo fungsi diatas
              match = false;
              st1 = new StringTokenizer(tkn_stmhs_kode,"`");
              while(st1.hasMoreTokens()) {
            	  String tkn_keter_kode = st1.nextToken();
            	  
                  st2 = new StringTokenizer(tkn_keter_kode,"-");
                  String keter = st2.nextToken();
                  String kode = st2.nextToken();
                  //System.out.println(stmhs+" vs "+kode);
                  if(stmhs.equalsIgnoreCase(kode)) {
                	  match = true;
                	  //KALO STATUSNYA AKTIF MAKA TIDAK DAPAT DIRUBAH / READ ONLY
                	  //UNTUK MERUBAH HARUS MENHAPUS TRNLM TERLEBIHH DAHULU
                	  //DEMIKIAN PULA UNTUK SET AKTIF DAN LULUS - TIDAK BISA DIINPUT DARI SINI

                  %>
                  	<option value="<%=thsms+"`"+kode %>" selected="selected"><%=keter %></option>
                  <%	  
                	  
                  }
                  else {
                	if(matchAktif) {
                	}
                	else {
                		if(kode.equalsIgnoreCase("A")||kode.equalsIgnoreCase("L")) {
                			//DEMIKIAN PULA UNTUK SET AKTIF DAN LULUS - TIDAK BISA DIINPUT DARI SINI	
                		}
                		else {
                	%>
                	<option value="<%=thsms+"`"+kode %>"><%=keter %></option>
                	<%  
                		}
                	}
                  }  
              }
              //System.out.println("match="+match);
              if(!match) {
            	  %>
              		<option value="<%=thsms+"`null" %>" selected="selected">PILIH STATUS MHS</option>
              	<%  
              }
              %>
              	</select>
              </td> 
         </tr>
         <%
         }
         %>
         <tr> 
              <td style="background:#369;color:#fff;text-align:center" colspan="5"><input type="submit" value="Next" style="width:50%"/></td> 
         </tr>
         </table> 
         </form>
		<!-- Column 1 end -->
		
	</div>
</div>

</body>
</html>