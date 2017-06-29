/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class ConvDTFunctionController
/*     */   implements DTFunctionController, Serializable, PropertyChangeListener
/*     */ {
/*     */   protected transient int mask_min;
/*     */   protected transient int mask_max;
/*     */   protected transient DTFunctionWindow function_window;
/*     */   protected transient EditableDTFunctionController func_f;
/*     */   protected transient MovableDTFunctionController func_g;
/*     */   
/*     */   public void setFunctionWindow(DTFunctionWindow paramDTFunctionWindow)
/*     */   {
/*  16 */     this.function_window = paramDTFunctionWindow;
/*     */     
/*  18 */     for (int i = 0; i < 17; i++) {
/*  19 */       this.function_window.getDValue(i).setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void resize(int paramInt1, int paramInt2) {}
/*     */   
/*     */   public void recompute()
/*     */   {
/*  28 */     this.mask_min = 0;
/*  29 */     this.mask_max = this.mask_min;
/*     */     
/*  31 */     for (int i = 0; i < 17; i++) {
/*  32 */       this.function_window.getDValue(i).setValue(0.0D);
/*     */     }
/*     */     
/*     */ 
/*  36 */     int j = 8;
/*  37 */     for (int k = -j; k <= j; k++) {
/*  38 */       DValue localDValue = this.function_window.getDValue(k + j);
/*     */       
/*  40 */       for (int m = 0; m <= 2 * j; m++) {
/*  41 */         if ((m - k >= 0) && (m - k < 17)) {
/*  42 */           localDValue.setValue(localDValue.getValue() + this.func_f.getDValue(m).getValue() * 
/*  43 */             this.func_g.getDValue(m - k).getValue());
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  62 */     showRevealed();
/*     */   }
/*     */   
/*     */   public void showRevealed()
/*     */   {
/*  67 */     int i = 8;
/*  68 */     for (int j = -i; j <= i; j++) {
/*  69 */       if ((j >= this.mask_min) && (j <= this.mask_max)) {
/*  70 */         this.function_window.getDValue(j + i).setVisible(true);
/*     */       }
/*     */       else {
/*  73 */         this.function_window.getDValue(j + i).setVisible(false);
/*     */       }
/*     */     }
/*     */     
/*  77 */     this.function_window.changeValues();
/*     */   }
/*     */   
/*     */   public void reveal(int paramInt)
/*     */   {
/*  82 */     if (paramInt < this.mask_min) {
/*  83 */       this.mask_min = paramInt;
/*     */     }
/*  85 */     if (paramInt > this.mask_max) {
/*  86 */       this.mask_max = paramInt;
/*     */     }
/*  88 */     showRevealed();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void beginUpdate(DValue paramDValue) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void valueUpdate() {}
/*     */   
/*     */ 
/*     */   public void endUpdate() {}
/*     */   
/*     */ 
/*     */   public void setF(EditableDTFunctionController paramEditableDTFunctionController)
/*     */   {
/* 105 */     this.func_f = paramEditableDTFunctionController;
/* 106 */     paramEditableDTFunctionController.setConvController(this);
/*     */   }
/*     */   
/*     */   public void setG(MovableDTFunctionController paramMovableDTFunctionController)
/*     */   {
/* 111 */     this.func_g = paramMovableDTFunctionController;
/* 112 */     paramMovableDTFunctionController.setConvController(this);
/*     */   }
/*     */   
/*     */ 
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {}
/*     */   
/*     */ 
/*     */   public void resetPosition()
/*     */   {
/* 121 */     this.mask_min = 0;
/* 122 */     this.mask_max = this.mask_min;
/*     */     
/* 124 */     showRevealed();
/*     */   }
/*     */   
/*     */   public void mouseStart(int paramInt1, int paramInt2) {}
/*     */   
/*     */   public void mouseDrag(int paramInt1, int paramInt2) {}
/*     */   
/*     */   public void mouseStop() {}
/*     */ }


/* Location:              /Users/masonbartle/Downloads/discrete_convolution.jar!/ConvDTFunctionController.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */