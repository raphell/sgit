package CommandLineInterpreter

import Commands.Add._
import Commands.Init._
import Commands.Commit._
import Commands.Branch._
import Commands.Checkout._
import Commands.Tag._
import Commands.Status._
import Commands.Log._

import Utils.Utils._
import scopt.{OParser, OParserBuilder}


object CommandParser {

  def dispatch(args : Array[String] ) : Unit = {
    getSgitDirectory() match {

        //commands when there is no .sgit directory
      case None => parse(args) match {
        case Some(config) => config.mode match{
          case "init" => init()
          case "" => println("You need to write a command after 'sgit'")
          case _ => println("ERROR, you are not in a sgit repository")
        }
      }

        //commands when there is already a .sgit directory
      case Some(sgitDir) => parse(args) match {
        case Some(configuration) => configuration.mode match {
          case "add" => add(sgitDir, configuration.paths)
          case "init" => println("You are already in a sgit repository")
          case "commit" => commit(sgitDir, configuration.commitMessage)
          case "branch" => {
            if(configuration.branchNames.nonEmpty) createBranch(sgitDir, configuration.branchNames)
            else if (configuration.displayBranchAndTag) displayBranchAndTag(sgitDir, configuration.verboseDisplay)
          }
          case "tag" => tag(sgitDir, configuration.tagName)
          case "checkout" => checkout(sgitDir, configuration.checkoutBranch)
          case "status" => status(sgitDir)
          case "log" => log(sgitDir)
          case _ => println("Function not implemented yet")
        }
        case None => println("Config not found")
      }
    }
  }



  val builder: OParserBuilder[Config] = OParser.builder[Config]

  val parser: OParser[Unit, Config] = {
    import builder._
    OParser.sequence(
      programName("sgit"),
      head("scopt", "4.x"),

      cmd(name = "init")
        .action((_, c) => c.copy(mode = "init"))
        .text("Create a Sgit repository in the current directory."),

      cmd(name = "status")
        .action((_, c) => c.copy(mode = "status"))
        .text("Display the status of the files in the project."),

      cmd(name = "add")
      .action((_, c) => c.copy(mode = "add"))
      .text("Add file contents to the index.")
      .children(
        arg[String](name = "<file>...")
          .unbounded()
          .action((x, c) => c.copy(paths = c.paths :+ x))
          .text("list of files to add")
      ),

      cmd(name = "commit")
      .action((_, c) => c.copy(mode = "commit"))
      .text("Create a commit")
      .children(
        opt[String]('m', "message")
          .required()
          .maxOccurs(1)
          .action((x, c) => c.copy(commitMessage = x))
          .text("The message for the commit")
      ),

      cmd(name = "log")
        .action((_, c) => c.copy(mode = "log"))
        .text("Display all the commit that where made on the current branch."),

      cmd(name = "branch")
      .action((_, c) => c.copy(mode = "branch"))
      .text("Add a new branch to the git repository")
      .children(
        arg[String](name = "<branch name>...")
          .optional()
          .unbounded()
          .action((x, c) => c.copy(branchNames = c.branchNames :+ x))
          .text("list of branches to add"),
        opt[Unit]('a', name = "all")
          .action((_, c) => c.copy(displayBranchAndTag = true))
          .text("List all branches and tags"),
        opt[Unit]('v', name = "verbose")
          .action((_, c) => c.copy(verboseDisplay = true))
          .text("Give branch's commit details")
      ),

      cmd(name = "tag")
        .action((_, c) => c.copy(mode = "tag"))
        .text("Add a new tag pointing to the last commit")
        .children(
          arg[String](name = "<tag name>...")
            .required()
            .unbounded()
            .action((x, c) => c.copy(tagName =  x))
            .text("Name of the tag")
        ),

      cmd(name = "checkout")
        .action((_, c) => c.copy(mode = "checkout"))
        .text(" switch to headless state or to a branch")
        .children(
          arg[String](name = "<branch name>...")
            .unbounded()
            .required()
            .maxOccurs(1)
            .action((x, c) => c.copy(checkoutBranch =  x))
            .text("The branch to switch to"),
        )
    )
  }

  def parse(args: Array[String]): Option[Config] = {
    OParser.parse(parser, args, Config())
  }

}