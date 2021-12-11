import java.util.*;

public class Octopus {
    private int energyLevel; // 0-9
    private boolean flashed;
    private List<Octopus> adjacent;

    public Octopus(int energyLevel) {
        this.flashed = false;
        this.energyLevel = energyLevel;
    }

    public void setAdjacent(List<Octopus> adjacent) {
        this.adjacent = adjacent;
    }

    public List<Octopus> getAdjacent() {
        return this.adjacent;
    }

    public int getEnergyLevel() {
        return this.energyLevel;
    }

    public boolean isPoweredDown() {
        return this.flashed;
    }

    public boolean isReadyToFlash() {
        return this.energyLevel > 9;
    }

    public void increaseEnergyLevel() {
        this.energyLevel++;
    }

    public void powerDownForToday() {
        this.flashed = true;
        this.energyLevel = 0;
    }

    public void restart() {
        this.flashed = false;
    }

    public void makeFlashable() {
        this.flashed = false;
    }

    @Override
    public String toString() {
        return Integer.toString(this.energyLevel);
    }
}
