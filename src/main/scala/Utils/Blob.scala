package Utils

import better.files.File

object Blob {


  def addBlobToObjects(sgit : File, file : File): Unit ={
    val data = file.contentAsString
    val dataHash = file.sha1.toUpperCase
    val objectDir = dataHash.take(2)
    val objectFileName = dataHash.substring(2)
    (sgit/"objects"/objectDir/objectFileName).createFileIfNotExists(createParents = true).overwrite(data)
  }

  def writeBlobInTree(f: File, newTree: File): Unit = {
    val line : String = "100664 blob " + f.sha1 + " " + f.name
    newTree.appendLine(line)
  }


}
