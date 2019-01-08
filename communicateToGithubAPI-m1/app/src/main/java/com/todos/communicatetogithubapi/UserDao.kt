package com.todos.communicatetogithubapi

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface IUserDao {

    @Insert
    fun insertUser( anyUser: User)

    @Query("SELECT * FROM users_github WHERE login = :myLogin")
    fun getUserByLogin(myLogin : String) : User?

    /*@Query("SELECT * FROM users_github")
    fun getAll(): List<User>

    @Query("SELECT * FROM users_github WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)*/
}