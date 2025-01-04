package nl.codingwithlinda.echojournal.core.application

import android.app.Application
import nl.codingwithlinda.echojournal.core.di.AndroidAppModule
import nl.codingwithlinda.echojournal.core.di.AppModule

class EchoApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        appModule = AndroidAppModule(this)

    }
    companion object{
        lateinit var appModule: AppModule
    }
}