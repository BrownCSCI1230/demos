package GP;

public abstract class GPMouseInteractor
  extends GPEventInteractor
{
  public void ButtonDown(GPPoint paramGPPoint, int paramInt) {}
  
  public void ButtonMotion(GPPoint paramGPPoint, int paramInt) {}
  
  public void ButtonUp(GPPoint paramGPPoint, int paramInt) {}
  
  void ButtonDownCallback(GPPoint paramGPPoint, int paramInt)
  {
    GPEventLock.Lock();
    ButtonDown(paramGPPoint, paramInt);
    GPEventLock.Unlock();
  }
  
  void ButtonMotionCallback(GPPoint paramGPPoint, int paramInt)
  {
    GPEventLock.Lock();
    ButtonMotion(paramGPPoint, paramInt);
    GPEventLock.Unlock();
  }
  
  void ButtonUpCallback(GPPoint paramGPPoint, int paramInt)
  {
    GPEventLock.Lock();
    ButtonUp(paramGPPoint, paramInt);
    GPEventLock.Unlock();
  }
}


/* Location:              /Users/masonbartle/Downloads/metamers.jar!/GP/GPMouseInteractor.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */