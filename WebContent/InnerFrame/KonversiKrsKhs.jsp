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
*/
Vector v= null;  
/*
*/
%>


</head>
<body onload="location.href='#'">
<div id="header">
<%@ include file="innerMenu.jsp" %>
<%
SearchDb sdb = new SearchDb();
String nmpst = sdb.getNmpst(kdpst);


%>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<%
			if(requestKonversiMakul) {
				%>
				<!--  form action="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/krsPreviewEdit.jsp" -->
				<form action="go.processReqPenyetaraan">
				<%
				
				Vector vMk= (Vector)request.getAttribute("v");
				request.removeAttribute("v");
				
				if(vMk!=null && vMk.size()>0) {
					ListIterator liT = vMk.listIterator();
					if(liT.hasNext()) {
						String baris=(String)liT.next();
						StringTokenizer st = new StringTokenizer(baris,",");
						//li2.add(tabel+","+kdkmk+","+nakmk+","+sksmk);
						String tabel = st.nextToken();
						String thsms = st.nextToken();
						String kdkmk = st.nextToken();
						String nakmk = st.nextToken();
						String sksmk = st.nextToken();
					%>
					<P>
					<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:800px">
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center" colspan="5"><label><B>MATAKULIAH PINDAHAN</B> </label></td></td>
        			</tr>
        			<tr>
        				<td style="background:#369;color:#fff;text-align:center;width:60px"><label><B>KODE</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:left;width:300px"><label><B>MATAKULIAH</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:center;width:5px"><label><B>=</B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:center;" colspan="2"><label><B>MATAKULIAH KONVERSI</B> </label></td></td>
        				
        			</tr>
        			<tr>
        			<%

        				msg = request.getParameter("msg");
        				objId = request.getParameter("id_obj");
        				nmm = request.getParameter("nmm");
        				npm = request.getParameter("npm");
        				kdpst = request.getParameter("kdpst");
        				obj_lvl =  request.getParameter("obj_lvl");
        				String idkur = sdb.getIndividualKurikulum(kdpst,npm);
        				Vector v1 = sdb.getListMatakuliahDalamKurikulum(kdpst,idkur);
        			%>	
        				<input type="hidden" name="id_obj" value="<%=objId %>" />
        				<input type="hidden" name="nmm" value="<%=nmm %>" />
        				<input type="hidden" name="npm" value="<%=npm %>" />
        				<input type="hidden" name="kdpst" value="<%=kdpst %>" />
        				<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
        				<input type="hidden" name="kdkmk" value="<%=kdkmk %>" />
        				<input type="hidden" name="nakmk" value="<%=nakmk %>" />


        				<td style="color:#000;text-align:center;"><label><B><%=kdkmk %></B> </label></td></td>
        				<td style="color:#000;text-align:left;"><label><B><%=nakmk %></B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:center;width:5px"><label><B>=</B> </label></td></td>
        				<td style="color:#000;text-align:center;width:435px">
        					<select name="makulAll" style="width:98%;height:98%">
        						<option value="null" selected="selected">-----------pilih matakuliah konversi----------</option>
        			<%
      		  				ListIterator li1 = v1.listIterator();
        					while(li1.hasNext()) {
            					String idkmk_ = (String)li1.next();
        						String kdkmk_ = (String)li1.next();
        						String nakmk_ = (String)li1.next();
        						String skstm_ = (String)li1.next();
        						String skspr_ = (String)li1.next();
        						String skslp_ = (String)li1.next();
        						String kdwpl_ = (String)li1.next();
        						String jenis_ = (String)li1.next();
        						String stkmk_ = (String)li1.next();
        						String nodos_ = (String)li1.next();
        						String semes_ = (String)li1.next();
        						int skstt_ = Integer.valueOf(skstm_).intValue()+Integer.valueOf(skslp_).intValue()+Integer.valueOf(skspr_).intValue();
            				%>
        					<option value="<%=thsms %>,<%=kdkmk %>,<%=idkmk_ %>,<%=kdkmk_ %>,<%=nakmk_ %>,<%=skstt_ %>" ><%=nakmk_ %>(sks:<%=skstt_ %>)</option>
        					<%
        				}
        			%>	
        					</select>
        				</td>
        			<%
        				if(msg==null) {
        			%>
        				<td style="color:#000;text-align:center;background:#369;"><label><input name="makulCB" value="trnlp,<%=thsms %>,<%=kdkmk %>" type="checkbox"></label></td></td>
        			<%
        				}
        			%>	
        			</tr>
					<%	
						while(liT.hasNext()) {
							baris=(String)liT.next();
							st = new StringTokenizer(baris,",");
							//li2.add(tabel+","+kdkmk+","+nakmk+","+sksmk);
							tabel = st.nextToken();
							thsms = st.nextToken();
							kdkmk = st.nextToken();
							nakmk = st.nextToken();
							sksmk = st.nextToken();
					%>
					<tr>
					<%
        			if(requestKonversiMakul) {
        				
        			%>	
        			
        				<!--  form action="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/krsPreviewEdit.jsp" -->
        					<input type="hidden" name="id_obj" value="<%=objId %>" />
        					<input type="hidden" name="nmm" value="<%=nmm %>" />
        					<input type="hidden" name="npm" value="<%=npm %>" />
        					<input type="hidden" name="kdpst" value="<%=kdpst %>" />
        					<input type="hidden" name="obj_lvl" value="<%=obj_lvl %>" />
        					
        					<input type="hidden" name="kdkmk" value="<%=kdkmk %>" />
        					<input type="hidden" name="nakmk" value="<%=nakmk %>" />
        					<input type="hidden" name="sksmk" value="<%=sksmk %>" />
        					
        					<input type="hidden" name="cmd" value="viewKrs" />
        			<%
        			}
        			%>
        				<td style="color:#000;text-align:center;"><label><B><%=kdkmk %></B> </label></td></td>
        				<td style="color:#000;text-align:left;"><label><B><%=nakmk %></B> </label></td></td>
        				<td style="background:#369;color:#fff;text-align:center;width:5px"><label><B>=</B> </label></td></td>
        				<td style="color:#000;text-align:center;width:435px">
        					<select name="makulAll" style="width:98%;height:98%">
        						<option value="null" selected="selected">-----------pilih matakuliah konversi----------</option>
        			<%
        			li1 = v1.listIterator();
					while(li1.hasNext()) {
    					String idkmk_ = (String)li1.next();
						String kdkmk_ = (String)li1.next();
						String nakmk_ = (String)li1.next();
						String skstm_ = (String)li1.next();
						String skspr_ = (String)li1.next();
						String skslp_ = (String)li1.next();
						String kdwpl_ = (String)li1.next();
						String jenis_ = (String)li1.next();
						String stkmk_ = (String)li1.next();
						String nodos_ = (String)li1.next();
						String semes_ = (String)li1.next();
						int skstt_ = Integer.valueOf(skstm_).intValue()+Integer.valueOf(skslp_).intValue()+Integer.valueOf(skspr_).intValue();
    				%>
					<option value="<%=thsms %>,<%=kdkmk %>,<%=idkmk_ %>,<%=kdkmk_ %>,<%=nakmk_ %>,<%=skstt_ %>" ><%=nakmk_ %>(sks:<%=skstt_ %>)</option>
					<%
        			}
        			%>	
        					</select>
        				</td>
        			<%
        				if(msg==null) {
        			%>	
        				<td style="color:#000;text-align:center;background:#369;"><label><input name="makulCB" value="trnlp,<%=thsms %>,<%=kdkmk %>" type="checkbox"></label></td></td>
        			<%
        				}
        			%>
        			</tr>
        			
        			<%		
						}
					//tutuup table
					%>
					<tr>
        				<td colspan="5" style="background:#369;">
        				<input style="width:799px;height:30px" type="submit" value="Request Penyetaraan Matakuliah" />
        				</td>
        			</tr>
        			</table>
        			</P>
        			<br />
					<%
					}	
				}
			%>
			<div style="text-align:center">
				
			</div>
			</form>
			<%	
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