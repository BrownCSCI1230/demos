package GP;

import java.awt.Graphics;

public class GPText
  extends GPShape
{
  GPPoint pos_;
  String text_;
  GPFont font_;
  
  public GPText()
  {
    this("", 0, 0);
  }
  
  public GPText(String paramString, int paramInt1, int paramInt2)
  {
    this(paramString, new GPPoint(paramInt1, paramInt2));
  }
  
  public GPText(String paramString, GPPoint paramGPPoint)
  {
    this.pos_ = new GPPoint(paramGPPoint);
    this.text_ = paramString;
    this.font_ = new GPFont();
  }
  
  public GPFont GetFont()
  {
    return new GPFont(this.font_);
  }
  
  public int GetX()
  {
    return this.pos_.x_;
  }
  
  public int GetY()
  {
    return this.pos_.y_;
  }
  
  public String GetString()
  {
    return this.text_;
  }
  
  public void SetFont(GPFont paramGPFont)
  {
    this.font_ = paramGPFont;
  }
  
  public void SetX(int paramInt)
  {
    this.pos_.x_ = paramInt;
  }
  
  public void SetY(int paramInt)
  {
    this.pos_.y_ = paramInt;
  }
  
  public void SetString(String paramString)
  {
    this.text_ = paramString;
  }
  
  public void SetCoords(int paramInt1, int paramInt2)
  {
    this.pos_.x_ = paramInt1;
    this.pos_.y_ = paramInt2;
  }
  
  public void SetPoint(GPPoint paramGPPoint)
  {
    this.pos_.SetPoint(paramGPPoint);
  }
  
  public void DrawPrimitive(Graphics paramGraphics)
  {
    paramGraphics.setFont(this.font_);
    paramGraphics.drawString(this.text_, this.pos_.x_, this.pos_.y_);
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/GP/GPText.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */