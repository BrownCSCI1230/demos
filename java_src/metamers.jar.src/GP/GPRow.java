package GP;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Panel;

public class GPRow
  extends Panel
  implements GPManager
{
  public GPRow(GPManager paramGPManager)
  {
    paramGPManager.add(this);
    setLayout(new FlowLayout());
  }
  
  public Component add(Component paramComponent)
  {
    return super.add(paramComponent);
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/GP/GPRow.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */