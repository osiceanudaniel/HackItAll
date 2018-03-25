package com.example.osi.hackitallapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Looper;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static java.net.Proxy.Type.HTTP;

public class MainPageActivity extends AppCompatActivity {

    private FirebaseAuth authUser;
    private FirebaseAuth.AuthStateListener authUserListener;
    private WebView webView;

    private String myToken = "";
    private Thread thread;
    private final String andreiUrl = "http://192.168.0.103:8090/api/signin";
    private final String vladutUrl = "http://192.168.0.108:5000/home";

    private final String TOPIC = "JavaSampleApproach";
    private String temp = "";

    private ProgressDialog progressLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_page);

        progressLogin = new ProgressDialog(MainPageActivity.this);
        progressLogin.setTitle(getString(R.string.refreshWaiting));

        // get the user reference form firebase
        authUser = FirebaseAuth.getInstance();

        // xml objects reference
        webView = (WebView) findViewById(R.id.webViewId);

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);

        // auth user listener
        authUserListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // if user is not logged in go to LoginActivity
                if(authUser.getCurrentUser() == null) {
                    startActivity(new Intent(MainPageActivity.this, LoginActivity.class));
                } else {
                    FirebaseUser m = authUser.getCurrentUser();
                    getSupportActionBar().setTitle(authUser.getCurrentUser().getEmail());

                    m.getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        @Override
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                String token = task.getResult().getToken();
                                myToken = token;

                                if(myToken != "") {
                                    Log.e("TAG2", "A Pe make request");
                                    thread = new Thread(new Runnable() {

                                        @Override
                                        public void run() {
                                            try {
                                                makeRequest(andreiUrl, myToken);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                    thread.start();
                                }

                                Log.e("TAS", "TOKEN: " + token);
                            }
                        }

                    });

//                    if(myToken != "") {
//                        Log.e("TAG2", "A Pe make request");
//                        thread = new Thread(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                try  {
//                                    makeRequest(andreiUrl, myToken);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                        thread.start();
                        progressLogin.show();
                        webView.setWebViewClient(new WebViewClient());
                        webView.getSettings().setJavaScriptEnabled(true);
                        webView.getSettings().setDomStorageEnabled(true);
                        webView.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
                        webView.loadUrl(vladutUrl);
                        progressLogin.dismiss();
//                    } else {
//                        Log.e("TAG3", "Token null");
//                    }
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        // attach authListener to authUser
        authUser.addAuthStateListener(authUserListener);
//        temp = myToken;
        Log.e("TAG252" , "ONSTART");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Json
    public static String makeRequest(String uri, String json) {
        HttpURLConnection urlConnection;
        String url;
        String data = json;
        String result = null;
        try {
            //Connect
            urlConnection = (HttpURLConnection) ((new URL(uri).openConnection()));
            urlConnection.setDoOutput(true);
//            urlConnection.setRequestProperty("Content-Type", "application/json");
//            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("X-Authorization-Firebase", data);
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();
            Log.e("TAG", "A intrat si pe aici");

            //Write
            OutputStream outputStream = urlConnection.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
//            writer.write(data);
//            writer.close();
            outputStream.close();

            //Read
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            result = sb.toString();

        } catch (UnsupportedEncodingException e) {
            Log.e("TAG5", "A intrat pe primul catch");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("TAG7", "A intrat  pe catch 2 : " + e.getMessage());
            e.printStackTrace();

        }
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuExitId:
                logoutUser();
                break;
            case R.id.menuRefreshId:
                progressLogin.show();
                onStart();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    // user logout
    private void logoutUser() {
        authUser.signOut();
    }

    // exit the app on back button pressed
    public void onBackPressed(){
        Intent exit = new Intent(Intent.ACTION_MAIN);
        exit.addCategory(Intent.CATEGORY_HOME);
        exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(exit);
    }
}
