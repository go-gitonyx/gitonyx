package eu.gitonyx.gitonyx.session;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SessionStore {

    private final ConcurrentHashMap<String, UUID> sessions = new ConcurrentHashMap<>();

    public String create(UUID userId) {
        String token = UUID.randomUUID().toString();
        sessions.put(token, userId);
        return token;
    }

    public Optional<UUID> validate(String token) {
        if (token == null) return Optional.empty();
        return Optional.ofNullable(sessions.get(token));
    }

    public void invalidate(String token) {
        if (token != null) sessions.remove(token);
    }
}
