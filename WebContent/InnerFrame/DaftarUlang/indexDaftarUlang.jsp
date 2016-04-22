<!DOCTYPE html>
<head>

<%@ page import="beans.dbase.*" %>
<%@ page import="beans.tools.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.login.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<%@ page import="beans.tools.PathFinder"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<title>Insert title here</title>
<%
	String from = request.getParameter("from");
	beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
	//System.out.println("dashAkademi.jsp");
	String finalList = (String)request.getAttribute("finalList");
	String totApproval = (String)request.getAttribute("totApproval");
	String listObjApproval = (String)request.getAttribute("listObjApproval");//beda tipis dgn listApprovee, ini nickname approvee utk prodi terkait
	String listApprovee = (String)request.getAttribute("listApprovee");//kalo listApprovee - distinct nickname approve - kalo approve tiap prodi ada yg beragam
	request.removeAttribute("finalList");
	request.removeAttribute("totApproval");
	request.removeAttribute("listObjApproval");
	request.removeAttribute("listApprovee");
	String usrObjNickname = validUsr.getObjNickNameGivenObjId();
	String thsms_regis = (String)request.getParameter("thsms_regis");
	int approve_counter = 0;
	if(listApprovee!=null && !Checker.isStringNullOrEmpty(listApprovee)) {
		StringTokenizer st = new StringTokenizer(listApprovee,",");
		approve_counter = st.countTokens();
	}
%>


</head>
<body>

<!--  div id="header">
	<  %@ include file="innerMenu.jsp" % >
</div -->
<div class="colmask fullpage">
	<div class="col1">

		<br />
		<%
	if(from==null || Checker.isStringNullOrEmpty(from)) { //bukan dari notification
		%>
		<div id="tmp" style="text-align:center">
		<form action="#" method="post">
		
			<h2>TAHUN AKADEMIK 
			<select name="target_thsms">
			<%
			
			Vector vlist_thsms = Tool.returnTokensListThsmsTpAntara("20121", Checker.getThsmsHeregistrasi());
			if(vlist_thsms!=null && vlist_thsms.size()>0) {
				ListIterator li = vlist_thsms.listIterator();
				while(li.hasNext()) {
					String tkn_thsms = (String)li.next();
					if(tkn_thsms.equalsIgnoreCase(thsms_regis)) {
			%>
				<option value="<%=tkn_thsms %>" selected="selected"><%=tkn_thsms %></option>
			<%			
					}
					else {
			%>
				<option value="<%=tkn_thsms %>" ><%=tkn_thsms %></option>
			<%		
					}
				}
			}
			%>
			</select> <input type="submit" value="Ganti THSMS"/>
			</h2>
		</form>
		</div>
		
		<br/>
		<%
	}
		
	try {
		if(finalList!=null && !Checker.isStringNullOrEmpty(finalList)) {
			//System.out.println(finalList);
			//out.print(finalList+"<br/>");
			StringTokenizer st = new StringTokenizer(finalList,"$");
		%>
		<form action="proses.updateDaftarUlang" method="post">
		<input type="hidden" name="from" value="<%=""+from%>"/>
		<input type="hidden" name="thsms_regis" value="<%=thsms_regis %>">
		<table align="center" border="1px" bordercolor="#369" style="background:#d9e1e5;color:#000;width:<%=735+(Integer.valueOf(totApproval).intValue()*15)%>px">
        <tr>
	    	<td style="background:#369;color:#fff;text-align:center" colspan="<%=4+approve_counter%>"><label><B>DAFTAR MAHASISWA YANG MENDAFTAR ULANG</B> </label></td>
	    </tr>
    	<tr>
    		<td style="background:#369;color:#fff;text-align:center;width:20px">NO</td><td style="background:#369;color:#fff;text-align:center;width:150px">PRODI</td><td style="background:#369;color:#fff;text-align:center;width:150px">NPM/NIM</td><td style="background:#369;color:#fff;text-align:center;width:315px">NAMA</td>
    	<%
    		StringTokenizer st2 = new StringTokenizer(listApprovee.replace("OPERATOR", ""),",");
    		while(st2.hasMoreTokens()) {
        	%>	
        		<td style="background:#369;color:#fff;text-align:center;width:15px"><%=st2.nextToken() %></td>
        	<%
        	}
    	//listObjApproval = listObjApproval.replace("OPERATOR", "");
    		/*
    		sttmp = new StringTokenizer(listObjApproval.replace("OPERATOR", ""),",");
    		while(sttmp.hasMoreTokens()) {
    	%>	
    		<td style="background:#369;color:#fff;text-align:center;width:15px"><%=sttmp.nextToken() %></td>
    	<%
    		}
    		*/
    	%>	
    	</tr>
    	<%
    		//boolean approved = false;
    		boolean show_submit_button = false;
    		int no = 0;
    		while(st.hasMoreTokens()) {
    			//nuFinalList = nmpst+"$"+kdpst+"$"+npmhs+"$"+nimhs+"$"+nmmhs+"$"+smawl+"$"+stpid+"$"+tglAju+"$"+tknApr+"$"+tknVerObj+"$"+urutan+"$"+idObj;
	    		String nmpst=st.nextToken();
    			String kdpst=st.nextToken();
	    		String npmhs=st.nextToken();
    			String nimhs=st.nextToken();
    			String nmmhs=st.nextToken();    		
	    		String smawl=st.nextToken();
    			String stpid=st.nextToken();
    			String tglAju=st.nextToken();
    			String tknApr=st.nextToken();
    			String tknVerObj=st.nextToken();
    			String urutan=st.nextToken();
    			String idObj=st.nextToken();		
    	%>
    	<tr>
    		<td style="text-align:center"><%=++no %></td><td style="text-align:center"><%=nmpst %></td><td style="text-align:center"><%=npmhs %> / <%=nimhs %></td><td><%=nmmhs %></td>
    	<%
	    		String nickMatch="";
    	/*
    			if(npmhs.equalsIgnoreCase("6510110200012")||npmhs.equalsIgnoreCase("6220113200004")) {
    				System.out.println("npmhs="+npmhs);
    				System.out.println("tknApr="+tknApr);
    				System.out.println("listApprovee="+listApprovee);
    				System.out.println("tknVerObj="+tknVerObj);
    				System.out.println("listObjApproval="+listObjApproval);
    			}
    	*/
    			StringTokenizer sttmp = new StringTokenizer(tknVerObj,",");
    			st2 = new StringTokenizer(listApprovee,",");
    			while(st2.hasMoreTokens()) {
    				
    				String at_col_approvee = st2.nextToken();
    				/*
    				cek apakah prodi ini butuh obj = at_col_approvee
    				*/
    				
    				if(tknVerObj.toUpperCase().contains(at_col_approvee.toUpperCase())) {
    					boolean match = false;
    					String targetNick = new String(at_col_approvee);
						StringTokenizer stt = new StringTokenizer(usrObjNickname,",");
    	    			while(stt.hasMoreTokens()&&!match) {
        					String nick = stt.nextToken();
        					if(targetNick.equalsIgnoreCase(nick)) {
        						match = true;
        						show_submit_button = true;
        						nickMatch = ""+targetNick;
        					}
        				}
    			
    	
	    				if(match) {
	    					if(tknApr.contains(nickMatch)) {
	    						//approved = true;
    	%>	
    		<td style="text-align:center"><input type="checkbox" name="option1" value="<%=kdpst+"#"+npmhs+"#"+validUsr.getNpm()+"#"+nickMatch %>" checked></td>
    	<%		
    						}
    						else {
    							//approved = false;
    	%>	
    		<td style="text-align:center"><input type="checkbox" name="option1" value="<%=kdpst+"#"+npmhs+"#"+validUsr.getNpm()+"#"+nickMatch %>"></td>
    	<%
    						}
    	
	    				}
    					else {
    						if(tknApr.contains(targetNick)) {
    							//approved = true;
    	%>
    		<td style="text-align:center;font-size:1.5em">&#9745;</td>
    	<%				
	    					}
    						else {
    							//approved = false;
    	%>
    		<td style="text-align:center;font-size:1.5em">&#9744;</td>
    	<%			
    						}    	
    					}	
    				}
    				else {
    					//kalo tidak membutuhkan persetujuan dari obj nickname at_col_approvee
    			%>
    	    	<td style="text-align:center"></td>
    	    	<%	
    				}
    			}
    			
    			/*
    			while(sttmp.hasMoreTokens()) {//listObjAproval
    				boolean match = false;
    				String targetNick = sttmp.nextToken();
					StringTokenizer stt = new StringTokenizer(usrObjNickname,",");
    	    		while(stt.hasMoreTokens()&&!match) {
        				String nick = stt.nextToken();
        				if(targetNick.equalsIgnoreCase(nick)) {
        					match = true;
        					show_submit_button = true;
        					nickMatch = ""+targetNick;
        				}
        			}
    			
    	
	    			if(match) {
    					if(tknApr.contains(nickMatch)) {
    	%>	
    		<td style="text-align:center"><input type="checkbox" name="option1" value="<%=kdpst+"#"+npmhs+"#"+validUsr.getNpm()+"#"+nickMatch %>" checked></td>
    	<%		
    					}
    					else {
    	%>	
    		<td style="text-align:center"><input type="checkbox" name="option1" value="<%=kdpst+"#"+npmhs+"#"+validUsr.getNpm()+"#"+nickMatch %>"></td>
    	<%
    					}
    	
	    			}
    				else {
    					if(tknApr.contains(targetNick)) {
    	%>
    		<td style="text-align:center;font-size:1.5em">&#9745;</td>
    	<%				
    					}
    					else {
    	%>
    		<td style="text-align:center;font-size:1.5em">&#9744;</td>
    	<%			
    					}    	
    				}
    			}	
    			*/
    	%>	
    	
    	</tr>
    	<%		
			}
    	%>
    </table>
    <%
    		if(show_submit_button) {
    %>
    <div style="text-align:center">
    <input  type="submit" value="UPDATE" style="width:500px">
    </div>
    </form>
    
    	<%
    		}
		}
		else {
		%>
		<h2 align="center"> BELUM ADA MAHASISWA YG MENDAFTAR ULANG </h2>
		<%
		}
	}
	catch(Exception e) {
		System.out.println(e);
	}
    	%>		
	</div>
</div>		
</body>
	