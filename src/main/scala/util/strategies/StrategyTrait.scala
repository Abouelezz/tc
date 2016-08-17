package util.strategies

import models.{NearbyVertices, Path, Vertex}

/**
  * Basic functionality and interface for any Strategy class
  */
trait StrategyTrait {

  /**
    * this will get shortest path
    *
    * @param source the Vertex that represents the source point
    * @param destination The Vertex representation of the destination point
    * @return
    */
  def getShortPath(source: Vertex, destination: Vertex): Path

  /**
    * this will use dijkstra's algorithm to get the shortest path
    *
    * @param source the Vertex that represents the starting point
    * @param timeLimit The max time to reach this point
    * @return
    */
  def getNearBy(source: Vertex, timeLimit: Double): NearbyVertices

}
