package com.dualtech.fallingpresents.android;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.dualtech.fallingpresents.ActionResolver;
import com.dualtech.fallingpresents.ActivityMethods;
import com.dualtech.fallingpresents.AdsController;
import com.dualtech.fallingpresents.FallingPresentsGame;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;

public class AndroidLauncher extends AndroidApplication implements ActionResolver, AdsController, ActivityMethods {
	GameHelper gameHelper;
	private final static int REQUEST_CODE_UNUSED = 9002;
	private static final String BANNER_AD_UNIT_ID = "ca-app-pub-6044705985167929/8567710899";
	private static final String BANNER_TEST = "ca-app-pub-3940256099942544/6300978111";
	AdView bannerAd;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Create the GameHelper.
		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		gameHelper.enableDebugLog(false);
		GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
		{
			@Override
			public void onSignInSucceeded()
			{
			}

			@Override
			public void onSignInFailed()
			{
			}
		};
		gameHelper.setMaxAutoSignInAttempts(0);
		gameHelper.setup(gameHelperListener);
		setupAds();

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize(new FallingPresentsGame(this), config);
		View gameView = initializeForView(new FallingPresentsGame(this, this, this), config);
		defineAdLayout(gameView);
	}

	public void defineAdLayout(View gameView){
		RelativeLayout layout = new RelativeLayout(this);
		layout.addView(gameView, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		layout.addView(bannerAd, params);

		setContentView(layout);
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

	public void setupAds() {
		bannerAd = new AdView(this);
		bannerAd.setVisibility(View.INVISIBLE);
		bannerAd.setBackgroundColor(0xff000000); // black
		//bannerAd.setAdUnitId(BANNER_AD_UNIT_ID);
		bannerAd.setAdUnitId(BANNER_TEST);
		bannerAd.setAdSize(AdSize.SMART_BANNER);
	}

	//Sign in to Google Play
	@Override
	public void signIn() {
		try
		{
			runOnUiThread(new Runnable()
			{
				//@Override
				public void run()
				{
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void signOut() {
		try
		{
			runOnUiThread(new Runnable()
			{
				//@Override
				public void run()
				{
					gameHelper.signOut();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void rateGame() {}

	//Submit score to leaderboard
	@Override
	public void submitScore(long score) {
		if (isSignedIn())
		{
			Games.Leaderboards.submitScore(gameHelper.getApiClient(), getString(R.string.leaderboard_id), score);
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), getString(R.string.leaderboard_id)), REQUEST_CODE_UNUSED);
		}
		else
		{
// Maybe sign in here then redirect to submitting score?
			signIn();
			submitScore(score);
			//Games.Leaderboards.submitScore(gameHelper.getApiClient(), getString(R.string.leaderboard_id), score);
			//startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), getString(R.string.leaderboard_id)), REQUEST_CODE_UNUSED);
		}
	}

	@Override
	public void showScores() {
		if (isSignedIn())
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), getString(R.string.leaderboard_id)), REQUEST_CODE_UNUSED);
		else
		{
// Maybe sign in here then redirect to showing scores?
			signIn();
			showScores();
			//startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(), getString(R.string.leaderboard_id)), REQUEST_CODE_UNUSED);
		}
	}

	@Override
	public boolean isSignedIn() {
		return gameHelper.isSignedIn();
	}

	@Override
	public void showBannerAd() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				bannerAd.setVisibility(View.VISIBLE);
				AdRequest.Builder builder = new AdRequest.Builder();
				AdRequest ad = builder.build();
				bannerAd.loadAd(ad);
			}
		});
	}

	@Override
	public void hideBannerAd() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				bannerAd.setVisibility(View.INVISIBLE);
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
		String text = "#FallingPresents\nI'm collecting presents from Santa\n What about you?\n " +
				"Download from https://play.google.com/store/apps/details?id=com.dualtech.fallingpresents.android";
		share("text/plain", text);
	}

	@Override
	public void shareScore(long score) {
		String text = "#FallingPresents\nI have collected " + score + " presents\nWhat about you?\n" +
				"Download from https://play.google.com/store/apps/details?id=com.dualtech.fallingpresents.android";
		share("text/plain", text);
	}
}
