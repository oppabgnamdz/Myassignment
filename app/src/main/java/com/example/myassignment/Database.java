package com.example.myassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myassignment.Model.Book;
import com.example.myassignment.Model.Category;
import com.example.myassignment.Model.User;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public static final String NAME = "Database.db";
    public static final String USER = "CREATE TABLE USER(NAME TEXT PRIMARY KEY not null,PASS TEXT,PHONE TEXT, ADDRESS TEXT)";
    public static final String CATEGORY = "CREATE TABLE CATEGORY(NAME TEXT PRIMARY KEY not null,DESCRIPTION TEXT,POSITION TEXT)";
    public static final String BOOK = "CREATE TABLE BOOK(BOOKNAME TEXT PRIMARY KEY NOT NULL,CATEGORYNAME TEXT,TITLE TEXT,AUTHOR TEXT,DATE TEXT,MONEY REAL,NUMBER INTEGER)";

    public Database(Context context) {
        super(context, NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER);
        db.execSQL(CATEGORY);
        db.execSQL(BOOK);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USER");
        db.execSQL("DROP TABLE IF EXISTS CATEGORY");
        db.execSQL("DROP TABLE IF EXISTS BOOK");
        onCreate(db);
    }

    //insert
    public long insertUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", user.getName());
        contentValues.put("Pass", user.getPass());
        contentValues.put("PHONE", user.getPhone());
        contentValues.put("ADDRESS", user.getAddress());
        long newRow = sqLiteDatabase.insert("USER", null, contentValues);
        return newRow;

    }

    public long inserCategory(Category category) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", category.getName());
        contentValues.put("DESCRIPTION", category.getDescription());
        contentValues.put("POSITION", category.getPosition());
        long newRow = sqLiteDatabase.insert("CATEGORY", null, contentValues);
        return newRow;

    }

    public long inserBook(Book book) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("BOOKNAME", book.getBookName());
        contentValues.put("CATEGORYNAME", book.getCategoryName());
        contentValues.put("TITLE", book.getTitle());
        contentValues.put("AUTHOR", book.getAuthor());
        contentValues.put("DATE", book.getDate());
        contentValues.put("MONEY", book.getMoney());
        contentValues.put("NUMBER", book.getNumber());
        long newRow = sqLiteDatabase.insert("BOOK", null, contentValues);
        return newRow;

    }


    //search
    public User searchUser(String name) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] arr = {"NAME", "PASS", "PHONE", "ADDRESS"};
        String selection = "NAME = ?";
        String[] args = {name};
        Cursor cursor = sqLiteDatabase.query("USER", arr, selection, args, null, null, null);
        cursor.moveToFirst();
        String userName = cursor.getString(0);
        String pass = cursor.getString(1);
        String phone = cursor.getString(2);
        String address = cursor.getString(3);
        User user = new User(userName, pass, phone, address);
        return user;
    }

    //update
    public int updateUser(String name, String pass) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("PASS", pass);
        String[] args = {name};
        int count = sqLiteDatabase.update("USER", contentValues, "NAME LIKE ?", args);
        return count;

    }

    //getAll
    public ArrayList<User> getAllUser() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<User> users = new ArrayList<>();
        String[] projection = {"NAME", "PASS", "PHONE", "ADDRESS"};
        Cursor cursor = sqLiteDatabase.query("USER", projection, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String name = cursor.getString(0);
            String pass = cursor.getString(1);
            String phone = cursor.getString(2);
            String address = cursor.getString(3);
            User user = new User(name, pass, phone, address);
            users.add(user);
            cursor.moveToNext();

        }
        return users;
    }

    public ArrayList<Category> getAllCategory() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<Category> categories = new ArrayList<>();
        String[] projection = {"NAME", "Description", "position"};
        Cursor cursor = sqLiteDatabase.query("CATEGORY", projection, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String name = cursor.getString(0);
            String description = cursor.getString(1);
            String position = cursor.getString(2);
            Category category = new Category(name, description, position);
            categories.add(category);
            cursor.moveToNext();

        }
        return categories;
    }

    public ArrayList<Book> getAllBook() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<Book> books = new ArrayList<>();
        String[] projection = {"bookName", "categoryName", "title", "author", "date", "money", "number"};
        Cursor cursor = sqLiteDatabase.query("Book", projection, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String bookName = cursor.getString(0);
            String categoryName = cursor.getString(1);
            String title = cursor.getString(2);
            String author = cursor.getString(3);
            String date = cursor.getString(4);
            String money = cursor.getString(5);
            String number = cursor.getString(6);
            Book book = new Book(bookName, categoryName, title, author, date, money, number);
            books.add(book);
            cursor.moveToNext();

        }
        return books;
          //alskdlakwelkwlekwe
    }
}
