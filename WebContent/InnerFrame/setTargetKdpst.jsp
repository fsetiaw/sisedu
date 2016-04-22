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
	//System.out.println("anehh");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String callerPage = request.getParameter("callerPage"); //callerPage & backTo agak rancu
	String fwdPage = request.getParameter("fwdPage"); //forward mode 
	String backTo = request.getParameter("backTo"); //callerPage & backTo agak rancu
	
	String thisPage = request.getRequestURI();
	String cmd = ""+request.getParameter("cmd");
	//System.out.println("cmd="+cmd);
	String atMenu = ""+request.getParameter("atMenu");
	//System.out.println("atMenu="+atMenu);
	String scope = request.getParameter("scope");
	//System.out.println("scope="+scope);
	String uri = request.getRequestURI();
	String redirectForOnlyOneScope="";
	String lockedMsg = (String)session.getAttribute("lockedMsg");	//only for requestBukakelas	
	String scopeType = ""+request.getParameter("scopeType");
	/*
		VARIABLE DIBAWAH INI BERASAL DARI /InnerFrame/innerMenu digunakan untuk masking Bahan Ajar
	*/
			
	String id_obj_mask=""+request.getParameter("id_obj");
	String nmm_mask=""+request.getParameter("nmm");
	String npm_mask=""+request.getParameter("npm");
	String obj_lvl_mask=""+request.getParameter("obj_lvl");
	String kdpst_mask=""+request.getParameter("kdpst");
	//System.out.println("cmd="+cmd);
	//System.out.println("nmm="+nmm_mask);
	////System.out.println("backTo at setTargetKdpst="+backTo);
	////System.out.println("fwdPage at setTargetKdpst="+fwdPage);
////System.out.println("scope at setTargetKdpst="+scope);
	////System.out.println("ur at isetTargetKdpst="+uri);
	////System.out.println("<br/>atMenu at setTargetKdpst="+atMenu);
	
	Vector vSc = null;
	if(scope.contains("kurikulum")||scope.contains("kelas")||scope.contains("Kelas")||scopeType.equalsIgnoreCase("prodiOnly")) {
		vSc = validUsr.getScopeUpd7des2012ProdiOnly(scope);
		//System.out.println("scope prodi only");
		//System.out.println("vSc.size()="+vSc.size());
	}
	else {
		vSc = validUsr.getScopeUpd7des2012(scope);
	}
%>

</head>
<%
	if(vSc!=null && vSc.size()==1) {
%>
<body onload="submitform()">
<%
	
	}
	else {
%>
<body>
<%
	}
%>
<div id="header">
	<ul>
	<%
	if(callerPage!=null && !Checker.isStringNullOrEmpty(callerPage)) {
	%>
		<li><a href="<%=callerPage %>" target="inner_iframe">GO<span>BACK</span></a></li>
	<%
	}
	else {
	%>
		<li><a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">GO<span>BACK</span></a></li>
		<%
	}
	
	

	%>
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->

		<%
		/*
		* router - redirection buat scop1 dan >1
		*/
		
		if(vSc!=null && vSc.size()==1) {
		//if(false) {	
		%>
			<script>
			//var auto_refresh = setInterval(function() { submitform(); }, 500);

			function submitform()
			{
  			//alert('test');
  				document.getElementById("singleOpt").submit();
			}
			</script>
		
		<%	
		}
		//System.out.println("fwdPage is "+fwdPage);
		//System.out.println("vsc is "+vSc.size());
		if(fwdPage!=null && !Checker.isStringNullOrEmpty(fwdPage)) {
			//System.out.println("sudah betul1");
			//System.out.println("fwdPage="+fwdPage);
			%>
			<form action="<%=fwdPage %>" target="_self" id="singleOpt">
			<%
		}
		else {
			if(cmd!=null && (cmd.equalsIgnoreCase("viewKurikulum") || cmd.equalsIgnoreCase("bukaKelas")  || cmd.equalsIgnoreCase("mba"))) {
				
				//System.out.println("sudah betul2");
				if(vSc.size()>0) { // bila operator punya scope >1 prodi
					//System.out.println("disini - 1 -");
					//System.out.println("sudah betul3");
			%>
			<form action="go.getListKurikulum?callerPage=<%=callerPage %>" target="_self" id="singleOpt">
				<input type="hidden" name="id_obj" value="<%=id_obj_mask %>" />
				<input type="hidden" name="nmm" value="<%=nmm_mask %>" />
				<input type="hidden" name="npm" value="<%=npm_mask %>" />
				<input type="hidden" name="obj_lvl" value="<%=obj_lvl_mask %>" />
				<input type="hidden" name="kdpst" value="<%=kdpst_mask %>" />
	
			<%	
				}
				else {
					//System.out.println("sudah betul4");
				
					//System.out.println("ERROR setTargetKdpst line 148: ngga boleh bisa kesisni");
				}
			}
			else {
				if(cmd!=null && cmd.equalsIgnoreCase("viewMhsPerKelas")) {
					
					//System.out.println("sudah betul5");
					if(vSc.size()>0) { // bila operator punya scope >1 prodi
						//System.out.println("sudah betul6");
			%>
			<form action="go.prepMhsPerKelas?callerPage=<%=callerPage %>" target="_self" id="singleOpt">
			<%
					}
				}
				else {	
					if(vSc.size()>0) { // bila operator punya scope >0 prodi
						//System.out.println("sudah betul7");
			%>
			<form action="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Akademik/dashAkademi.jsp" target="_self" id="singleOpt">
			<%
					}
				}
			}
		}
	
		if(cmd!=null && cmd.equalsIgnoreCase("bukaKelas")) {
			vSc=(Vector)session.getAttribute("vScopeBukaKelas");
			session.removeAttribute("lockedMsg");
			session.removeAttribute("vScopeBukaKelas");
			if(vSc==null) {
				vSc = new Vector();
			}
		//liSc = vSc.listIterator();
		}

		if(vSc!=null && vSc.size()>0) {
			ListIterator liSc = vSc.listIterator();
			String baris = (String)liSc.next();
			StringTokenizer st = new StringTokenizer(baris);
			String tmp = st.nextToken();
			
				%>
				<input type="hidden" name="cmd" value="<%=cmd%>" /> 
				<input type="hidden" name="atMenu" value="<%=atMenu%>" /> 
				<input type="hidden" name="scope" value="<%=scope%>" /> 
				<%
				if(callerPage!=null && Checker.isStringNullOrEmpty(callerPage)) {
				%>
				<input type="hidden" name="callerPage" value="<%=callerPage%>" /> 
				<%	
				}
				else {
				%>
				<input type="hidden" name="callerPage" value="<%=thisPage%>" /> 
				<%
				}
				%>
			<%
			//scope lebih dari satu jadi ada pilihan
			
			if(vSc!=null && vSc.size()>1) {
			//if(vSc.size()>0) {
			
			%>		
				<table align="center" border="1" style="background:#d9e1e5;color:#000;width:300px;">
				<tr>
					<td align="center" bgcolor="#369" style="color:#fff" padding-left="2px"><b>PILIH PROGRAM STUDI</b></td>
				</tr>	
				<tr>	
						<td align="left" width="100px" style="padding-left:2px">
							<select name="kdpst_nmpst" style="width:100%">
				<%
				liSc = vSc.listIterator();
				while(liSc.hasNext()){
					baris = (String)liSc.next();
					//System.out.println("++"+baris);
					st = new StringTokenizer(baris);
					String idObj = st.nextToken();
    				String kdpst = st.nextToken();
    				String nmpst = st.nextToken().replace("MHS_", "");
    				String obLvl = st.nextToken();
    				String kdjen = st.nextToken();
    				String kodeKampus = null; 
    				//peralihan ada yg ngga ada tkn kodekampus
    				if(st.hasMoreTokens()) {
    					kodeKampus = st.nextToken();
    				}
    				else {
    					kodeKampus = null;
    				}
					
					
					//String idObj = st.nextToken();
					//String kdpst = st.nextToken();
					//String nmpst = st.nextToken();
					nmpst = nmpst.replaceAll("MHS", "");
					nmpst = nmpst.replaceAll("_", " ");
					//String objLv = st.nextToken();
					%>
									<option value="<%=kdpst %>,<%=nmpst %>,<%=kodeKampus %>"><%=nmpst %></option>
						<%
				}
				%>
								</select>
							</td>
						</tr>	
				<%
				if(cmd.equalsIgnoreCase("bukaKelas")) {
				%>
						<tr>	
							<td align="left" width="100px" style="padding-left:2px">KELAS TAMBAHAN : <input type="checkbox" name="kelasTambahan" value="yes"></td>
						</tr>		
				<%	
				}
				%>			
						<tr>
							<td align="right" bgcolor="#369"><input type="submit" value="Next" style="width:100px;" /></td>
						</tr>
					</table>
				<%
			}
			else {
				if(vSc!=null && vSc.size()==1) {
					liSc = vSc.listIterator();
					baris = (String)liSc.next();
					st = new StringTokenizer(baris);
					String idObj = st.nextToken();
    				String kdpst = st.nextToken();
    				String nmpst = st.nextToken().replace("MHS_", "");
    				String obLvl = st.nextToken();
    				String kdjen = st.nextToken();
    				String kodeKampus = null; 
    				//peralihan ada yg ngga ada tkn kodekampus
    				if(st.hasMoreTokens()) {
    					kodeKampus = st.nextToken();
    				}
    				else {
    					kodeKampus = null;
    				}
    				
					//String idObj = st.nextToken();
					//String kdpst = st.nextToken();
					//String nmpst = st.nextToken();
					nmpst = nmpst.replaceAll("MHS", "");
					nmpst = nmpst.replaceAll("_", " ");
					//String objLv = st.nextToken();
				%>
				 <input type="hidden" name="kdpst_nmpst" value="<%=kdpst %>,<%=nmpst %>,<%=kodeKampus %>" /> 
				<%
				}
			}
			
				
			%>	
			</form>	
			<%
			//}
			if(vSc!=null && vSc.size()>0 && !Checker.isStringNullOrEmpty(lockedMsg)) {
			//if(!Checker.isStringNullOrEmpty(lockedMsg)) {
				//edit disini edit dibawah juga 
				//dibagian vscope==1
		%>	
			<br/>
			<p style="text-align:center;font-weight:bold;font-size:1.5em">
				<%= lockedMsg%>
			</p>
			<p style="text-align:center;font-style:italic;color:red;font-weight:bold">
				BAGI PRODI YANG SUDAH DIAJUKAN DAN TELAH DISETUJUI, HARAP MENGAJUKAN PERMOHONAN ULANG RENCANA PEMBUKAAN KELAS PERKULIAHAN KEPADA PIMPINAN
			</p>
		<%
			}
		}
		%>
		
	</div>
</div>		
</body>
</html>