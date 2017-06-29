package edu.brown.cs.exploratories.applets.twoBoxConvolution;

import edu.brown.cs.exploratories.components.Exploratory;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JSlider;

public class ConvolutionSlide
  extends Exploratory
{
  public static transient ConvolutionSlide __instance;
  protected DBFunctionWindow f_;
  protected CTFunctionWindow conv_;
  protected JSlider slider_;
  protected MovableDBFunctionController fcontroller_;
  protected ConvDBFunctionController ccontroller_;
  
  public static void main(String[] paramArrayOfString)
  {
    Exploratory.main(paramArrayOfString, ConvolutionSlide.class);
  }
  
  public ConvolutionSlide()
  {
    __instance = this;
    getContentPane().setLayout(new BoxLayout(getContentPane(), 1));
    this.f_ = new DBFunctionWindow();
    getContentPane().add(this.f_);
    this.fcontroller_ = new MovableDBFunctionController();
    this.f_.setController(this.fcontroller_);
    getContentPane().add(new JSeparator());
    this.slider_ = new JSlider();
    int i = this.f_.getChartWidth() / 2;
    this.slider_.setMinimum(65273);
    this.slider_.setMaximum(263);
    this.slider_.setValue(65273);
    this.slider_.setPaintTicks(true);
    this.slider_.setMajorTickSpacing(263);
    getContentPane().add(this.slider_);
    this.fcontroller_.setSlider(this.slider_);
    this.conv_ = new CTFunctionWindow();
    this.conv_.setEnabled(false);
    getContentPane().add(this.conv_);
    this.ccontroller_ = new ConvDBFunctionController();
    this.conv_.setController(this.ccontroller_);
    this.ccontroller_.setController(this.fcontroller_);
    getContentPane().add(new JLabel("Daniel L. Gould <dlg@cs.brown.edu>"));
    this.f_.setTitle("f(x) [Function to be Convolved]");
    this.slider_.setToolTipText("Slide Filter to Convolve");
    this.conv_.setTitle("f(x)*g(x) [Convolution]");
  }
  
  public void resetPositions()
  {
    this.ccontroller_.resetPosition();
  }
  
  public void setSlidable(boolean paramBoolean)
  {
    this.slider_.setEnabled(paramBoolean);
    if (paramBoolean) {
      this.slider_.setCursor(new Cursor(0));
    } else {
      this.slider_.setCursor(new Cursor(3));
    }
  }
  
  public void fixSlider()
  {
    int i = this.f_.getChartWidth() / 2 - 1;
    this.slider_.setMinimum(-i);
    this.slider_.setMaximum(i);
    this.slider_.setMajorTickSpacing(i + 1);
  }
  
  public void fixApplet() {}
  
  public void actionPerformed(ActionEvent paramActionEvent) {}
}


/* Location:              /Users/masonbartle/Downloads/two_box_convolution.jar!/edu/brown/cs/exploratories/applets/twoBoxConvolution/ConvolutionSlide.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */