package edu.ithaca.iclibrary;


import java.util.*;


/**
 * This class will be used to store the Saved Search Results
 * Created by Yaw P. Aidoo on 3/29/2016.
 */
public class SavedResultsStorage<Item> {

    private Item[] arr;         // array of items
    private int N;            // number of ele
    // ments on stack

    /**
     * Initializes an empty stack to store references to stored results pages.
     */
    public SavedResultsStorage() {
        arr = (Item[]) new Object[2];
        N = 0;
    }

    /**
     * Is this stack empty?
     *
     * @return true if this stack is empty; false otherwise
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * Returns the number of items in the stack.
     *
     * @return the number of items in the stack
     */
    public int size() {
        return N;
    }


    // resize the main array holding the elements
    private void resize(int capacity) {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = arr[i];
        }
        arr = temp;
    }

    /**
     * Adds the item to this stack.
     *
     * @param item the item to add
     */
    public void push(Item item) {
        if (N == arr.length) resize(2 * arr.length);    // double size of array if its full
        arr[N++] = item;                            // add item
    }

    /**
     * Removes and returns the item most recently added to this stack.
     *
     * @return the item most recently added
     * @throws java.util.NoSuchElementException if this stack is empty
     */
    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = arr[N - 1];
        arr[N - 1] = null;                              // to avoid loitering
        N--;
        // reduce size of array if needed to save space and memory
        if (N > 0 && N == arr.length / 4) resize(arr.length / 2);
        return item;
        //Do we need a pop function since we want to be able to show saved search history and not delete them ??
        //Group??
    }

}

