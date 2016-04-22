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
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%
//System.out.println("yap");
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
Vector v= null; 
String listTipeUjian = (String)session.getAttribute("listTipeUjian");
String targetThsms  = request.getParameter("targetThsms");
Vector vListKelasPerProdi = (Vector) session.getAttribute("vListKelasPerProdi");
session.removeAttribute("vListKelasPerProdi");
String tknScopeKampus = request.getParameter("tknScopeKampus");
//request.removeAttribute("listTipeUjian");
//String atMenu = request.getParameter("atMenu");
%>
<script type="text/javascript">
</script>
</head>
<body>
<div id="header">
<%@ include file="../innerMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
				

		<%
		/*
		ListIterator li13 = vListKelasPerProdi.listIterator();
		while(li13.hasNext()) {
			String judul_kdpst = null;
			out.println("0");
			Vector  vTmp = (Vector) li13.next();//vector ini isi nya kelas per prodi
			out.println("1");

			if(vTmp!=null && vTmp.size()>0) {
				if(vTmp!=null && vTmp.size()>0) {
					out.println("2a");
					ListIterator liTmp = vTmp.listIterator();
					if(liTmp.hasNext()) {
						String brs = (String)liTmp.next();
						System.out.println("2");
						StringTokenizer st = new StringTokenizer(brs,"||");
						if(st.countTokens()>1) {
							out.println("kelas gabungan- <br/>");
							boolean first = true;
							while(st.hasMoreTokens()) {
								String brs1 = st.nextToken();
								out.println("brs1- "+brs1+" <br/>");
								StringTokenizer st1 = new StringTokenizer(brs1,"`");
								String kode_gabung = ""+st1.nextToken();
								String nakmk = ""+st1.nextToken();
								String nopll = ""+st1.nextToken();
								String shift = ""+st1.nextToken();
								String idkur = ""+st1.nextToken();
								String idkmk = ""+st1.nextToken();
								String npmdos = ""+st1.nextToken();
								String nmmdos = ""+st1.nextToken();
								String kdkmk = ""+st1.nextToken();
								String cancel = ""+st1.nextToken();
								String kdpst = ""+st1.nextToken();
								String kodeKampus = ""+st1.nextToken();
								String listNpm = ""+st1.nextToken();
								StringTokenizer st2 = new StringTokenizer(listNpm,",");
								
								judul_kdpst = ""+kdpst;
							}	
						}
						else {
							out.println("kelas mandiri - "+brs+" <br/>");
							out.println("brs- "+brs+" <br/>");
						}
					}	
				}	
			}
		}
		*/	
		
		if(vListKelasPerProdi==null || vListKelasPerProdi.size()<1) {
			out.print("<h2>BELUM ADA DATA KELAS YANG DIAJUKAN</h2>");
		}
		else {
			ListIterator li1 = vListKelasPerProdi.listIterator();
			int i=0;
			while(li1.hasNext()) {
				String judul_kdpst = null;
				Vector vTmp = (Vector) li1.next();//vector ini isi nya kelas per prodi
				if(vTmp!=null && vTmp.size()>0) {
%>
<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px">
<%
					ListIterator liTmp = vTmp.listIterator();
					if(liTmp.hasNext()) {
						String brs = (String)liTmp.next();
						//System.out.println("brs="+brs);
						StringTokenizer st = new StringTokenizer(brs,"||");
						if(st.countTokens()>1) {
							//kelas gabungan
							boolean first = true;
							while(st.hasMoreTokens()) {
								String brs1 = st.nextToken();
								StringTokenizer st1 = new StringTokenizer(brs1,"`");
//kode_gabung+"`"+nakmk+"`"+nopll+"`"+shift+"`"+idkur+"`"+idkmk+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+cancel+"`"+kdpst;
								
								String kode_gabung = ""+st1.nextToken();
								String nakmk = ""+st1.nextToken();
								String nopll = ""+st1.nextToken();
								String shift = ""+st1.nextToken();
								String idkur = ""+st1.nextToken();
								String idkmk = ""+st1.nextToken();
								String npmdos = ""+st1.nextToken();
								String nmmdos = ""+st1.nextToken();
								String kdkmk = ""+st1.nextToken();
								String cancel = ""+st1.nextToken();
								String kdpst = ""+st1.nextToken();
								String kodeKampus = ""+st1.nextToken();
								String listNpm = ""+st1.nextToken();
								StringTokenizer st2 = new StringTokenizer(listNpm,",");
								
								judul_kdpst = ""+kdpst;
								if(first) {
									first = false;
									%>
	<tr>
        <td style="background:#369;color:#fff;text-align:center" colspan="8"><label><B>PRODI <%=Converter.getNamaKdpst(judul_kdpst)+" ("+Converter.getDetailKdjen(Checker.getKdjen(judul_kdpst))+")"%></B> </label></td>
    </tr>
    <tr>
        <td style="background:#369;color:#fff;text-align:center;width:75px"><label><B>KODE</B> </label></td>
        <td style="background:#369;color:#fff;text-align:left;width:250px"><label><B>MATAKULIAH</B> </label></td>
        <td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>KODE KAMPUS</B> </label></td>
        <td style="background:#369;color:#fff;text-align:center;width:75px"><label><B>SHIFT</B> </label></td>
        <td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>NO KLS PARALEL</B> </label></td>
        <td style="background:#369;color:#fff;text-align:center;width:200px"><label><B>NAMA DOSEN</B> </label></td>
       	<td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>#MHS</B> </label></td>
       	<td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>LIST MHS</B> </label></td>
 	</tr>
 	<tr>	
        <td style="color:#000;text-align:center;"><label><B><%=kdkmk %></B> </label></td>
        <td style="color:#000;text-align:left;"><label><B><%=nakmk %></B> </label></td>
        <td style="color:#000;text-align:center;"><label><B><%=kodeKampus %></B> </label></td>
        <td style="color:#000;text-align:center;"><label><B><%=shift %></B> </label></td>
        <td style="color:#000;text-align:center;"><label><B><%=nopll %></B> </label></td>
        <td style="color:#000;text-align:center;"><label><B><%=nmmdos %></B> </label></td>
        <td style="color:#000;text-align:center;"><label><B><%=st2.countTokens()%></B> </label></td>
        <td style="color:#000;text-align:center;"><label>
        <FORM action="get.getDaftarHadir" method="post">
        	<input type="hidden" name="targetThsms" value="<%=targetThsms %>" />
        	<input type="hidden" name="kelasInfo" value="<%=brs %>" />
        	<input type="submit" value="Next" style="width:99%;"/>
        </FORM>
        </td>
  	</tr>
									<%
									//out.print("judul kdpst = "+judul_kdpst+"<br/>");
									//out.print("kelas gabungan = "+nakmk+"<br/>");
								}
								else {
%>
 	<tr>	
        <td style="color:#000;text-align:center;"><label><B><%=kdkmk %></B> </label></td>
        <td style="color:#000;text-align:left;"><label><B><%=nakmk %></B> </label></td>
        <td style="color:#000;text-align:center;"><label><B><%=kodeKampus %></B> </label></td>
        <td style="color:#000;text-align:center;"><label><B><%=shift %></B> </label></td>
        <td style="color:#000;text-align:center;"><label><B><%=nopll %></B> </label></td>
        <td style="color:#000;text-align:center;"><label><B><%=nmmdos %></B> </label></td>
        <td style="color:#000;text-align:center;"><label><B><%=st2.countTokens()%></B> </label></td>
        <td style="color:#000;text-align:center;"><label>
        <FORM action="get.getDaftarHadir" method="post">
        	<input type="hidden" name="targetThsms" value="<%=targetThsms %>" />
        	<input type="hidden" name="kelasInfo" value="<%=brs %>" />
        	<input type="submit" value="Next" style="width:99%;"/>
        </FORM>
        </td>
  	</tr>
<%									
									//out.print("kelas gabungan = "+nakmk+"<br/>");
								}
							}
						}
						else {
							String brs1 = st.nextToken();
							//System.out.println("brs1="+brs1);
							StringTokenizer st1 = new StringTokenizer(brs1,"`");
							String kode_gabung = ""+st1.nextToken();
							String nakmk = ""+st1.nextToken();
							String nopll = ""+st1.nextToken();
							String shift = ""+st1.nextToken();
							String idkur = ""+st1.nextToken();
							String idkmk = ""+st1.nextToken();
							String npmdos = ""+st1.nextToken();
							String nmmdos = ""+st1.nextToken();
							String kdkmk = ""+st1.nextToken();
							String cancel = ""+st1.nextToken();
							String kdpst = ""+st1.nextToken();
							String kodeKampus = ""+st1.nextToken();
							String listNpm = ""+st1.nextToken();
							StringTokenizer st2 = new StringTokenizer(listNpm,",");
							judul_kdpst = ""+kdpst;
							%>
	<tr>
		<td style="background:#369;color:#fff;text-align:center" colspan="8"><label><B>PRODI <%= Converter.getNamaKdpst(judul_kdpst)+" ("+Converter.getDetailKdjen(Checker.getKdjen(judul_kdpst))+")"%></B> </label></td>
	</tr>
	<tr>
		<td style="background:#369;color:#fff;text-align:center;width:75px"><label><B>KODE</B> </label></td>
        <td style="background:#369;color:#fff;text-align:left;width:250px"><label><B>MATAKULIAH</B> </label></td>
        <td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>KODE KAMPUS</B> </label></td>
        <td style="background:#369;color:#fff;text-align:center;width:75px"><label><B>SHIFT</B> </label></td>
        <td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>NO KLS PARALEL</B> </label></td>
        <td style="background:#369;color:#fff;text-align:center;width:200px"><label><B>NAMA DOSEN</B> </label></td>
       	<td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>#MHS</B> </label></td>
       	<td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>LIST MHS</B> </label></td>
	</tr>
	<tr>	
        <td style="color:#000;text-align:center;"><label><B><%=kdkmk %></B> </label></td>
        <td style="color:#000;text-align:left;"><label><B><%=nakmk %></B> </label></td>
        <td style="color:#000;text-align:center;"><label><B><%=kodeKampus %></B> </label></td>
        <td style="color:#000;text-align:center;"><label><B><%=shift %></B> </label></td>
        <td style="color:#000;text-align:center;"><label><B><%=nopll %></B> </label></td>
        <td style="color:#000;text-align:center;"><label><B><%=nmmdos %></B> </label></td>
        <td style="color:#000;text-align:center;"><label><B><%=st2.countTokens()%></B> </label></td>
        <td style="color:#000;text-align:center;"><label>
        <FORM action="get.getDaftarHadir" method="post">
        	<input type="hidden" name="targetThsms" value="<%=targetThsms %>" />
        	<input type="hidden" name="kelasInfo" value="<%=brs %>" />
        	<input type="submit" value="Next" style="width:99%;"/>
        </FORM>
        </td>
  	</tr>
															<%							
						//	out.print("judul kdpst = "+judul_kdpst+"<br/>");
						//	out.print("kelas single = "+nakmk+"<br/>");
						}
						
						//out.print("nakmk = "+judul_kdpst+"<br/>");
						while(liTmp.hasNext()) {
							brs = (String)liTmp.next();
							
							st = new StringTokenizer(brs,"||");
							if(st.countTokens()>1) {
								//kelas gabungan
								while(st.hasMoreTokens()) {
									String brs1 = st.nextToken();
									//System.out.println("brs1="+brs1);
									StringTokenizer st1 = new StringTokenizer(brs1,"`");
									String kode_gabung = ""+st1.nextToken();
									String nakmk = ""+st1.nextToken();
									String nopll = ""+st1.nextToken();
									String shift = ""+st1.nextToken();
									String idkur = ""+st1.nextToken();
									String idkmk = ""+st1.nextToken();
									String npmdos = ""+st1.nextToken();
									String nmmdos = ""+st1.nextToken();
									String kdkmk = ""+st1.nextToken();
									String cancel = ""+st1.nextToken();
									String kdpst = ""+st1.nextToken();
									String kodeKampus = ""+st1.nextToken();
									String listNpm = ""+st1.nextToken();
									StringTokenizer st2 = new StringTokenizer(listNpm,",");
									//out.print("kelas gabungan = "+nakmk+"<br/>");
%>
	<tr>	
        <td style="color:#000;text-align:center;"><label><B><%=kdkmk %></B> </label></td>
        <td style="color:#000;text-align:left;"><label><B><%=nakmk %></B> </label></td>
        <td style="color:#000;text-align:center;"><label><B><%=kodeKampus %></B> </label></td>
        <td style="color:#000;text-align:center;"><label><B><%=shift %></B> </label></td>
        <td style="color:#000;text-align:center;"><label><B><%=nopll %></B> </label></td>
        <td style="color:#000;text-align:center;"><label><B><%=nmmdos %></B> </label></td>
        <td style="color:#000;text-align:center;"><label><B><%=st2.countTokens()%></B> </label></td>
        <td style="color:#000;text-align:center;"><label>
        <FORM action="get.getDaftarHadir" method="post">
        	<input type="hidden" name="targetThsms" value="<%=targetThsms %>" />
        	<input type="hidden" name="kelasInfo" value="<%=brs %>" />
        	<input type="submit" value="Next" style="width:99%;"/>
        </FORM>
        </td>
  	</tr>
<%									
								}
							}
							else {
								String brs1 = st.nextToken();
								//System.out.println("brs1="+brs1);
								StringTokenizer st1 = new StringTokenizer(brs1,"`");
								String kode_gabung = ""+st1.nextToken();
								String nakmk = ""+st1.nextToken();
								String nopll = ""+st1.nextToken();
								String shift = ""+st1.nextToken();
								String idkur = ""+st1.nextToken();
								String idkmk = ""+st1.nextToken();
								String npmdos = ""+st1.nextToken();
								String nmmdos = ""+st1.nextToken();
								String kdkmk = ""+st1.nextToken();
								String cancel = ""+st1.nextToken();
								String kdpst = ""+st1.nextToken();
								String kodeKampus = ""+st1.nextToken();
								String listNpm = ""+st1.nextToken();
								StringTokenizer st2 = new StringTokenizer(listNpm,",");
								//out.print("kelas single = "+nakmk+"<br/>");
%>
	<tr>	
        <td style="color:#000;text-align:center;"><label><B><%=kdkmk %></B> </label></td>
        <td style="color:#000;text-align:left;"><label><B><%=nakmk %></B> </label></td>
        <td style="color:#000;text-align:center;"><label><B><%=kodeKampus %></B> </label></td>
        <td style="color:#000;text-align:center;"><label><B><%=shift %></B> </label></td>
        <td style="color:#000;text-align:center;"><label><B><%=nopll %></B> </label></td>
        <td style="color:#000;text-align:center;"><label><B><%=nmmdos %></B> </label></td>
        <td style="color:#000;text-align:center;"><label><B><%=st2.countTokens()%></B> </label></td>
        <td style="color:#000;text-align:center;"><label>
        <FORM action="get.getDaftarHadir" method="post">
        	<input type="hidden" name="targetThsms" value="<%=targetThsms %>" />
        	<input type="hidden" name="kelasInfo" value="<%=brs %>" />
        	<input type="submit" value="Next" style="width:99%;"/>
        </FORM>
        </td>
  	</tr>	
<%								
							}
						}	
					}
%>
</table>
<br/>
<%	
				}
			}
		}
		%>
		<!-- Column 1 end -->
		
	</div>
</div>

</body>
</html>