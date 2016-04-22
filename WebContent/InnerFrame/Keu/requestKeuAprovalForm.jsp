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
Vector vReqAprKeu= (Vector)session.getAttribute("vReqAprKeu");  //depricated
session.removeAttribute("vReqAprKeu");
JSONArray jsoaPymntReq = (JSONArray) session.getAttribute("jsoaPymntReq");
/*
JSONArray jArray = readJsonArrayFromUrl("/v1/status/usg_db/listMhs");
			for (int i = 0; i < jArray.length(); i++) {
		        JSONObject jObj = jArray.getJSONObject(i);
		    
		        System.out.println(i + ". " + jObj.toString());
		        System.out.println("-----------------------------------------");
			}
*/			
%>

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
		
		//for (int i = 0; i < jsoaPymntReq.length(); i++) {
	     //   JSONObject jObj = jsoaPymntReq.getJSONObject(i);
	    
	   //     out.println(i + ". " + jObj.toString());
	    //    out.println("-----------------------------------------<br/>");
		//}
		//if(vReqAprKeu!=null && vReqAprKeu.size()>0) {
		if(jsoaPymntReq!=null && jsoaPymntReq.length()>0) {	
		%>			
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:750px">
        	<tr>
        		<td style="background:#369;color:#fff;text-align:center;width:100px"><label><B>NPM / NIM</B> </label></td>
        		<td style="background:#369;color:#fff;text-align:center;width:225px"><label><B>NAMA</B> </label></td>
        		<td style="background:#369;color:#fff;text-align:center;width:300px"><label><B>KETERANGAN SINGKAT</B> </label></td>
        		<td style="background:#369;color:#fff;text-align:center;width:100px"><label><B>BESARAN</B> </label></td>
        		<td style="background:#369;color:#fff;text-align:center;width:25px"><label><B></B> </label></td>
        	</tr>
        <%
        	//ListIterator li=vReqAprKeu.listIterator();
        
        //li.add(kuiid+"#"+kdpst+"#"+npmhs+"#"+norut+"#"+tgkui+"#"+tgtrs+"#"+keter+"#"+keterDetail+"#"+penyetor+"#"+besaran+"#"+posBiaya+"#"+gelombangKe+"#"+cicilanKe+"#"+krs+"#"+targetBankAcc+"#"+opnpm+"#"+opnmm+"#"+sdhDstorKeBank+"#"+nonpmNggaTauUtkApa+"#"+batal+"#"+noKodePmnt+"#"+initUpdTm+"#"+npmVoider+"#"+keterVoid+"#"+nmmVoider+"#"+namaBuktiFile+"#"+uploadTm+"#"+aprovalTm+"#"+rejectedTm+"#"+rejectedNote+"#"+npmApprovee);
        	//while(li.hasNext()) {
        	for(int i=0;i<jsoaPymntReq.length();i++) {	
        		JSONObject job = jsoaPymntReq.getJSONObject(i);
        		//System.out.println("("+i+")"+job.toString());
        %>	
        	<tr>
        		<td style="color:#000;text-align:center;padding:3px"><label><%=Tool.jsobGetString(job, "NPMHSPYMNT")%><br/>
        		<%
        		String nim = Tool.jsobGetString(job, "NIMHSMSMHS");
        		if(Checker.isStringNullOrEmpty(nim)) {
        			out.print("N/A");
        		}
        		else {
        			out.print(nim);
        		}
        		%></label></td>
        		<td style="color:#000;text-align:left;padding:3px"><label><%=Tool.jsobGetString(job, "NMMHSMSMHS") %></label></td>
        		<td style="color:#000;text-align:left;padding:3px"><label>
        		<%
        		//try {
        			//System.out.println("try");
        		int k=1;
	        	String groupId = job.getLong("GROUP_ID")+"";
    	    	String keterAll = job.getString("KETER_PYMNT_DETAIL") ;
    	    	//System.out.println("--"+keterAll+","+groupId);
        		double totalBesaran = job.getDouble("AMONTPYMNT");
        		JSONObject jobTmp = null;
        		int j = 0;
        		//System.out.println(i);		
        		if(groupId!=null && !Checker.isStringNullOrEmpty(groupId) && !groupId.equalsIgnoreCase("0")) {
        			keterAll = "<p>"+k+"."+keterAll+"</p>";
        			k++;
        			boolean sameGroupId = true;
        			//System.out.println("in");
	        		for(j=i+1;j<jsoaPymntReq.length()&&sameGroupId;j++) {	
	        			jobTmp = jsoaPymntReq.getJSONObject(j);
	        			//System.out.println(jobTmp.toString());
	        			if(jobTmp.getString("GROUP_ID")!=null && jobTmp.getString("GROUP_ID").equalsIgnoreCase(groupId)) {
    	    				keterAll = keterAll+"<p>"+k+"."+jobTmp.getString("KETER_PYMNT_DETAIL")+"</p>";
    	    				k++;
        					totalBesaran = totalBesaran + jobTmp.getDouble("AMONTPYMNT");
	       				}
	       				else {
	       					sameGroupId = false;
	       				}
        			}
	        		if(sameGroupId) {
	        			i = j-1;
	        		}
	        		else {
	        			i = j-2;
	        		}
        			
        			out.print(keterAll);
        	%>
        		</label></td>
        		<td style="color:#000;text-align:left;padding:3px"><label>Rp. <%=NumberFormater.indoNumberFormat(totalBesaran+"") %></label></td>
        			<%
        		}
        		else {
        			out.print(job.getString("KETER_PYMNT_DETAIL"));	
            			%>
            			</label></td>
            			<td style="color:#000;text-align:left;padding:3px"><label>Rp. <%=NumberFormater.indoNumberFormat(job.getDouble("AMONTPYMNT")+"") %></label></td>
            			<%
        		}
        		//}
        		//catch (JSONException e) {
	        	
        		//}

        		%>
        	
        		<form action="<%=Constants.getRootWeb() %>/InnerFrame/Keu/keuApprovalForm.jsp" method="post">
        		<td style="color:#000;text-align:left;padding:3px">
        		<!--
        		yg dipake norut = no kuitansi kalo ada groupin otomatus no kuitansinya sama 
        		 -->
        		<input type="hidden" value="<%=job.getString("NORUTPYMNT") %>" name="nokuireq" />
        		<input type="submit" value="Next" style="padding:1px;width:99%;height:25px"/></td>
        		</form>        		
        	</tr>
        <%
        		
        	}
        %>	
		</table>
		<%
		}
		%>	
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>