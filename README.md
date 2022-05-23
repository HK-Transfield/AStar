<h1>A* Search Algorithm</h1>

The program uses the ```JavaFX``` library to display a plotted graph. In order to run the JavaFX SDK, please ensure that the ```lib/``` directory is in the same folder as ```Stars.java``` and enter the follow commands:
<br> 
1. Add an environmental variable point to ```lib``` directory
```
export PATH_TO_FX=lib/
```
2. Compile the application using JavaFX runtime
```
javac --module-path $PATH_TO_FX --add-modules javafx.controls *.java
```

3. Run the application using
```
java --module-path $PATH_TO_FX --add-modules javafx.controls Stars [galaxy_csv_filename] [start_index] [end_index] [D]
```
---
Alternatively, to run the JavaFX application I have also included a simple shell script:
```
chmod +x run_star.sh
./run_star.sh [galaxy_csv_filename] [start_index] [end_index] [D]
```