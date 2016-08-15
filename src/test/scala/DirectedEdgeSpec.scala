package org.tc.test

import models.DirectedEdge
import org.scalatest._

class DirectedEdgeSpec extends FlatSpec {

  "A Directed Edge" should "contain from, to and weight" in {
    val directedEdge = DirectedEdge("A", "B", 100)
    assert(directedEdge.from == "A")
    assert(directedEdge.to == "B")
    assert(directedEdge.weight == 100)
  }

  it should "not have a negative weight" in {
    assertThrows[IllegalArgumentException] {
      DirectedEdge("A", "B", -100)
    }
  }
}