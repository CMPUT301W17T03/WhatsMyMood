package com.example.whatsmymood;

import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.lang.reflect.Array;
import java.util.ArrayList;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

/**
 * Created by YiJi on 2017-03-11.
 */

public class ElasticSearchMoodController {

    private static JestDroidClient client;

    //Adds moods to ElasticSearch... probably
    public static class AddMoodsTask extends AsyncTask<Mood, Void, Void> {

        @Override
        protected Void doInBackground(Mood...moods){
            verifySettings();

            for (Mood mood: moods){
                Index index = new Index.Builder(mood).index("cmput301w17t03").type("mood").build();

                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()){
                        mood.setId(result.getId());
                    }
                    else{
                        Log.i("Error", "ElasticSearch was not able to add the mood.");
                    }
                }
                catch (Exception e){
                    Log.i("Error", "The application failed to build and send the moods.");
                }
            }
            return null;
        }
    }

    public static class GetMoodsTask extends AsyncTask<String, Void, ArrayList<Mood>>{

        @Override
        protected ArrayList<Mood> doInBackground(String... searchparameters){
            verifySettings();

            ArrayList<Mood> moods = new ArrayList<Mood>();
            
        }
    }


    //Taken from LonelyTwitter - Lab 5 with ElasticSearch
    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

}
