package org.example;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;

// Showing how many pieces of work was executed.
@Label("Work")
@Category("JFR_Latencies")
@Description("A piece of work executed")
public class WorkEvent extends Event { }
