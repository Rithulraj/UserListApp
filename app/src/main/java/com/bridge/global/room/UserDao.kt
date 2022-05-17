package com.bridge.global.room

import androidx.room.*
import com.bridge.global.model.Datum

@Dao
interface UserDao {

    @Insert
    fun insertUser(users: Datum)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUsers(users: List<Datum>)

    @Query("Select * from user")
    fun gelAllUsers(): List<Datum>

    @Update
    fun updateUser(users: Datum)

    @Delete
    fun deleteUser(users: Datum)
}
