package test.testables

import models.EdgeWeightedDigraph
import util.strategies.Dijkstra

/**
  * this is the test version of Dijkstra strategy class
  * it has more Getters than it's needed on production or development
  */
class DijkstraTestable(graph: EdgeWeightedDigraph)  extends Dijkstra(graph) {

  /**
    * Getter for distTo
    *
    * @return
    */
  def getDistTo = distTo

  /**
    * Getter for EdgeTo
    *
    * @return
    */
  def getEdgeTo = edgeTo

  /**
    * Getter for the PQ
    *
    * @return
    */
  def getPq = pq
}
