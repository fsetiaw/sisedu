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
	System.out.println("form penialaian akhir");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	Vector vListNpmhsAndInfoNilai = (Vector) request.getAttribute("vListNpmhsAndInfoNilai");
	//tknCivitasInfo = kdpst+"`"+npmhs+"`"+nimhs+"`"+nmmhs+"`"+shift+"`"+kdjek+"`"+smawl+"`"+npmhs+"`"+nilai+"`"+nlakh+"`"+token_nilai-bobot;
	//(url+"?thsms="+thsms+"&idkmk="+idkmk+"&uniqueId="+uniqueId+"&kdkmk="+kdkmk+"&nakmk="+nakmk+"&shift="+shift)
	String thsms= request.getParameter("thsms");
	String idkmk=request.getParameter("idkmk");
	String uniqueId=request.getParameter("uniqueId");
	String kdkmk=request.getParameter("kdkmk");
	String nakmk=request.getParameter("nakmk");
	String shiftKelas=request.getParameter("shift");
	String bolehEdit=request.getParameter("bolehEdit");
	//System.out.println("bolehEdit="+bolehEdit);
	String nmmdos= request.getParameter("nmmdos");
	String npmdos= request.getParameter("npmdos");
	boolean editable = Boolean.parseBoolean(bolehEdit);
	boolean syDsnNya = false;
	if(npmdos.equalsIgnoreCase(validUsr.getNpm())) {
		syDsnNya = true;
	}
	//System.out.println("editable="+editable);
	//System.out.println("syDsnNya="+syDsnNya);
%>
</head>
<!--  body onload="location.href='#'" -->
<body>
<div id="header">
<ul>

	<!--  li><a href="get.apaYgBisaDinilai?atMenu=inputNilai" target="_self">GO<span>BACK</span></a></li -->
	<li><a href="getClasPol.statusKehadiranKelas?atMenu=inputNilai&bolehEditNilai=<%=bolehEdit %>" target="_self">GO<span>BACK</span></a></li>
</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!--  iframe name="hidden" src="hidden_comet" frameborder="0" height="0" width="100%"><iframe> <br/>
    	<iframe name="counter" src="count.html" frameborder="0" height="100%" width="100%"><iframe -->
		<!-- Column 1 start -->
		<%
		
		if(vListNpmhsAndInfoNilai!=null && vListNpmhsAndInfoNilai.size()>0) {
			//tknCivitasInfo = kdpst+"#&"+npmhs+"#&"+nimhs+"#&"+nmmhs+"#&"+shift+"#&"+kdjek+"#&"+smawl;
			ListIterator li = vListNpmhsAndInfoNilai.listIterator();
			String brs = (String)li.next();
			//System.out.println("info brs="+brs);
			StringTokenizer st = new StringTokenizer(brs,"`");
			//tknCivitasInfo = kdpst+"`"+npmhs+"`"+nimhs+"`"+nmmhs+"`"+shift+"`"+kdjek+"`"+smawl+"`"+npmhs+"`"+nilai+"`"+nlakh;
			//System.out.println("tknCivitasInfo=="+brs);
			String kdpst = st.nextToken();
			String npmhs = st.nextToken();
			String nimhs = st.nextToken();
			String nmmhs = st.nextToken();
			String shiftMhs = st.nextToken();
			String kdjek = st.nextToken();
			String smawl = st.nextToken();
			st.nextToken();//npmhs
			String nilai = st.nextToken();
			String nlakh = st.nextToken();
			String nlakhByDsn = st.nextToken();
			String nilai_bobot = st.nextToken();
			//System.out.println("nilai_bobot=="+nilai_bobot);
			int i=1;
			
		%>
		
		<br/>
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:600px">
		<%
	     	if(editable) {
	    %>
	    
	    <form action="proses.updateNilaiMk" method="POST">
	    <input type="hidden" name="info" value="<%=brs %>" />
	   	<input type="hidden" name=syDsnNya value="<%=syDsnNya %>" />
	    <input type="hidden" name="thsms" value="<%=thsms %>" />
	    <input type="hidden" name="idkmk" value="<%=idkmk %>" />
	    <input type="hidden" name="uniqueId" value="<%=uniqueId %>" />
	    <input type="hidden" name="kdkmk" value="<%=kdkmk %>" />
	    <input type="hidden" name="nakmk" value="<%=nakmk %>" />
	    <input type="hidden" name="npmdos" value="<%=npmdos %>" />
	    <input type="hidden" name="nmmdos" value="<%=nmmdos %>" />
	    <input type="hidden" name="shiftKelas" value="<%=shiftKelas %>" />

	    
	    <%
	    	}
	    %>
			<tr>
	       		<td style="background:#369;color:#fff;text-align:center" colspan="5"><label><h2>FORM PENILAIAN</h2><br/></h1><B><%=nakmk %>(<%=kdkmk %>) <br/> <%=shiftMhs %> <br/><%=nmmdos%></B> </label></td>
	       	</tr>	
			<tr>
	       		<td style="background:#369;color:#fff;text-align:center"><label><B>NO.</B> </label></td>
	       		<td style="background:#369;color:#fff;text-align:center"><label><B>NPM</B> </label></td>
	       		<td style="background:#369;color:#fff;text-align:center"><label><B>NAMA MAHASISWA</B> </label></td>
	       		<td style="background:#369;color:#fff;text-align:center"><label><B>NILAI AKHIR</B> </label></td>
	       	</tr>
	       	<tr>
	       		<td style="color:#000;text-align:center"><label><%=i++ %></label></td>
	       		<td style="color:#000;text-align:center"><label><a href="people.search?kword=<%=npmhs %>"><%=npmhs %></a></label></td>
	       		<td style="color:#000;text-align:center"><label><%=nmmhs %></label></td>
	       		<td style="color:#000;text-align:center"><label>
	       		<%
	       		if(editable) {
	       			if(syDsnNya || (nlakhByDsn.equalsIgnoreCase("false"))) {
	       		%>
	       			<select name="nlakh" style="width:100px;text-indent:40px;direction:ltr">
	       		<%
	       				st = new StringTokenizer(nilai_bobot,"-");
						while(st.hasMoreTokens()) {
							String nilaiTbbnl = st.nextToken();
							nilaiTbbnl = nilaiTbbnl.replace("tandaMin", "-");
							String bobotTbbnl = st.nextToken();
							if(nilaiTbbnl.equalsIgnoreCase(nlakh)) {
				%>
					<option value="<%=npmhs+","+nilaiTbbnl+","+bobotTbbnl%>" selected><%=nilaiTbbnl %></option>
				<%		
							}
							else {
				%>
					<option value="<%=npmhs+","+nilaiTbbnl+","+bobotTbbnl%>"><%=nilaiTbbnl %></option>
				<%	
							}
						}
	       		%>	
	       			</select>
	       		<%
	       			}
	       			else {
%>
		       		 	
		       			<select name="nlakh" style="width:100px;text-indent:40px;direction:ltr">
		       		<%
		       				st = new StringTokenizer(nilai_bobot,"-");
							while(st.hasMoreTokens()) {
								String nilaiTbbnl = st.nextToken();
								nilaiTbbnl = nilaiTbbnl.replace("tandaMin", "-");
								String bobotTbbnl = st.nextToken();
								if(nilaiTbbnl.equalsIgnoreCase(nlakh)) {
					%>
						<option value="<%=npmhs+","+nilaiTbbnl+","+bobotTbbnl%>" selected><%=nilaiTbbnl %></option>
					<%		
								}
								else {
					%>
						<!--  option disabled="disabled" value="<%=npmhs+","+nilaiTbbnl+","+bobotTbbnl%>"><%=nilaiTbbnl %></option -->
					<%	
								}
							}
		       		%>	
		       			</select>
		       		<%	
	       			}
	       		}
	       		else {	 
	       			out.print(nlakh);
	       		}
	       		%>
	       		</label></td>
	       	
	       	</tr>	
	       	
		<%	
			while(li.hasNext()) {
				//tknCivitasInfo = kdpst+"`"+npmhs+"`"+nimhs+"`"+nmmhs+"`"+shift+"`"+kdjek+"`"+smawl+"`"+npmhs+"`"+nilai+"`"+nlakh;
				brs = (String)li.next();
				//System.out.println("info brs="+brs);
				st = new StringTokenizer(brs,"`");
				kdpst = st.nextToken();
				npmhs = st.nextToken();
				nimhs = st.nextToken();
				nmmhs = st.nextToken();
				shiftMhs = st.nextToken();
				kdjek = st.nextToken();
				smawl = st.nextToken();
				st.nextToken();//npmhs
				nilai = st.nextToken();
				nlakh = st.nextToken();
				nlakhByDsn = st.nextToken();
				nilai_bobot = st.nextToken();
		%>	
			<tr>
	       		<td style="color:#000;text-align:center"><label><%=i++ %></label></td>
	       		<td style="color:#000;text-align:center"><label><a href="people.search?kword=<%=npmhs %>"><%=npmhs %></a></label></td>
	       		<td style="color:#000;text-align:center"><label><%=nmmhs %></label></td>
	       		<td style="color:#000;text-align:center"><label>
	       		<%
	       		if(editable) {
	       		%>
	       			<input type="hidden" name="info" value="<%=brs %>" />
	       		<%	
	       			if(syDsnNya || (nlakhByDsn.equalsIgnoreCase("false"))) {
	       		%>
	       		 	
	       			<select name="nlakh" style="width:100px;text-indent:40px;direction:ltr">
	       		<%
	       				st = new StringTokenizer(nilai_bobot,"-");
						while(st.hasMoreTokens()) {
							String nilaiTbbnl = st.nextToken();
							nilaiTbbnl = nilaiTbbnl.replace("tandaMin", "-");
							String bobotTbbnl = st.nextToken();
							if(nilaiTbbnl.equalsIgnoreCase(nlakh)) {
				%>
					<option value="<%=npmhs+","+nilaiTbbnl+","+bobotTbbnl%>" selected><%=nilaiTbbnl %></option>
				<%		
							}
							else {
				%>
					<option value="<%=npmhs+","+nilaiTbbnl+","+bobotTbbnl%>"><%=nilaiTbbnl %></option>
				<%	
							}
						}
	       		%>	
	       			</select>
	       		<%		
	       			}
	       			else {
	       				%>
		       		 	
		       			<select name="nlakh" style="width:100px;text-indent:40px;direction:ltr">
		       		<%
		       				st = new StringTokenizer(nilai_bobot,"-");
							while(st.hasMoreTokens()) {
								String nilaiTbbnl = st.nextToken();
								nilaiTbbnl = nilaiTbbnl.replace("tandaMin", "-");
								String bobotTbbnl = st.nextToken();
								if(nilaiTbbnl.equalsIgnoreCase(nlakh)) {
					%>
						<option value="<%=npmhs+","+nilaiTbbnl+","+bobotTbbnl%>" selected><%=nilaiTbbnl %></option>
					<%		
								}
								else {
					%>
						<!--  option disabled="disabled" value="<%=npmhs+","+nilaiTbbnl+","+bobotTbbnl%>"><%=nilaiTbbnl %></option -->
					<%	
								}
							}
		       		%>	
		       			</select>
		       		<%	
	       			}
	       		}	
	       		else {	 
	       			out.print(nlakh);
	       		}
	       		%>
	       		</label></td>
	    
	       	</tr>
		<%		
			}
			if(editable) {
			%>
			<tr>
	       		<td style="background:#369;color:#fff;text-align:center;height:35px" colspan="5">
	       		<input type="submit" value="UPDATE NILAI AKHIR" style="width:200px;height:25px"/>
	       		</td>
	       	</tr>	
			</form>	
			<%	
			}
		%>
		</table>
		<%
		}
		else {
		%>
		<h3 style="text-align=center">JUMLAH MAHASISWA DALAM LIST = 0 mhs </h3>
		<%
		}
		%>
		<!-- Column 1 start -->
	</div>
</div>			
</body>
</html>