import static org.junit.Assert.* ;

/** 
 * An intermixed sequence of 10 push and 10 pop operations 
 * are performed on a LIFO stack. The pushes push the integers 
 * 0 through 9 in order; the pops print out the return value.
 * 
 * Given a sequence of elements in the order they are popped, 
 * the isValidPopSequence returns whether or not such a sequence of 
 * pop operations is possible with a stack data structure.
 */

public class StackOpsValidator  {
    /*
    * @param onStack: elements denote whether index value is on stack
    * @return highest known value on the stack if stack is not empty
    *           else -1
    */
    private static int topGuaranteed(int[] onStack)    {
        int max = -1;
        for (int i=0; i<onStack.length; i++)   {
            if (onStack[i] == 1)
                max = i;
        }
        return max;
    }

    public static boolean isValidPopSequence(int[] popSequence)  {
        int numPushes = 10;
        /*
        * onStack[i] == 1: known with certainty element i is on stack
        * onStack[i] == 0: known with certainty element i not on stack
        * onStack[i] == -1: unknown whether or not element i is on stack
        */
        int[] onStack = new int[numPushes];
        for (int i=0; i<numPushes;i++)
            onStack[i] = -1;
        for (int popped: popSequence)   {
            // cannot pop an element that we know for sure is not on stack
            if (onStack[popped] == 0)   {
                System.out.println("onStack[popped] != 1");
                return false;
            }
            // cannot pop an element below the top-most element that 
            // we know for certain is on the stack
            if (popped < topGuaranteed(onStack))    {
                System.out.println("popped < topGuaranteed(onStack)");
                return false;
            }
            // denote that this element is no longer on the stack
            onStack[popped] = 0;
            // now we know that elements smaller than this element
            // which we were uncertain about before, are on the stack
            int indexToUpdate = popped - 1;
            while (indexToUpdate >= 0 && onStack[indexToUpdate] == -1) 
                onStack[indexToUpdate--] = 1;
        }
        return true;
    }

    public static void main(String[] args)  {
        int[] testInput = {0, 2, 1, 4, 5, 6, 9, 8, 7, 3};
        assertTrue(isValidPopSequence(testInput));
        testInput = new int[]{1, 2, 4, 0, 5, 6, 7, 3, 8, 9};
        assertFalse(isValidPopSequence(testInput));
        testInput = new int[]{1, 0, 2, 5, 7, 9, 8, 6, 4, 3};
        assertTrue(isValidPopSequence(testInput));
        testInput = new int[]{4, 3, 2, 1, 0, 5, 6, 7, 8, 9};
        assertTrue(isValidPopSequence(testInput));
        testInput = new int[]{0, 5, 4, 3, 7, 6, 2, 9, 1, 8};
        assertFalse(isValidPopSequence(testInput));
    }
}
