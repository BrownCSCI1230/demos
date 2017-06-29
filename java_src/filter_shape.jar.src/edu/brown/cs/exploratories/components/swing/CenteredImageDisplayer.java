package edu.brown.cs.exploratories.components.swing;

import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;

public class CenteredImageDisplayer
  extends ImageDisplayer
{
  public Rectangle calcImageRectangle(Rectangle paramRectangle)
  {
    if (paramRectangle == null) {
      paramRectangle = new Rectangle();
    }
    Insets localInsets = getInsets();
    int i = getWidth() - localInsets.left - localInsets.right;
    int j = getHeight() - localInsets.top - localInsets.bottom;
    Image localImage = getImage();
    int k = localImage != null ? localImage.getWidth(this) : 0;
    int m = localImage != null ? localImage.getHeight(this) : 0;
    paramRectangle.x = ((int)(i / 2.0D - k / 2.0D));
    paramRectangle.y = ((int)(j / 2.0D - m / 2.0D));
    paramRectangle.width = k;
    paramRectangle.height = m;
    return paramRectangle;
  }
}


/* Location:              /Users/masonbartle/Downloads/filter_shape.jar!/edu/brown/cs/exploratories/components/swing/CenteredImageDisplayer.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */