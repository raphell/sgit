package Utils

import better.files.File

object Commit {

  def getCurrentCommit(sgit : File): Option[File] ={

    val currentBranch = (sgit/"HEAD").contentAsString
    if(currentBranch!=""){
      if( (sgit/"objects"/currentBranch.take(2)/currentBranch.substring(2)).isRegularFile) {
        Some((sgit/"objects"/currentBranch.take(2)/currentBranch.substring(2)))
      }
      else{
        val commitFile = (sgit/currentBranch.split(" ")(1))
        if (commitFile.exists()){
          val commitHash = commitFile.contentAsString
          Some(sgit/"objects"/commitHash.take(2)/commitHash.substring(2))
        }
        else{
          None
        }
      }
    }
    else {
      None
    }
  }


  def getCommitFromStr(sgit : File, commit : String): Option[File] ={
    if( (sgit/"objects"/commit.take(2)/commit.substring(2)).isRegularFile) {
      Some((sgit/"objects"/commit.take(2)/commit.substring(2)))
    }
    else None
  }


  def displayCommitAndParents(commit: File, sgit: File): Unit ={

    println(commit.contentAsString.linesIterator.toArray.filter(line => line.contains("Date"))(0))
    println(" ")
    println({
      val lines = commit.contentAsString.linesIterator.toArray
      lines(lines.size-1)
    })
    println(" ")

    getParentCommit(sgit, commit) match {
      case Some(parents) => parents.foreach(f = parent => displayCommitAndParents(parent, sgit))
      case None =>
    }

  }

  def getParentCommit(sgit : File, commit: File): Option[Array[File]] ={
    val parents = commit.lineIterator.toArray
      .filter(line => line.contains("Parent"))
      .filter(line => {
        val parentHash = line.split(" ")(1)
        File(parentHash).isRegularFile
      })
      .map(line => {
        val parentHash = line.split(" ")(1)
        File(parentHash)
      })

    if (parents.size!=0) Some(parents)
    else None
  }



}
