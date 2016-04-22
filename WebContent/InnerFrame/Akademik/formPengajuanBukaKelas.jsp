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
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	Vector vListMakul = (Vector)request.getAttribute("vListMakul"); 
	if(vListMakul==null) {
		vListMakul = (Vector)session.getAttribute("vListMakul"); 
	}
	request.removeAttribute("vListMakul"); 
	session.setAttribute("vListMakul", vListMakul);
	String infoKur = request.getParameter("infoKur"); 
	if(infoKur.contains("||")) {
		infoKur = infoKur.replace("||", "#&");
	}
	else {
		if(infoKur.contains("##")) {
			infoKur = infoKur.replace("##", "#&");
		}
	}
	String tknShift = request.getParameter("tknShift");
	String kdpst_nmpst = request.getParameter("kdpst_nmpst");
	String kdjen = request.getParameter("kdjen");
	boolean atFormPengajuanBukaKelasTahap1 = true;
	boolean atFormPengajuanBukaKelasTahap2 = false;
	String backward2 = null;
	String kelasTambahan = request.getParameter("kelasTambahan");
	String kodeKampus = request.getParameter("kodeKampus");
	//String atMenu = request.getParameter("atMenu");
%>

<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
</head>
<body>
<div id="header">
<%@ include file="IndexAkademikPengajuanSubMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		
		<%
		
		if(infoKur!=null) {
			StringTokenizer st = new StringTokenizer(infoKur,"#&");
			String idkur = st.nextToken();
			String nmkur = st.nextToken();
			String skstt = st.nextToken();
			String smstt = st.nextToken();
			String start = st.nextToken();
			String ended = st.nextToken();
			String targt = st.nextToken();
		%>
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:550px">
				<tr>
	        		<td style="background:#369;color:#fff;text-align:center" colspan="2"><label><B>INFO KURIKULUM</B> </label></td>
	        	</tr>	
				<tr>
	        		<td style="background:#369;color:#fff;text-align:center"><label><B>NAMA / KODE KURIKULUM</B> </label></td>
	        		<td style="color:#000;text-align:left"><label><B><%=nmkur %></B> </label></td>
	        	</tr>
	        	<tr>
	        		<td style="background:#369;color:#fff;text-align:center"><label><B>TOTAL SKS / SMS KURIKULUM</B> </label></td>
	        		<td style="color:#000;text-align:left"><label><B><%=skstt %> sks / <%=smstt %> sms</B> </label></td>
	        	</tr>
	        	<tr>
	        		<td style="background:#369;color:#fff;text-align:center"><label><B>MULAI BERLAKU / BERAKHIR</B> </label></td>
	        		<td style="color:#000;text-align:left"><label><B><%=Converter.convertThsmsKeterOnly(start) %> / <%=Converter.convertThsmsKeterOnly(ended) %></B> </label></td>
	        	</tr>
	        	<!--  tr>
	        		<td style="background:#369;color:#fff;text-align:center"><label><B>BERLAKU UTK ANGKATAN</B> </label></td>
	        		<td style="color:#000;text-align:left"><label><B>
	        		<%
	        		st = new StringTokenizer(targt,",");
	        		if(st.countTokens()>1) {
	        			int i=0;
	        			while(st.hasMoreTokens()) {
	        				i++;
	        				if(i<6) {
	        					out.print(st.nextToken()+", ");
	        				}
	        				else {
	        					i=0;
	        					out.print(st.nextToken()+"<br/>");
	        				}
	        			}
	        		}
	        		%></B> </label></td>
	        	</tr-->
	    </table>
	    <br/>
	    <br/>  
	    
	    
		<%	
		}
		if(vListMakul!=null && vListMakul.size()>0) {
			ListIterator li = vListMakul.listIterator();
			int sksem=0;
			if(li.hasNext()) {
				String brs = (String)li.next();
				StringTokenizer st = new StringTokenizer(brs,"#&");
				String prev_semes=st.nextToken();
				String prev_kdpst=st.nextToken();
				String prev_kdkmk=st.nextToken();
				String prev_nakmk=st.nextToken();
				String prev_skstm=st.nextToken();
				String prev_skspr=st.nextToken();
				String prev_skslp=st.nextToken();
				String prev_skstt=st.nextToken();
				String prev_idkmk=st.nextToken();
				//prev_sms = ""+prev_semes;
				sksem = sksem+(Integer.valueOf(prev_skstt).intValue());
			%>
			<form action="formPengajuanBukaKelasTahap2.jsp" method="post">
			<input type="hidden" name="kelasTambahan" value="<%=kelasTambahan %>" />  
			<input type="hidden" name="kodeKampus" value="<%=kodeKampus %>" />  
			<input type="hidden" name="tknShift" value="<%=tknShift %>" />
			<input type="hidden" name="infoKur" value="<%=infoKur %>" />
			<input type="hidden" name="kdjen" value="<%=kdjen %>" />
			<input type="hidden" name="kdpst_nmpst" value="<%=kdpst_nmpst %>" />
	    	<input type="hidden" name="cmd" value="bukaKelas" />
	    	<input type="hidden" name="atMenu" value="bukaKelas" />
	    	<input type="hidden" name="callerPage" value="<%=callerPage %>" />
			<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:750px">
				<tr>
	        		<td style="background:#369;color:#fff;text-align:center" colspan="4"><label><B>SEMESTER <%=prev_semes %></B> </label></td>
	        	</tr>	
				<tr>
	        		<td style="background:#369;color:#fff;text-align:center;width:125px"><label><B>KODE MK</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center;width:350px"><label><B>MATAKULIAH</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center;width:25px"><label><B>SKS</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center;width:250px"><label><B>JUMLAH KELAS PARALEL / SHIFT</B> </label></td>
	        		<!--  td style="background:#369;color:#fff;text-align:center;width:75px"><label><B>TOT KELAS</B> </label></td -->
	        	</tr>
	        	<tr>
	        		<td style="color:#000;text-align:center"><label><B><%=prev_kdkmk %></B> </label></td>
	        		<td style="color:#000;text-align:left"><label><B><%=prev_nakmk.toUpperCase() %></B> </label></td>
	        		<td style="color:#000;text-align:center"><label><B><%=prev_skstt %></B> </label></td>
	        		<td style="color:#000;text-align:left" colspan="2"><B>
	        		<%
	        		st = new StringTokenizer(tknShift,"#");
	        		String uniqKeter = null;
        			String shift = null;
        			String hari = null;
        			String tknKdjen = null;
        			String keterTampil = null;
	        		while(st.hasMoreTokens()) {
	        			uniqKeter = st.nextToken();
	        			shift = st.nextToken();
	        			hari = st.nextToken();
	        			tknKdjen = st.nextToken();
	        			keterTampil = st.nextToken();
	        			if(!uniqKeter.equalsIgnoreCase("N/A")) {	
	        		%>
	        			<input type="text" name="totKls" value="0" style="text-align:center;width:20px;"/>
	        			<input type="hidden" name="klsInfo" value="<%=prev_kdkmk+"||"+prev_nakmk.toUpperCase()+"||"+uniqKeter+"||"+prev_idkmk%>" style="text-align:center;" read only/>
	        			<%= keterTampil%><br>
	        		<%	
	        			}
	        		}
	        		%>	
	        		</B></td>
	        	</tr>	
			<%
				if(!li.hasNext()) {
				//cuma satu record
				//hitung total sks & tutup table
			%>
				<tr>
		      		<td style="background:#369;color:#fff;text-align:left" colspan="2"><label><B>TOTAL SKS</B> </label></td>
			   		<td style="background:#369;color:#fff;text-align:center"><label><B><%=sksem %></B> </label></td>
			   		<td style="background:#369;color:#fff;text-align:center" colspan="1"><label><B></B> </label></td>
		      	</tr>
		    </table>
		    <%
				}
				else {
					while(li.hasNext()) {
						brs = (String)li.next();
						st = new StringTokenizer(brs,"#&");
						String semes=st.nextToken();
						String kdpst=st.nextToken();
						String kdkmk=st.nextToken();
						String nakmk=st.nextToken();
						String skstm=st.nextToken();
						String skspr=st.nextToken();
						String skslp=st.nextToken();
						String skstt=st.nextToken();
						String idkmk=st.nextToken();
						if(prev_semes.equals(semes)) {
						//	prev_semes=""semes;
							sksem = sksem+(Integer.valueOf(skstt).intValue());
						%>
				<tr>
	        		<td style="color:#000;text-align:center"><label><B><%=kdkmk %></B> </label></td>
	        		<td style="color:#000;text-align:left"><label><B><%=nakmk.toUpperCase() %></B> </label></td>
	        		<td style="color:#000;text-align:center"><label><B><%=skstt %></B> </label></td>
	        		<td style="color:#000;text-align:left"  colspan="1"><B>
	        		<%
	        		st = new StringTokenizer(tknShift,"#");
	        		uniqKeter = null;
        			shift = null;
        			hari = null;
        			tknKdjen = null;
        			keterTampil = null;
	        		while(st.hasMoreTokens()) {
	        			uniqKeter = st.nextToken();
	        			shift = st.nextToken();
	        			hari = st.nextToken();
	        			tknKdjen = st.nextToken();
	        			keterTampil = st.nextToken();
	        			if(!uniqKeter.equalsIgnoreCase("N/A")) {
	        		%>
	        			<input type="text" name="totKls" value="0" style="text-align:center;width:20px;"/>
	        			<input type="hidden" name="klsInfo" value="<%=kdkmk+"||"+nakmk.toUpperCase()+"||"+uniqKeter+"||"+idkmk%>" style="text-align:center;" read only/>
	        			<%= keterTampil%><br>
	        		<%	
	        			
	        			}
	        		}
	        		%>	
	        		</B> </td>
	        	</tr>
						<%
						}
						else {		
							//pergantian thsms
							//tutup table & create new Table
						%>
				<tr>
		      		<td style="background:#369;color:#fff;text-align:left" colspan="2"><label><B>TOTAL SKS</B> </label></td>
			   		<td style="background:#369;color:#fff;text-align:center"><label><B><%=sksem %></B> </label></td>
			   		<td style="background:#369;color:#fff;text-align:center" colspan="1"><label><B></B> </label></td>
		      	</tr>
		    </table>
		    <br/>
		    <br/>
		    <%
		    prev_semes = ""+semes;
		    sksem = (Integer.valueOf(skstt).intValue());
		    %>
		  	<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:750px">	
				<tr>
	        		<td style="background:#369;color:#fff;text-align:center" colspan="5"><label><B>SEMESTER <%=prev_semes %></B> </label></td>
	        	</tr>	
				<tr>
	        		<td style="background:#369;color:#fff;text-align:center;width:150px"><label><B>KODE MK</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center;width:375px"><label><B>MATAKULIAH</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center;width:25px"><label><B>SKS</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center;width:250px"><label><B>JUMLAH KELAS PARALEL / SHIFT</B> </label></td>    	
	        	</tr>
	        	<tr>
	        		<td style="color:#000;text-align:center"><label><B><%=kdkmk %></B> </label></td>
	        		<td style="color:#000;text-align:left"><label><B><%=nakmk.toUpperCase() %></B> </label></td>
	        		<td style="color:#000;text-align:center"><label><B><%=skstt %></B> </label></td>
	        		<td style="color:#000;text-align:left"  colspan="1"><B>
	        		<%
	        		st = new StringTokenizer(tknShift,"#");
	        		uniqKeter = null;
        			shift = null;
        			hari = null;
        			tknKdjen = null;
        			keterTampil = null;
	        		while(st.hasMoreTokens()) {
	        			uniqKeter = st.nextToken();
	        			shift = st.nextToken();
	        			hari = st.nextToken();
	        			tknKdjen = st.nextToken();
	        			keterTampil = st.nextToken();
	        			if(!uniqKeter.equalsIgnoreCase("N/A")) {
	        		%>
	        			<input type="text" name="totKls" value="0" style="text-align:center;width:20px;"/>
	        			<input type="hidden" name="klsInfo" value="<%=kdkmk+"||"+nakmk.toUpperCase()+"||"+uniqKeter+"||"+idkmk%>" style="text-align:center;" read only/>
	        			<%= keterTampil%><br>
	        		<%	
	        			}
	        		}
	        		%>	
	        		</B></td>
	        	</tr>
							<%
						}
						if(!li.hasNext()) {
				%>
				<tr>
		     		<td style="background:#369;color:#fff;text-align:left" colspan="2"><label><B>TOTAL SKS</B> </label></td>
			   		<td style="background:#369;color:#fff;text-align:center"><label><B><%=sksem %></B> </label></td>
			   		<td style="background:#369;color:#fff;text-align:center" colspan="1"><label><B></B> </label></td>
		      	</tr>
		    </table>
					    <%
						}
					}	
				}	
			}
		%>
		</table>
		<br/>
		<div style="text-align:center"><input type="submit" value="Next" style="width:500px;height:35px"/></div>
		</form>
		<%	
		}
		else {
			if(vListMakul==null) {
			%>
			<h2>BELUM MEMILIKI KURIKULUM</h2>
			<%
			}
		}
		%>
		
		<!-- Column 1 start -->
	</div>
</div>			
</body>
</html>