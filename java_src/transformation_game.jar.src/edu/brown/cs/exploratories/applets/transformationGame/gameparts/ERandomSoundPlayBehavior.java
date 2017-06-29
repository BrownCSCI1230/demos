package edu.brown.cs.exploratories.applets.transformationGame.gameparts;

import java.applet.AudioClip;
import java.util.Random;

public class ERandomSoundPlayBehavior
  implements EBehavior
{
  private AudioClip[] audioClips;
  private Random rand;
  
  public ERandomSoundPlayBehavior(AudioClip[] paramArrayOfAudioClip)
  {
    this.audioClips = paramArrayOfAudioClip;
    this.rand = new Random();
  }
  
  public void execute()
  {
    playRandomSound();
  }
  
  private void playRandomSound()
  {
    int i = this.audioClips.length;
    if (i != 0)
    {
      int j = Math.abs(this.rand.nextInt() % i);
      this.audioClips[j].play();
    }
  }
  
  public void reset() {}
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/gameparts/ERandomSoundPlayBehavior.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */