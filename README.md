## Public Transport Routing

The main purpose behind this application is to get the shortest path is to find the quickest route between 2 stations 
considering the direction and time.
The transport network is modelled as a directed graph with edges that represent a connection between
the two stations and are labelled with the travel time in seconds. If a transport line can be used in two
directions it is modelled as two distinct, opposing edges. The stations are identified with alphanumeric strings without
 special characters or whitespace, such as “A” or “B” or “BRANDENBURGERTOR”.

## How to use

`sbt run` the enter the the stations, required path and neatby station in the same format as in test1.txt
Or
`sbt run < test1.txt` for easier data entering

## Tests

To the run the spec tests
 `sbt test`

## Libs used

 - `sbt` as a build tool
 - `scalatest` as a spect test tool

## Algorithm

This app uses [dijkstra's algorithm](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm) with priority queue to get the shortest path. 
Time complexity: `O(E V log V)` Where `V` is the number of vertices and `E` is the number of edges
