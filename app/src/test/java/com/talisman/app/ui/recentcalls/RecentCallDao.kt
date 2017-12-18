package com.talisman.app.ui.recentcalls

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.talisman.app.ui.recentcalls.model.RecentCallResponse
import io.reactivex.Flowable

/**
 * Created by Tarun on 12/18/17.
 */
@Dao
interface RecentCallDao
{
    companion object {
        private const val QUERY_RECENT_CALL_RESPONSE = "SELECT * FROM RecentCallResponse "
        private const val DELETE_RECENT_CALL_RESPONSE = "DELETE FROM RecentCallResponse"
    }

    @Query(QUERY_RECENT_CALL_RESPONSE)
    fun getRecentCalls() : Flowable<RecentCallResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecentCalls(recentCalls : RecentCallResponse)

    @Query(DELETE_RECENT_CALL_RESPONSE)
    fun deleteRecentCalls()

}