package com.example.whatsmymood;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;


/**
 * Created by YiJi on 2017-03-11.
 */

public class ElasticSearchUserController {

    private static JestDroidClient client;

    //https://github.com/AlexCzeto/lonelyTwitter/blob/elasticsearch/app/src/main/java/ca/ualberta/cs/lonelytwitter/ElasticsearchTweetController.java
    //03-11-2017

    public static class AddUserTask extends AsyncTask<UserAccount, Void, Void> {

        @Override
        protected Void doInBackground(UserAccount... users) {
            verifySettings();

            for (UserAccount user : users) {
                Index index = new Index.Builder(user).index("cmput301w17t03").type("user").build();

                try {

                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()){
                        user.setId(result.getId());
                    }
                    else{
                        Log.i("Error","ElasticSearch was not able to add the user");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the user accounts");
                }

            }
            return null;
        }
    }

    public static class GetUserTask extends AsyncTask<String, Void, ArrayList<UserAccount>> {
        @Override
        protected ArrayList<UserAccount> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<UserAccount> users = new ArrayList<UserAccount>();

            //String query = "";
            String query = String.format("{ \"query\" : {  \"term\" : { \"username\" : \"%s\" } } }",search_parameters[0].toString().trim());
            Log.d("yiji",query);
            Search search = new Search.Builder(query).
                    addIndex("cmput301w17t03").addType("user")
                    .build();

            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()){
                    List<UserAccount> foundAccount = result.getSourceAsObjectList(UserAccount.class);
                    users.addAll(foundAccount);
                }
                else{
                    Log.i("Error","The search query failed to find any user accounts that matched");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return users;
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
