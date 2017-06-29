package edu.brown.cs.exploratories.applets.colorMixing;

import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class SubtractiveMixingComposite
  implements Composite
{
  public static final SubtractiveMixingComposite SINGLETON = new SubtractiveMixingComposite();
  
  public CompositeContext createContext(ColorModel paramColorModel1, ColorModel paramColorModel2, RenderingHints paramRenderingHints)
  {
    return new SubtractiveMixingCompositeContext(null);
  }
  
  private class SubtractiveMixingCompositeContext
    implements CompositeContext
  {
    private SubtractiveMixingCompositeContext() {}
    
    public void compose(Raster paramRaster1, Raster paramRaster2, WritableRaster paramWritableRaster)
    {
      int[] arrayOfInt1 = new int[4];
      int[] arrayOfInt2 = new int[4];
      int[] arrayOfInt3 = new int[4];
      int i = 0;
      int j = 1;
      int k = 2;
      int m = 0;
      int n = 1;
      int i1 = 2;
      int i2 = 3;
      for (int i3 = 0; i3 < paramRaster2.getWidth(); i3++) {
        for (int i4 = 0; i4 < paramRaster2.getHeight(); i4++)
        {
          arrayOfInt1 = paramRaster1.getPixel(i3, i4, arrayOfInt1);
          arrayOfInt2 = paramRaster2.getPixel(i3, i4, arrayOfInt2);
          double d1 = 255 - arrayOfInt1[i];
          double d2 = 255 - arrayOfInt1[j];
          double d3 = 255 - arrayOfInt1[k];
          double d4 = arrayOfInt1[i2] / 255.0D;
          arrayOfInt3[m] = ((int)Math.max(arrayOfInt2[m] - d1 * d4, 0.0D));
          arrayOfInt3[n] = ((int)Math.max(arrayOfInt2[n] - d2 * d4, 0.0D));
          arrayOfInt3[i1] = ((int)Math.max(arrayOfInt2[i1] - d3 * d4, 0.0D));
          arrayOfInt3[i2] = arrayOfInt2[i2];
          paramWritableRaster.setPixel(i3, i4, arrayOfInt3);
        }
      }
    }
    
    public void dispose() {}
    
    SubtractiveMixingCompositeContext(SubtractiveMixingComposite.1 param1)
    {
      this();
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/subtractive_color_mixing.jar!/edu/brown/cs/exploratories/applets/colorMixing/SubtractiveMixingComposite.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */