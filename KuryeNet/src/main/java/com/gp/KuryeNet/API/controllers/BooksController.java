package com.gp.KuryeNet.API.controllers;
import jakarta.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import com.gp.KuryeNet.business.abstracts.BookService;

import reactor.core.publisher.Mono;

@RestController
@Validated
@RequestMapping(path = { "${api.base-path:/api}/books", "${api.versioned-base-path:/api/v1}/books" })
public class BooksController {

	private BookService bookService;

	@Autowired
	public BooksController(BookService bookService) {
		super();
		this.bookService = bookService;
	}
	
	@GetMapping("/getall")
    public Mono<ResponseEntity<?>> getAll() {
        return Mono.fromCallable(() -> bookService.getAll())
                .map(result -> {
                	if (result != null) {
        	            return ResponseEntity.ok(result);
        	        } else {
        	            return ResponseEntity.badRequest().body("book is not get");
        	        }
                });
    }
	
	@GetMapping("/getByPageNumber")
    public Mono<ResponseEntity<?>> getByPageNumber(@RequestParam @Min(0) int pageNumber ) {
        return Mono.fromCallable(() -> bookService.getByPageNumber(pageNumber))
                .map(result -> {
                	if (result != null) {
        	            return ResponseEntity.ok(result);
        	        } else {
        	            return ResponseEntity.badRequest().body("book is not get");
        	        }
                });
    }
	
}

