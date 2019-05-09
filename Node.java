// import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Set;


public class Node {
  private Character character;
  private Node parent;
  private Integer points = null;

  private HashMap<Character, Node> children = new HashMap<Character, Node>();

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
      children.put(ch, child);
    }
  }
  /**
    Return the child that holds the given character. Return null if it doesn't exist
  */
  public Node getChild(char ch) {
    return children.get(ch);
  }

  /**
    Retrieve all node objects from the children hashmap.
    Additional provide sorting functionality
  */
  private ArrayList<Node> getChildNodes() {
    ArrayList<Node> childrenNodes = new ArrayList<Node>();

    // sort the keys in ascending order.
    ArrayList<Character> keys = new ArrayList<Character>(children.keySet());
    Collections.sort(keys);

    // loop through the keys to get values.
    for(Character ch : keys) {
      childrenNodes.add(children.get(ch));
    }

    return childrenNodes;
  }

  private Node findInChildren(String key) {
    for(Node child : getChildNodes()) {
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
      // remove itself from parent using the character as key.
      parent.children.remove(character);
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
    for(Node child: getChildNodes()) {
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
