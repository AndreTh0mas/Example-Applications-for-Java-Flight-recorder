package EventStreamin;

import jdk.management.jfr.RemoteRecordingStream;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.time.Duration;

// This is just an example, showcasing the use of RemoteRecordingStream
public class RemoteRecording {
    // Since jdk 16 only:
    // Just like external EventStream, with this we can do the same on a network via JMX connection
    public static void main(String[] args) throws IOException {
        String host = "com.spr";
        int port = 4711;
        String url = "service:jmx:rmi:///jndi/rmi://" + host + ":" + port + "/jmxrmi";
        JMXServiceURL u = new JMXServiceURL(url);
        JMXConnector c = JMXConnectorFactory.connect(u);
        MBeanServerConnection conn = c.getMBeanServerConnection();
        try (var rs = new RemoteRecordingStream(conn)) {
            rs.enable("jdk.GCPhasePause").withoutThreshold();
            rs.enable("jdk.CPULoad").withPeriod(Duration.ofSeconds(1));
            rs.onEvent("jdk.CPULoad", System.out::println);
            rs.onEvent("jdk.GCPhasePause", System.out::println);
            rs.start();
        }
    }
}
