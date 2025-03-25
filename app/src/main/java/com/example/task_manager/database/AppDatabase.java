    package com.example.task_manager.data.database;

    import android.content.Context;
    import androidx.room.Database;
    import androidx.room.Room;
    import androidx.room.RoomDatabase;

    import com.example.task_manager.data.dao.TaskDao;
    import com.example.task_manager.data.entity.TaskEntity;

    @Database(entities = {TaskEntity.class}, version = 1)
    public abstract class AppDatabase extends RoomDatabase {
        private static AppDatabase instance;

        public abstract TaskDao taskDao();

        public static synchronized AppDatabase getInstance(Context context) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, "task_database")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return instance;
        }
    }


