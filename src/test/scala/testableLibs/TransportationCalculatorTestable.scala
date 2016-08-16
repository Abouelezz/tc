package testableLibs

import lib.TransportationCalculator
import models.EdgeWeightedDigraph

/**
  * this is the test version of TransportationCalculator
  * it has more Getter than it's needed on production or development
  */
class TransportationCalculatorTestable(graph: EdgeWeightedDigraph)  extends TransportationCalculator(graph) {

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
