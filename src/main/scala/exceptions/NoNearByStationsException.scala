package org.tc.exceptions

/**
  * Exception used if no nearby stations within the time limit
  * @param source the source vertex
  * @param time the the time limit
  */
class NoNearbyStationsException(source: String, time: Double) extends Exception {
  override def toString: String = {
    f"Error: No nearby stations from $source within $time second(s)"
  }
}