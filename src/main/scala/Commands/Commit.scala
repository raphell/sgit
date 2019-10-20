package Commands

import better.files.File

import Utils.Tree._
import Utils.Commit._
import Utils.Branch._

object Commit {

  def commit(sgit : File, message : String): Unit ={
    println("IN COMMIT")

    val newCommit = (sgit/"commitTemp").createFile()
    val rootTreeHash = createTree(sgit, sgit.parent)

    newCommit.appendLine("tree " + rootTreeHash)

    getCurrentCommit(sgit) match{
      case Some(parent) => newCommit.appendLine("Parent " + parent)
      case None => println("hello")
    }

    newCommit.appendLine("Author ")
    newCommit.appendLine("Date :  ")
    newCommit.appendLine(message)

    val commitHash = newCommit.sha1
    val commitFileName = commitHash.take(2)
    val commitName = commitHash.substring(2)

    val commitFile : File = (sgit/"objects"/commitFileName/commitName).createFileIfNotExists(createParents = true)
    commitFile.overwrite(newCommit.contentAsString)
    newCommit.delete()

    putCurrentBranchOnCommit(sgit, commitHash)
  }





















}
