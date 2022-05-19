window.onload = function () {
    new Audio('audio/opener.wav').play();
};

window.beforeunload = function (e) {
    e.preventDefault();
    return confirm("Are you sure? The game will be lost.");
};

var width = 10;
var height = 10;
var roomId;

var FIELD_STATE = {
    WATER: "WATER",
    MISSED_SHOT: "MISSED_SHOT",
    ACCURATE_SHOT: "ACCURATE_SHOT",
    INTACT_SHIP: "INTACT_SHIP"
};

var EVENT = {
    CONNECT: "CONNECT",
    GAMEPLAY: "GAMEPLAY",
    SURRENDER: "SURRENDER"
};

function hideTableText() {
    if(width > 15 || height > 15) {
        document.getElementById("playerBoard").style.fontSize="0.8vh";
        document.getElementById("opponentBoard").style.fontSize="0.8vh";
    }
}

function createPlayerBoard() {
    var table = document.getElementById('playerBoard');

    for (var i = 0; i < height; i++) {
        var row = document.createElement('tr');
        for (var j = 0; j < width; j++) {
            var cell = document.createElement('td');
            cell.id = 'cell_' + (i * width + j);
            cell.innerHTML = (i * width + j);
            cell.className = "water";
            cell.onmouseenter = function () {
                //hoverMany(extractCellNumber(this), 4, "H");
            };
            cell.onmouseleave = function () {
                //unhoverMany(extractCellNumber(this), 4, "H");
            };
            row.append(cell);
        }
        table.append(row);
    }
}

function createOpponentBoard() {
    var table = document.getElementById('opponentBoard');

    for (var i = 0; i < height; i++) {
        var row = document.createElement('tr');
        for (var j = 0; j < width; j++) {
            var cell = document.createElement('td');
            cell.id = 'ocell_' + (i * width + j);
            cell.innerHTML = (i * width + j);
            cell.className = "water";
            cell.onclick = function (cell) {
                sendName(extractCellNumber(this));
            }
            row.append(cell);
        }
        table.append(row);
    }
}

function handleReturnedFieldType(returnedFieldType, cell, player) {
    var id = "cell_" + cell;
    if (player)
        id = "o" + id;
    switch (returnedFieldType) {
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
            document.getElementById(id).innerHTML = "&#128163";
            new Audio('audio/explosion.wav').play();
            break;
        case FIELD_STATE.INTACT_SHIP:
            changeDOMClassName(id, "intact");
            break;
    }
}
function handleShipShink(cells, keys, player) {
    for (let i = 0; i < keys.length; i++) {
        var id = "cell_" + keys[i];
        if (player)
            id = "o" + id;

        switch (cells[keys[i]]) {
            case FIELD_STATE.MISSED_SHOT:
                changeDOMClassName(id, "missed");
                break;
            case FIELD_STATE.ACCURATE_SHOT:
                changeDOMClassName(id, "accurate");
                document.getElementById(id).innerHTML = "&#128163";
                break;
        }
    }
    new Audio('audio/explosion.wav').play();
}

function extractCellNumber(cell) {
    if (cell.id.startsWith("ocell"))
        return cell.id.replace("ocell_", "");
    return cell.id.replace("cell_", "");
}

function badRequest() {
    new Audio('audio/alert.wav').play();
}

function hoverMany(startId, cellsCount, direction) {//todo make direction
    var id = "cell_" + startId;
    for (var i = 0; i < cellsCount; i++) {
        document.getElementById("cell_" + (parseInt(startId) + i)).className = 'intact';
    }
}

function unhoverMany(startId, cellsCount, direction) {//todo make direction
    var id = "cell_" + startId;
    for (var i = 0; i < cellsCount; i++) {
        var number = startId + i;
        document.getElementById("cell_" + (parseInt(startId) + i)).className = 'water';
    }
}

function winner() {
	new Audio('audio/win.wav').play();
	let page = document.getElementById("page");
	page.innerHTML = "<img src='https://c.tenor.com/D-pLzJqzkKcAAAAC/winner-wrestling.gif' alt='YOU WON!' />";
    page.className = "centered";
    changeGiveUpButtonToMenu();
}

function lose() {
    new Audio('audio/lose.wav').play();
    let page = document.getElementById("page");
    page.innerHTML = "<img src='https://media.giphy.com/media/l2Je3n9VXC8z3baTe/giphy.gif' alt='YOU LOST!' />";
    page.className = "centered";
    changeGiveUpButtonToMenu();
}

function changeGiveUpButtonToMenu() {
    let giveUpButton =  document.getElementById("giveUp")
    giveUpButton.innerText="MENU";
    giveUpButton.onclick=function() {window.location.replace("/welcome")};
}

function changeDOMClassName(elementId, className) {
    document.getElementById(elementId).className = className;
}

function connectUsers() {
    var socket = new SockJS('/shots-websocket');
    stompClient = Stomp.over(socket);
    stompClient.debug = null;

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        var url = stompClient.ws._transport.url;
        console.log("URL " + url);
        var splitUrl = url.split("/");
        url = splitUrl[splitUrl.length - 2];
        console.log("Your current session is: " + url);
        sessionId = url;

        stompClient.subscribe('/user/' + sessionId + '/queue/specific-user', function (msgOut) {
            readSubscribed(msgOut);
        });

        stompClient.send("/app/room", {
        }, JSON.stringify({
            "s": sessionId
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
        stompClient.send("/app/gameplay", {}, JSON.stringify({"cell":id, "roomId":roomId}));
    else {
        showStatus("NOT YOUR TURN!")
        setTimeout(() => { hideStatus(); }, 1000)
    }
}

function readSubscribed(message) {
    var response = JSON.parse(message.body);

    if (response.event == EVENT.CONNECT) {
        if (response.playerTwoSessionId != null)
            processConnectMessage(response);
    } else if (response.event == EVENT.GAMEPLAY) {
        processGameplayMessage(response)
    } else if(response.event == EVENT.SURRENDER) {
        processSurrenderMessage(response);
    }
}

var lastShootingPlayer;
var currentTurnPlayer;

function processConnectMessage(response) {
    roomId=response.roomId;
    //document.getElementById("playerSpan").innerText=(response.startingPlayerName + " starts");
    splitPlayerSpan(response.startingPlayerName + " starts");
    var myTurn = (sessionId==response.playerOneSessionId);

    lastShootingPlayer = response.playerOneSessionId;
    currentTurnPlayer = response.playerOneSessionId;

    if (sessionId == response.playerOneSessionId) {
        response.playerOneShipPositions.forEach(el => document.getElementById("cell_" + el).innerHTML = "&#128755;");
        document.getElementById("name").innerHTML = response.playerOneName;
        console.log(response.playerOneName);
    } else {
        response.playerTwoShipPositions.forEach(el => document.getElementById("cell_" + el).innerHTML = "&#128755;");
        document.getElementById("name").innerHTML = response.playerTwoName;
        console.log(response.playerTwoName);
    }

    highlightBoard(myTurn);
}

function splitPlayerSpan(text) {
    var split = "";
    for (var i = 0; i < text.length; i++) {
        split += "<span style='--i:" + i + "'>" + text.charAt(i) + "</span>";
    }
    document.getElementById("playerSpan").innerHTML=split;
}

function processGameplayMessage(response) {
    if (response.error != "") {
        console.error(response.error);
        badRequest();
        showStatus(response.error)
        setTimeout(() => { hideStatus(); }, 1000)
        return;
    }

    var cellArray = response.cells;
    var keys = Object.keys(cellArray);

    if (keys.length > 1) {
        handleShipShink(cellArray, keys, lastShootingPlayer == sessionId);
        logSunkedShip(response.shipCells, response.currentTurnPlayerName);
    } else {
        handleReturnedFieldType(cellArray[keys[0]], keys[0], lastShootingPlayer == sessionId);
        logMove(cellArray[keys[0]], response.currentTurnPlayerName, keys[0]);
    }

    lastShootingPlayer = response.currentTurnPlayer;
    currentTurnPlayer = response.currentTurnPlayer;
    //document.getElementById("playerSpan").innerHTML = "Now plays: " + response.currentTurnPlayerName;
    splitPlayerSpan("Now plays: " + response.currentTurnPlayerName);


    var myTurn = (sessionId == response.currentTurnPlayer);
    highlightBoard(myTurn);

    if (response.finished && sessionId == lastShootingPlayer) {
        winner();
    } else if (response.finished && sessionId != lastShootingPlayer) {
        lose();
    }
}

function processSurrenderMessage(response) {
    if (sessionId == response.surrenderPlayerSessionId) {
        lose();
        alert(response.surrenderMessage);
    } else {
        winner();
        alert(response.winnerMessage);
    }
    //setTimeout(() => { window.location.href='/logout' }, 3000)
}

function highlightBoard(currentPlayerTurn) {
    if (currentPlayerTurn) {
        document.getElementById("rightSide").className = "playerTurn";
    } else {
        document.getElementById("rightSide").className = "opponentTurn";
    }
}

function showStatus(message) {
    var status = document.getElementById("status");
    status.innerText = message;
    status.hidden = false;
}

function hideStatus() {
    document.getElementById("status").hidden = true;
}

var moveList = document.getElementById('loglist');
var coll = document.getElementsByClassName("collapsible");
var i;

for (i = 0; i < coll.length; i++) {
    coll[i].addEventListener("click", function () {
        this.classList.toggle("active");
        var content = this.nextElementSibling;
        if (content.style.maxHeight) {
            content.style.maxHeight = null;
        } else {
            content.style.maxHeight = content.scrollHeight + "px";
        }
    });
}

function logMove(fieldState, name, cell) {
    var entry = document.createElement('li');
    if (fieldState === "ACCURATE_SHOT") {
        entry.appendChild(document.createTextNode(getTime() + " Player " + name + " hit a ship on cell " + cell));
    } else {
        entry.appendChild(document.createTextNode(getTime() + " Player " + name + " missed a shot on cell " + cell));
    }
    moveList.insertAdjacentElement("afterbegin", entry);
}

function logSunkedShip(shipCells, name) {
    var entry = document.createElement('li');
    entry.appendChild(document.createTextNode(getTime() + " Player " + name + " sunk ship on cells [" + shipCells.join() + "]"));
    moveList.insertAdjacentElement("afterbegin", entry);
}

function getTime() {
    var timeStamp = new Date();
    var h = (timeStamp.getHours() < 10 ? '0' : '') + timeStamp.getHours();
    var m = (timeStamp.getMinutes() < 10 ? '0' : '') + timeStamp.getMinutes();
    var s = (timeStamp.getSeconds() < 10 ? '0' : '') + timeStamp.getSeconds();
    return h + ":" + m + ":" + s;
}

function giveUp() {
    var confirmGiveUp = confirm("Are you sure? The opponent will win.");
    if (confirmGiveUp) {
        stompClient.send("/app/gameplay/surrender", {}, JSON.stringify({"cell":-1, "roomId":roomId}));
    }
}

createPlayerBoard();
createOpponentBoard();
connectUsers();
hideStatus();
hideTableText();
