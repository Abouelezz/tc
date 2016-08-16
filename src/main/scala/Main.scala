package org.tc

import exceptions.{NoNearbyStationsException, NoRouteException}
import util.TransportationCalculator
import models.{DirectedEdge, EdgeWeightedDigraph}
import util.io.{inputParser, inputReader}

import scala.collection.mutable
import collection.mutable.{Map => MutableMap}


/**
  * This is the main class and it's mainly dealing with the Input and output logic
  */
object Main {

  /**
    * Helper function to print the short path
    *
    * @param pathStack the short path stack
    */
  private def printPath (pathStack: mutable.Stack[DirectedEdge]) = {

    val totalTime = pathStack.map(_.weight.toInt).sum

    val startPoint = pathStack.head.from

    val viaPointsString = pathStack.map(_.to).mkString(" -> ")

    println(s"$startPoint -> $viaPointsString: $totalTime")
  }

  /**
    * Helper function to print the near by stations
    *
    * @param nearBy map of the stations
    */
  private def printNearBy (nearBy: MutableMap[String, Double]) = {

    def formatNearBy(station: String, time: Int) = s"$station: $time"

    println(nearBy.toSeq.sortBy(_._2).map(v => formatNearBy(v._1, v._2.toInt)).mkString(", "))
  }

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
      transportationDigraph.addEdge(DirectedEdge(param._1, param._2, param._3))
    })

    /**
      * Initialising the TransportationCalculator with transportationDigraph
      */
    val TC =  new TransportationCalculator(transportationDigraph)

    /**
      * Try to find the shortest path and then print it
      */
    try {
      printPath(TC.shortPath(inputData.route._1, inputData.route._2))
    } catch {
      case e: NoRouteException => println(e)
    }

    /**
      * Try to find the nearby stations within the time limit and then print it
      */
    try {
      printNearBy(TC.nearBy(inputData.nearby._1, inputData.nearby._2))
    } catch {
      case e: NoNearbyStationsException => println(e)
    }
  }
}