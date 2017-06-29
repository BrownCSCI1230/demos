package edu.brown.cs.exploratories.applets.sampling;

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
import java.io.PrintStream;
import java.io.Serializable;
import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.border.TitledBorder;

public class DTFunctionWindow
  extends JLayeredPane
  implements Serializable, ComponentListener, MouseListener, MouseMotionListener
{
  protected boolean show_numbers = true;
  protected Color bg_color = Color.white;
  protected Color graph_color = Color.darkGray;
  protected Color active_graph_color = Color.blue;
  protected Color primary_cht_color = Color.black;
  protected Color secondary_cht_color = Color.lightGray;
  protected int x_divisions = 32;
  protected int y_divisions = 5;
  protected double x_start = 0.0D;
  protected double x_end = 31.0D;
  protected double y_start = 0.0D;
  protected double y_end = 1.0D;
  protected transient DValue[] values_;
  protected transient DValue drag_value;
  protected transient TitledBorder border_;
  protected transient Dimension prev_size;
  protected transient Image graphpaper_img = null;
  protected transient Graphics graphpaper_gfx = null;
  protected transient Image chart_img = null;
  protected transient Graphics chart_gfx = null;
  protected transient boolean redraw_backing = true;
  protected transient int chart_x;
  protected transient int chart_y;
  protected transient int chart_width;
  protected transient int chart_height;
  protected transient boolean was_foo = false;
  protected transient DTFunctionController function_controller;
  protected transient int oldloc_ = -1;
  public static final int NUM_VALUES = 32;
  
  public DTFunctionWindow()
  {
    setOpaque(true);
    setDoubleBuffered(true);
    this.border_ = BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Function", 0, 3);
    setBorder(this.border_);
    addMouseListener(this);
    addMouseMotionListener(this);
    addComponentListener(this);
    this.prev_size = getSize();
    this.values_ = new DValue[32];
    for (int i = 0; i < 32; i++) {
      this.values_[i] = new DValue(this, i, 0.0D);
    }
  }
  
  public void setController(DTFunctionController paramDTFunctionController)
  {
    this.function_controller = paramDTFunctionController;
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
  }
  
  public void createGraph(Graphics paramGraphics)
  {
    if ((this.chart_img == null) || (!this.prev_size.equals(getSize())) || (this.redraw_backing))
    {
      if (this.chart_img == null) {
        realloc();
      }
      this.chart_img = createImage(getSize().width, getSize().height);
      this.chart_gfx = this.chart_img.getGraphics();
      this.chart_gfx.setFont(getFont());
      this.chart_gfx.drawImage(this.graphpaper_img, 0, 0, Color.white, null);
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
      for (int i6 = 0; i6 < 32; i6++) {
        this.values_[i6].draw(this.chart_gfx);
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
            i6 = Math.min(3, str.length());
          } else {
            i6 = Math.min(2, str.length());
          }
          str = str.substring(0, i6);
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
    for (int n = 0; n < 32; n++) {
      this.values_[n].setX((int)(n * (this.chart_width / 31.0D)));
    }
  }
  
  public void changeValues()
  {
    if ((this.graphpaper_gfx == null) || (this.chart_gfx == null))
    {
      System.err.println("Internal error: Graphics Contexts not initialized in CTFunctionWindow.changeValues()");
      return;
    }
    this.chart_gfx.drawImage(this.graphpaper_img, 0, 0, Color.white, null);
    for (int i = 0; i < 32; i++) {
      this.values_[i].draw(this.chart_gfx);
    }
    repaint();
  }
  
  public DValue getDValue(int paramInt)
  {
    return this.values_[paramInt];
  }
  
  public int XPixToCht(int paramInt)
  {
    return paramInt - this.chart_x;
  }
  
  public int XChtToPix(int paramInt)
  {
    return paramInt + this.chart_x;
  }
  
  public double YPixToCht(int paramInt)
  {
    return (this.chart_y + this.chart_height - paramInt) * (this.y_end - this.y_start) / (this.chart_height * 1.0D) + this.y_start;
  }
  
  public int YChtToPix(double paramDouble)
  {
    return (int)(this.chart_y + this.chart_height - (paramDouble - this.y_start) * (this.chart_height / (this.y_end - this.y_start)));
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {}
  
  public void mouseEntered(MouseEvent paramMouseEvent) {}
  
  public void mouseExited(MouseEvent paramMouseEvent) {}
  
  public void mousePressed(MouseEvent paramMouseEvent)
  {
    DValue localDValue = null;
    for (int i = 0; i < 32; i++) {
      if (this.values_[i].intersects(paramMouseEvent)) {
        localDValue = this.values_[i];
      }
    }
    if (localDValue != null)
    {
      this.drag_value = localDValue;
      this.function_controller.beginUpdate(localDValue);
      localDValue.beginDrag(paramMouseEvent);
    }
  }
  
  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    if (this.drag_value != null)
    {
      this.drag_value.endDrag();
      this.drag_value = null;
    }
  }
  
  public void mouseMoved(MouseEvent paramMouseEvent)
  {
    boolean bool = false;
    int i = -1;
    for (int j = 0; j < 32; j++) {
      if (this.values_[j].intersects(paramMouseEvent))
      {
        bool = true;
        i = j;
      }
    }
    if ((bool) || (this.was_foo)) {
      changeValues();
    }
    if ((!bool) && (this.was_foo)) {
      ((SampDTFunctionController)this.function_controller).unsetHilight(true);
    } else if (i != this.oldloc_) {
      ((SampDTFunctionController)this.function_controller).setHilight(i, true);
    }
    this.was_foo = bool;
    this.oldloc_ = i;
  }
  
  public void mouseDragged(MouseEvent paramMouseEvent) {}
  
  public void valueUpdate()
  {
    this.function_controller.valueUpdate();
  }
  
  public void componentResized(ComponentEvent paramComponentEvent) {}
  
  public void componentMoved(ComponentEvent paramComponentEvent) {}
  
  public void componentShown(ComponentEvent paramComponentEvent) {}
  
  public void componentHidden(ComponentEvent paramComponentEvent) {}
  
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
  }
  
  public void setTitle(String paramString)
  {
    this.border_.setTitle(paramString);
  }
  
  public String getTitle()
  {
    return this.border_.getTitle();
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
}


/* Location:              /Users/masonbartle/Downloads/introduction_to_sampling.jar!/edu/brown/cs/exploratories/applets/sampling/DTFunctionWindow.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */