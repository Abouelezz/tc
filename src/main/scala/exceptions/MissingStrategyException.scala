package exceptions

import util.strategies.ShortestPathStrategiesEnum

/**
  * Missing StrategyException
  *
  * @param strategyName the missing Strategy name
  */
class MissingStrategyException(strategyName: String) extends IllegalArgumentException {

  override def toString: String = {

    s"Strategy $strategyName is not available. Only ${ShortestPathStrategiesEnum.values.mkString(", ")} is available"
  }
}