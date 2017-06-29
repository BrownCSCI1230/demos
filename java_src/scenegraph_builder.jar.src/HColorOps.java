/*    */ import java.awt.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HColorOps
/*    */ {
/*    */   public static Color getLighterColor(Color paramColor)
/*    */   {
/* 13 */     int i = paramColor.getRed();
/* 14 */     int j = paramColor.getGreen();
/* 15 */     int k = paramColor.getBlue();
/*    */     
/* 17 */     Color localColor = new Color(checkRGBPlus(i), 
/* 18 */       checkRGBPlus(j), checkRGBPlus(k));
/* 19 */     return localColor;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public static Color getDarkerColor(Color paramColor)
/*    */   {
/* 27 */     int i = paramColor.getRed();
/* 28 */     int j = paramColor.getGreen();
/* 29 */     int k = paramColor.getBlue();
/*    */     
/* 31 */     Color localColor = new Color(checkRGBMinus(i), 
/* 32 */       checkRGBMinus(j), checkRGBMinus(k));
/* 33 */     return localColor;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public static int checkRGBPlus(int paramInt)
/*    */   {
/* 40 */     if (paramInt <= 205)
/* 41 */       return paramInt + 50;
/* 42 */     return 255;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public static int checkRGBMinus(int paramInt)
/*    */   {
/* 49 */     if (paramInt >= 70)
/* 50 */       return paramInt - 70;
/* 51 */     return 0;
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/scenegraph_builder.jar!/HColorOps.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */