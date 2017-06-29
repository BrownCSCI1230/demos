package edu.brown.cs.exploratories.applets.threeStepScaling;

import com.sun.j3d.utils.image.TextureLoader;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.media.j3d.ImageComponent2D;
import javax.swing.JPanel;

public class InsetImageViewer
  extends JPanel
{
  protected static final int INSET_DEFAULT = 30;
  protected static final Color TEXT_LABEL_COLOR = Color.black;
  protected App app;
  protected BufferedImage displayedImage;
  protected Color imageInsetsColor;
  protected Insets imageInsets;
  protected Rectangle imageRect;
  protected Color transparent = new Color(255, 255, 255, 0);
  protected RectangleSamplePoint pixelMarker;
  protected double ratioWidth;
  protected double ratioHeight;
  protected int currX;
  protected int currY;
  protected boolean mouseFlag = false;
  protected int markerSize;
  protected InsetMultipleImagesViewer view;
  protected BufferedImage[] internalImage;
  
  public InsetImageViewer()
  {
    this.imageInsets = new Insets(30, 30, 30, 30);
    this.imageInsetsColor = Color.white;
    setBackground(this.imageInsetsColor);
    setDoubleBuffered(true);
    markerInit();
  }
  
  public InsetImageViewer(BufferedImage paramBufferedImage)
  {
    this.displayedImage = paramBufferedImage;
    this.imageInsets = new Insets(30, 30, 30, 30);
    this.imageInsetsColor = Color.white;
    setBackground(this.imageInsetsColor);
    setDoubleBuffered(true);
    markerInit();
  }
  
  public InsetImageViewer(BufferedImage paramBufferedImage, Insets paramInsets)
  {
    this.displayedImage = paramBufferedImage;
    this.imageInsets = paramInsets;
    this.imageInsetsColor = Color.white;
    setBackground(this.imageInsetsColor);
    setDoubleBuffered(true);
    markerInit();
  }
  
  public InsetImageViewer(String paramString)
  {
    String str = paramString;
    Class localClass = getClass();
    URL localURL = localClass.getResource(str);
    TextureLoader localTextureLoader = new TextureLoader(localURL, new Container());
    ImageComponent2D localImageComponent2D = localTextureLoader.getImage();
    BufferedImage localBufferedImage = localImageComponent2D.getImage();
    this.displayedImage = localBufferedImage;
    this.imageInsets = new Insets(30, 30, 30, 30);
    this.imageInsetsColor = Color.white;
    setBackground(this.imageInsetsColor);
    setDoubleBuffered(true);
    markerInit();
  }
  
  public InsetImageViewer(String paramString, Insets paramInsets)
  {
    String str = paramString;
    Class localClass = getClass();
    URL localURL = localClass.getResource(str);
    TextureLoader localTextureLoader = new TextureLoader(localURL, new Container());
    ImageComponent2D localImageComponent2D = localTextureLoader.getImage();
    BufferedImage localBufferedImage = localImageComponent2D.getImage();
    this.displayedImage = localBufferedImage;
    this.imageInsets = paramInsets;
    this.imageInsetsColor = Color.white;
    setBackground(this.imageInsetsColor);
    setDoubleBuffered(true);
    markerInit();
  }
  
  public Color getInsetColor()
  {
    return this.imageInsetsColor;
  }
  
  public void setInsetColor(Color paramColor)
  {
    this.imageInsetsColor = paramColor;
    if (getGraphics() != null) {
      paintComponent(getGraphics());
    }
  }
  
  public Insets getInsets()
  {
    return this.imageInsets;
  }
  
  public void setInsets(Insets paramInsets)
  {
    this.imageInsets = paramInsets;
    if (getGraphics() != null) {
      paintComponent(getGraphics());
    }
  }
  
  public BufferedImage getImage()
  {
    return this.displayedImage;
  }
  
  public void setImage(BufferedImage paramBufferedImage)
  {
    this.displayedImage.flush();
    this.displayedImage = paramBufferedImage;
    if (getGraphics() != null) {
      paintComponent(getGraphics());
    }
  }
  
  public void setImageByString(String paramString)
  {
    String str = paramString;
    Class localClass = getClass();
    URL localURL = localClass.getResource(str);
    TextureLoader localTextureLoader = new TextureLoader(localURL, new Container());
    ImageComponent2D localImageComponent2D = localTextureLoader.getImage();
    BufferedImage localBufferedImage = localImageComponent2D.getImage();
    this.displayedImage = localBufferedImage;
  }
  
  public Rectangle getImageRect()
  {
    return this.imageRect;
  }
  
  public double getRatioWidth()
  {
    return this.ratioWidth;
  }
  
  public double getRatioHeight()
  {
    return this.ratioHeight;
  }
  
  public RectangleSamplePoint getPixelMarker()
  {
    return this.pixelMarker;
  }
  
  public int getCurrentX()
  {
    return this.currX;
  }
  
  public void setCurrentX(int paramInt)
  {
    this.currX = paramInt;
  }
  
  public int getCurrentY()
  {
    return this.currY;
  }
  
  public void setCurrentY(int paramInt)
  {
    this.currY = paramInt;
  }
  
  public void setMouseFlag(boolean paramBoolean)
  {
    this.mouseFlag = paramBoolean;
  }
  
  public void setBase(int paramInt)
  {
    this.markerSize = paramInt;
  }
  
  public int getBase()
  {
    return this.markerSize;
  }
  
  public void setApp(App paramApp)
  {
    this.app = paramApp;
  }
  
  public App getApp()
  {
    return this.app;
  }
  
  public void paintComponent(Graphics paramGraphics)
  {
    super.paintComponent(paramGraphics);
    Graphics2D localGraphics2D = (Graphics2D)paramGraphics;
    this.imageRect = calcImageRect();
    localGraphics2D.setPaint(this.imageInsetsColor);
    localGraphics2D.fill(new Rectangle(0, 0, getWidth(), getHeight()));
    localGraphics2D.drawImage(this.displayedImage, this.imageRect.x, this.imageRect.y, this.imageRect.width, this.imageRect.height, this);
    if (this.mouseFlag == true)
    {
      this.pixelMarker.setWidth(getBase());
      this.pixelMarker.setHeight(getBase());
      this.pixelMarker.setX(getCurrentX() + this.imageRect.x);
      this.pixelMarker.setY(getCurrentY() + this.imageRect.y);
      this.pixelMarker.setBorderVisible(true);
      RectangleSamplePoint localRectangleSamplePoint = getPixelMarker();
      this.view = getApp().getProcessViewer();
      int i = localRectangleSamplePoint.getX();
      int j = localRectangleSamplePoint.getY();
      BufferedImage localBufferedImage = scaleImage(getImage(), getRatioWidth(), getRatioHeight());
      this.internalImage = new BufferedImage[3];
      this.internalImage[0] = localBufferedImage.getSubimage(i - this.imageRect.x, j - this.imageRect.y, getBase(), getBase());
      this.internalImage[1] = scaleImage(this.internalImage[0], 1.0D, 2.0D);
      this.internalImage[2] = scaleImage(this.internalImage[1], 2.0D, 1.0D);
      for (int k = 0; k < 3; k++) {
        this.view.setImage(k, this.internalImage[k]);
      }
    }
    this.pixelMarker.paint(localGraphics2D);
    localGraphics2D.setColor(TEXT_LABEL_COLOR);
    localGraphics2D.drawString("< Click > / < Draw > above to SAMPLE", this.imageRect.x, this.imageRect.y + this.imageRect.height + 15);
  }
  
  protected Rectangle calcImageRect()
  {
    int k;
    int i;
    int j;
    if ((getWidth() > 0) && (getHeight() > 0))
    {
      if (getWidth() > getHeight())
      {
        k = this.imageInsets.top;
        i = getHeight() - 2 * k;
        j = (getWidth() - i) / 2;
      }
      else
      {
        j = this.imageInsets.left;
        i = getWidth() - 2 * j;
        k = (getHeight() - i) / 2;
      }
    }
    else
    {
      j = 0;
      k = 0;
      i = 0;
    }
    if (getImage() != null)
    {
      this.ratioWidth = (i / getImage().getWidth());
      this.ratioHeight = (i / getImage().getHeight());
    }
    return new Rectangle(j, k, i, i);
  }
  
  public void markerInit()
  {
    this.pixelMarker = new RectangleSamplePoint(0, 0, this.transparent);
    this.pixelMarker.setBorderVisible(false);
  }
  
  public BufferedImage scaleImage(BufferedImage paramBufferedImage, double paramDouble1, double paramDouble2)
  {
    BufferedImage localBufferedImage = null;
    AffineTransform localAffineTransform = AffineTransform.getScaleInstance(paramDouble1, paramDouble2);
    AffineTransformOp localAffineTransformOp = new AffineTransformOp(localAffineTransform, 2);
    return localAffineTransformOp.filter(paramBufferedImage, localBufferedImage);
  }
}


/* Location:              /Users/masonbartle/Downloads/three_step_scaling.jar!/edu/brown/cs/exploratories/applets/threeStepScaling/InsetImageViewer.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */