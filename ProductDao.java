package com.alexios.android.RoomDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.alexios.android.models.Products;
import com.alexios.android.models.Wine;

import java.util.List;

import kotlinx.coroutines.flow.Flow;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM Products WHERE  catid =:categoryId")
    Flow<List<Products>> getAllProductsByCategory(int categoryId);

    @Insert
    void insertAll(List<Products> products);

    @Query("DELETE FROM Products")
    public void clearRecords();
}
