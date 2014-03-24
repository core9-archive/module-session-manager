package io.core9.plugin.session;

import io.core9.plugin.cache.Core9Cache;

public interface Session {

	String getSessionId();

	Core9Cache getCache();

	void put(String key, Object value);

	Object get(String key);

	void delete(String key);

}
