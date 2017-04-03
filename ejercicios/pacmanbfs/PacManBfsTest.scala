
import org.scalatest.{FlatSpec, Matchers, WordSpec}

/**
  * Created by lhernandez on 3/15/17.
  */
class PacManBfsTest extends WordSpec with Matchers {

  import PacManDSF._

  val BaseCase = """3 9
                   |5 1
                   |7 20
                   |%%%%%%%%%%%%%%%%%%%%
                   |%--------------%---%
                   |%-%%-%%-%%-%%-%%-%-%
                   |%--------P-------%-%
                   |%%%%%%%%%%%%%%%%%%-%
                   |%.-----------------%
                   |%%%%%%%%%%%%%%%%%%%%""".stripMargin.split("\n")


  "Search" when {
    "empty" should {
      "read problem" in {
        val pacMan = new PacManDSF()
        val result = pacMan.read(BaseCase.iterator)

        result.pacMan.pos.row should be (3)
        result.pacMan.pos.column should be (9)
        result.maze.grid.length should be (7)
        result.maze.grid(0).length should be (20)

        result.maze.isFood(PacManDSF.Pos(5,1)) should be (true)
        result.maze.isWall(PacManDSF.Pos(0,0)) should be (true)
        result.maze.isFree(PacManDSF.Pos(1,1)) should be (true)

        result.maze.isFood(PacManDSF.Pos(1,1)) should be (false)
        result.maze.isWall(PacManDSF.Pos(1,1)) should be (false)
        result.maze.isFree(PacManDSF.Pos(0,0)) should be (false)
      }

      "resolve BaseCase problem" in {
        val pacMan = new PacManDSF()
        val problem = pacMan.read(BaseCase.iterator)
        val result = pacMan.depthFirstSearch(problem)

        val lines = scala.io.Source.fromInputStream(getClass.getResourceAsStream("pacman.output.txt")).getLines()

        pacMan.print((line:String) => {
          val expected = lines.next()
          line should be(expected)
        }, result)
      }

      "resolve problem 2 food (27,28)" in {
        val data = """25 13
                     |3 1
                     |27 28
                     |%%%%%%%%%%%%%%%%%%%%%%%%%%%%
                     |%------------%%------------%
                     |%-%%%%-%%%%%-%%-%%%%%-%%%%-%
                     |%.%%%%-%%%%%-%%-%%%%%-%%%%-%
                     |%-%%%%-%%%%%-%%-%%%%%-%%%%-%
                     |%--------------------------%
                     |%-%%%%-%%-%%%%%%%%-%%-%%%%-%
                     |%-%%%%-%%-%%%%%%%%-%%-%%%%-%
                     |%------%%----%%----%%------%
                     |%%%%%%-%%%%%-%%-%%%%%-%%%%%%
                     |%%%%%%-%%%%%-%%-%%%%%-%%%%%%
                     |%%%%%%-%------------%-%%%%%%
                     |%%%%%%-%-%%%%--%%%%-%-%%%%%%
                     |%--------%--------%--------%
                     |%%%%%%-%-%%%%%%%%%%-%-%%%%%%
                     |%%%%%%-%------------%-%%%%%%
                     |%%%%%%-%-%%%%%%%%%%-%-%%%%%%
                     |%------------%%------------%
                     |%-%%%%-%%%%%-%%-%%%%%-%%%%-%
                     |%-%%%%-%%%%%-%%-%%%%%-%%%%-%
                     |%---%%----------------%%---%
                     |%%%-%%-%%-%%%%%%%%-%%-%%-%%%
                     |%%%-%%-%%-%%%%%%%%-%%-%%-%%%
                     |%------%%----%%----%%------%
                     |%-%%%%%%%%%%-%%-%%%%%%%%%%-%
                     |%------------P-------------%
                     |%%%%%%%%%%%%%%%%%%%%%%%%%%%%""".stripMargin.split("\n")

        val pacMan = new PacManDSF()
        val problem = pacMan.read(data.iterator)
        val result = pacMan.depthFirstSearch(problem)

        val lines = scala.io.Source.fromInputStream(getClass.getResourceAsStream("pacman.test2.txt")).getLines()

        pacMan.print((line:String) => {
          val expected = lines.next()
          //println(line)
          line should be(expected)
        }, result)
      }

      "resolve problem 3 food (27,28)" in {
        var data = """11 9
                     |2 15
                     |13 20
                     |%%%%%%%%%%%%%%%%%%%%
                     |%----%--------%----%
                     |%-%%-%-%%--%%-%.%%-%
                     |%-%-----%--%-----%-%
                     |%-%-%%-%%--%%-%%-%-%
                     |%-----------%-%----%
                     |%-%----%%%%%%-%--%-%
                     |%-%----%----%-%--%-%
                     |%-%----%-%%%%-%--%-%
                     |%-%-----------%--%-%
                     |%-%%-%-%%%%%%-%-%%-%
                     |%----%---P----%----%
                     |%%%%%%%%%%%%%%%%%%%%""".stripMargin.split("\n")

        val pacMan = new PacManDSF()
        val problem = pacMan.read(data.iterator)
        val result = pacMan.depthFirstSearch(problem)

        val lines = scala.io.Source.fromInputStream(getClass.getResourceAsStream("pacman.test3.txt")).getLines()

        val mark = (('1' to '9') ++ ('a' to 'z') ++ ('1' to '9') ++ ('1' to '9') ++ ('a' to 'z') ++ ('1' to '9') ++ ('a' to 'z')
          ++ ('1' to '9') ++ ('a' to 'z')++ ('1' to '9') ++ ('a' to 'z')++ ('1' to '9') ++ ('a' to 'z')++ ('1' to '9') ++ ('a' to 'z')).iterator

        data = data.map(l => l.replace('-', ' '))
        pacMan.print((line:String) => {
          println(line)
          //val expected = lines.next()
          /*if (line.split("\\s+").length >= 2)
            line.split("\\s+").toList match {
              case r :: l :: _ =>  data(r.toInt+3) = data(r.toInt+3).updated(l.toInt, mark.next())
              case _ =>  Unit
            }*/
          //line should be(expected)
        }, result)

        print(data.mkString("\n"))
      }

      "resolve problem 4 food (27,28)" in {
        val data = """5 5
                     |1 5
                     |7 7
                     |%%%%%%%
                     |%-%--.%
                     |%-%-%%%
                     |%-%---%
                     |%-%-%-%
                     |%----P%
                     |%%%%%%%""".stripMargin.split("\n")

        val pacMan = new PacManDSF()
        val problem = pacMan.read(data.iterator)
        val result = pacMan.depthFirstSearch(problem)

        pacMan.print((line:String) => {
          println(line)
          /*if (line.contains(' ')) {
            line.split("\\s+") .toList match {
              case r :: l :: _ =>
                data(r.toInt+3) = data(r.toInt+3).updated(l.toInt, 'P')
            }
            println(data.mkString("\n"))
          }*/

        }, result)
      }

    }
  }

}
