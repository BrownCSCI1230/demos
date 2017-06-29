package edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Vector;

public class VisualObject
  implements Clickable, Resizeable, Drawable, Manipulable, Highlightable, MouseTracker, Draggable
{
  protected int x;
  protected int y;
  protected ObjectSpace space;
  protected Graphics graphics;
  protected Rectangle target;
  protected Vector behaviors;
  
  public VisualObject(ObjectSpace paramObjectSpace)
  {
    paramObjectSpace.addObject(this);
    this.behaviors = new Vector(10, 5);
  }
  
  public VisualObject(ObjectSpace paramObjectSpace, int paramInt1, int paramInt2)
  {
    paramObjectSpace.addObject(this);
    this.x = ((int)(0.01D * paramInt1 * paramObjectSpace.getSize().width));
    this.y = ((int)(0.01D * paramInt2 * paramObjectSpace.getSize().height));
    this.behaviors = new Vector(10, 5);
  }
  
  public void setObjectSpace(ObjectSpace paramObjectSpace, Graphics paramGraphics)
  {
    this.space = paramObjectSpace;
    this.graphics = paramGraphics;
  }
  
  public boolean contains(int paramInt1, int paramInt2)
  {
    return this.target.contains(paramInt1, paramInt2);
  }
  
  public void setTarget(Rectangle paramRectangle)
  {
    this.target = paramRectangle;
  }
  
  public void setTarget(Image paramImage)
  {
    this.space.finishLoading();
    this.target = new Rectangle(0, 0, paramImage.getWidth(this.space.getImageObserver()), paramImage.getHeight(this.space.getImageObserver()));
  }
  
  public void mouseClicked(int paramInt1, int paramInt2) {}
  
  public void resize(double paramDouble1, double paramDouble2, int paramInt1, int paramInt2) {}
  
  public void draw() {}
  
  public void highlight(int paramInt1, int paramInt2) {}
  
  public void highlightOff() {}
  
  public void mouseMoved(int paramInt1, int paramInt2) {}
  
  public void mousePressed(int paramInt1, int paramInt2) {}
  
  public void mouseDragged(int paramInt1, int paramInt2) {}
  
  public void mouseReleased(int paramInt1, int paramInt2) {}
  
  public void move(int paramInt1, int paramInt2)
  {
    this.x += paramInt1;
    this.y += paramInt2;
  }
  
  public void addBehavior(Behavior paramBehavior)
  {
    this.behaviors.addElement(paramBehavior);
  }
  
  public void behaviorFinished(Behavior paramBehavior) {}
  
  public void animationFinished(int paramInt) {}
  
  public void removeBehavior(Behavior paramBehavior)
  {
    this.behaviors.removeElement(paramBehavior);
  }
  
  public void clearBehaviors()
  {
    for (int i = 0; i < this.behaviors.size(); i++) {
      ((Behavior)this.behaviors.elementAt(i)).stopBehavior();
    }
    this.behaviors.setSize(0);
  }
  
  public void action(int paramInt) {}
  
  public void setX(int paramInt)
  {
    this.x = paramInt;
  }
  
  public void setY(int paramInt)
  {
    this.y = paramInt;
  }
  
  public void setPosition(int paramInt1, int paramInt2)
  {
    this.x = paramInt1;
    this.y = paramInt2;
  }
  
  public int getX()
  {
    return this.x;
  }
  
  public int getY()
  {
    return this.y;
  }
  
  public ObjectSpace getSpace()
  {
    return this.space;
  }
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/visualObjectSpace/VisualObject.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */