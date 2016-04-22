<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%
//System.out.println("krsjsp");
String showHiddenRecord = "true";
//String tknKrsNotifications = request.getParameter("tknKrsNotifications");
String tknKrsNotifications = (String)session.getAttribute("tknKrsNotifications");
application.removeAttribute("tknKrsNotifications");
System.out.println(tknKrsNotifications);
String tknKrsNotificationsForSender = (String)session.getAttribute("tknKrsNotificationsForSender");
//String tknKrsNotificationsForSender = request.getParameter("tknKrsNotificationsForSender");
System.out.println("ini="+tknKrsNotificationsForSender);
String id = null;
String kstegori = null;
String thsms = null;
String berita = null;
String npmSender = null;
String nmmSender = null;
String npmReceiver = null;
String nmmReceiver = null;
String kdpstReceiver = null;
String smawlReceiver = null;
String hiddenAtSender = null;
String hiddenAtReceiver = null;
String delBySender = null;
String delByReceiver = null;
String approved = null;
String declined = null;
String unlockApproved = null;
String unlockDeclined = null;
String note = null;
String updtm = null;
boolean adaHiddenRecord = false;
//tkn = tkn+id+","+kstegori+","+thsms+","+berita+","+npmSender+","+nmmSender+","+npmReceiver+","+nmmReceiver+","+kdpstReceiver+","+smawlReceiver+","+hiddenAtSender+","+hiddenAtReceiver+","+delBySender+","+delByReceiver+","+approved+","+declined+","+note+","+updtm+"||";
%>
</head>
<body>
<div id="header">
	<ul>
		<li><a href="get.notifications" target="inner_iframe">GO<span>BACK</span></a></li>
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<!-- bagian yg menerima reques -->
		<%
if(tknKrsNotifications!=null && !Checker.isStringNullOrEmpty(tknKrsNotifications)) {
	StringTokenizer st = new StringTokenizer(tknKrsNotifications,"||");
	boolean first = true;
	int counter = 0;
	while(st.hasMoreTokens()) {
		counter++;
		if(first) {
			first = false;
		%>
	<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:700px">
	<tr>
   		<td style="background:#369;color:#fff;text-align:center;width:20px"><label><B>No</B> </label></td></td>
        <td style="background:#369;color:#fff;text-align:center;width:100px"><label><B>NPM</B> </label></td></td>
       	<td style="background:#369;color:#fff;text-align:left;width:200px"><label><B>Nama Mahasiswa</B> </label></td></td>
        <td style="background:#369;color:#fff;text-align:center;width:100px"><label><B>TipeRequest</B> </label></td></td>
        <td style="background:#369;color:#fff;text-align:center;width:280px"><label><B>Catatan Akhir</B> </label></td></td>
             
  	</tr>	
		<%					
		}
		String brs = st.nextToken();
		StringTokenizer st1 = new StringTokenizer(brs,",");
		id = st1.nextToken();
		kstegori= st1.nextToken();
		thsms= st1.nextToken();
		berita= st1.nextToken();
		npmSender= st1.nextToken();
		nmmSender= st1.nextToken();
		npmReceiver= st1.nextToken();
		nmmReceiver= st1.nextToken();
		kdpstReceiver= st1.nextToken();
		smawlReceiver= st1.nextToken();
		hiddenAtSender= st1.nextToken();
		hiddenAtReceiver= st1.nextToken();
		delBySender= st1.nextToken();
		delByReceiver= st1.nextToken();
		approved= st1.nextToken();
		declined= st1.nextToken();
		unlockApproved = st1.nextToken();
		unlockDeclined = st1.nextToken();
		note= st1.nextToken();
		updtm= st1.nextToken();
		if(showHiddenRecord.equalsIgnoreCase("false")) { //ngga pernah masuk sinsi
			if(hiddenAtReceiver.equalsIgnoreCase("false")) {
				%>
	<tr>
   		<td style="color:#000;text-align:center;" rowspan="2"><%=counter %></td>
        <td style="color:#000;text-align:center;"><%=npmSender %></td>
       	<td style="color:#000;text-align:left;"><%=nmmSender %></td>
        <td style="color:#000;text-align:center;"><%=kstegori %></td>     
        <td style="color:#000;text-align:left;">
        <%
        
       			if(note!=null && !Checker.isStringNullOrEmpty(note)) {
       				out.print(note.replace("$",","));
       			}
       	%>
       	</td>     
   <tr/>
   <tr> 
   		<td colspan="2">    
        <form action="people.search">
        	<input type="hidden" name="kword" value="<%=npmSender %>"/>
        	<input  type="submit" value="BUKAN BIMBINGAN SAYAa" style="height:25px;color:red"/>
        </form>
        </td> 
   		<td colspan="2">    
        <form action="people.search">
        	<input type="hidden" name="kword" value="<%=npmSender %>"/>
        	<input  type="submit" value="Next-->" style="height:25px"/>
        </form>
        </td> 
  	</tr>		
				<%
			}
		}
		else {
	%>
	<tr>
		<td style="color:#000;text-align:center;"  rowspan="2"><%=counter %></td>
		<td style="color:#000;text-align:center;"><%=npmSender %></td>
		<td style="color:#000;text-align:left;"><%=nmmSender %></td>
		<td style="color:#000;text-align:center;"><%=berita %></td>     
		<td style="color:#000;text-align:left;">
        <%
       
       		if(note!=null && !Checker.isStringNullOrEmpty(note)) {
       			out.print(note.replace("$",","));
       		}
       	%>
       	</td>
       
        <!--  form action="people.search" -->
	</tr>
	<tr>
		<!--  td colspan="2" align="center" style="background:#369;padding:10px 5px;">    
        <form action="people.search">
        	<input type="hidden" name="kword" value="<%=npmSender %>"/>
        	<input  type="submit" value="BUKAN BIMBINGAN SAYA" style="width:90%;height:25px;color:red"/>
        </form>
        </td --> 
		<td colspan="4" align="center"  style="background:#369;padding:5px;">    
        <form action="go.directToHistKrs">
			<input type="hidden" name="kword" value="<%=npmSender %>"/>
			<input type="hidden" name="showHiddenRecord" value="true"/>
			
<!--
			updated : bila tombol next ini tamppil maka dia adalav valid approval
			update ini untuk membuat approval krs bisa diwakilkan 
			sehingga ditambahkan hidden param validApprovee
 -->		
 			<input type="hidden" name="validApprovee" value="true"/>
			<input  type="submit" value="PROSES PENGAJUAN KRS" style="width:99%;height:25px;"/>
		</form>
		</td> 
	</tr>		
<%					
		}
	}
	%>
	</table>

	<%		
	
}
		%>
 	<!-- end bagian yg menerima reques -->
 	
 	<!-- bagian yg mengirim reques -->
 	<%
		if(tknKrsNotificationsForSender!=null && !Checker.isStringNullOrEmpty(tknKrsNotificationsForSender)) {
			boolean isThereHiddenrecord = false;
			StringTokenizer st = new StringTokenizer(tknKrsNotificationsForSender,"||");
			boolean first = true;
			int counter = 0;
			while(st.hasMoreTokens()) {
				
				
				String brs = st.nextToken();
				StringTokenizer st1 = new StringTokenizer(brs,",");
				id = st1.nextToken();
				kstegori= st1.nextToken();
				thsms= st1.nextToken();
				berita= st1.nextToken();
				npmSender= st1.nextToken();
				nmmSender= st1.nextToken();
				npmReceiver= st1.nextToken();
				nmmReceiver= st1.nextToken();
				kdpstReceiver= st1.nextToken();
				smawlReceiver= st1.nextToken();
				hiddenAtSender= st1.nextToken();
				hiddenAtReceiver= st1.nextToken();
				delBySender= st1.nextToken();
				delByReceiver= st1.nextToken();
				approved= st1.nextToken();
				declined= st1.nextToken();
				unlockApproved = st1.nextToken();
				unlockDeclined = st1.nextToken();
				note= st1.nextToken();
				updtm= st1.nextToken();
				counter++;	
				if(first) {
					first = false;
				%>
<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:650px">
	<tr>
		<td style="background:#369;color:#fff;text-align:center;width:20px"><label><B>NO</B> </label></td></td>
		<td style="background:#369;color:#fff;text-align:left;width:100px"><label><B>TIPE PENGAJUAN</B> </label></td></td>
		<td style="background:#369;color:#fff;text-align:center;width:100px"><label><B>TAHUN / SMS</B> </label></td></td>
		<td style="background:#369;color:#fff;text-align:center;width:80px"><label><B>STATUS</B> </label></td></td>
		<td style="background:#369;color:#fff;text-align:center;width:350px"><label><B>NOTE</B> </label></td></td>      
	</tr>	
				<%					
				}
				%>
	<tr>
   		<td style="color:#000;text-align:center;font-size:.9em"><%=counter %></td>
        <td style="color:#000;text-align:left;font-size:.9em"><%=berita %></td>
       	<td style="color:#000;text-align:center;font-size:.9em"><%=Converter.convertThsmsKeterOnly(thsms) %></td>
        <td style="color:#000;text-align:center;font-size:.9em">
        <%
        				String status = "<div style=\"color:#DF7401\">MENUNGGU PERSETUJUAN</div>";
        				if(berita.equalsIgnoreCase("PERMOHONAN BUKA KUNCI")) {
        					if(unlockApproved.equalsIgnoreCase("true")) {
        						status = "<div style=\"color:#08298A\">Disetujui</div>";
        					}
        					else {
        						if(unlockDeclined.equalsIgnoreCase("true")) {
            						status = "<div style=\"color:red\">Ditolak</div>";
            					}
        					}
        				}
        				else {
        					if(approved.equalsIgnoreCase("true")) {
        						status = "<div style=\"color:#08298A\">Disetujui</div>";
        					}
        					else {
        						if(declined.equalsIgnoreCase("true")) {
            						status = "<div style=\"color:red\">Ditolak</div>";
            					}
        					}
        				}	
        				out.print(status);
        					
       	%>
       	</td> 
       	<td style="color:#000;text-align:left;font-size:.9em">
       	<%
       	if(note!=null && !Checker.isStringNullOrEmpty(note)) {
       		out.print(note.replace("$",","));
       	}
       	%></td>
       	<%
       	if(approved.equalsIgnoreCase("true")) {
       	%>
       	<td>
       		<form action="go.hideRequestById">
       			<input type="hidden" name="requestId" value="<%=id %>" />
       			<input type="hidden" name="hiddenAt" value="sender" />
       			<input type="submit" value="Hide" style="height:30px" />
       		</form>
       		
       	</td>	
       	<%	
       	}
       	%>
  	</tr>	
  	<%
  			}
  	%>	
	</table>
	
	<%
			if(declined.equalsIgnoreCase("true")) {
	%>
	<form action=""></form>
	<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:650px">
		<tr>
			<td style="font-size:0.9em;font-weight:bold;color:black">
				Silahkan mengajukan KRS kembali dengan memilih Menu: <br/>DATA MAHASISWA > DATA KRS/KSH > INSERT DATA KRS BARU
			</td>
		</tr>
	</table>
	</form>
	<%			
			}
		}
			
	%>
 	<!-- end bagian yg mengirim reques -->
	</div>
</div>		
</body>
</html>