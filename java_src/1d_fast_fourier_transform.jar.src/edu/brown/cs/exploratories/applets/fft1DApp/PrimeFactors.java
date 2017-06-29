package edu.brown.cs.exploratories.applets.fft1DApp;

public class PrimeFactors
{
  public static int[] primefactors(int paramInt, int[] paramArrayOfInt)
  {
    int[] arrayOfInt1 = new int[64];
    int i = 0;
    int j = paramInt;
    for (int m = 0; (m < paramArrayOfInt.length) && (j != 1); m++)
    {
      k = paramArrayOfInt[m];
      while (j % k == 0)
      {
        j /= k;
        arrayOfInt1[(i++)] = k;
      }
    }
    int k = 2;
    while ((j % k == 0) && (j != 1))
    {
      j /= k;
      arrayOfInt1[(i++)] = k;
    }
    k = 3;
    while (j != 1)
    {
      while (j % k != 0) {
        k += 2;
      }
      j /= k;
      arrayOfInt1[(i++)] = k;
    }
    int n = 1;
    for (int i1 = 0; i1 < i; i1++) {
      n *= arrayOfInt1[i1];
    }
    if (n != paramInt) {
      throw new Error("factorization failed for " + paramInt);
    }
    int[] arrayOfInt2 = new int[i];
    System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 0, i);
    return arrayOfInt2;
  }
  
  public static int log2(int paramInt)
  {
    int i = 0;
    int j = 1;
    while (j < paramInt)
    {
      j *= 2;
      i++;
    }
    if (paramInt != 1 << i) {
      return -1;
    }
    return i;
  }
}


/* Location:              /Users/masonbartle/Downloads/1d_fast_fourier_transform.jar!/edu/brown/cs/exploratories/applets/fft1DApp/PrimeFactors.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */