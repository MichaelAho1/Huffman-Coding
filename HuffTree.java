
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Creates a Huffman tree and utilzes that tree to create and decode certain bitsequences.
 *
 * @author Michael Aho
 * @version 11/21/2024
 */
public class HuffTree {

  private HuffBaseNode root;
  private HashMap<Byte, Integer> frequencies;
  private HashMap<Byte, BitSequence> codes; // Maps from bytes to their Huffman codes.
  
  /**
   * Constructs a new tree by reading a file and creating frequencies depending on that file. 
   * 
   */
  public HuffTree(File inFile) throws IOException {
    codes = new HashMap<Byte, BitSequence>();
    frequencies = new HashMap<Byte, Integer>();
    FileInputStream input = new FileInputStream(inFile);
    int readByte;
    while ((readByte = input.read()) != -1) {
      byte bite = (byte) readByte;
      frequencies.put(bite, frequencies.getOrDefault(bite, 0) + 1);
    }
    input.close();
    System.out.println(frequencies);
    root = buildTree(frequencies);
    System.out.println(root);
    helperCode(root, new BitSequence());
  }
  
  /**
   * Constructs a new tree based on the given frequencies. 
   */
  public HuffTree(HashMap<Byte, Integer> frequencies) {
    codes = new HashMap<Byte, BitSequence>();
    this.frequencies = frequencies;
    root = buildTree(frequencies);
    helperCode(root, new BitSequence());
  }
  
  
  private void helperCode(HuffBaseNode baseNode, BitSequence code) {
    LinkedList<HuffBaseNode> nodes = new LinkedList<>();
    LinkedList<BitSequence> codesList = new LinkedList<>();
    nodes.add(baseNode);
    codesList.add(code);
    while (true) {
      if (nodes.isEmpty()) {
        return;
      }
      HuffBaseNode currentNode = nodes.poll();
      BitSequence currentCode = codesList.poll();
      if (currentNode instanceof HuffLeafNode) {
        codes.put(((HuffLeafNode) currentNode).getSymbol(), new BitSequence(currentCode));
      } else if (currentNode instanceof HuffInternalNode) {
        HuffInternalNode internalNode = (HuffInternalNode) currentNode;
        BitSequence leftCode = new BitSequence(currentCode);
        leftCode.appendBit(0);
        nodes.add(internalNode.getLeft());
        codesList.add(leftCode);
        BitSequence rightCode = new BitSequence(currentCode);
        rightCode.appendBit(1);
        nodes.add(internalNode.getRight());
        codesList.add(rightCode);
      }
    }
  }

  
  public HashMap<Byte, Integer> getFrequencies() {
    return new HashMap<>(frequencies);
  }
  
  /**
   * Encodes the given file into 1's and 0's (a bit sequence).
   */
  public BitSequence encode(File inFile) throws IOException {
    BitSequence encode = new BitSequence();
    FileInputStream input = new FileInputStream(inFile);
    int readByte = input.read();
    while (readByte != -1) {
      encode.appendBits(codes.get((byte) readByte));
      readByte = input.read();
    }
    input.close();
    return encode;
  }
  
  /**
   * Decodes a the given bit sequence and puts the new decoded characters onto the given out file.
   */
  public void decode(BitSequence encoding, File outFile) throws IOException {
    Iterator<Integer> it = encoding.iterator();
    FileOutputStream outputStream = new FileOutputStream(outFile);
    BitSequence seq = new BitSequence(); 
    while (it.hasNext()) {
      seq.appendBit(it.next());
      if (codes.containsValue(seq)) {
        Set<Byte> keySet = codes.keySet();
        Iterator<Byte> keyIterator = keySet.iterator();
        while (keyIterator.hasNext()) {
          Byte byt = keyIterator.next();
          if (seq.equals(codes.get(byt))) {
            outputStream.write(byt);
          }
        }
        seq = new BitSequence();
      }
    }
    outputStream.close();
  }


  private HuffBaseNode buildTree(HashMap<Byte, Integer> frequencies) {
    HuffBaseNode tmp1 = null;
    HuffBaseNode tmp2 = null;
    HuffBaseNode tmp3 = null;
    

    PriorityQueue<HuffBaseNode> pq = new PriorityQueue<>();
    
    for (Byte symbol : frequencies.keySet()) {
      if (frequencies.keySet().size() == 1) {
        return new HuffLeafNode(symbol, frequencies.get(symbol));
      }
      if (frequencies.keySet().size() == 0) {
        return null;
      }
      pq.add(new HuffLeafNode(symbol, frequencies.get(symbol)));
    }
    while (pq.size() > 1) { // While two items left
      tmp1 = pq.poll();
      tmp2 = pq.poll();
      tmp3 = new HuffInternalNode(tmp1, tmp2, tmp1.getWeight() + tmp2.getWeight());
      pq.add(tmp3); // Return new tree to heap
    }
    return tmp3; // Return the tree
  }
}