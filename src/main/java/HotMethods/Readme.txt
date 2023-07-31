This example illustrates the hot methods tab in JRA and JFR. 

1. Make a recording or open the prepared recording (hotmethods_before.jfr) and check where most of the time is spent. 
2. Can you make the application run faster?

Solution:















































1. We spend a lot of time in the LinkedList.equals(Object)/LinkedList.indexOf(Object) method.

2. Looking at the trace to the hottest method, shows that we are passing through the contains method.

3. Contains in a linked list is proportional to the number of entries.

4. A HashSet will, on average, do the trick in constant time.

5. You can compare the time it takes to run the individual work units by looking at the work events, either by enabling the Thread activity lane in the Java Application page, or by looking at them in the Event Browser page.