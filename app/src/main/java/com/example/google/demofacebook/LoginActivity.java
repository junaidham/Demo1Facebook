package com.example.google.demofacebook;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    // Call CallbackManager.Factory.create to create a callback manager to handle login responses In the Activity Class.
    CallbackManager callbackManager;
    AccessToken accessToken;
    AccessTokenTracker accessTokenTracker;
    private ProfilePictureView profilePictureView;
    ProfileTracker profileTracker;
    LoginButton loginButton;
   // LoginButton loginButton2;

    private TextView tvId,tvEmail,tvGender,tvName;
    private ImageView imageProfile;
    LinearLayout linearUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //The SDK needs to be initialized before using any of its methods
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        //initialize an instance of CallbackManager using the CallbackManager.Factory.create() method
        callbackManager = CallbackManager.Factory.create();


        // init
       initView();

      methodFirst();

      //methodSecond();

    }

    private void initView() {
        loginButton = (LoginButton) findViewById(R.id.login_button);
        //loginButton2 = (LoginButton) findViewById(R.id.login_button2);
        tvId = (TextView)findViewById(R.id.tvId);
        tvName = (TextView)findViewById(R.id.tvName);
        tvEmail = (TextView)findViewById(R.id.tvEmail);
        tvGender = (TextView)findViewById(R.id.tvGender);
        imageProfile =(ImageView)findViewById(R.id.imageProfile);
        profilePictureView =(ProfilePictureView)findViewById(R.id.picture);
        linearUser = (LinearLayout) findViewById(R.id.linearUser);

    }


    private void methodFirst() {

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                String facebook_id,f_name, m_name, l_name, gender, profile_image, full_name, email_id;
                if (currentProfile != null) {
                    facebook_id=currentProfile.getId();
                    Log.e("facebook_id", facebook_id);

                    f_name=currentProfile.getFirstName();
                    Log.e("f_name", f_name);

                    m_name=currentProfile.getMiddleName();
                    Log.e("m_name", m_name);

                    l_name=currentProfile.getLastName();
                    Log.e("l_name", l_name);

                    full_name=currentProfile.getName();
                    Log.e("full_name", full_name);

                    profile_image=currentProfile.getProfilePictureUri(120, 120).toString();
                    Log.e("profile_image", profile_image);
                }

            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if(AccessToken.getCurrentAccessToken() != null) {
                    profilePictureView.setProfileId(null);
                }*/
                fbLogIn();

            }
        });


    }

    private void fbLogIn() {
//        accessTokenTracker.startTracking();
//        profileTracker.stopTracking();

        LoginManager.getInstance().logInWithReadPermissions(this,
                Arrays.asList("email,public_profile"));

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                // displayMsg(profile);

                System.out.println("Granted Permission = "+loginResult.getRecentlyGrantedPermissions());

                // GraphRequest for database
                GraphRequest data_request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject, GraphResponse response) {

                                JSONObject jsonObject1 = new JSONObject();
                                System.out.println("jsonObject:"+jsonObject1.toString());

                                System.out.println("response :"+response.toString());
                                // user access information here
                                try {
                                    tvId.setText("Id: "+jsonObject.getString("id"));
                                    tvName.setText("Name: "+jsonObject.getString("name"));
                                    tvEmail.setText("Email: "+jsonObject.getString("email"));
                                    tvGender.setText("Gender: "+jsonObject.getString("gender"));
                                    profilePictureView.setPresetSize(ProfilePictureView.LARGE);
//                                    profilePictureView.setPresetSize(ProfilePictureView.NORMAL);
                                    profilePictureView.setProfileId(jsonObject.getString("id"));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                Bundle parameters = new Bundle();
                //parameters.putString("fields", "id,name,email,gender, birthday");
                parameters.putString("fields", "id,name,email,gender, picture.width(120).height(120)");
                data_request.setParameters(parameters);
                data_request.executeAsync();
            }

            @Override
            public void onCancel() {
               // accessTokenTracker.stopTracking();
                //profileTracker.stopTracking();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "error to Login Facebook ="+error.toString(), Toast.LENGTH_SHORT).show();
            }

        });

    } // end login


    private void methodSecond() {

        // provide permission for Read user information like - name, email, public_profile
        loginButton.setReadPermissions(Arrays.asList("public_profile, email"));
        //loginButton.setReadPermissions("email");
        //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email,public_profile"));

        // set listener
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //check for Granted Permission
                System.out.println("Granted Permission = "+loginResult.getRecentlyGrantedPermissions());

                //create a graph request to get UserDetail
                // If the login attempt is successful, onSuccess is called.
                getUserDetails(loginResult);

            }

            @Override
            public void onCancel() {
                // If the user cancels the login attempt, onCancel is called.
                // App code
                //accessTokenTracker.stopTracking();
                //  profileTracker.stopTracking();

            }

            @Override
            public void onError(FacebookException error) {
                // If an error occurs, onError is called.
                // App code
                Toast.makeText(LoginActivity.this, "error to Login Facebook ="+error.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    // pass user data to another activity
    private void getUserDetails(LoginResult loginResult) {

        GraphRequest data_request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject json_object, GraphResponse response) {
                        String userDetail = response.getRawResponse();
                        try {
                            JSONObject jsonObject = new JSONObject(userDetail);
                            System.out.println("jsonObject:"+jsonObject);

                            Log.i("app",response.toString());
                            setProfileToView(json_object);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // user access information here (in same Activity) or
                        // take data and pass to another activity by Intent

                        /*Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("userProfile", json_object.toString());
                        startActivity(intent);*/
                    }
                });
        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email,gender, picture.width(120).height(120)");
        data_request.setParameters(permission_param);
        data_request.executeAsync();

    }
    private void setProfileToView(JSONObject jsonObject) {
        // user access information here
        try {
            tvId.setText("Id: "+jsonObject.getString("id"));
            tvName.setText("Name: "+jsonObject.getString("name"));
            tvEmail.setText("Email: "+jsonObject.getString("email"));
            tvGender.setText("Gender: "+jsonObject.getString("gender"));
            profilePictureView.setPresetSize(ProfilePictureView.LARGE);
//            profilePictureView.setPresetSize(ProfilePictureView.NORMAL);
             profilePictureView.setProfileId(jsonObject.getString("id"));

            // using the Glide for display image
           /* String fbId = jsonObject.getString("id");  // id":"1591355207623520
            String fbImage = "https://graph.facebook.com/"+fbId+"picture?type=large";
            Glide.with(getApplicationContext()).load(fbImage).into(imageProfile);*/

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //in the onActivityResult in your Activity, pass the result to the CallbackManager
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }




}
