package util.io

import scala.util.Try

/**
  * input reader class, should read input stream looks like that
  * 8
  * A -> B: 240
  * A -> C: 70
  * A -> D: 120
  * C -> B: 60
  * D -> E: 480
  * C -> E: 240
  * B -> E: 210
  * E -> A: 300
  * route A -> B
  * nearby A, 130
  */
object inputReader {

  /**
    * This will gather all the need data
    *
    * @param readLine the function that reads normal string line
    * @param readInt the function the reads integer line
    * @return
    */
  @throws(classOf[MatchError])
  def gather(readLine: () => String, readInt: () => Int): InputDataModel = {

    /**
      * A Try of the connection count
      */
    val connectionsCountTry = getConnectionsCount(readInt)

    /* If the connection line wasn't valid */
    if (connectionsCountTry.isFailure) {
      throw new MatchError("Edges count is incorrect")
    }

    /* real numbers of connections */
    val connectionsCount = connectionsCountTry.get

    /* all connections parameters */
    val connectionsParams = getConnections(readLine, connectionsCount).map(inputParser.parseConnectionLine)

    /* the route line parameters */
    val route = inputParser.parseRouteLine(readLine())

    /* the nearby line parameters */
    val nearby = inputParser.parseNearbyLine(readLine())

    InputDataModel(connectionsCount, connectionsParams, route, nearby)
  }

  /**
    * Should be the number at the top
    *
    * @param readInt the function that retrieves Int
    * @return
    */
  def getConnectionsCount(readInt: () => Int) = Try { readInt() }

  /**
    * This will gather the connections lines
    *
    * @param readLine the readline function
    * @param connectionsCount edges count
    * @return
    */
  def getConnections(readLine: () => String, connectionsCount: Int) = {

    var connections: List[String] = List.empty

    for (i <- 1 to connectionsCount) {

      connections = connections :+ readLine()
    }

    connections
  }
}
