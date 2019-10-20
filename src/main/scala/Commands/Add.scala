package Commands

import better.files.File

import Utils.Utils._
import Utils.Blob._
import Utils.Index._



object Add {


  def add(sgitDir : File, paths : Array[String] ) : Unit = {
    println("IN ADD")
    paths.foreach(path => println("PATHS : "+path))

    val files = getFilesToAdd(sgitDir, paths)

    files.foreach(file => {
      println("FILE TO ADD : " + file)
      addBlobToObjects(sgitDir, file)
      addBlobToIndex(sgitDir, file)
    })
  }



  def getFilesToAdd(sgitDir : File, paths : Array[String] ) : Array[File] = {
    println("IN GET ALL FILES")

    val filesAndDir : Array[File] = paths.map(path => getShellCurrentDir/path )

    val directories : Array[File] = filesAndDir.filter(x => x.isDirectory)
    val files : Array[File] = filesAndDir.filter(x => x.isRegularFile)

    val filesInDirs : Array[File] = directories
                                      .map(dir => dir.listRecursively)
                                      .flatten
                                      .filter(f => f.isRegularFile)
    files ++ filesInDirs
  }

}
