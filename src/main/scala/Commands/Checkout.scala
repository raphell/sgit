package Commands

import better.files.File

import Utils.Branch._
import Utils.Tree._



object Checkout {


  def checkout(sgit : File, branch : String): Unit ={
    val conflictFiles = getNotSavedFile(sgit, branch)
    if (conflictFiles.size==0){
      val treeToForm = {

        val commitHash = {
          if( (sgit/"objects"/branch.take(2)/branch.substring(2)).isRegularFile) branch
          else (sgit/"refs"/"heads"/branch).contentAsString
        }
        val rootTreeArray = ((sgit/"objects"/commitHash.take(2)/commitHash.substring(2)).lineIterator.toArray)
        val rootTreeHash = rootTreeArray(0).split(" ")(1)
        (sgit/"objects"/rootTreeHash.take(2)/rootTreeHash.substring(2))
      }

      sgit.parent.children.toArray.filter(file => (file.name != ".sgit")).foreach(file => deleteFilesOfCurrentBranch(sgit/"index", sgit.parent))
      (sgit/"index").overwrite("")
      createDirsFromTree(sgit.parent, treeToForm, "rootTree", "", sgit)
      (sgit/"HEAD").overwrite({
        if( (sgit/"objects"/branch.take(2)/branch.substring(2)).isRegularFile) branch
        else "ref: refs/heads/" + branch
      })
    }
    else{
      println("Can't do that, following files must be committed to avoid loss of data")
      conflictFiles.foreach(file => println("       - " + file))
    }
  }




}
