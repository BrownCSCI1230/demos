package edu.brown.cs.exploratories.applets.fft1DApp;

import java.awt.Image;

public class ImageInfo
{
  public Image image;
  public String text;
  
  public ImageInfo(Image paramImage, String paramString)
  {
    this.image = paramImage;
    this.text = paramString;
  }
  
  public String toString()
  {
    return this.text;
  }
}


/* Location:              /Users/masonbartle/Downloads/1d_fast_fourier_transform.jar!/edu/brown/cs/exploratories/applets/fft1DApp/ImageInfo.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */