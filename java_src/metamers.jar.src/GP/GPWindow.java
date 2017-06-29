package GP;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.MenuBar;

public class GPWindow
  extends Frame
  implements GPManager
{
  GPRow row_ = null;
  MenuBar menuBar_;
  
  public GPWindow(String paramString)
  {
    super(paramString);
    setLayout(new FlowLayout());
    this.row_ = new GPRow(this);
    Show();
    this.menuBar_ = null;
  }
  
  GPWindow(String paramString, boolean paramBoolean)
  {
    super(paramString);
    setLayout(new FlowLayout());
    this.row_ = new GPRow(this);
    if (paramBoolean) {
      Show();
    }
    this.menuBar_ = null;
  }
  
  public void Show()
  {
    resize(preferredSize());
    show();
  }
  
  public void Hide()
  {
    hide();
  }
  
  public void FixSize()
  {
    resize(preferredSize());
    show();
  }
  
  public void AddMenu(GPMenu paramGPMenu)
  {
    if (this.menuBar_ == null)
    {
      this.menuBar_ = new MenuBar();
      setMenuBar(this.menuBar_);
    }
    this.menuBar_.add(paramGPMenu);
  }
  
  public Component add(Component paramComponent)
  {
    if (this.row_ != null) {
      this.row_.add(paramComponent);
    } else {
      super.add(paramComponent);
    }
    return paramComponent;
  }
  
  public Dimension preferredSize()
  {
    Dimension localDimension = new Dimension(this.row_.preferredSize());
    localDimension.width += 10;
    localDimension.height += 10;
    return localDimension;
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/GP/GPWindow.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */