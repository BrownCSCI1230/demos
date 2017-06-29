package CL;

import GP.GPPoint;

public abstract interface CLMouseController
{
  public abstract void ButtonDown(GPPoint paramGPPoint, int paramInt);
  
  public abstract void ButtonMotion(GPPoint paramGPPoint, int paramInt);
  
  public abstract void ButtonUp(GPPoint paramGPPoint, int paramInt);
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/CL/CLMouseController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */