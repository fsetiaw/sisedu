<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@page import="beans.setting.Constants"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="java.util.Collections" %>
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
	Vector vListNpmOnTrnlm =(Vector)request.getAttribute("vListNpmOnTrnlm");
	Collections.sort(vListNpmOnTrnlm);
	request.removeAttribute("vListNpmOnTrnlm");
	Vector vApprovedHeregistrasi = (Vector) request.getAttribute("vApprovedHeregistrasi");
	Collections.sort(vApprovedHeregistrasi);
	request.removeAttribute("vApprovedHeregistrasi");
	String missing_npm = "";
	if(vApprovedHeregistrasi!=null && vApprovedHeregistrasi.size()>0) {
		if(vListNpmOnTrnlm!=null && vListNpmOnTrnlm.size()>0) {
			ListIterator li1 = vApprovedHeregistrasi.listIterator();
			//ListIterator li2 = vListNpmOnTrnlm.listIterator();
			
			String npm_sms_lalu = "";
			while(li1.hasNext()) {
				npm_sms_lalu = (String)li1.next();
				ListIterator li2 = vListNpmOnTrnlm.listIterator();
				boolean match = false;
				while(li2.hasNext() && ! match) {
					String npm_trnlm = (String)li2.next();
					if(npm_sms_lalu.equalsIgnoreCase(npm_trnlm)) {
						//System.out.println(npm_sms_lalu+" vs "+npm_trnlm);
						match = true;
					}
					
				}
				if(!match) {
					//System.out.println(npm_sms_lalu+" missing");
					missing_npm = missing_npm+npm_sms_lalu+" ,";
				}
				
			}
			
		}
	}
	if(missing_npm.endsWith(",")) {
		missing_npm = missing_npm.substring(0, missing_npm.length()-1);
	}
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
	boolean valid = true;
	ListIterator li1=null,li2=null,li=null,lis=null;
	Vector v1=null;
	Vector vshift=null;
	//String distinct_shift="";
	if(v1v2!=null && v1v2.size()==2) {
		li = v1v2.listIterator();
		v1 = (Vector)li.next();
		//cuid+"`"+idkur_mk+"`"+kdpst_mk+"`"+shift_mk+"`"+no_pll+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+nakmk+"`"+sksmk+"`"+ttmhs
		vshift = (Vector)li.next();//shift only
	}
	else {
		valid = false;
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
		<br/>
		
		<%
if(!valid) {
		%>
		<h3 style="text-align:center">BELUM ADA RECORD KRS UNTUK PRODI <%=nmpst %> @ THSMS <%=Converter.convertThsmsKeterOnly(thsms)%></h3>
		<form action="go.prepMhsPerKelas">
		<!--  form action="go.prepMhsPerKelas?callerPage=<%=callerPage %>" target="_self" id="singleOpt" -->
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
				
		%>
		<table align="center" border="1px" bordercolor="#369" style="table-layout:fix;background:#d9e1e5;color:#000;width:800px">
			<tr>
				<td style="background:#369;color:#fff;text-align:center;width:70%"><label><B>Total Mahasiswa Daftar Ulang Semester Lalu / <%=Tool.returnPrevThsmsGivenTpAntara(thsms) %></B> </label></td>
	       		<td style="text-align:center;;width:50%"><label><B><%=vApprovedHeregistrasi.size() %> <br/></B> </label></td>
	       	</tr>
	       	<tr>
				<td style="background:#369;color:#fff;text-align:center;width:70%"><label><B>Total KRS Mahasiswa Semester Ini / <%=thsms%></B> </label></td>
	       		<td style="text-align:center;;width:50%"><label><B><%=vListNpmOnTrnlm.size() %> <br/></B> </label></td>
	       	</tr>
	       	<tr>
	       		<td colspan="2" style="background:#369;color:#fff;text-align:center;"><label><B>List Mahasiswa Yg Terdaftar SMS lalu tapi belum ada KRSnya sms ini</B> </label></td>
	       	</tr>
	       	<tr>	
	       		<td colspan="2" style="text-align:center;word-wrap:break-word"><label><B><%=missing_npm %> <br/></B> </label></td>
	       	
	       	</tr>
		</table>
		<br/>
		<br/>
		<form action="go.prepMhsPerKelas">
		<input type="hidden" name="atMenu" value="<%=atMenu%>" /> 
		<input type="hidden" name="kdpst_nmpst" value="<%=kdpst_nmpst%>" />
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px">
			<tr>
	       		<td style="background:#369;color:#fff;text-align:center" colspan="<%=vshift.size()+5%>"><label>THSMS 
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
				<td style="background:#369;color:#fff;text-align:center"><label><B>NO</B> </label></td>
	       		<td style="background:#369;color:#fff;text-align:center"><label><B>KODE MK</B> </label></td>
	       		<td style="background:#369;color:#fff;text-align:center"><label><B>MATAKULIAH</B> </label></td>
	       		<td style="background:#369;color:#fff;text-align:center"><label><B>NO<br/>URUT</B> </label></td>
	       		<td style="background:#369;color:#fff;text-align:center"><label><B>SKS</B> </label></td>
	       		<%
		li2 = vshift.listIterator();
	    while(li2.hasNext()) {
			String shift = (String)li2.next();
				%>
				<td style="background:#369;color:#fff;text-align:center"><label><B># MHS<br><%=Getter.getKodeKonversiShift(shift) %> </B> </label></td>
				<%	
	    }
	       		%>
	       		
	       	</tr>
	      <% 	
	      		
	   	int i=0;
	    li1 = v1.listIterator();
	      		//while(li1.hasNext() && (i<vshift.size()) ) {
	    while(li1.hasNext()) {	
	      	i++;
	      %>
	      	<tr>
	      <%			
	      	boolean first = true; //1st new kdkmk row
	      	for(int k=0; k<vshift.size();k++) {
	      		//System.out.println("k = "+k);
	      	
	      		//System.out.println("first = "+vshift.size());
	      	
	      		String brs = (String)li1.next();
	      		//System.out.println("bar = "+brs);
		    	StringTokenizer st = new StringTokenizer(brs,"`");
		    	String idkmk = st.nextToken();
				String kdkmk = st.nextToken();
				String nakmk = st.nextToken();
				String sksmk = st.nextToken();
				String no_kls_pll = st.nextToken();
				String idkur = st.nextToken();
				String cuid = st.nextToken();
				String target_shift = st.nextToken();
		    	
		    	
		    	
		    	/*
		      	String cuid = st.nextToken();
		      	String idkmk = st.nextToken();
		      	String idkur = st.nextToken();
		      	String kdpst_mk = st.nextToken();
		      	String shift = st.nextToken();
		      	String nopll = st.nextToken();
		      	String npmdos = st.nextToken();
		      	String nmmdos = st.nextToken();
		      	String kdkmk = st.nextToken();
		      	String nakmk = st.nextToken();
		      	String sksmk = st.nextToken();
		      	*/
				String ttmhs = st.nextToken();
				
				//jika value npmhs = 0, maka tidak ada yg mengambil
				boolean emptyClass = false;
				if(Checker.isStringNullOrEmpty(ttmhs) || ttmhs.equalsIgnoreCase("0")) {
					//if()) {
					emptyClass = true;
					ttmhs = "0";
				}
							
				if(first) {
					first = false;
					//System.out.println("cetak1");
							//prev_kdkmk = new String(kdkmk);
				%>
				
					<td style="color:#000;text-align:center"><label><B><%=i %></B> </label></td>
		     		<td style="color:#000;text-align:center"><label><B><%=kdkmk %></B> </label></td>
					<td style="color:#000;text-align:left;padding:0 0 0 5px"><label><B><%=nakmk %></B> </label></td>
					<td style="color:#000;text-align:center"><label><B><%=no_kls_pll %></B> </label></td>
					<td style="color:#000;text-align:center"><label><B><%=sksmk %></B> </label></td>
				<%
					if(!emptyClass) {
				%>					
					<td style="color:#000;text-align:center"><a href="prep.tampletInfoMhsBasedCuid?cuid=<%=cuid %>&nakmk=<%=nakmk%>&kdkmk=<%=kdkmk%>&shift=<%=target_shift%>&nopll=<%=no_kls_pll%>"><%=ttmhs %></a></td>
				<%						
					}	
					else {
				%>
					<td style="color:#000;text-align:center">0</td>
				<%	
					}
				}
				else {
					
				
					//brs = (String)li1.next();
					//System.out.println("bar = "+brs);
				   	st = new StringTokenizer(brs,"`");
				      		//while(st.hasMoreTokens()) {
				      				//i++;
				      				//cuid+"`"+idkur_mk+"`"+kdpst_mk+"`"+shift_mk+"`"+no_pll+"`"+npmdos+"`"+nmmdos+"`"+kdkmk+"`"+nakmk+"`"+sksmk+"`"+ttmhs
				   	idkmk = st.nextToken();
        			kdkmk = st.nextToken();
        			nakmk = st.nextToken();
        			sksmk = st.nextToken();
        			no_kls_pll = st.nextToken();
        			idkur = st.nextToken();
        			cuid = st.nextToken();
        			target_shift = st.nextToken();
				      				
				   /* 				
				    cuid = st.nextToken();
				   	idkmk = st.nextToken();
				    idkur = st.nextToken();
				    kdpst_mk = st.nextToken();
				    shift = st.nextToken();
				    nopll = st.nextToken();
				    npmdos = st.nextToken();
				    nmmdos = st.nextToken();
				    kdkmk = st.nextToken();
				    nakmk = st.nextToken();
				    sksmk = st.nextToken();
				    */
					ttmhs = st.nextToken();
									//jika value npmhs = 0, maka tidak ada yg mengambil
					emptyClass = false;
					if(Checker.isStringNullOrEmpty(ttmhs) || ttmhs.equalsIgnoreCase("0")) {
									//if()) {
						emptyClass = true;
						ttmhs = "0";
					}
					//System.out.println("cetak2");
					if(!emptyClass) {
										%>					
					<td style="color:#000;text-align:center"><a href="prep.tampletInfoMhsBasedCuid?cuid=<%=cuid %>&nakmk=<%=nakmk%>&kdkmk=<%=kdkmk%>&shift=<%=target_shift%>&nopll=<%=no_kls_pll%>"><%=ttmhs %></a></td>
										<%						
					}	
					else {
										%>
					<td style="color:#000;text-align:center">0</td>
										<%	
					}
				}
	      	}//end for
			%>
			</tr>
			<%							
						
	      			
		}
	}
	       	%>
	  	</table>
		<%
}
		%>
		</form>
<%

%>		
		<!-- Column 1 start -->
	</div>
</div>			
</body>
</html>