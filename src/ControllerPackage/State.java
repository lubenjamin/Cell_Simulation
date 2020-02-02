package ControllerPackage;

public class State {

  protected String state;

  public State(String state) {
    this.state = state;
  }

  public String getState() {
    return state;
  }

  @Override
  public boolean equals(Object a) {
    String s = a.toString();
    return s.equals(state);
  }
}
