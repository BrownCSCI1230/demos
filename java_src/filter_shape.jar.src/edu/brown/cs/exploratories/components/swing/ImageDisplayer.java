package edu.brown.cs.exploratories.components.swing;

import edu.brown.cs.exploratories.components.util.Debug;
import edu.brown.cs.exploratories.components.util.Debug.Debugable;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.PrintStream;
import javax.swing.JComponent;

public class ImageDisplayer
  extends JComponent
  implements Debug.Debugable
{
  private static final String CLASSNAME = "edu.brown.cs.exploratories.components.swing.ImageDisplayer";
  private static final boolean DEBUG = Debug.getDebugMode("edu.brown.cs.exploratories.components.swing.ImageDisplayer");
  public static final String PROP_IMAGE = "image";
  public static final String PROP_IMAGE_BACKGROUND_COLOR = "imageBackgroundColor";
  private Image image;
  private int imageWidth;
  private int imageHeight;
  private Color imageBackgroundColor;
  private transient int loadStatus = 0;
  protected static final Component component = new Component() {};
  protected static final MediaTracker tracker = new MediaTracker(component);
  private BufferedImage offscreenBuffer;
  
  public ImageDisplayer()
  {
    setLayout(null);
  }
  
  public Image getImage()
  {
    return this.image;
  }
  
  public void setImage(Image paramImage)
  {
    Image localImage = this.image;
    try
    {
      fireVetoableChange("image", localImage, paramImage);
      this.image = paramImage;
      loadImage(this.image);
      regenerateOffscreenBuffer();
      firePropertyChange("image", localImage, paramImage);
    }
    catch (PropertyVetoException localPropertyVetoException) {}
  }
  
  public Color getImageBackgroundColor()
  {
    return this.imageBackgroundColor;
  }
  
  public void setImageBackgroundColor(Color paramColor)
  {
    Color localColor = this.imageBackgroundColor;
    try
    {
      fireVetoableChange("imageBackgroundColor", localColor, paramColor);
      this.imageBackgroundColor = paramColor;
      regenerateOffscreenBuffer();
      firePropertyChange("imageBackgroundColor", localColor, paramColor);
    }
    catch (PropertyVetoException localPropertyVetoException) {}
  }
  
  public int getImageLoadStatus()
  {
    return this.loadStatus;
  }
  
  public Dimension getPreferredSize()
  {
    Insets localInsets = getInsets();
    Dimension localDimension = new Dimension();
    Rectangle localRectangle = calcImageRectangle(new Rectangle());
    localDimension.width = (localRectangle.width + localInsets.left + localInsets.right);
    localDimension.height = (localRectangle.height + localInsets.top + localInsets.bottom);
    return localDimension;
  }
  
  public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4);
    regenerateOffscreenBuffer();
  }
  
  public void setBounds(Rectangle paramRectangle)
  {
    super.setBounds(paramRectangle);
    regenerateOffscreenBuffer();
  }
  
  public Rectangle calcImageRectangle(Rectangle paramRectangle)
  {
    if (paramRectangle == null) {
      paramRectangle = new Rectangle();
    }
    paramRectangle.x = 0;
    paramRectangle.y = 0;
    paramRectangle.width = this.imageWidth;
    paramRectangle.height = this.imageHeight;
    return paramRectangle;
  }
  
  public void paintComponent(Graphics paramGraphics)
  {
    super.paintComponent(paramGraphics);
    if (this.offscreenBuffer != null)
    {
      Insets localInsets = getInsets();
      paramGraphics.drawImage(this.offscreenBuffer, localInsets.left, localInsets.top, this);
    }
  }
  
  protected void loadImage(Image paramImage)
  {
    if (paramImage != null) {
      synchronized (tracker)
      {
        tracker.addImage(paramImage, 0);
        try
        {
          tracker.waitForID(0, 0L);
        }
        catch (InterruptedException localInterruptedException)
        {
          System.out.println("INTERRUPTED while loading Image");
        }
        this.loadStatus = tracker.statusID(0, false);
        tracker.removeImage(paramImage, 0);
        this.imageWidth = paramImage.getWidth(this);
        this.imageHeight = paramImage.getHeight(this);
      }
    }
  }
  
  protected void regenerateOffscreenBuffer()
  {
    if (this.image != null)
    {
      Insets localInsets = getInsets();
      int i = getWidth() - localInsets.left - localInsets.right;
      int j = getHeight() - localInsets.top - localInsets.bottom;
      if ((i > 0) && (j > 0))
      {
        this.offscreenBuffer = new BufferedImage(i, j, 2);
        Graphics localGraphics = this.offscreenBuffer.getGraphics();
        Color localColor = getImageBackgroundColor();
        if (localColor == null) {
          localColor = getBackground();
        }
        localGraphics.setColor(localColor);
        localGraphics.fillRect(0, 0, i, j);
        Rectangle localRectangle = calcImageRectangle(new Rectangle());
        localGraphics.drawImage(getImage(), localRectangle.x, localRectangle.y, localRectangle.width, localRectangle.height, this);
      }
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/filter_shape.jar!/edu/brown/cs/exploratories/components/swing/ImageDisplayer.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */