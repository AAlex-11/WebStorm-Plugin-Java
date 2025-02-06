package com.example;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VfsUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class CreateFilesAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // Prompt the user for input
        String input = Messages.showInputDialog("Enter a string (e.g., AA01):", "File Creator Plugin", Messages.getQuestionIcon());

        if (input != null && !input.isEmpty()) {
            // Get the current directory
            VirtualFile baseDir = e.getProject().getBaseDir();

            try {
                // Create the .js file
                VirtualFile jsFile = baseDir.createChildData(this, input + ".js");
                VfsUtil.saveText(jsFile, "// " + input + ".js\n");

                // Create the .txt file
                VirtualFile txtFile = baseDir.createChildData(this, input + ".txt");
                VfsUtil.saveText(txtFile, "This is " + input + ".txt\n");

                // Notify the user
                Messages.showInfoMessage("Files created successfully: " + input + ".js and " + input + ".txt", "Success");
            } catch (IOException ex) {
                Messages.showErrorDialog("Failed to create files: " + ex.getMessage(), "Error");
            }
        } else {
            Messages.showErrorDialog("Input cannot be empty.", "Error");
        }
    }
}