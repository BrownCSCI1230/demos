package edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace;

public class CallbackBehavior
  extends Behavior
{
  public CallbackBehavior(VisualObject paramVisualObject, int paramInt1, int paramInt2)
  {
    super(paramVisualObject, paramInt1, paramInt2);
  }
  
  public void action()
  {
    this.myObject.action(this.count);
  }
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/visualObjectSpace/CallbackBehavior.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */