package com.intuit.fxrates.persistence.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import com.intuit.fxrates.entity.Counter;
import com.intuit.fxrates.persistence.CounterPersistence;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryCounterPersistence implements CounterPersistence {

    private final Map<String, Integer> counterMap;

    public InMemoryCounterPersistence() {
        this.counterMap = new HashMap<>();
    }

    @Override
    public void incrementCounter(String fromCurrency, String toCurrency) {
        final String mapEntry = fromCurrency + toCurrency;
        counterMap.put(mapEntry, counterMap.getOrDefault(mapEntry, 0) + 1);
    }

    @Override
    public List<Counter> getTopPairs(int topValue) {
        // Create a priority queue (Min heap)
        if (topValue > counterMap.size())
            topValue = counterMap.size();

        final PriorityQueue<String> pq = new PriorityQueue<String>(
                (key1, key2) -> counterMap.get(key1) == counterMap.get(key2) ? key2.compareTo(key1)
                        : counterMap.get(key1) - counterMap.get(key2));

        for (String key : counterMap.keySet()) {
            pq.add(key);

            if (pq.size() > topValue)
                pq.poll();
        }

        final LinkedList<Counter> result = new LinkedList<>();

        while (!pq.isEmpty()) {
            final String pair = pq.poll();
            result.addFirst(new Counter(pair, counterMap.get(pair)));
        }

        return result;
    }
}
