package testables

import models.EdgeWeightedDigraph
import util.strategies.Warshalls

/**
  * this is the test version of Dijkstra strategy class
  * it has more Getters than it's needed on production or development
  */
class WarshallsTestable(graph: EdgeWeightedDigraph)  extends Warshalls(graph) {

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
}
