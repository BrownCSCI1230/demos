package edu.brown.cs.exploratories.applets.transformationGame;

import edu.brown.cs.exploratories.applets.transformationGame.gameparts.EAnimatable;
import edu.brown.cs.exploratories.applets.transformationGame.gameparts.EShape;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.PrintStream;
import javax.swing.JComponent;

public abstract class ETransGameObject
  extends JComponent
  implements ETransConst, EAnimatable
{
  protected ETransFactory factory;
  protected ETransformationManager transMan;
  protected AffineTransform myTransform;
  protected AffineTransform boardCorrectionTransform;
  protected AffineTransform objectCorrectionTransform;
  protected EShape visibleImpl;
  
  public ETransGameObject(ETransFactory paramETransFactory)
  {
    this.factory = paramETransFactory;
    this.transMan = this.factory.getTransformationManager();
    this.myTransform = new AffineTransform();
    AffineTransform localAffineTransform1 = AffineTransform.getScaleInstance(1.0D, -1.0D);
    int i = 200;
    int j = 220;
    AffineTransform localAffineTransform2 = AffineTransform.getTranslateInstance(i, -j);
    localAffineTransform1.concatenate(localAffineTransform2);
    this.boardCorrectionTransform = localAffineTransform1;
    this.objectCorrectionTransform = AffineTransform.getTranslateInstance(-20.0D, -20.0D);
  }
  
  public void update()
  {
    updateTransform();
    if (null == this.myTransform) {
      this.myTransform = new AffineTransform();
    }
    this.visibleImpl.resetShape();
    this.visibleImpl.applyTransform(this.objectCorrectionTransform);
    this.visibleImpl.applyTransform(this.myTransform);
    this.visibleImpl.applyTransform(this.boardCorrectionTransform);
    repaint();
  }
  
  public abstract void updateTransform();
  
  public void paintComponent(Graphics paramGraphics)
  {
    Graphics2D localGraphics2D = (Graphics2D)paramGraphics;
    this.visibleImpl.showSelf(localGraphics2D);
  }
  
  public void setLocation(int paramInt1, int paramInt2)
  {
    this.visibleImpl.setLocation(paramInt1, paramInt2);
  }
  
  public void printMatrix(AffineTransform paramAffineTransform)
  {
    double[] arrayOfDouble = new double[6];
    paramAffineTransform.getMatrix(arrayOfDouble);
    System.out.println(arrayOfDouble[0] + "  " + arrayOfDouble[2] + "  " + arrayOfDouble[4]);
    System.out.println(arrayOfDouble[1] + "  " + arrayOfDouble[3] + "  " + arrayOfDouble[5]);
    System.out.println("0      0      1");
    System.out.println(" ");
  }
  
  public boolean contains(int paramInt1, int paramInt2)
  {
    return this.visibleImpl.contains(paramInt1, paramInt2);
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransGameObject.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */