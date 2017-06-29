package edu.brown.cs.exploratories.applets.convolution;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;

public class EditableCTFunctionController
  implements CTFunctionController, Serializable, PropertyChangeListener
{
  protected transient double scale_factor;
  protected transient int chart_width;
  protected transient int last_x = -1;
  protected transient int last_y = -1;
  protected transient double[] chart_values;
  protected transient CTFunctionWindow function_window;
  protected transient ProductCTFunctionController prod_ctrl = null;
  protected transient ConvCTFunctionController conv_ctrl = null;
  
  public void setFunctionWindow(CTFunctionWindow paramCTFunctionWindow)
  {
    this.function_window = paramCTFunctionWindow;
    this.chart_width = this.function_window.getChartWidth();
    this.chart_values = new double[this.chart_width];
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
    if (this.prod_ctrl != null) {
      this.prod_ctrl.recompute(j, i);
    }
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
  
  public void normalize()
  {
    ConvolutionSlide.__instance.resetPositions();
    double d1 = 0.0D;
    for (int i = 0; i < this.chart_width; i++) {
      d1 += this.chart_values[i];
    }
    double d2 = this.function_window.getXStart();
    double d3 = this.function_window.getXEnd();
    d1 *= (d3 - d2) / (this.chart_width * 1.0D);
    if (d1 < 1.0E-5D) {
      return;
    }
    for (int j = 0; j < this.chart_width; j++) {
      this.chart_values[j] /= d1;
    }
    this.function_window.changeValues(0, this.chart_width, this.chart_values);
    if (this.prod_ctrl != null) {
      this.prod_ctrl.recompute(0, this.chart_width);
    }
    if (this.conv_ctrl != null) {
      this.conv_ctrl.recompute();
    }
  }
  
  public void clear()
  {
    ConvolutionSlide.__instance.resetPositions();
    for (int i = 0; i < this.chart_width; i++) {
      this.chart_values[i] = 0.0D;
    }
    this.function_window.changeValues(0, this.chart_width, this.chart_values);
    if (this.prod_ctrl != null) {
      this.prod_ctrl.recompute(0, this.chart_width);
    }
    if (this.conv_ctrl != null) {
      this.conv_ctrl.recompute();
    }
  }
  
  public void fromFunction(Function paramFunction)
  {
    ConvolutionSlide.__instance.resetPositions();
    double d1 = this.function_window.getXStart();
    double d2 = this.function_window.getXEnd();
    for (int i = 0; i < this.chart_width; i++) {
      this.chart_values[i] = paramFunction.value((d2 - d1) / this.chart_width * i + d1);
    }
    this.function_window.changeValues(0, this.chart_width, this.chart_values);
    if (this.prod_ctrl != null) {
      this.prod_ctrl.recompute(0, this.chart_width);
    }
    if (this.conv_ctrl != null) {
      this.conv_ctrl.recompute();
    }
  }
  
  public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {}
  
  public void setProductController(ProductCTFunctionController paramProductCTFunctionController)
  {
    this.prod_ctrl = paramProductCTFunctionController;
  }
  
  public void setConvController(ConvCTFunctionController paramConvCTFunctionController)
  {
    this.conv_ctrl = paramConvCTFunctionController;
  }
  
  public int getChartWidth()
  {
    return this.chart_width;
  }
  
  public double[] getChartValues()
  {
    return this.chart_values;
  }
}


/* Location:              /Users/masonbartle/Downloads/convolution.jar!/edu/brown/cs/exploratories/applets/convolution/EditableCTFunctionController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */