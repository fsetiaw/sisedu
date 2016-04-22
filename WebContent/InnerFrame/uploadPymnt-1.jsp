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
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Date" %>


<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");

Vector v= null; 
String cmd = request.getParameter("cmd");
String msg = request.getParameter("msg");
String objId = request.getParameter("id_obj");
String nmm = request.getParameter("nmm");
String npm = request.getParameter("npm");
String kdpst = request.getParameter("kdpst");
String obj_lvl =  request.getParameter("obj_lvl");
/*
*/

Vector v_bak = (Vector) request.getAttribute("v_bak");
%>
<script type="text/javascript" src="<%=Constants.getRootWeb() %>/js/jquery.js"> </script>

<script type="text/javascript">

var req;
var tmp = "start";
function ajaxFunction()
{
   var url = "go.uploadFile";
   tmp = tmp+"<br/>"+url;
   document.getElementById("test").innerHTML = "start<br/>";
   if (window.XMLHttpRequest)        // Non-IE browsers
   {
	   	tmp = tmp+"<br/>non ie";
	  document.getElementById("test").innerHTML = tmp;
      req = new XMLHttpRequest();
      req.onReadyStateChange = processStateChange();
 
      try
      {
         req.open("GET", url, true);
      } 
      catch (e) 
      {
            alert(e);
      }
      req.send(null);
   }
   else if (window.ActiveXObject)    // IE Browsers
   {
      req = new ActiveXObject("Microsoft.XMLHTTP");
      tmp = tmp+"<br/>is ie";
      document.getElementById("test").innerHTML = tmp;
      if (req) 
      {
            req.onReadyStateChange = processStateChange();
            req.open("GET", url, true);
            req.send();
      }
   }
   
}

function processStateChange()
{
   /**
   *  State    Description
   *    0      The request is not initialized
   *    1      The request has been set up
   *    2      The request has been sent
   *    3      The request is in process
   *    4      The request is complete
   */
   if (req.readyState == 4)
   {
	   tmp = tmp+"<br/>4";
	   document.getElementById("test").innerHTML = tmp;
      if (req.status == 200) // OK response
      {
         var xml = req.responseXml;
         tmp = tmp+"<br/>200";
  	   	 document.getElementById("test").innerHTML = tmp;
  	   	 
         // No need to iterate since there will only be one set
         // of lines
         var isNotFinished = xml.getElementsByTagName("finished")[0].value;
         var myBytesRead = xml.getElementsByTagName("bytes_read")[0].value;
         var myContentLength = xml.getElementsByTagName("content_length")[0].value;
         var myPercent = xml.getElementsByTagName("percent_complete")[0].value;
 
         // Check to see if it's even started yet
         if ((isNotFinished == null) && (myPercent == null))
         {
               document.getElementById("initializing").style.visibility = "visible";
               // Sleep then call the function again
               window.setTimeout("ajaxFunction();", 1);
         }
         else 
         {
             document.getElementById("initializing" ).style.visibility = "hidden";
             document.getElementById("progressBarTable" ).style.visibility = "visible";
             document.getElementById("percentCompleteTable" ).style.visibility = "visible";
             document.getElementById("bytesRead" ).style.visibility = "visible";
 
             myBytesRead = myBytesRead.firstChild.data;
             myContentLength = myContentLength.firstChild.data;
 
             // It's started, get the status of the upload
             if (myPercent != null)
             {
                myPercent = myPercent.firstChild.data;
 
                document.getElementById("progressBar").style.width = myPercent + "%";
                document.getElementById("bytesRead").innerHTML = myBytesRead + " of " + myContentLength + " bytes read";
                document.getElementById("percentComplete").innerHTML = myPercent + "%";
 
                // Sleep then call the function again
                window.setTimeout("ajaxFunction();", 1);
             }
             else
             {
                document.getElementById("bytesRead").style.visibility = "hidden";
                document.getElementById("progressBar").style.width = "100%";
                document.getElementById("percentComplete").innerHTML = "Done!";
             }
            }
      }
      else
      {
            alert(req.statusText);
      }
   }
   else {
	   tmp = tmp+"<br/>not 4";
	   document.getElementById("test").innerHTML = tmp;
   }
}
</script>
</head>
<body onload="location.href='#'">
<div id="header">
<ul>
	<li><a href="javascript:history.go(-1)" >BACK <span><b style="color:#eee">---</b></span></a></li>
</ul>
</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<iframe id="uploadFrameID" name="uploadFrame" height="0" width="0" frameborder="0" scrolling="yes"></iframe>
   <form id="myForm" enctype="multipart/form-data" method="post" target="uploadFrame" action="go.uploadFile" onsubmit="ajaxFunction()">
   <input type="file" name="txtFile" id="txtFile" />
   <br />
   <input type="submit" id="submitID" name="submit" value="Upload11" />
   </form>
<div id="test" style="visibility: visible; position: absolute; top: 100px;">		
</div>
		<!-- Add hidden DIVs for updating and the progress bar
     (just a table) below the form -->
<div id="initializing" style="visibility: hidden; position: absolute; top: 100px;">
   <table width="100%" style="border: 1px; background-color: black;">
      <tr>
         <td>
            <table width="100%" style="border: 1px; background-color: black; color: white;">
               <tr>
                  <td align="center">
                     <b>Initializing Upload...</b>
                  </td>
               </tr>
            </table>
         </td>
      </tr>
   </table>
</div>
 
<div id="progressBarTable" style="visibility: hidden; position: absolute; top: 100px;">
   <table width="100%" style="border: 1px; background-color: black; color: white;">
      <tr>
         <td>
            <table id="progressBar" width="0px" style="border: 1px; width: 0px; background-color: blue;">
                  <tr>
                     <td>&nbsp;</td>
               </tr>
            </table>
         </td>
      </tr>
   </table>
   <table width="100%" style="background-color: white; color: black;">
      <tr>
         <td align="center" nowrap="nowrap">
            <span id="bytesRead" style="font-weight: bold;">&nbsp;</span>
         </td>
      </tr>
   </table>
</div>
 
<div id="percentCompleteTable" align="center" style="visibility: hidden; position: absolute; top: 100px;">
   <table width="100%" style="border: 1px;">
      <tr>
         <td>
            <table width="100%" style="border: 1px;">
               <tr>
                  <td align="center" nowrap="nowrap">
                     <span id="percentComplete" style="color: white; font-weight: bold;">&nbsp;</span>
                  </td>
               </tr>
            </table>
         </td>
      </tr>
   </table>
</div>
	
		<!-- Column 1 end -->
	</div>
</div>


</body>
</html>