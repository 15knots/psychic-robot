// $Id: TNode.java,v 1.2 2002/10/19 20:09:03 weber Exp $
package parser.grammar;

import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.Hashtable;

import antlr.CommonAST;
import antlr.Token;
import antlr.collections.AST;


/**
 * Class TNode is an implementation of the AST interface and adds many useful
 * features: <br>
 * It is double-linked for reverse searching. (this is currently
 * incomplete, in that method doubleLink() must be called after any changes
 * to the tree to maintain the reverse links). <br>
 * It can store a definition node (defNode), so that nodes such as scoped names
 * can refer to the node that
 * defines the name. <br>
 * It stores line numbers for nodes. Searches for parents
 * and children of a tree can be done based on their type.
 * The tree can be
 * printed to System.out using a lisp-style syntax.
 */
public class TNode
  extends CommonAST
{
  protected int lineNum = 0;
  protected TNode defNode;
  protected TNode up;
  protected TNode left;
  protected boolean marker = false;
  protected Hashtable attributes = null;

  public void initialize(Token token)
  {
    super.initialize(token);

    CToken tok = (CToken)token;
    setLineNum(tok.getLine());
    setAttribute("source",
		 tok.getSource());
    // fortlaufend eNummerierung des vom Lexer erzeugten Token...
    setAttribute("tokenNumber",
		 new Integer(tok.getTokenNumber()));
  }

  public void initialize(AST tr)
  {
    super.initialize(tr);

    TNode t = (TNode)tr;
    setLineNum(t.getLineNum());
    setDefNode(t.getDefNode());
    this.attributes = t.getAttributesTable();
  }

  /**
   * Get the marker value for this node. This member is a general-use marker.
   */
  public boolean getMarker()
  {
    return marker;
  }

  /**
   * Set the marker value for this node. This property is a general-use
   * boolean marker.
   */
  public void setMarker(boolean marker_)
  {
    marker = marker_;
  }

  /**
   * get the hashtable that holds attribute values.
   */
  public Hashtable getAttributesTable()
  {

    if(attributes == null) {
      attributes = new Hashtable(7);
    }

    return attributes;
  }

  /**
   * set an attribute in the attribute table.
   */
  public void setAttribute(String attrName, Object value)
  {

    if(attributes == null) {
      attributes = new Hashtable(7);
    }

    attributes.put(attrName, value);
  }

  /**
   * lookup the attribute name in the attribute table. If the value does not
   * exist, it returns null.
   */
  public Object getAttribute(String attrName)
  {

    if(attributes == null) {
      return null;
    } else {
      return attributes.get(attrName);
    }
  }

  /**
   * Get the line number for this node. If the line number is 0, search for a
   * non-zero line num among children
   */
  public int getLineNum()
  {

    if(lineNum != 0) {
      return lineNum;
    } else if(down == null) {
      return lineNum;
    } else {
      return ((TNode)down).getLocalLineNum();
    }
  }

  public int getLocalLineNum()
  {

    if(lineNum != 0) {
      return lineNum;
    } else if(down == null) {

      if(right == null) {
	return lineNum;
      } else {
	return ((TNode)right).getLocalLineNum();
      }
    } else {
      return ((TNode)down).getLocalLineNum();
    }
  }

  /**
   * Set the line number for this node
   */
  public void setLineNum(int lineNum_)
  {
    lineNum = lineNum_;
  }

  /**
   * return the last child of this node, or null if there is none
   */
  public TNode getLastChild()
  {
    TNode down = (TNode)getFirstChild();

    if(down != null) {
      return down.getLastSibling();
    } else {
      return null;
    }
  }

  /**
   * return the last sibling of this node, which is this if the next sibling
   * is null
   */
  public TNode getLastSibling()
  {
    TNode next = (TNode)getNextSibling();

    if(next != null) {
      return next.getLastSibling();
    } else {
      return this;
    }
  }

  /**
   * return the first sibling of this node, which is this if the prev sibling
   * is null
   */
  public TNode getFirstSibling()
  {
    TNode prev = (TNode)left;

    if(prev != null) {
      return prev.getFirstSibling();
    } else {
      return this;
    }
  }

  /**
   * return the parent node of this node
   */
  public TNode getParent()
  {
    return (TNode)getFirstSibling().up;
  }

  /**
   * add the new node as a new sibling, inserting it ahead of any existing
   * next sibling.  This method maintains double-linking. if node is null,
   * nothing happens.  If the node has siblings, then they are added in as
   * well.
   */
  public void addSibling(AST node)
  {

    if(node == null) {
      return;
    }

    TNode next = (TNode)right;
    right = (TNode)node;
    ((TNode)node).left = this;
    TNode nodeLastSib = ((TNode)node).getLastSibling();
    nodeLastSib.right = next;

    if(next != null) {
      next.left = nodeLastSib;
    }
  }

  /**
   * return the number of children of this node
   */
  public int numberOfChildren()
  {
    int count = 0;
    AST child = getFirstChild();

    while(child != null) {
      count++;
      child = child.getNextSibling();
    }

    return count;
  }

  /**
   * remove this node from the tree, resetting sibling and parent pointers as
   * necessary.  This method maintains double-linking
   */
  public void removeSelf()
  {
    TNode parent = (TNode)up;
    TNode prev = (TNode)left;
    TNode next = (TNode)right;

    if(parent != null) {
      parent.down = next;

      if(next != null) {
	next.up = parent;
	next.left = prev; // which should be null
      }
    } else {

      if(prev != null) {
	prev.right = next;
      }

      if(next != null) {
	next.left = prev;
      }
    }
  }

  /**
   * return the def node for this node
   */
  public TNode getDefNode()
  {
    return defNode;
  }

  /**
   * set the def node for this node
   */
  public void setDefNode(TNode n)
  {
    defNode = n;
  }

  /**
   * return a deep copy of this node, and all sub nodes. New tree is
   * doubleLinked, with no parent or siblings. Marker value is not copied!
   */
  public TNode deepCopy()
  {
    TNode copy = new TNode();
    copy.initialize( getType(), getText());
    copy.lineNum = lineNum;
    copy.defNode = defNode;

    if(attributes != null) {
      copy.attributes = (Hashtable)attributes.clone();
    }

    if(down != null) {
      copy.down = ((TNode)down).deepCopyWithRightSiblings();
    }

    copy.doubleLink();
    return copy;
  }

  /**
   * return a deep copy of this node, all sub nodes, and right siblings. New
   * tree is doubleLinked, with no parent or left siblings. defNode is not
   * copied
   */
  public TNode deepCopyWithRightSiblings()
  {
    TNode copy = new TNode();
    copy.initialize( getType(), getText());
    copy.lineNum = lineNum;
    copy.defNode = defNode;

    if(attributes != null) {
      copy.attributes = (Hashtable)attributes.clone();
    }

    if(down != null) {
      copy.down = ((TNode)down).deepCopyWithRightSiblings();
    }

    if(right != null) {
      copy.right = ((TNode)right).deepCopyWithRightSiblings();
    }

    copy.doubleLink();
    return copy;
  }

  /**
   * return a short string representation of the node
   */
  public String toString()
  {
    StringBuffer str = new StringBuffer(super.toString());

    if(this.getLineNum() != 0) {
      str.append(" line="+ getLineNum());
    }

    Enumeration keys = getAttributesTable().keys();

    while(keys.hasMoreElements()) {
      String key = (String)keys.nextElement();
      str.append(" "+key+"="+ getAttribute(key));
    }

    return str.toString();
  }

  /**
   * print given tree to System.out
   * @deprecated ANTLR hat mittlerweile bessere Möglichkeiten, dies
   *             auszugeben, z.B. parser.getTokenName( ast.getType())
   */
  public static void printTree(AST t)
  {

    if(t == null) {
      return;
    }

    printASTNode(t, 0);
    System.out.print("\n");
  }

  /**
   * protected method that does the work of printing
   */
  protected static void printASTNode(AST t, int indent)
  {
    AST child1;
    AST next;
    child1 = t.getFirstChild();

    System.out.print("\n");

    for(int i = 0; i < indent; i++) {
      System.out.print("  ");
    }

    if(child1 != null) {
      System.out.print("(");
    }

    System.out.print( t.toString());

    AST def = ((TNode)t).getDefNode();
    if( def != null) {
      System.out.print("["+ def.toString()+"]");
    }


    if(child1 != null) {
      printASTNode(child1, indent+1);

      System.out.print("\n");

      for(int i = 0; i < indent; i++) {
	System.out.print("  ");
      }

      System.out.print(")");
    }

    next = t.getNextSibling();

    if(next != null) {
      printASTNode(next, indent);
    }
  }

  /**
   * set up reverse links between this node and its first child and its first
   * sibling, and link those as well
   */
  public void doubleLink()
  {
    TNode right = (TNode)getNextSibling();

    if(right != null) {
      right.left = this;
      right.doubleLink();
    }

    TNode down = (TNode)getFirstChild();

    if(down != null) {
      down.up = this;
      down.doubleLink();
    }
  }

  /**
   * find first parent of the given type, return null on failure
   */
  public TNode parentOfType(int type)
  {

    if(up == null) {

      if(left == null) {
	return null;
      } else {
	return left.parentOfType(type);
      }
    }

    if(up.getType() == type) {
      return up;
    }

    return up.parentOfType(type);
  }

  /**
   * find the first child of the node of the given type, return null on
   * failure
   */
  public TNode firstChildOfType(int type)
  {
    TNode down = (TNode)getFirstChild();

    if(down == null) {
      return null;
    }

    if(down.getType() == type) {
      return down;
    }

    return down.firstSiblingOfType(type);
  }

  /**
   * find the first sibling of the node of the given type, return null on
   * failure
   */
  public TNode firstSiblingOfType(int type)
  {
    TNode right = (TNode)getNextSibling();

    if(right == null) {
      return null;
    }

    if(right.getType() == type) {
      return right;
    }

    return right.firstSiblingOfType(type);
  }
}