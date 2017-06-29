package edu.brown.cs.exploratories.applets.fft1DApp;

import java.awt.Color;

public class ScanlineDTFunctionController
  implements DTFunctionController
{
  protected FFT1DApp app_;
  protected FourierDTFunctionController fcontroller_;
  protected DTFunctionWindow function_window;
  protected int num_sample_colors = 1;
  protected Color[] sample_colors = { Color.blue };
  
  public ScanlineDTFunctionController(FFT1DApp paramFFT1DApp)
  {
    this.app_ = paramFFT1DApp;
  }
  
  public void setFunctionWindow(DTFunctionWindow paramDTFunctionWindow)
  {
    this.function_window = paramDTFunctionWindow;
  }
  
  public void setFController(FourierDTFunctionController paramFourierDTFunctionController)
  {
    this.fcontroller_ = paramFourierDTFunctionController;
  }
  
  public void update(int paramInt, double[] paramArrayOfDouble)
  {
    for (int i = 0; i < paramInt; i++)
    {
      DValue localDValue = this.function_window.getDValue(i);
      localDValue.setValue(paramArrayOfDouble[i] / 255.0D);
    }
    this.function_window.changeValues();
    this.fcontroller_.update(paramInt, paramArrayOfDouble);
  }
  
  public void resize(int paramInt1, int paramInt2) {}
  
  public void beginUpdate(DValue paramDValue) {}
  
  public void valueUpdate() {}
  
  public void endUpdate() {}
}


/* Location:              /Users/masonbartle/Downloads/1d_fast_fourier_transform.jar!/edu/brown/cs/exploratories/applets/fft1DApp/ScanlineDTFunctionController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */