package com.example.moiroom.adapter

import com.example.moiroom.data.MyResponse
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import retrofit2.Call

class MyResponseTypeAdapter : TypeAdapter<MyResponse>() {
    override fun write(writer: JsonWriter?, value: MyResponse?) {
        // Implement if needed
    }

    override fun read(reader: JsonReader?): MyResponse? {
        // Implement if needed
        return null
    }
}
