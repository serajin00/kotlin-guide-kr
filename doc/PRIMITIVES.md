# kotlin primitives

자바에서는 int, long, float 등의 프리미티브 타입이 있고 Integer, Long, Float 등의 박싱객체(Boxed-Object) 가 있지만,

코틀린에서는 **'모든 객체는 Any'** 이며 **'모든 타입은 대문자로 시작'** 이라는 단순한 룰이 적용된다.

kotlin | java
------ | ----
`Boolean` | `boolean`
`Byte` | `byte`
`Char` | `char`
`Int` | `int`
`Long` | `long`
`Float` | `float`
`Double` | `double`

---
배열과 리스트

kotlin | java
------ | ----
`IntArray` | `int[]`
`Array<Int>` | `Integer[]`
`Array<Int?>` | `Integer[]`
`List<Int>` | `List<Integer>`

코틀린에서는 프리미티브와 박싱객체 구분없이 정수는 `Int` 로 쓰면 된다. 코틀린 컴파일러가 상황에 맞게 최적화해서 컴파일해준다.

다만 자바에서 배열은 `int[]` 와 `Integer[]` 타입이 완전 다른 것이기 때문에 코틀린에서도 프리미티브에 대한 배열 타입은 별도로 정의되어 있다.

물론 String 은 프리미티브가 아니기 때문에 `StringArray` 라는 타입은 없다. 항상 `Array<String>` 이다.


type | 생성 (size) | 생성 - 초기화
---- | ---------- | ----------
`IntArray` | `IntArray(10)` | `intArrayOf(1, 2, 3)`
`FloatArray` | `FloatArray(10)` | `floatArrayOf(1.0f, 2.0f, 3.0f)`

---
배열을 생성하면서 값을 초기화 하고 싶은 경우는 다음과 같이 함수(lambda) 를 넘기면 된다. 

```kotlin
fun main(args: Array<String>) {
    val squares = IntArray(10) { i -> i * i }
    println(squares)              // [I@67854d1f
    println(squares.toList())     // [0, 1, 4, 9, 16, 25, 36, 49, 64, 81]

    val ones = IntArray(10) { 1 }
    println(ones.toList())        // [1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
}
```

## No implicit conversion

코틀린은 자바와 달리 암시적인 형변환 (더 큰 자료형으로의) 이 일어나지 않는다.

```java
// java
int i = 100;
long l = i;
float f = l;
```

```kotlin
// kotlin
val i = 100
var l: Long = i // compile error!
var f: Float = l // compile error!
```

불편하다고 볼 수도 있지만, 의도하지 않는 형변환을 막아주기에 의도가 명확하게 드러나는 코드가 된다.

자바에서의 형변환 문법은 코틀린에 존재하지 않는다. `toLong()` 등의 익스텐션 함수를 이용한다.

```java
// java
int i = 100;
long l = (long) i;
float f = (float) l;
```

```kotlin
// kotlin
val i = 100
val l = i.toLong()
val f = l.toFloat()
```

---
Int 에서 Long 타입으로의 변환이 자동으로 이뤄지지 않는다고 했지만, 이런 코드는 가능하다.

```kotlin
val l: Long = 1000 // 1000 은 Int 이고, 1000L 이 Long 이다.
```

타입이 Long 이라고 명시적으로 선언되어 있기 때문에, 컴파일 타임에 1000 을 1000L 로 해석할 수 있기 때문이다.


---
### _참고_
https://github.com/JetBrains/kotlin/blob/master/core/builtins/native/kotlin/Arrays.kt
https://github.com/JetBrains/kotlin/blob/master/core/builtins/native/kotlin/Array.kt
