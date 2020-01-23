# Simulation Design Final
### 

### High level design

As we think more about the text file, we want to abstract the reading of
the file from the other game classes and implementation. This way, the text
file structure could change without impacting other classes. 

The game runner would make a call to the reader inorder to print "weapon"
options. As far as comparing the two selected objects, we came up with two
options. the first would be creating weapon objects which contain the list
of what that object beats. However we realized this is not much functionality
and is maybe not deserving of a class. So the second option is using 
maps, for example, with a key being the weapon, and the value being a 
list of what the "weapon" beats. Then we have a comparator method which 
determines the winner. 


