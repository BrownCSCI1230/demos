package edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace;

public class TranslateBehavior
  extends Behavior
{
  private double dx;
  private double dy;
  private double curX;
  private double curY;
  private int newX;
  private int newY;
  private int oldX;
  private int oldY;
  private int d_endX;
  private int d_endY;
  
  public TranslateBehavior(VisualObject paramVisualObject, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2)
  {
    super(paramVisualObject, paramInt1, paramInt2);
    this.dx = paramDouble1;
    this.dy = paramDouble2;
    this.curX = 0.0D;
    this.curY = 0.0D;
    this.newX = 0;
    this.newY = 0;
    this.oldX = 0;
    this.oldY = 0;
    this.d_endX = ((int)(paramInt1 * this.dx));
    this.d_endY = ((int)(paramInt1 * this.dy));
  }
  
  public void action()
  {
    this.curX += this.dx;
    this.curY += this.dy;
    this.newX = ((int)this.curX);
    this.newY = ((int)this.curY);
    this.myObject.move(this.newX - this.oldX, this.newY - this.oldY);
    this.oldX = this.newX;
    this.oldY = this.newY;
  }
  
  public void jumpToFinalPosition()
  {
    this.myObject.move(this.d_endX, this.d_endY);
  }
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/visualObjectSpace/TranslateBehavior.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */