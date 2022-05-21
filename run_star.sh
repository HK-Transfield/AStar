#!/bin/bash
export PATH_TO_FX=lib/
csv_file="$1"
javac --module-path $PATH_TO_FX --add-modules javafx.controls PlotNodes.java && java --module-path $PATH_TO_FX --add-modules javafx.controls PlotNodes "$csv_file"