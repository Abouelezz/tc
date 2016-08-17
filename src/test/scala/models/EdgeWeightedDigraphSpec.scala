package test.models

import models.{DirectedEdge, EdgeWeightedDigraph, Vertex}
import org.scalatest._

class EdgeWeightedDigraphSpec extends FlatSpec {

  val edgeWeightedDigraph = new EdgeWeightedDigraph

  "An EdgeWeightedDigraph" should "have the directedEdge" in {
    val directedEdge = DirectedEdge(Vertex("A"), Vertex("B"), 100)
    edgeWeightedDigraph.addEdge(directedEdge)

    assert(edgeWeightedDigraph.edgesCount == 1)
    assert(edgeWeightedDigraph.getEdge(Vertex("A")).isDefined)
  }

  it should "not provide non-exist edges" in {

    assert(edgeWeightedDigraph.getEdge(Vertex("B")).isEmpty)

  }
}