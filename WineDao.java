package com.alexios.android.RoomDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.alexios.android.models.Wine;

import java.util.List;

import kotlinx.coroutines.flow.Flow;

@Dao
public interface WineDao {
    @Query("SELECT * FROM WinesCategory")
    Flow<List<Wine>> getWines();
    @Insert
    void insertAll(List<Wine> wines);
    @Query("DELETE FROM WinesCategory")
    public void clearRecords();
}
