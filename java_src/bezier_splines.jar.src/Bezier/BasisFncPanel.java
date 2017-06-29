/*    */ package Bezier;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Container;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.GridBagConstraints;
/*    */ import java.awt.GridBagLayout;
/*    */ import java.awt.Panel;
/*    */ 
/*    */ class BasisFncPanel
/*    */   extends Panel
/*    */ {
/*    */   protected GridBagLayout m_layout;
/*    */   protected GridBagConstraints m_gbc;
/*    */   protected BasisCanvas m_basCan;
/*    */   protected EquationCanvas m_basEqn;
/*    */   protected BasisFnc m_basis;
/*    */   
/*    */   public BasisFncPanel(BasisFnc basis)
/*    */   {
/* 21 */     this.m_layout = new GridBagLayout();this.m_gbc = new GridBagConstraints();this.m_basis = basis;
/* 22 */     this.m_basCan = new BasisCanvas(this.m_basis);
/* 23 */     this.m_basEqn = new EquationCanvas(125, 20);
/* 24 */     this.m_basEqn.setText(this.m_basis.getEqnText());
/* 25 */     this.m_basEqn.setJustify(1);
/*    */     
/* 27 */     setLayout(this.m_layout);
/*    */     
/* 29 */     this.m_gbc.gridx = 0;
/* 30 */     this.m_gbc.gridy = 0;
/* 31 */     this.m_gbc.gridwidth = 1;
/* 32 */     this.m_gbc.gridheight = 1;
/* 33 */     this.m_gbc.fill = 2;
/* 34 */     this.m_gbc.anchor = 11;
/* 35 */     this.m_layout.setConstraints(this.m_basCan, this.m_gbc);
/* 36 */     add(this.m_basCan);
/*    */     
/* 38 */     this.m_gbc.gridx = 0;
/* 39 */     this.m_gbc.gridy = 1;
/* 40 */     this.m_gbc.gridwidth = 1;
/* 41 */     this.m_gbc.gridheight = 1;
/* 42 */     this.m_gbc.fill = 2;
/* 43 */     this.m_gbc.anchor = 11;
/* 44 */     this.m_layout.setConstraints(this.m_basEqn, this.m_gbc);
/* 45 */     add(this.m_basEqn);
/*    */   }
/*    */   
/*    */ 
/*    */   public void paint(Graphics g)
/*    */   {
/* 51 */     this.m_basCan.repaint();
/* 52 */     this.m_basEqn.repaint();
/*    */   }
/*    */   
/*    */ 
/*    */   void setBasisFnc(BasisFnc bas)
/*    */   {
/* 58 */     this.m_basis = bas;
/* 59 */     this.m_basEqn.setText(this.m_basis.getEqnText());
/* 60 */     this.m_basCan.setBasisFnc(bas);
/* 61 */     this.m_basis.setContainer(this.m_basCan);
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/bezier_splines.jar!/Bezier/BasisFncPanel.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */