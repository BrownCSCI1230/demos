package GP;

import java.awt.Component;
import java.awt.Panel;

public class GPColumn
  extends Panel
  implements GPManager
{
  public GPColumn(GPManager paramGPManager)
  {
    paramGPManager.add(this);
    setLayout(new GPColumnLayout());
  }
  
  public Component add(Component paramComponent)
  {
    return super.add(paramComponent);
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/GP/GPColumn.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */