package org.vaadin.TinyMCE;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

import static org.vaadin.TinyMCE.TinyMCEeditorBuilder.Menubar.*;
import static org.vaadin.TinyMCE.TinyMCEeditorBuilder.Plugins.*;
import static org.vaadin.TinyMCE.TinyMCEeditorBuilder.Toolbar.*;


@Route("")
public class AddonView extends Div {

    public AddonView() {
        TinyMCEeditor theAddon = new TinyMCEeditorBuilder()
                .setInnerHTML("<p>Hello world</p>")
                .setApiKey("asfksfasakfslafkflsasadj")
                .setHeight("20em")
                .setMenubar(MENU_CODEFORMAT)
                .setToolbar(TOOLBAR_BOLD, TOOLBAR_COPY, TOOLBAR_CUT, TOOLBAR_CODE, TOOLBAR_ALIGNCENTER)
                .setPlugins(PLUGIN_ADVLIST, PLUGIN_ANCHOR, PLUGIN_TABLE)
                .createTinyMCEeditor();
        Button get = new Button("getValue", l -> System.out.println(theAddon.getValue()));
        Button set = new Button("set", l -> theAddon.appendStrValue("setValue"));
        add(theAddon, get, set);
    }
}
