package Utils

import better.files.File

import Tree._
import Index._


object Branch {


  def getCurrentBranchName(sgit : File): String ={
    val branchPath = (sgit/"HEAD").contentAsString.split(" ")(1).split("/")
    branchPath(branchPath.size-1)
  }


  def deleteFilesOfCurrentBranch(index: File, root : File): Unit = {
    index.lineIterator.toArray.foreach(line => println("Line IN INDEX : " + line))
    val listOfFile = index.lineIterator.toArray.map( line => (root/line.split(" ")(0)) )
    listOfFile.foreach(file => {
      file.delete()
    })
  }

  def putCurrentBranchOnCommit(sgit : File, commitHash: String): Unit = {
    val currentBranch = (sgit/"HEAD").contentAsString.split(" ")
    (sgit/currentBranch(1)).createFileIfNotExists(createParents = true).overwrite(commitHash)
  }

  def getCurrentRootTree(sgit : File): Option[File] = {

    val currentBranch = (sgit / "HEAD").contentAsString

    if (currentBranch != "") {
      val commitFile = (sgit / currentBranch.split(" ")(1))
      if (commitFile.exists()) {
        val commitHash = commitFile.contentAsString
        val rootTreeHash: String = (sgit / "objects" / commitHash.take(2) / commitHash.substring(2)).lineIterator.toArray.filter(line => line contains "tree")(0).split(" ")(1)
        Some(sgit / "objects" / rootTreeHash.take(2) / rootTreeHash.substring(2))
      }
      else {
        None
      }
    }
    else {
      None
    }
  }



  def getNotSavedFile(sgit : File, branchName : String): Array[String] = {
    val filesToCheck : Array[String] = getPathsFromIndex(sgit)
    filesToCheck.foreach(file => println(" FILES TO CHECK : "+ file))

    val currentRootTree = {
      val currentBranch = (sgit/"HEAD").contentAsString.split(" ")
      val commitHash = (sgit/currentBranch(1)).contentAsString
      val rootTreeHash : String = (sgit/"objects"/commitHash.take(2)/commitHash.substring(2)).lineIterator.toArray.filter(line => line contains "tree")(0).split(" ")(1)
      (sgit/"objects"/rootTreeHash.take(2)/rootTreeHash.substring(2))
    }

    val nextRootTree = {
      val commitHash = (sgit/"refs"/"heads"/branchName).contentAsString
      val rootTreeHash = (sgit/"objects"/commitHash.take(2)/commitHash.substring(2)).contentAsString
      (sgit/"objects"/rootTreeHash.take(2)/rootTreeHash.substring(2))
    }

    val fileNotSaved : Array[String] = filesToCheck.filterNot(file => {
      isFileInTree(currentRootTree, file, sgit) || isFileInTree(nextRootTree, file, sgit)
    })
    fileNotSaved
  }


}
