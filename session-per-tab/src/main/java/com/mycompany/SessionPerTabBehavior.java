package com.mycompany;

import java.util.UUID;

import javax.servlet.http.Cookie;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.util.cookies.CookieUtils;

/**
 *
 */
public class SessionPerTabBehavior extends Behavior
{

	public static final String WICKET_TAB_COOKIE_NAME = "wicketTabName";

	@Override
	public void renderHead(Component component, IHeaderResponse response)
	{
		super.renderHead(component, response);

		response.render(JavaScriptHeaderItem.forReference(component.getApplication().getJavaScriptLibrarySettings().getJQueryReference()));

		final Cookie cookie = ((WebRequest) component.getRequest()).getCookie(WICKET_TAB_COOKIE_NAME);
		if (cookie != null) {
			CookieUtils cookieUtils = new CookieUtils();
			cookieUtils.remove(cookie.getName());
		} else {
			final RequestCycle cycle = component.getRequestCycle();
			String windowName = cycle.getMetaData(SessionPerTabHttpSessionStore.WINDOW_NAME_KEY);
			if (windowName == null) {
				windowName = UUID.randomUUID().toString();
			}
			final String javascript = String.format("sessionStorage.setItem('%s', '%s');", WICKET_TAB_COOKIE_NAME, windowName);
			response.render(OnDomReadyHeaderItem.forScript(javascript));
		}
		response.render(JavaScriptHeaderItem.forScript(String.format("$(window).on('beforeunload', function() {" +
				"document.cookie = \"%1$s=\" + sessionStorage.getItem('%1$s')});", WICKET_TAB_COOKIE_NAME),
				WICKET_TAB_COOKIE_NAME));
	}
}
