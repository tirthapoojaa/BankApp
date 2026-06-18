package ratelimiter;

import java.util.concurrent.atomic.AtomicInteger;

public class RequestInfo {

    /*
     * AtomicInteger makes count updates safe when multiple Tomcat request
     * threads handle login attempts from the same IP at the same time.
     */
    private final AtomicInteger requestCount;

    private volatile long windowStartTime;

    public RequestInfo(long windowStartTime) {
        this.requestCount = new AtomicInteger(0);
        this.windowStartTime = windowStartTime;
    }

    public AtomicInteger getRequestCount() {
        return requestCount;
    }

    public long getWindowStartTime() {
        return windowStartTime;
    }

    public void resetWindow(long windowStartTime) {
        this.windowStartTime = windowStartTime;
        this.requestCount.set(0);
    }
}
