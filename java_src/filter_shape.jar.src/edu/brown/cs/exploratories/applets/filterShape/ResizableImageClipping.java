package edu.brown.cs.exploratories.applets.filterShape;

import edu.brown.cs.exploratories.components.swing.Selection;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;

public class ResizableImageClipping
  extends Selection
{
  BufferedImage clipping = null;
  
  public Image getClipping()
  {
    return this.clipping;
  }
  
  public void setClipping(Image paramImage)
  {
    if (paramImage != null)
    {
      this.clipping = new BufferedImage(paramImage.getWidth(this), paramImage.getHeight(this), 2);
      Graphics2D localGraphics2D = this.clipping.createGraphics();
      localGraphics2D.drawImage(paramImage, 0, 0, this);
      repaint();
    }
    else
    {
      this.clipping = null;
    }
  }
  
  public void paintComponent(Graphics paramGraphics)
  {
    if (this.clipping != null)
    {
      Insets localInsets = getInsets();
      double d1 = localInsets.top;
      double d2 = localInsets.left;
      double d3 = localInsets.bottom;
      double d4 = localInsets.right;
      double d5 = getHandleSize();
      double d6 = d5 / 2.0D;
      double d7 = getWidth();
      double d8 = getHeight();
      paramGraphics.drawImage(this.clipping, (int)(d2 + d6), (int)(d1 + d6), (int)(d7 - d2 - d4 - d5), (int)(d8 - d1 - d3 - d5), this);
    }
    super.paintComponent(paramGraphics);
  }
}


/* Location:              /Users/masonbartle/Downloads/filter_shape.jar!/edu/brown/cs/exploratories/applets/filterShape/ResizableImageClipping.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */