import lombok.Getter;
import utills.ConsoleWriter;

public class SlotMachine {
    private final SlotMachineConfig config;
    private final ReelSpinner reelSpinner;
    private final PayoutCalculator payoutCalculator;

    @Getter
    private double hitFrequency;
    @Getter
    private double returnToPlayer;
    @Getter
    private int totalOutcome = 0;

    public SlotMachine(SlotMachineConfig config) {
        this.config = config;
        int[][] distributionLines = buildDistributionLines(config.getDistribution(), SlotMachineConfig.NUM_REELS, SlotMachineConfig.TOTAL);
        int[][] payout = config.getPayout();
        this.reelSpinner = new ReelSpinner(distributionLines, SlotMachineConfig.TOTAL);
        this.payoutCalculator = new PayoutCalculator(payout);
    }

    public void play() {
        int totalHits = 0;


        for (int i = 0; i < config.getNumIterations(); i++) {
            int[] reelPositions = reelSpinner.spinReels();
            int payout = payoutCalculator.calculatePayout(reelPositions);
            if (payout > 0) {
                totalHits++;
            }
            totalOutcome += payout;
        }

        hitFrequency = (double) totalHits / config.getNumIterations();
        returnToPlayer = (double) totalOutcome / (config.getNumIterations() * SlotMachineConfig.STAKE);
        ConsoleWriter.printResult(totalOutcome, hitFrequency, returnToPlayer);
    }

    // данный метод транспонирует данные из csv-файла и раскладывает 11 элементов в 25,
    // чтобы потом удобно было выбирать рандомом
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
}
