package edu.brown.cs.exploratories.applets.transformationGame;

import edu.brown.cs.exploratories.applets.transformationGame.gameparts.ELevelManager;
import java.io.InputStreamReader;
import java.util.Vector;

public class ETransLevelManager
  implements ELevelManager
{
  private ETransFactory factory;
  private ETransLevelParser parser;
  private Vector levels;
  private InputStreamReader isReader;
  private boolean E_TRANS_PARSE_VERBOSE = true;
  private ETransformationManager transMan;
  private ETransPlayManager playMan;
  private ETransLevel currLevel;
  
  public ETransLevelManager(ETransFactory paramETransFactory)
  {
    this.factory = paramETransFactory;
    this.parser = new ETransLevelParser();
    this.levels = new Vector();
    this.isReader = getInputStreamReader();
    this.parser.parse(this.levels, this.isReader, this.E_TRANS_PARSE_VERBOSE);
    this.transMan = this.factory.getTransformationManager();
    this.playMan = this.factory.getPlayManager();
    this.currLevel = null;
  }
  
  protected InputStreamReader getInputStreamReader()
  {
    return this.factory.getLevelFileStreamReader();
  }
  
  public void loadLevel(int paramInt)
  {
    try
    {
      ETransLevel localETransLevel = (ETransLevel)this.levels.elementAt(paramInt - 1);
      this.currLevel = localETransLevel;
      loadSolution(localETransLevel);
      loadAvailableTransforms(localETransLevel);
      ETransPipelineArea localETransPipelineArea = this.factory.getPipelineArea();
      localETransPipelineArea.clearPieces();
      String str = "Level " + paramInt + " of " + this.levels.size();
      this.factory.getPlayArea().setString(str);
      this.transMan.clearCurrentTransformList();
      this.playMan.reset();
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {}
  }
  
  public void loadNextLevel()
  {
    int i = this.currLevel.getNum();
    loadLevel(i + 1);
  }
  
  public void loadPrevLevel()
  {
    int i = this.currLevel.getNum();
    loadLevel(i - 1);
  }
  
  protected void loadSolution(ETransLevel paramETransLevel)
  {
    Vector localVector = paramETransLevel.getSolution();
    ETransformList localETransformList = new ETransformList();
    for (int i = 0; i < localVector.size(); i++)
    {
      ETransform localETransform = (ETransform)localVector.elementAt(i);
      localETransformList.addTransform(localETransform);
    }
    this.transMan.setGoalTransformList(localETransformList);
  }
  
  protected void loadAvailableTransforms(ETransLevel paramETransLevel)
  {
    Vector localVector = paramETransLevel.getAvail();
    ETransPaletteArea localETransPaletteArea = this.factory.getPaletteArea();
    localETransPaletteArea.clearPieces();
    for (int i = 0; i < localVector.size(); i++)
    {
      ETransform localETransform = (ETransform)localVector.elementAt(i);
      localETransform.resetCurrentValue();
      localETransPaletteArea.addTransform(localETransform);
    }
    localETransPaletteArea.arrangePieces();
  }
  
  protected void loadNumber(ETransLevel paramETransLevel) {}
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransLevelManager.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */