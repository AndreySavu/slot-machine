public class PayoutCalculator {
    private final int[][] payout;

    public PayoutCalculator(int[][] payout) {
        this.payout = payout;
    }

    public int calculatePayout(int[] reelPositions) {
        int symbol = reelPositions[0];
        int count = 1;
        for (int i = 1; i < reelPositions.length; i++) {
            if (reelPositions[i] == symbol) {
                count++;
                symbol = reelPositions[i];
            } else {
                break;
            }
        }
        return payout[reelPositions[0] - 1][SlotMachineConfig.NUM_REELS - count];
    }
}

