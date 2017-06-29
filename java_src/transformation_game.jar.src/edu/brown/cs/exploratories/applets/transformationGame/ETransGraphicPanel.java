package edu.brown.cs.exploratories.applets.transformationGame;

import edu.brown.cs.exploratories.applets.transformationGame.tools.dragAndDrop.EDragDropSuperParent;
import edu.brown.cs.exploratories.applets.transformationGame.tools.dragAndDrop.EDragDropTool;
import edu.brown.cs.exploratories.applets.transformationGame.tools.dragAndDrop.EDragDroppable;
import java.awt.BorderLayout;
import java.io.PrintStream;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JRootPane;

public class ETransGraphicPanel
  extends EDragDropSuperParent
  implements ETransConst
{
  private ETransFactory factory;
  private ETransPlayArea playArea;
  private ETransPaletteArea paletteArea;
  private ETransPipelineArea pipelineArea;
  private EDragDropTool ddTool;
  private ETransPalettePiece currentPiece;
  private JLayeredPane layeredPane;
  private Integer GRAPHIC_PANEL_NORMAL_LAYER = new Integer(0);
  private Integer GRAPHIC_PANEL_DRAG_LAYER = new Integer(400);
  
  public ETransGraphicPanel(ETransFactory paramETransFactory)
  {
    this.factory = paramETransFactory;
    setSize(500, 500);
    this.playArea = this.factory.getPlayArea();
    this.paletteArea = this.factory.getPaletteArea();
    this.pipelineArea = this.factory.getPipelineArea();
    this.ddTool = this.factory.getDragDropTool();
    this.ddTool.setParentArea(this);
    this.ddTool.addDropArea(this.paletteArea);
    this.ddTool.addDropArea(this.pipelineArea);
    JRootPane localJRootPane = new JRootPane();
    setLayout(new BorderLayout());
    add(localJRootPane, "Center");
    this.layeredPane = new JLayeredPane();
    localJRootPane.setLayeredPane(this.layeredPane);
    this.layeredPane.setLayout(null);
    this.layeredPane.add(this.playArea, this.GRAPHIC_PANEL_NORMAL_LAYER);
    this.playArea.setBounds(0, 0, 400, 440);
    this.layeredPane.add(this.paletteArea, this.GRAPHIC_PANEL_NORMAL_LAYER);
    this.paletteArea.setBounds(400, 0, 100, 440);
    this.layeredPane.add(this.pipelineArea, this.GRAPHIC_PANEL_NORMAL_LAYER);
    this.pipelineArea.setBounds(0, 440, 500, 60);
  }
  
  public void addDragDroppable(EDragDroppable paramEDragDroppable)
  {
    JComponent localJComponent = (JComponent)paramEDragDroppable;
    this.layeredPane.add(localJComponent, this.GRAPHIC_PANEL_DRAG_LAYER);
    try
    {
      ETransPalettePiece localETransPalettePiece = (ETransPalettePiece)localJComponent;
      this.currentPiece = localETransPalettePiece;
    }
    catch (ClassCastException localClassCastException)
    {
      System.out.println("Problem adding DragDroppable piece to parentArea");
    }
  }
  
  public void removeDragDroppable(EDragDroppable paramEDragDroppable)
  {
    JComponent localJComponent = (JComponent)paramEDragDroppable;
    this.layeredPane.remove(localJComponent);
    this.currentPiece = null;
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransGraphicPanel.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */