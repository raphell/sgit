#!/bin/sh
sbt assembly
chmod +x $PWD/target/scala-2.13/sgit
ln -s $PWD/target/scala-2.13/sgit sgit
export PATH=$PWD/target/scala-2.13:$PATH
