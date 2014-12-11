package com.ipw.bitmaptest;


import android.support.v4.util.LruCache;
import android.support.v7.app.ActionBarActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	
	private LruCache<String,Bitmap> memoryCache;
	private final String KEY ="com.ipw.bitmaptest.key";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ImageView imageView = (ImageView)findViewById(R.id.image_view);
		TextView textView_1 = (TextView)findViewById(R.id.text_view_1);
 		TextView textView_2 = (TextView)findViewById(R.id.text_view_2);
 		
 		TextView textView_5 = (TextView)findViewById(R.id.text_view_5);
 		
 		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
 		final int cacheSize = maxMemory / 4;
 		
 		memoryCache = new LruCache<String,Bitmap>(cacheSize);
 			
 			
 		
 		textView_5.setText(Integer.valueOf(maxMemory).toString() + " Kilobytes");
 		
 		BitmapFactory.Options options = new BitmapFactory.Options();
		
		options.inJustDecodeBounds = true;
		
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.military,options);
		
		int height = options.outHeight;
		int width = options.outWidth;
		
		
		textView_1.setText("Height: " + Integer.valueOf(height).toString());
		textView_2.setText("Weight: " + Integer.valueOf(width).toString());
		
		if(bitmap != null){
			int bitmapWidth = bitmap.getWidth();
			int bitmapHeight = bitmap.getHeight();
				
		}
		
		imageView.setAdjustViewBounds(true);
		imageView.setMaxHeight(100);
		imageView.setMaxWidth(100);
		
		int maxWidth = imageView.getMaxWidth();
		int maxHeight = imageView.getMaxHeight();
		
		int sampleSize = 1;
		
		if(height > imageView.getMaxHeight() || width > imageView.getMaxWidth()){
			
			final int halfHeight = height /2;
			final int halfWidth = width/2;
			
			while(halfHeight/sampleSize > maxHeight && halfWidth/sampleSize > maxWidth ){
				sampleSize *= 2;
			}
		}
		
		options.inSampleSize = sampleSize;
		
		
		options.inJustDecodeBounds = false;
		
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.military, options);
		
      	if(bitmap != null){
      		
      		imageView.setImageBitmap(bitmap);
      		
      		addBitmapToMemoryCache(KEY,bitmap);
      		
      		TextView textView_3 = (TextView)findViewById(R.id.text_view_3);
      		TextView textView_4 = (TextView)findViewById(R.id.text_view_4);
      		
      		textView_4.setText("Height: "+ Integer.valueOf(bitmap.getHeight()).toString());
      		textView_3.setText("Width: " + Integer.valueOf(bitmap.getWidth()).toString());
      		
      		ImageView imageView_3 = (ImageView) findViewById(R.id.image_view_3);
      	    imageView_3.setImageBitmap(getBitmapFromMemoryCache(KEY));
      		
      	}
	}
	
    public Bitmap getBitmapFromMemoryCache(String key){
		
	    return memoryCache.get(key);
		
	}
	
	public void addBitmapToMemoryCache(String key, Bitmap bitmap){
		
		if(getBitmapFromMemoryCache(key) == null){
			
			memoryCache.put(key, bitmap);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
}
