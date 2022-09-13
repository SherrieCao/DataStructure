import java.io.*;
import java.util.*;

//This following algorithm is a solution following Seemran's example in class

class TimeStamp implements Comparable<TimeStamp> {
    private int time;
    private int status;
    private String guard_id;

    public TimeStamp(int time, int status, String guard_id) {
        this.time = time;
        this.status = status;
        this.guard_id = guard_id;
    }

    public int getTime() {
        return time;
    }

    public int getStatus() {
        return status;
    }

    public String getID() {
        return guard_id;
    }

    @Override
    public int compareTo(TimeStamp o) {
        if (this.time != o.getTime()) {
            return this.time - o.getTime();
        }
        return this.guard_id.compareTo(o.getID());
    }
}

public class lifeguards {
    private static int[] shortestNonOverlapIntervals(TimeStamp[] time_stamps) {
        int time_covered = 0;
        int length_intervals = time_stamps.length / 2;
        int[] intervals = new int[length_intervals];
        System.out.println("Number of Intervals: " + intervals.length);
        Arrays.fill(intervals, 0);
        String[] on_duty_guards = {};
        // Get the total time covered and the intervals when there is only one guard
        for (int i = 0; i < time_stamps.length; i++) {
            TimeStamp value = time_stamps[i];
            int current_time = value.getTime();
            if (on_duty_guards.length > 0) {
                int diff = current_time - time_stamps[i - 1].getTime();
                time_covered += diff;
            }
            // add a new guard to the on-duty list when her shift starts, and remove her
            // when the shift ends.
            if (value.getStatus() == 1) {
                on_duty_guards = Arrays.copyOf(on_duty_guards, on_duty_guards.length + 1);
                on_duty_guards[on_duty_guards.length - 1] = value.getID();
                // System.out.println(
                //         "Current number of on-duty guards: " + on_duty_guards.length + " at time " + current_time);
                // for (int n = 0; n < on_duty_guards.length; n++) {
                //     System.out.println("Current ID:" + on_duty_guards[n]);
                // }
            } else {
                if (on_duty_guards.length == 1) {
                    on_duty_guards = new String[0];
                } else {
                    String[] copy = new String[on_duty_guards.length - 1];
                    // System.out.println("ID of the guard signing off " + value.getID());
                    for (int j = 0, k = 0; j < on_duty_guards.length; j++) {
                        if (!on_duty_guards[j].equals(value.getID()) && k<copy.length) {
                            copy[k] = on_duty_guards[j];
                            k++;
                        }
                    }
                    on_duty_guards = copy;
                }
                // System.out.println(
                //         "Current number of on-duty guards: " + on_duty_guards.length + " at time " + current_time);
                // for (int n = 0; n < on_duty_guards.length; n++) {
                //     System.out.println("Current ID:" + on_duty_guards[n]);
                // }
            }
            // record the intervals when there is exactly one guard with the guard's id
            if (on_duty_guards.length == 1) {
                // System.out.println("There is only one guard! At time " + current_time + ". Next time stamp is "
                //         + time_stamps[i + 1].getTime() + ". Non-overlapping interval is "
                //         + (time_stamps[i + 1].getTime() - current_time));
                int non_overlapping_interval = time_stamps[i + 1].getTime() - current_time;
                // System.out.println("Current guard on duty has ID " + Integer.parseInt(on_duty_guards[0]));
                intervals[Integer.parseInt(on_duty_guards[0])] += non_overlapping_interval;
            }
        }
        int minInterval = intervals[0];
        for (int i = 1; i < intervals.length; i++) {
            // System.out.println("non-overlaping Interval: " + intervals[i]);
            if (intervals[i] < minInterval) {
                minInterval = intervals[i];
            }
        }
        return new int[] { minInterval, time_covered };
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("input/10.in"))) {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("output/10.out")));
            // initialize a dictionary for timestamps
            TimeStamp[] time_stamps = (TimeStamp[]) new TimeStamp[0];
            // fill in the dictionary
            int n = Integer.parseInt(br.readLine());
            for (int i = 0; i < n; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int start = Integer.parseInt(st.nextToken());
                int end = Integer.parseInt(st.nextToken());
                TimeStamp startOfShift = new TimeStamp(start, 1, Integer.toString(i));
                TimeStamp endOfShift = new TimeStamp(end, 0, Integer.toString(i));
                time_stamps = Arrays.copyOf(time_stamps, time_stamps.length + 2);
                time_stamps[time_stamps.length - 2] = startOfShift;
                time_stamps[time_stamps.length - 1] = endOfShift;
            }
            Arrays.sort(time_stamps);
            int[] result = shortestNonOverlapIntervals(time_stamps);
            System.out.println("Total Time covered: " + result[1]);
            System.out.println("Minimum non-overlaping interval: " + result[0]);
            int maxCover = result[1] - result[0];
            pw.println(maxCover);
            pw.close();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
