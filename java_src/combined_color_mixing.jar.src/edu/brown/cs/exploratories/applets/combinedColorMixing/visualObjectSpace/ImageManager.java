package edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace;

import java.awt.Image;
import java.util.Vector;

public class ImageManager
  implements Runnable
{
  private int curFrame;
  private int curGroup;
  private Image[][] image;
  private Vector groups;
  private boolean endlessLoopingOn;
  private int delay;
  private int standardDelay;
  private int repeatsLeft;
  private Thread engine;
  protected VisualObject myObject;
  private ObjectSpace space;
  private boolean engineRunning;
  
  public ImageManager(VisualObject paramVisualObject)
  {
    this.myObject = paramVisualObject;
    this.curGroup = 0;
    this.curFrame = 0;
    this.repeatsLeft = 0;
    this.endlessLoopingOn = false;
    this.groups = new Vector(5, 5);
    this.standardDelay = 120;
    this.delay = 120;
    this.space = this.myObject.getSpace();
  }
  
  public int addSingleImage(Image paramImage)
  {
    Vector localVector = new Vector(1, 1);
    localVector.addElement(paramImage);
    this.groups.addElement(localVector);
    return this.groups.indexOf(localVector);
  }
  
  public int addImageGroup(Image[] paramArrayOfImage, int paramInt)
  {
    Vector localVector = new Vector(paramInt, 1);
    for (int i = 0; i < paramInt; i++) {
      localVector.addElement(paramArrayOfImage[i]);
    }
    this.groups.addElement(localVector);
    return this.groups.indexOf(localVector);
  }
  
  public void setDelay(int paramInt)
  {
    this.standardDelay = paramInt;
  }
  
  public void show(int paramInt1, int paramInt2)
  {
    this.repeatsLeft = 0;
    this.endlessLoopingOn = false;
    this.delay = paramInt2;
    show(paramInt1);
  }
  
  public void show(int paramInt)
  {
    this.repeatsLeft = 0;
    this.endlessLoopingOn = false;
    this.delay = this.standardDelay;
    this.curGroup = paramInt;
    this.curFrame = 0;
    go();
  }
  
  public void startLoopedAnimation(int paramInt)
  {
    this.delay = this.standardDelay;
    startLoopedAnimation(paramInt, -1);
  }
  
  public void startLoopedAnimation(int paramInt1, int paramInt2)
  {
    if (paramInt2 == -1)
    {
      this.endlessLoopingOn = true;
    }
    else
    {
      this.repeatsLeft = paramInt2;
      this.endlessLoopingOn = false;
    }
    this.delay = this.standardDelay;
    go();
  }
  
  private void go()
  {
    boolean bool = this.space.getAnimStatus();
    if (bool)
    {
      this.engine = new Thread(this);
      this.engine.start();
    }
    else
    {
      jumpToFinalPosition();
      this.myObject.animationFinished(this.curGroup);
    }
  }
  
  public void jumpToFinalPosition()
  {
    this.curFrame = (((Vector)this.groups.elementAt(this.curGroup)).size() - 1);
  }
  
  public void run()
  {
    this.engineRunning = true;
    int i = 1;
    int j = ((Vector)this.groups.elementAt(this.curGroup)).size();
    while (i != 0)
    {
      action();
      this.space.redraw();
      try
      {
        Thread.sleep(this.delay);
      }
      catch (Exception localException) {}
      this.curFrame += 1;
      if (this.curFrame + 1 > j) {
        if (this.endlessLoopingOn)
        {
          this.curFrame = 0;
        }
        else if (this.repeatsLeft > 0)
        {
          this.repeatsLeft -= 1;
          this.curFrame = 0;
        }
        else
        {
          i = 0;
          this.curFrame -= 1;
        }
      }
    }
    this.engineRunning = false;
    this.engine = null;
    this.myObject.animationFinished(this.curGroup);
  }
  
  public Image getCurrentImage()
  {
    return (Image)((Vector)this.groups.elementAt(this.curGroup)).elementAt(this.curFrame);
  }
  
  public int getFrameIndex()
  {
    return this.curFrame;
  }
  
  public int getGroupIndex()
  {
    return this.curGroup;
  }
  
  public boolean isActive()
  {
    return this.engineRunning;
  }
  
  public void stop()
  {
    this.endlessLoopingOn = false;
    this.repeatsLeft = 0;
    this.curFrame = (((Vector)this.groups.elementAt(this.curGroup)).size() - 1);
  }
  
  public void action() {}
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/visualObjectSpace/ImageManager.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */