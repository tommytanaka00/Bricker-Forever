package algo_questions;/*
  Tests for the algorithms part of Ex3_2, OOP, written by Omri Wolf.
  In order to run tests, right click on testSrc directory -> mark directory as -> test sources root.
  then, if you don't have junit downloaded already, right click on "junit" in the second import line
  and download junit 5. continue to run all tests together or individually.
  Disclaimer: Greedy algorithms tests were written and checked by hand, and can be wrong.
  All assumptions I made over the input are written as a comment at each individual test.
  If you have any questions or improvement suggestions,
  please contact me at 054-3397915 or omri.wolf1@mail.huji.ac.il.
  Good luck!
 */

// if your algo_questions directory is different, change this line.

// download me!
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class testAlgo_questions {

    @Test
    public void testNumTrees() {
        // runs the method with a massive input to make sure method is not recursive.
        Solutions.numTrees(12000);
        for (int i = 1; i < 19; i++) {
            assertEquals(catalanNumber(i), Solutions.numTrees(i));
        }
    }

    /**
     * Assumptions:
     * 1. Order does matter: i.e 1 2 =\= 2 1 (1 is 1 liter filled, 2 is 2 liters filled).
     * 2. Staff asked the answer to be correct for 0 <= n <= 48 and return INT,
     *    but n = 46 overflows int max value so the algorithm returns an incorrect solution.
     *    so we are assuming 0 <= n <= 45
     */
    @Test
    public void testBucketWalk() {
        // runs the method with a massive input to make sure method is not recursive.
        Solutions.bucketWalk(12000);
        for (int i = 0; i < 46; i++) {
            assertEquals(fib(i + 1), Solutions.bucketWalk(i));
        }
    }

    /**
     * Assumptions:
     * 1. Assuming for each input, there exists a correct solution.
     * 2. Also assuming x[i] >= 0 (though algorithm should still probably work).
     */
    @Test
    public void testMinLeap() {
        int[] empty_arr = {};
        assertEquals(0, Solutions.minLeap(empty_arr));
        int[] single_arr = {1};
        for (int i = 0; i < 10; i++) {
            single_arr[0] = i;
            assertEquals(0, Solutions.minLeap(single_arr));
        }
        int[] single_jump = {5, 1000, 4, 2};
        assertEquals(1, Solutions.minLeap(single_jump));
        for (int i = 1; i < 20; i++) {
            int[] single_jumps = new int[i];
            Arrays.fill(single_jumps, 1);
            assertEquals(i - 1, Solutions.minLeap(single_jumps));
        }
        int[] anti_greedy = {4, 1, 5, 1, 1, 1, 1, 1};
        assertEquals(2, Solutions.minLeap(anti_greedy));
        int[] random_arr = {1, 3, 1, 2, 1, 3, 2, 1};
        assertEquals(4, Solutions.minLeap(random_arr));
        int[] zero_arr = {2, 0, 1, 3, 2, 1};
        assertEquals(3, Solutions.minLeap(zero_arr));
        int[] large_arr = {1, 1, 2, 4, 1, 5, 1, 1, 1, 1, 1, 1, 3, 1, 2, 6};
        assertEquals(8, Solutions.minLeap(large_arr));
    }



    /**
     * Assumptions:
     * 1. Assuming time for completing a task is positive (greater than 0).
     */
    @Test
    public void testAlotStudyTime() {
        int [] tasks = {};
        int [] timeSlots = {};
        // check empty arrays input
        assertEquals(0, Solutions.alotStudyTime(tasks, timeSlots));
        // one is empty, other is not
        tasks = new int[]{1};
        assertEquals(0, Solutions.alotStudyTime(tasks, timeSlots));
        assertEquals(0, Solutions.alotStudyTime(timeSlots, tasks));
        // no tasks can be completed
        tasks = new int[] {3, 4, 2, 7};
        timeSlots = new int[] {1, 1, 1};
        assertEquals(0, Solutions.alotStudyTime(tasks, timeSlots));
        // all tasks can be completed
        assertEquals(3, Solutions.alotStudyTime(timeSlots, tasks));
        // one task can be completed
        tasks = new int[] {5, 9, 6, 3, 2, 8, 9, 5};
        timeSlots = new int[] {2, 1, 2, 1, 2};
        assertEquals(1, Solutions.alotStudyTime(tasks, timeSlots));
        // checks for efficient placement, not placing task 5 in the time slot 6
        timeSlots = new int[] {6, 1, 4, 5, 10};
        assertEquals(4, Solutions.alotStudyTime(tasks, timeSlots));
        // 10 random generated tasks, 10 random generates timeslots
        tasks = new int[] {86, 59, 84, 97, 63, 20, 99, 94, 23, 20};
        timeSlots = new int[] {100, 89, 7, 99, 36, 5, 13, 72, 22, 69};
        assertEquals(7, Solutions.alotStudyTime(tasks, timeSlots));
    }

    private long catalanNumber(int n) {
        long catalan = 1;
        for (int i = 1; i <= n; i++) {
            catalan *= (4L * i - 2);
            catalan /= (i + 1);
        }
        return catalan;
    }

    private int fib(int n) {
        double Phi = (1 + Math.sqrt(5)) / 2;
        double phi = 1 - Phi;
        return (int) ((Math.pow(Phi, n) - Math.pow(phi, n)) / Math.sqrt(5));
    }



    /**
     * Tests for the alotStudyTime algorithm.
     */
    @Test
    public void alotStudyTimeTest() {
        assertEquals(3, Solutions.alotStudyTime(new int[]{1,1,1,1,1}, new int[]{1,1,1}));
        assertEquals(3, Solutions.alotStudyTime(new int[]{1,1,1}, new int[]{1,1,1,1,1}));
        assertEquals(0, Solutions.alotStudyTime(new int[]{2, 3, 1}, new int[]{}));
        assertEquals(0, Solutions.alotStudyTime(new int[]{}, new int[]{1, 3, 2}));
        assertEquals(3, Solutions.alotStudyTime(new int[]{2, 3, 1}, new int[]{1, 3, 2}));

        assertEquals(1, Solutions.alotStudyTime(new int[]{2, 3, 2}, new int[]{1, 7, 1}),
                "One could use 7, the other two will be left out");
        assertEquals(4, Solutions.alotStudyTime(new int[]{1,7,13,9,2,5,15}, new int[]{2,2,4,6,6,10}),
                "Example: 9->10, 5->6, 2->2, 1->2");
        assertEquals(5, Solutions.alotStudyTime(new int[]{2,5,3,4,9,13,9}, new int[]{1,7,3,2,1,1,10,15}),
                "Example: 13->15, 9->10, 5->7, 3->3, 2->2");
    }

    /**
     * Tests for the minLeap algorithm.
     */
    @Test
    public void minLeapTest() {
        assertEquals(0, Solutions.minLeap(new int[]{3}), "No leaps needed as n-1.");
        assertEquals(3, Solutions.minLeap(new int[]{1, 1, 1, 1}), "Only Option: 1,2,3,4");
        assertEquals(2, Solutions.minLeap(new int[]{1, 2, 1, 1}), "Best: 1,2,4");
        assertEquals(3, Solutions.minLeap(new int[]{1, 4, 6, 3, 1, 1, 1, 1, 1}), "Example: 1,2,3,9");
        assertEquals(3, Solutions.minLeap(new int[]{8, 4, 5, 8, 1, 5, 5, 2, 1, 1, 3, 1, 10}),
                "Example: 1,4,11,13");
        int[] depthArray = new int[100000000];
        Arrays.fill(depthArray, 1);
        assertEquals(depthArray.length - 1, Solutions.minLeap(depthArray),
                "Did you use recursion? keep in mind that recursive calls cost plenty of memory.");
        depthArray[124215] = 38;
        depthArray[89466461] = 1254;
        assertEquals(depthArray.length - depthArray[124215] - depthArray[89466461] + 1,
                Solutions.minLeap(depthArray));
    }

    /**
     * Tests for the bucketWalk algorithm.
     */
    @Test
    public void bucketWalkTest() {
        assertEquals(1, Solutions.bucketWalk(0));
        assertEquals(1, Solutions.bucketWalk(1));
        assertEquals(2, Solutions.bucketWalk(2));
        assertEquals(13, Solutions.bucketWalk(6));
        assertEquals(987, Solutions.bucketWalk(15));
        assertEquals(514229, Solutions.bucketWalk(28));
        assertEquals(14930352, Solutions.bucketWalk(35));
        assertEquals(1836311903, Solutions.bucketWalk(45));
    }

    /**
     * Tests for the numTrees algorithm.
     */
    @Test
    public void numTreesTest(){
        assertEquals(1, Solutions.numTrees(1));
        assertEquals(2, Solutions.numTrees(2));
        assertEquals(5, Solutions.numTrees(3));
        assertEquals(429, Solutions.numTrees(7));
        assertEquals(16796, Solutions.numTrees(10));
        assertEquals(9694845, Solutions.numTrees(15));
        assertEquals(1767263190, Solutions.numTrees(19));
    }

}

