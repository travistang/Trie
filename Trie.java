
public class Trie {
  Node root = new Node();

  public Trie() { }

  public boolean add(String key, Integer points) {
    Node cursor = root;
    for(int i = 0; i < key.length(); i++) {

      char ch = key.charAt(i);
      Node child = cursor.getChild(ch);
      // next node found, move to there.
      if(child != null) {
        cursor = child;
      } else {
        // there are no such node underneath, create one.
        Node newChild = new Node(ch, cursor);
        cursor.setChild(ch, newChild);
        // move the cursor to the new child
        cursor = newChild;
      }
    }
    // determine if the key is there.
    if(cursor.getPoints() != null) {
      return false;
    } else {
      // mark the node under the cursor with points
      cursor.setPoints(points);
      return true;
    }
  }

  public boolean delete(String key) {
    Node node = root.find(key);
    if(node == null || node.getPoints() == null) {
      return false;
    }
    // remove such record
    node.setPoints(null);
    // and clear up orphans
    node.delete();
    return true;
  }
  

  public boolean change(String key, Integer points) {
    Node node = root.find(key);

    if(node == null  // key is not found.
      || node.getPoints() == null // there is such node, but no record.
    ) {
      return false;
    }

    node.setPoints(points);
    return true;
   }

  public Integer points(String key) {
    Node node = root.find(key);
    if(node == null) {
      return null;
    } else {
      return node.getPoints();
    }
  }

  public String toString() {
    return "+" + root.toString();
  }
}
