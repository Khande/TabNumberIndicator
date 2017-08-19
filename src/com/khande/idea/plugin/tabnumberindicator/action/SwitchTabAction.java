package com.khande.idea.plugin.tabnumberindicator.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Khande on 17/8/19.
 */
public class SwitchTabAction extends AnAction {

    public static final int TAB_INDEX_LAST = -1;

    private int mTabIndex;

    public SwitchTabAction(@Nullable String text, int tabIndex) {
        super(text);
        mTabIndex = tabIndex;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            return;
        }

        ToolWindowManager windowManager = ToolWindowManager.getInstance(project);
        if (windowManager.isEditorComponentActive()) {
            switchTab(project, mTabIndex);
        }
    }


    private void switchTab(@NotNull final Project project, final int tabIndex) {
        FileEditorManagerEx fileEditorManagerEx = FileEditorManagerEx.getInstanceEx(project);
        EditorWindow currentWindow = fileEditorManagerEx.getCurrentWindow();
        if (currentWindow == null) {
            return;
        }

        VirtualFile[] openedFiles = currentWindow.getFiles();
        int targetFileIndex = tabIndex == TAB_INDEX_LAST ? openedFiles.length - 1 : tabIndex;
        if (targetFileIndex >= 0 && targetFileIndex < openedFiles.length) {
            fileEditorManagerEx.openFile(openedFiles[targetFileIndex], true);
        }

    }


}
