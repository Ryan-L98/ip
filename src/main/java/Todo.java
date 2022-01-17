public class Todo extends Task {
    public Todo(String name) {
        super(name);
    }

    @Override
    public String printStatus() {
        return "[T] " + Task.statusSymbols[super.getStatus()] + " " + this.toString();
    }
}
