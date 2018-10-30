/**
 * 
 */
var g_game_token = null;
var g_player_token = null;
var g_player_name = null;
var g_current_word = null;
var g_word_desc = null;
var g_round = null;
var g_player_round = null;
var g_histories = null;
var g_interval = null;
var g_is_finished = false;
var g_is_started = false;

function startGame() {

    $
	    .ajax({
		url : 'http://localhost:8080/guess-the-word/rest/game/start/?name='
			+ document.getElementById("name_id").value,
		type : "GET",

		contentType : 'application/json; charset=utf-8',
		success : function(resultData) {
		    if (resultData.status == "REQUEST_OK") {
			console.log(resultData.status)

			setGlobalVars(resultData.response);

			// var state_request_dto = {
			// "gameToken": resultData.response.gameToken,
			// "playerToken": resultData.response.playerToken
			// };

			document.getElementById("d_start_game_id").style.display = 'none';

			setStatusText("waiting");
			document.getElementById("d_waiting_id").style.display = "block";

			checkGameState();
			g_interval = setInterval(checkGameState, 2000);
		    }

		},
		error : function(jqXHR, textStatus, errorThrown) {
		},

		timeout : 120000,
	    });
}

function checkGameState() {
    var state_request_dto = {
	"gameToken" : g_game_token,
	"playerToken" : g_player_token
    };

    $.ajax({
	type : "POST",
	url : "http://localhost:8080/guess-the-word/rest/game/checkState/",
	// The key needs to match your method's input parameter
	// (case-sensitive).
	data : JSON.stringify(state_request_dto),
	contentType : "application/json; charset=utf-8",
	dataType : "json",
	success : function(resultData) {

	    if (resultData.status == "REQUEST_OK") {
		setGlobalVars(resultData.response);
		if (g_current_word != null) {
		    var wining = checkWining();
		    if (wining == true) {
			clearInterval(g_interval);
			loadWordtoTextBoxes();
		    } else {
			if (g_round == g_player_round) {
			    clearInterval(g_interval);
			}

			setStatusText("guess");
			if (g_is_started == false) {
			    g_is_started = true;
			    setGreetingTxt();
			    loadWordTextBoxs();
			} else {
			    loadWordtoTextBoxes();
			}
		    }

		}
	    }

	    else if (resultData.status == "BAD_REQUEST") {// game is finished
		g_is_finished = true;
		setStatusText("finish");
		clearInterval(g_interval);
	    }

	},
	failure : function(errMsg) {
	    alert(errMsg);
	}
    });
}

function setGlobalVars(dto) {
    if (g_game_token == null)
	g_game_token = dto.gameToken;
    if (g_player_token == null)
	g_player_token = dto.playerToken;
    if (g_player_name == null)
	g_player_name = dto.playerName;

    g_current_word = dto.currentWord;
    g_round = dto.round;

    if (g_player_round == null)
	g_player_round = dto.playerRound;

    g_histories = dto.histories;
    g_word_desc = dto.wordDescription;
}

function setStatusText(status) {
    if (status == "waiting") {
	document.getElementById("waiting_text_id").innerHTML = "Dear "
		+ g_player_name + ", wait another player to join ... ";
    } else if (status == "finish") {
	if (g_is_started == false)
	    document.getElementById("waiting_text_id").innerHTML = "Sorry! Dear "
		    + g_player_name
		    + ", no one join to this game in specified time ... ";
	else
	    document.getElementById("waiting_text_id").innerHTML = "The game is finished";
    } else if (status == "guess") {
	document.getElementById("d_guess_id").style.display = "block";
	if (g_round == g_player_round) {
	    document.getElementById("btn_check_action_id").style.display = "block";
	    document.getElementById("waiting_text_id").innerHTML = "It's your turn to guess the word";
	} else {
	    document.getElementById("btn_check_action_id").style.display = "none";
	    document.getElementById("waiting_text_id").innerHTML = "It's your playmate turn to guess the word";
	}
    } else if (status == "wining") {
	document.getElementById("waiting_text_id").innerHTML = g_round
		+ " is win!";
	document.getElementById("btn_check_action_id").style.display = "none";
    } else {
	document.getElementById("waiting_text_id").innerHTML = status;

    }
}

function loadWordTextBoxs() {

    var wordLength = g_current_word.length;

    var container = document.getElementById("d_word_id");

    for (i = 0; i < wordLength; i++) {
	var input = document.createElement("input");
	input.type = "text";
	input.maxLength = 1;
	input.size = 2;
	input.id = "member" + i;
	container.appendChild(input);
	var space = document.createTextNode("\u00A0");
	container.appendChild(space);
    }

    loadWordtoTextBoxes();
}

function checkAction() {

    var input = "";
    var wordLength = g_current_word.length;

    for (i = 0; i < wordLength; i++) {
	var element = "member" + i;
	var val = document.getElementById(element).value;
	if (val == "") {
	    val = "_";
	}
	input = input + val;
    }

    var action_dto = {
	"gameToken" : g_game_token,
	"playerToken" : g_player_token,
	"currentWord" : input
    };

    $.ajax({
	type : "POST",
	url : "http://localhost:8080/guess-the-word/rest/game/checkAction/",
	// The key needs to match your method's input parameter
	// (case-sensitive).
	data : JSON.stringify(action_dto),
	contentType : "application/json; charset=utf-8",
	dataType : "json",
	success : function(resultData) {
	    if (resultData.status == "REQUEST_OK") {
		setGlobalVars(resultData.response);
		setStatusText("guess");
		loadWordtoTextBoxes();
		var wining = checkWining();
		if (wining == false) {
		    if (g_round != g_player_round)
			g_interval = setInterval(checkGameState, 2000);
		}
	    }
	    if (resultData.status == "BAD_REQUEST") {
		setStatusText(resultData.response);
		loadWordtoTextBoxes();
	    }

	},
	failure : function(errMsg) {
	    alert(errMsg);
	}
    });
}

function loadWordtoTextBoxes() {

    var wordLength = g_current_word.length;

    for (i = 0; i < wordLength; i++) {
	var element = "member" + i;
	if (g_current_word.charAt(i) != "_")
	    document.getElementById(element).value = g_current_word.charAt(i);
	else
	    document.getElementById(element).value = "";

    }

}

function checkWining() {

    var length = g_current_word.length;
    for (let i = 0; i < length; i++) {

	if (g_current_word.charAt(i) == "_")
	    return false;

    }
    setStatusText("wining");

    return true;

}

function setGreetingTxt() {
    if (g_is_started) {
	document.getElementById("greeting_text_id").innerHTML = "Hi "
		+ g_player_name + "! You are  " + g_player_round;
	document.getElementById("word_desc_id").innerHTML = "The word is a "
		+ g_word_desc;
	document.getElementById("d_greeting_id").style.display = "block";
    } else {
	document.getElementById("d_greeting_id").style.display = "none";

    }
}
