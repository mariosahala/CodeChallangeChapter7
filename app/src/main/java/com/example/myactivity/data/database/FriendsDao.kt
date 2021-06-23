package com.example.myactivity.data.database

import androidx.room.*

@Dao
interface FriendsDao {
    @Insert
    fun addFriend(friend: Friends): Long

    @Update
    fun updateFriend(friend: Friends): Int

    @Delete
    fun deleteFriend(friend: Friends): Int

    @Query("SELECT * FROM Friends")
    fun getAllFriends(): MutableList<Friends>

    @Query("SELECT * FROM Friends WHERE friend_id=:friendsId")
    fun getFriendId(friendsId: String): Int
}