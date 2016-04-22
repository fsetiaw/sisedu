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
<%@ page import="org.codehaus.jettison.json.JSONArray" %>
<%@ page import="org.codehaus.jettison.json.JSONObject" %>
<%@ page import="org.codehaus.jettison.json.JSONException" %>
<%@ page import="org.owasp.esapi.ESAPI" %>
<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
//Vector vReqAprKeu= (Vector)session.getAttribute("vReqAprKeu");  
JSONArray jsoaPymntReq = (JSONArray) session.getAttribute("jsoaPymntReq");

String noKuiReq = request.getParameter("nokuireq");
boolean match = false;
String targetFile = null;
String targetNpmhs =  null;
JSONArray jsoaAkun = Getter.readJsonArrayFromUrl("/v1/akun");
//System.out.println("jsoaAkun lengt="+jsoaAkun.length());
%>
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"> </script>
<script type="text/javascript">


function winOpen(almat)
{
	window.open(almat,'','width=600,height=400');
}


$(document).ready(function()
{
	$("#somebutton").click(function()	
	{
		//$.get('go.uploadFile2', function(responseText) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
            //             // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
        $.post('go.validateForm?somebutton_String_Opt='+document.getElementById('somebutton').value, $('#formUpload1').serialize(), function(data) {
        	document.getElementById('div_msg').style.visibility='visible';
        	$('#div_msg').html(); 
        	$('#div_msg').html(data); 
        });
	});
		
	$("#somebutton1").click(function()	
	{
		//$.get('go.uploadFile2', function(responseText) { // Execute Ajax GET request on URL of "someservlet" and execute the following function with Ajax response text...
		            //             // Locate HTML DOM element with ID "somediv" and set its text content with the response text.
		$.post('go.validateForm?somebutton1_String_Opt='+document.getElementById('somebutton1').value, $('#formUpload1').serialize(), function(data) {
			document.getElementById('div_msg').style.visibility='visible';
		    $('#div_msg').html(); 
		    $('#div_msg').html(data); 
		});
	});	
});			
</script>
</head>
<body>
<div id="header">
	<ul>
		<li><a href="#" onclick="javascript:window.history.back(-1);return false;" target="_self">GO<span>BACK</span></a></li>
	</ul>
<%
%>

</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		
		<br/>
		<%
		if(jsoaPymntReq!=null && jsoaPymntReq.length()>0) {
        	for(int i=0;i<jsoaPymntReq.length();i++) {	
    			JSONObject job = jsoaPymntReq.getJSONObject(i);
    			//System.out.println("("+i+")"+job.toString());
        		if(job.getString("NORUTPYMNT").equalsIgnoreCase(noKuiReq)) {
        			match = true;
        %>
        <table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:750px">
        	<form name="formUpload1" id="formUpload1" method="post">
        		<input type="hidden" name="StringfwdPageIfValid_String_Opt" value="proses.pymntApproval"/>
        		
        		<tr>
        			<td colspan="4" style="background:#369;color:#fff;text-align:center;font-size:1.5em;padding:3px"><label><B>FORM VALIDASI PEMBAYARAN</B> </label></td>
        		</tr>	
        		<tr>
        			<td style="background:#369;color:#fff;text-align:left;width:100px;padding:3px"><label><B>NPM / NIM</B> </label></td></td>
	        		<td style="text-align:center;width:275px;padding:3px"><label><B><%=Tool.jsobGetString(job, "NPMHSPYMNT") %> / <%=Tool.jsobGetString(job, "NIMHSMSMHS").replace("null", "N/A") %></B> </label></td></td>
        			<td style="background:#369;color:#fff;text-align:left;width:100px;padding:3px"><label><B>NAMA</B> </label></td></td>
        			<td style="text-align:center;width:275px;padding:3px"><label><B><%=Tool.jsobGetString(job, "NMMHSMSMHS") %></B> </label></td></td>
        		</tr>
        		<tr>
        			<td style="background:#369;color:#fff;text-align:left;width:100px;padding:3px"><label><B>TGL SETOR</B> </label></td></td>
        			<td style="text-align:center;width:275px;padding:3px"><label><B>
        			<%
        			DateFormater.keteranganDate(Tool.jsobGetString(job, "TGTRSPYMNT"));
        			String trsDate = Tool.jsobGetString(job, "TGTRSPYMNT");
        			StringTokenizer st = new StringTokenizer(trsDate,"-");
        			String yy = st.nextToken();
        			String mm = st.nextToken();
        			if(mm.length()==2 && mm.startsWith("0")) {
        				mm = mm.substring(1,2);
        			}
        			String dd = st.nextToken();
        			if(dd.length()==2 && dd.startsWith("0")) {
        				dd = dd.substring(1,2);
        			}
        			trsDate = dd+"/"+mm+"/"+yy;
        			//out.print(trsDate);
        			%><input type="text" name="Tgl-Setor_Date_Wajib" style="275px" value="<%=trsDate%>"/></B> </label></td></td>
        			<td style="background:#369;color:#fff;text-align:left;width:100px;padding:3px"><label><B>PENYETOR</B> </label></td></td>
        			<td style="text-align:center;width:275px;padding:3px"><label><B>
        <%    		
        			if(Checker.isStringNullOrEmpty(Tool.jsobGetString(job, "PAYEEPYMNT"))) {
        				out.print(Tool.jsobGetString(job, "NMMHSMSMHS"));
        			}
        			else {
        				out.print(Tool.jsobGetString(job, "PAYEEPYMNT"));
        			}
        %>			</B> </label></td></td>
        		</tr>
        		<tr>	
        			<td colspan="4">
        				<table width="100%">	
        					<tr>
        						<td style="background:#369;color:#fff;text-align:left;width:50%;padding:3px">
        							<%="SUMBER DANA" %>
        						</td>
        						<td style="background:#369;color:#fff;text-align:center;width:25px;padding:3px" colspan="2">
        							<select name="Sumber-Dana_Huruf_Opt" style="width:100%">
<%
								JSONArray jsoaBea = Getter.readJsonArrayFromUrl("/v1/bea");
								for(int j=0;j<jsoaBea.length();j++) {	
									JSONObject job1 = jsoaBea.getJSONObject(j);
									//System.out.println("("+i+")"+job.toString());
									String idbea = job1.getString("IDPAKETBEASISWA");
									String nama_paket = job1.getString("NAMAPAKET");
									if(idbea.equalsIgnoreCase(Tool.jsobGetString(job, "IDPAKETBEASISWA"))) {
									%>
									<option value="<%=idbea%>,<%=nama_paket%>" selected="selected"><%=nama_paket%></option>
									<%	
									}
									else {
									%>
									<option value="<%=idbea%>,<%=nama_paket%>"><%=nama_paket%></option>
									<%	
									}
								}	
%>
									</select>
        						</td>
        					</tr>
        					<tr>
        						<td style="background:#369;color:#fff;text-align:left;width:50%;padding:3px">
        							<%="KETERANGAN TRANSAKSI" %>
        						</td>
        						<td style="background:#369;color:#fff;text-align:center;width:25px;padding:3px">
        							<%="BESARAN" %>
        						</td>
        						<td style="background:#369;color:#fff;text-align:center;width:25px;padding:3px">
        							<%="TARGET AKUN"%>
        						</td>
        					</tr>	
        <%
        			targetNpmhs = Tool.jsobGetString(job, "NPMHSPYMNT");
					targetFile = Tool.jsobGetString(job, "FILENAME");
		//			System.out.println("kok "+targetNpmhs+","+targetFile);
					int k=1;
	        		String groupId = job.getLong("GROUP_ID")+"";
    	  //  		String keterAll = job.getString("KETER_PYMNT_DETAIL") ;
    	    		//System.out.println(keterAll+","+groupId);
        			double totalBesaran = job.getDouble("AMONTPYMNT");
        			JSONObject jobTmp = null;
        			int j = 0;
        			//Systemtem.out.println(i);
        	%>
        					<tr>
        						<td style="text-align:left;padding:3px">	
        						<%= job.getString("KETER_PYMNT_DETAIL")%>
        						</td>
        						<td style="text-align:right;padding:3px">	
        						<%= NumberFormater.indoNumberFormat(job.getDouble("AMONTPYMNT")+"")%>
        						</td>
        						<td style="text-align:center;padding:3px">	
        							<select name="namaAkun_String_Wajib" style="direction:rtl">
        						<%
        						for(int l=0; l<jsoaAkun.length();l++) {
        	        				JSONObject jobAkun = jsoaAkun.getJSONObject(l);
        	        				
        	        				if(Tool.jsobGetString(jobAkun,"NAMA_AKUN").equalsIgnoreCase(job.getString("NOACCPYMNT")) || Tool.jsobGetString(jobAkun,"OLD_NICKNAME").equalsIgnoreCase(job.getString("NOACCPYMNT"))) {
        	        			%>
        	        					<option value="<%=job.getString("PYMTPYMNT")+":"+jobAkun.getString("NAMA_AKUN") %>" selected="selected"><%=jobAkun.getString("NAMA_AKUN") %></option>
        	        			<%		
        	        				}
        	        				else {
        	        			%>
        	        					<option value="<%=job.getString("PYMTPYMNT")+":"+jobAkun.getString("NAMA_AKUN") %>"><%=jobAkun.getString("NAMA_AKUN") %></option>
        	        			<%			
        	        				}
        	        					
        	        			}
								%>
									</select>
        						</td>
        					</tr>
        	<%		
        			if(groupId!=null && !Checker.isStringNullOrEmpty(groupId) && !groupId.equalsIgnoreCase("0")) {
        			//multiple item transakasi
        	%>
        		
        					
        	<%
        				//keterAll = "<p>"+k+"."+keterAll+"</p>";
        				k++;
        				boolean sameGroupId = true;
	        			for(j=i+1;j<jsoaPymntReq.length()&&sameGroupId;j++) {	
	        				jobTmp = jsoaPymntReq.getJSONObject(j);
	        				//System.out.println(jobTmp.toString());
	        				if(jobTmp.getString("GROUP_ID")!=null && jobTmp.getString("GROUP_ID").equalsIgnoreCase(groupId)) {
    	    					//keterAll = keterAll+"<p>"+k+"."+jobTmp.getString("KETER_PYMNT_DETAIL")+"</p>";
    	    					k++;
        						totalBesaran = totalBesaran + jobTmp.getDouble("AMONTPYMNT");
        	%>
        					<tr>
        						<td style="text-align:left;padding:3px">	
        						<%= jobTmp.getString("KETER_PYMNT_DETAIL")%>
        						</td>
        						<td style="text-align:right;padding:3px">	
        						<%= NumberFormater.indoNumberFormat(jobTmp.getDouble("AMONTPYMNT")+"")%>
        						</td>
        						<td style="text-align:center;padding:3px">	
        							<select name="namaAkun_String_Wajib" style="direction:rtl">
        						<%
        						for(int l=0; l<jsoaAkun.length();l++) {
        	        				JSONObject jobAkun = jsoaAkun.getJSONObject(l);
        	        				
        	        				if(Tool.jsobGetString(jobAkun,"NAMA_AKUN").equalsIgnoreCase(jobTmp.getString("NOACCPYMNT")) || Tool.jsobGetString(jobAkun,"OLD_NICKNAME").equalsIgnoreCase(jobTmp.getString("NOACCPYMNT"))) {
        	        			%>
        	        					<option value="<%=jobTmp.getString("PYMTPYMNT")+":"+jobAkun.getString("NAMA_AKUN") %>" selected="selected"><%=jobAkun.getString("NAMA_AKUN") %></option>
        	        			<%		
        	        				}
        	        				else {
        	        			%>
        	        					<option value="<%=jobTmp.getString("PYMTPYMNT")+":"+jobAkun.getString("NAMA_AKUN") %>"><%=jobAkun.getString("NAMA_AKUN") %></option>
        	        			<%			
        	        				}
        	        					
        	        			}
								%>
									</select>
        						</td>
        					</tr>
        							
        	<%					
	        				}
	        				else {
	        					sameGroupId = false;
	        				}
        				}
        				i = j-1;
        			}		
        			%>
        					<tr>
        						<td style="background:#369;color:#fff;text-align:left;width:100px;padding:3px"><label><B>TOTAL BESARAN</B> </label></td></td>
        						<td style="background:#369;color:#fff;text-align:right;width:275px;padding:3px"><label><B>Rp. <%=NumberFormater.indoNumberFormat(totalBesaran+"") %></B> </label></td></td>
        						<td style="background:#369;color:#fff;text-align:center;padding:3px" colspan="1">
        <%
        				if(match) {
        					if(targetFile!=null && !Checker.isStringNullOrEmpty(targetFile)) {
    							String target = Constants.getRootWeb()+"/InnerFrame/showPic.jsp";
    							String uri = request.getRequestURI();
    							String url = PathFinder.getPath(uri, target);
    							if(targetFile.contains(".jpg")||targetFile.contains(".jpeg")||targetFile.contains(".gif")||targetFile.contains(".tiff")||targetFile.contains(".bmp")||targetFile.contains(".png")) {
    					
    		    %>
								<a style="color:#fff" href="#" onclick="javascript:winOpen('<%=url %>?namafile=<%=Constants.getFolderBuktiBayaran()+"/"+targetNpmhs+"/"+targetFile %>')">Lihat Bukti Bank</a>
    			<%
    							}
    							else {
    			%>		
    							<a style="color:#fff" href="go.downloadFileAsIs?root_dir=null&keter=null&alm=<%=Constants.getFolderBuktiBayaran()+"/"+targetNpmhs%>&namaFile=<%=targetFile%>&hak=null">Download Bukti Bank</a>
    			<%	
    							}
        					}
        				}	
        			

        		%>

        				</table>
        			</td>
        		</tr>	
        		<%
        		/*
        			}
        			else {	
					//single item transakasi
        		%>

        	
        	<tr>
        		<td style="background:#369;color:#fff;text-align:left;width:100px;padding:3px"><label><B>BESARAN</B> </label></td></td>
        		<td style="text-align:left;width:275px;padding:3px"><label><B>Rp. <%=NumberFormater.indoNumberFormat(totalBesaran+"") %></B> </label></td></td>
        		<td style="text-align:center;padding:3px" colspan="2">
        <%
        				if(match) {
    						if(targetFile!=null && !Checker.isStringNullOrEmpty(targetFile)) {
    							String target = Constants.getRootWeb()+"/InnerFrame/showPic.jsp";
    							String uri = request.getRequestURI();
    							String url = PathFinder.getPath(uri, target);
    							if(targetFile.contains(".jpg")||targetFile.contains(".jpeg")||targetFile.contains(".gif")||targetFile.contains(".tiff")||targetFile.contains(".bmp")||targetFile.contains(".png")) {
    		    					
    				    		    %>
    							<a style="color:#fff" href="#" onclick="javascript:winOpen('<%=url %>?namafile=<%=Constants.getFolderBuktiBayaran()+"/"+targetNpmhs+"/"+targetFile %>')">Lihat Bukti Bank</a>
    							
    				    			<%
    				    		}
    				    		else {
    				    			%>		
    				    		<a style="color:#fff" href="go.downloadFileAsIs?root_dir=null&keter=null&alm=<%=Constants.getFolderBuktiBayaran()+"/"+targetNpmhs%>&namaFile=<%=targetFile%>&hak=null">Download Bukti Bank</a>
    				    			<%	
    				    		}
    						}
    					}
        			}//end else
        		*/
        %>		
        		</td>
        	</tr>
        <%
        			if(validUsr.isAllowTo("pymntApprovee")>0) {
        %>	
        
        	<!--  input type="hidden" name="kuiidReqested_Int_Wajib" value="kuiidReq %>" / -->
        	<input type="hidden" name="noKuiReq_Int_Wajib" value="<%=noKuiReq %>" />
        	<input type="hidden" name="targetNpmhs_String_Wajib" value="<%=job.getString("NPMHSPYMNT") %>" />
        	<input type="hidden" name="targetKdpst_String_Wajib" value="<%=job.getString("KDPSTPYMNT") %>" />
        	<input type="hidden" name="msgContent_String_Wajib" value="<%=job.getString("KETER_PYMNT_DETAIL") %>, dengan Besaran Rp. <%=NumberFormater.indoNumberFormat(job.getString("AMONTPYMNT")) %>" /> 
        	<tr>
				<td style="background:#369;">
					<!--  input type="submit" name="verdict" value="Tolak" style="font-weight:bold;width:90px;color:red;height:50px;display:inline"/ -->
					<div id="btn" style="text-align:center;background-color:#a6bac4;height:45px;visibility:visible" ><input type="button" id="somebutton" name="somebutton" value="Tolak" style="width:90%;height:35px;font-weght:bold;color:red;margin:5px"/></div>
				</td>	
        		<td style="background:#369;padding:3px;" colspan="3">
        			<textarea name="Alasan-Penolakan_String_Opt_somebutton_Wajib" rows="3" style="display:inline;width:95%;margin:10px" placeholder="isi alasan penolakan"></textarea>
				</td>
        	</tr>
        	<tr>
        		<td style="background:#369;text-align:right" colspan="4">		
        				<div id="btn" style="text-align:right;background-color:#a6bac4;height:45px;visibility:visible" >Bukti Bank Sesuai, <input type="button" id="somebutton1" name="somebutton1" value="Terima" style="width:95px;height:35px;font-weight:bold;margin:5px"/></div> 
        			<!-- input type="submit" name="verdict" value="Terima" style="font-weight:bold;width:90px;height:50px;display:inline"/ -->
				</td>
        	</tr>
        <%
        			}
        %>	
        </form>
        <%
        		}	
        	}//end for
        %>	
		</table>
		<%
			
		}
		else {
			//empti jason array
		}
		%>	
		<br/>
		<div id="div_msg" style="font-style:italic;color:red;text-align:left;background-color:#d9e1e5;width:750px;height:100%;visibility:hidden;margin:0px 0 0 132px;" ></div>
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>