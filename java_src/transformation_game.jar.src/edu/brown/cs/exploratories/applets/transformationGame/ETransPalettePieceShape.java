package edu.brown.cs.exploratories.applets.transformationGame;

import edu.brown.cs.exploratories.applets.transformationGame.gameparts.EShape;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D.Double;

public class ETransPalettePieceShape
  implements EShape, ETransConst
{
  int sizex = 80;
  int sizey = 40;
  Color outlineColor = ETransConst.E_TRANS_PALETTE_PIECE_OUTLINE_COLOR;
  Color fillColor;
  Color textColor = ETransConst.E_TRANS_PALETTE_PIECE_TEXT_COLOR;
  protected String transString;
  protected GeneralPath wholeShape = new GeneralPath(0, 10);
  protected int[] initX = { 0, this.sizex - 10, this.sizex - 10, this.sizex, this.sizex - 10, this.sizex - 10, 0, 0, 10, 0 };
  protected int[] initY = { 0, 0, 10, this.sizey / 2, this.sizey - 10, this.sizey, this.sizey, this.sizey - 10, this.sizey / 2, 10 };
  protected Line2D.Double line = new Line2D.Double(0.0D, this.sizey - 1, 0.0D, this.sizey - 1);
  
  public ETransPalettePieceShape()
  {
    resetShape();
    this.fillColor = ETransConst.E_TRANS_PALETTE_PIECE_FILL_COLOR;
  }
  
  public void resetShape()
  {
    this.wholeShape.moveTo(this.initX[0], this.initY[0]);
    this.wholeShape.lineTo(this.initX[1], this.initY[1]);
    this.wholeShape.lineTo(this.initX[2], this.initY[2]);
    this.wholeShape.lineTo(this.initX[3], this.initY[3]);
    this.wholeShape.lineTo(this.initX[4], this.initY[4]);
    this.wholeShape.lineTo(this.initX[5], this.initY[5]);
    this.wholeShape.lineTo(this.initX[6], this.initY[6]);
    this.wholeShape.lineTo(this.initX[7], this.initY[7]);
    this.wholeShape.lineTo(this.initX[8], this.initY[8]);
    this.wholeShape.lineTo(this.initX[9], this.initY[9]);
    this.wholeShape.lineTo(this.initX[0], this.initY[0]);
  }
  
  public void setLocation(int paramInt1, int paramInt2) {}
  
  public void applyTransform(AffineTransform paramAffineTransform) {}
  
  public void showSelf(Graphics2D paramGraphics2D)
  {
    paramGraphics2D.setColor(this.fillColor);
    paramGraphics2D.fill(this.wholeShape);
    paramGraphics2D.setColor(this.outlineColor);
    paramGraphics2D.draw(this.wholeShape);
    paramGraphics2D.setColor(this.textColor);
    paramGraphics2D.drawString(this.transString, this.sizex / 2, this.sizey / 2);
  }
  
  public void setString(String paramString)
  {
    this.transString = paramString;
  }
  
  public void setColorSpecial()
  {
    this.fillColor = ETransConst.E_TRANS_PALETTE_PIECE_FILL_COLOR_SPECIAL;
  }
  
  public void setColorSpecial2() {}
  
  public void setColorNormal()
  {
    this.fillColor = ETransConst.E_TRANS_PALETTE_PIECE_FILL_COLOR;
  }
  
  public boolean contains(int paramInt1, int paramInt2)
  {
    return this.wholeShape.contains(paramInt1, paramInt2);
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransPalettePieceShape.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */