<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<%@ page import="beans.setting.*" %>
<%
String target_kdpst = request.getParameter("target_kdpst");
%>
<meta http-equiv=Content-Type content="text/html; charset=windows-1252">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 14">
<link rel=File-List href="masterFlowChart_files/filelist.xml">
<style id="masterFlowChart_22374_Styles">
<!--table
	{mso-displayed-decimal-separator:"\.";
	mso-displayed-thousand-separator:"\,";}
.xl1522374
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:1;
	mso-number-format:General;
	text-align:general;
	vertical-align:bottom;
	mso-background-source:auto;
	mso-pattern:auto;
	white-space:nowrap;}
.xl6322374
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:1;
	mso-number-format:General;
	text-align:general;
	vertical-align:bottom;
	background:#C5D9F1;
	mso-pattern:black none;
	white-space:nowrap;}
.xl6422374
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:bottom;
	background:#C5D9F1;
	mso-pattern:black none;
	white-space:nowrap;}
.xl6522374
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:14.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	background:#C5D9F1;
	mso-pattern:black none;
	white-space:nowrap;}
.xl6622374
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:9.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	background:#C5D9F1;
	mso-pattern:black none;
	white-space:nowrap;}
.xl6722374
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:22.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:1;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	background:#C5D9F1;
	mso-pattern:black none;
	white-space:nowrap;}
.xl6822374
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:1;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	background:#C5D9F1;
	mso-pattern:black none;
	white-space:normal;}
.xl6922374
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:white;
	font-size:16.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	background:black;
	mso-pattern:black none;
	white-space:nowrap;}
.xl7022374
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:1.0pt solid windowtext;
	border-right:none;
	border-bottom:none;
	border-left:1.0pt solid windowtext;
	background:#F2F2F2;
	mso-pattern:black none;
	white-space:normal;}
.xl7122374
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:1.0pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:none;
	border-left:none;
	background:#F2F2F2;
	mso-pattern:black none;
	white-space:normal;}
.xl7222374
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:none;
	border-left:1.0pt solid windowtext;
	background:#F2F2F2;
	mso-pattern:black none;
	white-space:normal;}
.xl7322374
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:none;
	border-left:none;
	background:#F2F2F2;
	mso-pattern:black none;
	white-space:normal;}
.xl7422374
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:1.0pt solid windowtext;
	background:#F2F2F2;
	mso-pattern:black none;
	white-space:normal;}
.xl7522374
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:700;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:0;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	background:#F2F2F2;
	mso-pattern:black none;
	white-space:normal;}
.xl7622374
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:1;
	mso-number-format:General;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:none;
	border-left:1.0pt solid windowtext;
	background:#C5D9F1;
	mso-pattern:black none;
	white-space:nowrap;}
.xl7722374
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:1;
	mso-number-format:General;
	text-align:left;
	vertical-align:middle;
	border-top:1.0pt solid windowtext;
	border-right:none;
	border-bottom:none;
	border-left:1.0pt solid windowtext;
	background:#F2F2F2;
	mso-pattern:black none;
	white-space:normal;}
.xl7822374
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:1;
	mso-number-format:General;
	text-align:left;
	vertical-align:middle;
	border-top:1.0pt solid windowtext;
	border-right:none;
	border-bottom:none;
	border-left:none;
	background:#F2F2F2;
	mso-pattern:black none;
	white-space:normal;}
.xl7922374
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:1;
	mso-number-format:General;
	text-align:left;
	vertical-align:middle;
	border-top:1.0pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:none;
	border-left:none;
	background:#F2F2F2;
	mso-pattern:black none;
	white-space:normal;}
.xl8022374
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:1;
	mso-number-format:General;
	text-align:left;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:none;
	border-left:1.0pt solid windowtext;
	background:#F2F2F2;
	mso-pattern:black none;
	white-space:normal;}
.xl8122374
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:1;
	mso-number-format:General;
	text-align:left;
	vertical-align:middle;
	background:#F2F2F2;
	mso-pattern:black none;
	white-space:normal;}
.xl8222374
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:1;
	mso-number-format:General;
	text-align:left;
	vertical-align:middle;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:none;
	border-left:none;
	background:#F2F2F2;
	mso-pattern:black none;
	white-space:normal;}
.xl8322374
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:1;
	mso-number-format:General;
	text-align:left;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:1.0pt solid windowtext;
	background:#F2F2F2;
	mso-pattern:black none;
	white-space:normal;}
.xl8422374
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:1;
	mso-number-format:General;
	text-align:left;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	background:#F2F2F2;
	mso-pattern:black none;
	white-space:normal;}
.xl8522374
	{padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:black;
	font-size:11.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:Calibri, sans-serif;
	mso-font-charset:1;
	mso-number-format:General;
	text-align:left;
	vertical-align:middle;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	background:#F2F2F2;
	mso-pattern:black none;
	white-space:normal;}
-->
</style>
</head>

<body>
<!--[if !excel]>&nbsp;&nbsp;<![endif]-->
<!--The following information was generated by Microsoft Excel's Publish as Web
Page wizard.-->
<!--If the same item is republished from Excel, all information between the DIV
tags will be replaced.-->
<!----------------------------->
<!--START OF OUTPUT FROM EXCEL PUBLISH AS WEB PAGE WIZARD -->
<!----------------------------->

<div id="masterFlowChart_22374" align=center x:publishsource="Excel">

<table border=0 cellpadding=0 cellspacing=0 width=712 style='border-collapse:
 collapse;table-layout:fixed;width:534pt'>
 <col width=36 style='mso-width-source:userset;mso-width-alt:1316;width:27pt'>
 <col width=64 span=10 style='width:48pt'>
 <col width=36 style='mso-width-source:userset;mso-width-alt:1316;width:27pt'>
 <tr height=28 style='height:21.0pt'>
  <td colspan=12 height=28 class=xl6922374 width=712 style='height:21.0pt;
  width:534pt'>SKEMA UTAMA</td>
 </tr>
 <tr height=20 style='mso-height-source:userset;height:15.0pt'>
  <td height=20 class=xl6322374 style='height:15.0pt'>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
 </tr>
 <tr height=20 style='mso-height-source:userset;height:15.0pt'>
  <td height=20 class=xl6322374 style='height:15.0pt'>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
 </tr>
 <tr height=20 style='mso-height-source:userset;height:15.0pt'>
  <td height=20 class=xl6322374 style='height:15.0pt'>&nbsp;</td>
  <td colspan=2 rowspan=4 class=xl7022374 width=128 style='border-right:1.0pt solid black;
  border-bottom:1.0pt solid black;width:96pt'>
  <a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Flowchart/<%=target_kdpst %>/std1/indexStd1.jsp?atMenu=std1&target_kdpst=<%=target_kdpst%>">(1)<br>Standar Kompetensi Lulusan</a></td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td colspan=2 rowspan=4 class=xl7022374 width=128 style='border-right:1.0pt solid black;
  border-bottom:1.0pt solid black;width:96pt'>
  <a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Flowchart/<%=target_kdpst %>/std5/indexStd5.jsp?atMenu=isi&target_kdpst=<%=target_kdpst%>">(5)<br>Standar Dosen &amp; Tenaga Kependidikan</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td colspan=2 rowspan=4 class=xl7022374 width=128 style='border-right:1.0pt solid black;
  border-bottom:1.0pt solid black;width:96pt'>(7)<br>
    Standar Pengelolaan Pembelajaran</td>
  <td class=xl6322374>&nbsp;</td>
 </tr>
 <tr height=20 style='mso-height-source:userset;height:15.0pt'>
  <td height=20 class=xl6322374 style='height:15.0pt'>&nbsp;</td>
  <td class=xl6522374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
 </tr>
 <tr height=20 style='mso-height-source:userset;height:15.0pt'>
  <td height=20 class=xl6322374 style='height:15.0pt'>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
 </tr>
 <tr height=20 style='mso-height-source:userset;height:15.0pt'>
  <td height=20 class=xl6322374 style='height:15.0pt'>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6422374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
 </tr>
 <tr height=20 style='mso-height-source:userset;height:15.0pt'>
  <td height=20 class=xl6322374 style='height:15.0pt'>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6722374>&nbsp;</td>
  <td class=xl6722374>&nbsp;</td>
  <td class=xl6722374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
 </tr>
 <tr height=20 style='mso-height-source:userset;height:15.0pt'>
  <td height=20 class=xl6322374 style='height:15.0pt'>&nbsp;</td>
  <td colspan=2 rowspan=4 class=xl7022374 width=128 style='border-right:1.0pt solid black;
  border-bottom:1.0pt solid black;width:96pt'>
  <a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Flowchart/<%=target_kdpst %>/std2/indexStd2.jsp?atMenu=isi&target_kdpst=<%=target_kdpst%>">(2)<br>Standar Isi Pembelajaran</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6722374>&nbsp;</td>
  <td colspan=2 rowspan=4 class=xl7022374 width=128 style='border-right:1.0pt solid black;
  border-bottom:1.0pt solid black;width:96pt'>
  <a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Flowchart/<%=target_kdpst %>/std6/indexStd6.jsp?atMenu=isi&target_kdpst=<%=target_kdpst%>">(6)<br>Standar Sarana &amp; Prasarana Pembelajaran</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td colspan=2 rowspan=4 class=xl7022374 width=128 style='border-right:1.0pt solid black;
  border-bottom:1.0pt solid black;width:96pt'>(8)<br>
    Standar Pembiyaan Pembelajaran</td>
  <td class=xl6322374>&nbsp;</td>
 </tr>
 <tr height=20 style='mso-height-source:userset;height:15.0pt'>
  <td height=20 class=xl6322374 style='height:15.0pt'>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6722374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
 </tr>
 <tr height=20 style='mso-height-source:userset;height:15.0pt'>
  <td height=20 class=xl6322374 style='height:15.0pt'>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6622374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
 </tr>
 <tr height=20 style='mso-height-source:userset;height:15.0pt'>
  <td height=20 class=xl6322374 style='height:15.0pt'>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
 </tr>
 <tr height=20 style='mso-height-source:userset;height:15.0pt'>
  <td height=20 class=xl6322374 style='height:15.0pt'>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
 </tr>
 <tr height=20 style='mso-height-source:userset;height:15.0pt'>
  <td height=20 class=xl6322374 style='height:15.0pt'>&nbsp;</td>
  <td colspan=2 rowspan=4 class=xl7022374 width=128 style='border-right:1.0pt solid black;
  border-bottom:1.0pt solid black;width:96pt'>
  <a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Flowchart/<%=target_kdpst %>/std3/indexStd3.jsp?atMenu=isi&target_kdpst=<%=target_kdpst%>">(3)<br>Standar Proses Pembelajaran</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td colspan=2 rowspan=4 class=xl7022374 width=128 style='border-right:1.0pt solid black;
  border-bottom:1.0pt solid black;width:96pt'>(9)<br>
    Standar Penelitian</td>
  <td class=xl6322374>&nbsp;</td>
 </tr>
 <tr height=20 style='mso-height-source:userset;height:15.0pt'>
  <td height=20 class=xl6322374 style='height:15.0pt'>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
 </tr>
 <tr height=20 style='mso-height-source:userset;height:15.0pt'>
  <td height=20 class=xl6322374 style='height:15.0pt'>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
 </tr>
 <tr height=21 style='height:15.75pt'>
  <td height=21 class=xl6322374 style='height:15.75pt'>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
 </tr>
 <tr height=20 style='mso-height-source:userset;height:15.0pt'>
  <td height=20 class=xl6322374 style='height:15.0pt'>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td colspan=4 rowspan=6 class=xl7722374 width=256 style='border-right:1.0pt solid black;
  border-bottom:1.0pt solid black;width:192pt'>1. Karakteristik Proses
  Pembelajaran<br>
    2. Perencanaan Proses Pembelajaran<br>
    3. Pelaksanaan Proses Pembelajaran<br>
    4. Beban Belajar Mahasiswa</td>
  <td class=xl6822374 width=64 style='width:48pt'>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
 </tr>
 <tr height=20 style='mso-height-source:userset;height:15.0pt'>
  <td height=20 class=xl6322374 style='height:15.0pt'>&nbsp;</td>
  <td colspan=2 rowspan=4 class=xl7022374 width=128 style='border-right:1.0pt solid black;
  border-bottom:1.0pt solid black;width:96pt'>
  <a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/Flowchart/<%=target_kdpst %>/std4/indexStd4.jsp?atMenu=isi&target_kdpst=<%=target_kdpst%>">(4)<br>Standar Penilaian Pembelajaran</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6822374 width=64 style='width:48pt'>&nbsp;</td>
  <td colspan=2 rowspan=4 class=xl7022374 width=128 style='border-right:1.0pt solid black;
  border-bottom:1.0pt solid black;width:96pt'>(10)<br>
    Standar Pengabdian Masyarakat</td>
  <td class=xl6322374>&nbsp;</td>
 </tr>
 <tr height=20 style='height:15.0pt'>
  <td height=20 class=xl6322374 style='height:15.0pt'>&nbsp;</td>
  <td rowspan=2 class=xl7622374>----------&gt;</td>
  <td class=xl6822374 width=64 style='width:48pt'>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
 </tr>
 <tr height=20 style='height:15.0pt'>
  <td height=20 class=xl6322374 style='height:15.0pt'>&nbsp;</td>
  <td class=xl6822374 width=64 style='width:48pt'>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
 </tr>
 <tr height=21 style='height:15.75pt'>
  <td height=21 class=xl6322374 style='height:15.75pt'>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6822374 width=64 style='width:48pt'>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
 </tr>
 <tr height=21 style='height:15.75pt'>
  <td height=21 class=xl6322374 style='height:15.75pt'>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6822374 width=64 style='width:48pt'>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
 </tr>
 <tr height=20 style='mso-height-source:userset;height:15.0pt'>
  <td height=20 class=xl6322374 style='height:15.0pt'>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
 </tr>
 <tr height=20 style='height:15.0pt'>
  <td height=20 class=xl6322374 style='height:15.0pt'>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
  <td class=xl6322374>&nbsp;</td>
 </tr>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=36 style='width:27pt'></td>
  <td width=64 style='width:48pt'></td>
  <td width=64 style='width:48pt'></td>
  <td width=64 style='width:48pt'></td>
  <td width=64 style='width:48pt'></td>
  <td width=64 style='width:48pt'></td>
  <td width=64 style='width:48pt'></td>
  <td width=64 style='width:48pt'></td>
  <td width=64 style='width:48pt'></td>
  <td width=64 style='width:48pt'></td>
  <td width=64 style='width:48pt'></td>
  <td width=36 style='width:27pt'></td>
 </tr>
 <![endif]>
</table>

</div>


<!----------------------------->
<!--END OF OUTPUT FROM EXCEL PUBLISH AS WEB PAGE WIZARD-->
<!----------------------------->
</body>

</html>
