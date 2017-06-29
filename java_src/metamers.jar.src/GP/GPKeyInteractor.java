package GP;

import java.io.PrintStream;

public abstract class GPKeyInteractor
  extends GPEventInteractor
{
  int index_;
  static String[] keyNames_ = { "HOME", "END", "PAGE UP", "PAGE DOWN", "UP", "DOWN", "LEFT", "RIGHT", "RETURN", "TAB", "SPACE" };
  static int[] keys_ = { 1256, 1257, 1258, 1259, 1260, 1261, 1262, 1263, 10, 9, 32 };
  
  public GPKeyInteractor(String paramString)
  {
    String str = paramString.toUpperCase().trim();
    if (str.length() == 1)
    {
      this.index_ = str.charAt(0);
    }
    else
    {
      boolean bool = false;
      for (int i = 0; (i < keyNames_.length) && (!bool); i++) {
        bool = keyNames_[i].equals(str);
      }
      if (bool)
      {
        this.index_ = keys_[(i - 1)];
      }
      else
      {
        System.out.println("\"" + paramString + "\" is not a valid special key name. " + "Special keys are:");
        for (i = 0; i < keyNames_.length; i++) {
          System.out.println("   " + keyNames_[i]);
        }
        System.exit(0);
      }
    }
  }
  
  void KeyCallback()
  {
    GPEventLock.Lock();
    Keypress();
    GPEventLock.Unlock();
  }
  
  public abstract void Keypress();
  
  int GetIndex()
  {
    return this.index_;
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/GP/GPKeyInteractor.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */