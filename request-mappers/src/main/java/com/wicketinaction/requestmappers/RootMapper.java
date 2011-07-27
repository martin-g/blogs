package com.wicketinaction.requestmappers;

import java.util.List;

import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.IRequestMapper;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Url;

/**
 * A mapper that manipulates the incoming and outgoing Urls
 */
public class RootMapper implements IRequestMapper {

    private final IRequestMapper delegate;
    
    public RootMapper(IRequestMapper delegate) {
        this.delegate = delegate;
    }
    
    public IRequestHandler mapRequest(Request request) {
        Url url = request.getUrl();
        List<String> segments = url.getSegments();
        if (segments.size() > 0 && segments.get(0).equals("predefined")) {
            segments.remove(0);
        }
        
        return delegate.mapRequest(request.cloneWithUrl(url));
    }

    public int getCompatibilityScore(Request request) {
        return delegate.getCompatibilityScore(request);
    }

    public Url mapHandler(IRequestHandler requestHandler) {
        
        Url url = delegate.mapHandler(requestHandler);
        url.getSegments().add(0, "predefined");
        return url;
    }

}
