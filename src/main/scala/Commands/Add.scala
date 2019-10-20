package Commands

import better.files.File

import Utils.Utils._
import Utils.Blob._
import Utils.Index._



object Add {


  def add(sgitDir : File, paths : Array[String] ) : Unit = {
    paths.foreach(path => println("PATHS : "+path))

    val files = getFilesToAdd(sgitDir, paths)

    files.foreach(file => {
      addBlobToObjects(sgitDir, file)
      addBlobToIndex(sgitDir, file)
    })
  }



  def getFilesToAdd(sgitDir : File, paths : Array[String] ) : Array[File] = {
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
