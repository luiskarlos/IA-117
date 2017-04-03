import scala.collection.mutable
import scala.io.Source

object PacManAStar {

  case class Node(parent:Option[Node], pos:Pos, var cost: Int = 0)

  case class Pos(row:Int, column:Int) {
    def +(that: Pos) =
      Pos(row + that.row, column + that.column)

    override def toString = s"$row $column"
  }

  case class Move(pos:Pos)

  val UP    = Move(Pos(-1, 0))
  val LEFT  = Move(Pos( 0,-1))`
  val RIGHT = Move(Pos( 0, 1))
  val DOWN  = Move(Pos( 1, 0))

  val moves = Seq(UP, LEFT, RIGHT, DOWN)

  case class PacMan(pos:Pos)
  case class Food(pos:Pos)

  case class Maze(grid:Seq[String]) {
    def isWall(pos:Pos) = grid(pos.row).charAt(pos.column) == '%'
    def isFood(pos:Pos) = grid(pos.row).charAt(pos.column) == '.'
    def isFree(pos:Pos) = !isWall(pos)
  }

  case class Problem(pacMan:PacMan, food:Food, maze:Maze) {

    def goalTest(node:Node) = maze.isFood(node.pos)

    def actions(node:Node) = moves.filter( move => maze.isFree(move.pos + node.pos) )

    def result(node:Node, move:Move) = node.pos + move.pos
  }

}

class PacManAStar {
  import NPuzzle._

  def childNode(problem:Problem, parent:Node, action:Move) : Node = {
    val pos = problem.result(parent, action)
    Node(Some(parent), pos, if (problem.food.pos == pos) 0 else parent.cost + 1)
  }

  /**
    *
    */
  def aStar(problem:Problem): Option[Node] = {
    val heuristic = (node:NPuzzle.Node) => {
      math.abs(node.pos.row - problem.food.pos.row) +
        math.abs(node.pos.column - problem.food.pos.column)
    }

    val cost = (node:Node) => {
      node.cost + heuristic(node)
    }

    aStar(problem, cost)
  }

  /**
    *
    */
  def aStar(problem:Problem, cost: (Node) => Int): Option[Node] = {
    val root     = Node(None, problem.pacMan.pos)
    var explored = new mutable.HashSet[Pos]
    val frontier = new mutable.PriorityQueue[Node]()(Ordering.by(cost))
    frontier.enqueue(root)
    explored += root.pos

    var found:Option[Node] = None

    while(frontier.nonEmpty && found.isEmpty) {
      val node = frontier.dequeue()
      if (problem.goalTest(node)) {
        found = Some(node)
      } else {
        explored += node.pos
        val actions = problem.actions(node)
        actions.foreach(action => {
          val child = childNode(problem, node, action)
          val notChecked = !explored.contains(child.pos) && frontier.find(_.pos == child.pos).isEmpty
          if (notChecked) {
            frontier.enqueue(child)
          }
        })
      }
    }

    found
  }

  def print(printer: (String) => Unit, result: Option[Node]): Unit = {
    result.map { food =>
      var path = Seq.empty[Pos]
      var node:Option[Node] = Some(food)
      while (node.isDefined) {
        path = node.get.pos +: path
        node = node.get.parent
      }
      printer((path.length - 1).toString)
      path.foreach(r => printer(r.toString))
    }
  }

  def read(input:Iterator[String]) : Problem = {
    def readPos(input:String) : Pos =
      input.split("\\s+").toList match {
        case r :: l :: _ => Pos(r.toInt, l.toInt)
      }

    val pacMan = PacMan(readPos(input.next))
    val food = Food(readPos(input.next))

    val mazeSize = readPos(input.next)
    val grid = for (_ <- 1 to mazeSize.row)
      yield input.next

    val maze = Puzzle(grid)

    Problem(pacMan, food, maze)
  }

}
/**
  */
object Solution {

  def main(args: Array[String]): Unit = {
    val pacMan = new NPuzzle()
    val problem = pacMan.read(Source.fromInputStream(System.in).getLines())
    val result = pacMan.aStar(problem, (node:NPuzzle.Node) => {
      math.abs(node.pos.row - problem.food.pos.row) +
      math.abs(node.pos.column - problem.food.pos.column)
    })

    pacMan.print(println, result)
  }
}