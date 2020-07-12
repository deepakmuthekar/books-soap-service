package com.github.deepakmuthekar.books.config;

import java.io.StringWriter;
import java.util.UUID;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.xml.transform.TransformerHelper;

public class MDCInterceptor implements EndpointInterceptor {

	private static Logger logger = LoggerFactory.getLogger(MDCInterceptor.class);
	private TransformerHelper transformerHelper = new TransformerHelper();

	@Override
	public boolean handleRequest(MessageContext messageContext, Object endpoint) throws TransformerException {
		MDC.clear();
		MDC.put("request.id", UUID.randomUUID().toString());
		logger.info("Received SOAP Request: ");
		this.log(messageContext.getRequest().getPayloadSource());
		return true;
	}

	@Override
	public boolean handleResponse(MessageContext messageContext, Object endpoint) throws Exception {
		logger.info("Outgoing SOAP Response: ");
		this.log(messageContext.getResponse().getPayloadSource());
		return true;
	}

	@Override
	public boolean handleFault(MessageContext messageContext, Object endpoint) throws Exception {
		logger.info("Outgoing SOAP Fault: ");
		this.log(messageContext.getResponse().getPayloadSource());
		return true;
	}

	@Override
	public void afterCompletion(MessageContext messageContext, Object endpoint, Exception ex) throws Exception {
		logger.info("Inside afterCompletion()");
		MDC.clear();
	}

	private void log(Source source) throws TransformerException {
		Transformer transformer = createNonIndentingTransformer();
		StringWriter writer = new StringWriter();
		transformer.transform(source, new StreamResult(writer));
		logger.info(writer.toString());
	}

	private Transformer createNonIndentingTransformer() throws TransformerConfigurationException {
		Transformer transformer = createTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.setOutputProperty(OutputKeys.INDENT, "no");
		return transformer;
	}

	private Transformer createTransformer() throws TransformerConfigurationException {
		return this.transformerHelper.createTransformer();
	}
}
