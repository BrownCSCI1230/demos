package GP;

import java.awt.Graphics;

public abstract class GPShape
{
  GPColor foreground_ = new GPColor("white");
  
  public void Draw(GPDrawingArea paramGPDrawingArea)
  {
    paramGPDrawingArea.DrawThisShape(this);
  }
  
  public void Erase(GPDrawingArea paramGPDrawingArea)
  {
    GPColor localGPColor = this.foreground_;
    this.foreground_ = paramGPDrawingArea.background_;
    paramGPDrawingArea.DrawThisShape(this);
    this.foreground_ = localGPColor;
  }
  
  public void SetForeground(GPColor paramGPColor)
  {
    this.foreground_ = paramGPColor;
  }
  
  public GPColor GetForeground()
  {
    return new GPColor(this.foreground_);
  }
  
  void StyledDraw(Graphics paramGraphics)
  {
    paramGraphics.setColor(this.foreground_.color_);
    DrawPrimitive(paramGraphics);
  }
  
  abstract void DrawPrimitive(Graphics paramGraphics);
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/GP/GPShape.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */