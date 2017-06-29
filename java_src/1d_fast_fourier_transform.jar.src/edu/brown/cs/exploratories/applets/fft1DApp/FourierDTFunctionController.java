package edu.brown.cs.exploratories.applets.fft1DApp;

import java.awt.Color;

public class FourierDTFunctionController
  implements DTFunctionController
{
  protected FFT1DApp app_;
  protected FFT fft_;
  protected DTFunctionWindow function_window;
  protected int num_sample_colors = 1;
  protected Color[] sample_colors = { Color.blue };
  
  public FourierDTFunctionController(FFT1DApp paramFFT1DApp)
  {
    this.app_ = paramFFT1DApp;
    this.fft_ = new FFT2(256);
  }
  
  public void setFunctionWindow(DTFunctionWindow paramDTFunctionWindow)
  {
    this.function_window = paramDTFunctionWindow;
  }
  
  public void update(int paramInt, double[] paramArrayOfDouble)
  {
    float[] arrayOfFloat1 = new float['Ā'];
    for (int i = 0; i < 256; i++) {
      arrayOfFloat1[i] = ((float)(paramArrayOfDouble[i] / 255.0D));
    }
    this.fft_.transform(arrayOfFloat1);
    float[] arrayOfFloat2 = new float['Ā'];
    arrayOfFloat2[0] = (arrayOfFloat2[1] = (float)(paramArrayOfDouble[0] / 32.0D));
    if (arrayOfFloat2[0] > 1.0D) {
      arrayOfFloat2[0] = (arrayOfFloat2[1] = 1.0F);
    }
    for (int j = 1; j < 128; j++) {
      arrayOfFloat2[(2 * j + 1)] = (arrayOfFloat2[(2 * j)] = (float)(Math.sqrt(arrayOfFloat1[j] * arrayOfFloat1[j] + arrayOfFloat1[(256 - j)] * arrayOfFloat1[(256 - j)]) / 32.0D));
    }
    for (int k = 0; k < paramInt; k++)
    {
      DValue localDValue = this.function_window.getDValue(k);
      localDValue.setValue(arrayOfFloat2[k]);
    }
    this.function_window.changeValues();
  }
  
  public void resize(int paramInt1, int paramInt2) {}
  
  public void beginUpdate(DValue paramDValue) {}
  
  public void valueUpdate() {}
  
  public void endUpdate() {}
}


/* Location:              /Users/masonbartle/Downloads/1d_fast_fourier_transform.jar!/edu/brown/cs/exploratories/applets/fft1DApp/FourierDTFunctionController.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */