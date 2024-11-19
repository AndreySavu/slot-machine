import lombok.Getter;

import java.io.IOException;

import static utills.CsvReader.loadFromCsv;

@Getter
public class SlotMachineConfig {
    static final int STAKE = 1;
    static final int NUM_REELS = 3;
    static final int TOTAL = 25;
    static final int NUM_SYMBOLS = 11;

    private final int numIterations;
    private final int[][] payout;
    private final int[][] distribution;

    public SlotMachineConfig(int numIterations, String payoutPath, String distributionPath) throws IOException {
        this.numIterations = numIterations;
        this.payout = loadFromCsv(payoutPath, NUM_SYMBOLS, NUM_REELS);
        this.distribution = loadFromCsv(distributionPath, NUM_SYMBOLS, NUM_REELS);
    }
}