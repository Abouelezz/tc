## Public Transport Routing

The main purpose behind this application is to get the shortest path between 2 stations considering the direction and time.
The transport network is modelled as a directed graph with edges that represent a connection between
the two stations and are labelled with the travel time in seconds. If a transport line can be used in two
directions it is modelled as two distinct, opposing edges. The stations are identified with alphanumeric strings without
 special characters or whitespace, such as “A” or “B” or “BRANDENBURGERTOR”.

## How to use

### From CLI

`sbt run`
then enter the the stations, required path and nearby station in the same format like:
```
8
A -> B: 240
A -> C: 70
A -> D: 120
C -> B: 60
D -> E: 480
C -> E: 240
B -> E: 210
E -> A: 300
route A -> B
nearby A, 130
```

Or

`sbt run < simpleExample.txt` for easier data entering

### As a library

```scala
val edgeWeightedDigraph = new EdgeWeightedDigraph

edgeWeightedDigraph.addEdge(DirectedEdge("A", "B", 240))
edgeWeightedDigraph.addEdge(DirectedEdge("A", "D", 120))
edgeWeightedDigraph.addEdge(DirectedEdge("C", "B", 160))
edgeWeightedDigraph.addEdge(DirectedEdge("D", "E", 480))
edgeWeightedDigraph.addEdge(DirectedEdge("C", "E", 240))
edgeWeightedDigraph.addEdge(DirectedEdge("B", "E", 210))
edgeWeightedDigraph.addEdge(DirectedEdge("E", "A", 300))
edgeWeightedDigraph.addEdge(DirectedEdge("X", "A", 300))

val TC =  new TransportationCalculator(edgeWeightedDigraph)

val shortPath = TC.shortPath("A", "B") 
val nearby = TC.nearBy("A", 130) 
```

## Tests

To the run the spec tests
 `sbt test`

## Libs used

 - `sbt` as a build tool
 - `scalatest` as a spectest tool

## The Algorithm

This app uses [dijkstra's algorithm](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm) with priority queue to get the shortest path. 
Time complexity: `O(E V log V)` Where `V` is the number of vertices and `E` is the number of edges
