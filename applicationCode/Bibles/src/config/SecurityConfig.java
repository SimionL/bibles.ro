package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		if(http != null){

			CsrfConfigurer<HttpSecurity> csrf = http.csrf();

			if(csrf != null){
				csrf.disable();
			}

			SessionManagementConfigurer<HttpSecurity> sessionManagement = http.sessionManagement();

			if(sessionManagement != null){
				sessionManagement.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
				sessionManagement.maximumSessions(1);
				sessionManagement.sessionFixation().none();	
			}

			http.authorizeRequests().
			antMatchers(HttpMethod.POST, "/","/*","/**").permitAll().
			antMatchers(HttpMethod.GET,  "/","/*","/**").permitAll().
			anyRequest().permitAll().and().
			httpBasic().and().rememberMe().alwaysRemember(true);
		}
	}
}