package GP;

import java.awt.Choice;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

class GPColumnLayout
  implements LayoutManager
{
  private static final int CHOICE_FUDGE = 10;
  int hgap = 5;
  int vgap = 5;
  
  public void addLayoutComponent(String paramString, Component paramComponent) {}
  
  public void removeLayoutComponent(Component paramComponent) {}
  
  public Dimension preferredLayoutSize(Container paramContainer)
  {
    Dimension localDimension1 = new Dimension(0, 0);
    int i = paramContainer.countComponents();
    for (int j = 0; j < i; j++)
    {
      localObject = paramContainer.getComponent(j);
      if (((Component)localObject).isVisible())
      {
        Dimension localDimension2 = ((Component)localObject).preferredSize();
        localDimension1.width = Math.max(localDimension1.width, localDimension2.width);
        if (j > 0) {
          localDimension1.height += this.vgap;
        }
        localDimension1.height += localDimension2.height;
        if ((localObject instanceof Choice)) {
          localDimension1.height += 10;
        }
      }
    }
    Object localObject = paramContainer.insets();
    localDimension1.width += ((Insets)localObject).left + ((Insets)localObject).right + this.hgap * 2;
    localDimension1.height += ((Insets)localObject).top + ((Insets)localObject).bottom + this.vgap * 2;
    return localDimension1;
  }
  
  public Dimension minimumLayoutSize(Container paramContainer)
  {
    Dimension localDimension1 = new Dimension(0, 0);
    int i = paramContainer.countComponents();
    for (int j = 0; j < i; j++)
    {
      localObject = paramContainer.getComponent(j);
      if (((Component)localObject).isVisible())
      {
        Dimension localDimension2 = ((Component)localObject).minimumSize();
        localDimension1.width = Math.max(localDimension1.width, localDimension2.width);
        if (j > 0) {
          localDimension1.height += this.vgap;
        }
        localDimension1.height += localDimension2.height;
        if ((localObject instanceof Choice)) {
          localDimension1.height += 10;
        }
      }
    }
    Object localObject = paramContainer.insets();
    localDimension1.width += ((Insets)localObject).left + ((Insets)localObject).right + this.hgap * 2;
    localDimension1.height += ((Insets)localObject).top + ((Insets)localObject).bottom + this.vgap * 2;
    return localDimension1;
  }
  
  private void moveComponents(Container paramContainer, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    paramInt2 += paramInt4 / 2;
    for (int i = paramInt5; i < paramInt6; i++)
    {
      Component localComponent = paramContainer.getComponent(i);
      if (localComponent.isVisible())
      {
        localComponent.move(paramInt1, paramInt2);
        paramInt2 += this.vgap + localComponent.size().height;
        if ((localComponent instanceof Choice)) {
          paramInt2 += 10;
        }
      }
    }
  }
  
  public void layoutContainer(Container paramContainer)
  {
    Insets localInsets = paramContainer.insets();
    int i = paramContainer.size().height - (localInsets.top + localInsets.bottom + this.vgap * 2);
    int j = paramContainer.countComponents();
    int k = localInsets.left + this.hgap;
    int m = 0;
    int n = 0;
    int i1 = 0;
    for (int i2 = 0; i2 < j; i2++) {
      i1 = Math.max(i1, paramContainer.getComponent(i2).preferredSize().width);
    }
    for (int i3 = 0; i3 < j; i3++)
    {
      Component localComponent = paramContainer.getComponent(i3);
      if (localComponent.isVisible())
      {
        Dimension localDimension = localComponent.preferredSize();
        localComponent.resize(i1, localDimension.height);
        if ((m == 0) || (m + localDimension.height <= i))
        {
          if (m > 0) {
            m += this.vgap;
          }
          m += localDimension.height;
          if ((localComponent instanceof Choice)) {
            m += 10;
          }
        }
        else
        {
          moveComponents(paramContainer, k, localInsets.top + this.vgap, i1, i - m, n, i3);
          k += this.hgap + i1;
          m = localDimension.height;
          if ((localComponent instanceof Choice)) {
            m += 10;
          }
          n = i3;
        }
      }
    }
    moveComponents(paramContainer, k, localInsets.top + this.vgap, i1, i - m, n, j);
  }
  
  public String toString()
  {
    return getClass().getName();
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/GP/GPColumnLayout.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */