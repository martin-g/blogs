
var getIframe = function() {
	return $q('#applicationFrame');
}

var onPageLoad = function(toExecute) {

	getIframe()
		.off('load')
		.on('load', function() {
			$q(this).off('load');

			var newIframe, $;

			newIframe = window.frames[0];
			$ = newIframe.jQuery || jQueryWithContext;

			//debug(newIframe);

			toExecute.call(newIframe, $);
		});
};

/**
 * Non-Ajax pages do not have jQuery so we use
 * $q with context to simulate it
 */
var jQueryWithContext = function(selector) {
	return $q(selector, $q(getIframe()).contents());
};

/**
 * Registers a callback when Wicket Ajax call is completed
 */
var onAjaxComplete = function(iframe, toExecute) {
	
	// unregister any leaked subscriber
	iframe.jQuery(iframe.document).off('/ajax/call/complete');
	
	// register the requested subscriber
	iframe.Wicket.Event.subscribe('/ajax/call/complete', function(jqEvent, attributes, jqXHR, textStatus) {
		// immediately unregister this subscriber
		iframe.jQuery(iframe.document).off('/ajax/call/complete');
		
		// call back
		toExecute.call(iframe);
	});
};

var followHref = function(iframe, $, $link) {
	var loc = iframe.document.location;
//	console.log('Current url', loc.href);
//	console.log('Link', $link.selector);

	if ($link.length) {
		var newUrl = $link.attr('href');
//		console.log('Following href: ', newUrl);
		loc.replace(newUrl);
	}
}

var debug = function(iframe) {
	"use strict";

	console.log('Current url: ', iframe.window.location.href);
}
