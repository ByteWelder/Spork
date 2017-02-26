package spork.benchmark.android;

import spork.benchmark.binders.FieldBinder1;
import spork.benchmark.binders.FieldBinder2;
import spork.Spork;

public class Application extends android.app.Application {
	@Override
	public void onCreate() {
		super.onCreate();

		Spork.sharedInstance().getBinderRegistry().register(new FieldBinder1());
		Spork.sharedInstance().getBinderRegistry().register(new FieldBinder2());
	}
}
