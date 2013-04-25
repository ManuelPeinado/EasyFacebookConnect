EasyFacebookConnect
===================

EasyFacebookConnect is an Android library which makes life easier for the developer who wants to user Facebook SDK. You won't need to spend a few hours learning Facebook SDK for trying to implement login and other features.

Currently, it provides following features:

* Login with Facebook.
* Get User information.
* Upload user status to desired application.
* Upload photo to desired application.


Screenshots
-----------

<a>
  <img src="http://img341.imageshack.us/img341/4725/descarga2z.png">
</a>


Usage
-----

1 - Create Fabook App

* Create a new Facebook app.

<a>
  <img src="https://fbcdn-dragon-a.akamaihd.net/cfs-ak-prn1/84994/87/280413158724945-/fbandgs14.png" />
</a>

* Once created, note down the app ID shown at the top of the dashboard page. You'll need to add this to your project files.

<a>
  <img src="https://fbcdn-dragon-a.akamaihd.net/cfs-ak-prn1/85001/10/470612839627677-/fbandgs15.png" />
</a>

Alternatively, you can, of course, use the ID of an existing app.

In either case, you will also need to associate your Android key hash with the app. Click 'Edit App' and open up the 'Native Android App' section at the bottom of the dashboard. Add the key hash that you obtained at the end of the previous step with the keytool app.

<a>
  <img src="https://fbcdn-dragon-a.akamaihd.net/cfs-ak-ash3/676658/827/440884335967686-/Screen%20Shot%202012-10-17%20at%2010.45.03%20PM.png" />
</a>

Save this change.

You will also need to return to this dashboard and add your app's package name and main activity class once you have created a new Android project itself.

2 - In your App

* Register the package and activity with Facebook

At this point, you should return to the App Dashboard on the Facebook Developers site and add your Android app's package and activity names to the Android settings. Also enable 'Facebook Login':

<a>
  <img src="https://fbcdn-dragon-a.akamaihd.net/cfs-ak-ash3/676675/902/343496332413357-/conf.png" />
</a>

To generate your key hash on your local computer, you can run Java's keytool utility:

Mac Os X: keytool -exportcert -alias androiddebugkey -keystore ~/.android/debug.keystore | openssl sha1 -binary | openssl base64
Windows: keytool -exportcert -alias androiddebugkey -keystore %HOMEPATH%\.android\debug.keystore | openssl sha1 -binary | openssl base64

But I entirely RECOMMEND you, using this code in your activity, for avoiding problems:

```java
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Add code to print out the key hash
    try {
        PackageInfo info = getPackageManager().getPackageInfo(
                "com.facebook.samples.hellofacebook", 
                PackageManager.GET_SIGNATURES);
        for (Signature signature : info.signatures) {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(signature.toByteArray());
            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
    } catch (NameNotFoundException e) {

    } catch (NoSuchAlgorithmException e) {

    }

    ...
```
Save your changes and re-run the sample. Check your logcat output for a message similar to this:

```
12-20 10:47:37.747: D/KeyHash:(936): 478uEnKQV+fMQT8Dy4AKvHkYibo=
```
Save the printed key hash in your developer profile.

* Link to the EasyFacebookConnect project and configure the Facebook app ID.

To add the Facebook app ID into your project, open up the strings.xml file in the res/values folder of your project. You need to add an app_id string containing the ID you obtained in the previous step.

Example: <string name="app_id">497906953566757</string>

Next, open the AndroidManifest.xml file in the root of the project. Add a 'Uses Permission' item named android.permission.INTERNET

```xml
 <uses-permission android:name="android.permission.INTERNET"/>
```
Also, you must add this into your Application code:

```xml
<activity
    android:name="com.facebook.LoginActivity"
    android:label="@string/app_name"
    android:theme="@android:style/Theme.Translucent.NoTitleBar" />

<meta-data
    android:name="com.facebook.sdk.ApplicationId"
    android:value="@string/applicationId" />
```

2 - Finally

In your activity, extends EasyFacebookActivity and call connect() method. It will be necessary a listener parameter, which will provide the necessary callbacks for working as any method expected. Here is an example:


```java
connect(new EasySessionListener() {

    ProgressDialog progressDialog;

    @Override
    public void onStart() {
        super.onStart();
        progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
    }

    @Override
    public void onSuccess(Response response) {
        super.onSuccess(response);
    }

    @Override
    public void onFinish() {
        super.onFinish();
        progressDialog.dismiss();
        //doSomething
    }

    @Override
    public void onError(FacebookRequestError error) {
        super.onError(error);
        Toast.makeText(HomeActivity.this, error.getErrorMessage(), Toast.LENGTH_LONG).show();
    }

    public void onClosedLoginFailed(Session session, SessionState state, Exception exception) {
        Toast.makeText(HomeActivity.this, state.toString(), Toast.LENGTH_LONG).show();
    }

    public void onCreated(Session session, SessionState state, Exception exception) {
        Toast.makeText(HomeActivity.this, state.toString(), Toast.LENGTH_LONG).show();
    }

    public void onClosed(Session session, SessionState state, Exception exception) {
        Toast.makeText(HomeActivity.this, state.toString(), Toast.LENGTH_LONG).show();
    }

});
```

Developed By
------------

* César Díez Sánchez - <cesaryomismo@gmail.com>

<a href="https://twitter.com/menorking">
  <img alt="Follow me on Twitter" src="http://img692.imageshack.us/img692/5807/gstwittern.png" />
</a>

<a href="https://plus.google.com/115273462230054581675">
  <img alt="Follow me on Google Plus" src="http://img443.imageshack.us/img443/3623/gsgoogleplus.png" />
</a>

<a href="http://www.linkedin.com/in/cesardiezsanchez">
  <img alt="Add me to Linkedin" src="http://img854.imageshack.us/img854/4308/gslinkedin.png" />
</a>


License
-------

```
Copyright 2013 m3n0R

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[1]: https://github.com/m3n0R/EasyFacebookConnect/tree/master/EasyFacebookConnect-Samples





