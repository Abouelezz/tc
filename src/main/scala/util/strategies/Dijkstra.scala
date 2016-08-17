package util.strategies

import models._
import org.tc.exceptions.{NoNearbyStationsException, NoRouteException}

import scala.collection.mutable
import scala.collection.mutable.{Map => MutableMap}

/**
  * The main class to Calculate the short path from the source to destination
  * @param graph the EdgeWeightedDigraph that will handle the transportation system
  */
class Dijkstra(graph: EdgeWeightedDigraph) extends StrategyTrait {

  /**
    * This will save all the time needed for all other destinations
    */
  protected var distTo: MutableMap[Vertex, Double] = MutableMap.empty

  /**
    * This will save all the edges need to the other destinations
    *     The key is the destination vertex
    *     The value is the edge
    */
  protected var edgeTo: Map[Vertex, DirectedEdge] = Map.empty

  /**
    * Because scala's PriorityQueue is not keyed a tuple has been used
    * (Int, Double):
    *         _1 is the key of the destination vertex
    *         _2 Time needed
    */
  protected val pq = mutable.PriorityQueue.empty[(Vertex, Double)](
    Ordering.by((_: (Vertex, Double))._2).reverse
  )

  /**
    * Will be used for caching purposes
    * So if the last source matching the new one then we will need to go throw every thing again
    */
  protected var lastSource: Vertex = _

  /**
    * This should go throw all the graph's vertices and make
    * all destinations = Infinity since it's not visited yet
    *
    * @param source the Int that represents the starting point
    */
  private def prepare(source: Vertex) = {

    /**
      * Because every edge is not visited yet, all of them is Infinity
      * except for the source is 0 because it's known already
      */
    graph.getEdges.map(_._2.map( edge => edge.to match
      {
        case `source` => distTo += (edge.to -> 0)
        case _ => distTo += (edge.to -> Double.PositiveInfinity)
      }))

    /* source's destination always zero */
    pq += ((source, 0))

    /* Relax all edges */
    while (pq.nonEmpty) relax(graph, pq.dequeue._1)

    /* Saving the last relaxed source for caching purposes  */
    lastSource = source
  }

  /**
    *
    * @param source the Vertex that represents the source point
    * @param destination The Vertex representation of the destination point
    * @return
    */
  def getShortPath(source: Vertex, destination: Vertex): Path = {

    require(source != destination, "source and destination should be different")

    val shortPath = mutable.ListBuffer.empty[DirectedEdge]

    if (lastSource != source) prepare(source)

    /**
      * If the destination is not exist in the edgeTo map then it's
      * defiantly unknown path
      */
    if (!edgeTo.contains(destination)) {

      throw new NoRouteException(source, destination)
    }

    /**
      * Fill the short path stack
      * This will go throw the edgeTo map reversely until it reaches the start point
      */

    /* Te destination should always be the end of the stack */
    shortPath.prepend(edgeTo(destination))

    /**
      * Once we reach the start point it will end
      * otherwise it should always get prev edge reversely
      */
    while (shortPath.head.from != source) shortPath.prepend(edgeTo(shortPath.head.from))

    var vertices = shortPath.map(_.from)
    vertices += shortPath.last.to

    Path(vertices , shortPath.map(_.weight.toInt).sum)
  }

  /**
    * this will use dijkstra's algorithm to get the shortest path
    *
    * @param source the Vertex that represents the starting point
    * @param timeLimit The max time to reach this point
    * @return
    */
  def getNearBy(source: Vertex, timeLimit: Double): NearbyVertices = {

    require(timeLimit > 0, "time limit should be a positive number")

    try {

      if (lastSource != source) prepare(source)
    } catch {
      case e: Exception => throw new NoNearbyStationsException(source, timeLimit)
    }

    /**
      * This filters away the source and all destinations > time limit
      */
    val nbMap: List[(Vertex, Double)] = distTo.filter(_._1 != source).filter(_._2 <= timeLimit).toList

    if (nbMap.isEmpty) throw new NoNearbyStationsException(source, timeLimit)

    NearbyVertices(nbMap)
  }

  /**
    * Relax all edges
    *
    * @param graph the EdgeWeightedDigraph that will handle the transportation system
    * @param v the current vertex
    */
  private def relax(graph: EdgeWeightedDigraph, v: Vertex) = {

    if (graph.getEdge(v).isDefined) {
      /**
        * This will go throw all the edges connected to this vertex
        */

      graph.getEdges(v) foreach (edge => {

        /* The next destination */
        val w = edge.to

        /* When we have a shorter edge then save it */
        if (distTo.exists(_._1 == v)
          && distTo(w) > distTo(v) + edge.weight) {

          distTo.update(w, distTo(v) + edge.weight)
          edgeTo += (w -> edge)

          /**
            * If we have already one in the PQ then update
            * Otherwise create a new one
            */
          pq.exists(_._1 == w) match {
            case true => {
              pq.dropWhile(_._1 == w)
              pq += ((w, distTo(w)))
            }
            case false => pq += ((w, distTo(w)))
          }
        }
      })
    }
  }
}