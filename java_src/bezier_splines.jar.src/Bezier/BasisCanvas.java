/*    */ package Bezier;
/*    */ 
/*    */ import java.awt.Canvas;
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Dimension;
/*    */ import java.awt.FontMetrics;
/*    */ import java.awt.Graphics;
/*    */ 
/*    */ public class BasisCanvas extends Canvas
/*    */ {
/*    */   BasisFnc m_basis;
/*    */   
/*    */   public BasisCanvas(BasisFnc basis)
/*    */   {
/* 16 */     setBackground(Color.black);
/*    */     
/* 18 */     this.m_basis = basis;
/* 19 */     this.m_basis.setContainer(this);
/* 20 */     resize(125, 125);
/*    */   }
/*    */   
/*    */   public void setBasisFnc(BasisFnc b)
/*    */   {
/* 25 */     this.m_basis = b;
/*    */   }
/*    */   
/*    */ 
/*    */   public void drawAxes(Graphics g)
/*    */   {
/* 31 */     java.awt.Font theFont = BezierFonts.m_courierPlain12;
/* 32 */     int hor = 15;
/* 33 */     int ver = 15;
/* 34 */     int off = 4;
/*    */     
/* 36 */     Dimension d = size();
/*    */     
/* 38 */     g.setColor(Color.white);
/*    */     
/* 40 */     g.drawLine(15, d.height - 15, d.width - 15, d.height - 15);
/*    */     
/*    */ 
/* 43 */     g.drawLine(15, d.height - 15, 15, 15);
/*    */     
/*    */ 
/* 46 */     g.setFont(theFont);
/* 47 */     FontMetrics fm = g.getFontMetrics();
/* 48 */     g.drawString("0", off, d.height - off);
/* 49 */     g.drawString("t", d.width / 2, d.height - off);
/* 50 */     g.drawString("1", d.width - hor, d.height - off);
/* 51 */     g.drawString("1", off, ver + fm.getAscent() / 2);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void paint(Graphics g)
/*    */   {
/* 58 */     drawAxes(g);
/* 59 */     this.m_basis.Draw(g);
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/bezier_splines.jar!/Bezier/BasisCanvas.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */