package com.aptdemo.yzhao.androiddemo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResults extends ActionBarActivity {

    public final static String EXTRA_STREAM_NAME = "com.aptdemo.yzhao.androiddemo.STREAM_NAME";
    public final static String EXTRA_SEARCH_LOGIN_STATUS = "com.aptdemo.yzhao.androiddemo.SEARCH_LOGIN_STATUS";
    public final static String EXTRA_SEARCH_URLS = "com.aptdemo.yzhao.androiddemo.SEARCH_URLS";

    private static final String TAG = "android-demo-login";

    Context context = this;

    String loginStatSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        // Get the message from the intent
//        Intent intent = getIntent();
//        String gotUrl = intent.getStringExtra(Homepage.EXTRA_URL);
//
//        Log.d("got_url",gotUrl);
//
//        // Create the text view
//        TextView textView = new TextView(this);
//        textView.setTextSize(40);
//        textView.setText(gotUrl);
//
//        setContentView(textView);

        //Get the message from the intent
        Intent intent = getIntent();
        String sRes = intent.getStringExtra(DisplayStreams.EXTRA_SEARCH_RESULT);
        String sQuery = intent.getStringExtra(DisplayStreams.EXTRA_SEARCH_STRING);
        loginStatSearch = intent.getStringExtra(DisplayStreams.EXTRA_STREAM_LOGIN_STATUS);
        String listSize = intent.getStringExtra("listSize");




        TextView textView = (TextView) findViewById(R.id.showSearchQuery);

        if (listSize.equals("0"))
        {
            textView.setText("Sorry, we did not find any streams matching your query!");
        }
        else
        {
            textView.setText("Streams matching query: "+sQuery+" are:"+sRes+"!");
        }
        //textView.setText(sRes+"-"+sQuery);
        Log.d("text_recv", sRes+"-"+sQuery);
        //setContentView(textView);
        String[] streamNamesRecv = sRes.split(";");
        // Get back JSON
        //JSONObject jsonObj = new JSONObject(getIntent().getStringExtra("product"));

                final ArrayList<String> imageURLs = new ArrayList<String>();
                final ArrayList<String> imageCaps = new ArrayList<String>();
                try {
                    final JSONObject jObject = new JSONObject(getIntent().getStringExtra("sendJson"));
                    JSONArray displayImages = jObject.getJSONArray("coverUrls");
                    JSONArray displayCaption = jObject.getJSONArray("streamNames");

                    //imageUrls

                    for(int i=0;i<displayImages.length();i++) {

                        for (int j=0; j<streamNamesRecv.length; j++)
                        {
                            if ( !streamNamesRecv[j].equals("") && displayCaption.getString(i).equals(streamNamesRecv[j]) )
                            {
                                Log.d("streamSearchFound",displayCaption.getString(i));

                                if (displayImages.getString(i).equals("") )
                                {
                                    imageURLs.add("http://testp3-1106.appspot.com//static_images/noCover.png");
                                    imageCaps.add(displayCaption.getString(i));
        //                            imageCaps.add("http://testp3-1106.appspot.com//static_images/noCover.png");
                                    System.out.println("http://testp3-1106.appspot.com//static_images/noCover.png");
                                }
                                else
                                {
                                    imageURLs.add(displayImages.getString(i));
                                    imageCaps.add(displayCaption.getString(i));
                                    System.out.println(displayImages.getString(i));
                                }


                            }

                        }

                    }
                    GridView gridview = (GridView) findViewById(R.id.gridviewSearchResults);
                    System.out.println("before SetAdapter");
                    gridview.setAdapter(new ImageAdapter(context,imageURLs));
                    gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View v,
                                                int position, long id) {

                            Toast.makeText(context, imageCaps.get(position), Toast.LENGTH_SHORT).show();
                            System.out.println("after toast");
                            Dialog imageDialog = new Dialog(context);
                            imageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            imageDialog.setContentView(R.layout.thumbnail);
                            System.out.println("before ImageView");
                            ImageView image = (ImageView) imageDialog.findViewById(R.id.thumbnail_IMAGEVIEW);

                            Log.d("debuggingImageURLS", imageURLs.get(position));
                            Picasso.with(context).load(imageURLs.get(position)).into(image);

                            imageDialog.show();
                            try {
                                //JSONArray displayCaption = jObject.getJSONArray(imageCaps.get(position));
                                // START OF BLOCK TEST CODE
                                JSONObject streamObj = jObject.getJSONObject(imageCaps.get(position));
                                JSONArray streamImageUrls = streamObj.getJSONArray("imageUrls");
                                ArrayList<String> listImageUrls = new ArrayList<String>();


                                String allImageUrls = "";
                                for (int i = 0; i < streamImageUrls.length(); i++) {
                                    if (streamImageUrls.getString(i).equals("")) {
                                        listImageUrls.add("The stream has no images!");
                                    } else {
                                        listImageUrls.add(streamImageUrls.getString(i));
                                        System.out.println(streamImageUrls.getString(i));
                                    }
                                    allImageUrls = allImageUrls + "," + streamImageUrls.getString(i);
                                }
                                // END OF BLOCK TEST CODE


                                Intent intent = new Intent(context, ViewSingleStream.class);

                                String streamName = imageCaps.get(position);
                                intent.putExtra(EXTRA_SEARCH_URLS, allImageUrls);
                                intent.putExtra(EXTRA_STREAM_NAME,streamName);
                                startActivity(intent);
                            }
                            catch(JSONException j){
                                System.out.println("JSON Error");
                            }
                        }
                    });
                }
                catch(JSONException j){
                    System.out.println("JSON Error");
                }

            }


    public void GoHome(View view){
        Intent intent= new Intent(this, DisplayStreams.class);
        intent.putExtra(EXTRA_SEARCH_LOGIN_STATUS,loginStatSearch);
        startActivity(intent);

    }






}


