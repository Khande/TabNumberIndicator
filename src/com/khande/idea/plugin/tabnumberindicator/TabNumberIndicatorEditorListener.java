/*
 * Copyright 2017 Khande
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import org.jetbrains.annotations.Nullable;

/**
 * Created by Khande on 17/8/19.
 */
public class TabNumberIndicatorEditorListener implements FileEditorManagerListener {

    private EditorWindow mEditorWindow;
    private JBTabs mOpenedFileTabs;

    @Override
    public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
        Logger.info("file opened");

        if (mEditorWindow == null) {
            Project project = source.getProject();
            FileEditorManagerEx fileEditorManagerEx = FileEditorManagerEx.getInstanceEx(project);
            mEditorWindow = fileEditorManagerEx.getCurrentWindow();
        }

        if (mEditorWindow != null && mOpenedFileTabs == null) {
            EditorTabbedContainer tabbedPane = mEditorWindow.getTabbedPane();
            if (tabbedPane != null) {
                mOpenedFileTabs = tabbedPane.getTabs();
                mOpenedFileTabs.addListener(new TabsListener() {
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
                        Logger.debug("tabsMoved");
                        refreshTabNumberIndicator(mEditorWindow, mOpenedFileTabs);
                    }
                });
            }
        }

        refreshTabNumberIndicator(mEditorWindow, mOpenedFileTabs);
    }

    @Override
    public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
        Logger.info("file closed");
        refreshTabNumberIndicator(mEditorWindow, mOpenedFileTabs);
    }


    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent event) {
        Logger.info("file selection changed.");
    }
    

    private void refreshTabNumberIndicator(@Nullable final EditorWindow editorWindow, @Nullable final JBTabs tabs) {
        if (editorWindow == null || tabs == null) {
            return;
        }
        VirtualFile[] files = editorWindow.getFiles();
        Logger.debug("opened files count: " + files.length);
        for (int i = 0; i < files.length; i++) {
            tabs.getTabAt(i).setText((i + 1) + ": " + files[i].getPresentableName());
        }
    }

}
