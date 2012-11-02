package com.example.twixster;

import android.app.Activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.json.*;

import android.os.Bundle;
import android.util.Log;
import android.widget.*;

//import com.loopj.android.http.*;


public class ListMoviesActivity extends Activity {

	ArrayList<movies> movielist = new ArrayList<movies>();
	BaseAdapter adapter = null;
	final String TAG = getClass().getName();
	private TextView status;
	
	public void updateView() {
		adapter.notifyDataSetChanged();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movies);
		String zip = getIntent().getExtras().getString("zip");
		status = (TextView) findViewById(R.id.progress);
		status.setText("Loading Movies...");
		Toast.makeText(this, "Search Movie near "+zip, Toast.LENGTH_LONG).show();

		final ListView lv=(ListView)findViewById(R.id.movielist);
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		adapter = new MoviesAdapter(this,R.layout.movielist,movielist);
		lv.setAdapter(adapter);

		//get movies
		//AsyncHttpClient client = new AsyncHttpClient();
		String url="http://cs-server.usc.edu:21090/RequestParams/RequestParams?zip="+zip;
		Log.e(TAG,"Trying "+url);
		
		try {
			URL serverurl = new URL(url);
			URLConnection connection = serverurl.openConnection();
			String line;
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			if(builder.toString().contains("NoMovies")) {
				status.setText("No Movie Showing. Press Back Button!");
			} else {
			String jSONString = builder.toString();
			JSONObject jSONObject = new JSONObject(jSONString);
			JSONObject movies=jSONObject.getJSONObject("movies");
			JSONArray movie=movies.getJSONArray("movie");
			for(int i=0; i<movie.length();i++) {
				JSONObject m = movie.getJSONObject(i);
				//listItems.add(m.getString("title"));
				movies movieItem = new movies();
				movieItem.setmovieTitle(m.getString("title"));
				movieItem.setmovieImg(m.getString("cover"));
				movieItem.setmovieSelect("NO");
				movieItem.setMovieTheater(m.getString("theater"));
				movieItem.setMovieTime(m.getString("showtime"));
				movieItem.setMovieLink(m.getString("url"));
				movielist.add(movieItem);
				//Toast.makeText(ListMoviesActivity.this, m.getString("title"), Toast.LENGTH_SHORT).show();
				System.out.println(m.getString("title"));
			}
			status.setVisibility(8);
			updateView();
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		/*adapter=new ArrayAdapter<String>(this,
        	    android.R.layout.simple_list_item_1,
        	    listItems);
        setListAdapter(adapter);*/ 


	} //onCreate ends

} //listactivity class ends
