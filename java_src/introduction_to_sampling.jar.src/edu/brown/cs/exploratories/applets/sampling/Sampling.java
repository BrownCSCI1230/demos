package edu.brown.cs.exploratories.applets.sampling;

import edu.brown.cs.exploratories.components.Exploratory;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Sampling
  extends Exploratory
  implements ActionListener
{
  protected SampImage samp_img;
  protected DTFunctionWindow s_;
  protected SampDTFunctionController scontroller_;
  protected JRadioButton ps_btn;
  protected JRadioButton as_btn;
  protected ButtonGroup bg_;
  
  public static void main(String[] paramArrayOfString)
  {
    Exploratory.main(paramArrayOfString, Sampling.class);
  }
  
  public Sampling()
  {
    getContentPane().setLayout(new BoxLayout(getContentPane(), 1));
    this.samp_img = new SampImage(this);
    getContentPane().add(this.samp_img);
    JPanel localJPanel = new JPanel();
    this.bg_ = new ButtonGroup();
    this.ps_btn = new JRadioButton("Point Sample", true);
    localJPanel.add(this.ps_btn);
    this.bg_.add(this.ps_btn);
    this.ps_btn.addActionListener(this);
    getContentPane().add(localJPanel);
    this.s_ = new DTFunctionWindow();
    getContentPane().add(this.s_);
    this.scontroller_ = new SampDTFunctionController(this);
    this.scontroller_.setFunctionWindow(this.s_);
    this.s_.setController(this.scontroller_);
    this.samp_img.setSampleController(this.scontroller_);
    JLabel localJLabel = new JLabel("Daniel L. Gould <dlg@cs.brown.edu>");
    getContentPane().add(localJLabel);
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    if (paramActionEvent.getSource().equals(this.ps_btn)) {
      this.samp_img.setPointSample(true);
    } else if (paramActionEvent.getSource().equals(this.as_btn)) {
      this.samp_img.setPointSample(false);
    } else {
      System.out.println("foo");
    }
  }
  
  public SampDTFunctionController getController()
  {
    return this.scontroller_;
  }
  
  public SampImage getImage()
  {
    return this.samp_img;
  }
}


/* Location:              /Users/masonbartle/Downloads/introduction_to_sampling.jar!/edu/brown/cs/exploratories/applets/sampling/Sampling.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */