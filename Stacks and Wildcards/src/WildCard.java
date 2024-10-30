// implements main method 

public class WildCard 
{
    static final char opening = '('; // opening delimiters
    static final char closing = ')'; // respective closing delimiters
    static final char wildcard = '*';

    static ArrayStack openBuffer;
    static ArrayStack closeBuffer;
    static int wildcardCount;

    public static void main(String[] args)
    {

        wildcardTest("((*)))", true);
        wildcardTest("((*))))", false);
        wildcardTest("((*))(*))", true);
        wildcardTest("((())())", true);

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

        openBuffer = new ArrayStack();
        closeBuffer = new ArrayStack();
        wildcardCount = 0;
        
        // process string one character at a time
        for (char c : expression.toCharArray()) 
        {
            if (opening == c) // left delimiter ()
            {
                // stacks have to be resolved anytime a ( is seen when ) is not empty
                // this will either return invalid or "restart" the stacks
                if (!closeBuffer.isEmpty())
                {
                    cleanStacks();
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

        cleanStacks();
        boolean valid = (openBuffer.isEmpty() && closeBuffer.isEmpty());
        return valid;
    }
        
    private static void cleanStacks()
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
                if(wildcardCount == 0) break; // ( or ) remaining with no wildcard, so INVALID

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
    }

    private static void wildcardTest(String s, boolean valid)
    {
        System.out.printf("Case %s%n", s);
        boolean a = isMatched(s);
        System.out.println(a ? "Valid" : "Invalid"); // valid
        System.out.printf("Answer is %s%n%n%n", a == valid? true : false);
    }
}

