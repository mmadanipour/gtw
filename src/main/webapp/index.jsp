<html>
<head>
<link rel="stylesheet" type="text/css" href="js/style.css" />
</head>
<header>
	<h2>Guess The Word</h2>
	<script type="text/javascript" src="js/gtw.js"></script>
	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"
		type="text/javascript"></script>
	<script type="text/javascript" src="js/script.js"></script>
</header>
<body>
	<div id="d_start_game_id">
		<b>Enter your name</b> <input id="name_id"></input> </br> </br> </br>
		<button id="start_game_btn_id" onclick="startGame()">Start
			Game</button>
	</div>
	<br />
	<br />

	<div id="d_greeting_id" hidden="true">
		<b id="greeting_text_id"></b> <br> <br> <b id="word_desc_id"></b>
	</div>
	<div id="d_waiting_id">
		<b id="waiting_text_id"></b>
	</div>
	<br />
	<br />
	<div class="wrapper"  id="d_guess_id" hidden="true">
		<div id="d_word_id"></div>
		<br /> <br />
		<div class="wrapper">
			<button class = "btn" id="btn_check_action_id" onClick=checkAction()>Check</button>
		</div>
	</div>

</body>

</html>
