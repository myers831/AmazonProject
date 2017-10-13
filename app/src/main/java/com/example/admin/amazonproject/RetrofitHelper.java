package com.example.admin.amazonproject;

        import android.util.Log;

        import com.example.admin.amazonproject.model.Book;

        import java.util.List;

        import io.reactivex.Observable;
        import retrofit2.Call;
        import retrofit2.Retrofit;
        import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
        import retrofit2.converter.gson.GsonConverterFactory;
        import retrofit2.http.GET;
        import retrofit2.http.Path;

        import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by Admin on 10/13/2017.
 */

public class RetrofitHelper {
    public static final String BASE_URL = "http://de-coding-test.s3.amazonaws.com";

    public static Retrofit create() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;
    }

    public static Observable<List<Book>> getCall() {
        Retrofit retrofit = create();
        RetrofitService service = retrofit.create(RetrofitService.class);
        Log.d(TAG, "getCall: ");
        return service.getResponse();
    }

    public interface RetrofitService {
        @GET("/books.json")
        Observable<List<Book>> getResponse();

    }
}
