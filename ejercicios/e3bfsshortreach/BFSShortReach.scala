package e3bfsshortreach

import scala.collection.mutable
import scala.io.Source

/**
  *
  */
class BFSShortReach {

  val Cost = 6

  case class Node(state:Int, cost:Int)

  /**
    * Definicion del problema
    *
    * @param n nodos
    * @param m profundidad
    * @param edges vertices en ambas direcciones
    * @param start nodo de inicio
    * @param goals todos los nodos menos el origen. Se puede eliminar en favor de solamente no visitar el start.
    */
  case class Problem(n:Int, m:Int, edges:Map[Int, mutable.Set[Int]], start:Int, goals:Seq[Int]) {

    def initialState = start

    /**
      * La meta es llegar a todos los estados en la meta.
      */
    def goalTest(node:Node) = goals.contains(node.state)

    /**
      * Todo vertices (Acciones) que salen de este Node
      */
    def actions(node:Node) : Seq[Int] = edges.getOrElse(node.state, Seq.empty).toSeq

    /**
      * En este caso la accion es igual al estado deseado
      */
    def result(parent:Node, action:Int): Int = action

  }

  /**
    * @return new Node
    */
  def childNode(problem:Problem, parent:Node, action:Int) : Node =
     Node(problem.result(parent, action), parent.cost + Cost)

  /**
    * BFS modificado para almacenar multiples costos
    */
  def breadthFirstSearch(problem:Problem): Seq[Int] = {
    val root      = Node(problem.initialState, 0)
    var costs     = new mutable.HashMap[Int, Int]
    var explored  = new mutable.HashSet[Int]
    val frontier  = new mutable.Queue[Node]
    frontier += root

    while(frontier.nonEmpty) {
      val node = frontier.dequeue()
      explored += node.state
      val actions = problem.actions(node)
      actions.foreach( action => {
        val child = childNode(problem, node, action)
        val notChecked = !explored.contains(child.state) && frontier.find(_.state == child.state).isEmpty
        if (notChecked) {
          if (problem.goalTest(child)) {
            if (costs.getOrElse(child.state, Int.MaxValue) > child.cost)
              costs += (child.state -> child.cost)
          }
          frontier += child
        }
      })
    }

    //Maps goals to cost, -1 if it is not in the cost map
    problem.goals.map( goal => costs.getOrElse(goal, -1) )
  }

  object ProblemConstruction {

    private def tuple(stringTuple: String): (Int, Int) = {
      val values = stringTuple.split("\\s+").toSeq
      (values.head.toInt, values.tail.head.toInt)
    }

    private def readProblem(input: Iterator[String]): Problem = {
      val (nodesCount, edgesCount) = tuple(input.next().trim())
      val edgesMap = new mutable.HashMap[Int, mutable.Set[Int]] with mutable.MultiMap[Int, Int]
      for (_ <- 1 to edgesCount) {
        val edge = tuple(input.next())
        val reverseEdge = (edge._2, edge._1)

        edgesMap.addBinding(edge._1, edge._2)
        edgesMap.addBinding(reverseEdge._1, reverseEdge._2)
      }

      val start = input.next().trim().toInt
      Problem(nodesCount, edgesCount, edgesMap.toMap, start, (1 to nodesCount).filter(_ != start))
    }

    def readProblems(input: Iterator[String]): Seq[Problem] = {
      val q = input.next().trim().toInt
      var problems = Seq.empty[Problem]
      for (a <- 1 to q) {
        problems = problems :+ readProblem(input)
      }
      problems
    }
  }

  def resolve(lines:Iterator[String]) : (Seq[Problem], Seq[String]) = {
    val problems = ProblemConstruction.readProblems(lines)
    var solutions = Seq.empty[String]
    problems.foreach{ problem =>
      solutions = solutions :+ breadthFirstSearch(problem).mkString(" ")
    }
    (problems, solutions)
  }

}

object Solution {
  def main(args: Array[String]): Unit = {
    val bfs = new BFSShortReach()
    val input = Source.fromInputStream(System.in)
    val (_, solutions) = bfs.resolve(input.getLines())
    solutions.foreach(println)
  }
}