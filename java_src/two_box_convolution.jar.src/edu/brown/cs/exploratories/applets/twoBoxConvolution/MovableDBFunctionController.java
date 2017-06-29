package edu.brown.cs.exploratories.applets.twoBoxConvolution;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MovableDBFunctionController
  implements DBFunctionController, Serializable, PropertyChangeListener, ChangeListener
{
  protected transient int x_offset = 0;
  protected transient JSlider j_slider = null;
  protected transient double scale_factor;
  protected transient int chart_width;
  protected transient int last_x = -1;
  protected transient int last_y = -1;
  protected transient double[] chart_values;
  protected transient DBFunctionWindow function_window;
  protected transient ConvDBFunctionController conv_ctrl = null;
  
  public void setFunctionWindow(DBFunctionWindow paramDBFunctionWindow)
  {
    this.function_window = paramDBFunctionWindow;
    this.chart_width = this.function_window.getChartWidth();
    this.chart_values = new double[this.chart_width];
  }
  
  public void stateChanged(ChangeEvent paramChangeEvent)
  {
    JSlider localJSlider = (JSlider)paramChangeEvent.getSource();
    int i = localJSlider.getValue();
    if (i == this.x_offset) {
      return;
    }
    this.x_offset = i;
    String str = "f(x)g(x";
    double d1 = this.function_window.getXStart();
    double d2 = this.function_window.getXEnd();
    int j = this.function_window.getChartWidth();
    double d3 = this.x_offset * (d2 - d1) / (j * 1.0D);
    if (d3 < 0.0D)
    {
      str = str + "+";
      str = str + Math.abs(d3);
    }
    else if (d3 > 0.0D)
    {
      str = str + "-";
      str = str + d3;
    }
    str = str.substring(0, Math.min(9, str.length()));
    str = str + ") [Convolution Filter]";
    this.function_window.setTitle(str);
    this.function_window.changeLoc(this.x_offset);
    if (this.conv_ctrl != null) {
      this.conv_ctrl.reveal(this.x_offset);
    }
  }
  
  public void resetPosition()
  {
    if (0 == this.x_offset) {
      return;
    }
    this.x_offset = 0;
    this.j_slider.setValue(0);
    this.function_window.setTitle("g(x)");
    this.function_window.changeValues(0, this.chart_width, this.chart_values);
  }
  
  public void setSlider(JSlider paramJSlider)
  {
    this.j_slider = paramJSlider;
    this.j_slider.addChangeListener(this);
  }
  
  public void setConvController(ConvDBFunctionController paramConvDBFunctionController)
  {
    this.conv_ctrl = paramConvDBFunctionController;
  }
  
  public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {}
  
  public double getOverlap(int paramInt)
  {
    return this.function_window.getOverlap(paramInt);
  }
  
  public void mouseStart(int paramInt1, int paramInt2)
  {
    this.last_x = paramInt1;
    this.last_y = paramInt2;
    if (this.conv_ctrl != null) {
      this.conv_ctrl.invalidate();
    }
    mouseDrag(paramInt1, paramInt2);
  }
  
  public void mouseDrag(int paramInt1, int paramInt2)
  {
    double d1 = this.function_window.getYStart();
    double d2 = this.function_window.getYEnd();
    double d3 = (d2 - d1) / (this.function_window.getChartHeight() * 1.0D);
    int i = Math.abs(paramInt1 - this.last_x);
    int j;
    double[] arrayOfDouble;
    if (0 == i)
    {
      i = 1;
      j = paramInt1;
      arrayOfDouble = new double[1];
      arrayOfDouble[0] = (paramInt2 * d3 + d1);
      this.chart_values[j] = arrayOfDouble[0];
    }
    else
    {
      if (paramInt1 < this.last_x) {
        j = paramInt1;
      } else {
        j = paramInt1 - i + 1;
      }
      arrayOfDouble = new double[i];
      double d4;
      double d5;
      if (paramInt1 > this.last_x)
      {
        d4 = this.last_y * 1.0D;
        d5 = paramInt2 * 1.0D;
      }
      else
      {
        d5 = this.last_y * 1.0D;
        d4 = paramInt2 * 1.0D;
      }
      for (int k = 0; k < i; k++)
      {
        double d6 = (k + 1) / (i * 1.0D);
        arrayOfDouble[k] = (d3 * ((1.0D - d6) * d4 + d6 * d5) + d1);
        this.chart_values[(j + k)] = arrayOfDouble[k];
      }
    }
    this.function_window.changeValues(j, i, arrayOfDouble);
    this.last_x = paramInt1;
    this.last_y = paramInt2;
  }
  
  public void mouseStop()
  {
    if (this.conv_ctrl != null) {
      this.conv_ctrl.recompute();
    }
  }
  
  public void resize(int paramInt1, int paramInt2)
  {
    int i = paramInt1;
    if (this.chart_width != i) {
      this.chart_values = new double[i];
    }
    this.chart_width = i;
  }
  
  public int getOffset()
  {
    return this.x_offset;
  }
}


/* Location:              /Users/masonbartle/Downloads/two_box_convolution.jar!/edu/brown/cs/exploratories/applets/twoBoxConvolution/MovableDBFunctionController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */