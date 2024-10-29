// implements main method 

public class WildCard 
{
    
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
        final char opening = '('; // opening delimiters
        final char closing = ')'; // respective closing delimiters
        final char wildcard = '*';

        ArrayStack openBuffer = new ArrayStack();
        ArrayStack closeBuffer = new ArrayStack();
        int wildcardCount = 0;

        // process string into the stacks
        for (char c : expression.toCharArray()) 
        {
            if (opening == c) // this is a left delimiter
            {
                if (!closeBuffer.isEmpty() && checkStacks(openBuffer, closeBuffer, wildcardCount))
                {
                    openBuffer = new ArrayStack();
                    closeBuffer = new ArrayStack();
                    wildcardCount = 0;
                }
                // stacks have to be resolved anytime a ( is seen when ) is not empty
                openBuffer.push(c);
            }
            else if (closing == c) 
            { // this is a right delimiter
                closeBuffer.push(c);
            } 
            else if (c == wildcard) 
            {
                wildcardCount++;
            } 
            else break; // end char ($)
        }

        /* testing
        System.out.printf("Open Stack: %s%n", openBuffer.toString());
        System.out.printf("Closed Stack: %s%n", closeBuffer.toString());
        System.out.printf("%s wildcards%n", wildcardCount);
        */

        return checkStacks(openBuffer, closeBuffer, wildcardCount);
    }
        
    private static boolean checkStacks(ArrayStack openBuffer, ArrayStack closeBuffer, int wildcardCount)
    {
        while(true)
        {
            if(!closeBuffer.isEmpty() && !openBuffer.isEmpty()) // both not empty
            {
                openBuffer.pop();
                closeBuffer.pop();
                //System.out.printf("Both not empty.\n Open: %s%n Closed: %s%n Wildcards: %s%n", openBuffer.toString(), closeBuffer.toString(), wildcardCount);
                continue;
            }
            if(openBuffer.isEmpty() && closeBuffer.isEmpty()) break;
            else // one of them is empty
            {
                if(wildcardCount == 0) return false; // ( or ) left with no wildcard, so false
                else if(!openBuffer.isEmpty())
                { 
                    openBuffer.pop();
                    wildcardCount--;
                    //System.out.printf("Open not empty.\n Open: %s%n Closed: %s%n Wildcards: %s%n", openBuffer.toString(), closeBuffer.toString(), wildcardCount);
                    continue;
                } 
                else if(!closeBuffer.isEmpty())
                {
                    closeBuffer.pop();
                    wildcardCount--;
                    //System.out.printf("Closed not empty.\n Open: %s%n Closed: %s%n Wildcards: %s%n", openBuffer.toString(), closeBuffer.toString(), wildcardCount);
                    continue;
                }
            }
        }
        //System.out.println("Made it here");
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

