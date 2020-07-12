package com.github.deepakmuthekar.books.endpoints;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.github.deepakmuthekar.books.services.BookService;
import com.github.deepakmuthekar.schema.Book;
import com.github.deepakmuthekar.schema.GetBookRequest;
import com.github.deepakmuthekar.schema.GetBookResponse;

import lombok.AllArgsConstructor;

@Endpoint
@AllArgsConstructor
public class BooksEndpoint {

	private BookService service;

	@PayloadRoot(namespace = "http://deepakmuthekar.github.com/book-request", localPart  = "GetBookRequest")
	@ResponsePayload
	public GetBookResponse getBook(@RequestPayload GetBookRequest req) {
		Book bookByIsbn = this.service.getBookByIsbn(req.getIsbn());
		GetBookResponse response = new GetBookResponse();
		response.setBook(bookByIsbn);
		return response;
	}
}
