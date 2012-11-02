package com.example.twixster;

//import twitter4j.TwitterException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class ZipActivity extends Activity {
	/** Called when the activity is first created. */
	private Button getMovies;
	private EditText zipCode;
	private Button back;
	final String TAG = getClass().getName();
	private String zip;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zip);
		getMovies = (Button) findViewById(R.id.getMovies);
		zipCode  = (EditText) findViewById(R.id.zipCode);
		back=(Button)findViewById(R.id.button1);

		getMovies.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e(TAG,"Clicked Get Movies");
				zip = zipCode.getText().toString();
				if(zip.length()!=5 || !zip.matches("\\d+"))
				{
					zipCode.setText("");
					Toast.makeText(ZipActivity.this, "Zipcode must have 5 digits. Please, enter a correct ZipCode.", Toast.LENGTH_SHORT).show();
				} else {
					getMovies(v);
				}
				//Intent myIntent = new Intent(v.getContext(),ZipActivity.class);
				//startActivityForResult(myIntent, 0);
			}
		});
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(v.getContext(),LoginActivity.class);
				startActivity(myIntent);
			}
		});
	}

	public void getMovies(View v) {
		Intent myIntent = new Intent(v.getContext(),ListMoviesActivity.class);
		myIntent.putExtra("zip", zip);
		startActivity(myIntent);
	}


}
