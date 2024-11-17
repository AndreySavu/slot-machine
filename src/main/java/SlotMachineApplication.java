import java.io.IOException;

public class SlotMachineApplication {
    public static void main(String[] args) {
        try {
            int numIterations = args.length > 0 ? Integer.parseInt(args[0]) : 10;
            SlotMachineConfig config = new SlotMachineConfig(numIterations, "payout.csv", "distribution.csv");
            SlotMachine slotMachine = new SlotMachine(config);
            slotMachine.play();
        } catch (IOException e) {
            System.err.println("Error loading configuration: " + e.getMessage());
        }
    }
}
