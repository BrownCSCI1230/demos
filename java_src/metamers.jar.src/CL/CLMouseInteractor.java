package CL;

import GP.GPMouseInteractor;
import GP.GPPoint;

public class CLMouseInteractor
  extends GPMouseInteractor
{
  CLMouseController app_;
  
  public CLMouseInteractor(CLMouseController paramCLMouseController)
  {
    this.app_ = paramCLMouseController;
  }
  
  public void ButtonDown(GPPoint paramGPPoint, int paramInt)
  {
    this.app_.ButtonDown(paramGPPoint, paramInt);
  }
  
  public void ButtonMotion(GPPoint paramGPPoint, int paramInt)
  {
    this.app_.ButtonMotion(paramGPPoint, paramInt);
  }
  
  public void ButtonUp(GPPoint paramGPPoint, int paramInt)
  {
    this.app_.ButtonUp(paramGPPoint, paramInt);
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/CL/CLMouseInteractor.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */