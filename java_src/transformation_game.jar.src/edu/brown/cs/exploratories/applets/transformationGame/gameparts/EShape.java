package edu.brown.cs.exploratories.applets.transformationGame.gameparts;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public abstract interface EShape
{
  public abstract void showSelf(Graphics2D paramGraphics2D);
  
  public abstract void applyTransform(AffineTransform paramAffineTransform);
  
  public abstract void setLocation(int paramInt1, int paramInt2);
  
  public abstract void resetShape();
  
  public abstract void setString(String paramString);
  
  public abstract void setColorSpecial();
  
  public abstract void setColorSpecial2();
  
  public abstract void setColorNormal();
  
  public abstract boolean contains(int paramInt1, int paramInt2);
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/gameparts/EShape.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */