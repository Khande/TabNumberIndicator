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
