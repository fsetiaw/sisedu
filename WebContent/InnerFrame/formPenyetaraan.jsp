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
int i=0;
String aspti = request.getParameter("aspti");
String aspst = request.getParameter("aspst");
String fwdTo = request.getParameter("fwdTo");
String fwdPg = request.getParameter("fwdPg");
Vector v_listMakul = (Vector) request.getAttribute("v_listMakul");
Vector v_sisaMakul = new Vector();
String comment = (String)request.getAttribute("comment");
//ListIterator lism = v_sisaMakul.listIterator();
if(v_listMakul!=null) {
	//System.out.println("v_listMakul="+v_listMakul.size());
	v_sisaMakul = (Vector)v_listMakul.clone();
	
}
else {
	//System.out.println("v_listMakul empty");
}
%>

</head>
<!--  body onload="location.href='#'" -->
<body>
<div id="header">
<%@ include file="dataMhsPindahanSubMenu.jsp" %>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		
		<%
		if(v_listMakul==null || v_listMakul.size()==0) {
			out.print("KURIKULUM BELUM DITENTUKAN");
		}
		else {
			//System.out.println("v_listMakul size="+v_listMakul.size());
		}
		if(v_trnlp==null || v_trnlp.size()==0) {
			out.print("TRANSKRIP DARI PERGURUAN TINGGI ASAL BELUM DIINPUT");
		}
		
		if(!Checker.isStringNullOrEmpty(comment)) {
			out.print("<h3 align=\"center\">"+comment+"</h3>");
		}
		//System.out.println("vtrnlp="+vtrnlp);
		
		%>
		<form action=update.penyetaraan>
		<input type="hidden" name="id_obj" value="<%=objId %>" /><input type="hidden" name="nmm" value="<%= nmm%>" />
		<input type="hidden" name="npm" value="<%=npm %>" /><input type="hidden" name="obj_lvl" value="<%= obj_lvl%>" />
		<input type="hidden" name="kdpst" value="<%= kdpst%>" /><input type="hidden" name="aspti" value="<%= aspti%>" />
		<input type="hidden" name="cmd" value="<%=cmd %>" /><input type="hidden" name="fwdTo" value="<%= fwdTo%>" />
		<input type="hidden" name="fwdPg" value="formPenyetaraan.jsp" /><input type="hidden" name="aspst" value="<%=aspst %>" />
		
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:765px">
        	<tr>
        		<th style="background:#369;color:#fff;text-align:center" colspan="6"><label><B>FORM PENYETARAAN MATAKULIAH</B> </label></th>
        		
       		</tr>
      		<tr>
      			<td style="background:#369;color:#fff;text-align:left;width:15px"><label><B>NO.</B> </label></td></td>
      			<td style="background:#369;color:#fff;text-align:left;width:515px"><label><B>MATAKULIAH</B> </label></td></td>
      			<td style="background:#369;color:#fff;text-align:center;width:100px"><label><B>KODE MK ASAL</B> </label></td></td>
     			<td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>NILAI</B> </label></td></td>
        		<td style="background:#369;color:#fff;text-align:center;width:50px"><label><B>BOBOT</B> </label></td></td>
        		<td style="background:#369;color:#fff;text-align:center;width:35px"><label><B>SKS</B> </label></td></td>
        		
        	</tr>
		<%
		//yg digunakan hanya vtrnlp - data dari tabel
		//if(vtrnlm) {
		//	//System.out.println("v_-trnlm- = "+v0.size());
		//}	
		if(vtrnlp) {
			//System.out.println("v_-trnlp- = "+v0.size());
			
			ListIterator li0 = v0.listIterator();
			if(v_sisaMakul!=null) {
				while(li0.hasNext()) {
					String thsms = (String)li0.next();
					String kdkmk = (String)li0.next();
					String nakmk = (String)li0.next();
					String nlakh = (String)li0.next();
					String bobot = (String)li0.next();
					String kdasl = (String)li0.next();
					String nmasl = (String)li0.next();
					String sksmk = (String)li0.next();
					String totSks = (String)li0.next();//ignore
					String sksas = (String)li0.next();
					String transferred = (String)li0.next();
					ListIterator lim = v_sisaMakul.listIterator();
					//System.out.println("v_sisaMakul0="+v_sisaMakul.size());
					boolean match = false;
					while(lim.hasNext()&&!match) {
						lim.next();
						String kodeMk = (String)lim.next();
						lim.next();
						lim.next();
						lim.next();
						lim.next();
						lim.next();
						lim.next();
						lim.next();
						lim.next();
						lim.next();
						//System.out.println("kdkmk="+kdkmk+" vs kodeMk="+kodeMk);
						//System.out.println(thsms+","+kdkmk+","+nakmk+","+nlakh+","+bobot+","+kdasl+","+nmasl+","+sksmk);
						if(kdkmk!=null&&kdkmk.equalsIgnoreCase(kodeMk)&&Boolean.valueOf(transferred).booleanValue()) {
							match = true;
							lim.previous();
							lim.remove();
							lim.previous();
							lim.remove();
							lim.previous();
							lim.remove();
							lim.previous();
							lim.remove();
							lim.previous();
							lim.remove();
							lim.previous();
							lim.remove();
							lim.previous();
							lim.remove();
							lim.previous();
							lim.remove();
							lim.previous();
							lim.remove();
							lim.previous();
							lim.remove();
							lim.previous();
							lim.remove();
						}
					}
				}
			}	
			
			if(v_listMakul!=null) {
				//System.out.println("v_listMakul="+v_listMakul.size());
				//System.out.println("v_sisaMakul="+v_sisaMakul.size());
			}
			else {
				//System.out.println("v_listMakul empty");
			}
			//System.out.println("--1");
			li0 = v0.listIterator();
			while(li0.hasNext()) {
				i++;
			%>
			<tr>
			<%
				String thsms = (String)li0.next();
				String kdkmk = (String)li0.next();
				String nakmk = (String)li0.next();
				String nlakh = (String)li0.next();
				String bobot = (String)li0.next();
				String kdasl = (String)li0.next();
				String nmasl = (String)li0.next();
				String sksmk = (String)li0.next();
				String totSks = (String)li0.next();//ignore
				String sksas = (String)li0.next();
				String transferred = (String)li0.next();
				////System.out.println(thsms+"-"+kdkmk+"-"+kdasl+"-"+nmasl+"-"+transferred);
		%>
				<td rowspan="2" style="color:#000;text-align:center;"><label><B><%=i %></B> </label></td>
				<td style="color:#000;text-align:left;"><label><B><%=nmasl %></B> </label></td>
        		<td style="color:#000;text-align:center;"><label><B><%=kdasl %></B> </label></td>
        		<td style="color:#000;text-align:center;"><label><B><%=nlakh %></B> </label></td>
        		<td style="color:#000;text-align:center;"><label><B><%=bobot %></B> </label></td>
        		<td style="color:#000;text-align:center;"><label><B><%=sksas %></B> </label></td>
        	</tr>
        	<tr>	
        		<%
        		//if(Boolean.valueOf(transferred).booleanValue()) {
        		%>	
        		<td style="background:#88BCF0;color:#000;text-align:left;width:515px"><label><B>KODE MATAKULIAH PENYETARAAN</B> </label></td></td>
        		<td colspan="4" style="background:#88BCF0;color:#000;text-align:left;"><label><B>
       
        		<select name="listMakul">
            	<%
            		//v_sisaMakul ngga jadi dipake
            		//System.out.println("--2");
            		ListIterator lism = v_listMakul.listIterator();
            		boolean match = false;
            		while(lism.hasNext()) {
            			String idkmk1 = (String)lism.next();
        				String kdkmk1 = (String)lism.next();
        				String nakmk1 = (String)lism.next();
        				String skstm1 = (String)lism.next();
        				String skspr1 = (String)lism.next();
        				String skslp1 = (String)lism.next();
        				String kdwpl1 = (String)lism.next();
        				String jenis1 = (String)lism.next();
        				String stkmk1 = (String)lism.next();
        				String nodos1 = (String)lism.next();
        				String semes1 = (String)lism.next();
        				int sksmk1 = Integer.valueOf(skstm1).intValue()+Integer.valueOf(skslp1).intValue()+Integer.valueOf(skspr1).intValue();
        				if(kdkmk1.equalsIgnoreCase(kdkmk)) {
        					match = true;
        				%>
                		<option value="<%= kdkmk1+"#"+kdasl+"#"+nakmk1%>" selected="selected"><%= kdkmk1%>-<%= nakmk1%>,<%= sksmk1%> sks</option>
                		<%
        				}
        				else {
        				%>
                		<option value="<%= kdkmk1+"#"+kdasl+"#"+nakmk1%>"><%= kdkmk1%>-<%= nakmk1%>,<%= sksmk1%> sks</option>
                		<%	
        				}
            		}
            		if(!match) {
            			%>
            			<option value="0" selected="selected">--PILIH MATAKULIAH PENYETARAAN--</option>
            			<%
            		}
            	%>
            	</select>
        		</B> </label></td>
        	</tr>
        <%
			}
		}
        %>	
    	<tr>
    		<td style="background:#369;color:#fff;text-align:center" colspan="6"><label><B><input type="submit" value="UPDATE MATAKULIAH PENYETARAAN" style="width:80%;height:30px"/></B> </label></td></td>
   		</tr>
   
        </table>	
  		</form>
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>