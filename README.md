# Kotlin Serialization benchmarks

We find that `object` serialization has some performance issues in Kotlin Serialization. This
project contains some [Android Microbenchmarks](https://developer.android.com/topic/performance/benchmarking/microbenchmark-overview). 
When run on a Google Pixel 3 locally (roughly representative of a low-mid range device these days):

| Benchmark                       | Time (ns) | Allocations |
|---------------------------------|-----------|-------------|
| object_reflection_serializer    | 56,077    | 27          |
| object_reflection_json          | 69,069    | 40          |
| object_intrinsic_serializer     | 56,211    | 30          |
| object_intrinsic_json           | 118,768   | 73          |
| dataclass_reflection_serializer | 2,450     | 8           |
| dataclass_reflection_json       | 6,462     | 28          |
| dataclass_intrinsic_serializer  | 2,687     | 11          |
| dataclass_intrinsic_json        | 9,927     | 42          |

The benchmarks are very simple:

- The `_serializer` benchmarks simply call `serializer<T>()` or `serializer(KClass)` depending on the test.
- The `_json` benchmarks use the serializer to encode the object to JSON, and then back.

Link to [benchmark tests](../main/benchmark/src/androidTest/java/com/example/serialization/benchmark/Benchmarks.kt).
Link to [serializable classes](../main/app/src/main/java/com/example/serialization).

## How to run

- Download the project
- Connect an Android device (preferably physical but emulator works too)
- Either: run `./gradlew benchmark:cC`, or run the test in Android Studio