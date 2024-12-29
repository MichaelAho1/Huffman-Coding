/**
 * Creates an internal node for a huffman tree.
 *
 * @author CS intructors.
 * @version 11/21/2024
 */
public class HuffInternalNode extends HuffBaseNode {

  private HuffBaseNode left;
  private HuffBaseNode right;
  
  /**
   * Creates the internal node with its weight and the children.
   */
  public HuffInternalNode(HuffBaseNode left, HuffBaseNode right, int weight) {
    super(weight);
    this.left = left;
    this.right = right;
  }

  public HuffBaseNode getLeft() {
    return left;
  }

  public HuffBaseNode getRight() {
    return right;
  }

}