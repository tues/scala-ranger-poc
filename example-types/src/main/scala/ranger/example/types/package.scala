package ranger.example

import ranger.constrained

package object types {

  @constrained class Month      extends Int(1 to 12)
  @constrained class DayOfMonth extends Int(1 to 31)
  @constrained class DayOfWeek  extends Int(1 to  7)
  @constrained class Hour       extends Int(0 to 23)
  @constrained class Minute     extends Int(0 to 59)
  @constrained class Second     extends Int(0 to 59)

  // Theoretically, we could set maximum to Planck temperature
  @constrained class Celsius    extends Double(-273.15 to Double.MaxValue)
  @constrained class Fahrenheit extends Double(-459.67 to Double.MaxValue)

}
