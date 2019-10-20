import _root_.CommandLineInterpreter.CommandParser._
import _root_.Utils.Utils._


object SgitApp extends App{

  println("ROOT  : " + getShellCurrentDir )

  val marksArray = Array(56,79,60,99,71)
  val newArray = marksArray.slice(0,2)

  println(marksArray.mkString(" "))
  println(newArray)

  var test = Array("init")
  print("ARGS : ")
  test.foreach(print)
  dispatch(test)
  println(" ")
  println(" ")

  test = Array("status")
  print("ARGS : ")
  test.foreach(print)
  dispatch(test)
  println(" ")
  println(" ")


  println("///////////////////////////////////////////////")
  test = Array("add", "data/lettre.txt")
  print("ARGS : ")
  test.foreach(print)
  dispatch(test)
  println(" ")
  println(" ")

  test = Array("status")
  print("ARGS : ")
  test.foreach(print)
  dispatch(test)
  println(" ")
  println(" ")

  println("///////////////////////////////////////////////")
  test = Array("commit", "-m", "mon message de premier commit")
  print("ARGS : ")
  test.foreach(print)
  dispatch(test)
  println(" ")
  println(" ")

  test = Array("status")
  print("ARGS : ")
  test.foreach(print)
  dispatch(test)
  println(" ")
  println(" ")


  println("///////////////////////////////////////////////")
  test = Array("add", "data/doot")
  print("ARGS : ")
  test.foreach(print)
  dispatch(test)
  println(" ")
  println(" ")

  println("///////////////////////////////////////////////")
  test = Array("commit", "-m", "mon message de deuxieme commit")
  print("ARGS : ")
  test.foreach(print)
  dispatch(test)
  println(" ")
  println(" ")


  println("///////////////////////////////////////////////")
  test = Array("branch", "branch2")
  print("ARGS : ")
  test.foreach(print)
  dispatch(test)
  println(" ")
  println(" ")


  println("///////////////////////////////////////////////")
  test = Array("tag", "v1.0")
  print("ARGS : ")
  test.foreach(print)
  dispatch(test)
  println(" ")
  println(" ")


  println("///////////////////////////////////////////////")
  test = Array("branch", "-av")
  print("ARGS : ")
  test.foreach(print)
  dispatch(test)
  println(" ")
  println(" ")

  println("///////////////////////////////////////////////")
  test = Array("add", "data/test.txt")
  print("ARGS : ")
  test.foreach(print)
  dispatch(test)
  println(" ")
  println(" ")




  println("///////////////////////////////////////////////")
  test = Array("commit", "-m", "mon message de troisieme commit")
  print("ARGS : ")
  test.foreach(print)
  dispatch(test)
  println(" ")
  println(" ")

  println("///////////////////////////////////////////////")
  test = Array("log")
  print("ARGS : ")
  test.foreach(print)
  dispatch(test)
  println(" ")
  println(" ")

  println("///////////////////////////////////////////////")
  test = Array("checkout", "branch2")
  print("ARGS : ")
  test.foreach(print)
  dispatch(test)
  println(" ")
  println(" ")

  println("///////////////////////////////////////////////")
  test = Array("log")
  print("ARGS : ")
  test.foreach(print)
  dispatch(test)
  println(" ")
  println(" ")

  println("///////////////////////////////////////////////")
  test = Array("checkout", "master")
  print("ARGS : ")
  test.foreach(print)
  dispatch(test)
  println(" ")
  println(" ")

}


