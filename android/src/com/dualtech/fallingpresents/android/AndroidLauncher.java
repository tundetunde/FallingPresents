package com.dualtech.fallingpresents.android;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.dualtech.fallingpresents.FallingPresentsGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new FallingPresentsGame(), config);
	}

	public void share(String type, String caption){

		// Create the new Intent using the 'Send' action.
		Intent share = new Intent(Intent.ACTION_SEND);

		// Set the MIME type
		share.setType(type);

		// Add the URI and the caption to the Intent.
		//share.putExtra(Intent.EXTRA_STREAM, uri);
		share.putExtra(Intent.EXTRA_TEXT, caption);

		// Broadcast the Intent.
		startActivity(Intent.createChooser(share, "Share to"));
	}
}
