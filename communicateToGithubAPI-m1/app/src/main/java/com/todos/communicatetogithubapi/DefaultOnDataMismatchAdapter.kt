package com.todos.communicatetogithubapi

import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import java.io.IOException
import java.lang.reflect.Type
//import timber.log.Timber

class DefaultOnDataMismatchAdapter<T> private constructor(private val delegate: JsonAdapter<T>, private val defaultValue: T?) : JsonAdapter<T>() {

    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): T? {
        return try {
            delegate.fromJsonValue(reader.readJsonValue())
        } catch (e: Exception) {
            //Timber.w("Wrongful content - could not parse delegate " + delegate.toString())
            Log.e("Wrongful content - could not parse delegate ", delegate.toString())
            defaultValue
        }
    }

    @Throws(IOException::class)
    override fun toJson(writer: JsonWriter, value: T?) {
        delegate.toJson(writer, value)
    }

    companion object {
        @JvmStatic fun <T> newFactory(type: Class<T>, defaultValue: T?): JsonAdapter.Factory {
            return object : JsonAdapter.Factory {
                override fun create(requestedType: Type, annotations: Set<Annotation>, moshi: Moshi): JsonAdapter<*>? {
                    if (type != requestedType) {
                        return null
                    }
                    val delegate = moshi.nextAdapter<T>(this, type, annotations)
                    return DefaultOnDataMismatchAdapter(delegate, defaultValue)
                }
            }
        }
    }
}