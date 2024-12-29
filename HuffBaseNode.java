/**
 * Creates a base node for a huffman tree.
 *
 * @author CS intructors.
 * @version 11/21/2024
 */
public abstract class HuffBaseNode implements Comparable<HuffBaseNode> {

  private int weight;

  public HuffBaseNode(int weight) {
    this.weight = weight;
  }

  public int getWeight() {
    return weight;
  }

  @Override
  public int compareTo(HuffBaseNode other) {
    if (getWeight() < other.getWeight()) {
      return -1;
    } else if (getWeight() == other.getWeight()) {
      // NEEDS TO BE FIXED TO GET DETERMINISTIC TIE-BREAKING!
      return 0;
    } else {
      return 1;
    }

  }

}