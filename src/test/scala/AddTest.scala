import Utils.Utils._
import better.files._
import org.scalatest.{BeforeAndAfter, FunSpec, Matchers}

import Commands.Add._
import Commands.Init._


class AddTest extends FunSpec with Matchers with BeforeAndAfter {
  
  val currentDir: File = getShellCurrentDir

  after {
    if ((currentDir / ".sgit").exists()) {
      (currentDir / ".sgit").delete()
    }
  }

  override def withFixture(test: NoArgTest) = {
    try test()
    finally {
      if ((currentDir / ".sgit").exists()) {
        (currentDir / ".sgit").delete()
      }
    }
  }



  describe("Adding of a new file(s) "){

    it("should add the files in the 'objects' directory of .sgit"){
      init()

      val file = (currentDir/"data.txt").createFileIfNotExists().overwrite("Random contents")
      add((currentDir/".sgit"), Array("data.txt"))
      val hash = file.sha1
      (currentDir/".sgit"/"objects"/hash.take(2)/hash.substring(2)).exists shouldBe true
    }


    it("should add the files in the 'index' file of .sgit"){
      init()
      val file = (currentDir/"data.txt").createFileIfNotExists().overwrite("Random contents")
      add((currentDir/".sgit"), Array("data.txt"))

      val hash = file.sha1

      ((currentDir/".sgit"/"index").contentAsString contains ("data.txt "+hash)) shouldBe true
    }
  }

  describe("Adding of a file(s) already tracked"){

    it("should add the files in the 'objects' directory of .sgit"){
      init()
      val file = (currentDir/"data.txt").createFileIfNotExists().overwrite("Random contents")
      add((currentDir/".sgit"), Array("data.txt"))
      add((currentDir/".sgit"), Array("data.txt"))
      val hash = file.sha1

      (currentDir/".sgit"/"objects"/hash.take(2)/hash.substring(2)).exists shouldBe true
    }


    it("should replace the hash of the file in the 'index' file of .sgit"){
      init()
      val file = (currentDir/"data.txt").createFileIfNotExists().overwrite("Random contents")
      add((currentDir/".sgit"), Array("data.txt"))
      add((currentDir/".sgit"), Array("data.txt"))

      val hash = file.sha1

      ((currentDir/".sgit"/"index").contentAsString contains ("data.txt "+hash)) shouldBe true
      (((currentDir/".sgit"/"index").lineIterator.toArray.filter(line => line contains "data.txt")).size) shouldBe 1
    }

  }

}
