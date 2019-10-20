package Utils

import better.files.File

import Index._
import Blob._

object Tree {

  def createTree(sgit : File, dir : File): String = {
    println("IN CREATE TREE FOR : " + dir.path.toString)

    val newTree = (sgit / dir.name).createFileIfNotExists()
    val children : Array[File] = dir.children.toArray

    val fileChildren : Array[File] = children.filterNot(f => {f.isDirectory && f.exists}).filter(f => isInIndex(f, sgit))
    val dirChildren : Array[File] = children.filter(dir => dir.isDirectory).filter(dir => isInIndex(dir, sgit))

    println("There is " + fileChildren.size + " files and " + dirChildren.size + " directories")

    dirChildren.foreach(dir => writeTreeInTree(createTree(sgit, dir), dir.name, newTree))
    fileChildren.foreach(f => {println("WRITE BLOB IN TREE : " + f.path.toString)
      writeBlobInTree(f, newTree)
    })

    val newTreeHash = newTree.sha1
    val newTreeDir = newTreeHash.take(2)
    val newTreeName = newTreeHash.substring(2)

    if (( sgit/"objects"/newTreeDir/newTreeName).isRegularFile){
      newTree.delete()
      println("tree already exists")
    }
    else{
      val treeFile : File = ( sgit/"objects"/newTreeDir/newTreeName ).createFileIfNotExists(createParents = true)
      treeFile.overwrite(newTree.contentAsString)
      newTree.delete()
    }
    newTreeHash
  }



  def writeTreeInTree(hash: String, name : String, newTree: File): Unit = {
    val line : String = "040000 tree " + hash + " " + name
    newTree.appendLine(line)
  }



  def isFileInTree(currentTree: File, file: String, sgit : File) : Boolean = {
    println("IN IS FILE IN TREE")
    val relativePath : Array[String] = file.split("/")

    val lines = currentTree.lineIterator.toArray
    val line : Array[String] = lines.filter( line => line.contains(relativePath(0)) )

    if ( line.size > 0 ){
      if (relativePath.size > 1){
        val newFileHash = line(0).split(" ")(2)
        println("NEW HASH : " + newFileHash)
        isFileInTree(sgit/"objects"/newFileHash.take(2)/newFileHash.substring(2), relativePath.slice(1,relativePath.size).mkString("/"), sgit)
      }
      else true
    }
    else false
  }



  def createDirsFromTree(parentFile : File, treeToForm: File, typeFile : String, name :String, sgit : File): Unit = {
    println("IN CREATE DIR FROM TREE")

    typeFile match {
      case "tree" => (parentFile/name).createDirectoryIfNotExists()
      case "blob" => {
        (parentFile/name).createFileIfNotExists().overwrite(treeToForm.contentAsString)
        val lineToWriteInIndex = sgit.parent.relativize(parentFile/name).toString + " " + treeToForm.parent.name + treeToForm.name
        (sgit/"index").appendLine(lineToWriteInIndex)
      }
      case _ =>
    }

    val parent = {
      if (typeFile=="rootTree") parentFile
      else (parentFile/name)
    }

    println("TREE TO FORM : " + treeToForm.path.toString + " : " + treeToForm.exists)
    if (typeFile!="blob"){
      val lines = treeToForm.lineIterator
      lines.foreach( line => {
        println("LINE TO CREATE : " + line)
        val typeF = line.split(" ")(1)
        val dirF = line.split(" ")(2).take(2)
        val fileF = line.split(" ")(2).substring(2)
        val nameF = line.split(" ")(3)
        createDirsFromTree(parent, sgit/"objects"/dirF/fileF, typeF, nameF, sgit)
      })
    }
  }
}
