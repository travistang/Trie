// import java.util.List;
import java.util.ArrayList;

public class Node {
  private Character character;
  private Node parent;
  private Integer points = null;

  private ArrayList<Node> children = new ArrayList<Node>();

  public Node() { }

  public Node(char ch, Node parent) {
    this.character = new Character(ch);
    this.parent = parent;
  }

  public void setChild(char ch, Node child) {
    /**
      Prevent inserting node with the same key with one of the children
    */
    if(getChild(ch) == null) {
      children.add(child);
    }
  }

  public Node getChild(char ch) {
    // otherwise query each nodes
    for(Node n : children) {
      if(n.character == ch) return n;
    }

    // nothing found.
    return null;
  }

  private Node findInChildren(String key) {
    for(Node child : children) {
      Node result = child.find(key);
      if(result != null) return result;
    }
    return null;
  }

  public Node find(String key){
    char firstCharacter = key.charAt(0);
    String subString = key.substring(1);

    if(character == null) {
      return findInChildren(key);
    }
    // matches, proceed in searching
    if(character == firstCharacter) {
      if(subString.length() == 0) {
        return this;
      }
      return findInChildren(subString);
    } else {
      // this string is not for this node.
      return null;
    }
  }
  /*
    Delete This node if no children is there.
    Some cases to consider:
      - When there are no siblings
      - When there are siblings
      - When there are descendents
  */
  public void delete() {
    // don't remove if there are no parents (root)
    if(parent == null) return;
    // remove this self from parents only when this has no descendents
    // and that there are no records.
    if(children.size() == 0 && points == null) {
      parent.children.remove(this);
      parent.delete();
      return;
    }
    // otherwise the delete sequence ends here.
  }

  public String toString() {
    String rep = "";
    if(character != null) {
      rep += character;
    }

    if(points != null) {
      rep = rep + "[" + points.toString() + "]";
    }

    if(children.size() == 0) {
      return rep;
    }
    // wrap children around a bracket
    rep += '(';
    for(Node child: children) {
      rep += child.toString();
    }
    rep += ')';

    return rep;
  }

  public void setPoints(Integer points) {
    this.points = points;
  }

  public Integer getPoints() {
    return points;
  }
}
