package com.talisman.app.ui.tickets

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.talisman.app.ui.tickets.model.TicketResponse
import io.reactivex.Flowable

/**
 * Created by Tarun on 12/18/17.
 */
@Dao
interface TicketDao
{
    companion object {
        private const val QUERY_TICKET_RESPONSE = "SELECT * FROM TicketResponse "
        private const val DELETE_TICKET_RESPONSE = "DELETE FROM TicketResponse"
    }

    @Query(QUERY_TICKET_RESPONSE)
    fun getTickets(): Flowable<TicketResponse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTickets(tickets: TicketResponse)

    @Query(DELETE_TICKET_RESPONSE)
    fun deleteTickets()

}