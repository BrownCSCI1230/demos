package edu.brown.cs.exploratories.applets.threeStepScaling;

import java.awt.Color;
import java.awt.Graphics;

public abstract class AbstractSamplePointImpl
  implements SamplePoint
{
  private int X_POSITION = 100;
  private int Y_POSITION = 100;
  private int OUTER_SHAPE_WIDTH = 30;
  private int OUTER_SHAPE_HEIGHT = 30;
  private static final Color OUTSIDE_MARKER_COLOR = Color.black;
  private static final Color INSIDE_MARKER_COLOR = Color.white;
  private int xPos = this.X_POSITION;
  private int yPos = this.Y_POSITION;
  private int outerShapeWidth = this.OUTER_SHAPE_WIDTH;
  private int outerShapeHeight = this.OUTER_SHAPE_HEIGHT;
  private Color outsideMarkerColor = OUTSIDE_MARKER_COLOR;
  private Color insideMarkerColor = INSIDE_MARKER_COLOR;
  private Color markerFillColor;
  
  public AbstractSamplePointImpl(int paramInt1, int paramInt2, Color paramColor)
  {
    this.markerFillColor = paramColor;
  }
  
  public void setX(int paramInt)
  {
    this.xPos = paramInt;
  }
  
  public int getX()
  {
    return this.xPos;
  }
  
  public void setY(int paramInt)
  {
    this.yPos = paramInt;
  }
  
  public int getY()
  {
    return this.yPos;
  }
  
  public void setMarkerFillColor(Color paramColor)
  {
    this.markerFillColor = paramColor;
  }
  
  public Color getMarkerFillColor()
  {
    return this.markerFillColor;
  }
  
  public void setOutsideMarkerColor(Color paramColor)
  {
    this.outsideMarkerColor = paramColor;
  }
  
  public Color getOutsideMarkerColor()
  {
    return this.outsideMarkerColor;
  }
  
  public void setInsideMarkerColor(Color paramColor)
  {
    this.insideMarkerColor = paramColor;
  }
  
  public Color getInsideMarkerColor()
  {
    return this.insideMarkerColor;
  }
  
  public void setWidth(int paramInt)
  {
    this.outerShapeWidth = paramInt;
  }
  
  public int getWidth()
  {
    return this.outerShapeWidth;
  }
  
  public void setHeight(int paramInt)
  {
    this.outerShapeHeight = paramInt;
  }
  
  public int getHeight()
  {
    return this.outerShapeHeight;
  }
  
  public abstract void paint(Graphics paramGraphics);
}


/* Location:              /Users/masonbartle/Downloads/three_step_scaling.jar!/edu/brown/cs/exploratories/applets/threeStepScaling/AbstractSamplePointImpl.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */