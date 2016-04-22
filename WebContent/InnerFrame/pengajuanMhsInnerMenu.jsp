

<ul>

<li>
<%
if(am_i_stu!=null && am_i_stu.equalsIgnoreCase("true")) {
%>	
	<a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">BACK <span><b style="color:#eee">---</b></span></a>
<%
}
else {
%>
	<!--  a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">BACK <span><b style="color:#eee">---</b></span></a-->
	<a href="get.profile?npm=<%=npm %>&cmd=dashboard&nmm=<%=nmm %>&id_obj=<%=id_obj%>&kdpst=<%=kdpst %>&obj_lvl=<%=obj_lvl %>" target="_self">BACK <span><b style="color:#eee">---</b></span></a>
<%
}
%>
</li>
<%
boolean reqByOpr = false;
if(validUsr.isUsrAllowTo_updated("regByOpr", npm)) {
	reqByOpr = true;
}

if(reqByOpr) {
//if(validUsr.isUsrAllowTo("regByOpr", npm, obj_lvl)) {	
	if(cmd.equalsIgnoreCase("heregistrasi")) {
%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}InnerFrame/Prakuliah/Mhs/DaftarUlangByOpr.jsp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=heregistrasi" target="_self" class="active">DAFTAR<span>ULANG</span></a></li>
<%
	}
	else {
%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}InnerFrame/Prakuliah/Mhs/DaftarUlangByOpr.jsp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=heregistrasi" target="_self">DAFTAR<span>ULANG</span></a></li>
<%
	}	


	if(validUsr.isUsrAllowTo("ua", npm, obj_lvl)) {	
		if(cmd.equalsIgnoreCase("ua")) {
		///ToUnivSatyagama/WebContent/InnerFrame/Pengajuan/DashUjianAkhir.jsp
%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}InnerFrame/Pengajuan/DashUjianAkhir_part1.jsp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=ua" target="_self" class="active">UJIAN<span>AKHIR</span></a></li>
<%
		}
		else {
%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}InnerFrame/Pengajuan/DashUjianAkhir_part1.jsp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=ua" target="_self">UJIAN<span>AKHIR</span></a></li>
<%
		}	
	}
/*
	AWALNYA DIDESIGN PENGAJUAN CUTI, TAPI UDAH DIGANTI ,UMGKIN BISA UNTUK DIGUNAKAN KEMUADIAN UNTUK PENGATURAN STATUS TRLSM 
*/
//if(validUsr.isUsrAllowTo_updated("trlsm", npm)) {
if(false) {	
	if(cmd.equalsIgnoreCase("trlsm")) {
		///ToUnivSatyagama/WebContent/InnerFrame/Pengajuan/DashUjianAkhir.jsp
%>
	<li><a href="go.prepAjuanCuti?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=cuti" target="_self" class="active">CUTI<span>KULIAH</span></a></li>
<%
	}
	else {
%>
	<li><a href="go.prepAjuanCuti?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=cuti" target="_self">CUTI<span>KULIAH</span></a></li>
<%
	}	
}
//System.out.println("allow cuti "+npm);
	if(validUsr.isUsrAllowTo_updated("cuti", npm)) {
	//System.out.println("allow");
		if(cmd.equalsIgnoreCase("cuti")) {
		///ToUnivSatyagama/WebContent/InnerFrame/Pengajuan/DashUjianAkhir.jsp
%>
	<li><a href="go.moCuti?target_thsms=<%=Checker.getThsmsHeregistrasi() %>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=cuti" target="_self" class="active">CUTI<span>KULIAH</span></a></li>
<%
					//moCuti = riwayat cuti mhs 
		}
		else {
%>
	<li><a href="go.moCuti?target_thsms=<%=Checker.getThsmsHeregistrasi() %>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=cuti" target="_self">CUTI<span>KULIAH</span></a></li>
<%
		}	
	}
	else {
	//System.out.println("not allow");
	}
	
	if(validUsr.isUsrAllowTo_updated("reqPindahJurusan", npm)) {	
		if(cmd.equalsIgnoreCase("pp")) {
		///ToUnivSatyagama/WebContent/InnerFrame/Pengajuan/DashUjianAkhir.jsp
		
%>
	<li><a href="go.moPp?target_thsms=<%=Checker.getThsmsHeregistrasi() %>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=pp" target="_self" class="active">PINDAH<span>PRODI</span></a></li>
<%
					//moCuti = riwayat cuti mhs 
		}
		else {
%>
	<li><a href="go.moPp?target_thsms=<%=Checker.getThsmsHeregistrasi() %>&id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=pp" target="_self">PINDAH<span>PRODI</span></a></li>
<%
		}	
	}
	
	
	if(validUsr.isUsrAllowTo_updated("out", npm)) {	
		if(cmd.equalsIgnoreCase("out")) {
		///ToUnivSatyagama/WebContent/InnerFrame/Pengajuan/DashUjianAkhir.jsp
		
%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}InnerFrame/Pengajuan/keluar/form_keluar.jsp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=out" target="_self" class="active">KELUAR<span>&nbsp</span></a></li>
<%
		}
		else {
%>
	<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}InnerFrame/Pengajuan/keluar/form_keluar.jsp?id_obj=<%=id_obj%>&nmm=<%=nmm%>&npm=<%=npm %>&obj_lvl=<%=obj_lvl %>&kdpst=<%=kdpst %>&cmd=out" target="_self">KELUAR<span>&nbsp</span></a></li>
<%
		}	
	}
	
	//
	
}
%>
	
</ul>

