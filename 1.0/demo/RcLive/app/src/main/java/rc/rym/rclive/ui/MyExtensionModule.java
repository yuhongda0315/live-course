package rc.rym.rclive.ui;

import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.emoticon.IEmoticonTab;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.model.Conversation;

public class MyExtensionModule extends DefaultExtensionModule {

    private MyPlugin myPlugin;
    private MyEmoticon myEmoticon;

    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        if (myPlugin == null) {
            myPlugin = new MyPlugin();
        }
        List<IPluginModule> pluginModules =  super.getPluginModules(conversationType);
        pluginModules.add(myPlugin);
        return pluginModules;
    }

    @Override
    public List<IEmoticonTab> getEmoticonTabs() {
        if (myEmoticon == null) {
            myEmoticon = new MyEmoticon();
        }
        List<IEmoticonTab> emoticonTabs =  super.getEmoticonTabs();
        emoticonTabs.add(myEmoticon);
        return emoticonTabs;
    }
}
