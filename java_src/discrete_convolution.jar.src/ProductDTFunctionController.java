/*    */ import java.beans.PropertyChangeEvent;
/*    */ import java.beans.PropertyChangeListener;
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProductDTFunctionController
/*    */   implements DTFunctionController, Serializable, PropertyChangeListener
/*    */ {
/*    */   protected transient DTFunctionWindow function_window;
/*    */   protected transient EditableDTFunctionController func_f;
/*    */   protected transient MovableDTFunctionController func_g;
/*    */   
/*    */   public void setFunctionWindow(DTFunctionWindow paramDTFunctionWindow)
/*    */   {
/* 16 */     this.function_window = paramDTFunctionWindow;
/*    */     
/* 18 */     for (int i = 0; i < 17; i++) {
/* 19 */       this.function_window.getDValue(i).setActive(false);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public void resize(int paramInt1, int paramInt2) {}
/*    */   
/*    */   public void recompute()
/*    */   {
/* 28 */     int i = this.func_g.getOffset();
/*    */     int j;
/* 30 */     if (i > 0) {
/* 31 */       for (j = i; j < 17; j++) {
/* 32 */         this.function_window.getDValue(j).setValue(this.func_f.getDValue(j).getValue() * 
/* 33 */           this.func_g.getDValue(j - i).getValue());
/*    */       }
/*    */       
/*    */     } else {
/* 37 */       for (j = 0; j < 17 + i; j++) {
/* 38 */         this.function_window.getDValue(j).setValue(this.func_f.getDValue(j).getValue() * 
/* 39 */           this.func_g.getDValue(j - i).getValue());
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 44 */     this.function_window.changeValues();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void beginUpdate(DValue paramDValue) {}
/*    */   
/*    */ 
/*    */ 
/*    */   public void valueUpdate() {}
/*    */   
/*    */ 
/*    */   public void endUpdate() {}
/*    */   
/*    */ 
/*    */   public void setF(EditableDTFunctionController paramEditableDTFunctionController)
/*    */   {
/* 61 */     this.func_f = paramEditableDTFunctionController;
/* 62 */     paramEditableDTFunctionController.setProductController(this);
/*    */   }
/*    */   
/*    */   public void setG(MovableDTFunctionController paramMovableDTFunctionController)
/*    */   {
/* 67 */     this.func_g = paramMovableDTFunctionController;
/* 68 */     paramMovableDTFunctionController.setProductController(this);
/*    */   }
/*    */   
/*    */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {}
/*    */   
/*    */   public void resetPosition() {}
/*    */   
/*    */   public void mouseStart(int paramInt1, int paramInt2) {}
/*    */   
/*    */   public void mouseDrag(int paramInt1, int paramInt2) {}
/*    */   
/*    */   public void mouseStop() {}
/*    */ }


/* Location:              /Users/masonbartle/Downloads/discrete_convolution.jar!/ProductDTFunctionController.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */