/*    */ import java.beans.PropertyChangeEvent;
/*    */ import java.beans.PropertyChangeListener;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProductCTFunctionController
/*    */   implements CTFunctionController, Serializable, PropertyChangeListener
/*    */ {
/*    */   protected transient CTFunctionWindow function_window;
/*    */   protected transient EditableCTFunctionController func_f;
/*    */   protected transient MovableScalableCTFunctionController func_g;
/*    */   
/*    */   public void setFunctionWindow(CTFunctionWindow paramCTFunctionWindow)
/*    */   {
/* 16 */     this.function_window = paramCTFunctionWindow;
/*    */   }
/*    */   
/*    */   public void recompute(int paramInt1, int paramInt2)
/*    */   {
/* 21 */     double[] arrayOfDouble1 = new double[paramInt2];
/* 22 */     double[] arrayOfDouble2 = this.func_f.getChartValues();
/* 23 */     double[] arrayOfDouble3 = this.func_g.getChartValues();
/* 24 */     int i = this.func_g.getOffset();
/*    */     int j;
/* 26 */     if (i == 0) {
/* 27 */       for (j = 0; j < paramInt2; j++) {
/* 28 */         arrayOfDouble1[j] = (arrayOfDouble2[(paramInt1 + j)] * arrayOfDouble3[(paramInt1 + j)]);
/*    */       }
/*    */       
/* 31 */       this.function_window.changeValues(paramInt1, paramInt2, arrayOfDouble1);
/*    */     }
/* 33 */     else if (i > 0) {
/* 34 */       for (j = i; j < paramInt2; j++) {
/* 35 */         arrayOfDouble1[j] = (arrayOfDouble2[(paramInt1 + j)] * arrayOfDouble3[(paramInt1 + j - i)]);
/*    */       }
/*    */       
/* 38 */       this.function_window.changeValues(paramInt1, paramInt2, arrayOfDouble1);
/*    */     }
/*    */     else {
/* 41 */       j = paramInt2 + i;
/*    */       
/* 43 */       for (int k = 0; k < j; k++) {
/* 44 */         arrayOfDouble1[k] = (arrayOfDouble2[(paramInt1 + k)] * arrayOfDouble3[(paramInt1 + k - i)]);
/*    */       }
/*    */       
/* 47 */       this.function_window.changeValues(paramInt1, paramInt2, arrayOfDouble1);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public void resetPosition()
/*    */   {
/* 54 */     int i = this.function_window.getChartWidth();
/* 55 */     double[] arrayOfDouble = new double[i];
/* 56 */     for (int j = 0; j < i; j++) {
/* 57 */       arrayOfDouble[j] = 0.0D;
/*    */     }
/*    */     
/* 60 */     this.function_window.changeValues(0, i, arrayOfDouble);
/*    */   }
/*    */   
/*    */   public void setF(EditableCTFunctionController paramEditableCTFunctionController)
/*    */   {
/* 65 */     this.func_f = paramEditableCTFunctionController;
/* 66 */     paramEditableCTFunctionController.setProductController(this);
/*    */   }
/*    */   
/*    */   public void setG(MovableScalableCTFunctionController paramMovableScalableCTFunctionController)
/*    */   {
/* 71 */     this.func_g = paramMovableScalableCTFunctionController;
/* 72 */     paramMovableScalableCTFunctionController.setProductController(this);
/*    */   }
/*    */   
/*    */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {}
/*    */   
/*    */   public void mouseStart(int paramInt1, int paramInt2) {}
/*    */   
/*    */   public void mouseDrag(int paramInt1, int paramInt2) {}
/*    */   
/*    */   public void mouseStop() {}
/*    */   
/*    */   public void resize(int paramInt1, int paramInt2) {}
/*    */ }


/* Location:              /Users/masonbartle/Downloads/special_function_convolution.jar!/ProductCTFunctionController.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */