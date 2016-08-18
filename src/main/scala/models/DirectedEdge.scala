package models

/**
  * This case class is the implementation of the directed graph edges
  *
  * @param from vertex from
  * @param to vertex to
  * @param weight the edge weight
  */
case class DirectedEdge(from: Vertex, to: Vertex, weight: Double) {

  require(weight > 0, "Edge weight has to be a positive number or zero")
}