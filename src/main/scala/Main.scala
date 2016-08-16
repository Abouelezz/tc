package org.tc

import exceptions.{NoNearbyStationsException, NoRouteException}
import lib.TransportationCalculator
import models.{DirectedEdge, EdgeWeightedDigraph}

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

    var connections = 0
    var route: (String, String) = null
    var nearby: (String, Double) = null
    var connectionsParams: Array[(String, String, Double)] = Array.empty

    /**
      * Parse the connection line using regex
      *
      * @param line the input line
      * @return
      */
    def parseConnectionLine(line: String): (String, String, Double) = {
      val pattern = "([A-Z]+) -> ([A-Z]+): ([0-9]+)".r
      val pattern(from, to, weight) = line
      (from, to, weight.toDouble)
    }

    /**
      * Parse the required route line using regex
      *
      * @param line the input line
      * @return
      */
    def parseRouteLine(line: String): (String, String) = {
      val pattern = "route ([A-Z]+) -> ([A-Z]+)".r
      val pattern(from, to) = line
      (from, to)
    }

    /**
      * Parse the nearby line
      *
      * @param line the input line
      * @return
      */
    def parseNearbyLine(line: String): (String, Double) = {
      val pattern = "nearby ([A-Z]+), ([0-9]+)".r
      val pattern(from, timeLimit) = line
      (from, timeLimit.toDouble)
    }

    /**
      * Try to parse the whole input first and if failed it will throw an IllegalArgumentException
      */
    try {
      connections = scala.io.StdIn.readInt()

      for (i <- 1 to connections) {
        connectionsParams = connectionsParams :+ parseConnectionLine(scala.io.StdIn.readLine)
      }

      route = parseRouteLine(scala.io.StdIn.readLine)

      nearby = parseNearbyLine(scala.io.StdIn.readLine)
    } catch {
      case e: Exception => throw new IllegalArgumentException("Error in input format")
    }

    /**
      * Every connection is represented as a DirectedEdge
      * it creates every DirectedEdge then adds it to the
      * transportation Directed graph
      */
    connectionsParams.foreach(param => {
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
      printPath(TC.shortPath(route._1, route._2))
    } catch {
      case e: NoRouteException => println(e)
    }

    /**
      * Try to find the nearby stations within the time limit and then print it
      */
    try {
      printNearBy(TC.nearBy(nearby._1, nearby._2))
    } catch {
      case e: NoNearbyStationsException => println(e)
    }
  }
}