package edu.brown.cs.exploratories.applets.transformationGame;

import edu.brown.cs.exploratories.applets.transformationGame.tools.dragAndDrop.EDragDropArea;
import edu.brown.cs.exploratories.applets.transformationGame.tools.dragAndDrop.EDragDropTool;
import edu.brown.cs.exploratories.applets.transformationGame.tools.dragAndDrop.EDragDroppable;
import java.util.Vector;
import javax.swing.JComponent;

public class ETransPaletteArea
  extends EDragDropArea
  implements ETransConst
{
  private Vector pieceList;
  private ETransFactory factory;
  
  public ETransPaletteArea(ETransFactory paramETransFactory)
  {
    this.factory = paramETransFactory;
    this.pieceList = new Vector();
    setSize(100, 440);
    setBackground(ETransConst.E_TRANS_PALETTE_AREA_COLOR);
    setLayout(null);
  }
  
  public void addTransform(ETransform paramETransform)
  {
    ETransPalettePiece localETransPalettePiece = this.factory.getPalettePiece(paramETransform);
    this.pieceList.add(localETransPalettePiece);
    EDragDropTool localEDragDropTool = this.factory.getDragDropTool();
    localETransPalettePiece.addMouseListener(localEDragDropTool);
    localETransPalettePiece.addMouseMotionListener(localEDragDropTool);
    add(localETransPalettePiece);
  }
  
  public void arrangePieces()
  {
    for (int i = 0; i < this.pieceList.size(); i++)
    {
      ETransPalettePiece localETransPalettePiece = (ETransPalettePiece)this.pieceList.elementAt(i);
      int j = i * 50 + 10 + 20;
      localETransPalettePiece.setLocation(50, j);
    }
    repaint();
  }
  
  public void clearPieces()
  {
    for (int i = 0; i < this.pieceList.size(); i++)
    {
      ETransPalettePiece localETransPalettePiece = (ETransPalettePiece)this.pieceList.elementAt(i);
      remove(localETransPalettePiece);
    }
    this.pieceList.clear();
    repaint();
  }
  
  public void dragOutOfContainer(EDragDroppable paramEDragDroppable)
  {
    this.pieceList.remove(paramEDragDroppable);
    remove((JComponent)paramEDragDroppable);
    arrangePieces();
  }
  
  public void dropIntoContainer(EDragDroppable paramEDragDroppable)
  {
    this.pieceList.add(paramEDragDroppable);
    add((JComponent)paramEDragDroppable);
    arrangePieces();
  }
  
  public void resetPieces()
  {
    for (int i = 0; i < this.pieceList.size(); i++)
    {
      ETransPalettePiece localETransPalettePiece = (ETransPalettePiece)this.pieceList.elementAt(i);
      localETransPalettePiece.resetValue();
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransPaletteArea.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */