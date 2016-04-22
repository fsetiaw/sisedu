<!DOCTYPE html>
<html>
<head>
<%@ page import="beans.dbase.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.login.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<title>Insert title here</title>
<%
	System.out.println("masuk list mhs");
//	String thsms_pmb = Checker.getThsmsPmb();
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	Vector v = (Vector)session.getAttribute("vSummaryPMB");
	//System.out.println("volt size = "+v.size());
	String target_thsms = ""+request.getParameter("target_thsms");
	String target_nmpst = ""+request.getParameter("target_nmpst");
	String target_kdpst = ""+request.getParameter("target_kdpst");
	//System.out.println("target thsms = "+target_thsms);
	//System.out.println("target nmpst = "+target_nmpst);
	//System.out.println("target kdpst = "+target_kdpst);
//	if(target_thsms==null || target_thsms.equalsIgnoreCase("null")) {
//		target_thsms = ""+thsms_pmb;
//	}
//	Vector vListThsms = Tool.returnTokensListThsms("20001", thsms_pmb);
	boolean allowViewKeu=false;
	Vector vTmp1 = validUsr.getScopeUpd7des2012("vbak");
	if(vTmp1!=null && vTmp1.size()>0) {
		allowViewKeu = true;	
	}
%>


</head>
<body>
<div id="header">
<%@include file="subSummaryMhsMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
	<%
	
	if(v!=null && v.size()>0) {
		int i=0;
		ListIterator li = v.listIterator();
		if(allowViewKeu) {
	%>		
	<table border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:850px">
	<%		
		}
		else {
	%>		
	<table border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:750px">
	<%
		}
	%>
		<tr>
        	<td style="background:#369;color:#fff;text-align:center" colspan="9"><p style="font-weight:bold">LIST MHS ANGKATAN <%=Converter.convertThsmsKeterOnly(target_thsms) %> PRODI <%=target_nmpst %></p></td>
        </tr>	
        <tr>
        	<td style="background:#369;color:#fff;text-align:center;width:10px"><label><B>NO.</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center;width:125px"><label><B>N P M</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center;width:275px"><label><B>NAMA MAHASISWA</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center;width:100px"><label><B>LULUS PENYARINGAN</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center;width:100px"><label><B>DAFTAR ULANG</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center;width:95px"><label><B>TIPE MHS</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center;width:45px"><label><B>JENIS KELAMIN</B> </label></td>	
        <% 
        if(allowViewKeu) {
        %>
        	<td style="background:#369;color:#fff;text-align:center;width:100px"><label><B>TOTAL PEMBAYARAN</B> </label></td>
        <%
        }
        %>
        </tr>
		<%
		
		while(li.hasNext()) {
			
			String brs = (String) li.next();
			StringTokenizer st = new StringTokenizer(brs);
			String id_obj = st.nextToken();
			String kdpst = st.nextToken();
			String obj_dsc = st.nextToken();
			String obj_level = st.nextToken();
			st = new StringTokenizer(obj_dsc,"_");
			//String nmpst = "";
		//	while(st.hasMoreTokens()) {
		//		String tmp = st.nextToken();
		//		if(!tmp.equalsIgnoreCase("MHS")) {
		//			nmpst = nmpst+tmp;
		//		}
		//		if(st.hasMoreTokens()) {
		//			nmpst=nmpst+" ";	
		//		}
		//	}

			Vector vMhs = (Vector)li.next();
			ListIterator liMhs = vMhs.listIterator();
			int baru=0,pindahan=0,laki=0,wanita=0;
			String kdjen = null;
			while(liMhs.hasNext()) {
				brs = (String)liMhs.next();
				System.out.println("brsiis="+brs);
				st = new StringTokenizer(brs,"#&");
				kdjen = st.nextToken();
				String npmhs = st.nextToken();
				String nmmhs = st.nextToken();
				String stpid = st.nextToken();
				String kdjek = st.nextToken();
				String kode_kmp = st.nextToken();
				double unapproved =Double.parseDouble(st.nextToken());
				double approved = Double.parseDouble(st.nextToken());
				if(kdpst.equalsIgnoreCase(target_kdpst)) {
					if(stpid.equalsIgnoreCase("B")) {
						stpid = "BARU";
					}
					else {
						if(stpid.equalsIgnoreCase("P")) {
							stpid = "PINDAHAN";
						}
					}
					if(kdjek.equalsIgnoreCase("L")) {
						kdjek = "PRIA";
					}
					else {
						if(kdjek.equalsIgnoreCase("P")) {
							kdjek="WANITA";
						}
					}
			
					if(kdjen==null) {
						kdjen = Converter.getKdjen(kdpst);
					}
					if(kdjen!=null && (kdjen.equalsIgnoreCase("A")||kdjen.equalsIgnoreCase("B")||kdjen.equalsIgnoreCase("C")||kdjen.equalsIgnoreCase("D")||kdjen.equalsIgnoreCase("E"))) {
						i++;
				
		%>


		<tr>
			<td style="background:#d9e1e5;color:#000;text-align:center"><label><B><%=i %></B></label></td>
			<td style="background:#d9e1e5;color:#000;text-align:center"><label><B><%=npmhs %></B></label></td>
			<td style="background:#d9e1e5;color:#000;text-align:left"><label><B><%=nmmhs %></B></label></td>
			<td style="background:#d9e1e5;color:#000;text-align:center"><label><B></B> </label></td>
			<td style="background:#d9e1e5;color:#000;text-align:center"><label><B></B> </label></td>
			<td style="background:#d9e1e5;color:#000;text-align:center"><label><B><%=stpid %></B> </label></td>
			<td style="background:#d9e1e5;color:#000;text-align:center"><label><B><%=kdjek %></B> </label></td>
		<% 
        if(allowViewKeu) {
        	if(approved==(approved+unapproved)) {
        %>
            	<td style="background:#d9e1e5;color:#000;text-align:center"><label><B><%=NumberFormater.indoNumberFormat(""+approved) %></B> </label></td>
        <%		
        	}
        	else {
        %>
        	<td style="background:#d9e1e5;color:#000;text-align:center"><label><B><%=NumberFormater.indoNumberFormat(""+approved) %>/<%=NumberFormater.indoNumberFormat(""+(approved+unapproved)) %></B> </label></td>
        <%
        	}
        }
        %>
		</tr>
		<%
					}
				}
			}
		}
	}	
		%>
	</table>
	</div>
</div>	
</body>
</html>