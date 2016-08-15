package org.tc.exceptions

/**
  * Exception used if the route is not exist
  * @param source the source vertex
  * @param destination the destination vertex
  */
class NoRouteException(source: String, destination: String) extends Exception {
  override def toString: String = {
    s"Error: No route from $source to $destination"
  }
}