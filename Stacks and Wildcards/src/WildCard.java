public class WildCard 
{
    static final char opening = '('; // opening delimiters
    static final char closing = ')'; // respective closing delimiters
    static final char wildcard = '*';

    static ArrayStack openStack;
    static ArrayStack closeStack;
    static int wildcardCount;

    static boolean silenceResizing = true;

    public static void main(String[] args)
    {
        // replace with file io
        wildcardTest("((*)))$", true);
        wildcardTest("((*))))$", false);
        wildcardTest("((*))(*))$", true);
        wildcardTest("((())())$", true);
    }

    public static boolean isMatched(String expression) 
    {
        openStack = new ArrayStack();
        closeStack = new ArrayStack();
        wildcardCount = 0;

        if(silenceResizing)
        {
            openStack.silence();
            closeStack.silence();
        }

        // process string one character at a time
        for (char c : expression.toCharArray()) 
        {
            switch(c)
            {
                case opening:
                    // stacks need to be resolved when a new ( comes after )
                    if (!closeStack.isEmpty()) cleanStacks(); // this instance of cleanStacks can leave residual elements in stacks
                    openStack.push(c);
                    break;
                case closing:
                    closeStack.push(c);
                    break;
                case wildcard:
                    wildcardCount++;
                    break;
                default:
                    break;
            }
        }
        cleanStacks(); // this instance of cleanStacks() must make the stacks empty for validity
        boolean valid = (openStack.isEmpty() && closeStack.isEmpty());
        return valid;
    }
        
    private static void cleanStacks()
    {
        while(true)
        {
            if(openStack.isEmpty() && closeStack.isEmpty()) return;; // both empty, ignore wildcards and return

            if(!closeStack.isEmpty() && !openStack.isEmpty()) // both not empty, remove ()
            {
                openStack.pop();
                closeStack.pop();
                continue;
            }
            
            else // one of them is empty
            {
                // '(' or ')' remaining with no wildcard, so return
                // not necessarily invalid, since this can just be residuals left from a cleanup before the end of the string
                if(wildcardCount == 0) return; 

                else if(!openStack.isEmpty()) // (* cancel
                { 
                    openStack.pop();
                    wildcardCount--;
                    continue;
                } 
                else if(!closeStack.isEmpty()) // *) cancel
                {
                    closeStack.pop();
                    wildcardCount--;
                    continue;
                }
            }
        }
    }

    private static void wildcardTest(String s, boolean valid)
    {
        System.out.printf("Case %s%n", s);
        boolean a = isMatched(s);
        System.out.printf("Output: %s%n", a ? "Valid" : "Invalid");
        System.out.printf("%s%n%n", a == valid? "Correct" : "Incorrect");
    }
}

