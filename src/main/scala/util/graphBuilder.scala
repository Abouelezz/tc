package util

import models.{DirectedEdge, EdgeWeightedDigraph, Vertex}

/**
  * this object responsible for building the directed transportation graph
  * using of parameters of primitive types
  *
  * @todo spec test
  */
object graphBuilder {

  /**
    * The build function, first it initialize an empty directed graph then it takes
    * a list of tuples as the edges parameter then creates object for every vertex
    * and create object for every edge then fill the graph.
    *
    * @param connections
    * @return
    */
  def build(connections: List[(String, String, Double)]): EdgeWeightedDigraph = {
    val transportationDigraph = new EdgeWeightedDigraph

    /**
      * Every connection is represented as a DirectedEdge
      * it creates every DirectedEdge then adds it to the
      * transportation Directed graph
      */

    connections.foreach( param => {
      transportationDigraph.addEdge(
        DirectedEdge(
          Vertex(param._1), // from
          Vertex(param._2), // to
          param._3) // time
      )
    })

    transportationDigraph
  }
}
