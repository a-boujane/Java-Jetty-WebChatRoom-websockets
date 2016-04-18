package com.abe.ChattAppli;

import org.eclipse.jetty.websocket.api.Session;

public class SessionAndUsername {
	private Session session;
	private String username;
	
	public SessionAndUsername(Session session, String username) {
		this.session = session;
		this.username = username;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
