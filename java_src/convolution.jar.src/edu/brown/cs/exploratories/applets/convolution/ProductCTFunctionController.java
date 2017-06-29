package edu.brown.cs.exploratories.applets.convolution;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;

public class ProductCTFunctionController
  implements CTFunctionController, Serializable, PropertyChangeListener
{
  protected transient CTFunctionWindow function_window;
  protected transient EditableCTFunctionController func_f;
  protected transient MovableCTFunctionController func_g;
  
  public void setFunctionWindow(CTFunctionWindow paramCTFunctionWindow)
  {
    this.function_window = paramCTFunctionWindow;
  }
  
  public void recompute(int paramInt1, int paramInt2)
  {
    double[] arrayOfDouble1 = new double[paramInt2];
    double[] arrayOfDouble2 = this.func_f.getChartValues();
    double[] arrayOfDouble3 = this.func_g.getChartValues();
    int i = this.func_g.getOffset();
    int j;
    if (i == 0)
    {
      for (j = 0; j < paramInt2; j++) {
        arrayOfDouble1[j] = (arrayOfDouble2[(paramInt1 + j)] * arrayOfDouble3[(paramInt1 + j)]);
      }
      this.function_window.changeValues(paramInt1, paramInt2, arrayOfDouble1);
    }
    else if (i > 0)
    {
      for (j = i; j < paramInt2; j++) {
        arrayOfDouble1[j] = (arrayOfDouble2[(paramInt1 + j)] * arrayOfDouble3[(paramInt1 + j - i)]);
      }
      this.function_window.changeValues(paramInt1, paramInt2, arrayOfDouble1);
    }
    else
    {
      j = paramInt2 + i;
      for (int k = 0; k < j; k++) {
        arrayOfDouble1[k] = (arrayOfDouble2[(paramInt1 + k)] * arrayOfDouble3[(paramInt1 + k - i)]);
      }
      this.function_window.changeValues(paramInt1, paramInt2, arrayOfDouble1);
    }
  }
  
  public void resetPosition()
  {
    int i = this.function_window.getChartWidth();
    double[] arrayOfDouble = new double[i];
    for (int j = 0; j < i; j++) {
      arrayOfDouble[j] = 0.0D;
    }
    this.function_window.changeValues(0, i, arrayOfDouble);
  }
  
  public void setF(EditableCTFunctionController paramEditableCTFunctionController)
  {
    this.func_f = paramEditableCTFunctionController;
    paramEditableCTFunctionController.setProductController(this);
  }
  
  public void setG(MovableCTFunctionController paramMovableCTFunctionController)
  {
    this.func_g = paramMovableCTFunctionController;
    paramMovableCTFunctionController.setProductController(this);
  }
  
  public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {}
  
  public void mouseStart(int paramInt1, int paramInt2) {}
  
  public void mouseDrag(int paramInt1, int paramInt2) {}
  
  public void mouseStop() {}
  
  public void resize(int paramInt1, int paramInt2) {}
}


/* Location:              /Users/masonbartle/Downloads/convolution.jar!/edu/brown/cs/exploratories/applets/convolution/ProductCTFunctionController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */