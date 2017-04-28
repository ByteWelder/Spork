package spork.android.example.test.rules;

import android.support.test.espresso.intent.Intents;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * A test rule that initiates espresso-intents immediately rather than after launching an Activity.
 */
public class ImmediateIntentsTestRule implements TestRule {

	@Override
	public Statement apply(final Statement base, Description description) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				try {
					Intents.init();
					base.evaluate();
				} catch (Exception e) {
					Intents.release();
				}
			}
		};
	}
}
