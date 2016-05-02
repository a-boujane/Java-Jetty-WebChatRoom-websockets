package com.abe.ChattAppli;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ConcurrentModificationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketException;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class MySocket {
	static CopyOnWriteArrayList<Session> sessions = new CopyOnWriteArrayList<Session>();
	String dit = " >>>>> ";
	boolean first = true;
	Session session;

	static ConcurrentHashMap<InetSocketAddress, String> map = new ConcurrentHashMap<InetSocketAddress, String>();

	@OnWebSocketConnect
	public void onConnect(Session session) {
		try {
			first = true;
			System.out.println("Entered OnConnect" + session.getRemoteAddress());
			this.session = session;
			sessions.add(session);
			initialize(session);
		} catch (ConcurrentModificationException e) {
			System.out.println("Error OnConnect!!! ++++" +e.getMessage());		}
	}

	private void initialize(Session session) {
		map.put(session.getRemoteAddress(), "NOT_SET_USERNAME");
	}

	@OnWebSocketMessage
	public void onMessage(Session session, String message) {
		if (MySocket.isItKeepAlive(message)){
			try {
				this.session.getRemote().sendString(message);
				System.out.println("Got a keepAlive from "+session.getRemoteAddress());
			} catch (WebSocketException e) {
				// TODO Auto-generated catch block
				System.out.println(session.getRemoteAddress()+e.getMessage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println(session.getRemoteAddress()+e.getMessage());

			}
			}
		else{
		try {
			if (first) {
				map.replace(session.getRemoteAddress(), message);
				first = false;
				System.out.println("About to send this to all: "+ message + " is now online");
				MySocket.sendToAll(message + " is now online");
			}

			else {
				System.out.println("Received Message: " + message + " from :"
						+ MySocket.getUsernameByRemoteAddress(session.getRemoteAddress()));

				message = map.get(session.getRemoteAddress()) + dit + message;
				MySocket.sendToAll(message);
			} 
		} catch (WebSocketException e) {
			System.out.println( e.getMessage());
		}}

	}

	@OnWebSocketError
	public void onError(Session session, Throwable error) {
		try {
			System.out.println("Error from :" + MySocket.getUsernameByRemoteAddress(session.getRemoteAddress()));
			System.out.println(error.getMessage());
			//		this.onClose(0, "an Error Occurred - Internal Close");
		} catch (ConcurrentModificationException e) {
			System.out.println("Error!! ++++" +e.getMessage());
		}

	}

	@OnWebSocketClose
	public void onClose(int statusCode, String reason) {
		System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason+session.getRemoteAddress());
		try {
			map.remove(session.getRemoteAddress());

		} catch (ConcurrentModificationException e) {
			System.out.println("Debug the CLose Bruh!");
		}
	}

	private static void sendToAll(String message) {
		for (Session session : sessions) {
			try {
				session.getRemote().sendString(message);
				System.out.println("Message Sent to "+MySocket.getUsernameByRemoteAddress(session.getRemoteAddress()));
			}  catch (ConcurrentModificationException e) {
				System.out.println("COncurrentExceptionCaught " + session.getRemoteAddress() + e.getMessage());
				sessions.remove(session);}
			catch (WebSocketException e) {
				System.out.println(session.getRemoteAddress() + e.getMessage());
				sessions.remove(session);
			}
			 catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private static boolean isItKeepAlive(String g){
		return g.equals("***KeepAlive***");
	}

	private static String getUsernameByRemoteAddress(InetSocketAddress adr) {
		return map.get(adr) + "@" + adr;
	}

//	public JSONArray getUsers() {
//		return (new JSONArray(Sessions.getUsernames()));
//	}

}
