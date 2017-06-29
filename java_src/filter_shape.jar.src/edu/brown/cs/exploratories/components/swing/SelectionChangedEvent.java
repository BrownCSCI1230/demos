package edu.brown.cs.exploratories.components.swing;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;

public class SelectionChangedEvent
  extends ActionEvent
{
  public static final int CHANGE_START = 0;
  public static final int CHANGE_RESIZING = 1;
  public static final int CHANGE_MOVING = 2;
  public static final int CHANGE_END = 3;
  private Point newLocation;
  private Dimension newSize;
  
  public SelectionChangedEvent(Object paramObject, Point paramPoint, Dimension paramDimension, int paramInt)
  {
    super(paramObject, paramInt, "");
    this.newLocation = paramPoint;
    this.newSize = paramDimension;
  }
  
  public Point getNewLocation()
  {
    return this.newLocation;
  }
  
  public Dimension getNewSize()
  {
    return this.newSize;
  }
}


/* Location:              /Users/masonbartle/Downloads/filter_shape.jar!/edu/brown/cs/exploratories/components/swing/SelectionChangedEvent.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */