package util.io

/**
  * This class will handle the input data payload
  */
case class InputDataModel(
  connectionsNumber: Int,
  connections: List[(String, String, Double)],
  route: (String, String),
  nearby: (String, Double)
)