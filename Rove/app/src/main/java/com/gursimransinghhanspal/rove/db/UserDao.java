package com.gursimransinghhanspal.rove.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {

	@Query("select * from users")
	List<User> getAll();

	@Query("select * from users where local_id = :id")
	User getById(Integer id);

	@Query("delete from users")
	void deleteAll();
}
