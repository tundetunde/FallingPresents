package com.dualtech.fallingpresents.android;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.appodeal.ads.Appodeal;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.dualtech.fallingpresents.ActionResolver;
import com.dualtech.fallingpresents.ActivityMethods;
import com.dualtech.fallingpresents.AdsController;
import com.dualtech.fallingpresents.FallingPresentsGame;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
/*import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;*/

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class AndroidLauncher extends AndroidApplication implements AdsController, ActivityMethods {
	//GameHelper gameHelper;
	private final static int REQUEST_CODE_UNUSED = 9002;
	private static final String BANNER_AD_UNIT_ID = "ca-app-pub-6044705985167929/8567710899";
	private static final String BANNER_TEST = "ca-app-pub-3940256099942544/6300978111";
	private static final String FB_APP_ID = "1000906476636146";
	LoginButton loginFB;
	AccessToken accessToken;
	Profile profile;
	CallbackManager callbackManager;
	AdView adView;
	RelativeLayout L1;

	@Override
	protected void onPause() {
		super.onPause();
		AppEventsLogger.deactivateApp(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		AppEventsLogger.activateApp(this);
	}

	/*@Override
	protected void onStop()
	{
		unregisterReceiver(callbackManager);
		this.unregisterReceiver(this);
		super.onStop();
	}*/

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FacebookSdk.sdkInitialize(getApplicationContext());
		setContentView(R.layout.menu_view);
		callbackManager = CallbackManager.Factory.create();
		printFBKeyHash();
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new FallingPresentsGame(this, this), config);
		//defineAdLayout(gameView);
		newLayout(gameView);
		initializeFBButton(callbackManager);
	}

	public void newLayout(View gameView){
		L1 = (RelativeLayout)findViewById(R.id.L1);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);

		L1.addView(gameView, params);
		initAd();
		initializeAppodeal();
		gameView.bringToFront();
		adView.bringToFront();
		//loginFB.bringToFront();
		//adView.bringToFront();
	}

	public void initAd(){
		adView = (AdView) findViewById(R.id.adView4);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
	}

	private void initializeAppodeal(){
		String appKey = "93ab8c01d9c1f8ef98d534fec4465d746bbf6f675be62c7a";
		Appodeal.initialize(this, appKey, Appodeal.BANNER);
		Appodeal.setTesting(true);
		Appodeal.setLogging(true);
		//Appodeal.show(AndroidLauncher.this, Appodeal.BANNER);
	}

	public void share(String type, String caption){

		// Create the new Intent using the 'Send' action.
		Intent share = new Intent(Intent.ACTION_SEND);

		// Set the MIME type
		share.setType(type);
		// Add the URI and the caption to the Intent.
		share.putExtra(Intent.EXTRA_TEXT, caption);

		// Broadcast the Intent.
		startActivity(Intent.createChooser(share, "Share to"));
	}

	private void printFBKeyHash(){
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					"com.dualtech.fallingpresents.android",
					PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (PackageManager.NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}
	}

	private void initializeFBButton(CallbackManager callbackManager){
		loginFB = (LoginButton) findViewById(R.id.login_button);
		loginFB.bringToFront();
		loginFB.setReadPermissions("user_friends");
		// If using in a fragment
		//loginFB.setFragment(this);
		// Other app specific specialization

		// Callback registration
		loginFB.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
			@Override
			public void onSuccess(LoginResult loginResult) {
				// App code

				profile = Profile.getCurrentProfile();
			}

			@Override
			public void onCancel() {
				// App code
			}

			@Override
			public void onError(FacebookException error) {

			}
		});
		ArrayList list = new ArrayList<String>();
		list.add("publish_actions");
		LoginManager.getInstance().logInWithPublishPermissions(this, list);
		LoginManager.getInstance().registerCallback(callbackManager,
				new FacebookCallback<LoginResult>() {
					@Override
					public void onSuccess(LoginResult loginResult) {
						// App code
						//loginResult.getAccessToken();
					}

					@Override
					public void onCancel() {
						// App code
					}

					@Override
					public void onError(FacebookException exception) {
						// App code
					}
				});
	}

	@Override
	public void showBannerAd() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				adView.setVisibility(View.VISIBLE);
				/*AdRequest.Builder builder = new AdRequest.Builder();
				AdRequest ad = builder.build();
				adView.loadAd(ad);*/
				Appodeal.show(AndroidLauncher.this, Appodeal.BANNER);
			}
		});
	}

	@Override
	public void hideBannerAd() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				adView.setVisibility(View.INVISIBLE);
				Appodeal.hide(AndroidLauncher.this, Appodeal.BANNER);
			}
		});
	}

	//Checks whether to load ad or not
	@Override
	public boolean isWifiConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		return (ni != null && ni.isConnected());
	}

	@Override
	public void shareScore() {
		String text = "#HolidaysSpree\nI'm collecting gifts from Cupid\n What about you?\n " +
				"Download from https://play.google.com/store/apps/details?id=com.dualtech.fallingpresents.android";
		share("text/plain", text);
	}

	@Override
	public void shareScore(long score) {
		String text = "#HolidaysSpree\nI have collected " + score + " gifts\nWhat about you?\n" +
				"Download from https://play.google.com/store/apps/details?id=com.dualtech.fallingpresents.android";
		share("text/plain", text);
	}

	@Override
	public void postFacebookScore(long score) {
		Bundle params = new Bundle();
		params.putString("score", Long.toString(score));
/* make the API call */
		profile = Profile.getCurrentProfile();
		new GraphRequest(
				AccessToken.getCurrentAccessToken(),
				"/" + profile.getId() + "/scores",
				params,
				HttpMethod.POST,
				new GraphRequest.Callback() {
					public void onCompleted(GraphResponse response) {
            /* handle the result */
						Log.d("Post Score", response.toString());
					}
				}
		).executeAsync();
	}

	@Override
	public boolean isLoggedInFB() {
		AccessToken accessToken = AccessToken.getCurrentAccessToken();
		return accessToken != null;
	}

	@Override
	public void hideFbButton() {
		//loginFB.setVisibility(View.INVISIBLE);
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				loginFB.setVisibility(View.INVISIBLE);
			}
		});

	}

	@Override
	public void showFbButton() {
		//loginFB.setVisibility(View.VISIBLE);
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				loginFB.setVisibility(View.VISIBLE);
			}
		});
	}

	@Override
	public ArrayList<HashMap<String, Integer>> postLeaderboard() {
/* make the API call */
		final ArrayList<HashMap<String, Integer>> list = new ArrayList<>();
		Leaderboard.profilePics = new ArrayList<>();
		new GraphRequest(
				AccessToken.getCurrentAccessToken(),
				"/" + FB_APP_ID + "/scores",
				null,
				HttpMethod.GET,
				new GraphRequest.Callback() {
					public void onCompleted(GraphResponse response) {
            /* handle the result */
						Log.d("LeaderBoard ting", response.toString());
						JSONObject j = response.getJSONObject();
						JSONArray jsonArray = null;
						try {
							jsonArray = j.getJSONArray("data");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						for (int i=0; i< jsonArray.length(); i++) {
							try {
								String name = "";
								JSONObject jsonobject = (JSONObject) jsonArray.get(i);
								JSONObject user = jsonobject.getJSONObject("user");
								final int score = jsonobject.optInt("score");
								String id = user.optString("id");
								name = user.optString("name");
								final String theName = name;
								list.add(
										new HashMap<String, Integer>(){{
											put(theName, score);
										}}
								);
								Leaderboard.profilePics.add(getProfilePics(id));
							} catch (JSONException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
		).executeAndWait();

		return list;
	}

	@Override
	public void startLeaderboardActivity() {
		Leaderboard.leaderboardArray = postLeaderboard();
		Intent i = new Intent(this, Leaderboard.class);
		startActivity(i);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}

	public Bitmap getProfilePics(String id) throws IOException {
		URL url = new URL("https://graph.facebook.com/" + id + "/picture?type=small");
		Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
		return image;
	}
}
