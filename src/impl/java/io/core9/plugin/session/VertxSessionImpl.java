package io.core9.plugin.session;

//import java.util.UUID;

import org.infinispan.Cache;

import io.core9.plugin.cache.Core9Cache;
import io.core9.plugin.server.Cookie;
import io.core9.plugin.server.request.Request;
import io.core9.plugin.server.vertx.VertxServer;

public class VertxSessionImpl implements Session {

	private Request request;
	private VertxServer server;
	private Core9Cache cache;
	private Cache<String, Object> infinispanCache;
	private String sessionId;

	public VertxSessionImpl(Request req, VertxServer server, Core9Cache cache) {
		this.request = req;
		this.server = server;
		this.cache = cache;
		this.infinispanCache = cache.getInfinispanCache();
		sessionId = getSessionId();
	}

	@Override
	public String getSessionId() {
		Cookie cookie = request.getCookie("CORE9SESSIONID");
		String id;
		if (cookie == null) {
			// id = UUID.randomUUID().toString();
			id = GUID.getUUID();
			Cookie newCookie = server.newCookie("CORE9SESSIONID", id);
			// FIXME not sure if path needs to default to / but for session
			// cookies this is true
			newCookie.setPath("/");
			request.getResponse().addCookie(newCookie);

		} else {
			id = cookie.getValue();
		}
		return id;
	}

	@Override
	public void put(String key, Object value) {
		infinispanCache.put(sessionId + key, value);
	}

	@Override
	public Object get(String key) {
		return infinispanCache.get(sessionId + key);
	}

	@Override
	public Core9Cache getCache() {
		return cache;
	}

	
	public void delete(String key) {
		infinispanCache.remove(sessionId + key);
	}

}
