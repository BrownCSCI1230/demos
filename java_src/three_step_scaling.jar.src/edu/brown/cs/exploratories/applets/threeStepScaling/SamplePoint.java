package edu.brown.cs.exploratories.applets.threeStepScaling;

import java.awt.Color;
import java.awt.Graphics;

public abstract interface SamplePoint
{
  public abstract void setX(int paramInt);
  
  public abstract int getX();
  
  public abstract void setY(int paramInt);
  
  public abstract int getY();
  
  public abstract void setMarkerFillColor(Color paramColor);
  
  public abstract Color getMarkerFillColor();
  
  public abstract void setOutsideMarkerColor(Color paramColor);
  
  public abstract Color getOutsideMarkerColor();
  
  public abstract void setInsideMarkerColor(Color paramColor);
  
  public abstract Color getInsideMarkerColor();
  
  public abstract void setWidth(int paramInt);
  
  public abstract int getWidth();
  
  public abstract void setHeight(int paramInt);
  
  public abstract int getHeight();
  
  public abstract void paint(Graphics paramGraphics);
}


/* Location:              /Users/masonbartle/Downloads/three_step_scaling.jar!/edu/brown/cs/exploratories/applets/threeStepScaling/SamplePoint.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */