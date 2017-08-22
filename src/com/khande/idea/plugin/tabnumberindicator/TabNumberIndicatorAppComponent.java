package com.khande.idea.plugin.tabnumberindicator;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.KeyboardShortcut;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.keymap.Keymap;
import com.intellij.openapi.keymap.ex.KeymapManagerEx;
import com.intellij.util.messages.MessageBus;
import com.intellij.util.messages.MessageBusConnection;
import com.khande.idea.plugin.tabnumberindicator.action.SwitchTabAction;
import com.khande.idea.plugin.tabnumberindicator.utils.Logger;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Created by Khande on 17/8/19.
 */
public class TabNumberIndicatorAppComponent implements ProjectComponent {

    private MessageBusConnection connection;

    public TabNumberIndicatorAppComponent() {
        Logger.init(TabNumberIndicatorAppComponent.class.getSimpleName(), Logger.DEBUG);
    }

    @Override
    public void initComponent() {
        Logger.debug("Init TabNumberIndicatorAppComponent");
        MessageBus messageBus = ApplicationManager.getApplication().getMessageBus();
        connection = messageBus.connect();
        connection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new TabNumberIndicatorEditorListener());

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

        DefaultActionGroup editorTabsGroup = (DefaultActionGroup) actionManager.getAction("EditorTabsGroup");
        editorTabsGroup.add(switchTabActionsGroup);
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
        Logger.debug("Disposing component");
        connection.disconnect();
    }

    @Override
    @NotNull
    public String getComponentName() {
        return TabNumberIndicatorAppComponent.class.getCanonicalName();
    }
}
