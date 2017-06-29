package edu.brown.cs.exploratories.applets.fft1DApp;

import edu.brown.cs.exploratories.components.Exploratory;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class FFT1DApp
  extends Exploratory
{
  protected SampImage samp_img;
  protected DTFunctionWindow spatial_;
  protected DTFunctionWindow freq_;
  protected ScanlineDTFunctionController scontroller_;
  protected FourierDTFunctionController fcontroller_;
  
  public FFT1DApp()
  {
    GridBagLayout localGridBagLayout = new GridBagLayout();
    GridBagConstraints localGridBagConstraints = new GridBagConstraints();
    getContentPane().setLayout(localGridBagLayout);
    this.samp_img = new SampImage(this);
    localGridBagConstraints.fill = 0;
    localGridBagConstraints.weightx = 0.0D;
    localGridBagConstraints.weighty = 0.0D;
    localGridBagConstraints.gridx = 1;
    localGridBagConstraints.gridy = 1;
    localGridBagConstraints.gridwidth = 1;
    localGridBagConstraints.gridheight = 1;
    localGridBagLayout.setConstraints(this.samp_img, localGridBagConstraints);
    getContentPane().add(this.samp_img);
    JComboBox localJComboBox = new JComboBox(this.samp_img.getImageList());
    localJComboBox.addActionListener(new ActionListener()
    {
      private final JComboBox val$cb;
      
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        ImageInfo localImageInfo = (ImageInfo)this.val$cb.getSelectedItem();
        FFT1DApp.this.samp_img.setImage(localImageInfo);
      }
    });
    localGridBagConstraints.fill = 0;
    localGridBagConstraints.weightx = 0.0D;
    localGridBagConstraints.weighty = 0.0D;
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 1;
    localGridBagConstraints.gridwidth = 1;
    localGridBagConstraints.gridheight = 1;
    localGridBagLayout.setConstraints(localJComboBox, localGridBagConstraints);
    getContentPane().add(localJComboBox);
    this.spatial_ = new DTFunctionWindow();
    this.scontroller_ = new ScanlineDTFunctionController(this);
    this.scontroller_.setFunctionWindow(this.spatial_);
    this.spatial_.setController(this.scontroller_);
    this.samp_img.setSampleController(this.scontroller_);
    localGridBagConstraints.fill = 1;
    localGridBagConstraints.weightx = 1.0D;
    localGridBagConstraints.weighty = 0.5D;
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 2;
    localGridBagConstraints.gridwidth = 2;
    localGridBagConstraints.gridheight = 1;
    localGridBagLayout.setConstraints(this.spatial_, localGridBagConstraints);
    getContentPane().add(this.spatial_);
    this.freq_ = new DTFunctionWindow();
    this.fcontroller_ = new FourierDTFunctionController(this);
    this.fcontroller_.setFunctionWindow(this.freq_);
    this.freq_.setController(this.fcontroller_);
    this.scontroller_.setFController(this.fcontroller_);
    localGridBagConstraints.fill = 1;
    localGridBagConstraints.weightx = 1.0D;
    localGridBagConstraints.weighty = 0.5D;
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 3;
    localGridBagConstraints.gridwidth = 2;
    localGridBagConstraints.gridheight = 1;
    localGridBagLayout.setConstraints(this.freq_, localGridBagConstraints);
    getContentPane().add(this.freq_);
    JLabel localJLabel = new JLabel("Daniel L. Gould <dlg@cs.brown.edu>");
    localGridBagConstraints.fill = 2;
    localGridBagConstraints.weightx = 1.0D;
    localGridBagConstraints.weighty = 0.0D;
    localGridBagConstraints.gridx = 0;
    localGridBagConstraints.gridy = 4;
    localGridBagConstraints.gridwidth = 2;
    localGridBagConstraints.gridheight = 1;
    localGridBagLayout.setConstraints(localJLabel, localGridBagConstraints);
    getContentPane().add(localJLabel);
    setPackingLayout(true);
  }
  
  public ScanlineDTFunctionController getController()
  {
    return this.scontroller_;
  }
  
  public SampImage getImage()
  {
    return this.samp_img;
  }
  
  public static void main(String[] paramArrayOfString)
  {
    Exploratory.main(paramArrayOfString, FFT1DApp.class);
  }
}


/* Location:              /Users/masonbartle/Downloads/1d_fast_fourier_transform.jar!/edu/brown/cs/exploratories/applets/fft1DApp/FFT1DApp.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */