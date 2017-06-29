package edu.brown.cs.exploratories.applets.sampling;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.PixelGrabber;
import java.io.PrintStream;
import java.net.URL;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SampImage
  extends JPanel
  implements ChangeListener, MouseMotionListener
{
  protected Sampling applet_;
  protected transient SampDTFunctionController samp_ctrl;
  protected Image image_;
  protected transient MediaTracker media_tracker;
  protected transient PixelGrabber pix_grab;
  protected JSlider slider_;
  protected JLabel label_;
  protected boolean point_sample = true;
  protected transient int scan_line = 64;
  protected int num_samples = 32;
  protected int[] image_pixels;
  protected double[] values_;
  protected int hilight_ = -1;
  protected transient boolean first_time = true;
  protected int num_sample_colors = 1;
  protected Color[] sample_colors = { Color.blue };
  private static final String imageName = "mandrill256.gif";
  private static final int IMG_XSTART = 150;
  private static final int IMG_YSTART = 13;
  private static final int SOURCE_X = 440;
  private static final int SOURCE_Y = 320;
  private static final int SLIDER_HEIGHT = 272;
  private static final int SLIDER_WIDTH = 20;
  
  public SampImage(Sampling paramSampling)
  {
    this.applet_ = paramSampling;
    setOpaque(true);
    setDoubleBuffered(true);
    setMinimumSize(new Dimension(500, 340));
    setPreferredSize(new Dimension(500, 340));
    URL localURL = getClass().getResource("mandrill256.gif");
    this.image_ = Toolkit.getDefaultToolkit().getImage(localURL);
    this.media_tracker = new MediaTracker(this);
    this.media_tracker.addImage(this.image_, 1);
    this.label_ = new JLabel("scanline");
    add(this.label_);
    this.slider_ = new JSlider(1, 1, 255, 191);
    this.slider_.setLocation(new Point(585, 10));
    add(this.slider_);
    this.slider_.addChangeListener(this);
    this.values_ = new double[this.num_samples];
    addMouseMotionListener(this);
  }
  
  public void setSampleController(SampDTFunctionController paramSampDTFunctionController)
  {
    this.samp_ctrl = paramSampDTFunctionController;
  }
  
  public void paint(Graphics paramGraphics)
  {
    if (this.first_time) {
      try
      {
        this.media_tracker.waitForAll();
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
    this.label_.setLocation(new Point(32, 128));
    this.slider_.setLocation(new Point(128, 5));
    this.slider_.setSize(20, 272);
    super.paint(paramGraphics);
    paramGraphics.drawImage(this.image_, 150, 13, null);
    if (this.point_sample) {
      paintPointSamples(paramGraphics);
    } else {
      paintAreaSamples(paramGraphics);
    }
  }
  
  public void paintPointSamples(Graphics paramGraphics)
  {
    double d = this.image_.getWidth(null) / (this.num_samples - 1.0D);
    for (int i = 0; i < this.num_samples; i++)
    {
      if (i == this.hilight_) {
        paramGraphics.setColor(Color.red);
      } else {
        paramGraphics.setColor(Color.black);
      }
      paramGraphics.fillOval((int)(150.0D + i * d) - 3, 13 + this.scan_line - 3, 6, 6);
      if (i != this.hilight_) {
        paramGraphics.setColor(this.sample_colors[(i % this.num_sample_colors)]);
      }
      paramGraphics.drawLine((int)(150.0D + i * d), 13 + this.scan_line, (int)(150.0D + i * d), 13 + this.scan_line);
    }
  }
  
  public void paintAreaSamples(Graphics paramGraphics)
  {
    double d = this.image_.getWidth(null) / (this.num_samples - 1.0D);
    int i = (int)(d / 2.0D);
    for (int j = 0; j < this.num_samples; j++)
    {
      paramGraphics.setColor(this.sample_colors[(j % this.num_sample_colors)]);
      paramGraphics.drawRect((int)(150.0D + j * d) - i, 13 + this.scan_line - i, (int)d - 1, (int)d);
      paramGraphics.setColor(new Color((int)this.values_[j], (int)this.values_[j], (int)this.values_[j]));
      paramGraphics.fillRect((int)(150.0D + j * d) - i + 1, 13 + this.scan_line - i + 1, (int)d - 2, (int)d - 1);
    }
  }
  
  public void stateChanged(ChangeEvent paramChangeEvent)
  {
    this.scan_line = (this.image_.getHeight(null) - this.slider_.getValue() - 1);
    double d1 = this.image_.getWidth(null) / (this.num_samples - 1.0D);
    int i = this.scan_line * this.image_.getWidth(null);
    int j;
    if (this.point_sample)
    {
      for (j = 0; j < this.num_samples; j++) {
        this.values_[j] = this.image_pixels[(i + (int)Math.round(j * d1))];
      }
      this.samp_ctrl.update(this.num_samples, this.values_);
    }
    else
    {
      j = this.image_.getWidth(null);
      double d2 = (this.num_samples - 1.0D) / j;
      int k = 0;
      int m = 0;
      for (int n = 0; n < this.num_samples; n++) {
        this.values_[n] = 0.0D;
      }
      for (int i1 = 0; i1 < j; i1++)
      {
        this.values_[((int)Math.round(i1 * d2))] += this.image_pixels[(i + i1)];
        m++;
        if ((int)Math.round(i1 * d2) > k)
        {
          this.values_[k] /= m;
          k = (int)Math.round(i1 * d2);
          m = 0;
        }
      }
      this.values_[(this.num_samples - 1)] /= m;
      this.samp_ctrl.update(this.num_samples, this.values_);
    }
    repaint();
  }
  
  public void setPointSample(boolean paramBoolean)
  {
    this.point_sample = paramBoolean;
    repaint();
    stateChanged(null);
  }
  
  public void setScanline(int paramInt)
  {
    this.scan_line = paramInt;
  }
  
  public void mouseDragged(MouseEvent paramMouseEvent) {}
  
  public void mouseMoved(MouseEvent paramMouseEvent)
  {
    double d = this.image_.getWidth(null) / (this.num_samples - 1.0D);
    int i = 16;
    int j = 13 + this.scan_line;
    int k = j - paramMouseEvent.getY();
    int m = k * k;
    int n = paramMouseEvent.getX();
    for (int i1 = 0; i1 < this.num_samples; i1++)
    {
      int i2 = (int)(150.0D + i1 * d);
      int i3 = i2 - n;
      if (i3 * i3 + m <= i)
      {
        setHilight(i1, true);
        return;
      }
    }
    unsetHilight(true);
  }
  
  public void setHilight(int paramInt, boolean paramBoolean)
  {
    if (paramInt != this.hilight_)
    {
      this.hilight_ = paramInt;
      if (paramBoolean) {
        this.applet_.getController().setHilight(paramInt, false);
      }
      repaint();
    }
  }
  
  public void unsetHilight(boolean paramBoolean)
  {
    if (-1 != this.hilight_)
    {
      this.hilight_ = -1;
      if (paramBoolean) {
        this.applet_.getController().unsetHilight(false);
      }
      repaint();
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/introduction_to_sampling.jar!/edu/brown/cs/exploratories/applets/sampling/SampImage.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */