package util.io

/**
  * This Object responsible for parsing the input lines
  */
object inputParser {

  /**
    * Station name regex pattern, example:
    * ALEXANDERPLATZ
    */
  val stationNamePattern = "[A-Za-z]+".r

  /**
    * Time regex pattern, example:
    * 123
    */
  val timePattern = "[0-9]+".r

  /**
    * Connection line regex pattern, example:
    * A -> B: 240
    */
  val connectionLinePattern = s"($stationNamePattern) -> ($stationNamePattern): ($timePattern)".r

  /**
    * Route line regex pattern, example:
    * route A -> B
    */
  val routeLinePattern = s"route ($stationNamePattern) -> ($stationNamePattern)".r

  /**
    * Nearby Line regex pattern, example:
    * nearby A, 130
    */
  val nearbyLinePattern = s"nearby ($stationNamePattern), ($timePattern)".r

  /**
    * Parse the connection line using regex
    *
    * @param line the input line
    * @return
    */
  @throws(classOf[MatchError])
  def parseConnectionLine(line: String): (String, String, Double) = {

    try {

      val connectionLinePattern(from, to, weight) = line
      (from, to, weight.toDouble)
    } catch {

      case e: Exception => throw new MatchError("Input Error: Invalid connection")
    }
  }

  /**
    * Parse the required route line using regex
    *
    * @param line the input line
    * @return
    */
  @throws(classOf[MatchError])
  def parseRouteLine(line: String): (String, String) = {

    try {

      val routeLinePattern(from, to) = line
      (from, to)
    } catch {

      case e: Exception => throw new MatchError("Input Error: Invalid Route")
    }
  }

  /**
    * Parse the nearby line
    *
    * @param line the input line
    * @return
    */
  @throws(classOf[MatchError])
  def parseNearbyLine(line: String): (String, Double) = {

    try {

      val nearbyLinePattern(from, timeLimit) = line
      (from, timeLimit.toDouble)
    } catch {

      case e: Exception => throw new MatchError("Input Error: Invalid Nearby")
    }
  }
}