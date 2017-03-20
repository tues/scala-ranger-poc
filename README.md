# Scala Ranger: Proof of Concept

**Ranger** aims to enable range-constrained types in Scala. It is a concept known from Ada:

```Ada
type Count_To_Ten is range 1 .. 10;
```

Currently, this library lets you do the same in Scala with:

```Scala
@constrained CountToTen extends Int(1 to 10)
```

Then, you can use it like this:

```Scala
val nine   = CountToTen(9)
val eleven = CountToTen(11) // Won't compile:
                            // [error] value out of range
                            // [error]     val eleven = CountToTen(11)
                            // [error]                            ^
```

However, please bear in mind that **this is just a proof of concept**. You shouldn't use Ranger in production code yet, probably. In fact, this is just what I got after a few hours of Sunday hacking.

As such, there are no prebuilt artifacts yet. If you really want to try Ranger, you need to build it yourself.
