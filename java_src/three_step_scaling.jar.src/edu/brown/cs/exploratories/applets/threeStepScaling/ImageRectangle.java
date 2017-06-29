package edu.brown.cs.exploratories.applets.threeStepScaling;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class ImageRectangle
  extends Rectangle
{
  BufferedImage image;
  
  public void setImage(BufferedImage paramBufferedImage)
  {
    this.image = paramBufferedImage;
  }
  
  public BufferedImage getImage()
  {
    return this.image;
  }
}


/* Location:              /Users/masonbartle/Downloads/three_step_scaling.jar!/edu/brown/cs/exploratories/applets/threeStepScaling/ImageRectangle.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */