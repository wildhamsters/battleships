class Match {
	constructor(matchId, accurateShots, missedShots, rounds, startTime, finishTime) {
		this.matchId = matchId;
		this.accurateShots = accurateShots;
		this.missedShots = missedShots;
		this.rounds = rounds;
		this.startTime = startTime;
		this.finishTime = finishTime;
	}
}

function loadStats(response) {
	var table = document.getElementById("stats");

	for (var i = 0; i < response.length; i++) {
		var row = document.createElement("tr");
		row.append(createElement("td", response[i].matchId));
		row.append(createElement("td", response[i].accurateShots));
		row.append(createElement("td", response[i].missedShots));
		row.append(createElement("td", response[i].rounds));
		row.append(createElement("td", response[i].startTime));
		row.append(createElement("td", response[i].finishTime));

		table.append(row);
	}
}

function createElement(type, content) {
	var el = document.createElement(type);
	el.innerText = content;
	return el;
}

function downloadStats() {
	var xhr = new XMLHttpRequest();
    xhr.open("GET", "/statistics");
    xhr.send(null);

    xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            loadStats(JSON.parse(xhr.responseText));
        }
    };
}

downloadStats();
