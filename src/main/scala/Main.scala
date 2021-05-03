object Main extends App {
  import java.text.SimpleDateFormat
  import java.util.Calendar

  val date =
    new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance.getTime)
  val district = 188
  val minimumAge = 18

  import scala.concurrent.duration._
  import scala.language.postfixOps

  val interval = 20 seconds

  def sessionPredicate(session: ujson.Value): Boolean = {
    session("available_capacity").num > 0 && session(
      "min_age_limit"
    ).num == minimumAge
  }

  import upickle.default._

  for (_ <- 1 to 500) {
    Thread.sleep(interval.toMillis)

    val apiUrl =
      s"https://cdn-api.co-vin.in/api/v2/appointment/sessions/calendarByDistrict?district_id=$district&date=$date"

    val response = requests.get(apiUrl)

    val vaccineCenters = read[ujson.Value](response.text()).obj("centers").arr

    val availableCenters = vaccineCenters.filter(center => {
      center("sessions").arr.exists(sessionPredicate)
    })

    println("Available Centers: %d".format(availableCenters.length))
    println()

    availableCenters.foreach(center => {
      println("Center Name: %s".format(center("name").str))
      center("sessions").arr
        .filter(sessionPredicate)
        .foreach(session => {
          val vaccine = session("vaccine").str
          val available = session("available_capacity").num
          val date = session("date").str

          println("Vaccine: %s".format(vaccine))
          println("Available: %s".format(available))
          println("Date: %s".format(date))
          println()
          println()
        })
    })

    println()
  }
}
