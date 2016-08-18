package exceptions

import models.Vertex

/**
  * Exception used if no nearby stations within the time limit
  *
  * @param source the source vertex
  * @param time the the time limit
  */
class NoNearbyStationsException(source: Vertex, time: Double) extends Exception {

  override def toString: String = {

    f"Error: No nearby stations from $source within $time second(s)"
  }
}