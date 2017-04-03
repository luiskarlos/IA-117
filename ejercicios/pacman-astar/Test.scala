
import org.scalatest.{Matchers, WordSpec}

/**
  * Created by lhernandez on 3/15/17.
  */
class TestPacmanAStar extends WordSpec with Matchers {

  val BaseCase = scala.io.Source.fromInputStream(getClass.getResourceAsStream("basecase.txt")).getLines().toSeq

  "Search" when {
    "empty" should {
      "read problem" in {
        val pacMan = new NPuzzle()
        val result = pacMan.read(BaseCase.iterator)

        result.pacMan.pos.row should be (3)
        result.pacMan.pos.column should be (9)
        result.maze.grid.length should be (7)
        result.maze.grid(0).length should be (20)

        result.maze.isFood(NPuzzle.Pos(5,1)) should be (true)
        result.maze.isWall(NPuzzle.Pos(0,0)) should be (true)
        result.maze.isFree(NPuzzle.Pos(1,1)) should be (true)

        result.maze.isFood(NPuzzle.Pos(1,1)) should be (false)
        result.maze.isWall(NPuzzle.Pos(1,1)) should be (false)
        result.maze.isFree(NPuzzle.Pos(0,0)) should be (false)
      }

      "resolve BaseCase problem" in {
        val pacMan = new NPuzzle()
        val problem = pacMan.read(BaseCase.iterator)
        val result = pacMan.aStar(problem)

        val lines = scala.io.Source.fromInputStream(getClass.getResourceAsStream("basecase-result.txt")).getLines()

        pacMan.print((line:String) => {
          println(line)
          val expected = lines.next()
          line should be(expected)
        }, result)
      }
    }
  }

}
