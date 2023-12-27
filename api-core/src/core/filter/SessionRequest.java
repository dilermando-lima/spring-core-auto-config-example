package core.filter;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionRequest {
    
    private String anyKeyAuthId;

    public String getAnyKeyAuthId() {
        return anyKeyAuthId;
    }

    public void setAnyKeyAuthId(String anyKeyAuthId) {
        this.anyKeyAuthId = anyKeyAuthId;
    }

}
