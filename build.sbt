import sbtassembly.AssemblyPlugin.defaultUniversalScript



name := "sgit"

version := "0.1"

libraryDependencies += "com.github.scopt" %% "scopt" % "4.0.0-RC2"

libraryDependencies += "com.github.pathikrit" %% "better-files" % "3.8.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % "test"

scalaVersion := "2.13.1"

parallelExecution in Test := false

assemblyOption in assembly := (assemblyOption in assembly).value
  .copy(prependShellScript = Some(defaultUniversalScript(shebang = false)))

assemblyJarName in assembly := s"${name.value}"


