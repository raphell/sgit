package Commands

import better.files._
import Utils.Utils._

object Init{
  def init(path:File = getShellCurrentDir): Unit ={
    (path/".sgit").createDirectory()
    (path/".sgit"/"objects").createDirectory()
    (path/".sgit"/"refs"/"heads").createDirectories()
    (path/".sgit"/"refs"/"tags").createDirectories()
    (path/".sgit"/"index").createFile()
    (path/".sgit"/"HEAD").createFile().overwrite("ref: refs/heads/master")
  }
}