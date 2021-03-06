package pl.edu.agh.pmpquiz.guice;

import android.app.Application;

import roboguice.RoboGuice;

/**
 *
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RoboGuice.setBaseApplicationInjector(this, RoboGuice.DEFAULT_STAGE,
                RoboGuice.newDefaultRoboModule(this), new MyModule());
    }
}