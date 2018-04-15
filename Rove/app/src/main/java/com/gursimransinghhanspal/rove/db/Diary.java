package com.gursimransinghhanspal.rove.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Will contain all the cached diaries
 */

@Entity(
		tableName = "diaries",
		indices = {
				@Index(
						value = {"user_id"},
						name = "diariesIdx_user_id",
						unique = true
				)
		},
		foreignKeys = {
				@ForeignKey(
						entity = User.class,
						parentColumns = "user_id",
						childColumns = "user_id"
				)
		}
)
public class Diary {

	@PrimaryKey(autoGenerate = true)
	@NonNull
	@ColumnInfo(name = "local_id")
	public Integer localId;

	@ColumnInfo(name = "diary_id")
	public Integer diaryId;

	@ColumnInfo(name = "user_id")
	public Integer userId;

	public String title;

	public String cover_image;

	public Integer visibility;
}
