<!DOCTYPE html>
<head>
<%@ page import="beans.dbase.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.login.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="java.io.File" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<title>Insert title here</title>
<%
	String status_cancel_done_inprogress_pause = request.getParameter("status_cancel_done_inprogress_pause");
	boolean canceled = false;
	boolean done = false;
	boolean inprogress=false;
	boolean pause = false;
	StringTokenizer st = null;
	if(status_cancel_done_inprogress_pause!=null) {
		st = new StringTokenizer(status_cancel_done_inprogress_pause,",");
		canceled = Boolean.valueOf(st.nextToken()).booleanValue();
		done = Boolean.valueOf(st.nextToken()).booleanValue();
		inprogress=Boolean.valueOf(st.nextToken()).booleanValue();
		pause = Boolean.valueOf(st.nextToken()).booleanValue();
	}
	
	//System.out.println("sini2");
	//System.out.println(status_cancel_done_inprogress_pause);
	String answer = request.getParameter("jawaban");
	//System.out.println("answer = "+answer);
	String RealDateTimeStart = request.getParameter("RealDateTimeStart");
	//System.out.println("RealDateTimeStart="+RealDateTimeStart);
	String idOnlineTest = request.getParameter("idOnlineTest");
	String idJdwlTest = request.getParameter("idJdwlTest");
	String idCivJdwlBridge = request.getParameter("idCivJdwlBridge");
	//System.out.println("idJdwlTest="+idJdwlTest);
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String tokenKodeGroupAndListSoal = ""+(String)request.getParameter("tokenKodeGroupAndListSoal");
	int totalSoalUjian = Tool.getTotalSoalUjian(tokenKodeGroupAndListSoal);
	//System.out.println("aneh = "+tokenKodeGroupAndListSoal);
	st = new StringTokenizer(tokenKodeGroupAndListSoal,"$$");
	String atChapter = request.getParameter("atChapter");
	String atSoal = request.getParameter("atSoal");
	if(atChapter==null || atChapter.equalsIgnoreCase("null")) {
		atChapter="1";
	}
	////System.out.println("1");
	String tknSoal = request.getParameter("tknSoal");
	////System.out.println("1tokenSoal="+tknSoal);
	tknSoal = tknSoal.replace("o/o","%");
	tknSoal=tknSoal.replace("##","?");
	tknSoal=tknSoal.replace("tandaTambah","+");
	tknSoal=tknSoal.replace("tandaPi","&Pi;");
	tknSoal=tknSoal.replace("tandaAkar","&radic;");
	tknSoal=tknSoal.replace("tandaSpasi","&nbsp;");
	tknSoal=tknSoal.replace("tandaKutip","&quot;");
	
	////System.out.println("1tokenSoal="+tknSoal);
	//request.removeAttribute("vSoal");
	String idSoal = request.getParameter("id");
	
	int noBagian = 0;
	//String tkn_idsoal_atChapter = Tool.createTokenIdSoalAtChapterSesuaiNorutSoal(tokenKodeGroupAndListSoal);
%>
<script type="text/javascript">
   
</script>

</head>
<!--  body onload="location.href='#'" -->
<body>

<div id="header">
	<ul>
	<%
	//atSoal = "1";//maksudnya soal pertama pada chapter terkait
	String url_ff=null;
	//System.out.println("st countTokens()="+st.countTokens());
	for(int i=1;i<= st.countTokens();i++) {
		if(atChapter.equalsIgnoreCase(""+i)) {
			//System.out.println("atChpter="+atChapter);
			noBagian=i;
			
		%>
		<li><a href="go.gantiSoal?idOnlineTest=<%=idOnlineTest %>&idCivJdwlBridge=<%=idCivJdwlBridge %>&idJdwlTest=<%=idJdwlTest %>&atChapter=<%=i %>&tokenKodeGroupAndListSoal=<%=tokenKodeGroupAndListSoal %>&RealDateTimeStart=<%=RealDateTimeStart %>" target="_self" class="active">BAGIAN <span><%=i %></span></a></li>
		<%	
		}
		else {
		%>
		<li><a href="go.gantiSoal?idOnlineTest=<%=idOnlineTest %>&idCivJdwlBridge=<%=idCivJdwlBridge %>&idJdwlTest=<%=idJdwlTest %>&atChapter=<%=i %>&tokenKodeGroupAndListSoal=<%=tokenKodeGroupAndListSoal %>&RealDateTimeStart=<%=RealDateTimeStart %>" target="_self">BAGIAN <span><%=i %></span></a></li>
		<%	
		}
	}
	////System.out.println("2");
	%>
		
	</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<%
		int norut = 0;
		String tkn_idsoal_atChapter = Tool.createTokenIdSoalAtChapterSesuaiNorutSoalForNavigasiIdSoalInMiddlePos(tokenKodeGroupAndListSoal,idSoal);
		//System.out.println("3");
		//System.out.println(tkn_idsoal_atChapter);
		StringTokenizer stt = new StringTokenizer(tkn_idsoal_atChapter,",");
		//System.out.println("stt="+tkn_idsoal_atChapter);
		if(stt!=null) {
			%>
			<table align="center">
				<tr>	
				<td style="text-align:center">NOMOR SOAL :</td>
			<%
			boolean first=true;
			while(stt.hasMoreTokens()) {
				//norut++;
				
				String tmpNorutNav = stt.nextToken();
				String tmpIdSoal = stt.nextToken();
				String tmpAtChapter = stt.nextToken();
				//cek apa butuh prev menu
				if(first) {
					first = false;
					
					if(!tmpNorutNav.equalsIgnoreCase("1")) {
						String infoSoal = ToolSoalUjian.gotoDataPrevSoal(tokenKodeGroupAndListSoal,tmpIdSoal);
						StringTokenizer stmp = new StringTokenizer(infoSoal,",");
						String id_soal = stmp.nextToken();
						String atChapt = stmp.nextToken();
					%>	
						<td style="width:30px;text-align:center"><a href="go.gantiSoal?idOnlineTest=<%=idOnlineTest %>&idCivJdwlBridge=<%=idCivJdwlBridge %>&idJdwlTest=<%=idJdwlTest %>&idSoal=<%= id_soal%>&atSoal=<%= Integer.valueOf(tmpNorutNav).intValue()-1%>&atChapter=<%= atChapt%>&tokenKodeGroupAndListSoal=<%=tokenKodeGroupAndListSoal%>&RealDateTimeStart=<%=RealDateTimeStart%>"><B><</B></a></td>
					<%	
					}
				}
				if(tmpIdSoal.equalsIgnoreCase(idSoal)) {
					if(atSoal==null || atSoal.equalsIgnoreCase("null")) {
						atSoal=tmpNorutNav;
					}
				%>
				<td style="width:30px;text-align:center"><div style="font-weight:bold;color:#369;font-size:2em"><%=tmpNorutNav %></div></td>
				<%	
				}
				else {
				%>
				 <!--  td style="width:30px;text-align:center;"><a href="go.gantiSoal?idSoal=<%= tmpIdSoal%>&tknSoal=<%=tknSoal%>&atSoal=<%= tmpNorutNav%>&atChapter=<%= tmpAtChapter%>&tokenKodeGroupAndListSoal=<%=tokenKodeGroupAndListSoal%>"><%=tmpNorutNav %></a></td -->
				 <td style="width:30px;text-align:center;"><a href="go.gantiSoal?idOnlineTest=<%=idOnlineTest %>&idCivJdwlBridge=<%=idCivJdwlBridge %>&idJdwlTest=<%=idJdwlTest %>&idSoal=<%= tmpIdSoal%>&atSoal=<%= tmpNorutNav%>&atChapter=<%= tmpAtChapter%>&tokenKodeGroupAndListSoal=<%=tokenKodeGroupAndListSoal%>&RealDateTimeStart=<%=RealDateTimeStart%>"><%=tmpNorutNav %></a></td>
				<%	
				}
				if(!stt.hasMoreTokens()) {
					if(!tmpNorutNav.equalsIgnoreCase(""+Tool.getTotalSoalUjian(tokenKodeGroupAndListSoal))) {
						String infoSoal = ToolSoalUjian.gotoDataNextSoal(tokenKodeGroupAndListSoal,tmpIdSoal);
						StringTokenizer stmp = new StringTokenizer(infoSoal,",");
						String id_soal = stmp.nextToken();
						String atChapt = stmp.nextToken();
						%>
				<td style="width:30px;text-align:center"><a href="go.gantiSoal?idOnlineTest=<%=idOnlineTest %>&idCivJdwlBridge=<%=idCivJdwlBridge %>&idJdwlTest=<%=idJdwlTest %>&idSoal=<%= id_soal%>&atSoal=<%= Integer.valueOf(tmpNorutNav).intValue()-1%>&atChapter=<%= atChapt%>&tokenKodeGroupAndListSoal=<%=tokenKodeGroupAndListSoal%>&RealDateTimeStart=<%=RealDateTimeStart%>">></a></td>
						<%	
					}
				}
			}
			%>
				</tr>
			</table>

			<br />
			<%
			if(!canceled && !done && !pause && inprogress) {
			%>
			<form action="ins.jawabSoalUjian">	
				<input type="hidden" name="RealDateTimeStart" value="<%=RealDateTimeStart %>"/>
				<input type="hidden" name="totalSoalUjian" value="<%=totalSoalUjian %>"/>
				<input type="hidden" name="atSoal" value="<%=atSoal %>"/>
				<input type="hidden" name="idOnlineTest" value="<%=idOnlineTest %>"/>
				<input type="hidden" name="idSoal" value="<%=idSoal %>"/>
				<input type="hidden" name="idCivJdwlBridge" value="<%=idCivJdwlBridge %>"/>
				<input type="hidden" name="idJdwlTest" value="<%=idJdwlTest %>"/>
				<input type="hidden" name="tokenKodeGroupAndListSoal" value="<%=tokenKodeGroupAndListSoal %>"/>
			<%
			//prep data soal
			//tknSoal = soal+"$$"+tkn_choice+"$$"+pictFile+"$$"+audioFile+"$$"+videoFile;
			//System.out.println("tknSoal--"+tknSoal);
			StringTokenizer sts = new StringTokenizer(tknSoal,"$$");
			String soal = sts.nextToken(); 
			String tknJawaban = sts.nextToken();//tkn_choice
			String pictFile = sts.nextToken();
			String audioFile = sts.nextToken();
			String videoFile = sts.nextToken();
			java.io.File FilePict = new File(pictFile);
			java.io.File FileAudio = new File(audioFile);
			java.io.File FileVideo = new File(videoFile);
	    	
			%>
			
			<table style="width:98%;">
			
				<tr>
					<td>
					<p style="font-size:1.5em;">
					<b><u>SOAL #<%= atSoal%></u></b><br/>
					</p>
					</td>
				</tr>	
			<%

			if(FileAudio.exists()) {
			%>
				<tr>
					<td>
					<p style="font-size:1;">
					<!-- img src="show.passPhoto?picfile=namafiel.jpg" alt="NY International Auto Show" class ="imgInsetShadowGray" style="width:100%; height:100%;"/></a  -->
					<audio controls>
  						<source src="get.getAudioFile?audioFile=<%= FileAudio%>" type="audio/mpeg">
  						Your browser does not support this audio format.
					</audio>
					</p>
					</td>
				</tr>
			<%	
	    		//out.println("exist");
	    	}
			%>
			<tr>
					<td>
					<div style="font-size:1.5em;">
					<%
					soal = soal.replace("!=","&ne;");
					%>
					<%
					if(!soal.equalsIgnoreCase("null")) {
						out.print(soal);
					}
					
					if(FilePict.exists()) {
						%>
							<tr>
								<td>
									<p style="font-size:1;">
									<img src="get.getPicFile?picFile=<%= FilePict%>" alt="NY International Auto Show" class ="imgInsetShadowGray" style="width:90%; height:100%;"/>
									</p>
								</td>
							</tr>
						<%	
							    		//out.println("exist");
						}						
					%>
					</div>
					</td>
				</tr>
				<tr>
					<td>
					<br/>
					<div style="font-size:1.5em;font-weight:bold;color:#424242"><u>PILIHAN JAWABAN:</u></div>
					<p style="font-size:1.5em;color:#424242">
					<%
					//tkn jawaban
					//cek if tkn jawaban = pic file atau token jawaban
					ListIterator li = null;
					Vector vSoal = null;
					int no = 0;
					boolean pilihanPicFile = false;
					////System.out.println("cek apa tknJawabn = gambar "+tknJawaban);
					StringTokenizer st1 = new StringTokenizer(tknJawaban,"||");
					FilePict = new File(st1.nextToken());
					if(FilePict.exists()) {
						////System.out.println("fil ada");
						pilihanPicFile = true;
					%>	
						<img src="get.getPicFile?picFile=<%= FilePict%>" alt="" class ="imgInsetShadowGray" style="width:90%; height:90%;"/>
					<%
					}
					else {
						////System.out.println("fil nga ada");
						vSoal = ToolSoalUjian.acakMultipleChoice(tknJawaban, null);//default char pemisah||
						li = vSoal.listIterator();
						no = 0;
						while(li.hasNext()) {
							no++;
							////System.out.println("no="+no);
							String jawaban = (String)li.next();
							////System.out.println("jawaban="+jawaban);
							out.print(Converter.getAlphabetUntukNorut(no)+".  "+NumberFormater.omitDecimalDigitIfZero(jawaban)+"<br/>");
						}	
					}
					%>
					</p>
					</td>
				</tr>
				<tr>
					<td>
					<p style="font-size:1.5em;">
					PILIH JAWABAN:
					</p>
					<p style="font-size:1.5em">
					<%
					if(pilihanPicFile) {
						%>
					<select style="font-size:1.5em;width:50px;text-align:center" name="jawaban"/>
						<option value="null">-</option>
						<%
						int totPilihan = Integer.valueOf(st1.nextToken()).intValue();
						no = 0;
						while(no<totPilihan) {
							no++;
							String multiChoice = Converter.getAlphabetUntukNorut(no); 
							if(!multiChoice.equalsIgnoreCase(answer)) {
							%>
							<option value="<%=multiChoice%>"><%=multiChoice %></option>
							<%
							}
							else {
								%>
								<option value="<%=multiChoice%>" selected><%=multiChoice %></option>
								<%
							}
						}
						%>
						</select>
						<%
					}
					else {
					%>
					<select style="font-size:1.5em;width:50px;text-align:center" name="jawaban"/>
						<option value="null">-</option>
					<%
					li = vSoal.listIterator();
					no = 0;
					while(li.hasNext()) {
						no++;
						String jawaban = (String)li.next();
						String multiChoice = Converter.getAlphabetUntukNorut(no); 
						if(!jawaban.equalsIgnoreCase(answer)) {
						%>
						<option value="<%=jawaban%>"><%=multiChoice %></option>
						<%
						}
						else {
							%>
							<option value="<%=jawaban%>" selected><%=multiChoice %></option>
							<%
						}
					}
					%>
					</select>
					<%
					}
					%>
					<input type="submit" style="vertical-align:middle;padding:10px 10px;margin:0px 0px 13px 20px;font-weight:bold;color:#369" value="SOAL BERIKUT >>"/>
					</p>
					
					</td>
				</tr>
			</table>
			</form>
			<%
			}
			else {
				if(!canceled && !done && pause && inprogress) {
					//sedang di pause
				%>	
					<table align="center">
					<tr>	
						<td style="text-align:center;font-size:3em">UJIAN SEDANG DI-REHAT OLEH PENGAWAS</td>
					</tr>
					<tr>	
						<td style="text-align:center;font-size:2em">Anda dapat melanjutkan ujian setelah masa rehat selesai dengan meng-klik nomor soal diatas</td>
					</tr>
					</table>	
				<%	
				}
				else {
					if(canceled) {
						%>	
						<table align="center">
						<tr>	
							<td style="text-align:center;font-size:3em">UJIAN DIBATALKAN</td>
						</tr>
						<tr>	
							<td style="text-align:center;font-size:2em">Jadwal Ujian Ini Telah Dibatalkan, dan Anda Akan Dialihkan Ke Halaman Muka</td>
						</tr>
						</table>
						<META HTTP-EQUIV="refresh" CONTENT="5; URL=<%=Constants.getRootWeb()+"/index.jsp"%>">	
					<%
					}
					else {
						if(done) {
						%>	
						<table align="center">
						<tr>	
							<td style="text-align:center;font-size:3em;font-weight:bold">WAKTU UJIAN SUDAH SELESAI</td>
						</tr>
						<tr>	
							<td style="text-align:center;font-size:2em">Ujian Telah Ditutup dan Anda Akan Dialihkan Ke Halaman Muka </td>
						</tr>
						</table>
						<META HTTP-EQUIV="refresh" CONTENT="5; URL=<%=Constants.getRootWeb()+"/index.jsp"%>">	
					<%
						}
					}
					
				}
			}
		}
		/*
		//System.out.println("4");
		out.print("<br/>tkn_idsoal_atChapter="+tkn_idsoal_atChapter);
		out.print("<br/>noBagian="+noBagian);
		out.print("<br/>subtt="+Tool.getTotalSoalUjianAtBag(tokenKodeGroupAndListSoal,""+noBagian));
		out.print("<br/>soaltt="+Tool.getTotalSoalUjian(tokenKodeGroupAndListSoal));
		out.print("<br/>atSoal="+atSoal);
		out.print("<br/>atChapter="+atChapter);
		out.print("<br/>tokenKodeGroupAndListSoal="+tokenKodeGroupAndListSoal);
		out.print("<br/>idSoal="+idSoal);
		out.print("<br/>tknsoal="+tknSoal);
		*/
		//out.print("<br/>atSoal="+atSoal);
		%>
	</div>
</div>		
</body>
	