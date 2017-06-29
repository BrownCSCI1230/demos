package edu.brown.cs.exploratories.applets.threeStepScaling;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class PixelMouseListener
  implements MouseListener, MouseMotionListener
{
  InsetMultipleImagesViewer displayer;
  
  public InsetMultipleImagesViewer getViewer()
  {
    return this.displayer;
  }
  
  public void setViewer(InsetMultipleImagesViewer paramInsetMultipleImagesViewer)
  {
    this.displayer = paramInsetMultipleImagesViewer;
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
  
  public ImageRectangle getRect(Point paramPoint)
  {
    ImageRectangle localImageRectangle = null;
    for (int i = 0; i < 3; i++) {
      if ((paramPoint.x > this.displayer.getImageRect(i).x) && (paramPoint.x < this.displayer.getImageRect(i).x + this.displayer.getImageRect(i).width) && (paramPoint.y > this.displayer.getImageRect(i).y) && (paramPoint.y < this.displayer.getImageRect(i).y + this.displayer.getImageRect(i).height)) {
        localImageRectangle = this.displayer.getImageRect(i);
      }
    }
    return localImageRectangle;
  }
  
  public void tellViewer(MouseEvent paramMouseEvent)
  {
    this.displayer.setMouseFlag(true);
    Point localPoint = paramMouseEvent.getPoint();
    int i = localPoint.x;
    int j = localPoint.y;
    ImageRectangle localImageRectangle = getRect(localPoint);
    this.displayer.setCurrentRect(localImageRectangle);
    if (localImageRectangle != null)
    {
      i -= localImageRectangle.x;
      i -= i % this.displayer.getPixelSize();
      i += localImageRectangle.x;
      this.displayer.setCurrentColumn(i);
      j -= localImageRectangle.y;
      j -= j % this.displayer.getPixelSize();
      j += localImageRectangle.y;
      this.displayer.setCurrentRow(j);
    }
    this.displayer.paint(this.displayer.getGraphics());
  }
}


/* Location:              /Users/masonbartle/Downloads/three_step_scaling.jar!/edu/brown/cs/exploratories/applets/threeStepScaling/PixelMouseListener.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */