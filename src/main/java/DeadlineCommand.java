import java.time.LocalDateTime;

public class DeadlineCommand extends AddCommand {
    public LocalDateTime dateTime;
    public DeadlineCommand(String taskName, LocalDateTime dateTime) {
        super(taskName);
        this.dateTime = dateTime;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage store) throws BobException {
        Task task = new Deadline(getTaskName(), this.dateTime);
        addAndUpdate(task, tasks, ui, store);
    }
}
