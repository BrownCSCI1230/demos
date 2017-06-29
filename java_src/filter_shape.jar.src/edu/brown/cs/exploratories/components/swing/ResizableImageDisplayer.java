package edu.brown.cs.exploratories.components.swing;

import edu.brown.cs.exploratories.components.util.Debug;
import edu.brown.cs.exploratories.components.util.Debug.Debugable;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.beans.PropertyVetoException;

public class ResizableImageDisplayer
  extends ImageDisplayer
  implements Debug.Debugable
{
  private static final String CLASSNAME = "edu.brown.cs.exploratories.components.swing.ResizableImageDisplayer";
  private static final boolean DEBUG = Debug.getDebugMode("edu.brown.cs.exploratories.components.swing.ResizableImageDisplayer");
  public static final String PROP_ASPECT_RATIO = "aspectRatio";
  public static final double NULL_ASPECT_RATIO = 0.0D;
  public static final double DEFAULT_ASPECT_RATIO = 0.0D;
  private double aspectRatio;
  
  public ResizableImageDisplayer()
  {
    setAspectRatio(0.0D);
  }
  
  public double getAspectRatio()
  {
    return this.aspectRatio;
  }
  
  public void setAspectRatio(double paramDouble)
  {
    double d = this.aspectRatio;
    try
    {
      fireVetoableChange("aspectRatio", new Double(d), new Double(paramDouble));
      this.aspectRatio = paramDouble;
      firePropertyChange("aspectRatio", new Double(d), new Double(paramDouble));
    }
    catch (PropertyVetoException localPropertyVetoException) {}
  }
  
  public Rectangle calcImageRectangle(Rectangle paramRectangle)
  {
    if (paramRectangle == null) {
      paramRectangle = new Rectangle();
    }
    Insets localInsets = getInsets();
    double d1 = getWidth() - localInsets.left - localInsets.right;
    double d2 = getHeight() - localInsets.top - localInsets.bottom;
    double d3 = getAspectRatio();
    if (d3 == 0.0D)
    {
      Image localImage = getImage();
      if (localImage != null)
      {
        double d5 = localImage.getWidth(this);
        double d7 = localImage.getHeight(this);
        d3 = d5 / d7;
      }
      else
      {
        d3 = 1.0D;
      }
    }
    double d4 = d3 * d2;
    double d6 = d2;
    double d8 = 0.0D;
    double d9 = 0.0D;
    if (d4 > d1)
    {
      d4 = d1;
      d6 = d1 / d3;
      d9 = (d2 - d6) / 2.0D;
    }
    else
    {
      d8 = (d1 - d4) / 2.0D;
    }
    paramRectangle.x = ((int)d8);
    paramRectangle.y = ((int)d9);
    paramRectangle.width = ((int)d4);
    paramRectangle.height = ((int)d6);
    return paramRectangle;
  }
}


/* Location:              /Users/masonbartle/Downloads/filter_shape.jar!/edu/brown/cs/exploratories/components/swing/ResizableImageDisplayer.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */