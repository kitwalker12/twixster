package com.example.twixster;

//import java.sql.Date;
import oauth.signpost.*;
import oauth.signpost.basic.*;
import oauth.signpost.commonshttp.*;
import twitter4j.*;
import twitter4j.auth.*;
import android.app.Activity;
import android.content.*;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;


public class LoginActivity extends Activity {

	private Button loginButton;
	private Button clearButton;
	private SharedPreferences mPrefs;
	private static final String PREF_ACCESS_TOKEN = "accessToken";
	private static final String PREF_ACCESS_TOKEN_SECRET = "accessTokenSecret";


	private Twitter twitter;
	private OAuthProvider provider;
	private CommonsHttpOAuthConsumer consumer;

	final String TAG = getClass().getName();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.e(TAG,"Entered OnCreate");
		loginButton = (Button) findViewById(R.id.loginButton);
		clearButton = (Button) findViewById(R.id.clearButton);
		

		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e(TAG,"Clicked Login");
				buttonLogin(v);
			}
		});

		clearButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clearLogin(v);
			}
		});
	}

	private void buttonLogin(View v) {
		mPrefs = getSharedPreferences("twitterPrefs", MODE_PRIVATE);
		// TODO Auto-generated method stub
		if (mPrefs.contains(PREF_ACCESS_TOKEN)) {
			String token = mPrefs.getString(PREF_ACCESS_TOKEN, null);
	        String secret = mPrefs.getString(PREF_ACCESS_TOKEN_SECRET, null);

	        AccessToken a = new AccessToken(token, secret);
	        twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
			twitter.setOAuthAccessToken(a);
			Toast.makeText(this, "Welcome Back!", Toast.LENGTH_SHORT).show();
			Intent myIntent = new Intent(getWindow().getDecorView().findViewById(android.R.id.content).getContext(),ZipActivity.class);
			startActivity(myIntent);
		}
		else {
			try {
				consumer = new CommonsHttpOAuthConsumer(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
				provider = new DefaultOAuthProvider("http://twitter.com/oauth/request_token",
						"http://twitter.com/oauth/access_token",
						"http://twitter.com/oauth/authorize");
				String authUrl = provider.retrieveRequestToken(consumer, Constants.OAUTH_CALLBACK_URL);
				Toast.makeText(this, "Please authorize this app first!", Toast.LENGTH_SHORT).show();
				Intent oauthIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl));
				oauthIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				oauthIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				oauthIntent.addFlags(Intent.FLAG_FROM_BACKGROUND);
				this.startActivity(oauthIntent);
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
			}
		}
	}

	private void clearLogin(View v) {
		// TODO Auto-generated method stub
		mPrefs = getSharedPreferences("twitterPrefs", MODE_PRIVATE);
		Editor editor = mPrefs.edit();
		editor.clear();
		editor.commit();
		Toast.makeText(this, "Credentials Deleted. Re-authorize app!", Toast.LENGTH_SHORT).show();

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		Uri uri = intent.getData();
		if (uri != null && uri.toString().startsWith(Constants.OAUTH_CALLBACK_URL)) {
			String verifier = uri.getQueryParameter(oauth.signpost.OAuth.OAUTH_VERIFIER);

			try {
				// this will populate token and token_secret in consumer
				provider.retrieveAccessToken(consumer, verifier);

				// TODO: you might want to store token and token_secret in you app settings!!!!!!!!
				AccessToken a = new AccessToken(consumer.getToken(), consumer.getTokenSecret());
				String token = a.getToken();
				String secret = a.getTokenSecret();
				Editor editor = mPrefs.edit();
				editor.putString(PREF_ACCESS_TOKEN, token);
				editor.putString(PREF_ACCESS_TOKEN_SECRET, secret);
				editor.commit();

				// initialize Twitter4J
				twitter = new TwitterFactory().getInstance();
				twitter.setOAuthConsumer(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
				twitter.setOAuthAccessToken(a);
				Toast.makeText(this, "Logged in to Twitter", Toast.LENGTH_LONG).show();
				Intent myIntent = new Intent(getWindow().getDecorView().findViewById(android.R.id.content).getContext(),ZipActivity.class);
				startActivity(myIntent);
				//				
				//				// create a tweet
				//				Date d = new Date(System.currentTimeMillis());
				//				String tweet = "@royalgoombah #OAuth working! " + d.toLocaleString();
				//
				//				// send the tweet
				//				twitter.updateStatus(tweet);
				//
				//				// feedback for the user...
				//				Toast.makeText(this, tweet, Toast.LENGTH_LONG).show();

			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
			}

		}
	}
}