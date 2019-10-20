package Commands

import better.files.File

object Tag {

  def tag(sgit : File, tagName : String): Unit ={
    val refFile : File = sgit/"refs"/"tags"
    val sameName = refFile.collectChildren(f => f.name== tagName)
    sameName.length match {
      case 0 => {
        val newBranch = (refFile/tagName).createFile()
        val currentBranch = (sgit/"HEAD").contentAsString.split(" ")
        newBranch.overwrite((sgit/currentBranch(1)).contentAsString)
      }
      case _ => println("A tag with this name already exists")
    }
  }

}
