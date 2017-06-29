package edu.brown.cs.exploratories.applets.transformationGame;

import edu.brown.cs.exploratories.applets.transformationGame.gameparts.EShape;
import edu.brown.cs.exploratories.applets.transformationGame.tools.selection.ERadioSelectable;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.GeneralPath;
import javax.swing.JComponent;

public class ETransAnchor
  extends JComponent
  implements EShape, ETransConst, ERadioSelectable
{
  int diameter = 28;
  int radius = 14;
  int centerX = this.radius;
  int centerY = this.radius;
  private Area anchorArea;
  private Color fillColor;
  
  public ETransAnchor()
  {
    setSize(28, 28);
    Ellipse2D.Double localDouble = new Ellipse2D.Double();
    localDouble.setFrame(this.centerX - this.radius, this.centerY - this.radius, this.diameter, this.diameter);
    Area localArea1 = new Area(localDouble);
    GeneralPath localGeneralPath = new GeneralPath(0, 3);
    localGeneralPath.moveTo(this.centerX, this.centerY);
    localGeneralPath.lineTo(this.centerX - this.diameter, this.centerY - this.diameter);
    localGeneralPath.lineTo(this.centerX - this.diameter, this.centerY + this.diameter);
    localGeneralPath.lineTo(this.centerX, this.centerY);
    Area localArea2 = new Area(localGeneralPath);
    localArea1.subtract(localArea2);
    this.anchorArea = localArea1;
    this.fillColor = ETransConst.E_TRANS_ANCHOR_COLOR;
  }
  
  public void setLocation(int paramInt1, int paramInt2)
  {
    super.setLocation(paramInt1 - this.centerX, paramInt2 - this.centerY);
  }
  
  public boolean contains(double paramDouble1, double paramDouble2)
  {
    return this.anchorArea.contains(paramDouble1, paramDouble2);
  }
  
  public void paintComponent(Graphics paramGraphics)
  {
    Graphics2D localGraphics2D = (Graphics2D)paramGraphics;
    showSelf(localGraphics2D);
  }
  
  public void showSelf(Graphics2D paramGraphics2D)
  {
    paramGraphics2D.setColor(this.fillColor);
    paramGraphics2D.fill(this.anchorArea);
  }
  
  public void applyTransform(AffineTransform paramAffineTransform) {}
  
  public void resetShape() {}
  
  public void setString(String paramString) {}
  
  public void setColorSpecial()
  {
    this.fillColor = ETransConst.E_TRANS_ANCHOR_COLOR_SPECIAL;
    repaint();
  }
  
  public void setColorSpecial2() {}
  
  public void setColorNormal()
  {
    this.fillColor = ETransConst.E_TRANS_ANCHOR_COLOR;
    repaint();
  }
  
  public void setRadioSelectable(boolean paramBoolean) {}
  
  public boolean isRadioSelectable()
  {
    return true;
  }
  
  public void select()
  {
    setColorSpecial();
  }
  
  public void deselect()
  {
    setColorNormal();
  }
  
  public JComponent getComponent()
  {
    return this;
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransAnchor.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */