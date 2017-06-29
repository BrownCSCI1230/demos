package edu.brown.cs.exploratories.applets.transformationGame;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.Vector;

public class ETransLevelParser
  implements ETransConst
{
  protected void err_msg(String paramString) {}
  
  public void parse(Vector paramVector, InputStreamReader paramInputStreamReader, boolean paramBoolean)
  {
    StreamTokenizer localStreamTokenizer = new StreamTokenizer(paramInputStreamReader);
    localStreamTokenizer.wordChars(65, 126);
    localStreamTokenizer.parseNumbers();
    localStreamTokenizer.slashSlashComments(true);
    try
    {
      if (paramBoolean) {
        err_msg("Loading levels...");
      }
      int i = localStreamTokenizer.nextToken();
      int j = localStreamTokenizer.ttype;
      if (i != 35)
      {
        err_msg("Invalid Header: # must come first");
        return;
      }
      i = localStreamTokenizer.nextToken();
      if (i != -3)
      {
        err_msg("Invalid File Header: Should be a string");
        return;
      }
      String str = localStreamTokenizer.sval;
      if (!str.equals("IT_LEVEL_DATA_FORMAT"))
      {
        err_msg("Invalid File Header: Should be IT_LEVEL_DATA_FORMAT");
        return;
      }
      for (i = localStreamTokenizer.nextToken(); i != -1; i = localStreamTokenizer.nextToken()) {
        if ((i == -3) && (localStreamTokenizer.sval.equals("LEVEL")))
        {
          i = localStreamTokenizer.nextToken();
          if (i != -2)
          {
            err_msg("Invalid Parameter after LEVEL tag: Must be a number");
            return;
          }
          ETransLevel localETransLevel = new ETransLevel((int)localStreamTokenizer.nval);
          for (i = localStreamTokenizer.nextToken(); !localStreamTokenizer.sval.equals("SOLUTION"); i = localStreamTokenizer.nextToken())
          {
            if (i != -3)
            {
              err_msg("Expected string for available transform");
              return;
            }
            if (localStreamTokenizer.sval.equals("IT_TRANSLATE")) {
              localETransLevel.addAvailTrans(new ETransform(2, false, 0.0D));
            } else if (localStreamTokenizer.sval.equals("IT_SCALE")) {
              localETransLevel.addAvailTrans(new ETransform(5, false, 1.0D));
            } else if (localStreamTokenizer.sval.equals("IT_ROTATE")) {
              localETransLevel.addAvailTrans(new ETransform(8, false, 0.0D));
            } else if (localStreamTokenizer.sval.equals("IT_TRANSLATE_VARY")) {
              localETransLevel.addAvailTrans(new ETransform(2, true, 0.0D));
            } else if (localStreamTokenizer.sval.equals("IT_SCALE_VARY")) {
              localETransLevel.addAvailTrans(new ETransform(5, true, 1.0D));
            } else if (localStreamTokenizer.sval.equals("IT_ROTATE_VARY")) {
              localETransLevel.addAvailTrans(new ETransform(8, true, 0.0D));
            }
          }
          for (i = localStreamTokenizer.nextToken(); !localStreamTokenizer.sval.equals("END"); i = localStreamTokenizer.nextToken())
          {
            if (i != -3)
            {
              err_msg("Expected string for solution transform");
              return;
            }
            int k = -1;
            boolean bool = false;
            if (localStreamTokenizer.sval.equals("IT_X_TRANSLATE"))
            {
              k = 3;
            }
            else if (localStreamTokenizer.sval.equals("IT_Y_TRANSLATE"))
            {
              k = 4;
            }
            else if (localStreamTokenizer.sval.equals("IT_X_SCALE"))
            {
              k = 6;
            }
            else if (localStreamTokenizer.sval.equals("IT_Y_SCALE"))
            {
              k = 7;
            }
            else if (localStreamTokenizer.sval.equals("IT_ROTATE"))
            {
              k = 8;
            }
            else if (localStreamTokenizer.sval.equals("IT_X_TRANSLATE_VARY"))
            {
              k = 3;
              bool = true;
            }
            else if (localStreamTokenizer.sval.equals("IT_Y_TRANSLATE_VARY"))
            {
              k = 4;
              bool = true;
            }
            else if (localStreamTokenizer.sval.equals("IT_X_SCALE_VARY"))
            {
              k = 6;
              bool = true;
            }
            else if (localStreamTokenizer.sval.equals("IT_Y_SCALE_VARY"))
            {
              k = 7;
              bool = true;
            }
            else if (localStreamTokenizer.sval.equals("IT_ROTATE_VARY"))
            {
              k = 8;
              bool = true;
            }
            else
            {
              err_msg("Invalid transform type");
              return;
            }
            i = localStreamTokenizer.nextToken();
            if (i != -2)
            {
              err_msg("Expected solution transform value");
              return;
            }
            float f = (float)localStreamTokenizer.nval;
            localETransLevel.addSolution(new ETransform(k, bool, f));
          }
          paramVector.addElement(localETransLevel);
          if (paramBoolean) {
            err_msg("Level " + localETransLevel.getNum());
          }
        }
        else
        {
          err_msg("Expecting LEVEL tag");
          return;
        }
      }
    }
    catch (IOException localIOException)
    {
      err_msg("IO exception parsing level file.");
    }
    if (paramBoolean) {
      err_msg("done.");
    }
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransLevelParser.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */