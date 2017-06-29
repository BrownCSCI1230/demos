package edu.brown.cs.exploratories.applets.twoBoxConvolution;

public abstract interface DBFunctionController
{
  public abstract void setFunctionWindow(DBFunctionWindow paramDBFunctionWindow);
  
  public abstract void mouseStart(int paramInt1, int paramInt2);
  
  public abstract void mouseDrag(int paramInt1, int paramInt2);
  
  public abstract void mouseStop();
  
  public abstract void resize(int paramInt1, int paramInt2);
}


/* Location:              /Users/masonbartle/Downloads/two_box_convolution.jar!/edu/brown/cs/exploratories/applets/twoBoxConvolution/DBFunctionController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */