# Cryptanalyzer
implementation of a program for encrypting, decrypting and analyzing text using various methods such as the Caesar cipher and the "brute force" method for decryption

Cryptanalyzer: This is the main class of the program, which contains the main method and coordinates the work of other classes. In this class, the user selects the program mode (encryption, decryption or brute force), enters the path to the text file, and then performs the corresponding operations using other classes.

Map: This is a helper class that creates and returns a list of characters used in encryption and decryption. This class provides a static map method that returns a list of symbols.

Encryption: This is a class that implements the Cracker interface and is responsible for encrypting text using a Caesar cipher. It has an encrypt method that takes input text, encrypts it, and then writes the result to a new file.

Decipher: This is a class that also implements the Cracker interface, which is responsible for decrypting text using the "brute force" method. It has a decoding method that takes input text, performs all possible decodings with different offsets, and writes the results to a new file.

BruteForce: This is a class that also implements the Cracker interface, which provides a bruteForceDecrypt method to perform text decryption using the "brute force" method. It iterates over all possible shifts and decrypts with each shift, writing the results to a new file.
