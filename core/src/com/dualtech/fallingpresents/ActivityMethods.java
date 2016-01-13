package com.dualtech.fallingpresents;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tunde_000 on 20/12/2015.
 */
public interface ActivityMethods {
    void shareScore();
    void shareScore(long score);
    void postFacebookScore(long score);
    boolean isLoggedInFB();
    void hideFbButton();
    void showFbButton();
    ArrayList<HashMap<String, Integer>> postLeaderboard();
    void startLeaderboardActivity();
}
