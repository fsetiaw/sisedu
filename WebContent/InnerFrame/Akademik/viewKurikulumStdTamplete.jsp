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
	//System.out.println("beneran");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	Vector vListMakul = (Vector)request.getAttribute("vListMakul"); 
	String infoKur = request.getParameter("infoKur"); 
	//String cmd = request.getParameter("cmd");
	//String atMenu = request.getParameter("atMenu");
%>


</head>
<body>
<div id="header">
<%@ include file="IndexAkademikSubMenu.jsp" %>
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
			//String prev_sms = "";
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
				//prev_sms = ""+prev_semes;
				sksem = sksem+(Integer.valueOf(prev_skstt).intValue());
			%>
			<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:550px">
				<tr>
	        		<td style="background:#369;color:#fff;text-align:center" colspan="3"><label><B>SEMESTER <%=prev_semes %></B> </label></td>
	        	</tr>	
				<tr>
	        		<td style="background:#369;color:#fff;text-align:center"><label><B>KODE MK</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center"><label><B>MATAKULIAH</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center"><label><B>SKS</B> </label></td>
	        	</tr>
	        	<tr>
	        		<td style="color:#000;text-align:center"><label><B><%=prev_kdkmk %></B> </label></td>
	        		<td style="color:#000;text-align:center"><label><B><%=prev_nakmk %></B> </label></td>
	        		<td style="color:#000;text-align:center"><label><B><%=prev_skstt %></B> </label></td>
	        	</tr>	
			<%
				if(!li.hasNext()) {
				//cuma satu record
				//hitung total sks & tutup table
			%>
				<tr>
		      		<td style="background:#369;color:#fff;text-align:left" colspan="2"><label><B>TOTAL SKS</B> </label></td>
			   		<td style="background:#369;color:#fff;text-align:center"><label><B><%=sksem %></B> </label></td>
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
						if(prev_semes.equals(semes)) {
						//	prev_semes=""semes;
							sksem = sksem+(Integer.valueOf(skstt).intValue());
						%>
				<tr>
	        		<td style="color:#000;text-align:center"><label><B><%=kdkmk %></B> </label></td>
	        		<td style="color:#000;text-align:center"><label><B><%=nakmk %></B> </label></td>
	        		<td style="color:#000;text-align:center"><label><B><%=skstt %></B> </label></td>
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
		      	</tr>
		    </table>
		    <br/>
		    <br/>
		    <%
		    prev_semes = ""+semes;
		    sksem = (Integer.valueOf(skstt).intValue());
		    %>
		  	<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:550px">	
				<tr>
	        		<td style="background:#369;color:#fff;text-align:center" colspan="3"><label><B>SEMESTER <%=prev_semes %></B> </label></td>
	        	</tr>	
				<tr>
	        		<td style="background:#369;color:#fff;text-align:center"><label><B>KODE MK</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center"><label><B>MATAKULIAH</B> </label></td>
	        		<td style="background:#369;color:#fff;text-align:center"><label><B>SKS</B> </label></td>
	        	</tr>
	        	<tr>
	        		<td style="color:#000;text-align:center"><label><B><%=kdkmk %></B> </label></td>
	        		<td style="color:#000;text-align:center"><label><B><%=nakmk %></B> </label></td>
	        		<td style="color:#000;text-align:center"><label><B><%=skstt %></B> </label></td>
	        	</tr>
							<%
						}
						if(!li.hasNext()) {
				%>
				<tr>
		     		<td style="background:#369;color:#fff;text-align:left" colspan="2"><label><B>TOTAL SKS</B> </label></td>
			   		<td style="background:#369;color:#fff;text-align:center"><label><B><%=sksem %></B> </label></td>
		      	</tr>
		    </table>
					    <%
						}
					}	
				}	
			}
		%>
		</table>
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