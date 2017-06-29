/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ScalableCTFunctionController
/*     */   implements CTFunctionController, Serializable, PropertyChangeListener
/*     */ {
/*     */   public ScalableCTFunctionController()
/*     */   {
/*  12 */     this(new BoxFunction());
/*     */   }
/*     */   
/*     */   public ScalableCTFunctionController(Function paramFunction)
/*     */   {
/*  17 */     this.cur_function = paramFunction;
/*     */   }
/*     */   
/*     */ 
/*     */   public void setFunctionWindow(CTFunctionWindow paramCTFunctionWindow)
/*     */   {
/*  23 */     this.function_window = paramCTFunctionWindow;
/*     */     
/*  25 */     this.chart_width = this.function_window.getChartWidth();
/*  26 */     this.chart_values = new double[this.chart_width];
/*     */   }
/*     */   
/*     */   public void setFunction(Function paramFunction)
/*     */   {
/*  31 */     this.amp_scale = 1.0D;
/*  32 */     this.per_scale = 1.0D;
/*  33 */     this.cur_function = paramFunction;
/*     */     
/*  35 */     ConvolutionSlide.__instance.resetPositions();
/*     */     
/*  37 */     this.changed_ = true;
/*  38 */     this.function_window.changeValues(0, this.chart_width, getChartValues());
/*     */     
/*  40 */     if (this.prod_ctrl != null) {
/*  41 */       this.prod_ctrl.recompute(0, this.chart_width);
/*     */     }
/*  43 */     if (this.conv_ctrl != null) {
/*  44 */       this.conv_ctrl.recompute();
/*     */     }
/*     */   }
/*     */   
/*     */   public void mouseStart(int paramInt1, int paramInt2) {
/*  49 */     this.last_x = paramInt1;
/*  50 */     this.last_y = paramInt2;
/*     */     
/*  52 */     if (this.conv_ctrl != null) {
/*  53 */       this.conv_ctrl.invalidate();
/*     */     }
/*     */     
/*  56 */     mouseDrag(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void mouseDrag(int paramInt1, int paramInt2)
/*     */   {
/*  61 */     int i = 0;
/*  62 */     int j = 0;
/*     */     
/*  64 */     double d1 = this.function_window.getXStart();
/*  65 */     double d2 = this.function_window.getXEnd();
/*  66 */     double d3 = this.last_x * (d2 - d1) / this.chart_width + d1;
/*  67 */     double d4 = paramInt1 * (d2 - d1) / this.chart_width + d1;
/*     */     
/*  69 */     double d5 = 0.0D;
/*  70 */     if (Math.abs(d3) > 1.0E-5D) {
/*  71 */       d5 = d4 / d3;
/*     */     }
/*  73 */     if ((d5 > MIN_SCALE_FAC) && 
/*  74 */       (d5 * this.per_scale > X_MIN_SCALE) && 
/*  75 */       (Math.abs(d4) > MIN_X_DIST) && (Math.abs(d3) > MIN_X_DIST)) {
/*  76 */       this.per_scale /= d5;
/*  77 */       i = 1;
/*     */     }
/*     */     
/*  80 */     double d6 = this.function_window.getYStart();
/*  81 */     double d7 = this.function_window.getYEnd();
/*  82 */     double d8 = this.last_y * (d7 - d6) / this.function_window.getChartHeight() + d6;
/*  83 */     double d9 = paramInt2 * (d7 - d6) / this.function_window.getChartHeight() + d6;
/*     */     
/*  85 */     double d10 = 0.0D;
/*  86 */     if (Math.abs(d8) > 1.0E-5D) {
/*  87 */       d10 = d9 / d8;
/*     */     }
/*  89 */     if ((d10 > MIN_SCALE_FAC) && 
/*  90 */       (d10 * this.amp_scale > Y_MIN_SCALE) && 
/*  91 */       (Math.abs(d9) > MIN_Y_DIST) && (Math.abs(d8) > MIN_Y_DIST)) {
/*  92 */       this.amp_scale *= d10;
/*  93 */       j = 1;
/*     */     }
/*     */     
/*  96 */     if ((i != 0) || (j != 0)) {
/*  97 */       this.changed_ = true;
/*  98 */       this.function_window.changeValues(0, this.chart_width, getChartValues());
/*     */     }
/*     */     
/* 101 */     if (this.prod_ctrl != null) {
/* 102 */       this.prod_ctrl.recompute(0, this.chart_width);
/*     */     }
/* 104 */     if (i != 0)
/* 105 */       this.last_x = paramInt1;
/* 106 */     if (j != 0) {
/* 107 */       this.last_y = paramInt2;
/*     */     }
/*     */   }
/*     */   
/*     */   public void mouseStop() {
/* 112 */     if (this.conv_ctrl != null) {
/* 113 */       this.conv_ctrl.recompute();
/*     */     }
/*     */   }
/*     */   
/*     */   public void resize(int paramInt1, int paramInt2) {
/* 118 */     int i = paramInt1;
/*     */     
/* 120 */     if (this.chart_width != i) {
/* 121 */       this.chart_values = new double[i];
/*     */     }
/* 123 */     this.chart_width = i;
/* 124 */     this.changed_ = true;
/* 125 */     this.function_window.changeValues(0, this.chart_width, getChartValues());
/*     */   }
/*     */   
/*     */   public void normalize()
/*     */   {
/* 130 */     ConvolutionSlide.__instance.resetPositions();
/*     */     
/* 132 */     double d1 = 0.0D;
/* 133 */     for (int i = 0; i < this.chart_width; i++) {
/* 134 */       d1 += this.chart_values[i];
/*     */     }
/* 136 */     double d2 = this.function_window.getXStart();
/* 137 */     double d3 = this.function_window.getXEnd();
/* 138 */     d1 *= (d3 - d2) / this.chart_width;
/*     */     
/* 140 */     if (d1 < 1.0E-5D) {
/* 141 */       return;
/*     */     }
/* 143 */     this.amp_scale /= d1;
/*     */     
/* 145 */     this.changed_ = true;
/* 146 */     this.function_window.changeValues(0, this.chart_width, getChartValues());
/*     */     
/* 148 */     if (this.prod_ctrl != null) {
/* 149 */       this.prod_ctrl.recompute(0, this.chart_width);
/*     */     }
/* 151 */     if (this.conv_ctrl != null) {
/* 152 */       this.conv_ctrl.recompute();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {}
/*     */   
/*     */ 
/*     */   public void setProductController(ProductCTFunctionController paramProductCTFunctionController)
/*     */   {
/* 162 */     this.prod_ctrl = paramProductCTFunctionController;
/*     */   }
/*     */   
/*     */   public void setConvController(ConvCTFunctionController paramConvCTFunctionController)
/*     */   {
/* 167 */     this.conv_ctrl = paramConvCTFunctionController;
/*     */   }
/*     */   
/* 170 */   public int getChartWidth() { return this.chart_width; }
/*     */   
/*     */   public double[] getChartValues()
/*     */   {
/* 174 */     if (this.changed_) {
/* 175 */       this.changed_ = false;
/* 176 */       double d1 = this.function_window.getXStart();
/* 177 */       double d2 = this.function_window.getXEnd();
/* 178 */       double d3 = this.per_scale * (d2 - d1) / this.chart_width;
/* 179 */       double d4 = d1 * this.per_scale;
/* 180 */       for (int i = 0; i < this.chart_width; i++) {
/* 181 */         this.chart_values[i] = (this.amp_scale * this.cur_function.value(d4));
/* 182 */         d4 += d3;
/*     */       }
/*     */     }
/*     */     
/* 186 */     return this.chart_values;
/*     */   }
/*     */   
/*     */   public void setAmpScale(double paramDouble)
/*     */   {
/* 191 */     this.amp_scale = paramDouble;
/* 192 */     this.changed_ = true;
/* 193 */     this.function_window.changeValues(0, this.chart_width, getChartValues());
/*     */   }
/*     */   
/*     */   public void setPerScale(double paramDouble)
/*     */   {
/* 198 */     this.per_scale = paramDouble;
/* 199 */     this.changed_ = true;
/* 200 */     this.function_window.changeValues(0, this.chart_width, getChartValues());
/*     */   }
/*     */   
/*     */   public void scaleAmp(double paramDouble)
/*     */   {
/* 205 */     this.amp_scale *= paramDouble;
/* 206 */     this.changed_ = true;
/* 207 */     this.function_window.changeValues(0, this.chart_width, getChartValues());
/*     */   }
/*     */   
/*     */   public void scalePer(double paramDouble)
/*     */   {
/* 212 */     this.per_scale *= paramDouble;
/* 213 */     this.changed_ = true;
/* 214 */     this.function_window.changeValues(0, this.chart_width, getChartValues());
/*     */   }
/*     */   
/* 217 */   protected static double X_MIN_SCALE = 0.05D;
/* 218 */   protected static double Y_MIN_SCALE = 0.05D;
/* 219 */   protected static double MIN_X_DIST = 0.075D;
/* 220 */   protected static double MIN_Y_DIST = 0.06D;
/* 221 */   protected static double MIN_SCALE_FAC = 1.0E-5D;
/*     */   protected transient boolean changed_;
/*     */   protected transient double scale_factor;
/* 224 */   protected transient double amp_scale = 1.0D;
/* 225 */   protected transient double per_scale = 1.0D;
/*     */   protected transient int chart_width;
/*     */   protected transient double[] chart_values;
/* 228 */   protected transient int last_x = -1;
/* 229 */   protected transient int last_y = -1;
/*     */   protected transient CTFunctionWindow function_window;
/*     */   protected transient ProductCTFunctionController prod_ctrl;
/*     */   protected transient ConvCTFunctionController conv_ctrl;
/*     */   protected transient Function cur_function;
/*     */ }


/* Location:              /Users/masonbartle/Downloads/special_function_convolution.jar!/ScalableCTFunctionController.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */