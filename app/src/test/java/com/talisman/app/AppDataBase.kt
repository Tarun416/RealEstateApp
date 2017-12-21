package com.talisman.app

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.talisman.app.ui.customers.CustomerDao
import com.talisman.app.ui.customers.model.CustomerResponse
import com.talisman.app.ui.recentcalls.RecentCallDao
import com.talisman.app.ui.recentcalls.model.RecentCallResponse
import com.talisman.app.ui.tickets.TicketDao
import com.talisman.app.ui.tickets.model.TicketResponse
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration



/**
 * Created by varun on 11/8/17.
 */
@Database(
        version = 5,
        entities = arrayOf(RecentCallResponse::class,CustomerResponse::class,TicketResponse::class)
)

@TypeConverters(Converters::class)
abstract  class AppDataBase : RoomDatabase()
{

   companion object {
      val MIGRATION_4_5: Migration = object : Migration(4, 5) {
         override fun migrate(database: SupportSQLiteDatabase) {
            // Since we didn't alter the table, there's nothing else to do here.
         }
      }
   }


   abstract fun getRecentCalls() : RecentCallDao
   abstract fun customers() : CustomerDao
   abstract fun tickets() : TicketDao
}
