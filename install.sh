#!/bin/sh
sbt assembly
chmod +x $PWD/target/scala-2.13/sgit
export PATH=$PWD/target/scala-2.13:$PATH
