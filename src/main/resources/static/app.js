window.onload = function() {
    var audio = new Audio('audio/opener.wav');
	audio.play();
	clearDemo();
};

var width = 10;
var height = 10;

var FIELD_STATE  = {
	WATER: "WATER",
	MISSED_SHOT: "MISSED_SHOT",
	ACCURATE_SHOT: "ACCURATE_SHOT",
	INTACT_SHIP: "INTACT_SHIP"
};

var statusMessageId = 1;

function createPlayerBoard() { //todo change
	var table = document.getElementById('playerBoard');

	for(var i=0; i<height; i++) {
		var row = document.createElement('tr');
			for(var j = 0; j<width; j++) {
				var cell = document.createElement('td');
				cell.id = 'cell_' + (i * width + j);
				cell.innerHTML=(i * width + j);
				cell.className="water";
				cell.onclick=function(cell) {
					var xmlHttp = new XMLHttpRequest();
				    xmlHttp.onreadystatechange = function() {

				        if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
				            var response = JSON.parse(xmlHttp.responseText);

				            handleReturnedFieldType(response.u, cell.target.id);

				            if (response.t) {
				            	winner();
				            }
				        } else if (xmlHttp.readyState == 4 && xmlHttp.status == 400) {
				        	badRequest();
				        }
				    };
				    sendName(extractCellNumber(this));
				    document.getElementById("status").insertAdjacentHTML("afterbegin", "<p>" + statusMessageId++ + ". Shooted at " + extractCellNumber(this) + " </p>");
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

function createOpponentBoard() { //todo change
	var table = document.getElementById('opponentBoard');

	for(var i=0; i<height; i++) {
		var row = document.createElement('tr');
			for(var j = 0; j<width; j++) {
				var cell = document.createElement('td');
				cell.id = 'cell_' + (i * width + j);
				cell.innerHTML=(i * width + j);
				cell.className="water";
				cell.onclick=function(cell) {
					var xmlHttp = new XMLHttpRequest();
				    xmlHttp.onreadystatechange = function() {

				        if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
				            var response = JSON.parse(xmlHttp.responseText);

				            handleReturnedFieldType(response.u, cell.target.id);

				            if (response.t) {
				            	winner();
				            }
				        } else if (xmlHttp.readyState == 4 && xmlHttp.status == 400) {
				        	badRequest();
				        }
				    };
				    sendName(extractCellNumber(this));
				    document.getElementById("status").insertAdjacentHTML("afterbegin", "<p>" + statusMessageId++ + ". Shooted at " + extractCellNumber(this) + " </p>");
				}
				row.append(cell);
			}
			table.append(row);
	}
}

function handleReturnedFieldType(returnedFieldType, cell) {
	var id = "cell_" +  cell;
	switch(returnedFieldType) {
    	case FIELD_STATE.MISSED_SHOT:
    		changeDOMClassName(id, "missed");
    		new Audio('audio/confirm.wav').play();
    		break;
    	case FIELD_STATE.WATER:
    		changeDOMClassName(id, "water");
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
	return cell.id.replace("cell_", "")
}

function badRequest() {
	document.getElementById("status").insertAdjacentHTML("afterbegin", "<p style='color: red;'>" + statusMessageId++ + ". Bad request! </p>");
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
	document.body.innerHTML = "<img src='https://c.tenor.com/D-pLzJqzkKcAAAAC/winner-wrestling.gif' />";
}

function changeDOMClassName(elementId, className) {
	document.getElementById(elementId).className = className;
}

function clearDemo() {
	var xmlHttp = new XMLHttpRequest();
	xmlHttp.open("GET", 'http://localhost:8080/clear/', true);
	xmlHttp.send(null);

	for (var i = 0; i < 25; i++) {
		var cellId = "cell_" + i;
		changeDOMClassName(cellId, "water");
		document.getElementById(cellId).innerHTML = i;
	}

	document.getElementById("status").innerHTML = "";
}

function demo() {
	var arr = random(0, 25, 5);
	for (var i = 0; i < 5; i++) {
		document.getElementById('cell_' + arr[i]).click();
	}
}

function random(min, max, numOfElements) {
	var arr = shuffle(Array.from(Array(max).keys()));
	return arr.slice(0, numOfElements);
}

function shuffle(array) {
	var i = array.length,
		j = 0,
		temp;

	while (i--) {
		j = Math.floor(Math.random() * (i + 1));
		temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	return array;
}

// websockets

function connect() {
    var socket = new SockJS('/shots-websocket');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        var url = stompClient.ws._transport.url;
        console.log("URL " + url);
        url = url.replace(
          "ws://localhost:8080/app/room/",  "");
        url = url.replace("/websocket", "");
        url = url.replace(/^[0-9]+\//, "");
        console.log("Your current session is: " + url);
        sessionId = url;


        stompClient.subscribe('/shots/list', function (item) {
            readSubscribed(item);
        });

        stompClient.subscribe('/user/queue/specific-user'
              + '-user' + sessionId, function (msgOut) {
                 console.log("RECEIVED FROM THE USER: " + sessionId + " " + msgOut);
            });
    });
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
    stompClient.send("/app/action", {}, id);
}

function readSubscribed(message) {
    console.log(message.body);
    var response = JSON.parse(message.body);
    if(response.error != "") {
        console.error(response.error);
        return;
    }
    handleReturnedFieldType(response.updatedState, response.cell);
    if (response.finished) {
    	winner();
    }
}

createPlayerBoard();
createOpponentBoard()
//connect();
connectUsers();
