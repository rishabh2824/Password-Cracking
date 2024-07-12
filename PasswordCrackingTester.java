//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    PasswordCrackingTester.java
// Course:   CS 300 Spring 2023
//
// Author:   Rishabh Jain
// Email:    rvjain@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         None
// Online Sources:  None
///////////////////////////////////////////////////////////////////////////////
import java.util.NoSuchElementException;

public class PasswordCrackingTester {

  /**
   * Validates the constructor and accessor methods of PasswordStorage, specifically the
   * getComparisonCriteria(), size(), and isEmpty() methods, as well as accessing the protected data
   * field root.
   *
   * Be sure to try making multiple PasswordStorage objects with different Attributes.
   *
   * @return true if the basic accessor methods work as expected, false otherwise
   */
  public static boolean testBasicPasswordStorageMethods() {
    PasswordStorage occurrenceStorage = new PasswordStorage(Attribute.OCCURRENCE);
    PasswordStorage strengthStorage = new PasswordStorage(Attribute.STRENGTH_RATING);
    PasswordStorage hashStorage = new PasswordStorage(Attribute.HASHED_PASSWORD);

    if (occurrenceStorage.getComparisonCriteria() != Attribute.OCCURRENCE ||
        strengthStorage.getComparisonCriteria() != Attribute.STRENGTH_RATING ||
        hashStorage.getComparisonCriteria() != hashStorage.getComparisonCriteria()) {
      return false;
    }

    if (!occurrenceStorage.isEmpty()) {
      return false;
    }

    if (occurrenceStorage.root != null) {
      return false;
    }

    if (occurrenceStorage.size() != 0) {
      return false;
    }

    return true;
  }

  /**
   * Validates the Password class compareTo() method. Create at least two DIFFERENT Password objects
   * and compare them on each of the Attribute values. See the writeup for details on how the
   * various comparisons are expected to work.
   *
   * @return true if Password's compareTo() works as expected, false otherwise
   */
  public static boolean testPasswordCompareTo() {
    // SHA: aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d
    Password p1 = new Password("hello", 10);
    // SHA: c22b5f9178342609428d6f51b2c5af4c0bde6a42
    Password p2 = new Password("hi", 3);

    if (p1.compareTo(p2, Attribute.OCCURRENCE) <= 0) {
      return false;
    }

    if (p1.compareTo(p2, Attribute.STRENGTH_RATING) <= 0) {
      return false;
    }

    if (p1.compareTo(p2, Attribute.HASHED_PASSWORD) >= 0) {
      return false;
    }
    return true; // TODO
  }

  /**
   * Validates the incomplete methods in PasswordNode, specifically isLeafNode(),
   * numberOfChildren(), hasLeftChild() and hasRightChild(). Be sure to test all possible
   * configurations of a node in a binary tree!
   *
   * @return true if the status methods of PasswordNode work as expected, false otherwise
   */
  public static boolean testNodeStatusMethods() {
    PasswordNode root = new PasswordNode(new Password("h1", 1));
    PasswordNode l = new PasswordNode(new Password("h2", 2));
    PasswordNode r = new PasswordNode(new Password("h3", 3));
    PasswordNode ll = new PasswordNode(new Password("h4", 4));
    PasswordNode lr = new PasswordNode(new Password("h5", 5));
    PasswordNode rl = new PasswordNode(new Password("h6", 6));

    root.setLeft(l);
    root.setRight(r);

    l.setLeft(ll);
    l.setRight(lr);

    r.setLeft(rl);

    if (!root.hasLeftChild() || !root.hasRightChild() || root.isLeafNode() ||
        root.numberOfChildren() != 2) {
      return false;
    }
    if (!l.hasLeftChild() || !l.hasRightChild() || l.isLeafNode() || l.numberOfChildren() != 2) {
      return false;
    }
    if (!r.hasLeftChild() || r.hasRightChild() || r.isLeafNode() || r.numberOfChildren() != 1) {
      return false;
    }
    if (lr.hasLeftChild() || lr.hasRightChild() || !lr.isLeafNode() || lr.numberOfChildren() != 0) {
      return false;
    }
    if (ll.hasLeftChild() || ll.hasRightChild() || !ll.isLeafNode() || ll.numberOfChildren() != 0) {
      return false;
    }

    return true;
  }

  /**
   * This method check the toString() method
   * @return true if and only if all tests pass, and false otherwise
   */
  public static boolean testToString() {
    try {
      PasswordStorage bst = new PasswordStorage(Attribute.OCCURRENCE);

      // empty is empty string
      String expected = "";
      String actual = bst.toString();
      if (!actual.equals(expected)) {
        System.out.println("toString() does not return the proper value on an empty tree!");
        return false;
      }

      // size one only returns 1 thing
      Password p = new Password("1234567890", 15000);
      PasswordNode rootNode = new PasswordNode(p);

      bst.root = rootNode; // here I am manually building the tree by editing the root node
      // directly to be the node of my choosing

      expected = p.toString() + "\n";
      actual = bst.toString();
      if (!actual.equals(expected)) {
        return false;
      }


      // big tree returns in-order traversal
      Password p2 = new Password("test", 500);
      Password p3 = new Password("iloveyou", 765);
      Password p4 = new Password("qwerty", 250);
      Password p5 = new Password("admin", 1002);
      Password p6 = new Password("password", 2232);
      Password p7 = new Password("abc123", 2090);

      PasswordNode p4Node = new PasswordNode(p4);
      PasswordNode p3Node = new PasswordNode(p3);
      PasswordNode p7Node = new PasswordNode(p7);
      PasswordNode p6Node = new PasswordNode(p6, p7Node, null);
      PasswordNode p5Node = new PasswordNode(p5, null, p6Node);
      PasswordNode p2Node = new PasswordNode(p2, p4Node, p3Node);
      rootNode = new PasswordNode(p, p2Node, p5Node);
      bst.root = rootNode;

      expected =
          p4.toString() + "\n" + p2.toString() + "\n" + p3.toString() + "\n" + p.toString() + "\n" +
              p5.toString() + "\n" + p7.toString() + "\n" + p6.toString() + "\n";
      actual = bst.toString();

      if (!actual.equals(expected)) {
        return false;
      }

    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  /**
   * The method tests the isValidBST method.
   * @return true if and only if all methods pass, and false otherwise
   */
  public static boolean testIsValidBST() {
    try {
      PasswordStorage bst = new PasswordStorage(Attribute.OCCURRENCE);

      // empty tree is a valid bst
      /*
       * String expected = ""; String actual = bst.toString();
       */
      if (!bst.isValidBST()) {
        System.out.println("isValidBST() says that an empty tree is not a valid BST!");
        return false;
      }

      // size one is a bst
      Password p = new Password("1234567890", 1000);
      PasswordNode rootNode = new PasswordNode(p);

      bst.root = rootNode; // here I am manually building the tree by editing the root node
      // directly to be the node of my choosing

      if (!bst.isValidBST()) {
        System.out.println("isValidBST() says that a tree of size 1 is not a valid BST!");
        return false;
      }

      Password p2 = new Password("test", 500);
      Password p3 = new Password("iloveyou", 765);
      Password p4 = new Password("qwerty", 250);
      Password p5 = new Password("admin", 1002);
      Password p6 = new Password("password", 2232);
      Password p7 = new Password("abc123", 2090);

      // works on indentifying small obviously invalid tree
      PasswordNode p7Node = new PasswordNode(p7);
      PasswordNode p3Node = new PasswordNode(p3);
      rootNode = new PasswordNode(p, p7Node, p3Node);
      bst.root = rootNode;
      if (bst.isValidBST()) {
        return false;
      }

      // tree with only one subtree being valid, other subtree has a violation a couple layers deep


      PasswordNode p4Node = new PasswordNode(p4);
      p7Node = new PasswordNode(p7);
      p3Node = new PasswordNode(p3);
      PasswordNode p6Node = new PasswordNode(p6, null, p7Node);
      PasswordNode p5Node = new PasswordNode(p5, null, p6Node);
      PasswordNode p2Node = new PasswordNode(p2, p4Node, p3Node);
      rootNode = new PasswordNode(p, p2Node, p5Node);
      bst.root = rootNode;

      if (bst.isValidBST()) {
        System.out.println(
            "isValidBST() says that a tree with only one valid subtree is a valid bst");
        return false;
      }


      // works on valid large tree
      p4Node = new PasswordNode(p4);
      p3Node = new PasswordNode(p3);
      p7Node = new PasswordNode(p7);
      p6Node = new PasswordNode(p6, p7Node, null);
      p5Node = new PasswordNode(p5, null, p6Node);
      p2Node = new PasswordNode(p2, p4Node, p3Node);
      rootNode = new PasswordNode(p, p2Node, p5Node);
      bst.root = rootNode;

      if (!bst.isValidBST()) {
        return false;
      }

      PasswordNode one = new PasswordNode(p4);
      PasswordNode three = new PasswordNode(p3, one, null);
      PasswordNode two = new PasswordNode(p2, null, three);
      bst.root = two;

      if (bst.isValidBST()) {
        System.out.println("bad bst is valid");
        return false;
      }


    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  /**
   * This method tests the lookup method.
   * @return true if and only if all tests pass, and false otherwise
   */
  public static boolean testLookup() {

    PasswordStorage store = new PasswordStorage(Attribute.OCCURRENCE);
    // Calls lookup on empty tree
    if (store.lookup(new Password("random", 10)) != null) {
      return false;
    }

    // Adds 7 passwords
    Password p1 = new Password("h1", 1);
    Password p2 = new Password("h2", 2);
    Password p3 = new Password("h3", 3);
    Password p4 = new Password("h4", 4);
    Password p5 = new Password("h5", 5);
    Password p6 = new Password("h6", 6);
    Password p7 = new Password("h7", 7);

    store.addPassword(p4);
    store.addPassword(p2);
    store.addPassword(p6);
    store.addPassword(p1);
    store.addPassword(p3);
    store.addPassword(p5);
    store.addPassword(p7);


    if (store.lookup(new Password("random", 1)) != p1) {
      return false;
    }
    if (store.lookup(new Password("random", 2)) != p2) {
      return false;
    }
    if (store.lookup(new Password("random", 3)) != p3) {
      return false;
    }
    if (store.lookup(new Password("random", 4)) != p4) {
      return false;
    }
    if (store.lookup(new Password("random", 5)) != p5) {
      return false;
    }
    if (store.lookup(new Password("random", 6)) != p6) {
      return false;
    }
    if (store.lookup(new Password("random", 7)) != p7) {
      return false;
    }
    if (store.lookup(new Password("random", 8)) != null) {
      return false;
    }
    return true;
  }

  /**
   * This method tests the addPassword method.
   * @return true if and only if all tests pass, and false otherwise.
   */
  public static boolean testAddPassword() {
    try {
      PasswordStorage storage = new PasswordStorage(Attribute.OCCURRENCE);
      // Creates 7 passwords to use
      Password password1 = new Password("hello1", 1);
      Password password2 = new Password("hello2", 2);
      Password password3 = new Password("hello3", 3);
      Password password4 = new Password("hello4", 4);
      Password password5 = new Password("hello5", 5);
      Password password6 = new Password("hello6", 6);
      Password password7 = new Password("hello7", 7);

      // Adds password initially to check root is as expected
      storage.addPassword(password4);

      if (storage.root.getPassword() != password4 || storage.size() != 1 || storage.isEmpty()) {
        return false;
      }
      // Adds the other passwords and checks the structure and size of the tree
      int count = 1;
      for (Password pw : new Password[] {password1, password2, password3, password6, password5,
          password7}) {
        storage.addPassword(pw);
        if (storage.size() != ++count) {
          return false;
        }
      }

      if (storage.root.getLeft().getPassword() != password1) {
        return false;
      }
      if (storage.root.getRight().getPassword() != password6) {
        return false;
      }
      if (storage.root.getLeft().getLeft() != null) {
        return false;
      }
      if (storage.root.getLeft().getRight().getPassword() != password2) {
        return false;
      }
      if (storage.root.getLeft().getRight().getRight().getPassword() != password3) {
        return false;
      }
      if (storage.root.getLeft().getRight().getLeft() != null) {
        return false;
      }
      if (storage.root.getRight().getLeft().getPassword() != password5) {
        return false;
      }
      if (storage.root.getRight().getRight().getPassword() != password7) {
        return false;
      }

      // Checks if the expected leaf nodes are leaf nodes
      if (!storage.root.getLeft().getRight().getRight().isLeafNode()
          || !storage.root.getRight().getLeft().isLeafNode()
          || !storage.root.getRight().getRight().isLeafNode()) {
        return false;
      }

      // Adds a password whose compareTo is 0, and it should return the correct exception
      try {
        storage.addPassword(new Password("message", 7));
        return false;
      } catch (IllegalArgumentException exception) {
        // pass
      }
    } catch (Exception e) {
      return false;
    }



    return true;
  }

  /**
   * this method tests the removePassword method
   * @return true if and only if all tests pass, and false otherwise.
   */
  public static boolean testRemovePassword() {
    try {
      PasswordStorage store = new PasswordStorage(Attribute.OCCURRENCE);
      // Calls remove on empty tree
      try {
        store.removePassword(new Password("message", 9));
        return false;
      } catch (NoSuchElementException e) {
        // pass
      }

      // Adds 7 passwords
      Password hack1 = new Password("hello1", 1);
      Password hack2 = new Password("hello2", 2);
      Password hack3 = new Password("hello3", 3);
      Password hack4 = new Password("hello4", 4);
      Password hack5 = new Password("hello5", 5);
      Password hack6 = new Password("hello6", 6);
      Password hack7 = new Password("hello7", 7);

      store.addPassword(hack4);
      store.addPassword(hack2);
      store.addPassword(hack6);
      store.addPassword(hack1);
      store.addPassword(hack3);
      store.addPassword(hack5);
      store.addPassword(hack7);

      // removes a leaf node and checks the tree structure and size
      store.removePassword(new Password("message", 7));
      if (store.size() != 6) {
        return false;
      }
      if (store.root.getRight().hasRightChild()) {
        return false;
      }

      // removes a node with 1 child
      store.removePassword(new Password("message", 6));
      if (store.size() != 5) {
        return false;
      }
      if (!store.root.getRight().isLeafNode()
          || !store.root.getRight().getPassword().equals(hack5)) {
        return false;
      }

      // removes the root
      store.removePassword(new Password("message", 4));
      if (store.size() != 4) {
        return false;
      }
      if (!store.root.getPassword().equals(hack3)) {
        return false;
      }
      if (!store.root.getLeft().getPassword().equals(hack2)
          || !store.root.getLeft().getLeft().getPassword().equals(hack1)
          || !store.root.getRight().getPassword().equals(hack5)) {
        return false;
      }
    } catch (Exception e) {
      return false;
    }

    return true;
  }

  /**
   * This is the main method that runs all the tests.
   * @param args
   */
  public static void main(String[] args) {

    boolean compareToPassed = testPasswordCompareTo();
    boolean nodeStatusPassed = testNodeStatusMethods();
    boolean basicMethodsPassed = testBasicPasswordStorageMethods();
    boolean toStringPassed = testToString();
    boolean isValidBSTPassed = testIsValidBST();
    boolean lookupPassed = testLookup();
    boolean addPasswordPassed = testAddPassword();
    boolean removePasswordPassed = testRemovePassword();

    System.out.println("Password compareTo: " + (compareToPassed ? "PASS" : "FAIL"));
    System.out.println("PasswordNode Status Methods: " + (nodeStatusPassed ? "PASS" : "FAIL"));
    System.out.println("PasswordStorage Basic Methods: " + (basicMethodsPassed ? "PASS" : "FAIL"));
    System.out.println("PasswordStorage toString: " + (toStringPassed ? "PASS" : "FAIL"));
    System.out.println("PasswordStorage isValidBST: " + (isValidBSTPassed ? "PASS" : "FAIL"));
    System.out.println("PasswordStorage lookup: " + (lookupPassed ? "PASS" : "FAIL"));
    System.out.println("PasswordStorage addPassword: " + (addPasswordPassed ? "PASS" : "FAIL"));
    System.out.println(
        "PasswordStorage removePassword: " + (removePasswordPassed ? "PASS" : "FAIL"));
  }
}
