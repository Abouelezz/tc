package models

/**
  * This will handle the single Vertex
  *
  * @todo spec test
  * @param name vertex name
  */
case class Vertex(name: String) {
  override def toString = name
}
