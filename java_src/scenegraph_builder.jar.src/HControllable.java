public abstract interface HControllable
{
  public abstract HPosition getTransform();
  
  public abstract boolean rotateActive();
  
  public abstract boolean scaleActive();
  
  public abstract boolean translateActive();
  
  public abstract void updateTransform(HPosition paramHPosition);
}


/* Location:              /Users/masonbartle/Downloads/scenegraph_builder.jar!/HControllable.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */