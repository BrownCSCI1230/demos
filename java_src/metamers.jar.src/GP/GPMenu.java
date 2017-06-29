package GP;

import java.awt.Component;
import java.awt.Menu;

public class GPMenu
  extends Menu
  implements GPManager
{
  public GPMenu(GPWindow paramGPWindow, String paramString)
  {
    super(paramString);
    paramGPWindow.AddMenu(this);
  }
  
  public void AddSeparator()
  {
    addSeparator();
  }
  
  public Component add(Component paramComponent)
  {
    throw new Error("Attempt to add illegal component to GPMenu: " + paramComponent + "\nOnly GPPushButtons should be added to menus.");
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/GP/GPMenu.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */