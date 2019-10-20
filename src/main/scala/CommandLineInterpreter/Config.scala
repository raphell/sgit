package CommandLineInterpreter

case class Config(
                 mode : String = "",
                 file : String = "",
                 paths : Array[String] =  Array() ,   //to select various files
                 commitMessage : String = "",
                 branchNames : Array[String] = Array(),
                 checkoutBranch : String = "",
                 displayBranchAndTag : Boolean = false,
                 verboseDisplay : Boolean = false,
                 tagName: String = "",
                 )
