<!DOCTYPE html>
<head>

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

%>
<script type="text/javascript">

</script>
</head>
<body>
<div id="header">

</div>
<div class="colmask fullpage">
	<div class="col1">
		<br />
		<!-- Column 1 start -->
		<div align="center">
		
		<h3>HARAP MEMILIH TAHUN / SEMESTER :</h3>
		<form method="post" action="goto.fwdLink?linkTo=feeder_part2" target="inner_iframe">
			<select name="id_smt" id="id_smt" style="padding:5px 5px 5px 35px;width:200px;">
				<option value="20152">2015/2016 Genap</option>
				<option value="20151">2015/2016 Ganjil</option>
				<option value="20142">2014/2015 Genap</option>
				<option value="20141">2014/2015 Ganjil</option>
				<option value="20132">2013/2014 Genap</option>
				<option value="20131">2013/2014 Ganjil</option>
				<option value="20122">2012/2013 Genap</option>
				<option value="20121">2012/2013 Ganjil</option>
				<option value="20112">2011/2012 Genap</option>
				<option value="20111">2011/2012 Ganjil</option>
				<option value="20102">2010/2011 Genap</option>
				<option value="20101">2010/2011 Ganjil</option>
				<option value="20092">2009/2010 Genap</option>
				<option value="20091">2009/2010 Ganjil</option>
			</select>      
		<br/>	
		<br>             
		<input type="submit"  value="NEXT" style=";width:100px;"/>
		</form>			
				
		</div>		
		<!-- Column 1 end -->
	</div>
</div>

</body>
</html>