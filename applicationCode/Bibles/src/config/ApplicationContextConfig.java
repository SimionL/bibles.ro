package config;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import beans.ApplicationCode;
import beans.Bible;
import beans.Church;
import beans.Feedback;
import beans.Popup;
import beans.Reference;
import beans.Screensaver;
import beans.Settings;
import beans.ThankYou;
import beans.Voice;
import dbBeans.ChurchTable;
import dbBeans.Event;
import dbBeans.Language;
import dbBeans.MessageTable;
import dbBeans.Participant;
import dbBeans.ScreensaverTable;
import utilities.AppBean;
import utilities.Constant;

@EnableWebMvc
@Configuration
@EnableScheduling
@EnableTransactionManagement
@ComponentScan(basePackages = {"dao","beans","config","controller"})
public class ApplicationContextConfig implements WebMvcConfigurer{

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Scope("prototype")
	@Bean(name = "messageConverter")
	public StringHttpMessageConverter getMessageConverter() {
		StringHttpMessageConverter messageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		messageConverter.setDefaultCharset(Charset.forName("UTF-8"));

		return messageConverter;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/" );
	}

	@Scope("prototype")
	@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setViewClass(JstlView.class);
		return viewResolver;
	}

	@Scope("prototype")
	@Bean(name = "messageSource")
	public ResourceBundleMessageSource getResourceBundle() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		messageSource.setDefaultEncoding("UTF-8");

		return messageSource;
	}

	@Scope("prototype")
	@Bean
	public DataSource getDataSource() {

		final DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(Constant.driver);
		dataSource.setUrl(Constant.dburl);
		dataSource.setUsername(Constant.username);
		dataSource.setPassword(Constant.password);

		Properties properties = dataSource.getConnectionProperties();

		if(properties == null){
			properties = new Properties();
		}

		if(properties != null){
			properties.setProperty("charSet",                       "UTF-8");
			properties.setProperty("hostRecheckSeconds",            "100");
			properties.setProperty("connectTimeout",                "999");
			properties.setProperty("socketTimeout",                 "999");
			properties.setProperty("loginTimeout",                  "99999");
			properties.setProperty("preparedStatementCacheQueries", "999999");
			properties.setProperty("preparedStatementCacheSizeMiB", "999999");
			properties.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
			properties.setProperty("hibernate.jdbc.lob.non_contextual_creation", "true");
		}

		dataSource.setConnectionProperties(properties);

		return dataSource;
	}

	@Bean
	public SessionFactory getSessionFactory(DataSource dataSource) {

		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);

		sessionBuilder.addAnnotatedClasses(Language.class, ChurchTable.class, Event.class, Participant.class, ScreensaverTable.class, MessageTable.class);

		return sessionBuilder.buildSessionFactory();
	}

	@Scope("prototype")
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {

		return new HibernateTransactionManager(sessionFactory);
	}

	@Scope("prototype")
	@Bean(name="multipartResolver")
	public CommonsMultipartResolver getResolver() throws IOException{
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setMaxInMemorySize(2140000000);

		resolver.getFileUpload().setFileSizeMax(-1);
		resolver.getFileUpload().setSizeMax(-1);
		return resolver;
	}

	@Scope("session")
	@Bean(name="bible")
	public Bible getBible(){

		final Bible bible = new Bible();

		return bible;
	}

	@Scope("session")
	@Bean(name="settings")
	public Settings getSettings(){

		final Settings settings = new Settings();

		return settings;
	}

	@Scope("session")
	@Bean(name="popup")
	public Popup getPopup(){

		final Popup popup = new Popup();

		return popup;
	}

	@Scope("session")
	@Bean(name="reference")
	public Reference getReference(){

		final Reference reference = new Reference();

		return reference;
	}

	@Scope("session")
	@Bean(name="church")
	public Church getChurch(){

		final Church church = new Church();

		return church;
	}

	@Scope("session")
	@Bean(name="screensaver")
	public Screensaver getScreensaver(){

		final Screensaver screensaver = new Screensaver();

		return screensaver;
	}

	@Scope("session")
	@Bean(name="code")
	public ApplicationCode getApplicationCode(){

		final ApplicationCode code = new ApplicationCode();

		return code;
	}

	@Scope("session")
	@Bean(name="voice")
	public Voice getVoice(){

		final Voice voice = new Voice();

		return voice;
	}

	@Scope("session")
	@Bean(name="app")
	public AppBean getAppBean(){

		final AppBean app = new AppBean();

		return app;
	}

	@Scope("session")
	@Bean(name="thankYou")
	public ThankYou getThankYou(){

		final ThankYou thankYou = new ThankYou();

		return thankYou;
	}

	@Scope("session")
	@Bean(name="feedback")
	public Feedback getFeedback(){

		final Feedback feedback = new Feedback();

		return feedback;
	}
}