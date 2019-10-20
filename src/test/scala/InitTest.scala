import Commands.Init._
import Utils.Utils._
import better.files._
import org.scalatest.{BeforeAndAfter, FunSpec, Matchers}

class InitTest extends FunSpec with Matchers with BeforeAndAfter{

  println("IN INIT TEST")
  val currentDir : File = getShellCurrentDir


  before{
    println("BEFOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOORE")
    if ((currentDir / ".sgit").exists()) {
      println("DELETED .SGIT WITH BEFORE")
      (currentDir / ".sgit").listRecursively.foreach(f => f.delete())
    }
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
      if ((currentDir/".sgit").exists()) {
        println("SGIT DIR EXISTS : " + (currentDir/".sgit").exists())
        println("DELETED .SGIT WITH FIXTURE")
        (currentDir/".sgit").delete()
      }
    }
  }





  describe("Initialisation of a .sgit directory, and it's subdirectories"){

    it("should create a '.sgit' directory in the shell current directory"){
      (currentDir/".sgit").exists shouldBe false
      init()
      (currentDir/".sgit").exists shouldBe true
      (currentDir/".sgit").isDirectory shouldBe true

    }

    it("should create inside: a 'index' file, a 'HEAD' file, a 'refs/heads/master' file and a 'objects' directory"){
      (currentDir/".sgit"/"HEAD").exists shouldBe false
      (currentDir/".sgit"/"index").exists shouldBe false
      (currentDir/".sgit"/"refs"/"heads").exists shouldBe false
      (currentDir/".sgit"/"objects").exists shouldBe false
      (currentDir/".sgit").exists shouldBe false
      init()
      (currentDir/".sgit").exists shouldBe true
      (currentDir/".sgit").isDirectory shouldBe true
      (currentDir/".sgit"/"HEAD").exists shouldBe true
      (currentDir/".sgit"/"HEAD").isRegularFile shouldBe true
      (currentDir/".sgit"/"index").exists shouldBe true
      (currentDir/".sgit"/"index").isRegularFile shouldBe true
      (currentDir/".sgit"/"refs"/"heads").exists shouldBe true
      (currentDir/".sgit"/"refs"/"heads").isDirectory shouldBe true
      (currentDir/".sgit"/"refs"/"tags").exists shouldBe true
      (currentDir/".sgit"/"refs"/"tags").isDirectory shouldBe true
      (currentDir/".sgit"/"objects").exists shouldBe true
      (currentDir/".sgit"/"objects").isDirectory shouldBe true
    }

  }

}
