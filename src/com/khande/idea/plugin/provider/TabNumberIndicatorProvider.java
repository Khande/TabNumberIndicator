package com.khande.idea.plugin.provider;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorTabTitleProvider;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Khande on 17/8/19.
 */
public class TabNumberIndicatorProvider implements EditorTabTitleProvider {

    private static final Logger LOGGER = Logger.getInstance(TabNumberIndicatorProvider.class);

    @Nullable
    @Override
    public String getEditorTabTitle(Project project, VirtualFile file) {
        FileEditorManagerEx fileEditorManagerEx = FileEditorManagerEx.getInstanceEx(project);
        EditorWindow currentWindow = fileEditorManagerEx.getCurrentWindow();
        if (currentWindow != null) {
            VirtualFile[] files = currentWindow.getFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].equals(file)) {
                    return (i + 1) + ": " + file.getPresentableName();
                }
            }

        }
        return null;
    }


}
