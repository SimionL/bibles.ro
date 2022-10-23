package config;

import java.nio.charset.Charset;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import beans.Book;
import beans.Chapter;
import beans.Language;
import beans.Version;
import dao.DAOImplTool;
import dao.DAOTool;
import utilities.Constants;

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

	@Bean(name = "messageConverter")
	public StringHttpMessageConverter getMessageConverter() {
		StringHttpMessageConverter messageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		messageConverter.setDefaultCharset(Charset.forName("UTF-8"));

		return messageConverter;
	}

	@Bean(name = "viewResolver")
	public InternalResourceViewResolver getViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/jsp/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setViewClass(JstlView.class);
		return viewResolver;
	}

	@Bean(name = "messageSource")
	public ResourceBundleMessageSource getResourceBundle() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		messageSource.setDefaultEncoding("UTF-8");

		return messageSource;
	}

	@Bean(name = "dataSource")
	public DataSource getDataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(Constants.driver.value);
		dataSource.setUrl(Constants.dburl.value);
		dataSource.setUsername(Constants.username.value);
		dataSource.setPassword(Constants.password.value);

		Properties properties = dataSource.getConnectionProperties();

		if(properties != null){
			properties.setProperty("useUnicode", "true");
			properties.setProperty("characterEncoding", "UTF-8");
			properties.setProperty("charSet", "UTF-8");
			properties.setProperty("characterSetResults", "UTF-8");

			properties.setProperty("hibernate.dbcp.initialSize", "10000");
			properties.setProperty("hibernate.dbcp.maxActive", "10000");
			properties.setProperty("hibernate.dbcp.maxIdle", "10000");
			properties.setProperty("hibernate.dbcp.minIdle", "0");
			properties.setProperty("hibernate.c3p0.min_size", "10000");
			properties.setProperty("hibernate.c3p0.max_size", "10000");
			properties.setProperty("hibernate.c3p0.timeout", "10000");
			properties.setProperty("hibernate.c3p0.max_statements", "100000");
			//properties.setProperty("hibernate.c3p0.idle_test_period", "10000");

		}

		return dataSource;
	}

	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {

		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);

		sessionBuilder.addAnnotatedClasses(Chapter.class, Book.class, Version.class, Language.class);

		return sessionBuilder.buildSessionFactory();
	}

	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {

		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);

		return transactionManager;
	}

	@Bean(name = "dao")
	public DAOTool getDao(SessionFactory sessionFactory) {

		DAOImplTool dI = new DAOImplTool();
		dI.setSessionFactory(sessionFactory);

		return dI;
	}
}