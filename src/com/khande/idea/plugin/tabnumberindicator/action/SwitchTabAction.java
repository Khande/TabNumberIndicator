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

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

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
