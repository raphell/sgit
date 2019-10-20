import Commands.Add.add
import Utils.Utils._
import better.files._
import org.scalatest.{BeforeAndAfter, FunSpec, Matchers}
import Commands.Init._
import Commands.Branch._
import Commands.Commit._



class BranchTest extends FunSpec with Matchers with BeforeAndAfter {
  println("IN BRANCH TEST")
  val currentDir: File = getShellCurrentDir


  before{
    init()
    val file = (currentDir/"data.txt").createFileIfNotExists().overwrite("Random contents")
    add((currentDir/".sgit"), Array("data.txt"))
    commit((currentDir/".sgit"), "commit 1")
    commit((currentDir/".sgit"), "commit 2")
  }
  after {
    if ((currentDir / ".sgit").exists()) {
      println("DELETED .SGIT WITH AFTER")
      (currentDir / ".sgit").delete()
    }
  }

  override def withFixture(test: NoArgTest) = {
    try test()
    finally {
      if ((currentDir / ".sgit").exists()) {
        println("DELETED .SGIT WITH FIXTURE")
        (currentDir / ".sgit").delete()
      }
    }
  }


  describe("Display the commits that where made"){
    it("should create a file in '.sgit/refs/heads' with the name given in parameter"){

      createBranch((currentDir/".sgit"), Array("newBranch"))
      val hashOfCurrentCommit = (currentDir/".sgit"/(currentDir/".sgit"/"HEAD").contentAsString.split(" ")(1)).contentAsString

      (currentDir/".sgit"/"refs"/"heads"/"newBranch").exists shouldBe true
      (currentDir/".sgit"/"refs"/"heads"/"newBranch").isRegularFile shouldBe true
      ((currentDir/".sgit"/"refs"/"heads"/"newBranch").contentAsString == hashOfCurrentCommit) shouldBe true

    }

  }



}