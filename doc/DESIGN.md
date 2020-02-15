# Simulation Design Final
### Benjamin Lu (bll32)

## Team Roles and Responsibilities

 * Benjamin Lu - Model, FileReader

 * Connor Hazen, Controllers, Cell, States

 * Team Member #3


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

#### Features Affected by Assumptions

It is assumed that the XML file will at least have a <simulation> 
element within it. The XML files are not required to have all parameters
or correct parameters, but simulation will only run if parameters are correct.
Under these assumptions the program should not crash due to XML
file errors. 


#### Easy to Add Features

An easy to add feature are new types of models. When adding new models
of different polygons, all needs to be adjusted is how neighbors are found. 


The final few simulations would be quite easy to create with the established 
framework. 

#### Other Features not yet Done

We have yet to finish the hexagon display. 

