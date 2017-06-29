package GP;

import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;

public class GPFont
  extends Font
{
  private static final String DEFAULT_FONT = "TimesRoman";
  private static final int DEFAULT_STYLE = 0;
  private static final int DEFAULT_SIZE = 12;
  
  public GPFont()
  {
    this("TimesRoman", 0, 12);
  }
  
  public GPFont(GPFont paramGPFont)
  {
    super(paramGPFont.name, paramGPFont.style, paramGPFont.size);
  }
  
  public GPFont(String paramString)
  {
    this(paramString, 0, 12);
  }
  
  public GPFont(String paramString, int paramInt)
  {
    this(paramString, paramInt, 12);
  }
  
  public GPFont(String paramString, int paramInt1, int paramInt2)
  {
    super(paramString, paramInt1, paramInt2);
  }
  
  public int GetStringWidth(Component paramComponent, String paramString)
  {
    return paramComponent.getFontMetrics(this).stringWidth(paramString);
  }
  
  public int GetCharWidth(Component paramComponent, int paramInt)
  {
    return paramComponent.getFontMetrics(this).charWidth(paramInt);
  }
  
  public int GetHeight(Component paramComponent)
  {
    return paramComponent.getFontMetrics(this).getHeight();
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/GP/GPFont.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */