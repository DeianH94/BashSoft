package bg.softuni.io.commands;

import bg.softuni.exceptions.InvalidInputException;
import bg.softuni.io.IOManager;
import bg.softuni.judge.Tester;
import bg.softuni.network.DownloadManager;
import bg.softuni.repository.StudentsRepository;

public class TraverseFoldersCommand extends Command {
    public TraverseFoldersCommand(String input,
                                  String[] data,
                                  StudentsRepository repository,
                                  Tester tester,
                                  IOManager ioManager,
                                  DownloadManager downloadManager) {
        super(input, data, repository, tester, ioManager, downloadManager);
    }

    @Override
    public void execute() throws Exception {
        String[] data = this.getData();
        if (data.length != 1 && data.length != 2) {
            throw new InvalidInputException(this.getInput());
        }

        if (data.length == 1) {
            this.getIoManager().traverseDirectory(0);
        }

        if (data.length == 2) {
            this.getIoManager().traverseDirectory(Integer.valueOf(data[1]));
        }
    }
}
