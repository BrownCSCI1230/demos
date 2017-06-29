package CL;

import GP.GPDrawingArea;
import GP.GPPoint;

public abstract class CLManager
{
  static final int CL_ARRAY_SLOTS = 11;
  static final int CL_SLIDER_SLOTS = 2;
  CLVisualGraph[] active_array_;
  CLVisualGraph[] inactive_array_;
  CLFilter[] filter_array_;
  CLGraph[] nonvisual_array_;
  CLControlScale[] slider_array_;
  boolean click_good_ = false;
  int current_active_ = 0;
  int current_index_ = 0;
  int old_index_ = 0;
  int ancient_index_ = 0;
  double current_value_ = 0.0D;
  double old_value_ = 0.0D;
  double ancient_value_ = 0.0D;
  int max_graph_width_;
  GPDrawingArea da_;
  
  public CLManager(GPDrawingArea paramGPDrawingArea, int paramInt)
  {
    this.max_graph_width_ = paramInt;
    this.da_ = paramGPDrawingArea;
    this.active_array_ = new CLVisualGraph[11];
    this.inactive_array_ = new CLVisualGraph[11];
    this.filter_array_ = new CLFilter[11];
    this.nonvisual_array_ = new CLGraph[11];
    this.slider_array_ = new CLControlScale[2];
    for (int i = 0; i < 11; i++)
    {
      this.active_array_[i] = null;
      this.inactive_array_[i] = null;
      this.filter_array_[i] = null;
      this.nonvisual_array_[i] = null;
    }
    new CLCopyright(paramGPDrawingArea);
  }
  
  public void DrawAll()
  {
    for (int i = 0; i < 11; i++)
    {
      if (this.active_array_[i] != null) {
        this.active_array_[i].Draw(this.da_);
      }
      if (this.inactive_array_[i] != null) {
        this.inactive_array_[i].Draw(this.da_);
      }
    }
  }
  
  public void UndrawAll()
  {
    for (int i = 0; i < 11; i++)
    {
      if (this.active_array_[i] != null) {
        this.active_array_[i].Undraw(this.da_);
      }
      if (this.inactive_array_[i] != null) {
        this.inactive_array_[i].Undraw(this.da_);
      }
    }
  }
  
  public void CheckGraphs(GPPoint paramGPPoint)
  {
    this.click_good_ = false;
    for (int i = 0; i < 11; i++) {
      if (this.active_array_[i] != null)
      {
        boolean bool = this.active_array_[i].PointInGraph(paramGPPoint);
        if (bool)
        {
          this.click_good_ = true;
          this.current_active_ = i;
        }
      }
    }
  }
  
  public boolean CheckSliders(GPPoint paramGPPoint, int paramInt)
  {
    boolean bool = false;
    for (int i = 0; i < 2; i++) {
      if (this.slider_array_[i] != null)
      {
        bool = this.slider_array_[i].mouseStuff(paramGPPoint, paramInt);
        if (bool)
        {
          UpdateRelationships();
          return true;
        }
      }
    }
    return false;
  }
  
  public void ButtonDown(GPPoint paramGPPoint, int paramInt)
  {
    if (CheckSliders(paramGPPoint, 0)) {
      return;
    }
    CheckGraphs(paramGPPoint);
    if (this.click_good_)
    {
      this.current_index_ = this.active_array_[this.current_active_].ConvertPointToIndex(paramGPPoint);
      this.current_value_ = this.active_array_[this.current_active_].ConvertPointToValue(paramGPPoint);
      this.old_index_ = this.current_index_;
      this.old_value_ = this.current_value_;
      UpdateRelationships();
    }
  }
  
  public void ButtonMotion(GPPoint paramGPPoint, int paramInt)
  {
    if (CheckSliders(paramGPPoint, 1)) {
      return;
    }
    if (this.click_good_)
    {
      this.old_index_ = this.current_index_;
      this.old_value_ = this.current_value_;
      this.current_index_ = this.active_array_[this.current_active_].ConvertPointToIndex(paramGPPoint);
      this.current_value_ = this.active_array_[this.current_active_].ConvertPointToValue(paramGPPoint);
      UpdateRelationships();
    }
  }
  
  public void ButtonUp(GPPoint paramGPPoint, int paramInt)
  {
    if (CheckSliders(paramGPPoint, 2)) {
      return;
    }
    this.click_good_ = false;
  }
  
  void MaxHeight()
  {
    this.click_good_ = false;
    this.old_index_ = 0;
    this.current_index_ = this.max_graph_width_;
    this.old_value_ = 1.0D;
    this.current_value_ = 1.0D;
    for (int i = 0; i < 11; i++) {
      if (this.active_array_[i] != null)
      {
        this.current_active_ = i;
        UpdateRelationships();
      }
    }
  }
  
  void MinHeight()
  {
    this.click_good_ = false;
    this.old_index_ = 0;
    this.current_index_ = this.max_graph_width_;
    this.old_value_ = 0.0D;
    this.current_value_ = 0.0D;
    for (int i = 0; i < 11; i++) {
      if (this.active_array_[i] != null)
      {
        this.current_active_ = i;
        UpdateRelationships();
      }
    }
  }
  
  abstract void UpdateRelationships();
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/CL/CLManager.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */