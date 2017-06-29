package edu.brown.cs.exploratories.applets.combinedColorMixing;

import java.awt.Color;
import java.awt.Point;

public abstract interface LightCarrier
{
  public abstract void receiveLight(Color paramColor);
  
  public abstract Point getReceptionPoint();
  
  public abstract Point getEmissionPoint();
  
  public abstract Color getColor();
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/LightCarrier.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */