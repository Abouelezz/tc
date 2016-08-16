package util.io

import org.scalatest._

import scala.collection.mutable

class InputReaderSpec extends FlatSpec {

  "Input Reader" should "Gather the data from an input stream and turn it into a valid inputDataModel" in {

    val inputStack = mutable.Stack(
        "8",
        "A -> B: 240",
        "A -> C: 70",
        "A -> D: 120",
        "C -> B: 60",
        "D -> E: 480",
        "C -> E: 240",
        "B -> E: 210",
        "E -> A: 300",
        "route A -> B",
        "nearby A, 130"
      )

    def readLine(): String = inputStack.pop

    def readInt(): Int = inputStack.pop.toInt

    val inputData = inputReader.gather(readLine, readInt)

    assert(inputData.connectionsNumber == 8)
    assert(inputData.connections.length == 8)
    assert(inputData.connections(1)._1 == "A")
    assert(inputData.connections(1)._2 == "C")
    assert(inputData.connections(1)._3 == 70.0)
    assert(inputData.connections(7)._1 == "E")
    assert(inputData.connections(7)._2 == "A")
    assert(inputData.connections(7)._3 == 300.0)
    assert(inputData.route._1 == "A")
    assert(inputData.route._2 == "B")
    assert(inputData.nearby._1 == "A")
    assert(inputData.nearby._2 == 130)
  }
}