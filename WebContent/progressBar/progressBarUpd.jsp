<!DOCTYPE html>
<head>
    <title></title>
    <%@ page import="beans.setting.*" %>
    <%@ page import="beans.tools.*" %>
<%
String msgTmp = request.getParameter("msg");
String tmp = (String) session.getAttribute("tmp");
session.removeAttribute("tmp");
%> 

</head>
<body onload="MoveTo();">
    <style type="text/css">
        #prbar
        {
        	align:center;
            margin:5px;
            width:600px;
            background-color:#dddddd;
            overflow:hidden;
    
            /* Rounded Border */
            border: 1px solid #bbbbbb;
            -moz-border-radius: 15px;
            border-radius: 15px;
            
            /* Adding some shadow to the progress bar */
            -webkit-box-shadow: 0px 2px 4px #555555;
            -moz-box-shadow: 0px 2px 4px #555555;
            box-shadow: 0px 2px 4px #555555;            
        }
        
        /* No rounded corners for Opera, because the overflow:hidden dont work with rounded corners */
        doesnotexist:-o-prefocus, #prbar {
          border-radius:0px;
        }
        
        #prpos
        {
            background-color:#3399ff;
            width:0%;
            height:30px;
            
            /* CSS3 Progress Bar Transitions */
            transition: width 5s ease;
            -moz-transition: width 5s ease;
            -webkit-transition: width 5s ease;
            -o-transition: width 5s ease;
            -ms-transition: width 5s ease;
    
            /* CSS3 Stripes */
            background-image: linear-gradient(135deg,#3399ff 25%,#99ccff 25%,#99ccff 50%, #3399ff 50%, #3399ff 75%,#99ccff 75%,#99ccff 100%);
            background-image: -moz-linear-gradient(135deg,#3399ff 25%,#99ccff 25%,#99ccff 50%, #3399ff 50%, #3399ff 75%,#99ccff 75%,#99ccff 100%);
            background-image: -ms-linear-gradient(135deg,#3399ff 25%,#99ccff 25%,#99ccff 50%, #3399ff 50%, #3399ff 75%,#99ccff 75%,#99ccff 100%);
            background-image: -o-linear-gradient(135deg,#3399ff 25%,#99ccff 25%,#99ccff 50%, #3399ff 50%, #3399ff 75%,#99ccff 75%,#99ccff 100%);
            background-image: -webkit-gradient(linear, 100% 100%, 0 0,color-stop(.25, #99ccff), color-stop(.25, #3399ff),color-stop(.5, #3399ff),color-stop(.5, #99ccff),color-stop(.75, #99ccff),color-stop(.75, #3399ff),color-stop(1, #3399ff));
            background-image: -webkit-linear-gradient(135deg,#3399ff 25%,#99ccff 25%,#99ccff 50%, #3399ff 50%, #3399ff 75%,#99ccff 75%,#99ccff 100%);
            background-size: 40px 40px;
            
            /* Background stripes animation */
            animation: bganim 3s linear 2s infinite;
            -moz-animation: bganim 3s linear 2s infinite;
            -webkit-animation: bganim 3s linear 2s infinite;
            -o-animation: bganim 3s linear 2s infinite;
            -ms-animation: bganim 3s linear 2s infinite;
        }
        
        @keyframes bganim {
        from {background-position:0px;} to { background-position:40px;}
        }
        @-moz-keyframes bganim {
        from {background-position:0px;} to { background-position:40px;}
        }
        @-webkit-keyframes bganim {
        from {background-position:0px;} to { background-position:40px;}
        }
        @-o-keyframes bganim {
        from {background-position:0px;} to { background-position:40px;}
        } 
        @-ms-keyframes bganim {
        from {background-position:0px;} to { background-position:40px;}
        } 
    </style>
    <script type="text/javascript">
        function MoveTo() {
            var prpos = document.getElementById('prpos');
            prpos.style.width = document.getElementById('moveTo').value + "%";
        }
    </script>
    <table align="center">
    	<tr>
    		<td>
            <div style="font-size:1.5em;text-align:center;font-style:italic;color:blue">Prosesing Request</div>
        	</td>
        </tr>
        <tr><td>
            <div id="prbar"><div id="prpos"></div></div>
        </td></tr>
        <tr><td align="center" style="padding:5px;">
            <input id="moveTo" value="100" style="width:95%;" type="hidden"/> <button onLoad="MoveTo();return false;" hidden></button>
        </td>
        </tr>
        
    </table>
    <%
    String target1 = Constants.getRootWeb()+tmp;
	String uri1 = request.getRequestURI();
	String url_ff1 = PathFinder.getPath(uri1, target1);
	//request.getRequestDispatcher(url_ff).forward(request,response);
    %>
    <meta http-equiv='Refresh' content='6, URL=<%=target1 %>?msg=<%=msgTmp%>&alihkan=home.jsp'>
</body>
</html>