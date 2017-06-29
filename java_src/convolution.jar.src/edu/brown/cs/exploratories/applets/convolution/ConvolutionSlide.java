package edu.brown.cs.exploratories.applets.convolution;

import edu.brown.cs.exploratories.components.Exploratory;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.border.EtchedBorder;

public class ConvolutionSlide
  extends Exploratory
  implements ActionListener
{
  public static transient ConvolutionSlide __instance;
  protected CTFunctionWindow f_;
  protected CTFunctionWindow g_;
  protected CTFunctionWindow prod_;
  protected CTFunctionWindow conv_;
  protected JSlider slider_;
  protected JButton norm_btn;
  protected JButton f_clr_btn;
  protected JButton g_clr_btn;
  protected EditableCTFunctionController fcontroller_;
  protected MovableCTFunctionController gcontroller_;
  protected ProductCTFunctionController pcontroller_;
  protected ConvCTFunctionController ccontroller_;
  
  public ConvolutionSlide()
  {
    __instance = this;
    getContentPane().setLayout(new BoxLayout(getContentPane(), 1));
    this.f_clr_btn = new JButton("Clear");
    this.f_clr_btn.setBorder(new EtchedBorder());
    this.f_clr_btn.addActionListener(this);
    getContentPane().add(this.f_clr_btn);
    this.f_ = new CTFunctionWindow();
    getContentPane().add(this.f_);
    this.fcontroller_ = new EditableCTFunctionController();
    this.f_.setController(this.fcontroller_);
    getContentPane().add(new JSeparator());
    JPanel localJPanel = new JPanel();
    this.norm_btn = new JButton("Normalize");
    this.norm_btn.setBorder(new EtchedBorder(Color.yellow, Color.darkGray));
    this.norm_btn.addActionListener(this);
    localJPanel.add(this.norm_btn);
    this.g_clr_btn = new JButton("Clear");
    this.g_clr_btn.setBorder(new EtchedBorder());
    this.g_clr_btn.addActionListener(this);
    localJPanel.add(this.g_clr_btn);
    getContentPane().add(localJPanel);
    this.g_ = new CTFunctionWindow();
    getContentPane().add(this.g_);
    this.gcontroller_ = new MovableCTFunctionController();
    this.g_.setController(this.gcontroller_);
    this.slider_ = new JSlider();
    int i = this.g_.getChartWidth() / 2;
    this.slider_.setMinimum(65273);
    this.slider_.setMaximum(263);
    this.slider_.setValue(0);
    this.slider_.setPaintTicks(true);
    this.slider_.setMajorTickSpacing(263);
    getContentPane().add(this.slider_);
    this.gcontroller_.setSlider(this.slider_);
    getContentPane().add(new JSeparator());
    this.prod_ = new CTFunctionWindow();
    this.prod_.setEnabled(false);
    getContentPane().add(this.prod_);
    this.pcontroller_ = new ProductCTFunctionController();
    this.prod_.setController(this.pcontroller_);
    this.pcontroller_.setF(this.fcontroller_);
    this.pcontroller_.setG(this.gcontroller_);
    this.conv_ = new CTFunctionWindow();
    this.conv_.setEnabled(false);
    getContentPane().add(this.conv_);
    this.ccontroller_ = new ConvCTFunctionController();
    this.conv_.setController(this.ccontroller_);
    this.ccontroller_.setF(this.fcontroller_);
    this.ccontroller_.setG(this.gcontroller_);
    getContentPane().add(new JLabel("Daniel L. Gould <dlg@cs.brown.edu>"));
    this.f_.setTitle("f(x) [Function to be Convolved]");
    this.g_.setTitle("g(x) [Convolution Filter]");
    this.slider_.setToolTipText("Slide Filter to Convolve");
    this.prod_.setTitle("f(x)g(x) [Product]");
    this.conv_.setTitle("f(x)*g(x) [Convolution]");
  }
  
  public void resetPositions()
  {
    this.gcontroller_.resetPosition();
    this.pcontroller_.resetPosition();
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
    int i = this.g_.getChartWidth() / 2 - 1;
    this.slider_.setMinimum(-i);
    this.slider_.setMaximum(i);
    this.slider_.setMajorTickSpacing(i + 1);
  }
  
  public void fixApplet() {}
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getSource().equals(this.norm_btn)) {
      this.gcontroller_.normalize();
    } else if (paramActionEvent.getSource().equals(this.f_clr_btn)) {
      this.fcontroller_.clear();
    } else if (paramActionEvent.getSource().equals(this.g_clr_btn)) {
      this.gcontroller_.clear();
    } else {
      System.out.println("foo");
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/convolution.jar!/edu/brown/cs/exploratories/applets/convolution/ConvolutionSlide.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */