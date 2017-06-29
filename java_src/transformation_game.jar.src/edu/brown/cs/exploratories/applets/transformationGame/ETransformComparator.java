package edu.brown.cs.exploratories.applets.transformationGame;

import java.awt.geom.AffineTransform;

public class ETransformComparator
{
  private double translationEpsilon;
  private double generalEpsilon;
  private double[] goalMatrix;
  private double[] testMatrix;
  
  ETransformComparator(double paramDouble1, double paramDouble2)
  {
    this.translationEpsilon = paramDouble2;
    this.generalEpsilon = paramDouble1;
    this.goalMatrix = new double[6];
    this.testMatrix = new double[6];
  }
  
  public boolean transformsMatch(AffineTransform paramAffineTransform1, AffineTransform paramAffineTransform2)
  {
    boolean bool = true;
    paramAffineTransform1.getMatrix(this.goalMatrix);
    paramAffineTransform2.getMatrix(this.testMatrix);
    for (int i = 0; i <= 3; i++) {
      bool = (bool) && (generalValuesAreClose(this.goalMatrix[i], this.testMatrix[i]));
    }
    for (int j = 4; j <= 5; j++) {
      bool = (bool) && (translationValuesAreClose(this.goalMatrix[j], this.testMatrix[j]));
    }
    return bool;
  }
  
  private boolean translationValuesAreClose(double paramDouble1, double paramDouble2)
  {
    double d = Math.abs(paramDouble1 - paramDouble2);
    return d <= this.translationEpsilon;
  }
  
  private boolean generalValuesAreClose(double paramDouble1, double paramDouble2)
  {
    double d = Math.abs(paramDouble1 - paramDouble2);
    return d < this.generalEpsilon;
  }
}


/* Location:              /Users/masonbartle/Downloads/transformation_game.jar!/edu/brown/cs/exploratories/applets/transformationGame/ETransformComparator.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */