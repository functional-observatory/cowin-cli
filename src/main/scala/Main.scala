import upickle.default._

import javax.sound.sampled.AudioSystem
import scala.concurrent.duration._
import scala.language.postfixOps
import java.io.BufferedInputStream

object Main extends App {
  val date = "03-05-2021"
  val district = 188

  val interval = 20 seconds

  val clip = AudioSystem.getClip
  val notificationSoundStream = getClass.getResourceAsStream("53.au")
  // https://stackoverflow.com/questions/5529754/java-io-ioexception-mark-reset-not-supported
  val notificationSoundBuffer = new BufferedInputStream(
    notificationSoundStream
  )
  val inputStream = AudioSystem.getAudioInputStream(notificationSoundBuffer)
  clip.open(inputStream)

  def sessionPredicate(session: ujson.Value): Boolean = {
    session("available_capacity").num > 0 && session(
      "min_age_limit"
    ).num == 18
  }

  for (_ <- 1 to 500) {
    Thread.sleep(interval.toMillis)

    val apiUrl =
      s"https://cdn-api.co-vin.in/api/v2/appointment/sessions/calendarByDistrict?district_id=$district&date=$date"

    val response = requests.get(apiUrl)

    val vaccineCenters = read[ujson.Value](response.text()).obj("centers").arr

    val availableCenters = vaccineCenters.filter(center => {
      center("sessions").arr.exists(sessionPredicate)
    })

    clip.start()

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
