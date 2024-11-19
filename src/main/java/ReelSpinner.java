import java.util.Random;

public class ReelSpinner {
    private final int[][] distributionLines;
    private final Random[] randoms; // используем отдельный ГСЧ для каждого барабана
    private final int totalSymbols;

    public ReelSpinner(int[][] distributionLines, int totalSymbols) {
        this.distributionLines = distributionLines;
        this.totalSymbols = totalSymbols;
        this.randoms = new Random[distributionLines.length];
        for (int i = 0; i < distributionLines.length; i++) {
            randoms[i] = new Random();
        }
    }

    public int[] spinReels() {
        int[] reelPositions = new int[distributionLines.length];
        for (int i = 0; i < distributionLines.length; i++) {
            reelPositions[i] = distributionLines[i][randoms[i].nextInt(totalSymbols)];
        }
        return reelPositions;
    }
}
