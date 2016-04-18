package com.abe.ChattAppli;

import java.util.ArrayList;

import org.eclipse.jetty.websocket.api.Session;

public class Sessions {
	private static ArrayList<SessionAndUsername> sessions = new ArrayList<SessionAndUsername>();
	private static ArrayList<String> userNames = new ArrayList<String>();

	public static void addSessionAndUsername(Session session, String username) {
		sessions.add(new SessionAndUsername(session, username));
	}

	public static void addSessionAndUsername(SessionAndUsername sau) {
		sessions.add(sau);
	}

	public static SessionAndUsername findSessionAndUsername(Session session) {
		for (SessionAndUsername sau : sessions) {
			if (sau.equals(session))
				return sau;
		}
		return null;
	}

	public static void deleteSessionAndUsername(Session session) {
		SessionAndUsername sau = findSessionAndUsername(session);
		sessions.remove(sau);
		userNames.remove(sau.getUsername());
	}

	public static ArrayList<String> getUsernames() {
		return userNames;
	}

	public static void addSessionAndUsername(Session session) {
		addSessionAndUsername(session, "default");
	}

}
