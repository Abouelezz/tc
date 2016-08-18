package util

import exceptions.MissingStrategyException
import models._
import util.strategies.{Dijkstra, ShortestPathStrategiesEnum, Warshalls}

import scala.util.{Success, Try}

/**
  * The main engine class
  *
  * @param graph the weighted directed graph that represents the public traffic system
  * @param strategyName the required strategy name
  */
class PublicTransportPlanner(graph: EdgeWeightedDigraph, strategyName: String) {

  /**
    * Load the right strategy and if not there throw an exception with the aviliable strategies
    */
  @throws(classOf[IllegalArgumentException])
  val strategy = Try {ShortestPathStrategiesEnum.withName(strategyName)} match {
    case Success(ShortestPathStrategiesEnum.Dijkstra) => new Dijkstra(graph)
    case Success(ShortestPathStrategiesEnum.Warshalls) => new Warshalls(graph)

    case _ => throw new MissingStrategyException(strategyName)
  }

  /**
    * This method will get the shortest path from the strategy
    *
    * @param source the vertex that represents the starting station
    * @param destination The vertex that representation of the destination station
    * @return
    */
  def getShortPath(source: Vertex, destination: Vertex): Path = strategy.getShortPath(source, destination)

  /**
    * This method will get the nearby stations from the strategy
    *
    * @param source the Int that represents the starting point
    * @param timeLimit The max time to reach this point
    * @return
    */
  def getNearBy(source: Vertex, timeLimit: Double): NearbyVertices = strategy.getNearBy(source, timeLimit)
}