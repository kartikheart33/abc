package com.alexios.android.RoomDB;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.alexios.android.models.Filter;
import com.alexios.android.models.Products;
import com.alexios.android.models.Wine;

@Database(entities = {Wine.class, Products.class, Filter.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class AlexiousDataBase extends RoomDatabase {

    public abstract WineDao wineDao();
    public abstract ProductDao productDao();
    public abstract FilterDao filterDao();

    private static AlexiousDataBase INSTANCE;

    public static AlexiousDataBase getDbInstance(Context context) {

        if(INSTANCE == null)
        {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AlexiousDataBase.class, "AlexiousDataBase")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

}
