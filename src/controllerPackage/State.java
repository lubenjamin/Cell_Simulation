package controllerPackage;


/**
 * This class is passed to each cell to store current state. While it is very simple in this implementation,
 * more complex sub classes could be created for more sophisticated simulations, ie predator prey.
 */
public class State {

  protected final int state;

  public State(int state) {
    this.state = state;
  }

  public int getState() {
    return state;
  }

}
