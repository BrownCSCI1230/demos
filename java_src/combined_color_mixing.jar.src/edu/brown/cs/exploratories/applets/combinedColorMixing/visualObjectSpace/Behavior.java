package edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace;

public class Behavior
  implements Runnable
{
  private int iterations;
  protected int count;
  private int delay;
  private Thread engine;
  protected VisualObject myObject;
  protected ObjectSpace space;
  private boolean engineRunning;
  private boolean inturrupted;
  private boolean animOn;
  
  public Behavior(VisualObject paramVisualObject, int paramInt1, int paramInt2)
  {
    this.myObject = paramVisualObject;
    this.count = 0;
    this.iterations = paramInt1;
    this.delay = paramInt2;
    this.inturrupted = false;
    this.space = this.myObject.getSpace();
    this.engine = new Thread(this);
    this.engine.start();
  }
  
  public void run()
  {
    this.animOn = this.space.getAnimStatus();
    if (!this.animOn)
    {
      jumpToFinalPosition();
      endBehavior();
    }
    else
    {
      this.engineRunning = true;
      while (this.count < this.iterations)
      {
        action();
        this.space.redraw();
        this.count += 1;
        try
        {
          Thread.sleep(this.delay);
        }
        catch (Exception localException1) {}
      }
      try
      {
        Thread.sleep(this.delay);
      }
      catch (Exception localException2) {}
    }
    this.engineRunning = false;
    endBehavior();
  }
  
  public void endBehavior()
  {
    this.myObject.behaviorFinished(this);
    this.myObject.removeBehavior(this);
    this.space.redraw();
  }
  
  public void jumpToFinalPosition()
  {
    while (this.count < this.iterations)
    {
      action();
      this.count += 1;
    }
  }
  
  public void stopBehavior()
  {
    this.count = this.iterations;
    this.inturrupted = true;
  }
  
  public boolean wasInturrupted()
  {
    return this.inturrupted;
  }
  
  public void action() {}
  
  public void setIterations(int paramInt)
  {
    this.iterations = paramInt;
  }
  
  public int setIterations()
  {
    return this.iterations;
  }
  
  public int whatIteration()
  {
    return this.count;
  }
  
  public boolean isActive()
  {
    return this.engineRunning;
  }
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/visualObjectSpace/Behavior.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */