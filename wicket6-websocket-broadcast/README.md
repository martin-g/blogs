Example application for Web Socket broadcast
============================================

Compiling
---------
Must be compiled against wicket in https://github.com/NitorCreations/wicket.

Functionality
------------
Contains two pages:
- FeedPage - shows a content feed (like Twitter). Page shows 5 latest items.
- AddContentPage - contains a form to add messages to the feed.

One periodic task running asynchronously
- TimestampTask - sends a timestamp message periodically to feeds.

Incoming messages are broadcasted as normal wicket events to pages that have web socket connections. 
FeedPage catches this event and pushes content to browser via web connection.

You can open many FeedPages and AddContentPages in separate browsers.   