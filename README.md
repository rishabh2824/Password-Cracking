Password-Cracking is a process that can be used to recover passwords. It could be used to recover a lost password, but its use in most cases is for nefarious actors to gain unauthorized access to a system or account.
The simplest way to do this would be to brute-force all possible combinations of characters.
However, this gets out of control quickly, as a password that is six characters long and uses uppercase
letters, lowercase letters, and numbers has over 56 billion possibilities. 
Therefore, We are going to take a more effective approach knowing that:
  (1) lists of passwords are leaked from data breaches
  (2) Some passwords are stronger than others and 
  (3) Some passwords are used extremely frequently.
Using this information we are going create and store passwords into a binary search tree (BST)
and choose one of three different criteria to order the tree by based on how we want to attack
the system:
  1. the number of times the password has been used the strength of the password
  2. the SHA-1 hash of the password (a way to obscure the actual password)
