//
// Generated by JTB 1.3.2
//

package kanga.syntaxtree;

/**
 * The interface which all syntax tree classes must implement.
 */
public interface Node extends java.io.Serializable {
   public void accept(kanga.visitor.Visitor v);
   public <R,A> R accept(kanga.visitor.GJVisitor<R,A> v, A argu);
   public <R> R accept(kanga.visitor.GJNoArguVisitor<R> v);
   public <A> void accept(kanga.visitor.GJVoidVisitor<A> v, A argu);
}

