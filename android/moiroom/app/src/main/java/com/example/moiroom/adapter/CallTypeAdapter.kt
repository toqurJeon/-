package com.example.moiroom.adapter

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import retrofit2.Call
import java.io.IOException

class CallTypeAdapter : TypeAdapter<Call<*>>() {
    @Throws(IOException::class)
    override fun write(out: JsonWriter?, value: Call<*>?) {
        // Implement if needed
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader?): Call<*> {
        // Throw an exception indicating that instantiation is not supported
        throw UnsupportedOperationException("Call instantiation is not supported.")
    }
}
