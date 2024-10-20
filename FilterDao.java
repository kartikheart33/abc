package com.alexios.android.RoomDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.alexios.android.models.Filter;
import com.alexios.android.models.Wine;

import java.util.List;

import kotlinx.coroutines.flow.Flow;

@Dao
public interface FilterDao {
    @Query("SELECT * FROM Filter")
    Flow<List<Filter>> getFilter();
    @Insert
    void insertAll(List<Filter> filter);
    @Query("DELETE FROM Filter")
    public void clearRecords();
}
