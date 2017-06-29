package edu.brown.cs.exploratories.components.swing;

import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class ImageLibrary
{
  private Class loaderClass = null;
  private String propertiesFilename = "imageLibrary.properties";
  private Properties nameToFilename = new Properties();
  private Hashtable nameToImage = new Hashtable();
  protected static final Component component = new Component() {};
  protected final MediaTracker tracker = new MediaTracker(component);
  
  public Class getLoaderClass()
  {
    return this.loaderClass;
  }
  
  public void setLoaderClass(String paramString)
    throws ClassNotFoundException
  {
    setLoaderClass(Class.forName(paramString));
  }
  
  public void setLoaderClass(Class paramClass)
  {
    this.loaderClass = paramClass;
    loadImages();
  }
  
  public String getPropertiesFilename()
  {
    return this.propertiesFilename;
  }
  
  public void setPropertiesFilename(String paramString)
  {
    this.propertiesFilename = paramString;
    loadImages();
  }
  
  public Image getImage(String paramString)
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    Image localImage = (Image)this.nameToImage.get(paramString);
    if (localImage == null)
    {
      String str = this.nameToFilename.getProperty(paramString);
      if (str != null)
      {
        URL localURL = this.loaderClass.getResource(str);
        if (localURL != null)
        {
          localImage = Toolkit.getDefaultToolkit().getImage(localURL);
          if (localImage != null)
          {
            synchronized (this.tracker)
            {
              this.tracker.addImage(localImage, 0);
              try
              {
                this.tracker.waitForID(0, 0L);
              }
              catch (InterruptedException localInterruptedException)
              {
                System.out.println("INTERRUPTED while loading Image");
              }
              this.tracker.removeImage(localImage, 0);
            }
            this.nameToImage.put(paramString, localImage);
          }
        }
      }
    }
    return localImage;
  }
  
  public Enumeration getImageNames()
  {
    return this.nameToFilename.keys();
  }
  
  public JMenu createJMenu()
  {
    return createJMenu("Images");
  }
  
  public JMenu createJMenu(String paramString)
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    JMenu localJMenu = new JMenu(paramString);
    Enumeration localEnumeration = getImageNames();
    while (localEnumeration.hasMoreElements())
    {
      String str = (String)localEnumeration.nextElement();
      JMenuItem localJMenuItem = new JMenuItem(str);
      localJMenu.add(localJMenuItem);
    }
    return localJMenu;
  }
  
  private void loadImages()
  {
    this.nameToFilename.clear();
    this.nameToImage.clear();
    if ((this.propertiesFilename != null) && (this.loaderClass != null)) {
      try
      {
        URL localURL = this.loaderClass.getResource(this.propertiesFilename);
        if (localURL != null)
        {
          InputStream localInputStream = localURL.openStream();
          this.nameToFilename.load(localInputStream);
        }
      }
      catch (IOException localIOException)
      {
        this.nameToFilename.clear();
        this.nameToImage.clear();
      }
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/filter_shape.jar!/edu/brown/cs/exploratories/components/swing/ImageLibrary.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */