class Wire {
    private String name;
    private Integer steps;

    public Wire(String name) {
        this.name = name;
        this.steps = 0;
    }

    public String getName() {
        return this.name;
    }

    public void addStep() {
        this.steps++;
    }

    public Integer getSteps() {
        return this.steps;
    }
}