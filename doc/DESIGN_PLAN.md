# Simulation Design Plan
### 05
### Benjamin Lu (bll32), Abhijay Suhag (as866), Connor Hazen (cch57)

## Introduction 

With this project, our team is trying to design a project that is generalized and flexible enough to animate any type of 2D cellular automata simulation that is passed to it from an XML data file. We intend for the project to follow the traditional Model, View, Controller architecture which will separate the data structure from the UI and simulation logic among other things. The program should be flexible in the way in which it handles the rules and cell updates from different games, which will most likely be handled by the Controller. Therefore, the Controller should be open to extension so that specific simulations can affect the Visual and Model as it needs for its specific context. Otherwise, the Model and View should be able to be properly updated, but can remain closed in terms of extension. Additionally, the program should maintain a clear distinction between major components while meticulously communicating the necessary information between them, including those from user inputs. 

## Overview -
![image](/doc/DESIGN_PICTURE.png " ")

This program will be made up of four main classes, the highest being a general runner which creates an MVC simulation object. This MVC is made up of the Model, View and Controller. The view is responsible for displaying the current grid and cells, however it does not contain the UI objects. The Model contains the grid itself and its associated cells. Finally, the controller object acts as a middleman for the grid and view. 

Furthermore, this is where the different simulations will get implemented. The other three classes can be used for any simulation, however the controller will be specific to each simulation. 

We will create a super class of controller which contains all the duplicate functionality with subclass implementing the specifics. These unique elements include the game update logic for each simulation and the global parameters for implementing the view. For example, if there are specific status elements that need to be displayed, then the sim specific controller will handle those elements.

The point of this method is to abstract implementation. Let us examine the grid (model). To keep its structure hidden, we will merely provide a get function for specific cells and a get for neighbors. In this way, changing the grid structure does not affect how other methods interact with it. The view will merely call get on each cell it needs to display. For the actual implementation, we are considering using a hashmap where key is created from x,y pair, or using a 2d array. The values themselves are cell objects. 

For the updating, the actual simulation aspect, the controller will call for each cell and its neighbors and perform the logic. The cells are all objects themselves containing current state and next state parameters. When the controller has determined this new cell state, it sets the next state. Then once all states have been calculated, the current state is changed to the next state for each cell. 

The file itself is read through a specific file reader class. This file reader class parses the information for the controller and runner. This means changing the file format only requires changing the file reader.  The information contained includes initial cell values, global parameters and simulation type. 


## User Interface

The UI will contain the simulation view, a reset button, select simulation option, start, and stop button. Dependent on the simulation being run, different stats will be displayed below the simulation view. For example, the segregation simulation would display the percentage of cells that are satisfied, and the forest fire simulation would display the percentage of land that is on fire. A luxury feature to add if time permits are sliders that adjust the original states before the simulation starts. To choose levels, all five levels will be listed out as buttons and the user simply chooses which one to run. There should not be any erroneous situations that occur with bad input data or empty data. Only the five file options will be able to chosen by the user, there will be no instance where the user is inputting data or a file name likewise eliminating any bad input. 

![image](/doc/UI_example.png " ")

## Design Details 

Runner:

Initialize UI
Creates correct controller based of selected file
Detects simulation state(pause, slow, fast)
Call controller update when necessary 
Handels UI inputs (start, stop, and reset)
Create new controller on file change

Controller Super class:

Uses file parameters to create the view
Create grid from the file
Change cells to next state

Controller subclasses:

Update grid cells using simulation logic.
Update view with new cell states
Display any simulation specific status for UI (ex: percent satisfaction, percent burning)

Model:

Returns specific cells
Return specific cell neighbors

View: 

Initialize display nodes and add to scene graph
Get view options of cell and update javaFx nodes

Cell:

Hold current and next cell state
Hold current display params (color)

FileReader:


Find simulation type, any specific global params, and grid settings
Parse in a way that can be used to initialize the correct controller

Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)
For this we start by calling model.getNeighbors(currentCell). Using the returned list of cells and the gameOfLife controller logic, we can update the currentCell.nextState. 
Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing)
This case is very similar to the above case. Handling the edge vs middle is done in the gameOfLife controller inside the sim logic. Here we can use returned list size of just cell location to determine edge status and needed logic. 
Move to the next generation: update all cells in a simulation from their current state to their next state and display the results graphically
When we call update on the controller, it calculates the new nextState param for each cell. Then it calls the changeState method inside the superclass controller which loops over all cells and sets currentState equal to nextState. 
Set a simulation parameter: set the value of a global configuration parameter, probCatch, for a simulation, Fire, based on the value given in an XML fire
By creating a preset template for the XML file being read, filereader would be able to decode various information from the file including simulation type, parameters, and states. Controller then can call a getter method within file reader to receive the parameters read by fileReader.
Switch simulations: load a new simulation from an XML file, stopping the current running simulation, Segregation, and starting the newly loaded simulation, Wator
This is handled in the runner class. When a new file is selected, the old controller is deleted and its segment of the scene graph is cleared. Then we pass the new file to the file runner which returns a value telling us which simulation controller to create. We now call the start method which creates a new controller. It is the controller's job to initialize the view and model. 

## Design Considerations 

The dependencies between runner and controller are small. As long as the controller has an update method and an initialize method, then the runner is happy. 

For the MVC, there are more intimate connections. The view relies on each cell containing its display values, and the cells are retrieved from the model. However, the deeper dependency is found between controller and model. Here the model must possess all the necessary tools for the controller logic to function. For instance, the segregation simulation places cells in random open cells. This means we must have a function in model to return all cells of a certain type. 

Where to house sim specific logic? - We had a long discussion, back and forth, about where to put the sim specific operations. The XML file can be read by a generalized reader, but the game logic is too complex to be scanned. However, we also wanted to make it very easy to add new simulations. For this reason we decided to implement controller subclasses as this class can change both the view and deal with cell update logic. We considered doing model subclasses, but then realized this would limit simulation specific views.  

Where do we update cells? - Cells are updated within the controller corresponding to the chosen simulation. This is due to the different rules across simulations. Therefore, the controller subclasses must handle it. 

UI separate from controller? - General UI will be separate from controller. This includes the start, stop, reset, and file buttons. UI specific to certain simulations will correspond with the controller chosen for the specific simulation. This includes stats and sliders to modify the simulation starting state. 

What to include in file? - We wanted to include as much information as possible to make our code as flexible as possible. There for display to state encodings (display color for each state), initial cell states, and simulation specific global params. This allows for multiple simulations of the same type. 

Data structure for model, and how to hide its implementation? - For the model, we considered the possibility of both 2D arrays as well as a hashmap with the key being an x,y pair object and values being cell objects. Ultimately, we decided using a 2D array would give us a more generalizable implementation that could be built upon or altered in the future. It would also be more easy to implement and work with with functions we are thinking of coding within the Model. Drawbacks would include scalability as the looping for methods might become taxing as simulations increase, though for the intents for our project, we can assume they will be of reasonable size. A hashmap would allow us to compare cells of certain types more easily and allow us to create coordinate objects with unique hash codes, but would take some more time to implement, and could always be designed after getting a functional version of the program.

## Team Responsibilities

We are going to do this in two stages. In the first part: we will each code one of the MVC classes. The controller class will be barebones but still allow us to check the view and model classes. The next step is fleshing out the Runner class, file reader, and finishing the controller class(es). 

* Team Member #1
	Stage 1: Controller
	Stage 2: Controller Sub Classes

* Team Member #2
	Stage 1: View
	Stage 2: Runner

* Team Member #3
Stage 1: Model, Cell
	Stage 2:File Reader

