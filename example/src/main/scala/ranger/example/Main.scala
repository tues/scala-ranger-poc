package ranger.example

import ranger.example.types._

object Main {

  def main(args: Array[String]): Unit = {
    val validHour = Hour(6)
    val validFoldedHour = Hour(2 * 5)
    // WON'T COMPILE
    // val invalidHour = Hour(25)
    // val invalidFoldedHour = Hour(3 * 10)
    val hourAsInt: Int = validHour
    println(s"validHour = $validHour, validFoldedHour = $validFoldedHour")
    // WON'T COMPILE
    // println(s"invalidHour = $invalidHour, invalidFoldedHour = $invalidFoldedHour")
    println(s"hourAsInt = $hourAsInt")

    val validCelsius = Celsius(18.5)
    // WON'T COMPILE
    // val invalidCelsius = Celsius(-300.0)
    println(s"validCelsius = $validCelsius")
  }

}
