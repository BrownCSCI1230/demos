package edu.brown.cs.exploratories.applets.filterShape;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class ScaleEngine
{
  public static BufferedImage scale(BufferedImage paramBufferedImage, Filter paramFilter, Dimension paramDimension)
  {
    if ((paramBufferedImage == null) || (paramFilter == null) || (paramDimension == null)) {
      throw new NullPointerException();
    }
    int i = paramBufferedImage.getWidth();
    int j = paramBufferedImage.getHeight();
    int k = paramDimension.width;
    int m = paramDimension.height;
    double d1 = k / i;
    double d2 = m / j;
    BufferedImage localBufferedImage1 = null;
    BufferedImage localBufferedImage2 = null;
    if (d1 > d2)
    {
      localBufferedImage2 = scaleWidth(paramBufferedImage, paramFilter, k);
      localBufferedImage1 = scaleHeight(localBufferedImage2, paramFilter, m);
    }
    else
    {
      localBufferedImage2 = scaleHeight(paramBufferedImage, paramFilter, m);
      localBufferedImage1 = scaleWidth(localBufferedImage2, paramFilter, k);
    }
    return localBufferedImage1;
  }
  
  public static BufferedImage scaleWidth(BufferedImage paramBufferedImage, Filter paramFilter, int paramInt)
  {
    if ((paramBufferedImage == null) || (paramFilter == null)) {
      throw new NullPointerException();
    }
    int i = paramBufferedImage.getWidth();
    int j = paramBufferedImage.getHeight();
    BufferedImage localBufferedImage = new BufferedImage(paramInt, j, 1);
    WritableRaster localWritableRaster1 = paramBufferedImage.getRaster();
    WritableRaster localWritableRaster2 = localBufferedImage.getRaster();
    double d1 = paramFilter.getArea();
    double d2 = paramInt / i;
    double d3 = d2 < 1.0D ? paramFilter.getWidth() / d2 : paramFilter.getWidth();
    double[] arrayOfDouble = new double[4];
    for (int k = 0; k < j; k++) {
      for (int m = 0; m < paramInt; m++)
      {
        double d4 = m / d2;
        int n = (int)Math.floor(d4 - d3 / 2.0D);
        int i1 = (int)Math.ceil(d4 + d3 / 2.0D);
        double d5 = 0.0D;
        double d6 = 0.0D;
        double d7 = 0.0D;
        double d8 = 0.0D;
        for (int i2 = n; i2 < i1; i2++)
        {
          if ((i2 >= 0) && (i2 < i))
          {
            double d9 = paramFilter.getValue(d4 - i2);
            arrayOfDouble = localWritableRaster1.getPixel(i2, k, arrayOfDouble);
            d5 += d9 * arrayOfDouble[0];
            d6 += d9 * arrayOfDouble[1];
            d7 += d9 * arrayOfDouble[2];
            d8 += d9;
          }
          d8 /= d1;
          arrayOfDouble[0] = (d5 / d8);
          arrayOfDouble[1] = (d6 / d8);
          arrayOfDouble[2] = (d7 / d8);
          if (arrayOfDouble[0] > 255.0D) {
            arrayOfDouble[0] = 255.0D;
          }
          if (arrayOfDouble[1] > 255.0D) {
            arrayOfDouble[1] = 255.0D;
          }
          if (arrayOfDouble[2] > 255.0D) {
            arrayOfDouble[2] = 255.0D;
          }
          localWritableRaster2.setPixel(m, k, arrayOfDouble);
        }
      }
    }
    return localBufferedImage;
  }
  
  public static BufferedImage scaleHeight(BufferedImage paramBufferedImage, Filter paramFilter, int paramInt)
  {
    if ((paramBufferedImage == null) || (paramFilter == null)) {
      throw new NullPointerException();
    }
    int i = paramBufferedImage.getHeight();
    int j = paramBufferedImage.getWidth();
    BufferedImage localBufferedImage = new BufferedImage(j, paramInt, 1);
    WritableRaster localWritableRaster1 = paramBufferedImage.getRaster();
    WritableRaster localWritableRaster2 = localBufferedImage.getRaster();
    double d1 = paramFilter.getArea();
    double d2 = paramInt / i;
    double d3 = d2 < 1.0D ? paramFilter.getWidth() / d2 : paramFilter.getWidth();
    double[] arrayOfDouble = new double[4];
    for (int k = 0; k < j; k++) {
      for (int m = 0; m < paramInt; m++)
      {
        double d4 = m / d2;
        int n = (int)Math.floor(d4 - d3 / 2.0D);
        int i1 = (int)Math.ceil(d4 + d3 / 2.0D);
        double d5 = 0.0D;
        double d6 = 0.0D;
        double d7 = 0.0D;
        double d8 = 0.0D;
        for (int i2 = n; i2 < i1; i2++)
        {
          if ((i2 >= 0) && (i2 < i))
          {
            double d9 = paramFilter.getValue(d4 - i2);
            arrayOfDouble = localWritableRaster1.getPixel(k, i2, arrayOfDouble);
            d5 += d9 * arrayOfDouble[0];
            d6 += d9 * arrayOfDouble[1];
            d7 += d9 * arrayOfDouble[2];
            d8 += d9;
          }
          d8 /= d1;
          arrayOfDouble[0] = (d5 / d8);
          arrayOfDouble[1] = (d6 / d8);
          arrayOfDouble[2] = (d7 / d8);
          if (arrayOfDouble[0] > 255.0D) {
            arrayOfDouble[0] = 255.0D;
          }
          if (arrayOfDouble[1] > 255.0D) {
            arrayOfDouble[1] = 255.0D;
          }
          if (arrayOfDouble[2] > 255.0D) {
            arrayOfDouble[2] = 255.0D;
          }
          localWritableRaster2.setPixel(k, m, arrayOfDouble);
        }
      }
    }
    return localBufferedImage;
  }
}


/* Location:              /Users/masonbartle/Downloads/filter_shape.jar!/edu/brown/cs/exploratories/applets/filterShape/ScaleEngine.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */