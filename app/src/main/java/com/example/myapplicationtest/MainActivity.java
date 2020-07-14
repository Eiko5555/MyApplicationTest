package com.example.myapplicationtest;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    final OkHttpClient client = new OkHttpClient();
    RecyclerView recyclerView, recyclerViewMedium;
    RecyclerView.Adapter adapter, adapterMedium;
    String API_KEY_CLIENT_ID = "API";
    String secret_key = "KEY";
    String URL_POST = "https://api.petfinder.com/v2/oauth2/token";
    String URL_GET_ANIMALS_SIZE_SMALL = "https://api.petfinder.com/v2/animals?size=small&age=senior";
    String URL_GET_ANIMALS_SIZE_MEDIUM = "https://api.petfinder.com/v2/animals?size=medium&age=senior";
    String URL_GET_ORGANIZATION = "https://api.petfinder.com/v2/organizations/";
    Friend pet = new Friend();
    String accessToken , img1, getResponse, postResponse, getResponseID;
    String size, name, age, gender;
    String orgCity, orgState, orgPostal, address, contact, orgWebsite, orgPhone, orgName, orgEmail;
    String primaryBreed, secondaryBreed, unknownBreed;
    String breedsinfo, description, organization_id, spayed_neutered_result_string;
    Integer id;
    Boolean declawed, spayed_neutered;
    Response response;
    JSONObject objphoto1;
    ArrayList<Friend> smallFriend = new ArrayList<>();
    ArrayList<Friend> mediumFriend = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.horizontalrecyclerview);
        recyclerViewMedium = findViewById(R.id.horizontalScrollViewMedium);
        new loadSmall().execute();
        new loadMedium().execute();
//        new loadingData().execute();
    }
    public void httpRequesting() {
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("grant_type", "client_credentials")
                .addFormDataPart("client_id", API_KEY_CLIENT_ID)
                .addFormDataPart("client_secret", secret_key)
                .build();
        Request request = new Request.Builder()
                .addHeader("content-type", "application/json")
                .url(URL_POST).post(requestBody).build();
        {
            try {
                response = client.newCall(request).execute();
                postResponse = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                JSONObject jsonObject = new JSONObject(postResponse);
                accessToken = jsonObject.getString("access_token");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void httpRequestOrganizationID() {
        Request requestID = new Request.Builder() /*getting all JSON info*/
                .addHeader("content-type", "application/json")
                .url(URL_GET_ORGANIZATION + organization_id)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        try (Response responseID = client.newCall(requestID).execute()) {
            getResponseID = responseID.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    class loadSmall extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            httpRequesting();
            Request request2 = new Request.Builder() /*getting all JSON info*/
                    .addHeader("content-type", "application/json")
                    .url(URL_GET_ANIMALS_SIZE_SMALL)
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .build();
            try (Response response2 = client.newCall(request2).execute()) {
                getResponse = response2.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {/*if photo and id match put on image view on list*/
                JSONObject obj = new JSONObject(getResponse);
                JSONArray jsonArray = obj.getJSONArray("animals");
                for (int t = 0; t < jsonArray.length(); t++) {
                    JSONObject objId = jsonArray.getJSONObject(t);
                    JSONArray jsonArrayPhoto = objId.getJSONArray("photos");
                    if (jsonArrayPhoto.length() > 0) {
                        objphoto1 = jsonArrayPhoto.getJSONObject(0);//img1
//                        objphoto2 = jsonArray2.getJSONObject(1);//img2
//                        objphoto3 = jsonArray2.getJSONObject(2);//img3
                        img1 = objphoto1.getString("small");
//                        img2 = objphoto2.getString("small");
//                        img3 = objphoto3.getString("small");
                        pet.setSmall(img1);
                    } else {
                    }
//                    age = objId.getString("age");
                    id = objId.getInt("id");
//                    size = objId.getString("size");
//                    name = objId.getString("name");
//                    gender = objId.getString("gender");
//                    description = objId.getString("description");
//                    organization_id = objId.getString("organization_id");
//                    contact = objId.getString("contact");
//                    if (description == "null") {
//                        description = "No comment from organization.";
//                    }
//                    JSONObject forBreedJSOnO = objId.getJSONObject("breeds");
//                    primaryBreed = forBreedJSOnO.getString("primary");
//                    secondaryBreed = forBreedJSOnO.getString("secondary");
//                    unknownBreed = forBreedJSOnO.getString("unknown");
//                    if (unknownBreed == "true") {
//                        breedsinfo = "Unknown";
//                    }
//                    if (secondaryBreed != "null") {
//                        breedsinfo = primaryBreed + " " + secondaryBreed + " mix.";
//                    }
//                    if (secondaryBreed != "Mixed Breed") {
//                        breedsinfo = primaryBreed + " " + secondaryBreed + " mix.";
//                    }
//                    if (secondaryBreed != "Mixed Breed" || secondaryBreed == "null") {
//                        breedsinfo = primaryBreed + " Mix";
//                    }
//                    httpRequestOrganizationID();
//                    JSONObject objForID = new JSONObject(getResponseID);
//                    JSONObject jsonObjOrgID = objForID.getJSONObject("organization");
//                    orgName = jsonObjOrgID.getString("name");
//                    orgEmail = jsonObjOrgID.getString("email");
//                    if (orgEmail == "null") {
//                        orgEmail = "No Email information.";
//                    }
////                    Log.i("Email: ", orgEmail);
//                    orgPhone = jsonObjOrgID.getString("phone");
//                    if (orgPhone == "null") {
//                        orgPhone = "No phone number information.";
//                    }
////                    Log.i("Denwa: ", orgPhone);
//                    orgWebsite = jsonObjOrgID.getString("website");
//                    if (orgWebsite == "null") {
//                        orgWebsite = "No website information.";
//                    }
////                    Log.i("Web: ", orgWebsite);
//                    JSONObject objOrgAddress = jsonObjOrgID.getJSONObject("address");
//                    orgCity = objOrgAddress.getString("city");
//                    orgState = objOrgAddress.getString("state");
//                    orgPostal = objOrgAddress.getString("postcode");
//                    address = orgCity + ", " + orgState + ", " + orgPostal;
////                    Log.i("Address Info: ", address);
//                    JSONObject objAttribute = objId.getJSONObject("attributes");
//                    spayed_neutered = objAttribute.getBoolean("spayed_neutered");
//                    spayed_neutered_result_string = "Yes";
//                    if (spayed_neutered == false) {
//                        spayed_neutered_result_string = "No";
//                    }
////                    Log.i("spayed_neutered", spayed_neutered_result_string);
//                    pet.setAge(age);
                    pet.setId(id);
//                    pet.setSize(size);
//                    pet.setName(name);
//                    pet.setBreeds(breedsinfo);
//                    pet.setGender(gender);
//                    pet.setDescription(description);//set: orgName, email, phone, address,website
//                    pet.setOrganizationName(orgName);
//                    pet.setEmail(orgEmail);
//                    pet.setPhone(orgPhone);
//                    pet.setWebsite(orgWebsite);
//                    pet.setAddress(address);
//                    pet.setSpayInfo(spayed_neutered_result_string);
                    smallFriend.add(pet);
                    pet = new Friend();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return postResponse;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            recyclerView.setLayoutManager(new LinearLayoutManager(
                    MainActivity.this,
                    LinearLayoutManager.HORIZONTAL, false));
            adapter = new Myadapter(getApplicationContext(), smallFriend);
            recyclerView.setAdapter(adapter);
        }
    }
            class loadMedium extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            httpRequesting();
            Request request2 = new Request.Builder() /*getting all JSON info*/
                    .addHeader("content-type", "application/json")
                    .url(URL_GET_ANIMALS_SIZE_MEDIUM)
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .build();
            try (Response response2 = client.newCall(request2).execute()) {
                getResponse = response2.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {/*if photo and id match put on image view on list*/
                JSONObject obj = new JSONObject(getResponse);
                JSONArray jsonArray = obj.getJSONArray("animals");
                for (int t = 0; t < jsonArray.length(); t++) {
                    JSONObject objId = jsonArray.getJSONObject(t);
                    JSONArray jsonArrayPhoto = objId.getJSONArray("photos");
                    if (jsonArrayPhoto.length() > 0) {
                        objphoto1 = jsonArrayPhoto.getJSONObject(0);
                        img1 = objphoto1.getString("small");
                        pet.setSmall(img1);
                    } else {
//                        System.out.println("No photo available");
                    }
                    mediumFriend.add(pet);
                    pet = new Friend();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return postResponse;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            recyclerViewMedium.setLayoutManager(new LinearLayoutManager(
                    MainActivity.this,
                    LinearLayoutManager.HORIZONTAL, false));
            adapterMedium = new Myadapter(getApplicationContext(), mediumFriend);
            recyclerViewMedium.setAdapter(adapterMedium);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}