package bob;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import bob.command.ByeCommand;
import bob.command.Command;
import bob.command.DeadlineCommand;
import bob.command.DeleteCommand;
import bob.command.EventCommand;
import bob.command.FindCommand;
import bob.command.ListCommand;
import bob.command.MarkCommand;
import bob.command.ToDoCommand;
import bob.exception.BobException;
import bob.exception.DeadlineException;
import bob.exception.EventException;
import bob.exception.FindException;
import bob.exception.InvalidCommandException;
import bob.exception.ToDoException;


/**
 * Represents the parser for the Bob program.
 * Makes sense of the user input and translates it to a command.
 */
public class Parser {

    /**
     * Returns a Command that represents the user's inputs.
     * @param input a string representing the user's input
     * @return Command that was interpreted from user input
     * @throws BobException exceptions that tells Bob to let the user know what went wrong
     */
    public static Command parse(String input) throws BobException {
        String firstWord = input.split(" ")[0];
        if (firstWord.equalsIgnoreCase("list")) {
            return new ListCommand();
        } else if (firstWord.equalsIgnoreCase("mark")) {
            try {
                int index = Integer.parseInt(input.substring(4).trim()) - 1;
                return new MarkCommand(index);
            } catch (NumberFormatException e) {
                throw new InvalidCommandException();
            }
        } else if (firstWord.equalsIgnoreCase("delete")) {
            try {
                int index = Integer.parseInt(input.substring(6).trim()) - 1;
                return new DeleteCommand(index);
            } catch (NumberFormatException e) {
                throw new InvalidCommandException();
            }
        } else if (firstWord.equalsIgnoreCase("deadline")) {
            String[] strArr = input.substring(8).split("/by");
            if (strArr.length <= 1) {
                throw new DeadlineException();
            }
            String taskName = strArr[0].trim();
            try {
                LocalDateTime dateTime = LocalDateTime.parse(strArr[1].trim());
                return new DeadlineCommand(taskName, dateTime);
            } catch (DateTimeParseException e) {
                throw new DeadlineException();
            }
        } else if (firstWord.equalsIgnoreCase("todo")) {
            String taskName = input.substring(4).trim();
            String[] strArr = input.substring(4).split(" ");
            if (strArr.length <= 0 || (strArr.length == 1 && strArr[0].isBlank())) {
                throw new ToDoException();
            }
            return new ToDoCommand(taskName);
        } else if (firstWord.equalsIgnoreCase("event")) {
            String[] strArr = input.substring(5).split("/at");
            if (strArr.length <= 1) {
                throw new EventException();
            }
            try {
                String taskName = strArr[0].trim();
                String[] dateTime = strArr[1].trim().split("T");
                LocalDate date = LocalDate.parse(dateTime[0]);
                String[] times = dateTime[1].split("-");
                LocalTime startTime = LocalTime.parse(times[0]);
                LocalTime endTime = LocalTime.parse(times[1]);
                return new EventCommand(taskName, date, startTime, endTime);
            } catch (DateTimeParseException e) {
                throw new EventException();
            }
        } else if (firstWord.equalsIgnoreCase("bye")) {
            return new ByeCommand();
        } else if (firstWord.equalsIgnoreCase("find")) {
            String searchInput = input.substring(4).trim();
            if (searchInput.isBlank()) {
                throw new FindException();
            }
            return new FindCommand(searchInput);
        } else {
            throw new InvalidCommandException();
        }
    }
}
