class Currency(val code: String, val name: String)

object Currency {
  def apply(code: String, name: String): Currency = new Currency(code, name)
}
