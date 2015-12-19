package com.dualtech.fallingpresents;

/**
 * Created by tunde_000 on 19/12/2015.
 */
public interface ActionResolver {
    void signIn();
    void signOut();
    void rateGame();
    void submitScore(long score);
    void showScores();
    boolean isSignedIn();
}
