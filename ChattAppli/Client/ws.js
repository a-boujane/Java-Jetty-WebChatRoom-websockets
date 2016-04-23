var ws = new WebSocket("ws://10.0.0.17:8020/");

ws.onopen = function() {
	// alert("Opened!");
	var person = prompt("Please enter your name");
	ws.send(person);
	setInterval(function() {
		ws.send("***KeepAlive***");
	}, 20000);
};

ws.onmessage = function(evt) {
	if (evt.data !== "***KeepAlive***") {
		$("ol").append("<li>" + evt.data + "</li>");
	}
};

function sendMessage() {
	var msg = document.getElementById("message").value;
	if (msg !== "") {
		ws.send(msg);
		document.getElementById("message").value = "";
	}
}

function closeConnection() {
	ws.onclose = function() {
		alert("Closed!");
		ws.close();
	};
}

function onKeyDown(event) {
	if (event.keyCode == 13)
		sendMessage();
}