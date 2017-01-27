# kotlin 필수 함수
 let, run, apply, also, takeIf, use
 
https://github.com/JetBrains/kotlin/blob/master/libraries/stdlib/src/kotlin/util/Standard.kt

## let

```kotlin
fun <T, R> T.let(block: (T) -> R): R = block(this)
```  

리시버를 첫번째 파라미터로 해서 block 을 실행한 결과를 반환한다.

```kotlin
frozen.let { it.go() }
```
---
아래와 같은 코드를:

```kotlin
val list = listOf(1, 2, 3, 4)
val sumOfEven = list.filter { it % 2 == 0 }.sum()
```

다음과 같이 쓸 수 있다.
```kotlin
// 먼저 객체를 만들고 나중에 이름을 붙인다.
// list 변수가 외부에 노출되지 않고 스코프가 한정되는 효과가 있다.
val sumOfEven = listOf(1, 2, 3, 4).let { list ->
  list.filter { it % 2 == 0 }.sum()
}
```

## run
```kotlin
fun <T, R> T.run(block: T.() -> R): R = block()
```

let 과 유사하지만 차이점은 block 의 리시버(this)가 T 라는 것이다.

---
아래와 같은 클래스가 있을 때:
```kotlin
class Foo {
  var a = 0
  var b = 0
  var c = 0
  var d = 0
  fun sum() = a + b + c + d
}
```

다음과 같은 코드를:
```kotlin
val foo = Foo()
foo.a = 1
foo.b = 2
foo.c = 3
foo.d = 4
val sum = foo.sum()
```

이렇게 쓸 수 있다.
```kotlin
// foo 변수가 반복될 필요가 없고 노출되지도 않는다.
val sum = Foo().run {
  a = 1
  b = 2
  c = 3
  d = 4
  sum()
}
```

## apply
```kotlin
fun <T> T.apply(block: T.() -> Unit): T { block(); return this }
```
run 과 마찬가지로 리시버를 T 로 하지만 항상 자기자신을 리턴한다. 
복잡한 객체를 초기화할 때 유용하다.

---
다음과 같은 클래스가 있을 때:
```kotlin
enum class Gender {
  MALE, FEMALE
}

class Person {
  var id: String = ""
  var name: String = ""
  var memo: String = ""
  var age: Int? = null
  var gender: Gender? = null
  fun transgender() {
    gender = gender?.let { 
      when(it) {
        Gender.MALE -> Gender.FEMALE
        Gender.FEMALE -> Gender.MALE
      }
    }
  }
}
```

아래처럼 객체를 초기화 하는 것보다:
```kotlin
val person = Person()
person.id = "123"
person.name = "Kim"
person.memo = "good"
person.age = 30
person.gender = Gender.MALE
person.transgender()
```

apply 를 쓰면 간단해진다.
```kotlin
val person = Person().apply {
  id = "123"
  name = "Kim"
  memo = "good"
  age = 30
  gender = Gender.MALE
  transgender()
}
```

---
apply 는 간단히 디버그로 println 을 호출할 때 유용하다. 항상 this 를 리턴하기 때문이다.

```kotlin
  someFunction(someObject.foo().bar().apply { println(this) /* bar 의 결과를 확인해보자 */ }.doXXX())
```

---
> 위의 Person 클래스는 좋지 않다. 생성자 파라미터로 속성 값을 받도록 하는것이 바람직하다.  
> 여기서는 apply 사용 예를 보이기 위해 모든 속성을 var 로 작성했다.

## also
kotlin 1.1 에 추가되었다.
```kotlin
fun <T> T.also(block: (T) -> Unit): T { block(this); return this }
```
apply 와 같게 this 를 리턴하지만, 리시버 대신에 let 처럼 첫번째 파라미터로 전달한다. Ruby 의 tap 과 같다.  
apply 보다 표현력이 좋은 경우가 꽤 있다.

---
```kotlin
val readme = File("tmp/doc/readme.txt").also { it.parentFile.mkdirs() }
```

## takeIf
kotlin 1.1 에 추가되었다.
```kotlin
fun <T> T.takeIf(predicate: (T) -> Boolean): T? = if (predicate(this)) this else null
```

단일 객체에 대한 filter 라고 볼 수 있다.  
조건이 충족되면 this 를 리턴하고 아니면 null 을 리턴한다.

---
```kotlin
val lines: List<String> = File("data.txt").takeIf { it.exists() }?.readLines() ?: emptyList()
```

만약 takeIf 가 없다면 다음과 같이 작성해야 할 것이다.
```kotlin
val lines: List<String> = File("data.txt").let { file ->
  if (file.exists()) file.readLines() else emptyList()
}
```

## use 

코틀린 io 의 기본. java 7 의 try-with-resource 의 역할을 한다.

```kotlin
fun <T : Closeable?, R> T.use(block: (T) -> R): R {
    try {
        return block(this)
    } finally {
        try {
          this.close()
        } catch (e: Exception) {}
    }
}
```
> 위 코드는 실제와 다르다. 이해의 편의를 위해 의도적으로 java 6 시절의 io 처리 로직을 담았다.  
> https://github.com/JetBrains/kotlin/blob/master/libraries/stdlib/src/kotlin/io/Closeable.kt

---
텍스트 파일 출력 예제:
```kotlin
BufferedReader(InputStreamReader(FileInputStream(File("test.txt")))).use { reader ->
  while(true) {
    val line = reader.readLine() ?: break
    println(line)
  }
}
```
> 물론 이렇게 작성하지는 않는다. ``fun File.forEachLine(action: (String) -> Unit)`` 등의 extension 이 있기 때문이다.

---
파일 카피 예제:
```kotlin
FileInputStream("input.txt").use { input ->
  FileOutputStream("output.txt").use { output ->
    val buf = ByteArray(1024)
    
    while(true) {
      val len = input.read(buf)
      if (len < 0) break
      output.write(buf, 0, len)
    }
  }
}
```

---
부록. Sequence 를 이용한 파일 카피
```kotlin
FileInputStream("input.txt").use { input ->
  FileOutputStream("output.txt").use { output ->
    val buf = ByteArray(1024)
    
    generateSequence {
      input.read(buf).takeIf { len -> len >= 0 }
    }.forEach { len ->
      output.write(buf, 0, len)
    }
  }
}
```

---
> Closeable.close() 를 직접 호출할 일은 절대 없을 것이다.  
> 여러 리소스를 동시에 열어야 되는 경우에 use 가 중첩되는 것은 단점.