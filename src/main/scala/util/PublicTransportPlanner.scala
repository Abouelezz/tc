package util

import models._
import util.strategies.{Dijkstra, StrategiesEnum}

import scala.util.{Success, Try}


class PublicTransportPlanner(graph: EdgeWeightedDigraph, strategyName: String) {

  /**
    * Load the right strategy and if not there throw an exception with the aviliable strategies
    */
  val strategy = Try {StrategiesEnum.withName(strategyName)} match {
    case Success(StrategiesEnum.Dijkstra) => new Dijkstra(graph)
    case _ => throw new IllegalArgumentException(
      s"Strategy $strategyName is not available. Only ${StrategiesEnum.values.mkString(", ")} is available"
    )
  }

  /**
    *
    *
    * @param source the Int that represents the starting point
    * @param destination The String representation of the destination station
    * @return
    */
  def getShortPath(source: Vertex, destination: Vertex): Path = strategy.getShortPath(source, destination)

  /**
    *
    *
    * @param source the Int that represents the starting point
    * @param timeLimit The max time to reach this point
    * @return
    */
  def nearBy(source: Vertex, timeLimit: Double): NearbyVertices = strategy.getNearBy(source, timeLimit)
}