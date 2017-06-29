package edu.brown.cs.exploratories.applets.sampling;

public abstract interface DTFunctionController
{
  public abstract void setFunctionWindow(DTFunctionWindow paramDTFunctionWindow);
  
  public abstract void resize(int paramInt1, int paramInt2);
  
  public abstract void beginUpdate(DValue paramDValue);
  
  public abstract void valueUpdate();
  
  public abstract void endUpdate();
}


/* Location:              /Users/masonbartle/Downloads/introduction_to_sampling.jar!/edu/brown/cs/exploratories/applets/sampling/DTFunctionController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */