package edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace;

import java.awt.Graphics;

public abstract interface Drawable
{
  public abstract boolean contains(int paramInt1, int paramInt2);
  
  public abstract void draw();
  
  public abstract void setObjectSpace(ObjectSpace paramObjectSpace, Graphics paramGraphics);
  
  public abstract int getX();
  
  public abstract int getY();
  
  public abstract void setPosition(int paramInt1, int paramInt2);
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/visualObjectSpace/Drawable.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */