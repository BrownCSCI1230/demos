package edu.brown.cs.exploratories.applets.twoBoxConvolution;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.PrintStream;
import java.io.Serializable;

public class ConvDBFunctionController
  implements CTFunctionController, Runnable, Serializable, PropertyChangeListener
{
  protected transient int chart_width;
  protected transient double[] chart_values;
  protected transient int mask_min;
  protected transient int mask_max;
  protected transient Thread worker_thread = null;
  protected transient CTFunctionWindow function_window;
  protected transient MovableDBFunctionController func_f;
  
  public void setFunctionWindow(CTFunctionWindow paramCTFunctionWindow)
  {
    this.function_window = paramCTFunctionWindow;
  }
  
  public void recompute()
  {
    this.mask_min = 0;
    this.mask_max = 0;
    for (int i = 0; i < this.chart_width; i++) {
      this.chart_values[i] = 0.0D;
    }
    this.function_window.changeValues(0, this.chart_width, this.chart_values);
    ConvolutionSlide.__instance.setSlidable(false);
  }
  
  public void run()
  {
    int i = this.chart_width / 2;
    try
    {
      for (int j = -i; j < i; j++)
      {
        if (Thread.interrupted()) {
          throw new InterruptedException();
        }
        for (int k = 0; k < this.chart_width; k++) {
          if ((k - j <= 0) || (k - j >= this.chart_width)) {}
        }
      }
      double d = (this.function_window.getXEnd() - this.function_window.getXStart()) / (this.chart_width * 1.0D);
      for (int m = 0; m < this.chart_width; m++) {
        this.chart_values[m] *= d;
      }
      finishRun();
    }
    catch (InterruptedException localInterruptedException)
    {
      System.out.println("Interrupted exception caught");
    }
  }
  
  public void reveal(int paramInt)
  {
    paramInt += this.chart_width / 2;
    if (paramInt < this.mask_min) {
      this.mask_min = paramInt;
    }
    if (paramInt > this.mask_max) {
      this.mask_max = paramInt;
    }
    double d1 = this.function_window.getYEnd();
    double d2 = this.function_window.getYStart();
    int i = this.mask_max - this.mask_min;
    double[] arrayOfDouble = new double[i];
    for (int j = 0; j < i; j++) {
      arrayOfDouble[j] = this.func_f.getOverlap(j + this.mask_min);
    }
    this.function_window.changeValues(this.mask_min, i, arrayOfDouble);
    this.function_window.setMarker(paramInt);
  }
  
  public void resetPosition()
  {
    this.mask_min = 0;
    this.mask_max = 0;
    this.function_window.setMarker(-1);
    double[] arrayOfDouble = new double[this.chart_width];
    for (int i = 0; i < this.chart_width; i++) {
      arrayOfDouble[i] = 0.0D;
    }
    this.function_window.changeValues(0, this.chart_width, arrayOfDouble);
  }
  
  protected void invalidate()
  {
    if (this.worker_thread != null) {
      this.worker_thread.interrupt();
    }
  }
  
  private final void finishRun()
  {
    ConvolutionSlide.__instance.setSlidable(true);
  }
  
  public void resize(int paramInt1, int paramInt2)
  {
    int i = paramInt1;
    if (this.chart_width != i)
    {
      this.chart_values = new double[i];
      this.mask_min = 0;
      this.mask_max = 0;
    }
    this.chart_width = i;
  }
  
  public void setController(MovableDBFunctionController paramMovableDBFunctionController)
  {
    this.func_f = paramMovableDBFunctionController;
    paramMovableDBFunctionController.setConvController(this);
  }
  
  public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {}
  
  public void mouseStart(int paramInt1, int paramInt2) {}
  
  public void mouseDrag(int paramInt1, int paramInt2) {}
  
  public void mouseStop() {}
}


/* Location:              /Users/masonbartle/Downloads/two_box_convolution.jar!/edu/brown/cs/exploratories/applets/twoBoxConvolution/ConvDBFunctionController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */