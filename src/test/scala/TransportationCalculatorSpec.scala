package org.tc.test

import lib.TransportationCalculator
import models.{DirectedEdge, EdgeWeightedDigraph}
import org.scalatest._
import org.tc.exceptions.{NoNearByStationsException, NoRouteException}
import org.tc.EdgeWeightedDigraph

class TransportationCalculatorSpec extends FlatSpec {

  val edgeWeightedDigraph = new EdgeWeightedDigraph
  var TC: TransportationCalculator = null

  "An TransportationCalculator" should "get the right short path with the right direction" in {

    edgeWeightedDigraph.addEdge(DirectedEdge("A", "B", 240))
    edgeWeightedDigraph.addEdge(DirectedEdge("A", "D", 120))
    edgeWeightedDigraph.addEdge(DirectedEdge("C", "B", 160))
    edgeWeightedDigraph.addEdge(DirectedEdge("D", "E", 480))
    edgeWeightedDigraph.addEdge(DirectedEdge("C", "E", 240))
    edgeWeightedDigraph.addEdge(DirectedEdge("B", "E", 210))
    edgeWeightedDigraph.addEdge(DirectedEdge("E", "A", 300))
    edgeWeightedDigraph.addEdge(DirectedEdge("X", "A", 300))

    TC =  new TransportationCalculator(edgeWeightedDigraph)

    assert(TC.shortPath("A", "B").toString() == "Stack(DirectedEdge(A,B,240.0))")

    edgeWeightedDigraph.addEdge(DirectedEdge("A", "C", 70))

    TC =  new TransportationCalculator(edgeWeightedDigraph)

    assert(TC.shortPath("A", "B").toString() == "Stack(DirectedEdge(A,C,70.0), DirectedEdge(C,B,160.0))")
  }

  it should "throw error if the path is not exist in the required direction" in {
    assertThrows[NoRouteException] {
      TC.shortPath("A", "X")
    }
  }

  it should "get the right nearby stations without the time limit" in {

    assert(TC.nearBy("A", 200).toString() == "Map(D -> 120.0, C -> 70.0)")
  }

  it should "throw error if no stations available within the time limit needed" in {
    assertThrows[NoNearByStationsException] {
      TC.nearBy("A", 20)
    }
  }
}