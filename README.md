# Scala Ranger: Proof of Concept

**Ranger** aims to enable compile-time range-constrained types in Scala. It is a concept you may know from Ada:

```Ada
type Count_To_Ten is range 1 .. 10;
```

Currently, this library lets you do almost the same in Scala with:

```Scala
@constrained class CountToTen extends Int(1 to 10)
```

Then, you can use it like this:

```Scala
val nine   = CountToTen(9)
val eleven = CountToTen(11) // Won't compile:
                            // [error] value out of range
                            // [error]     val eleven = CountToTen(11)
                            // [error]                            ^
```

More examples:

```Scala
@constrained class Hour extends Int(0 to 23)
@constrained class Celsius extends Double(-273.15 to Double.MaxValue)
@constrained class Letter extends Char('a' to 'z')
@constrained class AToZZZZ extends String("a" to "zzzz")
```

However, please bear in mind that **this is just a proof of concept**. You shouldn't use Ranger in production code yet, probably. Actually, this is just what I got after a few hours of Sunday hacking. It's working, but it may bite you... or eat your cat. You have been warned.

As such, there are no prebuilt artifacts yet. If you really want to try Ranger, you need to build it yourself.

## Implementation

Currently, Ranger is using macros to do all the magic. In fact, `ranger.constrained` is a [macro annotation](http://docs.scala-lang.org/overviews/macros/annotations.html), which generates a [def macro](http://docs.scala-lang.org/overviews/macros/overview). **Macro-ception**! Unfortunately, this means it requires [Macro Paradise](http://docs.scala-lang.org/overviews/macros/paradise.html). Moreover, it needs a three stage compilation:

 1. Compile Ranger itself (project `core`)
 2. Compile your types defined in terms of `ranger.constrained` macro (project `example-types`)
 3. Final compilation of your program using those constrained types (project `example`)

Steps 1 and 2 require adding Macro Paradise as a compiler plugin. Look at `build.sbt` for reference.

It's very likely it would be better to implement Ranger as a compiler plugin. However, I couldn't resist the fun of implementing macro-generating macros. Please forgive me.

## Macro-expanded Implementation

`@ranger.constrained` generates a [value class](http://docs.scala-lang.org/overviews/core/value-classes.html) and a [companion object](http://docs.scala-lang.org/tutorials/tour/singleton-objects.html) with method `apply` defined as a [def macro](http://docs.scala-lang.org/overviews/macros/overview). Inside `apply`, it generates some code which makes sure that any value passed to `apply` satisfies the constraints set in the declaration of the corresponding type. If it does, you get your value wrapped in the value class. If not, you get a compilation error. Thus, you should get zero runtime overhead in most cases. On the other hand, you lose any operations which were defined for the base type. Yes, you don't even get addition by default. This is something I'm planning to improve in the future.

Additionally, an implicit conversion from your range-constrained type to the base type is generated. There's no reverse implicit conversion, though. It would defeat the purpose of this library. If you really want to convert a base-type value to your target-type value, you can use `YourType.unsafe(value)`. Example usage is provided in `ranger-example`.

## TODO

 - Cleaner macro-generating code
 - Predefined operators for resulting types
 - Better examples
 - Better documentation
