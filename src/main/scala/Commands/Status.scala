package Commands

import Utils.Branch._
import Utils.Index._
import Utils.Tree._

import better.files.File



object Status {

  def status(sgit : File): Unit ={
    val untrackedFiles : Array[File] = getUntrackedFiles(sgit.parent, sgit)
    val modifiedFiles : Array[String] = getModifiedFiles(sgit.parent, sgit)
    val branchName : String = getCurrentBranchName(sgit)

    println("On the branch " + branchName)
    println("Modifications which will be validated")
    println(" ")
    modifiedFiles.foreach(file => println("       " + file))
    println(" ")
    println("Not followed files")
    println("  Use git add <file>... > to include in the project")
    untrackedFiles.foreach(file => println("       " + sgit.parent.relativize(file).toString))
  }




  def getUntrackedFiles(file: File, sgit : File): Array[File] = {
    val children = file.children.toArray.filterNot(child => child.name==".sgit")
    val untracked = children.filterNot(child => isInIndex(child, sgit))
    val maybeTracked = children.filter(child => child.isDirectory).filter(child => isInIndex(child, sgit))
    untracked ++ maybeTracked.map( f => getUntrackedFiles(f, sgit) ).flatten
  }


  def getModifiedFiles(file : File, sgit: File): Array[String] ={
    val filesToCheck = getPathsFromIndex(sgit)

    getCurrentRootTree(sgit) match {
      case Some(rootTree) => filesToCheck.filter(file => isFileInTree(rootTree, file, sgit))
      case None => filesToCheck
    }
  }



}


