package edu.brown.cs.exploratories.applets.fft1DApp;

public class FFT2
  extends FFT
{
  private int logn;
  
  public FFT2(int paramInt)
  {
    super(paramInt);
    this.logn = PrimeFactors.log2(paramInt);
    if (this.logn < 0) {
      throw new IllegalArgumentException(paramInt + " is not a power of 2");
    }
  }
  
  public void transform(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
  {
    checkData(paramArrayOfFloat, paramInt1, paramInt2);
    if (this.n == 1) {
      return;
    }
    bitreverse(paramArrayOfFloat, paramInt1, paramInt2);
    int i = 1;
    int k = this.n;
    for (int m = 1; m <= this.logn; m++)
    {
      int j = i;
      i = 2 * i;
      k /= 2;
      for (int i1 = 0; i1 < k; i1++)
      {
        f1 = paramArrayOfFloat[(paramInt1 + paramInt2 * i1 * i)] + paramArrayOfFloat[(paramInt1 + paramInt2 * (i1 * i + j))];
        f2 = paramArrayOfFloat[(paramInt1 + paramInt2 * i1 * i)] - paramArrayOfFloat[(paramInt1 + paramInt2 * (i1 * i + j))];
        paramArrayOfFloat[(paramInt1 + paramInt2 * i1 * i)] = f1;
        paramArrayOfFloat[(paramInt1 + paramInt2 * (i1 * i + j))] = f2;
      }
      float f1 = 1.0F;
      float f2 = 0.0F;
      double d = -6.283185307179586D / i;
      float f3 = (float)Math.sin(d);
      float f4 = (float)Math.sin(d / 2.0D);
      float f5 = 2.0F * f4 * f4;
      for (int n = 1; n < j / 2; n++)
      {
        float f6 = f1 - f3 * f2 - f5 * f1;
        float f7 = f2 + f3 * f1 - f5 * f2;
        f1 = f6;
        f2 = f7;
        for (i1 = 0; i1 < k; i1++)
        {
          f6 = paramArrayOfFloat[(paramInt1 + paramInt2 * (i1 * i + n))];
          f7 = paramArrayOfFloat[(paramInt1 + paramInt2 * (i1 * i + j - n))];
          float f8 = paramArrayOfFloat[(paramInt1 + paramInt2 * (i1 * i + j + n))];
          float f9 = paramArrayOfFloat[(paramInt1 + paramInt2 * (i1 * i + i - n))];
          paramArrayOfFloat[(paramInt1 + paramInt2 * (i1 * i + n))] = (f6 + f1 * f8 - f2 * f9);
          paramArrayOfFloat[(paramInt1 + paramInt2 * (i1 * i + i - n))] = (f7 + f1 * f9 + f2 * f8);
          paramArrayOfFloat[(paramInt1 + paramInt2 * (i1 * i + j - n))] = (f6 - f1 * f8 + f2 * f9);
          paramArrayOfFloat[(paramInt1 + paramInt2 * (i1 * i + j + n))] = (-(f7 - f1 * f9 - f2 * f8));
        }
      }
      if (j > 1) {
        for (i1 = 0; i1 < k; i1++) {
          paramArrayOfFloat[(paramInt1 + paramInt2 * (i1 * i + i - j / 2))] *= -1.0F;
        }
      }
    }
  }
  
  public void backtransform(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
  {
    checkData(paramArrayOfFloat, paramInt1, paramInt2);
    if (this.n == 1) {
      return;
    }
    int i = this.n;
    int k = 1;
    int j = this.n / 2;
    for (int m = 1; m <= this.logn; m++)
    {
      for (int i1 = 0; i1 < k; i1++)
      {
        f1 = paramArrayOfFloat[(paramInt1 + paramInt2 * i1 * i)];
        f2 = paramArrayOfFloat[(paramInt1 + paramInt2 * (i1 * i + j))];
        paramArrayOfFloat[(paramInt1 + paramInt2 * i1 * i)] = (f1 + f2);
        paramArrayOfFloat[(paramInt1 + paramInt2 * (i1 * i + j))] = (f1 - f2);
      }
      float f1 = 1.0F;
      float f2 = 0.0F;
      double d = 6.283185307179586D / i;
      float f3 = (float)Math.sin(d);
      float f4 = (float)Math.sin(d / 2.0D);
      float f5 = 2.0F * f4 * f4;
      for (int n = 1; n < j / 2; n++)
      {
        float f6 = f1 - f3 * f2 - f5 * f1;
        float f7 = f2 + f3 * f1 - f5 * f2;
        f1 = f6;
        f2 = f7;
        for (i1 = 0; i1 < k; i1++)
        {
          float f8 = paramArrayOfFloat[(paramInt1 + paramInt2 * (i1 * i + n))];
          float f9 = paramArrayOfFloat[(paramInt1 + paramInt2 * (i1 * i + i - n))];
          float f10 = paramArrayOfFloat[(paramInt1 + paramInt2 * (i1 * i + j - n))];
          float f11 = -paramArrayOfFloat[(paramInt1 + paramInt2 * (i1 * i + j + n))];
          paramArrayOfFloat[(paramInt1 + paramInt2 * (i1 * i + n))] = (f8 + f10);
          paramArrayOfFloat[(paramInt1 + paramInt2 * (i1 * i + j - n))] = (f9 + f11);
          float f12 = f8 - f10;
          float f13 = f9 - f11;
          paramArrayOfFloat[(paramInt1 + paramInt2 * (i1 * i + j + n))] = (f1 * f12 - f2 * f13);
          paramArrayOfFloat[(paramInt1 + paramInt2 * (i1 * i + i - n))] = (f1 * f13 + f2 * f12);
        }
      }
      if (j > 1) {
        for (i1 = 0; i1 < k; i1++)
        {
          paramArrayOfFloat[(paramInt1 + paramInt2 * (i1 * i + j / 2))] *= 2.0F;
          paramArrayOfFloat[(paramInt1 + paramInt2 * (i1 * i + j + j / 2))] *= -2.0F;
        }
      }
      j /= 2;
      i /= 2;
      k *= 2;
    }
    bitreverse(paramArrayOfFloat, paramInt1, paramInt2);
  }
  
  public float[] toWraparoundOrder(float[] paramArrayOfFloat)
  {
    return toWraparoundOrder(paramArrayOfFloat, 0, 1);
  }
  
  public float[] toWraparoundOrder(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
  {
    checkData(paramArrayOfFloat, paramInt1, paramInt2);
    float[] arrayOfFloat = new float[2 * this.n];
    int i = this.n / 2;
    arrayOfFloat[0] = paramArrayOfFloat[paramInt1];
    arrayOfFloat[1] = 0.0F;
    arrayOfFloat[this.n] = paramArrayOfFloat[(paramInt1 + paramInt2 * i)];
    arrayOfFloat[(this.n + 1)] = 0.0F;
    for (int j = 1; j < i; j++)
    {
      arrayOfFloat[(2 * j)] = paramArrayOfFloat[(paramInt1 + paramInt2 * j)];
      arrayOfFloat[(2 * j + 1)] = paramArrayOfFloat[(paramInt1 + paramInt2 * (this.n - j))];
      arrayOfFloat[(2 * (this.n - j))] = paramArrayOfFloat[(paramInt1 + paramInt2 * j)];
      arrayOfFloat[(2 * (this.n - j) + 1)] = (-paramArrayOfFloat[(paramInt1 + paramInt2 * (this.n - j))]);
    }
    return arrayOfFloat;
  }
  
  protected void bitreverse(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
  {
    int i = 0;
    int j = 0;
    while (i < this.n - 1)
    {
      int k = this.n / 2;
      if (i < j)
      {
        float f = paramArrayOfFloat[(paramInt1 + paramInt2 * i)];
        paramArrayOfFloat[(paramInt1 + paramInt2 * i)] = paramArrayOfFloat[(paramInt1 + paramInt2 * j)];
        paramArrayOfFloat[(paramInt1 + paramInt2 * j)] = f;
      }
      while (k <= j)
      {
        j -= k;
        k /= 2;
      }
      j += k;
      i++;
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/1d_fast_fourier_transform.jar!/edu/brown/cs/exploratories/applets/fft1DApp/FFT2.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */