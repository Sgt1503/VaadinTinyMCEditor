package org.vaadin.TinyMCE;


/**
 * @author Sergey.Tolstykh
 * @version 1.0
 *          Date 30.05.2022
 */

public class TinyMCEeditorBuilder {

    //Plugins
    public enum Plugins{
        PLUGIN_ADVLIST("advlist"),
        PLUGIN_AUTOLINK("autolink"),
        PLUGIN_LISTS("lists"),
        PLUGIN_LINK("link"),
        PLUGIN_IMAGE("image"),
        PLUGIN_CHARMAP("charmap"),
        PLUGIN_PREVIEW("preview"),
        PLUGIN_ANCHOR("anchor"),
        PLUGIN_SEARCHREPLACE("searchreplace"),
        PLUGIN_VISUALBLOCKS("visualblocks"),
        PLUGIN_FULLSCREEN("fullscreen"),
        PLUGIN_INSERTDATETIME("insertdatetime"),
        PLUGIN_MEDIA("media"),
        PLUGIN_TABLE("table"),
        PLUGIN_CODE("code"),
        PLUGIN_HELP("help"),
        PLUGIN_WORDCOUNT("wordcount");

        private final String jsParamName;

        Plugins(String jsParamName) {
            this.jsParamName = jsParamName;
        }
    }
    //menu
    public enum Menubar {
        MENU_ALIGN("align"),
        MENU_BACKCOLOR("backcolor"),
        MENU_BLOCKS("blocks"),
        MENU_BOLD("bold"),
        MENU_CODEFORMAT("codeformat"),
        MENU_COPY("copy"),
        MENU_CUT("cut"),
        MENU_FORECOLOR("forecolor"),
        MENU_FONTFAMILY("fontfamily"),
        MENU_FONTSIZE("fontsize"),
        MENU_HR("hr"),
        MENU_ITALIC("italic"),
        MENU_LANGUAGE("language"),
        MENU_LINEHEIGHT("lineheight"),
        MENU_NEWDOCUMENT("newdocument"),
        MENU_PASTE("paste"),
        MENU_PASTETEXT("pastetext"),
        MENU_PRINT("print"),
        MENU_REDO("redo"),
        MENU_REMOVEFORMAT("removeformat"),
        MENU_SELECTALL("selectall"),
        MENU_STRIKETHROUGH("strikethrough"),
        MENU_STYLES("styles"),
        MENU_SUBSCRIPT("subscript"),
        MENU_SUPERSCRIPT("superscript"),
        MENU_UNDERLINE("underline"),
        MENU_UNDO("undo"),
        MENU_VISUALAID("visualaid");

        private final String jsParamName;
        Menubar(String jsParamName) {
            this.jsParamName = jsParamName;
        }
    }
    //toolbar
    public enum Toolbar{
        TOOLBAR_ALIGNCENTER("aligncenter"),
        TOOLBAR_ALIGNJUSTIFY("alignjustify"),
        TOOLBAR_ALIGNLEFT("alignleft"),
        TOOLBAR_ALIGNNONE("alignnone"),
        TOOLBAR_ALIGNRIGHT("alignright"),
        TOOLBAR_BLOCKQUOTE("blockquote"),
        TOOLBAR_BACKCOLOR("backcolor"),
        TOOLBAR_BLOCKS("blocks"),
        TOOLBAR_BOLD("bold"),
        TOOLBAR_COPY("copy"),
        TOOLBAR_CUT("cut"),
        TOOLBAR_FONTFAMILY("fontfamily"),
        TOOLBAR_FONTSIZE("fontsize"),
        TOOLBAR_FORECOLOR("forecolor"),
        TOOLBAR_H1("h1"),
        TOOLBAR_H2("h2"),
        TOOLBAR_H3("h3"),
        TOOLBAR_H4("h4"),
        TOOLBAR_H5("h5"),
        TOOLBAR_H6("h6"),
        TOOLBAR_HR("hr"),
        TOOLBAR_INDENT("indent"),
        TOOLBAR_ITALIC("italic"),
        TOOLBAR_LANGUAGE("language"),
        TOOLBAR_LINEHEIGHT("lineheight"),
        TOOLBAR_NEWDOCUMENT("newdocument"),
        TOOLBAR_OUTDENT("outdent"),
        TOOLBAR_PASTE("paste"),
        TOOLBAR_PASTETEXT("pastetext"),
        TOOLBAR_PRINT("print"),
        TOOLBAR_REDO("redo"),
        TOOLBAR_REMOVE("remove"),
        TOOLBAR_REMOVEFORMAT("removeformat"),
        TOOLBAR_SELECTALL("selectall"),
        TOOLBAR_STRIKETHROUGH("strikethrough"),
        TOOLBAR_STYLES("styles"),
        TOOLBAR_SUBSCRIPT("subscript"),
        TOOLBAR_SUPERSCRIPT("superscript"),
        TOOLBAR_UNDERLINE("underline"),
        TOOLBAR_UNDO("undo"),
        TOOLBAR_VISUALAID("visualaid"),
        TOOLBAR_CODE("code");

        private final String jsParamName;
        Toolbar(String jsParamName) {
            this.jsParamName = jsParamName;
        }
    }



    private String height;
    private String menubar;
    private String plugins;
    private String toolbar;
    private String contentStyle;
    private String apiKey;
    private String innerHTML;

    public TinyMCEeditorBuilder setInnerHTML(String innerHTML) {
        this.innerHTML = innerHTML;
        return this;
    }

    public TinyMCEeditorBuilder setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public TinyMCEeditorBuilder setHeight(String height) {
        this.height = height;
        return this;
    }

    public TinyMCEeditorBuilder setMenubar(String... menu) {
        this.menubar = prepareMenubar(menu);
        return this;
    }

    public TinyMCEeditorBuilder setMenubar(Menubar... menubar) {
        this.menubar = prepareMenubar(menubar);
        return this;
    }

    public TinyMCEeditorBuilder setPlugins(String... plugins) {
        this.plugins = preparePlugins(plugins);
        return this;
    }

    public TinyMCEeditorBuilder setPlugins(Plugins... plugins) {
        this.plugins = preparePlugins(plugins);
        return this;
    }

    public TinyMCEeditorBuilder setToolbar(String... toolbar) {
        this.toolbar = prepareToolbar(toolbar);
        return this;
    }

    public TinyMCEeditorBuilder setToolbar(Toolbar... toolbar) {
        this.toolbar = prepareToolbar(toolbar);
        return this;
    }

    public TinyMCEeditorBuilder setContentStyle(String... contentStyle) {
        this.contentStyle = prepareContentStyle(contentStyle);
        return this;
    }

    public TinyMCEeditor createTinyMCEeditor() {
        return new TinyMCEeditor(apiKey, innerHTML,height, menubar, plugins, toolbar, contentStyle);
    }


    //under development TODO: support of different initializing styles of editor's components
    private String prepareToolbar(String... args){
        String s = "";
        for (int i = 0; i < args.length; i++) {
            if (i == 1)
                s += args[i];
        }
        return s;
    }

    private String prepareToolbar(Toolbar... args){
        String s = "";
        for (Toolbar arg : args) {
            s += arg.jsParamName + " ";
        }
        return s;
    }

    private String preparePlugins(String... args){
        String s = "";
        for (String arg : args) {
            s += arg + " ";
        }
        return s;
    }

    private String preparePlugins(Plugins... args){
        String s = "";
        for (Plugins arg : args) {
            s += arg.jsParamName + " ";
        }
        return s;
    }

    private String prepareMenubar(String... args){
        String s = "";
        for (String arg : args) {
            s += arg + " ";
        }
        return s;
    }

    private String prepareMenubar(Menubar... args){
        String s = "";
        for (Menubar arg : args) {
            s += arg.jsParamName + " ";
        }
        return s;
    }

    private String prepareContentStyle(String... args){
        String s = "";
        for (String arg : args) {
                s += arg;
        }
        return s;
    }

}