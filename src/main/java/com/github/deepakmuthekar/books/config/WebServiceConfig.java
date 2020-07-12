package com.github.deepakmuthekar.books.config;

import java.util.List;
import java.util.Properties;

import javax.security.auth.callback.CallbackHandler;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.interceptor.PayloadLoggingInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;


@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext ctx) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(ctx);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<>(servlet, "/ws/*");
	}

	@Bean
	public XsdSchema schema() {
		return new SimpleXsdSchema(new ClassPathResource("book.xsd"));
	}

	@Bean(name = "books")
	public DefaultWsdl11Definition wsdlDefinition(XsdSchema schema) {
		DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
		definition.setPortTypeName("BookPort");
		definition.setTargetNamespace("http://deepakmuthekar.github.com/book-request");
		definition.setLocationUri("/ws");
		definition.setSchema(schema);
		return definition;
	}
	
	@Bean
	public XwsSecurityInterceptor securityInterceptor() {
		XwsSecurityInterceptor xsi = new XwsSecurityInterceptor();
		xsi.setCallbackHandler(callbackHandler());
		xsi.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
		return xsi;
	}

	@Bean
	public CallbackHandler callbackHandler() {
		Properties users = new Properties();
		users.put("user", "password");
		users.put("deepak", "password");
		SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler();
		handler.setUsers(users);
 		return handler;
	}

	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		
		
		MDCInterceptor mdcInterceptor = new MDCInterceptor();
	    interceptors.add(mdcInterceptor);
	    
	    interceptors.add(securityInterceptor());
	
	    PayloadLoggingInterceptor pli = new PayloadLoggingInterceptor();
	    interceptors.add(pli);
	    
		PayloadValidatingInterceptor validatingInterceptor = new PayloadValidatingInterceptor();
		validatingInterceptor.setValidateRequest(true);
		validatingInterceptor.setValidateResponse(true);
		validatingInterceptor.setXsdSchema(schema());
	    interceptors.add(validatingInterceptor);
	}

}
