package com.abe.ChattAppli;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketException;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONArray;

@WebSocket
public class MySocket {
	static ArrayList<Session> sessions = new ArrayList<Session>();
	String dit = "dit -->";
	boolean first = true;
	Session session;

	static ConcurrentHashMap<InetSocketAddress, String> map = new ConcurrentHashMap<InetSocketAddress, String>();

	@OnWebSocketConnect
	public void onConnect(Session session) {
		first = true;
		System.out.println("Entered OnConnect" + session.getRemoteAddress());
		this.session = session;
		sessions.add(session);
		initialize(session);
	}

	private void initialize(Session session) {
		map.put(session.getRemoteAddress(), "NOT_SET_USERNAME");
	}

	@OnWebSocketMessage
	public void onMessage(Session session, String message) {

		try {
			if (first) {
				map.replace(session.getRemoteAddress(), message);
				first = false;
				MySocket.sendToAll(message + " is now online");
			}

			else {
				System.out.println("Received Message: '" + message + "' from :"
						+ MySocket.getUsernameByRemoteAddress(session.getRemoteAddress()));

				message = map.get(session.getRemoteAddress()) + dit + message;
				MySocket.sendToAll(message);
			} 
		} catch (WebSocketException e) {
			System.out.println( e.getMessage());
		}

	}

	@OnWebSocketError
	public void onError(Session session, Throwable error) throws Exception {
		System.out.println("Error from :" + MySocket.getUsernameByRemoteAddress(session.getRemoteAddress()));
		System.out.println(error.getMessage());
//		this.onClose(0, "an Error Occured - Internal Close");

	}

	@OnWebSocketClose
	public void onClose(int statusCode, String reason) {
		System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason);
		try {
			map.remove(session.getRemoteAddress());

		} catch (Exception e) {
			System.out.println("Debug the CLose Bruh!");
		}
	}

	private static void sendToAll(String message) {
		for (Session session : sessions) {
			try {
				session.getRemote().sendString(message);
				System.out.println("Message Sent to "+session);
			} catch (WebSocketException e) {
				System.out.println(session.getRemoteAddress() + e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static String getUsernameByRemoteAddress(InetSocketAddress adr) {
		return map.get(adr) + "@" + adr;
	}

	public JSONArray getUsers() {
		return (new JSONArray(Sessions.getUsernames()));
	}

}
