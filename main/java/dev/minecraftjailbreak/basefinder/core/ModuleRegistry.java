package dev.minecraftjailbreak.basefinder.core;

import java.util.*;

public final class ModuleRegistry {
    private final Map<String, ClientModule> modules = new LinkedHashMap<>();

    public void register(ClientModule module) {
        Objects.requireNonNull(module, "module");
        if (modules.putIfAbsent(module.id(), module) != null)
            throw new IllegalArgumentException("Duplicate module: " + module.id());
    }

    public Optional<ClientModule> get(String id) {
        return Optional.ofNullable(modules.get(id));
    }

    public Collection<ClientModule> all() {
        return Collections.unmodifiableCollection(modules.values());
    }

    public List<String> names() {
        return modules.values().stream().map(ClientModule::displayName).toList();
    }
}
