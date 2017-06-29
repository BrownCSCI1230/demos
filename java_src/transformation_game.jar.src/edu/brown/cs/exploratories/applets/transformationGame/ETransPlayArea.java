package edu.brown.cs.exploratories.applets.transformationGame;

import edu.brown.cs.exploratories.applets.transformationGame.gameparts.EAnimator;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D.Double;
import javax.swing.JPanel;

public class ETransPlayArea
  extends JPanel
  implements ETransConst
{
  private ETransFactory factory;
  private ETransGoalObject goal;
  private ETransPlayObject playObject;
  private ETransGhostObject ghost;
  private EAnimator animator;
  private String levelString;
  
  public ETransPlayArea(ETransFactory paramETransFactory)
  {
    this.factory = paramETransFactory;
    setSize(400, 440);
    setBackground(ETransConst.E_TRANS_PLAY_AREA_COLOR);
    this.goal = this.factory.getGoalObject();
    this.playObject = this.factory.getPlayObject();
    this.ghost = this.factory.getGhostObject();
    setLayout(null);
    add(this.ghost);
    add(this.playObject);
    add(this.goal);
    this.goal.setSize(getSize());
    this.playObject.setSize(getSize());
    this.ghost.setSize(getSize());
    this.animator = new EAnimator();
    this.animator.addAnimatable(this.factory.getTransformationManager());
    this.animator.addAnimatable(this.goal);
    this.animator.addAnimatable(this.playObject);
    this.animator.addAnimatable(this.ghost);
    this.animator.start();
  }
  
  public void paintComponent(Graphics paramGraphics)
  {
    super.paintComponent(paramGraphics);
    Graphics2D localGraphics2D = (Graphics2D)paramGraphics;
    paintAxes(localGraphics2D);
    localGraphics2D.setColor(ETransConst.E_TRANS_LEVEL_NUM_TEXT_COLOR);
    localGraphics2D.drawString(this.levelString, 10, 20);
  }
  
  protected void paintAxes(Graphics2D paramGraphics2D)
  {
    BasicStroke localBasicStroke = new BasicStroke(2.0F);
    paramGraphics2D.setStroke(localBasicStroke);
    paramGraphics2D.setColor(ETransConst.E_TRANS_AXES_COLOR);
    paramGraphics2D.draw(new Line2D.Double(20.0D, 220.0D, 380.0D, 220.0D));
    paramGraphics2D.draw(new Line2D.Double(200.0D, 20.0D, 200.0D, 420.0D));
    paramGraphics2D.setColor(ETransConst.E_TRANS_AXES_LABEL_COLOR);
    paramGraphics2D.drawString("y", 200, 20);
    paramGraphics2D.drawString("x", 380, 220);
  }
  
  public void update()
  {
    this.goal.update();
    this.playObject.update();
    this.ghost.update();
  }
  
  public void setString(String paramString)
  {
    this.levelString = paramString;
  }
  
  public void resetGameObjects()
  {
    update();
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransPlayArea.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */