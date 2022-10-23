package config;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class AppHttpSessionListener implements HttpSessionListener {

	@Override
	@EventListener
	public void sessionCreated(HttpSessionEvent event) {

		if(event != null){
			HttpSession session = event.getSession();

			if(session != null){
				session.setMaxInactiveInterval(240*60);
			}
		}
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
	}
}