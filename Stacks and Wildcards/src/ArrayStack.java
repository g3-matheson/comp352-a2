/* ArrayStack -- implements a stack data structure with an array
* Starts with size n = 2
* The amortized complexity to add any element to the stack must not exceed O(1) over a sequence of n pushes.
* your code should display the current size of the stack before you expand it
    followed by a message indicating the stack will be expanded and to what size
*/

import java.lang.RuntimeException;

public class ArrayStack {

    /* 
    public static void main(String[] args) {
        ArrayStack testStack = new ArrayStack();
        
        System.out.println(testStack.isEmpty());

        testStack.push('A');
        System.out.println(testStack.toString());
        testStack.push('B');
        System.out.println(testStack.toString());
        
        System.out.println(testStack.size());
        System.out.println(testStack.isFull());

        // This should trigger an expansion
        testStack.push('C');  // Expected output: expansion messages
        System.out.println(testStack.toString());
        System.out.println(testStack.size());

        testStack.push('D');
        System.out.println(testStack.toString());
        testStack.push('E');
        System.out.println(testStack.toString());
        System.out.println(testStack.pop());

        System.out.println(testStack.top());
        System.out.println(testStack.toString());
    }
    */
    
    private final static int startingSize = 2;
    private char[] stack;
    private int capacity;
    // iterator
    private int iterator;
    
    // constructor
    public ArrayStack()
    {
        stack = new char[startingSize];
        capacity = startingSize;
        iterator = 0;
    }

    // pushes a new character onto the stack
    public void push(char c){
        stack[iterator] = c;
        iterator++;
       
        if(isFull()) resize();
    }

    // pops a character from the stack
    public char pop(){
        if(isEmpty()) throw new RuntimeException("Stack is empty, cannot pop() an element.");
        else
        {
            char c = top();
            stack[iterator-1] = (char) 0; // char null (\u0000)
            iterator--;
            return c;
        }
        
    }
    
    // returns the character at the top of the stack without removing it
    public char top()
    {
        if(isEmpty()) throw new RuntimeException("Stack is empty, cannot top() an element.");
        return stack[iterator-1];
    }
    
    // returns the current size of the stack
    public int size()
    {
        return iterator;
    }

    // returns whether or not the stack is empty
    public boolean isEmpty()
    {
        if(iterator == 0) return true;
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
        System.out.println("Stack is full, with current capacity " + iterator + ".\n"+
                "Capacity will be increased to: " + capacity +".\n");
        char[] tmpStack = new char[capacity];
        for(int i = 0; i < size(); i++)
        {
            tmpStack[i] = stack[i];
        }
        stack = tmpStack;
    }

    public String toString()
    {
        String s = "";
        for(int i = 0; i < iterator; i++)
        {
            s += stack[i];
        }
        return s;
    }
    

}
