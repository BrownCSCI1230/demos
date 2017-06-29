package edu.brown.cs.exploratories.applets.colorMixing;

import edu.brown.cs.exploratories.components.Exploratory;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class SubtractiveColorMixing
  extends Exploratory
{
  private static final String RESOURCES_PATH = "subtractiveColorMixingResources";
  private static final String BACKGROUND_IMAGES_PROPERTIES_FILE = "subtractiveMixingBackgroundImages.properties";
  private SubtractiveMixingContentPanel contentPanel = new SubtractiveMixingContentPanel();
  private Properties backgroundImages;
  private Hashtable menuItemsToImages;
  
  public SubtractiveColorMixing()
  {
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(this.contentPanel, "Center");
    loadBackgroundImages();
    JMenu localJMenu = new JMenu("Canvases");
    localJMenu.setMnemonic('C');
    JMenuItem localJMenuItem1 = new JMenuItem("Blank");
    localJMenuItem1.setMnemonic('B');
    localJMenuItem1.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        PaintCanvas localPaintCanvas = SubtractiveColorMixing.this.contentPanel.getPaintCanvas();
        localPaintCanvas.eraseCanvas();
        localPaintCanvas.setBackgroundImage(null);
      }
    });
    localJMenu.add(localJMenuItem1);
    this.menuItemsToImages = new Hashtable();
    ActionListener local2 = new ActionListener()
    {
      public void actionPerformed(ActionEvent paramAnonymousActionEvent)
      {
        Image localImage = (Image)SubtractiveColorMixing.this.menuItemsToImages.get(paramAnonymousActionEvent.getSource());
        PaintCanvas localPaintCanvas = SubtractiveColorMixing.this.contentPanel.getPaintCanvas();
        localPaintCanvas.eraseCanvas();
        localPaintCanvas.setBackgroundImage(localImage);
      }
    };
    Enumeration localEnumeration = this.backgroundImages.keys();
    while (localEnumeration.hasMoreElements())
    {
      localObject = (String)localEnumeration.nextElement();
      String str = (String)this.backgroundImages.get(localObject);
      JMenuItem localJMenuItem2 = new JMenuItem((String)localObject);
      URL localURL = getClass().getResource("subtractiveColorMixingResources/" + str);
      if (localURL != null)
      {
        Image localImage = Toolkit.getDefaultToolkit().getImage(localURL);
        this.menuItemsToImages.put(localJMenuItem2, localImage);
        localJMenuItem2.addActionListener(local2);
        localJMenu.add(localJMenuItem2);
      }
      else
      {
        System.err.println("Error: unable to load canvas: " + str);
      }
    }
    Object localObject = super.getJMenuBar();
    ((JMenuBar)localObject).add(localJMenu);
    setSize(800, 600);
    this.contentPanel.getPaintCanvas().eraseCanvas();
  }
  
  private void loadBackgroundImages()
  {
    if (this.backgroundImages == null)
    {
      this.backgroundImages = new Properties();
      InputStream localInputStream = getClass().getResourceAsStream("subtractiveColorMixingResources/subtractiveMixingBackgroundImages.properties");
      if (localInputStream != null) {
        try
        {
          this.backgroundImages.load(localInputStream);
        }
        catch (IOException localIOException)
        {
          System.err.println("Error loading background images properties file");
          this.backgroundImages.clear();
        }
      }
    }
  }
  
  public static void main(String[] paramArrayOfString)
  {
    Exploratory.main(paramArrayOfString, SubtractiveColorMixing.class);
  }
}


/* Location:              /Users/masonbartle/Downloads/subtractive_color_mixing.jar!/edu/brown/cs/exploratories/applets/colorMixing/SubtractiveColorMixing.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */