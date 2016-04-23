package com.abe.ChattAppli;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * Hello world!
 *
 * 
 */
public class MyServlet {
	public static void main(String[] args){
		try{
		Server server = new Server(8082);
		WebSocketHandler ws = new WebSocketHandler() {

			@Override
			public void configure(WebSocketServletFactory factory) {
				factory.register(MySocket.class);
			}

		};
		
		server.setHandler(ws);
		
		server.start();
		server.join();
		}
		
		catch(Exception e){
			System.out.println("Here is the Excapetion!!!!");
			System.out.println(e.getMessage());
		}

	}
}
