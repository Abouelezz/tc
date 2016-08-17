package test.models

import models.{DirectedEdge, Vertex}
import org.scalatest._

class DirectedEdgeSpec extends FlatSpec {

  "A Directed Edge" should "contain from, to and weight" in {
    val directedEdge = DirectedEdge(Vertex("A"), Vertex("B"), 100)
    assert(directedEdge.from == Vertex("A"))
    assert(directedEdge.to == Vertex("B"))
    assert(directedEdge.weight == 100)
  }

  it should "not have a negative weight" in {
    assertThrows[IllegalArgumentException] {
      DirectedEdge(Vertex("A"), Vertex("B"), -100)
    }
  }
}