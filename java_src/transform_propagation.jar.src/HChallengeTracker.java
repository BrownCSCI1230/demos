/*    */ import java.awt.Image;
/*    */ import java.util.Vector;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HChallengeTracker
/*    */ {
/* 10 */   private Vector _challengeObjects = new Vector();
/*    */   
/*    */ 
/*    */   public void addChallengeObject(Image[] paramArrayOfImage, int paramInt1, String[] paramArrayOfString, int paramInt2)
/*    */   {
/* 15 */     this._challengeObjects.addElement(new HChallengeObject(paramArrayOfImage, paramInt1, 
/* 16 */       paramArrayOfString, paramInt2));
/*    */   }
/*    */   
/*    */   public int getSize() {
/* 20 */     return this._challengeObjects.size();
/*    */   }
/*    */   
/*    */   public HChallengeObject getChallengeObject(int paramInt) {
/* 24 */     return (HChallengeObject)this._challengeObjects.elementAt(paramInt);
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/transform_propagation.jar!/HChallengeTracker.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */