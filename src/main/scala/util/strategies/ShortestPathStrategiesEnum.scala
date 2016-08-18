package util.strategies

/**
  * This is the Enums that represents the ShortestPathStrategies aviliable in the application
  */
object ShortestPathStrategiesEnum extends Enumeration {
  val Dijkstra, Warshalls = Value
  val default = Dijkstra
}