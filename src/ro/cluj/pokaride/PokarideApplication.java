package ro.cluj.pokaride;

import android.app.Application;
import android.content.Context;

public class PokarideApplication extends Application {
	private static PokarideApplication _instance;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public PokarideApplication() {
		_instance = this;
	}

	public static Context getContext() {
		return _instance;
	}

}
