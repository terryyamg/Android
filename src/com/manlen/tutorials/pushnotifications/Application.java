package com.manlen.tutorials.pushnotifications;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class Application extends android.app.Application {

  public Application() {
  }

  @Override
  public void onCreate() {
    super.onCreate();

	// Initialize the Parse SDK.
	Parse.initialize(this, "iWV39LCi0SMlLgQ9kaMrVmVVK0rtKAnB7HGclG1g", "nLpb66XGZPofY0lHn5J3MQKeODrOEwHD6ibKik51"); 

	// Specify an Activity to handle all pushes by default.
	PushService.setDefaultPushCallback(this, MainActivity.class);
  }
}