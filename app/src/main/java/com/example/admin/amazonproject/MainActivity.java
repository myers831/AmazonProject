package com.example.admin.amazonproject;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.amazonproject.model.Book;

import java.util.ArrayList;
import java.util.List;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    RecyclerView rvBooks;
    TextView tvLoading;
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView.LayoutManager layoutManager;
    public static final String TAG = "mainActivity";
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private SharedPreferences sp1;
    long waitTime = 1800000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: currentTime" + System.currentTimeMillis());

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit();
        sp1 = PreferenceManager.getDefaultSharedPreferences(this);

        databaseHelper = new DatabaseHelper(this);

        rvBooks = findViewById(R.id.rvBooks);
        tvLoading = findViewById(R.id.tvLoading);

        layoutManager = new LinearLayoutManager(MainActivity.this);

        RetrofitHelper.getCall()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<List<Book>, ObservableSource<Book>>() {
                    @Override
                    public ObservableSource<Book> apply(@NonNull final List<Book> books) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<Book>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<Book> e) throws Exception {
                                if(sp.getLong("ExactTime", 0) >= (sp.getLong("ExactTime", 0) + waitTime )){
                                    for (Book book : books) {
                                        e.onNext(book);
                                    }
                                    Toast.makeText(MainActivity.this, "DataBase Updated", Toast.LENGTH_SHORT).show();
                                }
                                e.onComplete();
                            }
                        });
                    }
                })
                .map(new Function<Book, Book>() {
                    @Override
                    public Book apply(@NonNull Book book) throws Exception {
                        String bookName = "Amazon Book: " + book.getTitle();
                        book.setTitle(bookName);

                        return book;
                    }
                })
                .subscribe(new Observer<Book>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: " + d.toString());
                    }

                    @Override
                    public void onNext(@NonNull Book book) {
                        databaseHelper.saveBook(book);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        if(sp.getLong("ExactTime", 0) >= (sp.getLong("ExactTime", 0) + waitTime )){
                            editor.putLong("ExactTime", System.currentTimeMillis());
                            editor.commit();
                            Toast.makeText(MainActivity.this, "UpdateTimer", Toast.LENGTH_SHORT).show();
                        }

                        recyclerViewAdapter = new RecyclerViewAdapter(databaseHelper.getBooksList());
                        tvLoading.setText("");
                        rvBooks.setLayoutManager(layoutManager);
                        rvBooks.setAdapter(recyclerViewAdapter);
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
}
