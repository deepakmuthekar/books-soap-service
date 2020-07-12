package com.github.deepakmuthekar.books.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.github.deepakmuthekar.books.endpoints.RecordNotFoundFault;
import com.github.deepakmuthekar.schema.Book;

@Service
public class BookService {

	private Map<String, Book> books = new ConcurrentHashMap<>();

	@PostConstruct
	public void bootstrap() {
		Book ijvm = new Book();
		ijvm.setId("Inside Java Virtual Machine");
		ijvm.setAuthor("Bill Venners");
		ijvm.setPrice(23.67);
		ijvm.setIsbn("978-0071350938");

		Book pp = new Book();
		pp.setId("The Pragmatic Programmer");
		pp.setAuthor("Andrew Hunt");
		pp.setPrice(27.00);
		pp.setIsbn("978-0201616224");

		books.put("978-0071350938", ijvm);
		books.put("978-0201616224", pp);
	}

	public Book getBookByIsbn(String isbn) {
		if(this.books.containsKey(isbn))
			return this.books.get(isbn);
		else 
			throw new RecordNotFoundFault("Book with ISBN "+ isbn +" doesn't exists");
	}
}
