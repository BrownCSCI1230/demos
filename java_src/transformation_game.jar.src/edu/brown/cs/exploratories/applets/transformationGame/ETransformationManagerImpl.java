package edu.brown.cs.exploratories.applets.transformationGame;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.io.PrintStream;

public class ETransformationManagerImpl
  implements ETransformationManager, MouseListener, MouseMotionListener, ETransConst
{
  private ETransFactory factory;
  private ETransformList goalTransformList;
  private ETransformList currentTransformList;
  private ETransform selectedTransform;
  private AffineTransform identityTransform;
  private ETransform dummyTransform;
  private ETransPalettePiece selectedPiece;
  private int lastMouseX;
  private int lastMouseY;
  private boolean dragHorizontal;
  private boolean newDrag;
  private int dragForce;
  private int lengthOfCycle = 50;
  private int currPlaceInAnimationCycle;
  
  public ETransformationManagerImpl(ETransFactory paramETransFactory)
  {
    this.factory = paramETransFactory;
    this.goalTransformList = null;
    this.currentTransformList = null;
    this.selectedTransform = null;
    this.identityTransform = new AffineTransform();
    this.dummyTransform = new ETransform(99, false, 0.0D);
    this.currPlaceInAnimationCycle = 0;
  }
  
  public void setGoalTransformList(ETransformList paramETransformList)
  {
    this.goalTransformList = paramETransformList;
  }
  
  public void setCurrentTransformList(ETransformList paramETransformList)
  {
    this.currentTransformList = paramETransformList;
  }
  
  public void clearCurrentTransformList()
  {
    if (this.currentTransformList != null) {
      this.currentTransformList.clear();
    }
  }
  
  public void setSelectedPiece(ETransPalettePiece paramETransPalettePiece)
  {
    this.selectedPiece = paramETransPalettePiece;
    if (this.selectedPiece == null) {
      setSelectedTransform(this.dummyTransform);
    } else {
      setSelectedTransform(paramETransPalettePiece.getTransform());
    }
  }
  
  private void setSelectedTransform(ETransform paramETransform)
  {
    this.selectedTransform = paramETransform;
  }
  
  public AffineTransform getCurrentCompositeTransform()
  {
    if (this.currentTransformList != null) {
      return this.currentTransformList.getCompositeTransform();
    }
    return this.identityTransform;
  }
  
  public AffineTransform getSelectedCompositeTransform()
  {
    if (this.currentTransformList != null) {
      return this.currentTransformList.getPartialCompositeTransform(this.selectedTransform);
    }
    return null;
  }
  
  public AffineTransform getGoalCompositeTransform()
  {
    if (this.goalTransformList != null) {
      return this.goalTransformList.getCompositeTransform();
    }
    return null;
  }
  
  public void update()
  {
    if (this.goalTransformList != null) {
      this.goalTransformList.update(this.currPlaceInAnimationCycle);
    }
    if (this.currentTransformList != null) {
      this.currentTransformList.update(this.currPlaceInAnimationCycle);
    }
    this.currPlaceInAnimationCycle = ((this.currPlaceInAnimationCycle + 1) % this.lengthOfCycle);
  }
  
  private void adjustSelectedTransform(int paramInt1, int paramInt2)
  {
    if (this.selectedTransform == this.dummyTransform) {
      return;
    }
    int i = this.selectedTransform.getType();
    if (i == 8)
    {
      this.dragHorizontal = true;
    }
    else if (this.newDrag)
    {
      this.dragHorizontal = (Math.abs(paramInt1) > Math.abs(paramInt2));
      this.newDrag = false;
      if (((this.selectedTransform.wasNudgedX()) && (!this.dragHorizontal)) || ((!this.selectedTransform.wasNudgedX()) && (this.dragHorizontal))) {
        this.selectedTransform.resetCurrentValue();
      }
    }
    if (this.dragHorizontal) {
      this.dragForce += Math.abs(paramInt1);
    } else {
      this.dragForce += Math.abs(paramInt2);
    }
    if (this.dragForce < 12) {
      return;
    }
    double d;
    switch (i)
    {
    case 5: 
      if (this.dragHorizontal)
      {
        this.selectedTransform.nudgeX(paramInt1);
        d = this.selectedTransform.getOfficialValue();
        this.selectedPiece.setValueString("X: " + d);
      }
      else
      {
        this.selectedTransform.nudgeY(paramInt2);
        d = this.selectedTransform.getOfficialValue();
        this.selectedPiece.setValueString("Y: " + d);
      }
      break;
    case 2: 
      if (this.dragHorizontal)
      {
        this.selectedTransform.nudgeX(paramInt1);
        d = this.selectedTransform.getOfficialValue();
        this.selectedPiece.setValueString("X: " + (int)d);
      }
      else
      {
        this.selectedTransform.nudgeY(paramInt2);
        d = this.selectedTransform.getOfficialValue();
        this.selectedPiece.setValueString("Y: " + (int)d);
      }
      break;
    case 8: 
      this.selectedTransform.nudgeX(paramInt1);
      d = this.selectedTransform.getOfficialValue();
      this.selectedPiece.setValueString((int)d + " deg");
      break;
    default: 
      System.out.println("Problem adjusting selected transform");
    }
    ETransPlayObject localETransPlayObject = this.factory.getPlayObject();
    localETransPlayObject.update();
    ETransGhostObject localETransGhostObject = this.factory.getGhostObject();
    localETransGhostObject.update();
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent) {}
  
  public void mouseEntered(MouseEvent paramMouseEvent) {}
  
  public void mouseExited(MouseEvent paramMouseEvent) {}
  
  public void mousePressed(MouseEvent paramMouseEvent)
  {
    Component localComponent = paramMouseEvent.getComponent();
    if (!localComponent.isVisible()) {
      return;
    }
    this.lastMouseX = paramMouseEvent.getX();
    this.lastMouseY = paramMouseEvent.getY();
    this.newDrag = true;
    this.dragForce = 0;
    this.selectedTransform.pause();
  }
  
  public void mouseReleased(MouseEvent paramMouseEvent)
  {
    this.newDrag = true;
    this.dragForce = 0;
    this.selectedTransform.unpause();
    update();
    ETransPlayManager localETransPlayManager = this.factory.getPlayManager();
    localETransPlayManager.userMoved();
    AffineTransform localAffineTransform1 = getGoalCompositeTransform();
    AffineTransform localAffineTransform2 = getCurrentCompositeTransform();
  }
  
  public void mouseDragged(MouseEvent paramMouseEvent)
  {
    Component localComponent = paramMouseEvent.getComponent();
    if (!localComponent.isVisible()) {
      return;
    }
    int i = paramMouseEvent.getX() - this.lastMouseX;
    int j = paramMouseEvent.getY() - this.lastMouseY;
    this.lastMouseX = paramMouseEvent.getX();
    this.lastMouseY = paramMouseEvent.getY();
    int k = -j;
    adjustSelectedTransform(i, k);
  }
  
  public void mouseMoved(MouseEvent paramMouseEvent) {}
  
  private void printMatrix(AffineTransform paramAffineTransform)
  {
    if (paramAffineTransform != null)
    {
      double[] arrayOfDouble = new double[6];
      paramAffineTransform.getMatrix(arrayOfDouble);
      System.out.println(arrayOfDouble[0] + "  " + arrayOfDouble[2] + "  " + arrayOfDouble[4]);
      System.out.println(arrayOfDouble[1] + "  " + arrayOfDouble[3] + "  " + arrayOfDouble[5]);
      System.out.println("0      0      1");
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransformationManagerImpl.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */