package com.github.deepakmuthekar.books.endpoints;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SuppressWarnings("serial")
@SoapFault(faultCode = FaultCode.CLIENT)
public class RecordNotFoundFault extends RuntimeException {

	public RecordNotFoundFault(String message) {
		super(message);
	}
}
