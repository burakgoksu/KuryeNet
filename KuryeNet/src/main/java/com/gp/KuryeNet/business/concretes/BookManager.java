package com.gp.KuryeNet.business.concretes;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gp.KuryeNet.business.abstracts.BookService;



@Service
public class BookManager implements BookService{

	@Override
	public Object getAll() {
        try {
            // Hedef URL
            String url = "https://gutendex.com/books";

            // HTTP isteği oluştur
            HttpClient client = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")  // Buraya kendi User-Agent bilginizi ekleyin
                    .build();

            // Yanıtı al
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // HTTP başarı durumunu kontrol et
            if (response.statusCode() == 200) {
                // JSON verisini işle
                String resultList = response.body();
                return resultList;
            } else {
                return "error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
	}

	@Override
	public Object getByPageNumber(int pageNumber) {
		try {
            // Hedef URL
            String url = "https://gutendex.com/books/?page="+pageNumber;

            // HTTP isteği oluştur
            HttpClient client = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .build();
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")  // Buraya kendi User-Agent bilginizi ekleyin
                    .build();

            // Yanıtı al
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // HTTP başarı durumunu kontrol et
            if (response.statusCode() == 200) {
                // JSON verisini işle
                String resultList = response.body();
                return resultList;
            } else {
                return "error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
	}

}
