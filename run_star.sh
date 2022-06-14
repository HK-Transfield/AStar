#!/bin/bash
export PATH_TO_FX=lib/ # set classpath


# compile and run program
javac Search.java
javac Node.java
javac Path.java
javac --module-path $PATH_TO_FX --add-modules javafx.controls Stars.java
java --module-path $PATH_TO_FX --add-modules javafx.controls Stars small_v2.csv 39 13 20
# java --module-path $PATH_TO_FX --add-modules javafx.controls Stars small_v2.csv 22 20 17
# java --module-path $PATH_TO_FX --add-modules javafx.controls Stars clusters_v2.csv 57 82 17
# java --module-path $PATH_TO_FX --add-modules javafx.controls Stars clusters_v2.csv 57 82 10
# java --module-path $PATH_TO_FX --add-modules javafx.controls Stars spiral_v2.csv 75 60 9
# java --module-path $PATH_TO_FX --add-modules javafx.controls Stars spiral_v2.csv 118 94 40
# java --module-path $PATH_TO_FX --add-modules javafx.controls Stars trap_v2.csv 170 230 20
# java --module-path $PATH_TO_FX --add-modules javafx.controls Stars trap_v2.csv 136 208 15
# java --module-path $PATH_TO_FX --add-modules javafx.controls Stars spiral_v2.csv 75 83 7
# java --module-path $PATH_TO_FX --add-modules javafx.controls Stars small_v2.csv 32 2 10
