package Utils

import better.files._
import scala.annotation.tailrec

object Utils {


  def getShellCurrentDir : File = {
    File(System.getProperty("user.dir"))
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
