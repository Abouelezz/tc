package models

import scala.collection.mutable.ListBuffer

/**
  * This will handle that resulted Path
  *
  * @todo spec test
  * @param vertices list of the vertices
  * @param weight the sum of weight of all edges
  */
case class Path(vertices: ListBuffer[Vertex], weight: Double) {

  override def toString = {

    s"${vertices.mkString(" -> ")}: ${weight.toInt}"
  }
}
