package com.abe.ChattAppli;

import java.net.UnknownHostException;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

public class DBConnection {

	public static void main(String[] args) throws UnknownHostException {

		MongoClient mongoClient = new MongoClient("10.0.0.24", 27017);

		Morphia morphia = new Morphia();
		Datastore ds = morphia.createDatastore(mongoClient, "chatapp");
		List<User> l = ds.find(User.class).asList();

		for (User u : l) {
			u.what();
		}
	}

}
