package ratelimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiter {

    private static final int MAX_REQUESTS = 5;
    private static final long WINDOW_MILLIS = 60 * 1000L;

    /*
     * ConcurrentHashMap is used because a servlet application handles many
     * requests concurrently. It allows safe per-IP lookup/creation without
     * locking the entire map for every login request.
     */
    private final Map<String, RequestInfo> requests = new ConcurrentHashMap<>();

    public RateLimitResult checkRequest(String clientIp) {

        long now = System.currentTimeMillis();
        RequestInfo requestInfo = requests.computeIfAbsent(
                clientIp,
                ip -> new RequestInfo(now));

        /*
         * Fixed window algorithm:
         * - each IP gets a windowStartTime
         * - requests are counted until 60 seconds pass
         * - after the window expires, the count resets and a new window starts
         *
         * Synchronizing on the per-IP RequestInfo keeps "check window, maybe
         * reset, increment count" as one atomic operation for that IP. This
         * avoids reset/increment races while still allowing different IPs to
         * be processed independently.
         */
        synchronized (requestInfo) {
            long elapsedTime = now - requestInfo.getWindowStartTime();
            if (elapsedTime >= WINDOW_MILLIS) {
                requestInfo.resetWindow(now);
                elapsedTime = 0;
            }

            int requestCount = requestInfo.getRequestCount().incrementAndGet();
            if (requestCount <= MAX_REQUESTS) {
                return RateLimitResult.allowed();
            }

            long retryAfterMillis = WINDOW_MILLIS - elapsedTime;
            long retryAfterSeconds = Math.max(1,
                    (long) Math.ceil(retryAfterMillis / 1000.0));
            return RateLimitResult.rejected(retryAfterSeconds);
        }
    }

    public static class RateLimitResult {

        private final boolean allowed;
        private final long retryAfterSeconds;

        private RateLimitResult(boolean allowed, long retryAfterSeconds) {
            this.allowed = allowed;
            this.retryAfterSeconds = retryAfterSeconds;
        }

        public static RateLimitResult allowed() {
            return new RateLimitResult(true, 0);
        }

        public static RateLimitResult rejected(long retryAfterSeconds) {
            return new RateLimitResult(false, retryAfterSeconds);
        }

        public boolean isAllowed() {
            return allowed;
        }

        public long getRetryAfterSeconds() {
            return retryAfterSeconds;
        }
    }
}
