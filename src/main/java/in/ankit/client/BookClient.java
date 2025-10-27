package in.ankit.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import in.ankit.binding.Book;

@Service
public class BookClient {
	
	public void saveBook() {
        String apiUrl = "https://ankit-book-api.herokuapp.com/book";	
       
        Book book = new Book();
        book.setName("Java");
        book.setPrice(19.99);
        
		WebClient client = WebClient.create(); 
		String resp = client.post() // HTTP Post Request.
				            .uri(apiUrl) // Endpoint URL.
				            .bodyValue(book) // Http request body data
				            .retrieve() // retrieve response body
				            .bodyToMono(String.class) // bind response data to the string variable
				            .block(); // make it as sync client
		System.out.println(resp);
	}

	
	public void GetBooksSync() {
		String apiUrl = "https://ankit-book-api.herokuapp.com/book";		
		WebClient client = WebClient.create();  // Create a client. {Because WebClient is an interface, we can't create an object directly.}
/*	
		String string = client.get()    // HTTP Get Request.
		      .uri(apiUrl) // Endpoint URL.
		      .retrieve()  // retrieve response body
		      .bodyToMono(String.class) // bind response data to the given class
		      .block(); // make it as sync client
        System.out.println(string);
*/		
		Book[] books = client.get()
				             .uri(apiUrl)
				             .retrieve()
				             .bodyToMono(Book[].class)
				             .block();
		for(Book book : books) {
			System.out.println(book);
		}
	}

	public void GetBooksAsync() {
		String apiUrl = "https://ankit-book-api.herokuapp.com/book";		
		WebClient client = WebClient.create();  
		client.get()
	             .uri(apiUrl)
	             .retrieve()
	             .bodyToMono(Book[].class)
	             .subscribe(BookClient::respHandler); // Async Communication
       System.out.println("----------Request Sent!!-------------");  
       /* Here, above SOP doesn't wait for respHandler to complete the execution. */
	}
       public static void respHandler(Book[] books) {  // The Book[].class is passed in the respHandler method.
		for(Book book : books) {
           System.out.println(book);
          }
     }
}