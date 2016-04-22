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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
/*
* viewKurikulumStdTamplete (based on)
*/
	String tmp ="";
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	//String thsms_target="";
	Vector vGab = (Vector)session.getAttribute("vGab");
	Vector vListDsn = (Vector)session.getAttribute("vListDsn");
	//li.add(id_obj+"||"+npm+"||"+nmm);
	//System.out.println("vListDsnSize="+vListDsn.size());
	request.removeAttribute("vGab");
	request.removeAttribute("vListDsn");
%>


</head>
<body>
<div id="header">
<%@ include file="IndexAkademikPengajuanSubMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<br/>
		<div style="text-align:center;color:red;font-size:.8em">
		* BARIS DENGAN WARNA BACKGROUND PUTIH MENANDAKAN KELAS KOSONG/TIDAK ADA MHS YG TERDAFTAR
		</div>
		</br>
		<%
		try {
		%>
		<form action="go.updDsnAjar" method="POST">
		
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:750px">
        	<tr>
	    		<td style="background:#369;color:#fff;text-align:center" colspan="8"><label><B>DAFTAR KELAS PERKULIAHAN</B> </label></td>
	    	</tr>
    		<tr>
    			<td style="background:#369;color:#fff;text-align:center;width:25px">NO</td>
    			<td style="background:#369;color:#fff;text-align:center;width:150px">PRODI</td>
    			<td style="background:#369;color:#fff;text-align:center;width:150px">MATAKULIAH</td>
    			<td style="background:#369;color:#fff;text-align:center;width:50px">KODE MK</td>
    			<td style="background:#369;color:#fff;text-align:center;width:50px">KODE KAMPUS</td>
    			<td style="background:#369;color:#fff;text-align:center;width:75px">SHIFT</td>
    			<td style="background:#369;color:#fff;text-align:center;width:50px">KLS PLL</td>
    			<td style="background:#369;color:#fff;text-align:center;width:200px">DOSEN PENGAJAR</td>
    		</tr>
		<%	
			//if(listMkKelompok!=null) {
			if(vGab!=null && vGab.size()>0) {
				int i=0;
			
				ListIterator lig = vGab.listIterator();
				while(lig.hasNext()) {
					
				
					String brs = (String)lig.next();
					//System.out.println("$$"+brs);
				//nakmk1+"$"+idkmk1+"$"+idkur1+"$"+kdkmk1+"$"+thsms1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+initNpmInput1+"$"+latestNpmUpdate1+"$"+latesStatusInfo1+"$"+currAvailStatus1+"$"+locked1+"$"+npmdos1+"$"+nodos1+"$"+npmasdos1+"$"+noasdos1+"$"+canceled1+"$"+kodeKelas1+"$"+kodeRuang1+"$"+kodeGedung1+"$"+kodeKampus1+"$"+tknHrTime1+"$"+nmdos1+"$"+nmasdos1+"$"+enrolled1+"$"+maxEnrolled1+"$"+minEnrolled1+"$"+subKeterKdkmk1+"$"+initReqTime1+"$"+tknNpmApr1+"$"+tknAprTime1+"$"+targetTtmhs1+"$"+passed1+"$"+rejected1+"$"+kodeGabungan);
					StringTokenizer st = new StringTokenizer(brs,"$");
					String nakmk1=st.nextToken();
					String idkmk1=st.nextToken();
					String idkur1=st.nextToken();
					String kdkmk1=st.nextToken();
					String thsms1=st.nextToken();
					String kdpst1=st.nextToken();
					String shift1=st.nextToken();
					String norutKlsPll1=st.nextToken();
					String initNpmInput1=st.nextToken();
					String latestNpmUpdate1=st.nextToken();
					String latesStatusInfo1=st.nextToken();
					String currAvailStatus1=st.nextToken();
					String locked1=st.nextToken();
					String npmdos1=st.nextToken();
					String nodos1=st.nextToken();
					String npmasdos1=st.nextToken();
					String noasdos1=st.nextToken();
					String canceled1=st.nextToken();
					String kodeKelas1=st.nextToken();
					String kodeRuang1=st.nextToken();
					String kodeGedung1=st.nextToken();
					String kodeKampus1=st.nextToken();
					String tknHrTime1=st.nextToken();
					String nmdos1=st.nextToken();
					//System.out.println("nmdos1="+nmdos1);
					String nmasdos1=st.nextToken();
					String enrolled1=st.nextToken();
					String maxEnrolled1=st.nextToken();
					String minEnrolled1=st.nextToken();
					String subKeterKdkmk1=st.nextToken();
					String initReqTime1=st.nextToken();
					String tknNpmApr1=st.nextToken();
					String tknAprTime1=st.nextToken();
					String targetTtmhs1=st.nextToken();
					String passed1=st.nextToken();
					String rejected1=st.nextToken();
					String konsen1=st.nextToken();
					String nmpst1=st.nextToken();
					String kodeGabungan=st.nextToken();
					String cuid=st.nextToken();
					String empty=st.nextToken();
					if(empty.equalsIgnoreCase("true")) {
			%>
			<tr bgcolor="white">
			<%			
					}
					else {
						%>
			<tr>
						<%				
					}
			%>
				<td style="text-align:center;"><%=++i %></td>
    			<td style="text-align:center;"><%
    			out.print(nmpst1);
    			if(!Checker.isStringNullOrEmpty(konsen1)) {
    				out.print("<br/>Konsentrasi<br/>"+konsen1.toUpperCase());
    			}
    			%></td>
				<td style="text-align:left;"><%=nakmk1%></td>
    			<td style="text-align:center;"><%=kdkmk1 %></td>
    			<td style="text-align:center;"><%=kodeKampus1 %></td>
    			<td style="text-align:center;"><%=shift1 %></td>
    			<td style="text-align:center;"><%=norutKlsPll1 %></td>
    			<td style="text-align:center;">
    			<%
    			if(vListDsn!=null && vListDsn.size()>0) {
    				ListIterator lidos = vListDsn.listIterator();
    				boolean match = false;
    			%>
    				<select name="infoDos">
    			<%	
    				while(lidos.hasNext()) {
    					//nodos+"$"+npmdos+"$"+nidndos+"$"+noKtp+"$"+ptihbase+"$"+psthbase+"$"+nmdos+"$"+gelar+"$"+smawl+"$"+tknKdpstAjar+"$"+email+"$"+tknTelp+"$"+tknHp+"$"+status
    					String brs1 = (String)lidos.next();
    					////li.add(id_obj+"||"+npm+"||"+nmm);
    					//System.out.println("1-1="+brs1);
    					st = new StringTokenizer(brs1,"||");
    					while(st.hasMoreTokens()) {
    						//String nodos=st.nextToken();
    						String id_obj=st.nextToken();
    						String npmdos=st.nextToken();
    						//String nidndos=st.nextToken();
    						//String noKtp=st.nextToken();
    						//String ptihbase=st.nextToken();
    						//String psthbase=st.nextToken();
    						String nmdos=st.nextToken();
    						
    						//String gelar=st.nextToken();
    						//String smawl=st.nextToken();
    						//String tknKdpstAjar=st.nextToken();
    						//String email=st.nextToken();
    						//String tknTelp=st.nextToken();
    						//String tknHp=st.nextToken();
    						//String status=st.nextToken();
    						String value=""+idkur1+"$"+idkmk1+"$"+kdpst1+"$"+shift1+"$"+norutKlsPll1+"$"+brs1.replace("||", "$");;
    						//if(nmdos1.equalsIgnoreCase(nmdos)) {
    						if(npmdos1.equalsIgnoreCase(npmdos)) {
    							match = true;
    							
    							//System.out.println("valeu="+value);
    						%>
    					<option value="<%=value %>" selected><%=nmdos.toUpperCase() %></option>
    						<%
    						}
    						else {
    							%>
    	    			<option value="<%=value %>"><%=nmdos.toUpperCase() %></option>
    	    					<%	
    						}
    					}
    					
    				}
    				if(!match) {
					%>
    					<option value="null">--PILIH DOSEN AJAR--</option>
    				<%	
					}
					%>
					</select>
					<%    			
    			}
    			else {
    			%>
    				HARAP INPUT DOSEN VIA INPUT CIVITAS
    			<%	
    			}
    			%>
    			
    			</td>	
    			
    		</tr>
    		<%
				}
			}	
    		%>
		</table>
		<br/>
		<div align="center">
		<input type="submit" value="UPDATE DOSEN AJAR" name="perintah" style="width:500px;"/><br/><br/>
		
		</div>
		</form>
		
		<%	
		
		}
		catch (Exception e){
			//System.out.println("tmp="+tmp);
		}
		%>	
		<!-- Column 1 start -->
	</div>
</div>			
</body>
</html>