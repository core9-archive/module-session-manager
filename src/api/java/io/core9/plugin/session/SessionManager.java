package io.core9.plugin.session;


import org.apache.shiro.subject.Subject;

import io.core9.core.plugin.Core9Plugin;
import io.core9.plugin.server.request.Request;
import io.core9.plugin.server.vertx.VertxServer;

public interface SessionManager extends Core9Plugin {

	Session getVertxSession(Request req, VertxServer server);

	Subject getCurrentUser(Session session);

}
