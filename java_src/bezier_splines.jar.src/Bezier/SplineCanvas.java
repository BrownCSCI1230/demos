/*     */ package Bezier;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Event;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ 
/*     */ public class SplineCanvas extends java.awt.Canvas
/*     */ {
/*     */   protected Spline m_spline;
/*     */   protected java.awt.Point m_currControlPt;
/*  13 */   protected boolean m_hints = false;
/*     */   
/*     */   protected Spline m_optional;
/*  16 */   boolean m_wasDragged = false;
/*     */   
/*     */ 
/*     */   public SplineCanvas(Spline paramSpline)
/*     */   {
/*  21 */     setBackground(java.awt.Color.black);
/*  22 */     this.m_spline = paramSpline;
/*     */   }
/*     */   
/*     */   public void setSpline(Spline paramSpline)
/*     */   {
/*  27 */     Dimension localDimension = size();
/*  28 */     this.m_spline = paramSpline;
/*  29 */     this.m_spline.setCanvas(this, localDimension.width, localDimension.height);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void drawAxes(Graphics paramGraphics)
/*     */   {
/*  36 */     java.awt.Font localFont = BezierFonts.m_courierPlain12;
/*  37 */     int i = 25;
/*  38 */     int j = 25;
/*  39 */     int k = 2;
/*  40 */     int m = 12;
/*  41 */     int n = 5;
/*     */     
/*  43 */     paramGraphics.setFont(localFont);
/*  44 */     FontMetrics localFontMetrics = paramGraphics.getFontMetrics();
/*  45 */     int i1 = localFontMetrics.getAscent() / 3;
/*  46 */     int i2 = localFontMetrics.stringWidth("100") / 2;
/*     */     
/*     */ 
/*  49 */     Dimension localDimension = size();
/*     */     
/*  51 */     paramGraphics.setColor(java.awt.Color.white);
/*     */     
/*  53 */     paramGraphics.drawLine(i, localDimension.height - j, 
/*  54 */       localDimension.width - i, localDimension.height - j);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  60 */     paramGraphics.drawLine(i, localDimension.height - j, 
/*  61 */       i, localDimension.height - j - 400);
/*     */     
/*     */ 
/*     */ 
/*  65 */     paramGraphics.drawString("  0", k, localDimension.height - m);
/*  66 */     paramGraphics.drawString("X", localDimension.width / 2 - 3, localDimension.height - 4);
/*  67 */     paramGraphics.drawString("100", 100 + i - i2, localDimension.height - m);
/*  68 */     paramGraphics.drawLine(100 + i, localDimension.height - j, 100 + i, localDimension.height - j - n);
/*  69 */     paramGraphics.drawString("200", 200 + i - i2, localDimension.height - m);
/*  70 */     paramGraphics.drawLine(200 + i, localDimension.height - j, 200 + i, localDimension.height - j - n);
/*  71 */     paramGraphics.drawLine(300 + i, localDimension.height - j, 300 + i, localDimension.height - j - n);
/*  72 */     paramGraphics.drawString("400", 400 + i - i2, localDimension.height - m);
/*  73 */     paramGraphics.drawLine(400 + i, localDimension.height - j, 400 + i, localDimension.height - j - n);
/*  74 */     paramGraphics.drawString("500", 500 + i - i2, localDimension.height - m);
/*  75 */     paramGraphics.drawLine(500 + i, localDimension.height - j, 500 + i, localDimension.height - j - n);
/*  76 */     paramGraphics.drawString("600", 600 + i - i2, localDimension.height - m);
/*  77 */     paramGraphics.drawLine(600 + i, localDimension.height - j, 600 + i, localDimension.height - j - n);
/*     */     
/*     */ 
/*  80 */     paramGraphics.drawString("Y", k, localDimension.height - j - 200 + i1);
/*  81 */     paramGraphics.drawString("100", k, localDimension.height - j - 100 + i1);
/*  82 */     paramGraphics.drawLine(i, localDimension.height - j - 100, i + n, localDimension.height - j - 100);
/*  83 */     paramGraphics.drawLine(i, localDimension.height - j - 200, i + n, localDimension.height - j - 200);
/*  84 */     paramGraphics.drawString("300", k, localDimension.height - j - 300 + i1);
/*  85 */     paramGraphics.drawLine(i, localDimension.height - j - 300, i + n, localDimension.height - j - 300);
/*  86 */     paramGraphics.drawString("400", k, localDimension.height - j - 400 + i1);
/*  87 */     paramGraphics.drawLine(i, localDimension.height - j - 400, i + n, localDimension.height - j - 400);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void paint(Graphics paramGraphics)
/*     */   {
/*  96 */     Dimension localDimension = size();
/*     */     
/*  98 */     drawAxes(paramGraphics);
/*     */     
/* 100 */     if (this.m_optional != null) {
/* 101 */       this.m_optional.Draw(paramGraphics);
/* 102 */       this.m_spline.setCanvas(this, localDimension.width, localDimension.height);
/*     */     }
/* 104 */     this.m_spline.setCanvas(this, localDimension.width, localDimension.height);
/* 105 */     this.m_spline.Draw(paramGraphics);
/*     */   }
/*     */   
/*     */   public boolean mouseDown(Event paramEvent, int paramInt1, int paramInt2)
/*     */   {
/* 110 */     this.m_currControlPt = this.m_spline.FindControlPoint(paramInt1, paramInt2);
/* 111 */     this.m_spline.updateEqn(paramInt1, paramInt2);
/*     */     
/* 113 */     if ((this.m_currControlPt != null) && 
/* 114 */       (this.m_optional != null) && (this.m_hints)) {
/* 115 */       this.m_optional.hintControlPt(paramInt1, paramInt2);
/* 116 */       repaint();
/*     */     }
/*     */     
/*     */ 
/* 120 */     return true;
/*     */   }
/*     */   
/*     */   public boolean mouseDrag(Event paramEvent, int paramInt1, int paramInt2)
/*     */   {
/* 125 */     if (this.m_currControlPt != null) {
/* 126 */       int i = 25;
/* 127 */       int j = 25;
/*     */       
/* 129 */       Dimension localDimension = size();
/* 130 */       if (paramInt1 < i) { paramInt1 = j;
/*     */       }
/*     */       
/* 133 */       if (paramInt2 < localDimension.height - j - 400)
/* 134 */         paramInt2 = localDimension.height - j - 400;
/* 135 */       if (paramInt1 > localDimension.width - i) paramInt1 = localDimension.width - i;
/* 136 */       if (paramInt2 > localDimension.height - j) { paramInt2 = localDimension.height - j;
/*     */       }
/*     */       
/* 139 */       if ((this.m_optional != null) && (this.m_hints)) {
/* 140 */         this.m_optional.hintControlPt(paramInt1, paramInt2);
/*     */       }
/*     */       
/* 143 */       this.m_currControlPt.x = paramInt1;
/* 144 */       this.m_currControlPt.y = paramInt2;
/*     */       
/* 146 */       repaint();
/*     */       
/* 148 */       this.m_wasDragged = true;
/*     */     }
/* 150 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean mouseUp(Event paramEvent, int paramInt1, int paramInt2)
/*     */   {
/* 156 */     if (this.m_wasDragged) {
/* 157 */       this.m_wasDragged = false;
/*     */       
/* 159 */       int i = 25;
/* 160 */       int j = 25;
/*     */       
/* 162 */       Dimension localDimension = size();
/* 163 */       if (paramInt1 < i) { paramInt1 = j;
/*     */       }
/*     */       
/* 166 */       if (paramInt2 < localDimension.height - j - 400)
/* 167 */         paramInt2 = localDimension.height - j - 400;
/* 168 */       if (paramInt1 > localDimension.width - i) paramInt1 = localDimension.width - i;
/* 169 */       if (paramInt2 > localDimension.height - j) { paramInt2 = localDimension.height - j;
/*     */       }
/*     */       
/*     */ 
/* 173 */       this.m_spline.updateEqn(paramInt1, paramInt2);
/*     */       
/* 175 */       if ((this.m_optional != null) && (this.m_hints)) {
/* 176 */         this.m_optional.unhintControlPt();
/*     */       }
/* 178 */       repaint();
/*     */     }
/*     */     
/* 181 */     return true;
/*     */   }
/*     */   
/*     */   public void setOptional(Spline paramSpline)
/*     */   {
/* 186 */     Dimension localDimension = size();
/* 187 */     this.m_optional = paramSpline;
/* 188 */     this.m_optional.setCanvas(this, localDimension.width, localDimension.height);
/*     */   }
/*     */   
/*     */   public void removeOptional()
/*     */   {
/* 193 */     this.m_optional = null;
/*     */   }
/*     */   
/*     */ 
/*     */   public void flipHints()
/*     */   {
/* 199 */     this.m_hints = (!this.m_hints);
/*     */   }
/*     */ }


/* Location:              /Users/masonbartle/Downloads/bezier_splines.jar!/Bezier/SplineCanvas.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */