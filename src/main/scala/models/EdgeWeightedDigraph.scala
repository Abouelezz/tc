package models

import scala.collection.mutable.{Map => MutableMap}

/**
  * This is the implementation of Edge Weighted Directed graph
  */
class EdgeWeightedDigraph() {

  /* Number of edges */
  private var E = 0

  /**
    * Directed graph edges :
    *           The key is the from vertex
    *           The value is a list of all connected edges
    */
  private var edges: MutableMap[Vertex, List[DirectedEdge]] = MutableMap.empty

  /**
    * This will add a new edge
    * Also it will avoid the duplicated edges with the same weight, and if there a
    * different weight for the same edge it will only take the less.
    *
    * @param e the new edge
    */
  def addEdge(e: DirectedEdge) = {

    // Get the all edges for the vertex and f not exist get a new one
    var edgesBag = edges.getOrElse(e.from, List.empty)

    // Make sure there is no other similar edges
    if (!edgesBag.exists(de => de.weight <= e.weight && de.to == e.to)) {

      // If there is already an edge with more weight then remove it
      if (edgesBag.exists(de => de.weight > e.weight && de.to == e.to)) {
        edgesBag = edgesBag.dropWhile(de => de.to == e.to)
        E -=1
      }

      // Add the edge to the bag
      edges.update(e.from, e :: edgesBag)
      E += 1
    }
  }

  /**
    * Return the edge identified by the from point
    *
    * @param v the from vertex
    * @return
    */
  def getEdge(v: Vertex) = edges.get(v)

  /**
    * Getter for the number of edges
    *
    * @return Int
    */
  def getEdges = edges

  /**
    * returns Edges count
    *
    * @return Int
    */
  def edgesCount = E
}
