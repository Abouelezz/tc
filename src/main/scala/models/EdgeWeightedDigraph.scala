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
  private var edges: MutableMap[String, List[DirectedEdge]] = MutableMap.empty

  /**
    * This will add a new edge
    * @param e the new edge
    */
  def addEdge(e: DirectedEdge) = {

    /* If it's the first time we initiate an empty list*/
    if (edges.get(e.from).isEmpty) edges += (e.from -> List.empty)

    edges.update(e.from, e :: edges(e.from))
    E += 1
  }

  /**
    * Return the edge identified by the from point
    *
    * @param v the from vertex
    * @return
    */
  def getEdge(v: String) = edges.get(v)

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
