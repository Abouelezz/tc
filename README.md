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

`sbt run < resources/examples/simpleExample.txt` for easier data entering

### As a library

```scala
val edgeWeightedDigraph = new EdgeWeightedDigraph

edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("A"), Vertex("B"), 240))
edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("A"), Vertex("D"), 120))
edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("C"), Vertex("B"), 160))
edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("D"), Vertex("E"), 480))
edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("C"), Vertex("E"), 240))
edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("B"), Vertex("E"), 210))
edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("E"), Vertex("A"), 300))
edgeWeightedDigraph.addEdge(DirectedEdge(Vertex("X"), Vertex("A"), 300))

val TC =  new PublicTransportPlanner(edgeWeightedDigraph)

val shortPath = TC.shortPath(Vertex("A"), Vertex("B")) 
val nearby = TC.nearBy(Vertex("A"), 130) 
```

## Tests

To the run the spec tests
 `sbt test`

## Libs used

 - `sbt` as a build tool
 - `scalatest` as a spectest tool