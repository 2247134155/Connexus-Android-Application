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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewSingleStream extends ActionBarActivity {

    public final static String EXTRA_STREAM_NAME_SINGLE = "com.aptdemo.yzhao.androiddemo.STREAM_NAME_SINGLE";
    public final static String EXTRA_LOGIN_STATUS_SINGLE = "com.aptdemo.yzhao.androiddemo.LOGIN_STATUS_SINGLE";

    final Context context2 = this;

    private String TAG  = "Display Images";

    public static String isOwnerLoggedIn="false";
    public static int index=-1;
    public static int smi_valid=0;

    public static String streamName="0";
    public static String gotUrls;
    public static String loginStatSingle="false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_stream);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (isOwnerLoggedIn==null) {
            isOwnerLoggedIn = "false";
        }


            //Get the message from the intent
            Intent intent = getIntent();
           isOwnerLoggedIn = intent.getStringExtra(DisplayStreams.EXTRA_STREAM_OWNER_LOGGED_IN);

        if (smi_valid == 0 || isOwnerLoggedIn!=null) {
            //Log.d("stream_name_inside",streamName);
            gotUrls = intent.getStringExtra(DisplayStreams.EXTRA_STREAM_URLS);

            if (gotUrls==null)
            {
                streamName = intent.getStringExtra(SearchResults.EXTRA_STREAM_NAME);
                gotUrls = intent.getStringExtra(SearchResults.EXTRA_SEARCH_URLS);

            }
            else {
                streamName = intent.getStringExtra(DisplayStreams.EXTRA_STREAM_NAME);
            }

            smi_valid = 0;
        }
        TextView displayStreamName = (TextView) findViewById(R.id.viewSingleStream);
        displayStreamName.setText(streamName);

        //Log.d("insideViewSingleStream_start",gotUrls);
        String[] stringImageURLs = gotUrls.split(",");

        final ArrayList<String> imageURLs2 = new ArrayList<String>();

        // See latest images if first time here and See more pictures is not pressed
        int loopLimit;
        int loopStart=1;

        int current_index;

        Button seeMoreImagesButton = (Button) findViewById(R.id.see_more_images);


            if (stringImageURLs.length <= 17) {
                loopLimit = stringImageURLs.length;
                seeMoreImagesButton.setEnabled(false);
                smi_valid=0;
                current_index = 0;
                index=-1;
                System.out.println("loop_limit" + loopLimit);
                System.out.println("loop_start" + loopStart);
            }
            else {
                if (smi_valid == 1) {
                    loopStart = index+17;
                    if (stringImageURLs.length < loopStart+16) {
                        loopLimit = stringImageURLs.length;
                        current_index = index;
                        seeMoreImagesButton.setEnabled(false);
                        smi_valid=0;
                        System.out.println("loop_limit" + loopLimit);
                        System.out.println("loop_start" + loopStart);

                    }
                    else {
                        loopLimit = loopStart + 16;
                        current_index = index+16;

                        System.out.println("loop_limit" + loopLimit);
                        System.out.println("loop_start" + loopStart);

                    }
                }
                else {
                    current_index=0;
                    loopStart = 1;
                    loopLimit = 17;

                    System.out.println("loop_limit" + loopLimit);
                    System.out.println("loop_start" + loopStart);

                }
            }

        index = current_index;



        for(int i=loopStart ;i<loopLimit;i++) {
            imageURLs2.add(stringImageURLs[i]);
        }


        Button uploadButton = (Button) findViewById(R.id.open_image_upload_page);

        if (isOwnerLoggedIn!=null && isOwnerLoggedIn.equals("true")) {
            uploadButton.setVisibility(View.VISIBLE);
            uploadButton.setClickable(true);
        }
        else{
            uploadButton.setVisibility(View.INVISIBLE);
            uploadButton.setClickable(false);

            // Update number of views for this stream
            final String request_url = "http://testp3-1106.appspot.com/updateStreamViews/" + streamName +"/";
            Log.d("reqUrl",request_url);

            AsyncHttpClient httpClient = new AsyncHttpClient();
            httpClient.get(request_url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                    try {
                        JSONObject jObject = new JSONObject(new String(response));
                        Log.d("responseViews", jObject.getString("numViews"));

                    } catch (JSONException j) {
                        System.out.println("JSON Error");
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                    Log.e(TAG, "There was a problem in retrieving the url : " + e.toString());
                }
            });
    }


        uploadButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent(context2, ImageUpload.class);
                        intent.putExtra(EXTRA_STREAM_NAME_SINGLE,streamName);
                        Log.d("stream_inside_click", streamName);
                        startActivity(intent);
                    }
                }
        );



        GridView gridview = (GridView) findViewById(R.id.gridviewSingleStream);
        gridview.setAdapter(new ImageAdapter(context2, imageURLs2));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                //Toast.makeText(context2, imageURLs2.get(position), Toast.LENGTH_SHORT).show();

                Dialog imageDialog = new Dialog(context2);
                imageDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                imageDialog.setContentView(R.layout.thumbnail);
                ImageView image = (ImageView) imageDialog.findViewById(R.id.thumbnail_IMAGEVIEW);

                Log.d("imageUrl_insideSingleStream", imageURLs2.get(position));

                if ( !imageURLs2.get(position).equals("") ) {
                    Picasso.with(context2).load(imageURLs2.get(position)).into(image);

                    imageDialog.show();
                    Log.d("EmptyUrl",imageURLs2.get(position));
                }
                else {
                    Log.d("EmptyUrl",imageURLs2.get(position));
                }
                }
        });


        Log.d("got_url", gotUrls);

        // Create the text view
        //TextView textView = new TextView(this);
        //textView.setTextSize(40);
        //textView.setText(gotUrls);

        //setContentView(textView);

    }

    public void GoHome(View view){
        Intent intent= new Intent(this, DisplayStreams.class);
        intent.putExtra(EXTRA_LOGIN_STATUS_SINGLE,isOwnerLoggedIn);
        startActivity(intent);

    }

    public void SeeMoreImages(View view){
        smi_valid=1;
        int newIndex=index;
        index=newIndex;

        startActivity(new Intent(ViewSingleStream.this, ViewSingleStream.class));
        finish();

    }

}
