package edu.brown.cs.exploratories.applets.transformationGame;

import java.awt.geom.AffineTransform;
import java.io.PrintStream;

public class ETransform
  implements ETransConst
{
  private AffineTransform javaTrans;
  private boolean isAnimated;
  private int transformationType;
  private double deltaValue;
  private double currValue;
  private double maxValue;
  private double defaultValue;
  private boolean increasing;
  private int adjustmentCount;
  private boolean wasNudgedX;
  private boolean isTumblingTransform;
  
  public ETransform(int paramInt, boolean paramBoolean, double paramDouble)
  {
    this.isAnimated = paramBoolean;
    this.isTumblingTransform = paramBoolean;
    this.transformationType = paramInt;
    if ((this.transformationType == 6) || (this.transformationType == 7)) {
      this.defaultValue = 1.0D;
    } else {
      this.defaultValue = 0.0D;
    }
    this.maxValue = paramDouble;
    if (this.isTumblingTransform) {
      this.currValue = this.defaultValue;
    } else {
      this.currValue = this.maxValue;
    }
    this.deltaValue = ((this.maxValue - this.defaultValue) / 25.0D);
    setAffineTransform();
    this.increasing = false;
    this.adjustmentCount = 0;
  }
  
  public void pause()
  {
    this.isAnimated = false;
  }
  
  public void unpause()
  {
    this.isAnimated = true;
  }
  
  private double degreesToRadians(double paramDouble)
  {
    return paramDouble * 3.141592653589793D / 180.0D;
  }
  
  public void printMatrix()
  {
    if (this.javaTrans != null)
    {
      double[] arrayOfDouble = new double[6];
      this.javaTrans.getMatrix(arrayOfDouble);
      System.out.println(arrayOfDouble[0] + "  " + arrayOfDouble[2] + "  " + arrayOfDouble[4]);
      System.out.println(arrayOfDouble[1] + "  " + arrayOfDouble[3] + "  " + arrayOfDouble[5]);
      System.out.println("0      0      1");
    }
  }
  
  public int getType()
  {
    return this.transformationType;
  }
  
  public boolean isTumblingTransform()
  {
    return this.isTumblingTransform;
  }
  
  public void update(int paramInt)
  {
    if ((this.isTumblingTransform) && (this.isAnimated))
    {
      adjustCurrValue(paramInt);
      setAffineTransform();
    }
  }
  
  public AffineTransform getAffineTransform()
  {
    return this.javaTrans;
  }
  
  private void adjustCurrValue(int paramInt)
  {
    if (paramInt < 25) {
      this.currValue = ((paramInt + 1) * this.deltaValue);
    } else {
      this.currValue = ((50 - paramInt) * this.deltaValue);
    }
  }
  
  public double getOfficialValue()
  {
    if (this.isTumblingTransform) {
      return this.maxValue;
    }
    return this.currValue;
  }
  
  public void nudgeX(int paramInt)
  {
    this.wasNudgedX = true;
    double d;
    if (this.isTumblingTransform)
    {
      switch (this.transformationType)
      {
      case 8: 
        this.maxValue -= paramInt;
        d = degreesToRadians(this.maxValue);
        this.javaTrans = AffineTransform.getRotateInstance(d);
        break;
      case 5: 
        this.maxValue += paramInt / 10.0D;
        this.javaTrans = AffineTransform.getScaleInstance(this.maxValue, 1.0D);
        break;
      case 2: 
        this.maxValue += paramInt;
        this.javaTrans = AffineTransform.getTranslateInstance(this.maxValue, 0.0D);
        break;
      default: 
        System.out.println("Problem w/ nudgeX()");
      }
      this.deltaValue = (this.maxValue / 25.0D);
    }
    else
    {
      switch (this.transformationType)
      {
      case 8: 
        this.currValue -= paramInt;
        d = degreesToRadians(this.currValue);
        this.javaTrans = AffineTransform.getRotateInstance(d);
        break;
      case 5: 
        this.currValue += paramInt / 10.0D;
        this.javaTrans = AffineTransform.getScaleInstance(this.currValue, 1.0D);
        break;
      case 2: 
        this.currValue += paramInt;
        this.javaTrans = AffineTransform.getTranslateInstance(this.currValue, 0.0D);
        break;
      default: 
        System.out.println("Problem w/ nudgeX()");
      }
    }
  }
  
  public void nudgeY(int paramInt)
  {
    this.wasNudgedX = false;
    if (this.isTumblingTransform)
    {
      switch (this.transformationType)
      {
      case 5: 
        this.maxValue += paramInt / 10.0D;
        this.javaTrans = AffineTransform.getScaleInstance(1.0D, this.maxValue);
        break;
      case 2: 
        this.maxValue += paramInt;
        this.javaTrans = AffineTransform.getTranslateInstance(0.0D, this.maxValue);
        break;
      default: 
        System.out.println("Problem w/ nudgeY()");
      }
      this.deltaValue = (this.maxValue / 25.0D);
    }
    else
    {
      switch (this.transformationType)
      {
      case 5: 
        this.currValue += paramInt / 10.0D;
        this.javaTrans = AffineTransform.getScaleInstance(1.0D, this.currValue);
        break;
      case 2: 
        this.currValue += paramInt;
        this.javaTrans = AffineTransform.getTranslateInstance(0.0D, this.currValue);
        break;
      default: 
        System.out.println("Problem w/ nudgeY()");
      }
    }
  }
  
  public boolean wasNudgedX()
  {
    return this.wasNudgedX;
  }
  
  public void resetCurrentValue()
  {
    switch (this.transformationType)
    {
    case 5: 
    case 6: 
    case 7: 
      this.currValue = 1.0D;
      this.maxValue = 1.0D;
      break;
    case 2: 
    case 3: 
    case 4: 
      this.currValue = 0.0D;
      this.maxValue = 0.0D;
      break;
    case 8: 
      this.currValue = 0.0D;
      this.maxValue = 0.0D;
      break;
    default: 
      System.out.println("Problem with resetting ETransform's current val");
    }
    setAffineTransform();
  }
  
  private void setAffineTransform()
  {
    switch (this.transformationType)
    {
    case 3: 
      this.javaTrans = AffineTransform.getTranslateInstance(this.currValue, 0.0D);
      break;
    case 4: 
      this.javaTrans = AffineTransform.getTranslateInstance(0.0D, this.currValue);
      break;
    case 8: 
      double d = degreesToRadians(this.currValue);
      this.javaTrans = AffineTransform.getRotateInstance(d);
      break;
    case 6: 
      this.javaTrans = AffineTransform.getScaleInstance(this.currValue, 1.0D);
      break;
    case 7: 
      this.javaTrans = AffineTransform.getScaleInstance(1.0D, this.currValue);
      break;
    case 99: 
      this.javaTrans = null;
      break;
    case 2: 
    case 5: 
      this.javaTrans = new AffineTransform();
      break;
    default: 
      System.out.println("Problem creating new ETransform.");
      System.out.println("type was " + this.transformationType);
      this.javaTrans = new AffineTransform();
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransform.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */