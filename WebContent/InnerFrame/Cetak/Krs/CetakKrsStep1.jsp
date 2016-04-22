<!DOCTYPE html>
<head>
<%@page import="beans.setting.Constants"%>
<title>Insert title here</title>
	<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.*" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
/*
String msg = request.getParameter("msg");
String objId = request.getParameter("id_obj");
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String kdpst = request.getParameter("kdpst");
String obj_lvl =  request.getParameter("obj_lvl");
*/
//System.out.println("msg disini= "+msg);
Vector v= null;  
/*
v = (Vector) request.getAttribute("v_profile");
String v_id_obj = (String)request.getAttribute("v_id_obj");
String v_nmmhs=(String)request.getAttribute("v_nmmhs");
String v_npmhs=(String)request.getAttribute("v_npmhs");
String v_nimhs=(String)request.getAttribute("v_nimhs");
String v_obj_lvl=(String)request.getAttribute("v_obj_lvl");
String v_kdpst=(String)request.getAttribute("v_kdpst");
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
*/


%>

</head>
<body onload="location.href='#'">
<div id="header">
<%@ include file="../../innerMenu.jsp" %>
<%
SearchDb sdb = new SearchDb();
//String nmpst = sdb.getNmpst(v_kdpst);
String nmpst = sdb.getNmpst(kdpst);
//System.out.println("ini1");
String idkur = sdb.getIndividualKurikulum(kdpst, npm);
//System.out.println("ini2");
Vector vMk=null;
if(idkur!=null) {
	vMk = sdb.getListMatakuliahDalamKurikulum(kdpst,idkur);
}	
//System.out.println("ini3");
Vector vSelectMk = new Vector();
ListIterator liSmk = vSelectMk.listIterator();
if(vMk!=null && vMk.size()>0) {
	//System.out.println("ini5");
	ListIterator liMk = vMk.listIterator();
	while(liMk.hasNext()) {
		String idkmk=(String)liMk.next();
		String kdkmk=(String)liMk.next();
		String nakmk=(String)liMk.next();
		String skstm=(String)liMk.next();
		String skspr=(String)liMk.next();
		String skslp=(String)liMk.next();
		String kdwpl=(String)liMk.next();
		String jenis=(String)liMk.next();
		String stkmk=(String)liMk.next();
		String nodos=(String)liMk.next();
		String semes=(String)liMk.next();
		liSmk.add(idkmk+","+kdkmk+","+nakmk);
	}	
}

boolean allowCetakKrs = false;  
if(validUsr.isUsrAllowTo("allowCetakKrs", v_npmhs, v_obj_lvl)) {
	allowCetakKrs= true;
}	
String tkn_info = "#";
%>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		
		<%
			if(allowCetakKrs) {
				Vector vHistKrsKhs = (Vector)request.getAttribute("vHistKrsKhs");
				//utk krs MK transfered tidak termasuk
				//Vector vTrnlp = (Vector)request.getAttribute("vTrnlp");
				
				request.removeAttribute("vHistKrsKhs");
				request.removeAttribute("vTrnlp");
				
				if(vHistKrsKhs!=null && vHistKrsKhs.size()>0) {
					ListIterator lih = vHistKrsKhs.listIterator();
					String prev_thsms="";
					String prev_kdkmk="";
					String prev_nakmk="";
					String prev_nlakh="";
					String prev_bobot="";
					String prev_sksmk="";
					String prev_kelas="";
					String prev_sksem="";
					String prev_nlips="";
					String prev_skstt="";
					String prev_nlipk="";
					if(lih.hasNext()) {
						String thsms=(String)lih.next();
						String kdkmk=(String)lih.next();
						String nakmk=(String)lih.next();
						String nlakh=(String)lih.next();
						String bobot=(String)lih.next();
						String sksmk=(String)lih.next();
						String kelas=(String)lih.next();
						String sksem=(String)lih.next();
						String nlips=(String)lih.next();
						String skstt=(String)lih.next();
						String nlipk=(String)lih.next();
						prev_thsms = ""+thsms;
						prev_kdkmk=""+kdkmk;
						prev_nakmk=""+nakmk;
						prev_nlakh=""+nlakh;
						prev_bobot=""+bobot;
						prev_sksmk=""+sksmk;
						prev_kelas=""+kelas;
						prev_sksem=""+sksem;
						prev_nlips=""+nlips;
						prev_skstt=""+skstt;
						prev_nlipk=""+nlipk;
						%>
					<form action="go.downloadKrs" >
					<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:700px">
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center" colspan="4"><label><B><%=prev_thsms %></B> </label></td>
        			</tr>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center;width:60px"><label><B>KODE</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:left;width:550px"><label><B>MATAKULIAH</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:center;width:35px"><label><B>SKS</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:center;width:55px"><label><B>KELAS</B> </label></td></td>
        			</tr>
        			<tr>
        			<%
        				tkn_info = tkn_info+prev_thsms+"#"+objId+"#"+nmm+"#"+npm+"#"+kdpst+"#"+obj_lvl+"#"+kdkmk+"#"+nakmk+"#"+nlakh+"#"+bobot+"#"+sksmk+"#";
        			%>	
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kdkmk %></B> </label></td></td>
        				<td style="color:#000;text-align:left;"><label><B><%=prev_nakmk %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_sksmk %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kelas %></B> </label></td></td>
        			</tr>
        			<%
						while(lih.hasNext()) {
							thsms=(String)lih.next();
							kdkmk=(String)lih.next();
							nakmk=(String)lih.next();
							nlakh=(String)lih.next();
							bobot=(String)lih.next();
							sksmk=(String)lih.next();
							kelas=(String)lih.next();
							sksem=(String)lih.next();
							nlips=(String)lih.next();
							skstt=(String)lih.next();
							nlipk=(String)lih.next();
							if(prev_thsms.equalsIgnoreCase(thsms)) {
								prev_thsms = ""+thsms;
								prev_kdkmk=""+kdkmk;
								prev_nakmk=""+nakmk;
								prev_nlakh=""+nlakh;
								prev_bobot=""+bobot;
								prev_sksmk=""+sksmk;
								prev_kelas=""+kelas;
								prev_sksem=""+sksem;
								prev_nlips=""+nlips;
								prev_skstt=""+skstt;
								prev_nlipk=""+nlipk;
								tkn_info = tkn_info+prev_thsms+"#"+objId+"#"+nmm+"#"+npm+"#"+kdpst+"#"+obj_lvl+"#"+kdkmk+"#"+nakmk+"#"+nlakh+"#"+bobot+"#"+sksmk+"#";
							%>
					<tr>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kdkmk %></B> </label></td></td>
        				<td style="color:#000;text-align:left;"><label><B><%=prev_nakmk %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_sksmk %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kelas %></B> </label></td></td>
        			</tr>
        			<%
							}//prev_thsms!=thsms
							else {
								if(!prev_thsms.equalsIgnoreCase(thsms)) {
								//ganti tahun
								//1. tutup table sebelumnya dengan ngasih nilai ipk dan ips	        		
	        		%>
	        		<tr>
        				<td style="background:#369;color:#fff;text-align:right" colspan="4">
        					<input type="hidden" name="tkn_krs" value="<%=tkn_info %>" />
        					<input type="submit" value="Download KRS" style="height:35px;width:150px" />
        				</td>
        			</tr>
        		</table>
        		</form>
        		<br />	
					<%	
									//reset tkn_info
									tkn_info = "#";
									prev_thsms = ""+thsms;
									prev_kdkmk=""+kdkmk;
									prev_nakmk=""+nakmk;
									prev_nlakh=""+nlakh;
									prev_bobot=""+bobot;
									prev_sksmk=""+sksmk;
									prev_kelas=""+kelas;
									prev_sksem=""+sksem;
									prev_nlips=""+nlips;
									prev_skstt=""+skstt;
									prev_nlipk=""+nlipk;
					%>
			<form action="go.downloadKrs" >	
				<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:700px">
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center" colspan="4"><label><B><%=prev_thsms %></B> </label></td></td>
        			</tr>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center;width:60px"><label><B>KODE</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:left;width:550px"><label><B>MATAKULIAH</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:center;width:35px"><label><B>SKS</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:center;width:55px"><label><B>KELAS</B> </label></td></td>
        			</tr>
        			<tr>
        			<%
        							tkn_info = tkn_info+prev_thsms+"#"+objId+"#"+nmm+"#"+npm+"#"+kdpst+"#"+obj_lvl+"#"+kdkmk+"#"+nakmk+"#"+nlakh+"#"+bobot+"#"+sksmk+"#";
        			%>	
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kdkmk %></B> </label></td></td>
        				<td style="color:#000;text-align:left;"><label><B><%=prev_nakmk %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_sksmk %></B> </label></td></td>
        				<td style="color:#000;text-align:center;"><label><B><%=prev_kelas %></B> </label></td></td>
        			</tr>
        		<% 	
								}
							}
						}//end while
					/*
					*tutup table setelah end while
					*/
		
	        		%>	
	        		<tr>
        				<td style="background:#369;color:#fff;text-align:right" colspan="4">
        					<input type="hidden" name="tkn_krs" value="<%=tkn_info %>" />
        					<input type="submit" value="Download KRS" style="height:35px;width:150px" />
        				</td>
        			</tr>
        		</table>
        		</form>
        		<%
					}
				}
				else {
					
				%>
				<h1 style="text-align:center">BELUM ADA REKORD KRS/KHS</h1>
				<%
				}
			}//end if(size>0)
			else {
				%>
				<h2 align="center"><b>Anda Tidak Mempunyai Hak Akses Untuk Data ini</b></h2>
			<%
			}
		//}
		%>
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>