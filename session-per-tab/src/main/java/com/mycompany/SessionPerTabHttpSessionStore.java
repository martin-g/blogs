package com.mycompany;

import java.util.UUID;

import javax.servlet.http.Cookie;

import org.apache.wicket.MetaDataKey;
import org.apache.wicket.Session;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.session.HttpSessionStore;

/**
 *
 */
public class SessionPerTabHttpSessionStore extends HttpSessionStore
{
	static final MetaDataKey<String> WINDOW_NAME_KEY = new MetaDataKey<String>()
	{
	};

	@Override
	protected Session getWicketSession(Request request)
	{
		final String windowName = getWindowName(request);
		if (windowName != null) {
			String attributeName = Session.SESSION_ATTRIBUTE_NAME + ":" + windowName;
			return (Session) getAttribute(request, attributeName);
		}

		return super.getWicketSession(request);
	}

	@Override
	protected void setWicketSession(Request request, Session session)
	{
		final String windowName = getWindowName(request);
		if (windowName != null) {
			String attributeName = Session.SESSION_ATTRIBUTE_NAME + ":" + windowName;
			setAttribute(request, attributeName, session);
			return;
		}

		super.setWicketSession(request, session);
	}

	private String getWindowName(final Request request) {

		final RequestCycle cycle = RequestCycle.get();
		String windowName = cycle.getMetaData(WINDOW_NAME_KEY);
		if (windowName == null)
		{
			final Cookie cookie = ((WebRequest) request).getCookie(SessionPerTabBehavior.WICKET_TAB_COOKIE_NAME);
			if (cookie != null)
			{
				windowName = cookie.getValue();
			} else {
				windowName = UUID.randomUUID().toString();
			}
			cycle.setMetaData(WINDOW_NAME_KEY, windowName);
		}

		return windowName;
	}
}
