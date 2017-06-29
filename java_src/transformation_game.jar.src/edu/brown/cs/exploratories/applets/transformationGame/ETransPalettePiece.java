package edu.brown.cs.exploratories.applets.transformationGame;

import edu.brown.cs.exploratories.applets.transformationGame.gameparts.EShape;
import edu.brown.cs.exploratories.applets.transformationGame.tools.dragAndDrop.EDragDroppable;
import edu.brown.cs.exploratories.applets.transformationGame.tools.selection.ERadioSelectable;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.PrintStream;
import javax.swing.JComponent;

public class ETransPalettePiece
  extends JComponent
  implements ETransConst, EDragDroppable, ERadioSelectable
{
  private ETransFactory factory;
  private EShape visibleImpl;
  private ETransform eTransform;
  private boolean isRadioSelectable;
  private String valString;
  
  public ETransPalettePiece(ETransFactory paramETransFactory, ETransform paramETransform)
  {
    this.factory = paramETransFactory;
    this.eTransform = paramETransform;
    setSize(80, 56);
    this.visibleImpl = this.factory.getPalettePieceImpl();
    String str = stringFromTransform(paramETransform);
    this.visibleImpl.setString(str);
    this.isRadioSelectable = false;
    this.valString = "";
  }
  
  public void paintComponent(Graphics paramGraphics)
  {
    super.paintComponent(paramGraphics);
    Graphics2D localGraphics2D = (Graphics2D)paramGraphics;
    this.visibleImpl.showSelf(localGraphics2D);
    localGraphics2D.setColor(ETransConst.E_TRANS_PALETTE_PIECE_TEXT_COLOR);
    Point localPoint = getLocation();
    localGraphics2D.drawString(this.valString, 3, 37);
  }
  
  private String stringFromTransform(ETransform paramETransform)
  {
    int i = paramETransform.getType();
    String str;
    switch (i)
    {
    case 2: 
      str = "T";
      break;
    case 8: 
      str = "R";
      break;
    case 5: 
      str = "S";
      break;
    default: 
      str = "?";
      System.out.println("trans type is " + i);
    }
    if (paramETransform.isTumblingTransform()) {
      str = str + "*";
    }
    return str;
  }
  
  public boolean isMoveableAt(int paramInt1, int paramInt2)
  {
    return true;
  }
  
  public boolean isDraggable()
  {
    return true;
  }
  
  public boolean isDroppableAt(int paramInt1, int paramInt2)
  {
    return true;
  }
  
  public JComponent getComponent()
  {
    return this;
  }
  
  public boolean isRadioSelectable()
  {
    return this.isRadioSelectable;
  }
  
  public void setRadioSelectable(boolean paramBoolean)
  {
    this.isRadioSelectable = paramBoolean;
  }
  
  public void setLocation(Point paramPoint)
  {
    int i = (int)paramPoint.getX() - 40;
    int j = (int)paramPoint.getY() - 20;
    super.setLocation(i, j);
  }
  
  public void setLocation(int paramInt1, int paramInt2)
  {
    int i = paramInt1 - 40;
    int j = paramInt2 - 20;
    super.setLocation(i, j);
  }
  
  public ETransform getTransform()
  {
    return this.eTransform;
  }
  
  public void select()
  {
    this.visibleImpl.setColorSpecial();
    repaint();
  }
  
  public void deselect()
  {
    this.visibleImpl.setColorNormal();
    repaint();
  }
  
  public void setValueString(String paramString)
  {
    this.valString = paramString;
    repaint();
  }
  
  public void resetValue()
  {
    getTransform().resetCurrentValue();
    setValueString("");
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransPalettePiece.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */