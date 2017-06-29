package edu.brown.cs.exploratories.applets.threeStepScaling;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ImageMouseListener
  implements MouseListener, MouseMotionListener
{
  InsetImageViewer displayer;
  App app;
  
  public App getApp()
  {
    return this.app;
  }
  
  public void setApp(App paramApp)
  {
    this.app = paramApp;
  }
  
  public InsetImageViewer getViewer()
  {
    return this.displayer;
  }
  
  public void setViewer(InsetImageViewer paramInsetImageViewer)
  {
    this.displayer = paramInsetImageViewer;
  }
  
  public void mouseClicked(MouseEvent paramMouseEvent)
  {
    tellViewer(paramMouseEvent);
  }
  
  public void mouseEntered(MouseEvent paramMouseEvent) {}
  
  public void mouseExited(MouseEvent paramMouseEvent) {}
  
  public void mousePressed(MouseEvent paramMouseEvent) {}
  
  public void mouseReleased(MouseEvent paramMouseEvent) {}
  
  public void mouseMoved(MouseEvent paramMouseEvent) {}
  
  public void mouseDragged(MouseEvent paramMouseEvent)
  {
    tellViewer(paramMouseEvent);
  }
  
  public boolean isInsidePicture(Point paramPoint)
  {
    int i = getApp().getBase();
    Rectangle localRectangle = this.displayer.getImageRect();
    return (paramPoint.x > localRectangle.x) && (paramPoint.x < localRectangle.x + localRectangle.width - i) && (paramPoint.y > localRectangle.y) && (paramPoint.y < localRectangle.y + localRectangle.height - i);
  }
  
  public void tellViewer(MouseEvent paramMouseEvent)
  {
    Point localPoint = paramMouseEvent.getPoint();
    if (isInsidePicture(localPoint) == true)
    {
      this.displayer.setMouseFlag(true);
      int i = this.displayer.getImageRect().x;
      int j = this.displayer.getImageRect().y;
      this.displayer.setCurrentX(localPoint.x - i);
      this.displayer.setCurrentY(localPoint.y - j);
      this.displayer.paint(this.displayer.getGraphics());
      InsetMultipleImagesViewer localInsetMultipleImagesViewer = getApp().getProcessViewer();
      localInsetMultipleImagesViewer.paint(localInsetMultipleImagesViewer.getGraphics());
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/three_step_scaling.jar!/edu/brown/cs/exploratories/applets/threeStepScaling/ImageMouseListener.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */