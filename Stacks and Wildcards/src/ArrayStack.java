/* ArrayStack -- implements a stack data structure with an array
* Starts with size n = 2
* The amortized complexity to add any element to the stack must not exceed O(1) over a sequence of n pushes.
* your code should display the current size of the stack before you expand it
    followed by a message indicating the stack will be expanded and to what size
*/

import java.util.Arrays;
import java.lang.RuntimeException;

public class ArrayStack {
    
    private final static int startingSize = 2;
    private char[] stack;
    private int capacity;
    // iterator
    private int i;
    
    // constructor
    public ArrayStack()
    {
        stack = new char[startingSize];
        capacity = startingSize;
        i = 0;
    }

    // pushes a new character onto the stack
    public void push(char c){
        stack[i++] = c;
        if(isFull()) resize();
    }

    // pops a character from the stack
    public char pop(){
        if(isEmpty()) throw new RuntimeException("Stack is empty, cannot pop an element.");
        else
        {
            char c = top();
            stack[i--] = (char) 0; // char null (\u0000)
            return c;
        }
        
    }
    
    // returns the character at the top of the stack without removing it
    public char top()
    {
        char c = stack[i];
        return c;
    }
    
    // returns the current size of the stack
    public int size()
    {
        return i;
    }

    // returns whether or not the stack is empty
    public boolean isEmpty()
    {
        if(size() == 0) return true;
        return false;
    }

    // returns whether or not the stack is full
    public boolean isFull()
    {
        if(size() == capacity) return true;
        return false;
    }

    private void resize()
    {
        capacity *= 2;
        stack = Arrays.copyOf(stack, capacity);
    }


}
