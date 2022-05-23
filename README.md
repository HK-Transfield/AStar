<h1>A* Search Algorithm</h1>

The program uses the library JavaFX 

```
chmod +x run_star.sh
./run_star.sh [galaxy_csv_filename] [start_index] [end_index] [D]
```

Alternatively, to run this JavaFX application, you must enter in the commands:
```
export PATH_TO_FX=lib/
javac --module-path $PATH_TO_FX --add-modules javafx.controls *.java 
java --module-path $PATH_TO_FX --add-modules javafx.controls Stars [galaxy_csv_filename] [start_index] [end_index] [D]
```