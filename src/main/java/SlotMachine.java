import utills.Writter;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SlotMachine {
    private final SlotMachineConfig config;
    private final Random random = new Random(163874342);
    private final int[][] distributionLines;
    private final int[][] payout;
    private final double mean;
    private final double stddev;

    public SlotMachine(SlotMachineConfig config) {
        this.config = config;
        this.distributionLines = buildDistributionLines(config.getDistribution(), config.NUM_REELS, config.TOTAL);
        this.payout = config.getPayout();
        this.mean = (config.TOTAL - 1) / 2.0;
        this.stddev = (config.TOTAL - 1) / 6.0;
    }

    public void play() {
        int cycle = config.TOTAL ^ 3;
        int totalHits = 0;
        int totalOutcome = 0;

        for (int i = 0; i < config.getNumIterations(); i++) {
            int[] reelPositions = spinReels();
            int payout = calculatePayout(reelPositions);

            if (payout > 0) {
                totalHits++;
            }
            totalOutcome += payout;
        }

        double hitFrequency = (double) totalHits / config.getNumIterations();
        double returnToPlayer = (double) totalOutcome / (config.getNumIterations() * config.STAKE);

        Writter.printResult(totalOutcome, hitFrequency, returnToPlayer);
    }

    private int[][] buildDistributionLines(int[][] distribution, int numReels, int maxSymbols) {
        int[] currentPosOnLines = new int[numReels];
        int[][] result = new int[numReels][maxSymbols];
        for (int i = 0; i < distribution.length; i++) {
            for (int j = 0; j < numReels; j++) {
                for (int k = 0; k < distribution[i][j]; k++) {
                    if (currentPosOnLines[j] < maxSymbols) {
                        result[j][currentPosOnLines[j]] = i + 1;
                        currentPosOnLines[j]++;
                    }
                }
            }
        }
        return result;

    }

    private int[] spinReels() {
        int[] reelPositions = new int[SlotMachineConfig.NUM_REELS];
        for (int i = 0; i < SlotMachineConfig.NUM_REELS; i++) {
            int position;
            do {
                position = (int) Math.round(random.nextInt(24) + 0);
                System.out.print(position + " ");
            } while (position < 0 || position >= config.TOTAL);
            reelPositions[i] = distributionLines[i][position];
        }
        //System.out.println(reelPositions[0] + " " + reelPositions[1] + " " + reelPositions[2]);
        return reelPositions;
    }

    private int calculatePayout(int[] reelPositions) {
        Map<Integer, Integer> symbolCount = new HashMap<>();

        for (int symbol : reelPositions) {
            symbolCount.put(symbol, symbolCount.getOrDefault(symbol, 0) + 1);
        }

        int maxPayout = 0;

        for (Map.Entry<Integer, Integer> entry : symbolCount.entrySet()) {
            int symbol = entry.getKey();
            int count = entry.getValue();

            maxPayout = Math.max(maxPayout, payout[symbol - 1][SlotMachineConfig.NUM_REELS - count]);

        }

        return maxPayout;
    }
}