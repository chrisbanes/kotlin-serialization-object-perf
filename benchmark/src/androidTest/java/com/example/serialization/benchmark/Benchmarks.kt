@file:Suppress("UNUSED_VARIABLE")

package com.example.serialization.benchmark

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.serialization.DataClassArgs
import com.example.serialization.ObjectArgs
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalSerializationApi::class)
@RunWith(AndroidJUnit4::class)
class Benchmarks {
    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun object_intrinsic_serializer() = benchmarkRule.measureRepeated {
        serializer<ObjectArgs>()
    }

    @Test
    fun dataclass_intrinsic_serializer() = benchmarkRule.measureRepeated {
        serializer<DataClassArgs>()
    }

    @Test
    fun object_intrinsic_json() = benchmarkRule.measureRepeated {
        val json = Json.encodeToString(ObjectArgs)
        Json.decodeFromString<ObjectArgs>(json)
    }

    @Test
    fun dataclass_intrinsic_json() = benchmarkRule.measureRepeated {
        val obj = DataClassArgs("name", 40)
        val json = Json.encodeToString(obj)
        Json.decodeFromString<DataClassArgs>(json)
    }

    @Test
    fun object_reflection_serializer() = benchmarkRule.measureRepeated {
        serializer(ObjectArgs::class.java)
    }

    @Test
    fun dataclass_reflection_serializer() = benchmarkRule.measureRepeated {
        serializer(DataClassArgs::class.java)
    }

    @Test
    fun object_reflection_json() = benchmarkRule.measureRepeated {
        val serializer = serializer(ObjectArgs::class.java)

        val json = Json.encodeToString(serializer, ObjectArgs)
        Json.decodeFromString(serializer, json)
    }

    @Test
    fun dataclass_reflection_json() = benchmarkRule.measureRepeated {
        val serializer = serializer(DataClassArgs::class.java)

        val obj = DataClassArgs("name", 40)
        val json = Json.encodeToString(serializer, obj)
        Json.decodeFromString(serializer, json)
    }
}