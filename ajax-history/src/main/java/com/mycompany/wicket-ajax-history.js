;(function ($, undefined) {

    'use strict';

    Wicket.Event.subscribe(Wicket.Event.Topic.AJAX_CALL_BEFORE_SEND, function(jqEvent, attrs) {
        if (attrs.event.extraData !== "wicket-popstate") {
            var $link = $('#' + attrs.c);
            var url = $link.attr('data-history-url');
            if (url) {
                var title = $link.attr('data-history-title');
                delete attrs.event;
                history.pushState(attrs, title, url);
            }
        }
    });

    window.onpopstate = function(event) {
        var state = event.state;
        $('#' + state.c).trigger(state.e, ['wicket-popstate']);
    }

})(jQuery);
