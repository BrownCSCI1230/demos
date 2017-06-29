package edu.brown.cs.exploratories.components.util;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;

public class Debug
{
  public static final String DEBUG_PROPERTIES_FILENAME = "Debug.properties";
  private static final String DEBUG_CLASSNAME = "edu.brown.cs.exploratories.components.util.Debug";
  private static final String DEFAULT_DEBUG_PROPERTY = "false";
  private static final char PACKAGE_SEPARATOR = '.';
  private static final char WILDCARD_CHAR = '*';
  private static final String NULL_PACKAGE = "";
  private static final Properties debugProperties = new Properties();
  
  public static final boolean getDebugMode(String paramString)
  {
    boolean bool = false;
    String str1 = debugProperties.getProperty(paramString, "false");
    Boolean localBoolean = new Boolean(str1);
    bool = localBoolean.booleanValue();
    if ((!bool) && (!debugProperties.containsKey(paramString)))
    {
      String str2 = getPackageName(paramString);
      if (str2 != "") {
        paramString = str2 + '.' + '*';
      } else {
        paramString = "*";
      }
      str1 = debugProperties.getProperty(paramString, "false");
      localBoolean = new Boolean(str1);
      bool = localBoolean.booleanValue();
    }
    return bool;
  }
  
  public static final synchronized void println(String paramString)
  {
    System.err.println(paramString);
    System.err.flush();
  }
  
  public static final synchronized void println(Object paramObject)
  {
    if (paramObject != null) {
      println(paramObject.toString());
    } else {
      println("null");
    }
  }
  
  public static final synchronized void println(String paramString1, String paramString2, String paramString3)
  {
    println(paramString1 + "." + paramString2 + ": " + paramString3);
  }
  
  public static final synchronized void println(Class paramClass, String paramString1, String paramString2)
  {
    println(paramClass.toString(), paramString1, paramString2);
  }
  
  private static final String getPackageName(String paramString)
  {
    int i = paramString.lastIndexOf('.');
    if (i == -1) {
      return "";
    }
    return paramString.substring(0, i);
  }
  
  static
  {
    try
    {
      Class localClass = Class.forName("edu.brown.cs.exploratories.components.util.Debug");
      InputStream localInputStream = localClass.getResourceAsStream("Debug.properties");
      if (localInputStream != null) {
        debugProperties.load(localInputStream);
      } else {
        throw new Exception("Properties file: Debug.properties not found");
      }
    }
    catch (Exception localException)
    {
      System.out.println("Exception caught in static block for class Debug: " + localException);
    }
  }
  
  public static abstract interface Debugable
  {
    public static final boolean GLOBAL_DEBUG = true;
  }
}


/* Location:              /Users/masonbartle/Downloads/filter_shape.jar!/edu/brown/cs/exploratories/components/util/Debug.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */