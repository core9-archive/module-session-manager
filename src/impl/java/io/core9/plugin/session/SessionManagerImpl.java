package io.core9.plugin.session;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import io.core9.plugin.cache.Core9Cache;
import io.core9.plugin.server.request.Request;
import io.core9.plugin.server.vertx.VertxServer;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

@PluginImplementation
public class SessionManagerImpl implements SessionManager {

	@InjectPlugin
	private Core9Cache cache;



	@Override
	public Session getVertxSession(Request req, VertxServer server) {
			return new VertxSessionImpl(req, server, cache);
	}

	@Override
	public Subject getCurrentUser(Session session) {
		
		Subject currentUser = null;
		
		try {
			currentUser = (Subject) session.get(session.getSessionId() + "currentUser");	        
        } catch (Exception e) {
	        // TODO: handle exception
        }

		if(currentUser == null){
			Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
			SecurityManager securityManager = factory.getInstance();
			SecurityUtils.setSecurityManager(securityManager);
			currentUser = SecurityUtils.getSubject();
			session.put(session.getSessionId() + "currentUser", currentUser);
		}
		

		return currentUser;

	}

}
