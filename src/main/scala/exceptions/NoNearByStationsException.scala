package org.tc.exceptions

/**
  * Exception used if the route is not exist
  * @param source the source vertex
  * @param time the the time limit
  */
class NoNearByStationsException(source: String, time: Double) extends Exception {
  override def toString: String = {
    f"Error: No nearby stations from $source within $time second(s)"
  }
}