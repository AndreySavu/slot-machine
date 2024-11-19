import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class ReelSpinnerTest {
    @Test
    public void testSpinReels() {
        int[][] distributionLines = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        ReelSpinner reelSpinner = new ReelSpinner(distributionLines, 3);

        int[] reelPositions = reelSpinner.spinReels();
        assertTrue("Reel positions should match the number of reels.", reelPositions.length == 3);
        assertTrue("Reel[0] should be one of 1, 2, or 3", Arrays.asList(1, 2, 3).contains(reelPositions[0]));
        assertTrue("Reel[1] should be one of 4, 5, or 6", Arrays.asList(4, 5, 6).contains(reelPositions[1]));
        assertTrue("Reel[2] should be one of 7, 8, or 9", Arrays.asList(7, 8, 9).contains(reelPositions[2]));
    }
}