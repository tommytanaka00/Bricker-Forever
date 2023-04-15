package algo_questions;

import java.util.Arrays;

public class Solutions {


    public static int alotStudyTime(int[] tasks, int[] timeSlots){
        //Base case
        if (tasks.length == 0 || timeSlots.length == 0)
        {
            return 0;
        }
        int numOfTimes = 0;
        Arrays.sort(tasks);
        Arrays.sort(timeSlots);

        /*
        We go through all the timeSlots, and check if a task fits.
        Since the arrays are sorted, if a certain task doesn't fit a timeslot, then
        all tasks that come after (that are bigger) will not fit in that or any of the
        previous timeslots, and this makes it more efficient
         */
        int j = 0;
        for(int i=0; i < Math.min(tasks.length, timeSlots.length); i++) {
            while(j < timeSlots.length){
                if(tasks[i] <= timeSlots[j]) {
                    numOfTimes++;
                    j++;
                    break;
                }
                j++;
            }
        }
        return numOfTimes;
    }

    //We will use a dynamic algorithm
    public static int minLeap(int[] leapNum){
        if (leapNum.length <= 0) //Base case
            return 0;

        int[] leapsToGetTo = new int[leapNum.length];
        for (int leapNumIdx = 0; leapNumIdx < leapNum.length; leapNumIdx++) {
            leapsToGetTo[leapNumIdx] = leapNum.length;
            if (leapNumIdx == 0)
                leapsToGetTo[leapNumIdx] = 0;
        }

        /*
        Each iteration updates the leaps required to get to a certain leaf.
        For example: from the first leaf, we can get to leapsToGetTo[0] different leaves
        (that will all have 1). All three types of maxIndex tells us what was the maximum
        index that we changed (for example, if leapsToGetTo[0] = 3, then maxIdx will be 3 since we
        updated 1,2 and 3 to be "a single jump can get there (1)")

        prevMaxIdx is the maximum index that the previous iteration updated.
        maxMaxIdx is the maximum index that was updated from all the previous jumps
        (for example, if the array after the first iteration is [0, 1, 1, 1, 1,...],
        if at leapNumIdx=2 we updated the 5th idx and at leapNumIdx=3 we updated the 7th idx,
        then the maxMaxIdx will be 7 but the maxIdx will still be 4 until we finish all the 1's,
        and only then the maxIdx will be the bigger between the two (between 5 and 7)

        (I hope the explanation was clear ^^')
         */
        int amountOfLeapsToGetTo = 1;
        int prevMaxIdx = 0;
        int maxIdx = 0;
        int maxMaxId = 0;
        for (int leapNumIdx = 0; leapNumIdx < leapNum.length; leapNumIdx++) {
            for (int j = 1; j <= leapNum[leapNumIdx]; j++) {
                if (leapNumIdx + j >= leapNum.length)
                    break;
                if (amountOfLeapsToGetTo < leapsToGetTo[leapNumIdx + j]) {
                    leapsToGetTo[leapNumIdx + j] = amountOfLeapsToGetTo;
                    if (leapNumIdx + j >= maxMaxId)
                    {
                        maxMaxId = leapNumIdx + j;
                    }
                }
            }
            if (leapNumIdx == prevMaxIdx){
                maxIdx = maxMaxId;
            }
            if (maxIdx > prevMaxIdx){
                prevMaxIdx = maxIdx;
                amountOfLeapsToGetTo++;
            }

        }
        return leapsToGetTo[leapNum.length - 1];
    }


    public static int bucketWalk(int n){
        if (n == 0 || n == 1) //Base case
            return 1;
        int[] toFindFibonacci = new int[n + 1];
        toFindFibonacci[0] = 1;
        toFindFibonacci[1] = 1;

        //every iteration here fills up the i-th fibonacci number, and they all depend
        //on the values that come before
        for (int i = 2; i < toFindFibonacci.length; i++) {
            toFindFibonacci[i] = toFindFibonacci[i - 1] + toFindFibonacci[i - 2];
        }
        return toFindFibonacci[toFindFibonacci.length - 1];
    }

    public static int numTrees(int n){
        if (n == 0 || n == 1) //Base case
            return 1;
        int[] toFindCatalan = new int[n + 1];
        toFindCatalan[0] = 1;
        toFindCatalan[1] = 1;

        //every iteration here fills up the i-th catalan number, and they all depend
        //on the values that come before
        for (int i = 2; i < toFindCatalan.length; i++) {
            int finalNum = 0;
            for (int j = 0; j < i; j++) {
                finalNum += toFindCatalan[j] * toFindCatalan[(i-1)-j];
            }
            toFindCatalan[i] = finalNum;
        }

        return toFindCatalan[toFindCatalan.length - 1];
    }
}
