//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    PasswordStorage.java
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

public class PasswordStorage {

  protected PasswordNode root; //the root of this BST that contains passwords
  private int size; //how many passwords are in the BST
  private final Attribute COMPARISON_CRITERIA; //what password information to use to
  // determine
  // order in the tree

  /**
   * Constructor that creates an empty BST and sets the comparison criteria.
   *
   * @param comparisonCriteria, the Attribute that will be used to determine order in the tree
   */
  public PasswordStorage(Attribute comparisonCriteria) {
    this.COMPARISON_CRITERIA = comparisonCriteria;
    size = 0;
    root = null;
  }

  /**
   * Getter for this BST's criteria for determining order in the three
   *
   * @return the Attribute that is being used to make comparisons in the tree
   */
  public Attribute getComparisonCriteria() {
    return COMPARISON_CRITERIA;
  }

  /**
   * Getter for this BST's size.
   *
   * @return the size of this tree
   */
  public int size() {
    return size;
  }

  /**
   * Determines whether or not this tree is empty.
   *
   * @return true if it is empty, false otherwise
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Provides in-order String representation of this BST, with each Password on its own line. The
   * String representation ends with a newline character ('\n')
   *
   * @return this BST as a string
   */
  @Override
  public String toString() {
    return toStringHelper(this.root);
  }

  /**
   * Recursive method the uses an in-order traversal to create the string representation of this
   * tree.
   *
   * @param currentNode, the root of the current tree
   * @return the in-order String representation of the tree rooted at current node
   */
  private String toStringHelper(PasswordNode currentNode) {
    if (currentNode == null) {
      return "";
    }
    //THIS MUST BE IMPLEMENTED RECURSIVELY
    StringBuilder sb = new StringBuilder();
    sb.append(toStringHelper(currentNode.getLeft()));
    sb.append(currentNode.getPassword()).append('\n');
    sb.append(toStringHelper(currentNode.getRight()));
    return sb.toString();
  }

  /**
   * Determines whether or not this tree is actually a valid BST.
   *
   * @return true if it is a BST, false otherwise
   */
  public boolean isValidBST() {
    return isValidBSTHelper(this.root, Password.getMinPassword(), Password.getMaxPassword());
  }

  /**
   * Recurisvely determines if the the tree rooted at the current node is a valid BST. That is,
   * every value to the left of currentNode is "less than" the value in currentNode and every value
   * to the right of it is "greater than" it.
   *
   * @param currentNode, the root node of the current tree
   * @param lowerBound,  the smallest possible password
   * @param upperBound,  the largest possible password
   * @return true if the tree rooted at currentNode is a BST, false otherwise
   */
  private boolean isValidBSTHelper(PasswordNode currentNode, Password lowerBound,
      Password upperBound) {

    // BASE CASE 1
    if (currentNode == null) {
      return true;
    }

    // BASE CASE 2
    if (lowerBound.compareTo(currentNode.getPassword(),
        COMPARISON_CRITERIA) >= 0 || upperBound.compareTo(currentNode.getPassword(),
        COMPARISON_CRITERIA) <= 0) {
      return false;
    }

    PasswordNode left = currentNode.getLeft();
    PasswordNode right = currentNode.getRight();

    // RECURSIVE CASE 1
    if (left != null) {
      if (left.getPassword().compareTo(currentNode.getPassword(), COMPARISON_CRITERIA) >= 0) {
        return false;
      }
      PasswordNode curr = left;
      while (curr.hasRightChild()) {
        curr = curr.getRight();
      }
      if (curr.getPassword().compareTo(currentNode.getPassword(), COMPARISON_CRITERIA) >= 0) {
        return false;
      }
      if (!isValidBSTHelper(left, lowerBound, upperBound)) {
        return false;
      }
    }
    // RECURSIVE CASE 2

    if (right != null) {
      if (right.getPassword().compareTo(currentNode.getPassword(), COMPARISON_CRITERIA) <= 0) {
        return false;
      }
      PasswordNode curr = right;
      while (curr.hasLeftChild()) {
        curr = curr.getLeft();
      }
      if (curr.getPassword().compareTo(currentNode.getPassword(), COMPARISON_CRITERIA) <= 0) {
        return false;
      }
      if (!isValidBSTHelper(right, lowerBound, upperBound)) {
        return false;
      }
    }

    return true;
  }

  /**
   * Returns the password that matches the criteria of the provided key. Ex. if the COMPARISON
   * CRITERIA is OCCURRENCE and the key has an occurrence of 10 it will return the password stored
   * in the tree with occurrence of 10
   *
   * @param key, the password that contains the information for the password we are searching for
   * @return the Password that matches the search criteria, if it does not exist in the tree it this
   * will be null
   */
  public Password lookup(Password key) {
    return lookupHelper(key, root);
  }

  /**
   * Recursive helper method to find the matching password in this BST
   *
   * @param key,         password containing the information we are searching for
   * @param currentNode, the node that is the current root of the tree
   * @return the Password that matches the search criteria, if it does not exist in the tree it this
   * will be null
   */
  private Password lookupHelper(Password key, PasswordNode currentNode) {
    if (currentNode == null) {
      return null;
    }
    int compare = key.compareTo(currentNode.getPassword(), COMPARISON_CRITERIA);
    if (compare == 0) {
      return currentNode.getPassword();
    } else {
      if (compare > 0) {
        return lookupHelper(key, currentNode.getRight());
      } else {
        return lookupHelper(key, currentNode.getLeft());
      }
    }
  }

  /**
   * Returns the best (max) password in this BST
   *
   * @return the best password in this BST
   * @throws NoSuchElementException if the BST is empty
   */
  public Password getBestPassword() {
    if (root == null) {
      throw new NoSuchElementException();
    }
    PasswordNode curr = root;
    while (curr.getRight() != null) {
      curr = curr.getRight();
    }
    return curr.getPassword();
  }

  /**
   * Returns the worst password in this BST
   *
   * @return the worst password in this BST
   * @throws NoSuchElementException if the BST is empty
   */
  public Password getWorstPassword() {
    if (root == null) {
      throw new NoSuchElementException();
    }
    PasswordNode curr = root;
    while (curr.getLeft() != null) {
      curr = curr.getLeft();
    }
    return curr.getPassword();
  }

  /**
   * Adds the Password to this BST.
   *
   * @param toAdd, the password to be added to the tree
   * @throws IllegalArgumentException if the (matching) password object is already in the tree
   */
  public void addPassword(Password toAdd) {
    if (root == null) {
      root = new PasswordNode(toAdd);
    } else {
      if (!addPasswordHelper(toAdd, root)) {
        throw new IllegalArgumentException("Tree already contains the password");
      }
    }
    size++;
  }

  /**
   * Recursive helper that traverses the tree and adds the password where it belongs
   *
   * @param toAdd,       the password to add to the tree
   * @param currentNode, the node that is the current root of the (sub)tree
   * @return true if it was successfully added, false otherwise
   */
  private boolean addPasswordHelper(Password toAdd, PasswordNode currentNode) {
    if (toAdd.compareTo(currentNode.getPassword(), COMPARISON_CRITERIA) < 0) {
      PasswordNode node = currentNode.getLeft();
      if (node == null) {
        currentNode.setLeft(new PasswordNode(toAdd));
        return true;
      } else {
        return addPasswordHelper(toAdd, node);
      }
    } else if (toAdd.compareTo(currentNode.getPassword(), COMPARISON_CRITERIA) > 0) {
      PasswordNode node = currentNode.getRight();
      if (node == null) {
        currentNode.setRight(new PasswordNode(toAdd));
        return true;
      } else {
        return addPasswordHelper(toAdd, node);
      }
    }
    return false;
  }

  /**
   * Removes the matching password from the tree
   *
   * @param toRemove, the password to be removed from the tree
   * @throws NoSuchElementException if the password is not in the tree
   */
  public void removePassword(Password toRemove) {
    root = removePasswordHelper(toRemove, root);
    size--;
  }

  /**
   * Recursive helper method to that removes the password from this BST.
   *
   * @param toRemove    the password to be removed from the tree
   * @param currentNode the root of the tree we are removing from
   * @return the PasswordNode representing the NEW root of this subtree now that toRemove has been
   * removed. This may still be currentNode, or it may have changed!
   */
  private PasswordNode removePasswordHelper(Password toRemove, PasswordNode currentNode) {

    //BASE CASE
    if (currentNode == null) {
      throw new NoSuchElementException();
    }

    int compare = toRemove.compareTo(currentNode.getPassword(), COMPARISON_CRITERIA);

    //RECURSIVE CASE
    if (compare != 0) {
      if (compare < 0) {
        currentNode.setLeft(removePasswordHelper(toRemove, currentNode.getLeft()));
      }
      //RECURSIVE CASE
      if (compare > 0) {
        currentNode.setRight(removePasswordHelper(toRemove, currentNode.getRight()));
      }
    } else {
      //BASE CASE
      if (currentNode.isLeafNode()) {
        return null;
      }
      //BASE CASE
      if (currentNode.numberOfChildren() == 1) {
        return currentNode.hasLeftChild() ? currentNode.getLeft() : currentNode.getRight();
      }
      //RECURSIVE CASE
      PasswordNode curr = currentNode.getLeft();
      PasswordNode parent = currentNode;
      while (curr.hasRightChild()) {
        parent = curr;
        curr = curr.getRight();
      }
      parent.setRight(null);
      curr.setLeft(currentNode.getLeft());
      curr.setRight(currentNode.getRight());
      currentNode = curr;
    }

    return currentNode;
  }
}
