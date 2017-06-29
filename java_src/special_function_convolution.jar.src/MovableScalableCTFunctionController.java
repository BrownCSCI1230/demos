/*     */ import java.util.EventObject;
/*     */ import javax.swing.JSlider;
/*     */ import javax.swing.event.ChangeEvent;
/*     */ import javax.swing.event.ChangeListener;
/*     */ 
/*     */ public class MovableScalableCTFunctionController
/*     */   extends ScalableCTFunctionController
/*     */   implements ChangeListener
/*     */ {
/*     */   protected transient int x_offset;
/*     */   protected transient JSlider j_slider;
/*     */   
/*     */   public MovableScalableCTFunctionController()
/*     */   {
/*  15 */     this(new BoxFunction());
/*     */   }
/*     */   
/*     */   public MovableScalableCTFunctionController(Function paramFunction)
/*     */   {
/*  20 */     super(paramFunction);
/*     */   }
/*     */   
/*     */ 
/*     */   public void mouseStart(int paramInt1, int paramInt2)
/*     */   {
/*  26 */     super.mouseStart(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */ 
/*     */   public void resize(int paramInt1, int paramInt2)
/*     */   {
/*  32 */     super.resize(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void stateChanged(ChangeEvent paramChangeEvent)
/*     */   {
/*  37 */     JSlider localJSlider = (JSlider)paramChangeEvent.getSource();
/*  38 */     int i = localJSlider.getValue();
/*  39 */     if (i == this.x_offset) {
/*  40 */       return;
/*     */     }
/*  42 */     this.x_offset = i;
/*     */     
/*  44 */     String str = "g(x";
/*  45 */     double d1 = this.function_window.getXStart();
/*  46 */     double d2 = this.function_window.getXEnd();
/*  47 */     int j = this.function_window.getChartWidth();
/*  48 */     double d3 = this.x_offset * (d2 - d1) / j;
/*  49 */     if (d3 < 0.0D) {
/*  50 */       str = str + "+";
/*  51 */       str = str + Math.abs(d3);
/*     */     }
/*  53 */     else if (d3 > 0.0D) {
/*  54 */       str = str + "-";
/*  55 */       str = str + d3;
/*     */     }
/*     */     
/*  58 */     str = str.substring(0, Math.min(9, str.length()));
/*  59 */     str = str + ") [Convolution Filter]";
/*     */     
/*  61 */     this.function_window.setTitle(str);
/*     */     
/*  63 */     double[] arrayOfDouble = new double[this.chart_width];
/*  64 */     int k; if (i < 0) {
/*  65 */       for (k = 0; k < this.chart_width + i; k++) {
/*  66 */         arrayOfDouble[k] = this.chart_values[(k - i)];
/*     */       }
/*     */       
/*     */     } else {
/*  70 */       for (k = 0; k < this.chart_width - i; k++) {
/*  71 */         arrayOfDouble[(k + i)] = this.chart_values[k];
/*     */       }
/*     */     }
/*     */     
/*  75 */     this.function_window.changeValues(0, this.chart_width, arrayOfDouble);
/*     */     
/*  77 */     if (this.prod_ctrl != null) {
/*  78 */       this.prod_ctrl.recompute(0, this.chart_width);
/*     */     }
/*  80 */     if (this.conv_ctrl != null) {
/*  81 */       this.conv_ctrl.reveal(this.x_offset);
/*     */     }
/*     */   }
/*     */   
/*     */   public void resetPosition() {
/*  86 */     if (this.x_offset == 0) {
/*  87 */       return;
/*     */     }
/*  89 */     this.x_offset = 0;
/*     */     
/*  91 */     this.j_slider.setValue(0);
/*  92 */     this.function_window.setTitle("g(x)");
/*     */     
/*  94 */     this.function_window.changeValues(0, this.chart_width, this.chart_values);
/*     */   }
/*     */   
/*     */   public void setSlider(JSlider paramJSlider)
/*     */   {
/*  99 */     this.j_slider = paramJSlider;
/* 100 */     this.j_slider.addChangeListener(this);
/*     */   }
/*     */   
/* 103 */   public int getOffset() { return this.x_offset; }
/*     */ }


/* Location:              /Users/masonbartle/Downloads/special_function_convolution.jar!/MovableScalableCTFunctionController.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */