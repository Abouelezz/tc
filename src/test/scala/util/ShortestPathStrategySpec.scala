package util

import models.{DirectedEdge, EdgeWeightedDigraph, Vertex}
import org.scalatest._
import _root_.exceptions.{NoRouteException, NoNearbyStationsException}
import util.strategies.ShortestPathStrategiesEnum

class ShortestPathStrategySpec extends FlatSpec {

  var edgeWeightedDigraph = new EdgeWeightedDigraph

  edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("A"), Vertex("B"), 240))
  edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("A"), Vertex("D"), 120))
  edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("C"), Vertex("B"), 160))
  edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("D"), Vertex("E"), 480))
  edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("C"), Vertex("E"), 240))
  edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("B"), Vertex("E"), 210))
  edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("E"), Vertex("A"), 300))
  edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("X"), Vertex("A"), 300))
  edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("A"), Vertex("C"), 70))
  edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("E"), Vertex("O"), 70))

  /**
    * This will loop over all ShortestPathStrategy enum an test it one by one
    */
  ShortestPathStrategiesEnum.values.foreach(sp => {
    testShortestPathStrategy(new PublicTransportPlanner(edgeWeightedDigraph, sp.toString), sp.toString)
  })

  def testShortestPathStrategy(sp: PublicTransportPlanner, strategyName: String) {
    strategyName should "get the right path with direct connection" in {

      assert(sp.getShortPath(Vertex("A"), Vertex("D")).toString() == "A -> D: 120")
    }

    it should "get the right path with indirect connection" in {

      assert(sp.getShortPath(Vertex("A"), Vertex("B")).toString() == "A -> C -> B: 230")
    }

    it should "get the right path with indirect connection with open end" in {

      assert(sp.getShortPath(Vertex("A"), Vertex("O")).toString() == "A -> C -> E -> O: 380")
    }

    it should "throw error if the path is not exist in the required direction" in {
      assertThrows[NoRouteException] {
        sp.getShortPath(Vertex("A"), Vertex("X"))
      }
    }

    it should "get the right nearby stations within the time limit" in {

      assert(sp.getNearBy(Vertex("A"), 200).toString() == "C: 70, D: 120")
    }

    it should "throw error if no stations available within the time limit needed" in {
      assertThrows[NoNearbyStationsException] {
        sp.getNearBy(Vertex("A"), 20)
      }
    }
  }
}