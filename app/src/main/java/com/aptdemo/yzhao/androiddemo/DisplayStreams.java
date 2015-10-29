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
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DisplayStreams extends ActionBarActivity {

    Context context = this;
    private String TAG  = "Display Streams";

    public final static String EXTRA_STREAM_NAME = "com.aptdemo.yzhao.androiddemo.STREAM_NAME";
    public final static String EXTRA_SEARCH_STRING = "com.aptdemo.yzhao.androiddemo.SEARCH_STRING";
    public final static String EXTRA_SEARCH_RESULT = "com.aptdemo.yzhao.androiddemo.SEARCH_RESULT";
    public final static String EXTRA_STREAM_URLS = "com.aptdemo.yzhao.androiddemo.STREAM_URLS";
    public final static String EXTRA_STREAM_LOGIN_STATUS = "com.aptdemo.yzhao.androiddemo.STREAM_LOGIN_STATUS";
    public final static String EXTRA_STREAM_OWNER_LOGGED_IN = "com.aptdemo.yzhao.androiddemo.STREAM_OWNER_LOGGED_IN";

    public static String searchResults;
    public static JSONObject sendJson;

    public Button subscribedButton;

    public static String loginStat;
    public static String request_url = "http://testp3-1106.appspot.com/dumpAllStreams";
    public static boolean subscribedPressed = false;
    public static String ownerLoggedIn = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_streams);

        Intent intent = getIntent();
        loginStat = intent.getStringExtra(Homepage.EXTRA_LOGIN_STATUS);

        ownerLoggedIn = "false";
        // If loginStatus is not coming from HomePage, then capture from search
        if (loginStat == null)
        {
            loginStat = intent.getStringExtra(SearchResults.EXTRA_SEARCH_LOGIN_STATUS);

            if (loginStat == null){
                loginStat=intent.getStringExtra(ViewSingleStream.EXTRA_LOGIN_STATUS_SINGLE);
            }
            ownerLoggedIn = "false";
        }

        else
        {
            request_url = "http://testp3-1106.appspot.com/dumpAllStreams";
            subscribedPressed = false;
            ownerLoggedIn = "false";


        }

        subscribedButton = (Button) findViewById(R.id.subscribed_button);

        if (!subscribedPressed) {


            if (loginStat != null && loginStat.equals("false")) {
                subscribedButton.setVisibility(View.INVISIBLE);

            } else {
                subscribedButton.setVisibility(View.VISIBLE);
            }

        }

        else
        {
            subscribedButton.setVisibility(View.VISIBLE);
            subscribedButton.setEnabled(false);
        }

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



        //final String request_url = "http://testp3-1106.appspot.com/dumpAllStreams";
        //NoCover URL: http://testp3-1106.appspot.com//static_images/noCover.png
        AsyncHttpClient httpClient = new AsyncHttpClient();
        httpClient.get(request_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                final ArrayList<String> imageURLs = new ArrayList<String>();
                final ArrayList<String> imageCaps = new ArrayList<String>();
                try {
                    final JSONObject jObject = new JSONObject(new String(response));
                    JSONArray displayImages,displayCaption;


                    if (!subscribedPressed) {
                        displayImages = jObject.getJSONArray("coverUrls");
                        displayCaption = jObject.getJSONArray("streamNames");
                    }
                    else
                    {
                        displayImages = jObject.getJSONArray("subscribedImagesUrls");
                        displayCaption = jObject.getJSONArray("subscribedStreamList");
                    }
                    sendJson = jObject;
                    searchResults="";
                    Log.d("search_results", searchResults);



                    Log.d("search_results", searchResults);

                    for (int i=0; i<displayCaption.length();i++)
                    {
                        Log.d("stream_name1",displayCaption.getString(i));
                        JSONObject streamSearch = jObject.getJSONObject(displayCaption.getString(i));
                        Log.d("stream_name1",streamSearch.getString("searchData"));
                        String searchData = streamSearch.getString("searchData");
                        searchResults = searchResults + displayCaption.getString(i) + ":" + searchData + ";";
                        Log.d("search_results", searchResults);
                    }

                    //imageUrls

                    for(int i=0;i<displayImages.length();i++) {
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
                    GridView gridview = (GridView) findViewById(R.id.gridviewStreams);
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

                            Log.d("debuggingImageURLS",imageURLs.get(position));
                            Picasso.with(context).load(imageURLs.get(position)).into(image);

                            imageDialog.show();
                            try {
                                //JSONArray displayCaption = jObject.getJSONArray(imageCaps.get(position));
                                // START OF BLOCK TEST CODE
                                JSONObject streamObj = jObject.getJSONObject(imageCaps.get(position));
                                JSONArray streamImageUrls = streamObj.getJSONArray("imageUrls");
                                ArrayList<String> listImageUrls = new ArrayList<String>();

                                // Getting Owner of Stream to check stuff
                                String streamOwner = streamObj.getString("owner");
                                Log.d("stream_owner",streamOwner);

                                String usersEmail="";
                                if (loginStat != null && !loginStat.equals("false"))
                                {
                                    String[] userStat = loginStat.split(",");
                                    usersEmail = userStat[1];
                                }
                                else
                                {
                                    if (loginStat == null) {
                                        Log.d("loginStatIssues", "null!");
                                    }
                                    else{
                                        Log.d("loginStatIssues", loginStat);

                                    }
                                }

                                if (!usersEmail.equals("") && streamOwner.toLowerCase().contains("@") )
                                {
                                    if (usersEmail.equals(streamOwner) )
                                    {
                                        ownerLoggedIn="true";
                                        Log.d("streamOwn",streamOwner);
                                        Log.d("usersEmail",usersEmail);
                                    }

                                }
                                else if (!usersEmail.equals("") && !streamOwner.toLowerCase().contains("@") )
                                {
                                    if (usersEmail.equals(streamOwner+"@gmail.com") )
                                    {
                                        ownerLoggedIn="true";
                                        Log.d("streamOwn",streamOwner);
                                        Log.d("usersEmail",usersEmail);
                                    }
                                }
                                else{
                                    ownerLoggedIn="false";
                                    Log.d("streamOwnF",streamOwner);
                                    Log.d("usersEmailF",usersEmail);
                                }




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

                                Log.d("stream_name",imageCaps.get(position));
                                Intent intent = new Intent(context, ViewSingleStream.class);

                                String allStreamsUrl = imageCaps.get(position);
                                intent.putExtra(EXTRA_STREAM_URLS, allImageUrls);
                                intent.putExtra(EXTRA_STREAM_NAME, imageCaps.get(position));
                                intent.putExtra(EXTRA_STREAM_OWNER_LOGGED_IN, ownerLoggedIn);
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

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                Log.e(TAG, "There was a problem in retrieving the url : " + e.toString());
                Toast.makeText(context, "Error:"+e.toString()+"!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchStreams(View view){
        Intent intent= new Intent(this, SearchResults.class);
        EditText text = (EditText) findViewById(R.id.search_box);
        String searchQuery = text.getText().toString();
        searchQuery = searchQuery.toLowerCase();
        String allSearchDb = searchResults;
        allSearchDb = allSearchDb.toLowerCase();

        String[] searchTags = allSearchDb.split(";");
        Log.d("search_query", searchQuery);
        String foundStreamNames="";
        ArrayList<String> streamNamesFound = new ArrayList<String>();

        for (int i=0; i<searchTags.length; i++)
        {
            if (!searchTags[i].equals(""))
            {
                Log.d("searchTags",searchTags[i]);
                String[] names = searchTags[i].split(":");
                String streamName = names[0];
                Log.d("streamNameAfterSplit",streamName);
                Log.d("reststuff",names[1]);
                String[] allTagsWithoutName = names[1].split(",");
                String[] allTags = new String[allTagsWithoutName.length+1];


                for (int k=0; k<allTagsWithoutName.length; k++ )
                {
                    allTags[k] = allTagsWithoutName[k];
                }

                allTags[allTagsWithoutName.length] = streamName;


                boolean foundStream=false;
                for (int j=0; j< allTags.length; j++)
                {

                    if (!allTags[j].equals(""))
                    {
                        if (allTags[j].equals(searchQuery))
                        {
                            Log.d("stream_found", streamName);
                            foundStream=true;
                            break;
                        }
                    }

                }
                if (foundStream)
                {
                    foundStreamNames = foundStreamNames + ";" + streamName;
                    streamNamesFound.add(streamName);

                    foundStream=false;

                }


            }

        }

        String sendSize=""+streamNamesFound.size()+"";
        Log.d("sendSize", sendSize);

        intent.putExtra(EXTRA_SEARCH_STRING, searchQuery);
        intent.putExtra(EXTRA_SEARCH_RESULT,foundStreamNames);
        intent.putExtra(EXTRA_STREAM_LOGIN_STATUS,loginStat);
        intent.putExtra("listSize",sendSize);
        intent.putExtra("sendJson",sendJson.toString());
        startActivity(intent);
    }

    public void subscribedStreams(View view){
        subscribedPressed = true;
        Log.d("subscribedEmail", loginStat);
        //Intent intent= new Intent(this, SubscribedStreams.class);
        //intent.putExtra(EXTRA_STREAM_LOGIN_STATUS,loginStat);

        subscribedButton = (Button) findViewById(R.id.subscribed_button);
        subscribedButton.setVisibility(View.VISIBLE);
        subscribedButton.setEnabled(false);

        String[] userStat = loginStat.split(",");
        String userEmail = userStat[1];

        //startActivity(intent);
        String base_url="http://testp3-1106.appspot.com/dumpSubscribedStreams/";
        request_url = base_url + userEmail +"/";


        startActivity(new Intent(DisplayStreams.this, DisplayStreams.class));
        finish();


    }

    public void viewNearbyStreams(View view){
        Intent intent = new Intent(this, ViewNearbyStreams.class);
        intent.putExtra("indexes","0_15");
        startActivity(intent);
    }

}
