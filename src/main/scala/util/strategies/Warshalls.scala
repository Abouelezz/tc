package util.strategies

import models._
import exceptions.{NoNearbyStationsException, NoRouteException}
import scala.collection.mutable
import scala.collection.mutable.{Map => MutableMap}

/**
  * This class is a strategy class to get the shortest path and the near by
  * This implementation uses the Floyd-Warshall algorithm.
  *
  * @param graph the directed graph
  */
class Warshalls(graph: EdgeWeightedDigraph) extends ShortestPathStrategyTrait {

  protected var distTo: MutableMap[(Vertex,Vertex), Double] = MutableMap.empty

  protected var edgeTo: MutableMap[(Vertex,Vertex), Option[DirectedEdge]] = MutableMap.empty

  /**
    * Flag to save the state and avoid rerun in the next operation
    */
  private var updated = false

  /**
    *
    * @param source the Vertex that represents the source point
    * @param destination The Vertex representation of the destination point
    * @return
    */
  def getShortPath(source: Vertex, destination: Vertex): Path = {

    require(source != destination, "source and destination should be different")

    doUpdateIfNot()

    val shortPath = mutable.ListBuffer.empty[DirectedEdge]

    /**
      * If the source and destination is not exist in the distTo then it's defiantly unknown path
      */
    if (!distTo.contains((source, destination)) || distTo((source, destination)) == Double.PositiveInfinity) {

      throw new NoRouteException(source, destination)
    }

    /**
      * Fill the short path stack
      * This will go throw the edgeTo map reversely until it reaches the start point
      */

    /* Te destination should always be the end of the stack */
    shortPath.prepend(edgeTo((source, destination)).get)

    /**
      * Once we reach the start point it will end
      * otherwise it should always get prev edge reversely
      */
    while (shortPath.head.from != source) shortPath.prepend(edgeTo(source, shortPath.head.from).get)

    generatePath(shortPath)
  }


  /**
    * This will get the near by vertices
    *
    * @param source the Vertex that represents the starting point
    * @param weight The max weight to reach this point
    * @return
    */
  def getNearBy(source: Vertex, weight: Double): NearbyVertices = {
    require(weight > 0, "weight should be a positive number")

    doUpdateIfNot()

    /**
      * This filters away the source and all destinations > weight
      */
    val nbMap: List[(Vertex, Double)] = distTo
      .filter(_._1._2 != source)
      .filter(_._1._1 == source)
      .filter(_._2 <= weight)
      .map( m=> (m._1._2, m._2))
      .toList

    if (nbMap.isEmpty) throw new NoNearbyStationsException(source, weight)

    NearbyVertices(nbMap)
  }

  /**
    * This will check if the update is happened before or not. and run it if not
    */
  private def doUpdateIfNot() = {

    if (!updated) {
      /**
        * The first stage, distTo and edgeTo has to be prepared before the update begins
        */
      prepare()

      /**
        * The second stage, Update will go throw all combinations of vertices and fill distTo and edgeTo
        */
      update()

      updated = true
    }
  }

  private def prepare() = {

    /**
      * Because all vertices is not visited yet, all of them is Infinity for now
      */
    graph.getVertices foreach { v =>
      graph.getVertices foreach { w =>
        distTo += (((v, w), Double.PositiveInfinity))
      }
    }

    /**
      * The known distances from edges has to be used
      */
    graph.getVertices foreach { v =>
      if (graph.getEdge(v).isDefined) {
        graph.getEdges(v) foreach { e =>
          distTo((e.from, e.to)) = e.weight
          edgeTo((e.from, e.to)) = Some(e)
        }

        /* in case of selfloops */
        if (distTo((v, v)) >= 0.0) {
          distTo((v, v)) = 0.0
          edgeTo((v, v)) = None
        }
      }
    }
  }

  /**
    * Calculate a shortest paths by checking each vertex to to every other vertex
    */
  def update() = {
    graph.getVertices foreach { i =>
      graph.getVertices foreach { v =>
        if (edgeTo.contains((v, i))) {
          graph.getVertices foreach { w =>
            if (distTo((v, w)) > distTo((v, i)) + distTo((i, w))) {
              distTo((v, w)) = distTo((v, i)) + distTo((i, w))
              edgeTo((v, w)) = edgeTo((i, w))
            }
          }
        }
      }
    }
  }
}