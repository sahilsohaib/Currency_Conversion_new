import scala.collection.mutable.HashMap

class CurrencyConverter {
  private val exchangeRates = new HashMap[(Currency, Currency), Double]()

  def addExchangeRate(rate: ExchangeRate): Unit = {
    exchangeRates((rate.fromCurrency, rate.toCurrency)) = rate.rate
  }

  def convert(amount: Double, fromCurrency: Currency, toCurrency: Currency): Double = {
    if (fromCurrency == toCurrency) {
      amount
    } else {
      exchangeRates.get((fromCurrency, toCurrency)) match {
        case Some(rate) => amount * rate
        case None => throw new IllegalArgumentException("No exchange rate found for the specified currencies.")
      }
    }
  }
}

object CurrencyConverterApp extends App {
  val usd = Currency("USD", "United States Dollar")
  val eur = Currency("EUR", "Euro")
  val gbp = Currency("GBP", "British Pound Sterling")

  val converter = new CurrencyConverter()

  println("Enter exchange rates (e.g., USD to EUR rate: 0.85):")
  val usdToEurRate = ExchangeRate(usd, eur, scala.io.StdIn.readDouble())
  val eurToUsdRate = ExchangeRate(eur, usd, scala.io.StdIn.readDouble())
  val usdToGbpRate = ExchangeRate(usd, gbp, scala.io.StdIn.readDouble())

  converter.addExchangeRate(usdToEurRate)
  converter.addExchangeRate(eurToUsdRate)
  converter.addExchangeRate(usdToGbpRate)

  println("Enter amount and source currency code (e.g., 100 USD):")
  val amountAndCurrency = scala.io.StdIn.readLine().split(" ")
  val amount = amountAndCurrency(0).toDouble
  val sourceCurrencyCode = amountAndCurrency(1)

  val sourceCurrency = if (sourceCurrencyCode == "USD") usd
  else if (sourceCurrencyCode == "EUR") eur
  else if (sourceCurrencyCode == "GBP") gbp
  else throw new IllegalArgumentException("Unsupported source currency code.")

  println("Enter target currency code (e.g., EUR):")
  val targetCurrencyCode = scala.io.StdIn.readLine()

  val targetCurrency = if (targetCurrencyCode == "USD") usd
  else if (targetCurrencyCode == "EUR") eur
  else if (targetCurrencyCode == "GBP") gbp
  else throw new IllegalArgumentException("Unsupported target currency code.")

  try {
    val convertedAmount = converter.convert(amount, sourceCurrency, targetCurrency)
    println(s"$amount $sourceCurrencyCode is equal to $convertedAmount $targetCurrencyCode")
  } catch {
    case e: IllegalArgumentException => println(e.getMessage)
  }
}


