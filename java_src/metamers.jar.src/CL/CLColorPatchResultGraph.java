package CL;

import GP.GPColor;
import GP.GPDrawingArea;
import GP.GPFilledOval;
import GP.GPPoint;
import GP.GPShape;

public class CLColorPatchResultGraph
  extends CLColorResultGraph
{
  protected GPShape patch_;
  
  public CLColorPatchResultGraph(int paramInt1, int paramInt2, GPPoint paramGPPoint, int paramInt3, int paramInt4, int paramInt5)
  {
    super(paramInt1, paramInt2, paramGPPoint, paramInt3);
    int i = this.corner_.GetX() + (this.bar_width_ * this.width_ + paramInt4 / 2 + 10);
    int j = this.corner_.GetY() + this.height_ / 2;
    this.patch_ = new GPFilledOval(i - paramInt4 / 2, j - paramInt5 / 2, i + paramInt4 / 2, j + paramInt5 / 2);
  }
  
  public CLColorPatchResultGraph(CLColorPatchResultGraph paramCLColorPatchResultGraph)
  {
    super(paramCLColorPatchResultGraph);
  }
  
  public void Draw(GPDrawingArea paramGPDrawingArea)
  {
    super.Draw(paramGPDrawingArea);
    DrawPatch(paramGPDrawingArea);
  }
  
  public void Undraw(GPDrawingArea paramGPDrawingArea)
  {
    super.Undraw(paramGPDrawingArea);
    this.patch_.Erase(paramGPDrawingArea);
  }
  
  public void DrawBarRange(GPDrawingArea paramGPDrawingArea, int paramInt1, int paramInt2)
  {
    super.DrawBarRange(paramGPDrawingArea, paramInt1, paramInt2);
    DrawPatch(paramGPDrawingArea);
  }
  
  public void DrawPatch(GPDrawingArea paramGPDrawingArea)
  {
    int[] arrayOfInt = new int[3];
    for (int i = 0; i < this.width_; i++) {
      if ((i < this.width_) && (i < 3)) {
        arrayOfInt[i] = ((int)(this.element_array_[i] * 255.0D));
      }
    }
    this.patch_.SetForeground(new GPColor(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2]));
    this.patch_.Draw(paramGPDrawingArea);
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/CL/CLColorPatchResultGraph.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */