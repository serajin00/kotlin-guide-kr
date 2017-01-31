# Pair

2개의 값을 담는 data class 이다. 간단한 정의는 다음과 같다.

```kotlin
data class Pair<A, B>(
    val first: A,
    val second: B
)
```
https://github.com/JetBrains/kotlin/blob/master/libraries/stdlib/src/kotlin/util/Tuples.kt

---
Pair 의 생성은 `to` 함수를 이용한다.

```kotlin
"age" to 19 // Pair<String, Int>
```

Map 을 초기화 할때 사용한다.

```kotlin
val jim = mapOf(
    "name" to "Jim",
    "gender" to "M",
    "age" to 31,
    "friends" to listOf("Tycus", "Sarah")
) // Map<String, Any>

val digits = mapOf(
    0 to "zero",
    1 to "one",
    2 to "two",
    3 to "three"
) // Map<Int, String>

```

data class 이기 때문에, destructuring 가능하다.

```kotlin
val (width, height) = 100 to 200
```

---
List 를 Map 으로 만들때 사용하는 associate 함수에서도 Pair 를 사용한다.

정의:
```kotlin
fun <T, K, V> Iterable<T>.associate(transform: (T) -> Pair<K, V>): Map<K, V>
```

사용 예:
```kotlin
data class Person(val name: String, val sex: Char, val age: Int)

val persons = listOf(
    Person("Aron", 'M', 30),
    Person("Flora", 'F', 20),
    Person("Lion", 'M', 34),
    Person("Ivy", 'F', 24),
    Person("Peach", 'F', 13)
)

val nameToAge = persons.associate { person ->
    person.name to person.age
}

```