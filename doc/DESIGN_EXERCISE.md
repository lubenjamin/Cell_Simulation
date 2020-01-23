# Simulation Design Final
#### Connor Hazen - cch57, Abhijay Suhag - as866, Benjamin Lu - bll32


##Discussion on inheritance
We examined Abhijay's code. 

Our first possible use of inheritance would be to create a level class
that contains all the shared code such as score visuals and life visuals. 
However, some levels have unique functionality so we could create subclasses
that extend the super level class. For example, the final level in Abhijay's
game has a boss animation that scales objects. This unique functionality 
would only need to be in the subclass thereby abstracting implementation 
from other classes. 

Additionally, we could create subclasses for each power up meaning future 
power ups could be created by simply creating a new subclass. This eases the 
burden on the Board class from handling the individual power up's.




## High Level Simulation Design
###1:
In order to not mess up neighbors, we came up with two ideas. The first being
have a current grid and future grid. However we realized want to have cell
objects which makes copying grids much harder. Our second idea was to have
cells contain a current value and future value. Each cells then computes its
next value off its neighbors current values. Then we switch all values once every
cell has updated. 

###2
The cells just contain their current state, however complex that may be. Then 
when we update cells, we call some method or class to calculate the new state
based of the simulation rules. 

###3
The grid contains the cell objects. Both the execution, which actually carries
out the simulations, and the visualizer must be able to access the grid. 

###4
It contains the kind of simulation, the title, the author, the width, the height, 
the initial cell states, the settings for global configuration. 

###5
The visualizer just reads through the grid and re-displays the data. The pause 
and slow down options are all handled externally by either the runner or
executioner. 
