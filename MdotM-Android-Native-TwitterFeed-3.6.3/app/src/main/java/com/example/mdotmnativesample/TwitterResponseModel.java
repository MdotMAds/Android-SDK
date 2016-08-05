package com.example.mdotmnativesample;

import java.io.Serializable;
import java.util.ArrayList;

public class TwitterResponseModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<Feeds> statuses=new ArrayList<Feeds>();
	public class Feeds implements Serializable{
		User user;
		public class User implements Serializable{
			String name;
			String description;
			String profile_image_url_https;
			String url;
		}	
	}
	

}
