package ranger.example.types

import org.scalatest.FlatSpec

class CompilationTests extends FlatSpec {

  "Hour" should "accept value within range" in {
    assertCompiles("Hour(5)")
  }

  it should "reject value outside range" in {
    assertDoesNotCompile("Hour(25)")
  }

  it should "accept minimum value" in {
    assertCompiles("Hour(0)")
  }

  it should "accept maximum value" in {
    assertCompiles("Hour(23)")
  }

  it should "accept constant-folded value within range" in {
    assertCompiles("Hour(3 * 3 - 2)")
  }

  it should "reject constant-folded value outside range" in {
    assertDoesNotCompile("Hour(3 * 7 + 5)")
  }

  it should "reject values unknown at compilation time" in {
    assertDoesNotCompile("Hour(Math.random().toInt)")
  }

  "Celsius" should "accept value within range" in {
    assertCompiles("Celsius(18.0)")
  }

  it should "reject value outside range" in {
    assertDoesNotCompile("Celsius(-300.0)")
  }

  it should "accept minimum value" in {
    // This is a floating point comparison, but both values are literal double
    // precision constants, so they should have the same representation, unless
    // your compiler is not deterministic.
    assertCompiles("Celsius(-273.15)")
  }

  it should "accept maximum value" in {
    assertCompiles("Celsius(Double.MaxValue)")
  }

  it should "accept constant-folded value within range" in {
    assertCompiles("Celsius(2 * 50.0 + 13)")
  }

  it should "reject constant-folded value outside range" in {
    assertDoesNotCompile("Celsius(-3 * 90.0 - 30)")
  }

  it should "reject values unknown at compilation time" in {
    assertDoesNotCompile("Celsius(Math.random())")
  }

  "Letter" should "accept value within range" in {
    assertCompiles("Letter('s')")
  }

  it should "reject value outside range" in {
    assertDoesNotCompile("Letter('!')")
  }

  it should "accept minimum value" in {
    assertCompiles("Letter('a')")
  }

  it should "accept maximum value" in {
    assertCompiles("Letter('z')")
  }

  it should "accept constant-folded value within range" in {
    assertCompiles("Letter('f' + 1)")
  }

  it should "reject constant-folded value outside range" in {
    assertDoesNotCompile("Letter('a' - 1)")
  }

  it should "reject values unknown at compilation time" in {
    assertDoesNotCompile("Letter(Math.random().toChar)")
  }

}
