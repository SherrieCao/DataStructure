// I read about the solution here http://www.usaco.org/current/data/sol_lifeguards_bronze_jan18.html by Nick Wu and the tutorial here https://www.youtube.com/watch?v=0y1L3VvjeA0&ab_channel=AndyrooDoesCoding
// This answer is a combination of the two as well as my own understanding of the question.

import java.io.*;
import java.util.*;
public class lifeguards {
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("input/3.in"))) {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("output/3.out")));
              
            // read in the shift information
            // get the number of shifts
            int n = Integer.parseInt(br.readLine());
            // initialize arrays of starting point and ending point with the length of the total number of shifts.
            int[] start = new int[n];
            int[] end = new int[n];
            // fill in the arrays
            for(int i = 0; i < n; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                start[i] = Integer.parseInt(st.nextToken());
                end[i] = Integer.parseInt(st.nextToken());
            }
            
            // figure out, for each time interval, how many lifeguards are covering it
            int timeSlots = 1000000000;
            int[] numCover = new int[timeSlots];
            for(int i = 0; i < n; i++) {
                for(int t = start[i]; t < end[i]; t++) {
                    // t is one time slot. 
                    numCover[t]++;
                }
            }
            int maxCover = 0;
            for(int i = 0; i < n; i++) {
                // we fire lifeguard i temporarily
                for(int t = start[i]; t < end[i]; t++) {
                    numCover[t]--;
                }
                // count how many intervals are still covered
                int covered = 0;
                for(int t = 0; t < timeSlots; t++) {
                    if(numCover[t] > 0) {
                        covered++;
                    }
                }
                maxCover = Math.max(maxCover, covered);
                // revert the firing
                for(int t = start[i]; t < end[i]; t++) {
                    numCover[t]++;
                }
            }
            pw.println(maxCover);
            pw.close();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
