<%
v = (Vector) request.getAttribute("v_profile");
//System.out.println("sizev="+v.size());
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

String v_kdjen=(String)request.getAttribute("v_kdjen");
String v_shift=(String)request.getAttribute("v_shift");
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
//System.out.println("v_nimhs "+v_nimhs);
//System.out.println("v_nmmhs "+v_nmmhs);
//System.out.println("v_kdpst "+v_kdpst);
//System.out.println("v_nmmhs "+v_nmmhs);
	String cmd =  (String)request.getParameter("cmd");
	//System.out.println("cmd = "+cmd);
	boolean allowViewKrs=false, allowEditKrs=false,requestKonversiMakul=false;
%>	

<ul>
	<%
	//if(validUsr.isAllowTo("hasPengajuanMhsMenu")>0) {
	if(validUsr.isUsrAllowTo("hasPengajuanMhsMenu", v_npmhs, v_obj_lvl)) {	
		if(cmd.equalsIgnoreCase("reqMhs")) {
		%>
		<!--  li><a href="get.infoTracerStudy?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=reqMhs" target="_self" class="active">PENGAJUAN<span></span></a></li -->
		<li><a href="<%=Constants.getRootWeb()+"/" %>InnerFrame/indexPengajuanMhs.jsp?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=reqMhs" target="_self" class="active">BACK<span><div style="color:#eee">&nbsp</div></span></a></li>
		<%
		}
		else {
		%>
		<!--  li><a href="get.infoTracerStudy?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=reqMhs" target="_self" >CETAK /<span>PRINTING</span></a></li -->
		<li><a href="<%=Constants.getRootWeb()+"/" %>InnerFrame/indexPengajuanMhs.jsp?id_obj=<%=v_id_obj%>&nmm=<%=v_nmmhs%>&npm=<%=v_npmhs %>&obj_lvl=<%=v_obj_lvl %>&kdpst=<%=v_kdpst %>&cmd=reqMhs" target="_self" >BACK<span><div style="color:#eee">&nbsp</div></span></a></li>
		<%
		}	
	}
	

	%>
</ul>

