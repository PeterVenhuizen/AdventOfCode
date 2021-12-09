class ProperSubmarine extends Submarine {
    protected int aim;

    public ProperSubmarine() {
        super();
        this.aim = 0;
    }

    @Override
    protected void forward(int x) {
        this.horizontalPosition += x;
        this.depth += this.aim * x;
    }

    @Override
    protected void down(int x) {
        this.aim += x;
    }

    @Override
    protected void up(int x) {
        this.aim -= x;
    }
}