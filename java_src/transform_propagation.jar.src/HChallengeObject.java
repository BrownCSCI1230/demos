/*    */ import java.awt.Image;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HChallengeObject
/*    */ {
/*    */   private String[] _string;
/*    */   private Image[] _image;
/*    */   private int _stringSize;
/*    */   private int _imageSize;
/*    */   
/*    */   public HChallengeObject(Image[] paramArrayOfImage, int paramInt1, String[] paramArrayOfString, int paramInt2)
/*    */   {
/* 15 */     this._string = paramArrayOfString;
/* 16 */     this._image = paramArrayOfImage;
/* 17 */     this._stringSize = paramInt2;
/* 18 */     this._imageSize = paramInt1;
/*    */   }
/*    */   
/*    */   public Image[] getImages() {
/* 22 */     return this._image;
/*    */   }
/*    */   
/*    */   public String[] getStrings() {
/* 26 */     return this._string;
/*    */   }
/*    */   
/*    */   public int getNumImages() {
/* 30 */     return this._imageSize;
/*    */   }
/*    */   
/*    */   public int getNumStrings() {
/* 34 */     return this._stringSize;
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/transform_propagation.jar!/HChallengeObject.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */