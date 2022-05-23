#!/bin/bash
export PATH_TO_FX=lib/ # set classpath
csv_file="$1"
start_index="$2"
end_index="$3"
d="$4"

# compile and run program
javac --module-path $PATH_TO_FX --add-modules javafx.controls *.java && java --module-path $PATH_TO_FX --add-modules javafx.controls Stars "$csv_file" "$start_index" "$end_index" "$d"