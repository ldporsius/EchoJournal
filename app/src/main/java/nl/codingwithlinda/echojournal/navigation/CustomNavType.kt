package nl.codingwithlinda.echojournal.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import nl.codingwithlinda.echojournal.core.data.EchoDto

object CustomNavType {

    val echoDtoNavType = object : NavType<EchoDto>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): EchoDto? {
           return Json.decodeFromString<EchoDto>( bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): EchoDto {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: EchoDto): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: EchoDto) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}