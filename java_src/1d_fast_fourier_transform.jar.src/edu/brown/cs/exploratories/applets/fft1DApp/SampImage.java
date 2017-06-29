package edu.brown.cs.exploratories.applets.fft1DApp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.PixelGrabber;
import java.io.PrintStream;
import java.net.URL;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SampImage
  extends JPanel
  implements ChangeListener, MouseListener, MouseMotionListener
{
  protected FFT1DApp applet_;
  protected transient ScanlineDTFunctionController samp_ctrl;
  protected transient MediaTracker media_tracker;
  protected transient PixelGrabber pix_grab;
  protected transient int m_scanline = 100;
  protected int[] image_pixels;
  protected double[] values_;
  protected ImageInfo[] images_;
  protected Image image_;
  protected String[] fname_ = { "mandrill256.gif", "horizsine.gif", "spreadsine.gif", "step.gif", "whitenoise.gif" };
  protected String[] text_ = { "Mandrill", "Horizontal Sine", "Spreading Sines", "Step Function", "White Noise" };
  protected int num_samples = 256;
  protected transient boolean first_time = true;
  protected Color scanline_color = Color.blue;
  private static final int IMG_XSTART = 100;
  private static final int IMG_YSTART = 10;
  
  public SampImage(FFT1DApp paramFFT1DApp)
  {
    this.applet_ = paramFFT1DApp;
    setOpaque(true);
    setDoubleBuffered(true);
    setMinimumSize(new Dimension(400, 280));
    setPreferredSize(new Dimension(400, 280));
    this.images_ = new ImageInfo[this.fname_.length];
    for (int i = 0; i < this.fname_.length; i++)
    {
      URL localURL = getClass().getResource(this.fname_[i]);
      Image localImage = Toolkit.getDefaultToolkit().getImage(localURL);
      this.media_tracker = new MediaTracker(this);
      this.media_tracker.addImage(localImage, i + 1);
      try
      {
        this.media_tracker.waitForID(i + 1);
      }
      catch (InterruptedException localInterruptedException)
      {
        localInterruptedException.printStackTrace();
      }
      this.images_[i] = new ImageInfo(localImage, this.text_[i]);
    }
    this.image_ = this.images_[0].image;
    this.values_ = new double[this.num_samples];
    addMouseListener(this);
  }
  
  public ImageInfo[] getImageList()
  {
    return this.images_;
  }
  
  public void setImage(ImageInfo paramImageInfo)
  {
    this.image_ = paramImageInfo.image;
    int i = this.image_.getWidth(null) * this.image_.getHeight(null);
    this.image_pixels = new int[i];
    this.pix_grab = new PixelGrabber(this.image_, 0, 0, this.image_.getWidth(null), this.image_.getHeight(null), this.image_pixels, 0, this.image_.getWidth(null));
    try
    {
      this.pix_grab.grabPixels();
    }
    catch (InterruptedException localInterruptedException)
    {
      return;
    }
    for (int j = 0; j < i; j++) {
      this.image_pixels[j] &= 0xFF;
    }
    stateChanged(null);
    repaint();
  }
  
  public void setSampleController(ScanlineDTFunctionController paramScanlineDTFunctionController)
  {
    this.samp_ctrl = paramScanlineDTFunctionController;
  }
  
  public void paint(Graphics paramGraphics)
  {
    if (this.first_time) {
      try
      {
        int i = this.image_.getWidth(null) * this.image_.getHeight(null);
        this.image_pixels = new int[i];
        this.pix_grab = new PixelGrabber(this.image_, 0, 0, this.image_.getWidth(null), this.image_.getHeight(null), this.image_pixels, 0, this.image_.getWidth(null));
        this.pix_grab.grabPixels();
        for (int j = 0; j < i; j++) {
          this.image_pixels[j] &= 0xFF;
        }
        stateChanged(null);
        this.first_time = false;
      }
      catch (InterruptedException localInterruptedException)
      {
        System.err.println("Interrupted exception in Media Tracker");
      }
    }
    super.paint(paramGraphics);
    paramGraphics.drawImage(this.image_, 100, 10, null);
    if ((this.m_scanline > 10) && (this.m_scanline < 10 + this.image_.getHeight(null)))
    {
      paramGraphics.setColor(this.scanline_color);
      paramGraphics.drawLine(100, this.m_scanline, this.image_.getHeight(null) + 100, this.m_scanline);
    }
  }
  
  public void stateChanged(ChangeEvent paramChangeEvent)
  {
    double d1 = this.image_.getWidth(null) / (this.num_samples - 1.0D);
    int i = this.m_scanline * this.image_.getWidth(null);
    for (int j = 0; j < this.num_samples; j++)
    {
      double d2 = this.image_pixels[(i + (int)Math.round(j * d1))];
      this.values_[j] = d2;
    }
    this.samp_ctrl.update(this.num_samples, this.values_);
    repaint();
  }
  
  public void setScanline(int paramInt)
  {
    this.m_scanline = paramInt;
    repaint();
    int i = paramInt - 10;
    if ((i > 0) && (i < this.image_.getHeight(null)))
    {
      int j = i * this.image_.getWidth(null);
      for (int k = 0; k < this.num_samples; k++) {
        this.values_[k] = this.image_pixels[(j + k)];
      }
      this.samp_ctrl.update(this.num_samples, this.values_);
    }
  }
  
  public void mousePressed(MouseEvent paramMouseEvent)
  {
    Point localPoint = paramMouseEvent.getPoint();
    setScanline(localPoint.y);
    addMouseMotionListener(this);
  }
  
  public final void mouseMoved(MouseEvent paramMouseEvent) {}
  
  public final void mouseDragged(MouseEvent paramMouseEvent)
  {
    Point localPoint = paramMouseEvent.getPoint();
    setScanline(localPoint.y);
  }
  
  public final void mouseReleased(MouseEvent paramMouseEvent)
  {
    Point localPoint = paramMouseEvent.getPoint();
    setScanline(localPoint.y);
    removeMouseMotionListener(this);
  }
  
  public final void mouseClicked(MouseEvent paramMouseEvent) {}
  
  public final void mouseEntered(MouseEvent paramMouseEvent) {}
  
  public final void mouseExited(MouseEvent paramMouseEvent) {}
}


/* Location:              /Users/masonbartle/Downloads/1d_fast_fourier_transform.jar!/edu/brown/cs/exploratories/applets/fft1DApp/SampImage.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */