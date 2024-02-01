package com.noyon.favtube;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ProgressBar progBar;
    ListView lView;

   ArrayList<HashMap<String,String>>List=new ArrayList<>();
   HashMap<String,String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progBar=findViewById(R.id.progBar);
        lView=findViewById(R.id.lView);


        String url="https://noy003.000webhostapp.com/music/youtube.json";


        JsonArrayRequest jArray=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) { // when response comes

                progBar.setVisibility(View.GONE);

                try {


                    for(int i=0; i<response.length(); i++){

                        JSONObject jObject=response.getJSONObject(i);
                        String videoId=jObject.getString("id");
                        String songName=jObject.getString("songName");



                        map=new HashMap<>();
                        map.put("videoId",videoId);
                        map.put("songName",songName);

                        List.add(map);

                    }

                    MyAdapter adapter=new MyAdapter();
                    lView.setAdapter(adapter);





                } catch (JSONException e) {

                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { // when doesnot get any response

                progBar.setVisibility(View.GONE);
            }
        });

        RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
        queue.add(jArray);





    }

    // adapter class


  private  class  MyAdapter extends  BaseAdapter {


      @Override
      public int getCount() {
          return List.size();
      }

      @Override
      public Object getItem(int position) {
          return null;
      }

      @Override
      public long getItemId(int position) {
          return 0;
      }

      @Override
      public View getView(int position, View convertView, ViewGroup parent) {

          LayoutInflater inflater= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          View myView=inflater.inflate(R.layout.item,parent,false);
          TextView tvTitle=myView.findViewById(R.id.tvTitle);
          ImageView thumbnail=myView.findViewById(R.id.thumbnail);
          LinearLayout itemBody=myView.findViewById(R.id.itemBody);

          //get the hashmap from arraylist
          map=List.get(position);
          // get string from hashmap
          String title=map.get("songName");
          String videoId=map.get("videoId");

          // geting youtube video thumbnal from video
         String thumnailurl="https://img.youtube.com/vi/"+videoId+"/0.jpg";

         //add this thumnail url to load in imageview
          Picasso.get().load(thumnailurl).into(thumbnail);

          tvTitle.setText(title);

          // click event to play any music
          itemBody.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  Intent myIntent =new Intent(MainActivity.this,PlayerActivity.class);

                  PlayerActivity.VideoId=videoId;

                  startActivity(myIntent);



              }
          });




          return myView;
      }
  }




  // back pressed method for set aleart dialob box

    @Override
    public void onBackPressed() {


        // super.onBackPressed(); //<-------- this line responsible for go back


       AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Confirm Exit!!");
        dialog.setMessage("Do you Want to Exit?");
        dialog.setIcon(R.drawable.exit);

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

                finishAndRemoveTask();


            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();

            }
        });
        dialog.setCancelable(false);
        dialog.show();




    }
}
