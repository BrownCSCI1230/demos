package edu.brown.cs.exploratories.applets.combinedColorMixing.visualObjectSpace;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

public class ImageLoader
{
  private String packagePath;
  private String filename;
  private Properties properties;
  
  public ImageLoader(String paramString1, String paramString2)
  {
    this.packagePath = paramString1.trim();
    this.filename = paramString2.trim();
    this.properties = new Properties();
    loadImageIndexFile();
  }
  
  public String getImage(String paramString)
  {
    return this.properties.getProperty(paramString);
  }
  
  private void loadImageIndexFile()
  {
    Class localClass = getClass();
    String str2 = this.packagePath.startsWith("/") ? "" : "/";
    String str3 = this.packagePath.endsWith("/") ? "" : "/";
    String str1 = str2 + this.packagePath + str3 + this.filename;
    URL localURL = localClass.getResource(str1);
    if (localURL == null) {
      throw new Error("datafile path returned null URL");
    }
    try
    {
      this.properties.load(localURL.openStream());
    }
    catch (IOException localIOException)
    {
      throw new Error("Error loading data file.");
    }
    Enumeration localEnumeration = this.properties.propertyNames();
    while (localEnumeration.hasMoreElements())
    {
      String str4 = (String)localEnumeration.nextElement();
      String str5 = this.properties.getProperty(str4);
      str5 = str5.trim();
      str2 = this.packagePath.startsWith("/") ? "" : "/";
      String str6 = (str5.startsWith("/")) || (this.packagePath.endsWith("/")) ? "" : "/";
      str5 = str2 + this.packagePath + str6 + str5;
      this.properties.setProperty(str4, str5);
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/combined_color_mixing.jar!/edu/brown/cs/exploratories/applets/combinedColorMixing/visualObjectSpace/ImageLoader.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */