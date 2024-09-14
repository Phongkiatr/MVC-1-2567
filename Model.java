import java.util.Queue;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Arrays;

public class Model {
    private double milkQuantity;
    private Queue<Cow> cowQueue;
    private MilkingMachineStatus[] milkingMachines;
    private int interruptCount;
    private int completeCowCount;
    private int angryCowCount;
    private int releaseProblemCowCount;

    Model() {
        milkingMachines = new MilkingMachineStatus[10];
        for (int i = 0; i < milkingMachines.length; i++) {
            milkingMachines[i] = new MilkingMachineStatus();
            milkingMachines[i].setMachineID(i);
        }
        cowQueue = new LinkedList<>();
        cowQueue.addAll(Stream.generate(Cow::new).limit(100).collect(Collectors.toList()));
        milkQuantity = 0;
        interruptCount = 0;
        completeCowCount = 0;
        angryCowCount = 0;
        releaseProblemCowCount = 0;
    }

    public double getMilkQuantity() {
        return milkQuantity;
    }

    public int getInterruptCount() {
        return interruptCount;
    }

    public int getCompleteCowCount() {
        return completeCowCount;
    }

    public int getAngryCowCount() {
        return angryCowCount;
    }

    public int getReleaseProblemCowCount() {
        return releaseProblemCowCount;
    }

    public String getMachineStatus(int i) {
        return milkingMachines[i].toString();
    }

    public void startMilkingProcess() {

        for (MilkingMachineStatus machine : milkingMachines) {
            if (Arrays.stream(machine.status).allMatch(status -> "Milking".equals(status))) {
                // ถ้าอยู่ในสถานะ "Milking" จะนับการรีดนม
                Cow cow = machine.getCow();
                if (cow != null) {
                    boolean isPreferredMachine = (machine == milkingMachines[cow.getPreferredMachine() % 10]);

                    if (isPreferredMachine) {
                        milkQuantity += 1;
                        completeCowCount++;
                    } else {
                        milkQuantity += 0.5;
                    }
                    // ปล่อยวัวหลังจากรีดนมแล้ว
                    machine.releaseCow();
                    machine.resetMachine();
                }
            }
        }

        // ถ้ามีวัวตัวผู้ถูกเพิ่มเข้ามาในเครื่องรีดนม จะทำการนำวัวหงุดหงิดกลับไปสู่สายพานตามลำดับ
        for (MilkingMachineStatus machine : milkingMachines) {
            Cow cow = machine.getCow();
            if (!machine.isAvailable() && isMaleCow(cow)) {
                machine.releaseCow();
                machine.resetMachine();
                releaseProblemCowCount++;
                moveAngryCowToQueue();
            }
        }
        
        // ถ้ามีวัวหงุดหงิดเท่ากับหรือเกิน 8 ตัวจะทำการนำวัวหงุดหงิดกลับไปสู่สายพานตามลำดับ
        if (angryCowCount >= 8) {
            moveAngryCowToQueue();
            angryCowCount = 0;
        }

        for (MilkingMachineStatus machine : milkingMachines) {
            // อัปเดตสถานะจาก "Cleaning" เป็น "Ready"
            for (int i = 0; i < machine.status.length; i++) {
                if ("Cleaning".equals(machine.status[i])) {
                    machine.status[i] = "Ready";
                }
            }

            // ถ้าหัวเครื่องรีดทั้ง 4 อยู่ในสถานะ "Ready", อัปเดตสถานะเป็น "Milking"
            long readyCount = Arrays.stream(machine.status).filter(status -> "Ready".equals(status)).count();
            if (readyCount >= 4) {
                Arrays.setAll(machine.status, i -> "Ready".equals(machine.status[i]) ? "Milking" : machine.status[i]);
            }
        }

        // อัปเดตสถานะของเครื่องรีดนมแต่ละหัว
        for (MilkingMachineStatus machine : milkingMachines) {
            if (machine.isAvailable()) {
                continue; // ข้ามขั้นตอนนี้ถ้าเครื่องจักรยังว่างอยู่
            }
            for (String status : machine.status) {
                if (status.equals("Cleaning")) {
                    status = "Ready";
                }
            }
            int statusID = machine.getStatusID();
            if ((machine.status[statusID]).equals("null")) {
                machine.status[statusID] = "Cleaning";
                machine.addStatusID();
            }
        }

        // เพิ่มวัวเข้าเครื่องรีดจนกว่าจะเต็ม
        while (true) {
            if (Arrays.stream(milkingMachines).allMatch(machine -> !machine.isAvailable())) {
                break; // หยุดเมื่อเครื่องรีดทุกเครื่องถูกใช้งานอยู่
            }

            Cow cow = cowQueue.poll(); // Get the next cow from the queue
            cowQueue.add(new Cow());
            int preferredMachine = cow.getPreferredMachine() % 10;
            if (milkingMachines[preferredMachine].isAvailable()) {
                milkingMachines[preferredMachine].assignCow(cow);
                milkingMachines[preferredMachine].status[0] = "Cleaning";
                milkingMachines[preferredMachine].addStatusID();
            } else {
                while (true) {
                    preferredMachine++;
                    if (preferredMachine >= 10) {
                        preferredMachine = 0;
                    }
                    if (milkingMachines[preferredMachine].isAvailable()) {
                        milkingMachines[preferredMachine].assignCow(cow);
                        milkingMachines[preferredMachine].status[0] = "Cleaning";
                        milkingMachines[preferredMachine].addStatusID();
                        angryCowCount++;
                        break;
                    }
                }
            }
        }
    }

    // นำวัวที่หงุดหงิดทั้งหมดกลับสู่สายพานตามลำดับ
    public void moveAngryCowToQueue() {
        for (MilkingMachineStatus machine : milkingMachines) {
            Cow cow = machine.getCow();
            if (!machine.isAvailable() && cow.getPreferredMachine() != machine.getMachineID()) {
                cowQueue.add(cow);
                machine.releaseCow();
                machine.resetMachine();
            }
        }
        interruptCount++;
    } 

    // ตรวจสอบว่าเป็นวัวตัวผู้หรือไม่
    public boolean isMaleCow(Cow cow) {
        return cow.isMale();
    }

    public class MilkingMachineStatus {
        private int machineID;
        private int statusID;
        protected String[] status;
        private Cow cow;

        MilkingMachineStatus() {
            statusID = 0;
            this.status = new String[4]; // Status ของเครื่องรีดนมทั้ง 4 หัว
            for (int i = 0; i < status.length; i++) {
                status[i] = "null";
            }
        }

        public void resetMachine() {
            statusID = 0;
            for (int i = 0; i < status.length; i++) {
                status[i] = "null";
            }
            cow = null;
        }

        public int getMachineID() {
            return machineID;
        }

        public void setMachineID(int machineID) {
            this.machineID = machineID;
        }

        public int getStatusID() {
            return statusID;
        }

        public void addStatusID() {
            if (statusID < 3)
                statusID++;
        }

        public boolean isAvailable() {
            return cow == null;
        }

        public void assignCow(Cow cow) {
            this.cow = cow;
        }

        public void releaseCow() {
            this.cow = null;
        }

        public Cow getCow() {
            return cow;
        }

        public String toString() {
            return String.format(
                    "<html>Cow <br>isMale: %s<br>Preferred: %s<br>Head1: %s<br>Head2: %s<br>Head3: %s<br>Head4: %s</html>",
                    cow.isMale(),
                    cow.getPreferredMachine() + 1,
                    status[0],
                    status[1],
                    status[2],
                    status[3]);
        }

    }

    public class Cow {
        private int udders;
        private boolean isMale;
        private int preferredMachine;

        public Cow() {
            this.udders = 3 + (int) (Math.random() * 3); // Random between 3 - 5
            this.isMale = Math.random() < 0.05; // Random 5% isMale
            this.preferredMachine = (int) (Math.random() * 10); // Random between 0 - 9
        }

        public int getUdders() {
            return udders;
        }

        public boolean isMale() {
            return isMale;
        }

        public int getPreferredMachine() {
            return preferredMachine;
        }

        public String toString() {
            return String.format("Preferred Machine: %d", preferredMachine);
        }

    }

}
