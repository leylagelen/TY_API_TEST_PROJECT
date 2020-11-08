import model.Book;
import model.response.BookResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface BookService {
    @GET("api/book")
    Call<List<Book>> getAll();

    @GET("api/book/{id}")
    Call<Book> getById(@Path("id") int id);

    @PUT("api/book")
    @Headers("Content-Type: application/json")
    Call<BookResponse> create(@Body String bookJson);
}