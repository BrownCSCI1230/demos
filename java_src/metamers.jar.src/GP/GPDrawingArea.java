package GP;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.io.PrintStream;

public class GPDrawingArea
  extends Canvas
{
  GPColor background_;
  GPMouseInteractor mouse_;
  Image image_;
  Graphics offscreen_;
  int width_;
  int height_;
  boolean xor_;
  GPKeyInteractor[] keys_;
  
  public GPDrawingArea(GPManager paramGPManager, int paramInt1, int paramInt2)
  {
    resize(paramInt1, paramInt2);
    paramGPManager.add(this);
    this.mouse_ = null;
    this.keys_ = new GPKeyInteractor['Ӱ'];
    this.image_ = createImage(paramInt1, paramInt2);
    this.offscreen_ = this.image_.getGraphics();
    this.width_ = paramInt1;
    this.height_ = paramInt2;
    this.background_ = new GPColor();
    Clear(this.background_);
    SetOverwriteMode();
  }
  
  public void SetWidth(int paramInt)
  {
    this.width_ = paramInt;
    this.image_ = createImage(this.width_, this.height_);
    this.offscreen_ = this.image_.getGraphics();
    Clear(this.background_);
    resize(this.width_, this.height_);
  }
  
  public void SetHeight(int paramInt)
  {
    this.height_ = paramInt;
    this.image_ = createImage(this.width_, this.height_);
    this.offscreen_ = this.image_.getGraphics();
    Clear(this.background_);
    resize(this.width_, this.height_);
  }
  
  public void SetBackground(GPColor paramGPColor)
  {
    this.background_ = paramGPColor;
  }
  
  public int GetWidth()
  {
    return this.width_;
  }
  
  public int GetHeight()
  {
    return this.height_;
  }
  
  public GPColor GetBackground()
  {
    return new GPColor(this.background_);
  }
  
  public synchronized void Clear(GPColor paramGPColor)
  {
    Graphics localGraphics = getGraphics();
    Color localColor = paramGPColor.GetColor();
    if (localGraphics != null)
    {
      localGraphics.setColor(localColor);
      localGraphics.fillRect(0, 0, this.width_, this.height_);
    }
    this.offscreen_.setColor(localColor);
    this.offscreen_.fillRect(0, 0, this.width_, this.height_);
  }
  
  public void AddInteractor(GPEventInteractor paramGPEventInteractor)
  {
    if ((paramGPEventInteractor instanceof GPMouseInteractor)) {
      this.mouse_ = ((GPMouseInteractor)paramGPEventInteractor);
    } else {
      this.keys_[((GPKeyInteractor)paramGPEventInteractor).GetIndex()] = ((GPKeyInteractor)paramGPEventInteractor);
    }
  }
  
  public void RemoveInteractor(GPEventInteractor paramGPEventInteractor)
  {
    if ((paramGPEventInteractor instanceof GPMouseInteractor))
    {
      if (paramGPEventInteractor == this.mouse_) {
        this.mouse_ = null;
      } else {
        System.out.println("Attempt to remove non-installed mouse interactor. Ignored.");
      }
    }
    else
    {
      int i = ((GPKeyInteractor)paramGPEventInteractor).GetIndex();
      if (this.keys_[i] == paramGPEventInteractor) {
        this.keys_[i] = null;
      } else {
        System.out.println("Attempt to remove non-installed key interactor. Ignored.");
      }
    }
  }
  
  public void SetOverwriteMode()
  {
    Graphics localGraphics = getGraphics();
    if (localGraphics != null) {
      localGraphics.setPaintMode();
    }
    this.offscreen_.setPaintMode();
    this.xor_ = false;
  }
  
  public void SetXORMode()
  {
    this.xor_ = true;
  }
  
  public void DrawThisShape(GPShape paramGPShape)
  {
    Graphics localGraphics = getGraphics();
    if (this.xor_)
    {
      localGraphics.setXORMode(paramGPShape.GetForeground().GetColor());
      this.offscreen_.setXORMode(paramGPShape.GetForeground().GetColor());
    }
    if (localGraphics != null) {
      paramGPShape.StyledDraw(localGraphics);
    } else {
      System.out.println("GPDrawingArea.DrawThisShape: onscreen = null!");
    }
    paramGPShape.StyledDraw(this.offscreen_);
  }
  
  public boolean handleEvent(Event paramEvent)
  {
    switch (paramEvent.id)
    {
    case 504: 
      requestFocus();
      return true;
    case 501: 
      if (this.mouse_ != null) {
        this.mouse_.ButtonDownCallback(new GPPoint(paramEvent.x, paramEvent.y), paramEvent.modifiers);
      }
      return this.mouse_ != null;
    case 506: 
      if (this.mouse_ != null) {
        this.mouse_.ButtonMotionCallback(new GPPoint(paramEvent.x, paramEvent.y), paramEvent.modifiers);
      }
      return this.mouse_ != null;
    case 502: 
      if (this.mouse_ != null) {
        this.mouse_.ButtonUpCallback(new GPPoint(paramEvent.x, paramEvent.y), paramEvent.modifiers);
      }
      return this.mouse_ != null;
    case 401: 
      int i;
      if ((paramEvent.key >= 97) && (paramEvent.key <= 122)) {
        i = paramEvent.key - 97 + 65;
      } else {
        i = paramEvent.key;
      }
      if (this.keys_[i] != null)
      {
        this.keys_[i].KeyCallback();
        return true;
      }
      return false;
    case 403: 
      if (this.keys_[(256 + paramEvent.key)] != null)
      {
        this.keys_[(256 + paramEvent.key)].KeyCallback();
        return true;
      }
      return false;
    }
    return super.handleEvent(paramEvent);
  }
  
  public void paint(Graphics paramGraphics)
  {
    if (this.image_ != null) {
      paramGraphics.drawImage(this.image_, 0, 0, null);
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/GP/GPDrawingArea.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */