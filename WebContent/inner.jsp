<?php
	function dad(){
		return isset($_GET['drag']) && $_GET['drag']=='on' ? 1 : 0;
	}
?>
<!DOCTYPE html>
<html>
	<head>
		<meta name="author" content="Claudio Bonifazi (claudio.bonifazi@gmail.com)">
		<link rel="author" href="../../humans.txt">
		<meta name="generator" content="Notepad++">
		<meta name="description" content="This is an attempt to recreate the whole Windows 8 Metro UI.">
		<meta name="keywords" content="Web Design, Web Developer, User Interface, Windows 8, Metro, Web, informatica, sito web, Claudio Bonifazi, Claudio, Bonifazi">
		<title> Click effect as in Windows 8 Metro UI - web development and design in Milan, IT - Claudio Bonifazi </title>
		<meta name="language" content="it">
		<meta name="charset" content="utf-8">
		<link rel="shortcut icon" href="../../favicon.ico">
		<link rel='stylesheet' href='http://fonts.googleapis.com/css?family=Ubuntu+Condensed'>
		<link rel="stylesheet" href="/ToUnivSatyagama/metro/layout/_import.css">
		<script src="/ToUnivSatyagama/metro/script/head.js"></script>
				<script type='text/javascript' src="/ToUnivSatyagama/jquery/jquery.min.js"></script>
		<script src="/ToUnivSatyagama/jquery/jquery.mousewheel.js"></script>
		<script>
		$(function(){
			$("#page-wrap").wrapInner("<table cellspacing='30'><tr>");
			$(".post").wrap("<td></td>");
			$("body").mousewheel(function(event, delta) {
				this.scrollLeft -= (delta * 30);
				event.preventDefault();
			});   
		});
		</script>
		<!--[if lt IE 9]>
			<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
	</head>
	<body class="grey">
		<div id="center">
			<section>
				<a href="#" class="normal">
					<span>blahblah</span>
				</a>
				<a href="#" class="fulltext">
					<span>random</span><br>
					Lorem Ipsum is simply dummy text of the printing industry.
				</a>
				<a href="#" class="icon">
					<img src="/ToUnivSatyagama/metro/layout/images/ie.png" alt="explorer" width="148" height="148">
				</a>
				<a href="randomcontent.php?con=search" class="search sidebar">
					<img src="/ToUnivSatyagama/metro/layout/images/searchIcon.png" alt="Search" width="148" height="148">
				</a>
				<a href="http://claudiobonifazi.com" class="preview">
					<img src="/ToUnivSatyagama/metro/layout/images/web-developer-designer.png" alt="explorer" width="300" height="100">
					Back to my site
				</a>
				<a href="randomcontent.php?con=numbers" class="numbers sidebar">
					<span>09</span>
					Readme
				</a>
				<a href="randomcontent.php?con=twitter" class="twitter sidebar">
					<img src="layout/images/small.png" alt="twitter_avatar" width="80" height="80">
					<span>@claudiobonifazi</span><br>
					lorem ipsum batman megashark vs. giant octopus lolz! RT @The_batman<br>

					<i>Tweeted from Something</i>
				</a>
				<a href="randomcontent.php?con=settings<?php echo dad() ? '&drag=on':''; ?>&defCol=violet" class="icon sidebar">
					<img src="/ToUnivSatyagama/metro/layout/images/gear_icon.png" alt="games" width="148" height="148">
				</a>
				<a href="#" class="image">
					<img src="/ToUnivSatyagama/metro/layout/images/valley.png" alt="valley" width="302" height="152">
				</a>
			</section>
			<!-- same stuff repeated, just for filling space -->
			<section>
				<a href="#" class="image">
					<img src="/ToUnivSatyagama/metro/layout/images/valley.png" alt="valley" width="302" height="152">
				</a>
				<a href="randomcontent.php?con=twitter" class="normal">
					...
				</a>
				<a href="#" class="icon">
					<img src="/ToUnivSatyagama/metro/layout/images/spade.png" alt="games" width="148" height="148">
				</a>
				<a href="#" class="icon">
					<img src="/ToUnivSatyagama/metro/layout/images/ie.png" alt="explorer" width="148" height="148">
				</a>
				<a href="#" class="twitter">
					...
				</a>
				<a href="#" class="fulltext">
					<span>...</span><br>
					...
				</a>
				<a href="#" class="fulltext">
					<span>...</span><br>
					...
				</a>
				<a href="#" class="search">
					<img src="/ToUnivSatyagama/metro/layout/images/searchIcon.png" alt="Search" width="148" height="148">
				</a>
				<a href="#" class="numbers">
					<span>##</span>
					...
				</a>
			</section>
		</div>
	</body>
</html>