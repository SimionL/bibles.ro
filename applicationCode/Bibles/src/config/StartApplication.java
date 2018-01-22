package config;

import java.nio.charset.StandardCharsets;
import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class StartApplication implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) {

		if(servletContext != null){

			AnnotationConfigWebApplicationContext rootAppContext = new AnnotationConfigWebApplicationContext();
			rootAppContext.register(ApplicationContextConfig.class);
			servletContext.addListener(new ContextLoaderListener(rootAppContext));
			servletContext.addListener(new AppHttpSessionListener());
			ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(rootAppContext));
			dispatcher.setLoadOnStartup(1);
			dispatcher.addMapping("/");

			Dynamic filter = servletContext.addFilter("encodingFilter",  new CharacterEncodingFilter());

			if(filter != null){

				filter.setAsyncSupported(true);
				filter.setInitParameter("encoding", StandardCharsets.UTF_8.toString());
				filter.setInitParameter("forceEncoding", "true");
				filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR, DispatcherType.ASYNC), true, "/*");
			}
		}
	}
}