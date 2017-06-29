/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HDrawEvent
/*    */ {
/*    */   private HRobotPart _part;
/*    */   private boolean _drawCenter;
/*    */   private HPosition _pos;
/*    */   
/*    */   public HDrawEvent(HRobotPart paramHRobotPart, boolean paramBoolean, HPosition paramHPosition)
/*    */   {
/* 24 */     this._part = paramHRobotPart;
/* 25 */     this._drawCenter = paramBoolean;
/* 26 */     this._pos = paramHPosition;
/*    */   }
/*    */   
/*    */   public HRobotPart getPart() {
/* 30 */     return this._part;
/*    */   }
/*    */   
/*    */   public boolean drawCenter() {
/* 34 */     return this._drawCenter;
/*    */   }
/*    */   
/*    */   public HPosition getCenter() {
/* 38 */     return this._pos;
/*    */   }
/*    */ }


/* Location:              /Users/masonbartle/Downloads/scenegraph_builder.jar!/HDrawEvent.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       0.7.1
 */