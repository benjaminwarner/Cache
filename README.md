****************
* Project 1: Cache
* CS321 
* 2019-09-12
* Benjamin Warner
**************** 

# OVERVIEW

This project implemented the ICache interface defined here: http://cs.boisestate.edu/~cs321/projects/cache/1-Cache.html,
using a double linked list. A command line simulator is available
from the CacheSimulator.java source file.

## INCLUDED FILES

- ICache.java (the ADT for a cache)
- DLLNode.java (simple class that acts as a node in a DLL)
- Cache.java (the actual implementation of the ADT)
- CacheSimulator.java (the command line simulator)
- README.md (this file)


## COMPILING AND RUNNING

Two options are available for compiling the source code.
First, you can run the `build.sh` shell script under the
`src` directory. This will compile CacheSimulator using 
the following command: `javac CacheSimulator.java -d ../bin`.
Then, it will compile all the test cases using the same command
for each test case file, replacing the directory `../bin` with
`../tst`.

Alternatively, you may compile the CacheSimulator using 
this command: `javac CacheSimulator.java` (**NOTE**: this will place
the binaries with the source).

Sample output:

```
$ java CacheSimulator 1 1000 path/to/a/text_file.txt
L1 cache with 25 entries created
. . .
L1 hit count: 55
L1 hit rate:  45.000%

Total number of accesses: 123
Total number of hits: 55
Overall hit rate: 45.000%
```

To run the compiled binary, use this command: `java CacheSimulator [OPTIONS...]`.

To run the tests provided, you may use the `run_tests.sh` shell
script under the `tst` directory (assuming you compiled the
source using `build.sh`). 

Alternatively, you may run the tests using this command:
`java org.testng.TestNG cache.xml` after compiling all test
case .java files and moving the cache.xml to the appropriate
location.


## PROGRAM DESIGN AND IMPORTANT CONCEPTS

The main concept to understand here is how a cache works. 
A cache stores data to better serve future requests that may need
that data. This cache uses the Most Recently Used algorithm to
store and manage data (basically, when data gets accessed/added,
it gets moved to the front of the list so that later lookups
are quicker).

Although this implementation uses a double linked list, an array
or a single linked list would work just as well. But, I suspect
that a double linked list provides some advantages over the other two
possibilities. Namely, shifting elements around after accessing data
is easier (in an array, after finding the data, all the elements would
need to be shifted by 1 to preserve the order after moving it to the
front).

The Cache class is the actual implementation of the ICache interface
(with some additional methods added on). It uses the DLLNode to manage
the elements that get added to the cache through its exposed interface.
Important methods to understand are the `add` and `get` methods, as
those will be the most used.

The CacheSimulator reads in a text file, and stores all the words of that file
in an ArrayList. It then creates 1 or 2 caches depending on the usage.
The reason why two separate caches get created when the user requests
L2 cache simulation, is because an L2 cache system *is* two separate 
caches on the hardware.

## TESTING

The test case files under the `src` directory were provided by 
instructor Matthew Thomas (mhthomas@boisestate.edu). These
ensure that the Cache class works as expected/defined by the
ICache interface. TestNG is the testing framework used to
run these test cases.

As for testing the CacheSimulator, the binary was run manually
using different command line arguments. Two text files were used
(and are available under the `tstdata` directory): small.txt and Encyclopedia.txt.

The small.txt file contains 20 words, while the Encyclopedia.txt file contains
*much much more*. The command line parsing part of the simulator is surrounded
in a blanket try catch clause, and prints the error message along with a usage string,
so if a user does manage to break the program spectacularly in that way,
the binary simply won't run.

**IMPORTANT NOTE**: the simulator was *only* tested using simple text files.
Running it on a file that contains binary or other weird data *is not guaranteed*
to work.


## DISCUSSION

Since I don't normally program in Java (mostly because I hate the language),
I spent some time reminding myself how to do simply things (like looking up
how a Scanner works, or how an ArrayList works). Spent a decent amount of time
looking at the API docs for Java 8 (which can be found here: https://docs.oracle.com/javase/8/docs/api/).

Had some issue with parsing words from the text file, as I was using a Scanner
and told it to use the delimitter " ". Well, when there's whitespace surrounding
two words, those get counted as one. The fix was simple, don't specify a
delimiter.

This project was relatively straight forward for me, and nothing was too difficult.
Actually quite enjoyed working on this project and the coding feels fresh to me
(I haven't spent a ton of time working on data structures, as in industry, you rarely
need to implement them yourself).

I am curious to see what the efficiency is for this implementation given random data.
