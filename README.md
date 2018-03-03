# Demo1Facebook
Simple Facebook Login integration in Android App

Generate SHA-1 fingerprint - FB
-------------------------------
On window 

keytool -exportcert -list -v \ -alias androiddebugkey -keystore %USERPROFILE%\.android\debug.keystore	

keytool -list -v -keystore "%USERPROFILE%\.android\debug.keystore" -alias androiddebugkey -storepass android -keypass android

On Linux or Mac OS

keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android 


Developer Facebook refe
----------------------

https://developers.facebook.com/docs/
https://developers.facebook.com/docs/facebook-login/
https://developers.facebook.com/apps/
https://developers.facebook.com/tools-and-support/

https://developers.facebook.com/docs/graph-api/reference/user
https://developers.facebook.com/docs/facebook-login/permissions/
https://developers.facebook.com/docs/accountkit/countrycodes
https://developers.facebook.com/docs/accountkit
https://developers.facebook.com/blog/post/2011/01/14/platform-updates--new-user-object-fields--edge-remove-event-and-more/

https://www.facebook.com/policies
https://www.facebook.com/terms.php



http GET request on id 
----------------------
https://graph.facebook.com/me/picture?access_token=<your_access_token_here>
http://graph.facebook.com/YOUR_ID_HERE/picture



App Not Setup:The developers of this app have not set up this app properly for Facebook Login
------

https://stackoverflow.com/questions/21329250/the-developers-of-this-app-have-not-set-up-this-app-properly-for-facebook-loginw.google.co.in/search?rlz=1C1CHZL_enIN754IN754&ei=B51bWpaYB4iY8QXavoaIDA&q=Simple+facebook+integration+in+android&oq=Simple+facebook+integration+in+android&gs_l=psy-ab.3...20951.29216.0.29662.0.0.0.0.0.0.0.0..0.0....0...1c.1.64.psy-ab..0.0.0....0.iGTJzWfewTI


User data permission and get the information fron GraphRequest via JSON
-----

bundle.putString("fields","id,email,first_name,last_name,picture.type(normal)")

publicProfile, Email, Gender, user_friends, Social Links, DOB, mobile phone, address,
id, address, age_range, birthday, email, gender, name, first_name,middle_name, last_name, hometown, phone_number, user_mobile_phone, user_address



build.gradle(Module:app)
----

 repositories {
        mavenCentral()
    }
    
    
for Facebook:

implementation 'com.facebook.android:facebook-login:4.29.0'  

for Picture display:

compile 'com.github.bumptech.glide:glide:3.7.0'



Screen Short
---------
![](Screen/1%20Screenshot_2018-01-15-19-16-57.png)
![](Screen/2%20Screenshot_2018-01-15-19-16-14.png)
![](Screen/4%20Screenshot_2018-01-15-19-15-40.png)

