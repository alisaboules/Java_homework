package Homework3;

import java.util.HashMap;
import java.util.Map;

public class StatisticsService {

  public static final int MIN_FLOOR = 1;
  public static final int MAX_FLOOR = 5;

  private final Map<Address, Integer> duplicates = new HashMap<>();
  private final Map<String, int[]> floorStatistics = new HashMap<>();

  public void accept(Address address) {
    duplicates.merge(address, 1, Integer::sum);

    int floor = address.floor();

    if (floor >= MIN_FLOOR && floor <= MAX_FLOOR) {
      int[] counts = floorStatistics.computeIfAbsent(
          address.city(), city -> new int[MAX_FLOOR - MIN_FLOOR + 1]);

      counts[floor - MIN_FLOOR]++;
    }
  }

  public Map<Address, Integer> getDuplicates() {
    Map<Address, Integer> result = new HashMap<>();

    for (Map.Entry<Address, Integer> entry : duplicates.entrySet()) {
      if (entry.getValue() > 1) {
        result.put(entry.getKey(), entry.getValue());
      }
    }

    return result;
  }

  public Map<String, int[]> getFloorStatistics() {
    Map<String, int[]> result = new HashMap<>();

    for (Map.Entry<String, int[]> entry : floorStatistics.entrySet()) {
      result.put(entry.getKey(), entry.getValue().clone());
    }

    return result;
  }
}
