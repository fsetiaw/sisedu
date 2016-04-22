<%
Vector v = (Vector) request.getAttribute("v_profile");
request.removeAttribute("v_profile");
request.setAttribute("v_profile", v);
Vector v_trnlp = (Vector)request.getAttribute("v_trnlp");
Vector v_trnlm = (Vector)request.getAttribute("v_trnlm");
Vector v0 = null;
//jika v_trnlp ada recordnya
//	maka gunakan v_trnlp
boolean vtrnlm=false;
boolean vtrnlp=false;
if(v_trnlp!=null&&v_trnlp.size()>0) {
	System.out.println(v_trnlp.size());
	//v0 = new Vector(v_trnlp.size());
	v0=(Vector)v_trnlp.clone();
	//v.addAll(0,v_trnlp);
	vtrnlp=true;
}
else {
	if(v_trnlm!=null&&v_trnlm.size()>0) {
		v0=(Vector)v_trnlm.clone();
		vtrnlm=true;
	}
}
//request.removeAttribute("v_trnlm");
request.setAttribute("v_trnlm", v_trnlm);
//request.removeAttribute("v_trnlp");
request.setAttribute("v_trnlp", v_trnlp);
//System.out.println("atPages="+atPage);
/*
* ada masalah redundan krn v_id_obj = idobj, v_npmhs = npm dst (variable diatas))
*/
String msg = request.getParameter("msg");
String objId = request.getParameter("id_obj");
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String kdpst = request.getParameter("kdpst");
String obj_lvl =  request.getParameter("obj_lvl");

String v_id_obj = null;
if(objId!=null && !objId.equalsIgnoreCase("null")) {
	v_id_obj = ""+objId;
}
else {
	v_id_obj = (String)request.getAttribute("v_id_obj");
}
String v_nmmhs=null;
if(nmm!=null && !nmm.equalsIgnoreCase("null")) {
	v_nmmhs = ""+nmm;
}
else {
	v_nmmhs = (String)request.getAttribute("v_nmmhs");
}
//String v_nmmhs=(String)request.getAttribute("v_nmmhs");
String v_npmhs=null;
if(npm!=null && !npm.equalsIgnoreCase("null")) {
	v_npmhs = ""+npm;
}
else {
	v_npmhs = (String)request.getAttribute("v_npmhs");
}
String v_kdpst=null;
if(kdpst!=null && !kdpst.equalsIgnoreCase("null")) {
	v_kdpst = ""+kdpst;
}
else {
	v_kdpst = (String)request.getAttribute("v_kdpst");
}
String v_obj_lvl=null;
if(obj_lvl!=null && !obj_lvl.equalsIgnoreCase("null")) {
	v_obj_lvl = ""+obj_lvl;
}
else {
	v_obj_lvl = (String)request.getAttribute("v_obj_lvl");
}

String v_nimhs=(String)request.getAttribute("v_nimhs");
//System.out.println("v_nimhs "+v_nimhs);
String v_kdjen=(String)request.getAttribute("v_kdjen");
String v_smawl=(String)request.getAttribute("v_smawl");
String v_tplhr=(String)request.getAttribute("v_tplhr");
String v_tglhr=(String)request.getAttribute("v_tglhr");
String v_aspti=(String)request.getAttribute("v_aspti");
String v_aspst=(String)request.getAttribute("v_aspst");
String v_btstu=(String)request.getAttribute("v_btstu");
String v_kdjek=(String)request.getAttribute("v_kdjek");
String v_nmpek=(String)request.getAttribute("v_nmpek");
String v_ptpek=(String)request.getAttribute("v_ptpek");
String v_stmhs=(String)request.getAttribute("v_stmhs");
String v_stpid=(String)request.getAttribute("v_stpid");
String v_sttus=(String)request.getAttribute("v_sttus");
String v_email=(String)request.getAttribute("v_email");
String v_nohpe=(String)request.getAttribute("v_nohpe");
String v_almrm=(String)request.getAttribute("v_almrm");
String v_kotrm=(String)request.getAttribute("v_kotrm");
String v_posrm=(String)request.getAttribute("v_posrm");
String v_telrm=(String)request.getAttribute("v_telrm");
String v_almkt=(String)request.getAttribute("v_almkt");
String v_kotkt=(String)request.getAttribute("v_kotkt");
String v_poskt=(String)request.getAttribute("v_poskt");
String v_telkt=(String)request.getAttribute("v_telkt");
String v_jbtkt=(String)request.getAttribute("v_jbtkt");
String v_bidkt=(String)request.getAttribute("v_bidkt");
String v_jenkt=(String)request.getAttribute("v_jenkt");
String v_nmmsp=(String)request.getAttribute("v_nmmsp");
String v_almsp=(String)request.getAttribute("v_almsp");
String v_possp=(String)request.getAttribute("v_possp");
String v_kotsp=(String)request.getAttribute("v_kotsp");
String v_negsp=(String)request.getAttribute("v_negsp");
String v_telsp=(String)request.getAttribute("v_telsp");
String v_neglh=(String)request.getAttribute("v_neglh");
String v_agama=(String)request.getAttribute("v_agama");

	String cmd =  (String)request.getParameter("cmd");
	//System.out.println("cmd = "+cmd);
	boolean allowViewKrs=false, allowEditKrs=false,requestKonversiMakul=false,allowInsSksdi=false;
	boolean allowPenyetaraan=false;
	if(validUsr.isUsrAllowTo("insSksdi", v_npmhs, v_obj_lvl)) {
		allowInsSksdi = true;
	}	
%>	

<ul>
	
	<%	
	if(validUsr.isUsrAllowTo("viewKrs", v_npmhs, v_obj_lvl)) {
		allowViewKrs = true;
		/*
		* cek apa user boleh edit krs
		*/
		if(validUsr.isUsrAllowTo("editKrs", v_npmhs, v_obj_lvl)) {
			allowEditKrs=true;
		}
	}
	
	if(validUsr.isUsrAllowTo("penyetaraan", v_npmhs, v_obj_lvl)) {
		allowPenyetaraan=true;
	}
	if(allowViewKrs) {
		%>
		<li><a href="get.histKrs?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=histKrs" target="_self">BACK <span><b style="color:#eee">---</b></span></a></li>
		<%
	}
	//if allow inert/edit krs pindahan 
	//inget harus ada pilihan dari insert civitas baru apa mo input krs langsung apa skip
	if(allowInsSksdi) {
		if(cmd.equalsIgnoreCase("edit_sksdi")) {
		%>
		<li><a href="go.defaultVprofileRoute?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&aspti=<%=v_aspti %>&aspst=<%=v_aspst %>&cmd=edit_sksdi&fwdTo=toNonJsp.gatewayMhsPindahan&fwdPg=<%=beans.setting.Constants.getRootWeb()+"" %>${PageContext.ServletContext.ContextPath}/InnerFrame/formInputKrsPindahan.jsp" target="_self" class="active">UPDATE/INPUT KRS<span>ASAL P.T.</span></a></li>
		<%
		}
		else {
		%>
		<li><a href="go.defaultVprofileRoute?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&aspti=<%=v_aspti %>&aspst=<%=v_aspst %>&cmd=edit_sksdi&fwdTo=toNonJsp.gatewayMhsPindahan&fwdPg=<%=beans.setting.Constants.getRootWeb()+"" %>${PageContext.ServletContext.ContextPath}/InnerFrame/formInputKrsPindahan.jsp" target="_self" >UPDATE/INPUT KRS<span>ASAL P.T.</span></a></li>
		<%	
		}
	}
	
	if(allowPenyetaraan) {
		if(cmd.equalsIgnoreCase("penyetaraan")) {
		%>
		<li><a href="go.defaultVprofileRoute?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&aspti=<%=v_aspti %>&aspst=<%=v_aspst %>&cmd=penyetaraan&fwdTo=toNonJsp.gatewayMhsPindahan&fwdPg=<%=beans.setting.Constants.getRootWeb()+"" %>${PageContext.ServletContext.ContextPath}/InnerFrame/formPenyetaraan.jsp" target="_self" class="active">FORM PENYETARAAN<span>MATAKULIAH</span></a></li>
		<%
		}
		else {
		%>
		<li><a href="go.defaultVprofileRoute?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&aspti=<%=v_aspti %>&aspst=<%=v_aspst %>&cmd=penyetaraan&fwdTo=toNonJsp.gatewayMhsPindahan&fwdPg=<%=beans.setting.Constants.getRootWeb()+"" %>${PageContext.ServletContext.ContextPath}/InnerFrame/formPenyetaraan.jsp" target="_self" >FORM PENYETARAAN<span>MATAKULIAH</span></a></li>
		<%	
		}
	}

	%>
</ul>
