package lib

import models.{DirectedEdge, EdgeWeightedDigraph}
import org.tc.exceptions.{NoNearByStationsException, NoRouteException}

import scala.collection.mutable
import scala.collection.mutable.{Map => MutableMap}

/**
  * The main class to Calculate the short pathe from the source to destination
  * @param graph the EdgeWeightedDigraph that will handle the transportation system
  */
class TransportationCalculator(graph: EdgeWeightedDigraph) {

  /**
    * This will save all the time needed for all other destinations
    */
  private var distTo: MutableMap[String, Double] = MutableMap.empty

  /**
    * This will save all the edges need to the other destinations
    *     The key is the destination vertex
    *     The value is the edge
    */
  private var edgeTo: Map[String, DirectedEdge] = Map.empty

  /**
    * Because scala's PriorityQueue is not keyed a tuple has been used
    * (Int, Double):
    *         _1 is the key of the destination vertex
    *         _2 Time needed
    */
  private val pq = mutable.PriorityQueue.empty[(String, Double)](
    Ordering.by((_: (String, Double))._1).reverse
  )

  /**
    * Will be used for caching purposes
    * So if the last source matching the new one then we will need to go throw every thing again
    */
  private var lastSource = ""

  /**
    * This should go throw all the graph's vertices and make
    * all destinations = Infinity since it's not visited yet
    *
    * @param source the Int that represents the starting point
    */
  private def prepare(source: String) = {

    /**
      * Because every vertex is not visited yet, all of them = Infinity
      * except for the source = 0 because it's known already
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
    * this will use dijkstra's algorithm to get the shortest path
    *
    * @param source the Int that represents the starting point
    * @param destination The String representation of the destination station
    * @return
    */
  def shortPath(source: String, destination: String): mutable.Stack[DirectedEdge] = {

    require(source != destination, "source and destination should be different")

    val shortPathStack = new mutable.Stack[DirectedEdge]()

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
    shortPathStack.push(edgeTo(destination))

    /**
      * Once we reach the start point it will end
      * otherwise it should always get prev edge reversely
      */
    while (shortPathStack.head.from != source) shortPathStack.push(edgeTo(shortPathStack.head.from))
    shortPathStack
  }

  /**
    * this will use dijkstra's algorithm to get the shortest path
    *
    * @param source the Int that represents the starting point
    * @param timeLimit The max time to reach this point
    * @return
    */
  def nearBy(source: String, timeLimit: Double): MutableMap[String, Double] = {

    require(timeLimit > 0, "time limit should be a positive number")

    try {

      if (lastSource != source) prepare(source)
    } catch {
      case e: Exception => throw new NoNearByStationsException(source, timeLimit)
    }

    /**
      * This filters away the source and all destinations > time limit
      */
    val nbMap = distTo.filter(_._1 != source).filter(_._2 <= timeLimit)

    if (nbMap.isEmpty) throw new NoNearByStationsException(source, timeLimit)

    nbMap
  }

  /**
    * Relax all edges
    *
    * @param graph the EdgeWeightedDigraph that will handle the transportation system
    * @param v the current vertex
    */
  private def relax(graph: EdgeWeightedDigraph, v: String) = {

    graph.getEdge(v).orNull match {

      case edges: List[DirectedEdge] => {
        /**
          * This will go throw all the edges connected to this vertex
          */

        edges foreach (edge => {

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
      case null =>
    }
  }
}