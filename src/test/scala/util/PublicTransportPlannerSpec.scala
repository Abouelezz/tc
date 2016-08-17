package test.util

import models.{DirectedEdge, EdgeWeightedDigraph, Vertex}
import org.scalatest._
import org.tc.exceptions.{NoNearbyStationsException, NoRouteException}
import test.testableLibs.{PublicTransportPlannerTestable => PublicTransportPlanner}

class PublicTransportPlannerSpec extends FlatSpec {

  val edgeWeightedDigraph = new EdgeWeightedDigraph
  var TC: PublicTransportPlanner = _

  "TransportationCalculator" should "get the right short path with the right direction" in {

    edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("A"), Vertex("B"), 240))
    edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("A"), Vertex("D"), 120))
    edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("C"), Vertex("B"), 160))
    edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("D"), Vertex("E"), 480))
    edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("C"), Vertex("E"), 240))
    edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("B"), Vertex("E"), 210))
    edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("E"), Vertex("A"), 300))
    edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("X"), Vertex("A"), 300))

    TC =  new PublicTransportPlanner(edgeWeightedDigraph)

    assert(TC.shortPath(Vertex("A"), Vertex("B")).toString() == "A -> B: 240")

    edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("A"), Vertex("C"), 70))

    TC =  new PublicTransportPlanner(edgeWeightedDigraph)

    assert(TC.shortPath(Vertex("A"), Vertex("B")).toString() == "A -> C -> B: 230")
  }

  it should "have the right distTo map" in {
      assert(TC.getDistTo.toString() == "Map(A -> 0.0, E -> 310.0, B -> 230.0, D -> 120.0, C -> 70.0)")

  }

  it should "have the right edgeTo map" in {
      assert(TC.getEdgeTo.toString() == "Map(C -> DirectedEdge(A,C,70.0), D -> DirectedEdge(A,D,120.0), B -> DirectedEdge(C,B,160.0), E -> DirectedEdge(C,E,240.0))")
  }

  it should "throw error if the path is not exist in the required direction" in {
    assertThrows[NoRouteException] {
      TC.shortPath(Vertex("A"), Vertex("X"))
    }
  }

  it should "get the right nearby stations within the time limit" in {

    assert(TC.nearBy(Vertex("A"), 200).toString() == "C: 70, D: 120")
  }

  it should "throw error if no stations available within the time limit needed" in {
    assertThrows[NoNearbyStationsException] {
      TC.nearBy(Vertex("A"), 20)
    }
  }
}