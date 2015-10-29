package com.aptdemo.yzhao.androiddemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;

public class SubscribedStreams extends ActionBarActivity {


    public static String loginStatSubscribed;

    public Button subscribedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_display_streams);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        Intent intent = getIntent();
//        loginStatSubscribed = intent.getStringExtra(DisplayStreams.EXTRA_STREAM_LOGIN_STATUS);
//
//        subscribedButton = (Button) findViewById(R.id.subscribed_button);
//
//        if (loginStatSubscribed!=null && loginStatSubscribed.equals("False")){
//            subscribedButton.setVisibility(View.INVISIBLE);
//
//        }
//        else {
//            subscribedButton.setVisibility(View.VISIBLE);
//            subscribedButton.setEnabled(false);
//        }


        // Code from Display Streams

//        final String request_url = "http://testp3-1106.appspot.com/dumpAllStreams";
//        //NoCover URL: http://testp3-1106.appspot.com//static_images/noCover.png
//        AsyncHttpClient httpClient = new AsyncHttpClient();
//        httpClient.get(request_url, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
//                final ArrayList<String> imageURLs = new ArrayList<String>();
//                final ArrayList<String> imageCaps = new ArrayList<String>();
//                try {
//                    final JSONObject jObject = new JSONObject(new String(response));
//                    JSONArray displayImages = jObject.getJSONArray("coverUrls");
//                    JSONArray displayCaption = jObject.getJSONArray("streamNames");
//                    sendJson = jObject;
//                    searchResults="";
//                    Log.d("search_results", searchResults);
//
//
//
//                    Log.d("search_results", searchResults);
//
//                    for (int i=0; i<displayCaption.length();i++)
//                    {
//                        Log.d("stream_name1",displayCaption.getString(i));
//                        JSONObject streamSearch = jObject.getJSONObject(displayCaption.getString(i));
//                        Log.d("stream_name1",streamSearch.getString("searchData"));
//                        String searchData = streamSearch.getString("searchData");
//                        searchResults = searchResults + displayCaption.getString(i) + ":" + searchData + ";";
//                        Log.d("search_results", searchResults);
//                    }
//
//                    //imageUrls
//
//                    for(int i=0;i<displayImages.length();i++) {
//                        if (displayImages.getString(i).equals("") )
//                        {
//                            imageURLs.add("http://testp3-1106.appspot.com//static_images/noCover.png");
//                            imageCaps.add(displayCaption.getString(i));
////                            imageCaps.add("http://testp3-1106.appspot.com//static_images/noCover.png");
//                            System.out.println("http://testp3-1106.appspot.com//static_images/noCover.png");
//                        }
//                        else
//                        {
//                            imageURLs.add(displayImages.getString(i));
//                            imageCaps.add(displayCaption.getString(i));
//                            System.out.println(displayImages.getString(i));
//                        }
//
//                    }
//                    GridView gridview = (GridView) findViewById(R.id.gridviewStreams);
//                    System.out.println("before SetAdapter");
//                    gridview.setAdapter(new ImageAdapter(context,imageURLs));
//                    gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View v,
//                                                int position, long id) {
//
//                            Toast.makeText(context, imageCaps.get(position), Toast.LENGTH_SHORT).show();
//                            System.out.println("after toast");
//                            Dialog imageDialog = new Dialog(context);
//                            imageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                            imageDialog.setContentView(R.layout.thumbnail);
//                            System.out.println("before ImageView");
//                            ImageView image = (ImageView) imageDialog.findViewById(R.id.thumbnail_IMAGEVIEW);
//
//                            Log.d("debuggingImageURLS",imageURLs.get(position));
//                            Picasso.with(context).load(imageURLs.get(position)).into(image);
//
//                            imageDialog.show();
//                            try {
//                                //JSONArray displayCaption = jObject.getJSONArray(imageCaps.get(position));
//                                // START OF BLOCK TEST CODE
//                                JSONObject streamObj = jObject.getJSONObject(imageCaps.get(position));
//                                JSONArray streamImageUrls = streamObj.getJSONArray("imageUrls");
//                                ArrayList<String> listImageUrls = new ArrayList<String>();
//
//
//                                String allImageUrls = "";
//                                for (int i = 0; i < streamImageUrls.length(); i++) {
//                                    if (streamImageUrls.getString(i).equals("")) {
//                                        listImageUrls.add("The stream has no images!");
//                                    } else {
//                                        listImageUrls.add(streamImageUrls.getString(i));
//                                        System.out.println(streamImageUrls.getString(i));
//                                    }
//                                    allImageUrls = allImageUrls + "," + streamImageUrls.getString(i);
//                                }
//                                // END OF BLOCK TEST CODE
//
//                                Log.d("stream_name",imageCaps.get(position));
//                                Intent intent = new Intent(context, ViewSingleStream.class);
//
//                                String allStreamsUrl = imageCaps.get(position);
//                                intent.putExtra(EXTRA_STREAM_URLS, allImageUrls);
//                                intent.putExtra(EXTRA_STREAM_NAME, imageCaps.get(position));
//                                startActivity(intent);
//                            }
//                            catch(JSONException j){
//                                System.out.println("JSON Error");
//                            }
//                        }
//                    });
//                }
//                catch(JSONException j){
//                    System.out.println("JSON Error");
//                }
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
//                Log.e(TAG, "There was a problem in retrieving the url : " + e.toString());
//            }
//        });








    }

}
