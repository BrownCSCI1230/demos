/*     */ package Bezier;
/*     */ 
/*     */ import java.awt.Canvas;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Point;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ public abstract class Spline
/*     */   extends ParametricLine
/*     */ {
/*     */   protected static final int LABEL_OFFSET = 12;
/*     */   protected EqnProcessor m_eqnProc;
/*  16 */   protected Random m_random = new Random();
/*  17 */   protected Color m_color = Color.white;
/*  18 */   protected Color m_tangentColor = Color.gray;
/*  19 */   protected boolean m_showTangents = true;
/*  20 */   protected boolean m_showLabels = true;
/*  21 */   protected boolean m_showColor = true;
/*  22 */   protected boolean m_showControlPoints = true;
/*     */   
/*     */   protected Point m_hintPt;
/*     */   protected Canvas m_canvas;
/*     */   protected int m_canvasWidth;
/*     */   protected int m_canvasHeight;
/*  28 */   protected Color m_c1 = Color.magenta; protected Color m_c2 = Color.yellow;
/*  29 */   protected Color m_c3 = Color.cyan; protected Color m_c4 = Color.green;
/*  30 */   public Point m_p1 = new Point(50, 200); public Point m_p2 = new Point(250, 150);
/*  31 */   public Point m_p3 = new Point(300, 300); public Point m_p4 = new Point(500, 100);
/*     */   protected BasisFnc m_bas1;
/*     */   protected BasisFnc m_bas2;
/*     */   protected BasisFnc m_bas3;
/*     */   protected BasisFnc m_bas4;
/*     */   
/*  37 */   public Spline(BasisFnc paramBasisFnc1, BasisFnc paramBasisFnc2, BasisFnc paramBasisFnc3, BasisFnc paramBasisFnc4) { this.m_bas1 = paramBasisFnc1;
/*  38 */     this.m_bas2 = paramBasisFnc2;
/*  39 */     this.m_bas3 = paramBasisFnc3;
/*  40 */     this.m_bas4 = paramBasisFnc4;
/*     */   }
/*     */   
/*     */ 
/*     */   public Spline() {}
/*     */   
/*     */ 
/*     */   public Point FindControlPoint(int paramInt1, int paramInt2)
/*     */   {
/*  49 */     if (MouseOnPt(paramInt1, paramInt2, this.m_p1))
/*  50 */       return this.m_p1;
/*  51 */     if (MouseOnPt(paramInt1, paramInt2, this.m_p2))
/*  52 */       return this.m_p2;
/*  53 */     if (MouseOnPt(paramInt1, paramInt2, this.m_p3))
/*  54 */       return this.m_p3;
/*  55 */     if (MouseOnPt(paramInt1, paramInt2, this.m_p4)) {
/*  56 */       return this.m_p4;
/*     */     }
/*  58 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public abstract void CalcPtForTValue(double paramDouble, Point paramPoint, Graphics paramGraphics);
/*     */   
/*     */ 
/*     */   public void setCanvas(Canvas paramCanvas, int paramInt1, int paramInt2)
/*     */   {
/*  67 */     this.m_canvas = paramCanvas;
/*  68 */     this.m_canvasWidth = paramInt1;
/*  69 */     this.m_canvasHeight = paramInt2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void highlightPoint(Graphics paramGraphics, Point paramPoint)
/*     */   {
/*  76 */     paramGraphics.fillOval(paramPoint.x - this.PT_RADIUS * 2, paramPoint.y - this.PT_RADIUS * 2, 
/*  77 */       this.PT_DIAMETER * 2, this.PT_DIAMETER * 2);
/*     */   }
/*     */   
/*     */   public void Draw(Graphics paramGraphics)
/*     */   {
/*  82 */     Draw(paramGraphics, false);
/*     */   }
/*     */   
/*     */   public void Draw(Graphics paramGraphics, boolean paramBoolean)
/*     */   {
/*  87 */     paramGraphics.setColor(this.m_color);
/*     */     
/*  89 */     if (this.m_hintPt != null) {
/*  90 */       paramGraphics.fillOval(this.m_hintPt.x - this.PT_DIAMETER * 2, this.m_hintPt.y - this.PT_DIAMETER * 2, 
/*  91 */         this.PT_DIAMETER * 4, this.PT_DIAMETER * 4);
/*  92 */       this.m_hintPt = null;
/*     */     }
/*     */     
/*  95 */     if (this.m_showTangents) {
/*  96 */       paramGraphics.setColor(this.m_tangentColor);
/*  97 */       paramGraphics.drawLine(this.m_p1.x, this.m_p1.y, this.m_p2.x, this.m_p2.y);
/*  98 */       paramGraphics.drawLine(this.m_p4.x, this.m_p4.y, this.m_p3.x, this.m_p3.y);
/*  99 */       if (this.m_showLabels) {
/* 100 */         paramGraphics.drawString("R1", (this.m_p1.x + this.m_p2.x) / 2 - 12, (this.m_p1.y + this.m_p2.y) / 2 - 12);
/* 101 */         paramGraphics.drawString("R4", (this.m_p4.x + this.m_p3.x) / 2 - 12, (this.m_p4.y + this.m_p3.y) / 2 - 12);
/*     */       }
/* 103 */       paramGraphics.setColor(this.m_color);
/*     */     }
/*     */     
/* 106 */     Point localPoint1 = new Point(0, 0);Point localPoint2 = new Point(0, 0);
/*     */     
/* 108 */     for (double d = 0.0D; d < 1.01D; d += this.m_tValue) {
/* 109 */       CalcPtForTValue(d, localPoint2, paramGraphics);
/* 110 */       if (d == 0.0D) {
/* 111 */         localPoint1.x = localPoint2.x;
/* 112 */         localPoint1.y = localPoint2.y;
/*     */       }
/* 114 */       paramGraphics.drawLine(localPoint1.x, localPoint1.y, localPoint2.x, localPoint2.y);
/*     */       
/*     */ 
/* 117 */       if ((paramBoolean) && (this.m_eqnProc != null))
/*     */       {
/* 119 */         this.m_eqnProc.update(this.m_p1, this.m_p2, this.m_p3, this.m_p4, localPoint2, d);
/*     */         
/* 121 */         highlightPoint(paramGraphics, localPoint2);
/* 122 */         this.m_bas1.highlightPoint(d);
/* 123 */         this.m_bas2.highlightPoint(d);
/* 124 */         this.m_bas3.highlightPoint(d);
/* 125 */         this.m_bas4.highlightPoint(d);
/*     */         try
/*     */         {
/* 128 */           Thread.sleep(1000L);
/*     */ 
/*     */         }
/*     */         catch (InterruptedException localInterruptedException) {}
/*     */ 
/*     */       }
/* 134 */       else if (this.m_showTPoints) {
/* 135 */         paramGraphics.fillOval(localPoint2.x - this.PT_RADIUS, localPoint2.y - this.PT_RADIUS, 
/* 136 */           this.PT_DIAMETER, this.PT_DIAMETER);
/*     */       }
/* 138 */       localPoint1.x = localPoint2.x;
/* 139 */       localPoint1.y = localPoint2.y;
/*     */     }
/*     */     
/* 142 */     if (this.m_showColor)
/* 143 */       paramGraphics.setColor(this.m_c1);
/* 144 */     if (this.m_showControlPoints)
/* 145 */       paramGraphics.fillOval(this.m_p1.x - this.PT_RADIUS, this.m_p1.y - this.PT_RADIUS, this.PT_DIAMETER, this.PT_DIAMETER);
/* 146 */     if (this.m_showLabels) {
/* 147 */       paramGraphics.drawString("P1", this.m_p1.x - 12, this.m_p1.y - 12);
/*     */     }
/* 149 */     if (this.m_showColor)
/* 150 */       paramGraphics.setColor(this.m_c2);
/* 151 */     if (this.m_showControlPoints)
/* 152 */       paramGraphics.fillOval(this.m_p2.x - this.PT_RADIUS, this.m_p2.y - this.PT_RADIUS, this.PT_DIAMETER, this.PT_DIAMETER);
/* 153 */     if (this.m_showLabels) {
/* 154 */       paramGraphics.drawString("P2", this.m_p2.x - 12, this.m_p2.y - 12);
/*     */     }
/* 156 */     if (this.m_showColor)
/* 157 */       paramGraphics.setColor(this.m_c3);
/* 158 */     if (this.m_showControlPoints)
/* 159 */       paramGraphics.fillOval(this.m_p3.x - this.PT_RADIUS, this.m_p3.y - this.PT_RADIUS, this.PT_DIAMETER, this.PT_DIAMETER);
/* 160 */     if (this.m_showLabels) {
/* 161 */       paramGraphics.drawString("P3", this.m_p3.x - 12, this.m_p3.y - 12);
/*     */     }
/* 163 */     if (this.m_showColor)
/* 164 */       paramGraphics.setColor(this.m_c4);
/* 165 */     if (this.m_showControlPoints)
/* 166 */       paramGraphics.fillOval(this.m_p4.x - this.PT_RADIUS, this.m_p4.y - this.PT_RADIUS, this.PT_DIAMETER, this.PT_DIAMETER);
/* 167 */     if (this.m_showLabels) {
/* 168 */       paramGraphics.drawString("P4", this.m_p4.x - 12, this.m_p4.y - 12);
/*     */     }
/* 170 */     if ((paramBoolean) && (this.m_eqnProc != null)) {
/* 171 */       this.m_eqnProc.update();
/*     */     }
/*     */   }
/*     */   
/* 175 */   public void flipShowColor() { this.m_showColor = (!this.m_showColor); }
/* 176 */   public void flipShowTangents() { this.m_showTangents = (!this.m_showTangents); }
/* 177 */   public void flipShowLabels() { this.m_showLabels = (!this.m_showLabels); }
/* 178 */   public void flipShowControlPoints() { this.m_showControlPoints = (!this.m_showControlPoints); }
/*     */   
/*     */   public void setColors(Color paramColor1, Color paramColor2) {
/* 181 */     this.m_color = paramColor1;
/* 182 */     this.m_tangentColor = paramColor2;
/*     */   }
/*     */   
/*     */   public void randomize(Dimension paramDimension) {
/* 186 */     int i = 25;
/* 187 */     int j = 25;
/*     */     
/* 189 */     int k = paramDimension.width - i - i;
/* 190 */     int m = 400;
/*     */     
/* 192 */     this.m_p1.x = ((int)(this.m_random.nextFloat() * (k - 40) + 20.0F + i));
/* 193 */     this.m_p1.y = ((int)(this.m_random.nextFloat() * (m - 40) + 20.0F + j));
/*     */     
/* 195 */     this.m_p2.x = ((int)(this.m_random.nextFloat() * (k - 40) + 20.0F + i));
/* 196 */     this.m_p2.y = ((int)(this.m_random.nextFloat() * (m - 40) + 20.0F + j));
/*     */     
/* 198 */     this.m_p3.x = ((int)(this.m_random.nextFloat() * (k - 40) + 20.0F + i));
/* 199 */     this.m_p3.y = ((int)(this.m_random.nextFloat() * (m - 40) + 20.0F + j));
/*     */     
/* 201 */     this.m_p4.x = ((int)(this.m_random.nextFloat() * (k - 40) + 20.0F + i));
/* 202 */     this.m_p4.y = ((int)(this.m_random.nextFloat() * (m - 40) + 20.0F + j));
/*     */   }
/*     */   
/*     */   protected void sleep(int paramInt)
/*     */   {
/*     */     try
/*     */     {
/* 209 */       Thread.sleep(paramInt);
/*     */     }
/*     */     catch (InterruptedException localInterruptedException) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void updateEqn(int paramInt1, int paramInt2)
/*     */   {
/* 218 */     if (this.m_eqnProc != null) {
/* 219 */       Point localPoint1 = new Point(0, 0);
/* 220 */       for (double d = 0.0D; d < 1.01D; d += this.m_tValue) {
/* 221 */         CalcPtForTValue(d, localPoint1, null);
/* 222 */         if (MouseOnPt(paramInt1, paramInt2, localPoint1))
/*     */         {
/*     */ 
/* 225 */           int i = 25;
/* 226 */           int j = this.m_canvasHeight - 25;
/* 227 */           localPoint1.x -= i;
/* 228 */           localPoint1.y = (j - paramInt2);
/* 229 */           Point localPoint2 = new Point(this.m_p1.x - i, j - this.m_p1.y);
/* 230 */           Point localPoint3 = new Point(this.m_p2.x - i, j - this.m_p2.y);
/* 231 */           Point localPoint4 = new Point(this.m_p3.x - i, j - this.m_p3.y);
/* 232 */           Point localPoint5 = new Point(this.m_p4.x - i, j - this.m_p4.y);
/*     */           
/* 234 */           this.m_eqnProc.update(localPoint2, localPoint3, localPoint4, localPoint5, 
/* 235 */             localPoint1, d);
/*     */           
/* 237 */           return;
/*     */         }
/*     */       }
/*     */       
/* 241 */       this.m_eqnProc.update(); return;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void hintControlPt(int paramInt1, int paramInt2)
/*     */   {
/* 250 */     this.m_hintPt = FindControlPoint(paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public void unhintControlPt()
/*     */   {
/* 255 */     this.m_hintPt = null;
/*     */   }
/*     */ }


/* Location:              /Users/masonbartle/Downloads/bezier_splines.jar!/Bezier/Spline.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */