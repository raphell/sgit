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
    println("commit: "+ commit.parent.name + commit.name)
    println(commit.contentAsString.linesIterator.toArray.filter(line => line.contains("Author"))(0))
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
    println("IN GET PARENTS")
    val parents = commit.lineIterator.toArray
      .filter(line => line.contains("Parent"))
      .filter(line => {
        val parentHash = line.split(" ")(1)
        println("PARENT HASH BEFORE" + parentHash)
        println((sgit/"object"/parentHash.take(2)/parentHash.substring(2)))
        println((sgit/"object"/parentHash.take(2)/parentHash.substring(2)).exists)
        (sgit/"objects"/parentHash.take(2)/parentHash.substring(2)).isRegularFile
      })
      .map(line => {
        val parentHash = line.split(" ")(1)
        println("PARENT HASSHHHHH : :" + parentHash)
        (sgit/"objects"/parentHash.take(2)/parentHash.substring(2))
      })

    if (parents.size!=0) Some(parents)
    else None
  }



}
