package edu.brown.cs.exploratories.applets.transformationGame;

import edu.brown.cs.exploratories.applets.transformationGame.gameparts.EBehavior;
import edu.brown.cs.exploratories.applets.transformationGame.gameparts.EFlashingShapeBehavior;
import edu.brown.cs.exploratories.applets.transformationGame.gameparts.ELevelManager;
import edu.brown.cs.exploratories.applets.transformationGame.gameparts.ERandomSoundPlayBehavior;
import edu.brown.cs.exploratories.applets.transformationGame.gameparts.EShape;
import edu.brown.cs.exploratories.applets.transformationGame.tools.dragAndDrop.EDragDropTool;
import edu.brown.cs.exploratories.applets.transformationGame.tools.selection.ERadioSelectTool;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.util.Vector;

public class ETransFactory
  implements ETransConst
{
  private ETransGamePanel gamePanel = null;
  private ETransGraphicPanel graphicPanel = null;
  private ETransControlPanel controlPanel = null;
  private ETransPlayArea playArea = null;
  private ETransPaletteArea paletteArea = null;
  private ETransPipelineArea pipelineArea = null;
  private ETransBrain brain = null;
  private ETransformationManager transMan = null;
  private ELevelManager levelMan = null;
  private ETransPlayManager playMan;
  private ETransGoalObject goalObj;
  private ETransPlayObject playObj;
  private ETransGhostObject ghostObj;
  private EShape goalImpl;
  private EShape playImpl;
  private EShape ghostImpl;
  private EDragDropTool dragDropTool;
  private ERadioSelectTool radioSelectTool;
  private ETransformComparator transComparator;
  private EFlashingShapeBehavior flashHouse;
  private ERandomSoundPlayBehavior soundBehavior;
  
  public InputStreamReader getLevelFileStreamReader()
  {
    InputStream localInputStream = getClass().getResourceAsStream("levels.itf");
    InputStreamReader localInputStreamReader = null;
    if (localInputStream != null) {
      localInputStreamReader = new InputStreamReader(localInputStream);
    }
    return localInputStreamReader;
  }
  
  public ETransGamePanel getTransGamePanel()
  {
    if (this.gamePanel == null) {
      this.gamePanel = new ETransGamePanel(this);
    }
    return this.gamePanel;
  }
  
  public ETransGraphicPanel getGraphicPanel()
  {
    if (null == this.graphicPanel) {
      this.graphicPanel = new ETransGraphicPanel(this);
    }
    return this.graphicPanel;
  }
  
  public ETransControlPanel getControlPanel()
  {
    if (null == this.controlPanel) {
      this.controlPanel = new ETransControlPanel(this);
    }
    return this.controlPanel;
  }
  
  public ETransPlayArea getPlayArea()
  {
    if (null == this.playArea)
    {
      this.playArea = new ETransPlayArea(this);
      this.flashHouse = getFlashingHouseBehavior();
      this.flashHouse.setParentContainer(this.playArea);
    }
    return this.playArea;
  }
  
  public ETransPaletteArea getPaletteArea()
  {
    if (null == this.paletteArea) {
      this.paletteArea = new ETransPaletteArea(this);
    }
    return this.paletteArea;
  }
  
  public ETransPipelineArea getPipelineArea()
  {
    if (null == this.pipelineArea) {
      this.pipelineArea = new ETransPipelineArea(this);
    }
    return this.pipelineArea;
  }
  
  public ETransBrain getTransBrain()
  {
    if (null == this.brain) {
      this.brain = new ETransBrain(this);
    }
    return this.brain;
  }
  
  public ELevelManager getLevelManager()
  {
    if (null == this.levelMan) {
      this.levelMan = new ETransLevelManager(this);
    }
    return this.levelMan;
  }
  
  public ETransformationManager getTransformationManager()
  {
    if (null == this.transMan) {
      this.transMan = new ETransformationManagerImpl(this);
    }
    return this.transMan;
  }
  
  public ETransPlayManager getPlayManager()
  {
    if (null == this.playMan)
    {
      this.playMan = new ETransPlayManager(this);
      this.playMan.addWinBehavior(getFlashingHouseBehavior());
      this.playMan.addWinBehavior(getWinSoundsBehavior());
    }
    return this.playMan;
  }
  
  public ETransGoalObject getGoalObject()
  {
    if (null == this.goalObj) {
      this.goalObj = new ETransGoalObject(this);
    }
    return this.goalObj;
  }
  
  public ETransPlayObject getPlayObject()
  {
    if (null == this.playObj) {
      this.playObj = new ETransPlayObject(this);
    }
    return this.playObj;
  }
  
  public ETransGhostObject getGhostObject()
  {
    if (null == this.ghostObj) {
      this.ghostObj = new ETransGhostObject(this);
    }
    return this.ghostObj;
  }
  
  public EShape getGoalImpl()
  {
    if (null == this.goalImpl) {
      this.goalImpl = new ETransGoalHouse();
    }
    return this.goalImpl;
  }
  
  public EShape getPlayImpl()
  {
    if (null == this.playImpl)
    {
      this.playImpl = new ETransPlayHouse();
      this.playMan = getPlayManager();
      this.flashHouse = getFlashingHouseBehavior();
      this.flashHouse.addShape(this.playImpl);
    }
    return this.playImpl;
  }
  
  public EShape getGhostImpl()
  {
    if (null == this.ghostImpl)
    {
      this.ghostImpl = new ETransGhostHouse();
      this.playMan = getPlayManager();
      this.flashHouse = getFlashingHouseBehavior();
      this.flashHouse.addShape(this.ghostImpl);
    }
    return this.ghostImpl;
  }
  
  public ETransPalettePiece getPalettePiece(ETransform paramETransform)
  {
    return new ETransPalettePiece(this, paramETransform);
  }
  
  public ETransPalettePieceShape getPalettePieceImpl()
  {
    return new ETransPalettePieceShape();
  }
  
  public ETransAnchor getAnchor()
  {
    return new ETransAnchor();
  }
  
  public EDragDropTool getDragDropTool()
  {
    if (null == this.dragDropTool) {
      this.dragDropTool = new EDragDropTool();
    }
    return this.dragDropTool;
  }
  
  public ERadioSelectTool getRadioSelectTool()
  {
    if (null == this.radioSelectTool) {
      this.radioSelectTool = new ERadioSelectTool();
    }
    return this.radioSelectTool;
  }
  
  public ETransformComparator getTransformComparator()
  {
    if (null == this.transComparator) {
      this.transComparator = new ETransformComparator(0.08D, 3.0D);
    }
    return this.transComparator;
  }
  
  public EFlashingShapeBehavior getFlashingHouseBehavior()
  {
    if (null == this.flashHouse) {
      this.flashHouse = new EFlashingShapeBehavior();
    }
    return this.flashHouse;
  }
  
  protected void err_msg(String paramString) {}
  
  private AudioClip[] getWinSounds()
  {
    AudioClip[] arrayOfAudioClip = null;
    Vector localVector = new Vector();
    Class localClass = getClass();
    try
    {
      InputStream localInputStream = localClass.getResourceAsStream("audioClipsList.txt");
      if (localInputStream == null)
      {
        System.err.println("audioClipsList.txt not found");
      }
      else
      {
        BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localInputStream));
        for (String str = localBufferedReader.readLine(); str != null; str = localBufferedReader.readLine())
        {
          URL localURL = localClass.getResource(str);
          if (localURL != null) {
            localVector.add(Applet.newAudioClip(localURL));
          }
        }
      }
    }
    catch (IOException localIOException)
    {
      System.err.println(localIOException);
    }
    int i = localVector.size();
    arrayOfAudioClip = new AudioClip[i];
    localVector.copyInto(arrayOfAudioClip);
    return arrayOfAudioClip;
  }
  
  public EBehavior getWinSoundsBehavior()
  {
    if (this.soundBehavior == null) {
      this.soundBehavior = new ERandomSoundPlayBehavior(getWinSounds());
    }
    return this.soundBehavior;
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransFactory.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */