import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context
import scala.annotation.StaticAnnotation
import scala.annotation.compileTimeOnly

package object ranger {

  @compileTimeOnly("enable macro paradise to expand macro annotations")
  class constrained extends StaticAnnotation {
    def macroTransform(annottees: Any*): Any = macro Impl.constrained
  }

  private class Impl(val c: Context) {
    import c.universe._

    def constrained(annottees: c.Expr[Any]*): c.Expr[Any] = {
      annottees.map(_.tree).toList match {
        case q"""class ${target: TypeName} extends ${Ident(base: TypeName)}(${min}.to(${max}))""" :: Nil => {
          constrainedClass(target, base, min, max)
        }
        case _ => c.abort(c.enclosingPosition, "@ranged can be applied only to expressions of the form: @ranged class Foo extends Int(1 to 10)")
      }
    }

    def constrainedClass(target: TypeName, base: TypeName, min: Tree, max: Tree): c.Expr[Any] = {
      val convertFunctionName = TermName("convert" + target + "To" + base)

      val outputs =
        q"""class ${target}(val v: $base) extends AnyVal

            import scala.language.implicitConversions

            implicit def $convertFunctionName(t: $target): $base = t.v

            object ${target.toTermName} {
              def unsafe(v: $base) = new $target(v)

              import scala.language.experimental.macros
              import scala.reflect.macros.blackbox

              def apply(v: $base): $target = macro applyImpl

              def applyImpl(c: blackbox.Context)(v: c.Tree): c.Tree = {
                import c.universe._
                v match {
                  case Literal(Constant(x: $base)) if x >= $min && x <= $max => {
                    Apply(Select(Ident(TermName(${target.toString})), TermName("unsafe")), List(v))
                  }
                  case Literal(Constant(x: $base)) => c.abort(c.enclosingPosition, "value out of range")
                  case _ => c.abort(c.enclosingPosition, "only literal constant values allowed")
                }
              }
            }
         """

      c.Expr[Any](outputs)
    }
  }

}
