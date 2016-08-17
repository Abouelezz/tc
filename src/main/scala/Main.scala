package org.tc

import util.{PublicTransportPlanner, graphBuilder}
import models.Vertex
import util.io._
import util.strategies.StrategiesEnum

object Main {

  /**
    * The main client
    * @param args
    */
  def main(args: Array[String]) = {

    val strategy = args.length match {
      case 1 => args(0)
      case _ => StrategiesEnum.default.toString
    }

    try {

      /**
        * Let the input reader gather the input data first
        */
      val inputData = inputReader.gather(scala.io.StdIn.readLine, scala.io.StdIn.readInt)

      /**
        * Calling the graphBuilder to build the whole graph object out of parameters
        */
      val transportationDigraph = graphBuilder.build(inputData.connections)

      /**
        * Initialising the PublicTransportPlanner with transportationDigraph
        */
      val TC =  new PublicTransportPlanner(transportationDigraph, strategy)

      /**
        * find the shortest path and then print it
        */

      println(TC.getShortPath(Vertex(inputData.route._1), Vertex(inputData.route._2)))

      /**
        * find the nearby stations within the time limit and then print it
        */
        println(TC.nearBy(Vertex(inputData.nearby._1), inputData.nearby._2))

    } catch {
      case e: Exception => println(e)
    }
  }
}