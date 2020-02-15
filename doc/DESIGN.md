# Simulation Design Final
### Benjamin Lu (bll32)

## Team Roles and Responsibilities

 * Benjamin Lu - Model, FileReader

 * Connor Hazen, Controllers, Cell, States

* Abhijay Suhag - View, Main


## Design goals

The main driving goal for our project was to minimize the necessary steps for adding new simulations. 
Therefore, must of our design considerations can be traced to this desire. The biggest result was
the centralized controller being the unique element per sim. By being connected to both the view and 
model, we could have more control over both back and front end per simulation. This allowed us to 
easily implement things like predator prey and also include dynamic initial params. 


#### What Features are Easy to Add

As far as new simulations, additions are very easy. Each simulations is made by first extending the
super class Controller. This class requires 5 methods to be overwritten, although more complex states 
might require creating a new state class as well. Once the controller is initialized, it must be
add to our switch statement in the Simulator. This statement is what creates correct controller for a given 
tag. Speaking of tags, the final step is creating the XML file, this file must contain the sim specific params,
height, width and simulation type. 

## High-level Design

#### Core Classes

##### Model

Model is one of the essential core classes for the simulation to run. 
Model initializes the grid of cell objects. However there are no getters
for the entire grid, other classes are only allowed to access individual
cells as well as neighbors of a specific cell. 

##### FileReader
 
FileReader serves as the first step in starting a simulation. XML files
contain the simulation type and parameters associated with that simulation.
FileReader is able to return the value of any parameter if that parameter
or value exists. If not, FileReader will generate an error alert explaining
which parameter is invalid. In order to start an information the controller
requires all the starting parameter values which are read in via FileReader

##### Controller

At a high level, the Controller makes a model which initializes each cell object. Then it reads the XML 
file using the passed file reader to set the initial state and simulation params. These initial params
are then used to initialize the cell states using the specific sub class logic. 

Each step of the simulation calls the controller update method. This method steps through each cell 
and calls the subclass updateCell method which uses sim specific update logic in combination with
model neighborhoods. The changes are stored in in the next state instance variable in each cell. 
This allows the simulation to update base purely off current state. Then, when all states are updated,
we go through and switch next and current state. 


##### Cells

 The cell class contains current state and next state, along with a string representing color. 
The states began as just string/ints. However I realized we needed to store more complex information
for certain sims. Instead of the cell now having extra methods, I just made the states be objects 
themselves. This means for sims like Predator Prey, we have a State sub class which contains things
like current heath or time to breed. 

#####View
	
The view serves to convert cells stored in the model to a visualization that has a unique color depending on the state of the cell.
	
These visuals are then organized dynamically in a grid that is displayed on the main stage to the viewer. It is dynamic
	
in the sense that the sizes of the cells change to fit the same size grid no matter how many cells there 
	
are (which can be changed in the xml file). The View also has a public update method to go through the visualization and update
	
the state colors to reflect what is happening based on simulation logic. The view also has a public method that resets all of the 
	
colors on the board back to original in the case that the user wants to reset the simulation.
	

	
#####UserInterface
	
The user interface utilizes a ControlPanel and UserSelectDisplay object to present the user with an interface
	
capable of displaying the visualization and buttons/sliders capable of altering animation states and speed. The user
	
can step, pause, play, and reset the simulation in addition to changing speed. These features are derived from 
	
the ControlPanel class. The UI also allows the user to open a new window to load a new simulation or switch simulations within
	
the same window. These functions are housed in the UserSelectDisplay. Dynamic Sim Specific UI are created in the class
	
by the same name, though this class's methods are called upon in simulation specific controllers. 
	

	
#####Simulator
	
Simulator houses the core animation function as well as controller creation. Specifically, the simulator
	
scans the data folder to populate a dropdown box in the UI with valid files. It then creates an animation 
	
and begins a game loop to continue animating. Upon the entry of a simulation by the user, a switch in the simulator is
	
referenced to decide which controller or game logic is going to be implemented and visualized. 
	


#### Features Affected by Assumptions

It is assumed that the XML file will at least have a <simulation> 
element within it. The XML files are not required to have all parameters
or correct parameters, but simulation will only run if parameters are correct.
Under these assumptions the program should not crash due to XML
file errors. 


#####View
	
If the view was abstracted and had shape subclasses, they would each be assuming that the corresponding
	
shaped model would be passed to it as well. The View also assumes the CellVisual has not been altered in any way.
	
#####Simulator
	
The simulator assumes that the type in the xml file will match a case in the list. Otherwise the controller will be set
	
to null and an error will inform the user of the problem.


#### Easy to Add Features

An easy to add feature are new types of models. When adding new models
of different polygons, all needs to be adjusted is how neighbors are found. 


#####Shape Visualization
In the as866 branch, an example of how to visualize hexagonal shaped grids is already implemented.
It is not in the current implementation of the simulation as we felt that it was not dynamic enough to be 
submitted. That is, one is able to make a hexagonal grid, though the cells will not change size depending on
the number of rows and columns. Essentially, to implement this, the View must be abstracted, leaving necessary
methods in the super class. The main thing unique to each subclass will be the way in which cells are initialized as 
hexagons require staggering while squares do not. Furthermore, the CellVisual class must be revised in order to be able to 
change shape dynamically which can be done by extending polygon and adding a method to set the shape. 

An easy to add feature are new types of models. When adding new models
of different polygons, all needs to be adjusted is how neighbors are found. 

#### Other Features not yet Done

* Saving current configuration of a simulation into a new xml file to be loaded later
* Outlining grid/Grid specific starting configs i.e. cell patches/images


