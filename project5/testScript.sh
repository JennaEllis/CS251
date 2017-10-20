# diff the output with the golden file
# turn off dumping of stdout/stderr to student reports
#vocReporterSuppressStdout
function runCmd()
{
exitOnFailure=$1
shift
status=$1
shift
echo -n "Executing command: $* "
eval $*
status=$?
if [ "$status" -ne "0" ]; then
echo "(Failed to compile)"
if [ "$exitOnFailure" -ne "0" ]; then
exit $status
fi
else
echo "(Passed)"
fi
}
 
file1="BurrowsWheeler.java"
file2="BurrowsWheeler.cpp"
##################################
# CHECK LANGUAGE USED TO CODE #
##################################
#IF JAVA USED
if [ -f "$file1" ]
then
##################################
# JAVA #
##################################
runCmd 1 status 'javac -cp .:stdlib.jar:algs4.jar BurrowsWheeler.java'
runCmd 1 status 'javac -cp .:stdlib.jar:algs4.jar MoveToFront.java'
runCmd 1 status 'javac -cp .:stdlib.jar:algs4.jar HexDump.java'
rundCmd 1 status 'javac -cp .:stdlib.jar:algs4.jar Huffman.java'

runCmd 0 status "java -cp .:stdlib.jar:algs4.jar BurrowsWheeler - < abra.txt > abraTest.txt.bwt"
runCmd 0 status "java -cp .:stdlib.jar:algs4.jar MoveToFront - < abraTest.txt.bwt > abraTest.txt.bwt.mtf" 
runCmd 0 status "java -cp .:stdlib.jar:algs4.jar Huffman - < abraTest.txt.bwt.mtf > abraTest.txt.bwt.mtf.huf"
runCmd 0 status "java -cp .:stdlib.jar:algs4.jar Huffman + < abraTest.txt.bwt.mtf.huf > abraTestRe.txt.bwt.mtf"
runCmd 0 status "java -cp .:stdlib.jar:algs4.jar MoveToFront + < abraTestRe.txt.bwt.mtf > abraTestRe.txt.bwt" 
runCmd 0 status "java -cp .:stdlib.jar:algs4.jar BurrowsWheeler + < abraTestRe.txt.bwt > abraTestRe.txt"
md5sum abraTest.txt.bwt.mtf.huf > md5abraEnc
runCmd 0 status "diff -B -b $ASNLIB/md5abraEnc md5abraEnc >& /dev/null" 
if [ "$status" -eq "0" ]; then
status="Passed"
else
status="Failed"
fi
echo status
md5sum abraTestRe.txt > md5abraDec
runCmd 0 status "diff -B -b $ASNLIB/md5abraDec md5abraDec >& /dev/null" 
if [ "$status" -eq "0" ]; then
status="Passed"
else
status="Failed"
fi
echo status
 
#IF C++ USED
elif [ -f "$file2" ]
then
##################################
# CPP #
##################################


runCmd 1 status 'g++ -std=c++11 BurrowsWheeler.cpp -o BurrowsWheeler'
runCmd 1 status 'g++ -std=c++11 MoveToFront.cpp -o MoveToFront'
runCmd 1 status 'g++ -std=c++11 HexDump.cpp -o HexDump'
rundCmd 1 status 'g++ -std=c++11 Huffman.cpp -o Huffman'

runCmd 0 status "./BurrowsWheeler - < $ASNLIB/abra.txt > abraTest.txt.bwt"
runCmd 0 status "./MoveToFront - < abraTest.txt.bwt > abraTest.txt.bwt.mtf" 
runCmd 0 status "./Huffman - < abraTest.txt.bwt.mtf > abraTest.txt.bwt.mtf.huf"
runCmd 0 status "./Huffman + < abraTest.txt.bwt.mtf.huf > abraTestRe.txt.bwt.mtf"
runCmd 0 status "./MoveToFront + < abraTestRe.txt.bwt.mtf > abraTestRe.txt.bwt" 
runCmd 0 status "./BurrowsWheeler + < abraTestRe.txt.bwt > abraTestRe.txt"
md5sum abraTest.txt.bwt.mtf.huf > md5abraEnc
runCmd 0 status "diff -B -b $ASNLIB/md5abraEnc md5abraEnc >& /dev/null" 
if [ "$status" -eq "0" ]; then
status="Passed"
else
status="Failed"
fi
echo status
md5sum abraTestRe.txt > md5abraDec
runCmd 0 status "diff -B -b $ASNLIB/md5abraDec md5abraDec >& /dev/null" 
if [ "$status" -eq "0" ]; then
status="Passed"
else
status="Failed"
fi
echo status

#IF JAVA AND CPP FILES NOT PRESENT
else 
echo "not found."
fi
exit 0
