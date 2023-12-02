class ExchangeRate(val fromCurrency: Currency, val toCurrency: Currency, val rate: Double)

object ExchangeRate {
  def apply(fromCurrency: Currency, toCurrency: Currency, rate: Double): ExchangeRate =
    new ExchangeRate(fromCurrency, toCurrency, rate)
}

