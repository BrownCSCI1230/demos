/*    */ package Bezier;
/*    */ 
/*    */ import corejava.Format;
/*    */ import java.awt.Point;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SimpleEqnProcessor
/*    */   extends EqnProcessor
/*    */ {
/*    */   public SimpleEqnProcessor(EquationCanvas qX, EquationCanvas qY)
/*    */   {
/* 15 */     super(qX, qY);this.m_qXDefaultStr = "Q(x) = (1.0-t)*P1 + t*P4";
/* 16 */     this.m_qYDefaultStr = "Q(y) = (1.0-t)*P1 + t*P4";
/* 17 */     update();
/*    */   }
/*    */   
/*    */ 
/*    */   protected void updateEqnStrings()
/*    */   {
/* 23 */     String tStr = new Format("%4.2f").form(this.m_t);
/* 24 */     String x1Str = new Format("%3i").form(this.m_p1.x);
/* 25 */     String x2Str = new Format("%3i").form(this.m_p2.x);
/* 26 */     String x3Str = new Format("%3i").form(this.m_p3.x);
/* 27 */     String x4Str = new Format("%3i").form(this.m_p4.x);
/* 28 */     String xcStr = new Format("%3i").form(this.m_currPt.x);
/* 29 */     String y1Str = new Format("%3i").form(this.m_p1.y);
/* 30 */     String y2Str = new Format("%3i").form(this.m_p2.y);
/* 31 */     String y3Str = new Format("%3i").form(this.m_p3.y);
/* 32 */     String y4Str = new Format("%3i").form(this.m_p4.y);
/* 33 */     String ycStr = new Format("%3i").form(this.m_currPt.y);
/*    */     
/* 35 */     this.m_qXStr = (xcStr + " = (1.0-" + tStr + ")*" + x1Str + " + " + tStr + "*" + x4Str);
/* 36 */     this.m_qYStr = (ycStr + " = (1.0-" + tStr + ")*" + y1Str + " + " + tStr + "*" + y4Str);
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/bezier_splines.jar!/Bezier/SimpleEqnProcessor.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */