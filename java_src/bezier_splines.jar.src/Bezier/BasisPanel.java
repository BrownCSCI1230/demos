/*    */ package Bezier;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Container;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.GridLayout;
/*    */ import java.awt.Panel;
/*    */ 
/*    */ public class BasisPanel extends Panel
/*    */ {
/*    */   protected BasisFncPanel m_pan1;
/*    */   protected BasisFncPanel m_pan2;
/*    */   protected BasisFncPanel m_pan3;
/*    */   protected BasisFncPanel m_pan4;
/*    */   
/*    */   public BasisPanel(BasisFnc bas1, BasisFnc bas2, BasisFnc bas3, BasisFnc bas4)
/*    */   {
/* 18 */     setLayout(new GridLayout(4, 1));
/*    */     
/* 20 */     this.m_pan1 = new BasisFncPanel(bas1);
/* 21 */     this.m_pan2 = new BasisFncPanel(bas2);
/* 22 */     this.m_pan3 = new BasisFncPanel(bas3);
/* 23 */     this.m_pan4 = new BasisFncPanel(bas4);
/*    */     
/* 25 */     add(this.m_pan1);
/* 26 */     add(this.m_pan2);
/* 27 */     add(this.m_pan3);
/* 28 */     add(this.m_pan4);
/*    */   }
/*    */   
/*    */   public void paint(Graphics g)
/*    */   {
/* 33 */     this.m_pan1.repaint();
/* 34 */     this.m_pan2.repaint();
/* 35 */     this.m_pan3.repaint();
/* 36 */     this.m_pan4.repaint();
/*    */   }
/*    */   
/*    */   public void setBasisFncs(BasisFnc bas1, BasisFnc bas2, BasisFnc bas3, BasisFnc bas4)
/*    */   {
/* 41 */     this.m_pan1.setBasisFnc(bas1);
/* 42 */     this.m_pan2.setBasisFnc(bas2);
/* 43 */     this.m_pan3.setBasisFnc(bas3);
/* 44 */     this.m_pan4.setBasisFnc(bas4);
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/bezier_splines.jar!/Bezier/BasisPanel.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */