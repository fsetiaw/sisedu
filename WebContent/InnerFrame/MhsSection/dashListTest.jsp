<!DOCTYPE html>
<html>
<head>

<title>Insert title here</title>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.Constants" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.sistem.*" %>
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");

Vector vListTest= (Vector)request.getAttribute("vListTest");
Vector vListTestForOpr= (Vector)request.getAttribute("vListTestForOpr");
String stat=null;
//brs1
String idCivJdwlBridge=null;
String idJdwlTest=null;
String kdpst=null;
String npmhs=null;
String wajib=null;
String rightAnswer=null;
String wrongAnswer=null;
String nilai=null;
String taken=null;
String notePengawas=null;
String sisaWaktu=null;
String lulus=null;
//brs2
String idOnlineTest=null;
String schedDateTime=null; 
String RealDateTimeStart=null;
String RealDateTimeEnd=null;
String canceled=null;
String done=null;
String inprogress=null;
String pause=null;
String npmopr=null;
String nmmopr=null;
String mhstt=null;
String room=null;
String ipAllow=null;
String reusable=null;
//brs3
String nmmTest=null;
String keterTest=null;
String totalSoal=null;
String totalWaktu=null;
String passingGrade=null;


//brs1-opr
String idOnlineTestOpr=null;
String schedDateTimeOpr=null;
String RealDateTimeStartOpr=null;
String RealDateTimeEndOpr=null;
String canceledOpr=null;
String doneOpr=null;
String inprogressOpr=null;
String pauseOpr=null;
String npmoprOpr=null;
String nmmoprOpr=null;
String mhsttOpr=null;
String roomOpr=null;
String ipAllowOpr=null;
String npmopr_allowOpr=null;
String idJadwalTestOpr=null;
String reusableOpr=null;
//brs2-opr
String tknNpmPeserta = null;
int totPeserta =0 ;
//brs3-opr
String nmmTestOpr=null;
String keterTestOpr=null;
String totalSoalOpr=null;
String totalWaktuOpr=null;
String passingGradeOpr=null;


//add
String schedDateTimeEnd=null;

if(vListTest!=null && vListTest.size()>0) {
	ListIterator li = vListTest.listIterator();
	while(li.hasNext()) {
		String brs1 = (String)li.next();
		StringTokenizer st = new StringTokenizer(brs1,"#&");
		idCivJdwlBridge=st.nextToken();
		idJdwlTest=st.nextToken();
		kdpst=st.nextToken();
		npmhs=st.nextToken();
		wajib=st.nextToken();
		rightAnswer=st.nextToken();
		wrongAnswer=st.nextToken();
		nilai=st.nextToken();
		taken=st.nextToken();
		notePengawas=st.nextToken();
		sisaWaktu=st.nextToken();
		lulus=st.nextToken();
		
		String brs2 = (String)li.next();
		st = new StringTokenizer(brs2,"#&");
		idOnlineTest=st.nextToken();
		schedDateTime=st.nextToken();
		RealDateTimeStart=st.nextToken();
		RealDateTimeEnd=st.nextToken();
		canceled=st.nextToken();
		done=st.nextToken();
		inprogress=st.nextToken();
		pause=st.nextToken();
		npmopr=st.nextToken();
		nmmopr=st.nextToken();
		mhstt=st.nextToken();
		room=st.nextToken();
		ipAllow=st.nextToken();
		reusable=st.nextToken();
		
		String brs3 = (String)li.next();
		st = new StringTokenizer(brs3,"#&");
		nmmTest=st.nextToken();
		keterTest=st.nextToken();
		totalSoal=st.nextToken();
		totalWaktu=st.nextToken();
		passingGrade=st.nextToken();
	}
}

%>

</head>
<body>
<div id="header">
<%@ include file="MhsInnerMenu.jsp"%>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		
		<%
		boolean noTestForU = true;;
		int i = 0;
		if(vListTest!=null && vListTest.size()>0) {
			ListIterator li = vListTest.listIterator();
			noTestForU = false;
			if(li.hasNext()) {
		%>
		
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:750px">
			<tr>
	        	<td style="background:#369;color:#fff;text-align:center" colspan="7"><label><B>DAFTAR TEST</B> </label></td>
	        </tr>
	        <tr>
	        	<td style="background:#369;color:#fff;text-align:center;width:10px" rowspan="2"><label><B>NO</B> </label></td>
	        	<td style="background:#369;color:#fff;text-align:left;width:250px" rowspan="2"><label><B>KETERANGAN TEST</B> </label></td>
	        	<td style="background:#369;color:#fff;text-align:center;width:10px" rowspan="2"><label><B>WAJIB</B> </label></td>
	        	<td style="background:#369;color:#fff;text-align:center;width:200px" colspan="2"><label><B>JADWAL TEST</B> </label></td>
	        	<td style="background:#369;color:#fff;text-align:center;width:10px" rowspan="2"><label><B>STATUS</B> </label></td>
	        	<td style="background:#369;color:#fff;text-align:center;width:20px" rowspan="2"><label><B>ROOM</B> </label></td>
	        </tr>
	        <tr>	
	        	<td style="background:#369;color:#fff;text-align:center;width:100px"><label><B>START</B> </label></td>
	        	<td style="background:#369;color:#fff;text-align:center;width:100px"><label><B>END</B> </label></td>
	        </tr>	
	    <%
	    		while(li.hasNext()) {
	    			i++;
					String brs1 = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs1,"#&");
					idCivJdwlBridge=st.nextToken();
					idJdwlTest=st.nextToken();
					kdpst=st.nextToken();
					npmhs=st.nextToken();
					wajib=st.nextToken();
					rightAnswer=st.nextToken();
					wrongAnswer=st.nextToken();
					nilai=st.nextToken();
					taken=st.nextToken();
					notePengawas=st.nextToken();
					sisaWaktu=st.nextToken();
					lulus=st.nextToken();
			
					String brs2 = (String)li.next();
					st = new StringTokenizer(brs2,"#&");
					idOnlineTest=st.nextToken();
					schedDateTime=st.nextToken();
					
					RealDateTimeStart=st.nextToken();
					RealDateTimeEnd=st.nextToken();
					canceled=st.nextToken();
					done=st.nextToken();
					inprogress=st.nextToken();
					pause=st.nextToken();
					npmopr=st.nextToken();
					nmmopr=st.nextToken();
					mhstt=st.nextToken();
					room=st.nextToken();
					ipAllow=st.nextToken();
					
					String brs3 = (String)li.next();
					st = new StringTokenizer(brs3,"#&");
					nmmTest=st.nextToken();
					keterTest=st.nextToken();
					totalSoal=st.nextToken();
					totalWaktu=st.nextToken();
					passingGrade=st.nextToken();
					
					schedDateTimeEnd=AskSystem.getDateTimeGivenPlus(schedDateTime,totalWaktu);
					%>
			<tr>
	        	<td style="color:#000;text-align:center;"><label><B><%=i %></B> </label></td>
	        	<td style="color:#000;text-align:left;"><label><B><%=keterTest %></B> </label></td>
	        	<td style="color:#000;text-align:center;"><label><B>
	        	<%
	        		if(wajib.equalsIgnoreCase("true")) {
	        			out.print("YA");
	        		}
	        		else {
	        			out.print("TIDAK");
	        		}
	        	%></B> </label></td>
	        	<td style="color:#000;text-align:center;"><label><B>
	        	<%
	        	if(reusable.equalsIgnoreCase("true")) {
	        		out.print("ON DEMAND");
	        	}
	        	else {
	        		out.print(schedDateTime);
	        	}
	        	 
	        	%></B> </label></td>
	        	<td style="color:#000;text-align:center;"><label><B>
	        	<%
	        	if(reusable.equalsIgnoreCase("true")) {
	        		out.print("N/A");
	        	}
	        	else {
	        		out.print(schedDateTimeEnd);
	        	}
	        	 
	        	%></B> </label></td>
	        	<td style="color:#000;text-align:center;"><label><B>
	        	<%
	        		if(taken.equalsIgnoreCase("true")) {
	        			stat = "UJIAN SDH DIAMBIL";
        				out.print("UJIAN SDH DIAMBIL");
	        		}
	        		else {
	        			if(canceled.equalsIgnoreCase("true")) {
	        				stat = "UJIAN BATAL";
	        				out.print("UJIAN BATAL");
	        			}
	        			else {
	        				if(done.equalsIgnoreCase("true")) {
	        					out.print("UJIAN SELESAI");
	        					stat = "UJIAN SELESAI";
	        				}
	        				else {
	        					if(pause.equalsIgnoreCase("true")) {
	        						out.print("SEDANG REHAT");
	        						stat = "SEDANG REHAT";
	        					}
	        					else {
	        						if(inprogress.equalsIgnoreCase("true")) {
		        						out.print("SEDANG BERJALAN");
		        						stat = "SEDANG BERJALAN";
		        					}
	        						else {
	        							out.print("SESUAI JADWAL");
	        							stat = "SESUAI JADWAL";
	        						}	
	        					}
	        				}
	        			}	
	        		}
	        	//08129598033 -
	        	%>
	        		</B> </label>
	        	</td>
	        	<td style="color:#000;text-align:center;"><label><B>
	        	<%
	        		String ruang = "TBA";
	        		if(!room.equalsIgnoreCase("null")) {
	        			ruang = ""+room;
	        		}
	        		if(ipAllow.equalsIgnoreCase("0.0.0.0")) {
	        			ruang = "ONLINE";
	        		}
	        		out.println(ruang);
	        		%>
	        		</B></label>
	        	</td>
	        	<%
	        	/*
	        	*	jika reusable ujian:
	        	*	ditentukan oleh id and real_time_	
	        	*/
	        		//if(inprogress.equalsIgnoreCase("true") && !done.equalsIgnoreCase("true") && !canceled.equalsIgnoreCase("true") && taken.equalsIgnoreCase("false")) {
	        		if(reusable.equalsIgnoreCase("true")) {	
	        			if(inprogress.equalsIgnoreCase("true")) {	
	        		
	        	%>
	        	<form action="go.prepSoalUjian">
	        		<input type="hidden" name="RealDateTimeStart" value="<%=RealDateTimeStart %>" />
	        		<input type="hidden" name="nmmTest" value="<%=nmmTest %>" />
					<input type="hidden" name="keterTest" value="<%=keterTest %>" />
					<input type="hidden" name="totalSoal" value="<%=totalSoal %>" />
					<input type="hidden" name="totalWaktu" value="<%=totalWaktu %>" />
					<input type="hidden" name="passingGrade" value="<%=passingGrade %>" />
					<input type="hidden" name="idCivJdwlBridge" value="<%=idCivJdwlBridge %>" />
	        		<input type="hidden" name="idJdwlTest" value="<%=idJdwlTest %>" />
	        		<input type="hidden" name="idOnlineTest" value="<%=idOnlineTest %>" />
	        		<input type="hidden" name="room" value="<%=room %>" />
	        		<input type="hidden" name="ipAllow" value="<%=ipAllow %>" />
	        		<input type="hidden" name="stat" value="<%=stat %>" />
	        		<%
	        		if(taken.equalsIgnoreCase("false")) {
	        		%>
				<td style="background:#369;color:#000;text-align:center;"><input type="submit" value="AMBIL&#13;&#10;UJIAN&#13;&#10;" style="text-align:center;font-weight:bold;color:red"></td>	        
					<%
					}
	        		else {
	        		%>
	    		<td style="background:#369;color:#000;text-align:center;"><input type="submit" value="UJIAN&#13;&#10;ULANG&#13;&#10;" style="text-align:center;font-weight:bold;color:red"></td>	        
	    		<%	
	        		}
					%>	        	
	        	</form>
	        	<%
	        			}
	        		}
	        		else {
	        			//non reusable
	        			if(inprogress.equalsIgnoreCase("true") && !done.equalsIgnoreCase("true") && !canceled.equalsIgnoreCase("true") && taken.equalsIgnoreCase("false")) {
	        		%>
	        	<form action="go.prepSoalUjian">
	        		<input type="hidden" name="RealDateTimeStart" value="<%=RealDateTimeStart %>" />
	        	    <input type="hidden" name="nmmTest" value="<%=nmmTest %>" />
	        		<input type="hidden" name="keterTest" value="<%=keterTest %>" />
	        		<input type="hidden" name="totalSoal" value="<%=totalSoal %>" />
	        		<input type="hidden" name="totalWaktu" value="<%=totalWaktu %>" />
	        		<input type="hidden" name="passingGrade" value="<%=passingGrade %>" />
	        		<input type="hidden" name="idCivJdwlBridge" value="<%=idCivJdwlBridge %>" />
	        	    <input type="hidden" name="idJdwlTest" value="<%=idJdwlTest %>" />
	        	    <input type="hidden" name="idOnlineTest" value="<%=idOnlineTest %>" />
	        	    <input type="hidden" name="room" value="<%=room %>" />
	        	    <input type="hidden" name="ipAllow" value="<%=ipAllow %>" />
	        	    <input type="hidden" name="stat" value="<%=stat %>" />
	        	<td style="background:#369;color:#000;text-align:center;"><input type="submit" value="AMBIL&#13;&#10;UJIAN&#13;&#10;" style="text-align:center;font-weight:bold;color:red"></td>	        
	        	</form>
	        	        	<%
	        			}
	        		}
	        	%>
	        	</tr>
					<%
				}
	    	%>
	    	</table>
	    	
	    	<%
			}
		}
		//else {
			
		//}
		boolean permitedOpr = false;
		i = 0;
		if(vListTestForOpr!=null && vListTestForOpr.size()>0) {
			ListIterator li = vListTestForOpr.listIterator();
			noTestForU = false;
			if(li.hasNext()) {
		%>
				
			<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:750px">
				<tr>
			       	<td style="background:#369;color:#fff;text-align:center" colspan="7"><label><B>DAFTAR TEST</B> </label></td>
		      	</tr>
			    <tr>
			      	<td style="background:#369;color:#fff;text-align:center;width:10px" rowspan="2"><label><B>NO</B> </label></td>
			        <td style="background:#369;color:#fff;text-align:left;width:250px" rowspan="2"><label><B>KETERANGAN TEST</B> </label></td>
			        
			        <td style="background:#369;color:#fff;text-align:center;width:200px" colspan="2"><label><B>JADWAL TEST</B> </label></td>
			        <td style="background:#369;color:#fff;text-align:center;width:20px" rowspan="2"><label><B>STATUS</B> </label></td>
			        <td style="background:#369;color:#fff;text-align:center;width:20px" rowspan="2"><label><B>ROOM</B> </label></td>
			  	</tr>
			   	<tr>	
			      	<td style="background:#369;color:#fff;text-align:center;width:100px"><label><B>START</B> </label></td>
			        <td style="background:#369;color:#fff;text-align:center;width:100px"><label><B>END</B> </label></td>
			  	</tr>	
		<%
		  		while(li.hasNext()) {
			   		i++;
					String brs1 = (String)li.next();
					StringTokenizer st = new StringTokenizer(brs1,"#&");
					idOnlineTestOpr=st.nextToken();
					schedDateTimeOpr=st.nextToken();
					RealDateTimeStartOpr=st.nextToken();
					RealDateTimeEndOpr=st.nextToken();
					canceledOpr=st.nextToken();
					doneOpr=st.nextToken();
					inprogressOpr=st.nextToken();
					pauseOpr=st.nextToken();
					npmoprOpr=st.nextToken();
					nmmoprOpr=st.nextToken();
					mhsttOpr=st.nextToken();
					roomOpr=st.nextToken();
					ipAllowOpr=st.nextToken();
					npmopr_allowOpr=st.nextToken();
					if(npmopr_allowOpr.contains(validUsr.getNpm())) {
						permitedOpr=true;
					}
					idJadwalTestOpr=st.nextToken();
					reusableOpr=st.nextToken();
					System.out.println("<br/>1.idJadwalTestOpr="+idJadwalTestOpr);
					tknNpmPeserta = (String)li.next();
					st = new StringTokenizer(tknNpmPeserta,",");
					totPeserta = st.countTokens();
					
					String brs3 = (String)li.next();
					st = new StringTokenizer(brs3,"#&");
					nmmTestOpr=st.nextToken();
					keterTestOpr=st.nextToken();
					totalSoalOpr=st.nextToken();
					totalWaktuOpr=st.nextToken();
					passingGradeOpr=st.nextToken();
					
					schedDateTimeEnd=AskSystem.getDateTimeGivenPlus(schedDateTime,totalWaktuOpr);
					%>
				<tr>
		        	<td style="color:#000;text-align:center;"><label><B><%=i %></B> </label></td>
		        	<td style="color:#000;text-align:left;"><label><B><%=keterTestOpr %></B> </label></td>
		        	<td style="color:#000;text-align:center;"><label><B><%=schedDateTimeOpr %></B> </label></td>
		        	<td style="color:#000;text-align:center;"><label><B><%=schedDateTimeEnd %></B> </label></td>
		        	<td style="color:#000;text-align:center;"><label><B>
		        	<%
		        	if(canceledOpr.equalsIgnoreCase("true")) {
		        		out.print("UJIAN BATAL");
		        		stat="UJIAN BATAL";
		        	}
		        	else {
		        		if(doneOpr.equalsIgnoreCase("true")) {
		        			if(reusableOpr.equalsIgnoreCase("false")) {  		
		        				out.print("UJIAN SELESAI");
		        				stat="UJIAN SELESAI";
		        			}
		        			else {
		        				out.print("SELESAI / REUSABLE");
		        				stat="REUSABLE";
		        			}
		        		}
		        		else {
		        			if(pauseOpr.equalsIgnoreCase("true")) {
		        				out.print("SEDANG REHAT");
		        				stat="SEDANG REHAT";
		        			}
		        			else {
		        				if(inprogressOpr.equalsIgnoreCase("true")) {
		        					out.print("SEDANG BERJALAN");
		        					stat="SEDANG BERJALAN";
		        				}
		        				else {
		        					out.print("SESUAI JADWAL");
		        					stat="SESUAI JADWAL";
		        				}	
		        			}
		        		}
		        	}
		        	%>
		        	</B> </label></td>
		        	<td style="color:#000;text-align:center;"><label><B>
		        	<%
		        	String ruang = "TBA";
		        	if(!roomOpr.equalsIgnoreCase("null")) {
		        		ruang = ""+roomOpr;
		        	}
		        	if(ipAllowOpr.equalsIgnoreCase("0.0.0.0")) {
		        		ruang = "ONLINE";
		        	}
		        	out.println(ruang);
		        	%>
		        	</B></label></td>
		        	<%
		        	//if(inprogressOpr.equalsIgnoreCase("true") && !doneOpr.equalsIgnoreCase("true") && !canceledOpr.equalsIgnoreCase("true")) {
		        	//if((doneOpr.equalsIgnoreCase("true")&&permitedOpr)  ||  ((inprogressOpr.equalsIgnoreCase("false") || (inprogressOpr.equalsIgnoreCase("true")&&npmoprOpr.equalsIgnoreCase(validUsr.getNpm())))&&canceledOpr.equalsIgnoreCase("false")&&doneOpr.equalsIgnoreCase("false"))) {	
		        	target = Constants.getRootWeb()+"/InnerFrame/Ujian/dashPengawasUjian.jsp";
		        	uri = request.getRequestURI();
		        	url = PathFinder.getPath(uri, target);
		        	if(reusableOpr.equalsIgnoreCase("true")) {
		        		if(permitedOpr) {	
		        			if(inprogressOpr.equalsIgnoreCase("true") && !npmoprOpr.equalsIgnoreCase(validUsr.getNpm())) {
		        			}
		        			else {
		        	%>
		        	<form action=<%=url %>>
		        		<%
		        		System.out.println("-stat="+stat);
		        		%>
		        		<input type="hidden" name="RealDateTimeStartOpr" value="<%=RealDateTimeStartOpr %>" />
		        		<input type="hidden" name="nmmTest" value="<%=nmmTestOpr %>" />
						<input type="hidden" name="keterTest" value="<%=keterTestOpr %>" />
						<input type="hidden" name="totalSoal" value="<%=totalSoalOpr %>" />
						<input type="hidden" name="totalWaktu" value="<%=totalWaktuOpr %>" />
						<input type="hidden" name="passingGrade" value="<%=passingGradeOpr %>" />
		        		<input type="hidden" name="stat" value="<%=stat %>" />
		        		<input type="hidden" name="idJadwalTest" value="<%=idJadwalTestOpr %>" />
		        		<!--  input type="hidden" name="reusable" value="<%=reusable %>" / -->
						<td style="background:#369;color:#000;text-align:center;"><input type="submit" value="LOGIN &#13;&#10;PENGAWAS&#13;&#10;" style="text-align:center;font-weight:bold;color:red"></td>	        
		        	</form>
		        	<%
		        			}
		        		}
		        	}
		        	else {
		        		//non reusable
		        		if(permitedOpr) {
		        			if(doneOpr.equalsIgnoreCase("false") && (inprogressOpr.equalsIgnoreCase("false") || (inprogressOpr.equalsIgnoreCase("true")&&npmoprOpr.equalsIgnoreCase(validUsr.getNpm())))&&canceledOpr.equalsIgnoreCase("false")) {
		        		%>
			        <form action=<%=url %>>
			        	<%
			        		System.out.println("-stat="+stat);
			        	%>
			        	<input type="hidden" name="RealDateTimeStartOpr" value="<%=RealDateTimeStartOpr %>" />
			        	<input type="hidden" name="nmmTest" value="<%=nmmTestOpr %>" />
						<input type="hidden" name="keterTest" value="<%=keterTestOpr %>" />
						<input type="hidden" name="totalSoal" value="<%=totalSoalOpr %>" />
						<input type="hidden" name="totalWaktu" value="<%=totalWaktuOpr %>" />
						<input type="hidden" name="passingGrade" value="<%=passingGradeOpr %>" />
			        	<input type="hidden" name="stat" value="<%=stat %>" />
			        	<input type="hidden" name="idJadwalTest" value="<%=idJadwalTestOpr %>" />
			        	<!--  input type="hidden" name="reusable" value="<%=reusable %>" / -->
						<td style="background:#369;color:#000;text-align:center;"><input type="submit" value="LOGIN &#13;&#10;PENGAWAS&#13;&#10;" style="text-align:center;font-weight:bold;color:red"></td>	        
			        </form>
			        	<%
		        			}
		        		}
		        	}
		        	%>
		        </tr>
						<%
					
		  		}	
		%>
			</table>	
		<%
			}
			
		}	
		
		if(noTestForU) {
		%>	
			<h2 style="text-align:center;font-weight:bold">Anda Tidak Terdaftar Untuk Mengikuti Test Online</h2> 
		<%	
		}
		%>
		<!-- Column 1 end   -->
		<br />
	</div>
</div>	
</body>
</html>