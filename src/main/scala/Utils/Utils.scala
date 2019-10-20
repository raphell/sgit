package Utils

import better.files._
import scala.annotation.tailrec

object Utils {


  def getShellCurrentDir : File = {
    (System.getProperty("user.dir")/"root")
  }


  @tailrec
  def getSgitDirectory(pathToCheck : File = getShellCurrentDir) : Option[File] = {
    val directoryToCheck = pathToCheck / ".sgit"
    if( directoryToCheck.isDirectory ){
      Some(directoryToCheck)
    }
    else{
      if(pathToCheck.parent != null){
        getSgitDirectory(pathToCheck.parent)
      }
      else{
        None
      }
    }
  }

}
