package edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace;

public class ParabolicBehavior
  extends Behavior
{
  private double dx;
  private double dy;
  private double curX;
  private double curY;
  private double pInput;
  private double deltaPInput;
  private double pScalar;
  private int type;
  private int endX;
  private int endY;
  public static int ALLIGN_X = 0;
  public static int ALLIGN_Y = 1;
  
  public ParabolicBehavior(VisualObject paramVisualObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    super(paramVisualObject, 1, paramInt5);
    this.type = paramInt6;
    this.endX = paramInt1;
    this.endY = paramInt2;
    double d1 = paramInt1 - this.myObject.getX();
    double d2 = paramInt2 - this.myObject.getY();
    double d3 = Math.sqrt(Math.pow(d1, 2.0D) + Math.pow(d2, 2.0D));
    this.dx = (d1 / paramInt4);
    this.dy = (d2 / paramInt4);
    setIterations(paramInt4);
    this.curX = this.myObject.getX();
    this.curY = this.myObject.getY();
    this.pInput = -1.0D;
    this.deltaPInput = (2.0D / paramInt4);
    if (this.type == ALLIGN_Y)
    {
      this.curY += -paramInt3;
      this.pScalar = paramInt3;
    }
    else
    {
      this.curX += paramInt3;
      this.pScalar = (-paramInt3);
    }
  }
  
  public void action()
  {
    this.curX += this.dx;
    this.curY += this.dy;
    this.pInput += this.deltaPInput;
    int i = (int)(this.pScalar * Math.pow(this.pInput, 2.0D));
    if (this.type == ALLIGN_Y) {
      this.myObject.setPosition((int)this.curX, (int)this.curY + i);
    } else {
      this.myObject.setPosition((int)this.curX + i, (int)this.curY);
    }
  }
  
  public void jumpToFinalPosition()
  {
    this.myObject.setPosition(this.endX, this.endY);
  }
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/visualObjectSpace/ParabolicBehavior.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */