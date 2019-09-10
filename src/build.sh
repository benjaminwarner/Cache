#!/bin/bash

javac CacheSimulator.java -d ../bin
for test_file in Test_*
do
	javac $test_file -d ../tst
done
