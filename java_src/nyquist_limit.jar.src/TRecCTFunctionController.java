/*    */ 
/*    */ public class TRecCTFunctionController
/*    */   implements CTFunctionController
/*    */ {
/*    */   protected CTFunctionWindow function_window;
/*    */   
/*    */   public void setFunctionWindow(CTFunctionWindow paramCTFunctionWindow)
/*    */   {
/*  9 */     this.function_window = paramCTFunctionWindow;
/*    */   }
/*    */   
/*    */   public void updateGraph(int paramInt, double[] paramArrayOfDouble)
/*    */   {
/* 14 */     int i = this.function_window.getChartWidth();
/* 15 */     double[] arrayOfDouble = new double[i];
/*    */     
/*    */ 
/* 18 */     for (int j = 0; j < i - 1; j++) {
/* 19 */       double d1 = (paramInt - 1.0D) * j / (i - 1.0D);
/* 20 */       int k = (int)Math.floor(d1);
/* 21 */       double d2 = d1 - k;
/* 22 */       arrayOfDouble[j] = ((1.0D - d2) * paramArrayOfDouble[k] + d2 * paramArrayOfDouble[(k + 1)]);
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 34 */     this.function_window.changeValues(0, i, arrayOfDouble);
/*    */   }
/*    */   
/*    */   public void mouseStart(int paramInt1, int paramInt2) {}
/*    */   
/*    */   public void mouseDrag(int paramInt1, int paramInt2) {}
/*    */   
/*    */   public void mouseStop() {}
/*    */   
/*    */   public void resize(int paramInt1, int paramInt2) {}
/*    */ }


/* Location:              /Users/masonbartle/Downloads/nyquist_limit.jar!/TRecCTFunctionController.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */