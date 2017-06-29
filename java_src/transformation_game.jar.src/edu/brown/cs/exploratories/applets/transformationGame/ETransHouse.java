package edu.brown.cs.exploratories.applets.transformationGame;

import edu.brown.cs.exploratories.applets.transformationGame.gameparts.EShape;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

public abstract class ETransHouse
  implements EShape, ETransConst
{
  int sizex = 40;
  int sizey = 60;
  protected GeneralPath wholeShape = new GeneralPath(0, 5);
  protected int[] initX = { 0, this.sizex, this.sizex, this.sizex / 2, 0 };
  protected int[] initY = { 0, 0, this.sizex, this.sizey, this.sizex };
  protected GeneralPath roofShape = new GeneralPath(0, 3);
  protected int[] roofX = { 0, this.sizex, this.sizex / 2 };
  protected int[] roofY = { this.sizex, this.sizex, this.sizey };
  protected GeneralPath doorShape = new GeneralPath(0, 4);
  protected int[] doorX = { 25, 35, 35, 25 };
  protected int[] doorY = { 0, 0, 20, 20 };
  protected Color bodyColor;
  protected Color roofColor;
  protected Color doorColor;
  protected String theString;
  
  public ETransHouse()
  {
    resetShape();
  }
  
  public void resetShape()
  {
    this.wholeShape.reset();
    this.doorShape.reset();
    this.roofShape.reset();
    this.wholeShape.moveTo(this.initX[0], this.initY[0]);
    this.wholeShape.lineTo(this.initX[1], this.initY[1]);
    this.wholeShape.lineTo(this.initX[2], this.initY[2]);
    this.wholeShape.lineTo(this.initX[3], this.initY[3]);
    this.wholeShape.lineTo(this.initX[4], this.initY[4]);
    this.wholeShape.lineTo(this.initX[0], this.initY[0]);
    this.doorShape.moveTo(this.doorX[0], this.doorY[0]);
    this.doorShape.lineTo(this.doorX[1], this.doorY[1]);
    this.doorShape.lineTo(this.doorX[2], this.doorY[2]);
    this.doorShape.lineTo(this.doorX[3], this.doorY[3]);
    this.doorShape.lineTo(this.doorX[0], this.doorX[0]);
    this.roofShape.moveTo(this.roofX[0], this.roofY[0]);
    this.roofShape.lineTo(this.roofX[1], this.roofY[1]);
    this.roofShape.lineTo(this.roofX[2], this.roofY[2]);
    this.roofShape.lineTo(this.roofX[0], this.roofY[0]);
  }
  
  public void showSelf(Graphics2D paramGraphics2D)
  {
    paramGraphics2D.setColor(this.bodyColor);
    paramGraphics2D.fill(this.wholeShape);
    paramGraphics2D.setColor(this.roofColor);
    paramGraphics2D.fill(this.roofShape);
    paramGraphics2D.setColor(this.doorColor);
    paramGraphics2D.fill(this.doorShape);
  }
  
  public void applyTransform(AffineTransform paramAffineTransform)
  {
    this.wholeShape.transform(paramAffineTransform);
    this.doorShape.transform(paramAffineTransform);
    this.roofShape.transform(paramAffineTransform);
  }
  
  public void setLocation(int paramInt1, int paramInt2) {}
  
  public void setString(String paramString)
  {
    this.theString = paramString;
  }
  
  public void setColorSpecial()
  {
    this.bodyColor = ETransConst.E_TRANS_HOUSE_WIN_COLOR_1;
    this.roofColor = ETransConst.E_TRANS_HOUSE_WIN_COLOR_2;
    this.doorColor = ETransConst.E_TRANS_HOUSE_WIN_COLOR_3;
  }
  
  public void setColorSpecial2()
  {
    this.bodyColor = ETransConst.E_TRANS_HOUSE_WIN_COLOR_2;
    this.roofColor = ETransConst.E_TRANS_HOUSE_WIN_COLOR_3;
    this.doorColor = ETransConst.E_TRANS_HOUSE_WIN_COLOR_1;
  }
  
  public void setColorNormal() {}
  
  public boolean contains(int paramInt1, int paramInt2)
  {
    return this.wholeShape.contains(paramInt1, paramInt2);
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransHouse.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */