/*     */ package Bezier;
/*     */ 
/*     */ import java.awt.Canvas;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ 
/*     */ public class EquationCanvas
/*     */   extends Canvas
/*     */ {
/*     */   protected String m_equStr;
/*     */   protected Font m_bigFont;
/*     */   protected Font m_smallFont;
/*     */   protected int m_xSize;
/*     */   protected int m_ySize;
/*     */   protected int m_justify;
/*     */   
/*     */   public EquationCanvas()
/*     */   {
/*  22 */     this.m_bigFont = BezierFonts.m_courierPlain12;this.m_smallFont = BezierFonts.m_courierPlain10;this.m_xSize = 640;this.m_ySize = 20;resize(this.m_xSize, this.m_ySize);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public EquationCanvas(int xSize, int ySize)
/*     */   {
/*  30 */     this.m_bigFont = BezierFonts.m_courierPlain12;this.m_smallFont = BezierFonts.m_courierPlain10;this.m_xSize = 640;this.m_ySize = 20;this.m_xSize = xSize;
/*  31 */     this.m_ySize = ySize;
/*  32 */     resize(this.m_xSize, this.m_ySize);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public EquationCanvas(Font bigFont, Font smallFont)
/*     */   {
/*  40 */     this.m_bigFont = BezierFonts.m_courierPlain12;this.m_smallFont = BezierFonts.m_courierPlain10;this.m_xSize = 640;this.m_ySize = 20;this.m_bigFont = bigFont;
/*  41 */     this.m_smallFont = smallFont;
/*     */     
/*  43 */     resize(this.m_xSize, this.m_ySize);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public EquationCanvas(Font bigFont, Font smallFont, int xSize, int ySize)
/*     */   {
/*  51 */     this.m_bigFont = BezierFonts.m_courierPlain12;this.m_smallFont = BezierFonts.m_courierPlain10;this.m_xSize = 640;this.m_ySize = 20;this.m_bigFont = bigFont;
/*  52 */     this.m_smallFont = smallFont;
/*     */     
/*  54 */     this.m_xSize = xSize;
/*  55 */     this.m_ySize = ySize;
/*  56 */     resize(this.m_xSize, this.m_ySize);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setText(String equStr)
/*     */   {
/*  62 */     this.m_equStr = equStr;
/*     */     
/*  64 */     Graphics g = getGraphics();
/*  65 */     if (g != null) {
/*  66 */       paint(g);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setJustify(int justify)
/*     */   {
/*  72 */     this.m_justify = justify;
/*     */   }
/*     */   
/*     */ 
/*     */   public void paint(Graphics g)
/*     */   {
/*  78 */     g.clearRect(0, 0, this.m_xSize, this.m_ySize);
/*     */     
/*  80 */     g.setFont(this.m_bigFont);
/*  81 */     FontMetrics fm = g.getFontMetrics();
/*     */     
/*  83 */     int ht = fm.getMaxAscent() / 2;
/*  84 */     int curX = 0;
/*  85 */     int curY = fm.getMaxAscent();
/*     */     int width;
/*  87 */     if (this.m_justify == 1)
/*     */     {
/*     */ 
/*     */ 
/*  91 */       width = fm.stringWidth(this.m_equStr) - 10;
/*  92 */       Dimension d = size();
/*  93 */       curX = (d.width - width) / 2;
/*     */     }
/*     */     
/*  96 */     String subEquStr = this.m_equStr;
/*  97 */     int caretIndex = subEquStr.indexOf("^");
/*  98 */     while (caretIndex != -1) {
/*  99 */       g.setFont(this.m_bigFont);
/* 100 */       fm = g.getFontMetrics();
/* 101 */       width = fm.stringWidth(subEquStr.substring(0, caretIndex));
/* 102 */       g.drawString(subEquStr.substring(0, caretIndex), curX, curY);
/* 103 */       curX += width;
/*     */       
/* 105 */       g.setFont(this.m_smallFont);
/* 106 */       fm = g.getFontMetrics();
/* 107 */       width = fm.stringWidth(subEquStr.substring(caretIndex + 1, caretIndex + 2));
/* 108 */       g.drawString(subEquStr.substring(caretIndex + 1, caretIndex + 2), curX, curY - ht);
/* 109 */       curX += width;
/*     */       
/* 111 */       subEquStr = subEquStr.substring(caretIndex + 2);
/* 112 */       caretIndex = subEquStr.indexOf("^");
/*     */     }
/* 114 */     g.setFont(this.m_bigFont);
/* 115 */     g.drawString(subEquStr, curX, curY);
/*     */   }
/*     */ }


/* Location:              /Users/masonbartle/Downloads/bezier_splines.jar!/Bezier/EquationCanvas.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */