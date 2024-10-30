import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WildCard 
{
    // character constants
    static final char opening = '('; // opening delimiters
    static final char closing = ')'; // respective closing delimiters
    static final char wildcard = '*';
    static final char endchar = '$';

    // stack and wildcard counter used for processing
    static ArrayStack openStack;
    static int wildcardCount;

    // dev options
    static boolean silenceResizing = true;
    static boolean silenceCorrectTests = true;
    static final String filename = "src\\test_answers.txt";

    // testing trackers
    static int nTests = 0;
    static int nCorrect = 0;

    public static void main(String[] args)
    {
        // open file for reading
        Scanner fileIn;
        try 
        {
            fileIn = new Scanner(new FileInputStream(filename));
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File with name " + filename + " was not found or could not be opened.");
            return;
        }

        // read one line at a time and test 
        while(fileIn.hasNextLine())
        {
            nTests++;
            String nextLine = fileIn.nextLine();
            // remove trailing spaces and $ char, split on ','
            String[] parsedLine = nextLine.replaceAll("\s||$", "").split(",");
            wildcardTest(parsedLine[0], parsedLine[1].equals("1")? true : false);
        }
        fileIn.close(); // done reading file, close

        // report findings
        System.out.printf("%s tests performed, %s correct (%.5s%%)", nTests, nCorrect, 100 * (float) nCorrect / nTests);
    }

    // tests string s both forward and backward with isValid()
    // if both directions are valid, "returns" true via nCorrect++
    // it prints the string, isValid() output and what the answer should have been
    private static void wildcardTest(String s, boolean valid)
    {
        // has to be true in reverse as well due to symmetry
        // resolves problem of cases like *( being considered valid (when done "forward")
        boolean algoAnswer = isValid(s, opening, closing) 
                          && isValid(new StringBuilder(s).reverse().toString(), closing, opening);
        if(algoAnswer== valid) nCorrect++; // for testing
        if(algoAnswer!= valid || !silenceCorrectTests) // can silence correct tests to narrow down problems
        {
            System.out.printf("Test %s: %s%n", nTests, s);
            System.out.printf("Output: %s%n", algoAnswer? "Valid" : "Invalid");
            System.out.printf("%s%n%n", algoAnswer== valid? "Correct" : "Incorrect");
        }
        
    }

    // checks if wildcard expression is valid
    // when forward: opener is (, closer is )
    // when backward: opener is ), closer is (
    public static boolean isValid(String expression, char opener, char closer) 
    {
        // initialize
        openStack = new ArrayStack();
        wildcardCount = 0;

        // for testing, change above
        if(silenceResizing) openStack.silence();
        
         // process string one character at a time
        int n = expression.length();
        for(int i = 0; i < n; i++)
        {
            char c = expression.charAt(i);
            if(c == opener) 
            {
                if(i == n-1) return false; // INVALID if opener at the end of the string
                else openStack.push(opener); // openers always counted otherwise
            }
            else if(c == closer)
            {
                if(openStack.isEmpty()) // if no opener to match closer, have to use wildcard
                {
                    if(wildcardCount == 0) return false; // INVALID if no wildcards left
                    else wildcardCount--; // subtract wildcard to cancel closer
                }
                else openStack.pop(); // if opener present, cancel with closer
            }
            else if(c == wildcard) wildcardCount++;
        }
        
        // now there can only be wildcards or openers left (hanging closers lead to INVALID)
            // but *( will be treated as valid when "forward"
            // so the other direction is called as well where ( is a hanging closer
            // that doesn't yet see the wildcard to its left
        while(!openStack.isEmpty())
        {
            if(wildcardCount > 0) // pop if wildcard available
            {
                openStack.pop();
                wildcardCount--;
            }
            else break;
        }

        // if the string is valid there can't be any unmatched openers
        return openStack.isEmpty();
    }
}

