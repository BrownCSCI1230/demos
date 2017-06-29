package edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace;

public class RedrawThread
  extends Thread
{
  private int delay;
  private ObjectSpace space;
  private boolean redrawRequest;
  private boolean continueFlag;
  
  public RedrawThread(ObjectSpace paramObjectSpace, int paramInt)
  {
    this.delay = paramInt;
    this.space = paramObjectSpace;
    this.redrawRequest = false;
    this.continueFlag = true;
  }
  
  public void run()
  {
    this.continueFlag = true;
    while (this.continueFlag)
    {
      if (this.redrawRequest) {
        synchronized (this)
        {
          this.space.executeRedraw();
          this.redrawRequest = false;
        }
      }
      try
      {
        sleep(this.delay);
      }
      catch (Exception localException) {}
    }
  }
  
  public void requestRedraw()
  {
    this.redrawRequest = true;
  }
  
  public void setDelay(int paramInt)
  {
    this.delay = paramInt;
  }
  
  public void stopDrawing()
  {
    this.continueFlag = false;
  }
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/visualObjectSpace/RedrawThread.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */