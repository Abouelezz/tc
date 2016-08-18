package util.strategies

import models.{DirectedEdge, NearbyVertices, Path, Vertex}
import exceptions.{NoNearbyStationsException, NoRouteException}

import scala.collection.mutable

/**
  * Basic functionality and interface for ShortestPathStrategy class
  */
trait ShortestPathStrategyTrait {

  /**
    * this will get shortest path
    *
    * @param source the Vertex that represents the source point
    * @param destination The Vertex representation of the destination point
    * @return
    */
  @throws(classOf[NoRouteException])
  def getShortPath(source: Vertex, destination: Vertex): Path

  /**
    * This will should the shortest path
    *
    * @param source the Vertex that represents the starting point
    * @param weight The max weight to reach this point
    * @return
    */
  @throws(classOf[NoNearbyStationsException])
  def getNearBy(source: Vertex, weight: Double): NearbyVertices

  /**
    * It will take a linked list of edges and turns it into a presentable path
    *
    * @param shortPath the linked list of Edges
    * @return
    */
  def generatePath(shortPath: mutable.ListBuffer[DirectedEdge]): Path = {

    var vertices = shortPath.map(_.from)
    vertices += shortPath.last.to

    Path(vertices, shortPath.map(_.weight.toInt).sum)
  }
}
