package com.abe.ChattAppli;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONArray;

@WebSocket
public class mySocket {
	SessionAndUsername sau;

	@OnWebSocketConnect
	public void onConnect(Session session) {
		this.sau = new SessionAndUsername(session, "default");
		initialize();
	}

	private void initialize() {
		Sessions.addSessionAndUsername(sau);
	}

	@OnWebSocketMessage
	public void onMessage(Session session, String message) throws IOException {
		Sessions.sendToAllSessions(message);
	}

	@OnWebSocketError
	public void onError(Session session, Throwable error) {
		System.out.println(error.getMessage());
	}

	@OnWebSocketClose
	public void onClose(int statusCode, String reason) {
        System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason);
		this.sau.getSession().close();
		Sessions.deleteSessionAndUsername(sau.getSession());
	}
		public void setUserName(String userName) {

		return;
	}

	public String setPassword(String password) {
		// TODO Auto-generated method stub
		return null;
	}

	public JSONArray getUsers() {
		return (new JSONArray(Sessions.getUsernames()));
	}

}
