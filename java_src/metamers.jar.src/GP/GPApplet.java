package GP;

import java.applet.Applet;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.io.PrintStream;
import java.util.Hashtable;

public abstract class GPApplet
  extends Applet
  implements GPManager
{
  GPWindow window_;
  GPRow row_;
  Hashtable parameters_;
  
  public void Attach()
  {
    this.window_ = new GPWindow(getClass().getName());
    this.row_ = this.window_.row_;
  }
  
  public abstract void Init();
  
  public void Run()
  {
    if (this.window_ == null)
    {
      resize(preferredSize());
    }
    else
    {
      this.window_.show();
      this.window_.resize(preferredSize());
      this.window_.show();
    }
  }
  
  public String GetAppletDescription()
  {
    return "GPApplet v0.9, by Adam Doppelt";
  }
  
  public void SetupParameters(String[] paramArrayOfString)
  {
    this.parameters_ = new Hashtable();
    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      String str = paramArrayOfString[i].toUpperCase();
      int j = str.indexOf('=');
      if (j != -1) {
        this.parameters_.put(str.substring(0, j), str.substring(j + 1));
      } else {
        this.parameters_.put("ARG" + (i + 1), str);
      }
    }
  }
  
  public String GetParameter(String paramString)
  {
    String str1 = paramString.toUpperCase();
    String str2 = paramString.toLowerCase();
    String str3 = getParameter(paramString);
    String str4 = getParameter(str2);
    String str5 = getParameter(str1);
    if ((str3 == null) && (str4 == null) && (str5 == null))
    {
      if (this.parameters_ == null) {
        throw new Error("GPApplet: SetupParameters not called.");
      }
      str3 = (String)this.parameters_.get(paramString);
      str4 = (String)this.parameters_.get(str2);
      str5 = (String)this.parameters_.get(str1);
    }
    if (str3 != null) {
      return str3;
    }
    if (str4 != null) {
      return str4;
    }
    if (str5 != null) {
      return str5;
    }
    return null;
  }
  
  public String[][] GetParameterDescription()
  {
    return null;
  }
  
  public void PrintPreferredSize()
  {
    System.out.println(getClass().getName() + " preferred size is: " + "WIDTH=" + preferredSize().width + " HEIGHT=" + preferredSize().height);
  }
  
  public void init()
  {
    for (Container localContainer = getParent(); !(localContainer instanceof Frame); localContainer = localContainer.getParent()) {}
    localContainer.show();
    setLayout(new FlowLayout());
    this.window_ = null;
    this.row_ = new GPRow(this);
    Init();
    Run();
  }
  
  public Component add(Component paramComponent)
  {
    if (this.row_ != null) {
      return this.row_.add(paramComponent);
    }
    return super.add(paramComponent);
  }
  
  public Dimension preferredSize()
  {
    Dimension localDimension = new Dimension(this.row_.preferredSize());
    localDimension.width += 10;
    localDimension.height += 10;
    return localDimension;
  }
  
  public String getAppletInfo()
  {
    return GetAppletDescription();
  }
  
  public String[][] getParameterInfo()
  {
    return GetParameterDescription();
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/GP/GPApplet.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */