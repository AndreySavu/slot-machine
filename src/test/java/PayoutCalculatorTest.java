import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class PayoutCalculatorTest {
    @Test
    public void testCalculatePayout() {

        int[][] payout = new int[][]{
                {5, 2, 0},
                {0, 2, 1},
        };
        PayoutCalculator payoutCalculator = new PayoutCalculator(payout);

        int[] combination = {1, 1, 0};
        assertEquals(2, payoutCalculator.calculatePayout(combination));
    }

    @Test
    public void testCalculatePayoutZero() {

        int[][] payout = new int[][]{
                {0, 1, 5},
                {0, 2, 10},
        };
        PayoutCalculator payoutCalculator = new PayoutCalculator(payout);

        int[] combination = {1, 1, 1};
        assertEquals(0, payoutCalculator.calculatePayout(combination));
    }
}