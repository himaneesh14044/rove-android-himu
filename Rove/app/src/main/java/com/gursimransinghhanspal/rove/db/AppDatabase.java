package com.gursimransinghhanspal.rove.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(
		entities = {
				User.class,
				Diary.class,
				Post.class
		},
		version = 1,
		exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

	private static AppDatabase INSTANCE;

	public abstract UserDao userModel();

	public abstract DiaryDao diaryModel();

	public abstract PostDao postModel();

	public static AppDatabase getInMemoryDatabase(Context context) {
		if (INSTANCE == null) {
			INSTANCE =
					Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
							// To simplify the codelab, allow queries on the main thread.
							// Don't do this on a real app! See PersistenceBasicSample for an example.
							.allowMainThreadQueries()
							.build();
		}
		return INSTANCE;
	}

	public static void destroyInstance() {
		INSTANCE = null;
	}
}
