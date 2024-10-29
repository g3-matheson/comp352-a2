// implements main method 

public class WildCard {
    
    public static void main(String[] args)
    {
        System.out.println(isMatched("((*)))$") ? "Valid" : "Invalid");
        System.out.println(isMatched("((*))))$") ? "Valid" : "Invalid");
        System.out.println(isMatched("(((*)))$") ? "Valid" : "Invalid");
        System.out.println(isMatched("((()))))$") ? "Valid" : "Invalid");
        System.out.println(isMatched("****$") ? "Valid" : "Invalid");
        System.out.println(isMatched("((*))(*))$") ? "Valid" : "Invalid");
    }

    public static boolean isMatched(String expression) {
        final char opening = '('; // opening delimiters
        final char closing = ')'; // respective closing delimiters
        final char wildcard = '*';
        final char end = '$';
        ArrayStack buffer = new ArrayStack( );
        for (char c : expression.toCharArray( )) {
            if (opening == c) // this is a left delimiter
                buffer.push(c);
            else if (closing == c) { // this is a right delimiter
                if (buffer.isEmpty( )) // nothing to match with
                    return false;
                if (buffer.pop( ) != opening)
                    return false; // mismatched delimiter
            } else if (c == wildcard) {
                if (buffer.isEmpty( )) // nothing to match with
                    buffer.push(opening);
                if (buffer.pop( ) != opening)
                    return false;
            }
        }
        return buffer.isEmpty( ); // were all opening delimiters matched?
    }
}
