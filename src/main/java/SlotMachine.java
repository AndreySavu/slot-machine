import utills.ConsoleWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SlotMachine {
    private final SlotMachineConfig config;
    private final int[][] distributionLines;
    private final int[][] payout;
    private final Random[] randoms;

    private int[] lastReelPos = {1, 2, 3};

    public SlotMachine(SlotMachineConfig config) {
        this.config = config;
        this.distributionLines = buildDistributionLines(config.getDistribution(), config.NUM_REELS, config.TOTAL);
        this.payout = config.getPayout();
        this.randoms = new Random[config.NUM_REELS];
        for (int i = 0; i < config.NUM_REELS; i++) {
            randoms[i] = new Random();
            //удалить потом

            for(int j =0; j<25;j++){
                System.out.print(distributionLines[i][j] + " ");
            }
            System.out.println();
            //удалить потом
        }
    }

    public void play() {
        int totalHits = 0;
        int totalOutcome = 0;

        Map<Integer, Integer> reel1Frequency = new HashMap<>();
        Map<Integer, Integer> reel2Frequency = new HashMap<>();
        Map<Integer, Integer> reel3Frequency = new HashMap<>();

        for (int i = 0; i < config.getNumIterations(); i++) {
            int[] reelPositions = spinReels(i);

            reel1Frequency.put(reelPositions[0], reel1Frequency.getOrDefault(reelPositions[0], 0) + 1);
            reel2Frequency.put(reelPositions[1], reel2Frequency.getOrDefault(reelPositions[1], 0) + 1);
            reel3Frequency.put(reelPositions[2], reel3Frequency.getOrDefault(reelPositions[2], 0) + 1);
            //System.out.println(reelPositions[0]+" "+ reelPositions[1]+" "+ reelPositions[2]);
            int payout = calculatePayout(reelPositions);
            if (payout > 0) {
                totalHits++;
            }
            totalOutcome += payout;
        }
        System.out.println("Reel 1 Probabilities: " + calculateProbabilities(reel1Frequency, config.getNumIterations()));;
        System.out.println("Reel 2 Probabilities: " + calculateProbabilities(reel2Frequency, config.getNumIterations()));
        System.out.println("Reel 3 Probabilities: " + calculateProbabilities(reel3Frequency, config.getNumIterations()));

        double hitFrequency = (double) totalHits / config.getNumIterations();
        double returnToPlayer = (double) totalOutcome / (config.getNumIterations() * config.STAKE);
        ConsoleWriter.printResult(totalOutcome, hitFrequency, returnToPlayer);
    }

    private Map<Integer, Double> calculateProbabilities(Map<Integer, Integer> frequencyMap, int totalSpins) {
        Map<Integer, Double> probabilities = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            int symbol = entry.getKey();
            int frequency = entry.getValue();
            double probability = (double) frequency *25  / totalSpins;
            probabilities.put(symbol, probability);
        }
        return probabilities;
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

    private int[] spinReels(int iteration) {
        int[] reelPositions = new int[SlotMachineConfig.NUM_REELS];

        for (int i = 0; i < SlotMachineConfig.NUM_REELS; i++) {
            reelPositions[i] = distributionLines[i][randoms[i].nextInt(config.TOTAL)];
        }
        return reelPositions;
    }

    private int calculatePayout(int[] reelPositions) {
        int result = 0;
        if (reelPositions[0] == reelPositions[1] && reelPositions[0]== reelPositions[2]){
            result = payout[reelPositions[0] - 1][SlotMachineConfig.NUM_REELS - 3];
            return result;
        }
        else if(reelPositions[0] == reelPositions[1]){
            result = payout[reelPositions[0] - 1][SlotMachineConfig.NUM_REELS - 2];
            return result;
        }
        else
            result  = payout[reelPositions[0] - 1][SlotMachineConfig.NUM_REELS - 1];

        return result;
    }
}
