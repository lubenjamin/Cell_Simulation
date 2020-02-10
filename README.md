Simulation
====

This project implements a cellular automata simulator.

Names: Connor Hazen, Abhijay Suhag, Benjamin Lu

### Timeline

Start Date: Jan 26th

Finish Date: Feb 9th

Hours Spent: 15

### Primary Roles

Simulation Logic and controllers - Connor Hazen
UI and View = Abhijay Suhag
FileReader and Model - Benjamin Lu


### Resources Used
default.css

English.properties

### Running the Program

View.Simulator class: View.Simulator

Data files needed: fire.xml, gameoflife.xml, percolation.xml, segregation.xml, predatorprey.xml
rockpaperscissors.xml

Features implemented: 5 Simulations with adjustable speed and step functionality,
custom initial state inputs for each simulation, ability to run multiple
simulations at once, error/exception checking and handling, error 
pop-ups triggered on faulty file data.


### Notes/Assumptions

Assumptions or Simplifications: Simulation files must have the correct 
parameters in order to actually show the simulation. No other assumptions 
needs to be made in order to operate the simulation. 

Interesting data files: fire_missing_type.xml and gameoflife_missing_initial_lives.xml 
both demonstrate error and exception handling. 

Known Bugs: If the simulation file, regardless if the correct parameters
are in place, does not have <simulation> as the root, the program will not 
crash but console will constantly spit errors.


### Impressions





