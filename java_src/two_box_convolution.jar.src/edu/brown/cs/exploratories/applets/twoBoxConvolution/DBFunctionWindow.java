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

public class DBFunctionWindow
  extends JLayeredPane
  implements Serializable, ComponentListener, MouseListener, MouseMotionListener
{
  protected int m_offset = Integer.MIN_VALUE;
  protected int m_width = 0;
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
  protected double y_start = -0.5D;
  protected double y_end = 1.5D;
  protected int marker_bar = -1;
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
  protected transient DBFunctionController function_controller;
  
  public DBFunctionWindow()
  {
    setOpaque(true);
    setDoubleBuffered(true);
    this.border_ = BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Function", 0, 3);
    setBorder(this.border_);
    addMouseListener(this);
    addComponentListener(this);
    this.prev_size = getSize();
  }
  
  public void setController(DBFunctionController paramDBFunctionController)
  {
    this.function_controller = paramDBFunctionController;
    this.function_controller.setFunctionWindow(this);
  }
  
  public void paintComponent(Graphics paramGraphics)
  {
    createBacking(paramGraphics);
    createGraph(paramGraphics);
    Rectangle localRectangle = paramGraphics.getClipBounds();
    paramGraphics.drawImage(this.chart_img, localRectangle.x, localRectangle.y, localRectangle.x + localRectangle.width, localRectangle.y + localRectangle.height, localRectangle.x, localRectangle.y, localRectangle.x + localRectangle.width, localRectangle.y + localRectangle.height, null);
    this.prev_size = getSize();
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
      this.m_width = i4;
      int i5 = getSize().height - (localInsets.top + localInsets.bottom + n + 2) - m;
      if ((i4 != this.chart_width) || (i5 != this.chart_height)) {
        realloc();
      }
      double d1 = i5 / (this.y_end - this.y_start);
      int i6 = (int)(-this.y_start * d1);
      if (i6 < 0) {
        i6 = 0;
      }
      if (i6 > i5) {
        i6 = i5;
      }
      double d2 = i4 / (this.x_end - this.x_start);
      int i7 = (int)(i2 + i4 / 2 - d2 * 0.5D);
      int i8 = i5 - i6 * 2 + 1;
      if (this.m_offset == Integer.MIN_VALUE) {
        this.m_offset = (-i4 / 2);
      }
      this.chart_gfx.setClip(i2, i3, i4, i5);
      this.chart_gfx.setColor(Color.lightGray);
      this.chart_gfx.fillRect(i7, i8, (int)d2, (int)d1);
      this.chart_gfx.setColor(Color.lightGray);
      this.chart_gfx.fillRect(i7 + this.m_offset, i8, (int)d2, (int)d1);
      if ((this.m_offset > -d2) && (this.m_offset < d2)) {
        if (this.m_offset <= 0)
        {
          this.chart_gfx.setColor(Color.blue);
          this.chart_gfx.fillRect(i7, i8, (int)d2 + this.m_offset, (int)d1);
        }
        else
        {
          this.chart_gfx.setColor(Color.blue);
          this.chart_gfx.fillRect(i7 + this.m_offset, i8, (int)d2 - this.m_offset, (int)d1);
        }
      }
      this.chart_gfx.setColor(Color.black);
      this.chart_gfx.drawRect(i7, i8, (int)d2, (int)d1);
      this.chart_gfx.setColor(Color.black);
      this.chart_gfx.drawRect(i7 + this.m_offset, i8, (int)d2, (int)d1);
    }
  }
  
  public double getOverlap(int paramInt)
  {
    paramInt -= this.m_width / 2;
    double d = this.m_width / (this.x_end - this.x_start);
    if ((paramInt > -d) && (paramInt < d))
    {
      if (paramInt < 0) {
        return 1.0D + paramInt / d;
      }
      return 1.0D - paramInt / d;
    }
    return 0.0D;
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
  
  public void changeValues(int paramInt1, int paramInt2, double[] paramArrayOfDouble) {}
  
  public void changeLoc(int paramInt)
  {
    this.m_offset = paramInt;
    if ((this.graphpaper_gfx == null) || (this.chart_gfx == null))
    {
      System.err.println("Internal error: Graphics Contexts not initialized in DBFunctionWindow.changeValues()");
      return;
    }
    this.active_changed = true;
    repaint();
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {}
  
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
  
  public void mousePressed(MouseEvent paramMouseEvent) {}
  
  public void mouseReleased(MouseEvent paramMouseEvent) {}
  
  public void mouseDragged(MouseEvent paramMouseEvent) {}
  
  public void mouseMoved(MouseEvent paramMouseEvent)
  {
    mouseDragged(paramMouseEvent);
  }
  
  public void componentResized(ComponentEvent paramComponentEvent)
  {
    realloc();
    this.function_controller.resize(this.chart_width, this.chart_height);
  }
  
  public void realloc() {}
  
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


/* Location:              /Users/masonbartle/Downloads/two_box_convolution.jar!/edu/brown/cs/exploratories/applets/twoBoxConvolution/DBFunctionWindow.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */