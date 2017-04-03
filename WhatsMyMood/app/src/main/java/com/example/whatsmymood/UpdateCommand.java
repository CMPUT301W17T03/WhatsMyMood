package com.example.whatsmymood;

/**
 * Created by nathan on 02/04/17.
 */

public class UpdateCommand extends Command {
    private UserAccount user;

    public UpdateCommand(UserAccount user) {
        this.user = user;
    }

    @Override
    public void executeCommand() {
        ElasticSearchUserController.UpdateUser updateUser = new ElasticSearchUserController.UpdateUser();
        updateUser.execute(this.user);
    }
}
