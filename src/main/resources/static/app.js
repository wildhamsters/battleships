window.onload = function() {
    new Audio('audio/opener.wav').play();
};

window.beforeunload = function (e) {
    e.preventDefault();
    return confirm("Are you sure? The game will be lost.");
};

var width = 10;
var height = 10;

var FIELD_STATE  = {
	WATER: "WATER",
	MISSED_SHOT: "MISSED_SHOT",
	ACCURATE_SHOT: "ACCURATE_SHOT",
	INTACT_SHIP: "INTACT_SHIP"
};

var EVENT = {
    CONNECT: "CONNECT",
    GAMEPLAY: "GAMEPLAY"
};

function createPlayerBoard() {
	var table = document.getElementById('playerBoard');

	for(var i=0; i<height; i++) {
		var row = document.createElement('tr');
			for(var j = 0; j<width; j++) {
				var cell = document.createElement('td');
				cell.id = 'cell_' + (i * width + j);
				cell.innerHTML=(i * width + j);
				cell.className="water";
				cell.onclick=function(cell) {
				    //sendName(extractCellNumber(this)); //todo disabled to disallow player's board clicking
				}
				cell.onmouseenter = function(){
                    //hoverMany(extractCellNumber(this), 4, "H");
                };
                cell.onmouseleave = function(){
                    //unhoverMany(extractCellNumber(this), 4, "H");
                };
				row.append(cell);
			}
			table.append(row);
	}
}

function createOpponentBoard() {
	var table = document.getElementById('opponentBoard');

	for(var i=0; i<height; i++) {
		var row = document.createElement('tr');
			for(var j = 0; j<width; j++) {
				var cell = document.createElement('td');
				cell.id = 'ocell_' + (i * width + j);
				cell.innerHTML=(i * width + j);
				cell.className="water";
				cell.onclick=function(cell) {
				    sendName(extractCellNumber(this));
				}
				row.append(cell);
			}
			table.append(row);
	}
}

function handleReturnedFieldType(returnedFieldType, cell, player) {
	var id = "cell_" +  cell;
	if(player)
	    id = "o" + id;
	switch(returnedFieldType) {
    	case FIELD_STATE.MISSED_SHOT:
    		changeDOMClassName(id, "missed");
    		new Audio('audio/water.wav').play();
    		break;
    	case FIELD_STATE.WATER:
    		changeDOMClassName(id, "water");
    		new Audio('audio/confirm.wav').play();
    		break;
    	case FIELD_STATE.ACCURATE_SHOT:
    		changeDOMClassName(id, "accurate");
    		document.getElementById(id).innerHTML="&#128163";
    		new Audio('audio/explosion.wav').play();
    		break;
    	case FIELD_STATE.INTACT_SHIP:
    		changeDOMClassName(id, "intact");
    		break;
    }
}

function extractCellNumber(cell) {
    if(cell.id.startsWith("ocell"))
        return cell.id.replace("ocell_", "");
	return cell.id.replace("cell_", "");
}

function badRequest() {
	new Audio('audio/alert.wav').play();
}

function hoverMany(startId, cellsCount, direction) {//todo make direction
    var id = "cell_" +  startId;
    for(var i=0; i<cellsCount; i++) {
        document.getElementById("cell_" + (parseInt(startId) + i)).className='intact';
    }
}

function unhoverMany(startId, cellsCount, direction) {//todo make direction
    var id = "cell_" +  startId;
    for(var i=0; i<cellsCount; i++) {
        var number = startId + i;
        document.getElementById("cell_" + (parseInt(startId) + i)).className='water';
    }
}

function winner() {
	new Audio('audio/win.wav').play();
	document.body.innerHTML = "<img src='https://c.tenor.com/D-pLzJqzkKcAAAAC/winner-wrestling.gif' alt='YOU WON!' />";
}

function lose() {
    new Audio('audio/lose.wav').play();
    document.body.innerHTML = "<img src='https://media.giphy.com/media/l2Je3n9VXC8z3baTe/giphy.gif' alt='YOU LOST!' />";
}

function changeDOMClassName(elementId, className) {
	document.getElementById(elementId).className = className;
}

function connectUsers() {
    var socket = new SockJS('/shots-websocket');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        var url = stompClient.ws._transport.url;
        console.log("URL " + url);
        url = url.replace(
          "ws://localhost:8080/shots-websocket/",  "");
        url = url.replace("/websocket", "");
        url = url.replace(/^[0-9]+\//, "");
        console.log("Your current session is: " + url);
        sessionId = url;

        stompClient.subscribe('/user/' + sessionId + '/queue/specific-user', function (msgOut) {
             console.log("RECEIVED FROM THE USER: " + sessionId + " " + msgOut);
             readSubscribed(msgOut);
        });

        stompClient.send("/app/room", {
        }, JSON.stringify({
            "s":sessionId
        }));
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function sendName(id) {
    if(sessionId==currentTurnPlayer)
        stompClient.send("/app/gameplay", {}, id);
    else {
        showStatus("NOT YOUR TURN!")
        setTimeout(() => {hideStatus();}, 1000)
    }
}

function readSubscribed(message) {
    console.log("received message body " + message.body);

    var response = JSON.parse(message.body);

    if(response.event==EVENT.CONNECT) {
        if(response.playerOneSessionId!=null)
            processConnectMessage(response);
    } else if (response.event==EVENT.GAMEPLAY) {
        processGameplayMessage(response)
    }
}

var lastShootingPlayer;
var currentTurnPlayer;

function processConnectMessage(response) {
    document.getElementById("playerSpan").innerText=(response.startingPlayerName + " starts");
    var myTurn = (sessionId==response.playerOneSessionId);

    lastShootingPlayer=response.playerOneSessionId;
    currentTurnPlayer=response.playerOneSessionId;

    if(sessionId==response.playerOneSessionId) {
        response.playerOneShipPositions.forEach(el => document.getElementById("cell_"+el).innerHTML="&#128755;");
    } else {
        response.playerTwoShipPositions.forEach(el => document.getElementById("cell_"+el).innerHTML="&#128755;");
    }

    highlightBoard(myTurn);
}

function processGameplayMessage(response) {
    if(response.error != "") {
        console.error(response.error);
        badRequest();
        showStatus(response.error)
        setTimeout(() => {hideStatus();}, 1000)
        return;
    }
    handleReturnedFieldType(response.updatedState, response.cell, lastShootingPlayer==sessionId);
    lastShootingPlayer=response.currentTurnPlayer;
    currentTurnPlayer=response.currentTurnPlayer;
    document.getElementById("playerSpan").innerHTML="Now plays: " + response.currentTurnPlayerName;

    logMove(response.updatedState, response.currentTurnPlayerName, response.cell);
    var myTurn = (sessionId==response.currentTurnPlayer);
    highlightBoard(myTurn);

    if (response.finished && sessionId==lastShootingPlayer) {
        winner();
    } else if (response.finished && sessionId!=lastShootingPlayer) {
        lose();
    }
}

function highlightBoard(currentPlayerTurn) {
    if(currentPlayerTurn) {
        document.getElementById("rightSide").className="playerTurn";
    } else {
        document.getElementById("rightSide").className="opponentTurn";
    }
}

function showStatus(message) {
    var status=document.getElementById("status");
    status.innerText=message;
    status.hidden=false;
}

function hideStatus() {
    document.getElementById("status").hidden=true;
}

var moveList = document.getElementById('loglist');
var coll = document.getElementsByClassName("collapsible");
var i;

for (i = 0; i < coll.length; i++) {
  coll[i].addEventListener("click", function() {
    this.classList.toggle("active");
    var content = this.nextElementSibling;
    if (content.style.maxHeight){
      content.style.maxHeight = null;
    } else {
      content.style.maxHeight = content.scrollHeight + "px";
    }
  });
}

function logMove(fieldState, name, cell) {
    var entry = document.createElement('li');
    if(fieldState === "ACCURATE_SHOT") {
        entry.appendChild(document.createTextNode("Player " + name + " hit a ship on cell " + cell));
    } else {
        entry.appendChild(document.createTextNode("Player " + name + " missed a shot on cell " + cell));
    }
    moveList.appendChild(entry);
}


createPlayerBoard();
createOpponentBoard();
connectUsers();
hideStatus();
