package com.bridge.global.room

import android.content.Context
import com.bridge.global.model.Datum
import kotlinx.coroutines.*

class UserRepository(context: Context) {
    private val scope = GlobalScope
    var db: UserDao? = AppDatabase.getInstance(context)?.userDao()

    //Fetch All the Users
    fun getAllUsers(): List<Datum>? {
        return db?.gelAllUsers()
    }

    // Insert new user
    fun insertUser(users: Datum) {
        scope.launch { db?.insertUser(users) }
    }

    fun insertAllUsers(users: List<Datum>) {
        scope.launch { db?.insertAllUsers(users) }
    }

    // update user
    fun updateUser(users: Datum) {
        db?.updateUser(users)
    }

    // Delete user
    fun deleteUser(users: Datum) {
        db?.deleteUser(users)
    }
}