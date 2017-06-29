package GP;

class GPEventLock
{
  private static GPEventLock lock_ = new GPEventLock();
  private boolean locked_ = false;
  
  public static void Lock()
  {
    lock_.PrivateLock();
  }
  
  public static void Unlock()
  {
    lock_.PrivateUnlock();
  }
  
  private synchronized void PrivateLock()
  {
    while (this.locked_) {
      try
      {
        wait();
      }
      catch (InterruptedException localInterruptedException) {}
    }
    this.locked_ = true;
  }
  
  private synchronized void PrivateUnlock()
  {
    this.locked_ = false;
    notifyAll();
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/GP/GPEventLock.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */