package edu.brown.cs.exploratories.applets.colorMixing;

import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class AdditiveMixingComposite
  implements Composite
{
  public static final AdditiveMixingComposite SINGLETON = new AdditiveMixingComposite();
  
  public CompositeContext createContext(ColorModel paramColorModel1, ColorModel paramColorModel2, RenderingHints paramRenderingHints)
  {
    return new AdditiveMixingCompositeContext(null);
  }
  
  private class AdditiveMixingCompositeContext
    implements CompositeContext
  {
    private AdditiveMixingCompositeContext() {}
    
    public void compose(Raster paramRaster1, Raster paramRaster2, WritableRaster paramWritableRaster)
    {
      int[] arrayOfInt1 = new int[4];
      int[] arrayOfInt2 = new int[4];
      int[] arrayOfInt3 = new int[4];
      int i = 0;
      int j = 1;
      int k = 2;
      int m = 3;
      for (int n = 0; n < paramRaster2.getWidth(); n++) {
        for (int i1 = 0; i1 < paramRaster2.getHeight(); i1++)
        {
          arrayOfInt1 = paramRaster1.getPixel(n, i1, arrayOfInt1);
          arrayOfInt2 = paramRaster2.getPixel(n, i1, arrayOfInt2);
          double d = arrayOfInt1[m] / 255.0D;
          arrayOfInt3[i] = ((int)Math.min(arrayOfInt2[i] + arrayOfInt1[i] * d, 255.0D));
          arrayOfInt3[j] = ((int)Math.min(arrayOfInt2[j] + arrayOfInt1[j] * d, 255.0D));
          arrayOfInt3[k] = ((int)Math.min(arrayOfInt2[k] + arrayOfInt1[k] * d, 255.0D));
          arrayOfInt3[m] = arrayOfInt2[m];
          paramWritableRaster.setPixel(n, i1, arrayOfInt3);
        }
      }
    }
    
    public void dispose() {}
    
    AdditiveMixingCompositeContext(AdditiveMixingComposite.1 param1)
    {
      this();
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/additive_color_mixing.jar!/edu/brown/cs/exploratories/applets/colorMixing/AdditiveMixingComposite.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */