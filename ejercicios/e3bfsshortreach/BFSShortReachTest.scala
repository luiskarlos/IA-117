package e3bfsshortreach

import org.scalatest.{FlatSpec, Matchers}


/**
  * Created by lhernandez on 3/9/17.
  */
class BFSShortReachTest extends FlatSpec with Matchers {

  val BaseCase = """|2
                   |4 2
                   |1 2
                   |1 3
                   |1
                   |3 1
                   |2 3
                   |2
                   |""".stripMargin.split("\n")


  "Search" should "should return nodes" in {
    val bfs = new BFSShortReach()

    val (problems, solutions) = bfs.resolve(BaseCase.iterator)

    problems(0).n should be (4)
    problems(0).m should be (2)


    solutions(0) should be ("6 6 -1")
    solutions(1) should be ("-1 6")
  }

}
