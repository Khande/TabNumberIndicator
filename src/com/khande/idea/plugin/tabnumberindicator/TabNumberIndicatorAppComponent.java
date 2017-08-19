package com.khande.idea.plugin.tabnumberindicator;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.util.messages.MessageBus;
import com.intellij.util.messages.MessageBusConnection;
import com.khande.idea.plugin.tabnumberindicator.action.SwitchTabAction;
import com.khande.idea.plugin.tabnumberindicator.utils.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Khande on 17/8/19.
 */
public class TabNumberIndicatorAppComponent implements ApplicationComponent {

    private MessageBusConnection connection;

    public TabNumberIndicatorAppComponent() {
        Logger.init(TabNumberIndicatorAppComponent.class.getSimpleName(), Logger.WARN);
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
        String actionName = tabIndex == SwitchTabAction.TAB_INDEX_LAST ? "Switch To Last Tab" : "Switch To Tab #" + (tabIndex + 1);
        SwitchTabAction switchTabAction = new SwitchTabAction(actionName, tabIndex);
//        switchTabAction.registerCustomShortcutSet(KeyEvent.VK_0 + tabIndex,
//                InputEvent.ALT_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK | InputEvent.META_DOWN_MASK, null);
        switchTabActionGroup.add(switchTabAction);
        actionManager.registerAction(actionName, switchTabAction);
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
