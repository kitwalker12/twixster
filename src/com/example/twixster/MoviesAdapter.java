package com.example.twixster;

import java.io.*;
import java.net.*;
import java.util.*;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;
import twitter4j.*;
import twitter4j.auth.*;

public class MoviesAdapter extends ArrayAdapter<movies> {

	private static ArrayList<movies> movielist;
	private LayoutInflater mInflater;
	private final int MovieLayoutResource;
	private int mSelectedPosition = -1;
	private RadioButton mSelectedRB;
	private SharedPreferences mPrefs;
	private static final String PREF_ACCESS_TOKEN = "accessToken";
	private static final String PREF_ACCESS_TOKEN_SECRET = "accessTokenSecret";
	private Twitter twitter;
	private Context context;

	public MoviesAdapter(final Context context, final int MovieLayoutResource, ArrayList<movies> objects) {
		super(context, 0);
		this.context=context;
		this.MovieLayoutResource = MovieLayoutResource;
		movielist=objects;
		mInflater=LayoutInflater.from(context);
		System.out.println("Inside Constructor");
		
		mPrefs = getContext().getSharedPreferences("twitterPrefs", Context.MODE_PRIVATE);
		String token = mPrefs.getString(PREF_ACCESS_TOKEN, null);
        String secret = mPrefs.getString(PREF_ACCESS_TOKEN_SECRET, null);

        AccessToken a = new AccessToken(token, secret);
        twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
		twitter.setOAuthAccessToken(a);
	}

	@Override
	public int getCount() {
		return movielist.size();
	}

	@Override
	public movies getItem(int position) {
		return movielist.get(position);
	}

	@Override
	public long getItemId(int position) {	
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		System.out.println("Inside GetView");
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(this.MovieLayoutResource, null);
			holder = new ViewHolder();
			holder.movieTitle = (TextView) convertView.findViewById(R.id.movieTitle);
			holder.movieImg = (ImageView) convertView.findViewById(R.id.movieImg);
			holder.movieSelect = (RadioButton) convertView.findViewById(R.id.movieSelect);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.movieSelect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if(position != mSelectedPosition && mSelectedRB != null){
					mSelectedRB.setChecked(false);
				}

				mSelectedPosition = position;
				mSelectedRB = (RadioButton)v;
				String title=movielist.get(position).getmovieTitle();
				String theatre=movielist.get(position).getMovieTheater();
				String time=movielist.get(position).getMovieTime().substring(0, 8);
				String url=movielist.get(position).getMovieLink();
				String tweet="I will see \""+title+"\" at "+theatre+" at "+time+". Link: "+url;
				Log.e("Adapter",tweet);
				try {
					twitter.updateStatus(tweet);
					Toast.makeText(context, "Tweet Successful!", Toast.LENGTH_SHORT).show();
				} catch (TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		if(mSelectedPosition != position){
			holder.movieSelect.setChecked(false);
		}else{
			holder.movieSelect.setChecked(true);
			if(mSelectedRB != null && holder.movieSelect != mSelectedRB){
				mSelectedRB = holder.movieSelect;
			}
		}

		holder.movieTitle.setText(movielist.get(position).getmovieTitle());
		/*String radioState = movielist.get(position).getmovieSelect();
		if(radioState.equals("YES")) {
			holder.movieSelect.setChecked(true);
		}
		else {
			holder.movieSelect.setChecked(false);
		}*/

		System.out.println("add movie");
		System.out.println(movielist.get(position).getmovieTitle());
		System.out.println(movielist.get(position).getmovieImg());
		try {
			URL url=null;
			url = new URL(movielist.get(position).getmovieImg());
			Object content=null;
			content=url.getContent();
			InputStream is = (InputStream)content;
			Drawable d = Drawable.createFromStream(is, "src");
			holder.movieImg.setImageDrawable(d);
		}
		catch(MalformedURLException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return convertView;
	}// get view ends


	static class ViewHolder {
		TextView movieTitle;
		ImageView movieImg;
		RadioButton movieSelect;
	}

}
