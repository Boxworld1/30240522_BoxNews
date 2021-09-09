package com.java.rongyilang.database;

import android.provider.ContactsContract;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DataUao {

    String tableName = "MyTable";

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertData(MyData myData);

    @Query("SELECT * FROM " + tableName)
    List<MyData> displayAll();

    @Query("SELECT * FROM " + tableName + " WHERE newsID = :newsID")
    List<MyData> findDataByID(String newsID);

    @Query("SELECT * FROM " + tableName + " WHERE category = :category")
    List<MyData> findDataByCategory(String category);

    @Query("SELECT * FROM " + tableName + " WHERE isHistory = 1")
    List<MyData> searchHistoryData();

    @Query("SELECT * FROM " + tableName + " WHERE isFavourite = 1")
    List<MyData> searchFavouriteData();

    @Update
    void updateData(MyData myData);

    @Delete
    void deleteData(MyData myData);


}
