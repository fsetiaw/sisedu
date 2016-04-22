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
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	Vector v1v2 = (Vector)request.getAttribute("v1v2"); 
	request.removeAttribute("v1v2"); 
	//String thsms = (String)request.getAttribute("target_thsms");
	//request.removeAttribute("target_thsms"); 
	String thsms = request.getParameter("target_thsms");
	String kdpst_nmpst = request.getParameter("kdpst_nmpst");
	
	String kdpst=null,nmpst=null;
	if(kdpst_nmpst!=null) {
		StringTokenizer st = new StringTokenizer(kdpst_nmpst,",");
		kdpst = st.nextToken();
		nmpst = st.nextToken();
	}	
	//String atMenu=request.getParameter("atMenu");
	
	ListIterator li1=null,li2=null,li=null;
	Vector v1=null,v2=null;
	if(v1v2!=null && v1v2.size()==2) {
		li = v1v2.listIterator();
		v1 = (Vector)li.next();
		v2 = (Vector)li.next();//shift only
	}
%>


</head>
<body>
<div id="header">
<%@ include file="KelasPerkuliahanSubMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		
		<%
		if(v1v2==null || v1v2.size()!=2) {
		%>
		<h3 style="text-align:center">BELUM ADA RECORD KRS UNTUK PRODI <%=nmpst %> @ THSMS <%=Converter.convertThsmsKeterOnly(thsms)%></h3>
		<form action="go.prepMhsPerKelas">
		<input type="hidden" name="atMenu" value="<%=atMenu%>" /> 
		<input type="hidden" name="kdpst_nmpst" value="<%=kdpst_nmpst%>" />
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:550px">
			<tr>
	       		<td style="background:#369;color:#fff;text-align:center" colspan="5"><label><B>UBAH THSMS 
	       		<select name="target_thsms">
	       		<%
	       		String thsms_pmb = Checker.getThsmsPmb();
	       		//Tool.returnPrevThsmsGivenTpAntara(smawl)
	       		//Vector tknThsms = Tool.returnTokensListThsms("20011", thsms_pmb);
	       		Vector tknThsms = Tool.returnTokensListThsmsTpAntara("20011", thsms_pmb);
	       		ListIterator liThsms = tknThsms.listIterator();
	       		while(liThsms.hasNext()) {
	       			String tmp_thsms = (String)liThsms.next();
	       			if(tmp_thsms.equalsIgnoreCase(thsms)) {
	       			%>
	       				<option value="<%= tmp_thsms%>" selected><%=Converter.convertThsmsKeterOnly(tmp_thsms) %></option>
	       			<%
	       			}
	       			else {
	       				%>
	       				<option value="<%= tmp_thsms%>"><%=Converter.convertThsmsKeterOnly(tmp_thsms) %></option>
	       			<%
	       			}
	       		}
	       		 
	       		%>
	       		</select>
	       		<input type="submit" value="next" />
	       		</B> </label></td>
	       	</tr>	
		</table>
	    </form>   	
		<%
		}
		else {
			li = v1v2.listIterator();
			if(li.hasNext()) {
				//v1 = (Vector)li.next();
				//v2 = (Vector)li.next();
				//Vector v3 = (Vector)li.next();
				/*
				String brs = (String)li.next();
	       		StringTokenizer st = new StringTokenizer(brs,",");
	       		String prev_shift = st.nextToken();
	       		String kdkmk = st.nextToken();
	       		String sksmk = st.nextToken();
	       		String nakmk = st.nextToken();
	       		String tknNpm = st.nextToken();
	       		st = new StringTokenizer(tknNpm,"#");
	       		int ttmhs = st.countTokens();
				*/
		%>
		<form action="go.prepMhsPerKelas">
		<input type="hidden" name="atMenu" value="<%=atMenu%>" /> 
		<input type="hidden" name="kdpst_nmpst" value="<%=kdpst_nmpst%>" />
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:650px">
			<tr>
	       		<td style="background:#369;color:#fff;text-align:center" colspan="<%=v2.size()+3%>"><label>THSMS 
	       		<select name="target_thsms">
	       		<%
	       		String thsms_pmb = Checker.getThsmsPmb();
	       		Vector tknThsms = Tool.returnTokensListThsmsTpAntara("20011", thsms_pmb);
	       		ListIterator liThsms = tknThsms.listIterator();
	       		while(liThsms.hasNext()) {
	       			String tmp_thsms = (String)liThsms.next();
	       			if(tmp_thsms.equalsIgnoreCase(thsms)) {
	       			%>
	       				<option value="<%= tmp_thsms%>" selected><%=Converter.convertThsmsKeterOnly(tmp_thsms) %></option>
	       			<%
	       			}
	       			else {
	       				%>
	       				<option value="<%= tmp_thsms%>"><%=Converter.convertThsmsKeterOnly(tmp_thsms) %></option>
	       			<%
	       			}
	       		}
	       		 
	       		%>
	       		</select>
	       		<input type="submit" value="next" />
	       		</B> </label></td>
	       	</tr>	
			<tr>
	       		<td style="background:#369;color:#fff;text-align:center"><label><B>KODE MK</B> </label></td>
	       		<td style="background:#369;color:#fff;text-align:center"><label><B>MATAKULIAH</B> </label></td>
	       		<td style="background:#369;color:#fff;text-align:center"><label><B>SKS</B> </label></td>
	       		<%
	       		li2 = v2.listIterator();
	       		while(li2.hasNext()) {
					String shift = (String)li2.next();
				%>
				<td style="background:#369;color:#fff;text-align:center"><label><B># MHS<br><%=Getter.getKodeKonversiShift(shift) %> </B> </label></td>
				<%	
	       		}
	       		%>
	       		
	       	</tr>
	      <% 	
	      		boolean first = true;
	      		int i=0;
	      		li1 = v1.listIterator();
	      		while(li1.hasNext() && (i<v2.size()) ) {
	      			
	      			String brs = (String)li1.next();
	      			//System.out.println(brs);
	      			StringTokenizer st = new StringTokenizer(brs,"$");
	      			while(st.hasMoreTokens()) {
	      				i++;
	      				String nakmk = st.nextToken();
	      				String kdkmk = st.nextToken();
						String sksmk = st.nextToken();
						String shift = st.nextToken();
						String npmhs = st.nextToken();
						//jika value npmhs = 0, maka tidak ada yg mengambil
						boolean emptyClass = false;
						if(npmhs.equalsIgnoreCase("0")) {
							emptyClass = true;
						}
						StringTokenizer stt = new StringTokenizer(npmhs,",");
						
						if(first) {
							first = false;
			%>
			<tr>
	     		<td style="color:#000;text-align:center"><label><B><%=kdkmk %></B> </label></td>
				<td style="color:#000;text-align:left"><label><B><%=nakmk %></B> </label></td>
				<td style="color:#000;text-align:center"><label><B><%=sksmk %></B> </label></td>
			<%
							if(!emptyClass) {
			%>	
				<td style="color:#000;text-align:center"><a href="prep.tampletInfoBasedListNpm?kdpst=<%=kdpst %>&nmpst=<%=nmpst %>&listNpm=<%=npmhs%>"><%=stt.countTokens() %></a></td>
			<%			
							}
							else {
			%>	
				<td style="color:#000;text-align:center">0</td>
			<%					
							}
						}
						else {
							if(!emptyClass) {
			%>
				<td style="color:#000;text-align:center"><a href="prep.tampletInfoBasedListNpm?kdpst=<%=kdpst %>&nmpst=<%=nmpst %>&listNpm=<%=npmhs%>"><%=stt.countTokens() %></a></td>
			<%
							}
							else {
			%>
								<td style="color:#000;text-align:center">0</td>
			<%					
							}
						}
						if(i==v2.size()) {
							first=true;
							i=0;
			%>
			</tr>
			<%							
						}
	      			}
	      		}
	       	}
	       	%>
	  	</table>
		<%
		}
		
		%>
		</form>
		<!-- Column 1 start -->
	</div>
</div>			
</body>
</html>