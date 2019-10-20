package Commands

import better.files.File


object Branch {

  def createBranch(sgit : File, branches : Array[String]) :Unit = {

    branches.foreach(b => {
      val refFile : File = sgit/"refs"/"heads"
      val sameName = refFile.collectChildren(f => f.name==b)
      sameName.length match {
        case 0 => {
          val newBranch = (refFile/b).createFile()
          val currentBranch = (sgit/"HEAD").contentAsString.split(" ")
          newBranch.overwrite((sgit/currentBranch(1)).contentAsString)
        }
        case _ => println("A branch with this name already exists")
      }
    })
  }



  def displayBranchAndTag(sgit : File, verbose : Boolean): Unit = {
    val files = (sgit/"refs"/"heads").children ++ (sgit/"refs"/"tags").children

    files.foreach(f => {
      val commitHash = f.contentAsString
      val commitContent : Array[String] = (sgit/"objects"/commitHash.take(2)/commitHash.substring(2)).lineIterator.toArray
      val commitMessage = commitContent(commitContent.size-1)

      if (sgit.relativize(f).toString == (sgit/"HEAD").contentAsString.split(" ")(1)) print("* ")
      else print("  ")
      verbose match  {
        case true => println( f.name + " " + commitHash.take(7) + " " + commitMessage )
        case false => println( f.name + " " + commitHash.take(7) + " " + commitMessage )
      }
    })
  }



}
