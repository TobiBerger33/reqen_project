package org.gruppeEins;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PriceCatalogManager {

    private final List<PriceCatalog> priceCatalogs = new ArrayList<>();

    public void addPriceCatalog(PriceCatalog pc) {
        if (pc != null) {
            priceCatalogs.add(pc);
        }
    }

    public Optional<PriceCatalog> getPriceCatalogById(int id) {
        return priceCatalogs.stream()
                .filter(pc -> pc.getId() == id)
                .findFirst();
    }

    public void updatePriceCatalog(PriceCatalog updatedPc) {
        if (updatedPc == null) {
            return;
        }
        getPriceCatalogById(updatedPc.getId()).ifPresent(existingPc -> {
            int index = priceCatalogs.indexOf(existingPc);
            priceCatalogs.set(index, updatedPc);
        });
    }

    public void removePriceCatalog(int id) {
        priceCatalogs.removeIf(pc -> pc.getId() == id);
    }
    
    public List<PriceCatalog> getAllPriceCatalogs() {
        return new ArrayList<>(priceCatalogs);
    }

    @Override
    public String toString() {
        return "PriceCatalogManager{" +
                "priceCatalogs=" + priceCatalogs +
                '}';
    }
}
