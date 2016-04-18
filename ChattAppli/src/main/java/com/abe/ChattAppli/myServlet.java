package com.abe.ChattAppli;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * Hello world!
 *
 * 
 */
public class myServlet {
	public static void main(String[] args) throws Exception {
		Server server = new Server(8088);
		WebSocketHandler ws = new WebSocketHandler() {

			@Override
			public void configure(WebSocketServletFactory factory) {
				factory.register(mySocket.class);
			}

		};
		
		server.setHandler(ws);
		
		server.start();
		server.join();
		

	}
}
