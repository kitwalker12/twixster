package com.example.twixster;

public class Constants {

	public static final String CONSUMER_KEY = "FKIw97K1DSXNqcRAopYpVw";
	public static final String CONSUMER_SECRET= "OXjTIaMr9hGEdk3JknR37SyHNI5K5W560w6X47hI";

	public static final String REQUEST_URL = "http://api.twitter.com/oauth/request_token";
	public static final String ACCESS_URL = "http://api.twitter.com/oauth/access_token";
	public static final String AUTHORIZE_URL = "http://api.twitter.com/oauth/authorize";

	public static final String	OAUTH_CALLBACK_SCHEME	= "Twixster";
	public static final String	OAUTH_CALLBACK_HOST		= "callback";
	public static final String	OAUTH_CALLBACK_URL		= OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;
}
