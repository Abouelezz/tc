package org.tc

import exceptions.{NoNearbyStationsException, NoRouteException}
import util.PublicTransportPlanner
import models.{DirectedEdge, EdgeWeightedDigraph, Vertex}
import util.io._

object Main {


  /**
    * The main client
    * @param args
    */
  def main(args: Array[String]) = {

    val transportationDigraph = new EdgeWeightedDigraph

    val inputData = inputReader.gather(scala.io.StdIn.readLine, scala.io.StdIn.readInt)

    /**
      * Every connection is represented as a DirectedEdge
      * it creates every DirectedEdge then adds it to the
      * transportation Directed graph
      */
    inputData.connections.foreach(param => {
      transportationDigraph.addEdge(DirectedEdge(Vertex(param._1), Vertex(param._2), param._3))
    })

    /**
      * Initialising the PublicTransportPlanner with transportationDigraph
      */
    val TC =  new PublicTransportPlanner(transportationDigraph)

    /**
      * Try to find the shortest path and then print it
      */
    try {
      println(TC.shortPath(Vertex(inputData.route._1), Vertex(inputData.route._2)))
    } catch {
      case e: NoRouteException => println(e)
    }

    /**
      * Try to find the nearby stations within the time limit and then print it
      */
    try {
      println(TC.nearBy(Vertex(inputData.nearby._1), inputData.nearby._2))
    } catch {
      case e: NoNearbyStationsException => println(e)
    }
  }
}