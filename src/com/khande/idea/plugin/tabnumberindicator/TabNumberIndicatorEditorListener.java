package com.khande.idea.plugin.tabnumberindicator;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorTabbedContainer;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.tabs.JBTabs;
import com.intellij.ui.tabs.TabInfo;
import com.intellij.ui.tabs.TabsListener;
import com.khande.idea.plugin.tabnumberindicator.utils.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Khande on 17/8/19.
 */
public class TabNumberIndicatorEditorListener implements FileEditorManagerListener {

    @Override
    public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
        Logger.info("file opened");
        refreshTabNumberIndicator(source.getProject());
    }

    @Override
    public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
        Logger.info("file closed");
        refreshTabNumberIndicator(source.getProject());
    }

    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent event) {
        Logger.info("selection changed");
    }

    private void refreshTabNumberIndicator(@NotNull final Project project) {
        FileEditorManagerEx fileEditorManagerEx = FileEditorManagerEx.getInstanceEx(project);
        EditorWindow currentWindow = fileEditorManagerEx.getCurrentWindow();
        if (currentWindow != null) {
            EditorTabbedContainer tabbedPane = currentWindow.getTabbedPane();
            if (tabbedPane == null) {
                return;
            }

            final VirtualFile[] files = currentWindow.getFiles();

            final JBTabs tabs = tabbedPane.getTabs();
            tabs.addListener(new TabsListener() {
                @Override
                public void selectionChanged(TabInfo oldSelection, TabInfo newSelection) {

                }

                @Override
                public void beforeSelectionChanged(TabInfo oldSelection, TabInfo newSelection) {

                }

                @Override
                public void tabRemoved(TabInfo tabToRemove) {

                }

                @Override
                public void tabsMoved() {
                    setRespondingTabNameForFile(files, tabs);
                }
            });

            setRespondingTabNameForFile(files, tabs);
        }

    }


    private void setRespondingTabNameForFile(@NotNull final VirtualFile[] files, @NotNull final JBTabs tabs) {
        for (int i = 0; i < files.length; i++) {
            tabs.getTabAt(i).setText((i + 1) + ": " + files[i].getPresentableName());
        }
    }

}
