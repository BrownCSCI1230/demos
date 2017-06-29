package edu.brown.cs.exploratories.applets.transformationGame;

import edu.brown.cs.exploratories.applets.transformationGame.tools.dragAndDrop.EDragDropArea;
import edu.brown.cs.exploratories.applets.transformationGame.tools.dragAndDrop.EDragDroppable;
import edu.brown.cs.exploratories.applets.transformationGame.tools.selection.ERadioSelectTool;
import edu.brown.cs.exploratories.applets.transformationGame.tools.selection.ERadioSelectable;
import edu.brown.cs.exploratories.applets.transformationGame.tools.selection.ESelectionListener;
import java.awt.Component;
import java.util.Vector;

public class ETransPipelineArea
  extends EDragDropArea
  implements ETransConst, ESelectionListener
{
  private Vector pieceList;
  private ETransformationManager transMan;
  private ETransFactory factory;
  private ETransAnchor anchor;
  private ERadioSelectTool selectTool;
  
  public ETransPipelineArea(ETransFactory paramETransFactory)
  {
    this.factory = paramETransFactory;
    setSize(500, 60);
    setBackground(ETransConst.E_TRANS_PIPELINE_AREA_COLOR);
    this.anchor = this.factory.getAnchor();
    setLayout(null);
    add(this.anchor);
    this.anchor.setLocation(450, 30);
    this.pieceList = new Vector();
    this.transMan = this.factory.getTransformationManager();
    this.selectTool = this.factory.getRadioSelectTool();
    this.anchor.addMouseListener(this.selectTool);
    this.anchor.addMouseMotionListener(this.selectTool);
    this.selectTool.addSelectionListener(this);
    this.selectTool.setSelected(this.anchor);
  }
  
  public void dragOutOfContainer(EDragDroppable paramEDragDroppable)
  {
    ETransPalettePiece localETransPalettePiece = (ETransPalettePiece)paramEDragDroppable;
    this.pieceList.remove(localETransPalettePiece);
    remove(localETransPalettePiece);
    localETransPalettePiece.removeMouseListener(this.selectTool);
    localETransPalettePiece.removeMouseMotionListener(this.selectTool);
    localETransPalettePiece.setRadioSelectable(false);
    arrangePieces();
    if (localETransPalettePiece == this.selectTool.getSelected()) {
      this.selectTool.setSelected(this.anchor);
    }
    ETransformList localETransformList = makeCurrentTransformList();
    this.transMan.setCurrentTransformList(localETransformList);
  }
  
  public void dropIntoContainer(EDragDroppable paramEDragDroppable)
  {
    ETransPalettePiece localETransPalettePiece = (ETransPalettePiece)paramEDragDroppable;
    int i = getIndexToAddAt(localETransPalettePiece);
    this.pieceList.insertElementAt(localETransPalettePiece, i);
    add(localETransPalettePiece);
    arrangePieces();
    localETransPalettePiece.addMouseListener(this.selectTool);
    localETransPalettePiece.addMouseMotionListener(this.selectTool);
    localETransPalettePiece.setRadioSelectable(true);
    this.selectTool.setSelected(localETransPalettePiece);
    ETransformList localETransformList = makeCurrentTransformList();
    this.transMan.setCurrentTransformList(localETransformList);
    this.transMan.setSelectedPiece(localETransPalettePiece);
  }
  
  private int getIndexToAddAt(ETransPalettePiece paramETransPalettePiece)
  {
    int i = -1;
    int j = paramETransPalettePiece.getX();
    for (int k = 0; k < this.pieceList.size(); k++)
    {
      Component localComponent = (Component)this.pieceList.elementAt(k);
      if (j > localComponent.getX())
      {
        i = k;
        break;
      }
    }
    if (i == -1) {
      i = this.pieceList.size();
    }
    return i;
  }
  
  private void arrangePieces()
  {
    int i = 30;
    int j = 450;
    for (int n = 0; n < this.pieceList.size(); n++)
    {
      ETransPalettePiece localETransPalettePiece = (ETransPalettePiece)this.pieceList.elementAt(n);
      int k = n * 70 + 40;
      int m = j - k;
      localETransPalettePiece.setLocation(m, i);
    }
    repaint();
  }
  
  private ETransformList makeCurrentTransformList()
  {
    ETransformList localETransformList = new ETransformList();
    for (int i = 0; i < this.pieceList.size(); i++)
    {
      ETransPalettePiece localETransPalettePiece = (ETransPalettePiece)this.pieceList.elementAt(i);
      ETransform localETransform = localETransPalettePiece.getTransform();
      localETransformList.addTransform(localETransform);
    }
    return localETransformList;
  }
  
  public void clearPieces()
  {
    this.selectTool.setSelected(this.anchor);
    for (int i = 0; i < this.pieceList.size(); i++)
    {
      ETransPalettePiece localETransPalettePiece = (ETransPalettePiece)this.pieceList.elementAt(i);
      remove(localETransPalettePiece);
    }
    this.pieceList.clear();
    repaint();
  }
  
  public void noteSelection(ERadioSelectable paramERadioSelectable)
  {
    if (paramERadioSelectable.equals(this.anchor))
    {
      this.transMan.setSelectedPiece(null);
    }
    else
    {
      ETransPalettePiece localETransPalettePiece = (ETransPalettePiece)paramERadioSelectable;
      this.transMan.setSelectedPiece(localETransPalettePiece);
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransPipelineArea.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */