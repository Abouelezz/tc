package test.util.io

import org.scalatest._
import util.io.inputParser

class InputParserSpec extends FlatSpec {


  "Input Parser" should "parse and get the right arguments out of the connection line string" in {

    val connectionString = inputParser.parseConnectionLine("FOO -> bar: 240")

    assert(connectionString._1 == "FOO")
    assert(connectionString._2 == "bar")
    assert(connectionString._3 == 240)

    val connectionString2 =
      inputParser.parseConnectionLine("FOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO -> BAR: 240")

    assert(connectionString2._1 == "FOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO")
    assert(connectionString2._2 == "BAR")
    assert(connectionString2._3 == 240)

    val connectionString3 =
      inputParser.parseConnectionLine("BAR -> FOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO: 240")

    assert(connectionString3._1 == "BAR")
    assert(connectionString3._2 == "FOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO")
    assert(connectionString3._3 == 240)

    val connectionString4 = inputParser.parseConnectionLine("foo -> BAR: 000024000")

    assert(connectionString4._1 == "foo")
    assert(connectionString4._2 == "BAR")
    assert(connectionString4._3 == 24000)
  }

  it should "fail if connection line is not valid" in {

    assertThrows[MatchError](
      inputParser.parseConnectionLine("A1BC3 -> x&y*z: 1234")
    )

    assertThrows[MatchError](
      inputParser.parseConnectionLine("FOO -> bar: invalid")
    )

    assertThrows[MatchError](
      inputParser.parseConnectionLine("foo -> bar")
    )

    assertThrows[MatchError](
      inputParser.parseConnectionLine("foo: 123")
    )
  }

  it should "parse and get the right arguments out of the route line" in {

    val routeString = inputParser.parseRouteLine("route FOO -> bar")

    assert(routeString._1 == "FOO")
    assert(routeString._2 == "bar")
  }

  it should "fail if route line is not valid" in {


    assertThrows[MatchError](
      inputParser.parseRouteLine("foo: 123")
    )
  }

  it should "parse and get the right arguments out of the nearby line" in {

    val nearbyString = inputParser.parseNearbyLine("nearby ABC, 123")

    assert(nearbyString._1 == "ABC")
    assert(nearbyString._2 == 123.0)
  }

  it should "fail if nearby line is not valid" in {

    assertThrows[MatchError](
      inputParser.parseNearbyLine("foo: 123")
    )
  }

}