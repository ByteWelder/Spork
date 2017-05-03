package spork.android.example.services;

import java.util.UUID;

import spork.android.example.concurrent.Callback;

/**
 * A dummy session service that uses an {@link ApiService} to execute session-related requests.
 */
public class SessionService {
	private Session currentSession;
	private final ApiService apiService;

	public SessionService(ApiService apiService) {
		this.apiService = apiService;
		this.currentSession = new InactiveSession();
	}

	public Session getCurentSession() {
		return currentSession;
	}

	/**
	 * Deactivate current session and start a new one.
	 * @return the new active session
	 */
	public void startNewSession(final Callback<Session> sessionCallback) {
		apiService.execute(currentSession, "/session/create", new Callback<HttpServiceImpl.Response>() {
			@Override
			public void onSuccess(HttpServiceImpl.Response object) {
				if (currentSession.isActive()) {
					currentSession.deactivate();
				}

				currentSession = new SessionImpl();

				sessionCallback.onSuccess(currentSession);
			}

			@Override
			public void onFailure(Exception caught) {
				sessionCallback.onFailure(new Exception("Failed to start new session", caught));
			}
		});
	}

	/**
	 * Deactivate a session.
	 */
	private void deactivate(final Session session) {
		apiService.execute(session, "/session/deactivate", new Callback<HttpServiceImpl.Response>() {
			@Override
			public void onSuccess(HttpServiceImpl.Response object) {
				// do nothing
			}

			@Override
			public void onFailure(Exception caught) {
				// do nothing
			}
		});
	}

	private class SessionImpl implements Session {
		private final UUID uuid;
		private boolean active = true;

		SessionImpl() {
			this.uuid = UUID.randomUUID();
		}

		@Override
		public String getId() {
			return uuid.toString();
		}

		@Override
		public boolean isActive() {
			return active;
		}

		@Override
		public void deactivate() {
			active = false;
			SessionService.this.deactivate(this);
		}
	}

	private static class InactiveSession implements Session {
		private final UUID uuid = UUID.randomUUID();

		@Override
		public String getId() {
			return uuid.toString();
		}

		@Override
		public boolean isActive() {
			return false;
		}

		@Override
		public void deactivate() {
		}
	}
}
