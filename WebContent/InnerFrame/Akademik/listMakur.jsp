<!DOCTYPE html>
<head>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.dbase.SearchDb" %>
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
	request.setAttribute("atPage", "listMakur");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	String kdpst_nmpst = request.getParameter("kdpst_nmpst");
	String idkur_ = ""+request.getParameter("idkur");
	StringTokenizer st = new StringTokenizer(kdpst_nmpst,",");
	String kdpst = st.nextToken();
	String nmpst = "";
	while(st.hasMoreTokens()) {
		nmpst = nmpst+st.nextToken();
		if(st.hasMoreTokens()) {
			nmpst = nmpst+" ";
		}
	}
	//SearchDb sdb = new SearchDb();
	boolean allowEditListKelas = false;
	String callerPage = request.getParameter("callerPage");
	Vector vInfoMakur = (Vector)request.getAttribute("vInfoMakur");
	String nama_prodi = Converter.getNamaKdpst(kdpst);
	
%>


</head>
<body>
<div id="header">
	<ul>
	<%
	
	if(callerPage!=null && !Checker.isStringNullOrEmpty(callerPage) && (!callerPage.equalsIgnoreCase(request.getRequestURI()))) {
		%>
			<li><a href="<%=callerPage%>" target="inner_iframe">GO<span>BACK</span></a></li>
		<%
	}
	
	Vector vEditMkKur = validUsr.getScopeUpd7des2012("hasEditMkKurikulum");
	if(vEditMkKur.size()>0) {
		allowEditListKelas = true;
	%>
		<jsp:include page="${PageContext.ServletContext.ContextPath}/InnerFrame/Akademik/innerMenu.jsp" />
	<%
	}
	%>
	</ul>
</div>

<div class="colmask fullpage">
	<div class="col1">
	<section style="text-align:center;background:#eee;font-size:2em;color:#369;font-weight:bold">
		TAMBAH / EDIT MATAKULIAH <> KURIKULUM
	</section>
		<br />
		<!-- Column 1 start -->
		<br />
		<p>
		
		<%
		if(idkur_==null || idkur_.equalsIgnoreCase("null")) {
		%>
		<table align="center" border="1" style="color:#000;width:800px;background:#d9e1e5;">	
			<tr>
				<td style="background:#369;color:#fff;text-align:center;font-size:1.5em" colspan="4"><b>FORM EDIT MATAKULIAH DALAM KURIKULUM <br/><%= nama_prodi %></b></td>
			</tr>
			<tr>
				
				<td style="width:50%;text-align:left;background:#87B1DA;">NAMA/KODE KURIKULUM</td>	
				<td style="width:25%;text-align:center;background:#87B1DA;">TOTAL MATAKULIAH</td>
				<td style="width:25%;text-align:center;background:#87B1DA;">TOTAL SKS</td>
			</tr>	
			
			<%
			ListIterator li=vInfoMakur.listIterator();
			if(li.hasNext()) {	
			
			%>
			
				
		<%
		
				while(li.hasNext()) {
					String baris1 = (String)li.next();
					st = new StringTokenizer(baris1);
					String idkur = st.nextToken();
					String start = st.nextToken();
					String ended = st.nextToken();
					String nmkur = (String)li.next();
					//System.out.println(baris1);
					//System.out.println(nmkur);
					Vector vInfo = (Vector)li.next();
					String ttkls = (String)li.next();
					String skstt = (String)li.next();
		%>
			<form action="get.listMakur" method="post">
			<input type="hidden" value="<%=kdpst %>,<%=nmpst %>" name="kdpst_nmpst" />
			<tr>
				<input type="hidden" name="idkur" value="<%=idkur%>" />
				<td width="50%"><%=nmkur.toUpperCase() %></td>	
				<td style="width:25%;text-align:center"><%=ttkls %></td>
				<td style="width:25%;text-align:center"><%=skstt %></td>
				<td style="text-align:center;background:#87B1DA"><input type="submit" value="edit" /></td>
			</tr>
			</form>
		<%	
				}
			}
			else {
			%>
			<tr>
				<td colspan="3">TIDAK ADA KURIKULUM AKTIF UNTUK SAAT INI</td>	
			</tr>
		<%	
			}
		%>
			
		</table>
		
		<%
		}
		else {
			Vector vJenisMakul = Getter.getListJenisMakul();
			ListIterator lijm = null;
			//System.out.println("okezone");
			//System.out.println("vJenisMakul="+vJenisMakul.size());
			Vector vInfoKelasAktif = (Vector) request.getAttribute("vInfoKelasAktif");
			ListIterator li = vInfoKelasAktif.listIterator();
			ListIterator li0=vInfoMakur.listIterator();
			String baris1 = (String)li0.next();
			////System.out.println("okezone1");
			st = new StringTokenizer(baris1);
			////System.out.println("okezone2");
			String idkur = st.nextToken();
			String start = st.nextToken();
			String ended = st.nextToken();
			String nmkur = (String)li0.next();
			Vector vInfo = (Vector)li0.next();
			String ttkls = (String)li0.next();
			String skstt = (String)li0.next();
			//System.out.println("okezone3");
			%>
			<form action="get.listMakur" method="post">
			<input type="hidden" name="idkur" value="<%=idkur%>" />
			<input type="hidden" name="update1" value="yes" /><input type="hidden" value="<%=kdpst %>,<%=nmpst %>" name="kdpst_nmpst" />
			<table align="center" border="1" style="color:#000;width:800px;background:#d9e1e5;">	
			<tr>
				<td style="background:#369;color:#fff;text-align:center;font-size:1.5em" colspan="8"><b>FORM TAMBAH / EDIT MATAKULIAH <br/>KURIKULUM <%=nmkur %><br/> PROGRAM <%=nmpst %></b></td>
			</tr>
			<tr>
				<td style="width:10%;text-align:left;background:#87B1DA;">KODE MK</td>
				<td style="width:45%;text-align:left;background:#87B1DA;">NAMA MATAKULIAH</td>	
				<td style="width:5%;text-align:center;background:#87B1DA;">SKS</td>
				<td style="width:13%;text-align:center;background:#87B1DA;">KELOMPOK</td>
				<td style="width:13%;text-align:center;background:#87B1DA;">JENIS</td>
				<td style="width:4%;text-align:center;background:#87B1DA;">SMS</td>
				<td style="width:5%;text-align:center;background:#87B1DA;">CEK</td>
				<td style="width:5%;text-align:center;background:#87B1DA;">MK<br/>AKHIR</td>
			</tr>
			<%
			if(li.hasNext()) {
				
				while(li.hasNext()) {
					String idkmk=(String)li.next();
					String kdkmk=(String)li.next();
					String nakmk=(String)li.next();
					String skstm=(String)li.next();
					String skspr=(String)li.next();
					String skslp=(String)li.next();
					String kdwpl=(String)li.next();
					String jenis=(String)li.next();
					String nodos=(String)li.next();
				%>
			<tr>
				<input type="hidden" name="idkmk_" value=<%=idkmk %> />
				<td style="width:10%;text-align:left;"><%=kdkmk.toUpperCase() %></td>
				<td style="width:50%;text-align:left;"><%=nakmk.toUpperCase() %></td>	
				<td style="width:5%;text-align:center;"><%=""+(Integer.valueOf(skstm).intValue()+Integer.valueOf(skspr).intValue()+Integer.valueOf(skslp).intValue()) %></td>
				<td style="width:13%;text-align:center;"><%=kdwpl %></td>
				<td style="width:13%;text-align:center;"> 
				<select name="jenisMakul" >
				<%
				
				if(vJenisMakul!=null && vJenisMakul.size()>0) {
				//if(false) {	
					lijm = vJenisMakul.listIterator();
					
					while(lijm.hasNext()) {
						String brs = (String)lijm.next();
						StringTokenizer stt = new StringTokenizer(brs,"`");
						String id = stt.nextToken();
						String keter = stt.nextToken();
						String kode = stt.nextToken();
						if(kode.equalsIgnoreCase(jenis)) {
				%>
					<option value="<%=idkmk%>`<%=kdkmk %>`<%=nakmk %>`<%=skstm %>`<%=skspr %>`<%=skslp %>`<%=kdwpl %>`<%=kode %>"" selected="selected"><%=keter %></option>
				<%		
						}
						else {
				%>
					<option value="<%=idkmk%>`<%=kdkmk %>`<%=nakmk %>`<%=skstm %>`<%=skspr %>`<%=skslp %>`<%=kdwpl %>`<%=kode %>""><%=keter %></option>
				<%		
						}
						
					}
				}
				else {
				%>
					<option value="<%=idkmk%>`<%=kdkmk %>`<%=nakmk %>`<%=skstm %>`<%=skspr %>`<%=skslp %>`<%=kdwpl %>`<%=jenis %>" selected="selected">
					<%
					//if(jenis.equalsIgnoreCase("0")) {
						out.print("ERROR: BELUM ADA PILIHAN");
					//}
					//else {
					//	out.print(jenis);
					//}
					%>
					</option>
				<%
				}
				%>
				</select>
				</td>
				
					<%
					boolean match = false;
					boolean finalMk = false;
					if(vInfo!=null && vInfo.size()>0) {
						//System.out.println("vSize = "+vInfo.size());
						ListIterator linfo = vInfo.listIterator();
						//liTmp.add(idkmk+","+sksmk+","+semes+","+nakmk+","+mk_akhir);
						String smsmk = "";
						while(linfo.hasNext() && !match) {
							String baris = (String)linfo.next();
							StringTokenizer st1 = new StringTokenizer(baris,",");
						//liTmp.add(idkmk+" "+sksmk+" "+nakmk);
							String idkmk_1= st1.nextToken();
							//System.out.println(idkmk_1);
							String sksmk_1= st1.nextToken();
							//System.out.println(sksmk_1);
							String semes_1= st1.nextToken();
							String nakmk_1= st1.nextToken();
							String mk_akhir= st1.nextToken();
							
							
							//System.out.println(nakmk_1);
							if(Integer.valueOf(idkmk).intValue()==Integer.valueOf(idkmk_1).intValue()) {
								match=true;
								smsmk=""+semes_1;
								if(mk_akhir!=null && mk_akhir.equalsIgnoreCase("true")) {
									finalMk = true;
								}
								else {
									finalMk = false;
								}
							}
						}

						%>
				<td style="width:4%;text-align:center;"><input type="text" name="<%=kdkmk %>-<%=idkmk %>" value="<%=smsmk.replaceAll("null","") %>" style="width:95%;text-align:center" /></td>
				<td style="width:5%;text-align:center;">
						<%
						if(!match) {
					%>
					<input type="checkbox" name="kdkmkInclude" value="<%=idkmk%>,<%=kdkmk %>,<%=nakmk %>,<%=skstm %>,<%=skspr %>,<%=skslp %>,<%=kdwpl %>,<%=jenis %>" />
					<%
						}
						else {
					%>
					<input type="checkbox" name="kdkmkInclude" value="<%=idkmk%>,<%=kdkmk %>,<%=nakmk %>,<%=skstm %>,<%=skspr %>,<%=skslp %>,<%=kdwpl %>,<%=jenis %>" checked/>
					<%
						}
					%>
				</td>
				<td style="width:5%;text-align:center;">
						<%
						if(!finalMk) {
					%>
					<input type="checkbox" name="finalMk" value="<%=idkmk%>,<%=kdkmk %>,<%=nakmk %>,<%=skstm %>,<%=skspr %>,<%=skslp %>,<%=kdwpl %>,<%=jenis %>" />
					<%
						}
						else {
					%>
					<input type="checkbox" name="finalMk" value="<%=idkmk%>,<%=kdkmk %>,<%=nakmk %>,<%=skstm %>,<%=skspr %>,<%=skslp %>,<%=kdwpl %>,<%=jenis %>" checked/>
					<%
						}
					%>
				</td>
			</tr>
				<%
					}
					else {
					%>
				<td style="width:4%;text-align:center;"><input type="text" name="<%=kdkmk %>" style="width:95%;text-align:center" /></td>
				<td style="width:5%;text-align:center;"><input type="checkbox" name="kdkmkInclude" value="<%=idkmk%>,<%=kdkmk %>,<%=nakmk %>,<%=skstm %>,<%=skspr %>,<%=skslp %>,<%=kdwpl %>,<%=jenis %>" />
				</td>
			</tr>	
					<%
					}
				}
				%>
			<tr>
				<td colspan="8" style="background:#369;text-align:right"><input type="submit" value="update" style="width:150px;width:70px"/></td>
			</tr>	
				<%
			}
			else {
			%>
			<tr>	
				<td colspan="8">TIDAK ADA MATAKULIAH AKTIF, HARAP EDIT MATAKULIAH</td>
			</tr>	
			<% 	
			}
		%>
		</table>
		</form>
		<%
		}
		%>	
		</p>
	</div>
</div>		
</body>
</html>