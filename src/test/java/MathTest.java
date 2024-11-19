import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class MathTest {
    @Test
    public void testReturnToPlayerAndHitFrequency() throws IOException {
        SlotMachineConfig config = new SlotMachineConfig(1000000, "payout.csv", "distribution.csv");
        SlotMachine slotMachine = new SlotMachine(config);

        slotMachine.play();

        double expectedRTP = 0.9747;
        double expectedHF = 0.2256;
        double actualRTP = slotMachine.getReturnToPlayer();
        double actualHF = slotMachine.getHitFrequency();

        // здесь можно установить иные условия в зависимости требуемой точности
        assertTrue("RTP should be within expected range", Math.abs(expectedRTP - actualRTP) < 0.007);
        assertTrue("Hit Frequency should be within expected range", Math.abs(expectedHF - actualHF) < 0.005);
    }

}