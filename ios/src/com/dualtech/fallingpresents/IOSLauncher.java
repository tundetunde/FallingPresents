package com.dualtech.fallingpresents;

import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.coregraphics.CGSize;
import org.robovm.apple.foundation.NSArray;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.foundation.NSString;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIScreen;
import org.robovm.pods.facebook.core.FBSDKProfile;
import org.robovm.pods.facebook.login.FBSDKLoginButton;
import org.robovm.pods.google.mobileads.GADBannerView;
import org.robovm.pods.google.mobileads.GADBannerViewDelegateAdapter;
import org.robovm.pods.google.mobileads.GADRequest;
import org.robovm.pods.google.mobileads.GADRequestError;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.badlogic.gdx.utils.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IOSLauncher extends IOSApplication.Delegate implements ActivityMethods, AdsController{

    private static final Logger log = new Logger(IOSLauncher.class.getName(), Application.LOG_DEBUG);
    private static final boolean USE_TEST_DEVICES = true;
    private GADBannerView adview;
    private boolean adsInitialized = false;
    private IOSApplication iosApplication;
    private FBSDKLoginButton loginFacebook;


    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        config.orientationLandscape = true;
        config.orientationPortrait = false;
        iosApplication = new IOSApplication(new FallingPresentsGame(this, this), config);
        return iosApplication;
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }

    public void initializeFB(){
        loginFacebook = new FBSDKLoginButton();
        ArrayList<String> permission = new ArrayList<>();
        permission.add("public_profile");
        permission.add("user_friends");
        permission.add("publish_actions");
        loginFacebook.setReadPermissions(permission);
        FBSDKProfile.enableUpdatesOnAccessTokenChange(true);
    }

    public void initializeAds() {
        if (!adsInitialized) {
            log.debug("Initalizing ads...");


            adsInitialized = true;

            adview = new GADBannerView();
            adview.setAdUnitID("ca-app-pub-3940256099942544/2934735716"); //put your secret key here
            adview.setRootViewController(iosApplication.getUIViewController());
            iosApplication.getUIViewController().getView().addSubview(adview);

            final GADRequest request = new GADRequest();
            if (USE_TEST_DEVICES) {
                final NSArray<?> testDevices = new NSArray<NSObject>(
                        new NSString(GADRequest.getSimulatorID()));
                request.setTestDevices(testDevices.asStringList());

                log.debug("Test devices: " + request.getTestDevices());
            }

            adview.setDelegate(new GADBannerViewDelegateAdapter() {
                @Override
                public void didReceiveAd(GADBannerView view) {
                    super.didReceiveAd(view);
                    log.debug("didReceiveAd");
                }

                @Override
                public void didFailToReceiveAd(GADBannerView view,
                                               GADRequestError error) {
                    super.didFailToReceiveAd(view, error);
                    log.debug("didFailToReceiveAd:" + error);
                }
            });

            adview.loadRequest(request);

            log.debug("Initalizing ads complete.");
        }
    }

    @Override
    public void shareScore() {

    }

    @Override
    public void shareScore(long score) {

    }

    @Override
    public void postFacebookScore(long score) {

    }

    @Override
    public boolean isLoggedInFB() {
        return false;
    }

    @Override
    public void hideFbButton() {

    }

    @Override
    public void showFbButton() {

    }

    @Override
    public ArrayList<HashMap<String, Integer>> postLeaderboard() {
        return null;
    }

    @Override
    public void startLeaderboardActivity() {

    }

    @Override
    public void showBannerAd() {
        initializeAds();
        final CGSize screenSize = UIScreen.getMainScreen().getBounds().getSize();
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getWidth();

        final CGSize adSize = adview.getBounds().getSize();
        double adWidth = adSize.getWidth();
        double adHeight = adSize.getHeight();

        log.debug(String.format("Showing ad. size[%s, %s]", adWidth, adHeight));
        log.debug(String.format("Showing screen. size[%s, %s]", screenWidth, screenHeight));

        float bannerWidth = (float) screenWidth;
        float bannerHeight = (float) screenHeight / 10;

        adview.setFrame(new CGRect((screenWidth / 2) - adWidth / 2, 0, bannerWidth, bannerHeight));
    }

    @Override
    public void hideBannerAd() {
        initializeAds();
        final CGSize screenSize = UIScreen.getMainScreen().getBounds().getSize();
        double screenWidth = screenSize.getWidth();

        final CGSize adSize = adview.getBounds().getSize();
        double adWidth = adSize.getWidth();
        double adHeight = adSize.getHeight();

        log.debug(String.format("Hidding ad. size[%s, %s]", adWidth, adHeight));

        float bannerWidth = (float) screenWidth;
        float bannerHeight = (float) 160;

        adview.setFrame(new CGRect(0, -bannerHeight, bannerWidth, bannerHeight));
    }

    @Override
    public boolean isWifiConnected() {
        return false;
    }
}