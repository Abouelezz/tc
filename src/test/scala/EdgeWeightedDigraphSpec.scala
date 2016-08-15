package org.tc.test

import models.{DirectedEdge, EdgeWeightedDigraph}
import org.scalatest._
import org.tc.EdgeWeightedDigraph

class EdgeWeightedDigraphSpec extends FlatSpec {

  val edgeWeightedDigraph = new EdgeWeightedDigraph

  "An EdgeWeightedDigraph" should "have the directedEdge" in {
    val directedEdge = DirectedEdge("A", "B", 100)
    edgeWeightedDigraph.addEdge(directedEdge)

    assert(edgeWeightedDigraph.edgesCount == 1)
    assert(edgeWeightedDigraph.getEdge("A").isDefined)
  }

  it should "not provide non-exist edges" in {
    assertThrows[IllegalArgumentException] {
      edgeWeightedDigraph.getEdge("B")
    }
  }
}