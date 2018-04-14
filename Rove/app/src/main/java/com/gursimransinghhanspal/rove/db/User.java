package com.gursimransinghhanspal.rove.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(
		tableName = "users",
		indices = {
				@Index(
						value = {"user_id"},
						name = "usersIdx_user_id",
						unique = true
				)
		}
)
public class User {
	@PrimaryKey(autoGenerate = true)
	@NonNull
	@ColumnInfo(name = "local_id")
	public Integer localId;

	@ColumnInfo(name = "is_user")
	public Boolean isUser;

	@ColumnInfo(name = "user_id")
	public String userId;

	@ColumnInfo(name = "email")
	public String email;

	@ColumnInfo(name = "first_name")
	public String firstName;

	@ColumnInfo(name = "last_name")
	public String lastName;

	@ColumnInfo(name = "profile_image")
	public String profileImage;

	@ColumnInfo(name = "number_of_posts")
	public Integer numPosts;

	@ColumnInfo(name = "number_of_followers")
	public Integer numFollowers;

	@ColumnInfo(name = "number_of_likes")
	public Integer numLikes;
}
