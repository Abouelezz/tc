package org.tc

import exceptions.{NoNearbyStationsException, NoRouteException}
import util.{PublicTransportPlanner, graphBuilder}
import models.{DirectedEdge, EdgeWeightedDigraph, Vertex}
import util.io._

object Main {


  /**
    * The main client
    * @param args
    */
  def main(args: Array[String]) = {



    val inputData = inputReader.gather(scala.io.StdIn.readLine, scala.io.StdIn.readInt)

    /**
      * Calling the graphBuilder to build the whole graph object out of parameters
      */
    val transportationDiraph = graphBuilder.build(inputData.connections)

    /**
      * Initialising the PublicTransportPlanner with transportationDigraph
      */
    val TC =  new PublicTransportPlanner(transportationDiraph)

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