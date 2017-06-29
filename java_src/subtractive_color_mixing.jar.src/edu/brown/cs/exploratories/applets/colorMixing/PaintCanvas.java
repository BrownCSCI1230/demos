package edu.brown.cs.exploratories.applets.colorMixing;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.beans.PropertyVetoException;
import java.io.PrintStream;
import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;

public class PaintCanvas
  extends JComponent
{
  public static final Color DEFAULT_BACKGROUND = Color.white;
  public static final String PROP_COMPOSITE = "composite";
  public static final Composite DEFAULT_COMPOSITE = AlphaComposite.SrcOver;
  public static final String PROP_BRUSH_RADIUS = "brushRadius";
  public static final int DEFAULT_BRUSH_RADIUS = 10;
  public static final String PROP_BACKGROUND_IMAGE = "backgroundImage";
  public static final Image DEFAULT_BACKGROUND_IMAGE = null;
  private BufferedImage buffer;
  private Composite composite;
  private int brushRadius;
  private Image backgroundImage;
  private static final Component component = new Component() {};
  private static final MediaTracker tracker = new MediaTracker(component);
  private Stroke paintStroke;
  private GeneralPath paintPath;
  private Point lastPoint;
  
  public PaintCanvas()
  {
    setBackground(DEFAULT_BACKGROUND);
    setComposite(DEFAULT_COMPOSITE);
    setBrushRadius(10);
    setBackgroundImage(DEFAULT_BACKGROUND_IMAGE);
    this.buffer = new BufferedImage(10, 10, 1);
    clearBuffer();
    MouseInputAdapter local2 = new MouseInputAdapter()
    {
      public void mousePressed(MouseEvent paramAnonymousMouseEvent)
      {
        PaintCanvas.this.handleMousePressed(paramAnonymousMouseEvent);
      }
      
      public void mouseDragged(MouseEvent paramAnonymousMouseEvent)
      {
        PaintCanvas.this.handleMouseDragged(paramAnonymousMouseEvent);
      }
      
      public void mouseReleased(MouseEvent paramAnonymousMouseEvent)
      {
        PaintCanvas.this.handleMouseReleased(paramAnonymousMouseEvent);
      }
    };
    addMouseListener(local2);
    addMouseMotionListener(local2);
  }
  
  public Image getBackgroundImage()
  {
    return this.backgroundImage;
  }
  
  public void setBackgroundImage(Image paramImage)
  {
    Image localImage = this.backgroundImage;
    if (localImage != paramImage) {
      synchronized (this)
      {
        try
        {
          fireVetoableChange("backgroundImage", localImage, paramImage);
          this.backgroundImage = paramImage;
          if (paramImage != null)
          {
            tracker.addImage(this.backgroundImage, 0);
            tracker.waitForID(0);
            tracker.removeImage(this.backgroundImage, 0);
          }
          firePropertyChange("backgroundImage", localImage, paramImage);
        }
        catch (PropertyVetoException localPropertyVetoException) {}catch (InterruptedException localInterruptedException)
        {
          System.out.println("INTERRUPTED while loading Image");
          tracker.removeImage(this.backgroundImage, 0);
          this.backgroundImage = null;
          if (localImage != null) {
            firePropertyChange("backgroundImage", localImage, null);
          }
        }
        clearBuffer();
      }
    }
  }
  
  public int[] getPixel(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null) {
      throw new NullPointerException();
    }
    int[] arrayOfInt = null;
    Insets localInsets = getInsets();
    if ((paramInt1 > localInsets.left) && (paramInt1 < getWidth() - localInsets.right) && (paramInt2 > localInsets.top) && (paramInt2 < getHeight() - localInsets.bottom))
    {
      WritableRaster localWritableRaster = this.buffer.getRaster();
      paramArrayOfInt = localWritableRaster.getPixel(paramInt1, paramInt2, paramArrayOfInt);
      arrayOfInt = paramArrayOfInt;
    }
    return arrayOfInt;
  }
  
  public int getBrushRadius()
  {
    return this.brushRadius;
  }
  
  public void setBrushRadius(int paramInt)
  {
    if (paramInt < 1) {
      paramInt = 1;
    }
    int i = this.brushRadius;
    try
    {
      fireVetoableChange("brushRadius", new Integer(i), new Integer(paramInt));
      this.brushRadius = paramInt;
      firePropertyChange("brushRadius", new Integer(i), new Integer(paramInt));
    }
    catch (PropertyVetoException localPropertyVetoException) {}
  }
  
  public Composite getComposite()
  {
    return this.composite;
  }
  
  public void setComposite(Composite paramComposite)
  {
    if (paramComposite == null) {
      paramComposite = AlphaComposite.SrcOver;
    }
    Composite localComposite = this.composite;
    try
    {
      fireVetoableChange("composite", localComposite, paramComposite);
      this.composite = paramComposite;
      firePropertyChange("composite", localComposite, this.composite);
    }
    catch (PropertyVetoException localPropertyVetoException) {}
  }
  
  public void setSize(int paramInt1, int paramInt2)
  {
    handleResize(paramInt1, paramInt2);
    super.setSize(paramInt1, paramInt2);
  }
  
  public void setSize(Dimension paramDimension)
  {
    handleResize(paramDimension.getWidth(), paramDimension.getHeight());
    super.setSize(paramDimension);
  }
  
  public void setBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    handleResize(paramInt3, paramInt4);
    super.setBounds(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void setBounds(Rectangle paramRectangle)
  {
    handleResize(paramRectangle.getWidth(), paramRectangle.getHeight());
    super.setBounds(paramRectangle);
  }
  
  public void eraseCanvas()
  {
    clearBuffer();
    repaint();
  }
  
  public void paintComponent(Graphics paramGraphics)
  {
    if (isEnabled())
    {
      Insets localInsets = getInsets();
      int i = getWidth() - localInsets.left - localInsets.right;
      int j = getHeight() - localInsets.top - localInsets.bottom;
      if (i < 0) {
        i = 0;
      } else if (i > this.buffer.getWidth()) {
        i = this.buffer.getWidth();
      }
      if (j < 0) {
        j = 0;
      } else if (j > this.buffer.getHeight()) {
        j = this.buffer.getHeight();
      }
      if ((i > 0) && (j > 0))
      {
        BufferedImage localBufferedImage = this.buffer.getSubimage(localInsets.left, localInsets.top, i, j);
        paramGraphics.drawImage(localBufferedImage, localInsets.left, localInsets.top, this);
      }
    }
  }
  
  private void handleResize(double paramDouble1, double paramDouble2)
  {
    double d1 = this.buffer.getWidth();
    double d2 = this.buffer.getHeight();
    if ((paramDouble1 > d1) || (paramDouble2 > d2))
    {
      WritableRaster localWritableRaster = this.buffer.getRaster();
      this.buffer = new BufferedImage((int)paramDouble1, (int)paramDouble2, 1);
      clearBuffer();
      this.buffer.setData(localWritableRaster);
    }
  }
  
  private void handleMousePressed(MouseEvent paramMouseEvent)
  {
    if (isEnabled())
    {
      this.paintStroke = new BasicStroke(getBrushRadius(), 1, 1);
      this.paintPath = new GeneralPath();
      Point localPoint = paramMouseEvent.getPoint();
      this.paintPath.moveTo(localPoint.x, localPoint.y);
      this.lastPoint = localPoint;
    }
  }
  
  private void handleMouseDragged(MouseEvent paramMouseEvent)
  {
    if (isEnabled())
    {
      Point localPoint = paramMouseEvent.getPoint();
      this.paintPath.lineTo(localPoint.x, localPoint.y);
      int i = getBrushRadius();
      Insets localInsets = getInsets();
      int j = Math.max(Math.min(this.lastPoint.x, localPoint.x) - i, localInsets.left);
      int k = Math.max(Math.min(this.lastPoint.y, localPoint.y) - i, localInsets.top);
      int m = Math.min(Math.max(this.lastPoint.x, localPoint.x) + i, this.buffer.getWidth() - localInsets.right);
      int n = Math.min(Math.max(this.lastPoint.y, localPoint.y) + i, this.buffer.getHeight() - localInsets.bottom);
      int i1 = m - j;
      int i2 = n - k;
      if ((i1 > 0) && (i2 > 0))
      {
        BufferedImage localBufferedImage1 = new BufferedImage(i1, i2, 1);
        BufferedImage localBufferedImage2 = this.buffer.getSubimage(j, k, i1, i2);
        Graphics2D localGraphics2D = localBufferedImage1.createGraphics();
        localGraphics2D.setComposite(AlphaComposite.SrcOver);
        localGraphics2D.drawImage(localBufferedImage2, 0, 0, this);
        localGraphics2D.setColor(getForeground());
        localGraphics2D.setComposite(this.composite);
        localGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        localGraphics2D.setTransform(AffineTransform.getTranslateInstance(-j, -k));
        localGraphics2D.setStroke(this.paintStroke);
        localGraphics2D.draw(this.paintPath);
        getGraphics().drawImage(localBufferedImage1, j, k, this);
      }
      this.lastPoint = localPoint;
    }
  }
  
  private void handleMouseReleased(MouseEvent paramMouseEvent)
  {
    if (isEnabled())
    {
      Graphics2D localGraphics2D = this.buffer.createGraphics();
      localGraphics2D.setColor(getForeground());
      localGraphics2D.setComposite(this.composite);
      localGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      localGraphics2D.setStroke(this.paintStroke);
      localGraphics2D.draw(this.paintPath);
      repaint();
      this.lastPoint = null;
    }
  }
  
  private void clearBuffer()
  {
    Graphics2D localGraphics2D = this.buffer.createGraphics();
    localGraphics2D.setColor(getBackground());
    localGraphics2D.setComposite(AlphaComposite.SrcOver);
    localGraphics2D.fillRect(0, 0, this.buffer.getWidth(), this.buffer.getHeight());
    if (this.backgroundImage != null) {
      localGraphics2D.drawImage(this.backgroundImage, 0, 0, this);
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/subtractive_color_mixing.jar!/edu/brown/cs/exploratories/applets/colorMixing/PaintCanvas.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */