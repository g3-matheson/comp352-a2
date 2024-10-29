// implements main method 

public class WildCard 
{
    static final char opening = '('; // opening delimiters
    static final char closing = ')'; // respective closing delimiters
    static final char wildcard = '*';

    public static void main(String[] args)
    {

        wildcardTest("((*)))", true);
        wildcardTest("((*))))", false);
        wildcardTest("((*))(*))", true);

        /* 
    
        System.out.println(isMatched("((*))))$") ? "Valid" : "Invalid"); // invalid
        System.out.println(isMatched("(((*))$") ? "Valid" : "Invalid"); // valid
        System.out.println(isMatched("((()))))$") ? "Valid" : "Invalid"); // invalid
        System.out.println(isMatched("****$") ? "Valid" : "Invalid"); // valid
        System.out.println(isMatched("((*))(*))$") ? "Valid" : "Invalid"); // invalid
        System.out.println(isMatched("((*)((*)$") ? "Valid" : "Invalid"); // valid
    
        */
    }

    public static boolean isMatched(String expression) 
    {
        ArrayStack openBuffer = new ArrayStack();
        ArrayStack closeBuffer = new ArrayStack();
        int wildcardCount = 0;

        // process string one character at a time
        for (char c : expression.toCharArray()) 
        {
            if (opening == c) // left delimiter ()
            {
                // stacks have to be resolved anytime a ( is seen when ) is not empty
                // this will either return invalid or "restart" the stacks
                if (!closeBuffer.isEmpty())
                {
                    if(checkStacks(openBuffer, closeBuffer, wildcardCount)) // if "sub"stack valid, continue
                    {
                        openBuffer = new ArrayStack();
                        closeBuffer = new ArrayStack();
                        wildcardCount = 0;
                    }
                    else return false; // if "sub"stack invalid, entire thing will be invalid
                }
                
                // add ( either to existing buffers or new ones
                openBuffer.push(c);
            }
            else if (closing == c) // right delimiter )
            { 
                closeBuffer.push(c);
            } 
            else if (c == wildcard) 
            {
                wildcardCount++;
            } 
            else break; // end char ($) or anything else unexpected
        }

        return checkStacks(openBuffer, closeBuffer, wildcardCount);
    }
        
    private static boolean checkStacks(ArrayStack openBuffer, ArrayStack closeBuffer, int wildcardCount)
    {
        while(true)
        {
            if(openBuffer.isEmpty() && closeBuffer.isEmpty()) break; // both empty, ignore wildcards, VALID!

            if(!closeBuffer.isEmpty() && !openBuffer.isEmpty()) // both not empty, remove ()
            {
                openBuffer.pop();
                closeBuffer.pop();
                //System.out.printf("Both not empty.\n Open: %s%n Closed: %s%n Wildcards: %s%n", openBuffer.toString(), closeBuffer.toString(), wildcardCount);
                continue;
            }
            
            else // one of them is empty
            {
                if(wildcardCount == 0) return false; // ( or ) remaining with no wildcard, so INVALID

                else if(!openBuffer.isEmpty()) // (* cancel
                { 
                    openBuffer.pop();
                    wildcardCount--;
                    //System.out.printf("Open not empty.\n Open: %s%n Closed: %s%n Wildcards: %s%n", openBuffer.toString(), closeBuffer.toString(), wildcardCount);
                    continue;
                } 
                else if(!closeBuffer.isEmpty()) // *) cancel
                {
                    closeBuffer.pop();
                    wildcardCount--;
                    //System.out.printf("Closed not empty.\n Open: %s%n Closed: %s%n Wildcards: %s%n", openBuffer.toString(), closeBuffer.toString(), wildcardCount);
                    continue;
                }
            }
        }

        // both buffers are empty, regardless of how many wildcards left it's valid
        return true;
    }

    private static void wildcardTest(String s, boolean valid)
    {
        System.out.printf("Case %s%n", s);
        boolean a = isMatched(s);
        System.out.println(a ? "Valid" : "Invalid"); // valid
        System.out.printf("Answer is %s%n%n%n", a == valid? true : false);
    }
}

