<%
Vector list_kdpst_sad = validUsr.getScopeUpd7des2012ProdiOnlyButKeepOwn("sad");
//Vector list_kdpst_ink = validUsr.getScopeUpd7des2012ReturnDistinctKdpst("ink");
Vector list_kdpst_ink = validUsr.getScopeUpd7des2012ProdiOnlyButKeepOwn("ink");
String atMenu = request.getParameter("atMenu");
SearchDbClassPoll scp = new SearchDbClassPoll(validUsr.getNpm());
boolean am_i_stu= validUsr.iAmStu();

/*
!!!!!!!!!!!!
harusnya utk f(getListKelasYgBisaDiUpdateStatusKehadiranAtauNilainya) cukup dengan yg limit 1 krn nanti diprocess lag di servlet
!!!!!!!!!!!!!!!!!
*/
Vector vKlsAjar = scp.getListKelasYgBisaDiUpdateStatusKehadiranAtauNilainya(list_kdpst_sad, Checker.getThsmsNow());
Vector vKlsPenilaian = scp.getListKelasYgBisaDiUpdateStatusKehadiranAtauNilainya(list_kdpst_ink, Checker.getThsmsInputNilai());
boolean bolehEdit = false;
boolean bolehEditNilai = false;
//if(scopeHakAkses.contains("`e`")||scopeHakAkses.contains("`i`")) {
if(vKlsAjar!=null) {	
	bolehEdit = true;
}
if(vKlsPenilaian!=null) {	
	bolehEditNilai = true;
}
//System.out.println("vklsAjar = "+vKlsAjar.size());
//System.out.println("bolehEdit = "+bolehEdit);
//System.out.println("bolehEditNilai = "+bolehEditNilai);
%>
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<META HTTP-EQUIV="EXPIRES" CONTENT="Mon, 22 Jul 2002 11:12:01 GMT">
<ul>
	<%
	if(false) {
	%>
		<li><a href="get.notifications" target="_self" class="active">HOME <span><b style="color:#eee">---</b></span></a></li>
	<%
	}
	else {
		if(false) {
	%>
		<li><a href="get.notifications" target="_self">HOME <span><b style="color:#eee">---</b></span></a></li>
	<%
		}
	}
	if(validUsr.isAllowTo("viewAbsen")>0) {
		if(atMenu!=null && atMenu.equalsIgnoreCase("absensi")) {
			%>
			<li><a href="get.getListOpenedClass?cmd=viewAbsen&atMenu=absensi&targetThsms=<%=Checker.getThsmsNow() %>" target="_self" class="active">DAFTAR<span>ABSENSI</span></a></li>
			<%	
		}
		else {
			%>
			<li><a href="get.getListOpenedClass?cmd=viewAbsen&atMenu=absensi&targetThsms=<%=Checker.getThsmsNow() %>" target="_self">DAFTAR<span>ABSENSI</span></a></li>
			<%	
		}
		
	}
	
	if(validUsr.isAllowTo("hasKartuUjianMenu")>0) {
		if(atMenu!=null && atMenu.equalsIgnoreCase("kartuUjian")) {
			%>
			<li><a href="get.listTipeUjian" target="_self" class="active">KARTU<span>UJIAN</span></a></li>
			<%	
		}
		else {
			%>
			<li><a href="get.listTipeUjian" target="_self">KARTU<span>UJIAN</span></a></li>
			<%	
		}	
	}
	//kalo dosen pengajar otomatis ada menu ini
	if(validUsr.isAllowTo("ink")>0 || (vKlsPenilaian!=null && vKlsPenilaian.size()>0)) {
		if(atMenu!=null && atMenu.equalsIgnoreCase("inputNilai")) {
			//<a href="getClasPol.statusKehadiranKelas?atMenu=inputNilai" target="_self" class="active">INPUT<span>NILAI</span></a></li>
			%>
			<li><a href="getClasPol.statusKehadiranKelas?atMenu=inputNilai&bolehEditNilai=<%=bolehEditNilai %>" target="_self" class="active">INPUT<span>NILAI</span></a></li>
			<%	
		}
		else {
			%>
			<li><a href="getClasPol.statusKehadiranKelas?atMenu=inputNilai&bolehEditNilai=<%=bolehEditNilai %>" target="_self">INPUT<span>NILAI</span></a></li>
			<%	
		}	
	}
	
	//status absensi dosen
	if(validUsr.isAllowTo("sad")>0 || (vKlsAjar!=null && vKlsAjar.size()>0)) {
		if(atMenu!=null && atMenu.equalsIgnoreCase("kehadiran")) {
			%>
			<li><a href="getClasPol.statusKehadiranKelas?atMenu=kehadiran" target="_self" class="active">STATUS KEHADIRAN<span>DOSEN AJAR</span></a></li>
			<%	
		}
		else {
			%>
			
			<li><a href="getClasPol.statusKehadiranKelas?atMenu=kehadiran" target="_self">STATUS KEHADIRAN<span>DOSEN AJAR</span></a></li>
			<%	
		}	
	}
	
	
	if(validUsr.isAllowTo("ua")>0 || am_i_stu) {
		if(atMenu!=null && atMenu.equalsIgnoreCase("ua")) {
			%>
			<li><a href="get.pengajuanUa?atMenu=ua" target="_self" class="active">PENGAJUAN<span>UJIAN AKHIR</span></a></li>
			<%	
		}
		else {
			%>
			
			<li><a href="get.pengajuanUa?atMenu=ua" target="_self">PENGAJUAN<span>UJIAN AKHIR</span></a></li>
			<%	
		}	
	}
	
	%>
</ul>

