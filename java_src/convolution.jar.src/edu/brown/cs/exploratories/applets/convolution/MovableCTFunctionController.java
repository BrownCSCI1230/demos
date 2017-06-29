package edu.brown.cs.exploratories.applets.convolution;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MovableCTFunctionController
  extends EditableCTFunctionController
  implements ChangeListener
{
  protected transient int x_offset = 0;
  protected transient JSlider j_slider = null;
  
  public void mouseStart(int paramInt1, int paramInt2)
  {
    super.mouseStart(paramInt1, paramInt2);
  }
  
  public void resize(int paramInt1, int paramInt2)
  {
    super.resize(paramInt1, paramInt2);
  }
  
  public void stateChanged(ChangeEvent paramChangeEvent)
  {
    JSlider localJSlider = (JSlider)paramChangeEvent.getSource();
    int i = localJSlider.getValue();
    if (i == this.x_offset) {
      return;
    }
    this.x_offset = i;
    String str = "g(x";
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
    double[] arrayOfDouble = new double[this.chart_width];
    int k;
    if (i < 0) {
      for (k = 0; k < this.chart_width + i; k++) {
        arrayOfDouble[k] = this.chart_values[(k - i)];
      }
    } else {
      for (k = 0; k < this.chart_width - i; k++) {
        arrayOfDouble[(k + i)] = this.chart_values[k];
      }
    }
    this.function_window.changeValues(0, this.chart_width, arrayOfDouble);
    if (this.prod_ctrl != null) {
      this.prod_ctrl.recompute(0, this.chart_width);
    }
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
  
  public int getOffset()
  {
    return this.x_offset;
  }
}


/* Location:              /Users/masonbartle/Downloads/convolution.jar!/edu/brown/cs/exploratories/applets/convolution/MovableCTFunctionController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */