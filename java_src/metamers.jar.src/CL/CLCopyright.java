package CL;

import GP.GPColor;
import GP.GPDrawingArea;
import GP.GPFont;
import GP.GPText;

public class CLCopyright
{
  static final int x_ = 10;
  static final int startY_ = 430;
  static final String line1_ = "by Jeff Beall, Adam Doppelt and John F. Hughes";
  static final String line2_ = "(c) 1995 Brown University and the NSF Graphics and Visualization Center";
  
  public CLCopyright(GPDrawingArea paramGPDrawingArea)
  {
    GPFont localGPFont = new GPFont();
    int i = 430;
    GPText localGPText1 = new GPText("by Jeff Beall, Adam Doppelt and John F. Hughes", 10, i);
    localGPText1.SetForeground(new GPColor("yellow"));
    localGPText1.Draw(paramGPDrawingArea);
    i += localGPFont.GetHeight(paramGPDrawingArea);
    GPText localGPText2 = new GPText("(c) 1995 Brown University and the NSF Graphics and Visualization Center", 10, i);
    localGPText2.SetForeground(new GPColor("yellow"));
    localGPText2.Draw(paramGPDrawingArea);
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/CL/CLCopyright.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */