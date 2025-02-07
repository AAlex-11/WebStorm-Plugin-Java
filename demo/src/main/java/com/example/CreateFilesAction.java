package com.example;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CreateFilesAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // Get the selected directory from the project structure
        VirtualFile selectedDir = e.getData(CommonDataKeys.VIRTUAL_FILE);

        if (selectedDir == null || !selectedDir.isDirectory()) {
            Messages.showErrorDialog("Please select a valid directory in the project structure.", "Error");
            return;
        }

        // Prompt user for input
        String input = Messages.showInputDialog("Enter a string (e.g., AA01):", "File Creator Plugin", Messages.getQuestionIcon());

        if (input != null && !input.isEmpty()) {
            WriteCommandAction.runWriteCommandAction(e.getProject(), () -> {
                try {
                    // Create .js file in the selected directory
                    VirtualFile jsFile = selectedDir.createChildData(this, input + ".js");
                    jsFile.setBinaryContent(("// " + input + ".js\n").getBytes());

                    // Create .txt file in the selected directory
                    VirtualFile txtFile = selectedDir.createChildData(this, input + ".txt");
                    txtFile.setBinaryContent(("This is " + input + ".txt\n").getBytes());

                    Messages.showInfoMessage("Files created in: " + selectedDir.getPath(), "Success");
                } catch (IOException ex) {
                    Messages.showErrorDialog("Failed to create files: " + ex.getMessage(), "Error");
                }
            });
        } else {
            Messages.showErrorDialog("Input cannot be empty.", "Error");
        }
    }
}
