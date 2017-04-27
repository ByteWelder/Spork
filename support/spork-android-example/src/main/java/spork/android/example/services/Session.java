package spork.android.example.services;

/**
 * Simple session interface for SessionService.
 */
public interface Session {
	String getId();
	boolean isActive();
	void deactivate();
}
