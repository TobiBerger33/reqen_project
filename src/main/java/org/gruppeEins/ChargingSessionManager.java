package org.gruppeEins;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChargingSessionManager {

    private final List<ChargingSession> sessions = new ArrayList<>();

    public void addSession(ChargingSession session) {
        if (session != null) {
            sessions.add(session);
        }
    }

    public Optional<ChargingSession> getSessionById(int id) {
        return sessions.stream()
                .filter(session -> session.getId() == id)
                .findFirst();
    }

    public ChargingSession getSessionByCustomerId(int customerID) {

        Optional<ChargingSession> session;
        session = sessions.stream()
                .filter(chargingSession-> chargingSession.getCustomer().getId() == customerID)
                .findFirst();

        if (session.isPresent()) {
            return session.get();
        }
        throw new IllegalStateException("There is no current charging session for this user");
    }

    public void updateSession(ChargingSession updatedSession) {
         if (updatedSession == null) {
            return;
        }
        getSessionById(updatedSession.getId()).ifPresent(existingSession -> {
            int index = sessions.indexOf(existingSession);
            sessions.set(index, updatedSession);
        });
    }

    public void removeSession(int id) {
        sessions.removeIf(session -> session.getId() == id);
    }
    
    public List<ChargingSession> getAllSessions() {
        return new ArrayList<>(sessions);
    }

    @Override
    public String toString() {
        return "ChargingSessionManager{" +
                "sessions=" + sessions +
                '}';
    }
}
