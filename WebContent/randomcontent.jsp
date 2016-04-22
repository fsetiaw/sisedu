<?php
	if( isset($_GET['con']))
		switch($_GET['con']){
			case 'search':
?>


<form action="#" method="post" id="searchForm" >
	<fieldset>
		<label for="search_stuff"><img src="layout/images/back.png" alt="back" width="30" height="30">Search</label>
		<input type="search" id="search_stuff">
		<button>go</button>
		<output></output>
	</fieldset>
</form>


<?php
				break;
			case 'numbers':
?>


<hgroup>
	<h1><img src="layout/images/back.png" alt="back" width="30" height="30">It's clobbering time!</h1>
	<h2>metro.js is an attempt to reproduce Windows 8 metro interface</h2>
	<h3>(Whith all his fancy effects!)</h3>
</hgroup>
<ol>
	<li>Get the <a href="./script/metro.js" target="_blank" title="Download the script">script</a></li>
	<li>call the method <pre>.metro()</pre> on an array of jQuery elements</li>
	<li>call it setting his argument to true: <pre>.metro( true )</pre> if you want the user to be able to drag&amp;drop to reorganize 'em</li>
	<li>If you think that any of them should open this lateral bar with ajaxed content, give it the class <pre>.sidebar</pre> and place in the <pre>href</pre> attribute the url to the content</li>
	<li>If you actually want to reproduce this interface in its entirety, read also the stylesheet (hope it's clear enough)</li>
	<li>I'll probably upload all this in a github repository sooner or later, in the meantime feel free to contact me on <a href="http://claudiobonifazi.com" target="_blank" title="Web development &amp; design in Milan - Claudio Bonifazi">my website</a></li>
</ol>
<hr>
<h4>Stuff that is work in progress:</h4>
<ol id="stillToBeDone">
	<li>If you try to drag something it works well only if you click it in the center. If the rectangle does a side animation, when you drag it above other links the whole stuff loses focus and it gets messed up</li>
	<li>when you drag something and drop it, it simply returns to its start position: the repositioning is still to be done</li>
	<li>when you drag something that works as a simple link to other pages (example: the link to my website) it redirects to the site when you drop it, i still have to figure out how to prevent the normal behavior in that case</li>
	<li>when an item gets dragged, dropped and consequently repositioned, it should return the new disposition. Maybe as an array, but what counts more is that the value sould be easily retreavable and readable, so to easily store it in a cookie or a database record or wathever</li>
	<li>same thing for the background colors</li>
	<li>I should actually add more options in the settings section</li>
	<li>Add an image slider, a video viewer and some other stuff</li>
	<li>Find a font more similar to Segoe UI</li>
	<li>It's not totally mobile friendly :\</li>
</ol>


<?php
				break;
			case 'twitter':
?>
<hgroup>
	<h1><img src="layout/images/back.png" alt="back" width="30" height="30">Twitter timeline</h1>
	<h2>This is supposed to be a some kind of twitter timeline viewer or wathever</h2>
	<h3>(Follow me! @<a href="http://twitter.com/claudiobonifazi" title="Twitter user: claudiobonifazi" target="_blank">claudiobonifazi</a>)</h3>
</hgroup>
<ul>
	<li>
		<span>This is a tweet</span>
		<i>Tweeted X days ago</i>
	</li>
	<li>
		<span>This is a tweet</span>
		<i>Tweeted X days ago</i>
	</li>		<li>
		<span>This is a tweet</span>
		<i>Tweeted X days ago</i>
	</li>		<li>
		<span>This is a tweet</span>
		<i>Tweeted X days ago</i>
	</li>		<li>
		<span>This is a tweet</span>
		<i>Tweeted X days ago</i>
	</li>		<li>
		<span>This is a tweet</span>
		<i>Tweeted X days ago</i>
	</li>		<li>
		<span>This is a tweet</span>
		<i>Tweeted X days ago</i>
	</li>		<li>
		<span>This is a tweet</span>
		<i>Tweeted X days ago</i>
	</li>
</ul>


<?php
				break;
			case 'searchResult':
?>


<ul>
	<li><a href="#" title="blablah">dummy result 1</a></li>
	<li><a href="#" title="blablah">dummy result 2</a></li>
	<li><a href="#" title="blablah">dummy result 3</a></li>
	<li><a href="#" title="blablah">dummy result 4</a></li>
	<li>...</li>
</ul>


<?php
				break;
			case 'settings':
?>


<hgroup>
	<h1><img src="layout/images/back.png" alt="back" width="30" height="30">Settings</h1>
	<h2>A whole bunch of buttons and blahblah</h2>
</hgroup>
<form action="#" method="post" id="settingsForm">
	<!-- I'm thinking of filling it with some more stuff //WIP -->

	<label for="dragg">Drag &amp; Drop</label>
	
	<button id="dragg"<?php echo isset($_GET['drag']) ? ' class="on"':''; ?>></button>
	<hr>
	<label for="bgColor">Background</label>
	<select id="bgColor">
		<?php
			$colors = array('violet','green','blue','orange','pink');
			foreach( $colors as $i=>$c )
				echo "<option value=\"$c\"".(isset($_GET['defCol']) && $_GET['defCol']==$c ? ' selected="selected"':'').">$c</option>";
		?>

	</select>

</form>


<?php
				break;
		}
?>

