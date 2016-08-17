package test.testableLibs

import util.PublicTransportPlanner
import models.EdgeWeightedDigraph

/**
  * this is the test version of PublicTransportPlanner
  * it has more Getter than it's needed on production or development
  */
class PublicTransportPlannerTestable(graph: EdgeWeightedDigraph)  extends PublicTransportPlanner(graph) {

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
