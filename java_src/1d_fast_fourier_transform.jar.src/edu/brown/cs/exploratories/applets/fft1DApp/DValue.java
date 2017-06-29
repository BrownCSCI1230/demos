package edu.brown.cs.exploratories.applets.fft1DApp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class DValue
  extends MouseMotionAdapter
{
  protected transient DTFunctionWindow function_window;
  protected transient boolean mouse_over = false;
  protected transient int last_x;
  protected transient double last_y;
  protected int x_;
  protected double value_;
  protected boolean is_visible = true;
  protected boolean is_editable = false;
  protected boolean is_active = true;
  protected boolean horiz_enable = false;
  protected boolean vert_enable = true;
  protected int h_min;
  protected int h_max;
  protected double v_min;
  protected double v_max;
  protected Color bar_color = Color.black;
  protected Color box_color = Color.darkGray;
  protected Color active_box_color = Color.red;
  public static final int BOX_WIDTH = 3;
  
  public DValue(DTFunctionWindow paramDTFunctionWindow, int paramInt, double paramDouble)
  {
    this.x_ = paramInt;
    this.value_ = paramDouble;
    this.function_window = paramDTFunctionWindow;
  }
  
  public void draw(Graphics paramGraphics)
  {
    if (this.is_visible)
    {
      int i = this.function_window.XChtToPix(this.x_);
      int j = this.function_window.YChtToPix(this.value_);
      paramGraphics.setColor(this.bar_color);
      paramGraphics.drawLine(i, this.function_window.YChtToPix(0.0D), i, j);
    }
  }
  
  public void beginDrag(MouseEvent paramMouseEvent)
  {
    if (!this.is_editable) {
      return;
    }
    if (this.is_active)
    {
      int i = this.function_window.getChartWidth();
      int j = this.function_window.getChartHeight();
      this.last_x = this.function_window.XPixToCht(paramMouseEvent.getX());
      this.last_y = this.function_window.YPixToCht(paramMouseEvent.getY());
      this.h_min = 0;
      this.h_max = i;
      this.v_min = this.function_window.getYStart();
      this.v_max = this.function_window.getYEnd();
      this.function_window.addMouseMotionListener(this);
    }
  }
  
  public void endDrag()
  {
    this.function_window.removeMouseMotionListener(this);
  }
  
  public void mouseDragged(MouseEvent paramMouseEvent)
  {
    if (this.horiz_enable)
    {
      int i = this.function_window.XPixToCht(paramMouseEvent.getX());
      int j = i - this.last_x;
      if ((i + j > this.h_min) && (i + j < this.h_max))
      {
        this.x_ += j;
        this.last_x = i;
      }
      else if (i + j <= this.h_min)
      {
        this.x_ = this.h_min;
        this.last_x = 0;
      }
      else
      {
        this.x_ = this.h_max;
        this.last_x = this.function_window.getChartWidth();
      }
    }
    if (this.vert_enable)
    {
      double d1 = this.function_window.YPixToCht(paramMouseEvent.getY());
      double d2 = d1 - this.last_y;
      if ((d1 + d2 > this.v_min) && (d1 + d2 < this.v_max))
      {
        this.value_ += d2;
        this.last_y = this.function_window.YPixToCht(paramMouseEvent.getY());
      }
      else if (d1 + d2 <= this.v_min)
      {
        this.value_ = this.v_min;
        this.last_y = this.function_window.getYStart();
      }
      else
      {
        this.value_ = this.v_max;
        this.last_y = this.function_window.getYEnd();
      }
    }
    this.function_window.changeValues();
    this.function_window.valueUpdate();
  }
  
  public boolean intersects(MouseEvent paramMouseEvent)
  {
    int i = this.function_window.XChtToPix(this.x_);
    int j = this.function_window.YChtToPix(this.value_);
    this.mouse_over = ((Math.abs(paramMouseEvent.getX() - i) <= 3) && (Math.abs(paramMouseEvent.getY() - j) <= 3));
    return this.mouse_over;
  }
  
  public int getX()
  {
    return this.x_;
  }
  
  public void setX(int paramInt)
  {
    this.x_ = paramInt;
  }
  
  public double getValue()
  {
    return this.value_;
  }
  
  public void setValue(double paramDouble)
  {
    this.value_ = paramDouble;
  }
  
  public void setVisible(boolean paramBoolean)
  {
    this.is_visible = paramBoolean;
  }
  
  public void setActive(boolean paramBoolean)
  {
    this.is_active = paramBoolean;
  }
  
  public void setHorizontalEnable(boolean paramBoolean)
  {
    this.horiz_enable = paramBoolean;
  }
  
  public void setVerticalEnable(boolean paramBoolean)
  {
    this.vert_enable = paramBoolean;
  }
  
  public void setColor(Color paramColor)
  {
    this.bar_color = paramColor;
  }
  
  public void setBoxColor(Color paramColor)
  {
    this.box_color = paramColor;
  }
  
  public void setActiveBoxColor(Color paramColor)
  {
    this.active_box_color = paramColor;
  }
}


/* Location:              /Users/masonbartle/Downloads/1d_fast_fourier_transform.jar!/edu/brown/cs/exploratories/applets/fft1DApp/DValue.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */