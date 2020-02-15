# Simulation Design Final
### Benjamin Lu (bll32)

## Team Roles and Responsibilities

 * Benjamin Lu - Model, FileReader

 * Team Member #2

 * Team Member #3


## Design goals

#### What Features are Easy to Add


## High-level Design

#### Core Classes

#####Model

Model is one of the essential core classes for the simulation to run. 
Model initializes the grid of cell objects. However there are no getters
for the entire grid, other classes are only allowed to access individual
cells as well as neighbors of a specific cell. 

#####FileReader

FileReader serves as the first step in starting a simulation. XML files
contain the simulation type and parameters associated with that simulation.
FileReader is able to return the value of any parameter if that parameter
or value exists. If not, FileReader will generate an error alert explaining
which parameter is invalid. In order to start an information the controller
requires all the starting parameter values which are read in via FileReader

## Assumptions that Affect the Design

#### Features Affected by Assumptions

It is assumed that the XML file will at least have a <simulation> 
element within it. The XML files are not required to have all parameters
or correct parameters, but simulation will only run if parameters are correct.
Under these assumptions the program should not crash due to XML
file errors. 


## New Features HowTo

#### Easy to Add Features

An easy to add feature are new types of models. When adding new models
of different polygons, all needs to be adjusted is how neighbors are found. 

#### Other Features not yet Done

