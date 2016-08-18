package exceptions

import models.Vertex

/**
  * Exception used if the route is not exist
  *
  * @param source the source vertex
  * @param destination the destination vertex
  */
class NoRouteException(source: Vertex, destination: Vertex) extends Exception {

  override def toString: String = {

    s"Error: No route from $source to $destination"
  }
}