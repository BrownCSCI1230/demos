package edu.brown.cs.exploratories.applets.twoBoxConvolution;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.PrintStream;
import java.io.Serializable;
import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.border.TitledBorder;

public class CTFunctionWindow
  extends JLayeredPane
  implements Serializable, ComponentListener, MouseListener, MouseMotionListener
{
  protected boolean graph_filled = true;
  protected boolean show_numbers = true;
  protected Color bg_color = Color.white;
  protected Color graph_color = Color.darkGray;
  protected Color active_graph_color = Color.blue;
  protected Color primary_cht_color = Color.black;
  protected Color secondary_cht_color = Color.lightGray;
  protected int x_divisions = 7;
  protected int y_divisions = 5;
  protected double x_start = -3.0D;
  protected double x_end = 3.0D;
  protected double y_start = -1.0D;
  protected double y_end = 1.0D;
  protected int marker_bar = -1;
  protected transient double[] graph_values = null;
  protected transient TitledBorder border_;
  protected transient Dimension prev_size;
  protected transient Image graphpaper_img = null;
  protected transient Graphics graphpaper_gfx = null;
  protected transient Image chart_img = null;
  protected transient Graphics chart_gfx = null;
  protected transient boolean redraw_backing = true;
  protected transient boolean is_active;
  protected transient boolean active_changed;
  protected transient int chart_x;
  protected transient int chart_y;
  protected transient int chart_width;
  protected transient int chart_height;
  protected transient PropertyChangeSupport change_support = new PropertyChangeSupport(this);
  protected transient CTFunctionController function_controller;
  
  public CTFunctionWindow()
  {
    setOpaque(true);
    setDoubleBuffered(true);
    this.border_ = BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Function", 0, 3);
    setBorder(this.border_);
    addMouseListener(this);
    addComponentListener(this);
    this.prev_size = getSize();
  }
  
  public void setController(CTFunctionController paramCTFunctionController)
  {
    this.function_controller = paramCTFunctionController;
    this.function_controller.setFunctionWindow(this);
  }
  
  public void paintComponent(Graphics paramGraphics)
  {
    createBacking(paramGraphics);
    createGraph(paramGraphics);
    Rectangle localRectangle = paramGraphics.getClipBounds();
    paramGraphics.drawImage(this.chart_img, localRectangle.x, localRectangle.y, localRectangle.x + localRectangle.width, localRectangle.y + localRectangle.height, localRectangle.x, localRectangle.y, localRectangle.x + localRectangle.width, localRectangle.y + localRectangle.height, null);
    this.prev_size = getSize();
    if (this.marker_bar > -1)
    {
      paramGraphics.setColor(Color.red);
      double d = this.chart_height / (this.y_end - this.y_start);
      int i = (int)(-this.y_start * d);
      int j = (int)((this.graph_values[this.marker_bar] - this.y_start) * d);
      paramGraphics.drawLine(this.marker_bar + this.chart_x, this.chart_y + this.chart_height - i, this.marker_bar + this.chart_x, this.chart_y + this.chart_height - j);
    }
    this.redraw_backing = false;
    this.active_changed = false;
  }
  
  public void createGraph(Graphics paramGraphics)
  {
    if ((this.chart_img == null) || (!this.prev_size.equals(getSize())) || (this.redraw_backing) || (this.active_changed))
    {
      if (this.chart_img == null) {
        realloc();
      }
      this.chart_img = createImage(getSize().width, getSize().height);
      this.chart_gfx = this.chart_img.getGraphics();
      this.chart_gfx.setFont(getFont());
      this.chart_gfx.drawImage(this.graphpaper_img, 0, 0, Color.gray, null);
      Insets localInsets = getInsets();
      int i;
      int j;
      int k;
      int m;
      int n;
      int i1;
      if (this.show_numbers)
      {
        i = this.graphpaper_gfx.getFontMetrics().stringWidth("0000");
        j = this.graphpaper_gfx.getFontMetrics().stringWidth("0");
        k = localInsets.left + i + 4;
        m = this.graphpaper_gfx.getFontMetrics().getMaxAscent() + this.graphpaper_gfx.getFontMetrics().getMaxDescent();
        n = m + 4;
        i1 = this.graphpaper_gfx.getFontMetrics().getMaxAscent();
      }
      else
      {
        i = 1;
        j = 0;
        k = 6;
        m = 1;
        n = 6;
        i1 = 0;
      }
      int i2 = k + 1;
      int i3 = localInsets.top + 1 + m;
      int i4 = getSize().width - localInsets.right - k - 2 - i / 2;
      int i5 = getSize().height - (localInsets.top + localInsets.bottom + n + 2) - m;
      if ((i4 != this.chart_width) || (i5 != this.chart_height)) {
        realloc();
      }
      if (this.is_active) {
        this.chart_gfx.setColor(this.active_graph_color);
      } else {
        this.chart_gfx.setColor(this.graph_color);
      }
      double d = i5 / (this.y_end - this.y_start);
      int i8;
      int i6;
      if (this.graph_filled)
      {
        i8 = (int)(-this.y_start * d);
        if (i8 < 0) {
          i8 = 0;
        }
        if (i8 > i5) {
          i8 = i5;
        }
        for (int i9 = 0; i9 < i4; i9++)
        {
          i6 = (int)((this.graph_values[i9] - this.y_start) * d);
          this.chart_gfx.drawLine(i9 + i2, i3 + i5 - i8, i9 + i2, i3 + i5 - i6);
        }
      }
      else
      {
        int i7 = (int)((this.graph_values[0] - this.y_start) * d);
        for (i8 = 0; i8 < i4 - 1; i8++)
        {
          i6 = i7;
          i7 = (int)((this.graph_values[(i8 + 1)] - this.y_start) * d);
          this.chart_gfx.drawLine(i8 + i2, i3 + i5 - i6, i8 + i2 + 1, i3 + i5 - i7);
        }
      }
    }
  }
  
  public void createBacking(Graphics paramGraphics)
  {
    if ((this.graphpaper_img == null) || (!this.prev_size.equals(getSize())) || (this.redraw_backing))
    {
      this.graphpaper_img = createImage(getSize().width, getSize().height);
      this.graphpaper_gfx = this.graphpaper_img.getGraphics();
      this.graphpaper_gfx.setFont(getFont());
      Insets localInsets = getInsets();
      int i;
      int j;
      int k;
      int m;
      int n;
      int i1;
      if (this.show_numbers)
      {
        i = this.graphpaper_gfx.getFontMetrics().stringWidth("0000");
        j = this.graphpaper_gfx.getFontMetrics().stringWidth("0");
        k = localInsets.left + i + 4;
        m = this.graphpaper_gfx.getFontMetrics().getMaxAscent() + this.graphpaper_gfx.getFontMetrics().getMaxDescent();
        n = m + 4;
        i1 = this.graphpaper_gfx.getFontMetrics().getMaxAscent();
      }
      else
      {
        i = 1;
        j = 0;
        k = 6;
        m = 1;
        n = 6;
        i1 = 0;
      }
      int i2 = k + 1;
      int i3 = localInsets.top + 1 + m;
      int i4 = getSize().width - localInsets.right - k - 2 - i / 2;
      int i5 = getSize().height - (localInsets.top + localInsets.bottom + n + 2) - m;
      this.graphpaper_gfx.setColor(Color.lightGray);
      this.graphpaper_gfx.fillRect(0, 0, getSize().width, getSize().height);
      this.graphpaper_gfx.setColor(getForeground());
      this.graphpaper_gfx.drawRect(i2 - 1, i3 - 1, i4 + 2, i5 + 2);
      this.graphpaper_gfx.setColor(this.bg_color);
      this.graphpaper_gfx.fillRect(i2, i3, i4, i5);
      double d1 = this.x_start;
      String str;
      int i6;
      for (int i7 = 0; i7 < this.x_divisions; i7++)
      {
        int i8 = (int)(i2 + i4 * i7 / (this.x_divisions - 1.0D));
        if (this.show_numbers)
        {
          this.graphpaper_gfx.setColor(this.primary_cht_color);
          str = new Double(d1).toString();
          if (d1 < 0.0D) {
            i6 = Math.min(5, str.length());
          } else {
            i6 = Math.min(4, str.length());
          }
          str = str.substring(0, i6);
          this.graphpaper_gfx.drawString(str, i8 - j * str.length() / 2, i3 + i5 + i1 + 2);
          d1 += (this.x_end - this.x_start) / (this.x_divisions - 1.0D);
        }
        this.graphpaper_gfx.setColor(this.secondary_cht_color);
        this.graphpaper_gfx.drawLine(i8, i3, i8, i3 + i5);
      }
      double d2 = this.y_end;
      for (int i9 = 0; i9 < this.y_divisions; i9++)
      {
        i10 = (int)(i3 + i5 * i9 / (this.y_divisions - 1.0D));
        if (this.show_numbers)
        {
          this.graphpaper_gfx.setColor(this.primary_cht_color);
          str = new Double(d2).toString();
          i6 = Math.min(4, str.length());
          str = str.substring(0, i6);
          this.graphpaper_gfx.drawString(str, localInsets.left + 2, i10 + 2);
          d2 -= (this.y_end - this.y_start) / (this.y_divisions - 1.0D);
        }
        this.graphpaper_gfx.setColor(this.secondary_cht_color);
        this.graphpaper_gfx.drawLine(i2, i10, i2 + i4, i10);
      }
      this.graphpaper_gfx.setColor(this.primary_cht_color);
      int i10 = (int)(-this.x_start / (this.x_end - this.x_start) * i4) + i2;
      if ((i10 >= i2) && (i10 <= i2 + i4)) {
        this.graphpaper_gfx.drawLine(i10, i3, i10, i3 + i5);
      }
      int i11 = (int)(i3 + i5 - -this.y_start / (this.y_end - this.y_start) * i5);
      if ((i11 >= i3) && (i11 <= i3 + i5)) {
        this.graphpaper_gfx.drawLine(i2, i11, i2 + i4, i11);
      }
    }
  }
  
  public void changeValues(int paramInt1, int paramInt2, double[] paramArrayOfDouble)
  {
    if ((this.graphpaper_gfx == null) || (this.chart_gfx == null))
    {
      System.err.println("Internal error: Graphics Contexts not initialized in CTFunctionWindow.changeValues()");
      return;
    }
    int i = paramInt1;
    if (i < 0) {
      i = 0;
    }
    int j = paramInt1 + paramInt2;
    if (j > this.chart_width) {
      j = this.chart_width;
    }
    this.chart_gfx.drawImage(this.graphpaper_img, i + this.chart_x, this.chart_y, j + this.chart_x, this.chart_y + this.chart_height, i + this.chart_x, this.chart_y, j + this.chart_x, this.chart_y + this.chart_height, null);
    if (this.is_active) {
      this.chart_gfx.setColor(this.active_graph_color);
    } else {
      this.chart_gfx.setColor(this.graph_color);
    }
    double d = this.chart_height / (this.y_end - this.y_start);
    try
    {
      int n;
      int i2;
      int i1;
      int k;
      if (this.graph_filled)
      {
        n = (int)(-this.y_start * d);
        if (n < 0) {
          n = 0;
        }
        if (n > this.chart_height) {
          n = this.chart_height;
        }
        for (i2 = 0; i2 < paramInt2; i2++)
        {
          this.graph_values[(i2 + paramInt1)] = paramArrayOfDouble[i2];
          i1 = i2 + paramInt1;
          this.graph_values[i1] = paramArrayOfDouble[i2];
          if (this.graph_values[i1] > this.y_end) {
            this.graph_values[i1] = this.y_end;
          }
          if (this.graph_values[i1] < this.y_start) {
            this.graph_values[i1] = this.y_start;
          }
          k = (int)((this.graph_values[i1] - this.y_start) * d);
          this.chart_gfx.drawLine(i2 + paramInt1 + this.chart_x, this.chart_y + this.chart_height - n, i2 + paramInt1 + this.chart_x, this.chart_y + this.chart_height - k);
        }
      }
      else
      {
        for (i1 = 0; i1 < paramInt2; i1++)
        {
          n = i1 + paramInt1;
          this.graph_values[n] = paramArrayOfDouble[i1];
          if (this.graph_values[n] > this.y_end) {
            this.graph_values[n] = this.y_end;
          }
          if (this.graph_values[n] < this.y_start) {
            this.graph_values[n] = this.y_start;
          }
        }
        i2 = paramInt1 + paramInt2 - 1;
        int m = (int)((this.graph_values[paramInt1] - this.y_start) * d);
        if (paramInt1 == i2) {
          this.chart_gfx.drawLine(paramInt1 + this.chart_x, this.chart_y + this.chart_height - m, paramInt1 + this.chart_x, this.chart_y + this.chart_height - m);
        } else {
          for (int i3 = paramInt1; i3 < i2; i3++)
          {
            k = m;
            m = (int)((this.graph_values[(i3 + 1)] - this.y_start) * d);
            this.chart_gfx.drawLine(i3 + this.chart_x, this.chart_y + this.chart_height - k, i3 + this.chart_x + 1, this.chart_y + this.chart_height - m);
          }
        }
      }
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
    {
      System.err.println("Internal error: Function Change Event arry out of bounds.");
    }
    repaint();
  }
  
  private void realloc()
  {
    Insets localInsets = getInsets();
    int i;
    int j;
    int k;
    int m;
    if ((this.graphpaper_gfx != null) && (this.show_numbers))
    {
      i = this.graphpaper_gfx.getFontMetrics().stringWidth("0000");
      j = localInsets.left + i + 4;
      k = this.graphpaper_gfx.getFontMetrics().getMaxAscent() + this.graphpaper_gfx.getFontMetrics().getMaxDescent();
      m = k + 4;
    }
    else
    {
      i = 1;
      j = 6;
      k = 1;
      m = 6;
    }
    this.chart_x = (j + 1);
    this.chart_y = (localInsets.top + 1 + k);
    this.chart_width = (getSize().width - localInsets.right - j - 2 - i / 2);
    this.chart_height = (getSize().height - (localInsets.top + localInsets.bottom + m + 2) - k);
    this.graph_values = new double[this.chart_width];
    this.function_controller.resize(this.chart_width, this.chart_height);
    ConvolutionSlide.__instance.fixApplet();
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent)
  {
    ConvolutionSlide.__instance.resetPositions();
    int i = paramMouseEvent.getX() - this.chart_x;
    if ((i < 0) || (i >= this.chart_width)) {
      return;
    }
    int j = this.chart_y + this.chart_height - paramMouseEvent.getY();
    if (j < 0) {
      j = 0;
    }
    if (j > this.chart_height) {
      j = this.chart_height;
    }
    this.function_controller.mouseStart(i, j);
    this.function_controller.mouseStop();
  }
  
  public void mouseEntered(MouseEvent paramMouseEvent)
  {
    this.is_active = true;
    this.active_changed = true;
    repaint();
  }
  
  public void mouseExited(MouseEvent paramMouseEvent)
  {
    this.is_active = false;
    this.active_changed = true;
    repaint();
  }
  
  public void mousePressed(MouseEvent paramMouseEvent)
  {
    ConvolutionSlide.__instance.resetPositions();
    addMouseMotionListener(this);
    int i = paramMouseEvent.getX() - this.chart_x;
    if ((i < 0) || (i >= this.chart_width)) {
      return;
    }
    int j = this.chart_y + this.chart_height - paramMouseEvent.getY();
    if (j < 0) {
      j = 0;
    }
    if (j > this.chart_height) {
      j = this.chart_height;
    }
    this.function_controller.mouseStart(i, j);
  }
  
  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    this.function_controller.mouseStop();
    removeMouseMotionListener(this);
    if (!this.graph_filled) {
      this.active_changed = true;
    }
    repaint();
  }
  
  public void mouseDragged(MouseEvent paramMouseEvent)
  {
    int i = paramMouseEvent.getX() - this.chart_x;
    if (i < 0) {
      i = 0;
    } else if (i >= this.chart_width - 1) {
      i = this.chart_width - 2;
    }
    int j = this.chart_y + this.chart_height - paramMouseEvent.getY();
    if (j < 0) {
      j = 0;
    }
    if (j > this.chart_height) {
      j = this.chart_height;
    }
    this.function_controller.mouseDrag(i, j);
  }
  
  public void mouseMoved(MouseEvent paramMouseEvent)
  {
    mouseDragged(paramMouseEvent);
  }
  
  public void componentResized(ComponentEvent paramComponentEvent)
  {
    realloc();
    this.function_controller.resize(this.chart_width, this.chart_height);
  }
  
  public void componentMoved(ComponentEvent paramComponentEvent) {}
  
  public void componentShown(ComponentEvent paramComponentEvent) {}
  
  public void componentHidden(ComponentEvent paramComponentEvent) {}
  
  public void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener)
  {
    this.change_support.addPropertyChangeListener(paramPropertyChangeListener);
  }
  
  public void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener)
  {
    this.change_support.removePropertyChangeListener(paramPropertyChangeListener);
  }
  
  public Dimension getMinimumSize()
  {
    return new Dimension(150, 150);
  }
  
  public Dimension getPreferredSize()
  {
    return new Dimension(600, 300);
  }
  
  public int getChartHeight()
  {
    return this.chart_height;
  }
  
  public int getChartWidth()
  {
    return this.chart_width;
  }
  
  public void setEnabled(boolean paramBoolean)
  {
    super.setEnabled(paramBoolean);
    if (!paramBoolean)
    {
      removeMouseListener(this);
      this.is_active = false;
      this.active_changed = true;
      repaint();
    }
    else
    {
      addMouseListener(this);
    }
  }
  
  public void setTitle(String paramString)
  {
    this.border_.setTitle(paramString);
  }
  
  public String getTitle()
  {
    return this.border_.getTitle();
  }
  
  public boolean getGraphFilled()
  {
    return this.graph_filled;
  }
  
  public void setGraphFilled(boolean paramBoolean)
  {
    this.redraw_backing = true;
    this.graph_filled = paramBoolean;
  }
  
  public void setForeground(Color paramColor)
  {
    this.redraw_backing = true;
    super.setForeground(paramColor);
  }
  
  public void setFont(Font paramFont)
  {
    this.redraw_backing = true;
    super.setFont(paramFont);
  }
  
  public Color getGraphBackgroundColor()
  {
    return this.bg_color;
  }
  
  public void setGraphBackgroundColor(Color paramColor)
  {
    this.redraw_backing = true;
    this.bg_color = paramColor;
  }
  
  public Color getGraphColor()
  {
    return this.graph_color;
  }
  
  public void setGraphColor(Color paramColor)
  {
    this.redraw_backing = true;
    this.graph_color = paramColor;
  }
  
  public Color getActiveGraphColor()
  {
    return this.active_graph_color;
  }
  
  public void setActiveGraphColor(Color paramColor)
  {
    this.redraw_backing = true;
    this.active_graph_color = paramColor;
  }
  
  public Color getPrimaryChartColor()
  {
    return this.primary_cht_color;
  }
  
  public void setPrimaryChartColor(Color paramColor)
  {
    this.redraw_backing = true;
    this.primary_cht_color = paramColor;
  }
  
  public Color getSecondaryChartColor()
  {
    return this.secondary_cht_color;
  }
  
  public void setSecondaryChartColor(Color paramColor)
  {
    this.redraw_backing = true;
    this.secondary_cht_color = paramColor;
  }
  
  public int getXDivisions()
  {
    return this.x_divisions;
  }
  
  public void setXDivisions(int paramInt)
  {
    this.redraw_backing = true;
    this.x_divisions = paramInt;
  }
  
  public int getYDivisions()
  {
    return this.y_divisions;
  }
  
  public void setYDivisions(int paramInt)
  {
    this.redraw_backing = true;
    this.y_divisions = paramInt;
  }
  
  public double getXStart()
  {
    return this.x_start;
  }
  
  public void setXStart(double paramDouble)
  {
    this.redraw_backing = true;
    this.x_start = paramDouble;
  }
  
  public double getXEnd()
  {
    return this.x_end;
  }
  
  public void setXEnd(double paramDouble)
  {
    this.redraw_backing = true;
    this.x_end = paramDouble;
  }
  
  public double getYStart()
  {
    return this.y_start;
  }
  
  public void setYStart(double paramDouble)
  {
    this.redraw_backing = true;
    this.y_start = paramDouble;
  }
  
  public double getYEnd()
  {
    return this.y_end;
  }
  
  public void setYEnd(double paramDouble)
  {
    this.redraw_backing = true;
    this.y_end = paramDouble;
  }
  
  public void setMarker(int paramInt)
  {
    this.marker_bar = paramInt;
  }
}


/* Location:              /Users/masonbartle/Downloads/two_box_convolution.jar!/edu/brown/cs/exploratories/applets/twoBoxConvolution/CTFunctionWindow.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */