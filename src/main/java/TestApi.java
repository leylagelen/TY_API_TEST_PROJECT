import com.google.gson.JsonObject;
import model.Book;
import model.response.BookResponse;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import retrofit2.Call;

import java.io.IOException;
import java.util.List;

public class TestApi {

    private static BookService bookService;
    private static String sampleTitle;
    private static String sampleAuthor;

    @BeforeClass
    public static void setup() {
        bookService = RetrofitClientInstance.getRetrofitInstance().create(BookService.class);
        sampleTitle = "title1";
        sampleAuthor = "author1";
    }

    @Test
    public void should_the_book_list_is_empty_at_the_beginning() {
        Call<List<Book>> call = bookService.getAll();

        try {
            var response = call.execute();
            Assert.assertEquals(response.code(), 200);
            Assert.assertNotEquals(response.body(), null);
            Assert.assertEquals(0, response.body().size());

        } catch (IOException e) {
            Assert.fail("A problem occurred! " + e.getMessage());
        }
    }

    @Test
    public void should_response_400_if_author_field_does_not_send() {
        JsonObject book = new JsonObject();
        book.addProperty("title", sampleTitle);

        Call<BookResponse> call = bookService.create(book.toString());

        try {
            var response = call.execute();
            Assert.assertEquals(response.code(), 400);
            Assert.assertNotEquals(response.body(), null);
            Assert.assertEquals(response.body().getError(), "Field 'author' is required.");
        } catch (IOException e) {
            Assert.fail("A problem occurred! " + e.getMessage());
        }
    }

    @Test
    public void should_response_400_if_title_field_does_not_send() {
        JsonObject book = new JsonObject();
        book.addProperty("author", sampleAuthor);

        Call<BookResponse> call = bookService.create(book.toString());

        try {
            var response = call.execute();
            Assert.assertEquals(response.code(), 400);
            Assert.assertNotEquals(response.body(), null);
            Assert.assertEquals(response.body().getError(), "Field 'title' is required.");
        } catch (IOException e) {
            Assert.fail("A problem occurred! " + e.getMessage());
        }
    }

    @Test
    public void should_response_400_if_title_field_is_empty() {
        JsonObject book = new JsonObject();
        book.addProperty("title", "");
        book.addProperty("author", sampleAuthor);

        Call<BookResponse> call = bookService.create(book.toString());

        try {
            var response = call.execute();
            Assert.assertEquals(response.code(), 400);
            Assert.assertNotEquals(response.body(), null);
            Assert.assertEquals(response.body().getError(), "Field 'title' cannot be empty.");
        } catch (IOException e) {
            Assert.fail("A problem occurred! " + e.getMessage());
        }
    }

    @Test
    public void should_response_400_if_author_field_is_empty() {
        JsonObject book = new JsonObject();
        book.addProperty("title", sampleTitle);
        book.addProperty("author", "");

        Call<BookResponse> call = bookService.create(book.toString());

        try {
            var response = call.execute();
            Assert.assertEquals(response.code(), 400);
            Assert.assertNotEquals(response.body(), null);
            Assert.assertEquals(response.body().getError(), "Field 'author' cannot be empty.");
        } catch (IOException e) {
            Assert.fail("A problem occurred! " + e.getMessage());
        }
    }

    @Test
    public void should_response_not_200_if_id_field_set() {
        JsonObject book = new JsonObject();
        book.addProperty("id", 1);
        book.addProperty("title", sampleTitle);
        book.addProperty("author", sampleAuthor);

        Call<BookResponse> call = bookService.create(book.toString());

        try {
            var response = call.execute();
            Assert.assertNotEquals(response.code(), 200);
        } catch (IOException e) {
            Assert.fail("A problem occurred! " + e.getMessage());
        }
    }

    @Test
    public void should_response_200_if_title_and_author_fields_set() {
        JsonObject book = new JsonObject();
        book.addProperty("title", sampleTitle);
        book.addProperty("author", sampleAuthor);

        Call<BookResponse> createCall = bookService.create(book.toString());

        try {
            var response1 = createCall.execute();
            Assert.assertEquals(response1.code(), 200);
            Assert.assertNotEquals(response1.body(), null);

            Call<Book> getByIdCall = bookService.getById(response1.body().getId());
            var response2 = getByIdCall.execute();
            Assert.assertEquals(response2.code(), 200);
            Assert.assertNotEquals(response2.body(), null);
            Assert.assertEquals(response2.body().getTitle(), sampleTitle);
            Assert.assertEquals(response2.body().getAuthor(), sampleAuthor);
        } catch (IOException e) {
            Assert.fail("A problem occurred! " + e.getMessage());
        }
    }

    @Test
    public void should_response_400_if_exist_title_and_author_fields_set() {
        JsonObject book = new JsonObject();
        book.addProperty("title", sampleTitle);
        book.addProperty("author", sampleAuthor);

        Call<BookResponse> call1 = bookService.create(book.toString());
        Call<BookResponse> call2 = bookService.create(book.toString());

        try {
            var response1 = call1.execute();
            var response2 = call2.execute();

            Assert.assertEquals(response1.code(), 200);
            Assert.assertNotEquals(response1.body(), null);
            Assert.assertEquals(response1.body().getTitle(), sampleTitle);
            Assert.assertEquals(response1.body().getAuthor(), sampleAuthor);
            Assert.assertEquals(response2.code(), 400);
            Assert.assertNotEquals(response2.body(), null);
            Assert.assertEquals(response2.body().getError(), "Another book with similar title and author already exists.");
        } catch (IOException e) {
            Assert.fail("A problem occurred! " + e.getMessage());
        }
    }
}
