package edu.brown.cs.exploratories.components.awt.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;

public class ActionListenerSupport
  implements Serializable
{
  private transient HashSet actionListeners = null;
  private transient Object source;
  
  public ActionListenerSupport(Object paramObject)
  {
    setSource(paramObject);
  }
  
  public Object getSource()
  {
    return this.source;
  }
  
  public void setSource(Object paramObject)
  {
    if (paramObject == null) {
      throw new IllegalArgumentException("Null reference passed to param val in ActionListenerSupport.setSource()");
    }
    this.source = paramObject;
  }
  
  public Iterator getListeners()
  {
    if (this.actionListeners != null) {
      return this.actionListeners.iterator();
    }
    return null;
  }
  
  public void addActionListener(ActionListener paramActionListener)
  {
    if (paramActionListener != null)
    {
      if (this.actionListeners == null) {
        this.actionListeners = new HashSet();
      }
      this.actionListeners.add(paramActionListener);
    }
  }
  
  public void removeActionListener(ActionListener paramActionListener)
  {
    if ((paramActionListener != null) && (this.actionListeners != null)) {
      this.actionListeners.remove(paramActionListener);
    }
  }
  
  public void fireActionEvent(int paramInt, String paramString)
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    fireActionEvent(paramInt, paramString, 0);
  }
  
  public void fireActionEvent(int paramInt1, String paramString, int paramInt2)
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    ActionEvent localActionEvent = new ActionEvent(getSource(), paramInt1, paramString, paramInt2);
    fireActionEvent(localActionEvent);
  }
  
  public void fireActionEvent(ActionEvent paramActionEvent)
  {
    if (paramActionEvent == null) {
      throw new NullPointerException();
    }
    if (this.actionListeners != null)
    {
      Iterator localIterator = this.actionListeners.iterator();
      ActionListener localActionListener = null;
      while (localIterator.hasNext())
      {
        localActionListener = (ActionListener)localIterator.next();
        if (localActionListener != null) {
          localActionListener.actionPerformed(paramActionEvent);
        }
      }
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/filter_shape.jar!/edu/brown/cs/exploratories/components/awt/event/ActionListenerSupport.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */