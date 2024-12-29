
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * This class utilized the Huffman coding and decoding process and 
 * trees in order to unzip and zip files.
 * 
 *
 * @author Michael Aho
 * @version 11/19/21
 *
 */
public class MadZip {

  /**
   * This method recieves a file to be compress then compresses the file and saves it. 
   * (encodes characters into a bit sequence)
   * 
   */
  public static void zip(File currentFile, File selected) throws IOException {
    HuffTree ht = new HuffTree(currentFile);
    BitSequence encodedData = ht.encode(currentFile);
    HuffmanSave saved = new HuffmanSave(encodedData, ht.getFrequencies());
    FileOutputStream fileOutput = new FileOutputStream(selected);
    ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
    objectOutput.writeObject(saved);
    objectOutput.close();
  }
  
  /**
   * This file uncompresses a certain file and saves the unzipped file into a new file. 
   * (Decodes a bit sequence)
   * 
   */
  public static void unzip(File currentFile, File selected) 
      throws IOException, ClassNotFoundException {
    FileInputStream fileInput = new FileInputStream(currentFile);
    ObjectInputStream objectInput = new ObjectInputStream(fileInput);
    HuffmanSave saved = (HuffmanSave) objectInput.readObject();
    HuffTree ht = new HuffTree(saved.getFrequencies());
    BitSequence encodedData = saved.getEncoding();
    ht.decode(encodedData, selected);
    objectInput.close();
  
  
  }
}