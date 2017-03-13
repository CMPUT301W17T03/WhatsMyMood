package com.example.whatsmymood;

import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.Update;


/**
 * Created by YiJi on 2017-03-11.
 */

/**
 * Universal elastic search controller to return
 * user objects
 */
public class ElasticSearchUserController {

    private static JestDroidClient client;

    //https://github.com/AlexCzeto/lonelyTwitter/blob/elasticsearch/app/src/main/java/ca/ualberta/cs/lonelytwitter/ElasticsearchTweetController.java
    //03-11-2017

    /**
     * Adds a new user to elastic search
     * Used in login activity
     */
    public static class AddUserTask extends AsyncTask<UserAccount, Void, Void> {

        @Override
        protected Void doInBackground(UserAccount... users) {
            verifySettings();
            for (UserAccount user : users) {
                Index index = new Index.Builder(user).index("cmput301w17t03").type("user").build();

                try {

                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()){
                        Log.d("tag", result.getId());
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

    /**
     * Gets the current user
     * Used for grabbing the current user as an object
     * and to edit the data
     */
    public static class GetUserTask extends AsyncTask<String, Void, ArrayList<UserAccount>> {
        @Override
        protected ArrayList<UserAccount> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<UserAccount> users = new ArrayList<UserAccount>();

            //String query = "";
            //String query = String.format("{ \"query\" : {  \"term\" : { \"username\" : \"Username\" } } }");  //,search_parameters[0].toString().trim());


            String query = String.format("{\n" +
                    "    \"query\" : {\n" +
                    "        \"match\" : " +
                    "               { \"username\" : \"" + "%s" + "\" }\n" +
                    "    }\n" +
                    "}", search_parameters[0].toString().trim());

            Search search = new Search.Builder(query).addIndex("cmput301w17t03").addType("user").build();

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

    /**
     * Updates a user by deleting and reinsterting a user
     * Used to update their user attributes
     */
    public static class UpdateUser extends AsyncTask<UserAccount, Void, Void> {

        @Override
        protected Void doInBackground(UserAccount... users) {
            verifySettings();
            for (UserAccount user : users) {

                /**
                 * THIS WORKS BY DELETING THE OLD USER
                 * AND UPDATING WITH THE SAME (updated) USER
                 */

                String deleteQuery = String.format("{\n" +
                        "    \"query\" : {\n" +
                        "        \"match\" : " +
                        "               { \"username\" : \"" + "%s" + "\" }\n" +
                        "    }\n" +
                        "}", user.getUsername());

                // Delete first then update the result
                DeleteByQuery delete = new DeleteByQuery.Builder(deleteQuery).addIndex("cmput301w17t03").addType("user").build();

                Index index = new Index.Builder(user).index("cmput301w17t03").type("user").build();

                try {
                    client.execute(delete);
                    DocumentResult updateResult = client.execute(index);
                    if (updateResult.isSucceeded()){
                        // TODO: ID'S change constantly, so we might have to implmement this in a different way or ditch ID's in User Accounts
                        user.setId(updateResult.getId());
                    }
                    else{
                        Log.i("Error","ElasticSearch was not able to add the user");
                    }
                }
                catch (Exception e) {
                    Log.i("tag", "The application failed to build and send the user accounts");
                    e.printStackTrace();
                }

            }
            return null;
        }
    }

    // Taken from LonelyTwitter - Lab 5 with ElasticSearch
    // Verifies elastic search settings
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
