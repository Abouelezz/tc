package models

/**
  * This will handle the nearby vertices
  *
  * @todo spec test
  * @param vertices list of vertices with time
  */
case class NearbyVertices(vertices: List[(Vertex, Double)]) {
  override def toString = {

    def formatNearby(station: Vertex, time: Double) = s"$station: ${time.toInt}"

    vertices.sortBy(_._2).map(v => formatNearby(v._1, v._2)).mkString(", ")
  }
}
