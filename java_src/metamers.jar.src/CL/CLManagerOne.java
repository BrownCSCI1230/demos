package CL;

import GP.GPDrawingArea;
import GP.GPPoint;

class CLManagerOne
  extends CLManager
{
  static final int II1_GRAPH_WIDTH = 350;
  static final int II1_GRAPH_HEIGHT = 100;
  static final int II1_RESULT_HEIGHT = 370;
  static final int II1_RESULT_BAR_WIDTH = 50;
  static final int II1_ONLY_ACTIVE = 0;
  static final int II1_ONLY_FILTER = 0;
  static final int II1_VISUAL_PRODUCT = 0;
  static final int II1_VISUAL_FILTER = 1;
  static final int II1_VISUAL_RESULT = 2;
  static final int II1_RESULT_INDEX = 0;
  
  public CLManagerOne(GPDrawingArea paramGPDrawingArea)
  {
    super(paramGPDrawingArea, 350);
    this.active_array_[0] = new CLVisualGraph(350, 100, new GPPoint(50, 30), "Input", 1);
    this.active_array_[1] = new CLVisualGraph(350, 100, new GPPoint(50, 165), "Filter", 0);
    this.inactive_array_[0] = new CLVisualGraph(350, 100, new GPPoint(50, 300), "Product", 0);
    this.inactive_array_[2] = new CLResultGraph(1, 370, new GPPoint(465, 30), 50);
    this.filter_array_[0] = new CLFilter(350, 1);
    for (int i = 0; i < 350; i++) {
      this.active_array_[1].SetElement(i, this.filter_array_[0].GetElement(i));
    }
  }
  
  public void UpdateRelationships()
  {
    this.active_array_[this.current_active_].SetRange(this.old_index_, this.current_index_, this.old_value_, this.current_value_);
    this.inactive_array_[0].MultiplyRange(this.old_index_, this.current_index_, this.active_array_[0], this.active_array_[1]);
    double d = this.active_array_[1].GetSum();
    if (d != 0.0D) {
      this.inactive_array_[2].SetElement(0, this.inactive_array_[0].GetSum() / d);
    } else {
      this.inactive_array_[2].SetElement(0, 1.0D);
    }
    this.active_array_[this.current_active_].DrawBarRange(this.da_, this.old_index_, this.current_index_);
    this.inactive_array_[0].DrawBarRange(this.da_, this.old_index_, this.current_index_);
    this.inactive_array_[2].DrawBarRange(this.da_, 0, 0);
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/CL/CLManagerOne.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */