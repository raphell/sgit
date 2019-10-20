package Utils

import better.files.File

object Index {


  def addBlobToIndex(sgit : File, fileToAdd : File): Unit = {
    val projectRoot = sgit.parent
    val relativePathToObject = projectRoot.relativize(fileToAdd)

    val indexLineToADD = relativePathToObject + " " + fileToAdd.sha1.toUpperCase
    val indexLines = (sgit/"index").lineIterator

    (sgit/"indexTemp").createFile()
    (sgit/"indexTemp").appendLine(indexLineToADD)

    indexLines.foreach(line => {
      if (!line.contains(relativePathToObject.toString)){
        (sgit/"indexTemp").appendLine(line)
      }
    })
    (sgit/"index").delete()
    (sgit/"indexTemp").renameTo("index")
  }


  def getPathsFromIndex(sgit : File): Array[String] ={
    val indexFile : File = sgit / "index"
    val indexLines = indexFile.lineIterator.toArray

    indexLines.map(line => line.split(" ")(0))
  }

  def isInIndex(fileToTest: File, sgit : File): Boolean = {
    val indexFile : File = sgit / "index"
    val rootFile : File = sgit.parent

    val relativePath : String = {
      if (rootFile.isParentOf(fileToTest)){
        fileToTest.name
      }
      else{
        sgit.parent.relativize(fileToTest).toString
      }
    }

    val indexLines = indexFile.lineIterator
    indexLines.foreach(line => {
      if (line contains relativePath.toString){
        return true
      }
    })
    false
  }


}
