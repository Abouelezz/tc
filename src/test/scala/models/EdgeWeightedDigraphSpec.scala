package test.models

import models.{DirectedEdge, EdgeWeightedDigraph}
import org.scalatest._

class EdgeWeightedDigraphSpec extends FlatSpec {

  val edgeWeightedDigraph = new EdgeWeightedDigraph

  "An EdgeWeightedDigraph" should "have the directedEdge" in {
    val directedEdge = DirectedEdge("A", "B", 100)
    edgeWeightedDigraph.addEdge(directedEdge)

    assert(edgeWeightedDigraph.edgesCount == 1)
    assert(edgeWeightedDigraph.getEdge("A").isDefined)
  }

  it should "not provide non-exist edges" in {

    assert(edgeWeightedDigraph.getEdge("B").isEmpty)

  }
}