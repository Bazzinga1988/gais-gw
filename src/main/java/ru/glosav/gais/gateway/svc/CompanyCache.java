package ru.glosav.gais.gateway.svc;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CompanyCache {
    private Map<String, String> cache = new HashMap<>();

    public void clear() {
        cache.clear();
    }

    public void add(String name, String extId) {
        cache.put(name, extId);
    }

    public Optional<String> find(String name) {
        return Optional.ofNullable(cache.get(name));
    }
}
