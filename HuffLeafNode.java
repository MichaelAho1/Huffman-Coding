/**
 * Creates a leaf node for a huffman tree.
 *
 * @author CS intructors.
 * @version 11/21/2024
 */
public class HuffLeafNode extends HuffBaseNode {

  private byte symbol;

  public HuffLeafNode(byte symbol, int weight) {
    super(weight);
    this.symbol = symbol;
  }

  public byte getSymbol() {
    return symbol;
  }

}