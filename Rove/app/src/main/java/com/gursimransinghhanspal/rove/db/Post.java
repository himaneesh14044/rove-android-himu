package com.gursimransinghhanspal.rove.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "posts")
public class Post {

	@PrimaryKey(autoGenerate = true)
	@NonNull
	@ColumnInfo(name = "local_id")
	public Integer localId;

	@ColumnInfo(name = "diary_id")
	public Integer diaryId;

	@ColumnInfo(name = "post_id")
	public Integer postId;


}
