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

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.KeyboardShortcut;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.keymap.Keymap;
import com.intellij.openapi.keymap.ex.KeymapManagerEx;
import com.khande.idea.plugin.tabnumberindicator.action.SwitchTabAction;
import com.khande.idea.plugin.tabnumberindicator.utils.Logger;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Created by Khande on 17/8/22.
 */
public class TabNumberIndicatorAppComponent implements ApplicationComponent {

    public TabNumberIndicatorAppComponent() {
        Logger.init(TabNumberIndicatorAppComponent.class.getSimpleName(), Logger.ERROR);
    }

    @Override
    public void initComponent() {
        addSwitchTabActionsGroup();
    }

    private void addSwitchTabActionsGroup() {
        ActionManager actionManager = ActionManager.getInstance();

        DefaultActionGroup switchTabActionsGroup = new DefaultActionGroup("Switch To Tabs", true);
        addSwitchTabAction(actionManager, switchTabActionsGroup, SwitchTabAction.TAB_INDEX_LAST);
        for (int i = 0; i < 10; i++) {
            switchTabActionsGroup.addSeparator();
            addSwitchTabAction(actionManager, switchTabActionsGroup, i);
        }

        DefaultActionGroup toolsMenu = (DefaultActionGroup) actionManager.getAction("ToolsMenu");
        toolsMenu.add(switchTabActionsGroup);
    }


    private void addSwitchTabAction(@NotNull final ActionManager actionManager, @NotNull DefaultActionGroup switchTabActionGroup,
                                    final int tabIndex) {
        String actionId = tabIndex == SwitchTabAction.TAB_INDEX_LAST ? "Switch To Last Tab" : "Switch To Tab #" + (tabIndex + 1);
        SwitchTabAction switchTabAction = new SwitchTabAction(actionId, tabIndex);

        Keymap activeKeymap = KeymapManagerEx.getInstanceEx().getActiveKeymap();
        int keyCode;
        if (tabIndex == SwitchTabAction.TAB_INDEX_LAST) {
            keyCode = KeyEvent.VK_PERIOD;
        } else {
            keyCode = KeyEvent.VK_0 + (tabIndex + 1) % 10;
        }

        KeyboardShortcut keyboardShortcut = new KeyboardShortcut(KeyStroke.getKeyStroke(keyCode,
                InputEvent.ALT_DOWN_MASK), null);
        activeKeymap.addShortcut(actionId, keyboardShortcut);

        switchTabActionGroup.add(switchTabAction);
        actionManager.registerAction(actionId, switchTabAction);
    }


    @Override
    public void disposeComponent() {

    }

    @Override
    @NotNull
    public String getComponentName() {
        return TabNumberIndicatorAppComponent.class.getCanonicalName();
    }
}
