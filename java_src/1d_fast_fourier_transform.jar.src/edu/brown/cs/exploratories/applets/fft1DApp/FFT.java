package edu.brown.cs.exploratories.applets.fft1DApp;

public abstract class FFT
{
  int n;
  
  public FFT(int paramInt)
  {
    if (paramInt <= 0) {
      throw new IllegalArgumentException("The transform length must be >=0 : " + paramInt);
    }
    this.n = paramInt;
  }
  
  protected void checkData(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
  {
    if (paramInt1 < 0) {
      throw new IllegalArgumentException("The offset must be >=0 : " + paramInt1);
    }
    if (paramInt2 < 1) {
      throw new IllegalArgumentException("The stride must be >=1 : " + paramInt2);
    }
    if (paramInt1 + paramInt2 * (this.n - 1) + 1 > paramArrayOfFloat.length) {
      throw new IllegalArgumentException("The data array is too small for " + this.n + ":" + "i0=" + paramInt1 + " stride=" + paramInt2 + " data.length=" + paramArrayOfFloat.length);
    }
  }
  
  public void transform(float[] paramArrayOfFloat)
  {
    transform(paramArrayOfFloat, 0, 1);
  }
  
  public abstract void transform(float[] paramArrayOfFloat, int paramInt1, int paramInt2);
  
  public float[] toWraparoundOrder(float[] paramArrayOfFloat)
  {
    return toWraparoundOrder(paramArrayOfFloat, 0, 1);
  }
  
  public abstract float[] toWraparoundOrder(float[] paramArrayOfFloat, int paramInt1, int paramInt2);
  
  public void backtransform(float[] paramArrayOfFloat)
  {
    backtransform(paramArrayOfFloat, 0, 1);
  }
  
  public abstract void backtransform(float[] paramArrayOfFloat, int paramInt1, int paramInt2);
  
  public float normalization()
  {
    return 1.0F / this.n;
  }
  
  public void inverse(float[] paramArrayOfFloat)
  {
    inverse(paramArrayOfFloat, 0, 1);
  }
  
  public void inverse(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
  {
    backtransform(paramArrayOfFloat, paramInt1, paramInt2);
    float f = normalization();
    for (int i = 0; i < this.n; i++) {
      paramArrayOfFloat[(paramInt1 + paramInt2 * i)] *= f;
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/1d_fast_fourier_transform.jar!/edu/brown/cs/exploratories/applets/fft1DApp/FFT.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */