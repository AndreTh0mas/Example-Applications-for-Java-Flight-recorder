This example features a logger that is shared between a bunch of worker threads.


1. The idea with the worker threads is to get work done in parallel.


Solution:























































1. The blocking events all seem to be due to calls to the Log method.

2. There are a few ways to fix this available to us. We can cut the call to the logger altogether. If the logging doesn't use a shared resource,
   we can just remove the synchronization from the log method. We could also provide each thread with its own logger, carefully making sure that 
   we do not end up blocking on another shared resource instead. Of course, this is just an example, and no such limitations exist - any of these
   solutions would do.

3. The latency_fixed.jfr recording shows the situation after the fix. The problem is no longer flagged.