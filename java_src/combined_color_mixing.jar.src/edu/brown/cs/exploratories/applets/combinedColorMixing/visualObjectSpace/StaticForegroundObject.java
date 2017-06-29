package edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class StaticForegroundObject
  implements Drawable
{
  private Image norm;
  private int x;
  private int y;
  private ObjectSpace space;
  protected Graphics graphics;
  protected Rectangle target;
  
  public StaticForegroundObject(ObjectSpace paramObjectSpace, int paramInt1, int paramInt2, String paramString)
  {
    this.space = paramObjectSpace;
    this.space.declareForegroundObject(this);
    this.norm = null;
    this.norm = this.space.loadImage(paramString);
    this.space.finishLoading();
    this.x = paramInt1;
    this.y = paramInt2;
    this.target = new Rectangle(0, 0, this.norm.getWidth(this.space.getImageObserver()), this.norm.getHeight(this.space.getImageObserver()));
  }
  
  public void setPosition(int paramInt1, int paramInt2)
  {
    this.x = paramInt1;
    this.y = paramInt2;
  }
  
  public void move(int paramInt1, int paramInt2)
  {
    this.x += paramInt1;
    this.y += paramInt2;
  }
  
  public void draw()
  {
    this.space.drawImage(this.x, this.y, this.norm);
  }
  
  public boolean contains(int paramInt1, int paramInt2)
  {
    return this.target.contains(paramInt1, paramInt2);
  }
  
  public void setObjectSpace(ObjectSpace paramObjectSpace, Graphics paramGraphics)
  {
    this.space = paramObjectSpace;
    this.graphics = paramGraphics;
  }
  
  public int getX()
  {
    return this.x;
  }
  
  public int getY()
  {
    return this.y;
  }
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/visualObjectSpace/StaticForegroundObject.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */