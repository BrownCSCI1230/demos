package edu.brown.cs.exploratories.applets.fft1DApp;

public abstract interface DTFunctionController
{
  public abstract void setFunctionWindow(DTFunctionWindow paramDTFunctionWindow);
  
  public abstract void resize(int paramInt1, int paramInt2);
  
  public abstract void beginUpdate(DValue paramDValue);
  
  public abstract void valueUpdate();
  
  public abstract void endUpdate();
}


/* Location:              /Users/masonbartle/Downloads/1d_fast_fourier_transform.jar!/edu/brown/cs/exploratories/applets/fft1DApp/DTFunctionController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */