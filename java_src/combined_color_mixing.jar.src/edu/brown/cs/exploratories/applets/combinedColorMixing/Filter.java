package edu.brown.cs.exploratories.applets.combinedColorMixing;

import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.Behavior;
import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.ImageManager;
import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.ObjectSpace;
import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.ParabolicBehavior;
import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.TranslateBehavior;
import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.TranslateToPointBehavior;
import edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace.VisualObject;
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Vector;

public class Filter
  extends VisualObject
{
  private Color myColor;
  private Image norm;
  private boolean highlightStatus;
  private ImageManager imageMan;
  private FilterHolder holder;
  private int normPic;
  private int activatePic;
  private int deactivatePic;
  private int state;
  private int homeX;
  private int homeY;
  private int myID;
  private StorageBox home;
  private static final int RESTING = 0;
  private static final int RESPONDING = 1;
  private static final int ORIENTING_TO_LIGHT = 2;
  private static final int ENGAGING = 3;
  private static final int IN_USE = 3;
  private static final int DISENGAGING = 4;
  private static final int ORIENTING_TO_HOME = 5;
  private static final int RETURNING = 6;
  
  public Filter(ObjectSpace paramObjectSpace, FilterHolder paramFilterHolder, Color paramColor, String paramString)
  {
    super(paramObjectSpace);
    this.holder = paramFilterHolder;
    this.imageMan = new ImageManager(this);
    this.norm = paramObjectSpace.loadImage(new String(paramString).concat("1"));
    this.normPic = this.imageMan.addSingleImage(this.norm);
    Image[] arrayOfImage1 = new Image[4];
    arrayOfImage1[0] = this.norm;
    arrayOfImage1[1] = this.norm;
    arrayOfImage1[2] = this.norm;
    arrayOfImage1[3] = this.norm;
    this.activatePic = this.imageMan.addImageGroup(arrayOfImage1, 4);
    Image[] arrayOfImage2 = new Image[5];
    arrayOfImage2[0] = arrayOfImage1[3];
    arrayOfImage2[1] = arrayOfImage1[2];
    arrayOfImage2[2] = arrayOfImage1[1];
    arrayOfImage2[3] = arrayOfImage1[0];
    arrayOfImage2[4] = this.norm;
    this.deactivatePic = this.imageMan.addImageGroup(arrayOfImage2, 5);
    paramObjectSpace.finishLoading();
    this.state = 0;
    this.homeX = 0;
    this.homeY = 0;
    this.myColor = paramColor;
    this.target = new Rectangle(0, 0, this.norm.getWidth(paramObjectSpace.getImageObserver()), this.norm.getHeight(paramObjectSpace.getImageObserver()));
  }
  
  public void mouseClicked(int paramInt1, int paramInt2)
  {
    if (this.state == 0)
    {
      this.state = 1;
      addBehavior(new ParabolicBehavior(this, this.holder.getTargetX(), this.holder.getTargetY() - 50, 150, 50, 10, ParabolicBehavior.ALLIGN_Y));
      this.holder.newFilterComing(this);
    }
    else if (this.state == 3)
    {
      this.holder.removeFilter();
      goHome();
    }
  }
  
  public void behaviorFinished(Behavior paramBehavior)
  {
    if (this.state == 1)
    {
      this.state = 2;
      this.imageMan.show(this.activatePic);
    }
    else if (this.state == 3)
    {
      this.state = 3;
      this.holder.holdFilter(this);
    }
    else if (this.state == 4)
    {
      this.state = 5;
      this.imageMan.show(this.deactivatePic);
    }
    else if (this.state == 6)
    {
      this.state = 0;
      this.home.sortLayering();
    }
  }
  
  public void goHome()
  {
    this.state = 4;
    clearBehaviors();
    addBehavior(new TranslateBehavior(this, 0.0D, -5.0D, 20, 10));
  }
  
  public void animationFinished(int paramInt)
  {
    if (this.state == 2)
    {
      this.state = 3;
      addBehavior(new TranslateBehavior(this, 0.0D, 5.0D, 10, 10));
    }
    else if (this.state == 5)
    {
      this.state = 6;
      returnToHome();
    }
  }
  
  private void returnToHome()
  {
    this.behaviors.setSize(0);
    addBehavior(new TranslateToPointBehavior(this, this.homeX, this.homeY, 7.0D, 10));
  }
  
  public void resize(double paramDouble1, double paramDouble2, int paramInt1, int paramInt2) {}
  
  public void setHome(int paramInt1, int paramInt2, StorageBox paramStorageBox)
  {
    this.home = paramStorageBox;
    if (this.state == 0)
    {
      this.x = paramInt1;
      this.y = paramInt2;
    }
    this.homeX = paramInt1;
    this.homeY = paramInt2;
  }
  
  public void setID(int paramInt)
  {
    this.myID = paramInt;
  }
  
  public void draw()
  {
    this.space.drawImage(this.x, this.y, this.imageMan.getCurrentImage());
  }
  
  public Color getColor()
  {
    return this.myColor;
  }
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/Filter.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */