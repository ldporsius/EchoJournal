package nl.codingwithlinda.persistence.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import nl.codingwithlinda.persistence.model.AmplitudeEntity
import nl.codingwithlinda.persistence.model.EchoEntity
import nl.codingwithlinda.persistence.model.EchoTopicXref
import nl.codingwithlinda.persistence.model.TopicEntity
import nl.codingwithlinda.persistence.room.dao.AmplitudeDao
import nl.codingwithlinda.persistence.room.dao.EchoDao
import nl.codingwithlinda.persistence.room.dao.TopicDao

@Database(
    version = 1,
    entities = [EchoEntity::class, AmplitudeEntity::class, TopicEntity::class, EchoTopicXref::class]
)
abstract class EchoDatabase: RoomDatabase(){
    abstract val echoDao: EchoDao
    abstract val amplitudeDao: AmplitudeDao
    abstract val topicDao: TopicDao

    companion object {
        @Volatile
        private var INSTANCE: EchoDatabase? = null

        fun getDatabase(context: Context): EchoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EchoDatabase::class.java,
                    "echo_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}