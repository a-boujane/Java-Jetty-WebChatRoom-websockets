package com.abe.ChattAppli;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

@Entity("users")
public class User {
	@Id
	private ObjectId _id;
	@Property
	private String fname;
	@Property
	private String lname;
	@Property
	private String uname;
	@Property
	private String email;
	@Property
	private String pic;
	@Property
	private boolean online;

//	public User() {
//	}
	
	public void what(){
	System.out.println(fname+lname+uname+email);
		
	}

}
