package edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace;

public class TranslateToPointBehavior
  extends Behavior
{
  private int endX;
  private int endY;
  private double dx;
  private double dy;
  private double curX;
  private double curY;
  
  public TranslateToPointBehavior(VisualObject paramVisualObject, int paramInt1, int paramInt2, double paramDouble, int paramInt3)
  {
    super(paramVisualObject, 1, paramInt3);
    this.endX = paramInt1;
    this.endY = paramInt2;
    this.dx = (paramInt1 - this.myObject.getX());
    this.dy = (paramInt2 - this.myObject.getY());
    double d = Math.sqrt(Math.pow(this.dx, 2.0D) + Math.pow(this.dy, 2.0D));
    int i = (int)Math.ceil(d / paramDouble);
    this.dx /= i;
    this.dy /= i;
    setIterations(i);
    this.curX = this.myObject.getX();
    this.curY = this.myObject.getY();
  }
  
  public void jumpToFinalPosition()
  {
    this.myObject.setPosition(this.endX, this.endY);
  }
  
  public void action()
  {
    this.curX += this.dx;
    this.curY += this.dy;
    this.myObject.setPosition((int)this.curX, (int)this.curY);
  }
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/visualObjectSpace/TranslateToPointBehavior.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */