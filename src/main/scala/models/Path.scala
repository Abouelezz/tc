package models

import scala.collection.mutable.ListBuffer

/**
  * This will handle that resulted Path
  *
  * @param vertices list of the stations
  * @param time the time needed
  */
case class Path(vertices: ListBuffer[Vertex], time: Double) {
  override def toString = {
    s"${vertices.mkString(" -> ")}: ${time.toInt}"
  }
}
