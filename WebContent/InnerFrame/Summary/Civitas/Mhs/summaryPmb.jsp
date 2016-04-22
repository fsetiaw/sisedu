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
	String thsms_pmb = Checker.getThsmsPmb();
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	Vector v = (Vector)session.getAttribute("vSummaryPMB");
	String target_thsms = request.getParameter("target_thsms");
	if(target_thsms==null || target_thsms.equalsIgnoreCase("null")) {
		target_thsms = ""+thsms_pmb;
	}
	Vector vListThsms = Tool.returnTokensListThsms("20001", thsms_pmb);
	

	
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
		int daftar_tt=0,ulang_tt=0,saring_tt=0,baru_tt=0,pindahan_tt=0,laki_tt=0,wanita_tt=0;
		int i=0;
		ListIterator li = v.listIterator();
	%>
		
	<table border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px">
		<form action="get.summaryPmb">
		<tr>
			<td style="background:#369;color:#fff;text-align:center" colspan="10">
        
        	<p style="font-weight:bold">PILIH TAHUN/SMS<select name="target_thsms">
        	<%
        	ListIterator li1 = vListThsms.listIterator();
        	while(li1.hasNext()) {
        		String thsms_tmp = (String)li1.next();
        		if(thsms_tmp.equalsIgnoreCase(target_thsms)) {
        		%>
        		<option value=<%=thsms_tmp %> selected><%=Converter.convertThsmsKeterOnly(thsms_tmp) %></option>
        		<%
        		}
        		else {
        		%>
            	<option value=<%=thsms_tmp %>><%=Converter.convertThsmsKeterOnly(thsms_tmp) %></option>
            	<%	
        		}
        	}
        	%>
        	</select>
        	<input type="submit" value=" <Klik> untuk Hitung Ulang" style="font-style:italic;"/>
        	</p>
			</td>
        </tr>	
        </form>
        <tr>
        	<td style="background:#369;color:#fff;text-align:center" rowspan="2"><label><B>NO.</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center" rowspan="2"><label><B>PROGRAM STUDI</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center" rowspan="2"><label><B>KAMPUS</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center" rowspan="2"><label><B>JUMLAH PENDAFTAR</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center" rowspan="2"><label><B>LULUS PENYARINGAN</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center" rowspan="2"><label><B>DAFTAR ULANG</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center" colspan="2"><label><B>TIPE MHS</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center" colspan="2"><label><B>JENIS KELAMIN</B> </label></td>	
        
        </tr>
		<tr>

			<td style="background:#369;color:#fff;text-align:center" style="width:100px"><label><B>BARU</B> </label></td>
			<td style="background:#369;color:#fff;text-align:center" style="width:100px"><label><B>PINDAH</B> </label></td>
			<td style="background:#369;color:#fff;text-align:center" style="width:100px"><label><B>PRIA</B> </label></td>
			<td style="background:#369;color:#fff;text-align:center" style="width:100px"><label><B>WANITA</B> </label></td>
		
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
			String nmpst = "";
			while(st.hasMoreTokens()) {
				String tmp = st.nextToken();
				if(!tmp.equalsIgnoreCase("MHS")) {
					nmpst = nmpst+tmp;
				}
				if(st.hasMoreTokens()) {
					nmpst=nmpst+" ";	
				}
			}

			Vector vMhs = (Vector)li.next();
			ListIterator liMhs = vMhs.listIterator();
			int baru=0,pindahan=0,laki=0,wanita=0;
			String kdjen = null;
			String kode_kmp = null;
			while(liMhs.hasNext()) {
				
				brs = (String)liMhs.next();
				st = new StringTokenizer(brs,"#&");
				kdjen = st.nextToken();
				if(kdjen==null) {
					kdjen = Converter.getKdjen(kdpst);
				}
				if(kdjen!=null && (kdjen.equalsIgnoreCase("A")||kdjen.equalsIgnoreCase("B")||kdjen.equalsIgnoreCase("C")||kdjen.equalsIgnoreCase("D")||kdjen.equalsIgnoreCase("E"))) {
					daftar_tt++;
				}
				String npmhs = st.nextToken();
				String nmmhs = st.nextToken();
				String stpid = st.nextToken();
				String kdjek = st.nextToken();
				kode_kmp = st.nextToken();
				if(stpid.equalsIgnoreCase("B")&&(kdjen.equalsIgnoreCase("A")||kdjen.equalsIgnoreCase("B")||kdjen.equalsIgnoreCase("C")||kdjen.equalsIgnoreCase("D")||kdjen.equalsIgnoreCase("E"))) {
					baru++;
					baru_tt++;
				}
				else {
					if(stpid.equalsIgnoreCase("P")&&(kdjen.equalsIgnoreCase("A")||kdjen.equalsIgnoreCase("B")||kdjen.equalsIgnoreCase("C")||kdjen.equalsIgnoreCase("D")||kdjen.equalsIgnoreCase("E"))) {
						pindahan++;
						pindahan_tt++;
					}
				}
				if(kdjek.equalsIgnoreCase("L")&&(kdjen.equalsIgnoreCase("A")||kdjen.equalsIgnoreCase("B")||kdjen.equalsIgnoreCase("C")||kdjen.equalsIgnoreCase("D")||kdjen.equalsIgnoreCase("E"))) {
					laki++;
					laki_tt++;
				}
				else {
					if(kdjek.equalsIgnoreCase("P")&&(kdjen.equalsIgnoreCase("A")||kdjen.equalsIgnoreCase("B")||kdjen.equalsIgnoreCase("C")||kdjen.equalsIgnoreCase("D")||kdjen.equalsIgnoreCase("E"))) {
						wanita++;
						wanita_tt++;
					}
				}
			}
			
			if(kdjen!=null && (kdjen.equalsIgnoreCase("A")||kdjen.equalsIgnoreCase("B")||kdjen.equalsIgnoreCase("C")||kdjen.equalsIgnoreCase("D")||kdjen.equalsIgnoreCase("E"))) {
				i++;
				
		%>
		<tr>
			<td style="background:#d9e1e5;color:#000;text-align:center"><label><B><%=i %></B></label></td>
			<td style="background:#d9e1e5;color:#000;text-align:left"><label><B><%=nmpst %> </B></label></td>
			<td style="background:#d9e1e5;color:#000;text-align:center"><label><B><%=kode_kmp %></B></label></td>
			<td style="background:#d9e1e5;color:#000;text-align:center"><label><B><a href="get.totKeuTiapMhs?forceGoTo=/InnerFrame/Summary/Civitas/Mhs/stdTampleteListMhs.jsp&atMenu=summaryPmbMenu&target_thsms=<%=target_thsms%>&target_nmpst=<%=nmpst%>&target_kdpst=<%=kdpst%>"><%=vMhs.size() %></a></B></label></td>
			<td style="background:#d9e1e5;color:#000;text-align:center"><label><B></B> </label></td>
			<td style="background:#d9e1e5;color:#000;text-align:center"><label><B></B> </label></td>
			<td style="background:#d9e1e5;color:#000;text-align:center"><label><B><%=baru %></B> </label></td>
			<td style="background:#d9e1e5;color:#000;text-align:center"><label><B><%=pindahan %></B> </label></td>
			<td style="background:#d9e1e5;color:#000;text-align:center"><label><B><%=laki %></B> </label></td>
			<td style="background:#d9e1e5;color:#000;text-align:center"><label><B><%=wanita %></B> </label></td>
		
		</tr>
		<%
			}
			System.out.println("nmpst = "+nmpst);
		}
		%>
		<tr>
        	<td style="background:#369;color:#fff;text-align:center" colspan="3"><label><B>TOTAL</B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center"><label><B><%=daftar_tt %></B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center"><label><B></B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center"><label><B></B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center"><label><B><%=baru_tt %></B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center"><label><B><%=pindahan_tt %></B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center"><label><B><%=laki_tt %></B> </label></td>
        	<td style="background:#369;color:#fff;text-align:center"><label><B><%=wanita_tt %></B> </label></td>
        
        </tr>
	</table>
	<%
	}
	//System.out.println("target_thsms = "+target_thsms);
	
	%>
	</div>
</div>	
</body>
</html>