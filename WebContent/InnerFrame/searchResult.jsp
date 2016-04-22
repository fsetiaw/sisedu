<!DOCTYPE html>
<html>
<head>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.setting.*" %>
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />


</head>
<body>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		
<%


Vector vHistBea = (Vector)request.getAttribute("vHistBea");
request.removeAttribute("vHistBea");
session.removeAttribute("forceBackTo");
Vector v= null;  
v = (Vector) request.getAttribute("v_search_result");
Vector vKui = (Vector) request.getAttribute("v_kui_search_result");
if((v==null || v.size()<1) && (vKui==null || vKui.size()<1)) {
	out.print("NO DATA MATCH FOUND");
}
else {
	if(v!=null && v.size()>0) {
		beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
		boolean merge = false;
		if(validUsr.isUsrAccessCommandContain("MERGE")) {
			merge = true;
		}
	
		ListIterator li = v.listIterator();
		ListIterator li2 = vHistBea.listIterator();
		double posisi = 1;
		boolean first = true;
%>
	<table cellspacing="1" >
<%
		while(li.hasNext()) {
			String npm = (String)li.next();
			String nim = (String)li.next();
			String kdpst = (String)li.next();
			String nmm = (String)li.next();
			String tplhr = (String)li.next();
			String tglhr = (String)li.next();
			String stmhs = (String)li.next();
			String id_obj = (String)li.next();
			String obj_lvl = (String)li.next();
			String obj_desc = (String)li.next();
			String jenisBea = "null";
			String thsmsBea = "null";
			String baris = (String)li2.next();
			if(!baris.startsWith("null")) {
				StringTokenizer stt = new StringTokenizer(baris,"`");
				//li.add(thsms+"`"+kdpst+"`"+npmhs+"`"+namaPaket+"`"+namaBank+"`"+noRekBank+"`"+nmPemilik+"`"+jenisBea);
				thsmsBea = stt.nextToken();
				stt.nextToken();
				stt.nextToken();
				stt.nextToken();
				stt.nextToken();
				stt.nextToken();
				stt.nextToken();
				jenisBea = stt.nextToken(); 
			}
%>
		<tr>
			<td>
			<p>
				<fieldset>
				<form action="InnerFrame/get.profile">
					<input type="hidden" value="<%=npm %>" name="npm"/><input type="hidden" value="dashboard" name="cmd"/><input type="hidden" value="<%=nim %>" name="nim"/><input type="hidden" value="<%=stmhs %>" name="stmhs"/><input type="hidden" value="<%=kdpst %>" name="kdpst" /><input type="hidden" value="<%=id_obj %>" name="id_obj" /><input type="hidden" value="<%=obj_lvl %>" name="obj_lvl" />
					<table bordercolor="#369" style="background:#d9e1e5;color:#000;width:650px">
						<tr>
							<td><input type="text" value="<%=nmm %>" name="nmm" readonly style="width:350px" /></td>
							<td><label><%=obj_desc %></label></td>
						</tr>
					</table>
        			<table border="1px " bordercolor="#F0F0F0" style="background:#369;color:#fff;width:650px">
        				<tr>
        					<td colspan="2"><label><B>NPM/NIM :</B> <%=npm %>/<%=nim %></label></td></td><td align="center">
        					<label><b>STATUS :</b><%=stmhs %><br/><b>
        					<%
        					if(!baris.startsWith("null")) {
        						out.print(jenisBea+" ("+thsmsBea+")");
        					}
        					%>
        					</b>
        					</td>
        				</tr>
        				<tr>
        					<td colspan="2"><label><b>TEMPAT/TGL LAHIR : </b> <%=tplhr %>/<%=tglhr %></label></td>
        					<td align="center"><input type="submit" name="submit" value="NEXT" formtarget="_self" style="width: 100px"/></td>
        				</tr>
  <%
  							if(merge) {
  %>      				
        				<tr>
        					<td colspan="2"><label><b>MERGE KE NPM : &nbsp<input type="text" name="mergeToNpm" style="width:150px" /> </td>
        					<td align="center"><input type="submit" name="submit" value="MERGE" formtarget="_self" style="width: 100px"/></td>
        				</tr>
        				<%
  							}
        				%>
        			</table>					
        		</form>
        		</fieldset>
			</p>
			</td>
<%
		}
%>
	</table>
<%
	}
	else {
		if(vKui!=null && vKui.size()>0) {
			ListIterator liKui = vKui.listIterator();
			while(liKui.hasNext()){
				String baris = (String)liKui.next();
				//li.add(kuiid+","+kdpst+","+npmhs+","+tgkui+","+tgtrs+","+keter+","+amont+","+opnmm+","+voidd);
				StringTokenizer st = new StringTokenizer(baris,",");
				String kuiid = st.nextToken();
				String norut = st.nextToken();
				String kdpst = st.nextToken();
				String npmhs = st.nextToken();
				String nmmhs = st.nextToken();
				String tgkui = st.nextToken();
				String tgtrs = st.nextToken();
				String keter = st.nextToken();
				String amont = st.nextToken();
				String opnmm = st.nextToken();
				String voidd = st.nextToken();
				out.print("<br /> ");
				String status = "VALID";
				String backColor = "#d9e1e5";
				if(voidd.equalsIgnoreCase("true")) {
					backColor = "#C20B0B";
					status = "BATAL";
				}
				%>
				<table align="center" bordercolor="#369" style="background:<%=backColor %>;color:#000;width:550px">
					<tr>
						<td style="background:#369;color:#fff;width:30%">NO KUITANSI</td>
						<td><label><%=norut %></label></td>
					</tr>
					<tr>
						<td style="background:#369;color:#fff;width:30%">ATAS NAMA</td>
						<td><label><%=nmmhs %>(NPM :<%=npmhs %>)</label></td>
					</tr>
					<tr>
						<td style="background:#369;color:#fff;width:30%">KETERANGAN</td>
						<td><label><%=keter %></label></td>
					</tr>
					<tr>
						<td style="background:#369;color:#fff;width:30%">JUMLAH</td>
						<td><label><%=amont %></label></td>
					</tr>
					<tr>
						<td style="background:#369;color:#fff;width:30%">PENERIMA</td>
						<td><label><%=opnmm %></label></td>
					</tr>
					<tr>
						<td style="background:#369;color:#fff;width:30%">STATUS</td>
						<td><label><%=status %></label></td>
					</tr>
				</table>
		       
				<%			
			}
		}
	}
}	
%>
	</div>
</div>
</body>
</html>