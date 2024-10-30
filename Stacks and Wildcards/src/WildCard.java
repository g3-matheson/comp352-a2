import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WildCard 
{
    static final char opening = '('; // opening delimiters
    static final char closing = ')'; // respective closing delimiters
    static final char wildcard = '*';
    static final char endchar = '$';

    static ArrayStack openStack;
    static int wildcardCount;

    static boolean silenceResizing = true;
    static boolean silenceCorrectTests = true;

    static int nTests = 0;
    static int nCorrect = 0;

    public static void main(String[] args)
    {
        String filename = "src\\test_answers.txt";
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

        while(fileIn.hasNextLine())
        {
            nTests++;
            String nextLine = fileIn.nextLine();
            String[] parsedLine = nextLine.replaceAll("\s||$", "").split(",");
            wildcardTest(parsedLine[0], parsedLine[1].equals("1")? true : false);
        }

        fileIn.close();

        System.out.printf("%s tests performed, %s correct (%2s%%)", nTests, nCorrect, 100 * (float) nCorrect / nTests);
    }

    public static boolean isMatched(String expression, char initiator, char closer) 
    {
        openStack = new ArrayStack();
        wildcardCount = 0;

        if(silenceResizing) openStack.silence();
        
        int n = expression.length();
        // process string one character at a time
        for(int i = 0; i < n; i++)
        {
            char c = expression.charAt(i);
            if(c == initiator)
            {
                if(i == n-1) return false;
                    else openStack.push(initiator);
            }
            else if(c == closer)
            {
                if(openStack.isEmpty())
                {
                    if(wildcardCount == 0) return false;
                    else wildcardCount--;
                }
                else openStack.pop();
            }
            else if(c == wildcard) wildcardCount++;
        }
        
        while(!openStack.isEmpty())
        {
            if(wildcardCount > 0)
            {
                openStack.pop();
                wildcardCount--;
            }
            else break;
        }

        return openStack.isEmpty();

    }

    private static void wildcardTest(String s, boolean valid)
    {
        boolean algoAnswer = isMatched(s, opening, closing) 
                          && isMatched(new StringBuilder(s).reverse().toString(), closing, opening);
        if(algoAnswer== valid) nCorrect++;
        if(algoAnswer!= valid || !silenceCorrectTests)
        {
            System.out.printf("Test %s: %s%n", nTests, s);
            System.out.printf("Output: %s%n", algoAnswer? "Valid" : "Invalid");
            System.out.printf("%s%n%n", algoAnswer== valid? "Correct" : "Incorrect");
        }
        
    }
}

