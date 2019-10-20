package Commands

import better.files.File

import Utils.Commit._

object Log {

  def log(sgit : File) : Unit = {
    getCurrentCommit(sgit) match {
      case Some(commit) => displayCommitAndParents(commit, sgit)
      case None => println("No commit found on this branch")
    }
  }

}
