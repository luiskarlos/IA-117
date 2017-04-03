import java.io.{File, PrintWriter}
import java.util.Date

import play.api.libs.json.Json

import scala.io.Source

/**
  * Created by lhernandez on 4/2/17.
  */
object StudentModel {
  private  def listFiles(root:File, maxLevel:Int = Int.MaxValue) : Seq[File] =
    if (maxLevel > 0 && root.isDirectory)
      root.listFiles().flatMap(listFiles(_, maxLevel - 1))
    else
      Seq(root)

  case class Checked(file:File) {
    val lastChecked = GitSupport.lastModification(file)
  }

  case class Info(main: String, url: String, languaje: String, onTime: String, score: BigDecimal, honorCode: String, grupo: Seq[String]) {
    val name = url.substring(url.lastIndexOf("/") + 1)
  }
  implicit val readInfoStudent = Json.reads[Info]
  implicit val writeInfoStudent = Json.writes[Info]

  case class Grade(source:String, work:String, nota:String, points:String, onTime:String, lastModified:Option[String], error:Option[String]) {

    def columns = Seq("Fuente", "Trabajo", "Nota", "Puntos", "aTiempo", "Estado")

    def toRow = Seq(
      source,
      work,
      nota,
      points.toString,
      onTime,
      error.getOrElse("")
    )
  }
  implicit val readGradeStudent = Json.reads[Grade]
  implicit val writeGradeStudent = Json.writes[Grade]

  case class Work(student:Student, file:Checked, originalInfo:String, info:Option[Info], error:Option[Throwable]) {
    val relativePath = file.file.getAbsolutePath.replace(student.root.getAbsolutePath, "")
    val mainFile = info.map(i => new File(file.file.getParent, i.main))
    val lastModification: Option[Date] = mainFile.filter(_.exists()).map(GitSupport.lastModification)

    val name = info.map(_.name).getOrElse("missing")

    def calcNote(workGrades:BaseGrade.WorkGrade) = {
      val note = workGrades.maxPoints.map(max => (BigDecimal(info.map(_.score.toString).getOrElse("0")) / max) * BigDecimal(100)).getOrElse(BigDecimal(100))
      if (note > 100.0 || note < 0.0) {
        BigDecimal(0)
      } else {
        note
      }
    }

    def calcPoints(workGrades:BaseGrade.WorkGrade) = {
      if (error.isDefined) {
        s"0/${workGrades.maxPoints}"
      } else {
        workGrades.maxPoints.map(max => BigDecimal(info.map(_.score.toString).getOrElse("0")) + "/" + max).getOrElse("--/"+workGrades.maxPoints.getOrElse("--"))
      }
    }

    def toGrade(workGrades:BaseGrade.WorkGrade) : Grade =
      Grade(relativePath,
        name,
        calcNote(workGrades).toString(),
        calcPoints(workGrades),
        info.map(_.onTime).getOrElse("No"),
        lastModification.map(BaseGrade.dateParser.format(_)),
        error.map(_.getMessage))

  }

  case class Id(nombre: String, nick: Option[String], cedula: String, tecId: String, email: String, git: String, gitSite: String, hakerrank: String) {
    def getNick:Option[String] = if (nick.isDefined && nick.get.trim.isEmpty) None else if (nick.isDefined) nick else None
  }
  implicit val ReadStudent = Json.reads[Id]

  case class GradeResult(work:Work, workGrade:Option[BaseGrade.WorkGrade]) {

    def toGrade =
      work.toGrade(workGrade.get)

  }

  case class GradeResults(graded:Seq[GradeResult], missing:Seq[GradeResult])

  case class Student(root:File, content:Option[String], id:Option[Id], error:Option[Exception]) {

    def name = id.map(_.getNick.getOrElse(id.map(_.tecId).getOrElse(root.getName))).getOrElse(root.getName)

    private def readWork(f:File) : Work = {
      val checked = Checked(f)
      val content = Source.fromFile(f)
        .mkString
        .replace("honor-code", "honorCode")
        .replace("si", "yes")
        .replace(".\\","")
      try {
        Work(this, checked, content, Some(Json.parse(content).as[Info]), None)
      } catch {
        case e:Throwable => Work(this, checked, content, None, Some(e))
      }
    }

    val works: Seq[Work] = (() => {
      listFiles(root)
        .filter(_.getName.equals("info.json"))
        .map(f => readWork(f))
    })()

    def grade(workGrades:Seq[BaseGrade.WorkGrade]) = {
      val grades = workGrades.map(wg => works.find(_.name == wg.name).map(work => GradeResult(work, Some(wg)))).flatten
      val missing = works.filter(w => grades.find(_.work == w).isEmpty).map(w => GradeResult(w, None))
      GradeResults(grades, missing)
    }
  }


  private def readStudentID(id:File) = {
    if (id.exists()) {
      val content = Source.fromFile(id)(io.Codec.ISO8859).mkString.replace("tec-id", "tecId").replace("git-site","gitSite")
      try {
        val idInfo = Json.parse(content).as[Id]
        Student(id.getParentFile, Some(content), Some(idInfo), None)
      } catch {
        case e:Throwable => Student(id.getParentFile, Some(content), None, Some(new Exception(s"Error parsing: $id\n$content")))
      }
    } else
      Student(id.getParentFile, None, None, Some(new Exception(s"id.json not found")))
  }

  def readStudent(root:File) =
    readStudentID(root)

  def loadStudents(root:File) =
    listFiles(root, 2)
      .filter(_.getName.equals("id.json"))
      .map(id => readStudent(id))


}
