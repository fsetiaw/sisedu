<!DOCTYPE html>
<head>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.*" %>
<%@ page import="beans.sistem.*" %>
<%@ page import="beans.setting.*" %>
<title>Insert title here</title>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%
	request.setAttribute("atPage", "listKuitansi");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	SearchDb sdb = new SearchDb();
	Vector v = (Vector)request.getAttribute("vListKui");
	String x = request.getParameter("x");
	String y = request.getParameter("y");
	String hasNext = request.getParameter("hasNext");
	String hasPrev = request.getParameter("hasPrev");
	String thn = request.getParameter("tahun");
	String bln = request.getParameter("bulan");
	String filterTgl = request.getParameter("filterTgl");
%>


</head>
<body>
<div id="header">
	<%@ include file="menuKeu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		
		<%
		//String thn = request.getParameter("tahun");
		//String bln = request.getParameter("bulan");
		if(thn==null && bln==null) {
			
			thn = AskSystem.getCurrentYear();
			bln = AskSystem.getCurrentMonth();
			
		}
		else {
			if(thn!=null && bln==null) {
				try {
					int i = Integer.valueOf(thn).intValue();
					thn = ""+i;
					bln = "01";
				}
				catch (Exception e) {
					thn = AskSystem.getCurrentYear();
					bln = AskSystem.getCurrentMonth();
				}
				
			}	
			else {
				if(thn!=null && bln!=null) {
					try {
						int i = Integer.valueOf(thn).intValue();
						thn = ""+i;
						i = Integer.valueOf(bln).intValue();
						bln = ""+i;
					}
					catch (Exception e) {
						thn = AskSystem.getCurrentYear();
						bln = AskSystem.getCurrentMonth();
					}
				}
			}
		}
		
		
		if(v!=null && v.size()>0) {
			if(Boolean.valueOf(hasPrev).booleanValue()) {
				int startAt = Integer.valueOf(x).intValue() - Integer.valueOf(y).intValue();
				if(startAt<0) {
					startAt=0;
				}
			%>
				<div align="center">
						<a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=<%=bln %>&x=<%=startAt %>&y=<%=y%>&filterTgl=<%=filterTgl%>"><img alt="Client Logo" title="Client Logo" src="<%=Constants.getRootWeb() %>/images/icon/upArrow2.jpg" /></a>
				</div>
			<%		
			}
			
			ListIterator li = v.listIterator();
			if(li.hasNext()) {
			%>
			
			<form action="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui" >
			<table border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:96%">
			<tr>
        		<td colspan="12" style="background:#369;color:#fff;text-align:center"><label><B>LIST KUITANSI TAHUN 
        			<input type="text" value="<%=thn%>" name="tahun" style="width:50px;font:bold;text-align:center"/> <input type="submit" value="Apply Filter" />
        			<br/>
        			<div syle="font-style:italic;font-size:.8em">Filter Berdasarkan : &nbsp&nbsp&nbsp&nbsp&nbsp
        			<select name="filterTgl" style="width:145px">
        			<%
        			if(filterTgl!=null && filterTgl.equalsIgnoreCase("tglkui")) {
        				%>
        				<option value="tglkui" selected="selected"> Tgl Cetak Kuitansi</option>
        				<option value="tgltrs" > Tgl Transaksi Bank</option>
        				<%	
        			}
        			else {
        				if(filterTgl!=null && filterTgl.equalsIgnoreCase("tgltrs")) {
        					%>
        					<option value="tglkui" > Tgl Cetak Kuitansi</option>
        					<option value="tgltrs" selected="selected"> Tgl Transaksi Bank</option>
        					<%
        				}
        				else {
        					%>
        					<option value="tglkui" > Tgl Cetak Kuitansi</option>
        					<option value="tgltrs" > Tgl Transaksi Bank</option>
        					<%
        				}
        			}
        			%>
        				
        			</select></div> 
        			<input type="hidden" name="x" value="0" />
        			<input type="hidden" name="y" value="11" />
        			</B> </label>
        		</td>
        	</tr>
        	<tr>
        	<%
        		if(bln.equalsIgnoreCase("1")||bln.equalsIgnoreCase("01")) {
        	%>
        		<td style="background:#d9e1e5;color:#fff;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=1&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">JAN</a></B></td>
        	<%
        		}
        		else {
        			%>
            		<td style="background:#fff;color:#369;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=1&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">JAN</a></B></td>
            	<%
        		}
        	%>	
        	<%
        		if(bln.equalsIgnoreCase("2")||bln.equalsIgnoreCase("02")) {
        	%>
        		<td style="background:#d9e1e5;color:#fff;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=2&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">FEB</a></B></td>
        	<%
        		}
        		else {
        			%>
            		<td style="background:#fff;color:#369;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=2&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">FEB</a></B></td>
            	<%
        		}
        	%>	
        	<%
        		if(bln.equalsIgnoreCase("3")||bln.equalsIgnoreCase("03")) {
        	%>
        		<td style="background:#d9e1e5;color:#fff;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=3&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">MAR</a></B></td>
        	<%
        		}
        		else {
        			%>
            		<td style="background:#fff;color:#369;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=3&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">MAR</a></B></td>
            	<%
        		}
        	%>	
        	<%
        		if(bln.equalsIgnoreCase("4")||bln.equalsIgnoreCase("04")) {
        	%>
        		<td style="background:#d9e1e5;color:#fff;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=4&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">APR</a></B></td>
        	<%
        		}
        		else {
        			%>
            		<td style="background:#fff;color:#369;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=4&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">APR</a></B></td>
            	<%
        		}
        	%>	
        	<%
        		if(bln.equalsIgnoreCase("5")||bln.equalsIgnoreCase("05")) {
        	%>
        		<td style="background:#d9e1e5;color:#fff;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=5&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">MEI</a></B></td>
        	<%
        		}
        		else {
        			%>
            		<td style="background:#fff;color:#369;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=5&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">MEI</a></B></td>
            	<%
        		}
        	%>	
        	<%
        		if(bln.equalsIgnoreCase("6")||bln.equalsIgnoreCase("06")) {
        	%>
        		<td style="background:#d9e1e5;color:#fff;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=6&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">JUN</a></B></td>
        	<%
        		}
        		else {
        			%>
            		<td style="background:#fff;color:#369;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=6&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">JUN</a></B></td>
            	<%
        		}
        	%>	
        	<%
        		if(bln.equalsIgnoreCase("7")||bln.equalsIgnoreCase("07")) {
        	%>
        		<td style="background:#d9e1e5;color:#fff;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=7&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">JUL</a></B></td>
        	<%
        		}
        		else {
        			%>
            		<td style="background:#fff;color:#369;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=7&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">JUL</a></B></td>
            	<%
        		}
        	%>	
        	<%
        		if(bln.equalsIgnoreCase("8")||bln.equalsIgnoreCase("08")) {
        	%>
        		<td style="background:#d9e1e5;color:#fff;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=8&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">AGU</a></B></td>
        	<%
        		}
        		else {
        			%>
            		<td style="background:#fff;color:#369;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=8&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">AGU</a></B></td>
            	<%
        		}
        	%>	
        	<%
        		if(bln.equalsIgnoreCase("9")||bln.equalsIgnoreCase("09")) {
        	%>
        		<td style="background:#d9e1e5;color:#fff;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=9&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">SEP</a></B></td>
        	<%
        		}
        		else {
        			%>
            		<td style="background:#fff;color:#369;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=9&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">SEP</a></B></td>
            	<%
        		}
        	%>	
        	<%
        		if(bln.equalsIgnoreCase("10")) {
        	%>
        		<td style="background:#d9e1e5;color:#fff;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=10&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">OKT</a></B></td>
        	<%
        		}
        		else {
        			%>
            		<td style="background:#fff;color:#369;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=10&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">OKT</a></B></td>
            	<%
        		}
        	%>	
        	<%
        		if(bln.equalsIgnoreCase("11")) {
        	%>
        		<td style="background:#d9e1e5;color:#fff;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=11&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">NOV</a></B></td>
        	<%
        		}
        		else {
        			%>
            		<td style="background:#fff;color:#369;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=11&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">NOV</a></B></td>
            	<%
        		}
        	%>	
        	<%
        		if(bln.equalsIgnoreCase("12")) {
        	%>
        		<td style="background:#d9e1e5;color:#fff;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=12&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">DES</a></B></td>
        	<%
        		}
        		else {
        			%>
            		<td style="background:#fff;color:#369;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=12&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">DES</a></B></td>
            	<%
        		}
        	%>	
        	</tr>
        	<tr>
        		<td style="background:#369;color:#fff;text-align:center"><B>NOKUI</B></td>
        		<td style="background:#369;color:#fff;text-align:center" colspan="1"><B>TGL KUI/TRS</B></td>
        		<td style="background:#369;color:#fff;text-align:center"><B>PRODI</B></td>
        		<td style="background:#369;color:#fff;text-align:center"><B>NO NPM</B></td>
        		<td style="background:#369;color:#fff;text-align:center"  colspan="3"><B>NAMA</B></td>
        		<td style="background:#369;color:#fff;text-align:center"  colspan="2"><B>KETER</B></td>
        		<td style="background:#369;color:#fff;text-align:center"  colspan="2"><B>JUMLAH</B></td>
        		<td style="background:#369;color:#fff;text-align:center"><B>STATUS</B></td>
        	</tr>
			<%	
				while(li.hasNext()) {
					String baris = (String)li.next();
					StringTokenizer st = new StringTokenizer(baris,",");
					String kuiid = st.nextToken();
					String kdpst = st.nextToken();
					String npmhs = st.nextToken();
					String nmmhs = st.nextToken();
					String norut = st.nextToken();
					String tgkui = st.nextToken();
					String tgtrs = st.nextToken();
					String keter = st.nextToken();
					String amont = st.nextToken();
					String voidd = st.nextToken();
					String noacc = st.nextToken();
				%>
				<tr>
					<td style="background:#fff;color:#369;text-align:center"><%=norut %></td>
        			<td style="background:#fff;color:#369;text-align:center" colspan="1">
        			<%
        			if(tgkui.equalsIgnoreCase(tgtrs) && noacc.equalsIgnoreCase("tunai")) {
        				out.print(tgkui+"<br/>Tunai");
        			}
        			else {
        				out.print(tgkui+"<br/>"+tgtrs);
        			}
        			%>
        			</td>
        			<td style="background:#fff;color:#369;text-align:center"><%=kdpst %></td>
        			<td style="background:#fff;color:#369;text-align:center"><%=npmhs %></B></td>
        			<td style="background:#fff;color:#369;text-align:left"  colspan="3"><%=nmmhs %></td>
        			<td style="background:#fff;color:#369;text-align:left"  colspan="2"><%=keter %></td>
        			<td style="background:#fff;color:#369;text-align:center"  colspan="2"><%=NumberFormater.indoNumberFormat(amont) %></td>
        		<%
        			if(voidd.equalsIgnoreCase("false")) {
        		%>	
        			<td style="background:#fff;color:#369;text-align:center">VALID</td>
        		<%
        			}
        			else {
        		%>	
        			<td style="background:#FF0000;color:#369;text-align:center">BATAL</td>
        		<%	
        			}
        		%>	
				</tr>		
				<%
				}
			}
			%>
			</table>
			</form>
			<%
			if(Boolean.valueOf(hasNext).booleanValue()) {	
				int startAt = Integer.valueOf(x).intValue()+(Integer.valueOf(y).intValue());
				
%>
		<div align="center">
    			<a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=<%=bln %>&x=<%=startAt %>&y=<%=y%>&filterTgl=<%=filterTgl%>"><img alt="Client Logo" title="Client Logo" src="<%=Constants.getRootWeb() %>/images/icon/downArrow2.jpg" /></a>
		</div>
<%		
			}
		}
		else {
			/*
			* TAMBAHAN INI DIGUNAKAN KARENA ERROR PAS PERUBAHAN TAHUN BARU DAB BLUM ADA TRANSAKSI NAMUN FORM FILTER TIDAK TAMPIL
			* diabawah ini duplicate form filter berdasarkan tahun seperti diatas, perubahan pada form filter dirubah juga diatas
			*/
		%>	
			<form action="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui" >
			<table border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:96%">
			<tr>
        		<td colspan="12" style="background:#369;color:#fff;text-align:center"><label><B>LIST KUITANSI TAHUN 
        			<input type="text" value="<%=thn%>" name="tahun" style="width:50px;font:bold;text-align:center"/> <input type="submit" value="Apply Filter" />
        			<br/>
        			<div syle="font-style:italic;font-size:.8em">Filter Berdasarkan : &nbsp&nbsp&nbsp&nbsp&nbsp
        			<select name="filterTgl" style="width:145px">
        			<%
        			if(filterTgl!=null && filterTgl.equalsIgnoreCase("tglkui")) {
        				%>
        				<option value="tglkui" selected="selected"> Tgl Cetak Kuitansi</option>
        				<option value="tgltrs" > Tgl Transaksi Bank</option>
        				<%	
        			}
        			else {
        				if(filterTgl!=null && filterTgl.equalsIgnoreCase("tgltrs")) {
        					%>
        					<option value="tglkui" > Tgl Cetak Kuitansi</option>
        					<option value="tgltrs" selected="selected"> Tgl Transaksi Bank</option>
        					<%
        				}
        				else {
        					%>
        					<option value="tglkui" > Tgl Cetak Kuitansi</option>
        					<option value="tgltrs" > Tgl Transaksi Bank</option>
        					<%
        				}
        			}
        			%>
        				
        			</select></div> 
        			<input type="hidden" name="x" value="0" />
        			<input type="hidden" name="y" value="11" />
        			</B> </label>
        		</td>
        	</tr>
        	<tr>
        	<%
        		if(bln.equalsIgnoreCase("1")||bln.equalsIgnoreCase("01")) {
        	%>
        		<td style="background:#d9e1e5;color:#fff;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=1&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">JAN</a></B></td>
        	<%
        		}
        		else {
        			%>
            		<td style="background:#fff;color:#369;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=1&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">JAN</a></B></td>
            	<%
        		}
        	%>	
        	<%
        		if(bln.equalsIgnoreCase("2")||bln.equalsIgnoreCase("02")) {
        	%>
        		<td style="background:#d9e1e5;color:#fff;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=2&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">FEB</a></B></td>
        	<%
        		}
        		else {
        			%>
            		<td style="background:#fff;color:#369;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=2&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">FEB</a></B></td>
            	<%
        		}
        	%>	
        	<%
        		if(bln.equalsIgnoreCase("3")||bln.equalsIgnoreCase("03")) {
        	%>
        		<td style="background:#d9e1e5;color:#fff;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=3&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">MAR</a></B></td>
        	<%
        		}
        		else {
        			%>
            		<td style="background:#fff;color:#369;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=3&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">MAR</a></B></td>
            	<%
        		}
        	%>	
        	<%
        		if(bln.equalsIgnoreCase("4")||bln.equalsIgnoreCase("04")) {
        	%>
        		<td style="background:#d9e1e5;color:#fff;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=4&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">APR</a></B></td>
        	<%
        		}
        		else {
        			%>
            		<td style="background:#fff;color:#369;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=4&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">APR</a></B></td>
            	<%
        		}
        	%>	
        	<%
        		if(bln.equalsIgnoreCase("5")||bln.equalsIgnoreCase("05")) {
        	%>
        		<td style="background:#d9e1e5;color:#fff;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=5&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">MEI</a></B></td>
        	<%
        		}
        		else {
        			%>
            		<td style="background:#fff;color:#369;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=5&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">MEI</a></B></td>
            	<%
        		}
        	%>	
        	<%
        		if(bln.equalsIgnoreCase("6")||bln.equalsIgnoreCase("06")) {
        	%>
        		<td style="background:#d9e1e5;color:#fff;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=6&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">JUN</a></B></td>
        	<%
        		}
        		else {
        			%>
            		<td style="background:#fff;color:#369;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=6&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">JUN</a></B></td>
            	<%
        		}
        	%>	
        	<%
        		if(bln.equalsIgnoreCase("7")||bln.equalsIgnoreCase("07")) {
        	%>
        		<td style="background:#d9e1e5;color:#fff;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=7&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">JUL</a></B></td>
        	<%
        		}
        		else {
        			%>
            		<td style="background:#fff;color:#369;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=7&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">JUL</a></B></td>
            	<%
        		}
        	%>	
        	<%
        		if(bln.equalsIgnoreCase("8")||bln.equalsIgnoreCase("08")) {
        	%>
        		<td style="background:#d9e1e5;color:#fff;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=8&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">AGU</a></B></td>
        	<%
        		}
        		else {
        			%>
            		<td style="background:#fff;color:#369;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=8&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">AGU</a></B></td>
            	<%
        		}
        	%>	
        	<%
        		if(bln.equalsIgnoreCase("9")||bln.equalsIgnoreCase("09")) {
        	%>
        		<td style="background:#d9e1e5;color:#fff;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=9&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">SEP</a></B></td>
        	<%
        		}
        		else {
        			%>
            		<td style="background:#fff;color:#369;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=9&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">SEP</a></B></td>
            	<%
        		}
        	%>	
        	<%
        		if(bln.equalsIgnoreCase("10")) {
        	%>
        		<td style="background:#d9e1e5;color:#fff;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=10&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">OKT</a></B></td>
        	<%
        		}
        		else {
        			%>
            		<td style="background:#fff;color:#369;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=10&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">OKT</a></B></td>
            	<%
        		}
        	%>	
        	<%
        		if(bln.equalsIgnoreCase("11")) {
        	%>
        		<td style="background:#d9e1e5;color:#fff;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=11&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">NOV</a></B></td>
        	<%
        		}
        		else {
        			%>
            		<td style="background:#fff;color:#369;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=11&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">NOV</a></B></td>
            	<%
        		}
        	%>	
        	<%
        		if(bln.equalsIgnoreCase("12")) {
        	%>
        		<td style="background:#d9e1e5;color:#fff;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=12&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">DES</a></B></td>
        	<%
        		}
        		else {
        			%>
            		<td style="background:#fff;color:#369;text-align:center;width:8%"><B><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Summary/view.listKui?tahun=<%=thn %>&bulan=12&x=0&y=11&filterTgl=<%=filterTgl%>" target="inner_iframe">DES</a></B></td>
            	<%
        		}
        	%>	
        	</tr>
        	<tr>
        		<td colspan="12" style="color:red;text-align:center;">
        			DATA TIDAK DITEMUKAN
        			
        		</td>
        	</tr>
        	</table>
        </form>	
     	<%
			//out.print("DATA TIDAK DUTEMUKAN");
		}
		%>
  	</div>
</div>  	
</body>
</html>